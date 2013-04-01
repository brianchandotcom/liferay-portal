/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public interface TransactionExecutor {

	public Object execute(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttribute transactionAttribute,
			MethodInvocation methodInvocation)
		throws Throwable;

}