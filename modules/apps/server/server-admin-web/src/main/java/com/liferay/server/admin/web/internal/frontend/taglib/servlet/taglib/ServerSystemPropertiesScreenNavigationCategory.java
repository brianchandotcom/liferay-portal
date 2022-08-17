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
		"screen.navigation.category.order:Integer=10",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class ServerSystemPropertiesScreenNavigationCategory
	implements ScreenNavigationCategory, ScreenNavigationEntry<Object> {

	@Override
	public String getCategoryKey() {
		return ServerAdminNavigationEntryConstants.
			CATEGORY_KEY_SYSTEM_PROPERTIES;
	}

	@Override
	public String getEntryKey() {
		return ServerAdminNavigationEntryConstants.ENTRY_KEY_SYSTEM_PROPERTIES;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "system-properties");
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
			"/view_system_properties.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _language;

}