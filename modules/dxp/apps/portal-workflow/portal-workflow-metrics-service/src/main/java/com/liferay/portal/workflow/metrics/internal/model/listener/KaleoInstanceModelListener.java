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
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;

import java.time.Duration;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

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

			addDocument(document, kaleoInstance.getCreateDate());
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

			deleteDocument(document);
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

			updateDocument(document, date);
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
			"InstanceWorkflowMetrics",
			digest(
				kaleoInstance.getCompanyId(),
				kaleoInstance.getKaleoInstanceId(),
				kaleoInstance.getKaleoDefinitionVersionId()));

		return document;
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-instances";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsInstanceType";
	}

}