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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Shepherd Ching
 * @author Jian Cao
 * @author Laszlo Csontos
 */
public class DB2Dialect extends org.hibernate.dialect.DB2Dialect {

	public DB2Dialect() {
		super();
		registerKeyword("optimize");
		registerKeyword("for");
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {

		boolean hasOffset = false;

		if ((offset > 0) || forceLimitUsage()) {
			hasOffset = true;
		}

		StringBundler sb = null;

		if (hasOffset) {
			sb = new StringBundler(11);
		}
		else {
			sb = new StringBundler(5);
		}

		// If offset is not specified ROW_NUMBER() OVER () can be omitted
		// and implicit sort will not be taken place this way.

		if (!hasOffset) {
			addQueryForLimitedRows(sb, sql, limit);
			addOptimizeForLimitedRows(sb, limit);

			return sb.toString();
		}

		// Outer query

		sb.append("SELECT outerQuery.* FROM (");
		sb.append("SELECT ROW_NUMBER() OVER() AS rownumber_, ");

		// Inner query

		sb.append("innerQuery.* FROM (");
		addQueryForLimitedRows(sb, sql, limit);
		sb.append(") AS innerQuery");

		// Offset

		sb.append(") AS outerQuery WHERE rownumber_ > ");
		sb.append(offset);

		addOptimizeForLimitedRows(sb, limit);

		return sb.toString();
	}

	@Override
	public boolean supportsVariableLimit() {
		return _SUPPORTS_VARIABLE_LIMIT;
	}

	private void addOptimizeForLimitedRows(StringBundler sb, int limit) {
		String sqlFragment = StringUtil.replace(
			_SQL_OPTIMIZE_FOR_LIMITED_ROWS, "[$LIMIT$]", String.valueOf(limit));

		sb.append(StringPool.SPACE);
		sb.append(sqlFragment);
	}

	private void addQueryForLimitedRows(
		StringBundler sb, String sql, int limit) {

		sb.append(sql);

		String sqlFragment = StringUtil.replace(
			_SQL_FETCH_FIRST_LIMITED_ROWS_ONLY, "[$LIMIT$]",
			String.valueOf(limit));

		sb.append(StringPool.SPACE);
		sb.append(sqlFragment);
	}

	private static final String _SQL_FETCH_FIRST_LIMITED_ROWS_ONLY =
		"FETCH FIRST [$LIMIT$] ROWS ONLY";

	private static final String _SQL_OPTIMIZE_FOR_LIMITED_ROWS =
		"OPTIMIZE FOR [$LIMIT$] ROWS";

	private static final boolean _SUPPORTS_VARIABLE_LIMIT = false;

}