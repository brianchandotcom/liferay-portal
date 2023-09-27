/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.upgrade.registry;

import com.liferay.adaptive.media.image.internal.upgrade.v2_0_0.util.AMImageEntryTable;
import com.liferay.adaptive.media.image.internal.upgrade.v2_2_1.AMImageConfigurationUpgradeProcess;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Ángel Jiménez
 */
@Component(service = UpgradeStepRegistrator.class)
public class AMImageServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {AMImageEntryTable.class}));

		registry.register(
			"2.0.0", "2.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getTableNames() {
					return new String[] {"AMImageEntry"};
				}

			});

		registry.register(
			"2.1.0", "2.2.0", new CTModelUpgradeProcess("AMImageEntry"));

		registry.register(
			"2.2.0", "2.2.1",
			new AMImageConfigurationUpgradeProcess(_configurationAdmin));
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}