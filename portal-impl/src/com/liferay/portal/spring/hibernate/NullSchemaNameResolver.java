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

package com.liferay.portal.spring.hibernate;

import java.sql.Connection;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.SchemaNameResolver;

/**
 * @author Shuyang Zhou
 */
public class NullSchemaNameResolver implements SchemaNameResolver {

	@Override
	public String resolveSchemaName(Connection connection, Dialect dialect) {
		return null;
	}

}