/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch.monitoring.web.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.search.elasticsearch.monitoring.web.internal.configuration.MonitoringConfiguration;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Bryan Engler
 */
public class MonitoringConfigurationUpgradeProcess extends UpgradeProcess {

	public MonitoringConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeMonitoringConfiguration();
	}

	private Configuration _getConfiguration(String className) throws Exception {
		String filterString = StringBundler.concat(
			"(", Constants.SERVICE_PID, "=", className, ")");

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations != null) {
			return configurations[0];
		}

		return null;
	}

	private void _upgradeMonitoringConfiguration() throws Exception {
		Configuration monitoringConfiguration = _getConfiguration(
			MonitoringConfiguration.class.getName());

		if (monitoringConfiguration != null) {
			monitoringConfiguration.update(
				monitoringConfiguration.getProperties());
		}
	}

	private final ConfigurationAdmin _configurationAdmin;

}