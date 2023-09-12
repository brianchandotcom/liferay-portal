/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.applications.menu.web.internal.util;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.product.navigation.applications.menu.configuration.ApplicationsMenuInstanceConfiguration;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class ApplicationsMenuUtil {

	public static boolean hasChildPanelApps(
		PanelAppRegistry panelAppRegistry,
		PanelCategoryRegistry panelCategoryRegistry,
		ThemeDisplay themeDisplay) {

		List<PanelCategory> applicationsMenuPanelCategories =
			panelCategoryRegistry.getChildPanelCategories(
				PanelCategoryKeys.APPLICATIONS_MENU,
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup());

		for (PanelCategory panelCategory : applicationsMenuPanelCategories) {
			List<PanelCategory> childPanelCategories =
				panelCategoryRegistry.getChildPanelCategories(
					panelCategory.getKey(), themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroup());

			for (PanelCategory childPanelCategory : childPanelCategories) {
				List<PanelApp> panelApps = panelAppRegistry.getPanelApps(
					childPanelCategory.getKey(),
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroup());

				if (!panelApps.isEmpty()) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isEnableApplicationsMenu(
		long companyId, ConfigurationProvider configurationProvider) {

		try {
			ApplicationsMenuInstanceConfiguration
				applicationsMenuInstanceConfiguration =
					configurationProvider.getCompanyConfiguration(
						ApplicationsMenuInstanceConfiguration.class, companyId);

			if (applicationsMenuInstanceConfiguration.
					enableApplicationsMenu()) {

				return true;
			}
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get applications menu instance configuration",
					configurationException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ApplicationsMenuUtil.class);

}