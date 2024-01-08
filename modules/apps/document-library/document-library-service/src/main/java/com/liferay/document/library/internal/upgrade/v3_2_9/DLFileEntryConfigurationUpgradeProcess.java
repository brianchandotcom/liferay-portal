/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_9;

import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.internal.constants.LegacyDLKeys;
import com.liferay.document.library.internal.upgrade.helper.DLConfigurationUpgradeHelper;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Drew Brokke
 * @author Marco Galluzzi
 * @author Alicia García
 */
public class DLFileEntryConfigurationUpgradeProcess extends UpgradeProcess {

	public DLFileEntryConfigurationUpgradeProcess(
		DLConfigurationUpgradeHelper dlConfigurationUpgradeHelper,
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_dlConfigurationUpgradeHelper = dlConfigurationUpgradeHelper;
		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!_dlConfigurationUpgradeHelper.hasLegacyProps() ||
			!_dlConfigurationUpgradeHelper.
				hasDLSizeLimitConfigurationChanges() ||
			!_dlConfigurationUpgradeHelper.
				hasDLFileEntryConfigurationChanges()) {

			return;
		}

		_upgradeConfiguration();
	}

	private void _upgradeConfiguration() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			DLFileEntryConfiguration.class,
			new KeyValuePair(
				LegacyDLKeys.DL_FILE_ENTRY_PREVIEWABLE_PROCESSOR_MAX_SIZE,
				"previewableProcessorMaxSize"));

		long systemPreviewableProcessorMaxSize =
			_dlConfigurationUpgradeHelper.
				getDLFileEntryConfigurationPreviewableProcessorMaxSize();

		_dlConfigurationUpgradeHelper.
			updateDLFileEntryConfigurationSystemConfiguration(
				systemPreviewableProcessorMaxSize);

		_dlConfigurationUpgradeHelper.updateScopedConfigurations(
			systemPreviewableProcessorMaxSize);

		_dlConfigurationUpgradeHelper.deleteConfigurations(
			DLConfigurationUpgradeHelper.CLASS_NAME_PDF_PREVIEW_CONFIGURATION);
	}

	private final DLConfigurationUpgradeHelper _dlConfigurationUpgradeHelper;
	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}