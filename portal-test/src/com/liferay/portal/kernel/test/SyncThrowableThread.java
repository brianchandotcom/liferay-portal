/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test;

import com.liferay.petra.reflect.ReflectionUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author Shuyang Zhou
 */
public class SyncThrowableThread<V> extends Thread {

	public SyncThrowableThread(Callable<V> callable) {
		_futureTask = new FutureTask<>(callable);
	}

	@Override
	public void run() {
		_futureTask.run();
	}

	public V sync() {
		try {
			join();

			return _futureTask.get();
		}
		catch (Throwable throwable) {
			return ReflectionUtil.throwException(throwable);
		}
	}

	private final FutureTask<V> _futureTask;

}