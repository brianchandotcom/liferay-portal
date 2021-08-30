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

package com.liferay.message.boards.internal.upgrade.v5_2_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Javier de Arcos
 */
public class MBMessageExternalReferenceCodeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("MBMessage", "externalReferenceCode")) {
			alterTableAddColumn(
				"MBMessage", "externalReferenceCode", "VARCHAR(75)");
		}

		_populateExternalReferenceCode();
	}

	private void _populateExternalReferenceCode() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select messageId from MBMessage where externalReferenceCode " +
					"is null or externalReferenceCode = ''");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update MBMessage set externalReferenceCode = ? " +
							"where messageId = ?"))) {

			while (resultSet.next()) {
				long messageId = resultSet.getLong(1);

				preparedStatement2.setString(1, String.valueOf(messageId));
				preparedStatement2.setLong(2, messageId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}