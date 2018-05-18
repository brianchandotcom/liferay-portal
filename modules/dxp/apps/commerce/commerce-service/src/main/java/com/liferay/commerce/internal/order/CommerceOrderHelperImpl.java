/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.internal.order;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommerceOrderHelperImpl implements CommerceOrderHelper {

	@Override
	public List<ObjectValuePair<Long, String>> getWorkflowTransitions(
			long userId, CommerceOrder commerceOrder)
		throws PortalException {

		List<ObjectValuePair<Long, String>> transitionOVPs = new ArrayList<>();

		_populateTransitionOVPs(transitionOVPs, userId, commerceOrder, true);
		_populateTransitionOVPs(transitionOVPs, userId, commerceOrder, false);

		return transitionOVPs;
	}

	private void _populateTransitionOVPs(
			List<ObjectValuePair<Long, String>> transitionOVPs, long userId,
			CommerceOrder commerceOrder, boolean searchByUserRoles)
		throws PortalException {

		long companyId = commerceOrder.getCompanyId();

		List<WorkflowTask> workflowTasks = _workflowTaskManager.search(
			companyId, userId, null, CommerceOrder.class.getName(),
			new Long[] {commerceOrder.getCommerceOrderId()}, null, null, false,
			searchByUserRoles, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);

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