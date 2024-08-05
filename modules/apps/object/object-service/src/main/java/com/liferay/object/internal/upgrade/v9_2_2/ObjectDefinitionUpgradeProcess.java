/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_2_2;

import com.liferay.object.definition.util.ObjectDefinitionUtil;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Tavares
 */
public class ObjectDefinitionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String sql = SQLTransformer.transform(
			"select objectDefinitionId, name from ObjectDefinition where " +
				"modifiable = [$FALSE$] and system_ = [$TRUE$]");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sql);
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ObjectDefinition set externalReferenceCode = ? " +
						"where objectDefinitionId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				preparedStatement2.setString(
					1,
					ObjectDefinitionUtil.
						getUnmodifiableSystemObjectDefinitionExternalReferenceCode(
							resultSet.getString(2)));
				preparedStatement2.setLong(2, resultSet.getLong(1));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}