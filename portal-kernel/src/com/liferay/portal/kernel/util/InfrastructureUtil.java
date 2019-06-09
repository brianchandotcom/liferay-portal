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
import com.liferay.portal.kernel.jndi.JNDIUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

import java.util.Properties;

import javax.mail.Session;

import javax.naming.Context;
import javax.naming.InitialContext;

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
		if (_mailSession == null) {
			_mailSession = _createMailSession();
		}

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

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public void setMailSession(Session mailSession) {
	}

	public void setTransactionManager(Object transactionManager) {
		_transactionManager = transactionManager;
	}

	private static Session _createMailSession() {
		Properties properties = PropsUtil.getProperties("mail.session.", true);

		String jndiName = properties.getProperty("jndi.name");

		if (Validator.isNotNull(jndiName)) {
			try {
				Properties jndiEnvironmentProperties = PropsUtil.getProperties(
					PropsKeys.JNDI_ENVIRONMENT, true);

				Context context = new InitialContext(jndiEnvironmentProperties);

				return (Session)JNDIUtil.lookup(context, jndiName);
			}
			catch (Exception e) {
				_log.error("Unable to lookup " + jndiName, e);
			}
		}

		return Session.getInstance(properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfrastructureUtil.class);

	private static DataSource _dataSource;
	private static DynamicDataSourceTargetSource _dynamicDataSourceTargetSource;
	private static Session _mailSession;
	private static Object _transactionManager;

}