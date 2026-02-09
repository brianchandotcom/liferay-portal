/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.web.internal.product.navigation.control.menu;

import com.liferay.product.navigation.control.menu.BaseInfoMessageProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.InfoMessageProductNavigationControlMenuEntryTypeConstants;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;

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
	protected String getPortletName() {
		return SharingPortletKeys.SHARED_ASSETS;
	}

	@Override
	protected String getType() {
		return InfoMessageProductNavigationControlMenuEntryTypeConstants.
			MAINTENANCE;
	}

}