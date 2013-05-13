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

package com.liferay.portal.dao.jdbc;

import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Shuyang Zhou
 */
@DoPrivileged
public class CurrentConnectionImpl implements CurrentConnection {

	@Override
	public Connection getConnection(DataSource dataSource) {
		ConnectionHolder connectionHolder =
			(ConnectionHolder)TransactionSynchronizationManager.getResource(
				dataSource);

		if (connectionHolder == null) {
			return null;
		}
		else {
			return connectionHolder.getConnection();
		}
	}

}