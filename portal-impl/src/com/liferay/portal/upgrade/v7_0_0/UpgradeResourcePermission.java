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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sampsa Sohlman
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	protected void createIndex() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQLTemplateString(
				"create index IX_D5F1E2A2 on ResourcePermission " +
					"(name[$COLUMN_LENGTH:255$])",
				false, false);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		createIndex();

		upgradeResourcePermissions();
	}

	protected void upgradeResourcePermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"update ResourcePermission set viewActionId = [$FALSE$] " +
					"where MOD(actionIds, 2) = 0");
			runSQL(
				"update ResourcePermission set viewActionId = [$TRUE$] where " +
					"MOD(actionIds, 2) = 1");

			List<String> stringPrimKeys = new ArrayList<>();

			try (PreparedStatement ps = connection.prepareStatement(
					"select distinct primKey from ResourcePermission");
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					String primKey = rs.getString("primKey");

					long primKeyId = GetterUtil.getLong(primKey);

					if (primKeyId <= 0) {
						stringPrimKeys.add(primKey);
					}
				}
			}

			if (stringPrimKeys.isEmpty()) {
				runSQL(
					"update ResourcePermission set primKeyId = CAST_LONG(" +
						"primKey)");

				return;
			}

			StringBundler sb = new StringBundler(stringPrimKeys.size() + 1);

			sb.append("in (?");

			for (int i = 1; i < stringPrimKeys.size(); i++) {
				sb.append(", ?");
			}

			sb.append(")");

			String inClause = sb.toString();

			_updatePrimKeyIds(
				"update ResourcePermission set primKeyId = 0 where primKey " +
					inClause,
				stringPrimKeys);

			_updatePrimKeyIds(
				"update ResourcePermission set primKeyId = CAST_LONG(primKey" +
					") where primKey not " + inClause,
				stringPrimKeys);
		}
	}

	private void _updatePrimKeyIds(String sql, List<String> primKeys)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				SQLTransformer.transform(sql))) {

			for (int i = 0; i < primKeys.size(); i++) {
				String primKey = primKeys.get(i);

				ps.setString(i + 1, primKey);
			}

			ps.executeUpdate();
		}
	}

}