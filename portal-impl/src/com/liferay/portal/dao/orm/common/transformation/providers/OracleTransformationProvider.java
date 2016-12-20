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
public class OracleTransformationProvider implements SQLTransformationProvider {

	@Override
	public Function<String, String>[] getTransformations() {
		return _transformations;
	}

	private static String _replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS VARCHAR(4000))");
	}

	private static final Function<String, String> _castTextTransformation =
		(String sql) -> {
			Pattern castTextPattern = PortalSQLTransformer.castTextPattern;

			return _replaceCastText(castTextPattern.matcher(sql));
		};

	private final Function<String, String> _castClobTextTransformation =
		(String sql) -> {
			Pattern castClobTextPattern =
				PortalSQLTransformer.castClobTextPattern;

			Matcher matcher = castClobTextPattern.matcher(sql);

			return matcher.replaceAll("DBMS_LOB.SUBSTR($1, 4000, 1)");
		};

	private final Function<String, String> _escapeTransformation =
		(String sql) -> StringUtil.replace(sql, "LIKE ?", "LIKE ? ESCAPE '\\'");

	private final Function<String, String> _integerDivisionTransformation =
		(String sql) -> {
			Pattern integerDivisionPattern =
				PortalSQLTransformer.integerDivisionPattern;

			Matcher matcher = integerDivisionPattern.matcher(sql);

			return matcher.replaceAll("TRUNC($1 / $2)");
		};

	private final Function<String, String> _notEqualsBlankStringTransformation =
		(String sql) -> StringUtil.replace(sql, " != ''", " IS NOT NULL");
	private final Function<String, String>[] _transformations = new Function[] {
		PortalSQLTransformer.bitwiseCheckDefaultTransformation,
		PortalSQLTransformer.booleanTransformation, _castClobTextTransformation,
		PortalSQLTransformer.castLongTransformation, _castTextTransformation,
		PortalSQLTransformer.crossJoinDefaultTransformation,
		PortalSQLTransformer.inStrDefaultTransformation,
		_integerDivisionTransformation,
		PortalSQLTransformer.nullDateTransformation,
		PortalSQLTransformer.substrDefaultTransformation, _escapeTransformation,
		_notEqualsBlankStringTransformation
	};

}