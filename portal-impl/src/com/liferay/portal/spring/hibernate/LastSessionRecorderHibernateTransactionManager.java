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

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
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

			Field loggerField = ReflectionUtil.getDeclaredField(
				TransactionSynchronizationManager.class, "logger");

			loggerField.set(
				null,
				new Log() {

					@Override
					public void debug(Object o) {
					}

					@Override
					public void debug(Object o, Throwable t) {
					}

					@Override
					public void error(Object o) {
					}

					@Override
					public void error(Object o, Throwable t) {
					}

					@Override
					public void fatal(Object o) {
					}

					@Override
					public void fatal(Object o, Throwable t) {
					}

					@Override
					public void info(Object o) {
					}

					@Override
					public void info(Object o, Throwable t) {
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
					public void trace(Object o) {
					}

					@Override
					public void trace(Object o, Throwable t) {
					}

					@Override
					public void warn(Object o) {
					}

					@Override
					public void warn(Object o, Throwable t) {
					}

				});
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}