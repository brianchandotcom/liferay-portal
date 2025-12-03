/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.TableOrphanReferencesDataCleanupPreupgradeProcess;

/**
 * @author Luis Ortiz
 */
public class PortletPreferencesDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null,
				"not exists (select 1 from LayoutRevision where " +
					"layoutRevisionId = [$SOURCE_TABLE_ALIAS$].plid)",
				"plid", "PortletPreferences", "plid", "Layout"));

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "portletPreferencesId", "PortletPreferenceValue",
				"portletPreferencesId", "PortletPreferences"));
	}

}