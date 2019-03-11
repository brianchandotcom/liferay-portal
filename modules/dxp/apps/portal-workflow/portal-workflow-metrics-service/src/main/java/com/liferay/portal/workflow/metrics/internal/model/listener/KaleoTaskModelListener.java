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
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		document.addUID(
			"WorkflowMetricsTask",
			digest(
				kaleoTask.getCompanyId(),
				kaleoTask.getKaleoDefinitionVersionId(),
				kaleoTask.getKaleoTaskId()));
		document.addKeyword("companyId", kaleoTask.getCompanyId());
		document.addDateSortable("createDate", kaleoTask.getCreateDate());
		document.addDateSortable("modifiedDate", kaleoTask.getModifiedDate());
		document.addKeyword("name", kaleoTask.getName());

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
				kaleoTask.getKaleoDefinitionVersionId());

		if (kaleoDefinitionVersion != null) {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.fetchKaleoDefinition();

			if (kaleoDefinition != null) {
				document.addKeyword(
					"processId", kaleoDefinition.getKaleoDefinitionId());
			}
		}

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

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTaskModelListener.class);

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

}