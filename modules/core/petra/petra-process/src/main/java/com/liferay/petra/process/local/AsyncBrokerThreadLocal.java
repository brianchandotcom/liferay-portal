/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process.local;

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.lang.CentralizedThreadLocal;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
class AsyncBrokerThreadLocal {

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