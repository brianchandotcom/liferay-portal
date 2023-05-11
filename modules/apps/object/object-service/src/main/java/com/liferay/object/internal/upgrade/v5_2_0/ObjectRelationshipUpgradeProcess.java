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
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.IOException;

import java.sql.SQLException;

/**
 * @author Mateus Santana
 */
public class ObjectRelationshipUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
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
				String.valueOf(values[0]), String.valueOf(values[1])),
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

}