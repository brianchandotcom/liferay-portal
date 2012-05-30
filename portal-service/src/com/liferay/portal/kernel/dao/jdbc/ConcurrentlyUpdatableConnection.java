/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Minhchau Dang
 */
public class ConcurrentlyUpdatableConnection {

	public ConcurrentlyUpdatableConnection(Connection con) throws SQLException {

		_con = con;

		DatabaseMetaData metaData = con.getMetaData();

		_supportsResultSetConcurrency = metaData.supportsResultSetConcurrency(
			ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {

		PreparedStatement ps = null;

		if (_supportsResultSetConcurrency) {
			ps = _con.prepareStatement(
				sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		}
		else {
			ps = _con.prepareStatement(sql);
		}

		return ps;
	}

	private final Connection _con;
	private final boolean _supportsResultSetConcurrency;

}