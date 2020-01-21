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

import com.liferay.headless.admin.workflow.dto.v1_0.Assignee;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignableUser;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowTaskAssignableUsers;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.AssigneeUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowTaskAssignableUsersResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/workflow-task-assignable-users.properties",
	scope = ServiceScope.PROTOTYPE,
	service = WorkflowTaskAssignableUsersResource.class
)
public class WorkflowTaskAssignableUsersResourceImpl
	extends BaseWorkflowTaskAssignableUsersResourceImpl {

	@Override
	public WorkflowTaskAssignableUsers getWorkflowTaskAssignableUser(
			Long[] workflowTaskIds)
		throws Exception {

		return new WorkflowTaskAssignableUsers() {
			{
				setWorkflowTaskAssignableUsers(
					() -> {
						List<WorkflowTaskAssignableUser>
							workflowTaskAssignableUsers = new ArrayList<>();

						Set<User> commonAssignableUsers = null;

						for (Long workflowTaskId : workflowTaskIds) {
							List<User> assignableUsers =
								_workflowTaskManager.getAssignableUsers(
									contextUser.getCompanyId(), workflowTaskId);

							if (commonAssignableUsers == null) {
								commonAssignableUsers = new HashSet<>(
									assignableUsers);
							}
							else {
								commonAssignableUsers.retainAll(
									assignableUsers);
							}

							workflowTaskAssignableUsers.add(
								_createWorkflowTaskAssignableUser(
									assignableUsers, workflowTaskId));
						}

						workflowTaskAssignableUsers.add(
							_createWorkflowTaskAssignableUser(
								commonAssignableUsers,
								_COMMON_WORKFLOW_TASK_ID));

						return workflowTaskAssignableUsers.toArray(
							new WorkflowTaskAssignableUser[0]);
					});
			}
		};
	}

	private WorkflowTaskAssignableUser _createWorkflowTaskAssignableUser(
		Collection<User> assignableUsers, Long workflowTaskId) {

		WorkflowTaskAssignableUser workflowTaskAssignableUser =
			new WorkflowTaskAssignableUser();

		workflowTaskAssignableUser.setAssignableUsers(
			transformToArray(
				assignableUsers, user -> AssigneeUtil.toAssignee(_portal, user),
				Assignee.class));
		workflowTaskAssignableUser.setWorkflowTaskId(workflowTaskId);

		return workflowTaskAssignableUser;
	}

	private static final Long _COMMON_WORKFLOW_TASK_ID = 0L;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}