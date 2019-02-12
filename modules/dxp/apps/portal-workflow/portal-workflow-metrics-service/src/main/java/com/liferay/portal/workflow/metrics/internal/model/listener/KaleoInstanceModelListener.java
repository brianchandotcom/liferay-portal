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
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsInstanceIndexer;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoInstanceModelListener
	extends BaseKaleoModelListener<KaleoInstance> {

	@Override
	public void onAfterCreate(KaleoInstance kaleoInstance)
		throws ModelListenerException {

		try {
			Document document = createDocument(kaleoInstance);

			Date date = kaleoInstance.getCreateDate();

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			document.addKeyword(
				Field.getSortableFieldName("date"), offsetDateTime.toString());

			_workflowMetricsInstanceIndexer.index(document);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemove(KaleoInstance kaleoInstance)
		throws ModelListenerException {

		try {
			if (kaleoInstance.isCompleted()) {
				return;
			}

			Document document = createDocument(kaleoInstance);

			OffsetDateTime offsetDateTime = OffsetDateTime.now();

			document.addKeyword(
				Field.getSortableFieldName("date"), offsetDateTime.toString());

			document.addKeyword("deleted", true);

			_workflowMetricsInstanceIndexer.update(document);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(KaleoInstance kaleoInstance)
		throws ModelListenerException {

		try {
			Document document = createDocument(kaleoInstance);

			Date date = kaleoInstance.getModifiedDate();

			if (kaleoInstance.isCompleted()) {
				Date createDate = kaleoInstance.getCreateDate();

				date = kaleoInstance.getCompletionDate();

				Duration duration = Duration.between(
					createDate.toInstant(), date.toInstant());

				document.addNumber("duration", duration.toMillis());
			}

			document.addKeyword("complete", kaleoInstance.isCompleted());

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			document.addKeyword(
				Field.getSortableFieldName("date"), offsetDateTime.toString());

			_workflowMetricsInstanceIndexer.update(document);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected Document createDocument(KaleoInstance kaleoInstance) {
		Document document = new DocumentImpl();

		document.addKeyword("className", kaleoInstance.getClassName());
		document.addKeyword("classPK", kaleoInstance.getClassPK());
		document.addKeyword("companyId", kaleoInstance.getCompanyId());
		document.addKeyword("instanceId", kaleoInstance.getKaleoInstanceId());
		document.addKeyword(
			"processId", kaleoInstance.getKaleoDefinitionVersionId());

		document.addUID(
			"WorkflowMetricsInstance",
			digest(
				kaleoInstance.getCompanyId(),
				kaleoInstance.getKaleoInstanceId(),
				kaleoInstance.getKaleoDefinitionVersionId()));

		return document;
	}

	@Reference
	private WorkflowMetricsInstanceIndexer _workflowMetricsInstanceIndexer;

}