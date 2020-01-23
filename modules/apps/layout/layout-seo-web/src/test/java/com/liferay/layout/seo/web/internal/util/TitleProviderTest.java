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

package com.liferay.layout.seo.web.internal.util;

import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListMergeable;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class TitleProviderTest {

	@Before
	public void setUp() {
		_titleProvider = new TitleProvider(
			new LayoutSEOLinkManager() {

				@Override
				public String getFullPageTitle(
					Layout layout, String portletId, String tilesTitle,
					ListMergeable<String> titleListMergeable,
					ListMergeable<String> subtitleListMergeable,
					String companyName, Locale locale) {

					return "htmlTitle";
				}

				@Override
				public List<LayoutSEOLink> getLocalizedLayoutSEOLinks(
					Layout layout, Locale locale, String canonicalURL,
					Map<Locale, String> alternateURLs) {

					return null;
				}

			});
	}

	@Test
	public void testGetTitle() throws PortalException {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(Mockito.mock(Company.class));

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		Assert.assertEquals(
			"htmlTitle", _titleProvider.getTitle(mockHttpServletRequest));
	}

	private TitleProvider _titleProvider;

}