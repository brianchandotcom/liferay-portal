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

package com.liferay.object.internal.upgrade.v5_2_0;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mateus Santana
 */
public class ObjectRelationshipUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		processConcurrently(
			StringBundler.concat(
				"select ObjectRelationship.dbTableName, ",
				"ObjectRelationship.objectDefinitionId1, ",
				"ObjectRelationship.objectDefinitionId2 from ",
				"ObjectRelationship where ObjectRelationship.type_ = ",
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY, "and ",
				"ObjectRelationship.reverse = 0)"),
			resultSet -> {
				String dbTableName = resultSet.getString(1);
				long objectDefinitionId1 = resultSet.getLong(2);
				long objectDefinitionId2 = resultSet.getLong(3);
				String pkObjectFieldDBColumnName1 =
					_getPKObjectFieldDBColumnName(objectDefinitionId1);
				String pkObjectFieldDBColumnName2 =
					_getPKObjectFieldDBColumnName(objectDefinitionId2);

				return new Object[] {
					dbTableName, pkObjectFieldDBColumnName1,
					pkObjectFieldDBColumnName2
				};
			},
			values -> {
				String dbTableName = String.valueOf(values[0]);
				String pkObjectFieldDBColumnName1 = String.valueOf(values[1]);
				String pkObjectFieldDBColumnName2 = String.valueOf(values[2]);

				_createIndex(dbTableName, pkObjectFieldDBColumnName1);
				_createIndex(dbTableName, pkObjectFieldDBColumnName2);
			},
			null);
	}

	private void _createIndex(
			String dbTableName, String pkObjectFieldDBColumnName)
		throws IOException, SQLException {

		IndexMetadata indexMetadata =
			IndexMetadataFactoryUtil.createIndexMetadata(
				false, dbTableName, pkObjectFieldDBColumnName);

		runSQL(indexMetadata.getCreateSQL(null));
	}

	private String _getPKObjectFieldDBColumnName(long objectDefinitionId) {
		String selectPKObjectFieldDBColumnName = SQLTransformer.transform(
			StringBundler.concat(
				"select ObjectDefinition.pkObjectFieldDBColumnName from ",
				"ObjectDefinition where ObjectDefinition.objectDefinitionId = ",
				" "));

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				selectPKObjectFieldDBColumnName + objectDefinitionId);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			return resultSet.getString(1);
		}
		catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		}
	}

}