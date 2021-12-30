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

package com.liferay.portal.workflow.task.web.internal.permission;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Brandizzi
 */
public class WorkflowTaskPermissionChecker {

	public void check(
			long groupId, WorkflowTask workflowTask,
			PermissionChecker permissionChecker)
		throws PortalException {

		if (!hasPermission(groupId, workflowTask, permissionChecker)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, WorkflowTask.class.getName(),
				workflowTask.getWorkflowTaskId(), ActionKeys.VIEW);
		}
	}

	public boolean hasPermission(
		long groupId, WorkflowTask workflowTask,
		PermissionChecker permissionChecker) {

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin()) {

			return true;
		}

		int userNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(
					permissionChecker.getUserId(), PortletKeys.MY_WORKFLOW_TASK,
					HashMapBuilder.put(
						"workflowInstanceId",
						String.valueOf(workflowTask.getWorkflowInstanceId())
					).put(
						"workflowTaskId",
						String.valueOf(workflowTask.getWorkflowTaskId())
					).build());

		if (hasAssetViewPermission(workflowTask, permissionChecker) &&
			((userNotificationEventsCount > 0) ||
			 (workflowTask.isCompleted() &&
			  (workflowTask.getAssigneeUserId() ==
				  permissionChecker.getUserId())))) {

			return true;
		}

		long[] roleIds = getRoleIds(groupId, permissionChecker);

		for (WorkflowTaskAssignee workflowTaskAssignee :
				workflowTask.getWorkflowTaskAssignees()) {

			if (_isWorkflowTaskAssignableToRoles(
					workflowTaskAssignee, roleIds) ||
				_isWorkflowTaskAssignableToUser(
					workflowTaskAssignee, permissionChecker.getUserId())) {

				return true;
			}
		}

		if (!hasAssetViewPermission(workflowTask, permissionChecker) &&
			!permissionChecker.isContentReviewer(
				permissionChecker.getCompanyId(), groupId)) {

			return false;
		}

		return false;
	}

	protected long[] getRoleIds(
		long groupId, PermissionChecker permissionChecker) {

		long[] roleIds = permissionChecker.getRoleIds(
			permissionChecker.getUserId(), groupId);

		try {
			List<Group> groups = new ArrayList<>();

			if (groupId != WorkflowConstants.DEFAULT_GROUP_ID) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				if (group.isOrganization()) {
					groups.addAll(_getAncestorOrganizationGroups(group));
				}

				if (group.isSite()) {
					groups.addAll(_getAncestorGroups(group));
				}
			}

			for (Group group : groups) {
				long[] roleIdArray = permissionChecker.getRoleIds(
					permissionChecker.getUserId(), group.getGroupId());

				roleIds = ArrayUtil.append(roleIds, roleIdArray);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return roleIds;
	}

	protected boolean hasAssetViewPermission(
		WorkflowTask workflowTask, PermissionChecker permissionChecker) {

		Map<String, Serializable> optionalAttributes =
			workflowTask.getOptionalAttributes();

		String className = MapUtil.getString(
			optionalAttributes, WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		if (workflowHandler == null) {
			return false;
		}

		long classPK = MapUtil.getLong(
			optionalAttributes, WorkflowConstants.CONTEXT_ENTRY_CLASS_PK);

		try {
			AssetRenderer<?> assetRenderer = workflowHandler.getAssetRenderer(
				classPK);

			if (assetRenderer == null) {
				return false;
			}

			return assetRenderer.hasViewPermission(permissionChecker);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return false;
	}

	private List<Group> _getAncestorGroups(Group group) throws PortalException {
		List<Group> groups = new ArrayList<>();

		for (Group ancestorGroup : group.getAncestors()) {
			groups.add(ancestorGroup);
		}

		return groups;
	}

	private List<Group> _getAncestorOrganizationGroups(Group group)
		throws PortalException {

		List<Group> groups = new ArrayList<>();

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(
				group.getOrganizationId());

		for (Organization ancestorOrganization : organization.getAncestors()) {
			groups.add(ancestorOrganization.getGroup());
		}

		return groups;
	}

	private boolean _isWorkflowTaskAssignableToRoles(
		WorkflowTaskAssignee workflowTaskAssignee, long[] roleIds) {

		String assigneeClassName = workflowTaskAssignee.getAssigneeClassName();

		if (!assigneeClassName.equals(Role.class.getName())) {
			return false;
		}

		if (ArrayUtil.contains(
				roleIds, workflowTaskAssignee.getAssigneeClassPK())) {

			return true;
		}

		return false;
	}

	private boolean _isWorkflowTaskAssignableToUser(
		WorkflowTaskAssignee workflowTaskAssignee, long userId) {

		String assigneeClassName = workflowTaskAssignee.getAssigneeClassName();

		if (!assigneeClassName.equals(User.class.getName())) {
			return false;
		}

		if (workflowTaskAssignee.getAssigneeClassPK() == userId) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowTaskPermissionChecker.class);

}