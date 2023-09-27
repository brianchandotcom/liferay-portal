/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process.local;

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.process.ProcessCallable;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
class ResponseProcessCallable<T extends Serializable>
	implements ProcessCallable<Boolean> {

	ResponseProcessCallable(long id, T result, Throwable throwable) {
		_id = id;
		_result = result;
		_throwable = throwable;
	}

	@Override
	public Boolean call() {
		AsyncBroker<Long, Serializable> asyncBroker =
			AsyncBrokerThreadLocal.getAsyncBroker();

		if (_throwable != null) {
			return asyncBroker.takeWithException(_id, _throwable);
		}

		return asyncBroker.takeWithResult(_id, _result);
	}

	private static final long serialVersionUID = 1L;

	private final long _id;
	private final T _result;
	private final Throwable _throwable;

}