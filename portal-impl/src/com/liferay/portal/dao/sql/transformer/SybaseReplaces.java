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
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Shuyang Zhou
 */
public class SybaseReplaces {

	public static List<Function<String, String>> getReplaces(DB db) {
		List<Function<String, String>> functions = new ArrayList<>(
			Arrays.asList(
				CommonReplaces::replaceBitwiseCheck,
				sql -> CommonReplaces.replaceBoolean(
					sql, db.getTemplateFalse(), db.getTemplateTrue()),
				SybaseReplaces::_replaceCastClobText,
				SybaseReplaces::_replaceCastLong,
				SybaseReplaces::_replaceCastText, CommonReplaces::replaceConcat,
				CommonReplaces::replaceDropTableIfExistsText,
				CommonReplaces::replaceInstr,
				CommonReplaces::replaceIntegerDivision,
				CommonReplaces::replaceLength, CommonReplaces::replaceMod,
				CommonReplaces::replaceNullDate, CommonReplaces::replaceSubstr,
				SybaseReplaces::_replaceCrossJoin,
				SybaseReplaces::_replaceReplace));

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions.add(CommonReplaces::replaceLower);
		}

		return functions;
	}

	private static String _replaceCastClobText(String sql) {
		Matcher matcher = CommonReplaces.castClobTestPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS NVARCHAR(5461))");
	}

	private static String _replaceCastLong(String sql) {
		Matcher matcher = CommonReplaces.castLongPattern.matcher(sql);

		return matcher.replaceAll("CONVERT(BIGINT, $1)");
	}

	private static String _replaceCastText(String sql) {
		Matcher matcher = CommonReplaces.castTextPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS NVARCHAR(5461))");
	}

	private static String _replaceCrossJoin(String sql) {
		return StringUtil.replace(sql, "CROSS JOIN", StringPool.COMMA);
	}

	private static String _replaceReplace(String sql) {
		return sql.replaceAll("(?i)replace\\(", "str_replace(");
	}

}