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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>UserServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.UserSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.User</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.UserSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserServiceHttp
 * @generated
 */
public class UserServiceSoap {

	/**
	 * Adds the users to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userIds the primary keys of the users
	 * @param serviceContext the service context to be applied (optionally
	 <code>null</code>)
	 */
	public static void addGroupUsers(
			long groupId, long[] userIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			UserServiceUtil.addGroupUsers(groupId, userIds, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Adds the users to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param userIds the primary keys of the users
	 */
	public static void addOrganizationUsers(long organizationId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.addOrganizationUsers(organizationId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Assigns the password policy to the users, removing any other currently
	 * assigned password policies.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @param userIds the primary keys of the users
	 */
	public static void addPasswordPolicyUsers(
			long passwordPolicyId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.addPasswordPolicyUsers(passwordPolicyId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Adds the users to the role.
	 *
	 * @param roleId the primary key of the role
	 * @param userIds the primary keys of the users
	 */
	public static void addRoleUsers(long roleId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.addRoleUsers(roleId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Adds the users to the team.
	 *
	 * @param teamId the primary key of the team
	 * @param userIds the primary keys of the users
	 */
	public static void addTeamUsers(long teamId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.addTeamUsers(teamId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
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
	public static com.liferay.portal.kernel.model.UserSoap addUser(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, String locale,
			String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.addUser(
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, LocaleUtil.fromLanguageId(locale), firstName,
					middleName, lastName, prefixId, suffixId, male,
					birthdayMonth, birthdayDay, birthdayYear, jobTitle,
					groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
					serviceContext);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.kernel.model.UserSoap addUser(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, String locale,
			String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			com.liferay.portal.kernel.model.AddressSoap[] addresses,
			com.liferay.portal.kernel.model.EmailAddressSoap[] emailAddresses,
			com.liferay.portal.kernel.model.PhoneSoap[] phones,
			com.liferay.portal.kernel.model.WebsiteSoap[] websites,
			com.liferay.announcements.kernel.model.AnnouncementsDeliverySoap[]
				announcementsDelivers,
			boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.addUser(
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, LocaleUtil.fromLanguageId(locale), firstName,
					middleName, lastName, prefixId, suffixId, male,
					birthdayMonth, birthdayDay, birthdayYear, jobTitle,
					groupIds, organizationIds, roleIds, userGroupIds,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.
						toModels(emailAddresses),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites),
					com.liferay.portlet.announcements.model.impl.
						AnnouncementsDeliveryModelImpl.toModels(
							announcementsDelivers),
					sendEmail, serviceContext);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Adds the users to the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param userIds the primary keys of the users
	 */
	public static void addUserGroupUsers(long userGroupId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.addUserGroupUsers(userGroupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
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
	public static com.liferay.portal.kernel.model.UserSoap addUserWithWorkflow(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, String locale,
			String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.addUserWithWorkflow(
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, LocaleUtil.fromLanguageId(locale), firstName,
					middleName, lastName, prefixId, suffixId, male,
					birthdayMonth, birthdayDay, birthdayYear, jobTitle,
					groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
					serviceContext);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
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
	public static com.liferay.portal.kernel.model.UserSoap addUserWithWorkflow(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, String locale,
			String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			com.liferay.portal.kernel.model.AddressSoap[] addresses,
			com.liferay.portal.kernel.model.EmailAddressSoap[] emailAddresses,
			com.liferay.portal.kernel.model.PhoneSoap[] phones,
			com.liferay.portal.kernel.model.WebsiteSoap[] websites,
			com.liferay.announcements.kernel.model.AnnouncementsDeliverySoap[]
				announcementsDelivers,
			boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.addUserWithWorkflow(
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, LocaleUtil.fromLanguageId(locale), firstName,
					middleName, lastName, prefixId, suffixId, male,
					birthdayMonth, birthdayDay, birthdayYear, jobTitle,
					groupIds, organizationIds, roleIds, userGroupIds,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.
						toModels(emailAddresses),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites),
					com.liferay.portlet.announcements.model.impl.
						AnnouncementsDeliveryModelImpl.toModels(
							announcementsDelivers),
					sendEmail, serviceContext);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Deletes the user's portrait image.
	 *
	 * @param userId the primary key of the user
	 */
	public static void deletePortrait(long userId) throws RemoteException {
		try {
			UserServiceUtil.deletePortrait(userId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Removes the user from the role.
	 *
	 * @param roleId the primary key of the role
	 * @param userId the primary key of the user
	 */
	public static void deleteRoleUser(long roleId, long userId)
		throws RemoteException {

		try {
			UserServiceUtil.deleteRoleUser(roleId, userId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Deletes the user.
	 *
	 * @param userId the primary key of the user
	 */
	public static void deleteUser(long userId) throws RemoteException {
		try {
			UserServiceUtil.deleteUser(userId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.UserSoap[] getCompanyUsers(
			long companyId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getCompanyUsers(companyId, start, end);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCompanyUsersCount(long companyId)
		throws RemoteException {

		try {
			int returnValue = UserServiceUtil.getCompanyUsersCount(companyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.UserSoap getCurrentUser()
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.getCurrentUser();

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the primary keys of all the users belonging to the group.
	 *
	 * @param groupId the primary key of the group
	 * @return the primary keys of the users belonging to the group
	 */
	public static long[] getGroupUserIds(long groupId) throws RemoteException {
		try {
			long[] returnValue = UserServiceUtil.getGroupUserIds(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns all the users belonging to the group.
	 *
	 * @param groupId the primary key of the group
	 * @return the users belonging to the group
	 */
	public static com.liferay.portal.kernel.model.UserSoap[] getGroupUsers(
			long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getGroupUsers(groupId);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the users belonging to a group.
	 *
	 * @param groupId the primary key of the group
	 * @param status the workflow status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param obc the comparator to order the users by (optionally
	 <code>null</code>)
	 * @return the matching users
	 */
	public static com.liferay.portal.kernel.model.UserSoap[] getGroupUsers(
			long groupId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.User> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getGroupUsers(groupId, status, start, end, obc);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the users belonging to a group.
	 *
	 * @param groupId the primary key of the group
	 * @param status the workflow status
	 * @param obc the comparator to order the users by (optionally
	 <code>null</code>)
	 * @return the matching users
	 */
	public static com.liferay.portal.kernel.model.UserSoap[] getGroupUsers(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.User> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getGroupUsers(groupId, status, obc);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the number of users with the status belonging to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param status the workflow status
	 * @return the number of users with the status belonging to the group
	 */
	public static int getGroupUsersCount(long groupId, int status)
		throws RemoteException {

		try {
			int returnValue = UserServiceUtil.getGroupUsersCount(
				groupId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.UserSoap[] getGtCompanyUsers(
			long gtUserId, long companyId, int size)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getGtCompanyUsers(gtUserId, companyId, size);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.UserSoap[]
			getGtOrganizationUsers(long gtUserId, long organizationId, int size)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getGtOrganizationUsers(
					gtUserId, organizationId, size);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.UserSoap[]
			getGtUserGroupUsers(long gtUserId, long userGroupId, int size)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getGtUserGroupUsers(
					gtUserId, userGroupId, size);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getOrganizationsAndUserGroupsUsersCount(
			long[] organizationIds, long[] userGroupIds)
		throws RemoteException {

		try {
			int returnValue =
				UserServiceUtil.getOrganizationsAndUserGroupsUsersCount(
					organizationIds, userGroupIds);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the primary keys of all the users belonging to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the primary keys of the users belonging to the organization
	 */
	public static long[] getOrganizationUserIds(long organizationId)
		throws RemoteException {

		try {
			long[] returnValue = UserServiceUtil.getOrganizationUserIds(
				organizationId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns all the users belonging to the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @return users belonging to the organization
	 */
	public static com.liferay.portal.kernel.model.UserSoap[]
			getOrganizationUsers(long organizationId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getOrganizationUsers(organizationId);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the users belonging to the organization with the status.
	 *
	 * @param organizationId the primary key of the organization
	 * @param status the workflow status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param obc the comparator to order the users by (optionally
	 <code>null</code>)
	 * @return the matching users
	 */
	public static com.liferay.portal.kernel.model.UserSoap[]
			getOrganizationUsers(
				long organizationId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.User> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getOrganizationUsers(
					organizationId, status, start, end, obc);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the users belonging to the organization with the status.
	 *
	 * @param organizationId the primary key of the organization
	 * @param status the workflow status
	 * @param obc the comparator to order the users by (optionally
	 <code>null</code>)
	 * @return the matching users
	 */
	public static com.liferay.portal.kernel.model.UserSoap[]
			getOrganizationUsers(
				long organizationId, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.User> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getOrganizationUsers(
					organizationId, status, obc);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the number of users with the status belonging to the
	 * organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param status the workflow status
	 * @return the number of users with the status belonging to the organization
	 */
	public static int getOrganizationUsersCount(long organizationId, int status)
		throws RemoteException {

		try {
			int returnValue = UserServiceUtil.getOrganizationUsersCount(
				organizationId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the primary keys of all the users belonging to the role.
	 *
	 * @param roleId the primary key of the role
	 * @return the primary keys of the users belonging to the role
	 */
	public static long[] getRoleUserIds(long roleId) throws RemoteException {
		try {
			long[] returnValue = UserServiceUtil.getRoleUserIds(roleId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the user with the email address.
	 *
	 * @param companyId the primary key of the user's company
	 * @param emailAddress the user's email address
	 * @return the user with the email address
	 */
	public static com.liferay.portal.kernel.model.UserSoap
			getUserByEmailAddress(long companyId, String emailAddress)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.getUserByEmailAddress(companyId, emailAddress);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the user with the primary key.
	 *
	 * @param userId the primary key of the user
	 * @return the user with the primary key
	 */
	public static com.liferay.portal.kernel.model.UserSoap getUserById(
			long userId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.getUserById(userId);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the user with the screen name.
	 *
	 * @param companyId the primary key of the user's company
	 * @param screenName the user's screen name
	 * @return the user with the screen name
	 */
	public static com.liferay.portal.kernel.model.UserSoap getUserByScreenName(
			long companyId, String screenName)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.getUserByScreenName(companyId, screenName);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.UserSoap[] getUserGroupUsers(
			long userGroupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getUserGroupUsers(userGroupId);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.UserSoap[] getUserGroupUsers(
			long userGroupId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.User> returnValue =
				UserServiceUtil.getUserGroupUsers(userGroupId, start, end);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the primary key of the user with the email address.
	 *
	 * @param companyId the primary key of the user's company
	 * @param emailAddress the user's email address
	 * @return the primary key of the user with the email address
	 */
	public static long getUserIdByEmailAddress(
			long companyId, String emailAddress)
		throws RemoteException {

		try {
			long returnValue = UserServiceUtil.getUserIdByEmailAddress(
				companyId, emailAddress);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the primary key of the user with the screen name.
	 *
	 * @param companyId the primary key of the user's company
	 * @param screenName the user's screen name
	 * @return the primary key of the user with the screen name
	 */
	public static long getUserIdByScreenName(long companyId, String screenName)
		throws RemoteException {

		try {
			long returnValue = UserServiceUtil.getUserIdByScreenName(
				companyId, screenName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns <code>true</code> if the user is a member of the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userId the primary key of the user
	 * @return <code>true</code> if the user is a member of the group;
	 <code>false</code> otherwise
	 */
	public static boolean hasGroupUser(long groupId, long userId)
		throws RemoteException {

		try {
			boolean returnValue = UserServiceUtil.hasGroupUser(groupId, userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns <code>true</code> if the user is a member of the role.
	 *
	 * @param roleId the primary key of the role
	 * @param userId the primary key of the user
	 * @return <code>true</code> if the user is a member of the role;
	 <code>false</code> otherwise
	 */
	public static boolean hasRoleUser(long roleId, long userId)
		throws RemoteException {

		try {
			boolean returnValue = UserServiceUtil.hasRoleUser(roleId, userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static boolean hasRoleUser(
			long companyId, String name, long userId, boolean inherited)
		throws RemoteException {

		try {
			boolean returnValue = UserServiceUtil.hasRoleUser(
				companyId, name, userId, inherited);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static boolean sendPasswordByEmailAddress(
			long companyId, String emailAddress)
		throws RemoteException {

		try {
			boolean returnValue = UserServiceUtil.sendPasswordByEmailAddress(
				companyId, emailAddress);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static boolean sendPasswordByScreenName(
			long companyId, String screenName)
		throws RemoteException {

		try {
			boolean returnValue = UserServiceUtil.sendPasswordByScreenName(
				companyId, screenName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static boolean sendPasswordByUserId(long userId)
		throws RemoteException {

		try {
			boolean returnValue = UserServiceUtil.sendPasswordByUserId(userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Sets the users in the role, removing and adding users to the role as
	 * necessary.
	 *
	 * @param roleId the primary key of the role
	 * @param userIds the primary keys of the users
	 */
	public static void setRoleUsers(long roleId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.setRoleUsers(roleId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Sets the users in the user group, removing and adding users to the user
	 * group as necessary.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param userIds the primary keys of the users
	 */
	public static void setUserGroupUsers(long userGroupId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.setUserGroupUsers(userGroupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Removes the users from the teams of a group.
	 *
	 * @param groupId the primary key of the group
	 * @param userIds the primary keys of the users
	 */
	public static void unsetGroupTeamsUsers(long groupId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.unsetGroupTeamsUsers(groupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Removes the users from the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userIds the primary keys of the users
	 * @param serviceContext the service context to be applied (optionally
	 <code>null</code>)
	 */
	public static void unsetGroupUsers(
			long groupId, long[] userIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			UserServiceUtil.unsetGroupUsers(groupId, userIds, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Removes the users from the organization.
	 *
	 * @param organizationId the primary key of the organization
	 * @param userIds the primary keys of the users
	 */
	public static void unsetOrganizationUsers(
			long organizationId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.unsetOrganizationUsers(organizationId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Removes the users from the password policy.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @param userIds the primary keys of the users
	 */
	public static void unsetPasswordPolicyUsers(
			long passwordPolicyId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Removes the users from the role.
	 *
	 * @param roleId the primary key of the role
	 * @param userIds the primary keys of the users
	 */
	public static void unsetRoleUsers(long roleId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.unsetRoleUsers(roleId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Removes the users from the team.
	 *
	 * @param teamId the primary key of the team
	 * @param userIds the primary keys of the users
	 */
	public static void unsetTeamUsers(long teamId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.unsetTeamUsers(teamId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Removes the users from the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param userIds the primary keys of the users
	 */
	public static void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws RemoteException {

		try {
			UserServiceUtil.unsetUserGroupUsers(userGroupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Updates the user's response to the terms of use agreement.
	 *
	 * @param userId the primary key of the user
	 * @param agreedToTermsOfUse whether the user has agree to the terms of use
	 * @return the user
	 */
	public static com.liferay.portal.kernel.model.UserSoap
			updateAgreedToTermsOfUse(long userId, boolean agreedToTermsOfUse)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updateAgreedToTermsOfUse(
					userId, agreedToTermsOfUse);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.kernel.model.UserSoap updateEmailAddress(
			long userId, String password, String emailAddress1,
			String emailAddress2,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updateEmailAddress(
					userId, password, emailAddress1, emailAddress2,
					serviceContext);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.kernel.model.UserSoap updateIncompleteUser(
			long companyId, boolean autoPassword, String password1,
			String password2, boolean autoScreenName, String screenName,
			String emailAddress, long facebookId, String openId, String locale,
			String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, boolean updateUserInformation,
			boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updateIncompleteUser(
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, LocaleUtil.fromLanguageId(locale), firstName,
					middleName, lastName, prefixId, suffixId, male,
					birthdayMonth, birthdayDay, birthdayYear, jobTitle,
					updateUserInformation, sendEmail, serviceContext);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Updates whether the user is locked out from logging in.
	 *
	 * @param userId the primary key of the user
	 * @param lockout whether the user is locked out
	 * @return the user
	 */
	public static com.liferay.portal.kernel.model.UserSoap updateLockoutById(
			long userId, boolean lockout)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updateLockoutById(userId, lockout);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Updates the user's OpenID.
	 *
	 * @param userId the primary key of the user
	 * @param openId the new OpenID
	 * @return the user
	 */
	public static com.liferay.portal.kernel.model.UserSoap updateOpenId(
			long userId, String openId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updateOpenId(userId, openId);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static void updateOrganizations(
			long userId, long[] organizationIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			UserServiceUtil.updateOrganizations(
				userId, organizationIds, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.kernel.model.UserSoap updatePassword(
			long userId, String password1, String password2,
			boolean passwordReset)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updatePassword(
					userId, password1, password2, passwordReset);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Updates the user's portrait image.
	 *
	 * @param userId the primary key of the user
	 * @param bytes the new portrait image data
	 * @return the user
	 */
	public static com.liferay.portal.kernel.model.UserSoap updatePortrait(
			long userId, byte[] bytes)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updatePortrait(userId, bytes);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Updates the user's password reset question and answer.
	 *
	 * @param userId the primary key of the user
	 * @param question the user's new password reset question
	 * @param answer the user's new password reset answer
	 * @return the user
	 */
	public static com.liferay.portal.kernel.model.UserSoap updateReminderQuery(
			long userId, String question, String answer)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updateReminderQuery(userId, question, answer);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Updates the user's screen name.
	 *
	 * @param userId the primary key of the user
	 * @param screenName the user's new screen name
	 * @return the user
	 */
	public static com.liferay.portal.kernel.model.UserSoap updateScreenName(
			long userId, String screenName)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updateScreenName(userId, screenName);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.kernel.model.UserSoap updateStatus(
			long userId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updateStatus(userId, status, serviceContext);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	 * @param hasPortrait if the user has a custom portrait image
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
	public static com.liferay.portal.kernel.model.UserSoap updateUser(
			long userId, String oldPassword, String newPassword1,
			String newPassword2, boolean passwordReset,
			String reminderQueryQuestion, String reminderQueryAnswer,
			String screenName, String emailAddress, long facebookId,
			String openId, boolean hasPortrait, byte[] portraitBytes,
			String languageId, String timeZoneId, String greeting,
			String comments, String firstName, String middleName,
			String lastName, long prefixId, long suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn,
			String facebookSn, String jabberSn, String skypeSn,
			String twitterSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds,
			com.liferay.portal.kernel.model.UserGroupRoleSoap[] userGroupRoles,
			long[] userGroupIds,
			com.liferay.portal.kernel.model.AddressSoap[] addresses,
			com.liferay.portal.kernel.model.EmailAddressSoap[] emailAddresses,
			com.liferay.portal.kernel.model.PhoneSoap[] phones,
			com.liferay.portal.kernel.model.WebsiteSoap[] websites,
			com.liferay.announcements.kernel.model.AnnouncementsDeliverySoap[]
				announcementsDelivers,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updateUser(
					userId, oldPassword, newPassword1, newPassword2,
					passwordReset, reminderQueryQuestion, reminderQueryAnswer,
					screenName, emailAddress, facebookId, openId, hasPortrait,
					portraitBytes, languageId, timeZoneId, greeting, comments,
					firstName, middleName, lastName, prefixId, suffixId, male,
					birthdayMonth, birthdayDay, birthdayYear, smsSn, facebookSn,
					jabberSn, skypeSn, twitterSn, jobTitle, groupIds,
					organizationIds, roleIds,
					com.liferay.portal.model.impl.UserGroupRoleModelImpl.
						toModels(userGroupRoles),
					userGroupIds,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.
						toModels(emailAddresses),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites),
					com.liferay.portlet.announcements.model.impl.
						AnnouncementsDeliveryModelImpl.toModels(
							announcementsDelivers),
					serviceContext);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.kernel.model.UserSoap updateUser(
			long userId, String oldPassword, String newPassword1,
			String newPassword2, boolean passwordReset,
			String reminderQueryQuestion, String reminderQueryAnswer,
			String screenName, String emailAddress, long facebookId,
			String openId, String languageId, String timeZoneId,
			String greeting, String comments, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String facebookSn, String jabberSn, String skypeSn,
			String twitterSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds,
			com.liferay.portal.kernel.model.UserGroupRoleSoap[] userGroupRoles,
			long[] userGroupIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.User returnValue =
				UserServiceUtil.updateUser(
					userId, oldPassword, newPassword1, newPassword2,
					passwordReset, reminderQueryQuestion, reminderQueryAnswer,
					screenName, emailAddress, facebookId, openId, languageId,
					timeZoneId, greeting, comments, firstName, middleName,
					lastName, prefixId, suffixId, male, birthdayMonth,
					birthdayDay, birthdayYear, smsSn, facebookSn, jabberSn,
					skypeSn, twitterSn, jobTitle, groupIds, organizationIds,
					roleIds,
					com.liferay.portal.model.impl.UserGroupRoleModelImpl.
						toModels(userGroupRoles),
					userGroupIds, serviceContext);

			return com.liferay.portal.kernel.model.UserSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserServiceSoap.class);

}