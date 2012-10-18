/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.sql.SQLException;

import javax.naming.NamingException;

/**
 * @author Hugo Huijser
 * @author Brian Wing Shun Chan
 */
public abstract class BaseDBProcess implements DBProcess {

	public BaseDBProcess() {
	}

	public String createIndex(String tableName, String columnName)
		throws IOException, SQLException {

		return createIndex(tableName, new String[] {columnName});
	}

	public String createIndex(String tableName, String[] columnNames)
		throws IOException, SQLException {

		String columnNamesString = StringUtil.merge(columnNames, ", ");

		String indexSpec = tableName + StringPool.UNDERLINE + columnNamesString;

		String indexHash = StringUtil.toHexString(
			indexSpec.hashCode()).toUpperCase();

		String indexName = "IX_" + indexHash;

		if (_log.isInfoEnabled()) {
			_log.info("creating index " + indexName + " on table " + tableName);
		}

		StringBundler sb = new StringBundler(7);

		sb.append("alter table ");
		sb.append(tableName);
		sb.append(" add index ");
		sb.append(indexName);
		sb.append(" (");
		sb.append(columnNamesString);
		sb.append(");");

		runSQL(sb.toString());

		return indexName;
	}

	public void dropIndex(String tableName, String indexName)
		throws IOException, SQLException {

		if (_log.isInfoEnabled()) {
			_log.info(
				"dropping index " + indexName + " from table " + tableName);
		}

		StringBundler sb = new StringBundler(5);

		sb.append("alter table ");
		sb.append(tableName);
		sb.append(" drop index ");
		sb.append(indexName);
		sb.append(";");

		runSQL(sb.toString());
	}

	public void runSQL(String template) throws IOException, SQLException {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(template);
	}

	public void runSQL(String[] templates) throws IOException, SQLException {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(templates);
	}

	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException {

		DB db = DBFactoryUtil.getDB();

		db.runSQLTemplate(path);
	}

	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException {

		DB db = DBFactoryUtil.getDB();

		db.runSQLTemplate(path, failOnError);
	}

	private static Log _log = LogFactoryUtil.getLog(BaseDBProcess.class);

}