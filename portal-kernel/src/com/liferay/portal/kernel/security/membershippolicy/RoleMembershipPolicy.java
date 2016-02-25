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
import com.liferay.portal.kernel.model.Role;

import java.io.Serializable;

import java.util.Map;

/**
 * Provides the Role Membership Policy interface, allowing customization of user
 * membership regarding roles.
 *
 * <p>
 * Role Membership Policies define the roles a user is allowed to be assigned,
 * and the roles the user must be assigned.
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
 * If a user doesn't have the custom attribute A, he cannot be assigned to role
 * B.
 * </li>
 * <li>
 * If the user is added to role A, he will automatically be added to role B.
 * </li>
 * <li>
 * All users with the custom attribute A will automatically have the role B.
 * </li>
 * <li>
 * All the users with role A cannot have role B (incompatible roles).
 * </li>
 * </ul>
 *
 * <p>
 * Liferay's core services invoke {@link #checkRoles(long[], long[], long[])} to
 * detect policy violations before adding the users to and removing the users
 * from the roles. On passing the check, the service proceeds with the changes
 * and propagates appropriate related actions in the portal by invoking {@link
 * #propagateRoles(long[], long[], long[])}. On failing the check, the service
 * foregoes making the changes. For example, Liferay executes this logic when
 * adding and updating roles, and adding and removing roles with respect to
 * users.
 * </p>
 *
 * <p>
 * Liferay's UI calls the "is*" methods, such as {@link #isRoleAllowed(long,
 * long)}, to determine appropriate options to display to the user. For example,
 * the UI calls {@link #isRoleAllowed(long, long)} to decide whether to enable
 * the checkbox for adding the role to the user.
 * </p>
 *
 * @author Roberto Díaz
 * @author Sergio González
 */
public interface RoleMembershipPolicy {

	/**
	 * Checks if the roles can be added to or removed from their users.
	 *
	 * <p>
	 * Liferay's core services call this method before adding the users to and
	 * removing the users from the respective roles. If this method throws an
	 * exception, the service foregoes making the changes.
	 * </p>
	 *
	 * @param userIds the primary keys of the users to be added and removed from
	 *        the roles
	 * @param addRoleIds the primary keys of the roles to be added (optionally
	 *        <code>null</code>)
	 * @param removeRoleIds the primary keys of the roles to be removed
	 *        (optionally <code>null</code>)
	 */
	public void checkRoles(
			long[] userIds, long[] addRoleIds, long[] removeRoleIds)
		throws PortalException;

	/**
	 * Returns <code>true</code> if the role can be added to the user. Liferay's
	 * UI calls this method.
	 *
	 * @param  userId the primary key of the user
	 * @param  roleId the primary key of the role
	 * @return <code>true</code> if the role can be added to the user;
	 *         <code>false</code> otherwise
	 */
	public boolean isRoleAllowed(long userId, long roleId)
		throws PortalException;

	/**
	 * Returns <code>true</code> if the role is mandatory for the user.
	 * Liferay's UI, for example, calls this method in deciding whether the
	 * checkbox to select a role will be enable.
	 *
	 * @param  userId the primary key of the user
	 * @param  roleId the primary key of the role
	 * @return <code>true</code> if the role is mandatory for the user;
	 *         <code>false</code> otherwise
	 */
	public boolean isRoleRequired(long userId, long roleId)
		throws PortalException;

	/**
	 * Performs membership policy related actions after the respective roles are
	 * added to and removed from the affected users. Liferay's core services
	 * call this method after the roles are added to and removed from the users.
	 *
	 * <p>
	 * The actions must ensure the membership policy of each role. For example,
	 * some actions for implementations to consider performing are:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * If the role A is added to a user, role B should be added too.
	 * </li>
	 * <li>
	 * If the role A is removed from a user, role B should be removed too.
	 * </li>
	 * </ul>
	 *
	 * @param userIds the primary keys of the users
	 * @param addRoleIds the primary keys of the added roles
	 * @param removeRoleIds the primary keys of the removed roles
	 */
	public void propagateRoles(
			long[] userIds, long[] addRoleIds, long[] removeRoleIds)
		throws PortalException;

	/**
	 * Checks the integrity of the membership policy of each of the portal's
	 * roles and performs operations necessary for the compliance of each role.
	 * This method can be triggered manually from the Control Panel. If the
	 * <code>membership.policy.auto.verify</code> portal property is
	 * <code>true</code> this method is triggered when starting Liferay and
	 * every time a membership policy hook is deployed.
	 */
	public void verifyPolicy() throws PortalException;

	/**
	 * Checks the integrity of the membership policy of the role and performs
	 * operations necessary for the compliance of the role.
	 *
	 * @param role the role to verify
	 */
	public void verifyPolicy(Role role) throws PortalException;

	/**
	 * Checks the integrity of the membership policy of the role, with respect
	 * to the role's new attribute values and expando attributes, and performs
	 * operations necessary for the role's compliance. Liferay calls this method
	 * when adding and updating roles.
	 *
	 * @param role the added or updated role to verify
	 * @param oldRole the old role
	 * @param oldExpandoAttributes the old expando attributes
	 */
	public void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException;

}