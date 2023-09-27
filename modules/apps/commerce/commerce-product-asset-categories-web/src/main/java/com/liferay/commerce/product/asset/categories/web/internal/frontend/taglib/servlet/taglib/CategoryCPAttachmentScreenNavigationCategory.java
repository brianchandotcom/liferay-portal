/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.asset.categories.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.product.asset.categories.web.internal.constants.CPAssetCategoriesScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "screen.navigation.category.order:Integer=20",
	service = ScreenNavigationCategory.class
)
public class CategoryCPAttachmentScreenNavigationCategory
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return CPAssetCategoriesScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_IMAGES;
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(
			locale,
			CPAssetCategoriesScreenNavigationConstants.
				CATEGORY_KEY_COMMERCE_IMAGES);
	}

	@Override
	public String getScreenNavigationKey() {
		return CPAssetCategoriesScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_GENERAL;
	}

	@Reference
	protected Language language;

}