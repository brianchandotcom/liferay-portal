/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.language.web.internal.display.context;

import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.site.navigation.language.web.internal.configuration.SiteNavigationLanguagePortletInstanceConfiguration;

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
public class SiteNavigationLanguageDisplayContextTest {

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
		SiteNavigationLanguageDisplayContext
			siteNavigationLanguageDisplayContext =
				new SiteNavigationLanguageDisplayContext(
					_mockHttpServletRequest());

		Assert.assertEquals(
			GroupConstants.GUEST,
			siteNavigationLanguageDisplayContext.getDisplayStyleGroupKey());
	}

	@Test
	public void testGetDisplayStyleGroupKeyWithConfiguration()
		throws Exception {

		_setUpSiteNavigationLanguagePortletInstanceConfiguration(
			GroupConstants.CONTROL_PANEL);

		SiteNavigationLanguageDisplayContext
			siteNavigationLanguageDisplayContext =
				new SiteNavigationLanguageDisplayContext(
					_mockHttpServletRequest());

		Assert.assertEquals(
			GroupConstants.CONTROL_PANEL,
			siteNavigationLanguageDisplayContext.getDisplayStyleGroupKey());
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
			_siteNavigationLanguagePortletInstanceConfiguration
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

	private void _setUpSiteNavigationLanguagePortletInstanceConfiguration(
		String groupKey) {

		Mockito.when(
			_siteNavigationLanguagePortletInstanceConfiguration.
				displayStyleGroupKey()
		).thenReturn(
			groupKey
		);
	}

	private final MockedStatic<ConfigurationProviderUtil>
		_configurationProviderUtilMockedStatic = Mockito.mockStatic(
			ConfigurationProviderUtil.class);
	private final MockedStatic<GroupLocalServiceUtil>
		_groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			GroupLocalServiceUtil.class);
	private final SiteNavigationLanguagePortletInstanceConfiguration
		_siteNavigationLanguagePortletInstanceConfiguration = Mockito.mock(
			SiteNavigationLanguagePortletInstanceConfiguration.class);

}