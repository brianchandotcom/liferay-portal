/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.shipping.engine.internal.constants.FunctionCommerceShippingEngineScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "screen.navigation.category.order:Integer=20",
	service = ScreenNavigationCategory.class
)
public class FunctionCommerceShippingMethodConfigurationScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return FunctionCommerceShippingEngineScreenNavigationConstants.
			CATEGORY_KEY_FUNCTION_COMMERCE_SHIPPING_METHOD_CONFIGURATION;
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(locale, "configuration");
	}

	@Override
	public String getScreenNavigationKey() {
		return FunctionCommerceShippingEngineScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_SHIPPING_METHOD;
	}

	@Reference
	protected Language language;

}