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
import com.liferay.portal.kernel.dao.jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;

/**
 * @author Brian Wing Shun Chan
 */
public class MappingSqlQueryImpl<T>
	extends org.springframework.jdbc.object.MappingSqlQuery<T>
	implements MappingSqlQuery<T> {

	public MappingSqlQueryImpl(
		DataSource dataSource, String sql, int[] types,
		RowMapper<T> rowMapper) {

		super(dataSource, sql);

		for (int type : types) {
			declareParameter(new SqlParameter(type));
		}

		_rowMapper = rowMapper;

		compile();
	}

	@Override
	protected T mapRow(ResultSet rs, int rowNumber) throws SQLException {
		return _rowMapper.mapRow(rs, rowNumber);
	}

	private final RowMapper<T> _rowMapper;

}