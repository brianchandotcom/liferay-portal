/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.util;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class ExpandoUtilTest {

	@Before
	public void setUp() {
		_originalSiteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setSiteDefaultLocale(_originalSiteDefaultLocale);
	}

	@Test
	public void testFillMissingDefaultLocaleValues() {
		Map<Locale, String> localizedMap = HashMapBuilder.put(
			LocaleUtil.GERMANY, "Hallo"
		).build();

		ExpandoUtil.fillMissingDefaultLocaleValues(
			_attributesOf("greeting", (Serializable)localizedMap));

		Assert.assertEquals("Hallo", localizedMap.get(LocaleUtil.US));
	}

	@Test
	public void testFillMissingDefaultLocaleValuesWithDefaultPresent() {
		Map<Locale, String> localizedMap = HashMapBuilder.put(
			LocaleUtil.GERMANY, "Hallo"
		).put(
			LocaleUtil.US, "Hello"
		).build();

		List<String> warningMessages =
			ExpandoUtil.fillMissingDefaultLocaleValues(
				_attributesOf("greeting", (Serializable)localizedMap));

		Assert.assertEquals("Hello", localizedMap.get(LocaleUtil.US));
		Assert.assertEquals("Hallo", localizedMap.get(LocaleUtil.GERMANY));

		Assert.assertTrue(
			warningMessages.toString(), warningMessages.isEmpty());
	}

	@Test
	public void testFillMissingDefaultLocaleValuesWithStringArrayValue() {
		Map<Locale, String[]> localizedMap = HashMapBuilder.put(
			LocaleUtil.GERMANY, new String[] {"Hallo", "Welt"}
		).build();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ExpandoUtil.class.getName(), LoggerTestUtil.WARN)) {

			List<String> warningMessages =
				ExpandoUtil.fillMissingDefaultLocaleValues(
					_attributesOf("greeting", (Serializable)localizedMap));

			Assert.assertArrayEquals(
				new String[] {"Hallo", "Welt"},
				localizedMap.get(LocaleUtil.US));

			_assertLog(logCapture, "greeting", LocaleUtil.GERMANY);
			_assertWarningMessages(
				warningMessages, "greeting", LocaleUtil.GERMANY);
		}
	}

	@Test
	public void testFillMissingDefaultLocaleValuesWithStringValue() {
		Map<Locale, String> localizedMap = HashMapBuilder.put(
			LocaleUtil.GERMANY, "Hallo"
		).build();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ExpandoUtil.class.getName(), LoggerTestUtil.WARN)) {

			List<String> warningMessages =
				ExpandoUtil.fillMissingDefaultLocaleValues(
					_attributesOf("greeting", (Serializable)localizedMap));

			Assert.assertEquals("Hallo", localizedMap.get(LocaleUtil.US));

			_assertLog(logCapture, "greeting", LocaleUtil.GERMANY);
			_assertWarningMessages(
				warningMessages, "greeting", LocaleUtil.GERMANY);
		}
	}

	private void _assertLog(LogCapture logCapture, String name, Locale locale) {
		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		LogEntry logEntry = logEntries.get(0);

		Assert.assertEquals(LoggerTestUtil.WARN, logEntry.getPriority());

		String message = logEntry.getMessage();

		Assert.assertTrue(message, message.contains(name));
		Assert.assertTrue(
			message, message.contains(LocaleUtil.toLanguageId(locale)));
	}

	private void _assertWarningMessages(
		List<String> warningMessages, String name, Locale locale) {

		Assert.assertEquals(
			warningMessages.toString(), 1, warningMessages.size());

		String warningMessage = warningMessages.get(0);

		Assert.assertTrue(warningMessage, warningMessage.contains(name));
		Assert.assertTrue(
			warningMessage,
			warningMessage.contains(LocaleUtil.toLanguageId(locale)));
	}

	private Map<String, Serializable> _attributesOf(
		String name, Serializable value) {

		return HashMapBuilder.<String, Serializable>put(
			name, value
		).build();
	}

	private Locale _originalSiteDefaultLocale;

}