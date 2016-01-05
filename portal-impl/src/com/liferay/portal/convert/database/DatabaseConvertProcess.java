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

package com.liferay.portal.convert.database;

import com.liferay.portal.convert.BaseConvertProcess;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;

import java.util.List;

import javax.sql.DataSource;

/**
 * @author Alexander Chow
 */
public class DatabaseConvertProcess extends BaseConvertProcess {

	public void destroy() {
		_databaseConverters.clear();

		_databaseConverters = null;
	}

	@Override
	public String getDescription() {
		return "migrate-data-from-one-database-to-another";
	}

	@Override
	public String getParameterDescription() {
		return "please-enter-jdbc-information-for-new-database";
	}

	@Override
	public String[] getParameterNames() {
		return new String[] {
			"jdbc-driver-class-name", "jdbc-url", "jdbc-user-name",
			"jdbc-password"
		};
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	protected DataSource buildDatasource() throws Exception {
		String[] values = getParameterValues();

		String driverClassName = values[0];
		String url = values[1];
		String userName = values[2];
		String password = values[3];
		String jndiName = StringPool.BLANK;

		return DataSourceFactoryUtil.initDataSource(
			driverClassName, url, userName, password, jndiName);
	}

	@Override
	protected void doConvert() throws Exception {
		MaintenanceUtil.appendStatus("Starting database migration.");

		DataSource dataSource = buildDatasource();

		for (DatabaseConverter databaseConverter : _databaseConverters) {
			databaseConverter.convert(dataSource);
		}

		MaintenanceUtil.appendStatus(
			"Please change your JDBC settings before restarting server");

		ShutdownUtil.shutdown(0);
	}

	private List<DatabaseConverter> _databaseConverters =
		ServiceTrackerCollections.openList(DatabaseConverter.class);

}