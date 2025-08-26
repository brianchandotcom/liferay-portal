/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.TableOrphanReferencesDataCleanupPreupgradeProcess;

/**
 * @author Luis Ortiz
 */
public class DDMDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"classNameId = (select classNameId from ClassName_ where " +
					"value = 'com.liferay.dynamic.data.mapping.model." +
						"DDMStructure')",
				new String[] {"classNameId"}, "classPK",
				new String[] {"structureId"}, "DDMStructure"));
		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"classNameId = (select classNameId from ClassName_ where " +
					"value = 'com.liferay.dynamic.data.mapping.model." +
						"DDMTemplate')",
				new String[] {"classNameId"}, "classPK",
				new String[] {"templateId"}, "DDMTemplate"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureId", "DDMFormInstance", "structureId",
				"DDMStructure"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureId", "DDMStorageLink", "structureId",
				"DDMStructure"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureId", "DDMStructureLink", "structureId",
				"DDMStructure"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureId", "DDMStructureVersion", "structureId",
				"DDMStructure"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "templateId", "DDMTemplateLink", "templateId",
				"DDMTemplate"));

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "formInstanceId", "DDMFormInstanceRecord",
				"formInstanceId", "DDMFormInstance"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "formInstanceId", "DDMFormInstanceRecordVersion",
				"formInstanceId", "DDMFormInstance"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "formInstanceId", "DDMFormInstanceReport",
				"formInstanceId", "DDMFormInstance"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "formInstanceId", "DDMFormInstanceReportVersion",
				"formInstanceId", "DDMFormInstance"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureVersionId", "DDMField", "structureVersionId",
				"DDMStructureVersion"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureVersionId", "DDMStructureLayout",
				"structureVersionId", "DDMStructureVersion"));

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "fieldId", "DDMFieldAttribute", "fieldId", "DDMField"));

		// Upgrade from 7.4

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "DDMStructureId", "JournalArticle", "structureId",
				"DDMStructure"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "DDMStructureId", "JournalFeed", "structureId",
				"DDMStructure"));

		// Upgrade from 7.0 to 7.3

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