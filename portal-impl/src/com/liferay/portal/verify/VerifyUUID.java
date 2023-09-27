/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.verify.model.VerifiableUUIDModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
					connection,
					StringBundler.concat(
						"update ", verifiableUUIDModel.getTableName(),
						" set uuid_ = ? where ",
						verifiableUUIDModel.getPrimaryKeyColumnName(),
						" = ?"))) {

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