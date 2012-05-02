/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.mail.Session;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class InfrastructureUtil {

	public static DataSource getDataSource() {
		return _dataSource;
	}

	public static Object getDynamicDataSourceTargetSource() {
		return _dynamicDataSourceTargetSource;
	}

	public static Session getMailSession() {
		return _mailSession;
	}

	public static Object getShardDataSourceTargetSource() {
		return _shardDataSourceTargetSource;
	}

	public static Object getShardSessionFactoryTargetSource() {
		return _shardSessionFactoryTargetSource;
	}

	public static Object getTransactionManager() {
		return _transactionManager;
	}

	public void setDataSource(DataSource dataSource) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_dataSource = dataSource;
	}

	public void setDynamicDataSourceTargetSource(
		Object dynamicDataSourceTargetSource) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_dynamicDataSourceTargetSource = dynamicDataSourceTargetSource;
	}

	public void setMailSession(Session mailSession) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_mailSession = mailSession;
	}

	public void setShardDataSourceTargetSource(
		Object shardDataSourceTargetSource) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_shardDataSourceTargetSource = shardDataSourceTargetSource;
	}

	public void setShardSessionFactoryTargetSource(
		Object shardSessionFactoryTargetSource) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_shardSessionFactoryTargetSource = shardSessionFactoryTargetSource;
	}

	public void setTransactionManager(Object transactionManager) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_transactionManager = transactionManager;
	}

	private static DataSource _dataSource;
	private static Object _dynamicDataSourceTargetSource;
	private static Session _mailSession;
	private static Object _shardDataSourceTargetSource;
	private static Object _shardSessionFactoryTargetSource;
	private static Object _transactionManager;

}