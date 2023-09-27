/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.text.Collator;
import java.text.RuleBasedCollator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * @author Preston Crary
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class CollatorUtilTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = NewEnvTestRule.INSTANCE;

	@Test
	public void testGetInstanceWithInvalidProperty() {
		PropsTestUtil.setProps("collator.rules", "<<<");

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				CollatorUtil.class.getName(), Level.ALL)) {

			CollatorUtil.getInstance(LocaleUtil.getDefault());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			String message = logEntry.getMessage();

			Assert.assertTrue(
				logEntry.toString(),
				message.contains("missing chars (=,;<&): <<"));
		}
	}

	@Test
	public void testGetInstanceWithoutProperty() {
		PropsTestUtil.setProps(Collections.emptyMap());

		Collator collator = CollatorUtil.getInstance(LocaleUtil.US);

		Assert.assertEquals(Collator.getInstance(LocaleUtil.US), collator);

		List<String> expected = new ArrayList<>();

		expected.add("AB");
		expected.add("A B");

		List<String> actual = new ArrayList<>();

		actual.add("A B");
		actual.add("AB");

		actual.sort(collator);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testGetInstanceWithProperty() {
		PropsTestUtil.setProps("collator.rules", _RULES);

		Collator collator = CollatorUtil.getInstance(LocaleUtil.getDefault());

		RuleBasedCollator ruleBasedCollator = (RuleBasedCollator)collator;

		Assert.assertEquals(_RULES, ruleBasedCollator.getRules());

		List<String> expected = new ArrayList<>();

		expected.add("A B");
		expected.add("AB");

		List<String> actual = new ArrayList<>();

		actual.add("AB");
		actual.add("A B");

		actual.sort(collator);

		Assert.assertEquals(expected, actual);
	}

	private static final String _RULES = "=A<b,' '<A";

}