/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		throws Exception;

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