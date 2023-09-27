/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v4_4_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Marco Galluzzi
 */
public class KBGroupServiceConfigurationUpgradeProcess extends UpgradeProcess {

	public KBGroupServiceConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format("(%s=%s*)", Constants.SERVICE_PID, _SERVICE_PID));

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties == null) {
				continue;
			}

			Object rssDelta = properties.get("rssDelta");

			if (rssDelta instanceof Integer) {
				properties.put("rssDelta", String.valueOf(rssDelta));
			}

			properties.remove("rssFormat");

			configuration.update(properties);
		}
	}

	private static final String _SERVICE_PID =
		"com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration";

	private final ConfigurationAdmin _configurationAdmin;

}