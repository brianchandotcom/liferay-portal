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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.service.permission.LayoutSetPrototypePermissionUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionCheckerBagImpl
	extends UserPermissionCheckerBagImpl implements PermissionCheckerBag {

	public PermissionCheckerBagImpl(long userId, Set<Role> roles) {
		super(
			userId, Collections.<Group>emptySet(),
			Collections.<Organization>emptyList(),
			Collections.<Group>emptySet(), Collections.<Group>emptyList(),
			roles);
	}

	public PermissionCheckerBagImpl(
		UserPermissionCheckerBag userPermissionCheckerBag, Set<Role> roles) {

		super(userPermissionCheckerBag, roles);
	}

	@Override
	public long[] getRoleIds() {
		if (_roleIds == null) {
			List<Role> roles = ListUtil.fromCollection(getRoles());

			long[] roleIds = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				Role role = roles.get(i);

				roleIds[i] = role.getRoleId();
			}

			Arrays.sort(roleIds);

			_roleIds = roleIds;
		}

		return _roleIds;
	}

	@Override
	public boolean isGroupOwner(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			permissionChecker.getUserId(), group.getGroupId(),
			RoleConstants.SITE_OWNER);

		try {
			if (value == null) {
				value = isGroupOwnerImpl(permissionChecker, group);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), group.getGroupId(), RoleConstants.SITE_OWNER,
					value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				getUserId(), group.getGroupId(), RoleConstants.SITE_OWNER);

			throw e;
		}

		return value;
	}

	protected boolean isGroupOwnerImpl(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (group.isSite()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					getUserId(), group.getGroupId(), RoleConstants.SITE_OWNER,
					true)) {

				return true;
			}
		}

		if (group.isLayoutPrototype()) {
			if (LayoutPrototypePermissionUtil.contains(
					permissionChecker, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isLayoutSetPrototype()) {
			if (LayoutSetPrototypePermissionUtil.contains(
					permissionChecker, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			while (organizationId !=
						OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				long organizationGroupId = organization.getGroupId();

				if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						getUserId(), organizationGroupId,
						RoleConstants.ORGANIZATION_OWNER, true)) {

					return true;
				}

				organizationId = organization.getParentOrganizationId();
			}
		}
		else if (group.isUser()) {
			long groupUserId = group.getClassPK();

			if (getUserId() == groupUserId) {
				return true;
			}
		}

		return false;
	}

	private long[] _roleIds;

}