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

package com.liferay.object.internal.upgrade.v5_3_1;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Murilo Stodolni
 */
public class SchemaUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		processConcurrently(
			SQLTransformer.transform(
				StringBundler.concat(
					"select ObjectField.dbColumnName, ObjectField.dbTableName ",
					"from ObjectField inner join ObjectDefinition on ",
					"ObjectField.businessType = '",
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP,
					"' and ObjectDefinition.active_ = [$TRUE$] where ",
					"ObjectField.objectDefinitionId = ",
					"ObjectDefinition.objectDefinitionId")),
			resultSet -> new Object[] {
				resultSet.getString(1), resultSet.getString(2)
			},
			values -> _createIndex(
				String.valueOf(values[0]), new DBInspector(connection),
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