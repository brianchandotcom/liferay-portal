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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

/**
 * @author Tina Tian
 */
public class SchedulerLifecycle extends BasePortalLifecycle {

	@Override
	protected void doPortalDestroy() throws Exception {
	}

	@Override
	protected void doPortalInit() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		registry.callService(
			SchedulerEngineHelper.class,
			schedulerEngineHelper -> {
				try {
					schedulerEngineHelper.start();
				}
				catch (SchedulerException se) {
					_log.error("Unable to start scheduler engine", se);
				}

				return null;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerLifecycle.class);

}