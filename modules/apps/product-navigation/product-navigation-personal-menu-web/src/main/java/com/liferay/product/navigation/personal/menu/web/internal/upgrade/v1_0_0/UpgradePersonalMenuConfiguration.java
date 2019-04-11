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

package com.liferay.product.navigation.personal.menu.web.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.product.navigation.personal.menu.configuration.PersonalMenuConfiguration;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pei-Jung Lan
 */
public class UpgradePersonalMenuConfiguration extends UpgradeProcess {

	public UpgradePersonalMenuConfiguration(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String filterString = StringBundler.concat(
			"(", Constants.SERVICE_PID, "=",
			PersonalMenuConfiguration.class.getName(), ")");

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations != null) {
			return;
		}

		Configuration configuration = _configurationAdmin.getConfiguration(
			PersonalMenuConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("personalApplicationsLookAndFeel", "my-dashboard");

		configuration.update(properties);
	}

	private final ConfigurationAdmin _configurationAdmin;

}