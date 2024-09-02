/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.schema.importer.jdbc;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DataSourceFactoryUtil {

	public static int getBatchSize() {
		return _batchSize;
	}

	public static int getFetchSize() {
		return _fetchSize;
	}

	public static DataSource initDataSource(
			String jdbcURL, String password, String userName)
		throws Exception {

		return initDataSource(jdbcURL, password, userName, null);
	}

	public static DataSource initDataSource(
			String jdbcURL, String password, String userName,
			String partitionName)
		throws Exception {

		String driverClassName = "com.mysql.cj.jdbc.Driver";

		if (jdbcURL.indexOf("postgresql") > 0) {
			driverClassName = "org.postgresql.Driver";
		}

		Class.forName(driverClassName);

		HikariConfig hikariConfig = new HikariConfig();

		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(jdbcURL);
		hikariConfig.setPassword(password);
		hikariConfig.setUsername(userName);

		if (partitionName != null) {
			if (StringUtil.equals(driverClassName, "org.postgresql.Driver")) {
				hikariConfig.setSchema(partitionName);
			}
			else {
				hikariConfig.setCatalog(partitionName);
			}
		}

		hikariConfig.setConnectionTimeout(30000);
		hikariConfig.setIdleTimeout(600000);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMaxLifetime(0);
		hikariConfig.setMinimumIdle(10);
		hikariConfig.setTransactionIsolation("TRANSACTION_READ_UNCOMMITTED");

		return new HikariDataSource(hikariConfig);
	}

	public static void setBatchSize(String batchSize) {
		_batchSize = GetterUtil.get(batchSize, _DEFAULT_BATCH_SIZE);
	}

	public static void setFetchSize(String fetchSize) {
		_fetchSize = GetterUtil.get(fetchSize, _DEFAULT_FETCH_SIZE);
	}

	private static final int _DEFAULT_BATCH_SIZE = 2500;

	private static final int _DEFAULT_FETCH_SIZE = 2500;

	private static int _batchSize;
	private static int _fetchSize;

}