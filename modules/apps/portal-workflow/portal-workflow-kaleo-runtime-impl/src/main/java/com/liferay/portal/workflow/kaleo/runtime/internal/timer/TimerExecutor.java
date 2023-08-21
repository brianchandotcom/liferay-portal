/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.timer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.KaleoActionExecutor;
import com.liferay.portal.workflow.kaleo.runtime.internal.assignment.helper.TaskAssignerHelper;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationHelper;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerInstanceTokenLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Regisson Aguiar
 */
@Component(service = TimerExecutor.class)
public class TimerExecutor {

	public void executeTimer(ExecutionContext executionContext)
		throws PortalException {

		KaleoTimerInstanceToken kaleoTimerInstanceToken =
			executionContext.getKaleoTimerInstanceToken();

		KaleoTimer kaleoTimer = kaleoTimerInstanceToken.getKaleoTimer();

		kaleoActionExecutor.executeKaleoActions(
			KaleoTimer.class.getName(), kaleoTimer.getKaleoTimerId(),
			ExecutionType.ON_TIMER, executionContext);

		List<KaleoTaskAssignment> kaleoTaskReassignments =
			kaleoTimer.getKaleoTaskReassignments();

		if (ListUtil.isNotEmpty(kaleoTaskReassignments)) {
			taskAssignerHelper.reassignKaleoTask(
				kaleoTaskReassignments, executionContext);
		}

		notificationHelper.sendKaleoNotifications(
			KaleoTimer.class.getName(), kaleoTimer.getKaleoTimerId(),
			ExecutionType.ON_TIMER, executionContext);

		if (!kaleoTimer.isRecurring()) {
			kaleoTimerInstanceTokenLocalService.completeKaleoTimerInstanceToken(
				kaleoTimerInstanceToken.getKaleoTimerInstanceTokenId(),
				executionContext.getServiceContext());
		}
	}

	@Reference
	protected KaleoActionExecutor kaleoActionExecutor;

	@Reference
	protected KaleoTimerInstanceTokenLocalService
		kaleoTimerInstanceTokenLocalService;

	@Reference
	protected NotificationHelper notificationHelper;

	@Reference
	protected TaskAssignerHelper taskAssignerHelper;

}