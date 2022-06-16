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

package com.liferay.portal.log;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;

import java.util.Map;

import org.apache.logging.log4j.ThreadContext;

/**
 * @author Tina Tian
 */
public class Log4jLogContextLogWrapper extends LogWrapper {

	public Log4jLogContextLogWrapper(Log log) {
		super(log);

		setLogWrapperClassName(Log4jLogContextLogWrapper.class.getName());
	}

	@Override
	public void debug(Object message) {
		_populateThreadContext();

		super.debug(message);

		_cleanThreadContext();
	}

	@Override
	public void debug(Object message, Throwable throwable) {
		_populateThreadContext();

		super.debug(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void debug(Throwable throwable) {
		_populateThreadContext();

		super.debug(null, throwable);

		_cleanThreadContext();
	}

	@Override
	public void error(Object message) {
		_populateThreadContext();

		super.error(message);

		_cleanThreadContext();
	}

	@Override
	public void error(Object message, Throwable throwable) {
		_populateThreadContext();

		super.error(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void error(Throwable throwable) {
		_populateThreadContext();

		super.error(null, throwable);

		_cleanThreadContext();
	}

	@Override
	public void fatal(Object message) {
		_populateThreadContext();

		super.fatal(message);

		_cleanThreadContext();
	}

	@Override
	public void fatal(Object message, Throwable throwable) {
		_populateThreadContext();

		super.fatal(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void fatal(Throwable throwable) {
		_populateThreadContext();

		super.fatal(null, throwable);

		_cleanThreadContext();
	}

	@Override
	public void info(Object message) {
		_populateThreadContext();

		super.info(message);

		_cleanThreadContext();
	}

	@Override
	public void info(Object message, Throwable throwable) {
		_populateThreadContext();

		super.info(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void info(Throwable throwable) {
		_populateThreadContext();

		super.info(null, throwable);

		_cleanThreadContext();
	}

	@Override
	public void trace(Object message) {
		_populateThreadContext();

		super.trace(message);

		_cleanThreadContext();
	}

	@Override
	public void trace(Object message, Throwable throwable) {
		_populateThreadContext();

		super.trace(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void trace(Throwable throwable) {
		_populateThreadContext();

		super.trace(null, throwable);

		_cleanThreadContext();
	}

	@Override
	public void warn(Object message) {
		_populateThreadContext();

		super.warn(message);

		_cleanThreadContext();
	}

	@Override
	public void warn(Object message, Throwable throwable) {
		_populateThreadContext();

		super.warn(message, throwable);

		_cleanThreadContext();
	}

	@Override
	public void warn(Throwable throwable) {
		_populateThreadContext();

		super.warn(null, throwable);

		_cleanThreadContext();
	}

	private void _cleanThreadContext() {
		ThreadContext.clearMap();
	}

	private void _populateThreadContext() {
		ServiceTrackerList<LogContext> serviceTrackerList = _serviceTrackerList;

		if (serviceTrackerList == null) {
			return;
		}

		for (LogContext logContext : serviceTrackerList) {
			Map<String, String> context = logContext.getContext();

			for (Map.Entry<String, String> entry : context.entrySet()) {
				ThreadContext.put(
					logContext.getName() + "." + entry.getKey(),
					entry.getValue());
			}
		}
	}

	private static volatile ServiceTrackerList<LogContext> _serviceTrackerList;

	static {
		PortalLifecycleUtil.register(
			new BasePortalLifecycle() {

				@Override
				protected void doPortalDestroy() {
					ServiceTrackerList<LogContext> serviceTrackerList =
						_serviceTrackerList;

					_serviceTrackerList = null;

					if (serviceTrackerList != null) {
						serviceTrackerList.close();
					}
				}

				@Override
				protected void doPortalInit() {
					_serviceTrackerList = ServiceTrackerListFactory.open(
						SystemBundleUtil.getBundleContext(), LogContext.class);
				}

			},
			PortalLifecycle.METHOD_ALL);
	}

}