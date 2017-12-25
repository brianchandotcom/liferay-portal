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

package com.liferay.portal.spring.hibernate;

import com.liferay.petra.reflect.ReflectionUtil;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Shuyang Zhou
 */
public class LastSessionRecorderHibernateTransactionManager
	extends HibernateTransactionManager {

	@Override
	protected Object doGetTransaction() {
		SessionHolder sessionHolder =
			SpringHibernateThreadLocalUtil.getResource(getSessionFactory());

		if (sessionHolder != null) {
			LastSessionRecorderUtil.setLastSession(sessionHolder.getSession());
		}

		return super.doGetTransaction();
	}

	static {
		try {
			Class.forName(SpringHibernateThreadLocalUtil.class.getName());

			Log dummyLog = new Log() {

				@Override
				public void debug(Object object) {
				}

				@Override
				public void debug(Object object, Throwable throwable) {
				}

				@Override
				public void error(Object object) {
				}

				@Override
				public void error(Object object, Throwable throwable) {
				}

				@Override
				public void fatal(Object object) {
				}

				@Override
				public void fatal(Object object, Throwable throwable) {
				}

				@Override
				public void info(Object object) {
				}

				@Override
				public void info(Object object, Throwable throwable) {
				}

				@Override
				public boolean isDebugEnabled() {
					return false;
				}

				@Override
				public boolean isErrorEnabled() {
					return false;
				}

				@Override
				public boolean isFatalEnabled() {
					return false;
				}

				@Override
				public boolean isInfoEnabled() {
					return false;
				}

				@Override
				public boolean isTraceEnabled() {
					return false;
				}

				@Override
				public boolean isWarnEnabled() {
					return false;
				}

				@Override
				public void trace(Object object) {
				}

				@Override
				public void trace(Object object, Throwable throwable) {
				}

				@Override
				public void warn(Object object) {
				}

				@Override
				public void warn(Object object, Throwable throwable) {
				}

			};

			Field loggerField = ReflectionUtil.getDeclaredField(
				TransactionSynchronizationManager.class, "logger");

			loggerField.set(null, dummyLog);

			loggerField = ReflectionUtil.getDeclaredField(
				SessionFactoryUtils.class, "logger");

			loggerField.set(null, dummyLog);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}