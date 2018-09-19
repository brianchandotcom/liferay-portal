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

package com.liferay.portal.scheduler.quartz.internal;

import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(enabled = false, immediate = true, service = {})
public class SchedulerLifecycleInitializer {

	@Activate
	protected void activate() {
		SchedulerLifecycle schedulerLifecycle = new SchedulerLifecycle();

		schedulerLifecycle.registerPortalLifecycle(PortalLifecycle.METHOD_INIT);
	}

	@Reference(unbind = "-")
	protected void setQuartzSchemaManager(
		QuartzSchemaManager quartzSchemaManager) {
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {
	}

}