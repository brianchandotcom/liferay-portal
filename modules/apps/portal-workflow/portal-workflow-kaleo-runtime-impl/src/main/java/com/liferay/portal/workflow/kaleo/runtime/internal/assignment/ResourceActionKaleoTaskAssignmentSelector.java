/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.workflow.kaleo.runtime.assignment.BaseKaleoTaskAssignmentSelector;
import com.liferay.portal.workflow.kaleo.runtime.assignment.KaleoTaskAssignmentSelector;

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
	property = "assignee.class.name=com.liferay.portal.kernel.model.ResourceAction",
	service = KaleoTaskAssignmentSelector.class
)
public class ResourceActionKaleoTaskAssignmentSelector
	extends BaseKaleoTaskAssignmentSelector {

	@Override
	public Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
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