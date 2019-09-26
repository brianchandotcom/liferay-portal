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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author László Csontos
 * @author Preston Crary
 */
public class UserBagImpl implements UserBag {

	public UserBagImpl(
		long userId, long[] userGroupsId, Collection<Organization> userOrgs,
		Collection<Long> userOrgGroups, Collection<UserGroup> userUserGroups,
		long[] userUserGroupGroups, Collection<Role> userRoles) {

		_userId = userId;

		_userGroupIds = userGroupsId;

		Arrays.sort(_userGroupIds);

		_userOrgGroupIds = ArrayUtil.toLongArray(userOrgGroups);

		Arrays.sort(_userOrgGroupIds);

		_userOrgIds = _toSortedLongArray(userOrgs);
		_userRoleIds = _toSortedLongArray(userRoles);

		_userUserGroupGroupsIds = userUserGroupGroups;

		Arrays.sort(_userUserGroupGroupsIds);

		_userUserGroupIds = _toSortedLongArray(userUserGroups);
	}

	public UserBagImpl(
		long userId, long[] userGroupsIds, Collection<Organization> userOrgs,
		Collection<Long> userOrgGroups, Collection<UserGroup> userUserGroups,
		long[] userUserGroupGroups, long[] userRoleIds) {

		_userId = userId;

		_userRoleIds = userRoleIds;

		Arrays.sort(_userRoleIds);

		_userGroupIds = userGroupsIds;

		Arrays.sort(userGroupsIds);

		_userOrgGroupIds = ArrayUtil.toLongArray(userOrgGroups);

		Arrays.sort(_userOrgGroupIds);

		_userOrgIds = _toSortedLongArray(userOrgs);

		_userUserGroupGroupsIds = userUserGroupGroups;

		Arrays.sort(_userUserGroupGroupsIds);

		_userUserGroupIds = _toSortedLongArray(userUserGroups);
	}

	@Override
	public Set<Group> getGroups() throws PortalException {
		Set<Group> groups = new HashSet<>(getUserGroups());

		groups.addAll(getUserOrgGroups());
		groups.addAll(getUserUserGroupGroups());

		return groups;
	}

	@Override
	public long[] getRoleIds() {
		return _userRoleIds.clone();
	}

	@Override
	public List<Role> getRoles() throws PortalException {
		return RoleLocalServiceUtil.getRoles(_userRoleIds);
	}

	@Override
	public long[] getUserGroupIds() {
		return _userGroupIds.clone();
	}

	@Override
	public List<Group> getUserGroups() throws PortalException {
		return GroupLocalServiceUtil.getGroups(_userGroupIds);
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public long[] getUserOrgGroupIds() {
		return _userOrgGroupIds.clone();
	}

	@Override
	public List<Group> getUserOrgGroups() throws PortalException {
		return GroupLocalServiceUtil.getGroups(_userOrgGroupIds);
	}

	@Override
	public long[] getUserOrgIds() {
		return _userOrgIds.clone();
	}

	@Override
	public List<Organization> getUserOrgs() throws PortalException {
		return OrganizationLocalServiceUtil.getOrganizations(_userOrgIds);
	}

	@Override
	public List<Group> getUserUserGroupGroups() throws PortalException {
		return GroupLocalServiceUtil.getGroups(_userUserGroupGroupsIds);
	}

	@Override
	public long[] getUserUserGroupsIds() {
		return _userUserGroupIds;
	}

	@Override
	public boolean hasRole(Role role) {
		return _search(_userRoleIds, role.getRoleId());
	}

	@Override
	public boolean hasUserGroup(Group group) {
		return _search(_userGroupIds, group.getGroupId());
	}

	@Override
	public boolean hasUserOrg(Organization organization) {
		return _search(_userOrgIds, organization.getOrganizationId());
	}

	@Override
	public boolean hasUserOrgGroup(Group group) {
		return _search(_userOrgGroupIds, group.getGroupId());
	}

	private static boolean _search(long[] ids, long id) {
		if (Arrays.binarySearch(ids, id) >= 0) {
			return true;
		}

		return false;
	}

	private static long[] _toSortedLongArray(
		Collection<? extends BaseModel<?>> baseModels) {

		if ((baseModels == null) || baseModels.isEmpty()) {
			return _EMPTY;
		}

		long[] array = new long[baseModels.size()];

		int index = 0;

		for (BaseModel<?> baseModel : baseModels) {
			array[index++] = (long)baseModel.getPrimaryKeyObj();
		}

		Arrays.sort(array);

		return array;
	}

	private static final long[] _EMPTY = {};

	private final long[] _userGroupIds;
	private final long _userId;
	private final long[] _userOrgGroupIds;
	private final long[] _userOrgIds;
	private final long[] _userRoleIds;
	private final long[] _userUserGroupGroupsIds;
	private final long[] _userUserGroupIds;

}