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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class HeapUtil {

	public static int getProcessId() {
		if (!_SUPPORTED) {
			throw new IllegalStateException(
				HeapUtil.class.getName() + " does not support the current JVM");
		}

		return _PROCESS_ID;
	}

	public static Future<ObjectValuePair<Void, Void>> heapDump(
		boolean live, boolean binary, String file) {

		return heapDump(_PROCESS_ID, live, binary, file);
	}

	public static Future<ObjectValuePair<Void, Void>> heapDump(
		int processId, boolean live, boolean binary, String file) {

		if (!_SUPPORTED) {
			throw new IllegalStateException(
				HeapUtil.class.getName() + " does not support the current JVM");
		}

		StringBundler sb = new StringBundler(5);

		sb.append("-dump:");

		if (live) {
			sb.append("live,");
		}

		if (binary) {
			sb.append("format=b,");
		}

		sb.append("file=");
		sb.append(file);

		List<String> arguments = new ArrayList<String>();

		arguments.add("jmap");
		arguments.add(sb.toString());
		arguments.add(String.valueOf(processId));

		try {
			return ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR, arguments);
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to perform heap dump", e);
		}
	}

	public static boolean isSupported() {
		return _SUPPORTED;
	}

	private static void _checkJMap(int processId) throws Exception {
		Future<ObjectValuePair<byte[], byte[]>> future =
			ProcessUtil.execute(
				ProcessUtil.COLLECTOR_OUTPUT_PROCESSOR, "jmap", "-histo:live",
				String.valueOf(processId));

		ObjectValuePair<byte[], byte[]> objectValuePair = future.get();

		String stdOutString = new String(objectValuePair.getKey());

		if (!stdOutString.contains("#instances")) {
			throw new IllegalStateException(
				"JMap cannot connect to process ID " + processId);
		}

		byte[] stdErrBytes = objectValuePair.getValue();

		if (stdErrBytes.length != 0) {
			throw new IllegalStateException(
				"JMap returns with error: " + new String(stdErrBytes));
		}
	}

	private static void _checkJPS(int processId) throws Exception {
		Future<ObjectValuePair<byte[], byte[]>> future = ProcessUtil.execute(
			ProcessUtil.COLLECTOR_OUTPUT_PROCESSOR, "jps");

		ObjectValuePair<byte[], byte[]> objectValuePair = future.get();

		String stdOutString = new String(objectValuePair.getKey());

		if (!stdOutString.contains(String.valueOf(processId))) {
			throw new IllegalStateException(
				"JPS cannot detect expected process ID " + processId);
		}

		byte[] stdErrBytes = objectValuePair.getValue();

		if (stdErrBytes.length != 0) {
			throw new IllegalStateException(
				"JPS returns with error: " + new String(stdErrBytes));
		}
	}

	private static int _getProcessId() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		String name = runtimeMXBean.getName();

		int index = name.indexOf(CharPool.AT);

		if (index == -1) {
			throw new RuntimeException("Unable to parse process name " + name);
		}

		int pid = GetterUtil.getInteger(name.substring(0, index));

		if (pid == 0) {
			throw new RuntimeException("Unable to parse process name " + name);
		}

		return pid;
	}

	private static final int _PROCESS_ID;

	private static final boolean _SUPPORTED;

	private static final Log _log = LogFactoryUtil.getLog(HeapUtil.class);

	static {
		int processId = -1;
		boolean supported = false;

		if (JavaDetector.isOracle()) {
			try {
				processId = _getProcessId();

				_checkJPS(processId);
				_checkJMap(processId);

				supported = true;
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(HeapUtil.class.getName() + " is disabled", e);
				}
			}
		}
		else if (_log.isDebugEnabled()) {
			_log.debug(
				HeapUtil.class.getName() + " is only supported on Oracle JVMs");
		}

		_PROCESS_ID = processId;
		_SUPPORTED = supported;
	}

}