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

package com.liferay.portal.kernel.module.framework;

import com.liferay.petra.reflect.ReflectionUtil;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Shuyang Zhou
 */
public class ThrowableCollector {

	public void collect(Throwable t) {
		while (true) {
			Throwable throwable = _atomicReference.get();

			if (throwable != null) {
				throwable.addSuppressed(t);

				break;
			}

			if (_atomicReference.compareAndSet(null, t)) {
				break;
			}
		}
	}

	public Throwable getThrowable() {
		return _atomicReference.get();
	}

	public void rethrow() {
		Throwable throwable = _atomicReference.get();

		if (throwable != null) {
			ReflectionUtil.throwException(throwable);
		}
	}

	private final AtomicReference<Throwable> _atomicReference =
		new AtomicReference<>();

}