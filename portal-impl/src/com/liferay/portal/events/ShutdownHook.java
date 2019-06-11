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

package com.liferay.portal.events;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
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

			ThreadGroup threadGroup = thread.getThreadGroup();

			if (threadGroup != null) {
				sb.append(" (");
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

		System.out.println(sb);
	}

}