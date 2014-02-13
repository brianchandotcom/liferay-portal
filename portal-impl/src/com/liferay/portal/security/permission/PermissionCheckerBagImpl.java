/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionCheckerBagImpl
	extends UserPermissionCheckerBagImpl implements PermissionCheckerBag {

	public PermissionCheckerBagImpl() {
	}

	public PermissionCheckerBagImpl(
		long userId, List<Group> userGroups, List<Organization> userOrgs,
		List<Group> userOrgGroups, List<Group> userUserGroupGroups,
		List<Role> roles) {

		super(userId, userGroups, userOrgs, userOrgGroups, userUserGroupGroups);

		_roles = roles;
	}

	public PermissionCheckerBagImpl(
		UserPermissionCheckerBag userPermissionCheckerBag, List<Role> roles) {

		super(userPermissionCheckerBag);

		_roles = roles;
	}

	@Override
	public long[] getRoleIds() {
		if (_roleIds == null) {
			List<Role> roles = getRoles();

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
	public List<Role> getRoles() {
		return _roles;
	}

	/**
	 * @deprecated As of 6.1.0, renamed to {@link
	 *             #isGroupAdmin(PermissionChecker, Group)}
	 */
	@Deprecated
	@Override
	public boolean isCommunityAdmin(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		return isGroupAdmin(permissionChecker, group);
	}

	/**
	 * @deprecated As of 6.1.0, renamed to {@link
	 *             #isGroupOwner(PermissionChecker, Group)}
	 */
	@Deprecated
	@Override
	public boolean isCommunityOwner(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		return isGroupOwner(permissionChecker, group);
	}

	@Override
	public boolean isContentReviewer(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		Boolean value = _contentReviewers.get(group.getCompanyId());

		if (value == null) {
			value = Boolean.valueOf(
				isContentReviewerImpl(permissionChecker, group));

			_contentReviewers.put(group.getCompanyId(), value);
		}

		return value.booleanValue();
	}

	@Override
	public boolean isGroupAdmin(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		Boolean value = _groupAdmins.get(group.getGroupId());

		if (value == null) {
			value = Boolean.valueOf(isGroupAdminImpl(permissionChecker, group));

			_groupAdmins.put(group.getGroupId(), value);
		}

		return value.booleanValue();
	}

	@Override
	public boolean isGroupMember(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		for (Role role : _roles) {
			String roleName = role.getName();

			if (roleName.equals(RoleConstants.SITE_MEMBER)) {
				return true;
			}
		}

		List<Group> userGroups = getUserGroups();

		if (userGroups.contains(group)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isGroupOwner(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		Boolean value = _groupOwners.get(group.getGroupId());

		if (value == null) {
			value = Boolean.valueOf(isGroupOwnerImpl(permissionChecker, group));

			_groupOwners.put(group.getGroupId(), value);
		}

		return value.booleanValue();
	}

	@Override
	public boolean isOrganizationAdmin(
			PermissionChecker permissionChecker, Organization organization)
		throws Exception {

		Boolean value = _organizationAdmins.get(
			organization.getOrganizationId());

		if (value == null) {
			value = Boolean.valueOf(
				isOrganizationAdminImpl(permissionChecker, organization));

			_organizationAdmins.put(organization.getOrganizationId(), value);
		}

		return value.booleanValue();
	}

	@Override
	public boolean isOrganizationOwner(
			PermissionChecker permissionChecker, Organization organization)
		throws Exception {

		Boolean value = _organizationOwners.get(
			organization.getOrganizationId());

		if (value == null) {
			value = Boolean.valueOf(
				isOrganizationOwnerImpl(permissionChecker, organization));

			_organizationOwners.put(organization.getOrganizationId(), value);
		}

		return value.booleanValue();
	}

	protected boolean isContentReviewerImpl(
			PermissionChecker permissionChecker, Group group)
		throws PortalException, SystemException {

		if (permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupAdmin(group.getGroupId())) {

			return true;
		}

		long userId = getUserId();

		if (RoleLocalServiceUtil.hasUserRole(
				userId, group.getCompanyId(),
				RoleConstants.PORTAL_CONTENT_REVIEWER, true)) {

			return true;
		}

		if (group.isSite()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, group.getGroupId(),
					RoleConstants.SITE_CONTENT_REVIEWER, true)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isGroupAdminImpl(
			PermissionChecker permissionChecker, Group group)
		throws PortalException, SystemException {

		if (group.isLayout()) {
			long parentGroupId = group.getParentGroupId();

			if (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
				return false;
			}

			group = GroupLocalServiceUtil.getGroup(parentGroupId);
		}

		long userId = getUserId();

		if (group.isSite()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, group.getGroupId(),
					RoleConstants.SITE_ADMINISTRATOR, true) ||
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, group.getGroupId(), RoleConstants.SITE_OWNER,
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
						userId, organizationGroupId,
						RoleConstants.ORGANIZATION_ADMINISTRATOR, true) ||
					UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						userId, organizationGroupId,
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
		throws PortalException, SystemException {

		long userId = getUserId();

		if (group.isSite()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, group.getGroupId(), RoleConstants.SITE_OWNER,
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
						userId, organizationGroupId,
						RoleConstants.ORGANIZATION_OWNER, true)) {

					return true;
				}

				organizationId = organization.getParentOrganizationId();
			}
		}
		else if (group.isUser()) {
			long groupUserId = group.getClassPK();

			if (userId == groupUserId) {
				return true;
			}
		}

		return false;
	}

	protected boolean isOrganizationAdminImpl(
			PermissionChecker permissionChecker, Organization organization)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

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

	private Map<Long, Boolean> _contentReviewers = new HashMap<Long, Boolean>();
	private Map<Long, Boolean> _groupAdmins = new HashMap<Long, Boolean>();
	private Map<Long, Boolean> _groupOwners = new HashMap<Long, Boolean>();
	private Map<Long, Boolean> _organizationAdmins =
		new HashMap<Long, Boolean>();
	private Map<Long, Boolean> _organizationOwners =
		new HashMap<Long, Boolean>();
	private long[] _roleIds;
	private List<Role> _roles;

}