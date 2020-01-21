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
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.AssigneeUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.AssigneeResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/assignee.properties",
	scope = ServiceScope.PROTOTYPE, service = AssigneeResource.class
)
public class AssigneeResourceImpl extends BaseAssigneeResourceImpl {

	@Override
	public Page<Assignee> getWorkflowTaskAssignableUsersPage(
			Long workflowTaskId, Pagination pagination)
		throws Exception {

		List<User> assignableUsers = _workflowTaskManager.getAssignableUsers(
			contextCompany.getCompanyId(), workflowTaskId);

		return Page.of(
			transform(
				ListUtil.subList(
					assignableUsers, pagination.getStartPosition(),
					pagination.getEndPosition()),
				assignableUser -> AssigneeUtil.toAssignee(
					_portal, assignableUser)),
			pagination, assignableUsers.size());
	}

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}