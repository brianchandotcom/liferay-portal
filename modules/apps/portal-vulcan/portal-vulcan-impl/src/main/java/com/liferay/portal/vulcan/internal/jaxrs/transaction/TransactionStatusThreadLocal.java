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

package com.liferay.portal.vulcan.internal.jaxrs.transaction;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.spring.transaction.TransactionStatusAdapter;

/**
 * @author Javier Gamarra
 */
public class TransactionStatusThreadLocal {

	public static TransactionStatusAdapter getTransactionStatusAdapter() {
		return _transactionStatusAdapterThreadLocal.get();
	}

	public static void setTransactionStatusAdapter(
		TransactionStatusAdapter transactionStatusAdapter) {

		_transactionStatusAdapterThreadLocal.set(transactionStatusAdapter);
	}

	private static final ThreadLocal<TransactionStatusAdapter>
		_transactionStatusAdapterThreadLocal = new CentralizedThreadLocal<>(
			TransactionStatusThreadLocal.class +
				"._transactionStatusAdapterThreadLocal");

}