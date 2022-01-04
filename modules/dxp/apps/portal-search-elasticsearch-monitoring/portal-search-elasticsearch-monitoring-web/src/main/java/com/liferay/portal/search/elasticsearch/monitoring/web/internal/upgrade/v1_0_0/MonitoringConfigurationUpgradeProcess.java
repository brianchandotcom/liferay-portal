/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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