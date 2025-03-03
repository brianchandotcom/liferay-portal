/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.tax.engine.internal.constants.FunctionCommerceTaxEngineScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	property = "screen.navigation.category.order:Integer=20",
	service = ScreenNavigationCategory.class
)
public class
	FunctionCommerceTaxMethodTaxCategoryMappingsScreenNavigationCategory
		implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return FunctionCommerceTaxEngineScreenNavigationConstants.
			CATEGORY_KEY_FUNCTION_COMMERCE_TAX_METHOD_TAX_CATEGORY_MAPPINGS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return language.get(resourceBundle, "tax-category-mappings");
	}

	@Override
	public String getScreenNavigationKey() {
		return FunctionCommerceTaxEngineScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_TAX_METHOD;
	}

	@Reference
	protected Language language;

}