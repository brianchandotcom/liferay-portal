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

import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class SqlUpdateFactoryImpl implements SqlUpdateFactory {

	@Override
	public SqlUpdate getSqlUpdate(
		DataSource dataSource, String sql, int[] types) {

		return new SqlUpdateImpl(dataSource, sql, types);
	}

}