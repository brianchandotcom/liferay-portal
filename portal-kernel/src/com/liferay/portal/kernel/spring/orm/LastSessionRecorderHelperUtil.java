/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.spring.orm;

/**
 * @author Shuyang Zhou
 */
public class LastSessionRecorderHelperUtil {

	public static void syncLastSessionState() {
		_lastSessionRecorderHelper.syncLastSessionState();
	}

	public static void syncLastSessionState(boolean portalSessionOnly) {
		_lastSessionRecorderHelper.syncLastSessionState(portalSessionOnly);
	}

	public void setLastSessionRecorderHelper(
		LastSessionRecorderHelper lastSessionRecorderHelper) {

		_lastSessionRecorderHelper = lastSessionRecorderHelper;
	}

	private static LastSessionRecorderHelper _lastSessionRecorderHelper;

}