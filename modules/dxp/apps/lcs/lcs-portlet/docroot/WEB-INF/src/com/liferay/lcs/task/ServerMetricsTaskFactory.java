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

package com.liferay.lcs.task;

import com.liferay.lcs.management.MBeanServerService;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ServerDetector;

/**
 * @author Riccardo Ferrari
 */
public class ServerMetricsTaskFactory {

	public ServerMetricsTask getInstance() {
		ServerMetricsTask serverMetricsTask = null;

		if (ServerDetector.isTomcat()) {
			if (_log.isTraceEnabled()) {
				_log.trace("Setting Tomcat metrics task");
			}

			serverMetricsTask = new TomcatServerMetricsTask();
		}
		else if (ServerDetector.isWebLogic()) {
			if (_log.isTraceEnabled()) {
				_log.trace("Setting WebLogic metrics task");
			}

			serverMetricsTask = new WeblogicServerMetricsTask();
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					ServerDetector.getServerId() +
						" server metrics are not supported in LCS");
			}

			return null;
		}

		serverMetricsTask.setKeyGenerator(_keyGenerator);
		serverMetricsTask.setLCSConnectionManager(_lcsConnectionManager);
		serverMetricsTask.setMBeanServerService(_mBeanServerService);

		return serverMetricsTask;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	public void setmBeanServerService(MBeanServerService mBeanServerService) {
		_mBeanServerService = mBeanServerService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServerMetricsTaskFactory.class);

	private KeyGenerator _keyGenerator;
	private LCSConnectionManager _lcsConnectionManager;
	private MBeanServerService _mBeanServerService;

}