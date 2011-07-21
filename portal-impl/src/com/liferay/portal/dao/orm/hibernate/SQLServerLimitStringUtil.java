/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Minhchau Dang
 * @author Steven Cao
 */
public class SQLServerLimitStringUtil {

	public static String getLimitString(String sql, int offset, int limit) {
		String sqlLowerCase = sql.toLowerCase();

		int fromPos = sqlLowerCase.indexOf(" from ");

		String selectFrom = sql.substring(0, fromPos);

		int orderByPos = sqlLowerCase.lastIndexOf(" order by ");

		String selectFromWhere = null;

		String orderBy = StringPool.BLANK;

		if (orderByPos > 0) {
			selectFromWhere = sql.substring(fromPos, orderByPos);
			orderBy = sql.substring(orderByPos + 9, sql.length());
		}
		else {
			selectFromWhere = sql.substring(fromPos);
		}

		String[] splitOrderBy = _splitOrderBy(selectFrom, orderBy);

		String innerOrderBy = splitOrderBy[0];
		String outerOrderBy = splitOrderBy[1];

		String[] splitSelectFrom = _splitSelectFrom(
			selectFrom, innerOrderBy, limit);

		String innerSelectFrom = splitSelectFrom[0];
		String outerSelectFrom = splitSelectFrom[1];

		StringBundler sb = new StringBundler(15);

		sb.append(outerSelectFrom);
		sb.append(" from (");
		sb.append(outerSelectFrom);
		sb.append(", row_number() over (");
		sb.append(outerOrderBy);
		sb.append(") as _page_row_num from (");
		sb.append(innerSelectFrom);
		sb.append(selectFromWhere);
		sb.append(innerOrderBy);
		sb.append(" ) _temp_table_1 ) _temp_table_2");
		sb.append(" where _page_row_num between ");
		sb.append(offset + 1);
		sb.append(" and ");
		sb.append(limit);
		sb.append(" order by _page_row_num");

		return sb.toString();
	}

	private static final String[] _splitOrderBy(
		String selectFrom, String orderBy) {

		StringBundler innerOrderBy = new StringBundler();
		StringBundler outerOrderBy = new StringBundler();

		String[] orderByColumns = StringUtil.split(orderBy, StringPool.COMMA);

		for (String orderByColumn : orderByColumns) {
			orderByColumn = orderByColumn.trim();

			String orderByColumnName = orderByColumn;
			String orderByType = "ASC";

			int spacePos = orderByColumn.lastIndexOf(CharPool.SPACE);

			if (spacePos != -1) {
				int parenPos = orderByColumn.indexOf(
					CharPool.OPEN_PARENTHESIS, spacePos);

				if (parenPos == -1) {
					orderByColumnName = orderByColumn.substring(0, spacePos);
					orderByType = orderByColumn.substring(spacePos + 1);
				}
			}

			String patternString = "\\Q".concat(orderByColumnName).concat(
				"\\E as (\\w+)");

			Pattern pattern = Pattern.compile(
				patternString, Pattern.CASE_INSENSITIVE);

			Matcher matcher = pattern.matcher(selectFrom);

			if (matcher.find()) {
				orderByColumnName = matcher.group(1);
			}

			if (selectFrom.contains(orderByColumnName)) {
				if (outerOrderBy.length() == 0) {
					outerOrderBy.append(" order by ");
				}
				else {
					outerOrderBy.append(StringPool.COMMA);
				}

				matcher = _QUALIFIED_COLUMN_PATTERN.matcher(orderByColumnName);

				orderByColumnName = matcher.replaceAll("$1");

				outerOrderBy.append(orderByColumnName);
				outerOrderBy.append(StringPool.SPACE);
				outerOrderBy.append(orderByType);
			}
			else {
				if (innerOrderBy.length() == 0) {
					innerOrderBy.append(" order by ");
				}
				else {
					innerOrderBy.append(StringPool.COMMA);
				}

				innerOrderBy.append(orderByColumnName);
				innerOrderBy.append(StringPool.SPACE);
				innerOrderBy.append(orderByType);
			}
		}

		if (outerOrderBy.length() == 0) {
			outerOrderBy.append(" order by CURRENT_TIMESTAMP");

		}

		return new String[] {
			innerOrderBy.toString(), outerOrderBy.toString()
		};
	}

	private static String[] _splitSelectFrom(
		String selectFrom, String innerOrderBy, int limit) {

		String innerSelectFrom = selectFrom;

		if (Validator.isNotNull(innerOrderBy)) {
			Matcher matcher = _SELECT_PATTERN.matcher(innerSelectFrom);

			innerSelectFrom = matcher.replaceAll(
				"select top ".concat(String.valueOf(limit)).concat(
					StringPool.SPACE));
		}

		String outerSelectFrom = selectFrom;

		while (outerSelectFrom.charAt(0) == CharPool.OPEN_PARENTHESIS) {
			outerSelectFrom = outerSelectFrom.substring(1);
		}

		Matcher matcher = _COLUMN_ALIAS_PATTERN.matcher(outerSelectFrom);

		outerSelectFrom = matcher.replaceAll("$1");

		matcher = _DISTINCT_PATTERN.matcher(outerSelectFrom);

		outerSelectFrom = matcher.replaceAll(StringPool.SPACE);

		matcher = _QUALIFIED_COLUMN_PATTERN.matcher(outerSelectFrom);

		outerSelectFrom = matcher.replaceAll("$1");

		return new String[] {
			innerSelectFrom, outerSelectFrom
		};
	}

	private static final Pattern _COLUMN_ALIAS_PATTERN = Pattern.compile(
		"[\\w\\.]+ AS (\\w+)", Pattern.CASE_INSENSITIVE);

	private static final Pattern _DISTINCT_PATTERN = Pattern.compile(
		" DISTINCT ", Pattern.CASE_INSENSITIVE);

	private static final Pattern _QUALIFIED_COLUMN_PATTERN = Pattern.compile(
		"\\w+\\.(\\w+)");

	private static final Pattern _SELECT_PATTERN = Pattern.compile(
		"SELECT ", Pattern.CASE_INSENSITIVE);

}