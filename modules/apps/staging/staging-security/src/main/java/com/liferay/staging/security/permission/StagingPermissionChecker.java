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

package com.liferay.staging.security.permission;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.model.User;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Tomas Polesovsky
 */
public class StagingPermissionChecker implements PermissionChecker {

	public StagingPermissionChecker(PermissionChecker permissionChecker) {
		_permissionChecker = permissionChecker;
	}

	@Override
	public PermissionChecker clone() {
		return new StagingPermissionChecker(_permissionChecker.clone());
	}

	@Override
	public long getCompanyId() {
		return _permissionChecker.getCompanyId();
	}

	@Override
	public List<Long> getOwnerResourceBlockIds(
		long companyId, long groupId, String name, String actionId) {

		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		return _permissionChecker.getOwnerResourceBlockIds(
			companyId, liveGroupId, name, actionId);
	}

	@Override
	public long getOwnerRoleId() {
		return _permissionChecker.getOwnerRoleId();
	}

	@Override
	public List<Long> getResourceBlockIds(
		long companyId, long groupId, long userId, String name,
		String actionId) {

		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		return _permissionChecker.getResourceBlockIds(
			companyId, liveGroupId, userId, name, actionId);
	}

	@Override
	public long[] getRoleIds(long userId, long groupId) {
		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		return _permissionChecker.getRoleIds(userId, liveGroupId);
	}

	@Override
	public User getUser() {
		return _permissionChecker.getUser();
	}

	@Override
	public UserBag getUserBag() throws Exception {
		return _permissionChecker.getUserBag();
	}

	@Override
	public long getUserId() {
		return _permissionChecker.getUserId();
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, long primKey, long ownerId,
		String actionId) {

		return _permissionChecker.hasOwnerPermission(
			companyId, name, primKey, ownerId, actionId);
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, String primKey, long ownerId,
		String actionId) {

		return _permissionChecker.hasOwnerPermission(
			companyId, name, primKey, ownerId, actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		if (liveGroupId != groupId) {
			if (primKey == groupId) {
				primKey = liveGroupId;
			}
		}

		return _permissionChecker.hasPermission(
			liveGroupId, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		if (liveGroupId != groupId) {
			if (primKey.equals(String.valueOf(groupId))) {
				primKey = String.valueOf(liveGroupId);
			}
		}

		return _permissionChecker.hasPermission(
			liveGroupId, name, primKey, actionId);
	}

	@Override
	public void init(User user) {
		_permissionChecker.init(user);
	}

	@Override
	public boolean isCheckGuest() {
		return _permissionChecker.isCheckGuest();
	}

	@Deprecated
	@Override
	public boolean isCommunityAdmin(long groupId) {
		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		return _permissionChecker.isCommunityAdmin(liveGroupId);
	}

	@Deprecated
	@Override
	public boolean isCommunityOwner(long groupId) {
		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		return _permissionChecker.isCommunityOwner(liveGroupId);
	}

	@Override
	public boolean isCompanyAdmin() {
		return _permissionChecker.isCompanyAdmin();
	}

	@Override
	public boolean isCompanyAdmin(long companyId) {
		return _permissionChecker.isCompanyAdmin(companyId);
	}

	@Override
	public boolean isContentReviewer(long companyId, long groupId) {
		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		return _permissionChecker.isContentReviewer(companyId, liveGroupId);
	}

	@Override
	public boolean isGroupAdmin(long groupId) {
		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		return _permissionChecker.isGroupAdmin(liveGroupId);
	}

	@Override
	public boolean isGroupMember(long groupId) {
		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		return _permissionChecker.isGroupMember(liveGroupId);
	}

	@Override
	public boolean isGroupOwner(long groupId) {
		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		return _permissionChecker.isGroupOwner(liveGroupId);
	}

	@Override
	public boolean isOmniadmin() {
		return _permissionChecker.isOmniadmin();
	}

	@Override
	public boolean isOrganizationAdmin(long organizationId) {
		return _permissionChecker.isOrganizationAdmin(organizationId);
	}

	@Override
	public boolean isOrganizationOwner(long organizationId) {
		return _permissionChecker.isOrganizationOwner(organizationId);
	}

	@Override
	public boolean isSignedIn() {
		return _permissionChecker.isSignedIn();
	}

	@Deprecated
	@Override
	public void resetValues() {
		_permissionChecker.resetValues();
	}

	@Deprecated
	@Override
	public void setValues(PortletRequest portletRequest) {
		_permissionChecker.setValues(portletRequest);
	}

	private final PermissionChecker _permissionChecker;

}