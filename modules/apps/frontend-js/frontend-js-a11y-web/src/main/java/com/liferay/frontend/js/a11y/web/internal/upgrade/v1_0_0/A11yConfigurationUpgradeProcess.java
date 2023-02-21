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

package com.liferay.frontend.js.a11y.web.internal.upgrade.v1_0_0;

import com.liferay.frontend.js.a11y.web.internal.configuration.A11yConfiguration;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Shuyang Zhou
 */
public class A11yConfigurationUpgradeProcess extends UpgradeProcess {

	public A11yConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=com.liferay.frontend.js.a11y.web.internal." +
				"configuration.FFA11yConfiguration)");

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		Configuration oldConfiguration = configurations[0];

		Dictionary<String, Object> oldProperties =
			oldConfiguration.getProperties();

		if (GetterUtil.getBoolean(oldProperties.get("enable"))) {
			Configuration configuration = _configurationAdmin.getConfiguration(
				A11yConfiguration.class.getName(), "?");

			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties == null) {
				properties = new HashMapDictionary<>();
			}

			if (!GetterUtil.getBoolean(properties.get("enable"))) {
				properties.put("enable", true);

				configuration.update(properties);
			}
		}

		oldConfiguration.delete();
	}

	private final ConfigurationAdmin _configurationAdmin;

}