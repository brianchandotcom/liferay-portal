/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.upgrade.registry;

import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.redirect.internal.upgrade.v3_0_2.RedirectEntrySourceURLUpgradeProcess;
import com.liferay.redirect.internal.upgrade.v3_0_3.RedirectPatternConfigurationUpgradeProcess;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = UpgradeStepRegistrator.class)
public class RedirectServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			UpgradeProcessFactory.addColumns(
				"RedirectNotFoundEntry", "userId LONG",
				"userName VARCHAR(75) null", "ignored BOOLEAN"));

		registry.register(
			"2.0.0", "2.0.1",
			new com.liferay.redirect.internal.upgrade.v2_0_1.
				RedirectNotFoundEntryUpgradeProcess());

		registry.register(
			"2.0.1", "3.0.0",
			UpgradeProcessFactory.dropColumns("RedirectNotFoundEntry", "hits"));

		registry.register("3.0.0", "3.0.1", new DummyUpgradeProcess());

		registry.register(
			"3.0.1", "3.0.2", new RedirectEntrySourceURLUpgradeProcess());

		registry.register(
			"3.0.2", "3.0.3",
			new com.liferay.redirect.internal.upgrade.v3_0_1.
				RedirectURLConfigurationUpgradeProcess(
					_prefsPropsToConfigurationUpgradeHelper));

		registry.register(
			"3.0.3", "3.0.4",
			new RedirectPatternConfigurationUpgradeProcess(
				_configurationAdmin));
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}