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

package com.liferay.dispatch.internal;

import com.liferay.dispatch.exception.NoSuchLogException;
import com.liferay.dispatch.executor.ScheduledTaskExecutor;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import java.util.Date;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = ScheduledTaskExecutorDispatcher.class)
public class ScheduledTaskExecutorDispatcher {

	public void dispatch(long dispatchTriggerId)
		throws IOException, PortalException {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.getDispatchTrigger(dispatchTriggerId);

		if (!dispatchTrigger.isOverlapAllowed() &&
			_isInProgress(dispatchTriggerId)) {

			Date date = new Date();

			_dispatchLogLocalService.addDispatchLog(
				dispatchTrigger.getUserId(),
				dispatchTrigger.getDispatchTriggerId(), date, null, null, date,
				BackgroundTaskConstants.STATUS_CANCELLED);

			return;
		}

		ScheduledTaskExecutor scheduledTaskExecutor =
			_scheduledTaskExecutorServiceTrackerMap.getService(
				dispatchTrigger.getTaskType());

		scheduledTaskExecutor.execute(dispatchTriggerId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_scheduledTaskExecutorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ScheduledTaskExecutor.class,
				"scheduled.task.executor.type");
	}

	@Deactivate
	protected void deactivate() {
		_scheduledTaskExecutorServiceTrackerMap.close();
	}

	private boolean _isInProgress(long dispatchTriggerId)
		throws NoSuchLogException {

		DispatchLog dispatchLog =
			_dispatchLogLocalService.fetchLatestDispatchLog(dispatchTriggerId);

		if ((dispatchLog != null) &&
			((dispatchLog.getStatus() ==
				BackgroundTaskConstants.STATUS_IN_PROGRESS) ||
			 (dispatchLog.getStatus() ==
				 BackgroundTaskConstants.STATUS_CANCELLED))) {

			return true;
		}

		return false;
	}

	@Reference
	private DispatchLogLocalService _dispatchLogLocalService;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	private ServiceTrackerMap<String, ScheduledTaskExecutor>
		_scheduledTaskExecutorServiceTrackerMap;

}