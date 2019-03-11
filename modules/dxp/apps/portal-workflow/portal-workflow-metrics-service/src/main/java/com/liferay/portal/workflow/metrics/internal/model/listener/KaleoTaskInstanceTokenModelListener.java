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
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.time.Duration;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoTaskInstanceTokenModelListener
	extends BaseKaleoModelListener<KaleoTaskInstanceToken> {

	@Override
	public void onAfterCreate(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			addDocument(kaleoTaskInstanceToken);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onAfterRemove(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			deleteDocument(kaleoTaskInstanceToken);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onAfterUpdate(KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws ModelListenerException {

		try {
			updateDocument(kaleoTaskInstanceToken);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	protected Document createDocument(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsToken",
			digest(
				kaleoTaskInstanceToken.getCompanyId(),
				kaleoTaskInstanceToken.getKaleoDefinitionVersionId(),
				kaleoTaskInstanceToken.getKaleoInstanceId(),
				kaleoTaskInstanceToken.getKaleoTaskId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId()));
		document.addKeyword("className", kaleoTaskInstanceToken.getClassName());
		document.addKeyword("classPK", kaleoTaskInstanceToken.getClassPK());
		document.addKeyword("companyId", kaleoTaskInstanceToken.getCompanyId());
		document.addKeyword("completed", kaleoTaskInstanceToken.isCompleted());
		document.addDateSortable(
			"createDate", kaleoTaskInstanceToken.getCreateDate());
		document.addKeyword("deleted", false);
		document.addKeyword(
			"instanceId", kaleoTaskInstanceToken.getKaleoInstanceId());
		document.addDateSortable(
			"modifiedDate", kaleoTaskInstanceToken.getModifiedDate());

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
				kaleoTaskInstanceToken.getKaleoDefinitionVersionId());

		if (kaleoDefinitionVersion != null) {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.fetchKaleoDefinition();

			if (kaleoDefinition != null) {
				document.addKeyword(
					"processId", kaleoDefinition.getKaleoDefinitionId());
			}
		}

		document.addKeyword("taskId", kaleoTaskInstanceToken.getKaleoTaskId());
		document.addKeyword(
			"tokenId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
		document.addKeyword("userId", kaleoTaskInstanceToken.getUserId());

		if (kaleoTaskInstanceToken.isCompleted()) {
			Date completionDate = kaleoTaskInstanceToken.getCompletionDate();

			document.addDateSortable("completionDate", completionDate);

			Date createDate = kaleoTaskInstanceToken.getCreateDate();

			Duration duration = Duration.between(
				createDate.toInstant(), completionDate.toInstant());

			document.addNumber("duration", duration.toMillis());
		}

		return document;
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-tokens";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsTokenType";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTaskInstanceTokenModelListener.class);

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

}