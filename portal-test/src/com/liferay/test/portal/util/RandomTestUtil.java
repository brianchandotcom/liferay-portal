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

package com.liferay.test.portal.util;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * @author Manuel de la Peña
 */
public class RandomTestUtil {

	public static Date nextDate() throws Exception {
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

	public static boolean randomBoolean() throws Exception {
		return _random.nextBoolean();
	}

	public static int randomInt() throws Exception {
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

	public static Map<Locale, String> randomLocaleStringMap() throws Exception {
		return randomLocaleStringMap(LocaleUtil.getDefault());
	}

	public static Map<Locale, String> randomLocaleStringMap(Locale locale)
		throws Exception {

		Map<Locale, String> map = new HashMap<Locale, String>();

		map.put(LocaleUtil.getDefault(), randomString());

		return map;
	}

	public static long randomLong() throws Exception {
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

	public static String randomString() throws Exception {
		return StringUtil.randomString();
	}

	public static String randomString(int length) throws Exception {
		return StringUtil.randomString(length);
	}

	private static Random _random = new Random();

}