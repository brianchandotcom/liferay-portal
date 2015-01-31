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

package com.liferay.arquillian.extension.transactional.internal.observer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.portal.kernel.transaction.Transactional;

import java.lang.reflect.Method;

import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.junit.State;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.TestEvent;

/**
 * @author Cristina González
 */
public class TransactionalObserver {

	public void afterTest(@Observes EventContext<After> eventContext)
		throws Throwable {

		Throwable throwable = State.caughtExceptionAfterJunit();

		try {
			eventContext.proceed();
		}
		catch (Throwable t) {
			if (throwable != null) {
				t.addSuppressed(throwable);
			}

			throwable = t;
		}

		if (throwable == null) {
			if (_transactionAttribute != null) {
				TransactionInvokerUtil.commit(
					_transactionAttribute, _transactionStatus);
			}
		}
		else {
			if (_transactionAttribute == null) {
				if (throwable != State.caughtExceptionAfterJunit()) {
					throw throwable;
				}
			}
			else {
				TransactionInvokerUtil.rollback(
					throwable, _transactionAttribute, _transactionStatus);
			}
		}
	}

	public void beforeTest(@Observes EventContext<Before> eventContext)
		throws Throwable {

		_transactionAttribute = getTransactionAttribute(
			eventContext.getEvent());

		if (_transactionAttribute != null) {
			_transactionStatus = TransactionInvokerUtil.start(
				_transactionAttribute);
		}

		eventContext.proceed();
	}

	protected TransactionAttribute getTransactionAttribute(
		TestEvent testEvent) {

		Method method = testEvent.getTestMethod();

		Transactional transactional = method.getAnnotation(Transactional.class);

		if (transactional == null) {
			TestClass testClass = testEvent.getTestClass();

			transactional = testClass.getAnnotation(Transactional.class);
		}

		if (transactional == null) {
			return null;
		}

		TransactionAttribute.Builder builder =
			new TransactionAttribute.Builder();

		builder.setPropagation(transactional.propagation());
		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		return builder.build();
	}

	private TransactionAttribute _transactionAttribute;
	private TransactionStatus _transactionStatus;

}