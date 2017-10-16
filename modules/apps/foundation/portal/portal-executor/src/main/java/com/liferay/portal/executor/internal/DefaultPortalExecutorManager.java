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

package com.liferay.portal.executor.internal;

import com.liferay.petra.concurrent.NoticeableExecutorService;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.concurrent.NoticeableThreadPoolExecutor;
import com.liferay.petra.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.petra.executor.PortalExecutorConfig;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Shuyang Zhou
 * @author Preston Crary
 */
@Component(immediate = true, service = PortalExecutorManager.class)
public class DefaultPortalExecutorManager implements PortalExecutorManager {

	public static final String DEFAULT_CONFIG_NAME = "default";

	@Override
	public NoticeableExecutorService getPortalExecutor(String name) {
		return getPortalExecutor(name, true);
	}

	@Override
	public NoticeableExecutorService getPortalExecutor(
		String name, boolean createIfAbsent) {

		NoticeableExecutorService noticeableExecutorService =
			_noticeableExecutorServices.get(name);

		if ((noticeableExecutorService == null) && createIfAbsent) {
			noticeableExecutorService = _createPortalExecutor(name);

			NoticeableExecutorService previousNoticeableExecutorService =
				registerPortalExecutor(name, noticeableExecutorService);

			if (previousNoticeableExecutorService != null) {
				noticeableExecutorService.shutdown();

				noticeableExecutorService = previousNoticeableExecutorService;
			}
		}

		return noticeableExecutorService;
	}

	@Override
	public NoticeableExecutorService registerPortalExecutor(
		String name, NoticeableExecutorService noticeableExecutorService) {

		NoticeableExecutorService previousNoticeableExecutorService =
			_noticeableExecutorServices.putIfAbsent(
				name, noticeableExecutorService);

		if (previousNoticeableExecutorService == null) {
			NoticeableFuture<Void> terminationNoticeableFuture =
				noticeableExecutorService.terminationNoticeableFuture();

			terminationNoticeableFuture.addFutureListener(
				future -> _noticeableExecutorServices.remove(name));
		}

		return previousNoticeableExecutorService;
	}

	@Override
	public void shutdown() {
		shutdown(false);
	}

	@Override
	public void shutdown(boolean interrupt) {
		for (NoticeableExecutorService noticeableExecutorService :
				_noticeableExecutorServices.values()) {

			if (interrupt) {
				noticeableExecutorService.shutdownNow();
			}
			else {
				noticeableExecutorService.shutdown();
			}
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addPortalExecutorConfig(
		PortalExecutorConfig portalExecutorConfig) {

		_portalExecutorConfigs.putIfAbsent(
			portalExecutorConfig.getName(), portalExecutorConfig);
	}

	@Deactivate
	protected void deactivate() {
		shutdown(true);
	}

	protected void removePortalExecutorConfig(
		PortalExecutorConfig portalExecutorConfig) {

		_portalExecutorConfigs.remove(
			portalExecutorConfig.getName(), portalExecutorConfig);
	}

	private NoticeableExecutorService _createPortalExecutor(
		String executorName) {

		PortalExecutorConfig portalExecutorConfig = _getPortalExecutorConfig(
			executorName);

		return new NoticeableThreadPoolExecutor(
			portalExecutorConfig.getCorePoolSize(),
			portalExecutorConfig.getMaxPoolSize(),
			portalExecutorConfig.getKeepAliveTime(),
			portalExecutorConfig.getTimeUnit(),
			new LinkedBlockingQueue<>(portalExecutorConfig.getMaxQueueSize()),
			portalExecutorConfig.getThreadFactory(),
			portalExecutorConfig.getRejectedExecutionHandler(),
			portalExecutorConfig.getThreadPoolHandler());
	}

	private PortalExecutorConfig _getPortalExecutorConfig(String name) {
		PortalExecutorConfig portalExecutorConfig = _portalExecutorConfigs.get(
			name);

		if (portalExecutorConfig != null) {
			return portalExecutorConfig;
		}

		return _portalExecutorConfigs.getOrDefault(
			DEFAULT_CONFIG_NAME, _defaultPortalExecutorConfig);
	}

	private final PortalExecutorConfig _defaultPortalExecutorConfig =
		new PortalExecutorConfig(
			DEFAULT_CONFIG_NAME, 1, 10, 60, TimeUnit.SECONDS, Integer.MAX_VALUE,
			new NamedThreadFactory(
				DEFAULT_CONFIG_NAME, Thread.NORM_PRIORITY,
				PortalClassLoaderUtil.getClassLoader()),
			new ThreadPoolExecutor.AbortPolicy(),
			new ThreadPoolHandlerAdapter() {

				@Override
				public void afterExecute(
					Runnable runnable, Throwable throwable) {

					CentralizedThreadLocal.clearShortLivedThreadLocals();
				}

			});

	private final ConcurrentMap<String, NoticeableExecutorService>
		_noticeableExecutorServices = new ConcurrentHashMap<>();
	private final ConcurrentMap<String, PortalExecutorConfig>
		_portalExecutorConfigs = new ConcurrentHashMap<>();

}