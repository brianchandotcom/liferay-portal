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

package com.liferay.subscription.internal.service;

import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageConstants;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBDiscussionLocalService;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceWrapper;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.MBGroupServiceSettings;
import com.liferay.portlet.messageboards.service.permission.MBPermission;
import com.liferay.portlet.messageboards.util.MBSubscriptionSender;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.messageboards.util.MailingListThreadLocal;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class SubscriptionMBMessageLocalServiceWrapper
	extends MBMessageLocalServiceWrapper {

	public SubscriptionMBMessageLocalServiceWrapper() {
		super(null);
	}

	public SubscriptionMBMessageLocalServiceWrapper(
		MBMessageLocalService mbMessageLocalService) {

		super(mbMessageLocalService);
	}

	@Override
	public MBMessage deleteMessage(MBMessage message) throws PortalException {
		int count = getThreadMessagesCount(
			message.getThreadId(), WorkflowConstants.STATUS_ANY);

		MBMessage deletedMessage = super.deleteMessage(message);

		if (count == 1) {
			_subscriptionLocalService.deleteSubscriptions(
				message.getCompanyId(), MBThread.class.getName(),
				message.getThreadId());
		}

		return deletedMessage;
	}

	@Override
	public void subscribeMessage(long userId, long messageId)
		throws PortalException {

		super.subscribeMessage(userId, messageId);

		MBMessage message = getMessage(messageId);

		_subscriptionLocalService.addSubscription(
			userId, message.getGroupId(), MBThread.class.getName(),
			message.getThreadId());
	}

	@Override
	public void unsubscribeMessage(long userId, long messageId)
		throws PortalException {

		super.unsubscribeMessage(userId, messageId);

		MBMessage message = getMessage(messageId);

		_subscriptionLocalService.deleteSubscription(
			userId, MBThread.class.getName(), message.getThreadId());
	}

	@Override
	public MBMessage updateStatus(
			long userId, long messageId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		MBMessage message = super.updateStatus(
			userId, messageId, status, serviceContext, workflowContext);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			notifySubscribers(
				userId, (MBMessage)message.clone(),
				(String)workflowContext.get(WorkflowConstants.CONTEXT_URL),
				serviceContext);
		}

		return message;
	}

	protected MBSubscriptionSender getSubscriptionSender(
		long userId, MBCategory category, MBMessage message, String messageURL,
		String entryTitle, boolean htmlFormat, String messageBody,
		String messageSubject, String messageSubjectPrefix, String inReplyTo,
		String fromName, String fromAddress, String replyToAddress,
		String emailAddress, String fullName,
		LocalizedValuesMap subjectLocalizedValuesMap,
		LocalizedValuesMap bodyLocalizedValuesMap,
		ServiceContext serviceContext) {

		MBSubscriptionSender subscriptionSender = new MBSubscriptionSender(
			MBPermission.RESOURCE_NAME);

		subscriptionSender.setAnonymous(message.isAnonymous());
		subscriptionSender.setBulk(PropsValues.MESSAGE_BOARDS_EMAIL_BULK);
		subscriptionSender.setClassName(message.getModelClassName());
		subscriptionSender.setClassPK(message.getMessageId());
		subscriptionSender.setCompanyId(message.getCompanyId());
		subscriptionSender.setContextAttribute(
			"[$MESSAGE_BODY$]", messageBody, false);

		long groupId = message.getGroupId();

		if (category.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			subscriptionSender.setContextAttribute(
				"[$CATEGORY_NAME$]", category.getName(), true);
		}
		else {
			subscriptionSender.setLocalizedContextAttributeWithFunction(
				"[$CATEGORY_NAME$]",
				locale -> _getLocalizedRootCategoryName(groupId, locale));
		}

		subscriptionSender.setContextAttributes(
			"[$MAILING_LIST_ADDRESS$]", replyToAddress, "[$MESSAGE_ID$]",
			message.getMessageId(), "[$MESSAGE_SUBJECT$]", messageSubject,
			"[$MESSAGE_SUBJECT_PREFIX$]", messageSubjectPrefix,
			"[$MESSAGE_URL$]", messageURL, "[$MESSAGE_USER_ADDRESS$]",
			emailAddress, "[$MESSAGE_USER_NAME$]", fullName);
		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setEntryTitle(entryTitle);
		subscriptionSender.setEntryURL(messageURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setFullName(fullName);
		subscriptionSender.setHtmlFormat(htmlFormat);
		subscriptionSender.setInReplyTo(inReplyTo);

		if (bodyLocalizedValuesMap != null) {
			subscriptionSender.setLocalizedBodyMap(
				LocalizationUtil.getMap(bodyLocalizedValuesMap));
		}

		if (subjectLocalizedValuesMap != null) {
			subscriptionSender.setLocalizedSubjectMap(
				LocalizationUtil.getMap(subjectLocalizedValuesMap));
		}

		Date modifiedDate = message.getModifiedDate();

		subscriptionSender.setMailId(
			MBUtil.MESSAGE_POP_PORTLET_PREFIX, message.getCategoryId(),
			message.getMessageId(), modifiedDate.getTime());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		String portletId = PortletProviderUtil.getPortletId(
			MBMessage.class.getName(), PortletProvider.Action.VIEW);

		subscriptionSender.setPortletId(portletId);

		subscriptionSender.setReplyToAddress(replyToAddress);
		subscriptionSender.setScopeGroupId(groupId);
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setUniqueMailId(false);

		return subscriptionSender;
	}

	protected void notifyDiscussionSubscribers(
			long userId, MBMessage message, ServiceContext serviceContext)
		throws PortalException {

		if (!PrefsPropsUtil.getBoolean(
				message.getCompanyId(),
				PropsKeys.DISCUSSION_EMAIL_COMMENTS_ADDED_ENABLED)) {

			return;
		}

		MBDiscussion mbDiscussion =
			_mbDiscussionLocalService.getThreadDiscussion(
				message.getThreadId());

		String contentURL = (String)serviceContext.getAttribute("contentURL");

		contentURL = HttpUtil.addParameter(
			contentURL, serviceContext.getAttribute("namespace") + "messageId",
			message.getMessageId());

		String userAddress = StringPool.BLANK;
		String userName = (String)serviceContext.getAttribute(
			"pingbackUserName");

		if (Validator.isNull(userName)) {
			userAddress = PortalUtil.getUserEmailAddress(message.getUserId());
			userName = PortalUtil.getUserName(
				message.getUserId(), StringPool.BLANK);
		}

		String fromName = PrefsPropsUtil.getString(
			message.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			message.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		String subject = PrefsPropsUtil.getContent(
			message.getCompanyId(), PropsKeys.DISCUSSION_EMAIL_SUBJECT);
		String body = PrefsPropsUtil.getContent(
			message.getCompanyId(), PropsKeys.DISCUSSION_EMAIL_BODY);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(message.getCompanyId());
		subscriptionSender.setClassName(MBDiscussion.class.getName());
		subscriptionSender.setClassPK(mbDiscussion.getDiscussionId());
		subscriptionSender.setContextAttribute(
			"[$COMMENTS_BODY$]", message.getBody(message.isFormatBBCode()),
			false);
		subscriptionSender.setContextAttributes(
			"[$COMMENTS_USER_ADDRESS$]", userAddress, "[$COMMENTS_USER_NAME$]",
			userName, "[$CONTENT_URL$]", contentURL);
		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setEntryTitle(message.getBody());
		subscriptionSender.setEntryURL(contentURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);

		Date modifiedDate = message.getModifiedDate();

		subscriptionSender.setMailId(
			"mb_discussion", message.getCategoryId(), message.getMessageId(),
			modifiedDate.getTime());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		String portletId = PortletProviderUtil.getPortletId(
			Comment.class.getName(), PortletProvider.Action.VIEW);

		subscriptionSender.setPortletId(portletId);

		subscriptionSender.setScopeGroupId(message.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setSubject(subject);
		subscriptionSender.setUniqueMailId(false);

		String className = (String)serviceContext.getAttribute("className");
		long classPK = ParamUtil.getLong(serviceContext, "classPK");

		subscriptionSender.addPersistedSubscribers(className, classPK);

		subscriptionSender.flushNotificationsAsync();
	}

	protected void notifySubscribers(
			long userId, MBMessage message, String messageURL,
			ServiceContext serviceContext)
		throws PortalException {

		if (!message.isApproved() || Validator.isNull(messageURL)) {
			return;
		}

		if (message.isDiscussion()) {
			try {
				notifyDiscussionSubscribers(userId, message, serviceContext);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			return;
		}

		MBGroupServiceSettings mbGroupServiceSettings =
			MBGroupServiceSettings.getInstance(message.getGroupId());

		if (serviceContext.isCommandAdd() &&
			mbGroupServiceSettings.isEmailMessageAddedEnabled()) {
		}
		else if (serviceContext.isCommandUpdate() &&
				 mbGroupServiceSettings.isEmailMessageUpdatedEnabled()) {
		}
		else {
			return;
		}

		Company company = _companyLocalService.getCompany(
			message.getCompanyId());

		User user = _userLocalService.getUser(userId);

		String emailAddress = user.getEmailAddress();
		String fullName = user.getFullName();

		if (message.isAnonymous()) {
			emailAddress = StringPool.BLANK;
			fullName = serviceContext.translate("anonymous");
		}

		MBCategory category = message.getCategory();

		List<Long> categoryIds = new ArrayList<>();

		categoryIds.add(message.getCategoryId());

		if (message.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			categoryIds.addAll(category.getAncestorCategoryIds());
		}

		String entryTitle = message.getSubject();

		String fromName = mbGroupServiceSettings.getEmailFromName();
		String fromAddress = mbGroupServiceSettings.getEmailFromAddress();

		String replyToAddress = StringPool.BLANK;

		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			replyToAddress = MBUtil.getReplyToAddress(
				message.getCategoryId(), message.getMessageId(),
				company.getMx(), fromAddress);
		}

		LocalizedValuesMap subjectLocalizedValuesMap = null;
		LocalizedValuesMap bodyLocalizedValuesMap = null;

		if (serviceContext.isCommandUpdate()) {
			subjectLocalizedValuesMap =
				mbGroupServiceSettings.getEmailMessageUpdatedSubject();
			bodyLocalizedValuesMap =
				mbGroupServiceSettings.getEmailMessageUpdatedBody();
		}
		else {
			subjectLocalizedValuesMap =
				mbGroupServiceSettings.getEmailMessageAddedSubject();
			bodyLocalizedValuesMap =
				mbGroupServiceSettings.getEmailMessageAddedBody();
		}

		boolean htmlFormat = mbGroupServiceSettings.isEmailHtmlFormat();

		String messageBody = message.getBody();

		if (htmlFormat && message.isFormatBBCode()) {
			try {
				messageBody = BBCodeTranslatorUtil.getHTML(messageBody);

				HttpServletRequest request = serviceContext.getRequest();

				if (request != null) {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)request.getAttribute(
							WebKeys.THEME_DISPLAY);

					messageBody = MBUtil.replaceMessageBodyPaths(
						themeDisplay, messageBody);
				}
			}
			catch (Exception e) {
				_log.error(
					"Unable to parse message " + message.getMessageId() + ": " +
						e.getMessage());
			}
		}

		String inReplyTo = null;
		String messageSubject = message.getSubject();
		String messageSubjectPrefix = StringPool.BLANK;

		if (message.getParentMessageId() !=
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {

			MBMessage parentMessage = _mbMessageLocalService.getMessage(
				message.getParentMessageId());

			Date modifiedDate = parentMessage.getModifiedDate();

			inReplyTo = PortalUtil.getMailId(
				company.getMx(), MBUtil.MESSAGE_POP_PORTLET_PREFIX,
				message.getCategoryId(), parentMessage.getMessageId(),
				modifiedDate.getTime());

			if (messageSubject.startsWith(
					MBMessageConstants.MESSAGE_SUBJECT_PREFIX_RE)) {

				messageSubjectPrefix =
					MBMessageConstants.MESSAGE_SUBJECT_PREFIX_RE;

				messageSubject = messageSubject.substring(
					messageSubjectPrefix.length());
			}
		}

		SubscriptionSender subscriptionSender = getSubscriptionSender(
			userId, category, message, messageURL, entryTitle, htmlFormat,
			messageBody, messageSubject, messageSubjectPrefix, inReplyTo,
			fromName, fromAddress, replyToAddress, emailAddress, fullName,
			subjectLocalizedValuesMap, bodyLocalizedValuesMap, serviceContext);

		subscriptionSender.addPersistedSubscribers(
			MBCategory.class.getName(), message.getGroupId());

		for (long categoryId : categoryIds) {
			if (categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
				subscriptionSender.addPersistedSubscribers(
					MBCategory.class.getName(), categoryId);
			}
		}

		subscriptionSender.addPersistedSubscribers(
			MBThread.class.getName(), message.getThreadId());

		subscriptionSender.flushNotificationsAsync();

		if (!MailingListThreadLocal.isSourceMailingList()) {
			for (long categoryId : categoryIds) {
				MBSubscriptionSender sourceMailingListSubscriptionSender =
					getSubscriptionSender(
						userId, category, message, messageURL, entryTitle,
						htmlFormat, messageBody, messageSubject,
						messageSubjectPrefix, inReplyTo, fromName, fromAddress,
						replyToAddress, emailAddress, fullName,
						subjectLocalizedValuesMap, bodyLocalizedValuesMap,
						serviceContext);

				sourceMailingListSubscriptionSender.setBulk(false);

				sourceMailingListSubscriptionSender.addMailingListSubscriber(
					message.getGroupId(), categoryId);

				sourceMailingListSubscriptionSender.flushNotificationsAsync();
			}
		}
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
	protected void setMbDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {

		_mbDiscussionLocalService = mbDiscussionLocalService;
	}

	@Reference(unbind = "-")
	protected void setMbMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private String _getLocalizedRootCategoryName(long groupId, Locale locale) {
		try {
			Group group = _groupLocalService.getGroup(groupId);

			return LanguageUtil.get(locale, "message-boards-home") + " - " +
				group.getDescriptiveName(locale);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get descriptive name for group " + groupId, pe);

			return LanguageUtil.get(locale, "message-boards-home");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SubscriptionMBMessageLocalServiceWrapper.class);

	private CompanyLocalService _companyLocalService;
	private GroupLocalService _groupLocalService;
	private MBDiscussionLocalService _mbDiscussionLocalService;
	private MBMessageLocalService _mbMessageLocalService;
	private SubscriptionLocalService _subscriptionLocalService;
	private UserLocalService _userLocalService;

}