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
		String dlNameBlacklist;
		String dlCharBlacklistRegexp;

		if (randomStringsArray.size() == 0) {
			for (int i = 0; i < 100; i++) {
				randomStringsArray.add(StringUtil.randomString(20));
			}
		}

		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			InputStream is = classLoader.getResourceAsStream(
				"portal.properties");

			props.load(is);

			dlNameBlacklist = props.getProperty(PropsKeys.DL_NAME_BLACKLIST);
			dlCharBlacklistRegexp = props.getProperty(
				PropsKeys.DL_CHAR_BLACKLIST_REGEXP);

			when(
				PropsUtil.get(PropsKeys.DL_NAME_BLACKLIST)
			).thenReturn(
				dlNameBlacklist
			);

			when(
				PropsUtil.get(PropsKeys.DL_CHAR_BLACKLIST_REGEXP)
			).thenReturn(
				dlCharBlacklistRegexp
			);

			dlNameBlacklistArray = PropsUtil.get(
				PropsKeys.DL_NAME_BLACKLIST).split(",");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testNameBlacklist() throws Exception {
		//Blacklistedd filenames

		for (String name : dlNameBlacklistArray) {
			Assert.assertFalse(name, DLConstants.isValidName(name));
		}
	}

	@Test
	public void testNameBlacklistExtension() throws Exception {
		//Blacklisted filenames with extension
		String testName;

		for (String name : dlNameBlacklistArray) {
			testName = name + ".txt";
			Assert.assertFalse(testName, DLConstants.isValidName(testName));
		}
	}

	@Test
	public void testPeriodAtEnd() throws Exception {
		//legal names with trailing period should fail

		for (String name : randomStringsArray) {
			name += "1.";
			Assert.assertFalse(name, DLConstants.isValidName(name));
		}
	}

	@Test
	public void testRandomStrings() throws Exception {
		//random strings of letters and numbers

		for (String name : randomStringsArray) {
			Assert.assertTrue(name, DLConstants.isValidName(name));
		}

		Assert.assertFalse("", DLConstants.isValidName(""));
	}

	@Test
	public void testRandomStringsWithBlacklistedChar() throws Exception {
		String testName;

		for (String name : randomStringsArray) {
			for (String blacklistedChar : dlCharBlacklistArray) {
				//random strings of letters and numbers with an illegal char
				StringBuilder sb = new StringBuilder();
				sb.append(name);
				sb.insert(name.length() / 2, blacklistedChar);
				testName = sb.toString();

				Assert.assertFalse(testName, DLConstants.isValidName(testName));

				//similar test with file extension
				StringBuilder sb2 = new StringBuilder();
				sb2.append(name);
				sb2.append(blacklistedChar);
				sb2.append(name);
				sb2.append(".txt");
				testName = sb2.toString();

				Assert.assertFalse(testName, DLConstants.isValidName(testName));
			}
		}
	}

	@Test
	public void testSpaceAtEnd() throws Exception {
		//legal names with trailing space should fail
		String testName;

		for (String name : randomStringsArray) {
			testName = name + " ";
			Assert.assertFalse(
				testName + "[space]", DLConstants.isValidName(testName));
		}
	}

	@Test
	public void testValidNames() throws Exception {
		String testName;

		for (String name : dlNameBlacklistArray) {
			//similar to blacklisted names but acceptable
			testName = name + "1";
			Assert.assertTrue(testName, DLConstants.isValidName(testName));

			//illegal name plus a space and extension; should be legal
			testName = name + " .txt";
			Assert.assertTrue(testName, DLConstants.isValidName(testName));
		}
	}

	private static String[] dlCharBlacklistArray =
		("<,>,:,\",/,\\,|,?,*" +
			"\u0000,\u0001,\u0002,\u0003,\u0004,\u0005,\u0006,\u0007,\u0008," +
			"\u0009,\u000B,\u000C,\u000E,\u000F,\u0010,\u0011,\u0012,\u0013," +
			"\u0014,\u0015,\u0016,\u0017,\u0018,\u0019,\u001A,\u001B,\u001C," +
			"\u001D,\u001E,\u001F"
		).split(",");
	private static String[] dlNameBlacklistArray;
	private static List<String> randomStringsArray = new ArrayList<String>();
}