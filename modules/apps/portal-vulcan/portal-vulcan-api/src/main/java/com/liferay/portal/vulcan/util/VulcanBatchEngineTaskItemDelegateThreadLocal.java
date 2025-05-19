/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;

/**
 * @author Daniel Szimko
 */
public class VulcanBatchEngineTaskItemDelegateThreadLocal {

	public static VulcanBatchEngineTaskItemDelegate<?> get() {
		return _threadLocal.get();
	}

	public static void remove() {
		_threadLocal.remove();
	}

	public static void set(VulcanBatchEngineTaskItemDelegate<?> delegate) {
		_threadLocal.set(delegate);
	}

	private static final ThreadLocal<VulcanBatchEngineTaskItemDelegate<?>>
		_threadLocal = new ThreadLocal<>();

}