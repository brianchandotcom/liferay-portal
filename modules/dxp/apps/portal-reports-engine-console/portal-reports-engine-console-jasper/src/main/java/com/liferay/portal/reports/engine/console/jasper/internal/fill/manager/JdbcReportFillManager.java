/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	immediate = true, property = "reportDataSourceType=jdbc",
	service = ReportFillManager.class
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