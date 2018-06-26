/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.workflow.apio.internal.architect.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.blog.apio.architect.identifier.BlogPostingIdentifier;
import com.liferay.comment.apio.architect.identifier.CommentIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.workflow.apio.architect.identifier.WorkflowTaskIdentifier;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose a WorkflowTask resources through
 * a web API. The resources are mapped from the internal model {@link
 * WorkflowTask}.
 *
 * @author Sarai Díaz
 * @review
 */
@Component
public class WorkflowTaskNestedCollectionResource
	implements NestedCollectionResource
		<WorkflowTask, Long, WorkflowTaskIdentifier, Long, PersonIdentifier> {

	@Override
	public NestedCollectionRoutes<WorkflowTask, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<WorkflowTask, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "workflow-tasks";
	}

	@Override
	public ItemRoutes<WorkflowTask, Long> itemRoutes(
		ItemRoutes.Builder<WorkflowTask, Long> builder) {

		return builder.addGetter(
			this::_getWorkflowTask, Company.class
		).build();
	}

	@Override
	public Representor<WorkflowTask> representor(
		Representor.Builder<WorkflowTask, Long> builder) {

		return builder.types(
			"WorkflowTask"
		).identifier(
			WorkflowTask::getWorkflowTaskId
		).addBidirectionalModel(
			"assignee", "tasks", PersonIdentifier.class,
			WorkflowTask::getAssigneeUserId
		).addBoolean(
			"completed", WorkflowTask::isCompleted
		).addDate(
			"dateCompleted", WorkflowTask::getCompletionDate
		).addDate(
			"dateCreated", WorkflowTask::getCreateDate
		).addDate(
			"expires", WorkflowTask::getDueDate
		).addLinkedModel(
			"blogPost", BlogPostingIdentifier.class, this::_getLinkedModelId
		).addLinkedModel(
			"comment", CommentIdentifier.class, this::_getLinkedModelId
		).addString(
			"definitionName", WorkflowTask::getWorkflowDefinitionName
		).addString(
			"description", WorkflowTask::getDescription
		).addString(
			"name", WorkflowTask::getName
		).addStringList(
			"transitions", this::_getTaskTransitionsNames
		).build();
	}

	private Serializable _getEntryClassPK(WorkflowTask workflowTask) {
		Map<String, Serializable> optionalAttributes =
			workflowTask.getOptionalAttributes();

		return optionalAttributes.get("entryClassPK");
	}

	private Long _getLinkedModelId(WorkflowTask workflowTask) {
		return GetterUtil.getLong(_getEntryClassPK(workflowTask));
	}

	private PageItems<WorkflowTask> _getPageItems(
			Pagination pagination, long userId, Company company)
		throws WorkflowException {

		List<WorkflowTask> workflowTasks =
			_workflowTaskManager.getWorkflowTasksByUser(
				company.getCompanyId(), userId, null,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int count = _workflowTaskManager.getWorkflowTaskCountByUser(
			company.getCompanyId(), userId, null);

		return new PageItems<>(workflowTasks, count);
	}

	private List<String> _getTaskTransitionsNames(WorkflowTask workflowTask) {
		long userId = workflowTask.getAssigneeUserId();

		long workflowTaskId = workflowTask.getWorkflowTaskId();

		return Try.fromFallible(
			() -> _userLocalService.getUserById(
				workflowTask.getAssigneeUserId())
		).map(
			User::getCompanyId
		).map(
			companyId -> _workflowTaskManager.getNextTransitionNames(
				companyId, userId, workflowTaskId)
		).orElse(
			null
		);
	}

	private WorkflowTask _getWorkflowTask(long workflowTaskId, Company company)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTask(
			company.getCompanyId(), workflowTaskId);
	}

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}