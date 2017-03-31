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

package com.liferay.document.library.internal.upgrade.v1_0_1;

import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;

import javax.portlet.PortletPreferences;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
public class UpgradeDLFileEntryConfiguration extends UpgradeProcess {

	public UpgradeDLFileEntryConfiguration(
		ConfigurationAdmin configurationAdmin, PrefsProps prefsProps) {

		_configurationAdmin = configurationAdmin;
		_prefsProps = prefsProps;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeConfiguration();
	}

	private void _upgradeConfiguration() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			DLFileEntryConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary();
		}

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		String oldPropertyKey = "dl.file.entry.previewable.processor.max.size";

		String oldPropertyValue = _prefsProps.getString(oldPropertyKey);

		if (Validator.isNotNull(oldPropertyValue)) {
			properties.put("previewableProcessorMaxSize", oldPropertyValue);

			portletPreferences.reset(oldPropertyKey);
		}

		configuration.update(properties);
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}