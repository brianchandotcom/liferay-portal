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
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.verify.model.VerifiableUUIDModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collection;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyUUID extends VerifyProcess {

	public static void verify(VerifiableUUIDModel... verifiableUUIDModels)
		throws Exception {

		VerifyUUID verifyUUID = new VerifyUUID();

		_verifiableUUIDModels = verifiableUUIDModels;

		verifyUUID.verify();
	}

	@Override
	protected void doVerify() throws Exception {
		if (!ArrayUtil.isEmpty(_verifiableUUIDModels)) {
			doVerify(_verifiableUUIDModels);
		}

		Map<String, VerifiableUUIDModel> verifiableUUIDModelsMap =
			PortalBeanLocatorUtil.locate(VerifiableUUIDModel.class);

		Collection<VerifiableUUIDModel> verifiableUUIDModels =
			verifiableUUIDModelsMap.values();

		doVerify(verifiableUUIDModels.toArray(new VerifiableUUIDModel[0]));
	}

	protected void doVerify(VerifiableUUIDModel... verifiableUUIDModels)
		throws Exception {

		processConcurrently(verifiableUUIDModels, this::verifyUUID, null);
	}

	protected void verifyUUID(VerifiableUUIDModel verifiableUUIDModel)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		if (db.isSupportsNewUuidFunction()) {
			try (LoggingTimer loggingTimer = new LoggingTimer(
					verifiableUUIDModel.getTableName());
				PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"update ", verifiableUUIDModel.getTableName(),
							" set uuid_ = ", db.getNewUuidFunctionName(),
							" where uuid_ is null or uuid_ = ''"))) {

				preparedStatement.executeUpdate();

				return;
			}
		}

		try (LoggingTimer loggingTimer = new LoggingTimer(
				verifiableUUIDModel.getTableName());
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ", verifiableUUIDModel.getPrimaryKeyColumnName(),
					" from ", verifiableUUIDModel.getTableName(),
					" where uuid_ is null or uuid_ = ''"));
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						StringBundler.concat(
							"update ", verifiableUUIDModel.getTableName(),
							" set uuid_ = ? where ",
							verifiableUUIDModel.getPrimaryKeyColumnName(),
							" = ?")))) {

			while (resultSet.next()) {
				long pk = resultSet.getLong(
					verifiableUUIDModel.getPrimaryKeyColumnName());

				preparedStatement2.setString(1, PortalUUIDUtil.generate());
				preparedStatement2.setLong(2, pk);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private static VerifiableUUIDModel[] _verifiableUUIDModels;

}