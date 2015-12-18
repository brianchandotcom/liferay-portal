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

package com.liferay.portal.kernel.test.rule;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.rule.BaseTestRule.StatementWrapper;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.util.concurrent.Callable;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class TransactionalTestRule implements TestRule {

	public static final TransactionalTestRule INSTANCE =
		new TransactionalTestRule();

	public TransactionalTestRule() {
		this(Propagation.SUPPORTS);
	}

	public TransactionalTestRule(Propagation propagation) {
		TransactionAttribute.Builder builder =
			new TransactionAttribute.Builder();

		builder.setPropagation(propagation);
		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		_transactionAttribute = builder.build();
	}

	@Override
	public Statement apply(Statement statement, Description description) {
		String methodName = description.getMethodName();

		if (methodName == null) {
			return statement;
		}

		return new StatementWrapper(statement) {

			@Override
			public void evaluate() throws Throwable {
				TransactionInvokerUtil.invoke(
					getTransactionAttribute(),
					new Callable<Void>() {

						@Override
						public Void call() throws Exception {
							try {
								statement.evaluate();
							}
							catch (Throwable t) {
								ReflectionUtil.throwException(t);
							}

							return null;
						}

					});
			}

		};
	}

	public TransactionAttribute getTransactionAttribute() {
		return _transactionAttribute;
	}

	private final TransactionAttribute _transactionAttribute;

}