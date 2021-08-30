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

package com.liferay.message.boards.internal.upgrade.v3_0_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class MBMessageTreePathUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("MBMessage", "treePath")) {
			alterTableAddColumn("MBMessage", "treePath", "STRING null");
		}

		_populateTreePath();
	}

	private String _calculatePath(Map<Long, Long> relations, long messageId) {
		List<String> paths = new ArrayList<>();

		paths.add(messageId + "/");

		while (relations.containsKey(messageId)) {
			messageId = relations.get(messageId);

			paths.add(messageId + "/");
		}

		paths.add("/");

		Collections.reverse(paths);

		return StringUtil.merge(paths, "");
	}

	private void _populateTreePath() throws Exception {
		runSQL(
			"update MBMessage set treePath = CONCAT('/', " +
				"CAST_TEXT(messageId), '/') where parentMessageId = 0");

		runSQL(
			"update MBMessage set treePath = CONCAT('/', " +
				"CAST_TEXT(rootMessageId), '/', CAST_TEXT(messageId), '/') " +
					"where parentMessageId = rootMessageId");

		Map<Long, Long> relations = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select messageId, parentMessageId from MBMessage where " +
					"parentMessageId != 0 order by createDate desc");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				relations.put(resultSet.getLong(1), resultSet.getLong(2));
			}
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select messageId from MBMessage where treePath is null or " +
					"treePath = ''");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update MBMessage set treePath = ? where messageId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long messageId = resultSet.getLong(1);

				preparedStatement2.setString(
					1, _calculatePath(relations, messageId));
				preparedStatement2.setLong(2, messageId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}