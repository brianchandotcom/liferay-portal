/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.sql;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactory;
import com.liferay.portal.kernel.dao.db.DBType;

import java.util.ServiceLoader;

/**
 * @author Mariano Álvaro Sáiz
 */
public class FakeDBFactory {

	public static DB getDB(DBType dbType) {
		ServiceLoader<DBFactory> serviceLoader = ServiceLoader.load(
			DBFactory.class, DBFactory.class.getClassLoader());

		for (DBFactory dbFactory : serviceLoader) {
			if (dbFactory.getDBType() == dbType) {
				return dbFactory.create(0, 0);
			}
		}

		throw new IllegalArgumentException("Illegal database type " + dbType);
	}

}