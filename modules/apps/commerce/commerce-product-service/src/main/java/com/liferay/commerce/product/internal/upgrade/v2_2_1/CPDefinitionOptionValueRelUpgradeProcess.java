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

package com.liferay.commerce.product.internal.upgrade.v2_2_1;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Marco Leo
 */
public class CPDefinitionOptionValueRelUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String selectCPDefinitionOptionRelSQL =
			"select groupId, CPDefinitionOptionRelId from " +
				"CPDefinitionOptionRel";
		String updateCPDefinitionOptionValueRelSQL =
			"update CPDefinitionOptionValueRel set groupId = ? WHERE " +
				"CPDefinitionOptionRelId = ?";

		try (PreparedStatement ps =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCPDefinitionOptionValueRelSQL);
			Statement s1 = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs1 = s1.executeQuery(selectCPDefinitionOptionRelSQL)) {

			while (rs1.next()) {
				ps.setLong(1, rs1.getLong("groupId"));
				ps.setLong(2, rs1.getLong("CPDefinitionOptionRelId"));

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

}