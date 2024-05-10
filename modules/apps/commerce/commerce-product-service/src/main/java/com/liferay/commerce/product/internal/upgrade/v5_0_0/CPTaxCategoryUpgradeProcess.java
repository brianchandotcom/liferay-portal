/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
						"where ctCollectionId = ? and CPTaxCategoryId = ?")) {

			try (ResultSet resultSet = s.executeQuery(
					StringBundler.concat(
						"select ctCollectionId, externalReferenceCode, ",
						"CPTaxCategoryId from CPTaxCategory where ",
						"externalReferenceCode in (select ",
						"externalReferenceCode from CPTaxCategory group by ",
						"externalReferenceCode having ",
						"count(externalReferenceCode) > 1)"))) {

				while (resultSet.next()) {
					String externalReferenceCode = resultSet.getString(2);

					long cpTaxCategoryId = resultSet.getLong(3);

					preparedStatement.setString(
						1, externalReferenceCode + StringUtil.randomString(4));

					preparedStatement.setLong(
						2, resultSet.getLong("ctCollectionId"));

					preparedStatement.setLong(3, cpTaxCategoryId);

					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
			}
		}
	}

}