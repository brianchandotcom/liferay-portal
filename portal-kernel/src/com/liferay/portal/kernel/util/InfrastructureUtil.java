/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
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

	public static Session getMailSession() {
		return _createMailSession();
	}

	public static Object getSessionFactory() {
		try {
			return _sessionFactoryDefaultNoticeableFuture.get();
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	public static Object getTransactionManager() {
		try {
			return _transactionManagerDefaultNoticeableFuture.get();
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	public static void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	public static void setSessionFactory(Object sessionFactory) {
		_sessionFactoryDefaultNoticeableFuture.set(sessionFactory);
	}

	public static void setTransactionManager(Object transactionManager) {
		_transactionManagerDefaultNoticeableFuture.set(transactionManager);
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
			catch (Exception exception) {
				_log.error("Unable to lookup " + jndiName, exception);
			}
		}

		return Session.getInstance(properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfrastructureUtil.class);

	private static DataSource _dataSource;
	private static final DefaultNoticeableFuture<Object>
		_sessionFactoryDefaultNoticeableFuture =
			new DefaultNoticeableFuture<>();
	private static final DefaultNoticeableFuture<Object>
		_transactionManagerDefaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

}