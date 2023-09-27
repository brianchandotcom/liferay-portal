/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.events;

/**
 * @author Shuyang Zhou
 */
public class ShutdownHelperUtil {

	public static boolean isShutdown() {
		return _shutdown;
	}

	public static void setShutdown(boolean shutdown) {
		_shutdown = shutdown;
	}

	private static volatile boolean _shutdown;

}