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
import com.liferay.portal.model.UserGroup;

import java.io.Serializable;

import java.util.Map;

/**
 * Provides the UserGroup Membership Policy interface, allowing customization
 * of user membership regarding userGroups.
 *
 * <p>
 * UserGroup Membership Policies define the userGroups a user is allowed
 * to be a member of and the userGroups the user must be a member of.
 * </p>
 *
 * <p>
 * An implementation may include any number of policies and actions to enforce
 * those policies. The implementation may include policies and actions like the
 * following:
 * </p>
 *
 * <ul>
 * <li>
 * If a user doesn't have the custom attribute A, he cannot be assigned to
 * userGroup B.
 * </li>
 * <li>
 * If the user is added to userGroup A, he will automatically be added to
 * userGroup B.
 * </li>
 * <li>
 * The user must have the Administrator Role in order to be added to
 * userGroup "Admin UserGroup".
 * </li>
 * </ul>
 *
 * <p>
 * Liferay's core services invoke {@link #checkMembership(long[], long[],
 * long[])} to detect policy violations before adding the users to and removing
 * the users from the userGroups. On passing the check, the service proceeds
 * with the changes and propagates appropriate related actions in the portal by
 * invoking {@link #propagateMembership(long[], long[], long[])}. On failing the
 * check, the service foregoes making the changes. For example, Liferay executes
 * this logic when adding and updating userGroups, adding and removing users
 * with respect to userGroups.
 * </p>
 *
 * <p>
 * Liferay's UI calls the "is*" methods, such as {@link
 * #isMembershipAllowed(long, long)}, to determine appropriate options to
 * display to the user. For example, the UI calls
 * {@link #isMembershipAllowed(long, long)} to decide whether to display the
 * "Join" link to the user.
 * </p>
 *
 * @author Roberto Díaz
 * @author Sergio González
 */
public interface UserGroupMembershipPolicy {

	/**
	 * Checks if the users can be added to and removed from the respective
	 * userGroups.
	 *
	 * <p>
	 * Liferay's core services call this method before adding the users to and
	 * removing the users from the respective userGroups. If this method
	 * throws an exception, the service foregoes making the changes.
	 * </p>
	 *
	 * @param  userIds the primary keys of the users to be added and removed
	 *         from the userGroups
	 * @param  addUserGroupIds the primary keys of the userGroups to which the
	 *         users are to be added (optionally <code>null</code>)
	 * @param  removeUserGroupIds the primary keys of the userGroups from which
	 *         the users are to be removed (optionally <code>null</code>)
	 * @throws PortalException if any one user could not be added to a
	 *         userGroup, if any one user could not be removed from a userGroup,
	 *         or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void checkMembership(
			long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the user can be added to the userGroup.
	 *
	 * @param  userId the primary key of the user
	 * @param  userGroupId the primary key of the userGroup
	 * @return <code>true</code> if the user can be added to the userGroup;
	 *         <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isMembershipAllowed(long userId, long userGroupId)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if userGroup membership for the user is
	 * mandatory. Liferay's UI, for example, calls this method in deciding
	 * whether to display the option to leave the userGroup.
	 *
	 * @param  userId the primary key of the user
	 * @param  userGroupId the primary key of the userGroup
	 * @return <code>true</code> if userGroup membership for the user is
	 *         mandatory; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isMembershipRequired(long userId, long userGroupId)
		throws PortalException, SystemException;

	/**
	 * Performs membership policy related actions after the users are added to
	 * and removed from the respective userGroups. Liferay's core services
	 * call this method after adding and removing the users to and from the
	 * respective userGroups.
	 *
	 * <p>
	 * The actions must ensure the integrity of each userGroup's membership
	 * policy. For example, some actions for implementations to consider
	 * performing are:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * If a user is added to userGroup A, has to be added to userGroup B too.
	 * </li>
	 * <li>
	 * If a user is removed from userGroup A, has to be removed to userGroup B
	 * too.
	 * </li>
	 * </ul>
	 *
	 * @param  userIds the primary key of the users to be added or removed
	 * @param  addUserGroupIds the primary keys of the userGroups to which
	 *         the users were added (optionally <code>null</code>)
	 * @param  removeUserGroupIds the primary keys of the userGroups from
	 *         which the users were removed (optionally <code>null</code>)
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void propagateMembership(
			long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds)
		throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of each of the portal's
	 * userGroups and performs operations necessary for the compliance of
	 * each userGroup. This method is called when upgrading Liferay and can also
	 * be triggered manually from the Control Panel.
	 *
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy() throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of the userGroup and
	 * performs operations necessary for the compliance of the userGroup.
	 *
	 * @param  userGroup the userGroup to verify
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy(UserGroup userGroup)
		throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of the userGroup,
	 * with respect to its attributes and/or expando attributes, and performs
	 * operations necessary for the compliance of the userGroup. Liferay calls
	 * this method when adding and updating userGroups.
	 *
	 * <p>
	 * The actions must ensure the integrity of the userGroup's membership
	 * policy based on what has changed in its attributes and/or expando
	 * attributes.
	 * </p>
	 *
	 * <p>
	 * For example, if the membership policy is that userGroups with the
	 * expando attribute A should only allow administrators, then this method
	 * could enforce that policy using the following logic:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * If the oldExpandoAttributes include the expando attribute A and the new
	 * expando attributes include it too, then no action needs to be performed
	 * regarding the policy. Note, the new expando attributes can be obtained
	 * by calling <code>assetTagLocalService.getTags(Group.class.getName(),
	 * group.getGroupId());</code>.
	 * </li>
	 * <li>
	 * If the oldExpandoAttributes include the expando attribute A and the new
	 * expando attributes don't include it, then no action needs to be performed
	 * regarding the policy, as non-administrator users need not be removed.
	 * </li>
	 * <li>
	 * However, if the oldExpandoAttributes don't include the expando attribute
	 * A and the new expando attributes include it, any userGroup user that
	 * does not have the Administrator role must be removed from the userGroup.
	 * </li>
	 *
	 * @param  userGroup the added or updated userGroup to verify
	 * @param  oldUserGroup the old userGroup
	 * @param  oldExpandoAttributes the old expando attributes
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy(
			UserGroup userGroup, UserGroup oldUserGroup,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException;

}