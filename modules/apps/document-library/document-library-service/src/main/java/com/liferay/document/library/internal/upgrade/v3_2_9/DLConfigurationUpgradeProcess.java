/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_9;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration;
import com.liferay.document.library.internal.constants.LegacyDLKeys;
import com.liferay.document.library.internal.upgrade.helper.DLConfigurationUpgradeHelper;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Drew Brokke
 * @author Alicia García
 */
public class DLConfigurationUpgradeProcess extends UpgradeProcess {

	public DLConfigurationUpgradeProcess(
		DLConfigurationUpgradeHelper dlConfigurationUpgradeHelper,
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_dlConfigurationUpgradeHelper = dlConfigurationUpgradeHelper;
		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!_dlConfigurationUpgradeHelper.hasLegacyProps()) {
			return;
		}

		_upgradeConfiguration();
	}

	private void _upgradeConfiguration() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			DLConfiguration.class,
			new KeyValuePair(
				LegacyDLKeys.DL_FILE_EXTENSIONS, "fileExtensions"));

		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			DLSizeLimitConfiguration.class,
			new KeyValuePair(LegacyDLKeys.DL_FILE_MAX_SIZE, "fileMaxSize"));
	}

	private final DLConfigurationUpgradeHelper _dlConfigurationUpgradeHelper;
	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}