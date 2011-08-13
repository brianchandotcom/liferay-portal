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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.UserServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.UserServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.UserSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.User}, that is translated to a
 * {@link com.liferay.portal.model.UserSoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserServiceHttp
 * @see       com.liferay.portal.model.UserSoap
 * @see       com.liferay.portal.service.UserServiceUtil
 * @generated
 */
public class UserServiceSoap {
	public static void addGroupUsers(long groupId, long[] userIds)
		throws RemoteException {
		try {
			UserServiceUtil.addGroupUsers(groupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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

	public static void addPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds) throws RemoteException {
		try {
			UserServiceUtil.addPasswordPolicyUsers(passwordPolicyId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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

	public static com.liferay.portal.model.UserSoap addUser(long companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, String locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds,
		com.liferay.portal.model.AddressSoap[] addresses,
		com.liferay.portal.model.EmailAddressSoap[] emailAddresses,
		com.liferay.portal.model.PhoneSoap[] phones,
		com.liferay.portal.model.WebsiteSoap[] websites,
		com.liferay.portlet.announcements.model.AnnouncementsDeliverySoap[] announcementsDelivers,
		boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.addUser(companyId,
					autoPassword, password1, password2, autoScreenName,
					screenName, emailAddress, facebookId, openId,
					LocaleUtil.fromLanguageId(locale), firstName, middleName,
					lastName, prefixId, suffixId, male, birthdayMonth,
					birthdayDay, birthdayYear, jobTitle, groupIds,
					organizationIds, roleIds, userGroupIds,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.toModels(
						emailAddresses),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites),
					com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl.toModels(
						announcementsDelivers), sendEmail, serviceContext);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap addUser(long companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, String locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.addUser(companyId,
					autoPassword, password1, password2, autoScreenName,
					screenName, emailAddress, facebookId, openId,
					LocaleUtil.fromLanguageId(locale), firstName, middleName,
					lastName, prefixId, suffixId, male, birthdayMonth,
					birthdayDay, birthdayYear, jobTitle, groupIds,
					organizationIds, roleIds, userGroupIds, sendEmail,
					serviceContext);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap addUserWithWorkflow(
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, String locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds,
		com.liferay.portal.model.AddressSoap[] addresses,
		com.liferay.portal.model.EmailAddressSoap[] emailAddresses,
		com.liferay.portal.model.PhoneSoap[] phones,
		com.liferay.portal.model.WebsiteSoap[] websites,
		com.liferay.portlet.announcements.model.AnnouncementsDeliverySoap[] announcementsDelivers,
		boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.addUserWithWorkflow(companyId,
					autoPassword, password1, password2, autoScreenName,
					screenName, emailAddress, facebookId, openId,
					LocaleUtil.fromLanguageId(locale), firstName, middleName,
					lastName, prefixId, suffixId, male, birthdayMonth,
					birthdayDay, birthdayYear, jobTitle, groupIds,
					organizationIds, roleIds, userGroupIds,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.toModels(
						emailAddresses),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites),
					com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl.toModels(
						announcementsDelivers), sendEmail, serviceContext);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap addUserWithWorkflow(
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, String locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.addUserWithWorkflow(companyId,
					autoPassword, password1, password2, autoScreenName,
					screenName, emailAddress, facebookId, openId,
					LocaleUtil.fromLanguageId(locale), firstName, middleName,
					lastName, prefixId, suffixId, male, birthdayMonth,
					birthdayDay, birthdayYear, jobTitle, groupIds,
					organizationIds, roleIds, userGroupIds, sendEmail,
					serviceContext);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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

	public static void deletePortrait(long userId) throws RemoteException {
		try {
			UserServiceUtil.deletePortrait(userId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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

	public static void deleteUser(long userId) throws RemoteException {
		try {
			UserServiceUtil.deleteUser(userId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long getDefaultUserId(long companyId)
		throws RemoteException {
		try {
			long returnValue = UserServiceUtil.getDefaultUserId(companyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long[] getGroupUserIds(long groupId)
		throws RemoteException {
		try {
			long[] returnValue = UserServiceUtil.getGroupUserIds(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long[] getOrganizationUserIds(long organizationId)
		throws RemoteException {
		try {
			long[] returnValue = UserServiceUtil.getOrganizationUserIds(organizationId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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

	public static com.liferay.portal.model.UserSoap getUserByEmailAddress(
		long companyId, java.lang.String emailAddress)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.getUserByEmailAddress(companyId,
					emailAddress);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap getUserById(long userId)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.getUserById(userId);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap getUserByScreenName(
		long companyId, java.lang.String screenName) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.getUserByScreenName(companyId,
					screenName);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress) throws RemoteException {
		try {
			long returnValue = UserServiceUtil.getUserIdByEmailAddress(companyId,
					emailAddress);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long getUserIdByScreenName(long companyId,
		java.lang.String screenName) throws RemoteException {
		try {
			long returnValue = UserServiceUtil.getUserIdByScreenName(companyId,
					screenName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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

	public static boolean hasRoleUser(long companyId, java.lang.String name,
		long userId, boolean inherited) throws RemoteException {
		try {
			boolean returnValue = UserServiceUtil.hasRoleUser(companyId, name,
					userId, inherited);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateIncompleteUser(
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, String locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, boolean updateUserInformation,
		boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateIncompleteUser(companyId,
					autoPassword, password1, password2, autoScreenName,
					screenName, emailAddress, facebookId, openId,
					LocaleUtil.fromLanguageId(locale), firstName, middleName,
					lastName, prefixId, suffixId, male, birthdayMonth,
					birthdayDay, birthdayYear, jobTitle, updateUserInformation,
					sendEmail, serviceContext);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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

	public static void unsetGroupUsers(long groupId, long[] userIds)
		throws RemoteException {
		try {
			UserServiceUtil.unsetGroupUsers(groupId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetOrganizationUsers(long organizationId,
		long[] userIds) throws RemoteException {
		try {
			UserServiceUtil.unsetOrganizationUsers(organizationId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds) throws RemoteException {
		try {
			UserServiceUtil.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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

	public static com.liferay.portal.model.UserSoap updateAgreedToTermsOfUse(
		long userId, boolean agreedToTermsOfUse) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateAgreedToTermsOfUse(userId,
					agreedToTermsOfUse);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateEmailAddress(
		long userId, java.lang.String password, java.lang.String emailAddress1,
		java.lang.String emailAddress2) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateEmailAddress(userId,
					password, emailAddress1, emailAddress2);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateLockoutById(
		long userId, boolean lockout) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateLockoutById(userId,
					lockout);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateOpenId(long userId,
		java.lang.String openId) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateOpenId(userId,
					openId);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateOrganizations(long userId, long[] organizationIds)
		throws RemoteException {
		try {
			UserServiceUtil.updateOrganizations(userId, organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updatePassword(
		long userId, java.lang.String password1, java.lang.String password2,
		boolean passwordReset) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updatePassword(userId,
					password1, password2, passwordReset);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updatePortrait(
		long userId, byte[] bytes) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updatePortrait(userId,
					bytes);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateReminderQuery(
		long userId, java.lang.String question, java.lang.String answer)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateReminderQuery(userId,
					question, answer);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateScreenName(
		long userId, java.lang.String screenName) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateScreenName(userId,
					screenName);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateStatus(long userId,
		int status) throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateStatus(userId,
					status);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, long facebookId,
		java.lang.String openId, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		com.liferay.portal.model.UserGroupRoleSoap[] userGroupRoles,
		long[] userGroupIds, com.liferay.portal.model.AddressSoap[] addresses,
		com.liferay.portal.model.EmailAddressSoap[] emailAddresses,
		com.liferay.portal.model.PhoneSoap[] phones,
		com.liferay.portal.model.WebsiteSoap[] websites,
		com.liferay.portlet.announcements.model.AnnouncementsDeliverySoap[] announcementsDelivers,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateUser(userId,
					oldPassword, newPassword1, newPassword2, passwordReset,
					reminderQueryQuestion, reminderQueryAnswer, screenName,
					emailAddress, facebookId, openId, languageId, timeZoneId,
					greeting, comments, firstName, middleName, lastName,
					prefixId, suffixId, male, birthdayMonth, birthdayDay,
					birthdayYear, smsSn, aimSn, facebookSn, icqSn, jabberSn,
					msnSn, mySpaceSn, skypeSn, twitterSn, ymSn, jobTitle,
					groupIds, organizationIds, roleIds,
					com.liferay.portal.model.impl.UserGroupRoleModelImpl.toModels(
						userGroupRoles), userGroupIds,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.toModels(
						emailAddresses),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites),
					com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl.toModels(
						announcementsDelivers), serviceContext);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserSoap updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, long facebookId,
		java.lang.String openId, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		com.liferay.portal.model.UserGroupRoleSoap[] userGroupRoles,
		long[] userGroupIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.User returnValue = UserServiceUtil.updateUser(userId,
					oldPassword, newPassword1, newPassword2, passwordReset,
					reminderQueryQuestion, reminderQueryAnswer, screenName,
					emailAddress, facebookId, openId, languageId, timeZoneId,
					greeting, comments, firstName, middleName, lastName,
					prefixId, suffixId, male, birthdayMonth, birthdayDay,
					birthdayYear, smsSn, aimSn, facebookSn, icqSn, jabberSn,
					msnSn, mySpaceSn, skypeSn, twitterSn, ymSn, jobTitle,
					groupIds, organizationIds, roleIds,
					com.liferay.portal.model.impl.UserGroupRoleModelImpl.toModels(
						userGroupRoles), userGroupIds, serviceContext);

			return com.liferay.portal.model.UserSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserServiceSoap.class);
}