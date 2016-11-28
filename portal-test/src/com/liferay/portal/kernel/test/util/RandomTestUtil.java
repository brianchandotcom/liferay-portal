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

package com.liferay.portal.kernel.test.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.test.randomizerbumpers.RandomizerBumper;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.InputStream;

import java.util.Arrays;
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

	@SafeVarargs
	public static byte[] randomBytes(
		int size, RandomizerBumper<byte[]>... randomizerBumpers) {

		byte[] bytes = new byte[size];

		generation:
		for (int i = 0; i < _RANDOMIZER_BUMPER_TRIES_MAX; i++) {
			_random.nextBytes(bytes);

			for (RandomizerBumper<byte[]> randomizerBumper :
					randomizerBumpers) {

				if (!randomizerBumper.accept(bytes)) {
					continue generation;
				}
			}

			return bytes;
		}

		throw new IllegalStateException(
			"Unable to generate a random byte array that is acceptable by " +
				"all randomizer bumpers " + Arrays.toString(randomizerBumpers) +
					" after " + _RANDOMIZER_BUMPER_TRIES_MAX + " tries");
	}

	@SafeVarargs
	public static byte[] randomBytes(
		RandomizerBumper<byte[]>... randomizerBumpers) {

		return randomBytes(8, randomizerBumpers);
	}

	public static double randomDouble() {
		double value = _random.nextDouble();

		if (value > 0) {
			return value;
		}
		else if (value == 0) {
			return randomDouble();
		}
		else {
			return -value;
		}
	}

	@SafeVarargs
	public static InputStream randomInputStream(
		RandomizerBumper<byte[]>... randomizerBumpers) {

		return new UnsyncByteArrayInputStream(randomBytes(randomizerBumpers));
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

	public static int randomInt(int min, int max)
		throws IllegalArgumentException {

		if ((min < 0) || (max < 0)) {
			throw new IllegalArgumentException(
				"Both min and max values must be positive");
		}

		if (max < min) {
			throw new IllegalArgumentException(
				"Max value must be greater than the min value");
		}

		int value = _random.nextInt(max - min + 1) + min;

		if (value > 0) {
			return value;
		}
		else if (value == 0) {
			return randomInt(min, max);
		}

		return -value;
	}

	public static Map<Locale, String> randomLocaleStringMap() {
		return randomLocaleStringMap(LocaleUtil.getDefault());
	}

	public static Map<Locale, String> randomLocaleStringMap(Locale locale) {
		Map<Locale, String> map = new HashMap<>();

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

	@SafeVarargs
	public static String randomString(
		int length, RandomizerBumper<String>... randomizerBumpers) {

		generation:
		for (int i = 0; i < _RANDOMIZER_BUMPER_TRIES_MAX; i++) {
			String randomString = PwdGenerator.getPassword(length);

			for (RandomizerBumper<String> randomizerBumper :
					randomizerBumpers) {

				if (!randomizerBumper.accept(randomString)) {
					continue generation;
				}
			}

			return randomString;
		}

		throw new IllegalStateException(
			"Unable to generate a random string that is acceptable by all " +
				"randomizer bumpers " + Arrays.toString(randomizerBumpers) +
					" after " + _RANDOMIZER_BUMPER_TRIES_MAX + " tries");
	}

	@SafeVarargs
	public static String randomString(
		RandomizerBumper<String>... randomizerBumpers) {

		return randomString(8, randomizerBumpers);
	}

	@SafeVarargs
	public static String[] randomStrings(
		int count, RandomizerBumper<String>... randomizerBumpers) {

		String[] strings = new String[count];

		for (int i = 0; i < count; i++) {
			strings[i] = randomString(randomizerBumpers);
		}

		return strings;
	}

	public static UnicodeProperties randomUnicodeProperties(
		int propertyCount, int keyLength, int valueLength) {

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		for (int i = 0; i < propertyCount; i++) {
			unicodeProperties.put(
				randomString(keyLength), randomString(valueLength));
		}

		return unicodeProperties;
	}

	private static final int _RANDOMIZER_BUMPER_TRIES_MAX = 100;

	private static final Random _random = new Random();

}