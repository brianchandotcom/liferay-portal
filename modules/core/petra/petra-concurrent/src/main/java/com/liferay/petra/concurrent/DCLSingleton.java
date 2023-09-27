/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.concurrent;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Shuyang Zhou
 */
public class DCLSingleton<T> {

	public void destroy(Consumer<T> consumer) {
		synchronized (this) {
			T singleton = _singleton;

			if (singleton != null) {
				if (consumer != null) {
					consumer.accept(singleton);
				}

				_singleton = null;
			}
		}
	}

	public T getSingleton(Supplier<T> supplier) {
		T singleton = _singleton;

		if (singleton != null) {
			return singleton;
		}

		synchronized (this) {
			if (_singleton == null) {
				_singleton = supplier.get();
			}
		}

		return _singleton;
	}

	private volatile T _singleton;

}