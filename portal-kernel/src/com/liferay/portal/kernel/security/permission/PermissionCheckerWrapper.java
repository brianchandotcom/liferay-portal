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

package com.liferay.portal.kernel.security.permission;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.contributor.RoleContributor;

import java.util.Map;

/**
 * @author Carlos Sierra Andrés
 */
public abstract class PermissionCheckerWrapper implements PermissionChecker {

	@Override
	public abstract PermissionChecker clone();

	@Override
	public long getCompanyId() {
		return delegatePermissionChecker.getCompanyId();
	}

	@Override
	public long[] getGuestUserRoleIds() {
		return delegatePermissionChecker.getGuestUserRoleIds();
	}

	@Override
	public long getOwnerRoleId() {
		return delegatePermissionChecker.getOwnerRoleId();
	}

	@Override
	public Map<Object, Object> getPermissionChecksMap() {
		return delegatePermissionChecker.getPermissionChecksMap();
	}

	@Override
	public long[] getRoleIds(long userId, long groupId) {
		return delegatePermissionChecker.getRoleIds(userId, groupId);
	}

	@Override
	public User getUser() {
		return delegatePermissionChecker.getUser();
	}

	@Override
	public UserBag getUserBag() throws Exception {
		return delegatePermissionChecker.getUserBag();
	}

	@Override
	public long getUserId() {
		return delegatePermissionChecker.getUserId();
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, long primKey, long ownerId,
		String actionId) {

		return delegatePermissionChecker.hasOwnerPermission(
			companyId, name, primKey, ownerId, actionId);
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, String primKey, long ownerId,
		String actionId) {

		return delegatePermissionChecker.hasOwnerPermission(
			companyId, name, primKey, ownerId, actionId);
	}

	@Override
	public boolean hasPermission(
		Group group, String name, long primKey, String actionId) {

		return delegatePermissionChecker.hasPermission(
			group, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		return delegatePermissionChecker.hasPermission(
			group, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return delegatePermissionChecker.hasPermission(
			groupId, name, primKey, actionId);
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return delegatePermissionChecker.hasPermission(
			groupId, name, primKey, actionId);
	}

	@Override
	public final void init(User user) {
		delegatePermissionChecker.init(user);
	}

	@Override
	public final void init(User user, RoleContributor[] roleContributors) {
		delegatePermissionChecker.init(user, roleContributors);
	}

	@Override
	public boolean isCheckGuest() {
		return delegatePermissionChecker.isCheckGuest();
	}

	@Override
	public boolean isCompanyAdmin() {
		return delegatePermissionChecker.isCompanyAdmin();
	}

	@Override
	public boolean isCompanyAdmin(long companyId) {
		return delegatePermissionChecker.isCompanyAdmin(companyId);
	}

	@Override
	public boolean isContentReviewer(long companyId, long groupId) {
		return delegatePermissionChecker.isContentReviewer(companyId, groupId);
	}

	@Override
	public boolean isGroupAdmin(long groupId) {
		return delegatePermissionChecker.isGroupAdmin(groupId);
	}

	@Override
	public boolean isGroupMember(long groupId) {
		return delegatePermissionChecker.isGroupMember(groupId);
	}

	@Override
	public boolean isGroupOwner(long groupId) {
		return delegatePermissionChecker.isGroupOwner(groupId);
	}

	@Override
	public boolean isOmniadmin() {
		return delegatePermissionChecker.isOmniadmin();
	}

	@Override
	public boolean isOrganizationAdmin(long organizationId) {
		return delegatePermissionChecker.isOrganizationAdmin(organizationId);
	}

	@Override
	public boolean isOrganizationOwner(long organizationId) {
		return delegatePermissionChecker.isOrganizationOwner(organizationId);
	}

	@Override
	public boolean isSignedIn() {
		return delegatePermissionChecker.isSignedIn();
	}

	@Override
	public final void setPermissionChecker(
		PermissionChecker permissionChecker) {

		this.permissionChecker = permissionChecker;
		delegatePermissionChecker.setPermissionChecker(permissionChecker);
	}

	protected PermissionCheckerWrapper(
		PermissionChecker delegatePermissionChecker) {

		this.delegatePermissionChecker = delegatePermissionChecker;
	}

	protected PermissionChecker delegatePermissionChecker;
	protected PermissionChecker permissionChecker;

}