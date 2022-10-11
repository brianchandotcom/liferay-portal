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

package com.liferay.portal.workflow.kaleo.runtime.internal.notification.recipient;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.NotificationRecipientBuilder;
import com.liferay.portal.workflow.kaleo.runtime.util.validator.GroupAwareRoleValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "recipient.type=ROLE",
	service = {
		NotificationRecipientBuilder.class,
		RoleNotificationRecipientBuilder.class
	}
)
public class RoleNotificationRecipientBuilder
	implements NotificationRecipientBuilder {

	@Override
	public void processKaleoNotificationRecipient(
			Set<NotificationRecipient> notificationRecipients,
			KaleoNotificationRecipient kaleoNotificationRecipient,
			NotificationReceptionType notificationReceptionType,
			ExecutionContext executionContext)
		throws Exception {

		long roleId = kaleoNotificationRecipient.getRecipientClassPK();

		addRoleRecipientAddresses(
			notificationRecipients, _roleLocalService.getRole(roleId),
			notificationReceptionType, executionContext);
	}

	@Override
	public void processKaleoTaskAssignmentInstance(
			Set<NotificationRecipient> notificationRecipients,
			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
			NotificationReceptionType notificationReceptionType,
			ExecutionContext executionContext)
		throws Exception {

		long roleId = kaleoTaskAssignmentInstance.getAssigneeClassPK();

		addRoleRecipientAddresses(
			notificationRecipients, _roleLocalService.getRole(roleId),
			notificationReceptionType, executionContext);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, GroupAwareRoleValidator.class);
	}

	protected void addRoleRecipientAddresses(
			Set<NotificationRecipient> notificationRecipients, Role role,
			NotificationReceptionType notificationReceptionType,
			ExecutionContext executionContext)
		throws Exception {

		List<User> users = _getRoleUsers(role, executionContext);

		for (User user : users) {
			if (user.isActive()) {
				NotificationRecipient notificationRecipient =
					new NotificationRecipient(user, notificationReceptionType);

				notificationRecipients.add(notificationRecipient);
			}
		}
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private List<Long> _getAncestorGroupIds(Group group, Role role)
		throws Exception {

		List<Long> groupIds = new ArrayList<>();

		for (Group ancestorGroup : group.getAncestors()) {
			if (_isValidGroup(group, role)) {
				groupIds.add(ancestorGroup.getGroupId());
			}
		}

		return groupIds;
	}

	private List<Long> _getAncestorOrganizationGroupIds(Group group, Role role)
		throws Exception {

		List<Long> groupIds = new ArrayList<>();

		Organization organization = _organizationLocalService.getOrganization(
			group.getOrganizationId());

		for (Organization ancestorOrganization : organization.getAncestors()) {
			if (_isValidGroup(group, role)) {
				groupIds.add(ancestorOrganization.getGroupId());
			}
		}

		return groupIds;
	}

	private List<Long> _getGroupIds(long groupId, Role role) throws Exception {
		List<Long> groupIds = new ArrayList<>();

		if (groupId != WorkflowConstants.DEFAULT_GROUP_ID) {
			Group group = _groupLocalService.getGroup(groupId);

			if (group.isOrganization()) {
				groupIds.addAll(_getAncestorOrganizationGroupIds(group, role));
			}

			if (group.isSite()) {
				groupIds.addAll(_getAncestorGroupIds(group, role));
			}

			if (_isValidGroup(group, role)) {
				groupIds.add(groupId);
			}
		}

		return groupIds;
	}

	private List<User> _getRoleUsers(
			Role role, ExecutionContext executionContext)
		throws Exception {

		long roleId = role.getRoleId();

		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return _userLocalService.getInheritedRoleUsers(
				roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		KaleoInstanceToken kaleoInstanceToken =
			executionContext.getKaleoInstanceToken();

		List<Long> groupIds = _getGroupIds(
			kaleoInstanceToken.getGroupId(), role);

		List<User> users = new ArrayList<>();

		for (Long groupId : groupIds) {
			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
					groupId, roleId);

			for (UserGroupRole userGroupRole : userGroupRoles) {
				users.add(userGroupRole.getUser());
			}

			List<UserGroupGroupRole> userGroupGroupRoles =
				_userGroupGroupRoleLocalService.
					getUserGroupGroupRolesByGroupAndRole(groupId, roleId);

			for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
				users.addAll(
					_userLocalService.getUserGroupUsers(
						userGroupGroupRole.getUserGroupId()));
			}

			if (Objects.equals(
					role.getName(), DepotRolesConstants.ASSET_LIBRARY_MEMBER) ||
				Objects.equals(role.getName(), RoleConstants.SITE_MEMBER)) {

				users.addAll(
					_userLocalService.getGroupUsers(
						groupId, WorkflowConstants.STATUS_APPROVED, null));
			}

			if (Objects.equals(
					role.getName(), RoleConstants.ORGANIZATION_USER)) {

				Group group = _groupLocalService.getGroup(groupId);

				if (group.isOrganization()) {
					long organizationId = group.getClassPK();

					users.addAll(
						_userLocalService.getOrganizationUsers(organizationId));
				}
			}
		}

		return users;
	}

	private boolean _isValidGroup(Group group, Role role) throws Exception {
		if ((group != null) && group.isDepot() &&
			(role.getType() == RoleConstants.TYPE_DEPOT)) {

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

		for (GroupAwareRoleValidator groupAwareRoleValidator :
				_serviceTrackerList) {

			if (groupAwareRoleValidator.isValidGroup(group, role)) {
				return true;
			}
		}

		return false;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	private ServiceTrackerList<GroupAwareRoleValidator> _serviceTrackerList;

	@Reference
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}