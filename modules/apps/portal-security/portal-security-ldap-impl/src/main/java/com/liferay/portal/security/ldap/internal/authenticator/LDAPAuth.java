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

package com.liferay.portal.security.ldap.internal.authenticator;

import com.liferay.admin.kernel.util.Omniadmin;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PasswordExpiredException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserLockoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.PortalLDAP;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration;
import com.liferay.portal.security.ldap.constants.LDAPConstants;
import com.liferay.portal.security.ldap.exportimport.LDAPUserImporter;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.security.ldap.util.LDAPUtil;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.AuthenticationException;
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
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 * @author Josef Sustacek
 */
@Component(
	immediate = true, property = "key=auth.pipeline.pre",
	service = Authenticator.class
)
public class LDAPAuth implements Authenticator {

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

		if (authMethod.equals(LDAPConstants.AUTH_METHOD_BIND)) {
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
				boolean authenticationException = false;

				if (e instanceof AuthenticationException) {
					authenticationException = true;
				}

				if (_log.isDebugEnabled()) {
					if (authenticationException) {
						_log.debug(
							StringBundler.concat(
								"Failed to bind to the LDAP server, wrong ",
								"password provided for userDN ", userDN),
							e);
					}
					else {
						_log.debug(
							"Failed to bind to the LDAP server with userDN " +
								userDN,
							e);
					}
				}
				else if (_log.isWarnEnabled() && !authenticationException) {
					_log.warn(
						StringBundler.concat(
							"Failed to bind to the LDAP server with userDN ",
							userDN, " :", e.getMessage()));
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
		else if (authMethod.equals(
					 LDAPConstants.AUTH_METHOD_PASSWORD_COMPARE)) {

			ldapAuthResult = new LDAPAuthResult();

			Attribute userPassword = attributes.get("userPassword");

			if (userPassword != null) {
				String encryptedPassword = password;
				String ldapPassword = new String((byte[])userPassword.get());

				if (Validator.isNotNull(
						ldapAuthConfiguration.passwordEncryptionAlgorithm())) {

					ldapPassword = removeEncryptionAlgorithm(ldapPassword);

					encryptedPassword = _passwordEncryptor.encrypt(
						ldapAuthConfiguration.passwordEncryptionAlgorithm(),
						password, ldapPassword);
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
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"No LDAP server configuration available for LDAP ",
						"server ", ldapServerId, " and company ", companyId));
			}

			return FAILURE;
		}

		NamingEnumeration<SearchResult> enu = null;

		try {
			LDAPServerConfiguration ldapServerConfiguration =
				_ldapServerConfigurationProvider.getConfiguration(
					companyId, ldapServerId);

			String baseDN = LDAPUtil.escapeCharacters(
				ldapServerConfiguration.baseDN());

			//  Process LDAP auth search filter

			String filter = _ldapSettings.getAuthSearchFilter(
				ldapServerId, companyId,
				_portalLDAP.encodeFilterAttribute(emailAddress, false),
				_portalLDAP.encodeFilterAttribute(screenName, false),
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

			if (!enu.hasMoreElements()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"No results found with search filter: " + filter);
				}

				return DNE;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Found results with search filter: " + filter);
			}

			SearchResult searchResult = enu.nextElement();

			String fullUserDN = searchResult.getNameInNamespace();

			Attributes attributes = _portalLDAP.getUserAttributes(
				ldapServerId, companyId, ldapContext, fullUserDN);

			// Authenticate

			LDAPAuthResult ldapAuthResult = authenticate(
				ldapContext, companyId, attributes, fullUserDN, password);

			// Get user or create from LDAP

			if (!ldapAuthResult.isAuthenticated()) {
				password = null;
			}

			User user = _ldapUserImporter.importUser(
				ldapServerId, companyId, ldapContext, attributes, password);

			// Process LDAP failure codes

			String errorMessage = ldapAuthResult.getErrorMessage();

			if (errorMessage != null) {
				SystemLDAPConfiguration systemLDAPConfiguration =
					_systemLDAPConfigurationProvider.getConfiguration(
						companyId);

				for (String errorUserLockoutKeyword :
						systemLDAPConfiguration.errorUserLockoutKeywords()) {

					if (errorMessage.contains(errorUserLockoutKeyword)) {
						throw new UserLockoutException.LDAPLockout(
							fullUserDN, errorMessage);
					}
				}

				for (String errorPasswordExpiredKeyword :
						systemLDAPConfiguration.
							errorPasswordExpiredKeywords()) {

					if (errorMessage.contains(errorPasswordExpiredKeyword)) {
						throw new PasswordExpiredException();
					}
				}
			}

			if (!ldapAuthResult.isAuthenticated()) {
				if (_log.isDebugEnabled()) {
					StringBundler sb = new StringBundler(10);

					sb.append("Uanble to authenticate with ");
					sb.append(fullUserDN);
					sb.append(" on LDAP server ");
					sb.append(ldapServerId);
					sb.append(", company ");
					sb.append(companyId);
					sb.append(", and LDAP context ");
					sb.append(ldapContext);
					sb.append(": ");
					sb.append(errorMessage);

					_log.debug(sb.toString());
				}

				return FAILURE;
			}

			// Process LDAP success codes

			String resultCode = ldapAuthResult.getResponseControl();

			if (resultCode.equals(LDAPAuth.RESULT_PASSWORD_RESET)) {
				_userLocalService.updatePasswordReset(user.getUserId(), true);
			}
		}
		catch (Exception e) {
			if (e instanceof PasswordExpiredException ||
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

			ldapContext.close();
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

		long preferredLDAPServerId = getPreferredLDAPServer(
			companyId, emailAddress, screenName, userId);

		int preferredLDAPServerResult = authenticateAgainstPreferredLDAPServer(
			companyId, preferredLDAPServerId, emailAddress, screenName, userId,
			password);

		LDAPImportConfiguration ldapImportConfiguration =
			_ldapImportConfigurationProvider.getConfiguration(companyId);

		if (preferredLDAPServerResult == SUCCESS) {
			if (_log.isDebugEnabled()) {
				_log.debug("Found preferred LDAP server");
			}

			if (ldapImportConfiguration.importUserPasswordEnabled()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Import user password enabled");
				}

				return preferredLDAPServerResult;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Import user password disabled");
			}

			return Authenticator.SKIP_LIFERAY_CHECK;
		}

		List<LDAPServerConfiguration> ldapServerConfigurations =
			_ldapServerConfigurationProvider.getConfigurations(companyId);

		for (LDAPServerConfiguration ldapServerConfiguration :
				ldapServerConfigurations) {

			if (preferredLDAPServerId ==
					ldapServerConfiguration.ldapServerId()) {

				if (_log.isDebugEnabled()) {
					_log.debug("Bypassing preferred LDAP server");
				}

				continue;
			}

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
			long companyId, long ldapServerId, String emailAddress,
			String screenName, long userId, String password)
		throws Exception {

		int result = DNE;

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

		return SUCCESS;
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

	protected long getPreferredLDAPServer(
			long companyId, String emailAddress, String screenName, long userId)
		throws PortalException {

		User user = null;

		if (userId > 0) {
			user = _userLocalService.fetchUserById(userId);
		}
		else if (Validator.isNotNull(emailAddress)) {
			user = _userLocalService.fetchUserByEmailAddress(
				companyId, emailAddress);
		}
		else if (Validator.isNotNull(screenName)) {
			user = _userLocalService.fetchUserByScreenName(
				companyId, screenName);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get preferred LDAP server");
			}

			return -1;
		}

		if (user == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get user " + userId);
			}

			return -1;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Using LDAP server ", user.getLdapServerId(),
					" to authenticate user ", userId));
		}

		return user.getLdapServerId();
	}

	protected String removeEncryptionAlgorithm(String ldapPassword) {
		if (_log.isDebugEnabled()) {
			_log.debug("Removing encryption algorithm");
		}

		int x = ldapPassword.indexOf(StringPool.OPEN_CURLY_BRACE);

		if (x == -1) {
			return ldapPassword;
		}

		int y = ldapPassword.indexOf(StringPool.CLOSE_CURLY_BRACE, x);

		if (y == -1) {
			return ldapPassword;
		}

		return ldapPassword.substring(y + 1);
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration)",
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
	protected void setOmniadmin(Omniadmin omniadmin) {
		_omniadmin = omniadmin;
	}

	@Reference(unbind = "-")
	protected void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
		_passwordEncryptor = passwordEncryptor;
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

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(LDAPAuth.class);

	private boolean _authPipelineEnableLiferayCheck;
	private final ThreadLocal<Map<String, LDAPAuthResult>>
		_failedLDAPAuthResults = new CentralizedThreadLocal<>(
			LDAPAuth.class + "._failedLDAPAuthResultCache", HashMap::new);
	private ConfigurationProvider<LDAPAuthConfiguration>
		_ldapAuthConfigurationProvider;
	private ConfigurationProvider<LDAPImportConfiguration>
		_ldapImportConfigurationProvider;
	private ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;
	private LDAPSettings _ldapSettings;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile LDAPUserImporter _ldapUserImporter;

	private Omniadmin _omniadmin;
	private PasswordEncryptor _passwordEncryptor;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile PortalLDAP _portalLDAP;

	private Props _props;
	private ConfigurationProvider<SystemLDAPConfiguration>
		_systemLDAPConfigurationProvider;
	private UserLocalService _userLocalService;

}