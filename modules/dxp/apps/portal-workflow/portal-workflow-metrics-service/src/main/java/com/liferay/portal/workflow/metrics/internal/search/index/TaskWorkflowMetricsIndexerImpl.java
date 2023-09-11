/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.workflow.metrics.internal.search.index.util.WorkflowMetricsIndexerUtil;
import com.liferay.portal.workflow.metrics.model.AddTaskRequest;
import com.liferay.portal.workflow.metrics.model.Assignment;
import com.liferay.portal.workflow.metrics.model.CompleteTaskRequest;
import com.liferay.portal.workflow.metrics.model.DeleteTaskRequest;
import com.liferay.portal.workflow.metrics.model.RoleAssignment;
import com.liferay.portal.workflow.metrics.model.UpdateTaskRequest;
import com.liferay.portal.workflow.metrics.model.UserAssignment;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.WorkflowMetricsIndex;

import java.time.Duration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = TaskWorkflowMetricsIndexer.class)
public class TaskWorkflowMetricsIndexerImpl
	extends BaseWorkflowMetricsIndexer implements TaskWorkflowMetricsIndexer {

	@Override
	public Document addTask(AddTaskRequest addTaskRequest) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		if (!searchCapabilities.isWorkflowMetricsSupported()) {
			return documentBuilder.build();
		}

		documentBuilder.setValue("active", Boolean.TRUE);

		List<Long> assignmentGroupIds = new ArrayList<>();
		List<Long> assignmentIds = new ArrayList<>();

		_populateTaskAssignments(
			assignmentGroupIds, assignmentIds, addTaskRequest.getAssignments());

		String assignmentType = _getAssignmentType(
			addTaskRequest.getAssignments());

		if (!assignmentIds.isEmpty()) {
			documentBuilder.setLongs(
				"assigneeIds", assignmentIds.toArray(new Long[0]));
			documentBuilder.setString("assigneeType", assignmentType);
		}

		documentBuilder.setString(
			"className", addTaskRequest.getClassName()
		).setLong(
			"classPK", addTaskRequest.getClassPK()
		).setLong(
			"companyId", addTaskRequest.getCompanyId()
		).setValue(
			"completed", addTaskRequest.isCompleted()
		);

		if (addTaskRequest.isCompleted()) {
			documentBuilder.setDate(
				"completionDate", getDate(addTaskRequest.getCompletionDate())
			).setLong(
				"completionUserId", addTaskRequest.getCompletionUserId()
			);
		}

		Date createDate = addTaskRequest.getCreateDate();

		documentBuilder.setDate(
			"createDate", getDate(createDate)
		).setValue(
			Field.getSortableFieldName("createDate_Number"),
			createDate.getTime()
		).setValue(
			"deleted", false
		);

		if (addTaskRequest.isCompleted()) {
			documentBuilder.setLong(
				"duration",
				_getDuration(addTaskRequest.getCompletionDate(), createDate));
		}

		documentBuilder.setValue(
			"instanceCompleted", addTaskRequest.isInstanceCompleted()
		).setDate(
			"instanceCompletionDate",
			getDate(addTaskRequest.getInstanceCompletionDate())
		).setLong(
			"instanceId", addTaskRequest.getInstanceId()
		).setDate(
			"modifiedDate", getDate(addTaskRequest.getModifiedDate())
		).setString(
			"name", addTaskRequest.getName()
		).setString(
			Field.getSortableFieldName("name"),
			StringUtil.toLowerCase(addTaskRequest.getName())
		).setLong(
			"nodeId", addTaskRequest.getNodeId()
		).setLong(
			"processId", addTaskRequest.getProcessId()
		).setLong(
			"taskId", addTaskRequest.getTaskId()
		).setString(
			"uid",
			digest(addTaskRequest.getCompanyId(), addTaskRequest.getTaskId())
		).setLong(
			"userId", addTaskRequest.getUserId()
		).setString(
			"version", addTaskRequest.getProcessVersion()
		);

		setLocalizedField(
			documentBuilder, "assetTitle", addTaskRequest.getAssetTitleMap());
		setLocalizedField(
			documentBuilder, "assetType", addTaskRequest.getAssetTypeMap());

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				addDocument(document);

				if (addTaskRequest.isCompleted()) {
					return;
				}

				ScriptBuilder scriptBuilder = scripts.builder();

				UpdateDocumentRequest updateDocumentRequest =
					new UpdateDocumentRequest(
						_instanceWorkflowMetricsIndex.getIndexName(
							addTaskRequest.getCompanyId()),
						WorkflowMetricsIndexerUtil.digest(
							_instanceWorkflowMetricsIndex.getIndexType(),
							addTaskRequest.getCompanyId(),
							addTaskRequest.getInstanceId()),
						scriptBuilder.idOrCode(
							StringUtil.read(
								getClass(),
								"dependencies/workflow-metrics-add-task-" +
									"script.painless")
						).language(
							"painless"
						).putParameter(
							"task",
							HashMapBuilder.<String, Object>put(
								"assigneeGroupIds", assignmentGroupIds
							).put(
								"assigneeIds", assignmentIds
							).put(
								"assigneeName",
								_getAssigneeName(
									addTaskRequest.getAssignments())
							).put(
								"assigneeType", assignmentType
							).put(
								"taskId", addTaskRequest.getTaskId()
							).put(
								"taskName", addTaskRequest.getName()
							).build()
						).scriptType(
							ScriptType.INLINE
						).build());

				updateDocumentRequest.setScriptedUpsert(true);

				if (PortalRunMode.isTestMode()) {
					updateDocumentRequest.setRefresh(true);
				}

				searchEngineAdapter.execute(updateDocumentRequest);
			});

		return document;
	}

	@Override
	public Document completeTask(CompleteTaskRequest completeTaskRequest) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", completeTaskRequest.getCompanyId()
		).setValue(
			"completed", true
		).setDate(
			"completionDate", getDate(completeTaskRequest.getCompletionDate())
		).setLong(
			"completionUserId", completeTaskRequest.getCompletionUserId()
		).setLong(
			"duration", completeTaskRequest.getDuration()
		).setDate(
			"modifiedDate", getDate(completeTaskRequest.getModifiedDate())
		).setLong(
			"taskId", completeTaskRequest.getTaskId()
		).setString(
			"uid",
			digest(
				completeTaskRequest.getCompanyId(),
				completeTaskRequest.getTaskId())
		).setLong(
			"userId", completeTaskRequest.getUserId()
		);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				updateDocument(document);

				_deleteTask(
					completeTaskRequest.getCompanyId(),
					completeTaskRequest.getTaskId());

				BooleanQuery booleanQuery = queries.booleanQuery();

				booleanQuery.addMustQueryClauses(
					queries.term(
						"companyId", completeTaskRequest.getCompanyId()),
					queries.term("taskId", completeTaskRequest.getTaskId()));

				_slaTaskResultWorkflowMetricsIndexer.updateDocuments(
					completeTaskRequest.getCompanyId(),
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
	public void deleteTask(DeleteTaskRequest deleteTaskRequest) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", deleteTaskRequest.getCompanyId()
		).setLong(
			"taskId", deleteTaskRequest.getTaskId()
		).setString(
			"uid",
			digest(
				deleteTaskRequest.getCompanyId(), deleteTaskRequest.getTaskId())
		);

		workflowMetricsPortalExecutor.execute(
			() -> {
				deleteDocument(documentBuilder);

				_deleteTask(
					deleteTaskRequest.getCompanyId(),
					deleteTaskRequest.getTaskId());
			});
	}

	@Override
	public String getIndexName(long companyId) {
		return _taskWorkflowMetricsIndex.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return _taskWorkflowMetricsIndex.getIndexType();
	}

	@Override
	public Document updateTask(UpdateTaskRequest updateTaskRequest) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		if (!searchCapabilities.isWorkflowMetricsSupported()) {
			return documentBuilder.build();
		}

		List<Long> assignmentGroupIds = new ArrayList<>();
		List<Long> assignmentIds = new ArrayList<>();

		_populateTaskAssignments(
			assignmentGroupIds, assignmentIds,
			updateTaskRequest.getAssignments());

		String assignmentType = _getAssignmentType(
			updateTaskRequest.getAssignments());

		if (!assignmentIds.isEmpty()) {
			documentBuilder.setLongs(
				"assigneeIds", assignmentIds.toArray(new Long[0]));
			documentBuilder.setString("assigneeType", assignmentType);
		}

		documentBuilder.setLong(
			"companyId", updateTaskRequest.getCompanyId()
		).setDate(
			"modifiedDate", getDate(updateTaskRequest.getModifiedDate())
		).setLong(
			"taskId", updateTaskRequest.getTaskId()
		).setString(
			"uid",
			digest(
				updateTaskRequest.getCompanyId(), updateTaskRequest.getTaskId())
		).setLong(
			"userId", updateTaskRequest.getUserId()
		);

		setLocalizedField(
			documentBuilder, "assetTitle",
			updateTaskRequest.getAssetTitleMap());
		setLocalizedField(
			documentBuilder, "assetType", updateTaskRequest.getAssetTypeMap());

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				updateDocument(document);

				if (Objects.isNull(document.getLongs("assigneeIds"))) {
					return;
				}

				BooleanQuery booleanQuery = queries.booleanQuery();

				booleanQuery.addMustQueryClauses(
					queries.term("companyId", document.getLong("companyId")),
					queries.term("taskId", document.getLong("taskId")));

				_slaTaskResultWorkflowMetricsIndexer.updateDocuments(
					updateTaskRequest.getCompanyId(),
					HashMapBuilder.<String, Object>put(
						"assigneeIds", assignmentIds
					).put(
						"assigneeType", assignmentType
					).build(),
					booleanQuery);

				ScriptBuilder scriptBuilder = scripts.builder();

				scriptBuilder.idOrCode(
					StringUtil.read(
						getClass(),
						"dependencies/workflow-metrics-update-task-" +
							"script.painless")
				).language(
					"painless"
				).putParameter(
					"task",
					HashMapBuilder.<String, Object>put(
						"assigneeGroupIds", assignmentGroupIds
					).put(
						"assigneeIds", assignmentIds
					).put(
						"assigneeName",
						_getAssigneeName(updateTaskRequest.getAssignments())
					).put(
						"assigneeType", assignmentType
					).put(
						"taskId", updateTaskRequest.getTaskId()
					).build()
				).scriptType(
					ScriptType.INLINE
				);

				UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
					new UpdateByQueryDocumentRequest(
						queries.nested(
							"tasks",
							queries.term(
								"tasks.taskId", updateTaskRequest.getTaskId())),
						scriptBuilder.build(),
						_instanceWorkflowMetricsIndex.getIndexName(
							updateTaskRequest.getCompanyId()));

				updateByQueryDocumentRequest.setRefresh(true);

				searchEngineAdapter.execute(updateByQueryDocumentRequest);
			});

		return document;
	}

	private void _deleteTask(long companyId, long taskId) {
		if (!searchCapabilities.isWorkflowMetricsSupported()) {
			return;
		}

		ScriptBuilder scriptBuilder = scripts.builder();

		searchEngineAdapter.execute(
			new UpdateByQueryDocumentRequest(
				queries.nested("tasks", queries.term("tasks.taskId", taskId)),
				scriptBuilder.idOrCode(
					StringUtil.read(
						getClass(),
						"dependencies/workflow-metrics-delete-task-" +
							"script.painless")
				).language(
					"painless"
				).putParameter(
					"taskId", taskId
				).scriptType(
					ScriptType.INLINE
				).build(),
				_instanceWorkflowMetricsIndex.getIndexName(companyId)));
	}

	private String _getAssigneeName(List<Assignment> assignments) {
		if (ListUtil.isEmpty(assignments) ||
			(assignments.get(0) instanceof RoleAssignment)) {

			return null;
		}

		UserAssignment userAssignment = (UserAssignment)assignments.get(0);

		return userAssignment.getName();
	}

	private String _getAssignmentType(List<Assignment> assignments) {
		if (ListUtil.isEmpty(assignments)) {
			return null;
		}

		Assignment assignment = assignments.get(0);

		if (assignment instanceof RoleAssignment) {
			return Role.class.getName();
		}

		return User.class.getName();
	}

	private long _getDuration(Date completionDate, Date createDate) {
		Duration duration = Duration.between(
			createDate.toInstant(), completionDate.toInstant());

		return duration.toMillis();
	}

	private void _populateTaskAssignments(
		List<Long> assignmentGroupIds, List<Long> assignmentIds,
		List<Assignment> assignments) {

		if (ListUtil.isEmpty(assignments)) {
			return;
		}

		Assignment firstAssignment = assignments.get(0);

		if (firstAssignment instanceof RoleAssignment) {
			for (Assignment assignment : assignments) {
				assignmentIds.add(assignment.getAssignmentId());

				RoleAssignment roleAssignment = (RoleAssignment)assignment;

				assignmentGroupIds.addAll(roleAssignment.getGroupIds());
			}
		}
		else {
			assignmentIds.add(firstAssignment.getAssignmentId());
		}
	}

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndex _taskWorkflowMetricsIndex;

}