/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.upgrade.registry;

import com.liferay.captcha.internal.upgrade.v1_0_0.CaptchaConfigurationUpgradeProcess;
import com.liferay.captcha.internal.upgrade.v1_1_0.CaptchaConfigurationPreferencesUpgradeProcess;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = UpgradeStepRegistrator.class)
public class CaptchaUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new CaptchaConfigurationUpgradeProcess(
				_prefsPropsToConfigurationUpgradeHelper));

		registry.register(
			"1.0.0", "1.1.0",
			new CaptchaConfigurationPreferencesUpgradeProcess(
				_configurationAdmin));
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}