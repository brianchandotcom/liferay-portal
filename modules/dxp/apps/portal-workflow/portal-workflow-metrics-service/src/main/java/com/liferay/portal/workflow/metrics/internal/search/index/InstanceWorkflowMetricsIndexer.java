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
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

import java.time.Duration;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = InstanceWorkflowMetricsIndexer.class)
public class InstanceWorkflowMetricsIndexer
	extends BaseWorkflowMetricsIndexer<KaleoInstance> {

	@Override
	protected Document createDocument(KaleoInstance kaleoInstance) {
		Document document = new DocumentImpl();

		long kaleoDefinitionId = getKaleoDefinitionId(
			kaleoInstance.getKaleoDefinitionVersionId());

		document.addUID(
			"WorkflowMetricsInstance",
			digest(
				kaleoInstance.getCompanyId(), kaleoDefinitionId,
				kaleoInstance.getKaleoInstanceId()));

		document.addKeyword("className", kaleoInstance.getClassName());
		document.addKeyword("classPK", kaleoInstance.getClassPK());
		document.addKeyword("companyId", kaleoInstance.getCompanyId());
		document.addKeyword("completed", kaleoInstance.isCompleted());

		Date createDate = kaleoInstance.getCreateDate();

		document.addDateSortable("createDate", createDate);

		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", kaleoInstance.getKaleoInstanceId());
		document.addDateSortable(
			"modifiedDate", kaleoInstance.getModifiedDate());
		document.addKeyword("processId", kaleoDefinitionId);
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

	@Override
	protected void populateIndex() {
		List<KaleoInstance> kaleoInstances =
			_kaleoInstanceLocalService.getKaleoInstances(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (KaleoInstance kaleoInstance : kaleoInstances) {
			addDocument(kaleoInstance);
		}
	}

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

}