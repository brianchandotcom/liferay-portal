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

package com.liferay.portal.ldap.internal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.ldap.configuration.SystemLDAPConfiguration;
import com.liferay.portal.ldap.constants.LDAPConstants;
import com.liferay.portal.ldap.constants.LegacyLDAPPropsKeys;
import com.liferay.portal.ldap.exportimport.configuration.LDAPExportConfiguration;
import com.liferay.portal.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.ldap.LDAPSettings;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.verify.VerifyProcess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.portal.ldap"},
	service = VerifyProcess.class
)
public class LDAPPropertiesVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyLDAPProperties();
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
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

	@Reference(unbind = "-")
	protected void setLdapSettings(LDAPSettings ldapSettings) {
		_ldapSettings = ldapSettings;
	}

	@Reference(unbind = "-")
	protected void setPrefsProps(PrefsProps prefsProps) {
		_prefsProps = prefsProps;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
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

	protected void verifyLDAPAuthProperties(long companyId) {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put(
			LDAPConstants.AUTH_ENABLED,
			_prefsProps.getBoolean(
				companyId, LegacyLDAPPropsKeys.LDAP_AUTH_ENABLED, false));
		dictionary.put(
			LDAPConstants.AUTH_METHOD,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_AUTH_METHOD, "bind"));
		dictionary.put(
			LDAPConstants.AUTH_REQUIRED,
			_prefsProps.getBoolean(
				companyId, LegacyLDAPPropsKeys.LDAP_AUTH_REQUIRED, false));
		dictionary.put(
			LDAPConstants.PASSWORD_ENCRYPTION_ALGORITHM,
			_prefsProps.getString(
				companyId,
				LegacyLDAPPropsKeys.LDAP_AUTH_PASSWORD_ENCRYPTION_ALGORITHM,
				"NONE"));
		dictionary.put(
			LDAPConstants.PASSWORD_POLICY_ENABLED,
			_prefsProps.getBoolean(
				companyId, LegacyLDAPPropsKeys.LDAP_PASSWORD_POLICY_ENABLED,
				false));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Adding LDAP auth configuration for company " + companyId +
					" with properties: " + dictionary);
		}

		_ldapAuthConfigurationProvider.updateProperties(companyId, dictionary);
	}

	protected void verifyLDAPProperties() throws Exception {
		List<Company> companies =_companyLocalService.getCompanies(false);

		for (Company company : companies) {
			long companyId = company.getCompanyId();

			verifyLDAPAuthProperties(companyId);
			verifyLDAPExportProperties(companyId);
			verifyLDAPImportProperties(companyId);
			verifySystemLDAPConfiguration(companyId);

			long[] ldapServerIds = StringUtil.split(
				_prefsProps.getString(companyId, "ldap.server.ids"), 0L);

			Set<String> keys = new HashSet<>();

			keys.addAll(
				Arrays.asList(LegacyLDAPPropsKeys.NONPOSTFIXED_LDAP_KEYS));

			for (long ldapServerId : ldapServerIds) {
				String postfix = _ldapSettings.getPropertyPostfix(ldapServerId);

				verifyLDAPServerConfiguration(companyId, ldapServerId, postfix);

				for (int i = 0;
						i < LegacyLDAPPropsKeys.SERVER_ID_POSTFIXED_KEYS.length;
							i++) {

					keys.add(
						LegacyLDAPPropsKeys.SERVER_ID_POSTFIXED_KEYS[i] +
							postfix);
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Removing preference keys " + keys + " for company " +
						companyId);
			}

			_companyLocalService.removePreferences(
				companyId, keys.toArray(new String[keys.size()]));

			UnicodeProperties properties = new UnicodeProperties();

			properties.put("ldap.server.ids", StringPool.BLANK);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Removing LDAP server IDs " +
						ListUtil.toList(ldapServerIds) + " for company " +
							companyId);
			}

			_companyLocalService.updatePreferences(companyId, properties);
		}
	}

	private void verifyLDAPExportProperties(long companyId) {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put(
			LDAPConstants.AUTH_REQUIRED,
			_prefsProps.getBoolean(
				companyId, LegacyLDAPPropsKeys.LDAP_AUTH_REQUIRED, false));
		dictionary.put(
			LDAPConstants.EXPORT_ENABLED,
			_prefsProps.getBoolean(
				companyId, LegacyLDAPPropsKeys.LDAP_EXPORT_ENABLED, false));
		dictionary.put(
			LDAPConstants.EXPORT_GROUP_ENABLED,
			_prefsProps.getBoolean(
				companyId, LegacyLDAPPropsKeys.LDAP_EXPORT_GROUP_ENABLED,
				false));
		dictionary.put(
			LDAPConstants.PASSWORD_ENCRYPTION_ALGORITHM,
			_prefsProps.getString(
				companyId,
				LegacyLDAPPropsKeys.LDAP_AUTH_PASSWORD_ENCRYPTION_ALGORITHM,
				"NONE"));
		dictionary.put(
			LDAPConstants.PASSWORD_POLICY_ENABLED,
			_prefsProps.getBoolean(
				companyId, LegacyLDAPPropsKeys.LDAP_PASSWORD_POLICY_ENABLED,
				false));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Adding LDAP export configuration for company " + companyId +
					" with properties: " + dictionary);
		}

		_ldapExportConfigurationProvider.updateProperties(
			companyId, dictionary);
	}

	private void verifyLDAPImportProperties(long companyId) {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put(
			LDAPConstants.IMPORT_CREATE_ROLE_PER_GROUP,
			_prefsProps.getBoolean(
				companyId,
				LegacyLDAPPropsKeys.LDAP_IMPORT_CREATE_ROLE_PER_GROUP, false));
		dictionary.put(
			LDAPConstants.IMPORT_ENABLED,
			_prefsProps.getBoolean(
				companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_ENABLED, false));
		dictionary.put(
			LDAPConstants.IMPORT_GROUP_CACHE_ENABLED,
			_prefsProps.getBoolean(
				companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_GROUP_CACHE_ENABLED,
				false));
		dictionary.put(
			LDAPConstants.IMPORT_INTERVAL,
			_prefsProps.getInteger(
				companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_INTERVAL, 10));
		dictionary.put(
			LDAPConstants.IMPORT_LOCK_EXPIRATION_TIME,
			_prefsProps.getLong(
				companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_LOCK_EXPIRATION_TIME,
				86400000));
		dictionary.put(
			LDAPConstants.IMPORT_METHOD,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_METHOD, "user"));
		dictionary.put(
			LDAPConstants.IMPORT_ON_STARTUP,
			_prefsProps.getBoolean(
				companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_ON_STARTUP, false));
		dictionary.put(
			LDAPConstants.IMPORT_USER_PASSWORD_AUTOGENERATED,
			_prefsProps.getBoolean(
				companyId,
				LegacyLDAPPropsKeys.LDAP_IMPORT_USER_PASSWORD_AUTOGENERATED,
				false));
		dictionary.put(
			LDAPConstants.IMPORT_USER_PASSWORD_DEFAULT,
			_prefsProps.getString(
				companyId,
				LegacyLDAPPropsKeys.LDAP_IMPORT_USER_PASSWORD_DEFAULT, "test"));
		dictionary.put(
			LDAPConstants.IMPORT_USER_PASSWORD_ENABLED,
			_prefsProps.getBoolean(
				companyId,
				LegacyLDAPPropsKeys.LDAP_IMPORT_USER_PASSWORD_ENABLED, true));
		dictionary.put(
			LDAPConstants.IMPORT_USER_SYNC_STRATEGY,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_USER_SYNC_STRATEGY,
				"auth-type"));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Adding LDAP import configuration for company " + companyId +
					" with properties: " + dictionary);
		}

		_ldapImportConfigurationProvider.updateProperties(
			companyId, dictionary);
	}

	private void verifyLDAPServerConfiguration(
		long companyId, long ldapServerId, String postfix) {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put(
			LDAPConstants.AUTH_SEARCH_FILTER,
			_prefsProps.getString(
				companyId,
				LegacyLDAPPropsKeys.LDAP_AUTH_SEARCH_FILTER + postfix,
				"(mail=@email_address@)"));
		dictionary.put(
			LDAPConstants.BASE_DN,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_BASE_DN + postfix,
				"dc=example,dc=com"));
		dictionary.put(
			LDAPConstants.BASE_PROVIDER_URL,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_BASE_PROVIDER_URL + postfix,
				"ldap://localhost:10389"));
		dictionary.put(
			LDAPConstants.CONTACT_CUSTOM_MAPPINGS,
			_prefsProps.getStringArray(
				companyId,
				LegacyLDAPPropsKeys.LDAP_CONTACT_CUSTOM_MAPPINGS + postfix,
				StringPool.NEW_LINE));
		dictionary.put(
			LDAPConstants.CONTACT_MAPPINGS,
			_prefsProps.getStringArray(
				companyId, LegacyLDAPPropsKeys.LDAP_CONTACT_MAPPINGS + postfix,
				StringPool.NEW_LINE));
		dictionary.put(
			LDAPConstants.GROUP_DEFAULT_OBJECT_CLASSES,
			_prefsProps.getStringArray(
				companyId,
				LegacyLDAPPropsKeys.LDAP_GROUP_DEFAULT_OBJECT_CLASSES + postfix,
				StringPool.COMMA));
		dictionary.put(
			LDAPConstants.GROUP_MAPPINGS,
			_prefsProps.getStringArray(
				companyId, LegacyLDAPPropsKeys.LDAP_GROUP_MAPPINGS + postfix,
				StringPool.NEW_LINE));
		dictionary.put(
			LDAPConstants.GROUP_SEARCH_FILTER,
			_prefsProps.getString(
				companyId,
				LegacyLDAPPropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix,
				"(objectClass=groupOfUniqueNames)"));
		dictionary.put(
			LDAPConstants.GROUP_SEARCH_FILTER_ENABLED,
			_prefsProps.getBoolean(
				companyId,
				LegacyLDAPPropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER_ENABLED +
					postfix,
				true));
		dictionary.put(
			LDAPConstants.GROUPS_DN,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_GROUPS_DN + postfix,
				"ou=groups,dc=example,dc=com"));
		dictionary.put(
			LDAPConstants.SECURITY_CREDENTIAL,
			_prefsProps.getString(
				companyId,
				LegacyLDAPPropsKeys.LDAP_SECURITY_CREDENTIALS + postfix,
				"secret"));
		dictionary.put(
			LDAPConstants.SECURITY_PRINCIPAL,
			_prefsProps.getString(
				companyId,
				LegacyLDAPPropsKeys.LDAP_SECURITY_PRINCIPAL + postfix,
				"uid=admin,ou=system"));
		dictionary.put(
			LDAPConstants.SERVER_NAME,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_SERVER_NAME + postfix));
		dictionary.put(
			LDAPConstants.USER_CUSTOM_MAPPINGS,
			_prefsProps.getStringArray(
				companyId,
				LegacyLDAPPropsKeys.LDAP_USER_CUSTOM_MAPPINGS + postfix,
				StringPool.NEW_LINE));
		dictionary.put(
			LDAPConstants.USER_DEFAULT_OBJECT_CLASSES,
			_prefsProps.getStringArray(
				companyId,
				LegacyLDAPPropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES + postfix,
				StringPool.COMMA));
		dictionary.put(
			LDAPConstants.USER_IGNORE_ATTRIBUTES,
			_prefsProps.getStringArray(
				companyId,
				LegacyLDAPPropsKeys.LDAP_USER_IGNORE_ATTRIBUTES + postfix,
				StringPool.COMMA));
		dictionary.put(
			LDAPConstants.USER_MAPPINGS,
			_prefsProps.getStringArray(
				companyId, LegacyLDAPPropsKeys.LDAP_USER_MAPPINGS + postfix,
				StringPool.NEW_LINE));
		dictionary.put(
			LDAPConstants.USER_SEARCH_FILTER,
			_prefsProps.getString(
				companyId,
				LegacyLDAPPropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER + postfix,
				"(objectClass=inetOrgPerson)"));
		dictionary.put(
			LDAPConstants.USERS_DN,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_USERS_DN + postfix,
				"users,dc=example,dc=com"));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Adding LDAP servier configuration for company " + companyId +
					" and LDAP server ID " + ldapServerId +
						" with properties: " + dictionary);
		}

		_ldapServerConfigurationProvider.updateProperties(
			companyId, ldapServerId, dictionary);
	}

	private void verifySystemLDAPConfiguration(long companyId) {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		Properties connectionProperties = _props.getProperties(
			LegacyLDAPPropsKeys.LDAP_CONNECTION_PROPERTY_PREFIX, true);

		List<String> connectionPropertiesList = new ArrayList<>(
			connectionProperties.size());

		for (Map.Entry entry : connectionProperties.entrySet()) {
			String connectionPropertyString =
				entry.getKey() + StringPool.EQUAL + entry.getValue();

			connectionPropertiesList.add(connectionPropertyString);
		}

		dictionary.put(
			LDAPConstants.CONNECTION_PROPERTIES,
			connectionPropertiesList.toArray(
				new String[connectionPropertiesList.size()]));
		dictionary.put(
			LDAPConstants.ERROR_PASSWORD_AGE,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_AGE, "age"));
		dictionary.put(
			LDAPConstants.ERROR_PASSWORD_EXPIRED,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_EXPIRED,
				"expired"));
		dictionary.put(
			LDAPConstants.ERROR_PASSWORD_HISTORY,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_HISTORY,
				"history"));
		dictionary.put(
			LDAPConstants.ERROR_PASSWORD_NOT_CHANGEABLE,
			_prefsProps.getString(
				companyId,
				LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_NOT_CHANGEABLE,
				"not allowed to change"));
		dictionary.put(
			LDAPConstants.ERROR_PASSWORD_SYNTAX,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_SYNTAX,
				"syntax"));
		dictionary.put(
			LDAPConstants.ERROR_PASSWORD_TRIVIAL,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_TRIVIAL,
				"trivial"));
		dictionary.put(
			LDAPConstants.ERROR_USER_LOCKOUT,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_ERROR_USER_LOCKOUT,
				"retry limit"));
		dictionary.put(
			LDAPConstants.FACTORY_INITIAL,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_FACTORY_INITIAL,
				"com.sun.jndi.ldap.LdapCtxFactory"));
		dictionary.put(
			LDAPConstants.PAGE_SIZE,
			_prefsProps.getInteger(
				companyId, LegacyLDAPPropsKeys.LDAP_PAGE_SIZE, 1000));
		dictionary.put(
			LDAPConstants.RANGE_SIZE,
			_prefsProps.getInteger(
				companyId, LegacyLDAPPropsKeys.LDAP_RANGE_SIZE, 1000));
		dictionary.put(
			LDAPConstants.REFERRAL,
			_prefsProps.getString(
				companyId, LegacyLDAPPropsKeys.LDAP_REFERRAL, "follow"));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Adding system LDAP configurations for company " + companyId +
					" with properties: " + dictionary);
		}

		_systemLDAPConfigurationProvider.updateProperties(
			companyId, dictionary);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPPropertiesVerifyProcess.class);

	private volatile CompanyLocalService _companyLocalService;
	private volatile ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;
	private volatile ConfigurationProvider<LDAPExportConfiguration>
		_ldapExportConfigurationProvider;
	private volatile ConfigurationProvider<LDAPImportConfiguration>
		_ldapImportConfigurationProvider;
	private volatile ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;
	private volatile LDAPSettings _ldapSettings;
	private volatile PrefsProps _prefsProps;
	private volatile Props _props;
	private volatile ConfigurationProvider<SystemLDAPConfiguration>
		_systemLDAPConfigurationProvider;

}