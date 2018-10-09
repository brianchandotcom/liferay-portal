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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.workflow.kaleo.definition.NodeType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class UpgradeKaleoTaskInstanceToken extends UpgradeProcess {

	protected void deleteKaleoInstanceTokens() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (_kaleoInstanceTokenIds.isEmpty()) {
				return;
			}

			StringBundler sb = new StringBundler(
				_kaleoInstanceTokenIds.size() * 4 + 1);

			sb.append("delete from KaleoInstanceToken where ");

			for (long kaleoInstanceTokenId : _kaleoInstanceTokenIds) {
				sb.append("(kaleoInstanceTokenId = ");
				sb.append(kaleoInstanceTokenId);
				sb.append(StringPool.CLOSE_PARENTHESIS);
				sb.append(" OR ");
			}

			sb.setIndex(sb.index() - 1);

			String sql = sb.toString();

			runSQL(sql);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateKaleoTaskInstanceTokens();

		deleteKaleoInstanceTokens();
	}

	protected long getKaleoInstanceTokenId(long kaleoInstanceTokenId)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		sb.append("select KaleoNode.type_, ");
		sb.append("KaleoInstanceToken.kaleoInstanceTokenId from KaleoNode ");
		sb.append("inner join KaleoInstanceToken on (KaleoNode.kaleoNodeId = ");
		sb.append("KaleoInstanceToken.currentKaleoNodeId) where ");
		sb.append("KaleoInstanceToken.kaleoInstanceTokenId = (select ");
		sb.append("parentKaleoInstanceTokenId from KaleoInstanceToken where ");
		sb.append("kaleoInstanceTokenId = ?)");

		String sql = sb.toString();

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, kaleoInstanceTokenId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String type = rs.getString("type_");

					if (!type.equals(NodeType.TASK.toString())) {
						return kaleoInstanceTokenId;
					}

					long parentKaleoInstanceTokenId = rs.getLong(
						"kaleoInstanceTokenId");

					_kaleoInstanceTokenIds.add(kaleoInstanceTokenId);

					return getKaleoInstanceTokenId(parentKaleoInstanceTokenId);
				}

				return kaleoInstanceTokenId;
			}
		}
	}

	protected void updateKaleoTaskInstanceTokens() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select kaleoTaskInstanceTokenId, kaleoInstanceTokenId from " +
					"KaleoTaskInstanceToken");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long oldKaleoInstanceTokenId = rs.getLong(
					"kaleoInstanceTokenId");

				long newKaleoInstanceTokenId = getKaleoInstanceTokenId(
					oldKaleoInstanceTokenId);

				if (oldKaleoInstanceTokenId == newKaleoInstanceTokenId) {
					continue;
				}

				StringBundler sb = new StringBundler(5);

				sb.append("update KaleoTaskInstanceToken set ");
				sb.append("kaleoInstanceTokenId = ");
				sb.append(newKaleoInstanceTokenId);
				sb.append(" where kaleoTaskInstanceTokenId = ");

				long kaleoTaskInstanceTokenId = rs.getLong(
					"kaleoTaskInstanceTokenId");

				sb.append(kaleoTaskInstanceTokenId);

				String sql = sb.toString();

				runSQL(sql);
			}
		}
	}

	private final Set<Long> _kaleoInstanceTokenIds = new HashSet<>();

}