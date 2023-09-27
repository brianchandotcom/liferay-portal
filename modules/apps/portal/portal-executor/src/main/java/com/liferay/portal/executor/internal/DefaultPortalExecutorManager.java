/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.executor.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Shuyang Zhou
 * @author Preston Crary
 */
@Component(service = PortalExecutorManager.class)
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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortalExecutorConfig.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(portalExecutorConfig, emitter) -> emitter.emit(
					portalExecutorConfig.getName())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();

		for (NoticeableExecutorService noticeableExecutorService :
				_noticeableExecutorServices.values()) {

			noticeableExecutorService.shutdownNow();
		}
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
		PortalExecutorConfig portalExecutorConfig =
			_serviceTrackerMap.getService(name);

		if (portalExecutorConfig != null) {
			return portalExecutorConfig;
		}

		PortalExecutorConfig defaultPortalExecutorConfig =
			_serviceTrackerMap.getService(DEFAULT_CONFIG_NAME);

		if (defaultPortalExecutorConfig != null) {
			return defaultPortalExecutorConfig;
		}

		return _defaultPortalExecutorConfig;
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
	private ServiceTrackerMap<String, PortalExecutorConfig> _serviceTrackerMap;

}