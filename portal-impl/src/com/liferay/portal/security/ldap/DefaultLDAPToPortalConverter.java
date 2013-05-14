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

package com.liferay.portal.security.ldap;

import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.kernel.ldap.LDAPUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.service.ListTypeServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.ContactUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PrefsPropsUtil;

import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;
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
public class DefaultLDAPToPortalConverter implements LDAPToPortalConverter {

	@Override
	public LDAPGroup importLDAPGroup(
			long companyId, Attributes attributes, Properties groupMappings)
		throws Exception {

		LDAPGroup ldapGroup = new LDAPGroup();

		ldapGroup.setCompanyId(companyId);

		String description = LDAPUtil.getAttributeString(
			attributes, groupMappings, GroupConverterKeys.DESCRIPTION);

		ldapGroup.setDescription(description);

		String groupName = LDAPUtil.getAttributeString(
			attributes, groupMappings, GroupConverterKeys.GROUP_NAME).
				toLowerCase();

		ldapGroup.setGroupName(groupName);

		return ldapGroup;
	}

	@Override
	public LDAPUser importLDAPUser(
			long companyId, Attributes attributes, Properties userMappings,
			Properties userExpandoMappings, Properties contactMappings,
			Properties contactExpandoMappings, String password)
		throws Exception {

		boolean autoScreenName = PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE);

