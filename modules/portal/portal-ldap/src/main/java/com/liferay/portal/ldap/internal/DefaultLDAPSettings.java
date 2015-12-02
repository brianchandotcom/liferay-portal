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

package com.liferay.portal.ldap.internal;

import com.liferay.portal.kernel.ldap.LDAPUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.ldap.configuration.SystemLDAPConfiguration;
import com.liferay.portal.ldap.exportimport.configuration.LDAPExportConfiguration;
import com.liferay.portal.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.LDAPSettings;
import com.liferay.portal.service.UserLocalService;

import java.util.Properties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = LDAPSettings.class)
public class DefaultLDAPSettings implements LDAPSettings {

	@Override
	public String getAuthSearchFilter(
			long ldapServerId, long companyId, String emailAddress,
			String screenName, String userId)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		String filter = ldapServerConfiguration.authSearchFilter();

		if (_log.isDebugEnabled()) {
			_log.debug("Search filter before transformation " + filter);
		}

		filter = StringUtil.replace(
			filter,
			new String[] {
				"@company_id@", "@email_address@", "@screen_name@", "@user_id@"
			},
			new String[] {
				String.valueOf(companyId), emailAddress, screenName, userId
			});

		LDAPUtil.validateFilter(filter);

		if (_log.isDebugEnabled()) {
			_log.debug("Search filter after transformation " + filter);
		}

		return filter;
	}

	@Override
	public Properties getContactExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		Properties contactExpandoMappings = getProperties(
			ldapServerConfiguration.contactCustomMappings());

		LogUtil.debug(_log, contactExpandoMappings);

		return contactExpandoMappings;
	}

	@Override
	public Properties getContactMappings(long ldapServerId, long companyId)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		Properties contactMappings = getProperties(
			ldapServerConfiguration.contactMappings());

		LogUtil.debug(_log, contactMappings);

		return contactMappings;
	}

	@Override
	public String getErrorPasswordHistory(long companyId) {
		SystemLDAPConfiguration systemLDAPConfiguration =
			_systemLDAPConfigurationProvider.getConfiguration(companyId);

		return systemLDAPConfiguration.errorPasswordHistory();
	}

	@Override
	public Properties getGroupMappings(long ldapServerId, long companyId)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		Properties groupMappings = getProperties(
			ldapServerConfiguration.groupMappings());

		LogUtil.debug(_log, groupMappings);

		return groupMappings;
	}

	@Override
	public long getPreferredLDAPServerId(long companyId, String screenName) {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, screenName);

		if (user == null) {
			return -1;
		}

		return user.getLdapServerId();
	}

	@Override
	public String getPropertyPostfix(long ldapServerId) {
		return StringPool.PERIOD + ldapServerId;
	}

	@Override
	public Properties getUserExpandoMappings(long ldapServerId, long companyId)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		Properties contactExpandoMappings = getProperties(
			ldapServerConfiguration.userCustomMappings());

		LogUtil.debug(_log, contactExpandoMappings);

		return contactExpandoMappings;
	}

	@Override
	public Properties getUserMappings(long ldapServerId, long companyId)
		throws Exception {

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		Properties userMappings = getProperties(
			ldapServerConfiguration.userMappings());

		LogUtil.debug(_log, userMappings);

		return userMappings;
	}

	@Override
	public boolean isExportEnabled(long companyId) {
		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		boolean defaultImportUserPasswordAutogenerated =
			ldapImportConfiguration.importUserPasswordAutogenerated();

		if (ldapImportConfiguration.importEnabled() &&
			defaultImportUserPasswordAutogenerated) {

			return false;
		}

		LDAPExportConfiguration ldapExportConfiguration =
			_ldapExportConfigurationProvider.getConfiguration(companyId);

		return ldapExportConfiguration.exportEnabled();
	}

	@Override
	public boolean isExportGroupEnabled(long companyId) {
		LDAPExportConfiguration ldapExportConfiguration =
			_ldapExportConfigurationProvider.getConfiguration(companyId);

		return ldapExportConfiguration.exportGroupEnabled();
	}

	@Override
	public boolean isImportEnabled(long companyId) {
		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		return ldapImportConfiguration.importEnabled();
	}

	@Override
	public boolean isImportOnStartup(long companyId) {
		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		return ldapImportConfiguration.importOnStartup();
	}

	@Override
	public boolean isPasswordPolicyEnabled(long companyId) {
		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(companyId);

		return ldapAuthConfiguration.passwordPolicyEnabled();
	}

	protected Properties getProperties(String[] keyValuePairs) {
		Properties properties = new Properties();

		for (String keyValuePair : keyValuePairs) {
			String[] keyValue = StringUtil.split(keyValuePair, CharPool.EQUAL);

			String value = StringPool.BLANK;

			if (keyValue.length == 2) {
				value = keyValue[1];
			}

			properties.put(keyValue[0], value);
		}

		return properties;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.ldap.authenticator.configuration.LDAPAuthConfiguration)",
		unbind = "-"
	)
	protected void setLDAPAuthConfigurationProvider(
		ConfigurationProvider<LDAPAuthConfiguration>
			LDAPAuthConfigurationProvider) {

		_ldapAuthConfigurationProvider = LDAPAuthConfigurationProvider;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.ldap.exportimport.configuration.LDAPExportConfiguration)",
		unbind = "-"
	)
	protected void setLDAPExportConfigurationProvider(
		ConfigurationProvider<LDAPExportConfiguration>
			ldapExportConfigurationProvider) {

		_ldapExportConfigurationProvider = ldapExportConfigurationProvider;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.ldap.exportimport.configuration.LDAPImportConfiguration)",
		unbind = "-"
	)
	protected void setLDAPImportConfigurationProvider(
		ConfigurationProvider<LDAPImportConfiguration>
			ldapImportConfigurationProvider) {

		_ldapImportConfigurationProvider = ldapImportConfigurationProvider;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.ldap.configuration.LDAPServerConfiguration)",
		unbind = "-"
	)
	protected void setLDAPServerConfigurationProvider(
		ConfigurationProvider<LDAPServerConfiguration>
			ldapServerConfigurationProvider) {

		_ldapServerConfigurationProvider = ldapServerConfigurationProvider;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.ldap.configuration.SystemLDAPConfiguration)",
		unbind = "-"
	)
	protected void setSystemLDAPConfigurationProvider(
		ConfigurationProvider<SystemLDAPConfiguration>
			systemLDAPConfigurationProvider) {

		_systemLDAPConfigurationProvider = systemLDAPConfigurationProvider;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultLDAPSettings.class);

	private volatile ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;
	private volatile ConfigurationProvider<LDAPExportConfiguration>
		_ldapExportConfigurationProvider;
	private volatile ConfigurationProvider<LDAPImportConfiguration>
		_ldapImportConfigurationProvider;
	private volatile ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;
	private volatile ConfigurationProvider<SystemLDAPConfiguration>
		_systemLDAPConfigurationProvider;
	private volatile UserLocalService _userLocalService;

}