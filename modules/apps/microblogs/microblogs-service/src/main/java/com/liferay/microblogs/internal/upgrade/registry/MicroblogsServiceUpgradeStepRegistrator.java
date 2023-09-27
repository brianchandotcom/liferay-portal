/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.internal.upgrade.registry;

import com.liferay.microblogs.internal.upgrade.v1_0_1.UserNotificationEventUpgradeProcess;
import com.liferay.microblogs.internal.upgrade.v1_0_2.SocialUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ryan Park
 * @author Manuel de la Peña
 */
@Component(service = UpgradeStepRegistrator.class)
public class MicroblogsServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "1.0.0",
			new com.liferay.microblogs.internal.upgrade.v1_0_0.
				MicroblogsEntryUpgradeProcess());

		registry.register(
			"1.0.0", "1.0.1", new UserNotificationEventUpgradeProcess());

		registry.register(
			"1.0.1", "1.0.2",
			new com.liferay.microblogs.internal.upgrade.v1_0_2.
				MicroblogsEntryUpgradeProcess(),
			new SocialUpgradeProcess());
	}

}