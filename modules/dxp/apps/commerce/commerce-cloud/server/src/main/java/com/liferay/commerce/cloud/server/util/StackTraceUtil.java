/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cloud.server.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Andrea Di Giorgi
 */
public class StackTraceUtil {

	public static String getStackTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();

		try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
			t.printStackTrace(printWriter);
		}

		return stringWriter.toString();
	}

}