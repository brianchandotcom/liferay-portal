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

package com.liferay.portal.security.permission;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerWrapper;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

import java.util.Objects;

/**
 * @author Tomas Polesovsky
 */
public class StagingPermissionCheckerWrapper extends PermissionCheckerWrapper {

	public StagingPermissionCheckerWrapper(
		PermissionChecker permissionChecker) {

		super(permissionChecker);
	}

	@Override
	public PermissionChecker clone() {
		return new StagingPermissionCheckerWrapper(
			delegatePermissionChecker.clone());
	}

	@Override
	public boolean hasPermission(
		Group group, String name, long primKey, String actionId) {

		Group liveGroup = StagingUtil.getLiveGroup(group);

		if ((liveGroup != group) && (primKey == group.getGroupId())) {
			primKey = liveGroup.getGroupId();
		}

		if (_isStagingFolder(name, actionId)) {
			return true;
		}

		return delegatePermissionChecker.hasPermission(
			liveGroup, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		Group liveGroup = StagingUtil.getLiveGroup(group);

		if ((liveGroup != group) &&
			primKey.equals(String.valueOf(group.getGroupId()))) {

			primKey = String.valueOf(liveGroup.getGroupId());
		}

		if (_isStagingFolder(name, actionId)) {
			return true;
		}

		return delegatePermissionChecker.hasPermission(
			liveGroup, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return delegatePermissionChecker.hasPermission(
			GroupLocalServiceUtil.fetchGroup(groupId), name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return delegatePermissionChecker.hasPermission(
			GroupLocalServiceUtil.fetchGroup(groupId), name, primKey, actionId);
	}

	@Override
	public boolean isContentReviewer(long companyId, long groupId) {
		return delegatePermissionChecker.isContentReviewer(
			companyId, StagingUtil.getLiveGroupId(groupId));
	}

	@Override
	public boolean isGroupAdmin(long groupId) {
		return delegatePermissionChecker.isGroupAdmin(
			StagingUtil.getLiveGroupId(groupId));
	}

	@Override
	public boolean isGroupMember(long groupId) {
		return delegatePermissionChecker.isGroupMember(
			StagingUtil.getLiveGroupId(groupId));
	}

	@Override
	public boolean isGroupOwner(long groupId) {
		return delegatePermissionChecker.isGroupOwner(
			StagingUtil.getLiveGroupId(groupId));
	}

	private boolean _isStagingFolder(String name, String actionId) {
		if (ExportImportThreadLocal.isStagingInProcessOnRemoteLive() &&
			actionId.equals("VIEW") &&
			(name.equals(Folder.class.getName()) ||
			 name.equals(DLFolder.class.getName()) ||
			 Objects.equals("com.liferay.document.library", name))) {

			return true;
		}

		return false;
	}

}