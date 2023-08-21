/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.upgrade.v2_8_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.constants.SegmentsExperimentConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcos Martins
 */
public class SegmentsExperimentUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_deleteSegmentsExperiments();
	}

	private void _deleteSegmentsExperiences(List<Long> segmentsExperienceIds)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("delete from SegmentsExperimentRel where ");
		sb.append("segmentsExperimentRelId in (");
		sb.append(StringUtil.merge(segmentsExperienceIds));
		sb.append(") and active is true");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.execute();
		}
	}

	private void _deleteSegmentsExperimentRels(List<Long> experimentIds)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("select segmentsExperimentRelId, segmentsExperienceId from ");
		sb.append("SegmentsExperimentRel where segmentsExperimentId in (");
		sb.append(StringUtil.merge(experimentIds));
		sb.append(")");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb.toString());
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"delete from SegmentsExperimentRel where " +
					"segmentsExperimentRelId = ?")) {

			List<Long> segmentsExperienceIds = new ArrayList<>();

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setLong(
						1, resultSet.getLong("segmentsExperimentRelId"));

					preparedStatement2.addBatch();

					segmentsExperienceIds.add(
						resultSet.getLong("segmentsExperienceId"));
				}

				if (!segmentsExperienceIds.isEmpty()) {
					preparedStatement2.executeBatch();

					_deleteSegmentsExperiences(segmentsExperienceIds);
				}
			}
		}
	}

	private void _deleteSegmentsExperiments() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select plid from SegmentsExperiment group by plid")) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long plid = resultSet.getLong("plid");

					_deleteSegmentsExperiments(plid);
				}
			}
		}
	}

	private void _deleteSegmentsExperiments(long plid) throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select segmentsExperimentId, status from SegmentsExperiment " +
					"where plid = ? order by createDate desc");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"delete from SegmentsExperiment where segmentsExperimentId = " +
					"?")) {

			preparedStatement1.setLong(1, plid);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				List<Long> experimentIds = new ArrayList<>();

				boolean first = true;

				while (resultSet.next()) {
					if (first) {
						first = false;

						continue;
					}

					if (resultSet.getInt("status") !=
							SegmentsExperimentConstants.STATUS_TERMINATED) {

						continue;
					}

					long segmentsExperimentId = resultSet.getLong(
						"segmentsExperimentId");

					preparedStatement2.setLong(1, segmentsExperimentId);

					preparedStatement2.addBatch();

					experimentIds.add(segmentsExperimentId);
				}

				if (!experimentIds.isEmpty()) {
					preparedStatement2.executeBatch();

					_deleteSegmentsExperimentRels(experimentIds);
				}
			}
		}
	}

}