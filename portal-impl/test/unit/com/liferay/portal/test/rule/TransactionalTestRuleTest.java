/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			LiferayUnitTestRule.INSTANCE, TransactionalTestRule.INSTANCE);

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