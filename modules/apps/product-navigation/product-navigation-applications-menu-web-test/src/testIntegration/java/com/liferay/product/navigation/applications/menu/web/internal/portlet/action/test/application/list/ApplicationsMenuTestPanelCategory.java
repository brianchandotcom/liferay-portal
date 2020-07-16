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
	immediate = true,
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