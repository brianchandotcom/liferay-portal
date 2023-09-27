/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class RecordUncaughtExceptionHandler
	implements Thread.UncaughtExceptionHandler {

	public Map<Thread, Throwable> getUncaughtMap() {
		return _uncaughtMap;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		_uncaughtMap.put(thread, throwable);
	}

	private final Map<Thread, Throwable> _uncaughtMap =
		new ConcurrentHashMap<>();

}