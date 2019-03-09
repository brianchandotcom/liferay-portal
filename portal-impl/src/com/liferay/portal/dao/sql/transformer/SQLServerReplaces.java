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

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Shuyang Zhou
 */
public class SQLServerReplaces {

	public static void addReplaces(List<Function<String, String>> functions) {
		Collections.addAll(
			functions, CommonReplaces::replaceBitwiseCheck,
			SQLServerReplaces::_replaceCastClobText,
			CommonReplaces::replaceCastLong,
			SQLServerReplaces::_replaceCastText, CommonReplaces::replaceConcat,
			SQLServerReplaces::_replaceDropTableIfExistsText,
			CommonReplaces::replaceInstr,
			CommonReplaces::replaceIntegerDivision,
			CommonReplaces::replaceLength, CommonReplaces::replaceMod,
			CommonReplaces::replaceNullDate, CommonReplaces::replaceSubstr);
	}

	private static String _replaceCastClobText(String sql) {
		Matcher matcher = CommonReplaces.castClobTestPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS NVARCHAR(MAX))");
	}

	private static String _replaceCastText(String sql) {
		Matcher matcher = CommonReplaces.castTextPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS NVARCHAR(MAX))");
	}

	private static String _replaceDropTableIfExistsText(String sql) {
		Matcher matcher = CommonReplaces.dropTableIfExistsTextPattern.matcher(
			sql);

		String dropTableIfExists =
			"IF OBJECT_ID('$1', 'U') IS NOT NULL DROP TABLE $1";

		return matcher.replaceAll(dropTableIfExists);
	}

}