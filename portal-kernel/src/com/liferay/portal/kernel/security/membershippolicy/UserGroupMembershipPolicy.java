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

package com.liferay.portal.kernel.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;

import java.io.Serializable;

import java.util.Map;

/**
 * Provides the User Group Membership Policy interface, allowing customization
 * of user membership regarding user groups.
 *
 * <p>
 * User Group Membership Policies define the user groups a user is allowed to be
 * a member of and the user groups the user must be a member of.
 * </p>
 *
 * <p>
 * An implementation may include any number of rules and actions to enforce
 * those rules. The implementation may include rules and actions like the
 * following:
 * </p>
 *
 * <ul>
 * <li>
 * If a user doesn't have the custom attribute A, he cannot be assigned to user
 * group B.
 * </li>
 * <li>
 * If the user is added to user group A, he will automatically be added to user
 * group B.
 * </li>
 * <li>
 * The user must have the Administrator Role in order to be added to user group
 * "Admin User Group".
 * </li>
 * </ul>
 *
 * <p>
 * Liferay's core services invoke {@link #checkMembership(long[], long[],
 * long[])} to detect policy violations before adding the users to and removing
 * the users from the user groups. On passing the check, the service proceeds
 * with the changes and propagates appropriate related actions in the portal by
 * invoking {@link #propagateMembership(long[], long[], long[])}. On failing the
 * check, the service foregoes making the changes. For example, Liferay executes
 * this logic when adding and updating user groups, adding and removing users
 * with respect to user groups.
 * </p>
 *
 * <p>
 * Liferay's UI calls the "is*" methods, such as {@link
 * #isMembershipAllowed(long, long)}, to determine appropriate options to
 * display to the user. For example, the UI calls {@link
 * #isMembershipAllowed(long, long)} to decide whether to enable the checkbox
 * for adding the user to the user group.
 * </p>
 *
 * @author Roberto Díaz
 * @author Sergio González
 */
public interface UserGroupMembershipPolicy {

	/**
	 * Checks if the users can be added to and removed from the respective user
	 * groups.
	 *
	 * <p>
	 * Liferay's core services call this method before adding the users to and
	 * removing the users from the respective user groups. If this method throws
	 * an exception, the service foregoes making the changes.
	 * </p>
	 *
	 * @param userIds the primary keys of the users to be added and removed from
	 *        the user groups
	 * @param addUserGroupIds the primary keys of the user groups to which the
	 *        users are to be added (optionally <code>null</code>)
	 * @param removeUserGroupIds the primary keys of the user groups from which
	 *        the users are to be removed (optionally <code>null</code>)
	 */
	public void checkMembership(
			long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds)
		throws PortalException;

	/**
	 * Returns <code>true</code> if the user can be added to the user group.
	 * Liferay's UI calls this method.
	 *
	 * @param  userId the primary key of the user
	 * @param  userGroupId the primary key of the user group
	 * @return <code>true</code> if the user can be added to the user group;
	 *         <code>false</code> otherwise
	 */
	public boolean isMembershipAllowed(long userId, long userGroupId)
		throws PortalException;

	/**
	 * Returns <code>true</code> if user group membership for the user is
	 * mandatory. Liferay's UI, for example, calls this method in deciding
	 * whether the checkbox to select the user group will be enable.
	 *
	 * @param  userId the primary key of the user
	 * @param  userGroupId the primary key of the user group
	 * @return <code>true</code> if user group membership for the user is
	 *         mandatory; <code>false</code> otherwise
	 */
	public boolean isMembershipRequired(long userId, long userGroupId)
		throws PortalException;

	/**
	 * Performs membership policy related actions after the users are added to
	 * and removed from the respective user groups. Liferay's core services call
	 * this method after adding and removing the users to and from the
	 * respective user groups.
	 *
	 * <p>
	 * The actions must ensure the integrity of each user group's membership
	 * policy. For example, some actions for implementations to consider
	 * performing are:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * If a user is added to user group A, he should be added to user group B
	 * too.
	 * </li>
	 * <li>
	 * If a user is removed from user group A, he should be removed from user
	 * group B too.
	 * </li>
	 * </ul>
	 *
	 * @param userIds the primary key of the users to be added or removed
	 * @param addUserGroupIds the primary keys of the user groups to which the
	 *        users were added (optionally <code>null</code>)
	 * @param removeUserGroupIds the primary keys of the user groups from which
	 *        the users were removed (optionally <code>null</code>)
	 */
	public void propagateMembership(
			long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds)
		throws PortalException;

	/**
	 * Checks the integrity of the membership policy of each of the portal's
	 * user groups and performs operations necessary for the compliance of each
	 * user group. This method can be triggered manually from the Control Panel.
	 * If the <code>membership.policy.auto.verify</code> portal property is
	 * <code>true</code> this method is triggered when starting Liferay and
	 * every time a membership policy hook is deployed.
	 */
	public void verifyPolicy() throws PortalException;

	/**
	 * Checks the integrity of the membership policy of the user group and
	 * performs operations necessary for the user group's compliance.
	 *
	 * @param userGroup the user group to verify
	 */
	public void verifyPolicy(UserGroup userGroup) throws PortalException;

	/**
	 * Checks the integrity of the membership policy of the user group, with
	 * respect to the user group's new attribute values and expando attributes,
	 * and performs operations necessary for the compliance of the user group.
	 * Liferay calls this method when adding and updating user groups.
	 *
	 * <p>
	 * The actions must ensure the integrity of the user group's membership
	 * policy based on what has changed in the user group's attribute values and
	 * expando attributes.
	 * </p>
	 *
	 * <p>
	 * For example, if the membership policy is that user groups with the
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
	 * A and the new expando attributes include it, any user group user that
	 * does not have the Administrator role must be removed from the user group.
	 * </li>
	 * </ul>
	 *
	 * @param userGroup the added or updated user group to verify
	 * @param oldUserGroup the old user group
	 * @param oldExpandoAttributes the old expando attributes
	 */
	public void verifyPolicy(
			UserGroup userGroup, UserGroup oldUserGroup,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException;

}