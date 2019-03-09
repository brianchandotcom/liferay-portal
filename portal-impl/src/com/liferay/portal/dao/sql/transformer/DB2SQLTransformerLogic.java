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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.internal.dao.sql.transformer.SQLFunctionTransformer;
import com.liferay.portal.kernel.dao.db.DB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class DB2SQLTransformerLogic extends BaseSQLTransformerLogic {

	public DB2SQLTransformerLogic(DB db) {
		List<Function<String, String>> functions = new ArrayList<>(
			Arrays.asList(
				sql -> replaceBoolean(
					sql, db.getTemplateFalse(), db.getTemplateTrue()),
				DB2SQLTransformerLogic::_replaceCastClobText,
				BaseSQLTransformerLogic::replaceCastLong,
				DB2SQLTransformerLogic::_replaceCastText,
				DB2SQLTransformerLogic::_replaceConcat,
				BaseSQLTransformerLogic::replaceDropTableIfExistsText,
				BaseSQLTransformerLogic::replaceIntegerDivision,
				BaseSQLTransformerLogic::replaceNullDate,
				DB2SQLTransformerLogic::_replaceAlterColumnType,
				DB2SQLTransformerLogic::_replaceLike));

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions.add(BaseSQLTransformerLogic::replaceLower);
		}

		setFunctions(functions);
	}

	private static String _replaceAlterColumnType(String sql) {
		Matcher matcher = _alterColumnTypePattern.matcher(sql);

		return matcher.replaceAll(
			"ALTER TABLE $1 ALTER COLUMN $2 SET DATA TYPE $3");
	}

	private static String _replaceCastClobText(String sql) {
		Matcher matcher = castClobTestPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS VARCHAR(254))");
	}

	private static String _replaceCastText(String sql) {
		Matcher matcher = castTextPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS VARCHAR(254))");
	}

	private static String _replaceConcat(String sql) {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer(
				"CONCAT(", StringPool.BLANK, " CONCAT ", StringPool.BLANK);

		return sqlFunctionTransformer.transform(sql);
	}

	private static String _replaceLike(String sql) {
		Matcher matcher = _likePattern.matcher(sql);

		return matcher.replaceAll(
			"LIKE COALESCE(CAST(? AS VARCHAR(32672)),'')");
	}

	private static final Pattern _alterColumnTypePattern = Pattern.compile(
		"ALTER_COLUMN_TYPE\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)",
		Pattern.CASE_INSENSITIVE);
	private static final Pattern _likePattern = Pattern.compile(
		"LIKE \\?", Pattern.CASE_INSENSITIVE);

}