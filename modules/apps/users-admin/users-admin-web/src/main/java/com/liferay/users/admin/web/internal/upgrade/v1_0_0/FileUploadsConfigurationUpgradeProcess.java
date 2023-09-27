/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.upgrade.v1_0_0;

import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

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
			UserFileUploadsConfiguration.class,
			new KeyValuePair(
				_OLD_KEY_USERS_IMAGE_CHECK_TOKEN, "imageCheckToken"),
			new KeyValuePair(_OLD_KEY_USERS_IMAGE_MAX_HEIGHT, "imageMaxHeight"),
			new KeyValuePair(_OLD_KEY_USERS_IMAGE_MAX_SIZE, "imageMaxSize"),
			new KeyValuePair(_OLD_KEY_USERS_IMAGE_MAX_WIDTH, "imageMaxWidth"));
	}

	private static final String _OLD_KEY_USERS_IMAGE_CHECK_TOKEN =
		"users.image.check.token";

	private static final String _OLD_KEY_USERS_IMAGE_MAX_HEIGHT =
		"users.image.max.height";

	private static final String _OLD_KEY_USERS_IMAGE_MAX_SIZE =
		"users.image.max.size";

	private static final String _OLD_KEY_USERS_IMAGE_MAX_WIDTH =
		"users.image.max.width";

	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}