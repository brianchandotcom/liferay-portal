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

package com.liferay.productivity.center.service.panel;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.productivity.center.panel.PanelCategory;
import com.liferay.productivity.center.panel.constants.PanelCategoryKeys;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {"panel.category=" + PanelCategoryKeys.USER_PERSONAL_PANEL},
	service = PanelCategory.class
)
public class MyPanelCategory implements PanelCategory {

	@Override
	public String getIconCssClass() {
		return "icon-user";
	}

	@Override
	public String getKey() {
		return PanelCategoryKeys.MY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "category.my");
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		return true;
	}

}