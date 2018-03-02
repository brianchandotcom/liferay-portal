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
import com.liferay.portal.upgrade.v7_0_5.UpgradeBookmarks;
import com.liferay.portal.upgrade.v7_0_5.UpgradeCompany;
import com.liferay.portal.upgrade.v7_0_5.UpgradeContact;
import com.liferay.portal.upgrade.v7_0_5.UpgradeEmailAddress;
import com.liferay.portal.upgrade.v7_0_5.UpgradeExpando;
import com.liferay.portal.upgrade.v7_0_5.UpgradeGroup;
import com.liferay.portal.upgrade.v7_0_5.UpgradeMBMailingList;
import com.liferay.portal.upgrade.v7_0_5.UpgradePortalPreferences;
import com.liferay.portal.upgrade.v7_0_5.UpgradeUser;
import com.liferay.portal.upgrade.v7_0_5.UpgradeVirtualHost;

/**
 * @author Roberto Díaz
 * @author Samuel Ziemer
 */
public class UpgradeProcess_7_0_5 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_7_0_5_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeBookmarks.class);
		upgrade(UpgradeCompany.class);
		upgrade(UpgradeContact.class);
		upgrade(UpgradeGroup.class);
		upgrade(UpgradeEmailAddress.class);
		upgrade(UpgradeExpando.class);
		upgrade(UpgradeMBMailingList.class);
		upgrade(UpgradePortalPreferences.class);
		upgrade(UpgradeUser.class);
		upgrade(UpgradeVirtualHost.class);

		clearIndexesCache();
	}

}