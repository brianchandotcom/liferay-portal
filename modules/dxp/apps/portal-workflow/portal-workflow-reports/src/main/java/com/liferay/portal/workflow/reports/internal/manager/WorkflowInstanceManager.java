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

package com.liferay.portal.workflow.reports.internal.manager;

import com.liferay.portal.workflow.reports.internal.search.query.WorkflowInstanceQueryExecutor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = WorkflowInstanceManager.class)
public class WorkflowInstanceManager {

	public long getWorkflowInstancesCount(
		long companyId, List<Long> processIds) {

		return _workflowInstanceQueryExecutor.searchCount(
			companyId, processIds);
	}

	@Reference
	private WorkflowInstanceQueryExecutor _workflowInstanceQueryExecutor;

}