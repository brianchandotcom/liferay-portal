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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.concurrent.ThrowableAwareRunnable;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.verify.model.VerifiableAuditedModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Shinn Lok
 */
public class VerifyAuditedModel extends VerifyProcess {

	public void verify(VerifiableAuditedModel... verifiableAuditedModels)
		throws Exception {

		List<String> unverifiedTableNames = new ArrayList<>();

		for (VerifiableAuditedModel verifiableAuditedModel :
				verifiableAuditedModels) {

			unverifiedTableNames.add(verifiableAuditedModel.getTableName());
		}

		List<VerifyAuditedModelRunnable> verifyAuditedModelRunnables =
			new ArrayList<>(unverifiedTableNames.size());

		while (!unverifiedTableNames.isEmpty()) {
			int count = unverifiedTableNames.size();

			for (VerifiableAuditedModel verifiableAuditedModel :
					verifiableAuditedModels) {

				if (unverifiedTableNames.contains(
						verifiableAuditedModel.getJoinByTableName()) ||
					!unverifiedTableNames.contains(
						verifiableAuditedModel.getTableName())) {

					continue;
				}

				VerifyAuditedModelRunnable verifyAuditedModelRunnable =
					new VerifyAuditedModelRunnable(verifiableAuditedModel);

				verifyAuditedModelRunnables.add(verifyAuditedModelRunnable);

				unverifiedTableNames.remove(
					verifiableAuditedModel.getTableName());
			}

			if (unverifiedTableNames.size() == count) {
				throw new VerifyException(
					"Circular dependency detected " + unverifiedTableNames);
			}
		}

		doVerify(verifyAuditedModelRunnables);
	}

	@Override
	protected void doVerify() throws Exception {
		Map<String, VerifiableAuditedModel> verifiableAuditedModelsMap =
			PortalBeanLocatorUtil.locate(VerifiableAuditedModel.class);

		Collection<VerifiableAuditedModel> verifiableAuditedModels =
			verifiableAuditedModelsMap.values();

		verify(
			verifiableAuditedModels.toArray(
				new VerifiableAuditedModel[verifiableAuditedModels.size()]));
	}

