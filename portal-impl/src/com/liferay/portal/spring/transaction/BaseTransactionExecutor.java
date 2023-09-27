/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.transaction;

import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Preston Crary
 */
public abstract class BaseTransactionExecutor implements TransactionExecutor {

	@Override
	public <T> T execute(
			TransactionAttributeAdapter transactionAttributeAdapter,
			UnsafeSupplier<T, Throwable> unsafeSupplier)
		throws Throwable {

		TransactionStatusAdapter transactionStatusAdapter = start(
			transactionAttributeAdapter);

		T returnValue = null;

		try {
			returnValue = unsafeSupplier.get();
		}
		catch (Throwable throwable) {
			rollback(
				throwable, transactionAttributeAdapter,
				transactionStatusAdapter);
		}

		commit(transactionAttributeAdapter, transactionStatusAdapter);

		return returnValue;
	}

}