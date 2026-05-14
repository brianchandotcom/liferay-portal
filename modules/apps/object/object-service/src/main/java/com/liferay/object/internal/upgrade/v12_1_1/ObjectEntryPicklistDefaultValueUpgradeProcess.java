/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v12_1_1;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Victor Kammerer
 */
public class ObjectEntryPicklistDefaultValueUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select ObjectField.dbColumnName, ",
						"ObjectField.dbTableName, ObjectFieldSetting.value as ",
						"defaultValue from ObjectField inner join ",
						"ObjectDefinition on ",
						"ObjectDefinition.objectDefinitionId = ",
						"ObjectField.objectDefinitionId inner join ",
						"ObjectFieldSetting on ",
						"ObjectFieldSetting.objectFieldId = ",
						"ObjectField.objectFieldId and ",
						"ObjectFieldSetting.name = 'defaultValue' where ",
						"ObjectDefinition.status = ? and ",
						"ObjectField.businessType = ? and ObjectField.state_ ",
						"= [$TRUE$] and ObjectFieldSetting.value is not null ",
						"and not exists (select 1 from ObjectFieldSetting ofs ",
						"where ofs.objectFieldId = ObjectField.objectFieldId ",
						"and ofs.name = 'defaultValueType' and ofs.value != ",
						"'inputAsValue')")))) {

			preparedStatement1.setInt(1, WorkflowConstants.STATUS_APPROVED);
			preparedStatement1.setString(
				2, ObjectFieldConstants.BUSINESS_TYPE_PICKLIST);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String defaultValue = resultSet.getString("defaultValue");

					if (Validator.isNull(defaultValue)) {
						continue;
					}

					String dbColumnName = resultSet.getString("dbColumnName");
					String dbTableName = resultSet.getString("dbTableName");

					if (!hasTable(dbTableName) ||
						!hasColumn(dbTableName, dbColumnName)) {

						continue;
					}

					try (PreparedStatement preparedStatement2 =
							connection.prepareStatement(
								StringBundler.concat(
									"update ", dbTableName, " set ",
									dbColumnName, " = ? where ", dbColumnName,
									" is null or ", dbColumnName, " = ''"))) {

						preparedStatement2.setString(1, defaultValue);
						preparedStatement2.executeUpdate();
					}
				}
			}
		}
	}

}