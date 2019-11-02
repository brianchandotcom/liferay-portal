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

import com.liferay.message.boards.internal.upgrade.v3_0_0.util.MBMessageTable;
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
public class UpgradeTreePath extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(MBMessageTable.class, new AlterTableAddColumn("treePath STRING"));

		_populateTreePath();
	}

	private String _calculatePath(Map<Long, Long> relations, long messageId) {
		List<String> paths = new ArrayList<>();

		paths.add("/");
		paths.add(String.valueOf(messageId));

		while (relations.containsKey(messageId)) {
			paths.add("/");
			messageId = relations.get(messageId);

			paths.add(String.valueOf(messageId));
		}

		paths.add("/");

		Collections.reverse(paths);

		return StringUtil.merge(paths, "");
	}

	private void _populateTreePath() throws Exception {
		runSQL(
			"update MBMessage set treePath = CONCAT('/', messageId, '/') " +
				"where parentMessageId = 0");

		runSQL(
			"update MBMessage set treePath = CONCAT('/', rootMessageId, '/', " +
				"messageId, '/') where parentMessageId = rootMessageId");

		Map<Long, Long> relations = new HashMap<>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select messageId, parentMessageId from MBMessage where " +
					"parentMessageId != 0 order by createDate desc");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				relations.put(rs.getLong(1), rs.getLong(2));
			}
		}

		try (PreparedStatement ps1 = connection.prepareStatement(
				"select messageId from MBMessage where treePath is null or " +
					"treePath = ''");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update MBMessage set treePath = ? where messageId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long messageId = rs.getLong(1);

				ps2.setString(1, _calculatePath(relations, messageId));
				ps2.setLong(2, messageId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}