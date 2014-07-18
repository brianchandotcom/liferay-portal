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

package com.liferay.portal.transaction;

import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionLifecycleManager;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class TransactionLifecycleManagerInitializer {

	public void setTransactionLifecycleListeners(
		List<TransactionLifecycleListener> transactionLifecycleListeners) {

		for (TransactionLifecycleListener transactionLifecycleListener :
				transactionLifecycleListeners) {

			TransactionLifecycleManager.register(transactionLifecycleListener);
		}
	}

}