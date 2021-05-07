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

package com.liferay.wiki.internal.upgrade.v2_2_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.wiki.internal.upgrade.v2_2_0.util.WikiNodeTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Luis Miguel Barcos
 */
public class WikiNodeExternalReferenceCodeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(WikiNodeTable.TABLE_NAME, "externalReferenceCode")) {
			alter(
				WikiNodeTable.class,
				new AlterTableAddColumn(
					"externalReferenceCode", "VARCHAR(75)"));
		}

		_populateExternalReferenceCode();
	}

	private void _populateExternalReferenceCode() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select nodeId from WikiNode where externalReferenceCode is " +
					"null or externalReferenceCode = ''");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update WikiNode set externalReferenceCode = ? where " +
							"nodeId = ?"))) {

			while (resultSet.next()) {
				long nodeId = resultSet.getLong(1);

				preparedStatement2.setString(1, String.valueOf(nodeId));
				preparedStatement2.setLong(2, nodeId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}