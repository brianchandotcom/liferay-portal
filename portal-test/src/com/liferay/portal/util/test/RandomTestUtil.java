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

package com.liferay.portal.util.test;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * @author Manuel de la Peña
 */
public class RandomTestUtil {

	public static Date nextDate() {
		return new Date();
	}

	public static double nextDouble() throws Exception {
		return CounterLocalServiceUtil.increment();
	}

	public static int nextInt() throws Exception {
		return (int)CounterLocalServiceUtil.increment();
	}

	public static long nextLong() throws Exception {
		return CounterLocalServiceUtil.increment();
	}

	public static boolean randomBoolean() {
		return _random.nextBoolean();
	}

	public static byte[] randomBytes() {
		String string = randomString();

		return string.getBytes();
	}

	public static char randomChar() {
		String s = randomString(1);

		return s.charAt(0);
	}

	public static int randomInt() {
		int value = _random.nextInt();

		if (value > 0) {
			return value;
		}
		else if (value == 0) {
			return randomInt();
		}
		else {
			return -value;
		}
	}

	public static Map<Locale, String> randomLocaleStringMap() {
		return randomLocaleStringMap(LocaleUtil.getDefault());
	}

	public static Map<Locale, String> randomLocaleStringMap(Locale locale) {
		Map<Locale, String> map = new HashMap<Locale, String>();

		map.put(LocaleUtil.getDefault(), randomString());

		return map;
	}

	public static long randomLong() {
		long value = _random.nextLong();

		if (value > 0) {
			return value;
		}
		else if (value == 0) {
			return randomLong();
		}
		else {
			return -value;
		}
	}

	public static String randomString() {
		return StringUtil.randomString();
	}

	public static String randomString(int length) {
		return StringUtil.randomString(length);
	}

	public static String[] randomStrings(int count) {
		String[] strings = new String[count];

		for (int i = 0; i < count; i++) {
			strings[i] = StringUtil.randomString();
		}

		return strings;
	}

	public static UnicodeProperties randomUnicodeProperties(
		int propertyCount, int keyLength, int valueLength) {

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		for (int i = 0; i < propertyCount; i++) {
			unicodeProperties.put(
				RandomTestUtil.randomString(keyLength),
				RandomTestUtil.randomString(valueLength));
		}

		return unicodeProperties;
	}

	private static Random _random = new Random();

}