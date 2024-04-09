/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.Closeable;

import org.junit.Assert;

/**
 * @author David Truong
 */
public class PerformanceTestTimer implements Closeable {

	public PerformanceTestTimer(Class<?> clazz, String name, long maxTime) {
		this(_getInvokerName(clazz, name), System.currentTimeMillis(), maxTime);
	}

	public PerformanceTestTimer(long maxTime) {
		this(_getInvokerName(null, null), System.currentTimeMillis(), maxTime);
	}

	public PerformanceTestTimer(String name, int maxTime) {
		this(_getInvokerName(null, name), System.currentTimeMillis(), maxTime);
	}

	@Override
	public void close() {
		long delta = System.currentTimeMillis() - _startTime;

		System.out.println(
			StringBundler.concat("Completed ", _name, " in ", delta, " ms"));

		Assert.assertTrue(
			StringBundler.concat(
				"Completed in ", delta,
				"ms, but the expected completion time should be less than ",
				_maxTime, "ms"),
			delta < _maxTime);
	}

	private static String _getInvokerName(Class<?> clazz, String name) {
		Thread thread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = thread.getStackTrace();

		StackTraceElement stackTraceElement = stackTraceElements[3];

		StringBundler sb = new StringBundler(5);

		if (clazz == null) {
			sb.append(stackTraceElement.getClassName());
		}
		else {
			sb.append(clazz.getName());
		}

		sb.append(StringPool.POUND);
		sb.append(stackTraceElement.getMethodName());

		if (name != null) {
			sb.append(StringPool.POUND);
			sb.append(name);
		}

		return sb.toString();
	}

	private PerformanceTestTimer(String name, long startTime, long maxTime) {
		_name = name;
		_startTime = startTime;
		_maxTime = maxTime;

		System.out.println("Starting " + name);
	}

	private final long _maxTime;
	private final String _name;
	private final long _startTime;

}