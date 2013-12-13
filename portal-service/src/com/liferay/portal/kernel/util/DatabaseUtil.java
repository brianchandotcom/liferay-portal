/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;

import java.io.IOException;

import java.sql.SQLException;

import javax.naming.NamingException;

/**
 * @author     Ganesh Ram
 * @author     Brian Wing Shun Chan
 * @deprecated As of 6.1.0, replaced by {@link DBFactoryUtil}
 */
@Deprecated
public class DatabaseUtil {

	public static Database getDatabase() {
		if (_database != null) {
			return _database;
		}

		_database = new Database() {

			@Override
			public String getType() {
				DB db = DBFactoryUtil.getDB();

				return db.getType();
			}

			@Override
			public void runSQLTemplate(String path)
				throws IOException, NamingException, SQLException {

				DB db = DBFactoryUtil.getDB();

				db.runSQLTemplate(path);
			}

			@Override
			public void runSQLTemplate(String path, boolean failOnError)
				throws IOException, NamingException, SQLException {

				DB db = DBFactoryUtil.getDB();

				db.runSQLTemplate(path, failOnError);
			}

		};

		return _database;
	}

	public static String getType() {
		return getDatabase().getType();
	}

	public static void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException {

		getDatabase().runSQLTemplate(path);
	}

	public static void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException {

		getDatabase().runSQLTemplate(path, failOnError);
	}

	public void setDatabase(Database database) {
		_database = database;
	}

	private static Database _database;

}