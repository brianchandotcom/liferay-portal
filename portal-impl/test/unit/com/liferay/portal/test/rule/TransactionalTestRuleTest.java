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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.BaseTransactionalTestRuleTest;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvoker;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;

import java.util.Deque;
import java.util.concurrent.Callable;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Shuyang Zhou
 */
public class TransactionalTestRuleTest extends BaseTransactionalTestRuleTest {

	@ClassRule
	@Rule
	public static final TransactionalTestRule transactionalTestRule =
		TransactionalTestRule.INSTANCE;

	static {
		TransactionInvokerUtil transactionInvokerUtil =
			new TransactionInvokerUtil();

		transactionInvokerUtil.setTransactionInvoker(
			new TransactionInvoker() {

				@Override
				public <T> T invoke(
						TransactionConfig transactionConfig,
						Callable<T> callable)
					throws Throwable {

					Deque<TransactionConfig> transactionConfigs =
						transactionConfigThreadLocal.get();

					transactionConfigs.push(transactionConfig);

					try {
						return callable.call();
					}
					finally {
						transactionConfigs.pop();

						if (transactionConfigs.isEmpty()) {
							transactionConfigThreadLocal.remove();
						}
					}
				}

			});
	}

}