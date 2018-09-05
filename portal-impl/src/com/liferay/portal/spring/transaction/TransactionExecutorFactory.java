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
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
public class TransactionExecutorFactory {

	public static TransactionExecutor createTransactionExecutor(
		PlatformTransactionManager platformTransactionManager,
		boolean counter) {

		if (counter) {
			if (platformTransactionManager instanceof
					CallbackPreferringPlatformTransactionManager) {

				return new CounterCallbackPreferringTransactionExecutor();
			}

			return new CounterTransactionExecutor();
		}
		else {
			if (platformTransactionManager instanceof
					CallbackPreferringPlatformTransactionManager) {

				return new CallbackPreferringTransactionExecutor();
			}

			return new DefaultTransactionExecutor();
		}
	}

}