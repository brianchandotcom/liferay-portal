/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.jasper.internal.fill.manager;

import com.liferay.portal.reports.engine.ReportRequest;
import com.liferay.portal.reports.engine.ReportRequestContext;

import java.sql.Connection;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "reportDataSourceType=jdbc", service = ReportFillManager.class
)
public class JdbcReportFillManager extends BaseReportFillManager {

	@Override
	protected Connection getConnection(ReportRequest reportRequest)
		throws Exception {

		Properties properties = new Properties();

		ReportRequestContext reportRequestContext =
			reportRequest.getReportRequestContext();

		_setProperty(
			properties, "driverClassName", reportRequestContext,
			ReportRequestContext.JDBC_DRIVER_CLASS);
		_setProperty(
			properties, "password", reportRequestContext,
			ReportRequestContext.JDBC_PASSWORD);
		_setProperty(
			properties, "url", reportRequestContext,
			ReportRequestContext.JDBC_URL);
		_setProperty(
			properties, "username", reportRequestContext,
			ReportRequestContext.JDBC_USER_NAME);

		DataSource dataSource = BasicDataSourceFactory.createDataSource(
			properties);

		return dataSource.getConnection();
	}

	private void _setProperty(
		Properties properties, String propertyKey,
		ReportRequestContext reportRequestContext, String attributeKey) {

		String value = (String)reportRequestContext.getAttribute(attributeKey);

		properties.put(propertyKey, value);
	}

}