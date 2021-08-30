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

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alberto Chaparro
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQLTemplate("update-7.2.0-7.3.0.sql", false);

		_copyCompanyKey();

		alterTableDropColumn("Company", "key_");
	}

	private void _copyCompanyKey() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select companyId, key_ from Company");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"insert into CompanyInfo (companyInfoId, companyId, " +
							"key_) values (?, ?, ?)"))) {

			while (resultSet.next()) {
				preparedStatement2.setLong(1, increment());
				preparedStatement2.setLong(2, resultSet.getLong(1));
				preparedStatement2.setString(3, resultSet.getString(2));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}