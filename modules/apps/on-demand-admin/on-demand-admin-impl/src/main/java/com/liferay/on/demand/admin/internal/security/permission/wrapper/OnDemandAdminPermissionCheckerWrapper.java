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

package com.liferay.on.demand.admin.internal.security.permission.wrapper;

import com.liferay.on.demand.admin.manager.OnDemandAdminManager;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.wrapper.PermissionCheckerWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Supplier;

/**
 * @author Pei-Jung Lan
 */
public class OnDemandAdminPermissionCheckerWrapper
	extends PermissionCheckerWrapper {

	public OnDemandAdminPermissionCheckerWrapper(
		PermissionChecker permissionChecker,
		OnDemandAdminManager onDemandAdminManager,
		UserLocalService userLocalService) {

		super(permissionChecker);

		_onDemandAdminManager = onDemandAdminManager;
		_userLocalService = userLocalService;
	}

	@Override
	public boolean hasPermission(
		Group group, String name, long primKey, String actionId) {

		return _hasPermission(
			name, primKey,
			() -> super.hasPermission(group, name, primKey, actionId));
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		return _hasPermission(
			name, GetterUtil.getLong(primKey),
			() -> super.hasPermission(group, name, primKey, actionId));
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return _hasPermission(
			name, primKey,
			() -> super.hasPermission(groupId, name, primKey, actionId));
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return _hasPermission(
			name, GetterUtil.getLong(primKey),
			() -> super.hasPermission(groupId, name, primKey, actionId));
	}

	private boolean _hasPermission(
		String name, long primKey, Supplier<Boolean> hasPermissionSupplier) {

		if (!StringUtil.equals(name, User.class.getName())) {
			return hasPermissionSupplier.get();
		}

		User user = _userLocalService.fetchUser(primKey);

		if ((user != null) && _onDemandAdminManager.isOnDemandAdminUser(user)) {
			return false;
		}

		return hasPermissionSupplier.get();
	}

	private final OnDemandAdminManager _onDemandAdminManager;
	private final UserLocalService _userLocalService;

}