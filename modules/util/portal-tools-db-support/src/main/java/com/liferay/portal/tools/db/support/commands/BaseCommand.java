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

package com.liferay.portal.tools.db.support.commands;

import com.liferay.portal.tools.db.support.DBSupportArgs;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseCommand implements Command {

	@Override
	public void execute(DBSupportArgs dbSupportArgs) throws Exception {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
				dbSupportArgs.getUrl(), dbSupportArgs.getUserName(),
				dbSupportArgs.getPassword());

			connection.setAutoCommit(false);

			execute(connection);

			connection.commit();
		}
		catch (Exception e) {
			if (connection != null) {
				connection.rollback();
			}

			throw e;
		}
	}

	protected abstract void execute(Connection connection) throws Exception;

}