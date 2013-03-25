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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public abstract class BaseTransactionExecutor implements TransactionExecutor {

	public void setPlatformTransactionManager(
		PlatformTransactionManager platformTransactionManager) {

		this.platformTransactionManager = platformTransactionManager;
	}

	protected void invokeCallbacks() {
		List<Callable<?>> callables =
			TransactionCommitCallbackUtil.popCallbackList();

		for (Callable<?> callable : callables) {
			try {
				callable.call();
			}
			catch (Exception e) {
				_log.error("Failed to execute transaction commit callback", e);
			}
		}
	}

	protected PlatformTransactionManager platformTransactionManager;

	protected static class ThrowableHolder {

		public ThrowableHolder(Throwable throwable) {
			_throwable = throwable;
		}

		public Throwable getThrowable() {
			return _throwable;
		}

		private final Throwable _throwable;

	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseTransactionExecutor.class);

}