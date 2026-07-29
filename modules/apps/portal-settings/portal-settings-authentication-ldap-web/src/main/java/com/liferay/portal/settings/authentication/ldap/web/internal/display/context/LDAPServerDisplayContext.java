/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.settings.authentication.ldap.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;

import java.io.IOException;

import java.util.Properties;

/**
 * @author Caio Farias
 */
public class LDAPServerDisplayContext {

	public LDAPServerDisplayContext(
		LDAPServerConfiguration ldapServerConfiguration, long ldapServerId) {

		_ldapServerConfiguration = ldapServerConfiguration;

		_baseProviderURL = _getBaseProviderURL(
			ldapServerConfiguration.baseProviderURL(), ldapServerId);
	}

	public String getBaseProviderURL() {
		return _baseProviderURL;
	}

	public String getGroupMapping(String name) throws IOException {
		if (_groupMappings == null) {
			_groupMappings = _getMappings(
				_ldapServerConfiguration.groupMappings());
		}

		return _groupMappings.getProperty(name, StringPool.BLANK);
	}

	public String getUserMapping(String name) throws IOException {
		if (_userMappings == null) {
			_userMappings = _getMappings(
				_ldapServerConfiguration.userMappings());
		}

		return _userMappings.getProperty(name, StringPool.BLANK);
	}

	private String _getBaseProviderURL(
		String baseProviderURL, long ldapServerId) {

		if (!PropsValues.FIPS_ENABLED) {
			return baseProviderURL;
		}

		if (ldapServerId == 0) {
			return "ldaps://localhost:10636";
		}

		if (StringUtil.startsWith(baseProviderURL, "ldap://")) {
			return "ldaps://" + baseProviderURL.substring("ldap://".length());
		}

		return baseProviderURL;
	}

	private Properties _getMappings(String[] mappings) throws IOException {
		return PropertiesUtil.load(
			StringUtil.merge(mappings, StringPool.NEW_LINE));
	}

	private final String _baseProviderURL;
	private Properties _groupMappings;
	private final LDAPServerConfiguration _ldapServerConfiguration;
	private Properties _userMappings;

}