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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
		String sqlLowerCase = StringUtil.toLowerCase(sql);

		int fromPos = sqlLowerCase.indexOf(" from ");

		String selectFrom = sql.substring(0, fromPos);

		int orderByPos = sqlLowerCase.lastIndexOf(" order by ");

		String selectFromWhere = null;

		String orderBy = StringPool.BLANK;

		if (orderByPos > 0) {
			selectFromWhere = sql.substring(fromPos, orderByPos);

			orderBy = sql.substring(orderByPos + 9);
		}
		else {
			selectFromWhere = sql.substring(fromPos);
		}

		String[] splitOrderBy = _splitOrderBy(selectFrom, orderBy);

		String innerOrderBy = splitOrderBy[0];
		String outerOrderBy = splitOrderBy[1];

		String innerSelectFrom = _getInnerSelectFrom(
			selectFrom, innerOrderBy, limit);

		return StringBundler.concat(
			"select * from (select *, row_number() over (", outerOrderBy,
			") as _page_row_num from (", innerSelectFrom, selectFromWhere,
			innerOrderBy,
			" ) _temp_table_1 ) _temp_table_2 where _page_row_num between ",
			offset + 1, " and ", limit, " order by _page_row_num");
	}

	private static String _getInnerSelectFrom(
		String selectFrom, String innerOrderBy, int limit) {

		String innerSelectFrom = selectFrom;

		if (Validator.isNotNull(innerOrderBy)) {
			Matcher matcher = _selectPattern.matcher(innerSelectFrom);

			innerSelectFrom = matcher.replaceAll(
				StringBundler.concat("$1 top ", limit, StringPool.SPACE));
		}

		return innerSelectFrom;
	}

	private static String[] _splitOrderBy(String selectFrom, String orderBy) {
		StringBundler innerOrderBySB = new StringBundler();
		StringBundler outerOrderBySB = new StringBundler();

		String[] orderByColumns = StringUtil.split(orderBy, CharPool.COMMA);

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

			String patternString = StringBundler.concat(
				"\\Q", orderByColumnName, "\\E as (\\w+)");

			Pattern pattern = Pattern.compile(
				patternString, Pattern.CASE_INSENSITIVE);

			Matcher matcher = pattern.matcher(selectFrom);

			if (matcher.find()) {
				orderByColumnName = matcher.group(1);
			}

			if (selectFrom.contains(orderByColumnName)) {
				if (outerOrderBySB.length() == 0) {
					outerOrderBySB.append(" order by ");
				}
				else {
					outerOrderBySB.append(StringPool.COMMA);
				}

				matcher = _qualifiedColumnPattern.matcher(orderByColumnName);

				orderByColumnName = matcher.replaceAll("$1");

				outerOrderBySB.append(orderByColumnName);

				outerOrderBySB.append(StringPool.SPACE);
				outerOrderBySB.append(orderByType);
			}
			else {
				if (innerOrderBySB.length() == 0) {
					innerOrderBySB.append(" order by ");
				}
				else {
					innerOrderBySB.append(StringPool.COMMA);
				}

				if (orderByColumnName.endsWith("ASC")) {
					orderByColumnName = orderByColumnName.substring(
						0, orderByColumnName.lastIndexOf("ASC"));
					orderByType = "ASC";
				}
				else if (orderByColumnName.endsWith("DESC")) {
					orderByColumnName = orderByColumnName.substring(
						0, orderByColumnName.lastIndexOf("DESC"));
					orderByType = "DESC";
				}

				innerOrderBySB.append(orderByColumnName);
				innerOrderBySB.append(StringPool.SPACE);
				innerOrderBySB.append(orderByType);
			}
		}

		if (outerOrderBySB.length() == 0) {
			outerOrderBySB.append(" order by CURRENT_TIMESTAMP");
		}

		return new String[] {
			innerOrderBySB.toString(), outerOrderBySB.toString()
		};
	}

	private static final Pattern _qualifiedColumnPattern = Pattern.compile(
		"\\w+\\.([\\w\\*]+)");
	private static final Pattern _selectPattern = Pattern.compile(
		"(SELECT(?: DISTINCT)?) ", Pattern.CASE_INSENSITIVE);

}