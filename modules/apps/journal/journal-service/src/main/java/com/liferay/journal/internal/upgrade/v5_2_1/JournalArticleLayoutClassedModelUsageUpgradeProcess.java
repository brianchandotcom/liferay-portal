/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v5_2_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleLayoutClassedModelUsageUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String sql1 = StringBundler.concat(
				"select distinct ",
				"LayoutClassedModelUsage.layoutClassedModelUsageId, ",
				"LayoutClassedModelUsage.classNameId, ",
				"LayoutClassedModelUsage.classPK, ",
				"LayoutClassedModelUsage.containerKey, ",
				"LayoutClassedModelUsage.containerType, LayoutRevision.plid ",
				"from LayoutClassedModelUsage inner join LayoutRevision on ",
				"LayoutRevision.layoutRevisionId = ",
				"LayoutClassedModelUsage.plid");
			String sql2 = StringBundler.concat(
				"select 1 from LayoutClassedModelUsage where classNameId = ? ",
				"and classPK = ? and containerKey = ? and containerType = ? ",
				"and plid = ?");

			processConcurrently(
				sql1,
				"update LayoutClassedModelUsage set plid = ? where " +
					"layoutClassedModelUsageId = ?",
				resultSet -> new Object[] {
					resultSet.getLong("layoutClassedModelUsageId"),
					resultSet.getLong("classNameId"),
					resultSet.getLong("classPK"),
					GetterUtil.getString(resultSet.getString("containerKey")),
					resultSet.getLong("containerType"),
					resultSet.getLong("plid")
				},
				(values, preparedStatement1) -> {
					long layoutClassedModelUsageId = (Long)values[0];

					long classNameId = (Long)values[1];
					long classPK = (Long)values[2];
					String containerKey = (String)values[3];
					long containerType = (Long)values[4];
					long plid = (Long)values[5];

					try (PreparedStatement preparedStatement2 =
							connection.prepareStatement(sql2)) {

						preparedStatement2.setLong(1, classNameId);
						preparedStatement2.setLong(2, classPK);
						preparedStatement2.setString(3, containerKey);
						preparedStatement2.setLong(4, containerType);
						preparedStatement2.setLong(5, plid);

						try (ResultSet resultSet =
								preparedStatement2.executeQuery()) {

							if (resultSet.next()) {
								runSQL(
									"delete from LayoutClassedModelUsage " +
										"where layoutClassedModelUsageId = " +
											layoutClassedModelUsageId);
							}
							else {
								preparedStatement1.setLong(1, plid);
								preparedStatement1.setLong(
									2, layoutClassedModelUsageId);

								preparedStatement1.addBatch();
							}
						}
					}
				},
				"Unable update layout classed model usages");
		}
	}

}