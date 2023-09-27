/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		String name, NoticeableExecutorService noticeableExecutorService) {

		return null;
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