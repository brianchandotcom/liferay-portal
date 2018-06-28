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

package com.liferay.users.admin.web.internal.upgrade.v1_0_0;

import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

/**
 * @author Drew Brokke
 */
public class UpgradeFileUploadsConfiguration extends UpgradeProcess {

	public UpgradeFileUploadsConfiguration(
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeFileUploadsConfiguration();
	}

	protected void upgradeFileUploadsConfiguration() throws Exception {
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