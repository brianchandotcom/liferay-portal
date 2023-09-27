/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.transaction;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public class TransactionCommitCallbackUtil {

	public static final TransactionLifecycleListener
		TRANSACTION_LIFECYCLE_LISTENER = new NewTransactionLifecycleListener() {

			@Override
			protected void doCommitted(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				List<Callable<?>> callables = popCallbackList();

				for (Callable<?> callable : callables) {
					try {
						callable.call();
					}
					catch (Exception exception) {
						_log.error(
							"Unable to execute transaction commit callback",
							exception);
					}
				}
			}

			@Override
			protected void doCreated(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				pushCallbackList();
			}

			@Override
			protected void doRollbacked(
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
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
		else {
			int index = callbackListList.size() - 1;

			List<Callable<?>> callableList = callbackListList.get(index);

			if (callableList == Collections.<Callable<?>>emptyList()) {
				callableList = new ArrayList<>();

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
		_callbackListListThreadLocal = new CentralizedThreadLocal<>(
			TransactionCommitCallbackUtil.class +
				"._callbackListListThreadLocal",
			ArrayList::new);

}