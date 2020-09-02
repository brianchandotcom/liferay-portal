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

package com.liferay.account.admin.web.internal.roles.admin.panel.category.role.type.mapper;

import com.liferay.account.constants.AccountPanelCategoryKeys;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.roles.admin.panel.category.role.type.mapper.PanelCategoryRoleTypeMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(service = PanelCategoryRoleTypeMapper.class)
public class AccountPanelCategoryRoleTypeMapper
	implements PanelCategoryRoleTypeMapper {

	@Override
	public String getPanelCategoryKey() {
		return AccountPanelCategoryKeys.CONTROL_PANEL_ACCOUNT_ENTRIES_ADMIN;
	}

	@Override
	public int[] getRoleTypes() {
		return new int[] {
			RoleConstants.TYPE_ACCOUNT, RoleConstants.TYPE_ORGANIZATION
		};
	}

}