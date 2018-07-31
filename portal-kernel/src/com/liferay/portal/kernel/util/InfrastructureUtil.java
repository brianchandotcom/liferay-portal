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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.dao.jdbc.aop.DynamicDataSourceTargetSource;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

import javax.mail.Session;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@OSGiBeanProperties(service = InfrastructureUtil.class)
public class InfrastructureUtil {

	public static DataSource getDataSource() {
		return _dataSource;
	}

	public static DynamicDataSourceTargetSource
		getDynamicDataSourceTargetSource() {

		return _dynamicDataSourceTargetSource;
	}

	public static Session getMailSession() {
		return _mailSession;
	}

	public static Object getTransactionManager() {
		return _transactionManager;
	}

	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	public void setDynamicDataSourceTargetSource(
		DynamicDataSourceTargetSource dynamicDataSourceTargetSource) {

		_dynamicDataSourceTargetSource = dynamicDataSourceTargetSource;
	}

	public void setMailSession(Session mailSession) {
		_mailSession = mailSession;
	}

	public void setTransactionManager(Object transactionManager) {
		_transactionManager = transactionManager;
	}

	private static DataSource _dataSource;
	private static DynamicDataSourceTargetSource _dynamicDataSourceTargetSource;
	private static Session _mailSession;
	private static Object _transactionManager;

}