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

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.admin.workflow.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.dto.v1_0.ObjectReviewed;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Context;

import javax.xml.bind.ValidationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/workflow-task.properties",
	scope = ServiceScope.PROTOTYPE, service = WorkflowTaskResource.class
)
public class WorkflowTaskResourceImpl extends BaseWorkflowTaskResourceImpl {

	@Override
	public Page<WorkflowTask> getRoleWorkflowTasksPage(
			Long roleId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByRole(
					contextCompany.getCompanyId(), roleId, null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByRole(
				contextCompany.getCompanyId(), roleId, null));
	}

	@Override
	public WorkflowTask getWorkflowTask(Long workflowTaskId) throws Exception {
		return _toWorkflowTask(
			_workflowTaskManager.getWorkflowTask(
				contextCompany.getCompanyId(), workflowTaskId));
	}

	@Override
	public Page<WorkflowTask> getWorkflowTasksAssignedToMePage(
			Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByUser(
					contextCompany.getCompanyId(), _user.getUserId(), null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByUser(
				contextCompany.getCompanyId(), _user.getUserId(), null));
	}

	@Override
	public Page<WorkflowTask> getWorkflowTasksAssignedToMyRolesPage(
			Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByUserRoles(
					contextCompany.getCompanyId(), _user.getUserId(), null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByUserRoles(
				contextCompany.getCompanyId(), _user.getUserId(), null));
	}

	@Override
	public WorkflowTask postWorkflowTaskAssignToMe(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		return _toWorkflowTask(
			_workflowTaskManager.assignWorkflowTaskToUser(
				_user.getCompanyId(), _user.getUserId(), workflowTaskId,
				_user.getUserId(), workflowTaskAssignToMe.getComment(),
				workflowTaskAssignToMe.getDueDate(), null));
	}

	@Override
	public WorkflowTask postWorkflowTaskAssignToUser(
			Long workflowTaskId,
			WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception {

		long assigneeId = workflowTaskAssignToUser.getAssigneeId();

		return _toWorkflowTask(
			_workflowTaskManager.assignWorkflowTaskToUser(
				contextCompany.getCompanyId(), _user.getUserId(),
				workflowTaskId, assigneeId,
				workflowTaskAssignToUser.getComment(),
				workflowTaskAssignToUser.getDueDate(), null));
	}

	@Override
	public WorkflowTask postWorkflowTaskChangeTransition(
			Long workflowTaskId, ChangeTransition changeTransition)
		throws Exception {

		String transition = changeTransition.getTransition();

		List<String> transitionsNames = _getTaskTransitionsNames(
			_workflowTaskManager.getWorkflowTask(
				_user.getCompanyId(), workflowTaskId));

		if (!transitionsNames.contains(transition)) {
			throw new ValidationException("Invalid transition: " + transition);
		}

		return _toWorkflowTask(
			_workflowTaskManager.completeWorkflowTask(
				_user.getCompanyId(), _user.getUserId(), workflowTaskId,
				transition, "", null));
	}

	@Override
	public WorkflowTask postWorkflowTaskUpdateDueDate(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		return _toWorkflowTask(
			_workflowTaskManager.updateDueDate(
				_user.getCompanyId(), _user.getUserId(), workflowTaskId,
				workflowTaskAssignToMe.getComment(),
				workflowTaskAssignToMe.getDueDate()));
	}

	private String _getResourceType(
		Map<String, Serializable> optionalAttributes) {

		String className = GetterUtil.getString(
			optionalAttributes.get("entryClassName"));

		if (className.equals(BlogsEntry.class.getName())) {
			return "BlogPosting";
		}

		if (className.equals(MBDiscussion.class.getName())) {
			return "Comment";
		}

		return null;
	}

	private List<String> _getTaskTransitionsNames(
			com.liferay.portal.kernel.workflow.WorkflowTask workflowTask)
		throws Exception {

		if (workflowTask.getAssigneeUserId() > 0) {
			User user = _userLocalService.getUserById(
				workflowTask.getAssigneeUserId());

			return _workflowTaskManager.getNextTransitionNames(
				user.getCompanyId(), workflowTask.getAssigneeUserId(),
				workflowTask.getWorkflowTaskId());
		}

		return Collections.emptyList();
	}

	private ObjectReviewed _toObjectReviewed(
		Map<String, Serializable> optionalAttributes) {

		return new ObjectReviewed() {
			{
				id = GetterUtil.getLong(optionalAttributes.get("entryClassPK"));
				resourceType = _getResourceType(optionalAttributes);
			}
		};
	}

	private WorkflowTask _toWorkflowTask(
			com.liferay.portal.kernel.workflow.WorkflowTask workflowTask)
		throws Exception {

		return new WorkflowTask() {
			{
				completed = workflowTask.isCompleted();
				dateCompleted = workflowTask.getCompletionDate();
				dateCreated = workflowTask.getCreateDate();
				definitionName = workflowTask.getWorkflowDefinitionName();
				description = workflowTask.getDescription();
				dueDate = workflowTask.getDueDate();
				id = workflowTask.getWorkflowTaskId();
				name = workflowTask.getName();
				objectReviewed = _toObjectReviewed(
					workflowTask.getOptionalAttributes());

				List<String> taskTransitionsNames = _getTaskTransitionsNames(
					workflowTask);

				transitions = taskTransitionsNames.toArray(new String[0]);
			}
		};
	}

	@Context
	private User _user;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}