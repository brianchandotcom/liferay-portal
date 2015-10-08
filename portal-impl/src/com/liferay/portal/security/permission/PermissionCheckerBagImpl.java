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
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
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
	public boolean isContentReviewer(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			permissionChecker.getUserId(), group.getGroupId(),
			RoleConstants.SITE_CONTENT_REVIEWER);

		try {
			if (value == null) {
				value = isContentReviewerImpl(permissionChecker, group);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), group.getGroupId(),
					RoleConstants.SITE_CONTENT_REVIEWER, value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				permissionChecker.getUserId(), group.getGroupId(),
				RoleConstants.SITE_CONTENT_REVIEWER);

			throw e;
		}

		return value;
	}

	@Override
	public boolean isGroupAdmin(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			permissionChecker.getUserId(), group.getGroupId(),
			RoleConstants.SITE_ADMINISTRATOR);

		try {
			if (value == null) {
				value = isGroupAdminImpl(permissionChecker, group);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), group.getGroupId(),
					RoleConstants.SITE_ADMINISTRATOR, value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				permissionChecker.getUserId(), group.getGroupId(),
				RoleConstants.SITE_ADMINISTRATOR);

			throw e;
		}

		return value;
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

	@Override
	public boolean isOrganizationAdmin(
			PermissionChecker permissionChecker, Organization organization)
		throws Exception {

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			permissionChecker.getUserId(), organization.getOrganizationId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		try {
			if (value == null) {
				value = isOrganizationAdminImpl(
					permissionChecker, organization);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), organization.getOrganizationId(),
					RoleConstants.ORGANIZATION_ADMINISTRATOR, value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				getUserId(), organization.getOrganizationId(),
				RoleConstants.ORGANIZATION_ADMINISTRATOR);

			throw e;
		}

		return value;
	}

	@Override
	public boolean isOrganizationOwner(
			PermissionChecker permissionChecker, Organization organization)
		throws Exception {

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			getUserId(), organization.getOrganizationId(),
			RoleConstants.ORGANIZATION_OWNER);

		try {
			if (value == null) {
				value = isOrganizationOwnerImpl(
					permissionChecker, organization);

				PermissionCacheUtil.putUserPrimaryKeyRole(
					getUserId(), organization.getOrganizationId(),
					RoleConstants.ORGANIZATION_OWNER, value);
			}
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserPrimaryKeyRole(
				getUserId(), organization.getOrganizationId(),
				RoleConstants.ORGANIZATION_OWNER);

			throw e;
		}

		return value;
	}

	protected boolean isContentReviewerImpl(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupAdmin(group.getGroupId())) {

			return true;
		}

		if (RoleLocalServiceUtil.hasUserRole(
				getUserId(), group.getCompanyId(),
				RoleConstants.PORTAL_CONTENT_REVIEWER, true)) {

			return true;
		}

		if (group.isSite()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					getUserId(), group.getGroupId(),
					RoleConstants.SITE_CONTENT_REVIEWER, true)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isGroupAdminImpl(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (group.isLayout()) {
			long parentGroupId = group.getParentGroupId();

			if (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
				return false;
			}

			group = GroupLocalServiceUtil.getGroup(parentGroupId);
		}

		if (group.isSite()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					getUserId(), group.getGroupId(),
					RoleConstants.SITE_ADMINISTRATOR, true) ||
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					getUserId(), group.getGroupId(), RoleConstants.SITE_OWNER,
					true)) {

				return true;
			}
		}

		if (group.isCompany()) {
			if (permissionChecker.isCompanyAdmin()) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isLayoutPrototype()) {
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
						RoleConstants.ORGANIZATION_ADMINISTRATOR, true) ||
					UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						getUserId(), organizationGroupId,
						RoleConstants.ORGANIZATION_OWNER, true)) {

					return true;
				}

				organizationId = organization.getParentOrganizationId();
			}
		}

		return false;
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

	protected boolean isOrganizationAdminImpl(
			PermissionChecker permissionChecker, Organization organization)
		throws PortalException {

		while (organization != null) {
			long organizationGroupId = organization.getGroupId();

			long userId = getUserId();

			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, organizationGroupId,
					RoleConstants.ORGANIZATION_ADMINISTRATOR, true) ||
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, organizationGroupId,
					RoleConstants.ORGANIZATION_OWNER, true)) {

				return true;
			}

			organization = organization.getParentOrganization();
		}

		return false;
	}

	protected boolean isOrganizationOwnerImpl(
			PermissionChecker permissionChecker, Organization organization)
		throws PortalException {

		while (organization != null) {
			long organizationGroupId = organization.getGroupId();

			long userId = getUserId();

			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, organizationGroupId,
					RoleConstants.ORGANIZATION_OWNER, true)) {

				return true;
			}

			organization = organization.getParentOrganization();
		}

		return false;
	}

	private long[] _roleIds;

}