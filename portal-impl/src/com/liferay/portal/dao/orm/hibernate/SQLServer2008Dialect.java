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

package com.liferay.portal.dao.orm.hibernate;

import java.sql.Types;

import org.hibernate.type.StandardBasicTypes;

/**
 * @author Minhchau Dang
 */
public class SQLServer2008Dialect
	extends org.hibernate.dialect.SQLServer2008Dialect {

	public SQLServer2008Dialect() {
		registerHibernateType(
			Types.NVARCHAR, StandardBasicTypes.STRING.getName());
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		return SQLServerLimitStringUtil.getLimitString(sql, offset, limit);
	}

	@Override
	public boolean supportsLimitOffset() {
		return _SUPPORTS_LIMIT_OFFSET;
	}

	@Override
	public boolean supportsVariableLimit() {
		return _SUPPORTS_VARIABLE_LIMIT;
	}

	private static final boolean _SUPPORTS_LIMIT_OFFSET = true;

	private static final boolean _SUPPORTS_VARIABLE_LIMIT = false;

}