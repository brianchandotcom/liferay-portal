/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.upgrade.v4_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Georgel Pop
 */
public class SegmentsExperienceUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String sql = StringBundler.concat(
			"select SegmentsExperience.ctCollectionId, ",
			"SegmentsExperience.segmentsExperienceId, ",
			"SegmentsExperience.groupId, SegmentsEntry.externalReferenceCode, ",
			"SegmentsEntry.groupId, Group_.externalReferenceCode from ",
			"SegmentsExperience inner join SegmentsEntry on ",
			"(SegmentsEntry.ctCollectionId = ",
			"SegmentsExperience.ctCollectionId or ",
			"SegmentsEntry.ctCollectionId = 0) and ",
			"SegmentsExperience.segmentsEntryId = ",
			"SegmentsEntry.segmentsEntryId inner join Group_ on ",
			"(Group_.ctCollectionId = SegmentsExperience.ctCollectionId or ",
			"Group_.ctCollectionId = 0) and SegmentsEntry.groupId = ",
			"Group_.groupId where SegmentsExperience.segmentsEntryId > 0");

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(sql);
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SegmentsExperience set segmentsEntryERC = ?, " +
						"segmentsEntryScopeERC = ? where " +
							"segmentsExperienceId = ? and ctCollectionId = ?");
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long segmentsExperienceGroupId = resultSet.getLong(3);
				String segmentsEntryERC = resultSet.getString(4);
				long segmentsEntryGroupId = resultSet.getLong(5);
				String segmentsEntryScopeERC = resultSet.getString(6);

				if (segmentsExperienceGroupId == segmentsEntryGroupId) {
					segmentsEntryScopeERC = null;
				}

				updatePreparedStatement.setString(1, segmentsEntryERC);
				updatePreparedStatement.setString(2, segmentsEntryScopeERC);

				updatePreparedStatement.setLong(
					3, resultSet.getLong("segmentsExperienceId"));
				updatePreparedStatement.setLong(
					4, resultSet.getLong("ctCollectionId"));

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns(
				"SegmentsExperience", "segmentsEntryId")
		};
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"SegmentsExperience", "segmentsEntryERC VARCHAR(75) null",
				"segmentsEntryScopeERC VARCHAR(75) null")
		};
	}

}