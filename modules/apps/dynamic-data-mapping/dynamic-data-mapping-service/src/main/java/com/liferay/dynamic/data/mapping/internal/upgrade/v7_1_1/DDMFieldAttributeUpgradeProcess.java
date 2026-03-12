/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v7_1_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Carolina Barbosa
 */
public class DDMFieldAttributeUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMFieldAttribute.ctCollectionId, ",
					"DDMFieldAttribute.fieldAttributeId, ",
					"DDMFieldAttribute.attributeName from DDMField inner join ",
					"DDMFieldAttribute on DDMField.ctCollectionId = ",
					"DDMFieldAttribute.ctCollectionId and DDMField.fieldId = ",
					"DDMFieldAttribute.fieldId where DDMField.fieldType = ",
					"'geolocation' and (DDMFieldAttribute.attributeName = ",
					"'latitude' or DDMFieldAttribute.attributeName = ",
					"'longitude')"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update DDMFieldAttribute set attributeName = ? where " +
						"ctCollectionId = ? and fieldAttributeId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				if (StringUtil.equals(
						resultSet.getString("attributeName"), "latitude")) {

					preparedStatement2.setString(1, "lat");
				}
				else {
					preparedStatement2.setString(1, "lng");
				}

				preparedStatement2.setLong(
					2, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(
					3, resultSet.getLong("fieldAttributeId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}