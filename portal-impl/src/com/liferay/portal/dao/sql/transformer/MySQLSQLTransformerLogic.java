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
public class MySQLSQLTransformerLogic extends BaseSQLTransformerLogic {

	public MySQLSQLTransformerLogic(DB db) {
		List<Function<String, String>> functions = new ArrayList<>(
			Arrays.asList(
				BaseSQLTransformerLogic::replaceBitwiseCheck,
				sql -> replaceBoolean(
					sql, db.getTemplateFalse(), db.getTemplateTrue()),
				BaseSQLTransformerLogic::replaceCastClobText,
				BaseSQLTransformerLogic::replaceCastLong,
				BaseSQLTransformerLogic::replaceCastText,
				BaseSQLTransformerLogic::replaceDropTableIfExistsText,
				MySQLSQLTransformerLogic::_replaceIntegerDivision,
				BaseSQLTransformerLogic::replaceNullDate));

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions.add(BaseSQLTransformerLogic::replaceLower);
		}

		setFunctions(functions);
	}

	private static String _replaceIntegerDivision(String sql) {
		Matcher matcher = integerDivisionPattern.matcher(sql);

		return matcher.replaceAll("$1 DIV $2");
	}

}