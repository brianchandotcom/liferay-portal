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

package com.liferay.portal.dao.jdbc;

import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.spring.hibernate.SpringHibernateThreadLocalUtil;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.ConnectionHolder;

/**
 * @author Shuyang Zhou
 */
public class CurrentConnectionImpl implements CurrentConnection {

	@Override
	public Connection getConnection(DataSource dataSource) {
		ConnectionHolder connectionHolder =
			(ConnectionHolder)SpringHibernateThreadLocalUtil.getResource(
				dataSource);

		if ((connectionHolder == null) ||
			(connectionHolder.getConnectionHandle() == null)) {

			return null;
		}

		return connectionHolder.getConnection();
	}

}