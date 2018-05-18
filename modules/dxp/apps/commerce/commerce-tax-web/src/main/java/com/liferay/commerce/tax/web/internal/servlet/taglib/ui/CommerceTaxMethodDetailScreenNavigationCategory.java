/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.tax.web.internal.servlet.taglib.ui;

import com.liferay.commerce.constants.CommerceTaxScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "screen.navigation.category.order:Integer=10",
	service = ScreenNavigationCategory.class
)
public class CommerceTaxMethodDetailScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceTaxScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_TAX_METHOD_DETAIL;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale,
			CommerceTaxScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_TAX_METHOD_DETAIL);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceTaxScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_TAX_METHOD;
	}

}