/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.modified.facet.portlet.shared.search;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Joshua Cords
 */
public class ModifiedFacetPortletSharedSearchContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testInvalidRangeStrings() {
		TestModifiedFacetPortletSharedSearchContributor
			testModifiedFacetPortletSharedSearchContributor =
				new TestModifiedFacetPortletSharedSearchContributor();

		PortletSharedSearchSettings portletSharedSearchSettings =
			_mockPortletSharedSearchSettings();

		List<String> selectedRangeStrings =
			testModifiedFacetPortletSharedSearchContributor.
				getSelectedRangeStrings(
					"dateRange", _PARAMETER_NAME, portletSharedSearchSettings,
					JSONUtil.put(
						JSONUtil.put(
							"label", "past-hour"
						).put(
							"range", _RANGE
						)));

		Assert.assertTrue(selectedRangeStrings.contains(_RANGE));
		Assert.assertEquals(
			"Expect only valid ranges", 1, selectedRangeStrings.size());
	}

	private PortletSharedSearchSettings _mockPortletSharedSearchSettings() {
		PortletSharedSearchSettings portletSharedSearchSettings = Mockito.mock(
			PortletSharedSearchSettings.class);

		Mockito.doReturn(
			new String[] {"past-hour", "bogus-value"}
		).when(
			portletSharedSearchSettings
		).getParameterValues(
			_PARAMETER_NAME
		);

		return portletSharedSearchSettings;
	}

	private static final String _PARAMETER_NAME = "modified";

	private static final String _RANGE = "[20240119221249 TO 20240119231249]";

	private static class TestModifiedFacetPortletSharedSearchContributor
		extends ModifiedFacetPortletSharedSearchContributor {

		@Override
		public List<String> getSelectedRangeStrings(
			String aggregationType, String parameterName,
			PortletSharedSearchSettings portletSharedSearchSettings,
			JSONArray rangesJSONArray) {

			return super.getSelectedRangeStrings(
				aggregationType, parameterName, portletSharedSearchSettings,
				rangesJSONArray);
		}

	}

}