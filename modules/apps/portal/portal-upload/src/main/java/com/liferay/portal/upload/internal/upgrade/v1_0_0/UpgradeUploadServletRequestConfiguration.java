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

package com.liferay.portal.upload.internal.upgrade.v1_0_0;

import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.upload.constants.LegacyUploadServletRequestPropsKeys;
import com.liferay.portal.upload.internal.configuration.UploadServletRequestConfiguration;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeUploadServletRequestConfiguration extends UpgradeProcess {

	public UpgradeUploadServletRequestConfiguration(
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			UploadServletRequestConfiguration.class,
			new KeyValuePair(
				LegacyUploadServletRequestPropsKeys.
					UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
				"maxSize"),
			new KeyValuePair(
				LegacyUploadServletRequestPropsKeys.
					UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
				"tempDir"));
	}

	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}