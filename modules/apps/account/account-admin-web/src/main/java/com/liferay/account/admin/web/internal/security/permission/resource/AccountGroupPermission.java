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

package com.liferay.account.admin.web.internal.security.permission.resource;

import com.liferay.account.model.AccountGroup;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = {})
public class AccountGroupPermission {

	public static boolean contains(
		PermissionChecker permissionChecker, AccountGroup accountGroup,
		String actionId) {

		try {
			return _accountGroupModelResourcePermission.contains(
				permissionChecker, accountGroup, actionId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long accountGroupId,
		String actionId) {

		try {
			return _accountGroupModelResourcePermission.contains(
				permissionChecker, accountGroupId, actionId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountGroup)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<AccountGroup> modelResourcePermission) {

		_accountGroupModelResourcePermission = modelResourcePermission;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountGroupPermission.class);

	private static ModelResourcePermission<AccountGroup>
		_accountGroupModelResourcePermission;

}