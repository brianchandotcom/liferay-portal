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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class LogContextLogWrapper extends LogWrapper {

	public LogContextLogWrapper(Log log) {
		super(log);

		setLogWrapperClassName(LogContextLogWrapper.class.getName());
	}

	public void debug(Object message) {
		super.debug(_getLogContextMessage(message));
	}

	public void debug(Object message, Throwable throwable) {
		super.debug(_getLogContextMessage(message), throwable);
	}

	public void debug(Throwable throwable) {
		super.debug(_getLogContextMessage(null), throwable);
	}

	public void error(Object message) {
		super.error(_getLogContextMessage(message));
	}

	public void error(Object message, Throwable throwable) {
		super.error(_getLogContextMessage(message), throwable);
	}

	public void error(Throwable throwable) {
		super.error(_getLogContextMessage(null), throwable);
	}

	public void fatal(Object message) {
		super.fatal(_getLogContextMessage(message));
	}

	public void fatal(Object message, Throwable throwable) {
		super.fatal(_getLogContextMessage(message), throwable);
	}

	public void fatal(Throwable throwable) {
		super.fatal(_getLogContextMessage(null), throwable);
	}

	public void info(Object message) {
		super.info(_getLogContextMessage(message));
	}

	public void info(Object message, Throwable throwable) {
		super.info(_getLogContextMessage(message), throwable);
	}

	public void info(Throwable throwable) {
		super.info(_getLogContextMessage(null), throwable);
	}

	public void trace(Object message) {
		super.trace(_getLogContextMessage(message));
	}

	public void trace(Object message, Throwable throwable) {
		super.trace(_getLogContextMessage(message), throwable);
	}

	public void trace(Throwable throwable) {
		super.trace(_getLogContextMessage(null), throwable);
	}

	public void warn(Object message) {
		super.warn(_getLogContextMessage(message));
	}

	public void warn(Object message, Throwable throwable) {
		super.warn(_getLogContextMessage(message), throwable);
	}

	public void warn(Throwable throwable) {
		super.warn(_getLogContextMessage(null), throwable);
	}

	private Object _getLogContextMessage(Object message) {
		ServiceTrackerList<LogContext> serviceTrackerList = _serviceTrackerList;

		if (serviceTrackerList == null) {
			return message;
		}

		StringBundler sb = new StringBundler();

		for (LogContext logContext : serviceTrackerList) {
			Map<String, String> context = logContext.getContext();

			if (context.isEmpty()) {
				continue;
			}

			sb.append(StringPool.OPEN_CURLY_BRACE);
			sb.append(logContext.getName());
			sb.append(StringPool.OPEN_CURLY_BRACE);

			List<String> keys = ListUtil.fromMapKeys(context);

			Collections.sort(keys);

			for (String key : keys) {
				sb.append(StringPool.QUOTE);
				sb.append(key);
				sb.append("\":\"");
				sb.append(context.get(key));
				sb.append(StringPool.QUOTE);
				sb.append(StringPool.COMMA);
			}

			sb.setStringAt(StringPool.DOUBLE_CLOSE_CURLY_BRACE, sb.index() - 1);
			sb.append(StringPool.COMMA);
		}

		if (sb.index() == 0) {
			return message;
		}

		if (message != null) {
			sb.setStringAt(message.toString(), sb.index() - 1);
		}
		else {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
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