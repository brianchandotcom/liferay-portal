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

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Shuyang Zhou
 */
public class DCLSingleton<T> {

	public void destroy(Consumer<T> destroyer) {
		synchronized (this) {
			T singleton = _singleton;

			if (singleton != null) {
				if (destroyer != null) {
					destroyer.accept(singleton);
				}

				_singleton = null;
			}
		}
	}

	public T getSingleton(Supplier<T> creator) {
		T singleton = _singleton;

		if (singleton != null) {
			return singleton;
		}

		synchronized (this) {
			if (_singleton == null) {
				_singleton = creator.get();
			}
		}

		return _singleton;
	}

	private volatile T _singleton;

}