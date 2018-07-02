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

package com.liferay.portal.kernel.executor;

import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolHandler;
import com.liferay.portal.kernel.util.NamedThreadFactory;

import java.io.Serializable;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.petra.executor.PortalExecutorConfig}
 */
@Deprecated
public class PortalExecutorConfig implements Serializable {

	public PortalExecutorConfig(
		String name, int corePoolSize, int maxPoolSize, long keepAliveTime,
		TimeUnit timeUnit, boolean allowCoreThreadTimeout, int maxQueueSize,
		RejectedExecutionHandler rejectedExecutionHandler,
		ThreadPoolHandler threadPoolHandler, int priority,
		ClassLoader contextClassLoader) {

		_name = name;
		_corePoolSize = corePoolSize;
		_maxPoolSize = maxPoolSize;
		_keepAliveTime = keepAliveTime;
		_timeUnit = timeUnit;
		_allowCoreThreadTimeout = allowCoreThreadTimeout;
		_maxQueueSize = maxQueueSize;
		_rejectedExecutionHandler = rejectedExecutionHandler;
		_threadPoolHandler = threadPoolHandler;

		_threadFactory = new NamedThreadFactory(
			name, priority, contextClassLoader);
	}

	public int getCorePoolSize() {
		return _corePoolSize;
	}

	public long getKeepAliveTime() {
		return _keepAliveTime;
	}

	public int getMaxPoolSize() {
		return _maxPoolSize;
	}

	public int getMaxQueueSize() {
		return _maxQueueSize;
	}

	public String getName() {
		return _name;
	}

	public RejectedExecutionHandler getRejectedExecutionHandler() {
		return _rejectedExecutionHandler;
	}

	public ThreadFactory getThreadFactory() {
		return _threadFactory;
	}

	public ThreadPoolHandler getThreadPoolHandler() {
		return _threadPoolHandler;
	}

	public TimeUnit getTimeUnit() {
		return _timeUnit;
	}

	public boolean isAllowCoreThreadTimeout() {
		return _allowCoreThreadTimeout;
	}

	private static final long serialVersionUID = 1L;

	private final boolean _allowCoreThreadTimeout;
	private final int _corePoolSize;
	private final long _keepAliveTime;
	private final int _maxPoolSize;
	private final int _maxQueueSize;
	private final String _name;
	private final RejectedExecutionHandler _rejectedExecutionHandler;
	private final ThreadFactory _threadFactory;
	private final ThreadPoolHandler _threadPoolHandler;
	private final TimeUnit _timeUnit;

}