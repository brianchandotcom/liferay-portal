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

package com.liferay.commerce.application.list.internal.panel;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.commerce.application.list.constants.CommercePanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortalPermission;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU,
		"panel.category.order:Integer=80"
	},
	service = PanelCategory.class
)
public class CommercePanelCategory extends BasePanelCategory {

	@Override
	public String getKey() {
		return CommercePanelCategoryKeys.COMMERCE;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "commerce");
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (_portalPermission.contains(
				permissionChecker, ActionKeys.VIEW_CONTROL_PANEL)) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PortalPermission _portalPermission;

}