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