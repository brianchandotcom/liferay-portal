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

package com.liferay.document.library.document.conversion.internal.upgrade.v1_0_0;

import com.liferay.document.library.document.conversion.internal.configuration.OpenOfficeConfiguration;
import com.liferay.document.library.document.conversion.internal.constants.LegacyOpenOfficePropsKeys;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeOpenOfficeConfiguration extends UpgradeProcess {

	public UpgradeOpenOfficeConfiguration(
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			OpenOfficeConfiguration.class,
			new KeyValuePair(
				LegacyOpenOfficePropsKeys.OPENOFFICE_CACHE_ENABLED,
				"cacheEnabled"),
			new KeyValuePair(
				LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_ENABLED,
				"serverEnabled"),
			new KeyValuePair(
				LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_HOST, "serverHost"),
			new KeyValuePair(
				LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_PORT,
				"serverPort"));
	}

	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}