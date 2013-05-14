/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.executor;

import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.concurrent.ThreadPoolHandler;
import com.liferay.portal.kernel.executor.PortalExecutorFactory;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.util.ClassLoaderUtil;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class PortalExecutorFactoryImpl implements PortalExecutorFactory {

	public void afterPropertiesSet() {
		if (_corePoolSize < 0) {
			throw new IllegalArgumentException("Core pool size is less than 0");
		}

		if (_keepAliveTime < 0) {
			throw new IllegalArgumentException(
				"Keep alive time is less than 0");
		}

		if (_maxPoolSize <= 0) {
			throw new IllegalArgumentException(
				"Max pool size is less than or equal to 0");
		}

		if (_maxPoolSize < _corePoolSize) {
			throw new IllegalArgumentException(
				"Max pool size is less than core pool size");
		}

		if (_maxQueueSize <= 0) {
			throw new IllegalArgumentException(
				"Max queue size is less than or equal to 0");
		}

		if (_rejectedExecutionHandler == null) {
			throw new IllegalArgumentException(
				"Rejected execution handler is null");
		}

		if (_threadPoolHandler == null) {
			throw new IllegalArgumentException("Thread pool handler is null");
		}

		if (_timeUnit == null) {
			throw new IllegalArgumentException("Time unit is null");
		}
	}

	@Override
	public ThreadPoolExecutor createPortalExecutor(String executorName) {
		ThreadFactory threadFactory = new NamedThreadFactory(
			executorName, Thread.NORM_PRIORITY,
			ClassLoaderUtil.getPortalClassLoader());

		return new ThreadPoolExecutor(
			_corePoolSize, _maxPoolSize, _keepAliveTime, _timeUnit,
			_allowCoreThreadTimeout, _maxQueueSize, _rejectedExecutionHandler,
			threadFactory, _threadPoolHandler);
	}

	public void setAllowCoreThreadTimeout(boolean allowCoreThreadTimeout) {
		_allowCoreThreadTimeout = allowCoreThreadTimeout;
	}

	public void setCorePoolSize(int corePoolSize) {
		_corePoolSize = corePoolSize;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		_keepAliveTime = keepAliveTime;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		_maxPoolSize = maxPoolSize;
	}

	public void setMaxQueueSize(int maxQueueSize) {
		_maxQueueSize = maxQueueSize;
	}

	public void setRejectedExecutionHandler(
		RejectedExecutionHandler rejectedExecutionHandler) {

		_rejectedExecutionHandler = rejectedExecutionHandler;
	}

	public void setThreadPoolHandler(ThreadPoolHandler threadPoolHandler) {
		_threadPoolHandler = threadPoolHandler;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		_timeUnit = timeUnit;
	}

	private boolean _allowCoreThreadTimeout;
	private int _corePoolSize;
	private long _keepAliveTime;
	private int _maxPoolSize;
	private int _maxQueueSize;
	private RejectedExecutionHandler _rejectedExecutionHandler;
	private ThreadPoolHandler _threadPoolHandler;
	private TimeUnit _timeUnit;

}