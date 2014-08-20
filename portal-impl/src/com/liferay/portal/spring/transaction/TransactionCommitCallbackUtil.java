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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public class TransactionCommitCallbackUtil {

	public static final TransactionLifecycleListener
		TRANSACTION_LIFECYCLE_LISTENER = new TransactionLifecycleListener() {

			@Override
			public void created(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				pushCallbackList();
			}

			@Override
			public void committed(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				List<Callable<?>> callables = popCallbackList();

				for (Callable<?> callable : callables) {
					try {
						callable.call();
					}
					catch (Exception e) {
						_log.error(
							"Unable to execute transaction commit callback", e);
					}
				}
			}

			@Override
			public void rollbacked(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus, Throwable throwable) {

				popCallbackList();
			}

		};

	public static void registerCallback(Callable<?> callable) {
		List<List<Callable<?>>> callbackListList =
			_callbackListListThreadLocal.get();

		if (callbackListList.isEmpty()) {

			// Not within a transaction boundary, should only happen during an
			// upgrade and verify process. See DBUpgrader#_disableTransactions.

			try {
				callable.call();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		else {
			int index = callbackListList.size() - 1;

			List<Callable<?>> callableList = callbackListList.get(index);

			if (callableList == Collections.<Callable<?>>emptyList()) {
				callableList = new ArrayList<Callable<?>>();

				callbackListList.set(index, callableList);
			}

			callableList.add(callable);
		}
	}

	protected static List<Callable<?>> popCallbackList() {
		List<List<Callable<?>>> callbackListList =
			_callbackListListThreadLocal.get();

		return callbackListList.remove(callbackListList.size() - 1);
	}

	protected static void pushCallbackList() {
		List<List<Callable<?>>> callbackListList =
			_callbackListListThreadLocal.get();

		callbackListList.add(Collections.<Callable<?>>emptyList());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TransactionCommitCallbackUtil.class);

	private static final ThreadLocal<List<List<Callable<?>>>>
		_callbackListListThreadLocal =
			new AutoResetThreadLocal<List<List<Callable<?>>>>(
				TransactionCommitCallbackUtil.class +
					"._callbackListListThreadLocal") {

				@Override
				protected List<List<Callable<?>>> initialValue() {
					return new ArrayList<List<Callable<?>>>();
				}

			};

}