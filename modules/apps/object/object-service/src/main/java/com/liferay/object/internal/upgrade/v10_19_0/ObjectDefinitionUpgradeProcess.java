/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_19_0;

import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.model.impl.ObjectDefinitionModelImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Feliphe Marinho
 */
public class ObjectDefinitionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select companyId, userId, userName, objectDefinitionId, ",
					"rootObjectDefinitionId from ObjectDefinition where ",
					"rootObjectDefinitionId <> 0"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into ObjectDefinitionSetting (mvccVersion, ",
						"uuid_, objectDefinitionSettingId, companyId, userId, ",
						"userName, createDate, modifiedDate, ",
						"objectDefinitionId, name, value) values (?, ?, ?, ?, ",
						"?, ?, ?, ?, ?, ?, ?)"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				preparedStatement2.setLong(1, 0);
				preparedStatement2.setString(2, PortalUUIDUtil.generate());
				preparedStatement2.setLong(3, increment());
				preparedStatement2.setLong(4, resultSet.getLong("companyId"));
				preparedStatement2.setLong(5, resultSet.getLong("userId"));
				preparedStatement2.setString(
					6, resultSet.getString("userName"));

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				preparedStatement2.setTimestamp(7, timestamp);
				preparedStatement2.setTimestamp(8, timestamp);

				preparedStatement2.setLong(
					9, resultSet.getLong("objectDefinitionId"));
				preparedStatement2.setString(
					10,
					ObjectDefinitionSettingConstants.
						NAME_ROOT_OBJECT_DEFINITION_IDS);
				preparedStatement2.setString(
					11,
					String.valueOf(
						resultSet.getLong("rootObjectDefinitionId")));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns(
				ObjectDefinitionModelImpl.TABLE_NAME, "rootObjectDefinitionId")
		};
	}

}