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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class SybaseTransformationProvider implements SQLTransformationProvider {

	@Override
	public Function<String, String>[] getTransformations() {
		return _transformations;
	}

	private static String _replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS NVARCHAR(5461))");
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

	private final Function<String, String> _castLongTransformation =
		(String sql) -> {
			Matcher matcher = PortalSQLTransformer.castLongPattern.matcher(sql);

			return matcher.replaceAll("CONVERT(BIGINT, $1)");
		};

	private final Function<String, String> _crossJoinTransformation =
		(String sql) -> StringUtil.replace(sql, "CROSS JOIN", StringPool.COMMA);

	private final Function<String, String> _inStrTransformation =
		(String sql) -> {
			Matcher matcher = PortalSQLTransformer.instrPattern.matcher(sql);

			return matcher.replaceAll("CHARINDEX($2, $1)");
		};

	private final Function<String, String> _modTransformation =
		(String sql) -> {
			Matcher matcher = PortalSQLTransformer.modPattern.matcher(sql);

			return matcher.replaceAll("$1 % $2");
		};

	private final Function<String, String> _replaceTransformation =
		(String sql) -> sql.replaceAll("(?i)replace\\(", "str_replace(");

	private final Function<String, String> _substrTransformation =
		(String sql) -> {
			Matcher matcher = PortalSQLTransformer.substrPattern.matcher(sql);

			return matcher.replaceAll("SUBSTRING($1, $2, $3)");
		};

	private final Function<String, String>[] _transformations = new Function[] {
		PortalSQLTransformer.bitwiseCheckTransformation,
		PortalSQLTransformer.booleanTransformation, _castClobTextTransformation,
		_castLongTransformation, _castTextTransformation,
		_crossJoinTransformation, _inStrTransformation,
		PortalSQLTransformer.integerDivisionTransformation,
		PortalSQLTransformer.nullDateTransformation, _substrTransformation,
		_modTransformation, _replaceTransformation
	};

}