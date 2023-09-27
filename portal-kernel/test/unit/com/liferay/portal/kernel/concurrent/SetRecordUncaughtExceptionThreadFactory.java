/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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