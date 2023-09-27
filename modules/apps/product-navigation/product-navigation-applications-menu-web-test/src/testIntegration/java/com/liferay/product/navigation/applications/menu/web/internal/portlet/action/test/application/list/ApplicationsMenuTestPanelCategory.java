/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.applications.menu.web.internal.portlet.action.test.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.product.navigation.applications.menu.web.internal.portlet.action.test.constants.ApplicationsMenuTestPanelCategoryKeys;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS,
		"panel.category.order:Integer=10"
	},
	service = PanelCategory.class
)
public class ApplicationsMenuTestPanelCategory extends BasePanelCategory {

	@Override
	public String getKey() {
		return ApplicationsMenuTestPanelCategoryKeys.
			APPLICATIONS_MENU_TEST_PANEL_CATEGORY;
	}

	@Override
	public String getLabel(Locale locale) {
		return ApplicationsMenuTestPanelCategoryKeys.
			APPLICATIONS_MENU_TEST_PANEL_CATEGORY;
	}

}