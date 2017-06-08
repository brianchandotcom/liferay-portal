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

package com.liferay.portal.dao.orm.common.transformation.function.provider;

import com.liferay.portal.dao.orm.common.PortalSQLTransformer;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class HypersonicTransformationFunctionProvider
	implements SQLTransformationFunctionProvider {

	@Override
	public Function<String, String>[] getTransformationFunctions() {
		return _transformationFunctions;
	}

	private static String _replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CONVERT($1, SQL_VARCHAR)");
	}

	private static final Function<String, String>
		_castClobTextTransformationFunction = (String sql) -> {
			Pattern castClobTextPattern =
				PortalSQLTransformer.castClobTextPattern;

			return _replaceCastText(castClobTextPattern.matcher(sql));
		};

	private static final Function<String, String>
		_castTextTransformationFunction = (String sql) -> {
			Pattern castTextPattern = PortalSQLTransformer.castTextPattern;

			return _replaceCastText(castTextPattern.matcher(sql));
		};

	private final Function<String, String> _castLongTransformationFunction =
		(String sql) -> {
			Matcher matcher = PortalSQLTransformer.castLongPattern.matcher(sql);

			return matcher.replaceAll("CONVERT($1, SQL_BIGINT)");
		};

	private final Function<String, String>[] _transformationFunctions =
		new Function[] {
			PortalSQLTransformer.bitwiseCheckDefaultTransformationFunction,
			PortalSQLTransformer.booleanTransformationFunction,
			_castClobTextTransformationFunction,
			_castLongTransformationFunction, _castTextTransformationFunction,
			PortalSQLTransformer.crossJoinDefaultTransformationFunction,
			PortalSQLTransformer.inStrDefaultTransformationFunction,
			PortalSQLTransformer.integerDivisionTransformationFunction,
			PortalSQLTransformer.nullDateTransformationFunction,
			PortalSQLTransformer.substrDefaultTransformationFunction
		};

}