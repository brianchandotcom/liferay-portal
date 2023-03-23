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

package com.liferay.commerce.product.internal.upgrade.v5_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Brian I. Kim
 */
public class CPTaxCategoryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateCPTaxCategoryExternalReferenceCode();
	}

	private void _updateCPTaxCategoryExternalReferenceCode() throws Exception {
		try (Statement s = connection.createStatement();
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update CPTaxCategory set externalReferenceCode = ? " +
						"where CPTaxCategoryId = ?")) {

			try (ResultSet resultSet = s.executeQuery(
					StringBundler.concat(
						"select externalReferenceCode, CPTaxCategoryId from ",
						"CPTaxCategory where externalReferenceCode in (select ",
						"externalReferenceCode from CPTaxCategory group by ",
						"externalReferenceCode having ",
						"count(externalReferenceCode) > 1)"))) {

				while (resultSet.next()) {
					String externalReferenceCode = resultSet.getString(1);

					long cpTaxCategoryId = resultSet.getLong(2);

					preparedStatement.setString(
						1, externalReferenceCode + StringUtil.randomString(4));

					preparedStatement.setLong(2, cpTaxCategoryId);

					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
			}
		}
	}

}