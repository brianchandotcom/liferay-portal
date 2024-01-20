/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmark;

import com.liferay.petra.string.StringBundler;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * @author Dante Wang
 */
public class Statistics {

	public Statistics(String testName, int runCount) {
		_testName = testName;
		_runCount = runCount;
	}

	public void finish() {
		long totalTime = System.currentTimeMillis() - _startTime;

		System.out.println(
			StringBundler.concat(
				"\nTests took ", totalTime, " ms. Each ", _testName,
				"session took ",
				String.format("%.2f", (double)totalTime / _runCount), " ms."));

		for (String testStepName : _testStepNames) {
			AtomicLong durationSum = _sumsMap.get(testStepName);

			double average = (double)durationSum.get() / _runCount;

			Queue<Long> durations = _durationsMap.get(testStepName);

			if (durations.size() != _runCount) {
				throw new IllegalStateException(
					StringBundler.concat(
						"The size of data collected is wrong! Expect ",
						_runCount, " but actual is ", durations.size()));
			}

			double variance = 0;

			for (Long duration : durations) {
				variance += (duration - average) * (duration - average);
			}

			variance = variance / _runCount;

			System.out.println(
				StringBundler.concat(
					"\nTest step ", testStepName, "\n\tAverage time: ",
					String.format("%.2f", average),
					" ms\n\tStandard deviation: ",
					String.format("%.2f", Math.sqrt(variance)), 2,
					" ms\n\tTPS: ",
					String.format(
						"%.2f", _runCount * 1000 / (double)durationSum.get())));
		}
	}

	public void record(List<Map.Entry<String, Long>> results) {
		for (Map.Entry<String, Long> entry : results) {
			AtomicLong sum = _getOrCreate(
				entry.getKey(), _sumsMap, AtomicLong::new);

			sum.addAndGet(entry.getValue());

			Queue<Long> durations = _getOrCreate(
				entry.getKey(), _durationsMap, ConcurrentLinkedQueue::new);

			durations.offer(entry.getValue());
		}
	}

	public void start() {
		_startTime = System.currentTimeMillis();
	}

	private <T> T _getOrCreate(
		String key, Map<String, T> map, Supplier<T> supplier) {

		T t = map.get(key);

		if (t == null) {
			t = supplier.get();

			T prevousT = map.putIfAbsent(key, t);

			if (prevousT != null) {
				t = prevousT;
			}
			else {
				if (!_testStepNames.contains(key)) {
					_testStepNames.offer(key);
				}
			}
		}

		return t;
	}

	private final Map<String, Queue<Long>> _durationsMap =
		new ConcurrentHashMap<>();
	private final int _runCount;
	private long _startTime;
	private final Map<String, AtomicLong> _sumsMap = new ConcurrentHashMap<>();
	private final String _testName;
	private final Queue<String> _testStepNames = new ConcurrentLinkedQueue<>();

}