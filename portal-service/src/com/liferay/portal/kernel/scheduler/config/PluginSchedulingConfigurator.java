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

package com.liferay.portal.kernel.scheduler.config;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class PluginSchedulingConfigurator
	extends AbstractSchedulingConfigurator {

	@Override
	public void configure() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		String servletContextName =
			PortletClassLoaderUtil.getServletContextName();

		boolean forceSync = ProxyModeThreadLocal.isForceSync();

		ProxyModeThreadLocal.setForceSync(true);

		try {
			ClassLoader portalClassLoader =
				PortalClassLoaderUtil.getClassLoader();

			currentThread.setContextClassLoader(portalClassLoader);

			for (SchedulerEntry schedulerEntry : schedulerEntries) {
				try {
					SchedulerEngineHelperUtil.schedule(
						schedulerEntry, storageType, servletContextName,
						exceptionsMaxSize);
				}
				catch (Exception e) {
					_log.error("Unable to schedule " + schedulerEntry, e);
				}
			}
		}
		finally {
			ProxyModeThreadLocal.setForceSync(forceSync);

			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void destroy() {
		for (SchedulerEntry schedulerEntry : schedulerEntries) {
			try {
				SchedulerEngineHelperUtil.delete(schedulerEntry, storageType);
			}
			catch (Exception e) {
				_log.error("Unable to unschedule " + schedulerEntry, e);
			}
		}

		schedulerEntries.clear();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PluginSchedulingConfigurator.class);

}