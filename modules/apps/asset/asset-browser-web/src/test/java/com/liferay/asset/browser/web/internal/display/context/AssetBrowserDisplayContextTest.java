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

package com.liferay.asset.browser.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.language.LanguageImpl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class AssetBrowserDisplayContextTest {

	@BeforeClass
	public static void setUpClass() {
		PortletPreferencesFactoryUtil portletPreferencesFactoryUtil =
			new PortletPreferencesFactoryUtil();

		portletPreferencesFactoryUtil.setPortletPreferencesFactory(
			Mockito.mock(PortletPreferencesFactory.class));

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());
	}

	@Test
	public void testGetGroupTypeTitleWithDepot() {
		AssetBrowserDisplayContext assetBrowserDisplayContext =
			new AssetBrowserDisplayContext(
				null, _getMockHttpServletRequest(GroupConstants.TYPE_DEPOT),
				null, null, null);

		Assert.assertEquals(
			"asset-library", assetBrowserDisplayContext.getGroupTypeTitle());
	}

	@Test
	public void testGetGroupTypeTitleWithSite() {
		AssetBrowserDisplayContext assetBrowserDisplayContext =
			new AssetBrowserDisplayContext(
				null, _getMockHttpServletRequest(GroupConstants.TYPE_SITE_OPEN),
				null, null, null);

		Assert.assertEquals(
			"site", assetBrowserDisplayContext.getGroupTypeTitle());
	}

	private Group _getGroup(long groupId, int type) {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getType()
		).thenReturn(
			type
		);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			groupId
		);

		return group;
	}

	private MockHttpServletRequest _getMockHttpServletRequest(int type) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(type));

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(int type) {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		long groupId = RandomTestUtil.randomLong();

		Mockito.when(
			themeDisplay.getScopeGroupId()
		).thenReturn(
			groupId
		);

		Group group = _getGroup(groupId, type);

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			group
		);

		return themeDisplay;
	}

}