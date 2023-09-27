/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.module.framework;

import com.liferay.petra.reflect.ReflectionUtil;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Shuyang Zhou
 */
public class ThrowableCollector {

	public void collect(Throwable throwable1) {
		while (true) {
			Throwable throwable2 = _atomicReference.get();

			if (throwable2 != null) {
				throwable2.addSuppressed(throwable1);

				break;
			}

			if (_atomicReference.compareAndSet(null, throwable1)) {
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