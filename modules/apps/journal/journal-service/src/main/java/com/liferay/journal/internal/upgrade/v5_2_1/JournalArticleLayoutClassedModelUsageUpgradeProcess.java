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
				(values, preparedStatement) -> {
					long layoutClassedModelUsageId = (Long)values[0];

					long classNameId = (Long)values[1];
					long classPK = (Long)values[2];
					String containerKey = (String)values[3];
					long containerType = (Long)values[4];
					long plid = (Long)values[5];

					PreparedStatement preparedStatement1 =
						connection.prepareStatement(sql2);

					preparedStatement1.setLong(1, classNameId);
					preparedStatement1.setLong(2, classPK);
					preparedStatement1.setString(3, containerKey);
					preparedStatement1.setLong(4, containerType);
					preparedStatement1.setLong(5, plid);

					try (ResultSet resultSet =
							preparedStatement1.executeQuery()) {

						if (resultSet.next()) {
							runSQL(
								"delete from LayoutClassedModelUsage where " +
									"layoutClassedModelUsageId = " +
										layoutClassedModelUsageId);
						}
						else {
							preparedStatement.setLong(1, plid);
							preparedStatement.setLong(
								2, layoutClassedModelUsageId);

							preparedStatement.addBatch();
						}
					}
				},
				"Unable update layout classed model usages");
		}
	}

}