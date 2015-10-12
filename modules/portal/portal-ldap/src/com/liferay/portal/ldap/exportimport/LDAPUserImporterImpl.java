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

package com.liferay.portal.ldap.exportimport;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.ldap.LDAPUtil;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.ldap.ContactConverterKeys;
import com.liferay.portal.ldap.UserConverterKeys;
import com.liferay.portal.ldap.configuration.LDAPConfiguration;
import com.liferay.portal.ldap.settings.LDAPConfigurationSettingsUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.exportimport.UserGroupImportTransactionThreadLocal;
import com.liferay.portal.security.exportimport.UserImporter;
import com.liferay.portal.security.exportimport.UserImporterUtil;
import com.liferay.portal.security.ldap.AttributesTransformer;
import com.liferay.portal.security.ldap.LDAPGroup;
import com.liferay.portal.security.ldap.LDAPSettings;
import com.liferay.portal.security.ldap.LDAPToPortalConverter;
import com.liferay.portal.security.ldap.LDAPUser;
import com.liferay.portal.security.ldap.LDAPUserImporter;
import com.liferay.portal.security.ldap.PortalLDAP;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.expando.util.ExpandoConverterUtil;

import java.io.Serializable;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.naming.Binding;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Hugo Huijser
 */
@Component(
	configurationPid = "com.liferay.portal.ldap.configuration.LDAPConfiguration",
	immediate = true,
	service = {LDAPUserImporter.class, UserImporter.class}
)
public class LDAPUserImporterImpl implements LDAPUserImporter, UserImporter {

	@Override
	public long getLastImportTime() {
		return _lastImportTime;
	}

