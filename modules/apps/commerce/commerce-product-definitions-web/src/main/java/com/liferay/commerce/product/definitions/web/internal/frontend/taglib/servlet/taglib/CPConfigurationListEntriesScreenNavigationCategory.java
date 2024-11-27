/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.product.servlet.taglib.ui.constants.CPConfigurationListScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "screen.navigation.category.order:Integer=30",
	service = ScreenNavigationCategory.class
)
public class CPConfigurationListEntriesScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CPConfigurationListScreenNavigationConstants.
			CATEGORY_KEY_ENTRIES;
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(
			locale,
			CPConfigurationListScreenNavigationConstants.CATEGORY_KEY_ENTRIES);
	}

	@Override
	public String getScreenNavigationKey() {
		return CPConfigurationListScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_CP_CONFIGURATION_LIST_GENERAL;
	}

	@Reference
	protected Language language;

}