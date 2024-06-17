/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.persistence.internal.upgrade.schema;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.release.BaseSchemaCreationUpgradeStep;

import java.sql.Connection;

/**
 * @author Mariano Álvaro Sáiz
 */
public class SchemaCreationUpgradeStep extends BaseSchemaCreationUpgradeStep {

	public SchemaCreationUpgradeStep() {
		super(
			"create table Configuration_ (configurationId VARCHAR(512) not " +
				"null primary key, dictionary TEXT);");
	}

	@Override
	public void upgrade() throws UpgradeException {
		try (Connection connection = DataAccess.getConnection()) {
			DB db = DBManagerUtil.getDB();

			db.runSQLTemplate(connection, sqlTemplate, false);
		}
		catch (Exception exception) {
			throw new UpgradeException(exception);
		}
	}

}