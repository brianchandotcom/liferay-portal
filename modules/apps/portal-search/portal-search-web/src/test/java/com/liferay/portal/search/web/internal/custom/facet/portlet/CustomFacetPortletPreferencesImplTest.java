/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.facet.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.web.internal.seo.SEOPortletPreferences;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Prathima Shreenath
 */
public class CustomFacetPortletPreferencesImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetSEOParameterNameWhenParameterNameIsNull() {
		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		_mockValue(
			CustomFacetPortletPreferences.PREFERENCE_KEY_AGGREGATION_FIELD,
			portletPreferences, "userName");
		_mockValue(
			CustomFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME,
			portletPreferences, StringPool.BLANK);

		SEOPortletPreferences seoPortletPreferences =
			new CustomFacetPortletPreferencesImpl(portletPreferences);

		Assert.assertEquals(
			"userName", seoPortletPreferences.getSEOParameterName());
	}

	@Test
	public void testGetSEOParameterNameWhenParameterNameIsSet() {
		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		_mockValue(
			CustomFacetPortletPreferences.PREFERENCE_KEY_AGGREGATION_FIELD,
			portletPreferences, RandomTestUtil.randomString());
		_mockValue(
			CustomFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME,
			portletPreferences, "customParameter");

		SEOPortletPreferences seoPortletPreferences =
			new CustomFacetPortletPreferencesImpl(portletPreferences);

		Assert.assertEquals(
			"customParameter", seoPortletPreferences.getSEOParameterName());
	}

	private void _mockValue(
		String key, PortletPreferences portletPreferences, String value) {

		Mockito.when(
			portletPreferences.getValue(key, StringPool.BLANK)
		).thenReturn(
			value
		);
	}

}