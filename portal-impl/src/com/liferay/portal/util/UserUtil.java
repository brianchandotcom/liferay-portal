/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Stefano Motta
 */
public class UserUtil {

	public static List<Group> getAllUserGroups(User user)
		throws PortalException {

		return ListUtil.concat(
			getInheritedGroups(user), getInheritedSiteGroups(user),
			getOrganizationsGroups(user), getSiteGroups(user),
			user.getGroups());
	}

	public static List<Role> getAllUserRoles(User user) throws PortalException {
		return ListUtil.concat(
			getInheritedRoles(user), getInheritedSiteRoles(user),
			getOrganizationsRoles(user), getSiteRoles(user), user.getRoles());
	}

	public static List<Group> getInheritedGroups(User user)
		throws PortalException {

		return GroupLocalServiceUtil.getUserGroupsGroups(user.getUserGroups());
	}

	public static List<Role> getInheritedRoles(User user)
		throws PortalException {

		Set<Role> roles = new HashSet<>();

		for (Group group :
				ListUtil.filter(
					getAllUserGroups(user),
					group -> RoleLocalServiceUtil.hasGroupRoles(
						group.getGroupId()))) {

			roles.addAll(
				RoleLocalServiceUtil.getGroupRoles(group.getGroupId()));
		}

		return ListUtil.fromCollection(roles);
	}

	public static List<Group> getInheritedSiteGroups(User user)
		throws PortalException {

		Set<Group> groups = new HashSet<>();

		groups.addAll(
			GroupLocalServiceUtil.getUserGroupsRelatedGroups(
				user.getUserGroups()));
		groups.addAll(_getOrganizationRelatedGroups(user));

		return ListUtil.fromCollection(groups);
	}

	public static List<Role> getInheritedSiteRoles(User user) {
		return TransformUtil.transform(
			UserGroupGroupRoleLocalServiceUtil.getUserGroupGroupRolesByUser(
				user.getUserId()),
			UserGroupGroupRole::getRole);
	}

	public static List<Group> getOrganizationsGroups(User user)
		throws PortalException {

		return GroupLocalServiceUtil.getOrganizationsGroups(
			_getOrganizations(user));
	}

	public static List<Role> getOrganizationsRoles(User user)
		throws PortalException {

		return TransformUtil.transform(
			ListUtil.filter(
				getUserGroupRoles(user), UserUtil::isOrganizationRole),
			UserGroupRole::getRole);
	}

	public static List<Group> getSiteGroups(User user) throws PortalException {
		return TransformUtil.transform(
			ListUtil.filter(getUserGroupRoles(user), UserUtil::isSiteRole),
			UserGroupRole::getGroup);
	}

	public static List<Role> getSiteRoles(User user) throws PortalException {
		return TransformUtil.transform(
			ListUtil.filter(getUserGroupRoles(user), UserUtil::isSiteRole),
			UserGroupRole::getRole);
	}

	public static List<UserGroupRole> getUserGroupRoles(User user)
		throws PortalException {

		return UserGroupRoleLocalServiceUtil.getUserGroupRoles(
			user.getUserId());
	}

	public static boolean isOrganizationRole(UserGroupRole userGroupRole) {
		Role role = RoleLocalServiceUtil.fetchRole(userGroupRole.getRoleId());

		if ((role != null) &&
			(role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

			return true;
		}

		return false;
	}

	public static boolean isSiteRole(UserGroupRole userGroupRole) {
		try {
			Group group = userGroupRole.getGroup();
			Role role = userGroupRole.getRole();

			if ((group != null) && group.isSite() && (role != null) &&
				(role.getType() == RoleConstants.TYPE_SITE)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

		return false;
	}

	private static List<Group> _getOrganizationRelatedGroups(User user)
		throws PortalException {

		List<Organization> organizations = _getOrganizations(user);

		if (organizations.isEmpty()) {
			return Collections.emptyList();
		}

		return GroupLocalServiceUtil.getOrganizationsRelatedGroups(
			organizations);
	}

	private static List<Organization> _getOrganizations(User user)
		throws PortalException {

		List<Organization> organizations = user.getOrganizations();

		if (!PropsValues.ORGANIZATIONS_MEMBERSHIP_STRICT) {
			organizations.addAll(_getParentOrganizations(organizations));
		}

		return organizations;
	}

	private static List<Organization> _getParentOrganizations(
			List<Organization> organizations)
		throws PortalException {

		List<Organization> parentOrganizations = new ArrayList<>();

		for (Organization organization : organizations) {
			Organization parentOrganization =
				organization.getParentOrganization();

			if ((parentOrganization != null) &&
				!organizations.contains(parentOrganization)) {

				parentOrganizations.add(parentOrganization);
			}
		}

		return parentOrganizations;
	}

	private static final Log _log = LogFactoryUtil.getLog(UserUtil.class);

}