		String screenName = LDAPUtil.getAttributeString(
			attributes, userMappings, UserConverterKeys.SCREEN_NAME).
				toLowerCase();
		String emailAddress = LDAPUtil.getAttributeString(
			attributes, userMappings, UserConverterKeys.EMAIL_ADDRESS);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Screen name " + screenName + " and email address " +
					emailAddress);
		}

		String firstName = LDAPUtil.getAttributeString(
			attributes, userMappings, UserConverterKeys.FIRST_NAME);
		String middleName = LDAPUtil.getAttributeString(
			attributes, userMappings, UserConverterKeys.MIDDLE_NAME);
		String lastName = LDAPUtil.getAttributeString(
			attributes, userMappings, UserConverterKeys.LAST_NAME);

		if (Validator.isNull(firstName) || Validator.isNull(lastName)) {
			String fullName = LDAPUtil.getAttributeString(
				attributes, userMappings, UserConverterKeys.FULL_NAME);

			FullNameGenerator fullNameGenerator =
				FullNameGeneratorFactory.getInstance();

			String[] names = fullNameGenerator.splitFullName(fullName);

			firstName = names[0];
			middleName = names[1];
			lastName = names[2];
		}

		if (!autoScreenName && Validator.isNull(screenName)) {
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

		LDAPUser ldapUser = new LDAPUser();

		ldapUser.setAutoPassword(password.equals(StringPool.BLANK));
		ldapUser.setAutoScreenName(autoScreenName);

		Contact contact = ContactUtil.create(0);

		int prefixId = getListTypeId(
			attributes, contactMappings, ContactConverterKeys.PREFIX,
			ListTypeConstants.CONTACT_PREFIX);

		contact.setPrefixId(prefixId);

		int suffixId = getListTypeId(
			attributes, contactMappings, ContactConverterKeys.SUFFIX,
			ListTypeConstants.CONTACT_SUFFIX);

		contact.setSuffixId(suffixId);

		String gender = LDAPUtil.getAttributeString(
			attributes, contactMappings, ContactConverterKeys.GENDER);

		gender = gender.toLowerCase();

		if (GetterUtil.getBoolean(gender) || gender.equals("female")) {
			contact.setMale(false);
		}
		else {
			contact.setMale(true);
		}

		try {
			Date birthday = DateUtil.parseDate(
				LDAPUtil.getAttributeString(
					attributes, contactMappings, ContactConverterKeys.BIRTHDAY),
				LocaleUtil.getDefault());

			contact.setBirthday(birthday);
		}
		catch (ParseException pe) {
			Calendar birthdayCalendar = CalendarFactoryUtil.getCalendar(
				1970, Calendar.JANUARY, 1);

			contact.setBirthday(birthdayCalendar.getTime());
		}

		contact.setSmsSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.SMS_SN));
		contact.setAimSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.AIM_SN));
		contact.setFacebookSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.FACEBOOK_SN));
		contact.setIcqSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.ICQ_SN));
		contact.setJabberSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.JABBER_SN));
		contact.setMsnSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.MSN_SN));
		contact.setMySpaceSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.MYSPACE_SN));
		contact.setSkypeSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.SKYPE_SN));
		contact.setTwitterSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.TWITTER_SN));
		contact.setYmSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.YM_SN));
		contact.setJobTitle(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.JOB_TITLE));

		ldapUser.setContact(contact);

		Map<String, String[]> contactExpandoAttributes = getExpandoAttributes(
			attributes, contactExpandoMappings);

		ldapUser.setContactExpandoAttributes(contactExpandoAttributes);

		ldapUser.setCreatorUserId(0);
		ldapUser.setGroupIds(null);
		ldapUser.setOrganizationIds(null);
		ldapUser.setPasswordReset(false);

		Object portrait = LDAPUtil.getAttributeObject(
			attributes, userMappings.getProperty(UserConverterKeys.PORTRAIT));

		if (portrait != null) {
			byte[] portraitBytes = (byte[])portrait;

			if (portraitBytes.length > 0) {
				ldapUser.setPortraitBytes((byte[])portrait);
			}

			ldapUser.setUpdatePortrait(true);
		}

		ldapUser.setRoleIds(null);
		ldapUser.setSendEmail(false);

		ServiceContext serviceContext = new ServiceContext();

		String uuid = LDAPUtil.getAttributeString(
			attributes, userMappings, UserConverterKeys.UUID);

		serviceContext.setUuid(uuid);

		ldapUser.setServiceContext(serviceContext);

		ldapUser.setUpdatePassword(!password.equals(StringPool.BLANK));

		User user = UserUtil.create(0);

		user.setCompanyId(companyId);
		user.setEmailAddress(emailAddress);
		user.setFirstName(firstName);

		String jobTitle = LDAPUtil.getAttributeString(
			attributes, userMappings, UserConverterKeys.JOB_TITLE);

		user.setJobTitle(jobTitle);

		Locale locale = LocaleUtil.getDefault();

		user.setLanguageId(locale.toString());

		user.setLastName(lastName);
		user.setMiddleName(middleName);
		user.setOpenId(StringPool.BLANK);
		user.setPasswordUnencrypted(password);
		user.setScreenName(screenName);

		String status = LDAPUtil.getAttributeString(
			attributes, userMappings, UserConverterKeys.STATUS);

		if (Validator.isNotNull(status)) {
			user.setStatus(GetterUtil.getInteger(status));
		}

		ldapUser.setUser(user);

		Map<String, String[]> userExpandoAttributes = getExpandoAttributes(
			attributes, userExpandoMappings);

		ldapUser.setUserExpandoAttributes(userExpandoAttributes);

		ldapUser.setUserGroupIds(null);
		ldapUser.setUserGroupRoles(null);

		return ldapUser;
	}

	protected Map<String, String[]> getExpandoAttributes(
			Attributes attributes, Properties expandoMappings)
		throws NamingException {

		Map<String, String[]> expandoAttributes =
			new HashMap<String, String[]>();

		for (Object key : expandoMappings.keySet()) {
			String name = (String)key;

			String[] value = LDAPUtil.getAttributeStringArray(
				attributes, expandoMappings, name);

			if (value != null) {
				expandoAttributes.put(name, value);
			}
		}

		return expandoAttributes;
	}

	protected int getListTypeId(
			Attributes attributes, Properties contactMappings,
			String contactMappingsKey, String listTypeType)
		throws Exception {

		List<ListType> contactPrefixListTypes =
			ListTypeServiceUtil.getListTypes(listTypeType);

		String name = LDAPUtil.getAttributeString(
			attributes, contactMappings, contactMappingsKey);

		for (ListType listType : contactPrefixListTypes) {
			if (name.equals(listType.getName())) {
				return listType.getListTypeId();
			}
		}

		return 0;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultLDAPToPortalConverter.class);

}