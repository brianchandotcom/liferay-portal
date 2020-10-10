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

package com.liferay.dispatch.executor;

import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import java.util.Date;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Matija Petanjek
 */
public abstract class BaseScheduledTaskExecutor
	implements ScheduledTaskExecutor {

	public abstract void doExecute(
			DispatchTrigger dispatchTrigger,
			ScheduledTaskExecutorOutput scheduledTaskExecutorOutput)
		throws IOException, PortalException;

	@Override
	public void execute(long dispatchTriggerId)
		throws IOException, PortalException {

		DispatchLogLocalService dispatchLogLocalService =
			_dispatchLogLocalServiceTracker.getService();

		DispatchTriggerLocalService dispatchTriggerLocalService =
			_dispatchTriggerLocalServiceTracker.getService();

		DispatchTrigger dispatchTrigger =
			dispatchTriggerLocalService.getDispatchTrigger(dispatchTriggerId);

		DispatchLog dispatchLog = dispatchLogLocalService.addDispatchLog(
			dispatchTrigger.getUserId(), dispatchTrigger.getDispatchTriggerId(),
			null, null, null, new Date(), TaskStatus.IN_PROGRESS);

		ScheduledTaskExecutorOutput scheduledTaskExecutorOutput =
			new ScheduledTaskExecutorOutput();

		try {
			doExecute(dispatchTrigger, scheduledTaskExecutorOutput);

			dispatchLogLocalService.updateDispatchLog(
				dispatchLog.getDispatchLogId(), new Date(),
				scheduledTaskExecutorOutput.getError(),
				scheduledTaskExecutorOutput.getOutput(), TaskStatus.SUCCESSFUL);
		}
		catch (Throwable throwable) {
			dispatchLogLocalService.updateDispatchLog(
				dispatchLog.getDispatchLogId(), new Date(),
				scheduledTaskExecutorOutput.getError(),
				scheduledTaskExecutorOutput.getOutput(), TaskStatus.FAILED);

			throw throwable;
		}
	}

	private static final ServiceTracker<?, DispatchLogLocalService>
		_dispatchLogLocalServiceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(DispatchLogLocalService.class),
			DispatchLogLocalService.class);
	private static final ServiceTracker<?, DispatchTriggerLocalService>
		_dispatchTriggerLocalServiceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(DispatchTriggerLocalService.class),
			DispatchTriggerLocalService.class);

}