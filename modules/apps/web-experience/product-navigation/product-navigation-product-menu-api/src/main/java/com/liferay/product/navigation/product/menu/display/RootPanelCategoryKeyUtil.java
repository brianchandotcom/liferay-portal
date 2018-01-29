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

package com.liferay.product.navigation.product.menu.display;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import java.util.List;
import javax.portlet.PortletRequest;

/**
 * @author Shuyang Zhou
 */
public class RootPanelCategoryKeyUtil {

	public static String getRootPanelCategoryKey(
		PortletRequest portletRequest) {

		PanelAppRegistry panelAppRegistry =
			(PanelAppRegistry)portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_APP_REGISTRY);
		PanelCategoryRegistry panelCategoryRegistry =
			(PanelCategoryRegistry)portletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String rootPanelCategoryKey = StringPool.BLANK;

		List<PanelCategory> childPanelCategories =
			panelCategoryRegistry.getChildPanelCategories(
				PanelCategoryKeys.ROOT, themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup());

		if (!childPanelCategories.isEmpty()) {
			PanelCategory lastChildPanelCategory = childPanelCategories.get(
				childPanelCategories.size() - 1);

			rootPanelCategoryKey = lastChildPanelCategory.getKey();

			if (Validator.isNotNull(themeDisplay.getPpid())) {
				PanelCategoryHelper panelCategoryHelper =
					new PanelCategoryHelper(
						panelAppRegistry, panelCategoryRegistry);

				for (PanelCategory panelCategory :
						panelCategoryRegistry.getChildPanelCategories(
							PanelCategoryKeys.ROOT)) {

					if (panelCategoryHelper.containsPortlet(
							themeDisplay.getPpid(), panelCategory.getKey(),
							themeDisplay.getPermissionChecker(),
							themeDisplay.getScopeGroup())) {

						rootPanelCategoryKey = panelCategory.getKey();

						return rootPanelCategoryKey;
					}
				}
			}
		}

		return rootPanelCategoryKey;
	}

}