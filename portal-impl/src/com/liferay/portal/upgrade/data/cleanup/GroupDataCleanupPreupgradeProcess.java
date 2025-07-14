/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.upgrade.data.cleanup.AllTablesOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.TableOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;

/**
 * @author Luis Ortiz
 */
public class GroupDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(
			new AllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"Group_", "groupId"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				"PortalPreferences", "ownerId",
				"ownerType = " + PortletKeys.PREFS_OWNER_TYPE_GROUP, "Group_",
				"groupId"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				"PortletPreferences", "ownerId",
				"ownerType = " + PortletKeys.PREFS_OWNER_TYPE_GROUP, "Group_",
				"groupId"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				"ResourcePermission", "primKeyId",
				"scope = " + ResourceConstants.SCOPE_GROUP, "Group_",
				"groupId"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				"ResourcePermission", "primKeyId",
				StringBundler.concat(
					"scope = ", ResourceConstants.SCOPE_INDIVIDUAL,
					" and name = '", Group.class.getName(), "'"),
				"Group_", "groupId"));
	}

}