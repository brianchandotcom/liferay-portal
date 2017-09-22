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