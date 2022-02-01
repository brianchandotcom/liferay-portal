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

package com.liferay.account.admin.web.internal.application.list;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"account.control.panel.category.wrapper=true",
		"panel.category.key=" + PanelCategoryKeys.ROOT,
		"panel.category.order:Integer=100"
	},
	service = PanelCategory.class
)
public class ControlPanelCategoryWrapper extends BasePanelCategory {

	@Override
	public String getKey() {
		return _rootControlPanelCategory.getKey();
	}

	@Override
	public String getLabel(Locale locale) {
		return _rootControlPanelCategory.getLabel(locale);
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (_rootControlPanelCategory.isShow(permissionChecker, group)) {
			return false;
		}

		User user = permissionChecker.getUser();

		if (_organizationPermission.contains(
				permissionChecker, user.getOrganizationIds(true),
				AccountActionKeys.MANAGE_ACCOUNTS)) {

			return true;
		}

		return false;
	}

	@Reference
	private OrganizationPermission _organizationPermission;

	@Reference(
		target = "(&(panel.category.key=" + PanelCategoryKeys.ROOT + ")(!(account.control.panel.category.wrapper=*)))"
	)
	private PanelCategory _rootControlPanelCategory;

}