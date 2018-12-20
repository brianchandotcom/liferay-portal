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

package com.liferay.portal.workflow.kaleo.runtime.internal;

import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoActionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoConditionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationRecipientLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskFormInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskFormLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTransitionLocalService;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseKaleoBean {

	@Reference
	protected KaleoActionLocalService kaleoActionLocalService;

	@Reference
	protected KaleoConditionLocalService kaleoConditionLocalService;

	@Reference
	protected KaleoDefinitionLocalService kaleoDefinitionLocalService;

	@Reference
	protected KaleoDefinitionVersionLocalService
		kaleoDefinitionVersionLocalService;

	@Reference
	protected KaleoInstanceLocalService kaleoInstanceLocalService;

	@Reference
	protected KaleoInstanceTokenLocalService kaleoInstanceTokenLocalService;

	@Reference
	protected KaleoLogLocalService kaleoLogLocalService;

	@Reference
	protected KaleoNodeLocalService kaleoNodeLocalService;

	@Reference
	protected KaleoNotificationLocalService kaleoNotificationLocalService;

	@Reference
	protected KaleoNotificationRecipientLocalService
		kaleoNotificationRecipientLocalService;

	@Reference
	protected KaleoTaskAssignmentLocalService kaleoTaskAssignmentLocalService;

	@Reference
	protected KaleoTaskFormInstanceLocalService
		kaleoTaskFormInstanceLocalService;

	@Reference
	protected KaleoTaskFormLocalService kaleoTaskFormLocalService;

	@Reference
	protected KaleoTaskInstanceTokenLocalService
		kaleoTaskInstanceTokenLocalService;

	@Reference
	protected KaleoTaskLocalService kaleoTaskLocalService;

	@Reference
	protected KaleoTimerInstanceTokenLocalService
		kaleoTimerInstanceTokenLocalService;

	@Reference
	protected KaleoTimerLocalService kaleoTimerLocalService;

	@Reference
	protected KaleoTransitionLocalService kaleoTransitionLocalService;

	@Reference
	protected WorkflowDefinitionLinkLocalService
		workflowDefinitionLinkLocalService;

}