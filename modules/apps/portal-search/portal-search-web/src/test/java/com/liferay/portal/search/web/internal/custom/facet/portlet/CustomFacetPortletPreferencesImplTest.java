/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.facet.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
			portletPreferences,
			CustomFacetPortletPreferences.PREFERENCE_KEY_AGGREGATION_FIELD,
			"userName");
		_mockValue(
			portletPreferences,
			CustomFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME,
			StringPool.BLANK);

		Assert.assertEquals(
			"userName",
			new CustomFacetPortletPreferencesImpl(
				portletPreferences
			).getSEOParameterName());
	}

	@Test
	public void testGetSEOParameterNameWhenParameterNameIsSet() {
		PortletPreferences portletPreferences = Mockito.mock(
			PortletPreferences.class);

		_mockValue(
			portletPreferences,
			CustomFacetPortletPreferences.PREFERENCE_KEY_AGGREGATION_FIELD,
			RandomTestUtil.randomString());
		_mockValue(
			portletPreferences,
			CustomFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME,
			"customParameter");

		Assert.assertEquals(
			"customParameter",
			new CustomFacetPortletPreferencesImpl(
				portletPreferences
			).getSEOParameterName());
	}

	private void _mockValue(
		PortletPreferences portletPreferences, String key, String value) {

		Mockito.when(
			portletPreferences.getValue(key, StringPool.BLANK)
		).thenReturn(
			value
		);
	}

}