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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.verify.model.grouped.VerifiableGroupedModel;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shinn Lok
 */
public class VerifyGroupId extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<String> unverifiableTableName = new ArrayList<String>();

		for (VerifiableGroupedModel verifiableGroupedModel :
				_verifiableGroupedModels) {

			unverifiableTableName.add(verifiableGroupedModel.getTableName());
		}

		while (!unverifiableTableName.isEmpty()) {
			int count = unverifiableTableName.size();

			for (VerifiableGroupedModel verifiableGroupedModel :
					_verifiableGroupedModels) {

				if (unverifiableTableName.contains(
						verifiableGroupedModel.getRelatedModelName()) ||
					!unverifiableTableName.contains(
						verifiableGroupedModel.getTableName())) {

					continue;
				}

				verifyModel(verifiableGroupedModel);

				unverifiableTableName.remove(
					verifiableGroupedModel.getTableName());
			}

			if (unverifiableTableName.size() == count) {
				throw new VerifyException(
					"Circular dependency detected " + unverifiableTableName);
			}
		}
	}

	protected long getGroupId(
			String modelName, String pkColumnName, long primKey)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId from " + modelName + " where " + pkColumnName +
					" = ?");

			ps.setLong(1, primKey);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("groupId");
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find " + modelName + StringPool.SPACE + primKey);
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void verifyModel(VerifiableGroupedModel verifiableGroupedModel)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			String tableName = verifiableGroupedModel.getTableName();
			String pkColumnName =
				verifiableGroupedModel.getPrimaryKeyColumnName();
			String relatedPKColumnName =
				verifiableGroupedModel.getRelatedPKColumnName();

			ps = con.prepareStatement(
				"select " + pkColumnName + StringPool.COMMA_AND_SPACE +
					relatedPKColumnName + " from " + tableName + " where " +
						"groupId is null");

			rs = ps.executeQuery();

			while (rs.next()) {
				long primKey = rs.getLong(pkColumnName);
				long relatedPrimKey = rs.getLong(relatedPKColumnName);

				long groupId = getGroupId(
					verifiableGroupedModel.getRelatedModelName(),
					relatedPKColumnName, relatedPrimKey);

				if (groupId <= 0) {
					continue;
				}

				runSQL(
					"update " + tableName + " set groupId = " + groupId +
						" where " + pkColumnName + " = " + primKey);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyGroupId.class);

	private ServiceTrackerList<VerifiableGroupedModel>
		_verifiableGroupedModels = ServiceTrackerCollections.list(
			VerifiableGroupedModel.class);

}