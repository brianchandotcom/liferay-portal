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

package com.liferay.portal.security.ldap.internal.verify;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration;
import com.liferay.portal.security.ldap.constants.LDAPConstants;
import com.liferay.portal.security.ldap.constants.LegacyLDAPPropsKeys;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.verify.VerifyProcess;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.portal.security.ldap",
	service = VerifyProcess.class
)
public class LDAPPropertiesVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_verifyLDAPProperties();
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
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
		target = "(factoryPid=com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration)",
		unbind = "-"
	)
	protected void setLDAPExportConfigurationProvider(
		ConfigurationProvider<LDAPExportConfiguration>
			ldapExportConfigurationProvider) {

		_ldapExportConfigurationProvider = ldapExportConfigurationProvider;
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
	protected void setPrefsProps(PrefsProps prefsProps) {
		_prefsProps = prefsProps;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration)",
		unbind = "-"
	)
	protected void setSystemLDAPConfigurationProvider(
		ConfigurationProvider<SystemLDAPConfiguration>
			systemLDAPConfigurationProvider) {

		_systemLDAPConfigurationProvider = systemLDAPConfigurationProvider;
	}

	private void _verifyLDAPAuthProperties(long companyId) {
		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				LDAPConstants.AUTH_ENABLED,
				_prefsProps.getBoolean(
					companyId, LegacyLDAPPropsKeys.LDAP_AUTH_ENABLED, false)
			).put(
				LDAPConstants.AUTH_METHOD,
				_prefsProps.getString(
					companyId, LegacyLDAPPropsKeys.LDAP_AUTH_METHOD, "bind")
			).put(
				LDAPConstants.AUTH_REQUIRED,
				_prefsProps.getBoolean(
					companyId, LegacyLDAPPropsKeys.LDAP_AUTH_REQUIRED, false)
			).put(
				LDAPConstants.PASSWORD_ENCRYPTION_ALGORITHM,
				_prefsProps.getString(
					companyId,
					LegacyLDAPPropsKeys.LDAP_AUTH_PASSWORD_ENCRYPTION_ALGORITHM,
					"NONE")
			).put(
				LDAPConstants.PASSWORD_POLICY_ENABLED,
				_prefsProps.getBoolean(
					companyId, LegacyLDAPPropsKeys.LDAP_PASSWORD_POLICY_ENABLED,
					false)
			).build();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Adding LDAP auth configuration for company ", companyId,
					" with properties: ", dictionary));
		}

		_ldapAuthConfigurationProvider.updateProperties(companyId, dictionary);
	}

	private void _verifyLDAPExportProperties(long companyId) {
		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				LDAPConstants.EXPORT_ENABLED,
				_prefsProps.getBoolean(
					companyId, LegacyLDAPPropsKeys.LDAP_EXPORT_ENABLED, false)
			).put(
				LDAPConstants.EXPORT_GROUP_ENABLED,
				_prefsProps.getBoolean(
					companyId, LegacyLDAPPropsKeys.LDAP_EXPORT_GROUP_ENABLED,
					false)
			).build();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Adding LDAP export configuration for company ", companyId,
					" with properties: ", dictionary));
		}

		_ldapExportConfigurationProvider.updateProperties(
			companyId, dictionary);
	}

	private void _verifyLDAPImportProperties(long companyId) {
		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				LDAPConstants.IMPORT_CREATE_ROLE_PER_GROUP,
				_prefsProps.getBoolean(
					companyId,
					LegacyLDAPPropsKeys.LDAP_IMPORT_CREATE_ROLE_PER_GROUP,
					false)
			).put(
				LDAPConstants.IMPORT_ENABLED,
				_prefsProps.getBoolean(
					companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_ENABLED, false)
			).put(
				LDAPConstants.IMPORT_GROUP_CACHE_ENABLED,
				_prefsProps.getBoolean(
					companyId,
					LegacyLDAPPropsKeys.LDAP_IMPORT_GROUP_CACHE_ENABLED, false)
			).put(
				LDAPConstants.IMPORT_INTERVAL,
				_prefsProps.getInteger(
					companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_INTERVAL, 10)
			).put(
				LDAPConstants.IMPORT_LOCK_EXPIRATION_TIME,
				_prefsProps.getLong(
					companyId,
					LegacyLDAPPropsKeys.LDAP_IMPORT_LOCK_EXPIRATION_TIME,
					86400000)
			).put(
				LDAPConstants.IMPORT_METHOD,
				_prefsProps.getString(
					companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_METHOD, "user")
			).put(
				LDAPConstants.IMPORT_ON_STARTUP,
				_prefsProps.getBoolean(
					companyId, LegacyLDAPPropsKeys.LDAP_IMPORT_ON_STARTUP,
					false)
			).put(
				LDAPConstants.IMPORT_USER_PASSWORD_AUTOGENERATED,
				_prefsProps.getBoolean(
					companyId,
					LegacyLDAPPropsKeys.LDAP_IMPORT_USER_PASSWORD_AUTOGENERATED,
					false)
			).put(
				LDAPConstants.IMPORT_USER_PASSWORD_DEFAULT,
				_prefsProps.getString(
					companyId,
					LegacyLDAPPropsKeys.LDAP_IMPORT_USER_PASSWORD_DEFAULT,
					"test")
			).put(
				LDAPConstants.IMPORT_USER_PASSWORD_ENABLED,
				_prefsProps.getBoolean(
					companyId,
					LegacyLDAPPropsKeys.LDAP_IMPORT_USER_PASSWORD_ENABLED, true)
			).put(
				LDAPConstants.IMPORT_USER_SYNC_STRATEGY,
				_prefsProps.getString(
					companyId,
					LegacyLDAPPropsKeys.LDAP_IMPORT_USER_SYNC_STRATEGY,
					"auth-type")
			).build();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Adding LDAP import configuration for company ", companyId,
					" with properties: ", dictionary));
		}

		_ldapImportConfigurationProvider.updateProperties(
			companyId, dictionary);
	}

	private void _verifyLDAPProperties() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_companyLocalService.forEachCompanyId(
				companyId -> {
					long[] ldapServerIds = StringUtil.split(
						_prefsProps.getString(companyId, "ldap.server.ids"),
						0L);

					if (ArrayUtil.isEmpty(ldapServerIds)) {
						return;
					}

					_verifyLDAPAuthProperties(companyId);
					_verifyLDAPExportProperties(companyId);
					_verifyLDAPImportProperties(companyId);
					_verifySystemLDAPConfiguration(companyId);

					Set<String> keys = new HashSet<>();

					Collections.addAll(
						keys, LegacyLDAPPropsKeys.LDAP_KEYS_NONPOSTFIXED);

					for (long ldapServerId : ldapServerIds) {
						String postfix = _ldapSettings.getPropertyPostfix(
							ldapServerId);

						_verifyLDAPServerConfiguration(
							companyId, ldapServerId, postfix);

						for (int i = 0;
							 i < LegacyLDAPPropsKeys.LDAP_KEYS_POSTFIXED.length;
							 i++) {

							keys.add(
								LegacyLDAPPropsKeys.LDAP_KEYS_POSTFIXED[i] +
									postfix);
						}
					}

					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Removing preference keys ", keys, " for ",
								"company ", companyId));
					}

					_companyLocalService.removePreferences(
						companyId, keys.toArray(new String[0]));

					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Removing LDAP server IDs ",
								ListUtil.fromArray(ldapServerIds), " for ",
								"company ", companyId));
					}

					_companyLocalService.updatePreferences(
						companyId,
						UnicodePropertiesBuilder.put(
							"ldap.server.ids", StringPool.BLANK
						).build());
				});
		}
	}

	private void _verifyLDAPServerConfiguration(
		long companyId, long ldapServerId, String postfix) {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				LDAPConstants.AUTH_SEARCH_FILTER,
				_prefsProps.getString(
					companyId,
					LegacyLDAPPropsKeys.LDAP_AUTH_SEARCH_FILTER + postfix,
					"(mail=@email_address@)")
			).put(
				LDAPConstants.BASE_DN,
				_prefsProps.getString(
					companyId, LegacyLDAPPropsKeys.LDAP_BASE_DN + postfix,
					"dc=example,dc=com")
			).put(
				LDAPConstants.BASE_PROVIDER_URL,
				_prefsProps.getString(
					companyId,
					LegacyLDAPPropsKeys.LDAP_BASE_PROVIDER_URL + postfix,
					"ldap://localhost:10389")
			).put(
				LDAPConstants.CONTACT_CUSTOM_MAPPINGS,
				_prefsProps.getStringArray(
					companyId,
					LegacyLDAPPropsKeys.LDAP_CONTACT_CUSTOM_MAPPINGS + postfix,
					StringPool.NEW_LINE)
			).put(
				LDAPConstants.CONTACT_MAPPINGS,
				_prefsProps.getStringArray(
					companyId,
					LegacyLDAPPropsKeys.LDAP_CONTACT_MAPPINGS + postfix,
					StringPool.NEW_LINE)
			).put(
				LDAPConstants.GROUP_DEFAULT_OBJECT_CLASSES,
				_prefsProps.getStringArray(
					companyId,
					LegacyLDAPPropsKeys.LDAP_GROUP_DEFAULT_OBJECT_CLASSES +
						postfix,
					StringPool.COMMA)
			).put(
				LDAPConstants.GROUP_MAPPINGS,
				_prefsProps.getStringArray(
					companyId,
					LegacyLDAPPropsKeys.LDAP_GROUP_MAPPINGS + postfix,
					StringPool.NEW_LINE)
			).put(
				LDAPConstants.GROUP_SEARCH_FILTER,
				_prefsProps.getString(
					companyId,
					LegacyLDAPPropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER +
						postfix,
					"(objectClass=groupOfUniqueNames)")
			).put(
				LDAPConstants.GROUP_SEARCH_FILTER_ENABLED,
				_prefsProps.getBoolean(
					companyId,
					LegacyLDAPPropsKeys.
						LDAP_IMPORT_GROUP_SEARCH_FILTER_ENABLED + postfix,
					true)
			).put(
				LDAPConstants.GROUPS_DN,
				_prefsProps.getString(
					companyId, LegacyLDAPPropsKeys.LDAP_GROUPS_DN + postfix,
					"ou=groups,dc=example,dc=com")
			).put(
				LDAPConstants.SECURITY_CREDENTIAL,
				_prefsProps.getString(
					companyId,
					LegacyLDAPPropsKeys.LDAP_SECURITY_CREDENTIALS + postfix,
					"secret")
			).put(
				LDAPConstants.SECURITY_PRINCIPAL,
				_prefsProps.getString(
					companyId,
					LegacyLDAPPropsKeys.LDAP_SECURITY_PRINCIPAL + postfix,
					"uid=admin,ou=system")
			).put(
				LDAPConstants.SERVER_NAME,
				_prefsProps.getString(
					companyId, LegacyLDAPPropsKeys.LDAP_SERVER_NAME + postfix)
			).put(
				LDAPConstants.USER_CUSTOM_MAPPINGS,
				_prefsProps.getStringArray(
					companyId,
					LegacyLDAPPropsKeys.LDAP_USER_CUSTOM_MAPPINGS + postfix,
					StringPool.NEW_LINE)
			).put(
				LDAPConstants.USER_DEFAULT_OBJECT_CLASSES,
				_prefsProps.getStringArray(
					companyId,
					LegacyLDAPPropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES +
						postfix,
					StringPool.COMMA)
			).put(
				LDAPConstants.USER_IGNORE_ATTRIBUTES,
				_prefsProps.getStringArray(
					companyId,
					LegacyLDAPPropsKeys.LDAP_USER_IGNORE_ATTRIBUTES + postfix,
					StringPool.COMMA)
			).put(
				LDAPConstants.USER_MAPPINGS,
				_prefsProps.getStringArray(
					companyId, LegacyLDAPPropsKeys.LDAP_USER_MAPPINGS + postfix,
					StringPool.NEW_LINE)
			).put(
				LDAPConstants.USER_SEARCH_FILTER,
				_prefsProps.getString(
					companyId,
					LegacyLDAPPropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER +
						postfix,
					"(objectClass=inetOrgPerson)")
			).put(
				LDAPConstants.USERS_DN,
				_prefsProps.getString(
					companyId, LegacyLDAPPropsKeys.LDAP_USERS_DN + postfix,
					"users,dc=example,dc=com")
			).build();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Adding LDAP servier configuration for company ", companyId,
					" and LDAP server ID ", ldapServerId, " with properties: ",
					dictionary));
		}

		_ldapServerConfigurationProvider.updateProperties(
			companyId, ldapServerId, dictionary);
	}

	private void _verifySystemLDAPConfiguration(long companyId) {
		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				LDAPConstants.ERROR_PASSWORD_AGE_KEYWORDS,
				new String[] {
					_prefsProps.getString(
						companyId, LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_AGE,
						"age")
				}
			).put(
				LDAPConstants.ERROR_PASSWORD_EXPIRED_KEYWORDS,
				new String[] {
					_prefsProps.getString(
						companyId,
						LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_EXPIRED,
						"expired")
				}
			).put(
				LDAPConstants.ERROR_PASSWORD_HISTORY_KEYWORDS,
				new String[] {
					_prefsProps.getString(
						companyId,
						LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_HISTORY,
						"history")
				}
			).put(
				LDAPConstants.ERROR_PASSWORD_NOT_CHANGEABLE_KEYWORDS,
				new String[] {
					_prefsProps.getString(
						companyId,
						LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_NOT_CHANGEABLE,
						"not allowed to change")
				}
			).put(
				LDAPConstants.ERROR_PASSWORD_SYNTAX_KEYWORDS,
				new String[] {
					_prefsProps.getString(
						companyId,
						LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_SYNTAX,
						"syntax")
				}
			).put(
				LDAPConstants.ERROR_PASSWORD_TRIVIAL_KEYWORDS,
				new String[] {
					_prefsProps.getString(
						companyId,
						LegacyLDAPPropsKeys.LDAP_ERROR_PASSWORD_TRIVIAL,
						"trivial")
				}
			).put(
				LDAPConstants.ERROR_USER_LOCKOUT_KEYWORDS,
				new String[] {
					_prefsProps.getString(
						companyId, LegacyLDAPPropsKeys.LDAP_ERROR_USER_LOCKOUT,
						"retry limit")
				}
			).put(
				LDAPConstants.FACTORY_INITIAL,
				_prefsProps.getString(
					companyId, LegacyLDAPPropsKeys.LDAP_FACTORY_INITIAL,
					"com.sun.jndi.ldap.LdapCtxFactory")
			).put(
				LDAPConstants.PAGE_SIZE,
				_prefsProps.getInteger(
					companyId, LegacyLDAPPropsKeys.LDAP_PAGE_SIZE, 1000)
			).put(
				LDAPConstants.RANGE_SIZE,
				_prefsProps.getInteger(
					companyId, LegacyLDAPPropsKeys.LDAP_RANGE_SIZE, 1000)
			).put(
				LDAPConstants.REFERRAL,
				_prefsProps.getString(
					companyId, LegacyLDAPPropsKeys.LDAP_REFERRAL, "follow")
			).build();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Adding system LDAP configurations for company ", companyId,
					" with properties: ", dictionary));
		}

		_systemLDAPConfigurationProvider.updateProperties(
			companyId, dictionary);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPPropertiesVerifyProcess.class);

	private CompanyLocalService _companyLocalService;
	private ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;
	private ConfigurationProvider<LDAPExportConfiguration>
		_ldapExportConfigurationProvider;
	private ConfigurationProvider<LDAPImportConfiguration>
		_ldapImportConfigurationProvider;
	private ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;
	private LDAPSettings _ldapSettings;
	private PrefsProps _prefsProps;
	private Props _props;
	private ConfigurationProvider<SystemLDAPConfiguration>
		_systemLDAPConfigurationProvider;

}