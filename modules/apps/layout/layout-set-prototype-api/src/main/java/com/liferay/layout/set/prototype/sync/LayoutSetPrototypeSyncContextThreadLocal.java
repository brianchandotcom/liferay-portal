/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.sync;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Carlos Correa
 */
public class LayoutSetPrototypeSyncContextThreadLocal {

	public static String getSyncSessionId() {
		return _syncSessionId.get();
	}

	public static void setSyncSessionId(String syncSessionId) {
		_syncSessionId.set(syncSessionId);
	}

	private static final ThreadLocal<String> _syncSessionId =
		new CentralizedThreadLocal<>(
			LayoutSetPrototypeSyncContextThreadLocal.class + "._syncSessionId",
			() -> null);

}