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