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
public class MySQLReplaces {

	public static void addReplaces(List<Function<String, String>> functions) {
		Collections.addAll(
			functions, CommonReplaces::replaceBitwiseCheck,
			CommonReplaces::replaceCastClobText,
			CommonReplaces::replaceCastLong, CommonReplaces::replaceCastText,
			CommonReplaces::replaceDropTableIfExistsText,
			CommonReplaces::replaceNullDate,
			MySQLReplaces::_replaceIntegerDivision);
	}

	private static String _replaceIntegerDivision(String sql) {
		Matcher matcher = CommonReplaces.integerDivisionPattern.matcher(sql);

		return matcher.replaceAll("$1 DIV $2");
	}

}