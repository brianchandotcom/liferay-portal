/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.portlet.category.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.portlet.category.PortletCategoryManager;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

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
			LoginPortletKeys.LOGIN);

		_portletPreferencesLocalService.addPortletPreferences(
			_group.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _draftLayout.getPlid(),
			LoginPortletKeys.LOGIN, portlet,
			PortletConstants.DEFAULT_PREFERENCES);

		_assertEmbedded(true);
	}

	@Test
	public void testAssertEmbeddedValueWithPortletInDeletedFragmentEntryLink()
		throws Exception {

		FragmentEntryLink fragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				JSONUtil.put(
					"portletId", LoginPortletKeys.LOGIN
				).toString(),
				_draftLayout,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_draftLayout.getPlid()));

		_fragmentEntryLinkLocalService.updateDeleted(
			TestPropsValues.getUserId(),
			fragmentEntryLink.getFragmentEntryLinkId(), true);

		_assertEmbedded(false);
	}

	@Test
	public void testAssertEmbeddedValueWithPortletInWFragmentEntryLink()
		throws Exception {

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			JSONUtil.put(
				"portletId", LoginPortletKeys.LOGIN
			).toString(),
			_draftLayout,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_draftLayout.getPlid()));

		_assertEmbedded(false);
	}

	private void _assertEmbedded(boolean expectedValue) throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = _getThemeDisplay(_draftLayout);

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
						LoginPortletKeys.LOGIN)) {

					Assert.assertEquals(
						expectedValue,
						portletJSONObject.getBoolean("embedded"));
				}
			}
		}
	}

	private ThemeDisplay _getThemeDisplay(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());
		themeDisplay.setLocale(LocaleUtil.getSiteDefault());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private Layout _draftLayout;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletCategoryManager _portletCategoryManager;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}