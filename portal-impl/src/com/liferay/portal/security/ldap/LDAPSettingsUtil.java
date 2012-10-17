/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class LDAPSettingsUtil {

	public static boolean validateLDAPFilter(String filter) {

		/* Rules here are based upon:
		 * [1] http://www.ietf.org/rfc/rfc2251.txt
		 * [2] http://www.ietf.org/rfc/rfc2252.txt
		 * [3] http://www.ietf.org/rfc/rfc2253.txt
		 * [4] http://www.ietf.org/rfc/rfc2254.txt
		 * [5] http://www.ietf.org/rfc/rfc4515.txt
		 */
		String s = null;
		boolean retVal = true;
		if (filter != null) {
			s = new String(filter);
			s = s.trim();

			/* There are two special values which
		     * may be used: an empty list with no attributes, and the attribute
		     * description string "*".  Both of these signify that all user
		     * attributes are to be returned.
		     */
			if (s.equals("") || s.equals("*")) {
				s = null;
			}
			else {
				//Whitespace is removed
				s = s.replaceAll("\\s+", "");
				s = s.trim();
			}
		}

		// Must have an opening and closing parenthesis

		if (retVal == true) {
			if (s != null) {
				if ( !(s.startsWith("(")) || ( !(s.endsWith(")")))) {
					retVal = false;
				}
			}
		}

		// Balance left and right parenthesis

		if (retVal == true) {
			if (s != null) {
				int i = 0;
				for (int j = 0; j < s.length(); j++) {
					if (s.charAt(j) == '(') {
						i++;
					}
					else if (s.charAt(j) == ')') {
						i--;
					}

					if (i < 0) {
						retVal = false;
					}
				}

				if (i != 0) {
					retVal = false;
				}
			}
		}

		// Cannot have two "filtertypes" in sequence

		if (retVal == true) {
			if (s != null) {
				boolean b = Pattern.matches(".*[~<>]*=[~<>]*=.*", s);
				retVal = !b;
			}
		}

		// Cannot have a "filtertype" after an opening parenthesis

		if (retVal == true) {
			if (s != null) {
				boolean b = Pattern.matches("\\([~<>]*=.*", s);
				retVal = !b;
			}
		}

		// Cannot have a "attribute" without a "filtertype" or "extensible"

		//<item> ::= <simple> | <present> | <substring>
		//<simple> ::= <attr> <filtertype> <value>
		//<present> ::= <attr> '=*'
		//<substring> ::= <attr> '=' <initial> <any> <final>

		if (retVal == true) {
			if (s != null) {
				boolean b = Pattern.matches("\\([^~<>=]*\\)", s);
				retVal = !b;
			}
		}

		if (retVal == true) {
			if (s != null) {
				boolean b = Pattern.matches(".*[^~<>=]*[~<>]*=\\)", s);
				retVal = !b;
			}
		}

		return retVal;
	}

	public static String getAuthSearchFilter(
			long ldapServerId, long companyId, String emailAddress,
			String screenName, String userId)
		throws SystemException {

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

		if (false == validateLDAPFilter(filter)) {
			throw new SystemException("Invalid LDAP AuthSearch Filter Syntax");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Search filter after transformation " + filter);
		}

		return filter;
	}

	public static Properties getContactExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Properties contactExpandoMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_CONTACT_CUSTOM_MAPPINGS + postfix));

		LogUtil.debug(_log, contactExpandoMappings);

		return contactExpandoMappings;
	}

	public static Properties getContactMappings(
			long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Properties contactMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_CONTACT_MAPPINGS + postfix));

		LogUtil.debug(_log, contactMappings);

		return contactMappings;
	}

	public static Properties getGroupMappings(long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Properties groupMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_GROUP_MAPPINGS + postfix));

		LogUtil.debug(_log, groupMappings);

		return groupMappings;
	}

	public static String getPropertyPostfix(long ldapServerId) {
		return StringPool.PERIOD + ldapServerId;
	}

	public static Properties getUserExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Properties userExpandoMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_USER_CUSTOM_MAPPINGS + postfix));

		LogUtil.debug(_log, userExpandoMappings);

		return userExpandoMappings;
	}

	public static Properties getUserMappings(long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Properties userMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_USER_MAPPINGS + postfix));

		LogUtil.debug(_log, userMappings);

		return userMappings;
	}

	public static boolean isExportEnabled(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_EXPORT_ENABLED,
				PropsValues.LDAP_EXPORT_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isExportGroupEnabled(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_EXPORT_GROUP_ENABLED,
				PropsValues.LDAP_EXPORT_GROUP_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isImportEnabled(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_IMPORT_ENABLED,
				PropsValues.LDAP_IMPORT_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isImportOnStartup(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_IMPORT_ON_STARTUP)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isPasswordPolicyEnabled(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_PASSWORD_POLICY_ENABLED,
				PropsValues.LDAP_PASSWORD_POLICY_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LDAPSettingsUtil.class);

}