/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (Exception exception) {
			if (connection != null) {
				connection.rollback();
			}

			throw exception;
		}
		finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	protected abstract void execute(Connection connection) throws Exception;

}