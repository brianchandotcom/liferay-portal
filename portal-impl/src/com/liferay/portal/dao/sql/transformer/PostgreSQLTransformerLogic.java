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

package com.liferay.portal.dao.sql.transformer;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class PostgreSQLTransformerLogic {

	public static List<Function<String, String>> getFunctions(DB db) {
		List<Function<String, String>> functions = new ArrayList<>(
			Arrays.asList(
				BaseSQLTransformerLogic::replaceBitwiseCheck,
				sql -> BaseSQLTransformerLogic.replaceBoolean(
					sql, db.getTemplateFalse(), db.getTemplateTrue()),
				PostgreSQLTransformerLogic::_replaceCastClobText,
				PostgreSQLTransformerLogic::_replaceCastLong,
				PostgreSQLTransformerLogic::_replaceCastText,
				BaseSQLTransformerLogic::replaceDropTableIfExistsText,
				PostgreSQLTransformerLogic::_replaceInstr,
				BaseSQLTransformerLogic::replaceIntegerDivision,
				PostgreSQLTransformerLogic::_replaceNegativeComparison,
				PostgreSQLTransformerLogic::_replaceNullDate));

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions.add(BaseSQLTransformerLogic::replaceLower);
		}

		return functions;
	}

	private static String _replaceCastClobText(String sql) {
		Matcher matcher = BaseSQLTransformerLogic.castClobTestPattern.matcher(
			sql);

		return matcher.replaceAll("CAST($1 AS TEXT)");
	}

	private static String _replaceCastLong(String sql) {
		Matcher matcher = BaseSQLTransformerLogic.castLongPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS INTEGER)");
	}

	private static String _replaceCastText(String sql) {
		Matcher matcher = BaseSQLTransformerLogic.castTextPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS TEXT)");
	}

	private static String _replaceInstr(String sql) {
		Matcher matcher = BaseSQLTransformerLogic.instrPattern.matcher(sql);

		return matcher.replaceAll("POSITION($2 in $1)");
	}

	private static String _replaceNegativeComparison(String sql) {
		Matcher matcher = _negativeComparisonPattern.matcher(sql);

		return matcher.replaceAll("$1 ($2)");
	}

	private static String _replaceNullDate(String sql) {
		return StringUtil.replace(
			sql, "[$NULL_DATE$]", "CAST(NULL AS TIMESTAMP)");
	}

	private static final Pattern _negativeComparisonPattern = Pattern.compile(
		"(!?=)\\s*(-([0-9]+)?)", Pattern.CASE_INSENSITIVE);

}