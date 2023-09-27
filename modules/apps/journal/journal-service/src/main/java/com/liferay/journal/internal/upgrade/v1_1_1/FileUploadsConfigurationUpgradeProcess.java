/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v1_1_1;

import com.liferay.journal.configuration.JournalFileUploadsConfiguration;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Drew Brokke
 */
public class FileUploadsConfigurationUpgradeProcess extends UpgradeProcess {

	public FileUploadsConfigurationUpgradeProcess(
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeFileUploadsConfiguration();
	}

	private void _upgradeFileUploadsConfiguration() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			JournalFileUploadsConfiguration.class,
			new KeyValuePair(
				_OLD_KEY_JOURNAL_IMAGE_EXTENSIONS, "imageExtensions"),
			new KeyValuePair(
				_OLD_KEY_JOURNAL_IMAGE_SMALL_MAX_SIZE, "smallImageMaxSize"));
	}

	private static final String _OLD_KEY_JOURNAL_IMAGE_EXTENSIONS =
		"journal.image.extensions";

	private static final String _OLD_KEY_JOURNAL_IMAGE_SMALL_MAX_SIZE =
		"journal.image.small.max.size";

	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}