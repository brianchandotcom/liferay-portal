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

package com.liferay.portal.security.ldap.bundle.ldapsettingsutil;

import com.liferay.portal.security.ldap.LDAPSettings;

import java.util.Properties;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestLDAPSettings implements LDAPSettings {

	@Override
	public String getAuthSearchFilter(
			long ldapServerId, long companyId, String emailAddress,
			String screenName, String userId)
		throws Exception {

		return "(companyId=" + companyId + ")";
	}

	@Override
	public Properties getContactExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception {

		Properties properties = new Properties();

		properties.setProperty("ldapServerId", String.valueOf(ldapServerId));

		return properties;
	}

	@Override
	public Properties getContactMappings(long ldapServerId, long companyId)
		throws Exception {

		Properties properties = new Properties();

		properties.setProperty("ldapServerId", String.valueOf(ldapServerId));

		return properties;
	}

	@Override
	public String getErrorPasswordHistory(long companyId) {
		return "history";
	}

	@Override
	public Properties getGroupMappings(long ldapServerId, long companyId)
		throws Exception {

		Properties properties = new Properties();

		properties.setProperty("ldapServerId", ldapServerId + "");

		return properties;
	}

	@Override
	public long getPreferredLDAPServerId(long companyId, String screenName) {
		if (companyId == 1) {
			return 1234567890;
		}

		return 0;
	}

	@Override
	public String getPropertyPostfix(long ldapServerId) {
		if (ldapServerId == 1) {
			return "liferay.ldap";
		}

		return "unknown";
	}

	@Override
	public Properties getUserExpandoMappings(long ldapServerId, long companyId)
		throws Exception {

		Properties properties = new Properties();

		properties.setProperty("ldapServerId", ldapServerId + "");

		return properties;
	}

	@Override
	public Properties getUserMappings(long ldapServerId, long companyId)
		throws Exception {

		Properties properties = new Properties();

		properties.setProperty("ldapServerId", ldapServerId + "");

		return properties;
	}

	@Override
	public boolean isExportEnabled(long companyId) {
		if (companyId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isExportGroupEnabled(long companyId) {
		if (companyId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isImportEnabled(long companyId) {
		if (companyId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isImportOnStartup(long companyId) {
		if (companyId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isPasswordPolicyEnabled(long companyId) {
		if (companyId == 1) {
			return true;
		}

		return false;
	}

}