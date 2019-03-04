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
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Manuel de la Peña
 */
public class SybaseSQLTransformerLogic extends BaseSQLTransformerLogic {

	public SybaseSQLTransformerLogic(DB db) {
		super(db);

		Function[] functions = {
			getBitwiseCheckFunction(), getBooleanFunction(),
			getCastClobTextFunction(), getCastLongFunction(),
			getCastTextFunction(), getConcatFunction(),
			getDropTableIfExistsTextFunction(), getInstrFunction(),
			getIntegerDivisionFunction(), getLengthFunction(), getModFunction(),
			getNullDateFunction(), getSubstrFunction(), _getCrossJoinFunction(),
			_getReplaceFunction()
		};

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions = ArrayUtil.append(functions, getLowerFunction());
		}

		setFunctions(functions);
	}

	@Override
	protected String replaceCastLong(Matcher matcher) {
		return matcher.replaceAll("CONVERT(BIGINT, $1)");
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS NVARCHAR(5461))");
	}

	private Function<String, String> _getCrossJoinFunction() {
		return (String sql) -> StringUtil.replace(
			sql, "CROSS JOIN", StringPool.COMMA);
	}

	private Function<String, String> _getReplaceFunction() {
		return (String sql) -> sql.replaceAll("(?i)replace\\(", "str_replace(");
	}

}