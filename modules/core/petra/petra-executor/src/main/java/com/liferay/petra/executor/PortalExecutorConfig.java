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

package com.liferay.petra.executor;

import com.liferay.petra.concurrent.ThreadPoolHandler;

import java.io.Serializable;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class PortalExecutorConfig implements Serializable {

	public PortalExecutorConfig(
		String name, int corePoolSize, int maxPoolSize, long keepAliveTime,
		TimeUnit timeUnit, int maxQueueSize, ThreadFactory threadFactory,
		RejectedExecutionHandler rejectedExecutionHandler,
		ThreadPoolHandler threadPoolHandler) {

		if (corePoolSize < 1) {
			throw new IllegalArgumentException(
				"To ensure FIFO, core pool size must be 1 or greater");
		}

		_name = name;
		_corePoolSize = corePoolSize;
		_maxPoolSize = maxPoolSize;
		_keepAliveTime = keepAliveTime;
		_timeUnit = timeUnit;
		_maxQueueSize = maxQueueSize;
		_threadFactory = threadFactory;
		_rejectedExecutionHandler = rejectedExecutionHandler;
		_threadPoolHandler = threadPoolHandler;
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

	private static final long serialVersionUID = 1L;

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