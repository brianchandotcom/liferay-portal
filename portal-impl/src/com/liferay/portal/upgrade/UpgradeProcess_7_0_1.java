/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeModulesFactory;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v7_0_0.UpgradeUserNotificationEvent;
import com.liferay.portal.upgrade.v7_0_1.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v7_0_1.UpgradeLayoutBranch;
import com.liferay.portal.upgrade.v7_0_1.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v7_0_1.UpgradeSchema;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeProcess_7_0_1 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_7_0_1_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(new UpgradeSchema());

		upgrade(new UpgradeDocumentLibrary());
		upgrade(new UpgradeLayoutBranch());
		upgrade(new UpgradeMessageBoards());
		upgrade(
			UpgradeModulesFactory.create(
				_BUNDLE_SYMBOLIC_NAMES, _CONVERTED_LEGACY_MODULES));
		upgrade(new UpgradeUserNotificationEvent());

		clearIndexesCache();
	}

	private static final String[] _BUNDLE_SYMBOLIC_NAMES = {
		"com.liferay.announcements.web", "com.liferay.directory.web",
		"com.liferay.microblogs.web", "com.liferay.notifications.web",
		"com.liferay.recent.documents.web", "com.liferay.social.networking.web",
		"com.liferay.social.privatemessaging.web"
	};

	private static final String[][] _CONVERTED_LEGACY_MODULES = {
		{"com.liferay.contacts.web", "com.liferay.contacts.web", "Contacts"},
		{
			"push-notifications-portlet",
			"com.liferay.push.notifications.service", "PushNotifications"
		},
		{"sync-web", "com.liferay.sync.service", "Sync"}
	};

}