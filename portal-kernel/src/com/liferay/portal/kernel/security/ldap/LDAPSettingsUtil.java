/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.ldap;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Properties;

/**
 * @author Edward Han
 */
public class LDAPSettingsUtil {

	public static Properties getContactExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception {

		return _ldapSettings.getContactExpandoMappings(ldapServerId, companyId);
	}

	public static Properties getContactMappings(
			long ldapServerId, long companyId)
		throws Exception {

		return _ldapSettings.getContactMappings(ldapServerId, companyId);
	}

	public static String[] getErrorPasswordHistoryKeywords(long companyId) {
		return _ldapSettings.getErrorPasswordHistoryKeywords(companyId);
	}

	public static Properties getGroupMappings(long ldapServerId, long companyId)
		throws Exception {

		return _ldapSettings.getGroupMappings(ldapServerId, companyId);
	}

	public static long getPreferredLDAPServerId(
		long companyId, String screenName) {

		return _ldapSettings.getPreferredLDAPServerId(companyId, screenName);
	}

	public static String getPropertyPostfix(long ldapServerId) {
		return _ldapSettings.getPropertyPostfix(ldapServerId);
	}

	public static Properties getUserExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception {

		return _ldapSettings.getUserExpandoMappings(ldapServerId, companyId);
	}

	public static Properties getUserMappings(long ldapServerId, long companyId)
		throws Exception {

		return _ldapSettings.getUserMappings(ldapServerId, companyId);
	}

	public static boolean isExportEnabled(long companyId) {
		return _ldapSettings.isExportEnabled(companyId);
	}

	public static boolean isExportGroupEnabled(long companyId) {
		return _ldapSettings.isExportGroupEnabled(companyId);
	}

	public static boolean isImportEnabled(long companyId) {
		return _ldapSettings.isImportEnabled(companyId);
	}

	public static boolean isImportOnStartup(long companyId) {
		return _ldapSettings.isImportOnStartup(companyId);
	}

	public static boolean isPasswordPolicyEnabled(long companyId) {
		return _ldapSettings.isPasswordPolicyEnabled(companyId);
	}

	private static volatile LDAPSettings _ldapSettings =
		ServiceProxyFactory.newServiceTrackedInstance(
			LDAPSettings.class, LDAPSettingsUtil.class, "_ldapSettings", false);

}