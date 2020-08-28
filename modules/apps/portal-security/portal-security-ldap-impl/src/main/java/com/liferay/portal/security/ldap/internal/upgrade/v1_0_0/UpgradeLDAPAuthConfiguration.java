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

import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Marta Medio
 */
public class UpgradeLDAPAuthConfiguration extends UpgradeProcess {

	public UpgradeLDAPAuthConfiguration(
		ConfigurationAdmin configurationAdmin,
		ConfigurationProvider configurationProvider) {

		_configurationAdmin = configurationAdmin;
		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeConfiguration();
	}

	private void _upgradeConfiguration() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=" + LDAPAuthConfiguration.class.getName() +
				")");

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> configurationProperties =
				configuration.getProperties();

			String companyId = GetterUtil.getString(
				configurationProperties.get("companyId"));

			if (companyId.equals(_PROPERTY_VALUE_COMPANY_ID_DEFAULT)) {
				ConfigurationProviderUtil.saveSystemConfiguration(
					LDAPAuthConfiguration.class, configurationProperties);

				configuration.delete();
			}
		}
	}

	private static final String _PROPERTY_VALUE_COMPANY_ID_DEFAULT = "0";

	private final ConfigurationAdmin _configurationAdmin;
	private final ConfigurationProvider _configurationProvider;

}