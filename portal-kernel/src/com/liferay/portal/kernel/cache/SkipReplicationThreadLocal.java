/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cache;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Tina Tian
 */
public class SkipReplicationThreadLocal {

	public static boolean isEnabled() {
		return _skipReplication.get();
	}

	public static SafeCloseable setEnabledWithSafeCloseable(boolean enabled) {
		return _skipReplication.setWithSafeCloseable(enabled);
	}

	private static final CentralizedThreadLocal<Boolean> _skipReplication =
		new CentralizedThreadLocal<>(
			SkipReplicationThreadLocal.class + "._skipReplication",
			() -> Boolean.FALSE, false);

}