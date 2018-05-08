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

package com.frontend.js.minifier;

import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera Avellón
 */
public class GoogleJavascriptMinifierTest {

	@Test
	public void testMinifierCode() {
		GoogleJavaScriptMinifier googleJavaScriptMinifier =
			new GoogleJavaScriptMinifier();

		String code = "function(){ var abcd; var efgh; }";

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					GoogleJavaScriptMinifier.class.getName(), Level.SEVERE)) {

			String minifiedJS = googleJavaScriptMinifier.compress("test", code);

			Assert.assertEquals(0, minifiedJS.length());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"(test:1): Parse error. 'identifier' expected " +
					"[JSC_PARSE_ERROR]",
				logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"(test): 1 error(s), 0 warning(s)", logRecord.getMessage());

			captureHandler.resetLogLevel(Level.SEVERE);
		}
	}

	@Test
	public void testMinifierSpaces() {
		GoogleJavaScriptMinifier googleJavaScriptMinifier =
			new GoogleJavaScriptMinifier();

		String code = " \t\r\n";

		String minifiedJS = googleJavaScriptMinifier.compress("test", code);

		Assert.assertEquals(0, minifiedJS.length());
	}

}