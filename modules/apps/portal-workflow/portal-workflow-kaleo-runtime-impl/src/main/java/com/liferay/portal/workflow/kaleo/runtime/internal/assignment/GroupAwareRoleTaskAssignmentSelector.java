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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.KaleoTaskAssignmentFactory;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.assignment.TaskAssignmentSelector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "assignee.class.name=com.liferay.portal.kernel.model.Role",
	service = TaskAssignmentSelector.class
)
public class GroupAwareRoleTaskAssignmentSelector
	implements TaskAssignmentSelector {

	@Override
	public Collection<KaleoTaskAssignment> calculateTaskAssignments(
			KaleoTaskAssignment kaleoTaskAssignment,
			ExecutionContext executionContext)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		Role role = _roleLocalService.getRole(
			kaleoTaskAssignment.getAssigneeClassPK());

		return createKaleoTaskAssigments(kaleoInstanceToken.getGroupId(), role);
	}

	protected List<KaleoTaskAssignment> createKaleoTaskAssigments(
			long groupId, Role role)
		throws PortalException {

		List<Long> groupIds = new ArrayList<>();

		Group group = null;

		if (groupId != WorkflowConstants.DEFAULT_GROUP_ID) {
			group = _groupLocalService.getGroup(groupId);

			if (group.isOrganization()) {
				groupIds.addAll(getAncestorOrganizationGroupIds(group, role));
			}

			if (group.isSite()) {
				groupIds.addAll(getAncestorGroupIds(group, role));
			}

			if (group.isLayout()) {
				group = _groupLocalService.getGroup(group.getParentGroupId());
			}
		}

		if (isValidAssignment(group, role)) {
			groupIds.add(groupId);
		}

		return createKaleoTaskAssigments(role, groupIds);
	}

	protected List<KaleoTaskAssignment> createKaleoTaskAssigments(
			Role role, List<Long> groupIds)
		throws PortalException {

		List<KaleoTaskAssignment> kaleoTaskAssignments = new ArrayList<>();

		for (Long groupId : groupIds) {
			kaleoTaskAssignments.add(createKaleoTaskAssignment(role, groupId));
		}

		return kaleoTaskAssignments;
	}

	protected KaleoTaskAssignment createKaleoTaskAssignment(
		Role role, long groupId) {

		KaleoTaskAssignment kaleoTaskAssignment =
			_kaleoTaskAssignmentFactory.createKaleoTaskAssignment();

		kaleoTaskAssignment.setGroupId(groupId);
		kaleoTaskAssignment.setAssigneeClassName(Role.class.getName());
		kaleoTaskAssignment.setAssigneeClassPK(role.getRoleId());

		return kaleoTaskAssignment;
	}

	protected List<Long> getAncestorGroupIds(Group group, Role role)
		throws PortalException {

		List<Long> groupIds = new ArrayList<>();

		for (Group ancestorGroup : group.getAncestors()) {
			if (isValidAssignment(group, role)) {
				groupIds.add(ancestorGroup.getGroupId());
			}
		}

		return groupIds;
	}

	protected List<Long> getAncestorOrganizationGroupIds(Group group, Role role)
		throws PortalException {

		List<Long> groupIds = new ArrayList<>();

		Organization organization = _organizationLocalService.getOrganization(
			group.getOrganizationId());

		for (Organization ancestorOrganization : organization.getAncestors()) {
			if (isValidAssignment(group, role)) {
				groupIds.add(ancestorOrganization.getGroupId());
			}
		}

		return groupIds;
	}

	protected boolean isValidAssignment(Group group, Role role)
		throws PortalException {

		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return true;
		}
		else if ((group != null) && group.isOrganization() &&
				 (role.getType() == RoleConstants.TYPE_ORGANIZATION)) {

			return true;
		}
		else if ((group != null) && group.isSite() &&
				 (role.getType() == RoleConstants.TYPE_SITE)) {

			return true;
		}

		return false;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private KaleoTaskAssignmentFactory _kaleoTaskAssignmentFactory;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}