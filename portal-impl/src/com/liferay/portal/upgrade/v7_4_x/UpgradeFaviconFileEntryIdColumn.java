/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Rubén Pulido
 */
public class UpgradeFaviconFileEntryIdColumn extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("Layout", "faviconFileEntryId")) {
			alterTableAddColumn("Layout", "faviconFileEntryId", "LONG");

			runSQL("update Layout set faviconFileEntryId = 0");
		}

		if (!hasColumn("LayoutSet", "faviconFileEntryId")) {
			alterTableAddColumn("LayoutSet", "faviconFileEntryId", "LONG");

			runSQL("update LayoutSet set faviconFileEntryId = 0");
		}
	}

}