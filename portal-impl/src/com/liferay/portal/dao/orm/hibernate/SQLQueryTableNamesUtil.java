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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * @author Shuyang Zhou
 */
public class SQLQueryTableNamesUtil {

	public static String[] getTableNames(String sql) {
		String[] tableNames = _portalCache.get(sql);

		if (tableNames != null) {
			return tableNames;
		}

		Statement statement = null;

		try {
			statement = _jSqlParser.parse(
				new UnsyncStringReader(_escapeSQL(sql)));
		}
		catch (JSQLParserException jsqlpe) {
			_log.error("Unable to parse SQL: " + sql, jsqlpe);
		}

		if ((statement == null) || !(statement instanceof Select)) {
			tableNames = new String[0];

			_portalCache.put(sql, tableNames);

			return tableNames;
		}

		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

		List<String> tableNameList = tablesNamesFinder.getTableList(
			(Select)statement);

		tableNames = tableNameList.toArray(new String[tableNameList.size()]);

		_portalCache.put(sql, tableNames);

		return tableNames;
	}

	private static String _escapeSQL(String sql) {
		sql = sql.trim();

		if ((sql.length() > 1) &&
			(sql.charAt(0) == CharPool.OPEN_PARENTHESIS)) {

			int closeIndex = sql.lastIndexOf(CharPool.CLOSE_PARENTHESIS);

			int openCount = 1;

			for (int i = 1; i < closeIndex; i++) {
				if (sql.charAt(i) == CharPool.OPEN_PARENTHESIS) {
					openCount++;
				}

				if (sql.charAt(i) == CharPool.CLOSE_PARENTHESIS) {
					openCount--;

					if (openCount == 0) {
						break;
					}
				}
			}

			if (openCount == 1) {
				String body = sql.substring(1, closeIndex);

				if (closeIndex < (sql.length() - 1)) {
					String tail = sql.substring(closeIndex + 1);

					sql = body.concat(tail);
				}
				else {
					sql = body;
				}
			}
		}

		Matcher matcher = _tableNamePattern.matcher(sql);

		sql = matcher.replaceAll("$1");

		matcher = _modPattern.matcher(sql);

		sql = matcher.replaceAll("1");

		matcher = _valuePattern.matcher(sql);

		return matcher.replaceAll("value_");
	}

	private static Log _log = LogFactoryUtil.getLog(
		SQLQueryTableNamesUtil.class);

	private static JSqlParser _jSqlParser = new CCJSqlParserManager();
	private static Pattern _modPattern = Pattern.compile(
		"MOD\\((.+?),(.+?)\\)", Pattern.CASE_INSENSITIVE);
	private static PortalCache<String, String[]> _portalCache =
		SingleVMPoolUtil.getCache(SQLQueryTableNamesUtil.class.getName());
	private static Pattern _tableNamePattern = Pattern.compile(
		"\\{(\\w+\\.\\*)\\}", Pattern.CASE_INSENSITIVE);
	private static Pattern _valuePattern = Pattern.compile(
		"\\bvalue\\b", Pattern.CASE_INSENSITIVE);

}