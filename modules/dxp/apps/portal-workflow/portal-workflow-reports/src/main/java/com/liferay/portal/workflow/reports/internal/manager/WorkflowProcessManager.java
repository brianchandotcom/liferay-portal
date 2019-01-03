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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexerFieldNames;
import com.liferay.portal.workflow.reports.internal.model.WorkflowProcess;
import com.liferay.portal.workflow.reports.internal.search.query.WorkflowProcessQueryExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = WorkflowProcessManager.class)
public class WorkflowProcessManager {

	public List<WorkflowProcess> getWorkflowProcesses(
		long companyId, String keywords, boolean active, int start, int size) {

		List<WorkflowProcess> workflowProcesses = new ArrayList<>();

		Map<String, Hits> map = _workflowProcessQueryExecutor.search(
			companyId, keywords, active, 0, 1, WorkflowIndexerFieldNames.NAME,
			1,
			new Sort(
				Field.getSortableFieldName(WorkflowIndexerFieldNames.DATE),
				true));

		List<Hits> hitsList = ListUtil.subList(
			new ArrayList<>(map.values()), start, start + size);

		for (Hits hits : hitsList) {
			Document[] documents = hits.getDocs();

			Document document = documents[0];

			List<Long> processIds =
				_workflowProcessQueryExecutor.getWorkflowProcessIds(
					companyId, document.get(WorkflowIndexerFieldNames.NAME));

			WorkflowProcess workflowProcess = new WorkflowProcess();

			workflowProcess.setInstancesCount(
				_workflowInstanceManager.getWorkflowInstancesCount(
					companyId, processIds));

			workflowProcess.setName(
				document.get(WorkflowIndexerFieldNames.NAME));
			workflowProcess.setTitle(
				document.get(WorkflowIndexerFieldNames.TITLE));

			workflowProcesses.add(workflowProcess);
		}

		return workflowProcesses;
	}

	public long getWorkflowProcessesCount(
		long companyId, String keywords, boolean active) {

		return _workflowProcessQueryExecutor.searchCount(
			companyId, keywords, active);
	}

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowProcessQueryExecutor _workflowProcessQueryExecutor;

}