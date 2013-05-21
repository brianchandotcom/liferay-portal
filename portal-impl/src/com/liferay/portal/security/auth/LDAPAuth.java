/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PasswordExpiredException;
import com.liferay.portal.UserLockoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.security.ldap.PortalLDAPImporterUtil;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.admin.util.OmniadminUtil;

import java.util.Hashtable;
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

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 * @author Josef Sustacek
 */
public class LDAPAuth implements Authenticator {

	public static final String AUTH_METHOD_BIND = "bind";

	public static final String AUTH_METHOD_PASSWORD_COMPARE =
		"password-compare";

	public static final String RESULT_PASSWORD_EXP_WARNING =
		"2.16.840.1.113730.3.4.5";

	public static final String RESULT_PASSWORD_RESET =
		"2.16.840.1.113730.3.4.4";

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

	protected LDAPAuthResult authenticate(
			LdapContext ctx, long companyId, Attributes attributes,
			String userDN, String password)
		throws Exception {

		LDAPAuthResult ldapAuthResult = new LDAPAuthResult();

		// Check passwords by either doing a comparison between the passwords or
		// by binding to the LDAP server. If using LDAP password policies, bind
		// auth method must be used in order to get the result control codes.

		String authMethod = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_AUTH_METHOD);
		InitialLdapContext innerCtx = null;

		if (authMethod.equals(AUTH_METHOD_BIND)) {
			try {
				Hashtable<String, Object> env =
					(Hashtable<String, Object>)ctx.getEnvironment();

				env.put(Context.SECURITY_PRINCIPAL, userDN);
				env.put(Context.SECURITY_CREDENTIALS, password);
				env.put(
					Context.REFERRAL,
					PrefsPropsUtil.getString(
						companyId, PropsKeys.LDAP_REFERRAL));

				// Do not use pooling because principal changes

				env.put("com.sun.jndi.ldap.connect.pool", "false");

				innerCtx = new InitialLdapContext(env, null);

				// Get LDAP bind results

				Control[] responseControls = innerCtx.getResponseControls();

				ldapAuthResult.setAuthenticated(true);
				ldapAuthResult.setResponseControl(responseControls);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Failed to bind to the LDAP server with userDN " +
							userDN + " and password " + password);
				}

				_log.error("Failed to bind to the LDAP server", e);

