/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fedex.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.shipping.engine.fedex.internal.constants.CommerceShippingConfigurationScreenNavigationConstants;
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
public class CommerceShippingMethodFedExConfigurationScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceShippingConfigurationScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_FEDEX_CONFIGURATION;
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(locale, "configuration");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceShippingConfigurationScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_SHIPPING_METHOD;
	}

	@Reference
	protected Language language;

}