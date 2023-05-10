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

package com.liferay.object.internal.upgrade.v5_0_0;

import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Murilo Stodolni
 */
public class ObjectFieldSettingUpgradeProcess extends UpgradeProcess {

	public ObjectFieldSettingUpgradeProcess(PortalUUID portalUUID) {
		_portalUUID = portalUUID;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select ObjectField.objectFieldId, ",
						"ObjectField.companyId, ObjectField.userId, ",
						"ObjectField.userName, ObjectField.defaultValue from ",
						"ObjectField where ObjectField.state_ = [$TRUE$]")));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into ObjectFieldSetting (uuid_, ",
						"objectFieldSettingId, companyId, userId, userName, ",
						"createDate, modifiedDate, objectFieldId, name, ",
						"value) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				preparedStatement2.setString(1, _portalUUID.generate());
				preparedStatement2.setLong(2, increment());
				preparedStatement2.setLong(3, resultSet.getLong("companyId"));
				preparedStatement2.setLong(4, resultSet.getLong("userId"));
				preparedStatement2.setString(
					5, resultSet.getString("userName"));

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				preparedStatement2.setTimestamp(6, timestamp);
				preparedStatement2.setTimestamp(7, timestamp);

				preparedStatement2.setLong(
					8, resultSet.getLong("objectFieldId"));
				preparedStatement2.setString(
					9, ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE);
				preparedStatement2.setString(
					10, ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE);

				preparedStatement2.addBatch();

				preparedStatement2.setString(1, _portalUUID.generate());
				preparedStatement2.setLong(2, increment());

				preparedStatement2.setString(
					9, ObjectFieldSettingConstants.NAME_DEFAULT_VALUE);
				preparedStatement2.setString(
					10, resultSet.getString("defaultValue"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns("ObjectField", "defaultValue")
		};
	}

	private final PortalUUID _portalUUID;

}