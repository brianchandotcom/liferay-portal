/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.sql.Connection;

/**
 * @author Murilo Stodolni
 */
public class ObjectDBManagerUtil {

	public static void createIndexMetadata(
			String columnName, Connection connection, String tableName,
			boolean unique)
		throws PortalException {

		try {
			DBInspector dbInspector = new DBInspector(connection);

			IndexMetadata indexMetadata =
				IndexMetadataFactoryUtil.createIndexMetadata(
					unique, tableName, columnName);

			if (dbInspector.hasIndex(tableName, indexMetadata.getIndexName())) {
				return;
			}

			DB db = DBManagerUtil.getDB();

			db.runSQL(connection, indexMetadata.getCreateSQL(null));
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

}