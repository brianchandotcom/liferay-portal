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

package com.liferay.headless.workflow.internal.resource.v1_0;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.workflow.dto.v1_0.ChangeTransition;
import com.liferay.headless.workflow.dto.v1_0.ObjectReviewed;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
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
	public Page<WorkflowTask> getWorkflowTasksByWorkflowTask(
			String workflowTaskId, Pagination pagination)
		throws Exception {

		if (workflowTaskId.equals(WorkflowTaskType.TO_ME.getName())) {
			return Page.of(
				transform(
					_workflowTaskManager.getWorkflowTasksByUser(
						contextCompany.getCompanyId(), _user.getUserId(), null,
						pagination.getStartPosition(),
						pagination.getEndPosition(), null),
					this::_toWorkflowTask),
				pagination,
				_workflowTaskManager.getWorkflowTaskCountByUser(
					contextCompany.getCompanyId(), _user.getUserId(), null));
		}
		else if (workflowTaskId.equals(
					WorkflowTaskType.TO_MY_ROLES.getName())) {

			return Page.of(
				transform(
					_workflowTaskManager.getWorkflowTasksByUserRoles(
						contextCompany.getCompanyId(), _user.getUserId(), null,
						pagination.getStartPosition(),
						pagination.getEndPosition(), null),
					this::_toWorkflowTask),
				pagination,
				_workflowTaskManager.getWorkflowTaskCountByUserRoles(
					contextCompany.getCompanyId(), _user.getUserId(), null));
		}

		return null;
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
				workflowTaskId, assigneeId, "", null, null));
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

	public enum WorkflowTaskType {

		TO_ME("assigned-to-me"), TO_MY_ROLES("assigned-to-my-roles");

		public String getName() {
			return _name;
		}

		private WorkflowTaskType(String name) {
			_name = name;
		}

		private final String _name;

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

	private List<String> _getTaskTransitionsNames(
			com.liferay.portal.kernel.workflow.WorkflowTask workflowTask)
		throws PortalException {

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
		throws PortalException {

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
				transitions = _getTaskTransitionsNames(
					workflowTask
				).toArray(
					new String[0]
				);
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