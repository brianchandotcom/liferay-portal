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
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.workflow.apio.architect.identifier.WorkflowLogIdentifier;
import com.liferay.workflow.apio.architect.identifier.WorkflowTaskIdentifier;
import com.liferay.workflow.apio.internal.architect.form.AssignToMeForm;
import com.liferay.workflow.apio.internal.architect.form.AssignToUserForm;
import com.liferay.workflow.apio.internal.architect.route.AssignToMePostRoute;
import com.liferay.workflow.apio.internal.architect.route.AssignToUserPostRoute;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose a {@code WorkflowTask} resource
 * through a web API. The resources are mapped from the internal model {@code
 * WorkflowTask}.
 *
 * @author Sarai Díaz
 */
@Component(immediate = true, service = ItemResource.class)
public class WorkflowTaskItemResource
	implements ItemResource<WorkflowTask, Long, WorkflowTaskIdentifier> {

	@Override
	public String getName() {
		return "workflow-tasks";
	}

	@Override
	public ItemRoutes<WorkflowTask, Long> itemRoutes(
		ItemRoutes.Builder<WorkflowTask, Long> builder) {

		return builder.addGetter(
			this::_getWorkflowTask, Company.class
		).addCustomRoute(
			new AssignToMePostRoute(), this::_assignToMe, CurrentUser.class,
			WorkflowTaskIdentifier.class, (credentials, id) -> true,
			AssignToMeForm::buildForm
		).addCustomRoute(
			new AssignToUserPostRoute(), this::_assignToUser, Company.class,
			CurrentUser.class, WorkflowTaskIdentifier.class,
			(credentials, id) -> true, AssignToUserForm::buildForm
		).build();
	}

	@Override
	public Representor<WorkflowTask> representor(
		Representor.Builder<WorkflowTask, Long> builder) {

		return builder.types(
			"WorkflowTask"
		).identifier(
			WorkflowTask::getWorkflowTaskId
		).addBoolean(
			"completed", WorkflowTask::isCompleted
		).addDate(
			"dateCompleted", WorkflowTask::getCompletionDate
		).addDate(
			"dateCreated", WorkflowTask::getCreateDate
		).addDate(
			"expires", WorkflowTask::getDueDate
		).addNested(
			"object", WorkflowTask::getOptionalAttributes,
			nestedBuilder -> nestedBuilder.types(
				"Object"
			).addApplicationRelativeURL(
				"identifier", this::_getResourceURL
			).addString(
				"resourceType", this::_getResourceType
			).build()
		).addRelatedCollection(
			"logs", WorkflowLogIdentifier.class
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

	private WorkflowTask _assignToMe(
		Long workflowTaskId, AssignToMeForm assignToMeForm,
		CurrentUser currentUser) {

		return Try.fromFallible(
			() -> _workflowTaskManager.assignWorkflowTaskToUser(
				currentUser.getCompanyId(), currentUser.getUserId(),
				workflowTaskId, currentUser.getUserId(), "", null, null)
		).orElse(
			null
		);
	}

	private WorkflowTask _assignToUser(
		Long workflowTaskId, AssignToUserForm assignToMeForm, Company company,
		CurrentUser currentUser) {

		long assigneeUserId = assignToMeForm.getUserId();

		return Try.fromFallible(
			() -> _workflowTaskManager.assignWorkflowTaskToUser(
				company.getCompanyId(), currentUser.getUserId(), workflowTaskId,
				assigneeUserId, "", null, null)
		).orElse(
			null
		);
	}

	private String _getResourceType(
		Map<String, Serializable> optionalAttributes) {

		Map<String, String> map = new HashMap<String, String>() {
			{
				put(BlogsEntry.class.getName(), "BlogPosting");
				put(MBDiscussion.class.getName(), "Comment");
			}
		};

		String type = map.get(optionalAttributes.get("entryClassName"));

		if (type == null) {
			return null;
		}

		return type;
	}

	private String _getResourceURL(
		Map<String, Serializable> optionalAttributes) {

		Map<String, String> map = new HashMap<String, String>() {
			{
				put(BlogsEntry.class.getName(), "blog-posting");
				put(MBDiscussion.class.getName(), "comment");
			}
		};

		String entryClassName = map.get(
			optionalAttributes.get("entryClassName"));

		if (entryClassName == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("p/");
		sb.append(entryClassName);
		sb.append("/");

		String entryClassPK = (String)optionalAttributes.get("entryClassPK");

		sb.append(entryClassPK);

		return sb.toString();
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