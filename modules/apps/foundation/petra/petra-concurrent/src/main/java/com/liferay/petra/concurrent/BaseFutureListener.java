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

package com.liferay.petra.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class BaseFutureListener<T> implements FutureListener<T> {

	@Override
	public final void complete(Future<T> future) {
		if (future.isCancelled()) {
			completeWithCancel(future);

			return;
		}

		try {
			completeWithResult(future, future.get());
		}
		catch (Throwable t) {
			if (t instanceof ExecutionException) {
				t = t.getCause();
			}

			completeWithException(future, t);
		}
	}

	public void completeWithCancel(Future<T> future) {
	}

	public void completeWithException(Future<T> future, Throwable throwable) {
	}

	public void completeWithResult(Future<T> future, T result) {
	}

}