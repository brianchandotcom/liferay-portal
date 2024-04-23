/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.performance;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Closeable;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.Arrays;

import org.junit.Assert;

/**
 * @author David Truong
 */
public class PerformanceTimer implements Closeable {

	public PerformanceTimer(Class<?> clazz, long maxTime, String name) {
		this(
			null, maxTime, getInvokerName(clazz, name),
			System.currentTimeMillis());
	}

	public PerformanceTimer(
		Class<?> clazz, Path logFilePath, long maxTime, String name) {

		this(
			logFilePath, maxTime, getInvokerName(clazz, name),
			System.currentTimeMillis());
	}

	public PerformanceTimer(long maxTime) {
		this(
			null, maxTime, getInvokerName(null, null),
			System.currentTimeMillis());
	}

	public PerformanceTimer(long maxTime, String name) {
		this(
			null, maxTime, getInvokerName(null, name),
			System.currentTimeMillis());
	}

	public PerformanceTimer(Path logFilePath, long maxTime) {
		this(
			logFilePath, maxTime, getInvokerName(null, null),
			System.currentTimeMillis());
	}

	public PerformanceTimer(Path logFilePath, long maxTime, String name) {
		this(
			logFilePath, maxTime, getInvokerName(null, name),
			System.currentTimeMillis());
	}

	@Override
	public void close() {
		long delta = System.currentTimeMillis() - startTime;

		log(StringBundler.concat("Completed ", name, " in ", delta, " ms"));

		Assert.assertTrue(
			StringBundler.concat(
				"Completed in ", delta,
				"ms, but the expected completion time should be less than ",
				maxTime, "ms"),
			delta < maxTime);
	}

	protected static String getInvokerName(Class<?> clazz, String name) {
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

	protected PerformanceTimer(
		Path logFilePath, long maxTime, String name, long startTime) {

		_logFilePath = logFilePath;

		this.maxTime = maxTime;
		this.name = name;
		this.startTime = startTime;

		log("Starting " + name);
	}

	protected void log(String message) {
		if (_logFilePath != null) {
			try {
				_writeToLogFile(message);
			}
			catch (IOException ioException) {
				_log.error(ioException);
			}
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(message);
			}
		}
	}

	protected final long maxTime;
	protected final String name;
	protected final long startTime;

	private void _writeToLogFile(String... contents) throws IOException {
		Files.write(
			_logFilePath, Arrays.asList(contents), StandardOpenOption.APPEND,
			StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PerformanceTimer.class);

	private Path _logFilePath;

}