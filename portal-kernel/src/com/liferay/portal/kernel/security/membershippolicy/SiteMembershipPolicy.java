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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the Site Membership Policy interface, allowing customization of user
 * membership regarding sites and site roles.
 *
 * <p>
 * Site Membership Policies define the sites a user is allowed to be a member
 * of, the sites the user must be a member of, the site roles the user is
 * allowed to be assigned, and the site roles the user must be assigned.
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
 * If a user is a member of the site he will automatically be a member of all
 * its child sites.
 * </li>
 * <li>
 * Only the members of the parent site can become a member of this site.
 * </li>
 * <li>
 * If a user doesn't have the custom attribute A, he cannot be assigned to site
 * B.
 * </li>
 * <li>
 * If the user is added to site A, he will automatically be added to site B.
 * </li>
 * <li>
 * The user must have the Administrator Role in order to be added to site "Admin
 * Site".
 * </li>
 * <li>
 * All users with the custom attribute A will automatically have the site
 * role B.
 * </li>
 * <li>
 * All the users with site role A cannot have site role B (incompatible
 * roles).
 * </li>
 * </ul>
 *
 * <p>
 * Liferay's core services invoke {@link #checkMembership(long[], long[],
 * long[])} to detect policy violations before adding the users to and removing
 * the users from the sites. On passing the check, the service proceeds with the
 * changes and propagates appropriate related actions in the portal by invoking
 * {@link #propagateMembership(long[], long[], long[])}. On failing the check,
 * the service foregoes making the changes. For example, Liferay executes this
 * logic when adding and updating sites, adding and removing users with respect
 * to sites, and adding and removing site roles with respect to users.
 * </p>
 *
 * <p>
 * Liferay's UI calls the "is*" methods, such as {@link
 * #isMembershipAllowed(long, long)}, to determine appropriate options to
 * display to the user. For example, the UI calls {@link
 * #isMembershipAllowed(long, long)} to decide whether to display the "Join"
 * link to the user.
 * </p>
 *
 * <p>
 * Liferay's core services call {@link #isMembershipProtected(PermissionChecker,
 * long, long)} and {@link #isRoleProtected(PermissionChecker, long, long,
 * long)} to protect user site memberships and site role assignments,
 * appropriately.
 * </p>
 *
 * @author Sergio González
 * @author Roberto Díaz
 */
public interface SiteMembershipPolicy {

	/**
	 * Checks if the users can be added to and removed from the respective
	 * sites.
	 *
	 * <p>
	 * Liferay's core services call this method before adding the users to and
	 * removing the users from the respective sites. If this method throws an
	 * exception, the service foregoes making the changes.
	 * </p>
	 *
	 * @param userIds the primary keys of the users to be added and removed from
	 *        the sites
	 * @param addGroupIds the primary keys of the sites to which the users are
	 *        to be added (optionally <code>null</code>)
	 * @param removeGroupIds the primary keys of the sites from which the users
	 *        are to be removed (optionally <code>null</code>)
	 */
	public void checkMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException;

	/**
	 * Checks if the site roles can be added to or removed from their users.
	 *
	 * <p>
	 * Liferay's core services call this method before adding the users to and
	 * removing the users from the respective site roles. If this method throws
	 * an exception, the service foregoes making the changes.
	 * </p>
	 *
	 * @param addUserGroupRoles the user group roles to be added
	 * @param removeUserGroupRoles the user group roles to be removed
	 */
	public void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException;

	/**
	 * Returns <code>true</code> if the user can be added to the site. Liferay's
	 * UI calls this method.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the site
	 * @return <code>true</code> if the user can be added to the site;
	 *         <code>false</code> otherwise
	 */
	public boolean isMembershipAllowed(long userId, long groupId)
		throws PortalException;

