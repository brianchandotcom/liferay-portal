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

package com.liferay.portal.ldap;

import com.liferay.portal.kernel.ldap.LDAPUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.ldap.configuration.LDAPConfiguration;
import com.liferay.portal.ldap.settings.LDAPConfigurationSettingsUtil;
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
@Component(
	configurationPid = "com.liferay.portal.ldap.configuration.LDAPConfiguration",
	immediate = true, service = LDAPSettings.class
)
public class DefaultLDAPSettings implements LDAPSettings {

	@Override
	public String getAuthSearchFilter(
			long ldapServerId, long companyId, String emailAddress,
			String screenName, String userId)
		throws Exception {

		String postfix = getPropertyPostfix(ldapServerId);

		String filter = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_AUTH_SEARCH_FILTER + postfix);

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

		String postfix = getPropertyPostfix(ldapServerId);

		Properties contactExpandoMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_CONTACT_CUSTOM_MAPPINGS + postfix,
				StringPool.BLANK));

		LogUtil.debug(_log, contactExpandoMappings);

		return contactExpandoMappings;
	}

	@Override
	public Properties getContactMappings(long ldapServerId, long companyId)
		throws Exception {

		String postfix = getPropertyPostfix(ldapServerId);

		Properties contactMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_CONTACT_MAPPINGS + postfix,
				StringPool.BLANK));

		LogUtil.debug(_log, contactMappings);

		return contactMappings;
	}

	@Override
	public Properties getGroupMappings(long ldapServerId, long companyId)
		throws Exception {

		String postfix = getPropertyPostfix(ldapServerId);

		Properties groupMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_GROUP_MAPPINGS + postfix,
				StringPool.BLANK));

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

		String postfix = getPropertyPostfix(ldapServerId);

		Properties userExpandoMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_USER_CUSTOM_MAPPINGS + postfix,
				StringPool.BLANK));

		LogUtil.debug(_log, userExpandoMappings);

		return userExpandoMappings;
	}

	@Override
	public Properties getUserMappings(long ldapServerId, long companyId)
		throws Exception {

		String postfix = getPropertyPostfix(ldapServerId);

		Properties userMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_USER_MAPPINGS + postfix,
				StringPool.BLANK));

		LogUtil.debug(_log, userMappings);

		return userMappings;
	}

	@Override
	public boolean isExportEnabled(long companyId) {
		LDAPConfiguration ldapCompanyServiceSettings =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		boolean defaultImportUserPasswordAutogenerated =
			ldapCompanyServiceSettings.importUserPasswordAutogenerated();

		if (isImportEnabled(companyId) &&
			defaultImportUserPasswordAutogenerated) {

			return false;
		}

		return ldapCompanyServiceSettings.exportEnabled();
	}

	@Override
	public boolean isExportGroupEnabled(long companyId) {
		LDAPConfiguration ldapCompanyServiceSettings =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		return ldapCompanyServiceSettings.exportGroupEnabled();
	}

	@Override
	public boolean isImportEnabled(long companyId) {
		LDAPConfiguration ldapCompanyServiceSettings =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		return ldapCompanyServiceSettings.importEnabled();
	}

	@Override
	public boolean isImportOnStartup(long companyId) {
		LDAPConfiguration ldapCompanyServiceSettings =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		return ldapCompanyServiceSettings.importOnStartup();
	}

	@Override
	public boolean isPasswordPolicyEnabled(long companyId) {
		LDAPConfiguration ldapCompanyServiceSettings =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

		return ldapCompanyServiceSettings.passwordPolicyEnabled();
	}

	@Reference(unbind = "-")
	protected void setLdapConfigurationSettingsUtil(
		LDAPConfigurationSettingsUtil ldapConfigurationSettingsUtil) {

		_ldapConfigurationSettingsUtil = ldapConfigurationSettingsUtil;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultLDAPSettings.class);

	private LDAPConfigurationSettingsUtil _ldapConfigurationSettingsUtil;
	private UserLocalService _userLocalService;

}