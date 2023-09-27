/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public void runSQLTemplateString(String template, boolean failOnError)
		throws IOException, NamingException, SQLException;

}