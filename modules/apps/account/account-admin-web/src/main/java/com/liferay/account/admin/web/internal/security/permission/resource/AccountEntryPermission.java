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

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
@Component(service = {})
public class AccountEntryPermission {

	public static boolean contains(
		PermissionChecker permissionChecker, AccountEntry accountEntry,
		String actionId) {

		try {
			return _accountEntryModelResourcePermission.contains(
				permissionChecker, accountEntry, actionId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long accountEntryId,
		String actionId) {

		try {
			return _accountEntryModelResourcePermission.contains(
				permissionChecker, accountEntryId, actionId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<AccountEntry> modelResourcePermission) {

		_accountEntryModelResourcePermission = modelResourcePermission;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryPermission.class);

	private static ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

}