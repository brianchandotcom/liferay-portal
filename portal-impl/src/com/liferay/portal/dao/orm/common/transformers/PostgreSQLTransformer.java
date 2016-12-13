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

package com.liferay.portal.dao.orm.common.transformers;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class PostgreSQLTransformer extends BaseSQLTransformer {

	public PostgreSQLTransformer() {
		transformations.add(bitwiseCheckTransformation);
		transformations.add(castClobTextTransformation);
		transformations.add(_negativeComparisonTransformation);
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS TEXT)");
	}

	@Override
	protected String replaceInStr(String sql) {
		Matcher matcher = instrPattern.matcher(sql);

		return matcher.replaceAll("POSITION($2 in $1)");
	}

	@Override
	protected String replaceNullDate(String sql) {
		return StringUtil.replace(
			sql, "[$NULL_DATE$]", "CAST(NULL AS TIMESTAMP)");
	}

	private static final Pattern _negativeComparisonPattern = Pattern.compile(
		"(!?=)( -([0-9]+)?)", Pattern.CASE_INSENSITIVE);

	private Function<String, String> _negativeComparisonTransformation =
		(String sql) ->  {
			Matcher matcher = _negativeComparisonPattern.matcher(sql);

			return matcher.replaceAll("$1 ($2)");
		};

}