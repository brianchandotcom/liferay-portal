/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class UploadServletRequestConfigurationUpgradeProcess
	extends UpgradeProcess {

	public UploadServletRequestConfigurationUpgradeProcess(
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