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

package com.liferay.portal.kernel.process.local;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.concurrent.AsyncBroker;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class AsyncBrokerThreadLocal {

	public static AsyncBroker<Long, Serializable> getAsyncBroker() {
		AsyncBroker<Long, Serializable> asyncBroker =
			_asyncBrokerThreadLocal.get();

		if (asyncBroker == null) {
			throw new IllegalStateException("Async broker is not set");
		}

		return asyncBroker;
	}

	public static void removeAsyncBroker() {
		_asyncBrokerThreadLocal.remove();
	}

	public static void setAsyncBroker(
		AsyncBroker<Long, Serializable> asyncBroker) {

		_asyncBrokerThreadLocal.set(asyncBroker);
	}

	private static final ThreadLocal<AsyncBroker<Long, Serializable>>
		_asyncBrokerThreadLocal = new CentralizedThreadLocal<>(false);

}