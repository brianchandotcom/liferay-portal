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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.text.ParseException;

import java.time.Duration;

import java.util.Date;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = TokenWorkflowMetricsIndexer.class)
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
			kaleoTaskAssignmentInstanceLocalService.
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
			document.addKeyword(
				"completionUserId",
				kaleoTaskInstanceToken.getCompletionUserId());
		}

		Date createDate = kaleoTaskInstanceToken.getCreateDate();

		document.addDateSortable("createDate", createDate);

		document.addKeyword("deleted", false);

		if (kaleoTaskInstanceToken.isCompleted()) {
			Duration duration = Duration.between(
				createDate.toInstant(), completionDate.toInstant());

			document.addNumber("duration", duration.toMillis());
		}

		KaleoInstance kaleoInstance =
			kaleoInstanceLocalService.fetchKaleoInstance(
				kaleoTaskInstanceToken.getKaleoInstanceId());

		if (kaleoInstance != null) {
			document.addKeyword(
				"instanceCompleted", kaleoInstance.isCompleted());
		}

		document.addKeyword(
			"instanceId", kaleoTaskInstanceToken.getKaleoInstanceId());
		document.addDateSortable(
			"modifiedDate", kaleoTaskInstanceToken.getModifiedDate());
		document.addKeyword(
			"processId", kaleoTaskInstanceToken.getKaleoDefinitionId());
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
	public String getIndexName() {
		return "workflow-metrics-tokens";
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsTokenType";
	}

	@Override
	public void reindex(long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			kaleoTaskInstanceTokenLocalService.getActionableDynamicQuery();

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

	@Override
	public void updateDocument(Document document) {
		super.updateDocument(document);

		BooleanQuery booleanQuery = queries.booleanQuery();

		booleanQuery.addMustQueryClauses(
			queries.term(
				"companyId", GetterUtil.getLong(document.get("companyId"))),
			queries.term(
				"instanceId", GetterUtil.getLong(document.get("instanceId"))),
			queries.term(
				"processId", GetterUtil.getLong(document.get("processId"))),
			queries.term("taskId", GetterUtil.getLong(document.get("taskId"))),
			queries.term(
				"tokenId", GetterUtil.getLong(document.get("tokenId"))));

		_slaTaskResultWorkflowMetricsIndexer.updateDocuments(
			documentImpl -> new DocumentImpl() {
				{
					if (!Objects.isNull(document.get("assigneeId"))) {
						addKeyword("assigneeId", document.get("assigneeId"));
					}

					if (!Objects.isNull(document.get("completionDate"))) {
						try {
							addDateSortable(
								"completionDate",
								document.getDate("completionDate"));
						}
						catch (ParseException parseException) {
							if (_log.isDebugEnabled()) {
								_log.debug(parseException, parseException);
							}
						}
					}

					if (!Objects.isNull(document.get("completionUserId"))) {
						addKeyword(
							"completionUserId",
							document.get("completionUserId"));
					}

					if (!Objects.isNull(document.get("instanceCompleted"))) {
						addKeyword(
							"instanceCompleted",
							document.get("instanceCompleted"));
					}

					addKeyword(Field.UID, documentImpl.getString(Field.UID));
				}
			},
			booleanQuery);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TokenWorkflowMetricsIndexer.class);

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

}