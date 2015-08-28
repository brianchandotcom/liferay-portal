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

package com.liferay.product.navigation.site.administration.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortalPermissionUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.SITES_ALL_SITES,
		"service.ranking:Integer=100"
	},
	service = PanelCategory.class
)
public class AdministrationPanelCategory extends BasePanelCategory {

	@Override
	public String getIconCssClass() {
		return "icon-sitemap";
	}

	@Override
	public String getKey() {
		return PanelCategoryKeys.SITES_ADMINISTRATION_SITES;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "administration");
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.VIEW_CONTROL_PANEL)) {

			return true;
		}

		return false;
	}

}