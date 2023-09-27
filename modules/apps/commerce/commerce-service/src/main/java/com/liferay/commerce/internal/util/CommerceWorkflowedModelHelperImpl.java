/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = CommerceWorkflowedModelHelper.class)
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
					userId, workflowTaskId);

			for (String transitionName : transitionNames) {
				transitionOVPs.add(
					new ObjectValuePair<>(workflowTaskId, transitionName));
			}
		}
	}

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}