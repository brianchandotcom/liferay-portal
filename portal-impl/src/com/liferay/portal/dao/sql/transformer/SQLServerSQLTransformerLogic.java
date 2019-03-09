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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Manuel de la Peña
 */
public class SQLServerSQLTransformerLogic {

	public static List<Function<String, String>> getFunctions(DB db) {
		List<Function<String, String>> functions = new ArrayList<>(
			Arrays.asList(
				BaseSQLTransformerLogic::replaceBitwiseCheck,
				sql -> BaseSQLTransformerLogic.replaceBoolean(
					sql, db.getTemplateFalse(), db.getTemplateTrue()),
				SQLServerSQLTransformerLogic::_replaceCastClobText,
				BaseSQLTransformerLogic::replaceCastLong,
				SQLServerSQLTransformerLogic::_replaceCastText,
				BaseSQLTransformerLogic::replaceConcat,
				SQLServerSQLTransformerLogic::_replaceDropTableIfExistsText,
				BaseSQLTransformerLogic::replaceInstr,
				BaseSQLTransformerLogic::replaceIntegerDivision,
				BaseSQLTransformerLogic::replaceLength,
				BaseSQLTransformerLogic::replaceMod,
				BaseSQLTransformerLogic::replaceNullDate,
				BaseSQLTransformerLogic::replaceSubstr));

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions.add(BaseSQLTransformerLogic::replaceLower);
		}

		return functions;
	}

	private static String _replaceCastClobText(String sql) {
		Matcher matcher = BaseSQLTransformerLogic.castClobTestPattern.matcher(
			sql);

		return matcher.replaceAll("CAST($1 AS NVARCHAR(MAX))");
	}

	private static String _replaceCastText(String sql) {
		Matcher matcher = BaseSQLTransformerLogic.castTextPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS NVARCHAR(MAX))");
	}

	private static String _replaceDropTableIfExistsText(String sql) {
		Matcher matcher =
			BaseSQLTransformerLogic.dropTableIfExistsTextPattern.matcher(sql);

		String dropTableIfExists =
			"IF OBJECT_ID('$1', 'U') IS NOT NULL DROP TABLE $1";

		return matcher.replaceAll(dropTableIfExists);
	}

}