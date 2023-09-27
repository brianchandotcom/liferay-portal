/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dependency.manager;

import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.SystemExecutorServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SystemCheckerUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class DependencyManagerSyncUtil {

	public static void registerSyncCallable(Callable<?> syncCallable) {
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

	public static void registerSyncFutureTask(
		FutureTask<?> syncFutureTask, String taskName) {

		_addFutureListener(
			_syncFutureTaskDefaultNoticeableFuture,
			future -> {
				syncFutureTask.run();

				try {
					syncFutureTask.get(_SYNC_TIMEOUT, TimeUnit.SECONDS);
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

	public static void sync() {
		if (_syncCallableDefaultNoticeableFuture.isDone()) {
			return;
		}

		_syncFutureTaskDefaultNoticeableFuture.run();

		_syncCallableDefaultNoticeableFuture.run();

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.INITIAL_SYSTEM_CHECK_ENABLED), true)) {

			SystemCheckerUtil.runSystemCheckers(_log::info, _log::warn);
		}
	}

	private static void _addFutureListener(
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

	private static final long _SYNC_TIMEOUT = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.DEPENDENCY_MANAGER_SYNC_TIMEOUT), 60);

	private static final Log _log = LogFactoryUtil.getLog(
		DependencyManagerSyncUtil.class);

	private static final DefaultNoticeableFuture<Void>
		_syncCallableDefaultNoticeableFuture = new DefaultNoticeableFuture<>();
	private static final DefaultNoticeableFuture<Void>
		_syncFutureTaskDefaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

}