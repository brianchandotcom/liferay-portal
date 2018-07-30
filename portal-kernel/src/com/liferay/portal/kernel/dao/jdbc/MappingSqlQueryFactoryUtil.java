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

package com.liferay.portal.kernel.dao.jdbc;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 */
public class MappingSqlQueryFactoryUtil {

	public static <T> MappingSqlQuery<T> getMappingSqlQuery(
		DataSource dataSource, String sql, RowMapper<T> rowMapper,
		ParamSetter... paramSetters) {

		return getMappingSqlQueryFactory().getMappingSqlQuery(
			dataSource, sql, rowMapper, paramSetters);
	}

	public static MappingSqlQueryFactory getMappingSqlQueryFactory() {
		return _mappingSqlUpdateFactory;
	}

	public void setMappingSqlQueryFactory(
		MappingSqlQueryFactory mappingSqlUpdateFactory) {

		_mappingSqlUpdateFactory = mappingSqlUpdateFactory;
	}

	private static MappingSqlQueryFactory _mappingSqlUpdateFactory;

}