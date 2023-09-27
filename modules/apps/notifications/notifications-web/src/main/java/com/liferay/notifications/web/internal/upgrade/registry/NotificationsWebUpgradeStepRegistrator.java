/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notifications.web.internal.upgrade.registry;

import com.liferay.notifications.web.internal.constants.NotificationsPortletKeys;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.upgrade.BaseReplacePortletId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(service = UpgradeStepRegistrator.class)
public class NotificationsWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new com.liferay.notifications.web.internal.upgrade.v1_0_0.
				UserNotificationEventUpgradeProcess(
					_userNotificationEventLocalService));

		registry.register("1.0.0", "1.3.0", new DummyUpgradeStep());

		registry.register("1.1.0", "1.3.0", new DummyUpgradeStep());

		registry.register("1.2.0", "1.3.0", new DummyUpgradeStep());

		registry.register("1.3.0", "2.0.0", new DummyUpgradeStep());

		UpgradeStep upgradePortletId = new BaseReplacePortletId() {

			@Override
			protected String[][] getRenamePortletIdsArray() {
				return new String[][] {
					{
						"1_WAR_notificationsportlet",
						NotificationsPortletKeys.NOTIFICATIONS
					},
					{
						"2_WAR_notificationsportlet",
						NotificationsPortletKeys.NOTIFICATIONS
					}
				};
			}

		};

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.notifications.web.internal.upgrade.v2_1_0.
				UserNotificationEventUpgradeProcess(
					_userNotificationEventLocalService),
			upgradePortletId);
	}

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}