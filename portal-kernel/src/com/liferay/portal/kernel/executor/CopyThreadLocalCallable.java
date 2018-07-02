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

package com.liferay.portal.kernel.executor;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.ThreadLocalBinder;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public abstract class CopyThreadLocalCallable<T> implements Callable<T> {

	public CopyThreadLocalCallable(boolean readOnly, boolean clearOnExit) {
		this(null, readOnly, clearOnExit);
	}

	public CopyThreadLocalCallable(
		ThreadLocalBinder threadLocalBinder, boolean readOnly,
		boolean clearOnExit) {

		_threadLocalBinder = threadLocalBinder;

		if (_threadLocalBinder != null) {
			_threadLocalBinder.record();
		}

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

	@Override
	public final T call() throws Exception {
		CentralizedThreadLocal.setThreadLocals(
			_longLivedThreadLocals, _shortLivedlThreadLocals);

		if (_threadLocalBinder != null) {
			_threadLocalBinder.bind();
		}

		try {
			return doCall();
		}
		finally {
			if (_clearOnExit) {
				if (_threadLocalBinder != null) {
					_threadLocalBinder.cleanUp();
				}

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
	private final ThreadLocalBinder _threadLocalBinder;

}