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
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsTaskIndexer;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import java.util.Date;

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
			Document document = createDocument(kaleoTask);

			Date date = kaleoTask.getCreateDate();

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			document.addKeyword(
				Field.getSortableFieldName("date"), offsetDateTime.toString());

			_workflowMetricsTaskIndexer.index(document);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemove(KaleoTask kaleoTask)
		throws ModelListenerException {

		try {
			Document document = createDocument(kaleoTask);

			OffsetDateTime offsetDateTime = OffsetDateTime.now();

			document.addKeyword(
				Field.getSortableFieldName("date"), offsetDateTime.toString());

			document.addKeyword("deleted", true);

			_workflowMetricsTaskIndexer.update(document);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected Document createDocument(KaleoTask kaleoTask) {
		Document document = new DocumentImpl();

		document.addKeyword("companyId", kaleoTask.getCompanyId());
		document.addKeyword("name", kaleoTask.getName());
		document.addKeyword("taskId", kaleoTask.getKaleoTaskId());
		document.addKeyword(
			"processId", kaleoTask.getKaleoDefinitionVersionId());

		document.addUID(
			"WorkflowMetricsTask",
			digest(
				kaleoTask.getCompanyId(), kaleoTask.getKaleoTaskId(),
				kaleoTask.getKaleoDefinitionVersionId()));

		return document;
	}

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private WorkflowMetricsTaskIndexer _workflowMetricsTaskIndexer;

}