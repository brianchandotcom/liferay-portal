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

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Eduardo García
 */
public class PortletCategoryComparatorTest {

	@Test
	public void testCompareLocalized() {
		PropsTestUtil.setProps(Collections.emptyMap());

		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		languageUtil.setLanguage(language);

		Mockito.when(
			language.get(Matchers.eq(LocaleUtil.SPAIN), Matchers.eq("area"))
		).thenReturn(
			"Área"
		);

		Mockito.when(
			language.get(Matchers.eq(LocaleUtil.SPAIN), Matchers.eq("zone"))
		).thenReturn(
			"Zona"
		);

		PortletCategory portletCategory1 = new PortletCategory("area");
		PortletCategory portletCategory2 = new PortletCategory("zone");

		PortletCategoryComparator portletCategoryComparator =
			new PortletCategoryComparator(LocaleUtil.SPAIN);

		int value = portletCategoryComparator.compare(
			portletCategory1, portletCategory2);

		Assert.assertTrue(value < 0);
	}

}