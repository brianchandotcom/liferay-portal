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

import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.test.util.PropsTestUtil;

import java.text.Collator;
import java.text.RuleBasedCollator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

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

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					CollatorUtil.class.getName(), Level.ALL)) {

			CollatorUtil.getInstance(LocaleUtil.getDefault());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			String message = logRecord.getMessage();

			Assert.assertTrue(
				logRecord.toString(),
				message.contains("java.text.ParseException"));
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