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

package com.liferay.address.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class CountryImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetNameCurrentValue() throws Exception {
		Country country = _countryLocalService.fetchCountryByA2(
			TestPropsValues.getCompanyId(), "US");

		Locale originalThemeDisplayLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		Map<String, String> originalLanguageIdToTitleMap =
			country.getLanguageIdToTitleMap();

		try {
			String testLanguageId = LocaleUtil.toLanguageId(LocaleUtil.FRANCE);

			_countryLocalService.updateCountryLocalizations(
				country,
				Collections.singletonMap(
					testLanguageId, RandomTestUtil.randomString()));

			country.setNameCurrentLanguageId(testLanguageId);

			Assert.assertEquals(
				country.getTitle(testLanguageId),
				country.getNameCurrentValue());

			Locale testLocale = LocaleUtil.SPAIN;

			_countryLocalService.updateCountryLocalizations(
				country,
				Collections.singletonMap(
					LocaleUtil.toLanguageId(testLocale),
					RandomTestUtil.randomString()));

			country.setNameCurrentLanguageId(null);

			LocaleThreadLocal.setThemeDisplayLocale(testLocale);

			Assert.assertEquals(
				country.getTitle(testLocale), country.getNameCurrentValue());

			_countryLocalService.updateCountryLocalizations(
				country, Collections.emptyMap());

			LocaleThreadLocal.setThemeDisplayLocale(testLocale);

			Assert.assertEquals(
				country.getName(testLocale), country.getNameCurrentValue());
		}
		finally {
			LocaleThreadLocal.setThemeDisplayLocale(originalThemeDisplayLocale);

			_countryLocalService.updateCountryLocalizations(
				country, originalLanguageIdToTitleMap);
		}
	}

	@Test
	public void testGetTitle() throws Exception {
		Country country = _countryLocalService.fetchCountryByA2(
			TestPropsValues.getCompanyId(), "US");

		Assert.assertTrue(
			Validator.isNotNull(
				country.getTitle(LanguageUtil.getLanguageId(LocaleUtil.US))));
		Assert.assertTrue(Validator.isNotNull(country.getTitle(LocaleUtil.US)));
	}

	@Inject
	private CountryLocalService _countryLocalService;

}