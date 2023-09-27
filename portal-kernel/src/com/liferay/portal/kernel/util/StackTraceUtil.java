/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.io.unsync.UnsyncStringWriter;

import java.io.PrintWriter;

/**
 * @author Brian Wing Shun Chan
 */
public class StackTraceUtil {

	public static String getStackTrace(Throwable throwable) {
		String stackTrace = null;

		try (UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();
			PrintWriter printWriter = UnsyncPrintWriterPool.borrow(
				unsyncStringWriter)) {

			throwable.printStackTrace(printWriter);

			printWriter.flush();

			stackTrace = unsyncStringWriter.toString();
		}

		return stackTrace;
	}

}