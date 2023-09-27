/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(enabled = false, service = {})
public class QuartzSchemaManager {

	@Activate
	protected void activate() {
		try (Connection connection = _dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from QUARTZ_JOB_DETAILS");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return;
			}
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(exception);
			}
		}

		try (Connection connection = _dataSource.getConnection()) {
			_populateSchema(connection);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _populateSchema(Connection connection) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"/META-INF/sql/quartz-tables.sql");

		if (inputStream == null) {
			throw new SystemException(
				"Unable to read /META-INF/sql/quartz-tables.sql");
		}

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

	private static final Log _log = LogFactoryUtil.getLog(
		QuartzSchemaManager.class);

	@Reference(target = "(&(bean.id=liferayDataSource)(original.bean=true))")
	private DataSource _dataSource;

}