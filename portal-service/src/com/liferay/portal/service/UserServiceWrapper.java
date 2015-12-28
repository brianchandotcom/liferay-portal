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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link UserService}.
 *
 * @author Brian Wing Shun Chan
 * @see UserService
 * @generated
 */
@ProviderType
public class UserServiceWrapper implements UserService,
	ServiceWrapper<UserService> {
	public UserServiceWrapper(UserService userService) {
		_userService = userService;
	}

	/**
	* Adds the users to the group.
	*
	* @param groupId the primary key of the group
	* @param userIds the primary keys of the users
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>)
	*/
	@Override
	public void addGroupUsers(long groupId, long[] userIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.addGroupUsers(groupId, userIds, serviceContext);
	}

	/**
	* Adds the users to the organization.
	*
	* @param organizationId the primary key of the organization
	* @param userIds the primary keys of the users
	*/
	@Override
	public void addOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.addOrganizationUsers(organizationId, userIds);
	}

	/**
	* Assigns the password policy to the users, removing any other currently
	* assigned password policies.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param userIds the primary keys of the users
	*/
	@Override
	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	/**
	* Adds the users to the role.
	*
	* @param roleId the primary key of the role
	* @param userIds the primary keys of the users
	*/
	@Override
	public void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.addRoleUsers(roleId, userIds);
	}

	/**
	* Adds the users to the team.
	*
	* @param teamId the primary key of the team
	* @param userIds the primary keys of the users
	*/
	@Override
	public void addTeamUsers(long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.addTeamUsers(teamId, userIds);
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
	* @param companyId the primary key of the user's company
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param facebookId the user's facebook ID
	* @param openId the user's OpenID
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the roles this user possesses
	* @param userGroupIds the primary keys of the user's user groups
	* @param addresses the user's addresses
	* @param emailAddresses the user's email addresses
	* @param phones the user's phone numbers
	* @param websites the user's websites
	* @param announcementsDelivers the announcements deliveries
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the UUID (with the <code>uuid</code>
	attribute), asset category IDs, asset tag names, and expando
	bridge attributes for the user.
	* @return the new user
	*/
	@Override
	public com.liferay.portal.model.User addUser(long companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDelivers,
		boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.addUser(companyId, autoPassword, password1,
			password2, autoScreenName, screenName, emailAddress, facebookId,
			openId, locale, firstName, middleName, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			groupIds, organizationIds, roleIds, userGroupIds, addresses,
			emailAddresses, phones, websites, announcementsDelivers, sendEmail,
			serviceContext);
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
	* @param companyId the primary key of the user's company
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param facebookId the user's facebook ID
	* @param openId the user's OpenID
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the roles this user possesses
	* @param userGroupIds the primary keys of the user's user groups
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the UUID (with the <code>uuid</code>
	attribute), asset category IDs, asset tag names, and expando
	bridge attributes for the user.
	* @return the new user
	*/
	@Override
	public com.liferay.portal.model.User addUser(long companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.addUser(companyId, autoPassword, password1,
			password2, autoScreenName, screenName, emailAddress, facebookId,
			openId, locale, firstName, middleName, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
			serviceContext);
	}

	/**
	* Adds the users to the user group.
	*
	* @param userGroupId the primary key of the user group
	* @param userIds the primary keys of the users
	*/
	@Override
	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.addUserGroupUsers(userGroupId, userIds);
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
	* @param companyId the primary key of the user's company
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param facebookId the user's facebook ID
	* @param openId the user's OpenID
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the roles this user possesses
	* @param userGroupIds the primary keys of the user's user groups
	* @param addresses the user's addresses
	* @param emailAddresses the user's email addresses
	* @param phones the user's phone numbers
	* @param websites the user's websites
	* @param announcementsDelivers the announcements deliveries
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the UUID (with the <code>uuid</code>
	attribute), asset category IDs, asset tag names, and expando
	bridge attributes for the user.
	* @return the new user
	*/
	@Override
	public com.liferay.portal.model.User addUserWithWorkflow(long companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDelivers,
		boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.addUserWithWorkflow(companyId, autoPassword,
			password1, password2, autoScreenName, screenName, emailAddress,
			facebookId, openId, locale, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
			addresses, emailAddresses, phones, websites, announcementsDelivers,
			sendEmail, serviceContext);
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
	* @param companyId the primary key of the user's company
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param facebookId the user's facebook ID
	* @param openId the user's OpenID
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the roles this user possesses
	* @param userGroupIds the primary keys of the user's user groups
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the UUID (with the <code>uuid</code>
	attribute), asset category IDs, asset tag names, and expando
	bridge attributes for the user.
	* @return the new user
	*/
	@Override
	public com.liferay.portal.model.User addUserWithWorkflow(long companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.addUserWithWorkflow(companyId, autoPassword,
			password1, password2, autoScreenName, screenName, emailAddress,
			facebookId, openId, locale, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
			sendEmail, serviceContext);
	}

	/**
	* Deletes the user's portrait image.
	*
	* @param userId the primary key of the user
	*/
	@Override
	public void deletePortrait(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.deletePortrait(userId);
	}

	/**
	* Removes the user from the role.
	*
	* @param roleId the primary key of the role
	* @param userId the primary key of the user
	*/
	@Override
	public void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.deleteRoleUser(roleId, userId);
	}

	/**
	* Deletes the user.
	*
	* @param userId the primary key of the user
	*/
	@Override
	public void deleteUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.deleteUser(userId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.User> getCompanyUsers(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getCompanyUsers(companyId, start, end);
	}

	@Override
	public int getCompanyUsersCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getCompanyUsersCount(companyId);
	}

	@Override
	public com.liferay.portal.model.User getCurrentUser()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getCurrentUser();
	}

	/**
	* Returns the primary keys of all the users belonging to the group.
	*
	* @param groupId the primary key of the group
	* @return the primary keys of the users belonging to the group
	*/
	@Override
	public long[] getGroupUserIds(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getGroupUserIds(groupId);
	}

	/**
	* Returns all the users belonging to the group.
	*
	* @param groupId the primary key of the group
	* @return the users belonging to the group
	*/
	@Override
	public java.util.List<com.liferay.portal.model.User> getGroupUsers(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getGroupUsers(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _userService.getOSGiServiceIdentifier();
	}

	/**
	* Returns the primary keys of all the users belonging to the organization.
	*
	* @param organizationId the primary key of the organization
	* @return the primary keys of the users belonging to the organization
	*/
	@Override
	public long[] getOrganizationUserIds(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getOrganizationUserIds(organizationId);
	}

	/**
	* Returns all the users belonging to the organization.
	*
	* @param organizationId the primary key of the organization
	* @return users belonging to the organization
	*/
	@Override
	public java.util.List<com.liferay.portal.model.User> getOrganizationUsers(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getOrganizationUsers(organizationId);
	}

	/**
	* Returns the primary keys of all the users belonging to the role.
	*
	* @param roleId the primary key of the role
	* @return the primary keys of the users belonging to the role
	*/
	@Override
	public long[] getRoleUserIds(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getRoleUserIds(roleId);
	}

	/**
	* Returns the user with the email address.
	*
	* @param companyId the primary key of the user's company
	* @param emailAddress the user's email address
	* @return the user with the email address
	*/
	@Override
	public com.liferay.portal.model.User getUserByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getUserByEmailAddress(companyId, emailAddress);
	}

	/**
	* Returns the user with the primary key.
	*
	* @param userId the primary key of the user
	* @return the user with the primary key
	*/
	@Override
	public com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getUserById(userId);
	}

	/**
	* Returns the user with the screen name.
	*
	* @param companyId the primary key of the user's company
	* @param screenName the user's screen name
	* @return the user with the screen name
	*/
	@Override
	public com.liferay.portal.model.User getUserByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getUserByScreenName(companyId, screenName);
	}

	@Override
	public java.util.List<com.liferay.portal.model.User> getUserGroupUsers(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getUserGroupUsers(userGroupId);
	}

	/**
	* Returns the primary key of the user with the email address.
	*
	* @param companyId the primary key of the user's company
	* @param emailAddress the user's email address
	* @return the primary key of the user with the email address
	*/
	@Override
	public long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getUserIdByEmailAddress(companyId, emailAddress);
	}

	/**
	* Returns the primary key of the user with the screen name.
	*
	* @param companyId the primary key of the user's company
	* @param screenName the user's screen name
	* @return the primary key of the user with the screen name
	*/
	@Override
	public long getUserIdByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.getUserIdByScreenName(companyId, screenName);
	}

	/**
	* Returns <code>true</code> if the user is a member of the group.
	*
	* @param groupId the primary key of the group
	* @param userId the primary key of the user
	* @return <code>true</code> if the user is a member of the group;
	<code>false</code> otherwise
	*/
	@Override
	public boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.hasGroupUser(groupId, userId);
	}

	/**
	* Returns <code>true</code> if the user has the role with the name,
	* optionally through inheritance.
	*
	* @param companyId the primary key of the role's company
	* @param name the name of the role (must be a regular role, not an
	organization, site or provider role)
	* @param userId the primary key of the user
	* @param inherited whether to include roles inherited from organizations,
	sites, etc.
	* @return <code>true</code> if the user has the role; <code>false</code>
	otherwise
	*/
	@Override
	public boolean hasRoleUser(long companyId, java.lang.String name,
		long userId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.hasRoleUser(companyId, name, userId, inherited);
	}

	/**
	* Returns <code>true</code> if the user is a member of the role.
	*
	* @param roleId the primary key of the role
	* @param userId the primary key of the user
	* @return <code>true</code> if the user is a member of the role;
	<code>false</code> otherwise
	*/
	@Override
	public boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.hasRoleUser(roleId, userId);
	}

	/**
	* Sends a password notification email to the user matching the email
	* address. The portal's settings determine whether a password is sent
	* explicitly or whether a link for resetting the user's password is sent.
	* The method sends the email asynchronously and returns before the email is
	* sent.
	*
	* <p>
	* The content of the notification email is specified with the
	* <code>admin.email.password</code> portal property keys. They can be
	* overridden via a <code>portal-ext.properties</code> file or modified
	* through the Portal Settings UI.
	* </p>
	*
	* @param companyId the primary key of the user's company
	* @param emailAddress the user's email address
	* @return <code>true</code> if the notification email includes a new
	password; <code>false</code> if the notification email only
	contains a reset link
	*/
	@Override
	public boolean sendPasswordByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.sendPasswordByEmailAddress(companyId, emailAddress);
	}

	/**
	* Sends a password notification email to the user matching the screen name.
	* The portal's settings determine whether a password is sent explicitly or
	* whether a link for resetting the user's password is sent. The method
	* sends the email asynchronously and returns before the email is sent.
	*
	* <p>
	* The content of the notification email is specified with the
	* <code>admin.email.password</code> portal property keys. They can be
	* overridden via a <code>portal-ext.properties</code> file or modified
	* through the Portal Settings UI.
	* </p>
	*
	* @param companyId the primary key of the user's company
	* @param screenName the user's screen name
	* @return <code>true</code> if the notification email includes a new
	password; <code>false</code> if the notification email only
	contains a reset link
	*/
	@Override
	public boolean sendPasswordByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.sendPasswordByScreenName(companyId, screenName);
	}

	/**
	* Sends a password notification email to the user matching the ID. The
	* portal's settings determine whether a password is sent explicitly or
	* whether a link for resetting the user's password is sent. The method
	* sends the email asynchronously and returns before the email is sent.
	*
	* <p>
	* The content of the notification email is specified with the
	* <code>admin.email.password</code> portal property keys. They can be
	* overridden via a <code>portal-ext.properties</code> file or modified
	* through the Portal Settings UI.
	* </p>
	*
	* @param userId the user's primary key
	* @return <code>true</code> if the notification email includes a new
	password; <code>false</code> if the notification email only
	contains a reset link
	*/
	@Override
	public boolean sendPasswordByUserId(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.sendPasswordByUserId(userId);
	}

	/**
	* Sets the users in the role, removing and adding users to the role as
	* necessary.
	*
	* @param roleId the primary key of the role
	* @param userIds the primary keys of the users
	*/
	@Override
	public void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.setRoleUsers(roleId, userIds);
	}

	/**
	* Sets the users in the user group, removing and adding users to the user
	* group as necessary.
	*
	* @param userGroupId the primary key of the user group
	* @param userIds the primary keys of the users
	*/
	@Override
	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.setUserGroupUsers(userGroupId, userIds);
	}

	/**
	* Removes the users from the teams of a group.
	*
	* @param groupId the primary key of the group
	* @param userIds the primary keys of the users
	*/
	@Override
	public void unsetGroupTeamsUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.unsetGroupTeamsUsers(groupId, userIds);
	}

	/**
	* Removes the users from the group.
	*
	* @param groupId the primary key of the group
	* @param userIds the primary keys of the users
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>)
	*/
	@Override
	public void unsetGroupUsers(long groupId, long[] userIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.unsetGroupUsers(groupId, userIds, serviceContext);
	}

	/**
	* Removes the users from the organization.
	*
	* @param organizationId the primary key of the organization
	* @param userIds the primary keys of the users
	*/
	@Override
	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.unsetOrganizationUsers(organizationId, userIds);
	}

	/**
	* Removes the users from the password policy.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param userIds the primary keys of the users
	*/
	@Override
	public void unsetPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	/**
	* Removes the users from the role.
	*
	* @param roleId the primary key of the role
	* @param userIds the primary keys of the users
	*/
	@Override
	public void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.unsetRoleUsers(roleId, userIds);
	}

	/**
	* Removes the users from the team.
	*
	* @param teamId the primary key of the team
	* @param userIds the primary keys of the users
	*/
	@Override
	public void unsetTeamUsers(long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.unsetTeamUsers(teamId, userIds);
	}

	/**
	* Removes the users from the user group.
	*
	* @param userGroupId the primary key of the user group
	* @param userIds the primary keys of the users
	*/
	@Override
	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.unsetUserGroupUsers(userGroupId, userIds);
	}

	/**
	* Updates the user's response to the terms of use agreement.
	*
	* @param userId the primary key of the user
	* @param agreedToTermsOfUse whether the user has agree to the terms of use
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updateAgreedToTermsOfUse(long userId,
		boolean agreedToTermsOfUse)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateAgreedToTermsOfUse(userId, agreedToTermsOfUse);
	}

	/**
	* Updates the user's email address.
	*
	* @param userId the primary key of the user
	* @param password the user's password
	* @param emailAddress1 the user's new email address
	* @param emailAddress2 the user's new email address confirmation
	* @param serviceContext the service context to be applied. Must set the
	portal URL, main path, primary key of the layout, remote address,
	remote host, and agent for the user.
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updateEmailAddress(long userId,
		java.lang.String password, java.lang.String emailAddress1,
		java.lang.String emailAddress2,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateEmailAddress(userId, password, emailAddress1,
			emailAddress2, serviceContext);
	}

	/**
	* Updates a user account that was automatically created when a guest user
	* participated in an action (e.g. posting a comment) and only provided his
	* name and email address.
	*
	* @param companyId the primary key of the user's company
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param facebookId the user's facebook ID
	* @param openId the user's OpenID
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param updateUserInformation whether to update the user's information
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the expando bridge attributes for the
	user.
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updateIncompleteUser(long companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, boolean updateUserInformation,
		boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateIncompleteUser(companyId, autoPassword,
			password1, password2, autoScreenName, screenName, emailAddress,
			facebookId, openId, locale, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, updateUserInformation, sendEmail, serviceContext);
	}

	/**
	* Updates whether the user is locked out from logging in.
	*
	* @param userId the primary key of the user
	* @param lockout whether the user is locked out
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updateLockoutById(long userId,
		boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateLockoutById(userId, lockout);
	}

	/**
	* Updates the user's OpenID.
	*
	* @param userId the primary key of the user
	* @param openId the new OpenID
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updateOpenId(long userId,
		java.lang.String openId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateOpenId(userId, openId);
	}

	/**
	* Sets the organizations that the user is in, removing and adding
	* organizations as necessary.
	*
	* @param userId the primary key of the user
	* @param organizationIds the primary keys of the organizations
	* @param serviceContext the service context to be applied. Must set whether
	user indexing is enabled.
	*/
	@Override
	public void updateOrganizations(long userId, long[] organizationIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_userService.updateOrganizations(userId, organizationIds, serviceContext);
	}

	/**
	* Updates the user's password without tracking or validation of the change.
	*
	* @param userId the primary key of the user
	* @param password1 the user's new password
	* @param password2 the user's new password confirmation
	* @param passwordReset whether the user should be asked to reset their
	password the next time they log in
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updatePassword(userId, password1, password2,
			passwordReset);
	}

	/**
	* Updates the user's portrait image.
	*
	* @param userId the primary key of the user
	* @param bytes the new portrait image data
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updatePortrait(long userId,
		byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updatePortrait(userId, bytes);
	}

	/**
	* Updates the user's password reset question and answer.
	*
	* @param userId the primary key of the user
	* @param question the user's new password reset question
	* @param answer the user's new password reset answer
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updateReminderQuery(long userId,
		java.lang.String question, java.lang.String answer)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateReminderQuery(userId, question, answer);
	}

	/**
	* Updates the user's screen name.
	*
	* @param userId the primary key of the user
	* @param screenName the user's new screen name
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updateScreenName(long userId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateScreenName(userId, screenName);
	}

	/**
	* Updates the user's workflow status.
	*
	* @param userId the primary key of the user
	* @param status the user's new workflow status
	* @return the user
	* @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, int,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.User updateStatus(long userId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateStatus(userId, status);
	}

	/**
	* Updates the user's workflow status.
	*
	* @param userId the primary key of the user
	* @param status the user's new workflow status
	* @param serviceContext the service context to be applied. You can specify
	an unencrypted custom password (used by an LDAP listener) for the
	user via attribute <code>passwordUnencrypted</code>.
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updateStatus(long userId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateStatus(userId, status, serviceContext);
	}

	/**
	* Updates the user with additional parameters.
	*
	* @param userId the primary key of the user
	* @param oldPassword the user's old password
	* @param newPassword1 the user's new password (optionally
	<code>null</code>)
	* @param newPassword2 the user's new password confirmation (optionally
	<code>null</code>)
	* @param passwordReset whether the user should be asked to reset their
	password the next time they login
	* @param reminderQueryQuestion the user's new password reset question
	* @param reminderQueryAnswer the user's new password reset answer
	* @param screenName the user's new screen name
	* @param emailAddress the user's new email address
	* @param facebookId the user's new Facebook ID
	* @param openId the user's new OpenID
	* @param languageId the user's new language ID
	* @param timeZoneId the user's new time zone ID
	* @param greeting the user's new greeting
	* @param comments the user's new comments
	* @param firstName the user's new first name
	* @param middleName the user's new middle name
	* @param lastName the user's new last name
	* @param prefixId the user's new name prefix ID
	* @param suffixId the user's new name suffix ID
	* @param male whether user is male
	* @param birthdayMonth the user's new birthday month (0-based, meaning
	0 for January)
	* @param birthdayDay the user's new birthday day
	* @param birthdayYear the user's birthday year
	* @param smsSn the user's new SMS screen name
	* @param facebookSn the user's new Facebook screen name
	* @param jabberSn the user's new Jabber screen name
	* @param skypeSn the user's new Skype screen name
	* @param twitterSn the user's new Twitter screen name
	* @param jobTitle the user's new job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the user's roles
	* @param userGroupRoles the user user's group roles
	* @param userGroupIds the primary keys of the user's user groups
	* @param addresses the user's addresses
	* @param emailAddresses the user's email addresses
	* @param phones the user's phone numbers
	* @param websites the user's websites
	* @param announcementsDelivers the announcements deliveries
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the UUID (with the
	<code>uuid</code> attribute), asset category IDs, asset tag
	names, and expando bridge attributes for the user.
	* @return the user
	* @deprecated As of 7.0.0, replaced by {@link #updateUser(long, String,
	String, String, boolean, String, String, String, String,
	long, String, boolean, byte[], String, String, String,
	String, String, String, String, int, int, boolean, int, int,
	int, String, String, String, String, String, String, String,
	String, String, String, String, long[], long[], long[], List,
	long[], List, List, List, List, List, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, long facebookId,
		java.lang.String openId, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, long prefixId,
		long suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String facebookSn,
		java.lang.String jabberSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds,
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDelivers,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateUser(userId, oldPassword, newPassword1,
			newPassword2, passwordReset, reminderQueryQuestion,
			reminderQueryAnswer, screenName, emailAddress, facebookId, openId,
			languageId, timeZoneId, greeting, comments, firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, smsSn, facebookSn, jabberSn, skypeSn, twitterSn,
			jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, addresses, emailAddresses, phones, websites,
			announcementsDelivers, serviceContext);
	}

	/**
	* Updates the user.
	*
	* @param userId the primary key of the user
	* @param oldPassword the user's old password
	* @param newPassword1 the user's new password (optionally
	<code>null</code>)
	* @param newPassword2 the user's new password confirmation (optionally
	<code>null</code>)
	* @param passwordReset whether the user should be asked to reset their
	password the next time they login
	* @param reminderQueryQuestion the user's new password reset question
	* @param reminderQueryAnswer the user's new password reset answer
	* @param screenName the user's new screen name
	* @param emailAddress the user's new email address
	* @param facebookId the user's new Facebook ID
	* @param openId the user's new OpenID
	* @param languageId the user's new language ID
	* @param timeZoneId the user's new time zone ID
	* @param greeting the user's new greeting
	* @param comments the user's new comments
	* @param firstName the user's new first name
	* @param middleName the user's new middle name
	* @param lastName the user's new last name
	* @param prefixId the user's new name prefix ID
	* @param suffixId the user's new name suffix ID
	* @param male whether user is male
	* @param birthdayMonth the user's new birthday month (0-based, meaning 0
	for January)
	* @param birthdayDay the user's new birthday day
	* @param birthdayYear the user's birthday year
	* @param smsSn the user's new SMS screen name
	* @param facebookSn the user's new Facebook screen name
	* @param jabberSn the user's new Jabber screen name
	* @param skypeSn the user's new Skype screen name
	* @param twitterSn the user's new Twitter screen name
	* @param jobTitle the user's new job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the user's roles
	* @param userGroupRoles the user user's group roles
	* @param userGroupIds the primary keys of the user's user groups
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the UUID (with the <code>uuid</code>
	attribute), asset category IDs, asset tag names, and expando
	bridge attributes for the user.
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, long facebookId,
		java.lang.String openId, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, long prefixId,
		long suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String facebookSn,
		java.lang.String jabberSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds,
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateUser(userId, oldPassword, newPassword1,
			newPassword2, passwordReset, reminderQueryQuestion,
			reminderQueryAnswer, screenName, emailAddress, facebookId, openId,
			languageId, timeZoneId, greeting, comments, firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, smsSn, facebookSn, jabberSn, skypeSn, twitterSn,
			jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, serviceContext);
	}

	/**
	* Updates the user with additional parameters.
	*
	* @param userId the primary key of the user
	* @param oldPassword the user's old password
	* @param newPassword1 the user's new password (optionally
	<code>null</code>)
	* @param newPassword2 the user's new password confirmation (optionally
	<code>null</code>)
	* @param passwordReset whether the user should be asked to reset their
	password the next time they login
	* @param reminderQueryQuestion the user's new password reset question
	* @param reminderQueryAnswer the user's new password reset answer
	* @param screenName the user's new screen name
	* @param emailAddress the user's new email address
	* @param facebookId the user's new Facebook ID
	* @param openId the user's new OpenID
	* @param portrait whether to update the user's portrait image
	* @param portraitBytes the new portrait image data
	* @param languageId the user's new language ID
	* @param timeZoneId the user's new time zone ID
	* @param greeting the user's new greeting
	* @param comments the user's new comments
	* @param firstName the user's new first name
	* @param middleName the user's new middle name
	* @param lastName the user's new last name
	* @param prefixId the user's new name prefix ID
	* @param suffixId the user's new name suffix ID
	* @param male whether user is male
	* @param birthdayMonth the user's new birthday month (0-based, meaning 0
	for January)
	* @param birthdayDay the user's new birthday day
	* @param birthdayYear the user's birthday year
	* @param smsSn the user's new SMS screen name
	* @param facebookSn the user's new Facebook screen name
	* @param jabberSn the user's new Jabber screen name
	* @param skypeSn the user's new Skype screen name
	* @param twitterSn the user's new Twitter screen name
	* @param jobTitle the user's new job title
	* @param groupIds the primary keys of the user's groups
	* @param organizationIds the primary keys of the user's organizations
	* @param roleIds the primary keys of the user's roles
	* @param userGroupRoles the user user's group roles
	* @param userGroupIds the primary keys of the user's user groups
	* @param addresses the user's addresses
	* @param emailAddresses the user's email addresses
	* @param phones the user's phone numbers
	* @param websites the user's websites
	* @param announcementsDelivers the announcements deliveries
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the UUID (with the <code>uuid</code>
	attribute), asset category IDs, asset tag names, and expando
	bridge attributes for the user.
	* @return the user
	*/
	@Override
	public com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, long facebookId,
		java.lang.String openId, boolean portrait, byte[] portraitBytes,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String facebookSn,
		java.lang.String jabberSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds,
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDelivers,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _userService.updateUser(userId, oldPassword, newPassword1,
			newPassword2, passwordReset, reminderQueryQuestion,
			reminderQueryAnswer, screenName, emailAddress, facebookId, openId,
			portrait, portraitBytes, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, smsSn, facebookSn,
			jabberSn, skypeSn, twitterSn, jobTitle, groupIds, organizationIds,
			roleIds, userGroupRoles, userGroupIds, addresses, emailAddresses,
			phones, websites, announcementsDelivers, serviceContext);
	}

	@Override
	public UserService getWrappedService() {
		return _userService;
	}

	@Override
	public void setWrappedService(UserService userService) {
		_userService = userService;
	}

	private UserService _userService;
}