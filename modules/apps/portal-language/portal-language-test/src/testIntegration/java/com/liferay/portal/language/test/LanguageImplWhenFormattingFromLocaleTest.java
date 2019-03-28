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

package com.liferay.portal.language.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class LanguageImplWhenFormattingFromLocaleTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testFormatWithKeyNull() {
		Assert.assertEquals(
			null,
			_language.format(LocaleThreadLocal.getDefaultLocale(), null, "31"));
	}

	@Test
	public void testFormatWithLocaleNull() {
		Locale defaultLocale = LocaleThreadLocal.getDefaultLocale();

		Locale nullableLocale = null;

		try {
			Assert.assertEquals(
				LanguageImplTestConstants.LANG_KEY_WITH_ARGUMENT,
				_language.format(
					nullableLocale,
					LanguageImplTestConstants.LANG_KEY_WITH_ARGUMENT, "31"));
		}
		finally {
			LocaleThreadLocal.setDefaultLocale(defaultLocale);
		}
	}

	@Test
	public void testFormatWithOneArgument() {
		String value = _language.format(
			LocaleUtil.US, LanguageImplTestConstants.LANG_KEY_WITH_ARGUMENT,
			"31");

		Assert.assertEquals("31 Hours", value);
	}

	@Test
	public void testFormatWithOneNontranslatableAmericanArgument() {
		Locale locale = LocaleUtil.US;

		String value = _language.format(
			locale, LanguageImplTestConstants.LANG_KEY_WITH_ARGUMENT,
			LanguageImplTestConstants.BIG_INTEGER, false);

		Assert.assertEquals("1,234,567,890 Hours", value);

		value = _language.format(
			locale, LanguageImplTestConstants.LANG_KEY_WITH_ARGUMENT,
			LanguageImplTestConstants.BIG_DOUBLE, false);

		Assert.assertEquals("1,234,567,890.12 Hours", value);

		value = _language.format(
			locale, LanguageImplTestConstants.LANG_KEY_WITH_ARGUMENT,
			LanguageImplTestConstants.BIG_FLOAT, false);

		Assert.assertEquals("1,234,567.875 Hours", value);
	}

	@Test
	public void testFormatWithOneNontranslatableSpanishArgument() {
		Locale locale = LocaleUtil.SPAIN;

		String value = _language.format(
			locale, LanguageImplTestConstants.LANG_KEY_WITH_ARGUMENT,
			LanguageImplTestConstants.BIG_INTEGER, false);

		Assert.assertEquals("1.234.567.890 horas", value);

		value = _language.format(
			locale, LanguageImplTestConstants.LANG_KEY_WITH_ARGUMENT,
			LanguageImplTestConstants.BIG_DOUBLE, false);

		Assert.assertEquals("1.234.567.890,12 horas", value);

		value = _language.format(
			locale, LanguageImplTestConstants.LANG_KEY_WITH_ARGUMENT,
			LanguageImplTestConstants.BIG_FLOAT, false);

		Assert.assertEquals("1.234.567,875 horas", value);
	}

	@Test
	public void testFormatWithTwoArguments() {
		String value = _language.format(
			LocaleUtil.US, LanguageImplTestConstants.LANG_KEY_WITH_ARGUMENTS,
			new Object[] {"A", "B"});

		Assert.assertEquals("A has invited you to join B.", value);
	}

	@Inject
	private static Language _language;

}