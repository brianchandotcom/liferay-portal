/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.hibernate.dialect.pagination.AbstractLimitHandler;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.engine.spi.RowSelection;

/**
 * @author Shepherd Ching
 * @author Jian Cao
 * @author László Csontos
 */
public class DB2Dialect extends org.hibernate.dialect.DB2Dialect {

	public DB2Dialect() {
		registerKeyword("for");
		registerKeyword("optimize");
	}

	@Override
	public String getForUpdateString() {
		return " for read only with rs use and keep exclusive locks";
	}

	@Override
	public LimitHandler getLimitHandler() {
		return _db2LimitHandler;
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

		if (!hasOffset) {
			addQueryForLimitedRows(sb, sql, limit);

			return sb.toString();
		}

		// Outer query

		sb.append("SELECT outerQuery.* FROM (");

		// Inner query

		sb.append("SELECT innerQuery.*, ROW_NUMBER() OVER() AS rowNumber_ ");
		sb.append("FROM (");

		addQueryForLimitedRows(sb, sql, limit);

		sb.append(") AS innerQuery");

		// Offset

		sb.append(") AS outerQuery WHERE rowNumber_ > ");
		sb.append(offset);

		return sb.toString();
	}

	@Override
	public boolean supportsVariableLimit() {
		return _SUPPORTS_VARIABLE_LIMIT;
	}

	protected void addQueryForLimitedRows(
		StringBundler sb, String sql, int limit) {

		sb.append(sql);
		sb.append(StringPool.SPACE);
		sb.append(
			StringUtil.replace(
				_SQL_FETCH_FIRST_LIMITED_ROWS_ONLY, "[$LIMIT$]",
				String.valueOf(limit)));
	}

	private static final String _SQL_FETCH_FIRST_LIMITED_ROWS_ONLY =
		"FETCH FIRST [$LIMIT$] ROWS ONLY";

	private static final boolean _SUPPORTS_VARIABLE_LIMIT = false;

	private final DB2LimitHandler _db2LimitHandler = new DB2LimitHandler();

	private final class DB2LimitHandler extends AbstractLimitHandler {

		@Override
		public String processSql(String sql, RowSelection selection) {
			return getLimitString(
				sql, selection.getFirstRow(), getMaxOrLimit(selection));
		}

		@Override
		public boolean supportsLimit() {
			return true;
		}

		@Override
		public boolean supportsVariableLimit() {
			return false;
		}

		@Override
		public boolean useMaxForLimit() {
			return true;
		}

	}

}