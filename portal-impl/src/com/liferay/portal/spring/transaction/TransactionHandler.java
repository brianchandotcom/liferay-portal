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

import aQute.bnd.annotation.ProviderType;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
@ProviderType
public interface TransactionHandler {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #commit(
	 *             TransactionAttributeAdapter, TransactionStatusAdapter)}
	 */
	@Deprecated
	public void commit(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttributeAdapter transactionAttributeAdapter,
		TransactionStatusAdapter transactionStatusAdapter);

	public void commit(
		TransactionAttributeAdapter transactionAttributeAdapter,
		TransactionStatusAdapter transactionStatusAdapter);

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #rollback(
	 *             Throwable, TransactionAttributeAdapter,
	 *             TransactionStatusAdapter)}
	 */
	@Deprecated
	public void rollback(
			PlatformTransactionManager platformTransactionManager,
			Throwable throwable,
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter)
		throws Throwable;

	public void rollback(
			Throwable throwable,
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter)
		throws Throwable;

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #start(
	 *             TransactionAttributeAdapter)}
	 */
	@Deprecated
	public TransactionStatusAdapter start(
		PlatformTransactionManager platformTransactionManager,
		TransactionAttributeAdapter transactionAttributeAdapter);

	public TransactionStatusAdapter start(
		TransactionAttributeAdapter transactionAttributeAdapter);

}