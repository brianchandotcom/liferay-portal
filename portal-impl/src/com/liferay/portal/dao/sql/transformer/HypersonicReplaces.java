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
public class HypersonicReplaces {

	public static void addReplaces(List<Function<String, String>> functions) {
		Collections.addAll(
			functions, CommonReplaces::replaceIntegerDivision,
			CommonReplaces::replaceNullDate,
			HypersonicReplaces::_replaceCastClobText,
			HypersonicReplaces::_replaceCastLong,
			HypersonicReplaces::_replaceCastText,
			HypersonicReplaces::_replaceDropTableIfExistsText);
	}

	private static String _replaceCastClobText(String sql) {
		Matcher matcher = CommonReplaces.castClobTestPattern.matcher(sql);

		return matcher.replaceAll("CONVERT($1, SQL_VARCHAR)");
	}

	private static String _replaceCastLong(String sql) {
		Matcher matcher = CommonReplaces.castLongPattern.matcher(sql);

		return matcher.replaceAll("CONVERT($1, SQL_BIGINT)");
	}

	private static String _replaceCastText(String sql) {
		Matcher matcher = CommonReplaces.castTextPattern.matcher(sql);

		return matcher.replaceAll("CONVERT($1, SQL_VARCHAR)");
	}

	private static String _replaceDropTableIfExistsText(String sql) {
		Matcher matcher = CommonReplaces.dropTableIfExistsTextPattern.matcher(
			sql);

		return matcher.replaceAll("DROP TABLE $1 IF EXISTS");
	}

}