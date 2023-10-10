/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal.upgrade.schema;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.sql.Connection;

/**
 * @author Shuyang Zhou
 */
public class SchemaCreationUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade() throws UpgradeException {
		ClassLoader classLoader =
			SchemaCreationUpgradeStep.class.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"/META-INF/sql/quartz-tables.sql");

		if (inputStream == null) {
			throw new SystemException(
				"Unable to read /META-INF/sql/quartz-tables.sql");
		}

		try (Connection connection = DataAccess.getConnection()) {
			String template = StringUtil.read(inputStream);

			DB db = DBManagerUtil.getDB();

			boolean autoCommit = connection.getAutoCommit();

			try {
				connection.setAutoCommit(false);

				db.runSQLTemplateString(connection, template, false);

				connection.commit();
			}
			catch (Exception exception) {
				connection.rollback();

				throw exception;
			}
			finally {
				connection.setAutoCommit(autoCommit);
			}
		}
		catch (Exception exception) {
			throw new UpgradeException(exception);
		}
	}

}