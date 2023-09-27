/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.pricing.web.internal.constants.CommercePricingClassScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	property = "screen.navigation.category.order:Integer=40",
	service = ScreenNavigationCategory.class
)
public class CommercePricingClassDiscountsScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommercePricingClassScreenNavigationConstants.
			CATEGORY_KEY_DISCOUNTS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return language.get(
			resourceBundle,
			CommercePricingClassScreenNavigationConstants.
				CATEGORY_KEY_DISCOUNTS);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommercePricingClassScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_PRICING_CLASS_GENERAL;
	}

	@Reference
	protected Language language;

}