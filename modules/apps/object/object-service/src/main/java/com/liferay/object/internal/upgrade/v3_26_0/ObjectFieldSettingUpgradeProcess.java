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

package com.liferay.object.internal.upgrade.v3_26_0;

import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Carolina Barbosa
 */
public class ObjectFieldSettingUpgradeProcess extends UpgradeProcess {

	public ObjectFieldSettingUpgradeProcess(PortalUUID portalUUID) {
		_portalUUID = portalUUID;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select ObjectField.objectFieldId, ",
						"ObjectField.companyId, ObjectField.userId, ",
						"ObjectField.userName, ObjectField.name from ",
						"ObjectField where ObjectField.relationshipType = '",
						ObjectRelationshipConstants.TYPE_ONE_TO_MANY, "'"));
			PreparedStatement insertPreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into ObjectFieldSetting (uuid_, ",
						"objectFieldSettingId, companyId, userId, userName, ",
						"createDate, modifiedDate, objectFieldId, name, ",
						"value) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				insertPreparedStatement.setString(1, _portalUUID.generate());
				insertPreparedStatement.setLong(2, increment());
				insertPreparedStatement.setLong(
					3, resultSet.getLong("companyId"));
				insertPreparedStatement.setLong(4, resultSet.getLong("userId"));
				insertPreparedStatement.setString(
					5, resultSet.getString("userName"));

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				insertPreparedStatement.setTimestamp(6, timestamp);
				insertPreparedStatement.setTimestamp(7, timestamp);

				insertPreparedStatement.setLong(
					8, resultSet.getLong("objectFieldId"));
				insertPreparedStatement.setString(
					9,
					ObjectFieldSettingConstants.
						OBJECT_RELATIONSHIP_ERC_FIELD_NAME);
				insertPreparedStatement.setString(
					10,
					StringUtil.replaceLast(
						resultSet.getString("name"), "Id", "ERC"));

				insertPreparedStatement.addBatch();
			}

			insertPreparedStatement.executeBatch();
		}
	}

	private final PortalUUID _portalUUID;

}