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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.provider.PLOOriginalTranslationProvider;
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
@RunWith(Arquillian.class)
public class PLOOriginalTranslationProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGet() throws Exception {
		String key = "available-languages";
		Locale locale = LocaleUtil.getDefault();

		String originalValue = LanguageUtil.get(locale, key);

		Assert.assertNotNull(originalValue);

		PLOEntry ploEntry = _ploEntryLocalService.addOrUpdatePLOEntry(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), key,
			LanguageUtil.getLanguageId(locale), RandomTestUtil.randomString());

		Assert.assertEquals(ploEntry.getValue(), LanguageUtil.get(locale, key));

		Assert.assertEquals(
			originalValue, _ploOriginalTranslationProvider.get(locale, key));
	}

	@Test
	public void testGetNonexistentKey() throws Exception {
		Assert.assertNull(
			_ploOriginalTranslationProvider.get(
				LocaleUtil.getDefault(),
				"DOES_NOT_EXIST_" + RandomTestUtil.randomString()));
	}

	@Inject
	private PLOEntryLocalService _ploEntryLocalService;

	@Inject
	private PLOOriginalTranslationProvider _ploOriginalTranslationProvider;

}