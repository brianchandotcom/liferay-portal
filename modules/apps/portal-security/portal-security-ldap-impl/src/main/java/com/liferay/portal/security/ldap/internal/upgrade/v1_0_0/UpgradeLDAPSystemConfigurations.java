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

package com.liferay.portal.security.ldap.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;
import com.liferay.portal.security.ldap.configuration.SystemLDAPConfiguration;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Marta Medio
 */
public class UpgradeLDAPSystemConfigurations extends UpgradeProcess {

	public UpgradeLDAPSystemConfigurations(
		ConfigurationAdmin configurationAdmin,
		ConfigurationProvider configurationProvider) {

		_configurationAdmin = configurationAdmin;
		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeConfiguration(LDAPAuthConfiguration.class.getName());
		_upgradeConfiguration(LDAPExportConfiguration.class.getName());
		_upgradeConfiguration(LDAPImportConfiguration.class.getName());
		_upgradeConfiguration(SystemLDAPConfiguration.class.getName());
	}

	private void _upgradeConfiguration(String className) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=" + className + ")");

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> configurationProperties =
				configuration.getProperties();

			Long companyId = GetterUtil.getLong(
				configurationProperties.get("companyId"));

			if (companyId == CompanyConstants.SYSTEM) {
				_configurationProvider.saveSystemConfiguration(
					Class.forName(className), configurationProperties);

				configuration.delete();
			}
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final ConfigurationProvider _configurationProvider;

}