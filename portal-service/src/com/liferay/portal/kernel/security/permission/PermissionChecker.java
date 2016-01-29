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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.User;

import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface PermissionChecker extends Cloneable {

	public static final long[] DEFAULT_ROLE_IDS = {};

	public PermissionChecker clone();

	/**
	 * Returns the primary key of the user's company.
	 *
	 * @return the primary key of the user's company
	 */
	public long getCompanyId();

	public List<Long> getOwnerResourceBlockIds(
		long companyId, long groupId, String name, String actionId);

	/**
	 * Returns the primary key of the owner role. This role is automatically
	 * given to the creator of a resource.
	 *
	 * @return the primary key of the owner role
	 */
	public long getOwnerRoleId();

	public List<Long> getResourceBlockIds(
		long companyId, long groupId, long userId, String name,
		String actionId);

	/**
	 * Returns the primary keys of the roles the user has within the group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the primary keys of the roles the user has within the group
	 */
	public long[] getRoleIds(long userId, long groupId);

	public User getUser();

	public UserBag getUserBag() throws Exception;

	/**
	 * Returns the primary key of the user.
	 *
	 * @return the primary key of the user
	 */
	public long getUserId();

	/**
	 * Returns <code>true</code> if the user is the owner of the resource and
	 * has permission to perform the action.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @param  ownerId the primary key of the resource's owner
	 * @param  actionId the action ID
	 * @return <code>true</code> if the user is the owner of the resource and
	 *         has permission to perform the action; <code>false</code>
	 *         otherwise
	 */
	public boolean hasOwnerPermission(
		long companyId, String name, long primKey, long ownerId,
		String actionId);

	/**
	 * Returns <code>true</code> if the user is the owner of the resource and
	 * has permission to perform the action.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @param  ownerId the primary key of the resource's owner
	 * @param  actionId the action ID
	 * @return <code>true</code> if the user is the owner of the resource and
	 *         has permission to perform the action; <code>false</code>
	 *         otherwise
	 */
	public boolean hasOwnerPermission(
		long companyId, String name, String primKey, long ownerId,
		String actionId);

	/**
	 * Returns <code>true</code> if the user has permission to perform the
	 * action on the resource.
	 *
	 * @param  groupId the primary key of the group containing the resource
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @param  actionId the action ID
	 * @return <code>true</code> if the user has permission to perform the
	 *         action on the resource; <code>false</code> otherwise
	 */
	public boolean hasPermission(
		long groupId, String name, long primKey, String actionId);

	/**
	 * Returns <code>true</code> if the user has permission to perform the
	 * action on the resource.
	 *
	 * @param  groupId the primary key of the group containing the resource
	 * @param  name the resource's name, which can be either a class name or a
	 *         portlet ID
	 * @param  primKey the primary key of the resource
	 * @param  actionId the action ID
	 * @return <code>true</code> if the user has permission to perform the
	 *         action on the resource; <code>false</code> otherwise
	 */
	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId);

	/**
	 * Initializes this permission checker.
	 *
	 * @param user the current user
	 */
	public void init(User user);

	/**
	 * Returns <code>true</code> if guest permissions will be used in permission
	 * checks.
	 *
	 * @return <code>true</code> if guest permissions will be used in permission
	 *         checks; <code>false</code> otherwise
	 */
	public boolean isCheckGuest();

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupAdmin(long)}
	 */
	@Deprecated
	public boolean isCommunityAdmin(long groupId);

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupOwner(long)}
	 */
	@Deprecated
	public boolean isCommunityOwner(long groupId);

	/**
	 * Returns <code>true</code> if the user is an administrator of their
	 * company.
	 *
	 * @return <code>true</code> if the user is an administrator of their
	 *         company; <code>false</code> otherwise
	 */
	public boolean isCompanyAdmin();

	/**
	 * Returns <code>true</code> if the user is an administrator of the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return <code>true</code> if the user is an administrator of the company;
	 *         <code>false</code> otherwise
	 */
	public boolean isCompanyAdmin(long companyId);

	/**
	 * Returns <code>true</code> if the user is a content reviewer or has
	 * sufficient permissions to review content (i.e. the user is a company or
	 * group administrator).
	 *
	 * @param  companyId the primary key of the company
	 * @param  groupId the primary key of the group
	 * @return <code>true</code> if the user is a reviewer or has sufficient
	 *         permissions to review content; <code>false</code> otherwise
	 */
	public boolean isContentReviewer(long companyId, long groupId);

	/**
	 * Returns <code>true</code> if the user is an administrator of the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return <code>true</code> if the user is an administrator of the group;
	 *         <code>false</code> otherwise
	 */
	public boolean isGroupAdmin(long groupId);

	/**
	 * Returns <code>true</code> if the user is a member of the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return <code>true</code> if the user is a member of the group;
	 *         <code>false</code> otherwise
	 */
	public boolean isGroupMember(long groupId);

	/**
	 * Returns <code>true</code> if the user is the owner of the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return <code>true</code> if the user is the owner of the group;
	 *         <code>false</code> otherwise
	 */
	public boolean isGroupOwner(long groupId);

	/**
	 * Returns <code>true</code> if the user is a universal administrator.
	 *
	 * @return <code>true</code> if the user is a universal administrator;
	 *         <code>false</code> otherwise
	 * @see    com.liferay.portlet.admin.util.OmniadminUtil
	 */
	public boolean isOmniadmin();

	/**
	 * Returns <code>true</code> if the user is an administrator of the
	 * organization.
	 *
	 * @param  organizationId the primary key of the organization
	 * @return <code>true</code> if the user is an administrator of the
	 *         organization; <code>false</code> otherwise
	 */
	public boolean isOrganizationAdmin(long organizationId);

	/**
	 * Returns <code>true</code> if the user is an owner of the organization.
	 *
	 * @param  organizationId the primary key of the organization
	 * @return <code>true</code> if the user is an owner of the organization;
	 *         <code>false</code> otherwise
	 */
	public boolean isOrganizationOwner(long organizationId);

	/**
	 * Returns <code>true</code> if the user is signed in.
	 *
	 * @return <code>true</code> if the user is signed in; <code>false</code>
	 *         otherwise
	 */
	public boolean isSignedIn();

	/**
	 * @deprecated As of 6.2.0, does nothing
	 */
	@Deprecated
	public void resetValues();

	/**
	 * @deprecated As of 6.2.0, does nothing
	 */
	@Deprecated
	public void setValues(PortletRequest portletRequest);

}