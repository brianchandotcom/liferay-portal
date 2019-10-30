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
import com.liferay.headless.admin.workflow.dto.v1_0.Role;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToRole;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	public Page<WorkflowTask> getWorkflowInstanceWorkflowTasksPage(
			Long workflowInstanceId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByWorkflowInstance(
					contextCompany.getCompanyId(), contextUser.getUserId(),
					workflowInstanceId, null, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByWorkflowInstance(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowInstanceId, null));
	}

	@Override
	public WorkflowTask getWorkflowTask(Long workflowTaskId) throws Exception {
		return _toWorkflowTask(
			_workflowTaskManager.getWorkflowTask(
				contextCompany.getCompanyId(), workflowTaskId));
	}

	@Override
	public String getWorkflowTaskHasOtherAssignableUsers(Long workflowTaskId)
		throws Exception {

		return Boolean.toString(
			_workflowTaskManager.hasOtherAssignees(
				workflowTaskId, contextUser.getUserId()));
	}

	@Override
	public Page<WorkflowTask> getWorkflowTasksAssignedToMePage(
			Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByUser(
					contextCompany.getCompanyId(), contextUser.getUserId(),
					null, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByUser(
				contextCompany.getCompanyId(), contextUser.getUserId(), null));
	}

	@Override
	public Page<WorkflowTask> getWorkflowTasksAssignedToMyRolesPage(
			Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByUserRoles(
					contextCompany.getCompanyId(), contextUser.getUserId(),
					null, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByUserRoles(
				contextCompany.getCompanyId(), contextUser.getUserId(), null));
	}

	@Override
	public Page<WorkflowTask> getWorkflowTasksAssignedToRolePage(
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
	public Page<WorkflowTask> getWorkflowTasksAssignedToUserPage(
			Long assigneeId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByUser(
					contextCompany.getCompanyId(), assigneeId, null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByUser(
				contextCompany.getCompanyId(), assigneeId, null));
	}

	@Override
	public Page<WorkflowTask> getWorkflowTasksAssignedToUserRolesPage(
			Long assigneeId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByUserRoles(
					contextCompany.getCompanyId(), assigneeId, null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByUserRoles(
				contextCompany.getCompanyId(), assigneeId, null));
	}

	@Override
	public Page<WorkflowTask> getWorkflowTasksPage(
			Boolean andOperator, Long[] assetPrimaryKeys, String assetTitle,
			String[] assetTypes, Boolean completed, Date dateDueEnd,
			Date dateDueStart, Boolean searchByUserRoles, String taskName,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.search(
					contextCompany.getCompanyId(), contextUser.getUserId(),
					assetTitle, taskName, assetTypes, assetPrimaryKeys,
					dateDueStart, dateDueEnd, completed, searchByUserRoles,
					andOperator, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.searchCount(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				assetTitle, taskName, assetTypes, assetPrimaryKeys,
				dateDueStart, dateDueEnd, completed, searchByUserRoles,
				andOperator));
	}

	@Override
	public Page<WorkflowTask> getWorkflowTasksSubmittingUserPage(
			Long creatorId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksBySubmittingUser(
					contextCompany.getCompanyId(), creatorId, null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountBySubmittingUser(
				contextCompany.getCompanyId(), creatorId, null));
	}

	@Override
	public WorkflowTask postWorkflowTaskAssignToMe(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		return _toWorkflowTask(
			_workflowTaskManager.assignWorkflowTaskToUser(
				contextUser.getCompanyId(), contextUser.getUserId(),
				workflowTaskId, contextUser.getUserId(),
				workflowTaskAssignToMe.getComment(),
				workflowTaskAssignToMe.getDueDate(), null));
	}

	@Override
	public WorkflowTask postWorkflowTaskAssignToRole(
			Long workflowTaskId,
			WorkflowTaskAssignToRole workflowTaskAssignToRole)
		throws Exception {

		return _toWorkflowTask(
			_workflowTaskManager.assignWorkflowTaskToRole(
				contextUser.getCompanyId(), contextUser.getUserId(),
				workflowTaskId, workflowTaskAssignToRole.getRoleId(),
				workflowTaskAssignToRole.getComment(),
				workflowTaskAssignToRole.getDueDate(), null));
	}

	@Override
	public WorkflowTask postWorkflowTaskAssignToUser(
			Long workflowTaskId,
			WorkflowTaskAssignToUser workflowTaskAssignToUser)
		throws Exception {

		return _toWorkflowTask(
			_workflowTaskManager.assignWorkflowTaskToUser(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowTaskId, workflowTaskAssignToUser.getAssigneeId(),
				workflowTaskAssignToUser.getComment(),
				workflowTaskAssignToUser.getDueDate(), null));
	}

	@Override
	public WorkflowTask postWorkflowTaskChangeTransition(
			Long workflowTaskId, ChangeTransition changeTransition)
		throws Exception {

		return _toWorkflowTask(
			_workflowTaskManager.completeWorkflowTask(
				contextUser.getCompanyId(), contextUser.getUserId(),
				workflowTaskId, changeTransition.getTransition(),
				changeTransition.getComment(), null));
	}

	@Override
	public WorkflowTask postWorkflowTaskUpdateDueDate(
			Long workflowTaskId, WorkflowTaskAssignToMe workflowTaskAssignToMe)
		throws Exception {

		return _toWorkflowTask(
			_workflowTaskManager.updateDueDate(
				contextUser.getCompanyId(), contextUser.getUserId(),
				workflowTaskId, workflowTaskAssignToMe.getComment(),
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

	private Role[] _getRoles(List<WorkflowTaskAssignee> workflowTaskAssignees)
		throws PortalException {

		List<Role> roles = new ArrayList<>();

		for (WorkflowTaskAssignee workflowTaskAssignee :
				workflowTaskAssignees) {

			String assigneeClassName =
				workflowTaskAssignee.getAssigneeClassName();

			if (!assigneeClassName.equals(
					com.liferay.portal.kernel.model.Role.class.getName())) {

				continue;
			}

			roles.add(
				_toRole(
					_roleLocalService.getRole(
						workflowTaskAssignee.getAssigneeClassPK())));
		}

		return roles.toArray(new Role[0]);
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

	private Role _toRole(com.liferay.portal.kernel.model.Role role)
		throws PortalException {

		return new Role() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					role.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUserById(role.getUserId()));
				dateCreated = role.getCreateDate();
				dateModified = role.getModifiedDate();
				description = role.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				id = role.getRoleId();
				name = role.getTitle(
					contextAcceptLanguage.getPreferredLocale());
				roleType = role.getTypeLabel();
			}
		};
	}

	private WorkflowTask _toWorkflowTask(
			com.liferay.portal.kernel.workflow.WorkflowTask workflowTask)
		throws Exception {

		return new WorkflowTask() {
			{
				assigneePerson = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(
						workflowTask.getAssigneeUserId()));
				assigneeRoles = _getRoles(
					workflowTask.getWorkflowTaskAssignees());
				completed = workflowTask.isCompleted();
				dateCompletion = workflowTask.getCompletionDate();
				dateCreated = workflowTask.getCreateDate();
				dateDue = workflowTask.getDueDate();
				definitionId = workflowTask.getWorkflowDefinitionId();
				definitionName = workflowTask.getWorkflowDefinitionName();
				definitionVersion = GetterUtil.getString(
					workflowTask.getWorkflowDefinitionVersion());
				description = workflowTask.getDescription();
				id = workflowTask.getWorkflowTaskId();
				instanceId = workflowTask.getWorkflowInstanceId();
				name = workflowTask.getName();
				objectReviewed = _toObjectReviewed(
					workflowTask.getOptionalAttributes());
			}
		};
	}

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}