/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot;

/**
 * @author Michael Hashimoto
 */
public class ThreadUtil {

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException interruptedException) {
			throw new RuntimeException(interruptedException);
		}
	}

}