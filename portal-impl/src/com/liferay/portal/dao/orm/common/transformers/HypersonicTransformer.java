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
public class HypersonicTransformer extends BaseSQLTransformer {

	public HypersonicTransformer() {
		transformations.add(bitwiseCheckDefaultTransformation);
		transformations.add(castClobTextTransformation);
		transformations.add(_castLongTransformation);
		transformations.add(crossJoinDefaultTransformation);
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CONVERT($1, SQL_VARCHAR)");
	}

	private Function<String, String> _castLongTransformation = (String sql) -> {
		Matcher matcher = castLongPattern.matcher(sql);

		return matcher.replaceAll("CONVERT($1, SQL_BIGINT)");
	};

}