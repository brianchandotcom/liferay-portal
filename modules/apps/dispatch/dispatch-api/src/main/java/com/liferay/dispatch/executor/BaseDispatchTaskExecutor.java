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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Date;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Matija Petanjek
 */
public abstract class BaseDispatchTaskExecutor implements DispatchTaskExecutor {

	public abstract void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
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
			null, null, null, new Date(), DispatchTaskStatus.IN_PROGRESS);

		DispatchTaskExecutorOutput dispatchTaskExecutorOutput =
			new DispatchTaskExecutorOutput();

		try {
			doExecute(dispatchTrigger, dispatchTaskExecutorOutput);

			dispatchLogLocalService.updateDispatchLog(
				dispatchLog.getDispatchLogId(), new Date(),
				dispatchTaskExecutorOutput.getError(),
				dispatchTaskExecutorOutput.getOutput(),
				DispatchTaskStatus.SUCCESSFUL);
		}
		catch (Throwable throwable) {
			String error = dispatchTaskExecutorOutput.getError();

			if (Validator.isNull(error)) {
				error = throwable.getMessage();

				if (Validator.isNull(error)) {
					Class<? extends Throwable> throwableClass =
						throwable.getClass();

					error = "Unable to execute due " + throwableClass.getName();

					if (_log.isDebugEnabled()) {
						_log.debug("Unable to execute task", throwable);
					}
				}
			}

			dispatchLogLocalService.updateDispatchLog(
				dispatchLog.getDispatchLogId(), new Date(), error,
				dispatchTaskExecutorOutput.getOutput(),
				DispatchTaskStatus.FAILED);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDispatchTaskExecutor.class);

	private static final ServiceTracker<?, DispatchLogLocalService>
		_dispatchLogLocalServiceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(DispatchLogLocalService.class),
			DispatchLogLocalService.class);
	private static final ServiceTracker<?, DispatchTriggerLocalService>
		_dispatchTriggerLocalServiceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(DispatchTriggerLocalService.class),
			DispatchTriggerLocalService.class);

}