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

		StringBundler sb = new StringBundler(4);

		sb.append("Uncaught exception in ");
		sb.append(thread);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(throwable);

		LogAssertionTestRule.caughtFailure(
			new AssertionError(sb.toString(), throwable));
	}

	private final Thread.UncaughtExceptionHandler _uncaughtExceptionHandler;

}