/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactory;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class MappingSqlQueryFactoryImpl implements MappingSqlQueryFactory {

	@Override
	public <T> MappingSqlQuery<T> getMappingSqlQuery(
		DataSource dataSource, String sql, int[] types,
		RowMapper<T> rowMapper) {

		return new MappingSqlQueryImpl<T>(dataSource, sql, types, rowMapper);
	}

}