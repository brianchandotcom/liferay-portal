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

package com.liferay.portal.kernel.scheduler.config;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycle;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class DefaultSchedulingConfigurator
	extends AbstractSchedulingConfigurator {

	@Override
	public void configure() {
		if (schedulerEntries.isEmpty()) {
			return;
		}

		SchedulingConfiguratorLifecycle schedulingConfiguratorLifecycle =
			new SchedulingConfiguratorLifecycle();

		schedulingConfiguratorLifecycle.registerPortalLifecycle(
			PortalLifecycle.METHOD_INIT);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultSchedulingConfigurator.class);

	private class SchedulingConfiguratorLifecycle extends BasePortalLifecycle {

		@Override
		protected void doPortalDestroy() throws Exception {
		}

		@Override
		protected void doPortalInit() throws Exception {
			for (SchedulerEntry schedulerEntry : schedulerEntries) {
				try {
					SchedulerEngineHelperUtil.schedule(
						schedulerEntry, storageType, null, exceptionsMaxSize);
				}
				catch (Exception e) {
					_log.error("Unable to schedule " + schedulerEntry, e);
				}
			}
		}

	}

}