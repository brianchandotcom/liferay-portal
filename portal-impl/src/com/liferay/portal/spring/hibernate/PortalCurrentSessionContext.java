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

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.context.spi.CurrentSessionContext;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.orm.hibernate5.SpringSessionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Shuyang Zhou
 */
public class PortalCurrentSessionContext implements CurrentSessionContext {

	public PortalCurrentSessionContext(
		SessionFactoryImplementor sessionFactoryImplementor) {

		_sessionFactoryImplementor = sessionFactoryImplementor;
	}

	@Override
	public Session currentSession() throws HibernateException {
		Object value = TransactionSynchronizationManager.getResource(
			_sessionFactoryImplementor);

		if (value instanceof Session) {
			return (Session)value;
		}
		else if (value instanceof SessionHolder) {
			SessionHolder sessionHolder = (SessionHolder)value;

			Session session = sessionHolder.getSession();

			if (!sessionHolder.isSynchronizedWithTransaction() &&
				TransactionSynchronizationManager.isSynchronizationActive()) {

				TransactionSynchronizationManager.registerSynchronization(
					new SpringSessionSynchronization(
						sessionHolder, _sessionFactoryImplementor, false));

				sessionHolder.setSynchronizedWithTransaction(true);

				FlushMode flushMode = session.getHibernateFlushMode();

				if (flushMode.equals(FlushMode.MANUAL) &&
					!TransactionSynchronizationManager.
						isCurrentTransactionReadOnly()) {

					session.setHibernateFlushMode(FlushMode.AUTO);

					sessionHolder.setPreviousFlushMode(flushMode);
				}
			}

			return session;
		}

		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			Session session = _sessionFactoryImplementor.openSession();

			if (TransactionSynchronizationManager.
					isCurrentTransactionReadOnly()) {

				session.setHibernateFlushMode(FlushMode.MANUAL);
			}

			SessionHolder sessionHolder = new SessionHolder(session);

			TransactionSynchronizationManager.registerSynchronization(
				new SpringSessionSynchronization(
					sessionHolder, _sessionFactoryImplementor, true));

			TransactionSynchronizationManager.bindResource(
				_sessionFactoryImplementor, sessionHolder);

			sessionHolder.setSynchronizedWithTransaction(true);

			return session;
		}

		throw new HibernateException(
			"Unable to get Session for current thread");
	}

	private final SessionFactoryImplementor _sessionFactoryImplementor;

}