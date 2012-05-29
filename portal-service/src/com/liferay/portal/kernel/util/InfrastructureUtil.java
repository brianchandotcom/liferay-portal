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

import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.mail.Session;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class InfrastructureUtil implements PACLConstants {

	public static DataSource getDataSource() {
		PortalRuntimePermission.checkGetBeanProperty(
			InfrastructureUtil.class, PORTAL_RUNTIME_PERMISSION_DATA_SOURCE);

		return _dataSource;
	}

	public static Object getDynamicDataSourceTargetSource() {
		PortalRuntimePermission.checkGetBeanProperty(
			InfrastructureUtil.class,
			PORTAL_RUNTIME_PERMISSION_DYNAMIC_DATA_SOURCE_TARGET_SOURCE);

		return _dynamicDataSourceTargetSource;
	}

	public static Session getMailSession() {
		PortalRuntimePermission.checkGetBeanProperty(
			InfrastructureUtil.class, PORTAL_RUNTIME_PERMISSION_MAIL_SESSION);

		return _mailSession;
	}

	public static Object getShardDataSourceTargetSource() {
		PortalRuntimePermission.checkGetBeanProperty(
			InfrastructureUtil.class,
			PORTAL_RUNTIME_PERMISSION_SHARD_DATA_SOURCE_TARGET_SOURCE);

		return _shardDataSourceTargetSource;
	}

	public static Object getShardSessionFactoryTargetSource() {
		PortalRuntimePermission.checkGetBeanProperty(
			InfrastructureUtil.class,
			PORTAL_RUNTIME_PERMISSION_SHARD_SESSION_FACTORY_TARGET_SOURCE);

		return _shardSessionFactoryTargetSource;
	}

	public static Object getTransactionManager() {
		PortalRuntimePermission.checkGetBeanProperty(
			InfrastructureUtil.class,
			PORTAL_RUNTIME_PERMISSION_TRANSACTION_MANAGER);

		return _transactionManager;
	}

	public void setDataSource(DataSource dataSource) {
		PortalRuntimePermission.checkSetBeanProperty(
			getClass(), PORTAL_RUNTIME_PERMISSION_DATA_SOURCE);

		_dataSource = dataSource;
	}

	public void setDynamicDataSourceTargetSource(
		Object dynamicDataSourceTargetSource) {

		PortalRuntimePermission.checkSetBeanProperty(
			getClass(),
			PORTAL_RUNTIME_PERMISSION_DYNAMIC_DATA_SOURCE_TARGET_SOURCE);

		_dynamicDataSourceTargetSource = dynamicDataSourceTargetSource;
	}

	public void setMailSession(Session mailSession) {
		PortalRuntimePermission.checkSetBeanProperty(
			getClass(), PORTAL_RUNTIME_PERMISSION_MAIL_SESSION);

		_mailSession = mailSession;
	}

	public void setShardDataSourceTargetSource(
		Object shardDataSourceTargetSource) {

		PortalRuntimePermission.checkSetBeanProperty(
			getClass(),
			PORTAL_RUNTIME_PERMISSION_SHARD_DATA_SOURCE_TARGET_SOURCE);

		_shardDataSourceTargetSource = shardDataSourceTargetSource;
	}

	public void setShardSessionFactoryTargetSource(
		Object shardSessionFactoryTargetSource) {

		PortalRuntimePermission.checkSetBeanProperty(
			getClass(),
			PORTAL_RUNTIME_PERMISSION_SHARD_SESSION_FACTORY_TARGET_SOURCE);

		_shardSessionFactoryTargetSource = shardSessionFactoryTargetSource;
	}

	public void setTransactionManager(Object transactionManager) {
		PortalRuntimePermission.checkSetBeanProperty(
			getClass(), PORTAL_RUNTIME_PERMISSION_TRANSACTION_MANAGER);

		_transactionManager = transactionManager;
	}

	private static DataSource _dataSource;
	private static Object _dynamicDataSourceTargetSource;
	private static Session _mailSession;
	private static Object _shardDataSourceTargetSource;
	private static Object _shardSessionFactoryTargetSource;
	private static Object _transactionManager;

}