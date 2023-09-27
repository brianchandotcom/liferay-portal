/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.wrapper;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.security.permission.contributor.RoleContributor;

import java.util.Map;

/**
 * @author Preston Crary
 */
public class PermissionCheckerWrapper implements PermissionChecker {

	public PermissionCheckerWrapper(PermissionChecker permissionChecker) {
		_permissionChecker = permissionChecker;
	}

	@Override
	public final PermissionChecker clone() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getCompanyId() {
		return _permissionChecker.getCompanyId();
	}

	@Override
	public long[] getGuestUserRoleIds() {
		return _permissionChecker.getGuestUserRoleIds();
	}

	@Override
	public long getOwnerRoleId() {
		return _permissionChecker.getOwnerRoleId();
	}

	@Override
	public Map<Object, Object> getPermissionChecksMap() {
		return _permissionChecker.getPermissionChecksMap();
	}

	@Override
	public long[] getRoleIds(long userId, long groupId) {
		return _permissionChecker.getRoleIds(userId, groupId);
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
		Group group, String name, long primKey, String actionId) {

		return _permissionChecker.hasPermission(group, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		return _permissionChecker.hasPermission(group, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return _permissionChecker.hasPermission(
			groupId, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return _permissionChecker.hasPermission(
			groupId, name, primKey, actionId);
	}

	@Override
	public final void init(User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void init(User user, RoleContributor[] roleContributors) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCheckGuest() {
		return _permissionChecker.isCheckGuest();
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
		return _permissionChecker.isContentReviewer(companyId, groupId);
	}

	@Override
	public boolean isGroupAdmin(long groupId) {
		return _permissionChecker.isGroupAdmin(groupId);
	}

	@Override
	public boolean isGroupMember(long groupId) {
		return _permissionChecker.isGroupMember(groupId);
	}

	@Override
	public boolean isGroupOwner(long groupId) {
		return _permissionChecker.isGroupOwner(groupId);
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

	private final PermissionChecker _permissionChecker;

}