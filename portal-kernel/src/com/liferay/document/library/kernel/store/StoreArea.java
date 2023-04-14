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

package com.liferay.document.library.kernel.store;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Adolfo Pérez
 */
public enum StoreArea {

	DELETED("_deleted"), LIVE(StringPool.BLANK);

	public static String getPath(String... path) {
		StoreArea storeArea = _storeAreaThreadLocal.get();

		return storeArea._namespace + StringPool.SLASH +
			StringUtil.merge(path, StringPool.SLASH);
	}

	public static <T extends Throwable> void withStoreArea(
			StoreArea storeArea, UnsafeRunnable<T> unsafeRunnable)
		throws T {

		StoreArea oldStoreArea = _storeAreaThreadLocal.get();

		try {
			_storeAreaThreadLocal.set(storeArea);

			unsafeRunnable.run();
		}
		finally {
			_storeAreaThreadLocal.set(oldStoreArea);
		}
	}

	private StoreArea(String namespace) {
		_namespace = namespace;
	}

	private static final ThreadLocal<StoreArea> _storeAreaThreadLocal =
		new CentralizedThreadLocal<>(
			StoreArea.class.getName() + "._storeAreaThreadLocal", () -> LIVE);

	private final String _namespace;

}