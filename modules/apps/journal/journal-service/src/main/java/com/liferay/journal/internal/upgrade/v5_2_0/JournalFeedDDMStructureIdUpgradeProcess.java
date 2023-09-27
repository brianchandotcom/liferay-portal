/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v5_2_0;

import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lourdes Fernández Besada
 */
public class JournalFeedDDMStructureIdUpgradeProcess extends UpgradeProcess {

	public JournalFeedDDMStructureIdUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		SiteConnectedGroupGroupProvider siteConnectedGroupGroupProvider) {

		_classNameLocalService = classNameLocalService;
		_siteConnectedGroupGroupProvider = siteConnectedGroupGroupProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, long[]> ancestorSiteAndDepotGroupIdsMap =
			new ConcurrentHashMap<>();
		Map<String, Map<Long, Long>> ddmStructureIdsMap =
			new ConcurrentHashMap<>();
		long journalArticleClassNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				"select distinct groupId, DDMStructureKey from JournalFeed " +
					"where DDMStructureKey is not null",
				"update JournalFeed set DDMStructureId = ? where groupId = ? " +
					"and DDMStructureKey = ?",
				resultSet -> new Object[] {
					resultSet.getLong(1),
					GetterUtil.getString(resultSet.getString(2))
				},
				(values, preparedStatement) -> {
					long groupId = (Long)values[0];

					String ddmStructureKey = (String)values[1];

					Map<Long, Long> ddmStructureIds =
						ddmStructureIdsMap.computeIfAbsent(
							ddmStructureKey, key -> new ConcurrentHashMap<>());

					Long ddmStructureId = ddmStructureIds.get(groupId);

					if (ddmStructureId == null) {
						ddmStructureId = _getDDMStructureId(
							ancestorSiteAndDepotGroupIdsMap, ddmStructureKey,
							ddmStructureIds, journalArticleClassNameId,
							groupId);

						ddmStructureIds.put(groupId, ddmStructureId);
					}

					if (ddmStructureId == 0L) {
						return;
					}

					preparedStatement.setLong(1, ddmStructureId);
					preparedStatement.setLong(2, groupId);
					preparedStatement.setString(3, ddmStructureKey);

					preparedStatement.addBatch();
				},
				"Unable to set journal feed dynamic data mapping structure ID");
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"JournalFeed", "DDMStructureId LONG")
		};
	}

	private Long _getDDMStructureId(
			Map<Long, long[]> ancestorSiteAndDepotGroupIdsMap,
			String ddmStructureKey, Map<Long, Long> groupIdsMap,
			long journalArticleClassNameId, Long siteGroupId)
		throws PortalException, SQLException {

		Long ddmStructureId = _getDDMStructureId(
			ddmStructureKey, siteGroupId, journalArticleClassNameId);

		if (ddmStructureId != 0L) {
			return ddmStructureId;
		}

		long[] ancestorSiteAndDepotGroupIds =
			ancestorSiteAndDepotGroupIdsMap.get(siteGroupId);

		if (ancestorSiteAndDepotGroupIds == null) {
			ancestorSiteAndDepotGroupIds =
				_siteConnectedGroupGroupProvider.
					getAncestorSiteAndDepotGroupIds(siteGroupId, true);

			ancestorSiteAndDepotGroupIdsMap.put(
				siteGroupId, ancestorSiteAndDepotGroupIds);
		}

		for (long ancestorSiteAndDepotGroupId : ancestorSiteAndDepotGroupIds) {
			ddmStructureId = groupIdsMap.get(ancestorSiteAndDepotGroupId);

			if (ddmStructureId == null) {
				ddmStructureId = _getDDMStructureId(
					ddmStructureKey, ancestorSiteAndDepotGroupId,
					journalArticleClassNameId);
			}

			if (ddmStructureId == 0L) {
				continue;
			}

			groupIdsMap.put(ancestorSiteAndDepotGroupId, ddmStructureId);

			return ddmStructureId;
		}

		if ((ddmStructureId == 0L) && _log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"Unable to find structure for ddmStructureKey ",
					ddmStructureKey, " siteGroupId ", siteGroupId,
					" ancestorSiteAndDepotGroupIds ",
					Arrays.toString(ancestorSiteAndDepotGroupIds)));
		}

		return ddmStructureId;
	}

	private long _getDDMStructureId(
			String ddmStructureKey, long groupId,
			long journalArticleClassNameId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select structureId from DDMStructure where groupId = ? and " +
					"classNameId = ? and structureKey = ?")) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, journalArticleClassNameId);
			preparedStatement.setString(3, ddmStructureKey);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("structureId");
				}
			}
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalFeedDDMStructureIdUpgradeProcess.class);

	private final ClassNameLocalService _classNameLocalService;
	private final SiteConnectedGroupGroupProvider
		_siteConnectedGroupGroupProvider;

}