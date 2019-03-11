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
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.time.Duration;

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
			addDocument(kaleoInstance);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onAfterRemove(KaleoInstance kaleoInstance)
		throws ModelListenerException {

		try {
			deleteDocument(kaleoInstance);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onAfterUpdate(KaleoInstance kaleoInstance)
		throws ModelListenerException {

		try {
			updateDocument(kaleoInstance);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	protected Document createDocument(KaleoInstance kaleoInstance) {
		Document document = new DocumentImpl();

		Date createDate = kaleoInstance.getCreateDate();

		document.addUID(
			"WorkflowMetricsInstance",
			digest(
				kaleoInstance.getCompanyId(),
				kaleoInstance.getKaleoDefinitionVersionId(),
				kaleoInstance.getKaleoInstanceId()));
		document.addKeyword("className", kaleoInstance.getClassName());
		document.addKeyword("classPK", kaleoInstance.getClassPK());
		document.addKeyword("companyId", kaleoInstance.getCompanyId());
		document.addKeyword("completed", kaleoInstance.isCompleted());
		document.addDateSortable("createDate", createDate);
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", kaleoInstance.getKaleoInstanceId());
		document.addDateSortable(
			"modifiedDate", kaleoInstance.getModifiedDate());

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
				kaleoInstance.getKaleoDefinitionVersionId());

		if (kaleoDefinitionVersion != null) {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.fetchKaleoDefinition();

			if (kaleoDefinition != null) {
				document.addKeyword(
					"processId", kaleoDefinition.getKaleoDefinitionId());
			}
		}

		document.addKeyword("userId", kaleoInstance.getUserId());

		if (kaleoInstance.isCompleted()) {
			Date completionDate = kaleoInstance.getCompletionDate();

			document.addDateSortable("completionDate", completionDate);

			Duration duration = Duration.between(
				createDate.toInstant(), completionDate.toInstant());

			document.addNumber("duration", duration.toMillis());
		}

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

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoInstanceModelListener.class);

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

}