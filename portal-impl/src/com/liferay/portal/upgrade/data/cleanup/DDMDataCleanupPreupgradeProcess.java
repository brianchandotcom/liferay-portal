/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.TableOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Luis Ortiz
 */
public class DDMDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DataCleanupPreupgradeProcess ddmStructureDataCleanupPreupgradeProcess =
			_getDDMStructureDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess
			ddmStructureVersionDataCleanupPreupgradeProcess =
				_getDDMStructureVersionDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess ddmTemplateDataCleanupPreupgradeProcess =
			_getDDMTemplateDataCleanupPreupgradeProcess();

		Map<DataCleanupPreupgradeProcess, List<DataCleanupPreupgradeProcess>>
			dataCleanupPreupgradeProcessesMap =
				LinkedHashMapBuilder.
					<DataCleanupPreupgradeProcess,
					 List<DataCleanupPreupgradeProcess>>put(
						_getDDMFieldDataCleanupPreupgradeProcess(),
						dependsOn(
							ddmStructureVersionDataCleanupPreupgradeProcess)
					).put(
						_getDDMFormInstanceDataCleanupPreupgradeProcess(),
						dependsOn(ddmStructureDataCleanupPreupgradeProcess)
					).put(
						ddmStructureDataCleanupPreupgradeProcess, dependsOn()
					).put(
						ddmStructureVersionDataCleanupPreupgradeProcess,
						dependsOn(ddmStructureDataCleanupPreupgradeProcess)
					).put(
						ddmTemplateDataCleanupPreupgradeProcess,
						dependsOn(ddmStructureDataCleanupPreupgradeProcess)
					).put(
						_getJournalPointingOrphanDDMStructureCleanupPreupgradeProcess62(),
						dependsOn(ddmTemplateDataCleanupPreupgradeProcess)
					).put(
						_getJournalPointingOrphanDDMStructureCleanupPreupgradeProcess70to73(),
						dependsOn(ddmTemplateDataCleanupPreupgradeProcess)
					).put(
						_getJournalPointingOrphanDDMStructureCleanupPreupgradeProcess74(),
						dependsOn(ddmTemplateDataCleanupPreupgradeProcess)
					).build();

		List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses =
			getSortedDataCleanupPreupgradeProcesses(
				dataCleanupPreupgradeProcessesMap);

		for (DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess :
				dataCleanupPreupgradeProcesses) {

			dataCleanupPreupgradeProcess.upgrade();
		}
	}

	private DataCleanupPreupgradeProcess
		_getDDMFieldDataCleanupPreupgradeProcess() {

		return new DataCleanupPreupgradeProcess(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "fieldId", "DDMFieldAttribute", "fieldId",
				"DDMField"));
	}

	private DataCleanupPreupgradeProcess
		_getDDMFormInstanceDataCleanupPreupgradeProcess() {

		return new DataCleanupPreupgradeProcess(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "formInstanceId", "DDMFormInstanceRecord",
				"formInstanceId", "DDMFormInstance"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "formInstanceId", "DDMFormInstanceRecordVersion",
				"formInstanceId", "DDMFormInstance"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "formInstanceId", "DDMFormInstanceReport",
				"formInstanceId", "DDMFormInstance"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "formInstanceId", "DDMFormInstanceReportVersion",
				"formInstanceId", "DDMFormInstance"));
	}

	private DataCleanupPreupgradeProcess
		_getDDMStructureDataCleanupPreupgradeProcess() {

		return new DataCleanupPreupgradeProcess(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				StringBundler.concat(
					"[$SOURCE_TABLE_ALIAS$].classNameId = (select classNameId ",
					"from ClassName_ where value = 'com.liferay.dynamic.data.",
					"mapping.model.DDMStructure')"),
				new String[] {"classNameId"}, "classPK",
				new String[] {"structureId"}, "DDMStructure"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "structureId", "DDMFormInstance", "structureId",
				"DDMStructure"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "structureId", "DDMStorageLink", "structureId",
				"DDMStructure"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "structureId", "DDMStructureLink", "structureId",
				"DDMStructure"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "structureId", "DDMStructureVersion", "structureId",
				"DDMStructure"));
	}

	private DataCleanupPreupgradeProcess
		_getDDMStructureVersionDataCleanupPreupgradeProcess() {

		return new DataCleanupPreupgradeProcess(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "structureVersionId", "DDMField",
				"structureVersionId", "DDMStructureVersion"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "structureVersionId", "DDMStructureLayout",
				"structureVersionId", "DDMStructureVersion"));
	}

	private DataCleanupPreupgradeProcess
		_getDDMTemplateDataCleanupPreupgradeProcess() {

		return new DataCleanupPreupgradeProcess(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				StringBundler.concat(
					"[$SOURCE_TABLE_ALIAS$].classNameId = (select classNameId ",
					"from ClassName_ where value = 'com.liferay.dynamic.data.",
					"mapping.model.DDMTemplate')"),
				new String[] {"classNameId"}, "classPK",
				new String[] {"templateId"}, "DDMTemplate"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "templateId", "DDMTemplateLink", "templateId",
				"DDMTemplate"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "templateId", "DDMTemplateVersion", "templateId",
				"DDMTemplate"));
	}

	private DataCleanupPreupgradeProcess
		_getJournalPointingOrphanDDMStructureCleanupPreupgradeProcess62() {

		return new DataCleanupPreupgradeProcess(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "structureId", "JournalArticle", "structureKey",
				"DDMStructure"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "structureId", "JournalFeed", "structureKey",
				"DDMStructure"));
	}

	private DataCleanupPreupgradeProcess
		_getJournalPointingOrphanDDMStructureCleanupPreupgradeProcess70to73() {

		return new DataCleanupPreupgradeProcess(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "DDMStructureKey", "JournalArticle", "structureKey",
				"DDMStructure"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "DDMStructureKey", "JournalFeed", "structureKey",
				"DDMStructure"));
	}

	private DataCleanupPreupgradeProcess
		_getJournalPointingOrphanDDMStructureCleanupPreupgradeProcess74() {

		return new DataCleanupPreupgradeProcess(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "DDMStructureId", "JournalArticle", "structureId",
				"DDMStructure"),
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, null, "DDMStructureId", "JournalFeed", "structureId",
				"DDMStructure"));
	}

}