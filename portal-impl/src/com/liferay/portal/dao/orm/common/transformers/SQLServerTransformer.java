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

/**
 * @author Manuel de la Peña
 */
public class SQLServerTransformer extends BaseSQLTransformer {

	public SQLServerTransformer() {
		transformations.add(bitwiseCheckTransformation);
		transformations.add(booleanTransformation);
		transformations.add(castClobTextTransformation);
		transformations.add(castLongTransformation);
		transformations.add(castTextTransformation);
		transformations.add(crossJoinDefaultTransformation);
		transformations.add(_inStrTransformation);
		transformations.add(integerDivisionTransformation);
		transformations.add(nullDateTransformation);
		transformations.add(_substrTransformation);
		transformations.add(_modTransformation);
		transformations.add(replaceTransformation);
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS NVARCHAR(MAX))");
	}

	private Function<String, String> _inStrTransformation = (String sql) -> {
		Matcher matcher = instrPattern.matcher(sql);

		return matcher.replaceAll("CHARINDEX($2, $1)");
	};

	private Function<String, String> _modTransformation = (String sql) -> {
		Matcher matcher = modPattern.matcher(sql);

		return matcher.replaceAll("$1 % $2");
	};

	private Function<String, String> _substrTransformation = (String sql) -> {
		Matcher matcher = substrPattern.matcher(sql);

		return matcher.replaceAll("SUBSTRING($1, $2, $3)");
	};

}