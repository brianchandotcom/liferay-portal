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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;

import java.time.Duration;

import java.util.Date;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, property = "workflow.metrics.index.entity.name=task",
	service = {TaskWorkflowMetricsIndexer.class, WorkflowMetricsIndex.class}
)
public class TaskWorkflowMetricsIndexerImpl
	extends BaseWorkflowMetricsIndexer implements TaskWorkflowMetricsIndexer {

	@Override
	public Document addTask(
		Long assigneeId, String className, long classPK, long companyId,
		boolean completed, Date completionDate, Long completionUserId,
		Date createDate, boolean instanceCompleted, long instanceId,
		Date modifiedDate, String name, long nodeId, long processId,
		String processVersion, long taskId, long userId) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		if (assigneeId != null) {
			documentBuilder.setLong("assigneeId", assigneeId);
		}

		documentBuilder.setString(
			Field.UID, digest(companyId, taskId)
		).setString(
			"className", className
		).setLong(
			"classPK", classPK
		).setLong(
			"companyId", companyId
		).setValue(
			"completed", completed
		);

		if (completed) {
			documentBuilder.setDate(
				"completionDate", formatDate(completionDate)
			).setLong(
				"completionUserId", completionUserId
			);
		}

		documentBuilder.setDate(
			"createDate", formatDate(createDate)
		).setValue(
			Field.getSortableFieldName(
				StringBundler.concat(
					"createDate", StringPool.UNDERLINE, "Number")),
			createDate.getTime()
		).setValue(
			"deleted", false
		);

		if (completed) {
			documentBuilder.setLong(
				"duration", _getDuration(completionDate, createDate));
		}

		documentBuilder.setValue(
			"instanceCompleted", instanceCompleted
		).setLong(
			"instanceId", instanceId
		).setDate(
			"modifiedDate", formatDate(modifiedDate)
		).setString(
			"name", name
		).setLong(
			"nodeId", nodeId
		).setLong(
			"processId", processId
		).setLong(
			"taskId", taskId
		).setLong(
			"userId", userId
		).setString(
			"version", processVersion
		);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(() -> addDocument(document));

		return document;
	}

	@Override
	public Document completeTask(
		long companyId, Date completionDate, long completionUserId,
		long duration, Date modifiedDate, long taskId, long userId) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setString(
			Field.UID, digest(companyId, taskId)
		).setLong(
			"companyId", companyId
		).setValue(
			"completed", true
		).setDate(
			"completionDate", formatDate(completionDate)
		).setLong(
			"completionUserId", completionUserId
		).setLong(
			"duration", duration
		).setDate(
			"modifiedDate", formatDate(modifiedDate)
		).setLong(
			"taskId", taskId
		).setLong(
			"userId", userId
		);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				updateDocument(document);

				BooleanQuery booleanQuery = queries.booleanQuery();

				booleanQuery.addMustQueryClauses(
					queries.term("companyId", document.getLong("companyId")),
					queries.term("taskId", document.getLong("taskId")));

				_slaTaskResultWorkflowMetricsIndexer.updateDocuments(
					HashMapBuilder.<String, Object>put(
						"completionDate", document.getDate("completionDate")
					).put(
						"completionUserId", document.getLong("completionUserId")
					).build(),
					booleanQuery);
			});

		return document;
	}

	@Override
	public void deleteTask(long companyId, long taskId) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setString(Field.UID, digest(companyId, taskId));

		workflowMetricsPortalExecutor.execute(
			() -> deleteDocument(documentBuilder));
	}

	@Override
	public String getIndexName() {
		return "workflow-metrics-tasks";
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsTaskType";
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
					() -> {
						KaleoDefinitionVersion kaleoDefinitionVersion =
							getKaleoDefinitionVersion(
								kaleoTaskInstanceToken.
									getKaleoDefinitionVersionId());

						if (Objects.isNull(kaleoDefinitionVersion)) {
							return;
						}

						KaleoInstance kaleoInstance =
							kaleoInstanceLocalService.fetchKaleoInstance(
								kaleoTaskInstanceToken.getKaleoInstanceId());

						if (Objects.isNull(kaleoInstance)) {
							return;
						}

						KaleoTaskAssignmentInstance
							kaleoTaskAssignmentInstance =
								kaleoTaskAssignmentInstanceLocalService.
									fetchFirstKaleoTaskAssignmentInstance(
										kaleoTaskInstanceToken.
											getKaleoTaskInstanceTokenId(),
										User.class.getName(), null);

						Long assigneeId = null;

						if (kaleoTaskAssignmentInstance != null) {
							assigneeId =
								kaleoTaskAssignmentInstance.
									getAssigneeClassPK();
						}

						addTask(
							assigneeId, kaleoTaskInstanceToken.getClassName(),
							kaleoTaskInstanceToken.getClassPK(),
							kaleoTaskInstanceToken.getCompanyId(),
							kaleoTaskInstanceToken.isCompleted(),
							kaleoTaskInstanceToken.getCompletionDate(),
							kaleoTaskInstanceToken.getCompletionUserId(),
							kaleoTaskInstanceToken.getCreateDate(),
							kaleoInstance.isCompleted(),
							kaleoTaskInstanceToken.getKaleoInstanceId(),
							kaleoTaskInstanceToken.getModifiedDate(),
							kaleoTaskInstanceToken.getKaleoTaskName(),
							kaleoTaskInstanceToken.getKaleoTaskId(),
							kaleoInstance.getKaleoDefinitionId(),
							kaleoDefinitionVersion.getVersion(),
							kaleoTaskInstanceToken.
								getKaleoTaskInstanceTokenId(),
							kaleoTaskInstanceToken.getUserId());
					}));

		actionableDynamicQuery.performActions();
	}

	@Override
	public Document updateTask(
		Long assigneeId, long companyId, Date modifiedDate, long taskId,
		long userId) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setString(Field.UID, digest(companyId, taskId));

		if (assigneeId != null) {
			documentBuilder.setLong("assigneeId", assigneeId);
		}

		documentBuilder.setLong(
			"companyId", companyId
		).setDate(
			"modifiedDate", formatDate(modifiedDate)
		).setLong(
			"taskId", taskId
		).setLong(
			"userId", userId
		);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				updateDocument(document);

				if (Objects.isNull(document.getLong("assigneeId"))) {
					return;
				}

				BooleanQuery booleanQuery = queries.booleanQuery();

				booleanQuery.addMustQueryClauses(
					queries.term("companyId", document.getLong("companyId")),
					queries.term("taskId", document.getLong("taskId")));

				_slaTaskResultWorkflowMetricsIndexer.updateDocuments(
					HashMapBuilder.<String, Object>put(
						"assigneeId", assigneeId
					).build(),
					booleanQuery);
			});

		return document;
	}

	private long _getDuration(Date completionDate, Date createDate) {
		Duration duration = Duration.between(
			createDate.toInstant(), completionDate.toInstant());

		return duration.toMillis();
	}

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

}