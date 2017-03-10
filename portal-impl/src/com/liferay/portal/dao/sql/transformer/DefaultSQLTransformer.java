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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.function.Function;

/**
 * @author Manuel de la Peña
 * @author Brian Wing Shun Chan
 */
public class DefaultSQLTransformer implements SQLTransformer {

	public DefaultSQLTransformer(Function<String, String>[] functions) {
		_functions = functions;
	}

	@Override
	public String transform(String sql) {
		if ((_functions == null) || (sql == null)) {
			return sql;
		}

		String transformedSQL = sql;

		for (Function<String, String> function : _functions) {
			transformedSQL = function.apply(transformedSQL);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Original SQL: " + sql);
			_log.debug("Transformed SQL: " + transformedSQL);
		}

		return transformedSQL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultSQLTransformer.class);

	private final Function<String, String>[] _functions;

}