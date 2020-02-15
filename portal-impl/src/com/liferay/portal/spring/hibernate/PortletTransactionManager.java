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

import java.sql.Connection;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

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

		Throwable throwable = null;

		try {
			TransactionStatusWrapper transactionStatusWrapper =
				(TransactionStatusWrapper)transactionStatus;

			transactionStatus = transactionStatusWrapper._transactionStatus;

			transactionStatusWrapper.reset();
		}
		catch (Throwable t) {
			throwable = t;

			throw t;
		}
		finally {
			if (throwable == null) {
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

		Session portletSession = _portletSessionFactory.openSession(
			portalConnection);

		SpringHibernateThreadLocalUtil.setResource(
			_portletSessionFactory,
			_createSessionHolder(portletSession, portalSessionHolder));

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