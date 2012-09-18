/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.test;

import com.liferay.portal.cache.transactional.TransactionalPortalCacheHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.spring.transaction.TransactionCommitCallbackTestUtil;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Miguel Pastor
 */
public class TransactionalCallbackAwareExecutionTestListener
	extends TransactionalExecutionTestListener {

	@Override
	protected void rollbackTransaction(TransactionContext transactionContext) {
		TransactionalPortalCacheHelper.commit();

		List<Callable<?>> callables =
			TransactionCommitCallbackTestUtil.popCallbackList();

		for (Callable<?> callable : callables) {
			try {
				callable.call();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		super.rollbackTransaction(transactionContext);
	}

	@Override
	protected void startNewTransaction(TransactionContext transactionContext) {
		super.startNewTransaction(transactionContext);

		TransactionalPortalCacheHelper.begin();

		TransactionCommitCallbackTestUtil.pushCallbackList();
	}

	private static Log _log = LogFactoryUtil.getLog(
		TransactionalCallbackAwareExecutionTestListener.class);

}