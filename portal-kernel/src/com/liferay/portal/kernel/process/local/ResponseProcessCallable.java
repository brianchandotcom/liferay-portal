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

import com.liferay.portal.kernel.concurrent.AsyncBroker;
import com.liferay.portal.kernel.process.ProcessCallable;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class ResponseProcessCallable<T extends Serializable>
	implements ProcessCallable<Boolean> {

	public ResponseProcessCallable(long id, T result, Throwable throwable) {
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