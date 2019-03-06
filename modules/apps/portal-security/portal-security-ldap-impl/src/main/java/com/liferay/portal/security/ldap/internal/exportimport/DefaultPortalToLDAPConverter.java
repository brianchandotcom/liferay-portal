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

package com.liferay.portal.security.ldap.internal.exportimport;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoConverterUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.auth.PasswordModificationThreadLocal;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.exportimport.UserOperation;
import com.liferay.portal.security.ldap.GroupConverterKeys;
import com.liferay.portal.security.ldap.PortalLDAP;
import com.liferay.portal.security.ldap.UserConverterKeys;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.exportimport.Modifications;
import com.liferay.portal.security.ldap.exportimport.PortalToLDAPConverter;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.ldap.Rdn;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Wesley Gong
 */
@Component(immediate = true, service = PortalToLDAPConverter.class)
public class DefaultPortalToLDAPConverter implements PortalToLDAPConverter {

	public DefaultPortalToLDAPConverter() {
		_reservedUserFieldNames.put(
			UserConverterKeys.GROUP, UserConverterKeys.GROUP);
		_reservedUserFieldNames.put(
			UserConverterKeys.PASSWORD, UserConverterKeys.PASSWORD);
		_reservedUserFieldNames.put(
			UserConverterKeys.PORTRAIT, UserConverterKeys.PORTRAIT);
		_reservedUserFieldNames.put(
			UserConverterKeys.SCREEN_NAME, UserConverterKeys.SCREEN_NAME);
	}

	@Override
	public String getGroupDNName(
			long ldapServerId, UserGroup userGroup, Properties groupMappings)
		throws Exception {

		Binding groupBinding = _portalLDAP.getGroup(
			ldapServerId, userGroup.getCompanyId(), userGroup.getName());

		if (groupBinding != null) {
			return groupBinding.getNameInNamespace();
		}

		StringBundler sb = new StringBundler(5);

		sb.append(
			GetterUtil.getString(
				groupMappings.getProperty(GroupConverterKeys.GROUP_NAME),
				_DEFAULT_DN));
		sb.append(StringPool.EQUAL);
		sb.append(Rdn.escapeValue(userGroup.getName()));
		sb.append(StringPool.COMMA);
		sb.append(
			_portalLDAP.getGroupsDN(ldapServerId, userGroup.getCompanyId()));

		return sb.toString();
	}

	@Override
	public Modifications getLDAPContactModifications(
			Contact contact, Map<String, Serializable> contactExpandoAttributes,
			Properties contactMappings, Properties contactExpandoMappings)
		throws Exception {

		if (contactMappings.isEmpty() && contactExpandoMappings.isEmpty()) {
			return null;
		}

		Modifications modifications = getModifications(
			contact, contactMappings, _reservedContactFieldNames);

		populateCustomAttributeModifications(
			contact, contact.getExpandoBridge(), contactExpandoAttributes,
			contactExpandoMappings, modifications);

		return modifications;
	}

	@Override
	public Attributes getLDAPGroupAttributes(
			long ldapServerId, UserGroup userGroup, User user,
			Properties groupMappings, Properties userMappings)
		throws Exception {

		Attributes attributes = new BasicAttributes(true);

		Attribute objectClassAttribute = new BasicAttribute(_OBJECT_CLASS);

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				userGroup.getCompanyId(), ldapServerId);

		String[] defaultObjectClassNames =
			ldapServerConfiguration.groupDefaultObjectClasses();

		for (String defaultObjectClassName : defaultObjectClassNames) {
			objectClassAttribute.add(defaultObjectClassName);
		}

		attributes.put(objectClassAttribute);

		addAttributeMapping(
			groupMappings.getProperty(GroupConverterKeys.GROUP_NAME),
			userGroup.getName(), attributes);
		addAttributeMapping(
			groupMappings.getProperty(GroupConverterKeys.DESCRIPTION),
			userGroup.getDescription(), attributes);
		addAttributeMapping(
			groupMappings.getProperty(GroupConverterKeys.USER),
			getUserDNName(ldapServerId, user, userMappings), attributes);

