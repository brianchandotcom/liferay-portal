/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.executor;

import com.liferay.portal.kernel.util.CentralizedThreadLocal;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public abstract class CopyThreadLocalCallable<T> implements Callable<T> {

	public CopyThreadLocalCallable(boolean readOnly, boolean clearOnExit) {
		if (readOnly) {
			_longLivedThreadLocals = Collections.unmodifiableMap(
				CentralizedThreadLocal.getLongLivedThreadLocals());
			_shortLivedlThreadLocals = Collections.unmodifiableMap(
				CentralizedThreadLocal.getShortLivedThreadLocals());
		}
		else {
			_longLivedThreadLocals =
				CentralizedThreadLocal.getLongLivedThreadLocals();
			_shortLivedlThreadLocals =
				CentralizedThreadLocal.getShortLivedThreadLocals();
		}

		_clearOnExit = clearOnExit;
	}

	public final T call() throws Exception {
		CentralizedThreadLocal.setThreadLocals(
			_longLivedThreadLocals, _shortLivedlThreadLocals);

		try {
			return doCall();
		}
		finally {
			if (_clearOnExit) {
				CentralizedThreadLocal.clearLongLivedThreadLocals();
				CentralizedThreadLocal.clearShortLivedThreadLocals();
			}
		}
	}

	public abstract T doCall() throws Exception;

	private final boolean _clearOnExit;
	private final Map<CentralizedThreadLocal<?>, Object> _longLivedThreadLocals;
	private final Map<CentralizedThreadLocal<?>, Object>
		_shortLivedlThreadLocals;

}