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
 * @author Brian Wing Shun Chan
 */
public class HypersonicSQLTransformerLogic {

	public static List<Function<String, String>> getFunctions(DB db) {
		List<Function<String, String>> functions = new ArrayList<>(
			Arrays.asList(
				sql -> BaseSQLTransformerLogic.replaceBoolean(
					sql, db.getTemplateFalse(), db.getTemplateTrue()),
				HypersonicSQLTransformerLogic::_replaceCastClobText,
				HypersonicSQLTransformerLogic::_replaceCastLong,
				HypersonicSQLTransformerLogic::_replaceCastText,
				HypersonicSQLTransformerLogic::_replaceDropTableIfExistsText,
				BaseSQLTransformerLogic::replaceIntegerDivision,
				BaseSQLTransformerLogic::replaceNullDate));

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions.add(BaseSQLTransformerLogic::replaceLower);
		}

		return functions;
	}

	private static String _replaceCastClobText(String sql) {
		Matcher matcher = BaseSQLTransformerLogic.castClobTestPattern.matcher(
			sql);

		return matcher.replaceAll("CONVERT($1, SQL_VARCHAR)");
	}

	private static String _replaceCastLong(String sql) {
		Matcher matcher = BaseSQLTransformerLogic.castLongPattern.matcher(sql);

		return matcher.replaceAll("CONVERT($1, SQL_BIGINT)");
	}

	private static String _replaceCastText(String sql) {
		Matcher matcher = BaseSQLTransformerLogic.castTextPattern.matcher(sql);

		return matcher.replaceAll("CONVERT($1, SQL_VARCHAR)");
	}

	private static String _replaceDropTableIfExistsText(String sql) {
		Matcher matcher =
			BaseSQLTransformerLogic.dropTableIfExistsTextPattern.matcher(sql);

		return matcher.replaceAll("DROP TABLE $1 IF EXISTS");
	}

}