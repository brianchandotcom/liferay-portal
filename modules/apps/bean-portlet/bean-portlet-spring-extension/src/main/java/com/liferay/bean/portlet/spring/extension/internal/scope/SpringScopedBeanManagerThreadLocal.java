/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.spring.extension.internal.scope;

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
public class SpringScopedBeanManagerThreadLocal {

	public static Runnable captureScopedBeanManagers() {
		Deque<SpringScopedBeanManager> scopedBeanManagers = _instance.get();

		return () -> _instance.set(scopedBeanManagers);
	}

	public static SpringScopedBeanManager getCurrentScopedBeanManager() {
		Deque<SpringScopedBeanManager> scopedBeanManagers = _instance.get();

		return scopedBeanManagers.peek();
	}

	public static Closeable install(
		SpringScopedBeanManager springScopedBeanManager) {

		Deque<SpringScopedBeanManager> scopedBeanManagerStack = _instance.get();

		scopedBeanManagerStack.push(springScopedBeanManager);

		return () -> {
			if (scopedBeanManagerStack.isEmpty()) {
				_log.error(
					"Unable to destroy scoped beans when scopes are inactive");
			}
			else {
				SpringScopedBeanManager closingScopedBeanManager =
					scopedBeanManagerStack.pop();

				if (scopedBeanManagerStack.isEmpty()) {
					closingScopedBeanManager.destroyScopedBeans();

					_instance.remove();
				}
			}
		};
	}

	public static <T extends Throwable> void invokeWithScopedBeanManager(
			Supplier<SpringScopedBeanManager> supplier,
			UnsafeRunnable<T> unsafeRunnable)
		throws T {

		Deque<SpringScopedBeanManager> scopedBeanManagers = _instance.get();

		scopedBeanManagers.push(supplier.get());

		try {
			unsafeRunnable.run();
		}
		finally {
			SpringScopedBeanManager springScopedBeanManager =
				scopedBeanManagers.pop();

			if (scopedBeanManagers.isEmpty()) {
				springScopedBeanManager.destroyScopedBeans();
			}
		}
	}

	public static void remove() {
		_instance.remove();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SpringScopedBeanManagerThreadLocal.class);

	private static final ThreadLocal<Deque<SpringScopedBeanManager>> _instance =
		new CentralizedThreadLocal<>(
			SpringScopedBeanManagerThreadLocal.class + "._instance",
			ConcurrentLinkedDeque::new);

}