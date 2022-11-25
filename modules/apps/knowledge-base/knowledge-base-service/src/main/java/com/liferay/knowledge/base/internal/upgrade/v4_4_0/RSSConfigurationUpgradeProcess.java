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

import com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Marco Galluzzi
 */
public class RSSConfigurationUpgradeProcess extends UpgradeProcess {

	public RSSConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeRSSConfiguration();
	}

	private void _upgradeRSSConfiguration() throws Exception {
		String filterString = String.format(
			"(%s=%s)", Constants.SERVICE_PID,
			KBGroupServiceConfiguration.class.getName());

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return;
		}

		Configuration configuration = configurations[0];

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			return;
		}

		int rssDelta = GetterUtil.getInteger(properties.get("rssDelta"));

		properties.put("rssDelta", String.valueOf(rssDelta));

		properties.remove("rssFormat");

		configuration.update(properties);
	}

	private final ConfigurationAdmin _configurationAdmin;

}