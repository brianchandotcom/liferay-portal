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

package com.liferay.change.tracking.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=400",
		"panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS_PUBLICATIONS
	},
	service = PanelApp.class
)
public class ChangeListsPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return CTPortletKeys.CHANGE_LISTS;
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group) {
		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				group.getCompanyId(), 0);

		if (ctPreferences == null) {
			return false;
		}

		return true;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

}