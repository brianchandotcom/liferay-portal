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

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class DB2Transformer extends BaseSQLTransformer {

	public DB2Transformer() {
		transformations.add(bitwiseCheckDefaultTransformation);
		transformations.add(booleanTransformation);
		transformations.add(castClobTextTransformation);
		transformations.add(castLongTransformation);
		transformations.add(castTextTransformation);
		transformations.add(crossJoinDefaultTransformation);
		transformations.add(inStrDefaultTransformation);
		transformations.add(integerDivisionTransformation);
		transformations.add(_likeTransformation);
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS VARCHAR(254))");
	}

	private static final Pattern _likePattern = Pattern.compile(
		"LIKE \\?", Pattern.CASE_INSENSITIVE);

	private Function<String, String> _likeTransformation = (String sql) -> {
		Matcher matcher = _likePattern.matcher(sql);

		return matcher.replaceAll(
			"LIKE COALESCE(CAST(? AS VARCHAR(32672)),'')");
	};

}