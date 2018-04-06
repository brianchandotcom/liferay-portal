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

package com.liferay.portal.workflow.kaleo.runtime.internal.assignment;

import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.BaseTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelector;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Eduardo Lundgren
 */
@Component(
	immediate = true,
	property = "assignee.class.name=com.liferay.portal.kernel.model.ResourceAction",
	service = TaskAssignmentSelector.class
)
public class ResourceActionTaskAssignmentSelector
	extends BaseTaskAssignmentSelector {

	@Override
	public Collection<KaleoTaskAssignment> calculateTaskAssignments(
		KaleoTaskAssignment kaleoTaskAssignment,
		ExecutionContext executionContext) {

		ServiceContext serviceContext = executionContext.getServiceContext();

		Map<String, Serializable> workflowContext =
			executionContext.getWorkflowContext();

		ServiceContext workflowContextServiceContext =
			(ServiceContext)workflowContext.get(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT);

		String companyId = GetterUtil.getString(
			(String)workflowContextServiceContext.getAttribute("companyId"),
			(String)workflowContext.get("companyId"));

		String groupId = GetterUtil.getString(
			(String)workflowContextServiceContext.getAttribute("groupId"),
			(String)workflowContext.get("groupId"));

		String resourceName = GetterUtil.getString(
			(String)workflowContextServiceContext.getAttribute(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME),
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));

		String resourceClassPK = GetterUtil.getString(
			(String)workflowContextServiceContext.getAttribute(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK),
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		List<Role> roles = new ArrayList<>();

		roles.addAll(
			_roleLocalService.getResourceRoles(
				serviceContext.getCompanyId(), resourceName,
				ResourceConstants.SCOPE_COMPANY, companyId,
				kaleoTaskAssignment.getAssigneeActionId()));

		roles.addAll(
			_roleLocalService.getResourceRoles(
				serviceContext.getCompanyId(), resourceName,
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				kaleoTaskAssignment.getAssigneeActionId()));

		roles.addAll(
			_roleLocalService.getResourceRoles(
				serviceContext.getCompanyId(), resourceName,
				ResourceConstants.SCOPE_GROUP, groupId,
				kaleoTaskAssignment.getAssigneeActionId()));

		roles.addAll(
			_roleLocalService.getResourceRoles(
				serviceContext.getCompanyId(), resourceName,
				ResourceConstants.SCOPE_INDIVIDUAL, resourceClassPK,
				kaleoTaskAssignment.getAssigneeActionId()));

		List<KaleoTaskAssignment> kaleoTaskAssignments = new ArrayList<>(
			roles.size());

		getRoleKaleoTaskAssignments(roles, kaleoTaskAssignments);

		return kaleoTaskAssignments;
	}

	@Reference
	private RoleLocalService _roleLocalService;

}