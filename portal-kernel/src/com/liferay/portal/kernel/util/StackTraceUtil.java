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

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;

import java.io.PrintWriter;

/**
 * @author Brian Wing Shun Chan
 */
public class StackTraceUtil {

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static String getCallerKey() {
		Exception e = new Exception();

		StackTraceElement[] stackTraceElements = e.getStackTrace();

		StackTraceElement stackTraceElement = stackTraceElements[1];

		return StringBundler.concat(
			stackTraceElement.getClassName(), "#",
			stackTraceElement.getMethodName(), "#",
			String.valueOf(stackTraceElement.getLineNumber()));
	}

	public static String getStackTrace(Throwable t) {
		String stackTrace = null;

		PrintWriter printWriter = null;

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			printWriter = UnsyncPrintWriterPool.borrow(unsyncStringWriter);

			t.printStackTrace(printWriter);

			printWriter.flush();

			stackTrace = unsyncStringWriter.toString();
		}
		finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}

		return stackTrace;
	}

}