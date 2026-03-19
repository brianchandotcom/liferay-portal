/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Carlos Correa
 */
public class StagingConfigurationUpgradeProcess extends UpgradeProcess {

	public StagingConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(%s=%s*)", Constants.SERVICE_PID,
				"com.liferay.staging.configuration.StagingConfiguration"));

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties == null) {
				continue;
			}

			properties.remove("publishDisplayedContent");

			configuration.update(properties);
		}
	}

	private final ConfigurationAdmin _configurationAdmin;

}