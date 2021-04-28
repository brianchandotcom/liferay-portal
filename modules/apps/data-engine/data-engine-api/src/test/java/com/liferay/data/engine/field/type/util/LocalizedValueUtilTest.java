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

package com.liferay.data.engine.field.type.util;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mateus Santana
 */
@RunWith(MockitoJUnitRunner.class)
public class LocalizedValueUtilTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpLanguageUtil();
	}

	@Test
	public void testToLocaleStringMapEmptyKeyValue() {
		Map<Locale, String> map = LocalizedValueUtil.toLocaleStringMap(
			HashMapBuilder.<String, Object>put(
				"", ""
			).build());

		Assert.assertEquals("", map.get(LocaleUtil.US));
	}

	@Test
	public void testToLocaleStringMapEmptyMap() {
		Assert.assertEquals(
			Collections.emptyMap(),
			LocalizedValueUtil.toLocaleStringMap(new HashMap<>()));
	}

	@Test
	public void testToLocaleStringMapValidMap() {
		Map<Locale, String> map = LocalizedValueUtil.toLocaleStringMap(
			HashMapBuilder.<String, Object>put(
				"en_US", "en_US"
			).build());

		Assert.assertEquals("en_US", map.get(LocaleUtil.US));
	}

	@Test
	public void testToLocalizedValueEmptyMap() {
		Assert.assertEquals(
			new LocalizedValue(LocaleUtil.US),
			LocalizedValueUtil.toLocalizedValue(new HashMap<>()));
	}

	@Test
	public void testToLocalizedValueNullValue() {
		Assert.assertNull(LocalizedValueUtil.toLocalizedValue(null));
	}

	@Test
	public void testToLocalizedValuesMapNullLocalizedValue() {
		Assert.assertEquals(
			Collections.emptyMap(),
			LocalizedValueUtil.toLocalizedValuesMap(null));
	}

	@Test
	public void testToLocalizedValuesMapValidLocalizedValue() {
		Map<String, Object> map = LocalizedValueUtil.toLocalizedValuesMap(
			new LocalizedValue() {
				{
					addString(LocaleUtil.US, "en_US");
					addString(LocaleUtil.BRAZIL, "pt_BR");
				}
			});

		Assert.assertEquals("en_US", map.get("en_US"));
	}

	@Test
	public void testToLocalizedValueValidMap() {
		LocalizedValue localizedValue = LocalizedValueUtil.toLocalizedValue(
			HashMapBuilder.<String, Object>put(
				"en_US", "en_US"
			).build());

		Assert.assertEquals("en_US", localizedValue.getString(LocaleUtil.US));
	}

	@Test
	public void testToStringObjectMap() {
		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"en_US", "en_US"
			).build(),
			LocalizedValueUtil.toStringObjectMap(
				HashMapBuilder.put(
					LocaleUtil.US, "en_US"
				).build()));
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = mock(Language.class);

		when(
			language.isAvailableLocale(LocaleUtil.BRAZIL)
		).thenReturn(
			true
		);

		when(
			language.isAvailableLocale(LocaleUtil.US)
		).thenReturn(
			true
		);

		when(
			language.getLanguageId(LocaleUtil.BRAZIL)
		).thenReturn(
			"pt_BR"
		);

		when(
			language.getLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);

		languageUtil.setLanguage(language);
	}

}