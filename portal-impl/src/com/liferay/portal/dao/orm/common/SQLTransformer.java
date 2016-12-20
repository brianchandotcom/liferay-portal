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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.dao.orm.common.transformation.PortalSQLTransformer;
import com.liferay.portal.dao.orm.common.transformation.Transformer;
import com.liferay.portal.dao.orm.common.transformation.providers.DB2TransformationProvider;
import com.liferay.portal.dao.orm.common.transformation.providers.HypersonicTransformationProvider;
import com.liferay.portal.dao.orm.common.transformation.providers.MySQLTransformationProvider;
import com.liferay.portal.dao.orm.common.transformation.providers.OracleTransformationProvider;
import com.liferay.portal.dao.orm.common.transformation.providers.PostgreSQLTransformationProvider;
import com.liferay.portal.dao.orm.common.transformation.providers.SQLServerTransformationProvider;
import com.liferay.portal.dao.orm.common.transformation.providers.SQLTransformationProvider;
import com.liferay.portal.dao.orm.common.transformation.providers.SybaseTransformationProvider;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SQLTransformer {

	public static void reloadSQLTransformer() {
		_instance._reloadSQLTransformer();
	}

	public static String transform(String sql) {
		return _instance._getTransformer().transform(sql);
	}

	public static String transformFromHqlToJpql(String sql) {
		return _instance._transformFromHqlToJpql(sql);
	}

	public static String transformFromJpqlToHql(String sql) {
		return _instance._transformFromJpqlToHql(sql);
	}

	private SQLTransformer() {
		_reloadSQLTransformer();
	}

	private Transformer _getTransformer() {
		return _transformer;
	}

	private void _reloadSQLTransformer() {
		if (_transformedSqls == null) {
			_transformedSqls = new ConcurrentHashMap<>();
		}
		else {
			_transformedSqls.clear();
		}

		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		SQLTransformationProvider sqlTransformationProvider = null;

		if (dbType == DBType.DB2) {
			sqlTransformationProvider = new DB2TransformationProvider();
		}
		else if (dbType == DBType.HYPERSONIC) {
			sqlTransformationProvider = new HypersonicTransformationProvider();
		}
		else if (dbType == DBType.MYSQL) {
			sqlTransformationProvider = new MySQLTransformationProvider(
				db.isSupportsStringCaseSensitiveQuery());
		}
		else if (dbType == DBType.ORACLE) {
			sqlTransformationProvider = new OracleTransformationProvider();
		}
		else if (dbType == DBType.POSTGRESQL) {
			sqlTransformationProvider = new PostgreSQLTransformationProvider();
		}
		else if (dbType == DBType.SQLSERVER) {
			sqlTransformationProvider = new SQLServerTransformationProvider();
		}
		else if (dbType == DBType.SYBASE) {
			sqlTransformationProvider = new SybaseTransformationProvider();
		}

		_transformer = PortalSQLTransformer.buildSQLTransformer(
			db, sqlTransformationProvider);
	}

	private String _transformFromHqlToJpql(String sql) {
		String newSQL = _transformedSqls.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _transformer.transform(sql);

		newSQL = _transformPositionalParams(newSQL);

		newSQL = StringUtil.replace(newSQL, _HQL_NOT_EQUALS, _JPQL_NOT_EQUALS);
		newSQL = StringUtil.replace(
			newSQL, _HQL_COMPOSITE_ID_MARKER, _JPQL_DOT_SEPARTOR);

		_transformedSqls.put(sql, newSQL);

		return newSQL;
	}

	private String _transformFromJpqlToHql(String sql) {
		String newSQL = _transformedSqls.get(sql);

		if (newSQL != null) {
			return newSQL;
		}

		newSQL = _transformer.transform(sql);

		Matcher matcher = _jpqlCountPattern.matcher(newSQL);

		if (matcher.find()) {
			String countExpression = matcher.group(1);
			String entityAlias = matcher.group(3);

			if (entityAlias.equals(countExpression)) {
				newSQL = matcher.replaceFirst(_HQL_COUNT_SQL);
			}
		}

		_transformedSqls.put(sql, newSQL);

		return newSQL;
	}

	private String _transformPositionalParams(String queryString) {
		if (queryString.indexOf(CharPool.QUESTION) == -1) {
			return queryString;
		}

		StringBundler sb = new StringBundler();

		int i = 1;
		int from = 0;
		int to = 0;

		while ((to = queryString.indexOf(CharPool.QUESTION, from)) != -1) {
			sb.append(queryString.substring(from, to));
			sb.append(StringPool.QUESTION);
			sb.append(i++);

			from = to + 1;
		}

		sb.append(queryString.substring(from));

		return sb.toString();
	}

	private static final String _HQL_COMPOSITE_ID_MARKER = "\\.id\\.";

	private static final String _HQL_COUNT_SQL = "SELECT COUNT(*) FROM $2 $3";

	private static final String _HQL_NOT_EQUALS = "!=";

	private static final String _JPQL_DOT_SEPARTOR = ".";

	private static final String _JPQL_NOT_EQUALS = "<>";

	private static final SQLTransformer _instance = new SQLTransformer();

	private static final Pattern _jpqlCountPattern = Pattern.compile(
		"SELECT COUNT\\((\\S+)\\) FROM (\\S+) (\\S+)");

	private Map<String, String> _transformedSqls;
	private Transformer _transformer;

}