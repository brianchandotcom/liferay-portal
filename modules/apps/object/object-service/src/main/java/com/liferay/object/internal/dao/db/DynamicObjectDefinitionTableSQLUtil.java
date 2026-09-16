/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.dao.db;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Shuyang Zhou
 */
public class DynamicObjectDefinitionTableSQLUtil {

	public static String getInsertMissingExtensionTableRowsSQL(
		ObjectDefinition objectDefinition) {

		String dbTableName = objectDefinition.getDBTableName();
		String extensionDBTableName =
			objectDefinition.getExtensionDBTableName();
		String pkObjectFieldDBColumnName =
			objectDefinition.getPKObjectFieldDBColumnName();

		String sql = StringBundler.concat(
			"insert into ", extensionDBTableName, " (",
			pkObjectFieldDBColumnName, ") select ", dbTableName,
			StringPool.PERIOD, pkObjectFieldDBColumnName, " from ", dbTableName,
			" left join ", extensionDBTableName, " on ", extensionDBTableName,
			StringPool.PERIOD, pkObjectFieldDBColumnName, " = ", dbTableName,
			StringPool.PERIOD, pkObjectFieldDBColumnName, " where ",
			extensionDBTableName, StringPool.PERIOD, pkObjectFieldDBColumnName,
			" is null");

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		return sql;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicObjectDefinitionTableSQLUtil.class);

}