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

package com.liferay.portal.dependency.manager.component.executor.factory.internal;

import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.SystemExecutorServiceUtil;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSync;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
public class DependencyManagerSyncImpl implements DependencyManagerSync {

	public DependencyManagerSyncImpl(
		BlockingQueue<Future<Void>> blockingQueue,
		ServiceRegistration<?> componentExecutorFactoryServiceRegistration,
		long syncTimeout) {

		_blockingQueue = blockingQueue;
		_componentExecutorFactoryServiceRegistration =
			componentExecutorFactoryServiceRegistration;
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

		if (_componentExecutorFactoryServiceRegistration != null) {
			try {
				_componentExecutorFactoryServiceRegistration.unregister();
			}
			catch (IllegalStateException illegalStateException) {
				if (_log.isDebugEnabled()) {
					_log.debug(illegalStateException);
				}

				// Concurrent unregister, no need to do anything

			}

			try {
				Future<Void> future = null;

				while ((future = _blockingQueue.poll()) != null) {
					future.get(_syncTimeout, TimeUnit.SECONDS);
				}
			}
			catch (Exception exception) {
				_log.error(
					"Unable to drain depenedency manager's async tasks",
					exception);
			}
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

	private final BlockingQueue<Future<Void>> _blockingQueue;
	private final ServiceRegistration<?>
		_componentExecutorFactoryServiceRegistration;
	private final DefaultNoticeableFuture<Void>
		_syncCallableDefaultNoticeableFuture = new DefaultNoticeableFuture<>();
	private final DefaultNoticeableFuture<Void>
		_syncFutureTaskDefaultNoticeableFuture =
			new DefaultNoticeableFuture<>();
	private final long _syncTimeout;

}