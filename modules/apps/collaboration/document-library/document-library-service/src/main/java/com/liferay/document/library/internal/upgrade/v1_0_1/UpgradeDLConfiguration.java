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

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.internal.constants.LegacyDLKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.upgrade.util.PrefsPropsToConfigurationUpgradeItem;
import com.liferay.portal.configuration.upgrade.util.PrefsPropsToConfigurationUpgradeUtil;
import com.liferay.portal.configuration.upgrade.util.PrefsPropsValueType;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PrefsProps;

import javax.portlet.PortletPreferences;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
public class UpgradeDLConfiguration extends UpgradeProcess {

	public UpgradeDLConfiguration(
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
			DLConfiguration.class.getName(), StringPool.QUESTION);
		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		PrefsPropsToConfigurationUpgradeItem[]
			prefsPropsToConfigurationUpgradeItems = {
				new PrefsPropsToConfigurationUpgradeItem(
					LegacyDLKeys.DL_FILE_EXTENSIONS,
					PrefsPropsValueType.STRING_ARRAY, "fileExtensions"),
				new PrefsPropsToConfigurationUpgradeItem(
					LegacyDLKeys.DL_FILE_MAX_SIZE, PrefsPropsValueType.LONG,
					"fileMaxSize")
			};

		PrefsPropsToConfigurationUpgradeUtil.upgradePrefsPropsToConfiguration(
			portletPreferences, configuration,
			prefsPropsToConfigurationUpgradeItems);
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}