				ldapAuthResult.setAuthenticated(false);
				ldapAuthResult.setErrorMessage(e.getMessage());
			}
			finally {
				if (innerCtx != null) {
					innerCtx.close();
				}
			}
		}
		else if (authMethod.equals(AUTH_METHOD_PASSWORD_COMPARE)) {
			Attribute userPassword = attributes.get("userPassword");

			if (userPassword != null) {
				String ldapPassword = new String((byte[])userPassword.get());

				String encryptedPassword = password;

				String algorithm = PrefsPropsUtil.getString(
					companyId,
					PropsKeys.LDAP_AUTH_PASSWORD_ENCRYPTION_ALGORITHM);

				if (Validator.isNotNull(algorithm)) {
					StringBundler sb = new StringBundler(4);

					sb.append(StringPool.OPEN_CURLY_BRACE);
					sb.append(algorithm);
					sb.append(StringPool.CLOSE_CURLY_BRACE);
					sb.append(
						PasswordEncryptorUtil.encrypt(
							algorithm, password, ldapPassword));

					encryptedPassword = sb.toString();
				}

				if (ldapPassword.equals(encryptedPassword)) {
					ldapAuthResult.setAuthenticated(true);
				}
				else {
					ldapAuthResult.setAuthenticated(false);

					if (_log.isWarnEnabled()) {
						_log.warn(
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

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		LdapContext ldapContext = PortalLDAPUtil.getContext(
			ldapServerId, companyId);

		if (ldapContext == null) {
			return FAILURE;
		}

		NamingEnumeration<SearchResult> enu = null;

		try {
			String baseDN = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_DN + postfix);

			//  Process LDAP auth search filter

			String filter = LDAPSettingsUtil.getAuthSearchFilter(
				ldapServerId, companyId, emailAddress, screenName,
				String.valueOf(userId));

			Properties userMappings = LDAPSettingsUtil.getUserMappings(
				ldapServerId, companyId);

			String userMappingsScreenName = GetterUtil.getString(
				userMappings.getProperty("screenName")).toLowerCase();

			SearchControls searchControls = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0,
				new String[] {userMappingsScreenName}, false, false);

			enu = ldapContext.search(baseDN, filter, searchControls);

			if (enu.hasMoreElements()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter returned at least one result");
				}

				SearchResult result = enu.nextElement();

				String fullUserDN = PortalLDAPUtil.getNameInNamespace(
					ldapServerId, companyId, result);

				Attributes attributes = PortalLDAPUtil.getUserAttributes(
					ldapServerId, companyId, ldapContext, fullUserDN);

				LDAPAuthResult ldapAuthResult = authenticate(
					ldapContext, companyId, attributes, fullUserDN, password);

				// Process LDAP failure codes

				String errorMessage = ldapAuthResult.getErrorMessage();

				if (errorMessage != null) {
					int pos = errorMessage.indexOf(
						PrefsPropsUtil.getString(
							companyId, PropsKeys.LDAP_ERROR_USER_LOCKOUT));

					if (pos != -1) {
						throw new UserLockoutException();
					}

					pos = errorMessage.indexOf(
						PrefsPropsUtil.getString(
							companyId, PropsKeys.LDAP_ERROR_PASSWORD_EXPIRED));

					if (pos != -1) {
						throw new PasswordExpiredException();
					}
				}

				if (!ldapAuthResult.isAuthenticated()) {
					return FAILURE;
				}

				// Get user or create from LDAP

				User user = PortalLDAPImporterUtil.importLDAPUser(
					ldapServerId, companyId, ldapContext, attributes, password);

				// Process LDAP success codes

				String resultCode = ldapAuthResult.getResponseControl();

				if (resultCode.equals(LDAPAuth.RESULT_PASSWORD_RESET)) {
					UserLocalServiceUtil.updatePasswordReset(
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

		if (!AuthSettingsUtil.isLDAPAuthEnabled(companyId)) {
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

		if (preferredLDAPServerResult == SUCCESS) {
			if (PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.LDAP_IMPORT_USER_PASSWORD_ENABLED)) {

				return preferredLDAPServerResult;
			}

			return Authenticator.SKIP_LIFERAY_CHECK;
		}

		long[] ldapServerIds = StringUtil.split(
			PrefsPropsUtil.getString(companyId, "ldap.server.ids"), 0L);

		for (long ldapServerId : ldapServerIds) {
			int result = authenticate(
				ldapServerId, companyId, emailAddress, screenName, userId,
				password);

			if (result == SUCCESS) {
				if (PrefsPropsUtil.getBoolean(
						companyId,
						PropsKeys.LDAP_IMPORT_USER_PASSWORD_ENABLED)) {

					return result;
				}

				return Authenticator.SKIP_LIFERAY_CHECK;
			}
		}

		for (int ldapServerId = 0;; ldapServerId++) {
			String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

			String providerUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_PROVIDER_URL + postfix);

			if (Validator.isNull(providerUrl)) {
				break;
			}

			int result = authenticate(
				ldapServerId, companyId, emailAddress, screenName, userId,
				password);

			if (result == SUCCESS) {
				if (PrefsPropsUtil.getBoolean(
						companyId,
						PropsKeys.LDAP_IMPORT_USER_PASSWORD_ENABLED)) {

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
				user = UserLocalServiceUtil.getUserById(companyId, userId);
			}
			else if (Validator.isNotNull(emailAddress)) {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					companyId, emailAddress);
			}
			else if (Validator.isNotNull(screenName)) {
				user = UserLocalServiceUtil.getUserByScreenName(
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

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String providerUrl = PrefsPropsUtil.getString(
			user.getCompanyId(), PropsKeys.LDAP_BASE_PROVIDER_URL + postfix);

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

		if (!PropsValues.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK) {
			return FAILURE;
		}

		if (userId > 0) {
			if (OmniadminUtil.isOmniadmin(userId)) {
				return SUCCESS;
			}
		}
		else if (Validator.isNotNull(emailAddress)) {
			User user = UserLocalServiceUtil.fetchUserByEmailAddress(
				companyId, emailAddress);

			if (user != null) {
				if (OmniadminUtil.isOmniadmin(user)) {
					return SUCCESS;
				}
			}
		}
		else if (Validator.isNotNull(screenName)) {
			User user = UserLocalServiceUtil.fetchUserByScreenName(
				companyId, screenName);

			if (user != null) {
				if (OmniadminUtil.isOmniadmin(user)) {
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

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_AUTH_REQUIRED)) {

			return failureCode;
		}
		else {
			return SUCCESS;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LDAPAuth.class);

}