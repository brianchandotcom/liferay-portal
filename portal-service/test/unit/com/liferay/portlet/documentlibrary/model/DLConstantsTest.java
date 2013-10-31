/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Everest Liu
 */
@PrepareForTest(PropsUtil.class)
@RunWith(PowerMockRunner.class)
public class DLConstantsTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		mockStatic(PropsUtil.class);

		Properties props = new Properties();

		String blacklistNames;
		String blacklistCharRegexp;

		if (_randomStrings.size() == 0) {
			for (int i = 0; i < 100; i++) {
				_randomStrings.add(StringUtil.randomString(20));
			}
			_randomStrings.add("._Word Work File D_1.tmp");
			_randomStrings.add("._Test.docx");
		}

		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			InputStream is = classLoader.getResourceAsStream(
				"portal.properties");

			props.load(is);

			blacklistNames = props.getProperty(PropsKeys.DL_NAME_BLACKLIST);
			blacklistCharRegexp = props.getProperty(
				PropsKeys.DL_CHAR_BLACKLIST_REGEXP);

			when(
				PropsUtil.getArray(PropsKeys.DL_NAME_BLACKLIST)
			).thenReturn(
				blacklistNames.split(",")
			);

			when(
				PropsUtil.get(PropsKeys.DL_CHAR_BLACKLIST_REGEXP)
			).thenReturn(
				blacklistCharRegexp
			);

			_blacklistNames = PropsUtil.getArray(PropsKeys.DL_NAME_BLACKLIST);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testNameBlacklist() throws Exception {
		for (String blacklistName : _blacklistNames) {
			Assert.assertFalse(
				blacklistName, DLConstants.isValidName(blacklistName));
		}
	}

	@Test
	public void testNameBlacklistExtension() throws Exception {
		String testName;

		for (String blacklistName : _blacklistNames) {
			testName = blacklistName + ".txt";
			Assert.assertFalse(testName, DLConstants.isValidName(testName));
		}
	}

	@Test
	public void testPeriodAtEnd() throws Exception {
		for (String randomString : _randomStrings) {
			randomString += "1.";

			Assert.assertFalse(
				randomString, DLConstants.isValidName(randomString));
		}
	}

	@Test
	public void testRandomStrings() throws Exception {
		for (String randomString : _randomStrings) {
			Assert.assertTrue(
				randomString, DLConstants.isValidName(randomString));
		}

		Assert.assertFalse(
			StringPool.BLANK, DLConstants.isValidName(StringPool.BLANK));
	}

	@Test
	public void testRandomStringsWithBlacklistedChar() throws Exception {
		String testName;

		for (String randomString : _randomStrings) {
			for (String blacklistChar : BLACKLIST_CHARS) {
				StringBuilder sb = new StringBuilder();

				sb.append(randomString);
				sb.insert(randomString.length() / 2, blacklistChar);

				testName = sb.toString();

				Assert.assertFalse(testName, DLConstants.isValidName(testName));

				StringBuilder sb2 = new StringBuilder();

				sb2.append(randomString);
				sb2.append(blacklistChar);
				sb2.append(randomString);
				sb2.append(".txt");

				testName = sb2.toString();

				Assert.assertFalse(testName, DLConstants.isValidName(testName));
			}
		}
	}

	@Test
	public void testSpaceAtEnd() throws Exception {
		String testName;

		for (String randomString : _randomStrings) {
			testName = randomString + " ";

			Assert.assertFalse(
				testName + "[space]", DLConstants.isValidName(testName));
		}
	}

	@Test
	public void testValidNames() throws Exception {
		String testName;

		for (String blacklistName : _blacklistNames) {
			testName = blacklistName + "1";
			Assert.assertTrue(testName, DLConstants.isValidName(testName));

			testName = blacklistName + " .txt";
			Assert.assertTrue(testName, DLConstants.isValidName(testName));
		}
	}

	private static final String[] BLACKLIST_CHARS = new String[] {
		"<", ">", ":", "\"", "\\", "|", "?", "*", "\u0000", "\u0001",
		"\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\u0008",
		"\u0009", "\u000B", "\u000C", "\u000E", "\u000F", "\u0010", "\u0011",
		"\u0012", "\u0013", "\u0014", "\u0015", "\u0016", "\u0017", "\u0018",
		"\u0019", "\u001A", "\u001B", "\u001C", "\u001D", "\u001E", "\u001F"};

	private static String[] _blacklistNames;
	private static List<String> _randomStrings = new ArrayList<String>();

}