/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.server.admin.web.internal.constants.ServerAdminNavigationEntryConstants;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albert Lee
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class ServerPortalPropertiesScreenNavigationCategory
	implements ScreenNavigationCategory, ScreenNavigationEntry<Object> {

	@Override
	public String getCategoryKey() {
		return ServerAdminNavigationEntryConstants.
			CATEGORY_KEY_PORTAL_PROPERTIES;
	}

	@Override
	public String getEntryKey() {
		return ServerAdminNavigationEntryConstants.ENTRY_KEY_PORTAL_PROPERTIES;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "portal-properties");
	}

	@Override
	public String getScreenNavigationKey() {
		return ServerAdminNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_PROPERTIES;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/view_portal_properties.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _language;

}