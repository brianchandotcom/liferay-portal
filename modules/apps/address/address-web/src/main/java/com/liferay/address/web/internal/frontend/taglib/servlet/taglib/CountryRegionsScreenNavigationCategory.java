/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.address.web.internal.constants.CountryScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=20"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CountryRegionsScreenNavigationCategory
	implements ScreenNavigationCategory, ScreenNavigationEntry<Country> {

	@Override
	public String getCategoryKey() {
		return CountryScreenNavigationConstants.CATEGORY_KEY_REGIONS;
	}

	@Override
	public String getEntryKey() {
		return CountryScreenNavigationConstants.CATEGORY_KEY_REGIONS;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "regions");
	}

	@Override
	public String getScreenNavigationKey() {
		return CountryScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COUNTRY;
	}

	@Override
	public boolean isVisible(User user, Country country) {
		if (country == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/country/regions.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.address.web)")
	private ServletContext _servletContext;

}