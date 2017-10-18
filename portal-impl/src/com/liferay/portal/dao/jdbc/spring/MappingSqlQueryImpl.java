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

import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.ParamSetter;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MappingSqlQueryImpl<T> implements MappingSqlQuery<T> {

	public MappingSqlQueryImpl(
		DataSource dataSource, String sql, RowMapper<T> rowMapper,
		ParamSetter... paramSetters) {

		_dataSource = dataSource;
		_sql = sql;
		_rowMapper = rowMapper;
		_paramSetters = paramSetters;
	}

	@Override
	public List<T> execute(Object... params) throws SQLException {
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

			List<T> results = null;

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					if (results == null) {
						results = new ArrayList<>();
					}

					results.add(_rowMapper.mapRow(rs));
				}
			}

			if (results == null) {
				results = Collections.emptyList();
			}

			return results;
		}
	}

	private final DataSource _dataSource;
	private final ParamSetter[] _paramSetters;
	private final RowMapper<T> _rowMapper;
	private final String _sql;

}