/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.MembershipRequestLocalServiceBaseImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.util.UniqueList;

import java.util.Date;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class MembershipRequestLocalServiceImpl
	extends MembershipRequestLocalServiceBaseImpl {

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

		membershipRequestPersistence.update(membershipRequest, false);

		notifyGroupAdministrators(membershipRequest, serviceContext);

		return membershipRequest;
	}

	public void deleteMembershipRequests(long groupId) throws SystemException {
		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByGroupId(groupId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	public void deleteMembershipRequests(long groupId, int statusId)
		throws SystemException {

		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByG_S(groupId, statusId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	public void deleteMembershipRequestsByUserId(long userId)
		throws SystemException {

		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByUserId(userId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	public List<MembershipRequest> getMembershipRequests(
			long userId, long groupId, int statusId)
		throws SystemException {

		return membershipRequestPersistence.findByG_U_S(
			groupId, userId, statusId);
	}

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

	public List<MembershipRequest> search(
			long groupId, int status, int start, int end)
		throws SystemException {

		return membershipRequestPersistence.findByG_S(
			groupId, status, start, end);
	}

	public int searchCount(long groupId, int status) throws SystemException {
		return membershipRequestPersistence.countByG_S(groupId, status);
	}

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

		membershipRequestPersistence.update(membershipRequest, false);

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

		Role siteAdministratorRole = roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		List<UserGroupRole> siteAdministratorUserGroupRoles =
			userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
				groupId, siteAdministratorRole.getRoleId());

		for (UserGroupRole userGroupRole : siteAdministratorUserGroupRoles) {
			userIds.add(userGroupRole.getUserId());
		}

		Role siteOwnerRole = rolePersistence.findByC_N(
			group.getCompanyId(), RoleConstants.SITE_OWNER);

		List<UserGroupRole> siteOwnerUserGroupRoles =
			userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
				groupId, siteOwnerRole.getRoleId());

		for (UserGroupRole userGroupRole : siteOwnerUserGroupRoles) {
			userIds.add(userGroupRole.getUserId());
		}

		if (!group.isOrganization()) {
			return userIds;
		}

		Role organizationAdministratorRole = roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.ORGANIZATION_ADMINISTRATOR);

		List<UserGroupRole> organizationAdminstratorUserGroupRoles =
			userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
				groupId, organizationAdministratorRole.getRoleId());

		for (UserGroupRole orgAdministratorUserGroupRole :
				organizationAdminstratorUserGroupRoles) {

			userIds.add(orgAdministratorUserGroupRole.getUserId());
		}

		Role orgOwnerRole = roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);

		List<UserGroupRole> organizationOwnerUserGroupRoles =
			userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
				groupId, orgOwnerRole.getRoleId());

		for (UserGroupRole organizationOwnerUserGroupRole :
				organizationOwnerUserGroupRoles) {

			userIds.add(organizationOwnerUserGroupRole.getUserId());
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