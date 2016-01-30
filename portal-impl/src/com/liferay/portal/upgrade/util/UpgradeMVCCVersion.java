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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.io.InputStream;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class UpgradeMVCCVersion extends UpgradeProcess {

	public void upgradeMVCCVersion(
			DatabaseMetaData databaseMetaData, String tableName)
		throws Exception {

		for (String excludeTableName : getExcludedTableNames()) {
			if (excludeTableName.equals(tableName)) {
				return;
			}
		}

		ResultSet tableResultSet = databaseMetaData.getTables(
			null, null, tableName, null);

		try {
			if (!tableResultSet.next()) {
				_log.error("Table " + tableName + " does not exist");

				return;
			}

			ResultSet columnResultSet = databaseMetaData.getColumns(
				null, null, tableName,
				normalizeName("mvccVersion", databaseMetaData));

			try {
				if (columnResultSet.next()) {
					return;
				}

				runSQL(
					"alter table " + tableName +
						" add mvccVersion LONG not null default 0");

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Added column mvccVersion to table " + tableName);
				}
			}
			finally {
				DataAccess.cleanUp(columnResultSet);
			}
		}
		finally {
			DataAccess.cleanUp(tableResultSet);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		List<Element> classElements = getClassElements();

		for (Element classElement : classElements) {
			if (classElement.element("version") == null) {
				continue;
			}

			upgradeMVCCVersion(databaseMetaData, classElement);
		}

		String[] moduleTableNames = getModuleTableNames();

		for (String moduleTableName : moduleTableNames) {
			upgradeMVCCVersion(databaseMetaData, moduleTableName);
		}
	}

	protected List<Element> getClassElements() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"META-INF/portal-hbm.xml");

		Document document = UnsecureSAXReaderUtil.read(inputStream);

		Element rootElement = document.getRootElement();

		return rootElement.elements("class");
	}

	protected String[] getExcludedTableNames() {
		return new String[0];
	}

	protected String[] getModuleTableNames() {
		return new String[] {"BackgroundTask", "Lock_"};
	}

	protected String normalizeName(
			String name, DatabaseMetaData databaseMetaData)
		throws SQLException {

		if (databaseMetaData.storesLowerCaseIdentifiers()) {
			return StringUtil.toLowerCase(name);
		}

		if (databaseMetaData.storesUpperCaseIdentifiers()) {
			return StringUtil.toUpperCase(name);
		}

		return name;
	}

	protected void upgradeMVCCVersion(
			DatabaseMetaData databaseMetaData, Element classElement)
		throws Exception {

		String tableName = classElement.attributeValue("table");

		upgradeMVCCVersion(databaseMetaData, tableName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeMVCCVersion.class);

}