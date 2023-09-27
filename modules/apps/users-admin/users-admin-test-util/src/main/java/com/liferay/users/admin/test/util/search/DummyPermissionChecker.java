/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.test.util.search;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class DummyPermissionChecker implements PermissionChecker {

	@Override
	public PermissionChecker clone() {
		return null;
	}

	@Override
	public long getCompanyId() {
		return 0;
	}

	@Override
	public long[] getGuestUserRoleIds() {
		return null;
	}

	@Override
	public long getOwnerRoleId() {
		return 0;
	}

	@Override
	public Map<Object, Object> getPermissionChecksMap() {
		return _permissionChecksMap;
	}

	@Override
	public long[] getRoleIds(long userId, long groupId) {
		return null;
	}

	@Override
	public User getUser() {
		return null;
	}

	@Override
	public UserBag getUserBag() throws Exception {
		return null;
	}

	@Override
	public long getUserId() {
		return 0;
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, long primKey, long ownerId,
		String actionId) {

		return false;
	}

	@Override
	public boolean hasOwnerPermission(
		long companyId, String name, String primKey, long ownerId,
		String actionId) {

		return false;
	}

	@Override
	public boolean hasPermission(
		Group group, String name, long primKey, String actionId) {

		return false;
	}

	@Override
	public boolean hasPermission(
		Group group, String name, String primKey, String actionId) {

		return false;
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId) {

		return false;
	}

	@Override
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return false;
	}

	@Override
	public void init(User user) {
	}

	@Override
	public boolean isCheckGuest() {
		return false;
	}

	@Override
	public boolean isCompanyAdmin() {
		return false;
	}

	@Override
	public boolean isCompanyAdmin(long arg0) {
		return false;
	}

	@Override
	public boolean isContentReviewer(long arg0, long arg1) {
		return false;
	}

	@Override
	public boolean isGroupAdmin(long arg0) {
		return false;
	}

	@Override
	public boolean isGroupMember(long arg0) {
		return false;
	}

	@Override
	public boolean isGroupOwner(long arg0) {
		return false;
	}

	@Override
	public boolean isOmniadmin() {
		return false;
	}

	@Override
	public boolean isOrganizationAdmin(long arg0) {
		return false;
	}

	@Override
	public boolean isOrganizationOwner(long arg0) {
		return false;
	}

	@Override
	public boolean isSignedIn() {
		return false;
	}

	private final Map<Object, Object> _permissionChecksMap = new HashMap<>();

}