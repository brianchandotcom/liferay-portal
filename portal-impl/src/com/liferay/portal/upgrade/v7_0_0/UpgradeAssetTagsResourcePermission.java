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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Andrew Betts
 */
public class UpgradeAssetTagsResourcePermission extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		deleteResourcePermissions();
	}

	protected void deleteResourcePermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"delete from ResourcePermission where name = " +
					"'com.liferay.portlet.asset.model.AssetTag' and scope = " +
						ResourceConstants.SCOPE_INDIVIDUAL);
		}
	}

}