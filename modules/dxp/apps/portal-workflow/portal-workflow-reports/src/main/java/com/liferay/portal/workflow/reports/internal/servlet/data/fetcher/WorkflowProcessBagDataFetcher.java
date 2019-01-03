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

package com.liferay.portal.workflow.reports.internal.servlet.data.fetcher;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.workflow.reports.internal.manager.WorkflowProcessManager;
import com.liferay.portal.workflow.reports.internal.model.WorkflowProcessBag;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = WorkflowProcessBagDataFetcher.class)
public class WorkflowProcessBagDataFetcher
	implements DataFetcher<WorkflowProcessBag> {

	@Override
	public WorkflowProcessBag get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		WorkflowProcessBag workflowProcessBag = new WorkflowProcessBag();

		long companyId = dataFetchingEnvironment.getArgument("companyId");
		String keywords = GetterUtil.getString(
			dataFetchingEnvironment.getArgument("keywords"));

		workflowProcessBag.setTotal(
			_workflowProcessManager.getWorkflowProcessesCount(
				companyId, keywords, true));

		int start = dataFetchingEnvironment.getArgument("start");
		int size = dataFetchingEnvironment.getArgument("size");

		workflowProcessBag.setWorkflowProcesses(
			_workflowProcessManager.getWorkflowProcesses(
				companyId, keywords, true, start, size));

		return workflowProcessBag;
	}

	@Reference
	private WorkflowProcessManager _workflowProcessManager;

}