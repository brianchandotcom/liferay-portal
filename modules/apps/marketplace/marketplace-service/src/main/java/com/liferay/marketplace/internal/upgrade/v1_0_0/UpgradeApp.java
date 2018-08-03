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

package com.liferay.marketplace.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Ryan Park
 */
public class UpgradeApp extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateModules();
	}

	protected void updateModules() throws Exception {
		if (!hasColumn("Marketplace_App", "title")) {
			runSQL("alter table Marketplace_App add title VARCHAR(75)");
		}

		if (!hasColumn("Marketplace_App", "description")) {
			runSQL("alter table Marketplace_App add description STRING");
		}

		if (!hasColumn("Marketplace_App", "category")) {
			runSQL("alter table Marketplace_App add category VARCHAR(75)");
		}

		if (!hasColumn("Marketplace_App", "iconURL")) {
			runSQL("alter table Marketplace_App add iconURL STRING");
		}

		if (!hasColumn("Marketplace_App", "version")) {
			runSQL("alter table Marketplace_App add version VARCHAR(75)");
		}
	}

}