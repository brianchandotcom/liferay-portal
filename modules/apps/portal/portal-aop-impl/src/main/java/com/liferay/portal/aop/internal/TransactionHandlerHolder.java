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

import com.liferay.portal.spring.transaction.TransactionHandler;

import org.osgi.framework.ServiceReference;

/**
 * @author Preston Crary
 */
public class TransactionHandlerHolder
	implements Comparable<TransactionHandlerHolder> {

	public TransactionHandlerHolder(
		ServiceReference<TransactionHandler> serviceReference,
		TransactionHandler transactionHandler) {

		_serviceReference = serviceReference;
		_transactionHandler = transactionHandler;
	}

	@Override
	public int compareTo(TransactionHandlerHolder transactionHandlerHolder) {
		return _serviceReference.compareTo(
			transactionHandlerHolder._serviceReference);
	}

	public TransactionHandler getTransactionHandler() {
		return _transactionHandler;
	}

	private final ServiceReference<TransactionHandler> _serviceReference;
	private final TransactionHandler _transactionHandler;

}