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

package com.liferay.portal.language.override.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class PLOEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddPLOEntryNewKey() throws Exception {
		String key = RandomTestUtil.randomString();
		Locale locale = LocaleUtil.getDefault();

		_assertTranslationValue(locale, key, null);

		PLOEntry ploEntry = _ploEntryLocalService.addOrUpdatePLOEntry(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), key,
			LanguageUtil.getLanguageId(locale), RandomTestUtil.randomString());

		_assertTranslationValue(locale, key, ploEntry.getValue());
	}

	@Test
	public void testAddPLOEntryOverrideExistingKey() throws Exception {
		String key = "available-languages";
		Locale locale = LocaleUtil.getDefault();

		String originalValue = LanguageResources.getMessage(locale, key);

		Assert.assertNotNull(originalValue);

		PLOEntry ploEntry = _ploEntryLocalService.addOrUpdatePLOEntry(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), key,
			LanguageUtil.getLanguageId(locale), RandomTestUtil.randomString());

		_assertTranslationValue(locale, key, ploEntry.getValue());
	}

	private void _assertTranslationValue(
		Locale locale, String key, String expectedValue) {

		Assert.assertEquals(
			expectedValue, LanguageResources.getMessage(locale, key));

		Assert.assertEquals(
			expectedValue,
			ResourceBundleUtil.getString(
				LanguageResources.getResourceBundle(locale), key));
	}

	@Inject
	private PLOEntryLocalService _ploEntryLocalService;

}