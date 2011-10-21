/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.RequiredUserException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.UserServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PasswordPolicyPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.service.permission.TeamPermissionUtil;
import com.liferay.portal.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.service.permission.UserGroupRolePermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.util.List;
import java.util.Locale;

/**
 * The implementation of the user remote service.
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Scott Lee
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class UserServiceImpl extends UserServiceBaseImpl {

	/**
	 * Adds the users to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if a group or user with the primary key could
	 *         not be found, or if the user did not have permission to assign
	 *         group members
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		try {
			GroupPermissionUtil.check(
				getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);
		}
		catch (PrincipalException pe) {

			// Allow any user to join open sites

			boolean hasPermission = false;

			if (userIds.length == 0) {
				hasPermission = true;
			}
			else if (userIds.length == 1) {
				User user = getUser();

				if (user.getUserId() == userIds[0]) {
					Group group = groupPersistence.findByPrimaryKey(groupId);

					if (!group.isOrganization() &&
						(user.getCompanyId() == group.getCompanyId())) {

						int type = group.getType();

						if (type == GroupConstants.TYPE_SITE_OPEN) {
							hasPermission = true;
						}
					}
				}
			}

			if (!hasPermission) {
				throw new PrincipalException();
			}
		}

		userLocalService.addGroupUsers(groupId, userIds);
	}

	/**
	 * Adds the users to the organization.
	 *
	 * @param  organizationId the primary key of the organization
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if an organization or user with the primary key
	 *         could not be found, if the user did not have permission to
	 *         assign organization members, or if current user did not have an
	 *         organization in common with a given user
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.ASSIGN_MEMBERS);

		validateOrganizationUsers(userIds);

		userLocalService.addOrganizationUsers(organizationId, userIds);
	}

	/**
	 * Assigns the password policy to the users, removing any other currently
	 * assigned password policies.
	 *
	 * @param  passwordPolicyId the primary key of the password policy
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if the user did not have permission to assign
	 *         policy members
	 * @throws SystemException if a system exception occurred
	 */
	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws PortalException, SystemException {

		PasswordPolicyPermissionUtil.check(
			getPermissionChecker(), passwordPolicyId,
			ActionKeys.ASSIGN_MEMBERS);

		userLocalService.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	/**
	 * Adds the users to the role.
	 *
	 * @param  roleId the primary key of the role
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if a role or user with the primary key could not
	 *         be found or if the user did not have permission to assign role
	 *         members
	 * @throws SystemException if a system exception occurred
	 */
	public void addRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.addRoleUsers(roleId, userIds);
	}

	/**
	 * Adds the users to the team.
	 *
	 * @param  teamId the primary key of the team
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if a team or user with the primary key could not
	 *         be found or if the user did not have permission to assign team
	 *         members
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeamUsers(long teamId, long[] userIds)
		throws PortalException, SystemException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.addTeamUsers(teamId, userIds);
	}

	/**
	 * Adds a user with additional parameters.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user including
	 * its resources, metadata, and internal data structures. It is not
	 * necessary to make subsequent calls to any methods to setup default
	 * groups, resources, etc.
	 * </p>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  autoPassword whether a password should be automatically
	 *         generated for the user
	 * @param  password1 the user's password
	 * @param  password2 the user's password confirmation
	 * @param  autoScreenName whether a screen name should be automatically
	 *         generated for the user
	 * @param  screenName the user's screen name
	 * @param  emailAddress the user's email address
	 * @param  facebookId the user's facebook ID
	 * @param  openId the user's OpenID
	 * @param  locale the user's locale
	 * @param  firstName the user's first name
	 * @param  middleName the user's middle name
	 * @param  lastName the user's last name
	 * @param  prefixId the user's name prefix ID
	 * @param  suffixId the user's name suffix ID
	 * @param  male whether the user is male
	 * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
	 *         January)
	 * @param  birthdayDay the user's birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  jobTitle the user's job title
	 * @param  groupIds the primary keys of the user's groups
	 * @param  organizationIds the primary keys of the user's organizations
	 * @param  roleIds the primary keys of the roles this user possesses
	 * @param  userGroupIds the primary keys of the user's user groups
	 * @param  addresses the user's addresses
	 * @param  emailAddresses the user's email addresses
	 * @param  phones the user's phone numbers
	 * @param  websites the user's websites
	 * @param  announcementsDelivers the announcements deliveries
	 * @param  sendEmail whether to send the user an email notification about
	 *         their new account
	 * @param  serviceContext the user's service context (optionally
	 *         <code>null</code>). Can specify the user's universally unique
	 *         identifier (with the <code>uuid</code> attribute), asset
	 *         category IDs, asset tag names, and expando bridge attributes.
	 * @return the new user
	 * @throws PortalException if the user's information was invalid, if the
	 *         creator did not have permission to add users, if the email
	 *         address was reserved, or some other portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public User addUser(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, Locale locale,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			List<Address> addresses, List<EmailAddress> emailAddresses,
			List<Phone> phones, List<Website> websites,
			List<AnnouncementsDelivery> announcementsDelivers,
			boolean sendEmail, ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(false);

			return addUserWithWorkflow(
				companyId, autoPassword, password1, password2, autoScreenName,
				screenName, emailAddress, facebookId, openId, locale, firstName,
				middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
				roleIds, userGroupIds, addresses, emailAddresses, phones,
				websites, announcementsDelivers, sendEmail, serviceContext);
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	/**
	 * Adds a user.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user including
	 * its resources, metadata, and internal data structures. It is not
	 * necessary to make subsequent calls to any methods to setup default
	 * groups, resources, etc.
	 * </p>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  autoPassword whether a password should be automatically
	 *         generated for the user
	 * @param  password1 the user's password
	 * @param  password2 the user's password confirmation
	 * @param  autoScreenName whether a screen name should be automatically
	 *         generated for the user
	 * @param  screenName the user's screen name
	 * @param  emailAddress the user's email address
	 * @param  facebookId the user's facebook ID
	 * @param  openId the user's OpenID
	 * @param  locale the user's locale
	 * @param  firstName the user's first name
	 * @param  middleName the user's middle name
	 * @param  lastName the user's last name
	 * @param  prefixId the user's name prefix ID
	 * @param  suffixId the user's name suffix ID
	 * @param  male whether the user is male
	 * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
	 *         January)
	 * @param  birthdayDay the user's birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  jobTitle the user's job title
	 * @param  groupIds the primary keys of the user's groups
	 * @param  organizationIds the primary keys of the user's organizations
	 * @param  roleIds the primary keys of the roles this user possesses
	 * @param  userGroupIds the primary keys of the user's user groups
	 * @param  sendEmail whether to send the user an email notification about
	 *         their new account
	 * @param  serviceContext the user's service context (optionally
	 *         <code>null</code>). Can specify the user's universally unique
	 *         identifier (with the <code>uuid</code> attribute), asset
	 *         category IDs, asset tag names, and expando bridge attributes.
	 * @return the new user
	 * @throws PortalException if the user's information was invalid, if the
	 *         creator did not have permission to add users, or if the email
	 *         address was reserved
	 * @throws SystemException if a system exception occurred
	 */
	public User addUser(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, Locale locale,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			boolean sendEmail, ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(false);

			return addUserWithWorkflow(
				companyId, autoPassword, password1, password2, autoScreenName,
				screenName, emailAddress, facebookId, openId, locale, firstName,
				middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
				roleIds, userGroupIds, sendEmail, serviceContext);
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	/**
	 * Adds a user with workflow and additional parameters.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user including
	 * its resources, metadata, and internal data structures. It is not
	 * necessary to make subsequent calls to any methods to setup default
	 * groups, resources, etc.
	 * </p>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  autoPassword whether a password should be automatically
	 *         generated for the user
	 * @param  password1 the user's password
	 * @param  password2 the user's password confirmation
	 * @param  autoScreenName whether a screen name should be automatically
	 *         generated for the user
	 * @param  screenName the user's screen name
	 * @param  emailAddress the user's email address
	 * @param  facebookId the user's facebook ID
	 * @param  openId the user's OpenID
	 * @param  locale the user's locale
	 * @param  firstName the user's first name
	 * @param  middleName the user's middle name
	 * @param  lastName the user's last name
	 * @param  prefixId the user's name prefix ID
	 * @param  suffixId the user's name suffix ID
	 * @param  male whether the user is male
	 * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
	 *         January)
	 * @param  birthdayDay the user's birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  jobTitle the user's job title
	 * @param  groupIds the primary keys of the user's groups
	 * @param  organizationIds the primary keys of the user's organizations
	 * @param  roleIds the primary keys of the roles this user possesses
	 * @param  userGroupIds the primary keys of the user's user groups
	 * @param  addresses the user's addresses
	 * @param  emailAddresses the user's email addresses
	 * @param  phones the user's phone numbers
	 * @param  websites the user's websites
	 * @param  announcementsDelivers the announcements deliveries
	 * @param  sendEmail whether to send the user an email notification about
	 *         their new account
	 * @param  serviceContext the user's service context (optionally
	 *         <code>null</code>). Can specify the user's universally unique
	 *         identifier (with the <code>uuid</code> attribute), asset
	 *         category IDs, asset tag names, and expando bridge attributes.
	 * @return the new user
	 * @throws PortalException if the user's information was invalid, if the
	 *         creator did not have permission to add users, if the email
	 *         address was reserved, or some other portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public User addUserWithWorkflow(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, Locale locale,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			List<Address> addresses, List<EmailAddress> emailAddresses,
			List<Phone> phones, List<Website> websites,
			List<AnnouncementsDelivery> announcementsDelivers,
			boolean sendEmail, ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean indexingEnabled = serviceContext.isIndexingEnabled();

		serviceContext.setIndexingEnabled(false);

		try {
			User user = addUserWithWorkflow(
				companyId, autoPassword, password1, password2, autoScreenName,
				screenName, emailAddress, facebookId, openId, locale, firstName,
				middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
				roleIds, userGroupIds, sendEmail, serviceContext);

			UsersAdminUtil.updateAddresses(
				Contact.class.getName(), user.getContactId(), addresses);

			UsersAdminUtil.updateEmailAddresses(
				Contact.class.getName(), user.getContactId(), emailAddresses);

			UsersAdminUtil.updatePhones(
				Contact.class.getName(), user.getContactId(), phones);

			UsersAdminUtil.updateWebsites(
				Contact.class.getName(), user.getContactId(), websites);

			updateAnnouncementsDeliveries(
				user.getUserId(), announcementsDelivers);

			if (indexingEnabled) {
				Indexer indexer = IndexerRegistryUtil.getIndexer(
					User.class);

				indexer.reindex(user);
			}

			return user;
		}
		finally {
			serviceContext.setIndexingEnabled(indexingEnabled);
		}
	}

	/**
	 * Adds a user with workflow.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user including
	 * its resources, metadata, and internal data structures. It is not
	 * necessary to make subsequent calls to any methods to setup default
	 * groups, resources, etc.
	 * </p>
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  autoPassword whether a password should be automatically
	 *         generated for the user
	 * @param  password1 the user's password
	 * @param  password2 the user's password confirmation
	 * @param  autoScreenName whether a screen name should be automatically
	 *         generated for the user
	 * @param  screenName the user's screen name
	 * @param  emailAddress the user's email address
	 * @param  facebookId the user's facebook ID
	 * @param  openId the user's OpenID
	 * @param  locale the user's locale
	 * @param  firstName the user's first name
	 * @param  middleName the user's middle name
	 * @param  lastName the user's last name
	 * @param  prefixId the user's name prefix ID
	 * @param  suffixId the user's name suffix ID
	 * @param  male whether the user is male
	 * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
	 *         January)
	 * @param  birthdayDay the user's birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  jobTitle the user's job title
	 * @param  groupIds the primary keys of the user's groups
	 * @param  organizationIds the primary keys of the user's organizations
	 * @param  roleIds the primary keys of the roles this user possesses
	 * @param  userGroupIds the primary keys of the user's user groups
	 * @param  sendEmail whether to send the user an email notification about
	 *         their new account
	 * @param  serviceContext the user's service context (optionally
	 *         <code>null</code>). Can specify the user's universally unique
	 *         identifier (with the <code>uuid</code> attribute), asset
	 *         category IDs, asset tag names, and expando bridge attributes.
	 * @return the new user
	 * @throws PortalException if the user's information was invalid, if the
	 *         creator did not have permission to add users, or if the email
	 *         address was reserved
	 * @throws SystemException if a system exception occurred
	 */
	public User addUserWithWorkflow(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, Locale locale,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			boolean sendEmail, ServiceContext serviceContext)
		throws PortalException, SystemException {

		long creatorUserId = 0;

		try {
			creatorUserId = getUserId();
		}
		catch (PrincipalException pe) {
		}

		checkAddUserPermission(
			creatorUserId, companyId, emailAddress, organizationIds,
			serviceContext);

		return userLocalService.addUserWithWorkflow(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
	}

	/**
	 * Adds the users to the user group.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if a user group or user with the primary could
	 *         could not be found, or if the current user did not have
	 *         permission to assign group members
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.addUserGroupUsers(userGroupId, userIds);
	}

	/**
	 * Deletes the user's portrait image.
	 *
	 * @param  userId the primary key of the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the user's portrait could not be found, or if the
	 *         current user did not have permission to update the user
	 * @throws SystemException if a system exception occurred
	 */
	public void deletePortrait(long userId)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		userLocalService.deletePortrait(userId);
	}

	/**
	 * Removes the user from the role.
	 *
	 * @param  roleId the primary key of the role
	 * @param  userId the primary key of the user
	 * @throws PortalException if a role or user with the primary key could not
	 *         be found, or if the current user did not have permission to
	 *         assign role members
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteRoleUser(long roleId, long userId)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.deleteRoleUser(roleId, userId);
	}

	/**
	 * Deletes the user.
	 *
	 * @param  userId the primary key of the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found or if the current user did not have permission to delete
	 *         the user
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteUser(long userId)
		throws PortalException, SystemException {

		if (getUserId() == userId) {
			throw new RequiredUserException();
		}

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.DELETE);

		userLocalService.deleteUser(userId);
	}

	/**
	 * Returns the primary key of the default user for the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the primary key of the default user for the company
	 * @throws PortalException if a default user for the company could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public long getDefaultUserId(long companyId)
		throws PortalException, SystemException {

		return userLocalService.getDefaultUserId(companyId);
	}

	/**
	 * Returns the primary keys of all the users belonging to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the primary keys of the users belonging to the group
	 * @throws PortalException if the current user did not have permission to
	 *         view group assignments
	 * @throws SystemException if a system exception occurred
	 */
	public long[] getGroupUserIds(long groupId)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.VIEW_MEMBERS);

		return userLocalService.getGroupUserIds(groupId);
	}

	/**
	 * Returns the primary keys of all the users belonging to the organization.
	 *
	 * @param  organizationId the primary key of the organization
	 * @return the primary keys of the users belonging to the organization
	 * @throws PortalException if the current user did not have permission to
	 *         view organization assignments
	 * @throws SystemException if a system exception occurred
	 */
	public long[] getOrganizationUserIds(long organizationId)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.VIEW_MEMBERS);

		return userLocalService.getOrganizationUserIds(organizationId);
	}

	/**
	 * Returns the primary keys of all the users belonging to the role.
	 *
	 * @param  roleId the primary key of the role
	 * @return the primary keys of the users belonging to the role
	 * @throws PortalException if the current user did not have permission to
	 *         view role members
	 * @throws SystemException if a system exception occurred
	 */
	public long[] getRoleUserIds(long roleId) throws
		PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.VIEW);

		return userLocalService.getRoleUserIds(roleId);
	}

	/**
	 * Returns the user with the email address.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  emailAddress the user's email address
	 * @return the user with the email address
	 * @throws PortalException if a user with the email address could not be
	 *         found or if the current user did not have permission to view the
	 *         user
	 * @throws SystemException if a system exception occurred
	 */
	public User getUserByEmailAddress(long companyId, String emailAddress)
		throws PortalException, SystemException {

		User user = userLocalService.getUserByEmailAddress(
			companyId, emailAddress);

		UserPermissionUtil.check(
			getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	/**
	 * Returns the user with the primary key.
	 *
	 * @param  userId the primary key of the user
	 * @return the user with the primary key
	 * @throws PortalException if a user with the primary key could not be
	 *         found or if the current user did not have permission to view the
	 *         user
	 * @throws SystemException if a system exception occurred
	 */
	public User getUserById(long userId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		UserPermissionUtil.check(
			getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	/**
	 * Returns the user with the screen name.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  screenName the user's screen name
	 * @return the user with the screen name
	 * @throws PortalException if a user with the screen name could not be
	 *         found or if the current user did not have permission to veiw the
	 *         user
	 * @throws SystemException if a system exception occurred
	 */
	public User getUserByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		User user = userLocalService.getUserByScreenName(
			companyId, screenName);

		UserPermissionUtil.check(
			getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

		return user;
	}

	/**
	 * Returns the primary key of the user with the email address.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  emailAddress the user's email address
	 * @return the primary key of the user with the email address
	 * @throws PortalException if a user with the email address could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public long getUserIdByEmailAddress(long companyId, String emailAddress)
		throws PortalException, SystemException {

		User user = getUserByEmailAddress(companyId, emailAddress);

		return user.getUserId();
	}

	/**
	 * Returns the primary key of the user with the screen name.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  screenName the user's screen name
	 * @return the primary key of the user with the screen name
	 * @throws PortalException if a user with the screen name could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public long getUserIdByScreenName(long companyId, String screenName)
		throws PortalException, SystemException {

		User user = getUserByScreenName(companyId, screenName);

		return user.getUserId();
	}

	/**
	 * Returns <code>true</code> if the user is a member of the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user
	 * @return <code>true</code> if the user is a member of the group;
	 *         <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasGroupUser(long groupId, long userId)
		throws SystemException {

		return userLocalService.hasGroupUser(groupId, userId);
	}

	/**
	 * Returns <code>true</code> if the user is a member of the role.
	 *
	 * @param  roleId the primary key of the role
	 * @param  userId the primary key of the user
	 * @return <code>true</code> if the user is a member of the role;
	 *         <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasRoleUser(long roleId, long userId)
		throws SystemException {

		return userLocalService.hasRoleUser(roleId, userId);
	}

	/**
	 * Returns <code>true</code> if the user has the role with the name,
	 * optionally through inheritance.
	 *
	 * @param  companyId the primary key of the role's company
	 * @param  name the name of the role (must be a regular role, not an
	 *         organization, site or provider role)
	 * @param  userId the primary key of the user
	 * @param  inherited whether to include roles inherited from organizations,
	 *         sites, etc.
	 * @return <code>true</code> if the user has the role; <code>false</code>
	 *         otherwise
	 * @throws PortalException if a role with the name could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasRoleUser(
			long companyId, String name, long userId, boolean inherited)
		throws PortalException, SystemException {

		return userLocalService.hasRoleUser(companyId, name, userId, inherited);
	}

	/**
	 * Updates a user account that was automatically created when a guest user
	 * participated in an action (e.g. posting a comment) and only provided his
	 * name and email address.
	 *
	 * @param  companyId the primary key of the user's company
	 * @param  autoPassword whether a password should be automatically
	 *         generated for the user
	 * @param  password1 the user's password
	 * @param  password2 the user's password confirmation
	 * @param  autoScreenName whether a screen name should be automatically
	 *         generated for the user
	 * @param  screenName the user's screen name
	 * @param  emailAddress the user's email address
	 * @param  facebookId the user's facebook ID
	 * @param  openId the user's OpenID
	 * @param  locale the user's locale
	 * @param  firstName the user's first name
	 * @param  middleName the user's middle name
	 * @param  lastName the user's last name
	 * @param  prefixId the user's name prefix ID
	 * @param  suffixId the user's name suffix ID
	 * @param  male whether the user is male
	 * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
	 *         January)
	 * @param  birthdayDay the user's birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  jobTitle the user's job title
	 * @param  updateUserInformation whether to update the user's information
	 * @param  sendEmail whether to send the user an email notification about
	 *         their new account
	 * @param  serviceContext the user's service context (optionally
	 *         <code>null</code>). Can specify the user's expando bridge
	 *         attributes.
	 * @return the user
	 * @throws PortalException if the user's information was invalid or if the
	 *         email address was reserved
	 * @throws SystemException if a system exception occurred
	 */
	public User updateIncompleteUser(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, Locale locale,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, boolean updateUserInformation,
			boolean sendEmail, ServiceContext serviceContext)
		throws PortalException, SystemException {

		long creatorUserId = 0;

		try {
			creatorUserId = getUserId();
		}
		catch (PrincipalException pe) {
		}

		checkAddUserPermission(
			creatorUserId, companyId, emailAddress, null, serviceContext);

		return userLocalService.updateIncompleteUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			updateUserInformation, sendEmail, serviceContext);
	}

	/**
	 * Sets the users in the role, removing and adding users to the role as
	 * necessary.
	 *
	 * @param  roleId the primary key of the role
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if the current user did not have permission to
	 *         assign role members
	 * @throws SystemException if a system exception occurred
	 */
	public void setRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.setRoleUsers(roleId, userIds);
	}

	/**
	 * Sets the users in the user group, removing and adding users to the user
	 * group as necessary.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if the current user did not have permission to
	 *         assign group members
	 * @throws SystemException if a system exception occurred
	 */
	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.setUserGroupUsers(userGroupId, userIds);
	}

	/**
	 * Removes the users from the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if the current user did not have permission to
	 *         modify group assignments
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetGroupUsers(long groupId, long[] userIds)
		throws PortalException, SystemException {

		try {
			GroupPermissionUtil.check(
				getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);
		}
		catch (PrincipalException pe) {

			// Allow any user to leave open and restricted sites

			boolean hasPermission = false;

			if (userIds.length == 0) {
				hasPermission = true;
			}
			else if (userIds.length == 1) {
				User user = getUser();

				if (user.getUserId() == userIds[0]) {
					Group group = groupPersistence.findByPrimaryKey(groupId);

					if (user.getCompanyId() == group.getCompanyId()) {
						int type = group.getType();

						if ((type == GroupConstants.TYPE_SITE_OPEN) ||
							(type == GroupConstants.TYPE_SITE_RESTRICTED)) {

							hasPermission = true;
						}
					}
				}
			}

			if (!hasPermission) {
				throw new PrincipalException();
			}
		}

		userLocalService.unsetGroupUsers(groupId, userIds);
	}

	/**
	 * Removes the users from the organization.
	 *
	 * @param  organizationId the primary key of the organization
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if the current user did not have permission to
	 *         modify organization assignments
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId,
			ActionKeys.ASSIGN_MEMBERS);

		userLocalService.unsetOrganizationUsers(organizationId, userIds);
	}

	/**
	 * Removes the users from the password policy.
	 *
	 * @param  passwordPolicyId the primary key of the password policy
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if the current user did not have permission to
	 *         modify policy assignments
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws PortalException, SystemException {

		PasswordPolicyPermissionUtil.check(
			getPermissionChecker(), passwordPolicyId,
			ActionKeys.ASSIGN_MEMBERS);

		userLocalService.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	/**
	 * Removes the users from the role.
	 *
	 * @param  roleId the primary key of the role
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if the current user did not have permission to
	 *         modify role assignments
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetRoleUsers(long roleId, long[] userIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.unsetRoleUsers(roleId, userIds);
	}

	/**
	 * Removes the users from the team.
	 *
	 * @param  teamId the primary key of the team
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if the current user did not have permission to
	 *         modify team assignments
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetTeamUsers(long teamId, long[] userIds)
		throws PortalException, SystemException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.unsetTeamUsers(teamId, userIds);
	}

	/**
	 * Removes the users from the user group.
	 *
	 * @param  userGroupId the primary key of the user group
	 * @param  userIds the primary keys of the users
	 * @throws PortalException if the current user did not have permission to
	 *         modify user group assignments
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws PortalException, SystemException {

		UserGroupPermissionUtil.check(
			getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_MEMBERS);

		userLocalService.unsetUserGroupUsers(userGroupId, userIds);
	}

	/**
	 * Updates the user's response to the terms of use agreement.
	 *
	 * @param  userId the primary key of the user
	 * @param  agreedToTermsOfUse whether the user has agree to the terms of
	 *         use
	 * @return the user
	 * @throws PortalException if the current user did not have permission to
	 *         update the user's agreement to terms-of-use
	 * @throws SystemException if a system exception occurred
	 */
	public User updateAgreedToTermsOfUse(
			long userId, boolean agreedToTermsOfUse)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		return userLocalService.updateAgreedToTermsOfUse(
			userId, agreedToTermsOfUse);
	}

	/**
	 * Updates the user's email address.
	 *
	 * @param  userId the primary key of the user
	 * @param  password the user's password
	 * @param  emailAddress1 the user's new email address
	 * @param  emailAddress2 the user's new email address confirmation
	 * @return the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found or if the current user did not have permission to update
	 *         the user
	 * @throws SystemException if a system exception occurred
	 */
	public User updateEmailAddress(
			long userId, String password, String emailAddress1,
			String emailAddress2)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		return userLocalService.updateEmailAddress(
			userId, password, emailAddress1, emailAddress2);
	}

	/**
	 * Updates whether the user is locked out from logging in.
	 *
	 * @param  userId the primary key of the user
	 * @param  lockout whether the user is locked out
	 * @return the user
	 * @throws PortalException if the user did not have permission to lock out
	 *         the user
	 * @throws SystemException if a system exception occurred
	 */
	public User updateLockoutById(long userId, boolean lockout)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.DELETE);

		return userLocalService.updateLockoutById(userId, lockout);
	}

	/**
	 * Updates the user's OpenID.
	 *
	 * @param  userId the primary key of the user
	 * @param  openId the new OpenID
	 * @return the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found or if the current user did not have permission to update
	 *         the user
	 * @throws SystemException if a system exception occurred
	 */
	public User updateOpenId(long userId, String openId)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		return userLocalService.updateOpenId(userId, openId);
	}

	/**
	 * Sets the organizations that the user is in, removing and adding
	 * organizations as necessary.
	 *
	 * @param  userId the primary key of the user
	 * @param  organizationIds the primary keys of the organizations
	 * @throws PortalException if a user with the primary key could not be
	 *         found or if the current user did not have permission to update
	 *         the user
	 * @throws SystemException if a system exception occurred
	 */
	public void updateOrganizations(
			long userId, long[] organizationIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		userLocalService.updateOrganizations(
			userId, organizationIds, serviceContext);
	}

	/**
	 * Updates the user's password without tracking or validation of the
	 * change.
	 *
	 * @param  userId the primary key of the user
	 * @param  password1 the user's new password
	 * @param  password2 the user's new password confirmation
	 * @param  passwordReset whether the user should be asked to reset their
	 *         password the next time they log in
	 * @return the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found or if the current user did not have permission to update
	 *         the user
	 * @throws SystemException if a system exception occurred
	 */
	public User updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		return userLocalService.updatePassword(
			userId, password1, password2, passwordReset);
	}

	/**
	 * Updates the user's portrait image.
	 *
	 * @param  userId the primary key of the user
	 * @param  bytes the new portrait image data
	 * @return the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the new portrait was invalid, or if the current user
	 *         did not have permission to update the user
	 * @throws SystemException if a system exception occurred
	 */
	public User updatePortrait(long userId, byte[] bytes)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		return userLocalService.updatePortrait(userId, bytes);
	}

	/**
	 * Updates the user's password reset question and answer.
	 *
	 * @param  userId the primary key of the user
	 * @param  question the user's new password reset question
	 * @param  answer the user's new password reset answer
	 * @return the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the new question or answer were invalid, or if the
	 *         current user did not have permission to update the user
	 * @throws SystemException if a system exception occurred
	 */
	public User updateReminderQuery(
			long userId, String question, String answer)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		return userLocalService.updateReminderQuery(userId, question, answer);
	}

	/**
	 * Updates the user's screen name.
	 *
	 * @param  userId the primary key of the user
	 * @param  screenName the user's new screen name
	 * @return the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the new screen name was invalid, or if the current
	 *         user did not have permission to update the user
	 * @throws SystemException if a system exception occurred
	 */
	public User updateScreenName(long userId, String screenName)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.UPDATE);

		return userLocalService.updateScreenName(userId, screenName);
	}

	/**
	 * Updates the user's workflow status.
	 *
	 * @param  userId the primary key of the user
	 * @param  status the user's new workflow status
	 * @return the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the current user was updating her own status to
	 *         anything but {@link WorkflowConstants.STATUS_APPROVED}, or if
	 *         the current user did not have permission to update the user's
	 *         workflow status.
	 * @throws SystemException if a system exception occurred
	 */
	public User updateStatus(long userId, int status)
		throws PortalException, SystemException {

		if ((getUserId() == userId) &&
			(status != WorkflowConstants.STATUS_APPROVED)) {

			throw new RequiredUserException();
		}

		UserPermissionUtil.check(
			getPermissionChecker(), userId, ActionKeys.DELETE);

		return userLocalService.updateStatus(userId, status);
	}

	/**
	 * Updates the user with additional parameters.
	 *
	 * @param  userId the primary key of the user
	 * @param  oldPassword the user's old password
	 * @param  newPassword1 the user's new password (optionally
	 *         <code>null</code>)
	 * @param  newPassword2 the user's new password confirmation (optionally
	 *         <code>null</code>)
	 * @param  passwordReset whether the user should be asked to reset their
	 *         password the next time they login
	 * @param  reminderQueryQuestion the user's new password reset question
	 * @param  reminderQueryAnswer the user's new password reset answer
	 * @param  screenName the user's new screen name
	 * @param  emailAddress the user's new email address
	 * @param  facebookId the user's new Facebook ID
	 * @param  openId the user's new OpenID
	 * @param  languageId the user's new language ID
	 * @param  timeZoneId the user's new time zone ID
	 * @param  greeting the user's new greeting
	 * @param  comments the user's new comments
	 * @param  firstName the user's new first name
	 * @param  middleName the user's new middle name
	 * @param  lastName the user's new last name
	 * @param  prefixId the user's new name prefix ID
	 * @param  suffixId the user's new name suffix ID
	 * @param  male whether user is male
	 * @param  birthdayMonth the user's new birthday month (0-based, meaning 0
	 *         for January)
	 * @param  birthdayDay the user's new birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  smsSn the user's new SMS screen name
	 * @param  aimSn the user's new AIM screen name
	 * @param  facebookSn the user's new Facebook screen name
	 * @param  icqSn the user's new ICQ screen name
	 * @param  jabberSn the user's new Jabber screen name
	 * @param  msnSn the user's new MSN screen name
	 * @param  mySpaceSn the user's new MySpace screen name
	 * @param  skypeSn the user's new Skype screen name
	 * @param  twitterSn the user's new Twitter screen name
	 * @param  ymSn the user's new Yahoo! Messenger screen name
	 * @param  jobTitle the user's new job title
	 * @param  groupIds the primary keys of the user's groups
	 * @param  organizationIds the primary keys of the user's organizations
	 * @param  roleIds the primary keys of the user's roles
	 * @param  userGroupRoles the user user's group roles
	 * @param  userGroupIds the primary keys of the user's user groups
	 * @param  addresses the user's addresses
	 * @param  emailAddresses the user's email addresses
	 * @param  phones the user's phone numbers
	 * @param  websites the user's websites
	 * @param  announcementsDelivers the announcements deliveries
	 * @param  serviceContext the user's service context (optionally
	 *         <code>null</code>). Can specify the user's universally unique
	 *         identifier (with the <code>uuid</code> attribute), replacement
	 *         asset category IDs, replacement asset tag names, and new expando
	 *         bridge attributes.
	 * @return the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the new information was invalid, or if the current
	 *         user did not have permission to update the user
	 * @throws SystemException if a system exception occurred
	 */
	public User updateUser(
			long userId, String oldPassword, String newPassword1,
			String newPassword2, boolean passwordReset,
			String reminderQueryQuestion, String reminderQueryAnswer,
			String screenName, String emailAddress, long facebookId,
			String openId, String languageId, String timeZoneId,
			String greeting, String comments, String firstName,
			String middleName, String lastName, int prefixId, int suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String aimSn, String facebookSn, String icqSn,
			String jabberSn, String msnSn, String mySpaceSn, String skypeSn,
			String twitterSn, String ymSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds,
			List<UserGroupRole> userGroupRoles, long[] userGroupIds,
			List<Address> addresses, List<EmailAddress> emailAddresses,
			List<Phone> phones, List<Website> websites,
			List<AnnouncementsDelivery> announcementsDelivers,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		UsersAdminUtil.updateAddresses(
			Contact.class.getName(), user.getContactId(), addresses);

		UsersAdminUtil.updateEmailAddresses(
			Contact.class.getName(), user.getContactId(), emailAddresses);

		UsersAdminUtil.updatePhones(
			Contact.class.getName(), user.getContactId(), phones);

		UsersAdminUtil.updateWebsites(
			Contact.class.getName(), user.getContactId(), websites);

		updateAnnouncementsDeliveries(user.getUserId(), announcementsDelivers);

		user = updateUser(
			userId, oldPassword, newPassword1, newPassword2, passwordReset,
			reminderQueryQuestion, reminderQueryAnswer, screenName,
			emailAddress, facebookId, openId, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
			icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
			jobTitle, groupIds, organizationIds, roleIds,
			userGroupRoles, userGroupIds, serviceContext);

		return user;
	}

	/**
	 * Updates the user.
	 *
	 * @param  userId the primary key of the user
	 * @param  oldPassword the user's old password
	 * @param  newPassword1 the user's new password (optionally
	 *         <code>null</code>)
	 * @param  newPassword2 the user's new password confirmation (optionally
	 *         <code>null</code>)
	 * @param  passwordReset whether the user should be asked to reset their
	 *         password the next time they login
	 * @param  reminderQueryQuestion the user's new password reset question
	 * @param  reminderQueryAnswer the user's new password reset answer
	 * @param  screenName the user's new screen name
	 * @param  emailAddress the user's new email address
	 * @param  facebookId the user's new Facebook ID
	 * @param  openId the user's new OpenID
	 * @param  languageId the user's new language ID
	 * @param  timeZoneId the user's new time zone ID
	 * @param  greeting the user's new greeting
	 * @param  comments the user's new comments
	 * @param  firstName the user's new first name
	 * @param  middleName the user's new middle name
	 * @param  lastName the user's new last name
	 * @param  prefixId the user's new name prefix ID
	 * @param  suffixId the user's new name suffix ID
	 * @param  male whether user is male
	 * @param  birthdayMonth the user's new birthday month (0-based, meaning 0
	 *         for January)
	 * @param  birthdayDay the user's new birthday day
	 * @param  birthdayYear the user's birthday year
	 * @param  smsSn the user's new SMS screen name
	 * @param  aimSn the user's new AIM screen name
	 * @param  facebookSn the user's new Facebook screen name
	 * @param  icqSn the user's new ICQ screen name
	 * @param  jabberSn the user's new Jabber screen name
	 * @param  msnSn the user's new MSN screen name
	 * @param  mySpaceSn the user's new MySpace screen name
	 * @param  skypeSn the user's new Skype screen name
	 * @param  twitterSn the user's new Twitter screen name
	 * @param  ymSn the user's new Yahoo! Messenger screen name
	 * @param  jobTitle the user's new job title
	 * @param  groupIds the primary keys of the user's groups
	 * @param  organizationIds the primary keys of the user's organizations
	 * @param  roleIds the primary keys of the user's roles
	 * @param  userGroupRoles the user user's group roles
	 * @param  userGroupIds the primary keys of the user's user groups
	 * @param  serviceContext the user's service context (optionally
	 *         <code>null</code>). Can specify the user's universally unique
	 *         identifier (with the <code>uuid</code> attribute), replacement
	 *         asset category IDs, replacement asset tag names, and new expando
	 *         bridge attributes.
	 * @return the user
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the new information was invalid, or if the current
	 *         user did not have permission to update the user
	 * @throws SystemException if a system exception occurred
	 */
	public User updateUser(
			long userId, String oldPassword, String newPassword1,
			String newPassword2, boolean passwordReset,
			String reminderQueryQuestion, String reminderQueryAnswer,
			String screenName, String emailAddress, long facebookId,
			String openId, String languageId, String timeZoneId,
			String greeting, String comments, String firstName,
			String middleName, String lastName, int prefixId, int suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String aimSn, String facebookSn, String icqSn,
			String jabberSn, String msnSn, String mySpaceSn, String skypeSn,
			String twitterSn, String ymSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds,
			List<UserGroupRole> userGroupRoles, long[] userGroupIds,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		UserPermissionUtil.check(
			getPermissionChecker(), userId, organizationIds, ActionKeys.UPDATE);

		long curUserId = getUserId();

		if (curUserId == userId) {
			User user = userPersistence.findByPrimaryKey(userId);

			screenName = screenName.trim().toLowerCase();

			if (!screenName.equalsIgnoreCase(user.getScreenName())) {
				validateScreenName(user, screenName);
			}

			emailAddress = emailAddress.trim().toLowerCase();

			if (!emailAddress.equalsIgnoreCase(user.getEmailAddress())) {
				validateEmailAddress(user, emailAddress);
			}
		}

		if (groupIds != null) {
			groupIds = checkGroups(userId, groupIds);
		}

		if (organizationIds != null) {
			organizationIds = checkOrganizations(userId, organizationIds);
		}

		if (roleIds != null) {
			roleIds = checkRoles(userId, roleIds);
		}

		if (userGroupRoles != null) {
			userGroupRoles = checkUserGroupRoles(userId, userGroupRoles);
		}

		return userLocalService.updateUser(
			userId, oldPassword, newPassword1, newPassword2, passwordReset,
			reminderQueryQuestion, reminderQueryAnswer, screenName,
			emailAddress, facebookId, openId, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
			icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
			jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, serviceContext);
	}

	protected void checkAddUserPermission(
			long creatorUserId, long companyId, String emailAddress,
			long[] organizationIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		boolean anonymousUser = GetterUtil.getBoolean(
			serviceContext.getAttribute("anonymousUser"));

		if ((creatorUserId != 0) ||
			(!company.isStrangers() && !anonymousUser)) {

			if (!PortalPermissionUtil.contains(
					getPermissionChecker(), ActionKeys.ADD_USER) &&
				!OrganizationPermissionUtil.contains(
					getPermissionChecker(), organizationIds,
					ActionKeys.ASSIGN_MEMBERS)) {

				throw new PrincipalException();
			}
		}

		if (creatorUserId == 0) {
			if (!company.isStrangersWithMx() &&
				company.hasCompanyMx(emailAddress)) {

				throw new ReservedUserEmailAddressException();
			}
		}
	}

	protected long[] checkGroups(long userId, long[] groupIds)
		throws PortalException, SystemException {

		// Add back any groups that the administrator does not have the rights
		// to remove and check that he has the permission to add any new group

		List<Group> oldGroups = groupLocalService.getUserGroups(userId);
		long[] oldGroupIds = new long[oldGroups.size()];

		for (int i = 0; i < oldGroups.size(); i++) {
			Group group = oldGroups.get(i);

			if (!ArrayUtil.contains(groupIds, group.getGroupId()) &&
				!GroupPermissionUtil.contains(
					getPermissionChecker(), group.getGroupId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				groupIds = ArrayUtil.append(groupIds, group.getGroupId());
			}

			oldGroupIds[i] = group.getGroupId();
		}

		for (long groupId : groupIds) {
			if (!ArrayUtil.contains(oldGroupIds, groupId)) {
				GroupPermissionUtil.check(
					getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);
			}
		}

		return groupIds;
	}

	protected long[] checkOrganizations(long userId, long[] organizationIds)
		throws PortalException, SystemException {

		// Add back any organizations that the administrator does not have the
		// rights to remove and check that he has the permission to add any new
		// organization

		List<Organization> oldOrganizations =
			organizationLocalService.getUserOrganizations(userId);
		long[] oldOrganizationIds = new long[oldOrganizations.size()];

		for (int i = 0; i < oldOrganizations.size(); i++) {
			Organization organization = oldOrganizations.get(i);

			if (!ArrayUtil.contains(
					organizationIds, organization.getOrganizationId()) &&
				!OrganizationPermissionUtil.contains(
					getPermissionChecker(), organization.getOrganizationId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				organizationIds = ArrayUtil.append(
					organizationIds, organization.getOrganizationId());
			}

			oldOrganizationIds[i] = organization.getOrganizationId();
		}

		for (long organizationId : organizationIds) {
			if (!ArrayUtil.contains(oldOrganizationIds, organizationId)) {
				OrganizationPermissionUtil.check(
					getPermissionChecker(), organizationId,
					ActionKeys.ASSIGN_MEMBERS);
			}
		}

		return organizationIds;
	}

	protected long[] checkRoles(long userId, long[] roleIds)
		throws PrincipalException, SystemException {

		// Add back any roles that the administrator does not have the rights to
		// remove and check that he has the permission to add any new role

		List<Role> oldRoles = roleLocalService.getUserRoles(userId);
		long[] oldRoleIds = new long[oldRoles.size()];

		for (int i = 0; i < oldRoles.size(); i++) {
			Role role = oldRoles.get(i);

			if (!ArrayUtil.contains(roleIds, role.getRoleId()) &&
				!RolePermissionUtil.contains(
					getPermissionChecker(), role.getRoleId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				roleIds = ArrayUtil.append(roleIds, role.getRoleId());
			}

			oldRoleIds[i] = role.getRoleId();
		}

		for (long roleId : roleIds) {
			if (!ArrayUtil.contains(oldRoleIds, roleId)) {
				RolePermissionUtil.check(
					getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);
			}
		}

		return roleIds;
	}

	protected List<UserGroupRole> checkUserGroupRoles(
			long userId, List<UserGroupRole> userGroupRoles)
		throws PortalException, SystemException {

		// Add back any group roles that the administrator does not have the
		// rights to remove

		List<UserGroupRole> oldUserGroupRoles =
			userGroupRoleLocalService.getUserGroupRoles(userId);

		for (UserGroupRole oldUserGroupRole : oldUserGroupRoles) {
			if (!userGroupRoles.contains(oldUserGroupRole) &&
				!UserGroupRolePermissionUtil.contains(
					getPermissionChecker(), oldUserGroupRole.getGroupId(),
					oldUserGroupRole.getRoleId())) {

				userGroupRoles.add(oldUserGroupRole);
			}
		}

		for (UserGroupRole userGroupRole : userGroupRoles) {
			if (!oldUserGroupRoles.contains(userGroupRole)) {
				UserGroupRolePermissionUtil.check(
					getPermissionChecker(), userGroupRole.getGroupId(),
					userGroupRole.getRoleId());
			}
		}

		return userGroupRoles;
	}

	protected void updateAnnouncementsDeliveries(
			long userId, List<AnnouncementsDelivery> announcementsDeliveries)
		throws PortalException, SystemException {

		for (AnnouncementsDelivery announcementsDelivery :
				announcementsDeliveries) {

			announcementsDeliveryService.updateDelivery(
				userId, announcementsDelivery.getType(),
				announcementsDelivery.getEmail(),
				announcementsDelivery.getSms(),
				announcementsDelivery.getWebsite());
		}
	}

	protected void validateEmailAddress(User user, String emailAddress)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!UsersAdminUtil.hasUpdateEmailAddress(permissionChecker, user)) {
			throw new UserEmailAddressException();
		}

		if (!user.hasCompanyMx() && user.hasCompanyMx(emailAddress)) {
			Company company = companyPersistence.findByPrimaryKey(
				user.getCompanyId());

			if (!company.isStrangersWithMx()) {
				throw new ReservedUserEmailAddressException();
			}
		}
	}

	protected void validateOrganizationUsers(long[] userIds)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!PropsValues.ORGANIZATIONS_ASSIGNMENT_STRICT ||
			permissionChecker.isCompanyAdmin()) {

			return;
		}

		List<Organization> organizations =
			organizationLocalService.getUserOrganizations(
				permissionChecker.getUserId());

		for (long userId : userIds) {
			boolean allowed = false;

			for (Organization organization : organizations) {
				boolean manageUsers = OrganizationPermissionUtil.contains(
					permissionChecker, organization, ActionKeys.MANAGE_USERS);
				boolean manageSuborganizations =
					OrganizationPermissionUtil.contains(
						permissionChecker, organization,
						ActionKeys.MANAGE_SUBORGANIZATIONS);

				if (!manageUsers && !manageSuborganizations) {
					continue;
				}

				boolean inherited = false;
				boolean includeSpecifiedOrganization = false;

				if (manageUsers && manageSuborganizations) {
					inherited = true;
					includeSpecifiedOrganization = true;
				}
				else if (!manageUsers && manageSuborganizations) {
					inherited = true;
					includeSpecifiedOrganization = false;
				}

				if (organizationLocalService.hasUserOrganization(
						userId, organization.getOrganizationId(), inherited,
						false, includeSpecifiedOrganization)) {

					allowed = true;

					break;
				}
			}

			if (!allowed) {
				throw new PrincipalException();
			}
		}
	}

	protected void validateScreenName(User user, String screenName)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!UsersAdminUtil.hasUpdateScreenName(permissionChecker, user)) {
			throw new UserScreenNameException();
		}
	}

}