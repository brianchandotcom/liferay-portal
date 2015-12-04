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

package com.liferay.portal.ldap.internal.exportimport;

import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.kernel.ldap.LDAPUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.ldap.ContactConverterKeys;
import com.liferay.portal.ldap.GroupConverterKeys;
import com.liferay.portal.ldap.UserConverterKeys;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.FullNameDefinition;
import com.liferay.portal.security.auth.FullNameDefinitionFactory;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.security.ldap.LDAPGroup;
import com.liferay.portal.security.ldap.LDAPToPortalConverter;
import com.liferay.portal.security.ldap.LDAPUser;
import com.liferay.portal.service.ListTypeService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.ContactPersistence;
import com.liferay.portal.service.persistence.UserPersistence;

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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = LDAPToPortalConverter.class)
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

		FullNameDefinition fullNameDefinition =
			FullNameDefinitionFactory.getInstance(LocaleUtil.getDefault());

		if (Validator.isNull(firstName) ||
			(fullNameDefinition.isFieldRequired("last-name") &&
			 Validator.isNull(lastName))) {

			String fullName = LDAPUtil.getAttributeString(
				attributes, userMappings, UserConverterKeys.FULL_NAME);

			FullNameGenerator fullNameGenerator =
				FullNameGeneratorFactory.getInstance();

			String[] names = fullNameGenerator.splitFullName(fullName);

			if (Validator.isNull(firstName)) {
				firstName = names[0];
			}

			if (Validator.isNull(middleName)) {
				middleName = names[1];
			}

			if (Validator.isNull(lastName)) {
				lastName = names[2];
			}
		}

		if (!autoScreenName && Validator.isNull(screenName)) {
			throw new UserScreenNameException.MustNotBeNull(
				ContactConstants.getFullName(firstName, middleName, lastName));
		}

		if (Validator.isNull(emailAddress) &&
			PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.USERS_EMAIL_ADDRESS_REQUIRED)) {

			throw new UserEmailAddressException.MustNotBeNull(
				ContactConstants.getFullName(firstName, middleName, lastName));
		}

		LDAPUser ldapUser = new LDAPUser();

		ldapUser.setAutoPassword(password.equals(StringPool.BLANK));
		ldapUser.setAutoScreenName(autoScreenName);

		Contact contact = _contactPersistence.create(0);

		long prefixId = getListTypeId(
			attributes, contactMappings, ContactConverterKeys.PREFIX,
			ListTypeConstants.CONTACT_PREFIX);

		contact.setPrefixId(prefixId);

		long suffixId = getListTypeId(
			attributes, contactMappings, ContactConverterKeys.SUFFIX,
			ListTypeConstants.CONTACT_SUFFIX);

		contact.setSuffixId(suffixId);

		String gender = LDAPUtil.getAttributeString(
			attributes, contactMappings, ContactConverterKeys.GENDER);

		gender = StringUtil.toLowerCase(gender);

		if (Validator.isNull(gender) || GetterUtil.getBoolean(gender) ||
			gender.equals("m") || gender.equals("male")) {

			contact.setMale(true);
		}
		else {
			contact.setMale(false);
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
		contact.setFacebookSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.FACEBOOK_SN));
		contact.setJabberSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.JABBER_SN));
		contact.setSkypeSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.SKYPE_SN));
		contact.setTwitterSn(
			LDAPUtil.getAttributeString(
				attributes, contactMappings, ContactConverterKeys.TWITTER_SN));
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

		User user = _userPersistence.create(0);

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

		Map<String, String[]> expandoAttributes = new HashMap<>();

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

	protected long getListTypeId(
			Attributes attributes, Properties contactMappings,
			String contactMappingsKey, String listTypeType)
		throws Exception {

		List<ListType> contactPrefixListTypes = _listTypeService.getListTypes(
			listTypeType);

		String name = LDAPUtil.getAttributeString(
			attributes, contactMappings, contactMappingsKey);

		for (ListType listType : contactPrefixListTypes) {
			if (name.equals(listType.getName())) {
				return listType.getListTypeId();
			}
		}

		return 0;
	}

	@Reference(unbind = "-")
	protected void setContactPersistence(
		ContactPersistence contactPersistence) {

		_contactPersistence = contactPersistence;
	}

	@Reference(unbind = "-")
	protected void setListTypeService(ListTypeService listTypeService) {
		_listTypeService = listTypeService;
	}

	@Reference(unbind = "-")
	protected void setUserPersistence(UserPersistence userPersistence) {
		_userPersistence = userPersistence;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultLDAPToPortalConverter.class);

	private volatile ContactPersistence _contactPersistence;
	private volatile ListTypeService _listTypeService;
	private volatile UserPersistence _userPersistence;

}