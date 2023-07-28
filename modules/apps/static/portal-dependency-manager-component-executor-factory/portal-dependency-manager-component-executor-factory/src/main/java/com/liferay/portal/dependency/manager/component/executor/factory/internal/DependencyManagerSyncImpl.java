/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dependency.manager.component.executor.factory.internal;

import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.SystemExecutorServiceUtil;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSync;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class DependencyManagerSyncImpl implements DependencyManagerSync {

	public DependencyManagerSyncImpl(long syncTimeout) {
		_syncTimeout = syncTimeout;
	}

	@Override
	public void registerSyncCallable(Callable<Void> syncCallable) {
		_addFutureListener(
			_syncCallableDefaultNoticeableFuture,
			future -> {
				try {
					syncCallable.call();
				}
				catch (Exception exception) {
					_log.error("Unable to sync callable", exception);
				}
			});
	}

	@Override
	public void registerSyncFutureTask(
		FutureTask<Void> syncFutureTask, String taskName) {

		_addFutureListener(
			_syncFutureTaskDefaultNoticeableFuture,
			future -> {
				syncFutureTask.run();

				try {
					syncFutureTask.get(_syncTimeout, TimeUnit.SECONDS);
				}
				catch (Exception exception) {
					_log.error("Unable to sync future", exception);
				}
			});

		if (!syncFutureTask.isDone()) {
			ExecutorService systemExecutorService =
				SystemExecutorServiceUtil.getExecutorService();

			systemExecutorService.submit(
				SystemExecutorServiceUtil.renameThread(
					syncFutureTask, taskName));
		}
	}

	@Override
	public void sync() {
		if (_syncCallableDefaultNoticeableFuture.isDone()) {
			return;
		}

		_syncFutureTaskDefaultNoticeableFuture.run();

		_syncCallableDefaultNoticeableFuture.run();
	}

	private void _addFutureListener(
		DefaultNoticeableFuture<Void> defaultNoticeableFuture,
		FutureListener<Void> futureListener) {

		defaultNoticeableFuture.addFutureListener(
			new FutureListener<Void>() {

				@Override
				public void complete(Future<Void> future) {
					futureListener.complete(future);

					defaultNoticeableFuture.removeFutureListener(this);
				}

			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DependencyManagerSyncImpl.class);

	private final DefaultNoticeableFuture<Void>
		_syncCallableDefaultNoticeableFuture = new DefaultNoticeableFuture<>();
	private final DefaultNoticeableFuture<Void>
		_syncFutureTaskDefaultNoticeableFuture =
			new DefaultNoticeableFuture<>();
	private final long _syncTimeout;

}