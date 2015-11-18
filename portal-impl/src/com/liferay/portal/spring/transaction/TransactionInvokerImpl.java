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

import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvoker;

import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public class TransactionInvokerImpl implements TransactionInvoker {

	@Override
	public <T> T invoke(
			TransactionAttribute transactionAttribute, Callable<T> callable)
		throws Throwable {

		return TransactionalCallableUtil.call(
			TransactionAttributeBuilder.build(
				true, transactionAttribute.getIsolation(),
				transactionAttribute.getPropagation(),
				transactionAttribute.isReadOnly(),
				transactionAttribute.getTimeout(),
				transactionAttribute.getRollbackForClasses(),
				transactionAttribute.getRollbackForClassNames(),
				transactionAttribute.getNoRollbackForClasses(),
				transactionAttribute.getNoRollbackForClassNames()),
			callable);
	}

}