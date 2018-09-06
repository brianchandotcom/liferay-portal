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

package com.liferay.portal.internal.minifier;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.io.Closeable;

/**
 * @author Shuyang Zhou
 */
public class MinifierThreadLocal {

	public static Closeable disable() {
		if (PropsValues.MINIFIER_ENABLED && _enabled.get()) {
			_enabled.set(false);

			return () -> _enabled.set(true);
		}

		return _dummy;
	}

	public static boolean isEnabled() {
		return _enabled.get();
	}

	private static final Closeable _dummy = () -> {
	};
	private static final ThreadLocal<Boolean> _enabled =
		new CentralizedThreadLocal<>(
			MinifierThreadLocal.class + "._enabled", () -> Boolean.TRUE);

}