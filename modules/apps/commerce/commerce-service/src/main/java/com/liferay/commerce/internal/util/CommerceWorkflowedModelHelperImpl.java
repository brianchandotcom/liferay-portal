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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.util.CommerceWorkflowedModelHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceWorkflowedModelHelper.class
)
public class CommerceWorkflowedModelHelperImpl
	implements CommerceWorkflowedModelHelper {

	@Override
	public List<ObjectValuePair<Long, String>> getWorkflowTransitions(
			long userId, long companyId, String className, long classPK)
		throws PortalException {

		List<ObjectValuePair<Long, String>> transitionOVPs = new ArrayList<>();

		_populateTransitionOVPs(
			transitionOVPs, userId, companyId, className, classPK, true);
		_populateTransitionOVPs(
			transitionOVPs, userId, companyId, className, classPK, false);

		return transitionOVPs;
	}

	private void _populateTransitionOVPs(
			List<ObjectValuePair<Long, String>> transitionOVPs, long userId,
			long companyId, String className, long classPK,
			boolean searchByUserRoles)
		throws PortalException {

		String[] assetTypes = null;

		if (Validator.isNotNull(className)) {
			assetTypes = new String[] {className};
		}

		List<WorkflowTask> workflowTasks = _workflowTaskManager.search(
			companyId, userId, null, null, assetTypes, new Long[] {classPK},
			null, null, null, null, false, searchByUserRoles, null, null, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (WorkflowTask workflowTask : workflowTasks) {
			long workflowTaskId = workflowTask.getWorkflowTaskId();

			List<String> transitionNames =
				_workflowTaskManager.getNextTransitionNames(
					companyId, userId, workflowTaskId);

			for (String transitionName : transitionNames) {
				transitionOVPs.add(
					new ObjectValuePair<>(workflowTaskId, transitionName));
			}
		}
	}

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}