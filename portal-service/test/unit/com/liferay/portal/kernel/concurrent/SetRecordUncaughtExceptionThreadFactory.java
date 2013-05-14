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

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Shuyang Zhou
 */
public class SetRecordUncaughtExceptionThreadFactory implements ThreadFactory {

	public RecordUncaughtExceptionHandler getRecordUncaughtExceptionHandler() {
		return recordUncaughtExceptionHandler;
	}

	@Override
	public Thread newThread(Runnable runnable) {
		Thread thread = _threadFactory.newThread(runnable);

		thread.setUncaughtExceptionHandler(recordUncaughtExceptionHandler);

		return thread;
	}

	private final ThreadFactory _threadFactory =
		Executors.defaultThreadFactory();
	private final RecordUncaughtExceptionHandler
		recordUncaughtExceptionHandler = new RecordUncaughtExceptionHandler();

}