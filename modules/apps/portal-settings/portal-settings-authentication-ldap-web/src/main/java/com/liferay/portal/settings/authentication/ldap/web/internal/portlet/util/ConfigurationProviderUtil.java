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