		return attributes;
	}

	@Override
	public Modifications getLDAPGroupModifications(
			long ldapServerId, UserGroup userGroup, User user,
			Properties groupMappings, Properties userMappings,
			UserOperation userOperation)
		throws Exception {

		Modifications modifications = getModifications(
			userGroup, groupMappings, new HashMap<String, String>());

		String groupDN = getGroupDNName(ldapServerId, userGroup, groupMappings);
		String userDN = getUserDNName(ldapServerId, user, userMappings);

		if (_portalLDAP.isGroupMember(
				ldapServerId, user.getCompanyId(), groupDN, userDN)) {

			if (userOperation == UserOperation.REMOVE) {
				modifications.addItem(
					DirContext.REMOVE_ATTRIBUTE,
					groupMappings.getProperty(GroupConverterKeys.USER), userDN);
			}
		}
		else {
			if (userOperation == UserOperation.ADD) {
				modifications.addItem(
					DirContext.ADD_ATTRIBUTE,
					groupMappings.getProperty(GroupConverterKeys.USER), userDN);
			}
		}

		return modifications;
	}

	@Override
	public Attributes getLDAPUserAttributes(
		long ldapServerId, User user, Properties userMappings) {

		Attributes attributes = new BasicAttributes(true);

		Attribute objectClassAttribute = new BasicAttribute(_OBJECT_CLASS);

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				user.getCompanyId(), ldapServerId);

		String[] defaultObjectClassNames =
			ldapServerConfiguration.userDefaultObjectClasses();

		for (String defaultObjectClassName : defaultObjectClassNames) {
			objectClassAttribute.add(defaultObjectClassName);
		}

		attributes.put(objectClassAttribute);

		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.UUID), user.getUuid(),
			attributes);
		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.SCREEN_NAME),
			user.getScreenName(), attributes);
		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.PASSWORD),
			getEncryptedPasswordForLDAP(user, userMappings), attributes);
		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.EMAIL_ADDRESS),
			user.getEmailAddress(), attributes);
		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.FULL_NAME),
			user.getFullName(), attributes);
		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.FIRST_NAME),
			user.getFirstName(), attributes);
		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.MIDDLE_NAME),
			user.getMiddleName(), attributes);
		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.LAST_NAME),
			user.getLastName(), attributes);
		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.JOB_TITLE),
			user.getJobTitle(), attributes);
		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.PORTRAIT),
			getUserPortrait(user), attributes);
		addAttributeMapping(
			userMappings.getProperty(UserConverterKeys.STATUS),
			String.valueOf(user.getStatus()), attributes);

		return attributes;
	}

	@Override
	public Modifications getLDAPUserGroupModifications(
			long ldapServerId, List<UserGroup> userGroups, User user,
			Properties userMappings)
		throws Exception {

		Modifications modifications = Modifications.getInstance();

		String groupMappingAttributeName = userMappings.getProperty(
			UserConverterKeys.GROUP);

		if (Validator.isNull(groupMappingAttributeName)) {
			return modifications;
		}

		Properties groupMappings = _ldapSettings.getGroupMappings(
			ldapServerId, user.getCompanyId());

		String userDN = getUserDNName(ldapServerId, user, userMappings);

		for (UserGroup userGroup : userGroups) {
			String groupDN = getGroupDNName(
				ldapServerId, userGroup, groupMappings);

			if (_portalLDAP.isUserGroupMember(
					ldapServerId, user.getCompanyId(), groupDN, userDN)) {

				continue;
			}

			modifications.addItem(
				DirContext.ADD_ATTRIBUTE, groupMappingAttributeName, groupDN);
		}

		return modifications;
	}

	@Override
	public Modifications getLDAPUserModifications(
			User user, Map<String, Serializable> userExpandoAttributes,
			Properties userMappings, Properties userExpandoMappings)
		throws Exception {

		Modifications modifications = getModifications(
			user, userMappings, _reservedUserFieldNames);

		if (PasswordModificationThreadLocal.isPasswordModified() &&
			Validator.isNotNull(
				PasswordModificationThreadLocal.getPasswordUnencrypted())) {

			String newPassword = getEncryptedPasswordForLDAP(
				user, userMappings);

			String passwordKey = userMappings.getProperty(
				UserConverterKeys.PASSWORD);

			addModificationItem(passwordKey, newPassword, modifications);
		}

		String portraitKey = userMappings.getProperty(
			UserConverterKeys.PORTRAIT);

		if (Validator.isNotNull(portraitKey)) {
			addModificationItem(
				new BasicAttribute(portraitKey, getUserPortrait(user)),
				modifications);
		}

		populateCustomAttributeModifications(
			user, user.getExpandoBridge(), userExpandoAttributes,
			userExpandoMappings, modifications);

		return modifications;
	}

	@Override
	public String getUserDNName(
			long ldapServerId, User user, Properties userMappings)
		throws Exception {

		Binding userBinding = _portalLDAP.getUser(
			ldapServerId, user.getCompanyId(), user.getScreenName(),
			user.getEmailAddress());

		if (userBinding != null) {
			return userBinding.getNameInNamespace();
		}

		StringBundler sb = new StringBundler(5);

		sb.append(
			GetterUtil.getString(
				userMappings.getProperty(_userDNFieldName), _DEFAULT_DN));
		sb.append(StringPool.EQUAL);
		sb.append(BeanPropertiesUtil.getStringSilent(user, _userDNFieldName));
		sb.append(StringPool.COMMA);
		sb.append(_portalLDAP.getUsersDN(ldapServerId, user.getCompanyId()));

		return sb.toString();
	}

	public void setContactReservedFieldNames(
		List<String> reservedContactFieldNames) {

		for (String reservedContactFieldName : reservedContactFieldNames) {
			_reservedContactFieldNames.put(
				reservedContactFieldName, reservedContactFieldName);
		}
	}

	public void setUserDNFieldName(String userDNFieldName) {
		_userDNFieldName = userDNFieldName;
	}

	public void setUserReservedFieldNames(List<String> reservedUserFieldNames) {
		for (String reservedUserFieldName : reservedUserFieldNames) {
			_reservedUserFieldNames.put(
				reservedUserFieldName, reservedUserFieldName);
		}
	}

	protected void addAttributeMapping(
		String attributeName, Object attributeValue, Attributes attributes) {

		if (Validator.isNotNull(attributeName) && (attributeValue != null)) {
			attributes.put(attributeName, attributeValue);
		}
	}

	protected void addAttributeMapping(
		String attributeName, String attributeValue, Attributes attributes) {

		if (Validator.isNotNull(attributeName) &&
			Validator.isNotNull(attributeValue)) {

			attributes.put(attributeName, attributeValue);
		}
	}

	protected void addModificationItem(
		BasicAttribute basicAttribute, Modifications modifications) {

		if (basicAttribute != null) {
			modifications.addItem(basicAttribute);
		}
	}

	protected void addModificationItem(
		String attributeName, String attributeValue,
		Modifications modifications) {

		if (Validator.isNotNull(attributeName)) {
			modifications.addItem(attributeName, attributeValue);
		}
	}

	protected String getEncryptedPasswordForLDAP(
		User user, Properties userMappings) {

		String password =
			PasswordModificationThreadLocal.getPasswordUnencrypted();

		if (Validator.isNull(password)) {
			return password;
		}

		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(
				user.getCompanyId());

		String algorithm = ldapAuthConfiguration.passwordEncryptionAlgorithm();

		if (Validator.isNotNull(algorithm) &&
			!algorithm.equals(PasswordEncryptorUtil.TYPE_NONE)) {

			try {
				StringBundler sb = new StringBundler(4);

				if (!hasLegacyPasswordEncryptionAlgorithm()) {
					sb.append(StringPool.OPEN_CURLY_BRACE);
					sb.append(algorithm);
					sb.append(StringPool.CLOSE_CURLY_BRACE);
				}

				sb.append(
					_passwordEncryptor.encrypt(algorithm, password, null));

				password = sb.toString();
			}
			catch (PwdEncryptorException pee) {
				throw new SystemException(pee);
			}
		}

		String passwordKey = userMappings.getProperty(
			UserConverterKeys.PASSWORD);

		if (passwordKey.equals("unicodePwd")) {
			String quotedPassword = StringPool.QUOTE.concat(
				password
			).concat(
				StringPool.QUOTE
			);

			try {
				byte[] unicodePassword = quotedPassword.getBytes("UTF-16LE");

				return new String(unicodePassword);
			}
			catch (UnsupportedEncodingException uee) {
				throw new SystemException(uee);
			}
		}

		return password;
	}

	protected Modifications getModifications(
		Object object, Properties objectMappings,
		Map<String, String> reservedFieldNames) {

		Modifications modifications = Modifications.getInstance();

		for (Map.Entry<Object, Object> entry : objectMappings.entrySet()) {
			String fieldName = (String)entry.getKey();

			if (reservedFieldNames.containsKey(fieldName)) {
				continue;
			}

			String ldapAttributeName = (String)entry.getValue();

			try {
				Object attributeValue = BeanPropertiesUtil.getObjectSilent(
					object, fieldName);

				if (attributeValue != null) {
					addModificationItem(
						ldapAttributeName, attributeValue.toString(),
						modifications);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to map field ", fieldName, " to class ",
							object.getClass()),
						e);
				}
			}
		}

		return modifications;
	}

	protected byte[] getUserPortrait(User user) {
		byte[] bytes = null;

		if (user.getPortraitId() == 0) {
			return bytes;
		}

		Image image = null;

		try {
			image = _imageLocalService.getImage(user.getPortraitId());

			if (image != null) {
				bytes = image.getTextObj();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get the portrait for user " + user.getUserId(),
					e);
			}
		}

		return bytes;
	}

	protected boolean hasLegacyPasswordEncryptionAlgorithm() {
		if (Validator.isNotNull(
				GetterUtil.getString(
					_props.get(
						PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY)))) {

			return true;
		}

		return false;
	}

	protected void populateCustomAttributeModifications(
		Object object, ExpandoBridge expandoBridge,
		Map<String, Serializable> expandoAttributes, Properties expandoMappings,
		Modifications modifications) {

		if ((expandoAttributes == null) || expandoAttributes.isEmpty()) {
			return;
		}

		for (Map.Entry<Object, Object> entry : expandoMappings.entrySet()) {
			String fieldName = (String)entry.getKey();

			Serializable fieldValue = expandoAttributes.get(fieldName);

			if (fieldValue == null) {
				continue;
			}

			String ldapAttributeName = (String)entry.getValue();

			try {
				int type = expandoBridge.getAttributeType(fieldName);

				String value = ExpandoConverterUtil.getStringFromAttribute(
					type, fieldValue);

				addModificationItem(ldapAttributeName, value, modifications);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to map field ", fieldName, " to class ",
							object.getClass()),
						e);
				}
			}
		}
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration)",
		unbind = "-"
	)
	protected void setLDAPAuthConfigurationProvider(
		ConfigurationProvider<LDAPAuthConfiguration>
			ldapAuthConfigurationProvider) {

		_ldapAuthConfigurationProvider = ldapAuthConfigurationProvider;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration)",
		unbind = "-"
	)
	protected void setLDAPServerConfigurationProvider(
		ConfigurationProvider<LDAPServerConfiguration>
			ldapServerConfigurationProvider) {

		_ldapServerConfigurationProvider = ldapServerConfigurationProvider;
	}

	@Reference(unbind = "-")
	protected void setLdapSettings(LDAPSettings ldapSettings) {
		_ldapSettings = ldapSettings;
	}

	@Reference(unbind = "-")
	protected void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
		_passwordEncryptor = passwordEncryptor;
	}

	private static final String _DEFAULT_DN = "cn";

	private static final String _OBJECT_CLASS = "objectclass";

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultPortalToLDAPConverter.class);

	private ImageLocalService _imageLocalService;
	private ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;
	private ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;
	private LDAPSettings _ldapSettings;
	private PasswordEncryptor _passwordEncryptor;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile PortalLDAP _portalLDAP;

	@Reference
	private Props _props;

	private final Map<String, String> _reservedContactFieldNames =
		new HashMap<>();
	private final Map<String, String> _reservedUserFieldNames = new HashMap<>();
	private String _userDNFieldName = UserConverterKeys.SCREEN_NAME;

}