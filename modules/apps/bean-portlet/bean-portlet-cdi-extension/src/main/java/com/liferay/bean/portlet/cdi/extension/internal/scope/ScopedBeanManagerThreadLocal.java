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

package com.liferay.bean.portlet.cdi.extension.internal.scope;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.io.Closeable;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author Neil Griffin
 */
public class ScopedBeanManagerThreadLocal {

	public static ScopedBeanManager getCurrentInstance() {
		Deque<ScopedBeanManager> scopedBeanManagers = _instance.get();

		return scopedBeanManagers.peek();
	}

	public static Closeable install(ScopedBeanManager scopedBeanManager) {
		Deque<ScopedBeanManager> scopedBeanManagers = _instance.get();

		scopedBeanManagers.push(scopedBeanManager);

		return () -> {
			Deque<ScopedBeanManager> closingScopedBeanManagers =
				_instance.get();

			ScopedBeanManager closingScopedBeanManager =
				closingScopedBeanManagers.pop();

			if (closingScopedBeanManagers.isEmpty()) {
				closingScopedBeanManager.destroyScopedBeans();

				_instance.remove();
			}
		};
	}

	private static final ThreadLocal<Deque<ScopedBeanManager>> _instance =
		new CentralizedThreadLocal<>(
			ScopedBeanManagerThreadLocal.class + "._instance",
			ConcurrentLinkedDeque::new);

}