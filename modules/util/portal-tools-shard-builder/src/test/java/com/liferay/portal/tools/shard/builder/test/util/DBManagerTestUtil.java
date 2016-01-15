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

package com.liferay.portal.tools.shard.builder.test.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Manuel de la Peña
 */
public class DBManagerTestUtil {

	public static int execute(DataSource dataSource, String sql, Object... args)
		throws SQLException {

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(sql) ) {

			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];

				ps.setObject(i + 1, arg);
			}

			return ps.executeUpdate();
		}
	}

	public static boolean ping(DataSource dataSource, String sql)
		throws SQLException {

		try (Connection con = dataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
					ResultSet rs = ps.executeQuery() ) {

			return rs.next();
		}
	}

}