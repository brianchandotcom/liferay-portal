/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_9;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.constants.DLFileEntryConfigurationConstants;
import com.liferay.document.library.internal.configuration.DLSizeLimitConfiguration;
import com.liferay.document.library.internal.constants.LegacyDLKeys;
import com.liferay.document.library.internal.upgrade.helper.DLConfigurationUpgradeHelper;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;

/**
 * @author Alicia García
 */
public class DLLegacyConfigurationUpgradeProcess extends UpgradeProcess {

	public DLLegacyConfigurationUpgradeProcess(
		DLConfigurationUpgradeHelper dlConfigurationUpgradeHelper,
		PrefsProps prefsProps,
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_dlConfigurationUpgradeHelper = dlConfigurationUpgradeHelper;
		_prefsProps = prefsProps;
		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			DLConfiguration.class,
			new KeyValuePair(
				LegacyDLKeys.DL_FILE_EXTENSIONS, "fileExtensions"));

		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			DLSizeLimitConfiguration.class,
			new KeyValuePair(LegacyDLKeys.DL_FILE_MAX_SIZE, "fileMaxSize"));

		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			DLFileEntryConfiguration.class,
			new KeyValuePair(
				LegacyDLKeys.DL_FILE_ENTRY_PREVIEWABLE_PROCESSOR_MAX_SIZE,
				"previewableProcessorMaxSize"));

		_dlConfigurationUpgradeHelper.updateDLSizeLimitConfiguration();

		long systemPreviewableProcessorMaxSize =
			_dlConfigurationUpgradeHelper.updateSystemConfiguration();

		_dlConfigurationUpgradeHelper.updateScopedConfigurations(
			systemPreviewableProcessorMaxSize);

		_dlConfigurationUpgradeHelper.deleteConfigurations(
			DLConfigurationUpgradeHelper.CLASS_NAME_PDF_PREVIEW_CONFIGURATION);
	}

	private final DLConfigurationUpgradeHelper _dlConfigurationUpgradeHelper;
	private final PrefsProps _prefsProps;
	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}