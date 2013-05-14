/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.events;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ShutdownHook implements Runnable {

	@Override
	public void run() {
		if (GetterUtil.getBoolean(
				System.getProperty("shutdown.hook.print.full.thread.dump"))) {

			printFullThreadDump();
		}
	}

	protected void printFullThreadDump() {
		StringBundler sb = new StringBundler();

		sb.append("Full thread dump ");
		sb.append(System.getProperty("java.vm.name"));
		sb.append(" ");
		sb.append(System.getProperty("java.vm.version"));
		sb.append("\n\n");

		Map<Thread, StackTraceElement[]> stackTraces =
			Thread.getAllStackTraces();

		for (Map.Entry<Thread, StackTraceElement[]> entry :
				stackTraces.entrySet()) {

			Thread thread = entry.getKey();
			StackTraceElement[] elements = entry.getValue();

			sb.append("\"");
			sb.append(thread.getName());
			sb.append("\"");

			if (thread.getThreadGroup() != null) {
				sb.append(" (");
				sb.append(thread.getThreadGroup().getName());
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			sb.append(", priority=");
			sb.append(thread.getPriority());
			sb.append(", id=");
			sb.append(thread.getId());
			sb.append(", state=");
			sb.append(thread.getState());
			sb.append("\n");

			for (int i = 0; i < elements.length; i++) {
				sb.append("\t");
				sb.append(elements[i]);
				sb.append("\n");
			}

			sb.append("\n");
		}

		System.out.println(sb);
	}

}