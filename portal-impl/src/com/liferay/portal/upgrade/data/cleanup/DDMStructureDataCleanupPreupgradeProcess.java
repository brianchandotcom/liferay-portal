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
public class DDMStructureDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// Upgrade from 7.4

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "DDMStructureId", "JournalArticle", "structureId",
				"DDMStructure"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "DDMStructureId", "JournalFeed", "structureId",
				"DDMStructure"));

		// Upgrade from 7.0 - 7.3

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "DDMStructureKey", "JournalArticle", "structureKey",
				"DDMStructure"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "DDMStructureKey", "JournalFeed", "structureKey",
				"DDMStructure"));

		// Upgrade from 6.2

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureId", "JournalArticle", "structureKey",
				"DDMStructure"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureId", "JournalFeed", "structureKey",
				"DDMStructure"));
	}

}