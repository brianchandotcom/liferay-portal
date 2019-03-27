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

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.petra.concurrent.BaseNoticeableExecutorService;
import com.liferay.petra.concurrent.NoticeableExecutorService;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.executor.PortalExecutorManager;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael C. Han
 */
public class MockPortalExecutorManager implements PortalExecutorManager {

	@Override
	public NoticeableExecutorService getPortalExecutor(String name) {
		return new MockNoticeableExecutorService();
	}

	@Override
	public NoticeableExecutorService getPortalExecutor(
		String name, boolean createIfAbsent) {

		return new MockNoticeableExecutorService();
	}

	@Override
	public NoticeableExecutorService registerPortalExecutor(
		String name, NoticeableExecutorService threadPoolExecutor) {

		return null;
	}

	@Override
	public void shutdown() {
	}

	@Override
	public void shutdown(boolean interrupt) {
	}

	private static class MockNoticeableExecutorService
		extends BaseNoticeableExecutorService
		implements NoticeableExecutorService {

		@Override
		public boolean awaitTermination(long timeout, TimeUnit unit) {
			return true;
		}

		@Override
		public void execute(Runnable runnable) {
			runnable.run();
		}

		@Override
		public boolean isShutdown() {
			return _shutdown;
		}

		@Override
		public boolean isTerminated() {
			return _shutdown;
		}

		@Override
		public void shutdown() {
			_shutdown = true;
		}

		@Override
		public List<Runnable> shutdownNow() {
			_shutdown = true;

			return Collections.emptyList();
		}

		@Override
		public NoticeableFuture<Void> terminationNoticeableFuture() {
			return null;
		}

		private boolean _shutdown;

	}

}