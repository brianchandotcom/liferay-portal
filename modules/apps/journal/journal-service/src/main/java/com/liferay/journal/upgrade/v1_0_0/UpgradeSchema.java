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

package com.liferay.journal.upgrade.v1_0_0;

import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.upgrade.v1_0_0.util.JournalArticleTable;
import com.liferay.journal.upgrade.v1_0_0.util.JournalFeedTable;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.upgrade.UpgradeMVCCVersion;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;

import java.sql.SQLException;

/**
 * @author Eduardo Garcia
 */
public class UpgradeSchema extends UpgradeProcess {

	public UpgradeSchema(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetEntryLocalService assetEntryLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		CompanyLocalService companyLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		GroupLocalService groupLocalService,
		LayoutLocalService layoutLocalService,
		UserLocalService userLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_assetEntryLocalService = assetEntryLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
		_companyLocalService = companyLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_groupLocalService = groupLocalService;
		_layoutLocalService = layoutLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String template = StringUtil.read(
			UpgradeSchema.class.getResourceAsStream("dependencies/update.sql"));

		runSQLTemplateString(template, false, false);

		upgrade(UpgradeMVCCVersion.class);

		upgradeJournalArticleType();

		try {
			runSQL(
				"alter_column_name JournalArticle structureId " +
					"DDMStructureKey VARCHAR(75) null");
			runSQL(
				"alter_column_name JournalArticle templateId DDMTemplateKey " +
					"VARCHAR(75) null");
			runSQL("alter_column_type JournalArticle description TEXT null");

			runSQL(
				"alter_column_name JournalFeed structureId DDMStructureKey " +
					"TEXT null");
			runSQL(
				"alter_column_name JournalFeed templateId DDMTemplateKey " +
					"TEXT null");
			runSQL(
				"alter_column_name JournalFeed rendererTemplateId " +
					"DDMRendererTemplateKey TEXT null");
		}
		catch (SQLException sqle) {

			// JournalArticle

			upgradeTable(
				JournalArticleTable.TABLE_NAME,
				JournalArticleTable.TABLE_COLUMNS,
				JournalArticleTable.TABLE_SQL_CREATE,
				JournalArticleTable.TABLE_SQL_ADD_INDEXES);

			// JournalFeed

			upgradeTable(
				JournalFeedTable.TABLE_NAME, JournalFeedTable.TABLE_COLUMNS,
				JournalFeedTable.TABLE_SQL_CREATE,
				JournalFeedTable.TABLE_SQL_ADD_INDEXES);
		}
	}

	protected void upgradeJournalArticleType() throws UpgradeException {
		UpgradeJournalArticleType upgradeJournalArticleType =
			new UpgradeJournalArticleType(
				_assetCategoryLocalService, _assetEntryLocalService,
				_assetVocabularyLocalService, _companyLocalService,
				_ddmStructureLocalService, _groupLocalService,
				_layoutLocalService, _userLocalService);

		upgradeJournalArticleType.upgrade();
	}

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetEntryLocalService _assetEntryLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final CompanyLocalService _companyLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final GroupLocalService _groupLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final UserLocalService _userLocalService;

}