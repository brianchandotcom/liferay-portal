/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.upgrade.registry;

import com.liferay.friendly.url.configuration.FriendlyURLRedirectionConfiguration;
import com.liferay.friendly.url.internal.upgrade.v2_0_0.util.FriendlyURLEntryTable;
import com.liferay.friendly.url.internal.upgrade.v3_0_0.UpgradeCompanyId;
import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Ángel Jiménez
 */
@Component(service = UpgradeStepRegistrator.class)
public class FriendlyURLServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {FriendlyURLEntryTable.class}));

		registry.register("2.0.0", "3.0.0", new UpgradeCompanyId());

		registry.register(
			"3.0.0", "3.1.0",
			new CTModelUpgradeProcess(
				"FriendlyURLEntry", "FriendlyURLEntryLocalization",
				"FriendlyURLEntryMapping"));

		registry.register("3.1.0", "3.1.1", new DummyUpgradeStep());

		registry.register("3.1.1", "3.2.0", new DummyUpgradeStep());

		registry.register(
			"3.2.0", "3.3.0",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.friendly.url.internal.configuration." +
					"FriendlyURLRedirectionConfiguration",
				FriendlyURLRedirectionConfiguration.class.getName()));

		registry.register(
			"3.3.0", "3.4.0",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.friendly.url.internal.configuration." +
					"FriendlyURLRedirectionConfiguration.scoped",
				FriendlyURLRedirectionConfiguration.class.getName() +
					".scoped"));
	}

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}