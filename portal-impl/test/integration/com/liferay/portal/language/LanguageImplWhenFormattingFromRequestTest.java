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

package com.liferay.portal.language;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.language.LanguageWrapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class LanguageImplWhenFormattingFromRequestTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_languageImpl = (LanguageImpl)PortalBeanLocatorUtil.locate(
			"com.liferay.portal.language.LanguageImpl");
	}

	@Test
	public void testFormatWithOneArgument() {
		LanguageImplTest.MockLanguageServletRequest mockLanguageServletRequest =
			new LanguageImplTest.MockLanguageServletRequest(LocaleUtil.US);

		String value = _languageImpl.format(
			mockLanguageServletRequest.getRequest(), _LANG_KEY_WITH_ARGUMENT,
			"31");

		Assert.assertEquals("31 Hours", value);
	}

	@Test
	public void testFormatWithOneLanguageWrapper() {
		LanguageImplTest.MockLanguageServletRequest mockLanguageServletRequest =
			new LanguageImplTest.MockLanguageServletRequest(LocaleUtil.US);

		String value = _languageImpl.format(
			mockLanguageServletRequest.getRequest(), _LANG_KEY_WITH_ARGUMENT,
			new LanguageWrapper("a", "31", "a"));

		Assert.assertEquals("a31a Hours", value);
	}

	@Test
	public void testFormatWithOneNontranslatableAmericanArgument() {
		LanguageImplTest.MockLanguageServletRequest mockLanguageServletRequest =
			new LanguageImplTest.MockLanguageServletRequest(LocaleUtil.US);

		String value = _languageImpl.format(
			mockLanguageServletRequest.getRequest(), _LANG_KEY_WITH_ARGUMENT,
			_BIG_INTEGER, false);

		Assert.assertEquals("1,234,567,890 Hours", value);

		value = _languageImpl.format(
			mockLanguageServletRequest.getRequest(), _LANG_KEY_WITH_ARGUMENT,
			_BIG_DOUBLE, false);

		Assert.assertEquals("1,234,567,890.12 Hours", value);

		value = _languageImpl.format(
			mockLanguageServletRequest.getRequest(), _LANG_KEY_WITH_ARGUMENT,
			_BIG_FLOAT, false);

		Assert.assertEquals("1,234,567.875 Hours", value);
	}

	@Test
	public void testFormatWithOneNontranslatableSpanishArgument() {
		LanguageImplTest.MockLanguageServletRequest mockLanguageServletRequest =
			new LanguageImplTest.MockLanguageServletRequest(LocaleUtil.SPAIN);

		String value = _languageImpl.format(
			mockLanguageServletRequest.getRequest(), _LANG_KEY_WITH_ARGUMENT,
			_BIG_INTEGER, false);

		Assert.assertEquals("1.234.567.890 horas", value);

		value = _languageImpl.format(
			mockLanguageServletRequest.getRequest(), _LANG_KEY_WITH_ARGUMENT,
			_BIG_DOUBLE, false);

		Assert.assertEquals("1.234.567.890,12 horas", value);

		value = _languageImpl.format(
			mockLanguageServletRequest.getRequest(), _LANG_KEY_WITH_ARGUMENT,
			_BIG_FLOAT, false);

		Assert.assertEquals("1.234.567,875 horas", value);
	}

	@Test
	public void testFormatWithTwoArguments() {
		LanguageImplTest.MockLanguageServletRequest mockLanguageServletRequest =
			new LanguageImplTest.MockLanguageServletRequest(LocaleUtil.US);

		String value = _languageImpl.format(
			mockLanguageServletRequest.getRequest(), _LANG_KEY_WITH_ARGUMENTS,
			new Object[] {"A", "B"});

		Assert.assertEquals("A has invited you to join B.", value);
	}

	@Test
	public void testFormatWithTwoLanguageWrappers() {
		LanguageImplTest.MockLanguageServletRequest mockLanguageServletRequest =
			new LanguageImplTest.MockLanguageServletRequest(LocaleUtil.US);

		LanguageWrapper[] languageWrappers = new LanguageWrapper[2];

		languageWrappers[0] = new LanguageWrapper("a", "A", "a");
		languageWrappers[1] = new LanguageWrapper("b", "B", "b");

		String value = _languageImpl.format(
			mockLanguageServletRequest.getRequest(), _LANG_KEY_WITH_ARGUMENTS,
			languageWrappers);

		Assert.assertEquals("aAa has invited you to join bBb.", value);
	}

}