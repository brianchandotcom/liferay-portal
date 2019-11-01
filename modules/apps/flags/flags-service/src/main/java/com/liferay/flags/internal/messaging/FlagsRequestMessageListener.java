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

package com.liferay.flags.internal.messaging;

import com.liferay.flags.configuration.FlagsGroupServiceConfiguration;
import com.liferay.petra.content.ContentUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.config.DefaultMessagingConfigurator;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.SubscriptionSender;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "destination.name=" + DestinationNames.FLAGS,
	service = MessageListener.class
)
public class FlagsRequestMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		_defaultMessagingConfigurator = new DefaultMessagingConfigurator();

		_defaultMessagingConfigurator.setDestinationConfigurations(
			Collections.singleton(
				new DestinationConfiguration(
					DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
					DestinationNames.FLAGS)));

		_defaultMessagingConfigurator.afterPropertiesSet();
	}

	@Deactivate
	protected void deactivate() {
		_defaultMessagingConfigurator.destroy();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		FlagsRequest flagsRequest = (FlagsRequest)message.getPayload();

		// Service context

		ServiceContext serviceContext = flagsRequest.getServiceContext();

		// Company

		long companyId = serviceContext.getCompanyId();

		Company company = _companyLocalService.getCompany(
			serviceContext.getCompanyId());

		// Group

		Layout layout = _layoutLocalService.getLayout(serviceContext.getPlid());

		Group group = layout.getGroup();

		// Reporter user

		String reporterUserName = null;
		String reporterEmailAddress = null;

		User reporterUser = _userLocalService.getUserById(
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

		User reportedUser = _userLocalService.getUserById(
			flagsRequest.getReportedUserId());

		if (reportedUser.isDefaultUser()) {
			reportedUserName = group.getDescriptiveName();
		}
		else {
			reportedUserName = reportedUser.getFullName();
			reportedEmailAddress = reportedUser.getEmailAddress();
			reportedURL = reportedUser.getDisplayURL(
				serviceContext.getThemeDisplay());
		}

		// Content

		String contentType = ResourceActionsUtil.getModelResource(
			locale, flagsRequest.getClassName());

		// Reason

		String reason = LanguageUtil.get(locale, flagsRequest.getReason());

		// Email

		FlagsGroupServiceConfiguration flagsGroupServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				FlagsGroupServiceConfiguration.class, companyId);

		String fromName = flagsGroupServiceConfiguration.emailFromName();
		String fromAddress = flagsGroupServiceConfiguration.emailFromAddress();

		String subject = ContentUtil.get(
			FlagsRequestMessageListener.class.getClassLoader(),
			flagsGroupServiceConfiguration.emailSubject());
		String body = ContentUtil.get(
			FlagsRequestMessageListener.class.getClassLoader(),
			flagsGroupServiceConfiguration.emailBody());

		// Recipients

		Set<User> recipients = getRecipients(
			companyId, serviceContext.getScopeGroupId());

		for (User recipient : recipients) {
			try {
				notify(
					reporterUser.getUserId(), company, group,
					reporterEmailAddress, reporterUserName,
					reportedEmailAddress, reportedUserName, reportedURL,
					flagsRequest.getClassPK(), flagsRequest.getContentTitle(),
					contentType, flagsRequest.getContentURL(), reason, fromName,
					fromAddress, recipient.getFullName(),
					recipient.getEmailAddress(), subject, body, serviceContext);
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe, ioe);
				}
			}
		}
	}

	protected Set<User> getRecipients(long companyId, long groupId)
		throws PortalException {

		Set<User> recipients = new LinkedHashSet<>();

		List<String> roleNames = new ArrayList<>();

		Group group = _groupLocalService.getGroup(groupId);

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
			Role role = _roleLocalService.getRole(companyId, roleName);

			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
					groupId, role.getRoleId());

			for (UserGroupRole userGroupRole : userGroupRoles) {
				recipients.add(userGroupRole.getUser());
			}
		}

		if (recipients.isEmpty()) {
			Role role = _roleLocalService.getRole(
				companyId, RoleConstants.ADMINISTRATOR);

			recipients.addAll(_userLocalService.getRoleUsers(role.getRoleId()));
		}

		return recipients;
	}

	protected void notify(
			long reporterUserId, Company company, Group group,
			String reporterEmailAddress, String reporterUserName,
			String reportedEmailAddress, String reportedUserName,
			String reportedUserURL, long contentId, String contentTitle,
			String contentType, String contentURL, String reason,
			String fromName, String fromAddress, String toName,
			String toAddress, String subject, String body,
			ServiceContext serviceContext)
		throws Exception {

		Date now = new Date();

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(company.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$CONTENT_ID$]", contentId, "[$CONTENT_TITLE$]", contentTitle,
			"[$CONTENT_TYPE$]", contentType, "[$CONTENT_URL$]", contentURL,
			"[$DATE$]", now.toString(), "[$REASON$]", reason,
			"[$REPORTED_USER_ADDRESS$]", reportedEmailAddress,
			"[$REPORTED_USER_NAME$]", reportedUserName, "[$REPORTED_USER_URL$]",
			reportedUserURL, "[$REPORTER_USER_ADDRESS$]", reporterEmailAddress,
			"[$REPORTER_USER_NAME$]", reporterUserName);
		subscriptionSender.setCreatorUserId(reporterUserId);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedContextAttributeWithFunction(
			"[$SITE_NAME$]", locale -> _getGroupDescriptiveName(group, locale));
		subscriptionSender.setMailId("flags_request", contentId);
		subscriptionSender.setPortletId(PortletKeys.FLAGS);
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setSubject(subject);

		subscriptionSender.addRuntimeSubscribers(toAddress, toName);

		subscriptionSender.flushNotificationsAsync();
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserGroupRoleLocalService(
		UserGroupRoleLocalService userGroupRoleLocalService) {

		_userGroupRoleLocalService = userGroupRoleLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private String _getGroupDescriptiveName(Group group, Locale locale) {
		try {
			return group.getDescriptiveName(locale);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get descriptive name for group " +
					group.getGroupId(),
				pe);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FlagsRequestMessageListener.class);

	private CompanyLocalService _companyLocalService;
	private DefaultMessagingConfigurator _defaultMessagingConfigurator;
	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private RoleLocalService _roleLocalService;
	private UserGroupRoleLocalService _userGroupRoleLocalService;
	private UserLocalService _userLocalService;

}