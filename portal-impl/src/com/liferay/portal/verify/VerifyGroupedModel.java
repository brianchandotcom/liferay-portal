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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
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

		if (unverifiedTableNames.isEmpty()) {
			return;
		}

		int count = unverifiedTableNames.size();

		processConcurrently(
			verifiableGroupedModels,
			verifiableGroupedModel -> {
				if (unverifiedTableNames.contains(
						verifiableGroupedModel.getRelatedTableName()) ||
					!unverifiedTableNames.contains(
						verifiableGroupedModel.getTableName())) {

					return;
				}

				unverifiedTableNames.remove(
					verifiableGroupedModel.getTableName());

				if (unverifiedTableNames.size() == count) {
					throw new VerifyException(
						"Circular dependency detected " + unverifiedTableNames);
				}

				verifyGroupedModel(verifiableGroupedModel);
			},
			null);
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
			Connection connection, String tableName, String primaryKeColumnName,
			long primKey)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select groupId from ", tableName, " where ",
					primaryKeColumnName, " = ?"))) {

			preparedStatement.setLong(1, primKey);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("groupId");
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Unable to find ", tableName, " ", primKey));
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

			try (Connection connection = DataAccess.getConnection();
				PreparedStatement preparedStatement1 =
					connection.prepareStatement(
						StringBundler.concat(
							"select ",
							verifiableGroupedModel.getPrimaryKeyColumnName(),
							StringPool.COMMA_AND_SPACE,
							verifiableGroupedModel.
								getRelatedPrimaryKeyColumnName(),
							" from ", verifiableGroupedModel.getTableName(),
							" where groupId is null"));
				ResultSet resultSet = preparedStatement1.executeQuery()) {

				try (PreparedStatement preparedStatement2 =
						AutoBatchPreparedStatementUtil.autoBatch(
							connection.prepareStatement(
								StringBundler.concat(
									"update ",
									verifiableGroupedModel.getTableName(),
									" set groupId = ? where ",
									verifiableGroupedModel.
										getPrimaryKeyColumnName(),
									" = ?")))) {

					while (resultSet.next()) {
						long relatedPrimKey = resultSet.getLong(
							verifiableGroupedModel.
								getRelatedPrimaryKeyColumnName());

						long groupId = getGroupId(
							connection,
							verifiableGroupedModel.getRelatedTableName(),
							verifiableGroupedModel.
								getRelatedPrimaryKeyColumnName(),
							relatedPrimKey);

						if (groupId <= 0) {
							continue;
						}

						preparedStatement2.setLong(1, groupId);

						long primKey = resultSet.getLong(
							verifiableGroupedModel.getPrimaryKeyColumnName());

						preparedStatement2.setLong(2, primKey);

						preparedStatement2.addBatch();
					}

					preparedStatement2.executeBatch();
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyGroupedModel.class);

}