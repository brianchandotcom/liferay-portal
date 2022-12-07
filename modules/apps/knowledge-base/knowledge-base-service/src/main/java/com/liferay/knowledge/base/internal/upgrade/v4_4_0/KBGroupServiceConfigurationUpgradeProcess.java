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
		_upgradeRSSConfiguration();
	}

	private void _upgradeRSSConfiguration() throws Exception {
		String filterString = String.format(
			"(%s=%s*)", Constants.SERVICE_PID, _SERVICE_PID);

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties == null) {
				continue;
			}

			Integer rssDelta = (Integer)properties.get("rssDelta");

			if (rssDelta != null) {
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