/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Shuyang Zhou
 */
public class LogAssertionUncaughtExceptionHandler
	implements Thread.UncaughtExceptionHandler {

	public LogAssertionUncaughtExceptionHandler(
		Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {

		_uncaughtExceptionHandler = uncaughtExceptionHandler;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		if (_uncaughtExceptionHandler != null) {
			_uncaughtExceptionHandler.uncaughtException(thread, throwable);
		}

		LogAssertionTestRule.caughtFailure(
			new AssertionError(
				StringBundler.concat(
					"Uncaught exception in ", thread,
					StringPool.COMMA_AND_SPACE, throwable),
				throwable));
	}

	private final Thread.UncaughtExceptionHandler _uncaughtExceptionHandler;

}