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
import com.liferay.portal.kernel.dao.jdbc.ConnectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;

import java.sql.Connection;

import javax.sql.DataSource;

/**
 * @author Murilo Stodolni
 */
public class ObjectDBManagerUtil {

	public static void createIndexMetadata(
			Connection connection, String tableName, boolean unique,
			String... columnNames)
		throws PortalException {

		try {
			DBInspector dbInspector = new DBInspector(connection);

			IndexMetadata indexMetadata =
				IndexMetadataFactoryUtil.createIndexMetadata(
					unique, tableName, columnNames);

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

	public static void runSQL(DataSource dataSource, Log log, String sql) {

		// Objects DDL instructions end with ";". Only DB#runSQL can handle it.
		// See LPD-25786.

		if (log.isDebugEnabled()) {
			log.debug("SQL: " + sql);
		}

		DB db = DBManagerUtil.getDB();

		try (Connection connection = ConnectionUtil.getConnection(dataSource)) {
			db.runSQL(connection, new String[] {sql});
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

}