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
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSync;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
public class DependencyManagerSyncImpl implements DependencyManagerSync {

	public DependencyManagerSyncImpl(
		ExecutorService executorService,
		ServiceRegistration<?> componentExecutorFactoryRegistration,
		long syncTimeout) {

		_executorService = executorService;
		_componentExecutorFactoryRegistration =
			componentExecutorFactoryRegistration;
		_syncTimeout = syncTimeout;
	}

	@Override
	public void registerSyncCallable(Callable<Void> syncCallable) {
		_addFutureListener(
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
	public void registerSyncFuture(Future<Void> syncFuture) {
		_addFutureListener(
			future -> {
				try {
					syncFuture.get(_syncTimeout, TimeUnit.SECONDS);
				}
				catch (Exception exception) {
					_log.error("Unable to sync future", exception);
				}
			});
	}

	@Override
	public void sync() {
		if (_syncDefaultNoticeableFuture.isDone()) {
			return;
		}

		try {
			_componentExecutorFactoryRegistration.unregister();
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException, illegalStateException);
			}

			// Concurrent unregister, no need to do anything

		}

		_executorService.shutdown();

		try {
			if (!_executorService.awaitTermination(
					_syncTimeout, TimeUnit.SECONDS)) {

				_executorService.shutdownNow();

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Dependency manager sync timeout after waiting " +
							_syncTimeout + "s");
				}
			}
		}
		catch (InterruptedException interruptedException) {
			_log.error(
				"Dependency manager sync interrupted", interruptedException);
		}

		_syncDefaultNoticeableFuture.run();
	}

	private void _addFutureListener(FutureListener<Void> futureListener) {
		_syncDefaultNoticeableFuture.addFutureListener(
			new FutureListener<Void>() {

				@Override
				public void complete(Future<Void> future) {
					futureListener.complete(future);

					_syncDefaultNoticeableFuture.removeFutureListener(this);
				}

			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DependencyManagerSyncImpl.class);

	private final ServiceRegistration<?> _componentExecutorFactoryRegistration;
	private final ExecutorService _executorService;
	private final DefaultNoticeableFuture<Void> _syncDefaultNoticeableFuture =
		new DefaultNoticeableFuture<>();
	private final long _syncTimeout;

}