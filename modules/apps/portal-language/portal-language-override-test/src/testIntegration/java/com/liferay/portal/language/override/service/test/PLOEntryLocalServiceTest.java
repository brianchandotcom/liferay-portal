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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.language.override.exception.PLOEntryKeyException;
import com.liferay.portal.language.override.exception.PLOEntryValueException;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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

		_assertTranslationValue(key, null);

		PLOEntry ploEntry = _ploEntryLocalService.addOrUpdatePLOEntry(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), key,
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()),
			RandomTestUtil.randomString());

		_assertTranslationValue(key, ploEntry.getValue());
	}

	@Test
	public void testAddPLOEntryOverrideExistingKey() throws Exception {
		String key = "available-languages";

		Assert.assertNotNull(
			LanguageResources.getMessage(LocaleUtil.getDefault(), key));

		PLOEntry ploEntry = _ploEntryLocalService.addOrUpdatePLOEntry(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), key,
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()),
			RandomTestUtil.randomString());

		_assertTranslationValue(key, ploEntry.getValue());
	}

	@Test(expected = PLOEntryKeyException.MustBeShorter.class)
	public void testAddPLOEntryWhenKeyTooLong() throws Exception {
		int keyMaxLength =
			ModelHintsUtil.getMaxLength(PLOEntry.class.getName(), "key") + 1;

		_ploEntryLocalService.addOrUpdatePLOEntry(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(keyMaxLength),
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()),
			RandomTestUtil.randomString());
	}

	@Test(expected = PLOEntryKeyException.MustNotBeNull.class)
	public void testAddPLOEntryWithEmptyKey() throws Exception {
		String key = StringPool.BLANK;

		_ploEntryLocalService.addOrUpdatePLOEntry(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), key,
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()),
			RandomTestUtil.randomString());
	}

	@Test(expected = PLOEntryValueException.MustNotBeNull.class)
	public void testAddPLOEntryWithEmptyValue() throws Exception {
		String value = StringPool.BLANK;

		_ploEntryLocalService.addOrUpdatePLOEntry(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(),
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()), value);
	}

	private void _assertTranslationValue(String key, String value) {
		Assert.assertEquals(
			value, LanguageResources.getMessage(LocaleUtil.getDefault(), key));
		Assert.assertEquals(
			value,
			ResourceBundleUtil.getString(
				LanguageResources.getResourceBundle(LocaleUtil.getDefault()),
				key));
	}

	@Inject
	private PLOEntryLocalService _ploEntryLocalService;

}