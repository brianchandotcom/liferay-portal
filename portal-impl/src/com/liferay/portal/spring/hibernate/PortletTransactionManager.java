/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.db.partition.DBPartition;

import java.sql.Connection;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionCreationOptions;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jdbc.Work;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Shuyang Zhou
 */
public class PortletTransactionManager implements PlatformTransactionManager {

	public PortletTransactionManager(
		HibernateTransactionManager portalHibernateTransactionManager,
		SessionFactory portletSessionFactory) {

		_portalHibernateTransactionManager = portalHibernateTransactionManager;
		_portletSessionFactory = portletSessionFactory;
	}

	@Override
	public void commit(TransactionStatus transactionStatus)
		throws TransactionException {

		if (!(transactionStatus instanceof TransactionStatusWrapper)) {
			_portalHibernateTransactionManager.commit(transactionStatus);

			return;
		}

		Throwable throwable1 = null;

		try {
			TransactionStatusWrapper transactionStatusWrapper =
				(TransactionStatusWrapper)transactionStatus;

			transactionStatus = transactionStatusWrapper._transactionStatus;

			transactionStatusWrapper.reset();
		}
		catch (Throwable throwable2) {
			throwable1 = throwable2;

			throw throwable2;
		}
		finally {
			if (throwable1 == null) {
				_portalHibernateTransactionManager.commit(transactionStatus);
			}
			else {
				_portalHibernateTransactionManager.rollback(transactionStatus);
			}
		}
	}

	public SessionFactory getPortletSessionFactory() {
		return _portletSessionFactory;
	}

	@Override
	public TransactionStatus getTransaction(
			TransactionDefinition transactionDefinition)
		throws TransactionException {

		TransactionStatus portalTransactionStatus =
			_portalHibernateTransactionManager.getTransaction(
				transactionDefinition);

		SessionHolder portalSessionHolder =
			(SessionHolder)SpringHibernateThreadLocalUtil.getResource(
				_portalHibernateTransactionManager.getSessionFactory());

		if (portalSessionHolder == null) {
			return portalTransactionStatus;
		}

		Connection portalConnection = _getConnection(portalSessionHolder);

		SessionHolder portletSessionHolder =
			(SessionHolder)SpringHibernateThreadLocalUtil.getResource(
				_portletSessionFactory);

		if (portletSessionHolder != null) {
			if (portalConnection == _getConnection(portletSessionHolder)) {
				return portalTransactionStatus;
			}

			Session portalSession = portalSessionHolder.getSession();

			portalSession.flush();
		}

		SessionBuilder<?> sessionBuilder = _portletSessionFactory.withOptions();

		sessionBuilder = sessionBuilder.connection(portalConnection);

		Session portletSession = new SessionImpl(
			(SessionFactoryImpl)_portletSessionFactory,
			(SessionCreationOptions)sessionBuilder) {

			@Override
			public boolean isTransactionInProgress() {
				if (TransactionSynchronizationManager.
						isActualTransactionActive()) {

					return true;
				}

				return super.isTransactionInProgress();
			}

		};

		SpringHibernateThreadLocalUtil.setResource(
			_portletSessionFactory,
			_createSessionHolder(portletSession, portalSessionHolder));

		if (DBPartition.isPartitionEnabled()) {
			LastSessionRecorderUtil.addPortletSession(portletSession);
		}

		return new TransactionStatusWrapper(
			portalTransactionStatus, _portletSessionFactory,
			portletSessionHolder, portletSession);
	}

	@Override
	public void rollback(TransactionStatus transactionStatus)
		throws TransactionException {

		if (!(transactionStatus instanceof TransactionStatusWrapper)) {
			_portalHibernateTransactionManager.rollback(transactionStatus);

			return;
		}

		try {
			TransactionStatusWrapper transactionStatusWrapper =
				(TransactionStatusWrapper)transactionStatus;

			transactionStatus = transactionStatusWrapper._transactionStatus;

			transactionStatusWrapper.reset();
		}
		finally {
			_portalHibernateTransactionManager.rollback(transactionStatus);
		}
	}

	private SessionHolder _createSessionHolder(
		Session session, SessionHolder templateSessionHolder) {

		SessionHolder sessionHolder = new SessionHolder(session);

		sessionHolder.setPreviousFlushMode(
			templateSessionHolder.getPreviousFlushMode());

		if (templateSessionHolder.isRollbackOnly()) {
			sessionHolder.setRollbackOnly();
		}

		sessionHolder.setSynchronizedWithTransaction(
			templateSessionHolder.isSynchronizedWithTransaction());

		if (templateSessionHolder.hasTimeout()) {
			Date deadline = templateSessionHolder.getDeadline();

			sessionHolder.setTimeoutInMillis(
				deadline.getTime() - System.currentTimeMillis());
		}

		sessionHolder.setTransaction(templateSessionHolder.getTransaction());

		if (templateSessionHolder.isVoid()) {
			sessionHolder.unbound();
		}

		return sessionHolder;
	}

	private Connection _getConnection(SessionHolder sessionHolder) {
		Session session = sessionHolder.getSession();

		ConnectionReference connectionHolder = new ConnectionReference();

		session.doWork(
			new Work() {

				@Override
				public void execute(Connection connection) {
					connectionHolder.setConnection(connection);
				}

			});

		return connectionHolder.getConnection();
	}

	private final HibernateTransactionManager
		_portalHibernateTransactionManager;
	private final SessionFactory _portletSessionFactory;

	private static class ConnectionReference {

		public Connection getConnection() {
			return _connection;
		}

		public void setConnection(Connection connection) {
			_connection = connection;
		}

		private Connection _connection;

	}

	private static class TransactionStatusWrapper implements TransactionStatus {

		@Override
		public Object createSavepoint() throws TransactionException {
			return _transactionStatus.createSavepoint();
		}

		@Override
		public void flush() {
			_transactionStatus.flush();
		}

		@Override
		public boolean hasSavepoint() {
			return _transactionStatus.hasSavepoint();
		}

		@Override
		public boolean isCompleted() {
			return _transactionStatus.isCompleted();
		}

		@Override
		public boolean isNewTransaction() {
			return _transactionStatus.isNewTransaction();
		}

		@Override
		public boolean isRollbackOnly() {
			return _transactionStatus.isRollbackOnly();
		}

		@Override
		public void releaseSavepoint(Object savepoint)
			throws TransactionException {

			_transactionStatus.releaseSavepoint(savepoint);
		}

		public void reset() {
			try {
				_portletSession.flush();
			}
			finally {
				SpringHibernateThreadLocalUtil.setResource(
					_portletSessionFactory, _previousPortletSessionHolder);
			}
		}

		@Override
		public void rollbackToSavepoint(Object savepoint)
			throws TransactionException {

			_transactionStatus.rollbackToSavepoint(savepoint);
		}

		@Override
		public void setRollbackOnly() {
			_transactionStatus.setRollbackOnly();
		}

		private TransactionStatusWrapper(
			TransactionStatus transactionStatus,
			SessionFactory portletSessionFactory,
			SessionHolder previousPortletSessionHolder,
			Session portletSession) {

			_transactionStatus = transactionStatus;
			_portletSessionFactory = portletSessionFactory;
			_previousPortletSessionHolder = previousPortletSessionHolder;
			_portletSession = portletSession;
		}

		private final Session _portletSession;
		private final SessionFactory _portletSessionFactory;
		private final SessionHolder _previousPortletSessionHolder;
		private final TransactionStatus _transactionStatus;

	}

}