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

package com.liferay.marketplace.internal.upgrade.v0_0_1;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Ryan Park
 */
public class UpgradeModule extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateModules();
	}

	protected void updateModules() throws Exception {
		if (!hasColumn("Marketplace_Module", "bundleSymbolicName")) {
			runSQL(
				"alter table Marketplace_Module add bundleSymbolicName " +
					"VARCHAR(500)");
		}

		if (!hasColumn("Marketplace_Module", "bundleVersion")) {
			runSQL(
				"alter table Marketplace_Module add bundleVersion VARCHAR(75)");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeModule.class);

}