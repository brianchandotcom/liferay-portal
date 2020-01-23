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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v7_0_0.UpgradeUserNotificationEvent;
import com.liferay.portal.upgrade.v7_0_1.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v7_0_1.UpgradeLayoutBranch;
import com.liferay.portal.upgrade.v7_0_1.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v7_0_1.UpgradeModules;
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
		upgrade(new UpgradeModules());
		upgrade(new UpgradeUserNotificationEvent());

		clearIndexesCache();
	}

}