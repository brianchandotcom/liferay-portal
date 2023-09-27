/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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