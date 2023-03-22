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

package com.liferay.journal.internal.upgrade.v5_1_1;

import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Lourdes Fernández Besada
 */
public class JournalArticleAssetEntryClassTypeIdUpgradeProcess
	extends UpgradeProcess {

	public JournalArticleAssetEntryClassTypeIdUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				StringBundler.concat(
					"select distinct AssetEntry.entryId, ",
					"JournalArticle.DDMStructureId from AssetEntry, ",
					"JournalArticle where AssetEntry.classNameId = ",
					classNameId, " and AssetEntry.classPK in ",
					"(JournalArticle.resourcePrimKey, JournalArticle.id_) and ",
					" AssetEntry.classTypeId != JournalArticle.DDMStructureId"),
				"update AssetEntry set classTypeId = ? where entryId = ?",
				resultSet -> new Object[] {
					resultSet.getLong(1), resultSet.getLong(2),
					resultSet.getLong(3)
				},
				(values, preparedStatement) -> {
					Long entryId = (Long)values[0];

					Long ddmStructureId = (Long)values[1];

					preparedStatement.setLong(1, ddmStructureId);

					preparedStatement.setLong(2, entryId);

					preparedStatement.addBatch();
				},
				"Unable to set asset entry classTypeId");
		}
	}

	private final ClassNameLocalService _classNameLocalService;

}