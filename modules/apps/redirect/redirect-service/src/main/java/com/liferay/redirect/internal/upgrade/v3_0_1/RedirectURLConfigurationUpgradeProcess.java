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