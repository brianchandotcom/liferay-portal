/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v5_2_0;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.petra.string.StringBundler;
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
			StringBundler.concat(
				"select ObjectDefinition.pkObjectFieldDBColumnName, ",
				"ObjectRelationship.dbTableName from ObjectDefinition inner ",
				"join ObjectRelationship on ObjectRelationship.type_ = '",
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY, "' where ",
				"ObjectDefinition.objectDefinitionId = ",
				"ObjectRelationship.objectDefinitionId1"),
			resultSet -> new Object[] {
				resultSet.getString(1), resultSet.getString(2)
			},
			values -> _createIndex(
				String.valueOf(values[0]), dbInspector,
				String.valueOf(values[1])),
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

}