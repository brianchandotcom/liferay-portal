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

package com.liferay.portal.security.ldap;

import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.util.ldap.LDAPUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class BaseLDAPToPortalConverter implements LDAPToPortalConverter {

	public LDAPGroup importLDAPGroup(
			long companyId, Attributes attributes, Properties groupMappings)
		throws Exception {

		String groupName = LDAPUtil.getAttributeValue(
			attributes, groupMappings, GroupConverterKeys.GROUP_NAME).
				toLowerCase();
		String description = LDAPUtil.getAttributeValue(
			attributes, groupMappings, GroupConverterKeys.DESCRIPTION);

		LDAPGroup ldapGroup = new LDAPGroup();

		ldapGroup.setCompanyId(companyId);
		ldapGroup.setDescription(description);
		ldapGroup.setGroupName(groupName);

		return ldapGroup;
	}

	public LDAPUser importLDAPUser(
			long companyId, Attributes attributes, Properties userMappings,
			Properties userExpandoMappings, Properties contactMappings,
			Properties contactExpandoMappings, String password)
		throws Exception {

		boolean autoPassword = false;
		boolean updatePassword = true;

		if (password.equals(StringPool.BLANK)) {
			autoPassword = true;
			updatePassword = false;
		}

		long creatorUserId = 0;
		boolean passwordReset = false;
		boolean autoScreenName = PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE);

		String screenName = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.SCREEN_NAME).
				toLowerCase();
		String emailAddress = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.EMAIL_ADDRESS);
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.FIRST_NAME);
		String middleName = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.MIDDLE_NAME);
		String lastName = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.LAST_NAME);
		String uuid = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.UUID);

		if (Validator.isNull(firstName) || Validator.isNull(lastName)) {
			String fullName = LDAPUtil.getAttributeValue(
				attributes, userMappings, UserConverterKeys.FULL_NAME);

			FullNameGenerator fullNameGenerator =
				FullNameGeneratorFactory.getInstance();

			String[] names = fullNameGenerator.splitFullName(fullName);

			firstName = names[0];
			middleName = names[1];
			lastName = names[2];
		}

		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = LDAPUtil.getAttributeValue(
			attributes, userMappings, UserConverterKeys.JOB_TITLE);
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUuid(uuid);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Screen name " + screenName + " and email address " +
					emailAddress);
		}

		if (Validator.isNull(screenName) && !autoScreenName) {
			throw new UserScreenNameException(
				"Screen name cannot be null for " +
					ContactConstants.getFullName(
						firstName, middleName, lastName));
		}

		if (Validator.isNull(emailAddress) &&
			PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.USERS_EMAIL_ADDRESS_REQUIRED)) {

			throw new UserEmailAddressException(
				"Email address cannot be null for " +
					ContactConstants.getFullName(
						firstName, middleName, lastName));
		}

		User user = new UserImpl();

		user.setCompanyId(companyId);
		user.setEmailAddress(emailAddress);
		user.setFirstName(firstName);
		user.setJobTitle(jobTitle);
		user.setLanguageId(locale.toString());
		user.setLastName(lastName);
		user.setMiddleName(middleName);
		user.setOpenId(openId);
		user.setPasswordUnencrypted(password);
		user.setScreenName(screenName);

		Map<String, String> userExpandoAttributes = getExpandoAttributes(
			attributes, userExpandoMappings);

		Contact contact = new ContactImpl();

		contact.setBirthday(
			CalendarFactoryUtil.getCalendar(
				birthdayYear, birthdayMonth, birthdayDay).getTime());
		contact.setMale(male);
		contact.setPrefixId(prefixId);
		contact.setSuffixId(suffixId);

		Map<String, String> contactExpandoAttributes = getExpandoAttributes(
			attributes, contactExpandoMappings);

		LDAPUser ldapUser = new LDAPUser();

		ldapUser.setAutoPassword(autoPassword);
		ldapUser.setAutoScreenName(autoScreenName);
		ldapUser.setContact(contact);
		ldapUser.setContactExpandoAttributes(contactExpandoAttributes);
		ldapUser.setCreatorUserId(creatorUserId);
		ldapUser.setGroupIds(groupIds);
		ldapUser.setOrganizationIds(organizationIds);
		ldapUser.setPasswordReset(passwordReset);
		ldapUser.setRoleIds(roleIds);
		ldapUser.setSendEmail(sendEmail);
		ldapUser.setServiceContext(serviceContext);
		ldapUser.setUpdatePassword(updatePassword);
		ldapUser.setUser(user);
		ldapUser.setUserExpandoAttributes(userExpandoAttributes);
		ldapUser.setUserGroupIds(userGroupIds);
		ldapUser.setUserGroupRoles(userGroupRoles);

		return ldapUser;
	}

	protected Map<String, String> getExpandoAttributes(
			Attributes attributes, Properties expandoMappings)
		throws NamingException {

		Map<String, String> expandoAttributes = new HashMap<String, String>();

		for (Object key : expandoMappings.keySet()) {
			String name = (String)key;

			String value = LDAPUtil.getAttributeValue(
				attributes, expandoMappings, name);

			if (Validator.isNotNull(value)) {
				expandoAttributes.put(name, value);
			}
		}

		return expandoAttributes;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseLDAPToPortalConverter.class);

}