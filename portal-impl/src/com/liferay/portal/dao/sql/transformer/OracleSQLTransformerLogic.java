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
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Manuel de la Peña
 */
public class OracleSQLTransformerLogic extends BaseSQLTransformerLogic {

	public OracleSQLTransformerLogic(DB db) {
		super(db);

		Function[] functions = {
			getBooleanFunction(), getCastClobTextFunction(),
			getCastLongFunction(), getCastTextFunction(), getConcatFunction(),
			getIntegerDivisionFunction(), getNullDateFunction(),
			_getEscapeFunction(), _getNotEqualsBlankStringFunction()
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
				"CONCAT(", StringPool.BLANK, " || ", StringPool.BLANK);

		return sqlFunctionTransformer::transform;
	}

	@Override
	protected String replaceCastClobText(Matcher matcher) {
		return matcher.replaceAll("DBMS_LOB.SUBSTR($1, 4000, 1)");
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS VARCHAR(4000))");
	}

	@Override
	protected String replaceIntegerDivision(Matcher matcher) {
		return matcher.replaceAll("TRUNC($1 / $2)");
	}

	private Function<String, String> _getEscapeFunction() {
		return (String sql) -> StringUtil.replace(
			sql, "LIKE ?", "LIKE ? ESCAPE '\\'");
	}

	private Function<String, String> _getNotEqualsBlankStringFunction() {
		return (String sql) -> StringUtil.replace(
			sql, " != ''", " IS NOT NULL");
	}

}