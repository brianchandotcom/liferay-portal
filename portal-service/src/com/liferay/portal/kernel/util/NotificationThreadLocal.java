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

package com.liferay.portal.kernel.util;

/**
 * @author Jorge Ferrer
 */
public class NotificationThreadLocal {

	public static boolean isEnabled() {
		return _enabled.get();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #isEnabled}
	 */
	@Deprecated
	public static boolean isNotificationEnabled() {
		return isEnabled();
	}

	public static void setEnabled(boolean enabled) {
		_enabled.set(enabled);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setEnabled(boolean)}
	 */
	@Deprecated
	public static void setNotificationEnabled(boolean notificationEnabled) {
		setEnabled(notificationEnabled);
	}

	private static final ThreadLocal<Boolean> _enabled =
		new AutoResetThreadLocal<Boolean>(
			NotificationThreadLocal.class + "._enabled", true);

}