	@Override
	public User importUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, String password)
		throws Exception {

		Properties userMappings = _ldapSettings.getUserMappings(
			ldapServerId, companyId);
		Properties userExpandoMappings = _ldapSettings.getUserExpandoMappings(
			ldapServerId, companyId);
		Properties contactMappings = _ldapSettings.getContactMappings(
			ldapServerId, companyId);
		Properties contactExpandoMappings =
			_ldapSettings.getContactExpandoMappings(ldapServerId, companyId);

		User user = importUser(
			ldapServerId, companyId, attributes, userMappings,
			userExpandoMappings, contactMappings, contactExpandoMappings,
			password);

		Properties groupMappings = _ldapSettings.getGroupMappings(
			ldapServerId, companyId);

		importGroups(
			ldapServerId, companyId, ldapContext, attributes, user,
			userMappings, groupMappings);

		return user;
	}

	@Override
	public User importUser(
			long ldapServerId, long companyId, String emailAddress,
			String screenName)
		throws Exception {

		LdapContext ldapContext = null;

		NamingEnumeration<SearchResult> enu = null;

		try {
			String postfix = _ldapSettings.getPropertyPostfix(ldapServerId);

			String baseDN = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_DN + postfix);

			ldapContext = _portalLDAP.getContext(ldapServerId, companyId);

			if (ldapContext == null) {
				_log.error("Unable to bind to the LDAP server");

				return null;
			}

			String filter = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_AUTH_SEARCH_FILTER + postfix);

			if (_log.isDebugEnabled()) {
				_log.debug("Search filter before transformation " + filter);
			}

			filter = StringUtil.replace(
				filter,
				new String[] {
					"@company_id@", "@email_address@", "@screen_name@"
				},
				new String[] {
					String.valueOf(companyId), emailAddress, screenName
				});

			LDAPUtil.validateFilter(filter);

			if (_log.isDebugEnabled()) {
				_log.debug("Search filter after transformation " + filter);
			}

			Properties userMappings = _ldapSettings.getUserMappings(
				ldapServerId, companyId);

			String userMappingsScreenName = GetterUtil.getString(
				userMappings.getProperty("screenName"));

			userMappingsScreenName = StringUtil.toLowerCase(
				userMappingsScreenName);

			SearchControls searchControls = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0,
				new String[] {userMappingsScreenName}, false, false);

			enu = ldapContext.search(baseDN, filter, searchControls);

			if (enu.hasMoreElements()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter returned at least one result");
				}

				Binding binding = enu.nextElement();

				Attributes attributes = _portalLDAP.getUserAttributes(
					ldapServerId, companyId, ldapContext,
					_portalLDAP.getNameInNamespace(
						ldapServerId, companyId, binding));

				return importUser(
					ldapServerId, companyId, ldapContext, attributes,
					StringPool.BLANK);
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Problem accessing LDAP server " + e.getMessage());
			}

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new SystemException(
				"Problem accessing LDAP server " + e.getMessage());
		}
		finally {
			if (enu != null) {
				enu.close();
			}

			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	@Override
	public User importUser(
			long companyId, String emailAddress, String screenName)
		throws Exception {

		long[] ldapServerIds = StringUtil.split(
			PrefsPropsUtil.getString(companyId, "ldap.server.ids"), 0L);

		for (long ldapServerId : ldapServerIds) {
			User user = importUser(
				ldapServerId, companyId, emailAddress, screenName);

			if (user != null) {
				return user;
			}
		}

		for (int ldapServerId = 0;; ldapServerId++) {
			String postfix = _ldapSettings.getPropertyPostfix(ldapServerId);

			String providerUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_PROVIDER_URL + postfix);

			if (Validator.isNull(providerUrl)) {
				break;
			}

			User user = importUser(
				ldapServerId, companyId, emailAddress, screenName);

			if (user != null) {
				return user;
			}
		}

		if (_log.isDebugEnabled()) {
			if (Validator.isNotNull(emailAddress)) {
				_log.debug(
					"User with the email address " + emailAddress +
				" was not found in any LDAP servers");
			}
			else {
				_log.debug(
					"User with the screen name " + screenName +
				" was not found in any LDAP servers");
			}
		}

		return null;
	}

	@Override
	public User importUserByScreenName(long companyId, String screenName)
		throws Exception {

		long ldapServerId = _portalLDAP.getLdapServerId(
			companyId, screenName, StringPool.BLANK);

		SearchResult result = (SearchResult)_portalLDAP.getUser(
			ldapServerId, companyId, screenName, StringPool.BLANK);

		if (result == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No user was found in LDAP with screenName " + screenName);
			}

			return null;
		}

		LdapContext ldapContext = _portalLDAP.getContext(
			ldapServerId, companyId);

		String fullUserDN = _portalLDAP.getNameInNamespace(
			ldapServerId, companyId, result);

		Attributes attributes = _portalLDAP.getUserAttributes(
			ldapServerId, companyId, ldapContext, fullUserDN);

		User user = importUser(
			ldapServerId, companyId, ldapContext, attributes, StringPool.BLANK);

		ldapContext.close();

		return user;
	}

	@Override
	public void importUsers() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			importUsers(company.getCompanyId());
		}
	}

	@Override
	public void importUsers(long companyId) throws Exception {
		if (!_ldapSettings.isImportEnabled(companyId)) {
			return;
		}

		try {
			long defaultUserId = _userLocalService.getDefaultUserId(companyId);

			if (_lockManager.hasLock(
					defaultUserId, UserImporterUtil.class.getName(),
					companyId)) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Skipping LDAP import for company " + companyId +
							" because another LDAP import is in process");
				}

				return;
			}

			LDAPConfiguration ldapConfiguration =
				_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

			_lockManager.lock(
				defaultUserId, UserImporterUtil.class.getName(), companyId,
				LDAPUserImporterImpl.class.getName(), false,
				ldapConfiguration.importLockExpirationTime());

			long[] ldapServerIds = StringUtil.split(
				PrefsPropsUtil.getString(companyId, "ldap.server.ids"), 0L);

			for (long ldapServerId : ldapServerIds) {
				importUsers(ldapServerId, companyId);
			}

			for (int ldapServerId = 0;; ldapServerId++) {
				String postfix = _ldapSettings.getPropertyPostfix(ldapServerId);

				String providerUrl = PrefsPropsUtil.getString(
					companyId, PropsKeys.LDAP_BASE_PROVIDER_URL + postfix);

				if (Validator.isNull(providerUrl)) {
					break;
				}

				importUsers(ldapServerId, companyId);
			}
		}
		finally {
			_lockManager.unlock(UserImporterUtil.class.getName(), companyId);
		}
	}

	@Override
	public void importUsers(long ldapServerId, long companyId)
		throws Exception {

		if (!_ldapSettings.isImportEnabled(companyId)) {
			return;
		}

		LdapContext ldapContext = _portalLDAP.getContext(
			ldapServerId, companyId);

		if (ldapContext == null) {
			return;
		}

		_lastImportTime = System.currentTimeMillis();

		LDAPConfiguration ldapConfiguration =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		try {
			Properties userMappings = _ldapSettings.getUserMappings(
				ldapServerId, companyId);
			Properties userExpandoMappings =
				_ldapSettings.getUserExpandoMappings(ldapServerId, companyId);
			Properties contactMappings = _ldapSettings.getContactMappings(
				ldapServerId, companyId);
			Properties contactExpandoMappings =
				_ldapSettings.getContactExpandoMappings(
					ldapServerId, companyId);
			Properties groupMappings = _ldapSettings.getGroupMappings(
				ldapServerId, companyId);

			String importMethod = ldapConfiguration.importMethod();

			if (importMethod.equals(_IMPORT_BY_GROUP)) {
				importFromLDAPByGroup(
					ldapServerId, companyId, ldapContext, userMappings,
					userExpandoMappings, contactMappings,
					contactExpandoMappings, groupMappings);
			}
			else if (importMethod.equals(_IMPORT_BY_USER)) {
				importFromLDAPByUser(
					ldapServerId, companyId, ldapContext, userMappings,
					userExpandoMappings, contactMappings,
					contactExpandoMappings, groupMappings);
			}
		}
		catch (Exception e) {
			_log.error("Unable to import LDAP users and groups", e);
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	@Reference
	public void setAttributesTransformer(
		AttributesTransformer attributesTransformer) {

		_attributesTransformer = attributesTransformer;
	}

	@Reference
	public void setLDAPToPortalConverter(
		LDAPToPortalConverter ldapToPortalConverter) {

		_ldapToPortalConverter = ldapToPortalConverter;
	}

	@Reference
	public void setSingleVMPool(SingleVMPool singleVMPool) {
		_portalCache = (PortalCache<String, Long>)singleVMPool.getPortalCache(
			UserImporter.class.getName(), false);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		LDAPConfiguration ldapConfiguration =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration();

		_ldapUserIgnoreAttributes = SetUtil.fromArray(
			ldapConfiguration.userIgnoreAttributes());
	}

	protected void addRole(
			long companyId, LDAPGroup ldapGroup, UserGroup userGroup)
		throws Exception {

		LDAPConfiguration ldapConfiguration =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		if (!ldapConfiguration.importCreateRolePerGroup()) {
			return;
		}

		Role role = null;

		try {
			role = _roleLocalService.getRole(
				companyId, ldapGroup.getGroupName());
		}
		catch (NoSuchRoleException nsre) {
			User defaultUser = _userLocalService.getDefaultUser(companyId);

			Map<Locale, String> descriptionMap = new HashMap<>();

			descriptionMap.put(
				LocaleUtil.getDefault(), "Autogenerated role from LDAP import");

			role = _roleLocalService.addRole(
				defaultUser.getUserId(), null, 0, ldapGroup.getGroupName(),
				null, descriptionMap, RoleConstants.TYPE_REGULAR, null, null);
		}

		Group group = userGroup.getGroup();

		if (_groupLocalService.hasRoleGroup(
				role.getRoleId(), group.getGroupId())) {

			return;
		}

		_groupLocalService.addRoleGroups(
			role.getRoleId(), new long[] {group.getGroupId()});
	}

	protected User addUser(long companyId, LDAPUser ldapUser, String password)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Adding user " + ldapUser.getEmailAddress());
		}

		boolean autoPassword = ldapUser.isAutoPassword();

		LDAPConfiguration ldapConfiguration =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		if (!ldapConfiguration.importUserPasswordEnabled()) {
			autoPassword =
				ldapConfiguration.
					importUserPasswordAutogenerated() &&
					 !PropsValues.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK;

			if (!autoPassword) {
				String defaultPassword =
					ldapConfiguration.importUserPasswordDefault();

				if (StringUtil.equalsIgnoreCase(
						defaultPassword, _USER_PASSWORD_SCREEN_NAME)) {

					defaultPassword = ldapUser.getScreenName();
				}

				password = defaultPassword;
			}
		}

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(ldapUser.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		User user = _userLocalService.addUser(
			ldapUser.getCreatorUserId(), companyId, autoPassword, password,
			password, ldapUser.isAutoScreenName(), ldapUser.getScreenName(),
			ldapUser.getEmailAddress(), 0, StringPool.BLANK,
			ldapUser.getLocale(), ldapUser.getFirstName(),
			ldapUser.getMiddleName(), ldapUser.getLastName(), 0, 0,
			ldapUser.isMale(), birthdayMonth, birthdayDay, birthdayYear,
			StringPool.BLANK, ldapUser.getGroupIds(),
			ldapUser.getOrganizationIds(), ldapUser.getRoleIds(),
			ldapUser.getUserGroupIds(), ldapUser.isSendEmail(),
			ldapUser.getServiceContext());

		if (ldapUser.isUpdatePortrait()) {
			byte[] portraitBytes = ldapUser.getPortraitBytes();

			if (ArrayUtil.isNotEmpty(portraitBytes)) {
				user = _userLocalService.updatePortrait(
					user.getUserId(), portraitBytes);
			}
		}

		return user;
	}

	protected void addUserGroupsNotAddedByLDAPImport(
			long userId, Set<Long> userGroupIds)
		throws Exception {

		List<UserGroup> userGroups = _userGroupLocalService.getUserUserGroups(
			userId);

		for (UserGroup userGroup : userGroups) {
			if (!userGroup.isAddedByLDAPImport()) {
				userGroupIds.add(userGroup.getUserGroupId());
			}
		}
	}

	protected String escapeValue(String value) {
		return StringUtil.replace(value, "\\,", "\\\\,");
	}

	protected User getUser(long companyId, LDAPUser ldapUser) throws Exception {
		LDAPConfiguration ldapConfiguration =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		if (Validator.equals(
				ldapConfiguration.importUserSyncStrategy(),
				_USER_SYNC_STRATEGY_UUID)) {

			ServiceContext serviceContext = ldapUser.getServiceContext();

			return _userLocalService.fetchUserByUuidAndCompanyId(
				serviceContext.getUuid(), companyId);
		}

		String authType = PrefsPropsUtil.getString(
			companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
			PropsValues.COMPANY_SECURITY_AUTH_TYPE);

		if (authType.equals(CompanyConstants.AUTH_TYPE_SN) &&
			!ldapUser.isAutoScreenName()) {

			return _userLocalService.fetchUserByScreenName(
				companyId, ldapUser.getScreenName());
		}

		return _userLocalService.fetchUserByEmailAddress(
			companyId, ldapUser.getEmailAddress());
	}

	protected Attribute getUsers(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, UserGroup userGroup,
			Properties groupMappings)
		throws Exception {

		Attribute attribute = attributes.get(groupMappings.getProperty("user"));

		if (attribute == null) {
			return null;
		}

		String postfix = _ldapSettings.getPropertyPostfix(ldapServerId);

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN + postfix);

		StringBundler sb = new StringBundler(7);

		sb.append("(&");

		String importGroupSearchFilter = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix);

		LDAPUtil.validateFilter(
			importGroupSearchFilter,
			PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix);

		sb.append(importGroupSearchFilter);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(groupMappings.getProperty("groupName"));
		sb.append("=");
		sb.append(escapeValue(userGroup.getName()));
		sb.append("))");

		return _portalLDAP.getMultivaluedAttribute(
			companyId, ldapContext, baseDN, sb.toString(), attribute);
	}

	protected void importFromLDAPByGroup(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Properties userMappings, Properties userExpandoMappings,
			Properties contactMappings, Properties contactExpandoMappings,
			Properties groupMappings)
		throws Exception {

		byte[] cookie = new byte[0];

		while (cookie != null) {
			List<SearchResult> searchResults = new ArrayList<>();

			String groupMappingsGroupName = GetterUtil.getString(
				groupMappings.getProperty("groupName"));

			groupMappingsGroupName = StringUtil.toLowerCase(
				groupMappingsGroupName);

			cookie = _portalLDAP.getGroups(
				ldapServerId, companyId, ldapContext, cookie, 0,
				new String[] {groupMappingsGroupName}, searchResults);

			for (SearchResult searchResult : searchResults) {
				try {
					Attributes attributes = _portalLDAP.getGroupAttributes(
						ldapServerId, companyId, ldapContext,
						_portalLDAP.getNameInNamespace(
							ldapServerId, companyId, searchResult),
						true);

					UserGroup userGroup = importUserGroup(
						companyId, attributes, groupMappings);

					Attribute usersAttribute = getUsers(
						ldapServerId, companyId, ldapContext, attributes,
						userGroup, groupMappings);

					if (usersAttribute == null) {
						if (_log.isInfoEnabled()) {
							_log.info(
								"No users found in " + userGroup.getName());
						}

						continue;
					}

					importUsers(
						ldapServerId, companyId, ldapContext, userMappings,
						userExpandoMappings, contactMappings,
						contactExpandoMappings, userGroup.getUserGroupId(),
						usersAttribute);
				}
				catch (Exception e) {
					_log.error("Unable to import group " + searchResult, e);
				}
			}
		}
	}

	protected void importFromLDAPByUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Properties userMappings, Properties userExpandoMappings,
			Properties contactMappings, Properties contactExpandoMappings,
			Properties groupMappings)
		throws Exception {

		byte[] cookie = new byte[0];

		while (cookie != null) {
			List<SearchResult> searchResults = new ArrayList<>();

			String userMappingsScreenName = GetterUtil.getString(
				userMappings.getProperty("screenName"));

			userMappingsScreenName = StringUtil.toLowerCase(
				userMappingsScreenName);

			cookie = _portalLDAP.getUsers(
				ldapServerId, companyId, ldapContext, cookie, 0,
				new String[] {userMappingsScreenName}, searchResults);

			for (SearchResult searchResult : searchResults) {
				try {
					Attributes userAttributes = _portalLDAP.getUserAttributes(
						ldapServerId, companyId, ldapContext,
						_portalLDAP.getNameInNamespace(
							ldapServerId, companyId, searchResult));

					User user = importUser(
						ldapServerId, companyId, userAttributes, userMappings,
						userExpandoMappings, contactMappings,
						contactExpandoMappings, StringPool.BLANK);

					importGroups(
						ldapServerId, companyId, ldapContext, userAttributes,
						user, userMappings, groupMappings);
				}
				catch (Exception e) {
					_log.error("Unable to import user " + searchResult, e);
				}
			}
		}
	}

	protected Set<Long> importGroup(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullGroupDN, User user, Properties groupMappings,
			Set<Long> newUserGroupIds)
		throws Exception {

		String userGroupIdKey = null;

		Long userGroupId = null;

		LDAPConfiguration ldapConfiguration =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		if (ldapConfiguration.importGroupCacheEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append(ldapServerId);
			sb.append(StringPool.UNDERLINE);
			sb.append(companyId);
			sb.append(StringPool.UNDERLINE);
			sb.append(fullGroupDN);

			userGroupIdKey = sb.toString();

			userGroupId = _portalCache.get(userGroupIdKey);
		}

		if (userGroupId != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Skipping reimport of full group DN " + fullGroupDN);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Importing full group DN " + fullGroupDN);
			}

			Attributes groupAttributes = null;

			try {
				groupAttributes = _portalLDAP.getGroupAttributes(
					ldapServerId, companyId, ldapContext, fullGroupDN);
			}
			catch (NameNotFoundException nnfe) {
				_log.error(
					"LDAP group not found with full group DN " + fullGroupDN,
					nnfe);
			}

			UserGroup userGroup = importUserGroup(
				companyId, groupAttributes, groupMappings);

			if (userGroup == null) {
				return newUserGroupIds;
			}

			userGroupId = userGroup.getUserGroupId();

			if (ldapConfiguration.importGroupCacheEnabled()) {
				_portalCache.put(userGroupIdKey, userGroupId);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Adding " + user.getUserId() + " to group " + userGroupId);
		}

		newUserGroupIds.add(userGroupId);

		return newUserGroupIds;
	}

	protected void importGroups(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, User user, Properties userMappings,
			Properties groupMappings)
		throws Exception {

		String groupMappingsUser = groupMappings.getProperty("user");

		Set<Long> newUserGroupIds = new LinkedHashSet<>();

		LDAPConfiguration ldapConfiguration =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		if (Validator.isNotNull(groupMappingsUser) &&
			ldapConfiguration.importGroupSearchFilterEnabled()) {

			String postfix = _ldapSettings.getPropertyPostfix(ldapServerId);

			String baseDN = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_DN + postfix);

			StringBundler sb = new StringBundler(9);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(StringPool.AMPERSAND);

			String importGroupSearchFilter = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix);

			LDAPUtil.validateFilter(
				importGroupSearchFilter,
				PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix);

			sb.append(importGroupSearchFilter);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(groupMappingsUser);
			sb.append(StringPool.EQUAL);

			Binding binding = _portalLDAP.getUser(
				ldapServerId, companyId, user.getScreenName(),
				user.getEmailAddress());

			String fullUserDN = _portalLDAP.getNameInNamespace(
				ldapServerId, companyId, binding);

			sb.append(escapeValue(fullUserDN));

			sb.append(StringPool.CLOSE_PARENTHESIS);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			byte[] cookie = new byte[0];

			while (cookie != null) {
				List<SearchResult> searchResults = new ArrayList<>();

				String groupMappingsGroupName = GetterUtil.getString(
					groupMappings.getProperty("groupName"));

				groupMappingsGroupName = StringUtil.toLowerCase(
					groupMappingsGroupName);

				cookie = _portalLDAP.searchLDAP(
					companyId, ldapContext, cookie, 0, baseDN, sb.toString(),
					new String[] {groupMappingsGroupName}, searchResults);

				for (SearchResult searchResult : searchResults) {
					String fullGroupDN = _portalLDAP.getNameInNamespace(
						ldapServerId, companyId, searchResult);

					newUserGroupIds = importGroup(
						ldapServerId, companyId, ldapContext, fullGroupDN, user,
						groupMappings, newUserGroupIds);
				}
			}
		}
		else {
			String userMappingsGroup = userMappings.getProperty("group");

			if (Validator.isNull(userMappingsGroup)) {
				return;
			}

			Attribute userGroupAttribute = attributes.get(userMappingsGroup);

			if (userGroupAttribute == null) {
				return;
			}

			for (int i = 0; i < userGroupAttribute.size(); i++) {
				String fullGroupDN = (String)userGroupAttribute.get(i);

				newUserGroupIds = importGroup(
					ldapServerId, companyId, ldapContext, fullGroupDN, user,
					groupMappings, newUserGroupIds);
			}
		}

		addUserGroupsNotAddedByLDAPImport(user.getUserId(), newUserGroupIds);

		Set<Long> oldUserGroupIds = new LinkedHashSet<>();

		List<UserGroup> oldUserGroups =
			_userGroupLocalService.getUserUserGroups(user.getUserId());

		for (UserGroup oldUserGroup : oldUserGroups) {
			oldUserGroupIds.add(oldUserGroup.getUserGroupId());
		}

		if (!oldUserGroupIds.equals(newUserGroupIds)) {
			long[] userGroupIds = ArrayUtil.toLongArray(newUserGroupIds);

			_userGroupLocalService.setUserUserGroups(
				user.getUserId(), userGroupIds);
		}
	}

	protected User importUser(
			long ldapServerId, long companyId, Attributes attributes,
			Properties userMappings, Properties userExpandoMappings,
			Properties contactMappings, Properties contactExpandoMappings,
			String password)
		throws Exception {

		UserImportTransactionThreadLocal.setOriginatesFromImport(true);

		try {
			attributes = _attributesTransformer.transformUser(attributes);

			LDAPUser ldapUser = _ldapToPortalConverter.importLDAPUser(
				companyId, attributes, userMappings, userExpandoMappings,
				contactMappings, contactExpandoMappings, password);

			User user = getUser(companyId, ldapUser);

			if ((user != null) && user.isDefaultUser()) {
				return user;
			}

			ServiceContext serviceContext = ldapUser.getServiceContext();

			serviceContext.setAttribute("ldapServerId", ldapServerId);

			boolean isNew = false;

			if (user == null) {
				user = addUser(companyId, ldapUser, password);

				isNew = true;
			}

			String modifyTimestamp = LDAPUtil.getAttributeString(
				attributes, "modifyTimestamp");

			user = updateUser(
				companyId, ldapUser, user, userMappings, contactMappings,
				password, modifyTimestamp, isNew);

			updateExpandoAttributes(
				user, ldapUser, userExpandoMappings, contactExpandoMappings);

			return user;
		}
		finally {
			UserImportTransactionThreadLocal.setOriginatesFromImport(false);
		}
	}

	protected UserGroup importUserGroup(
			long companyId, Attributes attributes, Properties groupMappings)
		throws Exception {

		attributes = _attributesTransformer.transformGroup(attributes);

		LDAPGroup ldapGroup = _ldapToPortalConverter.importLDAPGroup(
			companyId, attributes, groupMappings);

		UserGroup userGroup = null;

		try {
			userGroup = _userGroupLocalService.getUserGroup(
				companyId, ldapGroup.getGroupName());

			if (!Validator.equals(
					userGroup.getDescription(), ldapGroup.getDescription())) {

				_userGroupLocalService.updateUserGroup(
					companyId, userGroup.getUserGroupId(),
					ldapGroup.getGroupName(), ldapGroup.getDescription(), null);
			}
		}
		catch (NoSuchUserGroupException nsuge) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Adding user group to portal " + ldapGroup.getGroupName());
			}

			long defaultUserId = _userLocalService.getDefaultUserId(companyId);

			UserGroupImportTransactionThreadLocal.setOriginatesFromImport(true);

			try {
				userGroup = _userGroupLocalService.addUserGroup(
					defaultUserId, companyId, ldapGroup.getGroupName(),
					ldapGroup.getDescription(), null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to create user group " +
							ldapGroup.getGroupName());
				}

				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
			finally {
				UserGroupImportTransactionThreadLocal.setOriginatesFromImport(
					false);
			}
		}

		addRole(companyId, ldapGroup, userGroup);

		return userGroup;
	}

	protected void importUsers(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Properties userMappings, Properties userExpandoMappings,
			Properties contactMappings, Properties contactExpandoMappings,
			long userGroupId, Attribute attribute)
		throws Exception {

		Set<Long> newUserIds = new LinkedHashSet<>(attribute.size());

		for (int i = 0; i < attribute.size(); i++) {
			String fullUserDN = (String)attribute.get(i);

			Attributes userAttributes = null;

			try {
				userAttributes = _portalLDAP.getUserAttributes(
					ldapServerId, companyId, ldapContext, fullUserDN);
			}
			catch (NameNotFoundException nnfe) {
				_log.error(
					"LDAP user not found with fullUserDN " + fullUserDN, nnfe);

				continue;
			}

			try {
				User user = importUser(
					ldapServerId, companyId, userAttributes, userMappings,
					userExpandoMappings, contactMappings,
					contactExpandoMappings, StringPool.BLANK);

				if (user != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Adding " + user.getUserId() + " to group " +
								userGroupId);
					}

					_userLocalService.addUserGroupUsers(
						userGroupId, new long[] {user.getUserId()});

					newUserIds.add(user.getUserId());
				}
			}
			catch (Exception e) {
				_log.error("Unable to load user " + userAttributes, e);
			}
		}

		List<User> userGroupUsers = _userLocalService.getUserGroupUsers(
			userGroupId);

		for (User user : userGroupUsers) {
			if ((ldapServerId == user.getLdapServerId()) &&
				!newUserIds.contains(user.getUserId())) {

				_userLocalService.deleteUserGroupUser(
					userGroupId, user.getUserId());
			}
		}
	}

	protected void populateExpandoAttributes(
		ExpandoBridge expandoBridge, Map<String, String[]> expandoAttributes,
		Properties expandoMappings) {

		Map<String, Serializable> serializedExpandoAttributes = new HashMap<>();

		for (Map.Entry<String, String[]> expandoAttribute :
				expandoAttributes.entrySet()) {

			String name = expandoAttribute.getKey();

			if (!expandoBridge.hasAttribute(name)) {
				continue;
			}

			if (expandoMappings.containsKey(name) &&
				!_ldapUserIgnoreAttributes.contains(name)) {

				int type = expandoBridge.getAttributeType(name);

				Serializable value =
					ExpandoConverterUtil.getAttributeFromStringArray(
						type, expandoAttribute.getValue());

				serializedExpandoAttributes.put(name, value);
			}
		}

		if (serializedExpandoAttributes.isEmpty()) {
			return;
		}

		try {
			_expandoValueLocalService.addValues(
				expandoBridge.getCompanyId(), expandoBridge.getClassName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME,
				expandoBridge.getClassPK(), serializedExpandoAttributes);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to populate expando attributes");
			}

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {

		_expandoValueLocalService = expandoValueLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLdapConfigurationSettingsUtil(
		LDAPConfigurationSettingsUtil ldapConfigurationSettingsUtil) {

		_ldapConfigurationSettingsUtil = ldapConfigurationSettingsUtil;
	}

	@Reference(unbind = "-")
	protected void setLdapSettings(LDAPSettings ldapSettings) {
		_ldapSettings = ldapSettings;
	}

	@Reference(unbind = "-")
	protected void setLockManager(LockManager lockManager) {
		_lockManager = lockManager;
	}

	@Reference(unbind = "-")
	protected void setPortalLDAP(PortalLDAP portalLDAP) {
		_portalLDAP = portalLDAP;
	}

	protected void setProperty(
		Object bean1, Object bean2, String propertyName) {

		Object value = BeanPropertiesUtil.getObject(bean2, propertyName);

		BeanPropertiesUtil.setProperty(bean1, propertyName, value);
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserGroupLocalService(
		UserGroupLocalService userGroupLocalService) {

		_userGroupLocalService = userGroupLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void updateExpandoAttributes(
			User user, LDAPUser ldapUser, Properties userExpandoMappings,
			Properties contactExpandoMappings)
		throws Exception {

		ExpandoBridge userExpandoBridge = user.getExpandoBridge();

		populateExpandoAttributes(
			userExpandoBridge, ldapUser.getUserExpandoAttributes(),
			userExpandoMappings);

		Contact contact = user.getContact();

		ExpandoBridge contactExpandoBridge = contact.getExpandoBridge();

		populateExpandoAttributes(
			contactExpandoBridge, ldapUser.getContactExpandoAttributes(),
			contactExpandoMappings);
	}

	protected void updateLDAPUser(
			User ldapUser, Contact ldapContact, User user,
			Properties userMappings, Properties contactMappings)
		throws PortalException {

		Contact contact = user.getContact();

		for (String propertyName : _CONTACT_PROPERTY_NAMES) {
			String mappingPropertyName = propertyName;

			if (propertyName.equals("male")) {
				mappingPropertyName = ContactConverterKeys.GENDER;
			}
			else if (propertyName.equals("prefixId")) {
				mappingPropertyName = ContactConverterKeys.PREFIX;
			}
			else if (propertyName.equals("suffixId")) {
				mappingPropertyName = ContactConverterKeys.SUFFIX;
			}

			if (!contactMappings.containsKey(mappingPropertyName) ||
				_ldapUserIgnoreAttributes.contains(propertyName)) {

				setProperty(ldapContact, contact, propertyName);
			}
		}

		for (String propertyName : _USER_PROPERTY_NAMES) {
			String mappingPropertyName = propertyName;

			if (propertyName.equals("portraitId")) {
				mappingPropertyName = UserConverterKeys.PORTRAIT;
			}

			if (!userMappings.containsKey(mappingPropertyName) ||
				_ldapUserIgnoreAttributes.contains(propertyName) ) {

				setProperty(ldapUser, user, propertyName);
			}
		}
	}

	protected User updateUser(
			long companyId, LDAPUser ldapUser, User user,
			Properties userMappings, Properties contactMappings,
			String password, String modifyTimestamp, boolean isNew)
		throws Exception {

		Date modifiedDate = null;

		boolean passwordReset = ldapUser.isPasswordReset();

		LDAPConfiguration ldapConfiguration =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		if (ldapConfiguration.exportEnabled()) {
			passwordReset = user.isPasswordReset();
		}

		try {
			if (Validator.isNotNull(modifyTimestamp)) {
				modifiedDate = LDAPUtil.parseDate(modifyTimestamp);

				if (modifiedDate.equals(user.getModifiedDate())) {
					if (ldapUser.isAutoPassword()) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Skipping user " + user.getEmailAddress() +
									" because he is already synchronized");
						}

						return user;
					}

					_userLocalService.updatePassword(
						user.getUserId(), password, password, passwordReset,
						true);

					if (_log.isDebugEnabled()) {
						_log.debug(
							"User " + user.getEmailAddress() +
								" is already synchronized, but updated " +
									"password to avoid a blank value");
					}

					return user;
				}
			}
			else if (!isNew) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Skipping user " + user.getEmailAddress() +
							" because the LDAP entry was never modified");
				}

				return user;
			}
		}
		catch (ParseException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse LDAP modify timestamp " + modifyTimestamp,
					pe);
			}
		}

		if (!ldapConfiguration.importUserPasswordEnabled()) {
			password = ldapConfiguration.importUserPasswordDefault();

			if (StringUtil.equalsIgnoreCase(
					password, _USER_PASSWORD_SCREEN_NAME)) {

				password = ldapUser.getScreenName();
			}
		}

		if (Validator.isNull(ldapUser.getScreenName()) ||
			ldapUser.isAutoScreenName()) {

			ldapUser.setScreenName(user.getScreenName());
		}

		if (ldapUser.isUpdatePassword()) {
			_userLocalService.updatePassword(
				user.getUserId(), password, password, passwordReset, true);
		}

		Contact ldapContact = ldapUser.getContact();

		updateLDAPUser(
			ldapUser.getUser(), ldapContact, user, userMappings,
			contactMappings);

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(ldapContact.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		user = _userLocalService.updateUser(
			user.getUserId(), password, StringPool.BLANK, StringPool.BLANK,
			passwordReset, ldapUser.getReminderQueryQuestion(),
			ldapUser.getReminderQueryAnswer(), ldapUser.getScreenName(),
			ldapUser.getEmailAddress(), ldapUser.getFacebookId(),
			ldapUser.getOpenId(), (ldapUser.getPortraitId() > 0),
			ldapUser.getPortraitBytes(), ldapUser.getLanguageId(),
			ldapUser.getTimeZoneId(), ldapUser.getGreeting(),
			ldapUser.getComments(), ldapUser.getFirstName(),
			ldapUser.getMiddleName(), ldapUser.getLastName(),
			ldapUser.getPrefixId(), ldapUser.getSuffixId(), ldapUser.isMale(),
			birthdayMonth, birthdayDay, birthdayYear, ldapUser.getSmsSn(),
			ldapUser.getAimSn(), ldapUser.getFacebookSn(), ldapUser.getIcqSn(),
			ldapUser.getJabberSn(), ldapUser.getMsnSn(),
			ldapUser.getMySpaceSn(), ldapUser.getSkypeSn(),
			ldapUser.getTwitterSn(), ldapUser.getYmSn(), ldapUser.getJobTitle(),
			ldapUser.getGroupIds(), ldapUser.getOrganizationIds(),
			ldapUser.getRoleIds(), ldapUser.getUserGroupRoles(),
			ldapUser.getUserGroupIds(), ldapUser.getServiceContext());

		if (modifiedDate != null) {
			user = _userLocalService.updateModifiedDate(
				user.getUserId(), modifiedDate);
		}

		user = _userLocalService.updateStatus(
			user.getUserId(), ldapUser.getStatus(), new ServiceContext());

		return user;
	}

	private static final String[] _CONTACT_PROPERTY_NAMES = {
		"aimSn", "birthday", "employeeNumber", "facebookSn", "icqSn",
		"jabberSn", "male", "msnSn", "mySpaceSn", "prefixId", "skypeSn",
		"smsSn", "suffixId", "twitterSn", "ymSn"
	};

	private static final String _IMPORT_BY_GROUP = "group";

	private static final String _IMPORT_BY_USER = "user";

	private static final String _USER_PASSWORD_SCREEN_NAME = "screenName";

	private static final String[] _USER_PROPERTY_NAMES = {
		"comments", "emailAddress", "firstName", "greeting", "jobTitle",
		"languageId", "lastName", "middleName", "openId", "portraitId",
		"timeZoneId"
	};

	private static final String _USER_SYNC_STRATEGY_UUID = "uuid";

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPUserImporterImpl.class);

	private AttributesTransformer _attributesTransformer;
	private CompanyLocalService _companyLocalService;
	private ExpandoValueLocalService _expandoValueLocalService;
	private GroupLocalService _groupLocalService;
	private long _lastImportTime;
	private LDAPConfigurationSettingsUtil _ldapConfigurationSettingsUtil;
	private LDAPSettings _ldapSettings;
	private LDAPToPortalConverter _ldapToPortalConverter;
	private Set<String> _ldapUserIgnoreAttributes;
	private LockManager _lockManager;
	private PortalCache<String, Long> _portalCache;
	private PortalLDAP _portalLDAP;
	private RoleLocalService _roleLocalService;
	private UserGroupLocalService _userGroupLocalService;
	private UserLocalService _userLocalService;

}