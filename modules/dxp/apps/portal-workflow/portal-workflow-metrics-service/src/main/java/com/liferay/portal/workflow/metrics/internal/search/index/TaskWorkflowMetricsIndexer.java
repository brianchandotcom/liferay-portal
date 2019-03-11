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

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = TaskWorkflowMetricsIndexer.class)
public class TaskWorkflowMetricsIndexer
	extends BaseWorkflowMetricsIndexer<KaleoTask> {

	@Override
	protected Document createDocument(KaleoTask kaleoTask) {
		Document document = new DocumentImpl();

		long kaleoDefinitionId = getKaleoDefinitionId(
			kaleoTask.getKaleoDefinitionVersionId());

		document.addUID(
			"WorkflowMetricsTask",
			digest(
				kaleoTask.getCompanyId(), kaleoDefinitionId,
				kaleoTask.getKaleoTaskId()));

		document.addKeyword("companyId", kaleoTask.getCompanyId());
		document.addDateSortable("createDate", kaleoTask.getCreateDate());
		document.addDateSortable("modifiedDate", kaleoTask.getModifiedDate());
		document.addKeyword("name", kaleoTask.getName());
		document.addKeyword("processId", kaleoDefinitionId);
		document.addKeyword("taskId", kaleoTask.getKaleoTaskId());

		return document;
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-tasks";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsTaskType";
	}

	@Override
	protected void populateIndex() {
		List<KaleoTask> kaleoTasks = _kaleoTaskLocalService.getKaleoTasks(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (KaleoTask kaleoTask : kaleoTasks) {
			addDocument(kaleoTask);
		}
	}

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

}