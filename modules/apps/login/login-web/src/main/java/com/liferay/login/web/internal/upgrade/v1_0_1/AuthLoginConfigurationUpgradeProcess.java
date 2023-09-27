/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.login.web.internal.upgrade.v1_0_1;

import com.liferay.login.web.internal.configuration.AuthLoginConfiguration;
import com.liferay.login.web.internal.constants.LegacyAuthLoginPropsKeys;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Erick Monteiro
 */
public class AuthLoginConfigurationUpgradeProcess extends UpgradeProcess {

	public AuthLoginConfigurationUpgradeProcess(
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			AuthLoginConfiguration.class,
			new KeyValuePair(
				LegacyAuthLoginPropsKeys.AUTH_LOGIN_PROMPT_ENABLED,
				"promptEnabled"));
	}

	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}