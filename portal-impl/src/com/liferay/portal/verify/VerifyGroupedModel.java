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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.verify.model.VerifiableGroupedModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Shinn Lok
 */
public class VerifyGroupedModel extends VerifyProcess {

	public void verify(VerifiableGroupedModel... verifiableGroupedModels)
		throws Exception {

		List<String> unverifiedTableNames = new ArrayList<>();

		for (VerifiableGroupedModel verifiableGroupedModel :
				verifiableGroupedModels) {

			unverifiedTableNames.add(verifiableGroupedModel.getTableName());
		}

		List<VerifiableGroupedModelCallable> verifiableGroupedModelCallables =
			new ArrayList<>(unverifiedTableNames.size());

		while (!unverifiedTableNames.isEmpty()) {
			int count = unverifiedTableNames.size();

			for (VerifiableGroupedModel verifiableGroupedModel :
					verifiableGroupedModels) {

				if (unverifiedTableNames.contains(
						verifiableGroupedModel.getRelatedTableName()) ||
					!unverifiedTableNames.contains(
						verifiableGroupedModel.getTableName())) {

					continue;
				}

				VerifiableGroupedModelCallable verifiableGroupedModelCallable =
					new VerifiableGroupedModelCallable(verifiableGroupedModel);

				verifiableGroupedModelCallables.add(
					verifiableGroupedModelCallable);

				unverifiedTableNames.remove(
					verifiableGroupedModel.getTableName());
			}

			if (unverifiedTableNames.size() == count) {
				throw new VerifyException(
					"Circular dependency detected " + unverifiedTableNames);
			}
		}

		doVerify(verifiableGroupedModelCallables);
	}

	@Override
	protected void doVerify() throws Exception {
		Map<String, VerifiableGroupedModel> verifiableGroupedModelsMap =
			PortalBeanLocatorUtil.locate(VerifiableGroupedModel.class);

		Collection<VerifiableGroupedModel> verifiableGroupedModels =
			verifiableGroupedModelsMap.values();

		verify(verifiableGroupedModels.toArray(new VerifiableGroupedModel[0]));
	}

	protected long getGroupId(
			Connection con, String tableName, String primaryKeColumnName,
			long primKey)
		throws Exception {

		try (PreparedStatement ps = con.prepareStatement(
				StringBundler.concat(
					"select groupId from ", tableName, " where ",
					primaryKeColumnName, " = ?"))) {

			ps.setLong(1, primKey);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("groupId");
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Unable to find ", tableName, " ",
							String.valueOf(primKey)));
				}

				return 0;
			}
		}
	}

	@Override
	protected boolean isForceConcurrent(
		Collection<? extends Callable<Void>> callables) {

		return true;
	}

	protected void verifyGroupedModel(
			VerifiableGroupedModel verifiableGroupedModel)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(
				verifiableGroupedModel.getTableName())) {

			StringBundler sb = new StringBundler(7);

			sb.append("select ");
			sb.append(verifiableGroupedModel.getPrimaryKeyColumnName());
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(verifiableGroupedModel.getRelatedPrimaryKeyColumnName());
			sb.append(" from ");
			sb.append(verifiableGroupedModel.getTableName());
			sb.append(" where groupId is null");

			try (Connection con = DataAccess.getConnection();
				PreparedStatement ps1 = con.prepareStatement(sb.toString());
				ResultSet rs = ps1.executeQuery()) {

				sb = new StringBundler(5);

				sb.append("update ");
				sb.append(verifiableGroupedModel.getTableName());
				sb.append(" set groupId = ? where ");
				sb.append(verifiableGroupedModel.getPrimaryKeyColumnName());
				sb.append(" = ?");

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.autoBatch(
							con.prepareStatement(sb.toString()))) {

					while (rs.next()) {
						long relatedPrimKey = rs.getLong(
							verifiableGroupedModel.
								getRelatedPrimaryKeyColumnName());

						long groupId = getGroupId(
							con, verifiableGroupedModel.getRelatedTableName(),
							verifiableGroupedModel.
								getRelatedPrimaryKeyColumnName(),
							relatedPrimKey);

						if (groupId <= 0) {
							continue;
						}

						ps2.setLong(1, groupId);

						long primKey = rs.getLong(
							verifiableGroupedModel.getPrimaryKeyColumnName());

						ps2.setLong(2, primKey);

						ps2.addBatch();
					}

					ps2.executeBatch();
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyGroupedModel.class);

	private class VerifiableGroupedModelCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			verifyGroupedModel(_verifiableGroupedModel);

			return null;
		}

		private VerifiableGroupedModelCallable(
			VerifiableGroupedModel verifiableGroupedModel) {

			_verifiableGroupedModel = verifiableGroupedModel;
		}

		private final VerifiableGroupedModel _verifiableGroupedModel;

	}

}