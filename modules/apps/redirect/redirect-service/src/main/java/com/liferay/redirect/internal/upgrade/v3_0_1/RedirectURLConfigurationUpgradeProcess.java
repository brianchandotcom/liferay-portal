/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.upgrade.v3_0_1;

import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.redirect.internal.configuration.RedirectURLConfiguration;
import com.liferay.redirect.internal.constants.LegacyRedirectURLPropsKeys;

/**
 * @author Drew Brokke
 */
public class RedirectURLConfigurationUpgradeProcess extends UpgradeProcess {

	public RedirectURLConfigurationUpgradeProcess(
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			RedirectURLConfiguration.class,
			new KeyValuePair(
				LegacyRedirectURLPropsKeys.REDIRECT_URL_DOMAINS_ALLOWED,
				"allowedDomains"),
			new KeyValuePair(
				LegacyRedirectURLPropsKeys.REDIRECT_URL_IPS_ALLOWED,
				"allowedIPs"),
			new KeyValuePair(
				LegacyRedirectURLPropsKeys.REDIRECT_URL_SECURITY_MODE,
				"securityMode"));
	}

	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}