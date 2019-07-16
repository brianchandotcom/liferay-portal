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

package com.liferay.portal.workflow.kaleo.runtime.internal.timer.messaging;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.WorkflowEngine;
import com.liferay.portal.workflow.kaleo.runtime.util.SchedulerUtil;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerInstanceTokenLocalService;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = TimerMessageListener.class)
public class TimerMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long kaleoTimerInstanceTokenId = message.getLong(
			"kaleoTimerInstanceTokenId");

		try {
			KaleoTimerInstanceToken kaleoTimerInstanceToken =
				getKaleoTimerInstanceToken(message);

			Map<String, Serializable> workflowContext =
				WorkflowContextUtil.convert(
					kaleoTimerInstanceToken.getWorkflowContext());

			ServiceContext serviceContext = (ServiceContext)workflowContext.get(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT);

			_workflowEngine.executeTimerWorkflowInstance(
				kaleoTimerInstanceTokenId, serviceContext, workflowContext);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to execute scheduled job. Unregistering job " +
						message,
					e);
			}

			SchedulerEngineHelperUtil.delete(
				SchedulerUtil.getGroupName(kaleoTimerInstanceTokenId),
				StorageType.PERSISTED);
		}
	}

	protected KaleoTimerInstanceToken getKaleoTimerInstanceToken(
			Message message)
		throws PortalException {

		long kaleoTimerInstanceTokenId = message.getLong(
			"kaleoTimerInstanceTokenId");

		return _kaleoTimerInstanceTokenLocalService.getKaleoTimerInstanceToken(
			kaleoTimerInstanceTokenId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TimerMessageListener.class);

	@Reference
	private KaleoTimerInstanceTokenLocalService
		_kaleoTimerInstanceTokenLocalService;

	@Reference
	private WorkflowEngine _workflowEngine;

}