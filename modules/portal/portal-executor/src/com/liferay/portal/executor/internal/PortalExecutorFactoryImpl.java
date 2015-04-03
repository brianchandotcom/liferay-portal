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

import com.liferay.portal.executor.PortalExecutorFactory;
import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.concurrent.ThreadPoolHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = {
		"allowCoreThreadTimeout=true", "corePoolSize=0", "keepAliveTime=60",
		"maxPoolSize=10",
		"rejectedExecutionHandler=com.liferay.portal.kernel.concurrent.AbortPolicy",
		"threadPoolHandler=com.liferay.portal.kernel.concurrent.ClearThreadLocalThreadPoolHandler",
		"timeUnit=SECONDS"
	},
	service = PortalExecutorFactory.class
)
public class PortalExecutorFactoryImpl implements PortalExecutorFactory {

	@Override
	public ThreadPoolExecutor createPortalExecutor(String executorName) {
		ThreadFactory threadFactory = new NamedThreadFactory(
			executorName, Thread.NORM_PRIORITY,
			PortalClassLoaderUtil.getClassLoader());

		Config config = _config;

		return new ThreadPoolExecutor(
			config._corePoolSize, config._maxPoolSize, config._keepAliveTime,
			config._timeUnit, config._allowCoreThreadTimeout,
			config._maxQueueSize, config._rejectedExecutionHandler,
			threadFactory, config._threadPoolHandler);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) throws Exception {
		int corePoolSize = GetterUtil.getInteger(
			properties.get("corePoolSize"));

		if (corePoolSize < 0) {
			throw new IllegalArgumentException("Core pool size is less than 0");
		}

		long keepAliveTime = GetterUtil.getLong(
			properties.get("keepAliveTime"));

		if (keepAliveTime < 0) {
			throw new IllegalArgumentException(
				"Keep alive time is less than 0");
		}

		int maxPoolSize = GetterUtil.getInteger(properties.get("maxPoolSize"));

		if (maxPoolSize <= 0) {
			throw new IllegalArgumentException(
				"Max pool size is less than or equal to 0");
		}

		if (maxPoolSize < corePoolSize) {
			throw new IllegalArgumentException(
				"Max pool size is less than core pool size");
		}

		int maxQueueSize = GetterUtil.getInteger(
			properties.get("maxQueueSize"), Integer.MAX_VALUE);

		if (maxQueueSize <= 0) {
			throw new IllegalArgumentException(
				"Max queue size is less than or equal to 0");
		}

		_config = new Config(
			GetterUtil.getBoolean(
				properties.get("allowCoreThreadTimeout"), true),
			corePoolSize, keepAliveTime, maxPoolSize, maxQueueSize,
			(RejectedExecutionHandler)InstanceFactory.newInstance(
				GetterUtil.getString(
					properties.get("rejectedExecutionHandler"))),
			(ThreadPoolHandler)InstanceFactory.newInstance(
				GetterUtil.getString(properties.get("threadPoolHandler"))),
			TimeUnit.valueOf(GetterUtil.getString(properties.get("timeUnit"))));
	}

	private volatile Config _config;

	private static class Config {

		private Config(
			boolean allowCoreThreadTimeout, int corePoolSize,
			long keepAliveTime, int maxPoolSize, int maxQueueSize,
			RejectedExecutionHandler rejectedExecutionHandler,
			ThreadPoolHandler threadPoolHandler, TimeUnit timeUnit) {

			_allowCoreThreadTimeout = allowCoreThreadTimeout;
			_corePoolSize = corePoolSize;
			_keepAliveTime = keepAliveTime;
			_maxPoolSize = maxPoolSize;
			_maxQueueSize = maxQueueSize;
			_rejectedExecutionHandler = rejectedExecutionHandler;
			_threadPoolHandler = threadPoolHandler;
			_timeUnit = timeUnit;
		}

		private final boolean _allowCoreThreadTimeout;
		private final int _corePoolSize;
		private final long _keepAliveTime;
		private final int _maxPoolSize;
		private final int _maxQueueSize;
		private final RejectedExecutionHandler _rejectedExecutionHandler;
		private final ThreadPoolHandler _threadPoolHandler;
		private final TimeUnit _timeUnit;

	}

}