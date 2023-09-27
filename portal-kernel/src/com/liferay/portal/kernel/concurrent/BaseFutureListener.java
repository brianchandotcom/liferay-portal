/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent;

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
		catch (Throwable throwable) {
			if (throwable instanceof ExecutionException) {
				throwable = throwable.getCause();
			}

			completeWithException(future, throwable);
		}
	}

	public void completeWithCancel(Future<T> future) {
	}

	public void completeWithException(Future<T> future, Throwable throwable) {
	}

	public void completeWithResult(Future<T> future, T result) {
	}

}