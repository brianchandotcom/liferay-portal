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

import com.liferay.portal.internal.dao.sql.transformer.SQLFunctionTransformer;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class DB2SQLTransformerLogic extends BaseSQLTransformerLogic {

	public DB2SQLTransformerLogic(DB db) {
		super(db);

		Function[] functions = {
			getBooleanFunction(), getCastClobTextFunction(),
			getCastLongFunction(), getCastTextFunction(), getConcatFunction(),
			getIntegerDivisionFunction(), getNullDateFunction(),
			_getAlterColumnTypeFunction(), _getLikeFunction()
		};

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions = ArrayUtil.append(functions, getLowerFunction());
		}

		setFunctions(functions);
	}

	@Override
	protected Function<String, String> getConcatFunction() {
		SQLFunctionTransformer sqlFunctionTransformer =
			new SQLFunctionTransformer(
				"CONCAT(", StringPool.BLANK, " CONCAT ", StringPool.BLANK);

		return sqlFunctionTransformer::transform;
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS VARCHAR(254))");
	}

	private Function<String, String> _getAlterColumnTypeFunction() {
		return (String sql) -> {
			Matcher matcher = _alterColumnTypePattern.matcher(sql);

			return matcher.replaceAll(
				"ALTER TABLE $1 ALTER COLUMN $2 SET DATA TYPE $3");
		};
	}

	private Function<String, String> _getLikeFunction() {
		return (String sql) -> {
			Matcher matcher = _likePattern.matcher(sql);

			return matcher.replaceAll(
				"LIKE COALESCE(CAST(? AS VARCHAR(32672)),'')");
		};
	}

	private static final Pattern _alterColumnTypePattern = Pattern.compile(
		"ALTER_COLUMN_TYPE\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)",
		Pattern.CASE_INSENSITIVE);
	private static final Pattern _likePattern = Pattern.compile(
		"LIKE \\?", Pattern.CASE_INSENSITIVE);

}