/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.applications.menu.web.internal.util;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.util.PanelCategoryRegistryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class ApplicationsMenuUtil {

	public static boolean hasChildPanelApps(
		PanelAppRegistry panelAppRegistry, ThemeDisplay themeDisplay) {

		List<PanelCategory> applicationsMenuPanelCategories =
			PanelCategoryRegistryUtil.getChildPanelCategories(
				PanelCategoryKeys.APPLICATIONS_MENU,
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup());

		for (PanelCategory panelCategory : applicationsMenuPanelCategories) {
			PanelApp panelApp = panelAppRegistry.getFirstAvailablePanelApp(
				panelCategory.getKey(), themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup());

			if (panelApp != null) {
				return true;
			}
		}

		return false;
	}

}