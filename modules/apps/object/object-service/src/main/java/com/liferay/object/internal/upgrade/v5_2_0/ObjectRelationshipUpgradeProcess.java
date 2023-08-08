/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v5_2_0;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Mateus Santana
 */
public class ObjectRelationshipUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DBInspector dbInspector = new DBInspector(connection);

		processConcurrently(
			SQLTransformer.transform(
				StringBundler.concat(
					"select distinct ",
					"ObjectDefinition.pkObjectFieldDBColumnName, ",
					"ObjectRelationship.dbTableName, ",
					"ObjectRelationship.objectDefinitionId1, ",
					"ObjectRelationship.objectDefinitionId2 from ",
					"ObjectDefinition inner join ObjectRelationship on ",
					"ObjectRelationship.type_ = '",
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY, "' where ",
					"ObjectDefinition.objectDefinitionId = ",
					"ObjectRelationship.objectDefinitionId1 and ",
					"ObjectDefinition.active_ = [$TRUE$]")),
			resultSet -> new Object[] {
				resultSet.getString(1), resultSet.getString(2),
				resultSet.getLong(3), resultSet.getLong(4)
			},
			values -> _createIndex(
				String.valueOf(values[0]), dbInspector,
				String.valueOf(values[1]), (long)values[2], (long)values[3]),
			null);
	}

	private void _createIndex(
			String columnName, DBInspector dbInspector, String tableName)
		throws Exception {

		IndexMetadata indexMetadata =
			IndexMetadataFactoryUtil.createIndexMetadata(
				false, tableName, columnName);

		if (!dbInspector.hasIndex(tableName, indexMetadata.getIndexName())) {
			runSQL(indexMetadata.getCreateSQL(null));
		}
	}

	private void _createIndex(
			String columnName, DBInspector dbInspector, String tableName,
			long objectDefinitionId1, long objectDefinitionId2)
		throws Exception {

		if (objectDefinitionId1 != objectDefinitionId2) {
			_createIndex(columnName, dbInspector, tableName);
		}
		else {
			_createIndex(columnName.concat("1"), dbInspector, tableName);
			_createIndex(columnName.concat("2"), dbInspector, tableName);
		}
	}

}