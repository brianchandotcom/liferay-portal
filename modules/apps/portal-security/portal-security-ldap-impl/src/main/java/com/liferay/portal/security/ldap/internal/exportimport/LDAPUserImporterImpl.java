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
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.expando.kernel.util.ExpandoConverterUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.GroupFriendlyURLException;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.NoSuchUserGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.exportimport.UserGroupImportTransactionThreadLocal;
import com.liferay.portal.kernel.security.ldap.AttributesTransformer;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.exportimport.UserImporter;
import com.liferay.portal.security.ldap.ContactConverterKeys;
import com.liferay.portal.security.ldap.PortalLDAP;
import com.liferay.portal.security.ldap.UserConverterKeys;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.exportimport.LDAPGroup;
import com.liferay.portal.security.ldap.exportimport.LDAPToPortalConverter;
import com.liferay.portal.security.ldap.exportimport.LDAPUser;
import com.liferay.portal.security.ldap.exportimport.LDAPUserImporter;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.security.ldap.internal.UserImportTransactionThreadLocal;
import com.liferay.portal.security.ldap.util.LDAPUtil;
import com.liferay.portal.security.ldap.validator.LDAPFilterValidator;

import java.io.Serializable;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Hugo Huijser
 * @author Edward C. Han
 */
@Component(
	immediate = true, service = {LDAPUserImporter.class, UserImporter.class}
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

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		LDAPImportContext ldapImportContext = getLDAPImportContext(
			companyId,
			_ldapSettings.getContactExpandoMappings(ldapServerId, companyId),
			_ldapSettings.getContactMappings(ldapServerId, companyId),
			_ldapSettings.getGroupMappings(ldapServerId, companyId),
			ldapContext, ldapServerId,
			new HashSet<>(
				Arrays.asList(ldapServerConfiguration.userIgnoreAttributes())),
			_ldapSettings.getUserExpandoMappings(ldapServerId, companyId),
			_ldapSettings.getUserMappings(ldapServerId, companyId));

		User user = importUser(
			ldapImportContext, StringPool.BLANK, attributes, password);

		importGroups(ldapImportContext, attributes, user);

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
			LDAPServerConfiguration ldapServerConfiguration =
				_ldapServerConfigurationProvider.getConfiguration(
					companyId, ldapServerId);

			ldapContext = _portalLDAP.getContext(ldapServerId, companyId);

			if (ldapContext == null) {
				_log.error("Unable to bind to the LDAP server");

				return null;
			}

			String baseDN = LDAPUtil.escapeCharacters(
				ldapServerConfiguration.baseDN());

			String filter = ldapServerConfiguration.authSearchFilter();

			if (_log.isDebugEnabled()) {
				_log.debug("Search filter before transformation " + filter);
			}

			filter = StringUtil.replace(
				filter,
				new String[] {
					"@company_id@", "@email_address@", "@screen_name@"
				},
				new String[] {
					String.valueOf(companyId),
					_portalLDAP.encodeFilterAttribute(emailAddress, false),
					_portalLDAP.encodeFilterAttribute(screenName, false)
				});

			_ldapFilterValidator.validate(filter);

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
					binding.getNameInNamespace());

				return importUser(
					ldapServerId, companyId, ldapContext, attributes, null);
			}

			return null;
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

		Collection<LDAPServerConfiguration> ldapServerConfigurations =
			_ldapServerConfigurationProvider.getConfigurations(companyId);

		for (LDAPServerConfiguration ldapServerConfiguration :
				ldapServerConfigurations) {

			String providerUrl = ldapServerConfiguration.baseProviderURL();

			if (Validator.isNull(providerUrl)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No provider URL defined in " +
							ldapServerConfiguration);
				}

				continue;
			}

			User user = importUser(
				ldapServerConfiguration.ldapServerId(), companyId, emailAddress,
				screenName);

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

		String fullUserDN = result.getNameInNamespace();

		Attributes attributes = _portalLDAP.getUserAttributes(
			ldapServerId, companyId, ldapContext, fullUserDN);

		User user = importUser(
			ldapServerId, companyId, ldapContext, attributes, null);

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

		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		try {
			long userId = _userLocalService.getDefaultUserId(companyId);

			Lock lock = _lockManager.lock(
				userId, UserImporter.class.getName(), companyId,
				LDAPUserImporterImpl.class.getName(), false,
				ldapImportConfiguration.importLockExpirationTime(), false);

			if (!lock.isNew()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Skipping LDAP import for company ", companyId,
							" because another LDAP import is in process by ",
							"the same user ", userId));
				}

				return;
			}
		}
		catch (DuplicateLockException dle) {
			if (_log.isDebugEnabled()) {
				Lock lock = dle.getLock();

				_log.debug(
					StringBundler.concat(
						"Skipping LDAP import for company ", companyId,
						" because another LDAP import is in process by ",
						"another user ", lock.getUserId()));
			}

			return;
		}
		catch (Throwable t) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping LDAP import for company " + companyId +
						" because unable to lock the lock",
					t);
			}

			return;
		}

		try {
			Collection<LDAPServerConfiguration> ldapServerConfigurations =
				_ldapServerConfigurationProvider.getConfigurations(companyId);

			for (LDAPServerConfiguration ldapServerConfiguration :
					ldapServerConfigurations) {

				importUsers(ldapServerConfiguration.ldapServerId(), companyId);
			}
		}
		finally {
			_lockManager.unlock(UserImporter.class.getName(), companyId);
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

		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		String[] userIgnoreAttributes =
			ldapServerConfiguration.userIgnoreAttributes();

		Set<String> ldapUserIgnoreAttributes = new HashSet<>(
			Arrays.asList(userIgnoreAttributes));

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

			String importMethod = ldapImportConfiguration.importMethod();

			LDAPImportContext ldapImportContext = getLDAPImportContext(
				companyId, contactExpandoMappings, contactMappings,
				groupMappings, ldapContext, ldapServerId,
				ldapUserIgnoreAttributes, userExpandoMappings, userMappings);

			if (importMethod.equals(_IMPORT_BY_GROUP)) {
				importFromLDAPByGroup(ldapImportContext);
			}
			else if (importMethod.equals(_IMPORT_BY_USER)) {
				importFromLDAPByUser(ldapImportContext);
			}
		}
		catch (Exception e) {
			_log.error("Unable to import LDAP users and groups", e);
		}
		finally {
			ldapContext.close();
		}
	}

	@Reference(unbind = "-")
	public void setSingleVMPool(SingleVMPool singleVMPool) {
		_portalCache = (PortalCache<String, Long>)singleVMPool.getPortalCache(
			UserImporter.class.getName(), false);
	}

	protected void addRole(
			long companyId, LDAPGroup ldapGroup, UserGroup userGroup)
		throws Exception {

		Company company = _companyLocalService.getCompany(companyId);

		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		if (!ldapImportConfiguration.importCreateRolePerGroup()) {
			return;
		}

		Role role = null;

		try {
			role = _roleLocalService.getRole(
				companyId, ldapGroup.getGroupName());
		}
		catch (NoSuchRoleException nsre) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsre, nsre);
			}

			User defaultUser = _userLocalService.getDefaultUser(companyId);

			Map<Locale, String> descriptionMap = new HashMap<>();

			descriptionMap.put(
				company.getLocale(), "Autogenerated role from LDAP import");

			role = _roleLocalService.addRole(
				defaultUser.getUserId(), null, 0, ldapGroup.getGroupName(),
				null, descriptionMap, RoleConstants.TYPE_REGULAR, null, null);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Imported autogenerated role from LDAP import: " + role);
			}
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

		StopWatch stopWatch = new StopWatch();

		if (_log.isDebugEnabled()) {
			stopWatch.start();

			_log.debug(
				StringBundler.concat(
					"Adding LDAP user ", ldapUser, " to company ", companyId));
		}

		boolean autoPassword = ldapUser.isAutoPassword();

		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		if (!ldapImportConfiguration.importUserPasswordEnabled()) {
			autoPassword =
				ldapImportConfiguration.importUserPasswordAutogenerated();

			if (!autoPassword) {
				String defaultPassword =
					ldapImportConfiguration.importUserPasswordDefault();

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

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Finished adding LDAP user ", ldapUser, " as user ", user,
					" in ", stopWatch.getTime(), "ms"));
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

	protected String escapeLDAPName(String ldapName) {
		return StringUtil.replace(ldapName, '\\', "\\\\");
	}

	protected LDAPImportContext getLDAPImportContext(
		long companyId, Properties contactExpandoMappings,
		Properties contactMappings, Properties groupMappings,
		LdapContext ldapContext, long ldapServerId,
		Set<String> ldapUserIgnoreAttributes, Properties userExpandoMappings,
		Properties userMappings) {

		return new LDAPImportContext(
			companyId, contactExpandoMappings, contactMappings, groupMappings,
			ldapContext, ldapServerId, ldapUserIgnoreAttributes,
			userExpandoMappings, userMappings);
	}

	protected User getUser(long companyId, LDAPUser ldapUser) throws Exception {
		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		if (Objects.equals(
				ldapImportConfiguration.importUserSyncStrategy(),
				_USER_SYNC_STRATEGY_UUID)) {

			ServiceContext serviceContext = ldapUser.getServiceContext();

			return _userLocalService.fetchUserByUuidAndCompanyId(
				serviceContext.getUuidWithoutReset(), companyId);
		}

		String authType = PrefsPropsUtil.getString(
			companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
			_companySecurityAuthType);

		if (authType.equals(CompanyConstants.AUTH_TYPE_SN) &&
			!ldapUser.isAutoScreenName()) {

			return _userLocalService.fetchUserByScreenName(
				companyId, ldapUser.getScreenName());
		}

		return _userLocalService.fetchUserByEmailAddress(
			companyId, ldapUser.getEmailAddress());
	}

	protected Attribute getUsers(
			LDAPImportContext ldapImportContext, Attributes groupAttributes,
			UserGroup userGroup)
		throws Exception {

		Properties groupMappings = ldapImportContext.getGroupMappings();

		Attribute attribute = groupAttributes.get(
			groupMappings.getProperty("user"));

		if (attribute == null) {
			return null;
		}

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				ldapImportContext.getCompanyId(),
				ldapImportContext.getLdapServerId());

		String baseDN = ldapServerConfiguration.baseDN();

		StringBundler sb = new StringBundler(7);

		sb.append("(&");

		String groupSearchFilter = ldapServerConfiguration.groupSearchFilter();

		_ldapFilterValidator.validate(
			groupSearchFilter, "LDAPServerConfiguration.groupSearchFilter");

		sb.append(groupSearchFilter);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(groupMappings.getProperty("groupName"));
		sb.append("=");
		sb.append(_portalLDAP.encodeFilterAttribute(userGroup.getName(), true));
		sb.append("))");

		return _portalLDAP.getMultivaluedAttribute(
			ldapImportContext.getCompanyId(),
			ldapImportContext.getLdapContext(), baseDN, sb.toString(),
			attribute);
	}

	protected void importFromLDAPByGroup(LDAPImportContext ldapImportContext)
		throws Exception {

		byte[] cookie = new byte[0];

		while (cookie != null) {
			List<SearchResult> searchResults = new ArrayList<>();

			Properties groupMappings = ldapImportContext.getGroupMappings();

			String groupMappingsGroupName = GetterUtil.getString(
				groupMappings.getProperty("groupName"));

			groupMappingsGroupName = StringUtil.toLowerCase(
				groupMappingsGroupName);

			cookie = _portalLDAP.getGroups(
				ldapImportContext.getLdapServerId(),
				ldapImportContext.getCompanyId(),
				ldapImportContext.getLdapContext(), cookie, 0,
				new String[] {groupMappingsGroupName}, searchResults);

			for (SearchResult searchResult : searchResults) {
				try {
					Attributes groupAttributes = _portalLDAP.getGroupAttributes(
						ldapImportContext.getLdapServerId(),
						ldapImportContext.getCompanyId(),
						ldapImportContext.getLdapContext(),
						searchResult.getNameInNamespace(), true);

					UserGroup userGroup = importUserGroup(
						ldapImportContext.getCompanyId(), groupAttributes,
						groupMappings);

					Attribute usersAttribute = getUsers(
						ldapImportContext, groupAttributes, userGroup);

					if (usersAttribute == null) {
						if (_log.isInfoEnabled()) {
							_log.info(
								"No users found in " + userGroup.getName());
						}

						continue;
					}

					importUsers(
						ldapImportContext, userGroup.getUserGroupId(),
						usersAttribute);
				}
				catch (Exception e) {
					_log.error("Unable to import group " + searchResult, e);
				}
			}
		}
	}

	protected void importFromLDAPByUser(LDAPImportContext ldapImportContext)
		throws Exception {

		byte[] cookie = new byte[0];

		while (cookie != null) {
			List<SearchResult> searchResults = new ArrayList<>();

			Properties userMappings = ldapImportContext.getUserMappings();

			String userMappingsScreenName = GetterUtil.getString(
				userMappings.getProperty("screenName"));

			userMappingsScreenName = StringUtil.toLowerCase(
				userMappingsScreenName);

			cookie = _portalLDAP.getUsers(
				ldapImportContext.getLdapServerId(),
				ldapImportContext.getCompanyId(),
				ldapImportContext.getLdapContext(), cookie, 0,
				new String[] {userMappingsScreenName}, searchResults);

			for (SearchResult searchResult : searchResults) {
				try {
					String fullUserDN = searchResult.getNameInNamespace();

					if (ldapImportContext.containsImportedUser(fullUserDN)) {
						continue;
					}

					Attributes userAttributes = _portalLDAP.getUserAttributes(
						ldapImportContext.getLdapServerId(),
						ldapImportContext.getCompanyId(),
						ldapImportContext.getLdapContext(), fullUserDN);

					User user = importUser(
						ldapImportContext, fullUserDN, userAttributes, null);

					importGroups(ldapImportContext, userAttributes, user);
				}
				catch (GroupFriendlyURLException gfurle) {
					int type = gfurle.getType();

					if (type == GroupFriendlyURLException.DUPLICATE) {
						_log.error(
							"Unable to import user " + searchResult +
								" because of a duplicate group friendly URL",
							gfurle);
					}
					else {
						_log.error(
							"Unable to import user " + searchResult, gfurle);
					}
				}
				catch (Exception e) {
					_log.error("Unable to import user " + searchResult, e);
				}
			}
		}
	}

	protected Set<Long> importGroup(
			LDAPImportContext ldapImportContext, String fullGroupDN, User user,
			Set<Long> newUserGroupIds)
		throws Exception {

		String userGroupIdKey = null;

		Long userGroupId = null;

		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(
				ldapImportContext.getCompanyId());

		if (ldapImportConfiguration.importGroupCacheEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append(ldapImportContext.getLdapServerId());
			sb.append(StringPool.UNDERLINE);
			sb.append(ldapImportContext.getCompanyId());
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
					ldapImportContext.getLdapServerId(),
					ldapImportContext.getCompanyId(),
					ldapImportContext.getLdapContext(), fullGroupDN);
			}
			catch (NameNotFoundException nnfe) {
				_log.error(
					"LDAP group not found with full group DN " + fullGroupDN,
					nnfe);
			}

			UserGroup userGroup = importUserGroup(
				ldapImportContext.getCompanyId(), groupAttributes,
				ldapImportContext.getGroupMappings());

			if (userGroup == null) {
				return newUserGroupIds;
			}

			userGroupId = userGroup.getUserGroupId();

			if (ldapImportConfiguration.importGroupCacheEnabled()) {
				_portalCache.put(userGroupIdKey, userGroupId);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Adding user ", user, " to user group ", userGroupId));
		}

		newUserGroupIds.add(userGroupId);

		return newUserGroupIds;
	}

	protected void importGroups(
			LDAPImportContext ldapImportContext, Attributes userAttributes,
			User user)
		throws Exception {

		Properties groupMappings = ldapImportContext.getGroupMappings();

		String groupMappingsUser = groupMappings.getProperty("user");

		Set<Long> newUserGroupIds = new LinkedHashSet<>();

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				ldapImportContext.getCompanyId(),
				ldapImportContext.getLdapServerId());

		if (Validator.isNotNull(groupMappingsUser) &&
			ldapServerConfiguration.groupSearchFilterEnabled()) {

			String baseDN = ldapServerConfiguration.baseDN();

			StringBundler sb = new StringBundler(9);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(StringPool.AMPERSAND);

			String groupSearchFilter =
				ldapServerConfiguration.groupSearchFilter();

			_ldapFilterValidator.validate(
				groupSearchFilter, "LDAPServerConfiguration.groupSearchFilter");

			sb.append(groupSearchFilter);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(groupMappingsUser);
			sb.append(StringPool.EQUAL);

			Binding binding = _portalLDAP.getUser(
				ldapImportContext.getLdapServerId(),
				ldapImportContext.getCompanyId(), user.getScreenName(),
				user.getEmailAddress());

			String fullUserDN = binding.getNameInNamespace();

			sb.append(_portalLDAP.encodeFilterAttribute(fullUserDN, false));

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
					ldapImportContext.getCompanyId(),
					ldapImportContext.getLdapContext(), cookie, 0, baseDN,
					sb.toString(), new String[] {groupMappingsGroupName},
					searchResults);

				for (SearchResult searchResult : searchResults) {
					String fullGroupDN = searchResult.getNameInNamespace();

					newUserGroupIds = importGroup(
						ldapImportContext, fullGroupDN, user, newUserGroupIds);
				}
			}
		}
		else {
			Properties userMappings = ldapImportContext.getUserMappings();

			String userMappingsGroup = userMappings.getProperty("group");

			if (Validator.isNull(userMappingsGroup)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Skipping group import because no mappings for LDAP " +
							"groups were specified in user mappings " +
								userMappings);
				}

				return;
			}

			Attribute userGroupAttribute = userAttributes.get(
				userMappingsGroup);

			if (userGroupAttribute == null) {
				return;
			}

			for (int i = 0; i < userGroupAttribute.size(); i++) {
				String fullGroupDN = (String)userGroupAttribute.get(i);

				newUserGroupIds = importGroup(
					ldapImportContext, fullGroupDN, user, newUserGroupIds);
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
			LDAPImportContext ldapImportContext, String fullUserDN,
			Attributes userLdapAttribtes, String password)
		throws Exception {

		UserImportTransactionThreadLocal.setOriginatesFromImport(true);

		try {
			userLdapAttribtes = _attributesTransformer.transformUser(
				userLdapAttribtes);

			LDAPUser ldapUser = _ldapToPortalConverter.importLDAPUser(
				ldapImportContext.getCompanyId(), userLdapAttribtes,
				ldapImportContext.getUserMappings(),
				ldapImportContext.getUserExpandoMappings(),
				ldapImportContext.getContactMappings(),
				ldapImportContext.getContactExpandoMappings(), password);

			User user = getUser(ldapImportContext.getCompanyId(), ldapUser);

			if ((user != null) && user.isDefaultUser()) {
				return user;
			}

			ServiceContext serviceContext = ldapUser.getServiceContext();

			serviceContext.setAttribute(
				"ldapServerId", ldapImportContext.getLdapServerId());

			boolean isNew = false;

			if (user == null) {
				user = addUser(
					ldapImportContext.getCompanyId(), ldapUser, password);

				isNew = true;
			}

			String modifyTimestamp = LDAPUtil.getAttributeString(
				userLdapAttribtes, "modifyTimestamp");

			user = updateUser(
				ldapImportContext, ldapUser, user, password, modifyTimestamp,
				isNew);

			updateExpandoAttributes(ldapImportContext, user, ldapUser);

			ldapImportContext.addImportedUserId(fullUserDN, user.getUserId());

			return user;
		}
		finally {
			UserImportTransactionThreadLocal.setOriginatesFromImport(false);
		}
	}

	protected UserGroup importUserGroup(
			long companyId, Attributes groupAttributes,
			Properties groupMappings)
		throws Exception {

		groupAttributes = _attributesTransformer.transformGroup(
			groupAttributes);

		LDAPGroup ldapGroup = _ldapToPortalConverter.importLDAPGroup(
			companyId, groupAttributes, groupMappings);

		UserGroup userGroup = null;

		try {
			userGroup = _userGroupLocalService.getUserGroup(
				companyId, ldapGroup.getGroupName());

			if (!Objects.equals(
					userGroup.getDescription(), ldapGroup.getDescription())) {

				_userGroupLocalService.updateUserGroup(
					companyId, userGroup.getUserGroupId(),
					ldapGroup.getGroupName(), ldapGroup.getDescription(), null);
			}
		}
		catch (NoSuchUserGroupException nsuge) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsuge, nsuge);
			}

			StopWatch stopWatch = new StopWatch();

			if (_log.isDebugEnabled()) {
				stopWatch.start();

				_log.debug("Adding LDAP group " + ldapGroup);
			}

			long defaultUserId = _userLocalService.getDefaultUserId(companyId);

			UserGroupImportTransactionThreadLocal.setOriginatesFromImport(true);

			try {
				userGroup = _userGroupLocalService.addUserGroup(
					defaultUserId, companyId, ldapGroup.getGroupName(),
					ldapGroup.getDescription(), null);

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Finished adding LDAP group ", ldapGroup,
							" as user group ", userGroup, " in ",
							stopWatch.getTime(), "ms"));
				}
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
			LDAPImportContext ldapImportContext, long userGroupId,
			Attribute usersLdapAttribute)
		throws Exception {

		if (_log.isDebugEnabled()) {
			int size = usersLdapAttribute.size();

			_log.debug(
				StringBundler.concat(
					"Importing ", size, " users from LDAP server ",
					ldapImportContext.getLdapServerId(), " to company ",
					ldapImportContext.getCompanyId()));
		}

		Set<Long> newUserIds = new LinkedHashSet<>();

		for (int i = 0; i < usersLdapAttribute.size(); i++) {
			String fullUserDN = (String)usersLdapAttribute.get(i);

			Long userId = ldapImportContext.getImportedUserId(fullUserDN);

			if (userId != null) {
				newUserIds.add(userId);
			}
			else {
				Attributes userAttributes = null;

				try {
					userAttributes = _portalLDAP.getUserAttributes(
						ldapImportContext.getLdapServerId(),
						ldapImportContext.getCompanyId(),
						ldapImportContext.getLdapContext(), fullUserDN);
				}
				catch (NameNotFoundException nnfe) {
					_log.error(
						"LDAP user not found with fullUserDN " + fullUserDN,
						nnfe);

					continue;
				}

				try {
					User user = importUser(
						ldapImportContext, fullUserDN, userAttributes, null);

					if (user != null) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								StringBundler.concat(
									"Adding user ", user, " to user group ",
									userGroupId));
						}

						newUserIds.add(user.getUserId());
					}
				}
				catch (GroupFriendlyURLException gfurle) {
					int type = gfurle.getType();

					if (type == GroupFriendlyURLException.DUPLICATE) {
						_log.error(
							"Unable to import user " + userAttributes +
								" because of a duplicate group friendly URL",
							gfurle);
					}
					else {
						_log.error(
							"Unable to import user " + userAttributes, gfurle);
					}
				}
				catch (Exception e) {
					_log.error("Unable to load user " + userAttributes, e);
				}
			}
		}

		Set<Long> deletedUserIds = new LinkedHashSet<>();

		List<User> userGroupUsers = _userLocalService.getUserGroupUsers(
			userGroupId);

		for (User user : userGroupUsers) {
			if ((ldapImportContext.getLdapServerId() ==
					user.getLdapServerId()) &&
				!newUserIds.contains(user.getUserId())) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Removing user ", user, " from user group ",
							userGroupId));
				}

				deletedUserIds.add(user.getUserId());
			}
		}

		_userLocalService.addUserGroupUsers(
			userGroupId, ArrayUtil.toLongArray(newUserIds));

		_userLocalService.deleteUserGroupUsers(
			userGroupId, ArrayUtil.toLongArray(deletedUserIds));
	}

	protected void populateExpandoAttributes(
		ExpandoBridge expandoBridge, Map<String, String[]> expandoAttributes,
		Properties expandoMappings, Set<String> ldapUserIgnoreAttributes) {

		Map<String, Serializable> serializedExpandoAttributes = new HashMap<>();

		for (Map.Entry<String, String[]> expandoAttribute :
				expandoAttributes.entrySet()) {

			String name = expandoAttribute.getKey();

			if (!expandoBridge.hasAttribute(name)) {
				continue;
			}

			if (expandoMappings.containsKey(name) &&
				!ldapUserIgnoreAttributes.contains(name)) {

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

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration)",
		unbind = "-"
	)
	protected void setLDAPImportConfigurationProvider(
		ConfigurationProvider<LDAPImportConfiguration>
			ldapImportConfigurationProvider) {

		_ldapImportConfigurationProvider = ldapImportConfigurationProvider;
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
	protected void setLockManager(LockManager lockManager) {
		_lockManager = lockManager;
	}

	protected void setProperty(
		Object bean1, Object bean2, String propertyName) {

		Object value = BeanPropertiesUtil.getObject(bean2, propertyName);

		BeanPropertiesUtil.setProperty(bean1, propertyName, value);
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_companySecurityAuthType = GetterUtil.getString(
			props.get(PropsKeys.COMPANY_SECURITY_AUTH_TYPE));
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
			LDAPImportContext ldapImportContext, User user, LDAPUser ldapUser)
		throws Exception {

		ExpandoBridge userExpandoBridge = user.getExpandoBridge();

		populateExpandoAttributes(
			userExpandoBridge, ldapUser.getUserExpandoAttributes(),
			ldapImportContext.getUserExpandoMappings(),
			ldapImportContext.getLdapUserIgnoreAttributes());

		Contact contact = user.getContact();

		ExpandoBridge contactExpandoBridge = contact.getExpandoBridge();

		populateExpandoAttributes(
			contactExpandoBridge, ldapUser.getContactExpandoAttributes(),
			ldapImportContext.getContactExpandoMappings(),
			ldapImportContext.getLdapUserIgnoreAttributes());
	}

	protected void updateLDAPUser(
			User ldapUser, Contact ldapContact, User user,
			Properties userMappings, Properties contactMappings,
			Set<String> ldapUserIgnoreAttributes)
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
				ldapUserIgnoreAttributes.contains(propertyName)) {

				setProperty(ldapContact, contact, propertyName);
			}
		}

		for (String propertyName : _USER_PROPERTY_NAMES) {
			String mappingPropertyName = propertyName;

			if (propertyName.equals("portraitId")) {
				mappingPropertyName = UserConverterKeys.PORTRAIT;
			}

			if (!userMappings.containsKey(mappingPropertyName) ||
				ldapUserIgnoreAttributes.contains(propertyName)) {

				setProperty(ldapUser, user, propertyName);
			}
		}
	}

	protected User updateUser(
			LDAPImportContext ldapImportContext, LDAPUser ldapUser, User user,
			String password, String modifyTimestamp, boolean isNew)
		throws Exception {

		StopWatch stopWatch = new StopWatch();

		long companyId = ldapImportContext.getCompanyId();
		long ldapServerId = ldapImportContext.getLdapServerId();

		if (_log.isDebugEnabled()) {
			stopWatch.start();

			if (isNew) {
				_log.debug(
					StringBundler.concat(
						"Updating new user ", user, " from LDAP server ",
						ldapServerId, " to company ", companyId));
			}
			else {
				_log.debug(
					StringBundler.concat(
						"Updating existing user ", user, " from LDAP server ",
						ldapServerId, " to company ", companyId));
			}
		}

		Date modifiedDate = null;

		try {
			modifiedDate = LDAPUtil.parseDate(modifyTimestamp);
		}
		catch (ParseException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse LDAP modify timestamp " + modifyTimestamp,
					pe);
			}
		}

		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		boolean passwordReset = ldapUser.isPasswordReset();

		if (_ldapSettings.isExportEnabled(companyId)) {
			passwordReset = user.isPasswordReset();
		}

		if ((modifiedDate != null) &&
			modifiedDate.equals(user.getModifiedDate())) {

			if ((ldapUser.isUpdatePassword() ||
				 !ldapImportConfiguration.importUserPasswordEnabled()) &&
				!modifiedDate.equals(user.getPasswordModifiedDate())) {

				updateUserPassword(
					ldapImportConfiguration, user.getUserId(),
					user.getScreenName(), password, passwordReset,
					modifiedDate);

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Synchronizing password for ",
							user.getEmailAddress(),
							" because it might be out of date"));
				}
			}

			return user;
		}
		else if ((modifiedDate == null) && !isNew) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skipping user " + user.getEmailAddress() +
						" because the LDAP entry was never modified");
			}

			return user;
		}

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		String[] userIgnoreAttributes =
			ldapServerConfiguration.userIgnoreAttributes();

		Set<String> ldapUserIgnoreAttributes = new HashSet<>(
			Arrays.asList(userIgnoreAttributes));

		if (Validator.isNull(ldapUser.getScreenName()) ||
			ldapUser.isAutoScreenName()) {

			ldapUser.setScreenName(user.getScreenName());
		}

		if (ldapUser.isUpdatePassword() ||
			!ldapImportConfiguration.importUserPasswordEnabled()) {

			password = updateUserPassword(
				ldapImportConfiguration, user.getUserId(),
				ldapUser.getScreenName(), password, passwordReset,
				modifiedDate);
		}

		Contact ldapContact = ldapUser.getContact();

		updateLDAPUser(
			ldapUser.getUser(), ldapContact, user,
			ldapImportContext.getUserMappings(),
			ldapImportContext.getContactMappings(), ldapUserIgnoreAttributes);

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(ldapContact.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		ServiceContext serviceContext = ldapUser.getServiceContext();

		if (modifiedDate != null) {
			serviceContext.setModifiedDate(modifiedDate);
		}

		user = _userLocalService.updateUser(
			user.getUserId(), password, StringPool.BLANK, StringPool.BLANK,
			passwordReset, ldapUser.getReminderQueryQuestion(),
			ldapUser.getReminderQueryAnswer(), ldapUser.getScreenName(),
			ldapUser.getEmailAddress(), ldapUser.getFacebookId(),
			ldapUser.getOpenId(), ldapUser.isUpdatePortrait(),
			ldapUser.getPortraitBytes(), ldapUser.getLanguageId(),
			ldapUser.getTimeZoneId(), ldapUser.getGreeting(),
			ldapUser.getComments(), ldapUser.getFirstName(),
			ldapUser.getMiddleName(), ldapUser.getLastName(),
			ldapUser.getPrefixId(), ldapUser.getSuffixId(), ldapUser.isMale(),
			birthdayMonth, birthdayDay, birthdayYear, ldapUser.getSmsSn(),
			ldapUser.getFacebookSn(), ldapUser.getJabberSn(),
			ldapUser.getSkypeSn(), ldapUser.getTwitterSn(),
			ldapUser.getJobTitle(), ldapUser.getGroupIds(),
			ldapUser.getOrganizationIds(), ldapUser.getRoleIds(),
			ldapUser.getUserGroupRoles(), ldapUser.getUserGroupIds(),
			serviceContext);

		if (user.getStatus() != ldapUser.getStatus()) {
			user = _userLocalService.updateStatus(
				user.getUserId(), ldapUser.getStatus(), serviceContext);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Finished update for user ", user, " in ",
					stopWatch.getTime(), "ms"));
		}

		return user;
	}

	protected String updateUserPassword(
			LDAPImportConfiguration ldapImportConfiguration, long userId,
			String screenName, String password, boolean passwordReset,
			Date modifiedDate)
		throws PortalException {

		boolean passwordGenerated = false;

		if (!ldapImportConfiguration.importUserPasswordEnabled()) {
			passwordGenerated = true;

			if (ldapImportConfiguration.importUserPasswordAutogenerated()) {
				password = PwdGenerator.getPassword();
			}
			else {
				password = ldapImportConfiguration.importUserPasswordDefault();

				if (StringUtil.equalsIgnoreCase(
						password, _USER_PASSWORD_SCREEN_NAME)) {

					password = screenName;
				}
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setModifiedDate(modifiedDate);

		try {
			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			User user = _userLocalService.updatePassword(
				userId, password, password, passwordReset, true);

			if (!passwordGenerated) {
				user.setDigest(user.getDigest(password));
			}

			user.setPasswordModifiedDate(modifiedDate);

			_userLocalService.updateUser(user);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		return password;
	}

	private static final String[] _CONTACT_PROPERTY_NAMES = {
		"birthday", "employeeNumber", "facebookSn", "jabberSn", "male",
		"prefixId", "skypeSn", "smsSn", "suffixId", "twitterSn"
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

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile AttributesTransformer _attributesTransformer;

	private CompanyLocalService _companyLocalService;
	private String _companySecurityAuthType;
	private ExpandoValueLocalService _expandoValueLocalService;
	private GroupLocalService _groupLocalService;
	private long _lastImportTime;

	@Reference
	private LDAPFilterValidator _ldapFilterValidator;

	private ConfigurationProvider<LDAPImportConfiguration>
		_ldapImportConfigurationProvider;
	private ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;
	private LDAPSettings _ldapSettings;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile LDAPToPortalConverter _ldapToPortalConverter;

	private LockManager _lockManager;
	private PortalCache<String, Long> _portalCache;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile PortalLDAP _portalLDAP;

	private RoleLocalService _roleLocalService;
	private UserGroupLocalService _userGroupLocalService;
	private UserLocalService _userLocalService;

}