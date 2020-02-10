/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.CollectorOutputProcessor;
import com.liferay.petra.process.ProcessUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;
import java.util.Map;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class ThreadUtil {

	public static Thread[] getThreads() {
		Thread currentThread = Thread.currentThread();

		ThreadGroup threadGroup = currentThread.getThreadGroup();

		while (threadGroup.getParent() != null) {
			threadGroup = threadGroup.getParent();
		}

		int threadCountGuess = threadGroup.activeCount();

		Thread[] threads = new Thread[threadCountGuess];

		int threadCountActual = threadGroup.enumerate(threads);

		while (threadCountActual == threadCountGuess) {
			threadCountGuess *= 2;

			threads = new Thread[threadCountGuess];

			threadCountActual = threadGroup.enumerate(threads);
		}

		return threads;
	}

	public static String threadDump() {
		String threadDump = _getThreadDumpFromJstack();

		if (Validator.isNull(threadDump)) {
			threadDump = _getThreadDumpFromStackTrace();
		}

		return "\n\n".concat(threadDump);
	}

	private static String _getThreadDumpFromJstack() {
		String vendorURL = System.getProperty("java.vendor.url");

		if ((!vendorURL.equals("http://java.oracle.com/") &&
			 !vendorURL.equals("http://java.sun.com/")) ||
			!HeapUtil.isSupported()) {

			return StringPool.BLANK;
		}

		try {
			NoticeableFuture<Map.Entry<byte[], byte[]>> noticeableFuture =
				ProcessUtil.execute(
					CollectorOutputProcessor.INSTANCE, "jstack", "-l",
					String.valueOf(HeapUtil.getProcessId()));

			Map.Entry<byte[], byte[]> entry = noticeableFuture.get();

			return new String(entry.getKey());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to use jstack to get thread dump for process " +
						HeapUtil.getProcessId(),
					exception);
			}

			return StringPool.BLANK;
		}
	}

	private static String _getThreadDumpFromStackTrace() {
		String jvm =
			System.getProperty("java.vm.name") + " " +
				System.getProperty("java.vm.version");

		StringBundler sb = new StringBundler(
			StringBundler.concat(
				"Full thread dump of ", jvm, " on ", new Date(), "\n\n"));

		Map<Thread, StackTraceElement[]> stackTraces =
			Thread.getAllStackTraces();

		for (Map.Entry<Thread, StackTraceElement[]> entry :
				stackTraces.entrySet()) {

			Thread thread = entry.getKey();
			StackTraceElement[] elements = entry.getValue();

			sb.append(StringPool.QUOTE);
			sb.append(thread.getName());
			sb.append(StringPool.QUOTE);

			if (thread.getThreadGroup() != null) {
				sb.append(StringPool.SPACE);
				sb.append(StringPool.OPEN_PARENTHESIS);

				ThreadGroup threadGroup = thread.getThreadGroup();

				sb.append(threadGroup.getName());

				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sb.append(", priority=");
			sb.append(thread.getPriority());
			sb.append(", id=");
			sb.append(thread.getId());
			sb.append(", state=");
			sb.append(thread.getState());
			sb.append("\n");

			for (StackTraceElement element : elements) {
				sb.append("\t");
				sb.append(element);
				sb.append("\n");
			}

			sb.append("\n");
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(ThreadUtil.class);

}