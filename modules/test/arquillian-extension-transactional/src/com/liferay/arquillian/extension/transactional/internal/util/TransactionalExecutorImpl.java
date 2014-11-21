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

package com.liferay.arquillian.extension.transactional.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.transaction.Transactional;

import java.lang.reflect.Method;

import java.util.concurrent.Callable;

import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.event.suite.ClassEvent;
import org.jboss.arquillian.test.spi.event.suite.TestEvent;

/**
 * @author Cristina González
 */
public class TransactionalExecutorImpl implements TransactionalExecutor {

	@Override
	public void execute(EventContext<?> eventContext) throws Throwable {
		Transactional transactional = getAnnotation(eventContext.getEvent());

		executeInTransaction(transactional, eventContext);
	}

	protected void executeInTransaction(
			Transactional transactional, final EventContext<?> eventContext)
		throws Throwable {

		if (transactional == null) {
			eventContext.proceed();

			return;
		}

		TransactionAttribute.Builder builder =
			new TransactionAttribute.Builder();

		builder.setPropagation(transactional.propagation());
		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		TransactionInvokerUtil.invoke(
			builder.build(), new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					eventContext.proceed();

					return null;
				}

			});
	}

	protected Transactional getAnnotation(ClassEvent classEvent) {
		TestClass testClass = classEvent.getTestClass();

		return testClass.getAnnotation(Transactional.class);
	}

	protected Transactional getAnnotation(Object object) {
		if (object instanceof ClassEvent) {
			return getAnnotation((ClassEvent)object);
		}
		else if (object instanceof TestEvent) {
			return getAnnotation((TestEvent)object);
		}

		throw new RuntimeException(
			"Object " + object + " is not a class event or test event");
	}

	protected Transactional getAnnotation(TestEvent testEvent) {
		Method method = testEvent.getTestMethod();

		Transactional transactional = method.getAnnotation(Transactional.class);

		if (transactional != null) {
			return transactional;
		}

		return getAnnotation((ClassEvent)testEvent);
	}

}