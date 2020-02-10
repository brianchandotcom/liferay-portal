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

package com.liferay.portal.workflow.kaleo.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.workflow.kaleo.demo.data.creator.WorkflowInstanceDemoDataCreator;
import com.liferay.portal.workflow.kaleo.demo.data.creator.internal.util.WorkflowDemoDataCreatorUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = WorkflowInstanceDemoDataCreator.class)
public class WorkflowInstanceDemoDataCreatorImpl
	implements WorkflowInstanceDemoDataCreator {

	@Override
	public WorkflowInstance getWorkflowInstance(
			long companyId, long workflowInstanceId)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstance(
			companyId, workflowInstanceId);
	}

	@Override
	public WorkflowInstance getWorkflowInstance(
			long companyId, String assetClassName, long assetClassPK)
		throws Exception {

		return WorkflowDemoDataCreatorUtil.retry(
			() -> {
				List<WorkflowInstance> workflowInstances =
					_workflowInstanceManager.getWorkflowInstances(
						companyId, null, assetClassName, assetClassPK, false, 0,
						1, null);

				if (workflowInstances.isEmpty()) {
					return null;
				}

				return workflowInstances.get(0);
			});
	}

	@Override
	public void updateCompletionDate(
			long workflowInstanceId, Date completionDate)
		throws Exception {

		WorkflowDemoDataCreatorUtil.retry(
			() -> {
				KaleoInstance kaleoInstance =
					_kaleoInstanceLocalService.getKaleoInstance(
						workflowInstanceId);

				if (!kaleoInstance.isCompleted()) {
					return null;
				}

				kaleoInstance.setCompletionDate(completionDate);

				_kaleoInstanceLocalService.updateKaleoInstance(kaleoInstance);

				return kaleoInstance;
			});
	}

	@Override
	public void updateCreateDate(long workflowInstanceId, Date createDate)
		throws PortalException {

		KaleoInstance kaleoInstance =
			_kaleoInstanceLocalService.getKaleoInstance(workflowInstanceId);

		if (createDate != null) {
			kaleoInstance.setCreateDate(createDate);

			_kaleoInstanceLocalService.updateKaleoInstance(kaleoInstance);
		}
	}

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}