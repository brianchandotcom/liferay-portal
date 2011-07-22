/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.flags.messaging;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.util.UniqueList;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.InternetAddress;

/**
 * @author Julio Camarero
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class FlagsRequestMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		FlagsRequest flagsRequest = (FlagsRequest)message.getPayload();

		// Service context

		ServiceContext serviceContext = flagsRequest.getServiceContext();

		// Company

		long companyId = serviceContext.getCompanyId();

		Company company = CompanyLocalServiceUtil.getCompany(
			serviceContext.getCompanyId());

		// Group

		Layout layout = LayoutLocalServiceUtil.getLayout(
			serviceContext.getPlid());

		Group group = layout.getGroup();

		String groupName = HtmlUtil.escape(group.getDescriptiveName());

		// Reporter user

		String reporterUserName = null;
		String reporterEmailAddress = null;

		User reporterUser = UserLocalServiceUtil.getUserById(
			serviceContext.getUserId());

		Locale locale = LocaleUtil.getDefault();

		if (reporterUser.isDefaultUser()) {
			reporterUserName = LanguageUtil.get(locale, "anonymous");
		}
		else {
			reporterUserName = reporterUser.getFullName();
			reporterEmailAddress = reporterUser.getEmailAddress();
		}

		// Reported user

		String reportedUserName = StringPool.BLANK;
		String reportedEmailAddress = StringPool.BLANK;
		String reportedURL = StringPool.BLANK;

		User reportedUser = UserLocalServiceUtil.getUserById(
			flagsRequest.getReportedUserId());

		if (reportedUser.isDefaultUser()) {
			reportedUserName = HtmlUtil.escape(group.getDescriptiveName());
		}
		else {
			reportedUserName = HtmlUtil.escape(reportedUser.getFullName());
			reportedEmailAddress = reportedUser.getEmailAddress();
			reportedURL = reportedUser.getDisplayURL(
				serviceContext.getPortalURL(), serviceContext.getPathMain());
		}

		// Content

		String contentType = ResourceActionsUtil.getModelResource(
			locale, flagsRequest.getClassName());

		// Reason

		String reason = LanguageUtil.get(locale, flagsRequest.getReason());

		// Email

		String fromName = PrefsPropsUtil.getString(
			companyId, PropsKeys.FLAGS_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			companyId, PropsKeys.FLAGS_EMAIL_FROM_ADDRESS);
		String subject = PrefsPropsUtil.getContent(
			companyId, PropsKeys.FLAGS_EMAIL_SUBJECT);
		String body = PrefsPropsUtil.getContent(
			companyId, PropsKeys.FLAGS_EMAIL_BODY);

		// Recipients

		List<User> recipients = getRecipients(
			companyId, serviceContext.getScopeGroupId());

		for (User recipient : recipients) {
			try {
				notify(
					company, groupName, reporterEmailAddress, reporterUserName,
					reportedEmailAddress, reportedUserName, reportedURL,
					flagsRequest.getClassPK(), flagsRequest.getContentTitle(),
					contentType, flagsRequest.getContentURL(), reason,
					fromName, fromAddress, recipient.getFullName(),
					recipient.getEmailAddress(), subject, body, serviceContext);
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe);
				}
			}
		}
	}

	protected List<User> getRecipients(long companyId, long groupId)
		throws PortalException, SystemException {

		List<User> recipients = new UniqueList<User>();

		List<String> roleNames = new ArrayList<String>();

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isSite()) {
			roleNames.add(RoleConstants.SITE_ADMINISTRATOR);
			roleNames.add(RoleConstants.SITE_OWNER);
		}

		if (group.isCompany()) {
			roleNames.add(RoleConstants.ADMINISTRATOR);
		}
		else if (group.isOrganization()) {
			roleNames.add(RoleConstants.ORGANIZATION_ADMINISTRATOR);
			roleNames.add(RoleConstants.ORGANIZATION_OWNER);
		}

		for (String roleName : roleNames) {
			Role role = RoleLocalServiceUtil.getRole(companyId, roleName);

			List<UserGroupRole> userGroupRoles =
				UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(
					groupId, role.getRoleId());

			for (UserGroupRole userGroupRole : userGroupRoles) {
				recipients.add(userGroupRole.getUser());
			}
		}

		if (recipients.isEmpty()) {
			Role role = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.ADMINISTRATOR);

			recipients.addAll(
				UserLocalServiceUtil.getRoleUsers(role.getRoleId()));
		}

		return recipients;
	}

	protected void notify(
			Company company, String groupName, String reporterEmailAddress,
			String reporterUserName, String reportedEmailAddress,
			String reportedUserName, String reportedUserURL, long contentId,
			String contentTitle, String contentType, String contentURL,
			String reason, String fromName, String fromAddress, String toName,
			String toAddress, String subject, String body,
			ServiceContext serviceContext)
		throws Exception {

		Date now = new Date();

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$SITE_NAME$]",
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$CONTENT_ID$]",
				"[$CONTENT_TITLE$]",
				"[$CONTENT_TYPE$]",
				"[$CONTENT_URL$]",
				"[$DATE$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$REASON$]",
				"[$REPORTED_USER_ADDRESS$]",
				"[$REPORTED_USER_NAME$]",
				"[$REPORTED_USER_URL$]",
				"[$REPORTER_USER_ADDRESS$]",
				"[$REPORTER_USER_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				groupName,
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				String.valueOf(contentId),
				contentTitle,
				contentType,
				contentURL,
				now.toString(),
				fromAddress,
				fromName,
				serviceContext.getPortalURL(),
				reason,
				reportedEmailAddress,
				reportedUserName,
				reportedUserURL,
				reporterEmailAddress,
				reporterUserName,
				toAddress,
				toName
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$SITE_NAME$]",
				"[$COMPANY_ID$]",
				"[$COMPANY_MX$]",
				"[$COMPANY_NAME$]",
				"[$CONTENT_ID$]",
				"[$CONTENT_TITLE$]",
				"[$CONTENT_TYPE$]",
				"[$CONTENT_URL$]",
				"[$DATE$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$REASON$]",
				"[$REPORTED_USER_ADDRESS$]",
				"[$REPORTED_USER_NAME$]",
				"[$REPORTED_USER_URL$]",
				"[$REPORTER_USER_ADDRESS$]",
				"[$REPORTER_USER_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				groupName,
				String.valueOf(company.getCompanyId()),
				company.getMx(),
				company.getName(),
				String.valueOf(contentId),
				contentTitle,
				contentType,
				contentURL,
				now.toString(),
				fromAddress,
				fromName,
				serviceContext.getPortalURL(),
				reason,
				reportedEmailAddress,
				reportedUserName,
				reportedUserURL,
				reporterEmailAddress,
				reporterUserName,
				toAddress,
				toName
			});

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		MailMessage message = new MailMessage(from, to, subject, body, true);

		MailServiceUtil.sendEmail(message);
	}

	private static Log _log = LogFactoryUtil.getLog(
		FlagsRequestMessageListener.class);

}