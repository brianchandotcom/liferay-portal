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

package com.liferay.portal.ldap.internal.authenticator;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PasswordExpiredException;
import com.liferay.portal.UserLockoutException;
import com.liferay.portal.kernel.ldap.LDAPFilterException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.ldap.configuration.SystemLDAPConfiguration;
import com.liferay.portal.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.security.ldap.LDAPSettings;
import com.liferay.portal.security.ldap.LDAPUserImporter;
import com.liferay.portal.security.ldap.PortalLDAP;
import com.liferay.portal.security.pwd.PasswordEncryptor;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.admin.util.Omniadmin;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 * @author Josef Sustacek
 */
@Component(
	immediate = true, property = {"key=auth.pipeline.pre"},
	service = Authenticator.class
)
public class LDAPAuth implements Authenticator {

	public static final String AUTH_METHOD_BIND = "bind";

	public static final String AUTH_METHOD_PASSWORD_COMPARE =
		"password-compare";

	public static final String RESULT_PASSWORD_EXP_WARNING =
		"2.16.840.1.113730.3.4.5";

	public static final String RESULT_PASSWORD_RESET =
		"2.16.840.1.113730.3.4.4";

	@Override
	public int authenticateByEmailAddress(
			long companyId, String emailAddress, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			return authenticate(
				companyId, emailAddress, StringPool.BLANK, 0, password);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new AuthException(e);
		}
	}

	@Override
	public int authenticateByScreenName(
			long companyId, String screenName, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			return authenticate(
				companyId, StringPool.BLANK, screenName, 0, password);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new AuthException(e);
		}
	}

	@Override
	public int authenticateByUserId(
			long companyId, long userId, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			return authenticate(
				companyId, StringPool.BLANK, StringPool.BLANK, userId,
				password);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new AuthException(e);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_authPipelineEnableLiferayCheck = GetterUtil.getBoolean(
			_props.get(PropsKeys.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK));
	}

	protected LDAPAuthResult authenticate(
			LdapContext ctx, long companyId, Attributes attributes,
			String userDN, String password)
		throws Exception {

		LDAPAuthResult ldapAuthResult = null;

		// Check passwords by either doing a comparison between the passwords or
		// by binding to the LDAP server. If using LDAP password policies, bind
		// auth method must be used in order to get the result control codes.

		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(companyId);

		String authMethod = ldapAuthConfiguration.method();

		SystemLDAPConfiguration systemLDAPConfiguration =
			_systemLDAPConfigurationProvider.getConfiguration(companyId);

		if (authMethod.equals(AUTH_METHOD_BIND)) {
			Hashtable<String, Object> env =
				(Hashtable<String, Object>)ctx.getEnvironment();

			env.put(Context.REFERRAL, systemLDAPConfiguration.referral());
			env.put(Context.SECURITY_CREDENTIALS, password);
			env.put(Context.SECURITY_PRINCIPAL, userDN);

			// Do not use pooling because principal changes

			env.put("com.sun.jndi.ldap.connect.pool", "false");

			ldapAuthResult = getFailedLDAPAuthResult(env);

			if (ldapAuthResult != null) {
				return ldapAuthResult;
			}

			ldapAuthResult = new LDAPAuthResult();

			InitialLdapContext initialLdapContext = null;

			try {
				initialLdapContext = new InitialLdapContext(env, null);

				// Get LDAP bind results

				Control[] responseControls =
					initialLdapContext.getResponseControls();

				ldapAuthResult.setAuthenticated(true);
				ldapAuthResult.setResponseControl(responseControls);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Failed to bind to the LDAP server with userDN " +
							userDN + " and password " + password,
						e);
				}

				ldapAuthResult.setAuthenticated(false);
				ldapAuthResult.setErrorMessage(e.getMessage());

				setFailedLDAPAuthResult(env, ldapAuthResult);
			}
			finally {
				if (initialLdapContext != null) {
					initialLdapContext.close();
				}
			}
		}
		else if (authMethod.equals(AUTH_METHOD_PASSWORD_COMPARE)) {
			ldapAuthResult = new LDAPAuthResult();

			Attribute userPassword = attributes.get("userPassword");

			if (userPassword != null) {
				String ldapPassword = new String((byte[])userPassword.get());

				String encryptedPassword = password;

				String algorithm =
					ldapAuthConfiguration.passwordEncryptionAlgorithm();

				if (Validator.isNotNull(algorithm)) {
					encryptedPassword = _passwordEncryptor.encrypt(
						algorithm, password, ldapPassword);
				}

				if (ldapPassword.equals(encryptedPassword)) {
					ldapAuthResult.setAuthenticated(true);
				}
				else {
					ldapAuthResult.setAuthenticated(false);

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Passwords do not match for userDN " + userDN);
					}
				}
			}
		}

		return ldapAuthResult;
	}

	protected int authenticate(
			long ldapServerId, long companyId, String emailAddress,
			String screenName, long userId, String password)
		throws Exception {

		LdapContext ldapContext = _portalLDAP.getContext(
			ldapServerId, companyId);

		if (ldapContext == null) {
			return FAILURE;
		}

		NamingEnumeration<SearchResult> enu = null;

		try {
			LDAPServerConfiguration ldapServerConfiguration =
				_ldapServerConfigurationProvider.getConfiguration(
					companyId, ldapServerId);

			String baseDN = ldapServerConfiguration.baseDN();

			//  Process LDAP auth search filter

			String filter = _ldapSettings.getAuthSearchFilter(
				ldapServerId, companyId, emailAddress, screenName,
				String.valueOf(userId));

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

				SearchResult result = enu.nextElement();

				String fullUserDN = _portalLDAP.getNameInNamespace(
					ldapServerId, companyId, result);

				Attributes attributes = _portalLDAP.getUserAttributes(
					ldapServerId, companyId, ldapContext, fullUserDN);

				// Get user or create from LDAP

				User user = _ldapUserImporter.importUser(
					ldapServerId, companyId, ldapContext, attributes, password);

				// Authenticate

				LDAPAuthResult ldapAuthResult = authenticate(
					ldapContext, companyId, attributes, fullUserDN, password);

				// Process LDAP failure codes

				String errorMessage = ldapAuthResult.getErrorMessage();

				if (errorMessage != null) {
					SystemLDAPConfiguration systemLDAPConfiguration =
						_systemLDAPConfigurationProvider.getConfiguration(
							companyId);

					int pos = errorMessage.indexOf(
						systemLDAPConfiguration.errorUserLockout());

					if (pos != -1) {
						throw new UserLockoutException.LDAPLockout(
							fullUserDN, errorMessage);
					}

					pos = errorMessage.indexOf(
						systemLDAPConfiguration.errorPasswordExpired());

					if (pos != -1) {
						throw new PasswordExpiredException();
					}
				}

				if (!ldapAuthResult.isAuthenticated()) {
					return FAILURE;
				}

				// Process LDAP success codes

				String resultCode = ldapAuthResult.getResponseControl();

				if (resultCode.equals(LDAPAuth.RESULT_PASSWORD_RESET)) {
					_userLocalService.updatePasswordReset(
						user.getUserId(), true);
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter did not return any results");
				}

				return DNE;
			}
		}
		catch (Exception e) {
			if (e instanceof LDAPFilterException ||
				e instanceof PasswordExpiredException ||
				e instanceof UserLockoutException) {

				throw e;
			}

			_log.error("Problem accessing LDAP server", e);

			return FAILURE;
		}
		finally {
			if (enu != null) {
				enu.close();
			}

			if (ldapContext != null) {
				ldapContext.close();
			}
		}

		return SUCCESS;
	}

	protected int authenticate(
			long companyId, String emailAddress, String screenName, long userId,
			String password)
		throws Exception {

		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(companyId);

		if (!ldapAuthConfiguration.enabled()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Authenticator is not enabled");
			}

			return SUCCESS;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Authenticator is enabled");
		}

		int preferredLDAPServerResult = authenticateAgainstPreferredLDAPServer(
			companyId, emailAddress, screenName, userId, password);

		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		if (preferredLDAPServerResult == SUCCESS) {
			if (ldapImportConfiguration.importUserPasswordEnabled()) {
				return preferredLDAPServerResult;
			}

			return Authenticator.SKIP_LIFERAY_CHECK;
		}

		List<LDAPServerConfiguration> ldapServerConfigurations =
			_ldapServerConfigurationProvider.getConfigurations(companyId);

		for (LDAPServerConfiguration ldapServerConfiguration :
				ldapServerConfigurations) {

			int result = authenticate(
				ldapServerConfiguration.ldapServerId(), companyId, emailAddress,
				screenName, userId, password);

			if (result == SUCCESS) {
				if (ldapImportConfiguration.importUserPasswordEnabled()) {
					return result;
				}

				return Authenticator.SKIP_LIFERAY_CHECK;
			}
		}

		return authenticateRequired(
			companyId, userId, emailAddress, screenName, true, FAILURE);
	}

	protected int authenticateAgainstPreferredLDAPServer(
			long companyId, String emailAddress, String screenName, long userId,
			String password)
		throws Exception {

		int result = DNE;

		User user = null;

		try {
			if (userId > 0) {
				user = _userLocalService.getUserById(companyId, userId);
			}
			else if (Validator.isNotNull(emailAddress)) {
				user = _userLocalService.getUserByEmailAddress(
					companyId, emailAddress);
			}
			else if (Validator.isNotNull(screenName)) {
				user = _userLocalService.getUserByScreenName(
					companyId, screenName);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to get preferred LDAP server");
				}

				return result;
			}
		}
		catch (NoSuchUserException nsue) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get preferred LDAP server", nsue);
			}

			return result;
		}

		long ldapServerId = user.getLdapServerId();

		if (ldapServerId < 0) {
			return result;
		}

		LDAPServerConfiguration ldapServerConfiguration =
			_ldapServerConfigurationProvider.getConfiguration(
				companyId, ldapServerId);

		String providerUrl = ldapServerConfiguration.baseProviderURL();

		if (Validator.isNull(providerUrl)) {
			return result;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Using LDAP server ID " + ldapServerId +
					" to authenticate user " + user.getUserId());
		}

		result = authenticate(
			ldapServerId, companyId, emailAddress, screenName, userId,
			password);

		return result;
	}

	protected int authenticateOmniadmin(
			long companyId, String emailAddress, String screenName, long userId)
		throws Exception {

		// Only allow omniadmin if Liferay password checking is enabled

		if (!_authPipelineEnableLiferayCheck) {
			return FAILURE;
		}

		if (userId > 0) {
			if (_omniadmin.isOmniadmin(userId)) {
				return SUCCESS;
			}
		}
		else if (Validator.isNotNull(emailAddress)) {
			User user = _userLocalService.fetchUserByEmailAddress(
				companyId, emailAddress);

			if (user != null) {
				if (_omniadmin.isOmniadmin(user)) {
					return SUCCESS;
				}
			}
		}
		else if (Validator.isNotNull(screenName)) {
			User user = _userLocalService.fetchUserByScreenName(
				companyId, screenName);

			if (user != null) {
				if (_omniadmin.isOmniadmin(user)) {
					return SUCCESS;
				}
			}
		}

		return FAILURE;
	}

	protected int authenticateRequired(
			long companyId, long userId, String emailAddress, String screenName,
			boolean allowOmniadmin, int failureCode)
		throws Exception {

		// Make exceptions for omniadmins so that if they break the LDAP
		// configuration, they can still login to fix the problem

		if (allowOmniadmin &&
			(authenticateOmniadmin(
				companyId, emailAddress, screenName, userId) == SUCCESS)) {

			return SUCCESS;
		}

		LDAPAuthConfiguration ldapAuthConfiguration =
			_ldapAuthConfigurationProvider.getConfiguration(companyId);

		if (ldapAuthConfiguration.required()) {
			return failureCode;
		}
		else {
			return SUCCESS;
		}
	}

	protected LDAPAuthResult getFailedLDAPAuthResult(Map<String, Object> env) {
		Map<String, LDAPAuthResult> failedLDAPAuthResults =
			_failedLDAPAuthResults.get();

		String cacheKey = getKey(env);

		return failedLDAPAuthResults.get(cacheKey);
	}

	protected String getKey(Map<String, Object> env) {
		StringBundler sb = new StringBundler(5);

		sb.append(MapUtil.getString(env, Context.PROVIDER_URL));
		sb.append(StringPool.POUND);
		sb.append(MapUtil.getString(env, Context.SECURITY_PRINCIPAL));
		sb.append(StringPool.POUND);
		sb.append(MapUtil.getString(env, Context.SECURITY_CREDENTIALS));

		return sb.toString();
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.ldap.authenticator.configuration.LDAPAuthConfiguration)",
		unbind = "-"
	)
	protected void setConfigurationProvider(
		ConfigurationProvider<LDAPAuthConfiguration>
			ldapAuthConfigurationProvider) {

		_ldapAuthConfigurationProvider = ldapAuthConfigurationProvider;
	}

	protected void setFailedLDAPAuthResult(
		Map<String, Object> env, LDAPAuthResult ldapAuthResult) {

		Map<String, LDAPAuthResult> failedLDAPAuthResults =
			_failedLDAPAuthResults.get();

		String cacheKey = getKey(env);

		if (failedLDAPAuthResults.containsKey(cacheKey)) {
			return;
		}

		failedLDAPAuthResults.put(cacheKey, ldapAuthResult);
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
	protected void setLdapUserImporter(LDAPUserImporter ldapUserImporter) {
		_ldapUserImporter = ldapUserImporter;
	}

	@Reference(unbind = "-")
	protected void setOmniadmin(Omniadmin omniadmin) {
		_omniadmin = omniadmin;
	}

	@Reference(unbind = "-")
	protected void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
		_passwordEncryptor = passwordEncryptor;
	}

	@Reference(unbind = "-")
	protected void setPortalLDAP(PortalLDAP portalLDAP) {
		_portalLDAP = portalLDAP;
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

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(LDAPAuth.class);

	private boolean _authPipelineEnableLiferayCheck;
	private final ThreadLocal<Map<String, LDAPAuthResult>>
		_failedLDAPAuthResults =
			new AutoResetThreadLocal<Map<String, LDAPAuthResult>>(
				LDAPAuth.class + "._failedLDAPAuthResultCache",
				new HashMap<String, LDAPAuthResult>());
	private volatile ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;
	private volatile ConfigurationProvider<LDAPImportConfiguration>
		_ldapImportConfigurationProvider;
	private volatile ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;
	private volatile LDAPSettings _ldapSettings;
	private volatile LDAPUserImporter _ldapUserImporter;
	private volatile Omniadmin _omniadmin;
	private volatile PasswordEncryptor _passwordEncryptor;
	private volatile PortalLDAP _portalLDAP;
	private volatile Props _props;
	private volatile ConfigurationProvider<SystemLDAPConfiguration>
		_systemLDAPConfigurationProvider;
	private volatile UserLocalService _userLocalService;

}