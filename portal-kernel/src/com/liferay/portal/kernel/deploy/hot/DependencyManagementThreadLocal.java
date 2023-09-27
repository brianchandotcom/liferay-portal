/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.deploy.hot;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class DependencyManagementThreadLocal {

	public static Boolean isEnabled() {
		return _enabled.get();
	}

	public static void setEnabled(boolean enabled) {
		_enabled.set(enabled);
	}

	private static final ThreadLocal<Boolean> _enabled;

	static {
		if (GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.HOT_DEPLOY_DEPENDENCY_MANAGEMENT_ENABLED),
				true)) {

			_enabled = new CentralizedThreadLocal<>(
				DependencyManagementThreadLocal.class + ".enabled",
				() -> Boolean.TRUE);
		}
		else {
			_enabled = new ThreadLocal<Boolean>() {

				@Override
				public Boolean get() {
					return Boolean.FALSE;
				}

			};
		}
	}

}