/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.timer.messaging;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.WorkflowEngine;
import com.liferay.portal.workflow.kaleo.runtime.constants.KaleoRuntimeDestinationNames;
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
@Component(
	property = "destination.name=" + KaleoRuntimeDestinationNames.WORKFLOW_TIMER,
	service = MessageListener.class
)
public class TimerMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long kaleoTimerInstanceTokenId = message.getLong(
			"kaleoTimerInstanceTokenId");

		try {
			KaleoTimerInstanceToken kaleoTimerInstanceToken =
				_getKaleoTimerInstanceToken(message);

			Map<String, Serializable> workflowContext =
				WorkflowContextUtil.convert(
					kaleoTimerInstanceToken.getWorkflowContext());

			ServiceContext serviceContext = (ServiceContext)workflowContext.get(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT);

			_workflowEngine.executeTimerWorkflowInstance(
				kaleoTimerInstanceTokenId, serviceContext, workflowContext);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to execute scheduled job. Unregistering job " +
						message,
					exception);
			}

			SchedulerEngineHelperUtil.delete(
				SchedulerUtil.getGroupName(kaleoTimerInstanceTokenId),
				StorageType.PERSISTED);
		}
	}

	private KaleoTimerInstanceToken _getKaleoTimerInstanceToken(Message message)
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