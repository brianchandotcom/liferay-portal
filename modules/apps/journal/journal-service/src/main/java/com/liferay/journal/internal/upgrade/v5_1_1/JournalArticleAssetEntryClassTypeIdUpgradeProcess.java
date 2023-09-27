/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v5_1_1;

import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
		Map<Long, Map<Long, List<Long>>> entryIdsMaps =
			new ConcurrentHashMap<>();

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				StringBundler.concat(
					"select distinct AssetEntry.entryId, ",
					"AssetEntry.classTypeId, JournalArticle.DDMStructureId ",
					"from AssetEntry, JournalArticle where ",
					"AssetEntry.classNameId = ", classNameId,
					" and (AssetEntry.classPK = JournalArticle.id_ or ",
					"AssetEntry.classPK = JournalArticle.resourcePrimKey) and ",
					"AssetEntry.classTypeId != JournalArticle.DDMStructureId"),
				"update AssetEntry set classTypeId = ? where entryId = ?",
				resultSet -> new Object[] {
					resultSet.getLong(1), resultSet.getLong(2),
					resultSet.getLong(3)
				},
				(values, preparedStatement) -> {
					Long entryId = (Long)values[0];
					Long classTypeId = (Long)values[1];

					Long ddmStructureId = (Long)values[2];

					preparedStatement.setLong(1, ddmStructureId);

					preparedStatement.setLong(2, entryId);

					preparedStatement.addBatch();

					Map<Long, List<Long>> entryIdsMap =
						entryIdsMaps.computeIfAbsent(
							classTypeId, key -> new ConcurrentHashMap<>());

					List<Long> entryIds = entryIdsMap.computeIfAbsent(
						ddmStructureId, key -> new ArrayList<>());

					entryIds.add(entryId);
				},
				"Unable to set asset entry class type ID");
		}

		if (_log.isDebugEnabled() && entryIdsMaps.isEmpty()) {
			_log.debug(
				"No asset entries with the wrong class type ID were found");
		}

		if (!_log.isWarnEnabled() || entryIdsMaps.isEmpty()) {
			return;
		}

		for (Map.Entry<Long, Map<Long, List<Long>>> entry1 :
				entryIdsMaps.entrySet()) {

			long classTypeId = entry1.getKey();

			_log.warn(
				"Asset entries with the wrong class type ID " + classTypeId +
					" were found");

			Map<Long, List<Long>> entryIdsMap = entry1.getValue();

			for (Map.Entry<Long, List<Long>> entry2 : entryIdsMap.entrySet()) {
				long ddmStructureId = entry2.getKey();
				List<Long> entryIds = entry2.getValue();

				_log.warn(
					ddmStructureId +
						" has been set as class type ID for the entry IDs " +
							entryIds);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleAssetEntryClassTypeIdUpgradeProcess.class);

	private final ClassNameLocalService _classNameLocalService;

}