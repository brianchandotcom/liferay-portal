/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.junit.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.BaseTransactionalTestRuleTest;
import com.liferay.portal.kernel.test.rule.ClassTestRule;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvoker;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.Deque;
import java.util.concurrent.Callable;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class TransactionalTestRuleArquillianTest
	extends BaseTransactionalTestRuleTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			false,
			new ClassTestRule<Object>() {

				@Override
				public void afterClass(Description description, Object c) {
					TransactionInvokerUtil transactionInvokerUtil =
						new TransactionInvokerUtil();

					transactionInvokerUtil.setTransactionInvoker(
						_transactionInvoker);
				}

				@Override
				public Void beforeClass(Description description) {
					return null;
				}

			},
			TransactionalTestRule.INSTANCE);

	private static final TransactionInvoker _transactionInvoker;

	static {
		BeanLocator beanLocator = PortalBeanLocatorUtil.getBeanLocator();

		if (beanLocator == null) {

			// When the bean locator is null, it means we are on the client
			// side, simply do nothing

			_transactionInvoker = null;
		}
		else {
			_transactionInvoker = ReflectionTestUtil.getFieldValue(
				TransactionInvokerUtil.class, "_transactionInvoker");

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
							return _transactionInvoker.invoke(
								transactionConfig, callable);
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

}