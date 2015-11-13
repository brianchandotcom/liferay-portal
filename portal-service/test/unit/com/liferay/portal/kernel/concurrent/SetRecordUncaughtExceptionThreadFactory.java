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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Shuyang Zhou
 */
public class SetRecordUncaughtExceptionThreadFactory implements ThreadFactory {

	public List<Thread> getCreatedThreads() {
		return _createdThreads;
	}

	public RecordUncaughtExceptionHandler getRecordUncaughtExceptionHandler() {
		return _recordUncaughtExceptionHandler;
	}

	@Override
	public Thread newThread(Runnable runnable) {
		Thread thread = _threadFactory.newThread(runnable);

		thread.setUncaughtExceptionHandler(_recordUncaughtExceptionHandler);

		_createdThreads.add(thread);

		return thread;
	}

	private final List<Thread> _createdThreads = new ArrayList<>();
	private final RecordUncaughtExceptionHandler
		_recordUncaughtExceptionHandler = new RecordUncaughtExceptionHandler();
	private final ThreadFactory _threadFactory =
		Executors.defaultThreadFactory();

}