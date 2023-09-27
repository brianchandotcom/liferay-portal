/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.cluster.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurationThreadLocal {

	public static boolean isLocalUpdate() {
		return _localUpdate.get();
	}

	public static void setLocalUpdate(boolean localUpdate) {
		_localUpdate.set(localUpdate);
	}

	private static final ThreadLocal<Boolean> _localUpdate =
		new CentralizedThreadLocal<>(
			ConfigurationThreadLocal.class + "._localUpdate",
			() -> Boolean.FALSE, false);

}