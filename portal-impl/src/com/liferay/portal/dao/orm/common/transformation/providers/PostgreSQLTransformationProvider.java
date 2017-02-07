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

package com.liferay.portal.dao.orm.common.transformation.providers;

import com.liferay.portal.dao.orm.common.transformation.PortalSQLTransformer;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class PostgreSQLTransformationProvider
	implements SQLTransformationProvider {

	@Override
	public Function<String, String>[] getTransformations() {
		return _transformations;
	}

	private static String _replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS TEXT)");
	}

	private static final Function<String, String> _castClobTextTransformation =
		(String sql) -> {
			Pattern castClobTextPattern =
				PortalSQLTransformer.castClobTextPattern;

			return _replaceCastText(castClobTextPattern.matcher(sql));
		};

	private static final Function<String, String> _castTextTransformation =
		(String sql) -> {
			Pattern castTextPattern = PortalSQLTransformer.castTextPattern;

			return _replaceCastText(castTextPattern.matcher(sql));
		};

	private static final Pattern _negativeComparisonPattern = Pattern.compile(
		"(!?=)( -([0-9]+)?)", Pattern.CASE_INSENSITIVE);

	private final Function<String, String> _inStrTransformation =
		(String sql) -> {
			Pattern instrPattern = PortalSQLTransformer.instrPattern;

			Matcher matcher = instrPattern.matcher(sql);

			return matcher.replaceAll("POSITION($2 in $1)");
		};

	private final Function<String, String> _negativeComparisonTransformation =
		(String sql) -> {
			Matcher matcher = _negativeComparisonPattern.matcher(sql);

			return matcher.replaceAll("$1 ($2)");
		};

	private final Function<String, String> _nullDateTransformation =
		(String sql) ->
			StringUtil.replace(sql, "[$NULL_DATE$]", "CAST(NULL AS TIMESTAMP)");
	private final Function<String, String>[] _transformations = new Function[] {
		PortalSQLTransformer.bitwiseCheckTransformation,
		PortalSQLTransformer.booleanTransformation, _castClobTextTransformation,
		PortalSQLTransformer.castLongTransformation, _castTextTransformation,
		PortalSQLTransformer.crossJoinDefaultTransformation,
		_inStrTransformation,
		PortalSQLTransformer.integerDivisionTransformation,
		_nullDateTransformation,
		PortalSQLTransformer.substrDefaultTransformation,
		_negativeComparisonTransformation
	};

}