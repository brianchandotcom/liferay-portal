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

package com.liferay.portal.aop.internal;

import com.liferay.portal.spring.transaction.TransactionExecutor;

import org.osgi.framework.ServiceReference;

/**
 * @author Preston Crary
 */
public class TransactionExecutorHolder
	implements Comparable<TransactionExecutorHolder> {

	public TransactionExecutorHolder(
		ServiceReference<TransactionExecutor> serviceReference,
		TransactionExecutor transactionExecutor) {

		_serviceReference = serviceReference;
		_transactionExecutor = transactionExecutor;
	}

	@Override
	public int compareTo(TransactionExecutorHolder transactionExecutorHolder) {
		return _serviceReference.compareTo(
			transactionExecutorHolder._serviceReference);
	}

	public TransactionExecutor getTransactionExecutor() {
		return _transactionExecutor;
	}

	private final ServiceReference<TransactionExecutor> _serviceReference;
	private final TransactionExecutor _transactionExecutor;

}