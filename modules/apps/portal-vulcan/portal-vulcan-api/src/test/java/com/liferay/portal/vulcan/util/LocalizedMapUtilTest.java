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

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.BadRequestException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Víctor Galán
 */
public class LocalizedMapUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMerge() {

		// Null map

		Map<Locale, String> map = LocalizedMapUtil.merge(
			null, new AbstractMap.SimpleEntry<>(LocaleUtil.US, "hello"));

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertEquals("hello", map.get(LocaleUtil.US));

		// Null entry

		map = LocalizedMapUtil.merge(
			HashMapBuilder.put(
				LocaleUtil.US, "hello"
			).build(),
			null);

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertEquals("hello", map.get(LocaleUtil.US));

		// Entry hello null

		map = LocalizedMapUtil.merge(
			HashMapBuilder.put(
				LocaleUtil.US, "hello"
			).build(),
			new AbstractMap.SimpleEntry<>(LocaleUtil.US, null));

		Assert.assertEquals(map.toString(), 0, map.size());
		Assert.assertNull(map.get(LocaleUtil.US));

		// Merge map

		map = LocalizedMapUtil.merge(
			HashMapBuilder.put(
				LocaleUtil.US, "hello"
			).build(),
			new AbstractMap.SimpleEntry<>(LocaleUtil.FRANCE, "bonjour"));

		Assert.assertEquals(map.toString(), 2, map.size());
		Assert.assertEquals("bonjour", map.get(LocaleUtil.FRANCE));
		Assert.assertEquals("hello", map.get(LocaleUtil.US));
	}

	@Test
	public void testValidateI18n() {
		String randomEntityName = RandomTestUtil.randomString();

		Set<Locale> notFoundLocales = new HashSet<Locale>() {
			{
				add(LocaleUtil.CHINESE);
				add(LocaleUtil.GERMAN);
			}
		};

		try {
			LocalizedMapUtil.validateI18n(
				false, LocaleUtil.ENGLISH, randomEntityName,
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				notFoundLocales);

			Assert.fail();
		}
		catch (BadRequestException badRequestException) {
			String message = badRequestException.getMessage();

			List<Locale> missingNotFoundLocales = new ArrayList<>();

			for (Locale notFoundLocale : notFoundLocales) {
				if (!message.contains(notFoundLocale.toString())) {
					missingNotFoundLocales.add(notFoundLocale);
				}
			}

			Assert.assertTrue(
				"Error message missing locales: " + missingNotFoundLocales,
				missingNotFoundLocales.isEmpty());
		}
	}

}