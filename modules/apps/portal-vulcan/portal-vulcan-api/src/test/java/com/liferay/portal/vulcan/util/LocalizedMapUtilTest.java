/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void testMergeI18nMap() {
		Map<String, String> map = LocalizedMapUtil.mergeI18nMap(
			HashMapBuilder.put(
				"en_US", "Brazil"
			).build(),
			null, "Brasil");

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertEquals("Brazil", map.get("en_US"));

		map = LocalizedMapUtil.mergeI18nMap(null, "pt_BR", "Brasil");

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertEquals("Brasil", map.get("pt_BR"));

		map = LocalizedMapUtil.mergeI18nMap(
			HashMapBuilder.put(
				"pt_BR", "Brasil"
			).build(),
			"en_US", "Brazil");

		Assert.assertEquals(map.toString(), 2, map.size());
		Assert.assertEquals("Brazil", map.get("en_US"));
		Assert.assertEquals("Brasil", map.get("pt_BR"));

		map = LocalizedMapUtil.mergeI18nMap(
			HashMapBuilder.put(
				"en_US", "Brazil"
			).build(),
			"en_US", null);

		Assert.assertEquals(map.toString(), 0, map.size());
		Assert.assertNull(map.get("en_US"));
	}

	@Test
	public void testMergeLocalizedMap() {

		// Null map

		Map<Locale, String> map = LocalizedMapUtil.mergeLocalizedMap(
			null, new AbstractMap.SimpleEntry<>(LocaleUtil.US, "hello"));

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertEquals("hello", map.get(LocaleUtil.US));

		// Null entry

		map = LocalizedMapUtil.mergeLocalizedMap(
			HashMapBuilder.put(
				LocaleUtil.US, "hello"
			).build(),
			null);

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertEquals("hello", map.get(LocaleUtil.US));

		// Entry hello null

		map = LocalizedMapUtil.mergeLocalizedMap(
			HashMapBuilder.put(
				LocaleUtil.US, "hello"
			).build(),
			new AbstractMap.SimpleEntry<>(LocaleUtil.US, null));

		Assert.assertEquals(map.toString(), 0, map.size());
		Assert.assertNull(map.get(LocaleUtil.US));

		// Merge map

		map = LocalizedMapUtil.mergeLocalizedMap(
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

			Assert.assertTrue(missingNotFoundLocales.isEmpty());
		}
	}

}