/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.minifier.internal;

import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Iván Zaera Avellón
 */
public class GoogleJavascriptMinifierTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMinifierCode() {
		GoogleJavaScriptMinifier googleJavaScriptMinifier =
			new GoogleJavaScriptMinifier();

		String code = "function(){ var invalidFunctionExpression; }";

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				GoogleJavaScriptMinifier.class.getName(), Level.SEVERE)) {

			String minifiedJS = googleJavaScriptMinifier.compress("test", code);

			Assert.assertEquals(44, minifiedJS.length());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"(test:1): Parse error. 'identifier' expected " +
					"[JSC_PARSE_ERROR]",
				logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				"(test): 1 error(s), 0 warning(s)", logEntry.getMessage());

			logCapture.resetPriority(String.valueOf(Level.SEVERE));
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