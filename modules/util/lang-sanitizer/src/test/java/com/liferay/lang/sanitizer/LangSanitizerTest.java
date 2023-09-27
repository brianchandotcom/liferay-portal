/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.lang.sanitizer;

import com.liferay.lang.sanitizer.util.EscapeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.net.URL;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;

/**
 * @author Seiphon Wang
 */
public class LangSanitizerTest {

	@Before
	public void setUpScanner() throws Exception {
		ClassLoader classLoader = LangSanitizer.class.getClassLoader();

		_policy = Policy.getInstance(
			classLoader.getResourceAsStream("sanitizer-configuration.xml"));
	}

	@Test
	public void testCorrectProperties() throws Exception {
		ClassLoader classLoader = LangSanitizerTest.class.getClassLoader();

		URL url = classLoader.getResource(_CORRECT_PROPERTIES_FILE_NAME);

		if (url == null) {
			throw new FileNotFoundException(_CORRECT_PROPERTIES_FILE_NAME);
		}

		Properties properties = new Properties();

		File file = new File(url.getFile());

		if (file.exists()) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				properties.load(fileInputStream);
			}
		}

		Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();

		for (Map.Entry<Object, Object> entry : entrySet) {
			String originalString = (String)entry.getValue();

			AntiSamy antiSamy = new AntiSamy();

			CleanResults cleanResults = antiSamy.scan(originalString, _policy);

			String sanitizedString = EscapeUtil.unescape(
				cleanResults.getCleanHTML());

			String value = EscapeUtil.unescape(
				EscapeUtil.formatTag(originalString));

			Assert.assertEquals(value, sanitizedString);
		}
	}

	@Test
	public void testIncorrectProperties() throws Exception {
		ClassLoader classLoader = LangSanitizerTest.class.getClassLoader();

		URL url = classLoader.getResource(_INCORRECT_PROPERTIES_FILE_NAME);

		if (url == null) {
			throw new FileNotFoundException(_INCORRECT_PROPERTIES_FILE_NAME);
		}

		Properties properties = new Properties();

		File file = new File(url.getFile());

		if (file.exists()) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				properties.load(fileInputStream);
			}
		}

		Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();

		for (Map.Entry<Object, Object> entry : entrySet) {
			String originalString = (String)entry.getValue();

			AntiSamy antiSamy = new AntiSamy();

			CleanResults cleanResults = antiSamy.scan(originalString, _policy);

			String sanitizedString = EscapeUtil.unescape(
				cleanResults.getCleanHTML());

			String value = EscapeUtil.unescape(
				EscapeUtil.formatTag(originalString));

			Assert.assertNotEquals(value, sanitizedString);
		}
	}

	protected final ClassLoader classLoader =
		LangSanitizerTest.class.getClassLoader();

	private static final String _CORRECT_PROPERTIES_FILE_NAME =
		"com/liferay/lang/sanitizer/dependencies" +
			"/Correct_Language_test.properties";

	private static final String _INCORRECT_PROPERTIES_FILE_NAME =
		"com/liferay/lang/sanitizer/dependencies" +
			"/Incorrect_Language_test.properties";

	private Policy _policy;

}