	protected Object[] getAuditedModelArray(
			Connection con, String tableName, String pkColumnName, long primKey,
			boolean allowAnonymousUser, long previousUserId)
		throws Exception {

		try (PreparedStatement ps = con.prepareStatement(
				"select companyId, userId, createDate, modifiedDate from " +
					tableName + " where " + pkColumnName + " = ?")) {

			ps.setLong(1, primKey);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					long companyId = rs.getLong("companyId");

					long userId = 0;
					String userName = null;

					if (allowAnonymousUser) {
						userId = previousUserId;
						userName = "Anonymous";
					}
					else {
						userId = rs.getLong("userId");

						userName = getUserName(con, userId);
					}

					Timestamp createDate = rs.getTimestamp("createDate");
					Timestamp modifiedDate = rs.getTimestamp("modifiedDate");

					return new Object[] {
						companyId, userId, userName, createDate, modifiedDate
					};
				}

				if (_log.isDebugEnabled()) {
					_log.debug("Unable to find " + tableName + " " + primKey);
				}

				return null;
			}
		}
	}

	protected Object[] getDefaultUserArray(Connection con, long companyId)
		throws Exception {

		try (PreparedStatement ps = con.prepareStatement(
				"select userId, firstName, middleName, lastName from User_ " +
					"where companyId = ? and defaultUser = ?")) {

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			try (ResultSet rs = ps.executeQuery()) {
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
		}
	}

	protected String getUserName(Connection con, long userId) throws Exception {
		try (PreparedStatement ps = con.prepareStatement(
				"select firstName, middleName, lastName from User_ where " +
					"userId = ?")) {

			ps.setLong(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
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
		}
	}

	protected void verifyAuditedModel(
			Connection con, PreparedStatement ps, String tableName,
			long primKey, Object[] auditedModelArray, boolean updateDates)
		throws Exception {

		try {
			long companyId = (Long)auditedModelArray[0];

			if (auditedModelArray[2] == null) {
				auditedModelArray = getDefaultUserArray(con, companyId);

				if (auditedModelArray == null) {
					return;
				}
			}

			long userId = (Long)auditedModelArray[1];
			String userName = (String)auditedModelArray[2];
			Timestamp createDate = (Timestamp)auditedModelArray[3];
			Timestamp modifiedDate = (Timestamp)auditedModelArray[4];

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

			ps.addBatch();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to verify model " + tableName, e);
			}
		}
	}

	protected void verifyAuditedModel(
			VerifiableAuditedModel verifiableAuditedModel)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(
				verifiableAuditedModel.getTableName())) {

			StringBundler sb = new StringBundler(8);

			sb.append("select ");
			sb.append(verifiableAuditedModel.getPrimaryKeyColumnName());
			sb.append(", companyId, userId");

			if (verifiableAuditedModel.getJoinByTableName() != null) {
				sb.append(StringPool.COMMA_AND_SPACE);
				sb.append(verifiableAuditedModel.getJoinByTableName());
			}

			sb.append(" from ");
			sb.append(verifiableAuditedModel.getTableName());
			sb.append(" where userName is null order by companyId");

			Object[] auditedModelArray = null;

			long previousCompanyId = 0;

			try (Connection con = DataAccess.getUpgradeOptimizedConnection();
				PreparedStatement ps1 = con.prepareStatement(sb.toString());
				ResultSet rs = ps1.executeQuery();
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						_createPreparedStatement(
							con, verifiableAuditedModel.getTableName(),
							verifiableAuditedModel.getPrimaryKeyColumnName(),
							verifiableAuditedModel.isUpdateDates()))) {

				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					long primKey = rs.getLong(
						verifiableAuditedModel.getPrimaryKeyColumnName());
					long previousUserId = rs.getLong("userId");

					if (verifiableAuditedModel.getJoinByTableName()
							!= null) {

						long relatedPrimKey = rs.getLong(
							verifiableAuditedModel.getJoinByTableName());

						auditedModelArray = getAuditedModelArray(
							con, verifiableAuditedModel.getRelatedModelName(),
							verifiableAuditedModel.getRelatedPKColumnName(),
							relatedPrimKey,
							verifiableAuditedModel.isAnonymousUserAllowed(),
							previousUserId);
					}
					else if (previousCompanyId != companyId) {
						auditedModelArray = getDefaultUserArray(con, companyId);

						previousCompanyId = companyId;
					}

					if (auditedModelArray == null) {
						continue;
					}

					verifyAuditedModel(
						con, ps2, verifiableAuditedModel.getTableName(),
						primKey, auditedModelArray,
						verifiableAuditedModel.isUpdateDates());
				}

				ps2.executeBatch();
			}
		}
	}

	private PreparedStatement _createPreparedStatement(
			Connection con, String tableName, String primaryKeyColumnName,
			boolean updateDates)
		throws SQLException {

		StringBundler sb = new StringBundler(7);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set companyId = ?, userId = ?, userName = ?");

		if (updateDates) {
			sb.append(", createDate = ?, modifiedDate = ?");
		}

		sb.append(" where ");
		sb.append(primaryKeyColumnName);
		sb.append(" = ?");

		return con.prepareStatement(sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyAuditedModel.class);

	private class VerifyAuditedModelRunnable extends ThrowableAwareRunnable {

		public VerifyAuditedModelRunnable(
			VerifiableAuditedModel verifiableAuditedModel) {

			_verifiableAuditedModel = verifiableAuditedModel;
		}

		@Override
		protected void doRun() throws Exception {
			verifyAuditedModel(_verifiableAuditedModel);
		}

		private final VerifiableAuditedModel _verifiableAuditedModel;

	}

}