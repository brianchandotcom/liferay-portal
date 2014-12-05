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

package com.liferay.portal.security.ldap;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Properties;

/**
 * @author Edward Han
 */
public class LDAPSettingsUtil {

	public static String getAuthSearchFilter(
			long ldapServerId, long companyId, String emailAddress,
			String screenName, String userId)
		throws Exception {

		return _getInstance().getAuthSearchFilter(
			ldapServerId, companyId, emailAddress, screenName, userId);
	}

	public static Properties getContactExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception {

		return _getInstance().getContactExpandoMappings(
			ldapServerId, companyId);
	}

	public static Properties getContactMappings(
			long ldapServerId, long companyId)
		throws Exception {

		return _getInstance().getContactMappings(ldapServerId, companyId);
	}

	public static Properties getGroupMappings(long ldapServerId, long companyId)
		throws Exception {

		return _getInstance().getGroupMappings(ldapServerId, companyId);
	}

	public static long getPreferredLDAPServerId(
		long companyId, String screenName) {

		return _getInstance().getPreferredLDAPServerId(companyId, screenName);
	}

	public static String getPropertyPostfix(long ldapServerId) {
		return _getInstance().getPropertyPostfix(ldapServerId);
	}

	public static Properties getUserExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception {

		return _getInstance().getUserExpandoMappings(ldapServerId, companyId);
	}

	public static Properties getUserMappings(long ldapServerId, long companyId)
		throws Exception {

		return _getInstance().getUserMappings(ldapServerId, companyId);
	}

	public static boolean isExportEnabled(long companyId) {
		return _getInstance().isExportEnabled(companyId);
	}

	public static boolean isExportGroupEnabled(long companyId) {
		return _getInstance().isExportGroupEnabled(companyId);
	}

	public static boolean isImportEnabled(long companyId) {
		return _getInstance().isImportEnabled(companyId);
	}

	public static boolean isImportOnStartup(long companyId) {
		return _getInstance().isImportOnStartup(companyId);
	}

	public static boolean isPasswordPolicyEnabled(long companyId) {
		return _getInstance().isPasswordPolicyEnabled(companyId);
	}

	private static LDAPSettings _getInstance() {
		return _instance._serviceTracker.getService();
	}

	private LDAPSettingsUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(LDAPSettings.class);

		_serviceTracker.open();
	}

	private static final LDAPSettingsUtil _instance = new LDAPSettingsUtil();

	private final ServiceTracker<LDAPSettings, LDAPSettings> _serviceTracker;

}