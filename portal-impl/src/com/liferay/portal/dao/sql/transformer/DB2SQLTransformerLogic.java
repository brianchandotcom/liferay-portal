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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.internal.dao.sql.transformer.SQLFunctionTransformer;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.util.ArrayUtil;

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
			getDropTableIfExistsTextFunction(), getIntegerDivisionFunction(),
			getNullDateFunction(), _getLikeFunction()
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

	@Override
	protected String replaceDropTableIfExistsText(Matcher matcher) {
		StringBundler sb = new StringBundler(5);

		sb.append("BEGIN\n");
		sb.append("DECLARE CONTINUE HANDLER FOR SQLSTATE '42704'\n");
		sb.append("BEGIN END;\n");
		sb.append("EXECUTE IMMEDIATE 'DROP TABLE $1';\n");
		sb.append("END");

		String dropTableIfExists = sb.toString();

		return matcher.replaceAll(dropTableIfExists);
	}

	private Function<String, String> _getLikeFunction() {
		return (String sql) -> {
			Matcher matcher = _likePattern.matcher(sql);

			return matcher.replaceAll(
				"LIKE COALESCE(CAST(? AS VARCHAR(32672)),'')");
		};
	}

	private static final Pattern _likePattern = Pattern.compile(
		"LIKE \\?", Pattern.CASE_INSENSITIVE);

}