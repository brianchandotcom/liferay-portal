/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.process.ProcessUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class HeapUtil {

	public static void heapDump(boolean live, boolean binary, String file) {
		int processId = _getProcessId();

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
			ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR, arguments);
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to perform heap dump", e);
		}
	}

	private static int _getProcessId() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		String name = runtimeMXBean.getName();

		int index = name.indexOf(CharPool.AT);

		if (index == -1) {
			throw new RuntimeException("Unable to parse process name " + name);
		}

		String id = name.substring(0, index);

		try {
			return GetterUtil.getInteger(id);
		}
		catch (NumberFormatException nfe) {
			throw new RuntimeException(
				"Unable to parse process name " + name, nfe);
		}
	}

}