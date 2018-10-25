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

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author Neil Griffin
 */
public class ScopedBeanManagerStack
	extends ConcurrentLinkedDeque<ScopedBeanManager> {

	public static ScopedBeanManagerStack getCurrentInstance() {
		return _instance.get();
	}

	public Closeable install(ScopedBeanManager scopedBeanManager) {
		push(scopedBeanManager);

		_instance.set(this);

		return () -> {
			int sizeBeforePop = size();

			pop();

			if (sizeBeforePop == 1) {
				scopedBeanManager.destroyScopedBeans();

				_instance.remove();
			}
		};
	}

	private static final ThreadLocal<ScopedBeanManagerStack> _instance =
		new CentralizedThreadLocal<>(
			ScopedBeanManagerStack.class + "._instance");

}