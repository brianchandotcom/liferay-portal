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

import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
public interface TransactionHandler {

	public void commit(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttributeAdaptor transactionAttributeAdaptor,
		TransactionStatusAdaptor transactionStatusAdaptor);

	public void rollback(
			PlatformTransactionManager platformTransactionManager,
			Throwable throwable,
			TransactionAttributeAdaptor transactionAttributeAdaptor,
			TransactionStatusAdaptor transactionStatusAdaptor)
		throws Throwable;

	public TransactionStatusAdaptor start(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttributeAdaptor transactionAttributeAdaptor);

}