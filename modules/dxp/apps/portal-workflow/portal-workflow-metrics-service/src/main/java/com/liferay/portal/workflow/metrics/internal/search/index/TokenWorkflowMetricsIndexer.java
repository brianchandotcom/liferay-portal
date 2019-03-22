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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.time.Duration;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = TokenWorkflowMetricsIndexer.class)
public class TokenWorkflowMetricsIndexer
	extends BaseWorkflowMetricsIndexer<KaleoTaskInstanceToken> {

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

		Long kaleoDefinitionId = getKaleoDefinitionId(
			kaleoTaskInstanceToken.getKaleoDefinitionVersionId());

		if (kaleoDefinitionId != null) {
			document.addKeyword("processId", kaleoDefinitionId);
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

	@Override
	protected void populateIndex() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoTaskInstanceTokenLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(KaleoTaskInstanceToken kaleoTaskInstanceToken) -> addDocument(
				kaleoTaskInstanceToken));

		actionableDynamicQuery.performActions();
	}

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

}