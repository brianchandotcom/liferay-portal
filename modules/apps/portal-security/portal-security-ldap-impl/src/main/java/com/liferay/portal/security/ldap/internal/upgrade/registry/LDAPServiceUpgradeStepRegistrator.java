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

package com.liferay.portal.security.ldap.internal.upgrade.registry;

import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;
import com.liferay.portal.security.ldap.internal.upgrade.v0_0_2.LDAPPropertiesUpgradeProcess;
import com.liferay.portal.security.ldap.internal.upgrade.v1_0_0.LDAPSystemConfigurationsUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(service = UpgradeStepRegistrator.class)
public class LDAPServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "0.0.2",
			new LDAPPropertiesUpgradeProcess(
				_companyLocalService, _ldapAuthConfigurationProvider,
				_ldapExportConfigurationProvider,
				_ldapImportConfigurationProvider,
				_ldapServerConfigurationProvider, _ldapSettings, _prefsProps,
				_systemLDAPConfigurationProvider));

		registry.register(
			"0.0.2", "1.0.0",
			new LDAPSystemConfigurationsUpgradeProcess(
				_configurationAdmin, _configurationProvider));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration)"
	)
	private com.liferay.portal.security.ldap.configuration.ConfigurationProvider
		<LDAPAuthConfiguration> _ldapAuthConfigurationProvider;

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration)"
	)
	private com.liferay.portal.security.ldap.configuration.ConfigurationProvider
		<LDAPExportConfiguration> _ldapExportConfigurationProvider;

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration)"
	)
	private com.liferay.portal.security.ldap.configuration.ConfigurationProvider
		<LDAPImportConfiguration> _ldapImportConfigurationProvider;

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration)"
	)
	private com.liferay.portal.security.ldap.configuration.ConfigurationProvider
		<LDAPServerConfiguration> _ldapServerConfigurationProvider;

	@Reference
	private LDAPSettings _ldapSettings;

	@Reference
	private PrefsProps _prefsProps;

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration)"
	)
	private com.liferay.portal.security.ldap.configuration.ConfigurationProvider
		<SystemLDAPConfiguration> _systemLDAPConfigurationProvider;

}