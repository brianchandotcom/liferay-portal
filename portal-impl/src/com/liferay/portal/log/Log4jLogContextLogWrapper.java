/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.log;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.ThreadContext;

/**
 * @author Tina Tian
 */
public class Log4jLogContextLogWrapper extends LogWrapper {

	public Log4jLogContextLogWrapper(Log log, String name) {
		super(log);

		_name = name;

		setLogWrapperClassName(Log4jLogContextLogWrapper.class.getName());
	}

	@Override
	public void debug(Object message) {
		ThreadContext.putAll(_getContext());

		super.debug(message);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void debug(Object message, Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.debug(message, throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void debug(Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.debug(throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void error(Object message) {
		ThreadContext.putAll(_getContext());

		super.error(message);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void error(Object message, Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.error(message, throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void error(Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.error(throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void fatal(Object message) {
		ThreadContext.putAll(_getContext());

		super.fatal(message);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void fatal(Object message, Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.fatal(message, throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void fatal(Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.fatal(throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void info(Object message) {
		ThreadContext.putAll(_getContext());

		super.info(message);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void info(Object message, Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.info(message, throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void info(Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.info(throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void trace(Object message) {
		ThreadContext.putAll(_getContext());

		super.trace(message);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void trace(Object message, Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.trace(message, throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void trace(Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.trace(throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void warn(Object message) {
		ThreadContext.putAll(_getContext());

		super.warn(message);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void warn(Object message, Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.warn(message, throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	@Override
	public void warn(Throwable throwable) {
		ThreadContext.putAll(_getContext());

		super.warn(throwable);

		ThreadContext.removeAll(_getContext().keySet());
	}

	private static ServiceTrackerList<LogContext> _createServiceTrackerList() {
		try {
			return ServiceTrackerListFactory.open(
				SystemBundleUtil.getBundleContext(), LogContext.class);
		}
		catch (IllegalStateException illegalStateException) {
			return null;
		}
	}

	private Map<String, String> _getContext() {
		Map<String, String> context = new HashMap<>();

		ServiceTrackerList<LogContext> serviceTrackerList =
			_serviceTrackerListDCLSingleton.getSingleton(
				Log4jLogContextLogWrapper::_createServiceTrackerList);

		if (serviceTrackerList == null) {
			return context;
		}

		for (LogContext logContext : serviceTrackerList) {
			for (Map.Entry<String, String> entry :
					logContext.getContext(
						_name
					).entrySet()) {

				String key = entry.getKey();

				String logContextName = logContext.getName();

				if (Validator.isNotNull(logContextName)) {
					key = logContextName + "." + key;
				}

				context.put(key, entry.getValue());
			}
		}

		return context;
	}

	private static final DCLSingleton<ServiceTrackerList<LogContext>>
		_serviceTrackerListDCLSingleton = new DCLSingleton<>();

	private final String _name;

}