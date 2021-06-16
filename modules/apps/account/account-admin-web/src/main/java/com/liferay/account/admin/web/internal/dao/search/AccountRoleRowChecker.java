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

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountRoleDisplay;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;

import javax.portlet.PortletResponse;

/**
 * @author Pei-Jung Lan
 */
public class AccountRoleRowChecker extends EmptyOnClickRowChecker {

	public AccountRoleRowChecker(PortletResponse portletResponse) {
		super(portletResponse);
	}

	@Override
	public boolean isDisabled(Object object) {
		AccountRoleDisplay accountRoleDisplay = (AccountRoleDisplay)object;

		Role role = accountRoleDisplay.getRole();

		if ((role.getType() == RoleConstants.TYPE_ACCOUNT) &&
			AccountRoleConstants.isSharedRole(role)) {

			return true;
		}

		return false;
	}

}