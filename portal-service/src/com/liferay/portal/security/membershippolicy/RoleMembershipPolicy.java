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
import com.liferay.portal.model.Role;

import java.io.Serializable;

import java.util.Map;

/**
 * Provides the Role Membership Policy interface, allowing customization of user
 * membership regarding roles.
 *
 * <p>
 * Role Membership Policies define the roles a user is allowed to be a member
 * of, the roles the user must be a member of, the roles the user is
 * allowed to be assigned, and the roles the user must be assigned.
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
 * If a user doesn't have the custom attribute A, he cannot be assigned to role
 * B.
 * </li>
 * <li>
 * If the user is added to role A, he will automatically be added to role B.
 * </li>
 * <li>
 * The user must have the Administrator Role in order to be added to role "Admin
 * Role".
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
 * Liferay's core services invoke {@link #checkRoles(long[], long[], long[])}
 * to detect policy violations before adding the users to and removing the users
 * from the roles. On passing the check, the service proceeds with the changes
 * and propagates appropriate related actions in the portal by invoking
 * {@link #propagateRoles(long[], long[], long[])}. On failing the check, the
 * service foregoes making the changes. For example, Liferay executes this logic
 * when adding and updating roles, adding and removing users with respect to
 * roles, and adding and removing roles with respect to users.
 * </p>
 *
 * <p>
 * Liferay's UI calls the "is*" methods, such as {@link #isRoleAllowed(long,
 * long)}, to determine appropriate options to display to the user. For example,
 * the UI calls {@link #isRoleAllowed(long, long)} to decide whether to display
 * the "Join" link to the user.
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
	 * @param  addRoleIds the user roles to be added
	 * @param  removeRoleIds the user roles to be removed
	 * @throws PortalException if any one user role violated the policy or if a
	 *         portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void checkRoles(
			long[] userIds, long[] addRoleIds, long[] removeRoleIds)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the role can be added to the user. Liferay's
	 * UI calls this method.
	 *
	 * @param  userId the primary key of the user
	 * @param  roleId the primary key of the role
	 * @return <code>true</code> if the role can be added to the user;
	 *         <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isRoleAllowed(long userId, long roleId)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the role is mandatory for the user.
	 * If <code>true</code>, nobody can remove the role from this user.
	 * Liferay's UI calls this method.
	 *
	 * @param  userId the primary key of the user
	 * @param  roleId the primary key of the role
	 * @return <code>true</code> if the role is mandatory for the user;
	 *         <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public boolean isRoleRequired(long userId, long roleId)
		throws PortalException, SystemException;

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
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void propagateRoles(
			long[] userIds, long[] addRoleIds, long[] removeRoleIds)
		throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of each of the portal's
	 * roles and performs operations necessary for the compliance of each role.
	 * This method is called when upgrading Liferay and can also be triggered
	 * manually from the Control Panel.
	 *
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy() throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of the role and performs
	 * operations necessary for the compliance of the role.
	 *
	 * @param  role the role to verify
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy(Role role) throws PortalException, SystemException;

	/**
	 * Checks the integrity of the membership policy of the role, with respect
	 * to its new attributes and/or expando attributes, and performs operations
	 * necessary for the compliance of the role. Liferay calls this method when
	 * adding and updating roles.
	 *
	 * @param  role the added or updated role to verify
	 * @param  oldRole the old role
	 * @param  oldExpandoAttributes the old expando attributes
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void verifyPolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException;

}