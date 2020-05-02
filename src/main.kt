import java.io.StringBufferInputStream
import java.util.*
import kotlin.collections.*

fun main(args: Array<String>) {
//    arrayManipulationTask()
//    magicSquareTask()
//    pickingNumbersTask()
    climbingLeaderboardTask()
}

fun climbingLeaderboardTask() {
    val scan = Scanner(System.`in`)

    val scoresCount = scan.nextLine().trim().toInt()

    val scores = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()

    val aliceCount = scan.nextLine().trim().toInt()

    val alice = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()

    val result = climbingLeaderboard(scores, alice)
    println(result.joinToString("\n"))
    val result2 = climbingLeaderboardDescending(scores, alice)
    println(result2.joinToString("\n"))
}

fun climbingLeaderboard(scores: Array<Int>, alice: Array<Int>): Array<Int> {
    val aliceRanks = Array(alice.size) { -1 }
    val reversedScores = scores.sortedArray()
    val arr = ArrayList<Int>()
    arr.add(reversedScores[0])
    for (i in 1 until reversedScores.size) {
        if (reversedScores[i] > arr.last()) {
            arr.add(reversedScores[i])
        }
    }

    val lowestRank = arr.size +  1

    for (i in alice.indices) {
        val score = alice[i]
        val idx = binarySearchAscending(arr.toTypedArray(), 0, arr.size, score)//arr.binarySearch(score)
        if (idx < 0) {
            aliceRanks[i] = lowestRank + 1 + idx
        } else {
            aliceRanks[i] = arr.size - idx
        }
    }

    return aliceRanks
}

fun binarySearchAscending(a: Array<Int>, fromIndex: Int, toIndex: Int, key: Int): Int {
    var low = fromIndex
    var high = toIndex - 1
    while (low <= high) {
        val mid = low + high ushr 1
        val midVal = a[mid]
        val cmp = midVal.compareTo(key)
        if (cmp < 0) low = mid + 1 else if (cmp > 0) high = mid - 1 else return mid // key found
    }
    return -(low + 1) // key not found.
}

fun climbingLeaderboardDescending(scores: Array<Int>, alice: Array<Int>): Array<Int> {
    val aliceRanks = Array(alice.size) { -1 }
    val arr = ArrayList<Int>()
    arr.add(scores[0])
    for (i in 1 until scores.size) {
        if (scores[i] < arr.last()) {
            arr.add(scores[i])
        }
    }

    for (i in alice.indices) {
        val score = alice[i]
        val idx = binarySearchDescending(arr.toTypedArray(), 0, arr.size, score)//arr.binarySearch(score)
        if (idx < 0) {
            aliceRanks[i] = -idx
        } else {
            aliceRanks[i] = idx + 1
        }
    }

    return aliceRanks
}

fun binarySearchDescending(a: Array<Int>, fromIndex: Int, toIndex: Int, key: Int): Int {
    var low = fromIndex
    var high = toIndex - 1
    while (low <= high) {
        val mid = low + high ushr 1
        val midVal = a[mid]
        val cmp = midVal.compareTo(key)
        if (cmp < 0) high = mid - 1 else if (cmp > 0) low = mid + 1 else return mid // key found
    }
    return -(low + 1) // key not found.
}

fun pickingNumbersTask() {
    val n = readLine()!!.trim().toInt()

    val a = readLine()!!.trimEnd().split(" ").map{ it.toInt() }.toTypedArray()

    val result = pickingNumbers(a)

    println(result)
}

fun pickingNumbers(a: Array<Int>): Int {
    val arrays = ArrayList<ArrayList<Int>>()

    a.sort()

    for (i in a.indices) {
        val arr = ArrayList<Int>()
        arr.add(a[i])
        for (j in a.indices) {
            if (i != j && (a[j] - a[i] in 0..1)) {
                arr.add(a[j])
            }
        }
        arrays.add(arr)
    }

    arrays.sortBy {
        it.size
    }

    return arrays.last().size
}

fun magicSquareTask() {
    val scan = Scanner(System.`in`)

    val s = Array(3) { Array(3) { 0 } }

    for (i in 0 until 3) {
        s[i] = scan.nextLine().split(" ").map { it.trim().toInt() }.toTypedArray()
    }

    val result = formingMagicSquare(s)

    println(result)
}

fun formingMagicSquare(s: Array<Array<Int>>): Int {
    val all = arrayOf(
            arrayOf(arrayOf(8, 1, 6), arrayOf(3, 5, 7), arrayOf(4, 9, 2)),
            arrayOf(arrayOf(6, 1, 8), arrayOf(7, 5, 3), arrayOf(2, 9, 4)),
            arrayOf(arrayOf(4, 9, 2), arrayOf(3, 5, 7), arrayOf(8, 1, 6)),
            arrayOf(arrayOf(2, 9, 4), arrayOf(7, 5, 3), arrayOf(6, 1, 8)),
            arrayOf(arrayOf(8, 3, 4), arrayOf(1, 5, 9), arrayOf(6, 7, 2)),
            arrayOf(arrayOf(4, 3, 8), arrayOf(9, 5, 1), arrayOf(2, 7, 6)),
            arrayOf(arrayOf(6, 7, 2), arrayOf(1, 5, 9), arrayOf(8, 3, 4)),
            arrayOf(arrayOf(2, 7, 6), arrayOf(9, 5, 1), arrayOf(4, 3, 8))
    )

    val diffs = Array(8) { 0 }
    all.forEachIndexed { index, ms ->
        diffs[index] = calculateCost(ms, s)
    }
    return diffs.min() ?: 0
}

fun calculateCost(ms: Array<Array<Int>>, s: Array<Array<Int>>): Int {
    var cost: Int = 0
    for (i in 0 until 3) {
        for (j in 0 until 3) {
            if (ms[i][j] != s[i][j]) {
                cost += kotlin.math.abs(s[i][j] - ms[i][j])
            }
        }
    }
    return cost
}

fun arrayManipulationTask() {
    val scan = Scanner(StringBufferInputStream(ClassLoader.getSystemResource("array_data.txt").readText()))
//    val scan = Scanner(System.`in`)

    val nm = scan.nextLine().split(" ")

    val n = nm[0].trim().toInt()

    val m = nm[1].trim().toInt()

    val queries = Array<Array<Int>>(m) { Array<Int>(3) { 0 } }

    for (i in 0 until m) {
        queries[i] = scan.nextLine().split(" ").map { it.trim().toInt() }.toTypedArray()
    }

    val result = arrayManipulation(n, queries)

    println(result)
}

// Array manipulation
fun arrayManipulation(n: Int, queries: Array<Array<Int>>): Long {
    var maxValue = 0L
    val arr = Array<Long>(n) { 0L }
    queries.forEach { query ->
        val startIdx = query[0] - 1
        val endIdx = query[1]
        val summand = query[2]
        arr[startIdx] = arr[startIdx] + summand
        if (endIdx < n) arr[endIdx] = arr[endIdx] - summand;
    }

    var temp = 0L
    for (i in 0 until n) {
        temp += arr[i];
        if (temp > maxValue) maxValue = temp;
    }

    return maxValue
}
