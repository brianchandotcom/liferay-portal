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
	immediate = true,
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

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_corePoolSize = GetterUtil.getInteger(properties.get("corePoolSize"));

		if (_corePoolSize < 0) {
			throw new IllegalArgumentException("Core pool size is less than 0");
		}

		_keepAliveTime = GetterUtil.getLong(properties.get("keepAliveTime"));

		if (_keepAliveTime < 0) {
			throw new IllegalArgumentException(
				"Keep alive time is less than 0");
		}

		_maxPoolSize = GetterUtil.getInteger(properties.get("maxPoolSize"));

		if (_maxPoolSize <= 0) {
			throw new IllegalArgumentException(
				"Max pool size is less than or equal to 0");
		}

		if (_maxPoolSize < _corePoolSize) {
			throw new IllegalArgumentException(
				"Max pool size is less than core pool size");
		}

		_maxQueueSize = GetterUtil.getInteger(
			properties.get("maxQueueSize"), Integer.MAX_VALUE);

		if (_maxQueueSize <= 0) {
			throw new IllegalArgumentException(
				"Max queue size is less than or equal to 0");
		}

		try {
			_rejectedExecutionHandler =
				(RejectedExecutionHandler)InstanceFactory.newInstance(
					GetterUtil.getString(
						properties.get("rejectedExecutionHandler")));

			_threadPoolHandler = (ThreadPoolHandler)InstanceFactory.newInstance(
				GetterUtil.getString(properties.get("threadPoolHandler")));
		}
		catch (Exception e) {
			throw new IllegalStateException("Unable to instantiate objects", e);
		}

		_timeUnit = TimeUnit.valueOf(
			GetterUtil.getString(properties.get("timeUnit")));
	}

	private volatile boolean _allowCoreThreadTimeout;
	private volatile int _corePoolSize;
	private volatile long _keepAliveTime;
	private volatile int _maxPoolSize;
	private volatile int _maxQueueSize;
	private volatile RejectedExecutionHandler _rejectedExecutionHandler;
	private volatile ThreadPoolHandler _threadPoolHandler;
	private volatile TimeUnit _timeUnit;

}