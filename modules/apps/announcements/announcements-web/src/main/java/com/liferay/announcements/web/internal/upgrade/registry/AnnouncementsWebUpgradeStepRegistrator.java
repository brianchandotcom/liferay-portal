/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.upgrade.registry;

import com.liferay.announcements.web.internal.upgrade.v1_0_2.PermissionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
@Component(service = UpgradeStepRegistrator.class)
public class AnnouncementsWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register("0.0.1", "1.0.1", new DummyUpgradeStep());

		// See LPS-65946

		registry.register("1.0.0", "1.0.1", new DummyUpgradeStep());

		registry.register("1.0.1", "1.0.2", new PermissionUpgradeProcess(true));

		// See LPS-69656

		registry.register("1.0.2", "1.0.3", new PermissionUpgradeProcess(true));

		registry.register("1.0.3", "1.0.4", new DummyUpgradeStep());

		registry.register(
			"1.0.4", "2.0.0",
			new com.liferay.announcements.web.internal.upgrade.v2_0_0.
				PortletPreferencesUpgradeProcess());
	}

}