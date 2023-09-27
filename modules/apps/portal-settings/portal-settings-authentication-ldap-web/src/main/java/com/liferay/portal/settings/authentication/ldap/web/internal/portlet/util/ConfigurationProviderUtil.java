/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.settings.authentication.ldap.web.internal.portlet.util;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;

/**
 * @author Michael C. Han
 */
public class ConfigurationProviderUtil {

	public static ConfigurationProvider<LDAPAuthConfiguration>
		getLDAPAuthConfigurationProvider() {

		return _ldapAuthConfigurationProviderSnapshot.get();
	}

	public static ConfigurationProvider<LDAPExportConfiguration>
		getLDAPExportConfigurationProvider() {

		return _ldapExportConfigurationProviderSnapshot.get();
	}

	public static ConfigurationProvider<LDAPImportConfiguration>
		getLDAPImportConfigurationProvider() {

		return _ldapImportConfigurationProviderSnapshot.get();
	}

	public static ConfigurationProvider<LDAPServerConfiguration>
		getLDAPServerConfigurationProvider() {

		return _ldapServerConfigurationProviderSnapshot.get();
	}

	private static final Snapshot<ConfigurationProvider<LDAPAuthConfiguration>>
		_ldapAuthConfigurationProviderSnapshot = new Snapshot<>(
			ConfigurationProviderUtil.class,
			Snapshot.cast(ConfigurationProvider.class),
			"(factoryPid=com.liferay.portal.security.ldap.authenticator." +
				"configuration.LDAPAuthConfiguration)");
	private static final Snapshot
		<ConfigurationProvider<LDAPExportConfiguration>>
			_ldapExportConfigurationProviderSnapshot = new Snapshot<>(
				ConfigurationProviderUtil.class,
				Snapshot.cast(ConfigurationProvider.class),
				"(factoryPid=com.liferay.portal.security.ldap.exportimport." +
					"configuration.LDAPExportConfiguration)");
	private static final Snapshot
		<ConfigurationProvider<LDAPImportConfiguration>>
			_ldapImportConfigurationProviderSnapshot = new Snapshot<>(
				ConfigurationProviderUtil.class,
				Snapshot.cast(ConfigurationProvider.class),
				"(factoryPid=com.liferay.portal.security.ldap.exportimport." +
					"configuration.LDAPImportConfiguration)");
	private static final Snapshot
		<ConfigurationProvider<LDAPServerConfiguration>>
			_ldapServerConfigurationProviderSnapshot = new Snapshot<>(
				ConfigurationProviderUtil.class,
				Snapshot.cast(ConfigurationProvider.class),
				"(factoryPid=com.liferay.portal.security.ldap.configuration." +
					"LDAPServerConfiguration)");

}