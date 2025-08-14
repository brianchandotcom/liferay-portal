/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.DefaultAllTablesOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.TableOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.OrphanReferencesDataCleanupUtil;

/**
 * @author Luis Ortiz
 */
public class JournalDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// First delete all JournalArticle related data

		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"classNameId = (select classNameId from ClassName_ where " +
					"value = 'com.liferay.journal.model.JournalArticle')",
				new String[] {"classNameId"}, "classPK",
				new String[] {"resourcePrimKey", "id_"}, "JournalArticle"));

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "articleId", "JournalArticleResource", "articleId",
				"JournalArticle"));

		// Upgrade from 7.4

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				"classNameId = (select classNameId from ClassName_ where " +
					"value = 'com.liferay.journal.model.JournalArticle') and " +
						"structureKey != 'BASIC-WEB-CONTENT'",
				"structureId", "DDMStructure", "DDMStructureId",
				"JournalArticle"));

		// Upgrade from 7.0 to 7.3

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				"classNameId = (select classNameId from ClassName_ where " +
					"value = 'com.liferay.journal.model.JournalArticle') and " +
						"structureKey != 'BASIC-WEB-CONTENT'",
				"structureKey", "DDMStructure", "DDMStructureKey",
				"JournalArticle"));

		// Upgrade from 6.2

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				"classNameId = (select classNameId from ClassName_ where " +
					"value = 'com.liferay.journal.model.JournalArticle') and " +
						"structureKey != 'BASIC-WEB-CONTENT'",
				"structureKey", "DDMStructure", "structureId",
				"JournalArticle"));

		// Then delete all JournalFeed related data

		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"classNameId = (select classNameId from ClassName_ where " +
					"value = 'com.liferay.journal.model.JournalFeed')",
				new String[] {"classNameId"}, "classPK", new String[] {"id_"},
				"JournalFeed"));

		// Then delete Layout related data

		upgrade(
			new UpgradeProcess() {

				@Override
				protected void doUpgrade() throws Exception {
					DBInspector dbInspector = new DBInspector(connection);

					OrphanReferencesDataCleanupUtil.cleanUpSameTable(
						StringBundler.concat(
							"classNameId = (select classNameId from ",
							"ClassName_ where value = '",
							Layout.class.getName(), "')"),
						connection, dbInspector.normalizeName("classPK"),
						dbInspector.normalizeName("Layout"),
						dbInspector.normalizeName("plid"));
				}

			});

		upgrade(
			new DefaultAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"plid", "Layout"));
		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				StringBundler.concat(
					"classNameId in (select classNameId from ClassName_ where ",
					"value = '", Layout.class.getName(), "' or value like '",
					Layout.class.getName(), "-%')"),
				new String[] {"classNameId"}, "classPK", new String[] {"plid"},
				"Layout"));

		// Then SegmentsExperience related data

		upgrade(
			new DefaultAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"segmentsExperienceId", "SegmentsExperience"));

		// Finally delete all DDMTemplate and DDM Structure related data

		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"classNameId = (select classNameId from ClassName_ where " +
					"value = 'com.liferay.dynamic.data.mapping.model." +
						"DDMTemplate')",
				new String[] {"classNameId"}, "classPK",
				new String[] {"templateId"}, "DDMTemplate"));

		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"classNameId = (select classNameId from ClassName_ where " +
					"value = 'com.liferay.dynamic.data.mapping.model." +
						"DDMStructure')",
				new String[] {"classNameId"}, "classPK",
				new String[] {"structureId"}, "DDMStructure"));

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureId", "DDMStructureVersion", "structureId",
				"DDMStructure"));

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureVersionId", "DDMField", "structureVersionId",
				"DDMStructureVersion"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "structureVersionId", "DDMStructureLayout",
				"structureVersionId", "DDMStructureVersion"));
	}

}