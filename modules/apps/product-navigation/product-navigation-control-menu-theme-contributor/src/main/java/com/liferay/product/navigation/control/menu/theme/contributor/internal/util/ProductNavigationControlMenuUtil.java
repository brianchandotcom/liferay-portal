/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.product.navigation.control.menu.theme.contributor.internal.util;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuCategory;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.product.navigation.control.menu.util.ProductNavigationControlMenuCategoryRegistry;
import com.liferay.product.navigation.control.menu.util.ProductNavigationControlMenuEntryRegistry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ProductNavigationControlMenuUtil {

	public static boolean isShowControlMenu(
		HttpServletRequest httpServletRequest,
		ProductNavigationControlMenuCategoryRegistry
			productNavigationControlMenuCategoryRegistry,
		ProductNavigationControlMenuEntryRegistry
			productNavigationControlMenuEntryRegistry) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return false;
		}

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.PREVIEW)) {
			return false;
		}

		User user = themeDisplay.getUser();

		if (!themeDisplay.isImpersonated() && !user.isSetupComplete()) {
			return false;
		}

		List<ProductNavigationControlMenuCategory>
			productNavigationControlMenuCategories =
				productNavigationControlMenuCategoryRegistry.
					getProductNavigationControlMenuCategories(
						ProductNavigationControlMenuCategoryKeys.ROOT);

		for (ProductNavigationControlMenuCategory
				productNavigationControlMenuCategory :
					productNavigationControlMenuCategories) {

			List<ProductNavigationControlMenuEntry>
				productNavigationControlMenuEntries =
					productNavigationControlMenuEntryRegistry.
						getProductNavigationControlMenuEntries(
							productNavigationControlMenuCategory,
							httpServletRequest);

			for (ProductNavigationControlMenuEntry
					productNavigationControlMenuEntry :
						productNavigationControlMenuEntries) {

				if (productNavigationControlMenuEntry.isRelevant(
						httpServletRequest)) {

					return true;
				}
			}
		}

		return false;
	}

}