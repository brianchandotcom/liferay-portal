/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.upgrade.v3_2_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Eudaldo Alonso
 */
public class SegmentsExperienceUpgradeProcess extends UpgradeProcess {

	public SegmentsExperienceUpgradeProcess(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select * from SegmentsExperience");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into SegmentsExperience (mvccVersion, ",
						"ctCollectionId, uuid_, externalReferenceCode, ",
						"segmentsExperienceId, groupId, companyId, userId, ",
						"userName, createDate, modifiedDate, segmentsEntryId, ",
						"segmentsExperienceKey, plid, name, priority, ",
						"active_, typeSettings, lastPublishDate) values (?, ",
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
						"?)"));
			 PreparedStatement preparedStatement3 =
				 AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					 connection,
					 	"update FragmentEntryLink set segmentsExperienceId = " +
							"? where segmentsExperienceId = ? and plid = ?");
			 PreparedStatement preparedStatement4 =
				 AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					 connection,
					 StringBundler.concat(
						 "update LayoutPageTemplateStructureRel set ",
						 "segmentsExperienceId = ? where segmentsExperienceId ",
						 "= ? and LayoutPageTemplateStructureId in (select ",
						 "LayoutPageTemplateStructureId from ",
						 "LayoutPageTemplateStructure where plid = ?)"));

			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");
				String segmentsExperienceKey = resultSet.getString(
					"segmentsExperienceKey");

				Layout layout = _layoutLocalService.fetchLayout(
					resultSet.getLong("plid"));

				Layout draftLayout = layout.fetchDraftLayout();

				if ((draftLayout == null) ||
					_existDraftLayoutSegmentsExperience(
						groupId, segmentsExperienceKey,
						draftLayout.getPlid())) {

					continue;
				}

				preparedStatement2.setLong(1, 0);
				preparedStatement2.setLong(
					2, resultSet.getLong("ctCollectionId"));

				String uuid = PortalUUIDUtil.generate();

				preparedStatement2.setString(3, uuid);
				preparedStatement2.setString(4, uuid);

				long draftLayoutSegmentsExperienceId = increment();

				preparedStatement2.setLong(5, draftLayoutSegmentsExperienceId);

				preparedStatement2.setLong(6, groupId);
				preparedStatement2.setLong(7, resultSet.getLong("companyId"));
				preparedStatement2.setLong(8, resultSet.getLong("userId"));
				preparedStatement2.setString(
					9, resultSet.getString("userName"));
				preparedStatement2.setDate(10, resultSet.getDate("createDate"));
				preparedStatement2.setDate(
					11, resultSet.getDate("modifiedDate"));
				preparedStatement2.setLong(
					12, resultSet.getLong("segmentsEntryId"));
				preparedStatement2.setString(13, segmentsExperienceKey);
				preparedStatement2.setLong(14, draftLayout.getPlid());
				preparedStatement2.setString(15, resultSet.getString("name"));
				preparedStatement2.setLong(16, resultSet.getInt("priority"));
				preparedStatement2.setBoolean(
					17, resultSet.getBoolean("active_"));
				preparedStatement2.setString(
					18, resultSet.getString("typeSettings"));
				preparedStatement2.setDate(
					19, resultSet.getDate("lastPublishDate"));

				preparedStatement2.addBatch();

				long segmentsExperienceId = resultSet.getLong(
					"segmentsExperienceId");

				preparedStatement3.setLong(1, draftLayoutSegmentsExperienceId);
				preparedStatement3.setLong(2, segmentsExperienceId);
				preparedStatement3.setLong(3, draftLayout.getPlid());

				preparedStatement3.addBatch();

				preparedStatement4.setLong(1, draftLayoutSegmentsExperienceId);
				preparedStatement4.setLong(2, segmentsExperienceId);
				preparedStatement4.setLong(3, draftLayout.getPlid());

				preparedStatement4.addBatch();
			}

			preparedStatement2.executeBatch();
			preparedStatement3.executeBatch();
			preparedStatement4.executeBatch();
		}
	}

	private boolean _existDraftLayoutSegmentsExperience(
			long groupId, String segmentsExperienceKey, long plid)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from SegmentsExperience where groupId = ? " +
					"and segmentsExperienceKey = ? and plid = ?")) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setString(2, segmentsExperienceKey);
			preparedStatement.setLong(3, plid);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	private final LayoutLocalService _layoutLocalService;

}