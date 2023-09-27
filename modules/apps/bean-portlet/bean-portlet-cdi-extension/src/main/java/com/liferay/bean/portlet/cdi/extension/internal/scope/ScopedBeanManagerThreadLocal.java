/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.cdi.extension.internal.scope;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Closeable;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Supplier;

/**
 * @author Neil Griffin
 */
public class ScopedBeanManagerThreadLocal {

	public static Runnable captureScopedBeanManagers() {
		Deque<ScopedBeanManager> scopedBeanManagers = _threadLocal.get();

		return () -> _threadLocal.set(scopedBeanManagers);
	}

	public static ScopedBeanManager getCurrentScopedBeanManager() {
		Deque<ScopedBeanManager> scopedBeanManagers = _threadLocal.get();

		return scopedBeanManagers.peek();
	}

	public static Closeable install(ScopedBeanManager scopedBeanManager) {
		Deque<ScopedBeanManager> scopedBeanManagerStack = _threadLocal.get();

		scopedBeanManagerStack.push(scopedBeanManager);

		return () -> {
			if (scopedBeanManagerStack.isEmpty()) {
				_log.error(
					"Unable to destroy scoped beans when scopes are inactive");
			}
			else {
				ScopedBeanManager closingScopedBeanManager =
					scopedBeanManagerStack.pop();

				if (scopedBeanManagerStack.isEmpty()) {
					closingScopedBeanManager.destroyScopedBeans();

					_threadLocal.remove();
				}
			}
		};
	}

	public static <T extends Throwable> void invokeWithScopedBeanManager(
			Supplier<ScopedBeanManager> supplier,
			UnsafeRunnable<T> unsafeRunnable)
		throws T {

		Deque<ScopedBeanManager> scopedBeanManagers = _threadLocal.get();

		scopedBeanManagers.push(supplier.get());

		try {
			unsafeRunnable.run();
		}
		finally {
			ScopedBeanManager scopedBeanManager = scopedBeanManagers.pop();

			if (scopedBeanManagers.isEmpty()) {
				scopedBeanManager.destroyScopedBeans();
			}
		}
	}

	public static void remove() {
		_threadLocal.remove();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ScopedBeanManagerThreadLocal.class);

	private static final ThreadLocal<Deque<ScopedBeanManager>> _threadLocal =
		new CentralizedThreadLocal<>(
			ScopedBeanManagerThreadLocal.class + "._threadLocal",
			ConcurrentLinkedDeque::new);

}