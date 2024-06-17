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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.release.BaseSchemaCreationUpgradeStep;

import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;

/**
 * @author Shuyang Zhou
 */
public class SchemaCreationUpgradeStep extends BaseSchemaCreationUpgradeStep {

	public SchemaCreationUpgradeStep() {
		super(_getSQLTemplate());
	}

	@Override
	public void upgrade() throws UpgradeException {
		try (Connection connection = DataAccess.getConnection()) {
			DB db = DBManagerUtil.getDB();

			boolean autoCommit = connection.getAutoCommit();

			try {
				connection.setAutoCommit(false);

				db.runSQLTemplate(connection, sqlTemplate, false);

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

	private static String _getSQLTemplate() {
		ClassLoader classLoader =
			SchemaCreationUpgradeStep.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"/META-INF/sql/quartz-tables.sql")) {

			if (inputStream == null) {
				throw new SystemException(
					"Unable to read /META-INF/sql/quartz-tables.sql");
			}

			return StringUtil.read(inputStream);
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

}