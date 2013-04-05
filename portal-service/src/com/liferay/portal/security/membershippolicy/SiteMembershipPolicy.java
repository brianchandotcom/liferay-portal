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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Site Membership Policy allows to customize the user membership regarding
 * sites and site roles.
 *
 * Site Membership Policies define the sites a user is allowed to be a member
 * of, the sites the user must be a member of, the roles the user can be
 * assigned to and the roles the user must be assigned.
 *
 * @author Sergio González
 * @author Roberto Díaz
 */
public interface SiteMembershipPolicy {

	/**
	 * Check if the users can be added or removed from certain sites.
	 *
	 * @param  userIds the primary key of the users that are added or removed
	 * @param  addGroupIds the primary key of the sites that the users are added
	 *         to. (optionally <code>null</code> if the user is not added to any
	 *         site)
	 * @param  removeGroupIds the primary key of the sites that the users are
	 *         removed from. (optionally <code>null</code> if the user is not
	 *         removed from any site)
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void checkMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException, SystemException;

	/**
	 * Check if certain site roles can be assigned or unassigned to users.
	 *
	 * @param  addUserGroupRoles the user user's group roles that are added
	 * @param  removeUserGroupRoles the user user's group roles that are removed
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the user can be added to the site.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the site
	 * @return <code>true</code> if the user can be added to the site;
	 *         <code>false</code> otherwise
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean isMembershipAllowed(long userId, long groupId)
		throws PortalException, SystemException;

	/**
	 *
	 * @param  permissionChecker
	 * @param  userId
	 * @param  groupId
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId, long groupId)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the site membership for the user is
	 * mandatory.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the site
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean isMembershipRequired(long userId, long groupId)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the site role can be added to the user.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the site
	 * @param  roleId the primary key of the role
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean isRoleAllowed(long userId, long groupId, long roleId)
		throws PortalException, SystemException;

	/**
	 *
	 * @param  permissionChecker
	 * @param  userId
	 * @param  groupId
	 * @param  roleId
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId, long groupId,
			long roleId)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the site role is mandatory for the user.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the site
	 * @param  roleId the primary key of the role
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean isRoleRequired(long userId, long groupId, long roleId)
		throws PortalException, SystemException;

	/**
	 * Performs the membership policy related actions when a user is added or
	 * removed from a site.
	 *
	 * These actions needs to ensure the integrity of the membership policy from
	 * a sites point of view.
	 *
	 * Example of actions:
	 *
	 * 1. Add the users to the children sites of the current site.
	 * 2.
	 *
	 * @param  userIds the primary key of the users that are added or removed
	 * @param  addGroupIds the primary key of the sites that the users are added
	 *         to. (optionally <code>null</code> if the user is not added to any
	 *         site)
	 * @param  removeGroupIds the primary key of the sites that the users are
	 *         removed from. (optionally <code>null</code> if the user is not
	 *         removed from any site)
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void propagateMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException, SystemException;

	/**
	 * Performs the membership policy related actions when a site role is added
	 * or removed from the user.
	 *
	 * These actions need to ensure the integrity of the membership policy from
	 * a site roles point of view.
	 *
	 * Example of actions:
	 *
	 * 1. If the role A is added to a user, role B should be added too.
	 * 2. If the role A is removed from a user, role B should be removed too.
	 *
	 * @param  addUserGroupRoles the user user's group roles that are added
	 * @param  removeUserGroupRoles the user user's group roles that are removed
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException;

	/**
	 * Check the integrity of the membership policy for all the sites of the
	 * portal from a sites and site roles point of view.
	 *
	 * If necessary, perform the actions to fix the issue.
	 *
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void verifyPolicy() throws PortalException, SystemException;

	/**
	 * Check the integrity of the membership policy for a certain site of the
	 * portal from a sites and site roles point of view.
	 *
	 * If necessary, perform the actions to fix the issue.
	 *
	 * @param  group the site
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void verifyPolicy(Group group)
		throws PortalException, SystemException;

	/**
	 * Check the integrity of the membership policy for certain site from a
	 * sites and site roles point of view when adding or updating the site.
	 *
	 * Perform the necessary operations when adding or updating a site.
	 *
	 * @param  group the site
	 * @param  oldGroup
	 * @param  oldAssetCategories
	 * @param  oldAssetTags
	 * @param  oldExpandoAttributes
	 * @param  oldTypeSettingsProperties
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void verifyPolicy(
			Group group, Group oldGroup, List<AssetCategory> oldAssetCategories,
			List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes,
			UnicodeProperties oldTypeSettingsProperties)
		throws PortalException, SystemException;

	/**
	 *
	 * @param  role
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void verifyPolicy(Role role) throws PortalException, SystemException;

	/**
	 *
	 * @param  role
	 * @param  oldRole
	 * @param  oldExpandoAttributes
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException;

}