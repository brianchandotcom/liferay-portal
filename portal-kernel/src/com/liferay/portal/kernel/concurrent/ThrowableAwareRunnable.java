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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public abstract class ThrowableAwareRunnable implements Runnable {

	public Throwable getThrowable() {
		return _throwable;
	}

	public boolean hasException() {
		if (_throwable != null) {
			return true;
		}

		return false;
	}

	@Override
	public void run() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			doRun();
		}
		catch (Exception e) {
			_log.error("Unable to process runnable: " + e.getMessage(), e);

			_throwable = e;
		}
	}

	protected abstract void doRun() throws Exception;

	private static final Log _log = LogFactoryUtil.getLog(
		ThrowableAwareRunnable.class);

	private Throwable _throwable;

}