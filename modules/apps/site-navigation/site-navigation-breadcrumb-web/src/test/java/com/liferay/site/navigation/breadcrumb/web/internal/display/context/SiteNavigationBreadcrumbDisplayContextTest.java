/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.breadcrumb.web.internal.display.context;

import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.site.navigation.breadcrumb.web.internal.configuration.SiteNavigationBreadcrumbPortletInstanceConfiguration;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Eudaldo Alonso
 */
public class SiteNavigationBreadcrumbDisplayContextTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpConfigurationProviderUtil();
		_setUpGroupLocalServiceUtil();
	}

	@After
	public void tearDown() {
		_configurationProviderUtilMockedStatic.close();
		_groupLocalServiceUtilMockedStatic.close();
	}

	@Test
	public void testGetDisplayStyleGroupKey() throws Exception {
		SiteNavigationBreadcrumbDisplayContext
			siteNavigationBreadcrumbDisplayContext =
				new SiteNavigationBreadcrumbDisplayContext(
					_mockHttpServletRequest(), null);

		Assert.assertEquals(
			GroupConstants.GUEST,
			siteNavigationBreadcrumbDisplayContext.getDisplayStyleGroupKey());
	}

	@Test
	public void testGetDisplayStyleGroupKeyWithConfiguration()
		throws Exception {

		Mockito.when(
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				displayStyleGroupKey()
		).thenReturn(
			GroupConstants.CONTROL_PANEL
		);

		SiteNavigationBreadcrumbDisplayContext
			siteNavigationBreadcrumbDisplayContext =
				new SiteNavigationBreadcrumbDisplayContext(
					_mockHttpServletRequest(), null);

		Assert.assertEquals(
			GroupConstants.CONTROL_PANEL,
			siteNavigationBreadcrumbDisplayContext.getDisplayStyleGroupKey());
	}

	private HttpServletRequest _mockHttpServletRequest() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			(ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		return httpServletRequest;
	}

	private void _setUpConfigurationProviderUtil() {
		_configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getPortletInstanceConfiguration(
				Mockito.any(), Mockito.any())
		).thenReturn(
			_siteNavigationBreadcrumbPortletInstanceConfiguration
		);
	}

	private void _setUpGroupLocalServiceUtil() {
		Group group = new GroupImpl();

		group.setGroupKey(GroupConstants.GUEST);

		Mockito.when(
			GroupLocalServiceUtil.fetchGroup(Mockito.anyLong())
		).thenReturn(
			group
		);
	}

	private final MockedStatic<ConfigurationProviderUtil>
		_configurationProviderUtilMockedStatic = Mockito.mockStatic(
			ConfigurationProviderUtil.class);
	private final MockedStatic<GroupLocalServiceUtil>
		_groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			GroupLocalServiceUtil.class);
	private final SiteNavigationBreadcrumbPortletInstanceConfiguration
		_siteNavigationBreadcrumbPortletInstanceConfiguration = Mockito.mock(
			SiteNavigationBreadcrumbPortletInstanceConfiguration.class);

}