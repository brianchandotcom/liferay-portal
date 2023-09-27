/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.catalog.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.catalog.web.internal.constants.CommerceCatalogScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(
	property = "screen.navigation.category.order:Integer=30",
	service = ScreenNavigationCategory.class
)
public class CommerceCatalogAccountGroupsScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceCatalogScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_CATALOG_ACCOUNT_GROUPS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return language.get(resourceBundle, getCategoryKey());
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceCatalogScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_CATALOG_GENERAL;
	}

	@Reference
	protected Language language;

}