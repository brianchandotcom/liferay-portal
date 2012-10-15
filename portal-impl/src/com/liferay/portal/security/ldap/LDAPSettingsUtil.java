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

import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class LDAPSettingsUtil {

	public static boolean validateLDAPFilter(
			String filter, boolean throwException)
		throws SystemException {
		
		boolean retVal = validateLDAPFilter(filter);
		if ((retVal == false) && (throwException == true)) {
			throw new SystemException();
		}
		return retVal;
	}

	public static boolean validateLDAPFilter(String filter) {

		String s = null;
		boolean retVal = true;
		if (filter != null) {
			s = new String(filter);
			s = s.trim();
			if (s.equals("") || s.equals("*")) {
				s = null;  
			}
			else {
				//insert spaces for the tokenizer
				s = s.replaceAll("\\(", " \\( ");
				s = s.replaceAll("\\)", " \\) ");
				s = s.replaceAll("~=", " ~= ");
				s = s.replaceAll("<=", " <= ");
				s = s.replaceAll(">=", " >= ");
				s = s.replaceAll("=", "= ");
				ArrayList<Integer> items = new ArrayList<Integer>();
				for (int j = 0; j < s.length(); j++) {
					if ((s.charAt(j) == '=') && (j>0)) {

						if (!(s.charAt(j-1) == '~') &&
							!(s.charAt(j-1) == '<') &&
							!(s.charAt(j-1) == '>')) {

							items.add(new Integer(j));
						}
					}
				}

				if (items.size() > 0) { 
					int offset = 0;
					for (int j = 0; j < items.size(); j++) {
						s = s.substring(0,(Integer)(items.get(j)) + offset) + " "
							+ s.substring((Integer)(items.get(j)) + offset);
						offset++;
					}
				}

				//multiple whitespace is eliminated and replaced with a single whitespace
				s = s.replaceAll("\\s+", " ");
				s = s.trim();
			}
		}

		// Must have an opening and closing parenthesis with nothing outside of them
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

		// Cannot have two or more "filtertype" fields in sequence
		String[] filtertype =  {"=", "~=", "<=", ">="};

		if (retVal == true) {
			if (s != null) {
				int i = 0;
				StringTokenizer st = new StringTokenizer(s);
				while (st.hasMoreTokens()) {
					String t = st.nextToken();
					if ((t.equals(filtertype[0])) ||
						(t.equals(filtertype[1])) ||
						(t.equals(filtertype[2])) ||
						(t.equals(filtertype[3]))) {

						i++;
					}
					else {
						i = 0;
					}

					if (i > 1) {
						retVal = false;
					}
				}
			}
		}

		// Cannot have a "filtertype" after an opening parenthesis

		if (retVal == true) {
			if (s != null) {
				int i = 0;
				StringTokenizer st = new StringTokenizer(s);
				while (st.hasMoreTokens()) {
					String t = st.nextToken();
					if (t.equals("(")) {
						i++;
					}
					else {

						if (i > 0) {
							if ((t.equals(filtertype[0])) ||
								(t.equals(filtertype[1])) ||
								(t.equals(filtertype[2])) ||
								(t.equals(filtertype[3]))) {

								retVal = false;
							}
						}
						i = 0;
					}
				}
			}
		}

		// Cannot have a "attribute" without a "filtertype" or "extensible"
		//<item> ::= <simple> | <present> | <substring>
		//<simple> ::= <attr> <filtertype> <value> 
		//<present> ::= <attr> '=*'
		//<substring> ::= <attr> '=' <initial> <any> <final>
	
		if (retVal == true) {
			if (s != null) {
				int j = 0;
				int k = 0;
				StringTokenizer st = new StringTokenizer(s);
				while (st.hasMoreTokens()) {
					String t = st.nextToken();
					if (t.equals("(")) {
						j = 0;
						k = 0;
					}
					else if (t.equals(")")) {
						if ((k == 0) && (j == 0)) {
							retVal = false;
						}

						k++;
					}
					else if ((t.equals(filtertype[0])) ||
						(t.equals(filtertype[1])) ||
						(t.equals(filtertype[2])) ||
						(t.equals(filtertype[3]))) {
							j++;
					}
					else {
						if (j == 0) {

							if ((t.toUpperCase().equals("NULL")) ||
								(t.toUpperCase().equals("NUL"))) {

								retVal = false;
							}
						}
					}
				}
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

		validateLDAPFilter(filter, true);

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