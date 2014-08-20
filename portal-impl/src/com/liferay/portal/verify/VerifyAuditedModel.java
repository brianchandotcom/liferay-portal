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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 * @author Shinn Lok
 */
public class VerifyAuditedModel extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<String> pendingModels = new ArrayList<String>();

		for (VerifiableAuditedModel verifiableAuditedModel :
				_verifiableAuditedModels) {

			pendingModels.add(verifiableAuditedModel.getModelName());
		}

		while (!pendingModels.isEmpty()) {
			int count = pendingModels.size();

			for (VerifiableAuditedModel verifiableAuditedModel :
					_verifiableAuditedModels) {

				String joinByColumnName =
					verifiableAuditedModel.getJoinByColumnName();
				String modelName = verifiableAuditedModel.getModelName();

				if (pendingModels.contains(joinByColumnName) ||
					!pendingModels.contains(modelName)) {

					continue;
				}

				verifyModel(verifiableAuditedModel);

				pendingModels.remove(modelName);
			}

			if (pendingModels.size() == count) {
				throw new VerifyException(
					"Circular dependency detected " + pendingModels);
			}
		}
	}

	protected Object[] getDefaultUserArray(Connection con, long companyId)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
				"select userId, firstName, middleName, lastName from User_" +
					" where companyId = ? and defaultUser = ?");

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			if (rs.next()) {
				long userId = rs.getLong("userId");
				String firstName = rs.getString("firstName");
				String middleName = rs.getString("middleName");
				String lastName = rs.getString("lastName");

				FullNameGenerator fullNameGenerator =
					FullNameGeneratorFactory.getInstance();

				String userName = fullNameGenerator.getFullName(
					firstName, middleName, lastName);

				Timestamp createDate = new Timestamp(
					System.currentTimeMillis());

				return new Object[] {
					companyId, userId, userName, createDate, createDate
				};
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	protected Object[] getModelArray(
			String modelName, String pkColumnName, long primKey)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select companyId, userId, createDate, modifiedDate from " +
					modelName + " where " + pkColumnName + " = ?");

			ps.setLong(1, primKey);

			rs = ps.executeQuery();

			if (rs.next()) {
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");

				return new Object[] {
					companyId, userId, getUserName(userId), createDate,
					modifiedDate
				};
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find " + modelName + StringPool.SPACE + primKey);
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String getUserName(long userId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select firstName, middleName, lastName from User_ where " +
					"userId = ?");

			ps.setLong(1, userId);

			rs = ps.executeQuery();

			if (rs.next()) {
				String firstName = rs.getString("firstName");
				String middleName = rs.getString("middleName");
				String lastName = rs.getString("lastName");

				FullNameGenerator fullNameGenerator =
					FullNameGeneratorFactory.getInstance();

				return fullNameGenerator.getFullName(
					firstName, middleName, lastName);
			}

			return StringPool.BLANK;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void verifyModel(
			String modelName, String pkColumnName, long primKey,
			Object[] modelArray, boolean updateDates)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			long companyId = (Long)modelArray[0];

			if (modelArray[2] == null) {
				modelArray = getDefaultUserArray(con, companyId);

				if (modelArray == null) {
					return;
				}
			}

			long userId = (Long)modelArray[1];
			String userName = (String)modelArray[2];
			Timestamp createDate = (Timestamp)modelArray[3];
			Timestamp modifiedDate = (Timestamp)modelArray[4];

			StringBundler sb = new StringBundler(7);

			sb.append("update ");
			sb.append(modelName);
			sb.append(" set companyId = ?, userId = ?, userName = ?");

			if (updateDates) {
				sb.append(", createDate = ?, modifiedDate = ?");
			}

			sb.append(" where ");
			sb.append(pkColumnName);
			sb.append(" = ?");

			ps = con.prepareStatement(sb.toString());

			ps.setLong(1, companyId);
			ps.setLong(2, userId);
			ps.setString(3, userName);

			if (updateDates) {
				ps.setTimestamp(4, createDate);
				ps.setTimestamp(5, modifiedDate);
				ps.setLong(6, primKey);
			}
			else {
				ps.setLong(4, primKey);
			}

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to verify model " + modelName, e);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void verifyModel(VerifiableAuditedModel verifiableAuditedModel)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(8);

			String pkColumnName = verifiableAuditedModel.getPkColumnName();

			sb.append("select ");
			sb.append(pkColumnName);
			sb.append(", companyId");

			String joinByColumnName =
				verifiableAuditedModel.getJoinByColumnName();

			if (joinByColumnName != null) {
				sb.append(StringPool.COMMA_AND_SPACE);
				sb.append(joinByColumnName);
			}

			String modelName = verifiableAuditedModel.getModelName();

			sb.append(" from ");
			sb.append(modelName);
			sb.append(" where userName is null order by companyId");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			Object[] modelArray = null;

			long previousCompanyId = 0;

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long primKey = rs.getLong(pkColumnName);

				if (joinByColumnName != null) {
					long relatedPrimKey = rs.getLong(joinByColumnName);

					modelArray = getModelArray(
						verifiableAuditedModel.getRelatedModelName(),
						verifiableAuditedModel.getRelatedPKColumnName(),
						relatedPrimKey);
				}
				else if (previousCompanyId != companyId) {
					modelArray = getDefaultUserArray(con, companyId);

					previousCompanyId = companyId;
				}

				if (modelArray == null) {
					continue;
				}

				verifyModel(
					modelName, pkColumnName, primKey, modelArray,
					verifiableAuditedModel.isUpdateDates());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyAuditedModel.class);

	private ServiceTrackerList<VerifiableAuditedModel>
		_verifiableAuditedModels = ServiceTrackerCollections.list(
			VerifiableAuditedModel.class);

}