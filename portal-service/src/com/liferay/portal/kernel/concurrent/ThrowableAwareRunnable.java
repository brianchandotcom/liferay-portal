/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ClassUtil;

/**
 * @author Michael C. Han
 */
public abstract class ThrowableAwareRunnable implements Runnable {

	public Throwable getThrowable() {
		return _throwable;
	}

	public boolean hasException() {
		return (_throwable != null);
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Processing runnable " + ClassUtil.getClassName(this));
			}

			doRun();
		}
		catch (Exception e) {
			_log.error("Unable to process runnable: " + e.getMessage());

			_throwable = e;
		}
		finally {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Completed processing runnable " +
						ClassUtil.getClassName(this) + " in " +
							(System.currentTimeMillis() - start) + "ms");
			}
		}
	}

	protected abstract void doRun() throws Exception;

	private static Log _log = LogFactoryUtil.getLog(
		ThrowableAwareRunnable.class);

	private Throwable _throwable;

}