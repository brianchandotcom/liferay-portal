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

package com.liferay.portal.spring.transaction;

import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.portal.kernel.util.InitialThreadLocal;

import java.util.ArrayDeque;
import java.util.Deque;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
public class CurrentPlatformTransactionManagerUtil {

	public static final TransactionLifecycleListener
		TRANSACTION_LIFECYCLE_LISTENER = new TransactionLifecycleListener() {

			@Override
			public void committed(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				Deque<PlatformTransactionManager> platformTransactionManagers =
					_platformTransactionManagersThreadLocal.get();

				platformTransactionManagers.pop();
			}

			@Override
			public void created(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				Deque<PlatformTransactionManager> platformTransactionManagers =
					_platformTransactionManagersThreadLocal.get();

				platformTransactionManagers.push(
					(PlatformTransactionManager)
						transactionStatus.getPlatformTransactionManager());
			}

			@Override
			public void rollbacked(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus, Throwable throwable) {

				Deque<PlatformTransactionManager> platformTransactionManagers =
					_platformTransactionManagersThreadLocal.get();

				platformTransactionManagers.pop();
			}

		};

	public static PlatformTransactionManager
		getCurrentPlatformTransactionManager() {

		Deque<PlatformTransactionManager> platformTransactionManagers =
			_platformTransactionManagersThreadLocal.get();

		return platformTransactionManagers.peek();
	}

	private static final ThreadLocal<Deque<PlatformTransactionManager>>
		_platformTransactionManagersThreadLocal = new InitialThreadLocal<>(
			CurrentPlatformTransactionManagerUtil.class +
				"._platformTransactionManagersThreadLocal",
			new ArrayDeque<>());

}