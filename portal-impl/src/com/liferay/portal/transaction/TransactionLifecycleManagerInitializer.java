/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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