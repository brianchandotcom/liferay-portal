/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public class TransactionCommitCallbackUtil {

	public static void registerCallback(Callable<?> callable) {
		List<List<Callable<?>>> callbackListStack =
			_transactionCommitCallbackThreadLocal.get();

		int lastIndex = callbackListStack.size() - 1;

		List<Callable<?>> callbackList = callbackListStack.get(lastIndex);

		if (callbackList == Collections.EMPTY_LIST) {
			callbackList = new ArrayList<Callable<?>>();
			callbackListStack.set(lastIndex, callbackList);
		}

		callbackList.add(callable);
	}

	protected static void pushCallbackList() {
		List<List<Callable<?>>> callbackListStack =
			_transactionCommitCallbackThreadLocal.get();

		callbackListStack.add(Collections.EMPTY_LIST);
	}

	protected static List<Callable<?>> popCallbackList() {
		List<List<Callable<?>>> callbackListStack =
			_transactionCommitCallbackThreadLocal.get();

		return callbackListStack.remove(callbackListStack.size() - 1);
	}

	private static ThreadLocal<List<List<Callable<?>>>>
		_transactionCommitCallbackThreadLocal =
		new AutoResetThreadLocal<List<List<Callable<?>>>>(
			TransactionCommitCallbackUtil.class +
				"._transactionCommitCallbackThreadLocal") {

					@Override
					protected List<List<Callable<?>>> initialValue() {
						return new ArrayList<List<Callable<?>>>();
					}

				};

}