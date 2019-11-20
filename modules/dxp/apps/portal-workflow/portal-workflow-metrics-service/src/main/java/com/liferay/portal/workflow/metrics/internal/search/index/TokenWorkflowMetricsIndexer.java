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
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.time.Duration;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	service = {Indexer.class, TokenWorkflowMetricsIndexer.class}
)
public class TokenWorkflowMetricsIndexer extends BaseWorkflowMetricsIndexer {

	public Document createDocument(
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

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			_kaleoTaskAssignmentInstanceLocalService.
				fetchFirstKaleoTaskAssignmentInstance(
					kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
					User.class.getName(), null);

		if (kaleoTaskAssignmentInstance != null) {
			document.addKeyword(
				"assigneeId", kaleoTaskAssignmentInstance.getAssigneeClassPK());
		}

		document.addKeyword("className", kaleoTaskInstanceToken.getClassName());
		document.addKeyword("classPK", kaleoTaskInstanceToken.getClassPK());
		document.addKeyword("companyId", kaleoTaskInstanceToken.getCompanyId());
		document.addKeyword("completed", kaleoTaskInstanceToken.isCompleted());

		Date completionDate = kaleoTaskInstanceToken.getCompletionDate();

		if (kaleoTaskInstanceToken.isCompleted()) {
			document.addDateSortable("completionDate", completionDate);
		}

		Date createDate = kaleoTaskInstanceToken.getCreateDate();

		document.addDateSortable("createDate", createDate);

		document.addKeyword("deleted", false);

		if (kaleoTaskInstanceToken.isCompleted()) {
			Duration duration = Duration.between(
				createDate.toInstant(), completionDate.toInstant());

			document.addNumber("duration", duration.toMillis());
		}

		document.addKeyword("instanceCompleted", false);
		document.addKeyword(
			"instanceId", kaleoTaskInstanceToken.getKaleoInstanceId());
		document.addDateSortable(
			"modifiedDate", kaleoTaskInstanceToken.getModifiedDate());

		KaleoDefinition kaleoDefinition = getKaleoDefinition(
			kaleoTaskInstanceToken.getKaleoDefinitionVersionId());

		if (kaleoDefinition != null) {
			document.addKeyword(
				"processId", kaleoDefinition.getKaleoDefinitionId());
		}

		document.addKeyword("taskId", kaleoTaskInstanceToken.getKaleoTaskId());
		document.addKeyword(
			"taskName", kaleoTaskInstanceToken.getKaleoTaskName());
		document.addKeyword(
			"tokenId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
		document.addKeyword("userId", kaleoTaskInstanceToken.getUserId());

		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(
				kaleoTaskInstanceToken.getKaleoDefinitionVersionId());

		if (kaleoDefinitionVersion != null) {
			document.addKeyword("version", kaleoDefinitionVersion.getVersion());
		}

		return document;
	}

	@Override
	public void updateDocument(Document document) {
		super.updateDocument(document);

		if (GetterUtil.getBoolean(document.get("completed"))) {
			_slaProcessResultWorkflowMetricsIndexer.expireDocuments(
				GetterUtil.getLong(document.get("companyId")),
				GetterUtil.getLong(document.get("instanceId")));

			_slaTaskResultWorkflowMetricsIndexer.expireDocuments(
				GetterUtil.getLong(document.get("companyId")),
				GetterUtil.getLong(document.get("instanceId")));
		}
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
	protected void reindex(long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoTaskInstanceTokenLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(KaleoTaskInstanceToken kaleoTaskInstanceToken) ->
				workflowMetricsPortalExecutor.execute(
					() -> addDocument(createDocument(kaleoTaskInstanceToken))));

		actionableDynamicQuery.performActions();
	}

	@Reference
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private SLAProcessResultWorkflowMetricsIndexer
		_slaProcessResultWorkflowMetricsIndexer;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

}