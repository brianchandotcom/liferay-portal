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

import com.liferay.headless.admin.workflow.dto.v1_0.ChangeTransition;
import com.liferay.headless.admin.workflow.dto.v1_0.Role;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToMe;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToRole;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignToUser;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.ObjectReviewedUtil;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.RoleUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	public Page<WorkflowTask> getWorkflowInstanceWorkflowTasksAssignedToMePage(
			Long workflowInstanceId, Boolean completed, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByWorkflowInstance(
					contextCompany.getCompanyId(), contextUser.getUserId(),
					workflowInstanceId, completed,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByWorkflowInstance(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowInstanceId, completed));
	}

	@Override
	public Page<WorkflowTask>
			getWorkflowInstanceWorkflowTasksAssignedToUserPage(
				Long workflowInstanceId, Long assigneeId, Boolean completed,
				Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByWorkflowInstance(
					contextCompany.getCompanyId(), assigneeId,
					workflowInstanceId, completed,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByWorkflowInstance(
				contextCompany.getCompanyId(), assigneeId, workflowInstanceId,
				completed));
	}

	@Override
	public Page<WorkflowTask> getWorkflowInstanceWorkflowTasksPage(
			Long workflowInstanceId, Boolean completed, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.getWorkflowTasksByWorkflowInstance(
					contextCompany.getCompanyId(), null, workflowInstanceId,
					completed, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.getWorkflowTaskCountByWorkflowInstance(
				contextCompany.getCompanyId(), null, workflowInstanceId,
				completed));
	}

	@Override
	public WorkflowTask getWorkflowTask(Long workflowTaskId) throws Exception {
		return _toWorkflowTask(
			_workflowTaskManager.getWorkflowTask(
				contextCompany.getCompanyId(), workflowTaskId));
	}

	@Override
	public String getWorkflowTaskHasAssignableUsers(Long workflowTaskId)
		throws Exception {

		return Boolean.toString(
			_workflowTaskManager.hasAssignableUsers(
				contextCompany.getCompanyId(), workflowTaskId));
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
			String[] assetTypes, Long[] assigneeUserIds, Boolean completed,
			Date dateDueEnd, Date dateDueStart, Boolean searchByUserRoles,
			String[] taskNames, Long workflowDefinitionId,
			Long[] workflowInstanceIds, Pagination pagination, Sort[] sorts)
		throws Exception {

		return Page.of(
			transform(
				_workflowTaskManager.search(
					contextCompany.getCompanyId(), contextUser.getUserId(),
					assetTitle, taskNames, assetTypes, assetPrimaryKeys,
					assigneeUserIds, dateDueStart, dateDueEnd, completed,
					searchByUserRoles, workflowDefinitionId,
					workflowInstanceIds,
					GetterUtil.getBoolean(andOperator, true),
					pagination.getStartPosition(), pagination.getEndPosition(),
					_toOrderByComparator((Sort)ArrayUtil.getValue(sorts, 0))),
				this::_toWorkflowTask),
			pagination,
			_workflowTaskManager.searchCount(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				assetTitle, taskNames, assetTypes, assetPrimaryKeys,
				assigneeUserIds, dateDueStart, dateDueEnd, completed,
				searchByUserRoles, workflowDefinitionId, workflowInstanceIds,
				GetterUtil.getBoolean(andOperator, true)));
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
	public void patchWorkflowTaskAssignToUser(
			WorkflowTaskAssignToUser[] workflowTaskAssignToUsers)
		throws Exception {

		for (WorkflowTaskAssignToUser workflowTaskAssignToUser :
				workflowTaskAssignToUsers) {

			_workflowTaskManager.assignWorkflowTaskToUser(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowTaskAssignToUser.getWorkflowTaskId(),
				workflowTaskAssignToUser.getAssigneeId(),
				workflowTaskAssignToUser.getComment(),
				workflowTaskAssignToUser.getDueDate(), null);
		}
	}

	@Override
	public void patchWorkflowTaskChangeTransition(
			ChangeTransition[] changeTransitions)
		throws Exception {

		for (ChangeTransition changeTransition : changeTransitions) {
			_workflowTaskManager.completeWorkflowTask(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				changeTransition.getWorkflowTaskId(),
				changeTransition.getTransitionName(),
				changeTransition.getComment(), null);
		}
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
				workflowTaskId, changeTransition.getTransitionName(),
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

	private OrderByComparator<com.liferay.portal.kernel.workflow.WorkflowTask>
		_toOrderByComparator(Sort sort) {

		if (sort != null) {
			boolean ascending = !sort.isReverse();

			String sortFieldName = sort.getFieldName();

			if (StringUtil.startsWith(sortFieldName, "dateCompletion")) {
				return _workflowComparatorFactory.
					getTaskCompletionDateComparator(ascending);
			}
			else if (StringUtil.startsWith(sortFieldName, "dateCreated")) {
				return _workflowComparatorFactory.getTaskCreateDateComparator(
					ascending);
			}
			else if (StringUtil.startsWith(sortFieldName, "dateDue")) {
				return _workflowComparatorFactory.getTaskDueDateComparator(
					ascending);
			}
			else if (StringUtil.startsWith(sortFieldName, "name")) {
				return _workflowComparatorFactory.getTaskNameComparator(
					ascending);
			}

			return _workflowComparatorFactory.getTaskInstanceIdComparator(
				ascending);
		}

		return null;
	}

	private Role _toRole(com.liferay.portal.kernel.model.Role role)
		throws PortalException {

		return RoleUtil.toRole(
			contextAcceptLanguage.isAcceptAllLanguages(),
			contextAcceptLanguage.getPreferredLocale(), _portal, role,
			_userLocalService.getUserById(role.getUserId()));
	}

	private WorkflowTask _toWorkflowTask(
			com.liferay.portal.kernel.workflow.WorkflowTask workflowTask)
		throws Exception {

		return new WorkflowTask() {
			{
				if (workflowTask.getAssigneeUserId() > 0) {
					assigneePerson = CreatorUtil.toCreator(
						_portal,
						_userLocalService.getUser(
							workflowTask.getAssigneeUserId()));
					assigneeRoles = _getRoles(
						workflowTask.getWorkflowTaskAssignees());
				}

				completed = workflowTask.isCompleted();
				dateCompletion = workflowTask.getCompletionDate();
				dateCreated = workflowTask.getCreateDate();
				dateDue = workflowTask.getDueDate();
				description = workflowTask.getDescription();
				id = workflowTask.getWorkflowTaskId();
				label = _language.get(
					ResourceBundleUtil.getModuleAndPortalResourceBundle(
						contextAcceptLanguage.getPreferredLocale(),
						WorkflowTaskResourceImpl.class),
					workflowTask.getName());
				name = workflowTask.getName();
				objectReviewed = ObjectReviewedUtil.toObjectReviewed(
					contextAcceptLanguage.getPreferredLocale(),
					workflowTask.getOptionalAttributes());
				workflowDefinitionId = workflowTask.getWorkflowDefinitionId();
				workflowDefinitionName =
					workflowTask.getWorkflowDefinitionName();
				workflowDefinitionVersion = String.valueOf(
					workflowTask.getWorkflowDefinitionVersion());
				workflowInstanceId = workflowTask.getWorkflowInstanceId();
			}
		};
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowComparatorFactory _workflowComparatorFactory;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}