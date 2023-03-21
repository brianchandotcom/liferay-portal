/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.server.admin.web.internal.constants.ServerAdminNavigationEntryConstants;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albert Lee
 */
@Component(
	property = "screen.navigation.category.order:Integer=20",
	service = ScreenNavigationCategory.class
)
public class ServerPortalPropertiesScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return ServerAdminNavigationEntryConstants.
			CATEGORY_KEY_PORTAL_PROPERTIES;
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(locale, "portal-properties");
	}

	@Override
	public String getScreenNavigationKey() {
		return ServerAdminNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_PROPERTIES;
	}

	@Reference
	protected Language language;

}