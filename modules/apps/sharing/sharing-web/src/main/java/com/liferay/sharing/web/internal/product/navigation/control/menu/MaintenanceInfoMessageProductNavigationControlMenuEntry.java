/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.web.internal.product.navigation.control.menu;

import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseInfoMessageProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.InfoMessageProductNavigationControlMenuEntryTypeConstants;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.TOOLS,
		"product.navigation.control.menu.entry.order:Integer=250"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class MaintenanceInfoMessageProductNavigationControlMenuEntry
	extends BaseInfoMessageProductNavigationControlMenuEntry {

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return false;
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (Validator.isNull(portletDisplay.getPortletName())) {
			return Objects.equals(
				SharingPortletKeys.SHARED_ASSETS, themeDisplay.getPpid());
		}

		return Objects.equals(
			SharingPortletKeys.SHARED_ASSETS, portletDisplay.getPortletName());
	}

	@Override
	protected String getPortletName() {
		return SharingPortletKeys.SHARED_ASSETS;
	}

	@Override
	protected String getType() {
		return InfoMessageProductNavigationControlMenuEntryTypeConstants.
			MAINTENANCE;
	}

}