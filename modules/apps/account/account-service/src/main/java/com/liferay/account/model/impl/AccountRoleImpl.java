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

package com.liferay.account.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AccountRoleImpl extends AccountRoleBaseImpl {

	public AccountRoleImpl() {
	}

	@Override
	public Role getRole() {
		try {
			return RoleLocalServiceUtil.getRole(getRoleId());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			throw new RuntimeException(pe);
		}
	}

	@Override
	public String getRoleName() throws PortalException {
		Role role = getRole();

		if (role == null) {
			return StringPool.BLANK;
		}

		return role.getName();
	}

	private static Log _log = LogFactoryUtil.getLog(AccountRoleImpl.class);

}