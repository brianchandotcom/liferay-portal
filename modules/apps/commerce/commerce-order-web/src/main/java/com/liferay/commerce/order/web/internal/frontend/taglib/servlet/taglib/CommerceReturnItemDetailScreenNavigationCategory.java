/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.order.web.internal.constants.CommerceReturnItemScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "screen.navigation.category.order:Integer=10",
	service = ScreenNavigationCategory.class
)
public class CommerceReturnItemDetailScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CommerceReturnItemScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_RETURN_ITEM_DETAIL;
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(
			locale,
			CommerceReturnItemScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_RETURN_ITEM_DETAIL);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceReturnItemScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_RETURN_ITEM_GENERAL;
	}

	@Reference
	protected Language language;

}