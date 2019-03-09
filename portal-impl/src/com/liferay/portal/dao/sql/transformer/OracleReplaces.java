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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Shuyang Zhou
 */
public class OracleReplaces {

	public static List<Function<String, String>> getReplaces(DB db) {
		List<Function<String, String>> functions = new ArrayList<>(
			Arrays.asList(
				sql -> CommonReplaces.replaceBoolean(
					sql, db.getTemplateFalse(), db.getTemplateTrue()),
				OracleReplaces::_replaceCastClobText,
				CommonReplaces::replaceCastLong,
				OracleReplaces::_replaceCastText,
				OracleReplaces::_replaceConcat,
				OracleReplaces::_replaceDropTableIfExistsText,
				OracleReplaces::_replaceIntegerDivision,
				CommonReplaces::replaceNullDate, OracleReplaces::_replaceEscape,
				OracleReplaces::_replaceNotEqualsBlankString));

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions.add(CommonReplaces::replaceLower);
		}

		return functions;
	}

	private static String _replaceCastClobText(String sql) {
		Matcher matcher = CommonReplaces.castClobTestPattern.matcher(sql);

		return matcher.replaceAll("DBMS_LOB.SUBSTR($1, 4000, 1)");
	}

	private static String _replaceCastText(String sql) {
		Matcher matcher = CommonReplaces.castTextPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS VARCHAR(4000))");
	}

	private static String _replaceConcat(String sql) {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer(
				"CONCAT(", StringPool.BLANK, " || ", StringPool.BLANK);

		return sqlFunctionTransformer.transform(sql);
	}

	private static String _replaceDropTableIfExistsText(String sql) {
		Matcher matcher = CommonReplaces.dropTableIfExistsTextPattern.matcher(
			sql);

		StringBundler sb = new StringBundler(9);

		sb.append("BEGIN\n");
		sb.append("EXECUTE IMMEDIATE 'DROP TABLE $1';\n");
		sb.append("EXCEPTION\n");
		sb.append("WHEN OTHERS THEN\n");
		sb.append("IF SQLCODE != -942 THEN\n");
		sb.append("RAISE;\n");
		sb.append("END IF;\n");
		sb.append("END;\n");
		sb.append("/");

		String dropTableIfExists = sb.toString();

		return matcher.replaceAll(dropTableIfExists);
	}

	private static String _replaceEscape(String sql) {
		return StringUtil.replace(sql, "LIKE ?", "LIKE ? ESCAPE '\\'");
	}

	private static String _replaceIntegerDivision(String sql) {
		Matcher matcher = CommonReplaces.integerDivisionPattern.matcher(sql);

		return matcher.replaceAll("TRUNC($1 / $2)");
	}

	private static String _replaceNotEqualsBlankString(String sql) {
		return StringUtil.replace(sql, " != ''", " IS NOT NULL");
	}

}