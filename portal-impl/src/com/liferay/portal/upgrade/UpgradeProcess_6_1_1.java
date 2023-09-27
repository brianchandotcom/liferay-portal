/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v6_1_1.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v6_1_1.UpgradeJournal;
import com.liferay.portal.upgrade.v6_1_1.UpgradeLayout;
import com.liferay.portal.upgrade.v6_1_1.UpgradeLayoutSet;
import com.liferay.portal.upgrade.v6_1_1.UpgradeLayoutSetBranch;
import com.liferay.portal.upgrade.v6_1_1.UpgradeSchema;

/**
 * @author Julio Camarero
 */
public class UpgradeProcess_6_1_1 extends Pre7UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_1_1_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(new UpgradeSchema());

		upgrade(new UpgradeDocumentLibrary());
		upgrade(new UpgradeJournal());
		upgrade(new UpgradeLayout());
		upgrade(new UpgradeLayoutSet());
		upgrade(new UpgradeLayoutSetBranch());

		clearIndexesCache();
	}

}