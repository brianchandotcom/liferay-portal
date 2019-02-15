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

package com.liferay.portal.workflow.metrics.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoTaskModelListener extends BaseKaleoModelListener<KaleoTask> {

	@Override
	public void onAfterCreate(KaleoTask kaleoTask)
		throws ModelListenerException {

		try {
			addDocument(kaleoTask);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onAfterRemove(KaleoTask kaleoTask)
		throws ModelListenerException {

		try {
			deleteDocument(kaleoTask);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	protected Document createDocument(KaleoTask kaleoTask) {
		Document document = new DocumentImpl();

		document.addDateSortable("createDate", kaleoTask.getCreateDate());
		document.addDateSortable("modifiedDate", kaleoTask.getModifiedDate());

		document.addKeyword("companyId", kaleoTask.getCompanyId());
		document.addKeyword("name", kaleoTask.getName());
		document.addKeyword(
			"processId", kaleoTask.getKaleoDefinitionVersionId());
		document.addKeyword("taskId", kaleoTask.getKaleoTaskId());
		document.addUID(
			"TaskWorkflowMetrics",
			digest(
				kaleoTask.getCompanyId(), kaleoTask.getKaleoTaskId(),
				kaleoTask.getKaleoDefinitionVersionId()));

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

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTaskModelListener.class);

}