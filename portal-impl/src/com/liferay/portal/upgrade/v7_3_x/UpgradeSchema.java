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
import com.liferay.portal.upgrade.v7_3_x.util.CompanyTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alberto Chaparro
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQLTemplate("update-7.2.0-7.3.0.sql", false);

		_copyCompanyKey();

		alter(CompanyTable.class, new AlterTableDropColumn("key_"));
	}

	private void _copyCompanyKey() throws SQLException {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select companyId, key_ from Company");
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"insert into CompanyInfo (companyInfoId, companyId, " +
						"key_) values (?, ?, ?)"))) {

			while (rs.next()) {
				ps2.setLong(1, increment());
				ps2.setLong(2, rs.getLong(1));
				ps2.setString(3, rs.getString(2));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}