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

package com.liferay.data.cleanup.internal.upgrade;

/**
 * @author Eudaldo Alonso
 */
public class SyncUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removePortletData(
			new String[] {"com.liferay.sync.web"},
			new String[] {
				"com_liferay_sync_connector_web_portlet_SyncAdminPortlet",
				"com_liferay_sync_connector_web_portlet_SyncDevicesPortlet"
			},
			new String[] {
				"com_liferay_sync_web_portlet_SyncAdminPortlet",
				"com_liferay_sync_web_portlet_SyncDevicesPortlet"
			});

		removeServiceData(
			"Sync", new String[] {"com.liferay.sync.service"},
			new String[] {
				"com.liferay.sync.model.SyncDevice",
				"com.liferay.sync.model.SyncDLFileVersionDiff",
				"com.liferay.sync.model.SyncDLObject"
			},
			new String[] {
				"Sync_SyncDevice", "Sync_SyncDLFileVersionDiff",
				"Sync_SyncDLObject"
			});
	}

}