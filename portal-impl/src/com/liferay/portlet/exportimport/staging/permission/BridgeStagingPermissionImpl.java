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

package com.liferay.portlet.exportimport.staging.permission;

import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author Daniel Kocsis
 */
public class BridgeStagingPermissionImpl implements StagingPermission {

	public BridgeStagingPermissionImpl() {
		this(new DummyStagingPermissionImpl());
	}

	public BridgeStagingPermissionImpl(
		StagingPermission defaultStagingPermission) {

		_defaultStagingPermission = defaultStagingPermission;

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(StagingPermission.class);

		_serviceTracker.open();
	}

	@Override
	public Boolean hasPermission(
		PermissionChecker permissionChecker, Group group, String className,
		long classPK, String portletId, String actionId) {

		return getStagingPermission().hasPermission(
			permissionChecker, group, className, classPK, portletId, actionId);
	}

	@Override
	public Boolean hasPermission(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String portletId, String actionId) {

		return getStagingPermission().hasPermission(
			permissionChecker, groupId, className, classPK, portletId,
			actionId);
	}

	protected StagingPermission getStagingPermission() {
		if (_serviceTracker.isEmpty()) {
			return _defaultStagingPermission;
		}

		return _serviceTracker.getService();
	}

	private final StagingPermission _defaultStagingPermission;
	private final ServiceTracker<StagingPermission, StagingPermission>
		_serviceTracker;

}