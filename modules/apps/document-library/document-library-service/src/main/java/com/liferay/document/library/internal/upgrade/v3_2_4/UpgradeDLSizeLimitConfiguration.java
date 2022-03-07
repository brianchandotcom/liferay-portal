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

package com.liferay.document.library.internal.upgrade.v3_2_4;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Adolfo Pérez
 */
public class UpgradeDLSizeLimitConfiguration extends UpgradeProcess {

	public UpgradeDLSizeLimitConfiguration(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long fileMaxSize = _updateDLConfiguration();

		if (fileMaxSize > 0) {
			_updateDLSizeLimitConfiguration(fileMaxSize);
		}
	}

	private long _updateDLConfiguration() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=" + DLConfiguration.class.getName() + ")");

		if (configurations == null) {
			return 0;
		}

		Configuration configuration = configurations[0];

		Dictionary<String, Object> dictionary = configuration.getProperties();

		if (dictionary == null) {
			return 0;
		}

		Long fileMaxSize = (Long)dictionary.remove("fileMaxSize");

		if (fileMaxSize == null) {
			return 0;
		}

		configuration.update(dictionary);

		return fileMaxSize;
	}

	private void _updateDLSizeLimitConfiguration(long fileMaxSize)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			DLSizeLimitConfiguration.class.getName(), StringPool.QUESTION);

		configuration.update(
			HashMapDictionaryBuilder.put(
				"fileMaxSize", fileMaxSize
			).build());
	}

	private final ConfigurationAdmin _configurationAdmin;

}