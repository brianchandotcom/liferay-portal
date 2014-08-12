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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import org.hibernate.Session;

/**
 * @author Shuyang Zhou
 */
public class LastSessionRecorderUtil {

	public static final TransactionLifecycleListener
		TRANSACTION_LIFECYCLE_LISTENER = new TransactionLifecycleListener() {

		@Override
		public void committed(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus) {
		}

		@Override
		public void created(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus) {

			syncLastSessionState();
		}

		@Override
		public void rollbacked(
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus, Throwable throwable) {
		}

	};

	public static void syncLastSessionState() {
		Session session = _lastSessionThreadLocal.get();

		if ((session != null) && session.isOpen()) {
			try {
				session.flush();
				session.clear();
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}
	}

	protected static void setLastSession(Session session) {
		_lastSessionThreadLocal.set(session);
	}

	private static final ThreadLocal<Session> _lastSessionThreadLocal =
		new AutoResetThreadLocal<Session>(
			LastSessionRecorderUtil.class.getName() +
				"._lastSessionThreadLocal");

}