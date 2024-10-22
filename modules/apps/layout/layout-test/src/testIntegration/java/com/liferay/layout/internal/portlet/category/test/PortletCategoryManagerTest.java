/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.portlet.category.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.portlet.category.PortletCategoryManager;
import com.liferay.layout.test.constants.LayoutPortletKeys;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class PortletCategoryManagerTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_draftLayout = layout.fetchDraftLayout();
	}

	@Test
	public void testAssertEmbeddedValue() throws Exception {
		_assertEmbedded(false);
	}

	@Test
	public void testAssertEmbeddedValueWithEmbeddedPortlet() throws Exception {
		Portlet portlet = _portletLocalService.getPortletById(
			LayoutPortletKeys.LAYOUT_NONINSTANCEABLE_TEST_PORTLET);

		_portletPreferencesLocalService.addPortletPreferences(
			_group.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _draftLayout.getPlid(),
			LayoutPortletKeys.LAYOUT_NONINSTANCEABLE_TEST_PORTLET, portlet,
			PortletConstants.DEFAULT_PREFERENCES);

		_assertEmbedded(true);
	}

	@Test
	public void testAssertEmbeddedValueWithPortletInDeletedFragmentEntryLink()
		throws Exception {

		JSONObject jsonObject = ContentLayoutTestUtil.addPortletToLayout(
			_draftLayout,
			LayoutPortletKeys.LAYOUT_NONINSTANCEABLE_TEST_PORTLET);

		ContentLayoutTestUtil.markItemForDeletionFromLayout(
			jsonObject.getString("addedItemId"), _draftLayout,
			LayoutPortletKeys.LAYOUT_NONINSTANCEABLE_TEST_PORTLET);

		_assertEmbedded(false);
	}

	@Test
	public void testAssertEmbeddedValueWithPortletInWFragmentEntryLink()
		throws Exception {

		ContentLayoutTestUtil.addPortletToLayout(
			_draftLayout,
			LayoutPortletKeys.LAYOUT_NONINSTANCEABLE_TEST_PORTLET);

		_assertEmbedded(false);
	}

	private void _assertEmbedded(boolean expectedValue) throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = ContentLayoutTestUtil.getThemeDisplay(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()),
			_group, _draftLayout);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		JSONArray jsonArray = _portletCategoryManager.getPortletsJSONArray(
			mockHttpServletRequest, themeDisplay);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray portletsJSONArray = jsonObject.getJSONArray("portlets");

			for (int j = 0; j < portletsJSONArray.length(); j++) {
				JSONObject portletJSONObject = portletsJSONArray.getJSONObject(
					j);

				if (Objects.equals(
						portletJSONObject.get("portletId"),
						LayoutPortletKeys.
							LAYOUT_NONINSTANCEABLE_TEST_PORTLET)) {

					Assert.assertEquals(
						expectedValue,
						portletJSONObject.getBoolean("embedded"));
				}
			}
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private Layout _draftLayout;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletCategoryManager _portletCategoryManager;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}