	/**
	 * Returns <code>true</code> if the policy prevents the user from being
	 * removed from the site by the user associated with the permission checker.
	 *
	 * @param  permissionChecker the permission checker referencing a user
	 * @param  userId the primary key of the user to check for protection
	 * @param  groupId the primary key of the site
	 * @return <code>true</code> if the policy prevents the user from being
	 *         removed from the site by the user associated with the permission
	 *         checker; <code>false</code> otherwise
	 */
	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId, long groupId)
		throws PortalException;

	/**
	 * Returns <code>true</code> if site membership for the user is mandatory.
	 * Liferay's UI, for example, calls this method in deciding whether to
	 * display the option to leave the site.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the site
	 * @return <code>true</code> if site membership for the user is mandatory;
	 *         <code>false</code> otherwise
	 */
	public boolean isMembershipRequired(long userId, long groupId)
		throws PortalException;

	/**
	 * Returns <code>true</code> if the role can be added to the user on the
	 * site. Liferay's UI calls this method.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the site
	 * @param  roleId the primary key of the role
	 * @return <code>true</code> if the role can be added to the user on the
	 *         site; <code>false</code> otherwise
	 */
	public boolean isRoleAllowed(long userId, long groupId, long roleId)
		throws PortalException;

	/**
	 * Returns <code>true</code> if the policy prevents the user from being
	 * removed from the role by the user associated with the permission checker.
	 *
	 * @param  permissionChecker the permission checker referencing a user
	 * @param  userId the primary key of the user to check for protection
	 * @param  groupId the primary key of the site
	 * @param  roleId the primary key of the role
	 * @return <code>true</code> if the policy prevents the user from being
	 *         removed from the role by the user associated with the permission
	 *         checker; <code>false</code> otherwise
	 */
	public boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId, long groupId,
			long roleId)
		throws PortalException;

	/**
	 * Returns <code>true</code> if the role is mandatory for the user on the
	 * site. Liferay's UI calls this method.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the site
	 * @param  roleId the primary key of the role
	 * @return <code>true</code> if the role is mandatory for the user on the
	 *         site; <code>false</code> otherwise
	 */
	public boolean isRoleRequired(long userId, long groupId, long roleId)
		throws PortalException;

	/**
	 * Performs membership policy related actions after the users are added to
	 * and removed from the respective sites. Liferay's core services call this
	 * method after adding and removing the users to and from the respective
	 * sites.
	 *
	 * <p>
	 * The actions must ensure the integrity of each site's membership policy.
	 * For example, some actions for implementations to consider performing are:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * Adding the users to the child sites of each site to which the users
	 * were added.
	 * </li>
	 * <li>
	 * Removing the users from the child sites of each site from which the users
	 * were removed.
	 * </li>
	 * </ul>
	 *
	 * @param userIds the primary key of the users that have been added or
	 *        removed
	 * @param addGroupIds the primary keys of the sites to which the users were
	 *        added (optionally <code>null</code>)
	 * @param removeGroupIds the primary keys of the sites from which the users
	 *        were removed (optionally <code>null</code>)
	 */
	public void propagateMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException;

	/**
	 * Performs membership policy related actions after the respective site
	 * roles are added to and removed from the affected users. Liferay's core
	 * services call this method after the roles are added to and removed from
	 * the users.
	 *
	 * <p>
	 * The actions must ensure the membership policy of each site role. For
	 * example, some actions for implementations to consider performing are:
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
	 * @param addUserGroupRoles the user group roles added
	 * @param removeUserGroupRoles the user group roles removed
	 */
	public void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException;

	/**
	 * Checks the integrity of the membership policy of each of the portal's
	 * sites and performs operations necessary for the compliance of each site
	 * and site role. This method can be triggered manually from the Control
	 * Panel. If the <code>membership.policy.auto.verify</code> portal property
	 * is <code>true</code> this method is triggered when starting Liferay and
	 * every time a membership policy hook is deployed.
	 */
	public void verifyPolicy() throws PortalException;

	/**
	 * Checks the integrity of the membership policy of the site and performs
	 * operations necessary for the site's compliance.
	 *
	 * @param group the site to verify
	 */
	public void verifyPolicy(Group group) throws PortalException;

	/**
	 * Checks the integrity of the membership policy of the site, with respect
	 * to the site's new attribute values, categories, tags, expando attributes,
	 * and type settings properties, and performs operations necessary for the
	 * compliance of the site and its site roles. Liferay calls this method when
	 * adding and updating sites.
	 *
	 * <p>
	 * The actions must ensure the integrity of the site's membership policy
	 * based on what has changed in the site's attribute values, categories,
	 * tags, expando attributes, and type settings properties.
	 * </p>
	 *
	 * <p>
	 * For example, if the membership policy is that sites with the
	 * "admnistrator" tag should only allow administrators as users, then this
	 * method could enforce that policy using the following logic:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * If the old tags include the "administrator" tag and the new tags include
	 * it too, then no action needs to be performed regarding the
	 * policy. Note, the new tags can be obtained by calling
	 * <code>assetTagLocalService.getTags(Group.class.getName(),
	 * group.getGroupId());</code>.
	 * </li>
	 * <li>
	 * If the old tags include the "administrator" tag and the new tags don't
	 * include it,
	 * then no action needs to be performed regarding the
	 * policy, as non-administrator users need not be removed.
	 * </li>
	 * <li>
	 * However, if the old tags don't include the "administrator" tag, but the
	 * new tags include it, any site user that does not have the Administrator
	 * role
	 * must be removed from the site.
	 * </li>
	 * </ul>
	 *
	 * @param group the added or updated site to verify
	 * @param oldGroup the old site
	 * @param oldAssetCategories the old categories
	 * @param oldAssetTags the old tags
	 * @param oldExpandoAttributes the old expando attributes
	 * @param oldTypeSettingsProperties the old type settings properties
	 */
	public void verifyPolicy(
			Group group, Group oldGroup, List<AssetCategory> oldAssetCategories,
			List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes,
			UnicodeProperties oldTypeSettingsProperties)
		throws PortalException;

	/**
	 * Checks the integrity of the membership policy of the site role and
	 * performs operations necessary for the role's compliance.
	 *
	 * @param role the role to verify
	 */
	public void verifyPolicy(Role role) throws PortalException;

	/**
	 * Checks the integrity of the membership policy of the site role, with
	 * respect to its expando attributes, and performs operations necessary for
	 * the role's compliance. Liferay calls this method when adding and updating
	 * site roles.
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