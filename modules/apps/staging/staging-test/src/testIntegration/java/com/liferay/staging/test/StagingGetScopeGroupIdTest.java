/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.portlet.ActionRequest;
import javax.portlet.Portlet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Tamas Molnar
 * @author Eric Yan
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class StagingGetScopeGroupIdTest extends BaseLocalStagingTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Ignore
	@Test
	public void testPortletScopeGroupIdWithPortletLayout() throws Exception {
		_testPortletScopeGroupId(
			liveLayout, stagingLayout, BlogsPortletKeys.BLOGS);
	}

	@Override
	protected String[] getNotStagedPortletIds() {
		return new String[] {BlogsPortletKeys.BLOGS_ADMIN};
	}

	private void _testPortletScopeGroupId(
			Layout liveLayout, Layout stagingLayout, String portletId)
		throws Exception {

		LayoutTestUtil.addPortletToLayout(stagingLayout, portletId);

		_updateLayoutPortletScope(
			stagingLayout, portletId, stagingLayout.getUuid(), "layout");
		publishLayouts();

		Group scopeGroup = GroupLocalServiceUtil.fetchGroup(
			liveLayout.getCompanyId(), _portal.getClassNameId(Layout.class),
			liveLayout.getPlid());

		_testScopeGroupId(
			scopeGroup.getGroupId(), liveLayout, stagingLayout, portletId,
			false);
		_testScopeGroupId(
			scopeGroup.getGroupId(), liveLayout, stagingLayout, portletId,
			true);
	}

	private void _testScopeGroupId(
			long expectedScopeGroupId, Layout liveLayout, Layout stagingLayout,
			String portletId, boolean checkStagingGroup)
		throws Exception {

		_mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, liveLayout);

		Assert.assertEquals(
			expectedScopeGroupId,
			PortalUtil.getScopeGroupId(
				_mockHttpServletRequest, portletId, checkStagingGroup));

		_mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, stagingLayout);

		Assert.assertEquals(
			expectedScopeGroupId,
			PortalUtil.getScopeGroupId(
				_mockHttpServletRequest, portletId, checkStagingGroup));
	}

	private void _updateLayoutPortletScope(
			Layout layout, String layoutPortletId, String lfrScopeLayoutUuid,
			String lfrScopeType)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(layout.getCompanyId()));
		themeDisplay.setLanguageId(layout.getDefaultLanguageId());
		themeDisplay.setLayout(layout);
		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(layout.getDefaultLanguageId()));
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		themeDisplay.setUser(TestPropsValues.getUser());

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletActionRequest.setParameter(
			"portletResource", layoutPortletId);
		mockLiferayPortletActionRequest.setParameter(
			"scope",
			StringBundler.concat(
				lfrScopeType, StringPool.COMMA, lfrScopeLayoutUuid));
		mockLiferayPortletActionRequest.setPreferences(
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, layoutPortletId));

		ReflectionTestUtil.invoke(
			_portlet, "_updateScope", new Class<?>[] {ActionRequest.class},
			mockLiferayPortletActionRequest);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

	@Inject
	private Portal _portal;

	@Inject(
		filter = "component.name=com.liferay.portlet.configuration.web.internal.portlet.PortletConfigurationPortlet"
	)
	private Portlet _portlet;

	@Inject
	private PortletLocalService _portletLocalService;

}