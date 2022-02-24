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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Dialect;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.hibernate.PortletTransactionManager;
import com.liferay.portal.spring.transaction.TransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionExecutorThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.sql.Connection;

import java.util.function.Function;

import org.hibernate.engine.SessionFactoryImplementor;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
public class VerifySessionFactoryWrapper implements SessionFactory {

	public static SessionFactory createVerifySessionFactoryWrapper(
		SessionFactoryImpl sessionFactoryImpl) {

		if (PropsValues.SPRING_HIBERNATE_SESSION_FACTORY_VERIFY) {
			return new VerifySessionFactoryWrapper(sessionFactoryImpl);
		}

		return sessionFactoryImpl;
	}

	public VerifySessionFactoryWrapper(SessionFactoryImpl sessionFactoryImpl) {
		_sessionFactoryImpl = sessionFactoryImpl;
	}

	@Override
	public void closeSession(Session session) throws ORMException {
		if (session == null) {
			return;
		}

		if (PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED &&
			ProxyUtil.isProxyClass(session.getClass())) {

			InvocationHandler invocationHandler =
				ProxyUtil.getInvocationHandler(session);

			if (invocationHandler.getClass() ==
					SessionInvocationHandler.class) {

				session.flush();
			}
		}

		_sessionFactoryImpl.closeSession(session);
	}

	@Override
	public Session getCurrentSession() throws ORMException {
		return _sessionFactoryImpl.getCurrentSession();
	}

	@Override
	public Dialect getDialect() throws ORMException {
		return _sessionFactoryImpl.getDialect();
	}

	@Override
	public Session openNewSession(Connection connection) throws ORMException {
		return _sessionFactoryImpl.openNewSession(connection);
	}

	@Override
	public Session openSession() throws ORMException {
		if (!PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED || _verify()) {
			return _sessionFactoryImpl.openSession();
		}

		Session session = _sessionFactoryImpl.openSession();

		return _sessionProxyProviderFunction.apply(
			new SessionInvocationHandler(session));
	}

	private void _logFailure(
		SessionFactoryImplementor currentSessionFactoryImplementor,
		SessionFactoryImplementor targetSessionFactoryImplementor) {

		_log.error(
			"Failed session factory verification",
			new IllegalStateException(
				StringBundler.concat(
					"Wrong current transaction manager, current session ",
					"factory classes metadata: ",
					currentSessionFactoryImplementor.getAllClassMetadata(),
					", target session factory classes metadata: ",
					targetSessionFactoryImplementor.getAllClassMetadata())));
	}

	private boolean _verify() {
		TransactionExecutor transactionExecutor =
			TransactionExecutorThreadLocal.getCurrentTransactionExecutor();

		if (transactionExecutor == null) {
			throw new IllegalStateException("No current transaction executor");
		}

		PlatformTransactionManager platformTransactionManager =
			transactionExecutor.getPlatformTransactionManager();

		if (platformTransactionManager == null) {
			throw new IllegalStateException(
				"No transaction manager for transaction executor: " +
					transactionExecutor);
		}

		SessionFactoryImplementor targetSessionFactoryImplementor =
			_sessionFactoryImpl.getSessionFactoryImplementor();

		if (platformTransactionManager instanceof HibernateTransactionManager) {
			HibernateTransactionManager hibernateTransactionManager =
				(HibernateTransactionManager)platformTransactionManager;

			SessionFactoryImplementor currentSessionFactoryImplementor =
				(SessionFactoryImplementor)
					hibernateTransactionManager.getSessionFactory();

			if (targetSessionFactoryImplementor ==
					currentSessionFactoryImplementor) {

				return true;
			}

			_logFailure(
				currentSessionFactoryImplementor,
				targetSessionFactoryImplementor);

			return false;
		}

		if (platformTransactionManager instanceof PortletTransactionManager) {
			PortletTransactionManager portletTransactionManager =
				(PortletTransactionManager)platformTransactionManager;

			SessionFactoryImplementor currentSessionFactoryImplementor =
				(SessionFactoryImplementor)
					portletTransactionManager.getPortletSessionFactory();

			if (targetSessionFactoryImplementor ==
					currentSessionFactoryImplementor) {

				return true;
			}

			_logFailure(
				currentSessionFactoryImplementor,
				targetSessionFactoryImplementor);

			return false;
		}

		throw new IllegalStateException(
			"Unknown transaction manager type: " + platformTransactionManager);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifySessionFactoryWrapper.class);

	private static final Function<InvocationHandler, Session>
		_sessionProxyProviderFunction = ProxyUtil.getProxyProviderFunction(
			Session.class);

	private final SessionFactoryImpl _sessionFactoryImpl;

	private static class SessionInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws ReflectiveOperationException {

			return method.invoke(_session, args);
		}

		private SessionInvocationHandler(Session session) {
			_session = session;
		}

		private final Session _session;

	}

}