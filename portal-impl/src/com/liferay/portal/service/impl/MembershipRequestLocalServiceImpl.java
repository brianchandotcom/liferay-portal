/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.MembershipRequestCommentsException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.MembershipRequestLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.ResourcePermissionUtil;
import com.liferay.portal.util.SubscriptionSender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class MembershipRequestLocalServiceImpl
	extends MembershipRequestLocalServiceBaseImpl {

	@Override
	public MembershipRequest addMembershipRequest(
			long userId, long groupId, String comments,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(comments);

		long membershipRequestId = counterLocalService.increment();

		MembershipRequest membershipRequest =
			membershipRequestPersistence.create(membershipRequestId);

		membershipRequest.setCompanyId(user.getCompanyId());
		membershipRequest.setUserId(userId);
		membershipRequest.setCreateDate(now);
		membershipRequest.setGroupId(groupId);
		membershipRequest.setComments(comments);
		membershipRequest.setStatusId(
			MembershipRequestConstants.STATUS_PENDING);

		membershipRequestPersistence.update(membershipRequest);

		notifyGroupAdministrators(membershipRequest, serviceContext);

		return membershipRequest;
	}

	@Override
	public void deleteMembershipRequests(long groupId) throws SystemException {
		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByGroupId(groupId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	@Override
	public void deleteMembershipRequests(long groupId, int statusId)
		throws SystemException {

		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByG_S(groupId, statusId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	@Override
	public void deleteMembershipRequestsByUserId(long userId)
		throws SystemException {

		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByUserId(userId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	@Override
	public List<MembershipRequest> getMembershipRequests(
			long userId, long groupId, int statusId)
		throws SystemException {

		return membershipRequestPersistence.findByG_U_S(
			groupId, userId, statusId);
	}

	@Override
	public boolean hasMembershipRequest(long userId, long groupId, int statusId)
		throws SystemException {

		List<MembershipRequest> membershipRequests = getMembershipRequests(
			userId, groupId, statusId);

		if (membershipRequests.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public List<MembershipRequest> search(
			long groupId, int status, int start, int end)
		throws SystemException {

		return membershipRequestPersistence.findByG_S(
			groupId, status, start, end);
	}

	@Override
	public int searchCount(long groupId, int status) throws SystemException {
		return membershipRequestPersistence.countByG_S(groupId, status);
	}

	@Override
	public void updateStatus(
			long replierUserId, long membershipRequestId, String replyComments,
			int statusId, boolean addUserToGroup, ServiceContext serviceContext)
		throws PortalException, SystemException {

		validate(replyComments);

		MembershipRequest membershipRequest =
			membershipRequestPersistence.findByPrimaryKey(membershipRequestId);

		membershipRequest.setReplyComments(replyComments);
		membershipRequest.setReplyDate(new Date());

		if (replierUserId != 0) {
			membershipRequest.setReplierUserId(replierUserId);
		}
		else {
			long defaultUserId = userLocalService.getDefaultUserId(
				membershipRequest.getCompanyId());

			membershipRequest.setReplierUserId(defaultUserId);
		}

		membershipRequest.setStatusId(statusId);

		membershipRequestPersistence.update(membershipRequest);

		if ((statusId == MembershipRequestConstants.STATUS_APPROVED) &&
			addUserToGroup) {

			long[] addUserIds = new long[] {membershipRequest.getUserId()};

			userLocalService.addGroupUsers(
				membershipRequest.getGroupId(), addUserIds);
		}

		if (replierUserId != 0) {
			notify(
				membershipRequest.getUserId(), membershipRequest,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REPLY_SUBJECT,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REPLY_BODY, serviceContext);
		}
	}

	protected List<Long> getGroupAdministratorUserIds(long groupId)
		throws PortalException, SystemException {

		List<Long> userIds = new UniqueList<Long>();

		Group group = groupLocalService.getGroup(groupId);
		String modelResource = Group.class.getName();

		List<Role> roles = ResourceActionsUtil.getRoles(
			group.getCompanyId(), group, modelResource, null);

		List<Team> teams = teamLocalService.getGroupTeams(groupId);

		if (teams != null) {
			for (Team team : teams) {
				Role role = roleLocalService.getTeamRole(
					team.getCompanyId(), team.getTeamId());

				roles.add(role);
			}
		}

		Resource resource = resourceLocalService.getResource(
			group.getCompanyId(), modelResource,
			ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(groupId));

		List<String> actions = ResourceActionsUtil.getResourceActions(
			Group.class.getName());

		for (Role role : roles) {
			String roleName = role.getName();

			if (roleName.equals(RoleConstants.OWNER)) {
				continue;
			}

			if ((roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				 roleName.equals(RoleConstants.ORGANIZATION_OWNER)) &&
				!group.isOrganization()) {

				continue;
			}

			if (roleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.SITE_OWNER) ||
				roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.ORGANIZATION_OWNER)) {

				Role curRole = roleLocalService.getRole(
					group.getCompanyId(), roleName);

				List<UserGroupRole> userGroupRoles =
					userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
						groupId, curRole.getRoleId());

				for (UserGroupRole userGroupRole : userGroupRoles) {
					userIds.add(userGroupRole.getUserId());
				}
			}

			List<String> currentIndividualActions = new ArrayList<String>();
			List<String> currentGroupActions = new ArrayList<String>();
			List<String> currentGroupTemplateActions = new ArrayList<String>();
			List<String> currentCompanyActions = new ArrayList<String>();

			ResourcePermissionUtil.populateResourcePermissionActionIds(
				groupId, role, resource, actions, currentIndividualActions,
				currentGroupActions, currentGroupTemplateActions,
				currentCompanyActions);

			if (currentIndividualActions.contains(ActionKeys.ASSIGN_MEMBERS) ||
				currentGroupActions.contains(ActionKeys.ASSIGN_MEMBERS) ||
				currentGroupTemplateActions.contains(
					ActionKeys.ASSIGN_MEMBERS) ||
				currentCompanyActions.contains(ActionKeys.ASSIGN_MEMBERS)) {

				List<UserGroupRole> currentUserGroupRoles =
					userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
						groupId, role.getRoleId());

				for (UserGroupRole userGroupRole : currentUserGroupRoles) {
					userIds.add(userGroupRole.getUserId());
				}
			}
		}

		return userIds;
	}

	protected void notify(
			long userId, MembershipRequest membershipRequest,
			String subjectProperty, String bodyProperty,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		User requestUser = userPersistence.findByPrimaryKey(
			membershipRequest.getUserId());

		String fromName = PrefsPropsUtil.getStringFromNames(
			membershipRequest.getCompanyId(), PropsKeys.SITES_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);

		String fromAddress = PrefsPropsUtil.getStringFromNames(
			membershipRequest.getCompanyId(),
			PropsKeys.SITES_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		String subject = PrefsPropsUtil.getContent(
			membershipRequest.getCompanyId(), subjectProperty);

		String body = PrefsPropsUtil.getContent(
			membershipRequest.getCompanyId(), bodyProperty);

		String statusKey = null;

		if (membershipRequest.getStatusId() ==
				MembershipRequestConstants.STATUS_APPROVED) {

			statusKey = "approved";
		}
		else if (membershipRequest.getStatusId() ==
					MembershipRequestConstants.STATUS_DENIED) {

			statusKey = "denied";
		}
		else {
			statusKey = "pending";
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(membershipRequest.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$COMMENTS$]", membershipRequest.getComments(),
			"[$REPLY_COMMENTS$]", membershipRequest.getReplyComments(),
			"[$REQUEST_USER_ADDRESS$]", requestUser.getEmailAddress(),
			"[$REQUEST_USER_NAME$]", requestUser.getFullName(), "[$STATUS$]",
			LanguageUtil.get(user.getLocale(), statusKey), "[$USER_ADDRESS$]",
			user.getEmailAddress(), "[$USER_NAME$]", user.getFullName());
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId(
			"membership_request", membershipRequest.getMembershipRequestId());
		subscriptionSender.setScopeGroupId(membershipRequest.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setSubject(subject);
		subscriptionSender.setUserId(userId);

		subscriptionSender.addRuntimeSubscribers(toAddress, toName);

		subscriptionSender.flushNotificationsAsync();
	}

	protected void notifyGroupAdministrators(
			MembershipRequest membershipRequest, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<Long> userIds = getGroupAdministratorUserIds(
			membershipRequest.getGroupId());

		for (Long userId : userIds) {
			notify(
				userId, membershipRequest,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REQUEST_SUBJECT,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REQUEST_BODY, serviceContext);
		}
	}

	protected void validate(String comments) throws PortalException {
		if (Validator.isNull(comments) || Validator.isNumber(comments)) {
			throw new MembershipRequestCommentsException();
		}
	}

}