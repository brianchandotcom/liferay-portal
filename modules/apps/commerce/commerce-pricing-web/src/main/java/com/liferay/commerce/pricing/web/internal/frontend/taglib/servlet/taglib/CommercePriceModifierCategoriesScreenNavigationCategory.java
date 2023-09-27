/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.pricing.web.internal.constants.CommercePriceListScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "screen.navigation.category.order:Integer=20",
	service = ScreenNavigationCategory.class
)
public class CommercePriceModifierCategoriesScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommercePriceListScreenNavigationConstants.
			CATEGORY_KEY_CATEGORIES;
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(
			locale,
			CommercePriceListScreenNavigationConstants.CATEGORY_KEY_CATEGORIES);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommercePriceListScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_PRICE_MODIFIER_GENERAL;
	}

	@Reference
	protected Language language;

}