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

package com.liferay.portal.kernel.dao.db;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

/**
 * @author Hugo Huijser
 * @author Brian Wing Shun Chan
 */
public interface DBProcess {

	public void runSQL(Connection connection, String template)
		throws IOException, SQLException;

	/**
	 * @throws IOException
	 * @throws SQLException
	 */
	public default void runSQL(DBTypeToSQLMap dbTypeToSQLMap)
		throws IOException, SQLException {

		throw new UnsupportedOperationException();
	}

	public void runSQL(String template) throws IOException, SQLException;

	public void runSQL(String[] templates) throws IOException, SQLException;

	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException;

	public void runSQLTemplateString(
			String template, boolean evaluate, boolean failOnError)
		throws IOException, NamingException, SQLException;

}