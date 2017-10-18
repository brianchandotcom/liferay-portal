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

package com.liferay.portal.dao.jdbc.spring;

import com.liferay.portal.kernel.dao.jdbc.ParamSetter;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SqlUpdateImpl implements SqlUpdate {

	public SqlUpdateImpl(
		DataSource dataSource, String sql, ParamSetter... paramSetters) {

		_dataSource = dataSource;
		_sql = sql;
		_paramSetters = paramSetters;
	}

	@Override
	public int update(Object... params) throws SQLException {
		if (_paramSetters.length != params.length) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Expected ", String.valueOf(_paramSetters.length),
					" parameters instead of ", String.valueOf(params.length),
					" parameters"));
		}

		try (Connection connection = ConnectionUtil.getConnection(_dataSource);
			PreparedStatement preparedStatement = connection.prepareStatement(
				_sql)) {

			for (int i = 0; i < _paramSetters.length; i++) {
				ParamSetter paramSetter = _paramSetters[i];

				paramSetter.set(preparedStatement, i + 1, params[i]);
			}

			return preparedStatement.executeUpdate();
		}
	}

	private final DataSource _dataSource;
	private final ParamSetter[] _paramSetters;
	private final String _sql;

}