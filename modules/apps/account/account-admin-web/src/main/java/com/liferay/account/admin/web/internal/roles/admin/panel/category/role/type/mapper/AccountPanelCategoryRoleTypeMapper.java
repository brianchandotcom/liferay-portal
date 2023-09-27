/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.roles.admin.panel.category.role.type.mapper;

import com.liferay.account.constants.AccountPanelCategoryKeys;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.portal.kernel.model.Role;
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
	public String[] getExcludedPanelAppKeys(Role role) {
		return new String[] {AccountPortletKeys.ACCOUNT_GROUPS_ADMIN};
	}

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