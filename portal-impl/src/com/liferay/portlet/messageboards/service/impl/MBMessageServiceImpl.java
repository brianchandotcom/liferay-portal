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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.LockedThreadException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadConstants;
import com.liferay.portlet.messageboards.service.base.MBMessageServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;
import com.sun.syndication.io.FeedException;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 * @author Shuyang Zhou
 */
public class MBMessageServiceImpl extends MBMessageServiceBaseImpl {

	@Override
	public MBMessage addDiscussionMessage(
			long groupId, String className, long classPK,
			String permissionClassName, long permissionClassPK,
			long permissionOwnerId, long threadId, long parentMessageId,
			String subject, String body, ServiceContext serviceContext)
		throws PortalException {

		User user = getGuestOrUser();

		MBDiscussionPermission.check(
			getPermissionChecker(), user.getCompanyId(),
			serviceContext.getScopeGroupId(), permissionClassName,
			permissionClassPK, permissionOwnerId, ActionKeys.ADD_DISCUSSION);

		return mbMessageLocalService.addDiscussionMessage(
			user.getUserId(), null, groupId, className, classPK, threadId,
			parentMessageId, subject, body, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addMessage(long, String,
	 *             String, String, java.util.List, boolean, double, boolean,
	 *             com.liferay.portal.service.ServiceContext)}
	 */
	@Deprecated
	@Override
	public MBMessage addMessage(
			long groupId, long categoryId, long threadId, long parentMessageId,
			String subject, String body, String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		return addMessage(
			parentMessageId, subject, body, format, inputStreamOVPs, anonymous,
			priority, allowPingbacks, serviceContext);
	}

	@Override
	public MBMessage addMessage(
			long groupId, long categoryId, String subject, String body,
			String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		MBCategoryPermission.check(
			getPermissionChecker(), groupId, categoryId,
			ActionKeys.ADD_MESSAGE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), groupId, categoryId,
				ActionKeys.ADD_FILE)) {

			inputStreamOVPs = Collections.emptyList();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), groupId, categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadConstants.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), null, groupId, categoryId, subject, body,
			format, inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);
	}

	@Override
	public MBMessage addMessage(
			long categoryId, String subject, String body,
			ServiceContext serviceContext)
		throws PortalException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();

		return addMessage(
			category.getGroupId(), categoryId, subject, body,
			MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs, false, 0.0,
			false, serviceContext);
	}

	@Override
	public MBMessage addMessage(
			long parentMessageId, String subject, String body, String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		MBMessage parentMessage = mbMessagePersistence.findByPrimaryKey(
			parentMessageId);

		checkReplyToPermission(
			parentMessage.getGroupId(), parentMessage.getCategoryId(),
			parentMessageId);

		boolean preview = ParamUtil.getBoolean(serviceContext, "preview");

		int workFlowAction = serviceContext.getWorkflowAction();

		if ((workFlowAction == WorkflowConstants.STATUS_DRAFT) && !preview &&
			!serviceContext.isSignedIn()) {

			MBMessagePermission.check(
				getPermissionChecker(), parentMessageId, ActionKeys.UPDATE);
		}

		if (lockLocalService.isLocked(
				MBThread.class.getName(), parentMessage.getThreadId())) {

			throw new LockedThreadException();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), parentMessage.getGroupId(),
				parentMessage.getCategoryId(), ActionKeys.ADD_FILE)) {

			inputStreamOVPs = Collections.emptyList();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), parentMessage.getGroupId(),
				parentMessage.getCategoryId(),
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadConstants.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), null, parentMessage.getGroupId(),
			parentMessage.getCategoryId(), parentMessage.getThreadId(),
			parentMessageId, subject, body, format, inputStreamOVPs, anonymous,
			priority, allowPingbacks, serviceContext);
	}

	@Override
	public void deleteDiscussionMessage(
			long groupId, String className, long classPK,
			String permissionClassName, long permissionClassPK,
			long permissionOwnerId, long messageId)
		throws PortalException {

		User user = getUser();

		MBDiscussionPermission.check(
			getPermissionChecker(), user.getCompanyId(), groupId,
			permissionClassName, permissionClassPK, messageId,
			permissionOwnerId, ActionKeys.DELETE_DISCUSSION);

		mbMessageLocalService.deleteDiscussionMessage(messageId);
	}

	@Override
	public void deleteMessage(long messageId) throws PortalException {
		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.DELETE);

		mbMessageLocalService.deleteMessage(messageId);
	}

	@Override
	public void deleteMessageAttachments(long messageId)
		throws PortalException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.DELETE);

		mbMessageLocalService.deleteMessageAttachments(messageId);
	}

	@Override
	public List<MBMessage> getCategoryMessages(
			long groupId, long categoryId, int status, int start, int end)
		throws PortalException {

		List<MBMessage> messages = new ArrayList<MBMessage>();

		List<MBMessage> categoryMessages =
			mbMessageLocalService.getCategoryMessages(
				groupId, categoryId, status, start, end);

		for (MBMessage message : categoryMessages) {
			if (MBMessagePermission.contains(
					getPermissionChecker(), message, ActionKeys.VIEW)) {

				messages.add(message);
			}
		}

		return messages;
	}

	@Override
	public int getCategoryMessagesCount(
		long groupId, long categoryId, int status) {

		return mbMessageLocalService.getCategoryMessagesCount(
			groupId, categoryId, status);
	}

	@Override
	public String getCategoryMessagesRSS(
			long groupId, long categoryId, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		MBCategory category = mbCategoryLocalService.fetchMBCategory(
			categoryId);

		if (category == null) {
			Group group = groupLocalService.getGroup(categoryId);

			groupId = group.getGroupId();
			categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
			name = group.getDescriptiveName();
			description = group.getDescription();
		}
		else {
			groupId = category.getGroupId();
			name = category.getName();
			description = category.getDescription();
		}

		List<MBMessage> messages = new ArrayList<MBMessage>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;
		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false);

		while ((messages.size() < max) && listNotExhausted) {
			List<MBMessage> messageList =
				mbMessageLocalService.getCategoryMessages(
					groupId, categoryId, status, lastIntervalStart,
					lastIntervalStart + max, comparator);

			lastIntervalStart += max;
			listNotExhausted = (messageList.size() == max);

			for (MBMessage message : messageList) {
				if (messages.size() >= max) {
					break;
				}

				if (MBMessagePermission.contains(
						getPermissionChecker(), message, ActionKeys.VIEW)) {

					messages.add(message);
				}
			}
		}

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			messages, themeDisplay);
	}

	@Override
	public String getCompanyMessagesRSS(
			long companyId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		String name = company.getName();
		String description = company.getName();

		List<MBMessage> messages = new ArrayList<MBMessage>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;
		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false);

		while ((messages.size() < max) && listNotExhausted) {
			List<MBMessage> messageList =
				mbMessageLocalService.getCompanyMessages(
					companyId, status, lastIntervalStart,
					lastIntervalStart + max, comparator);

			lastIntervalStart += max;
			listNotExhausted = (messageList.size() == max);

			for (MBMessage message : messageList) {
				if (messages.size() >= max) {
					break;
				}

				if (MBMessagePermission.contains(
						getPermissionChecker(), message, ActionKeys.VIEW)) {

					messages.add(message);
				}
			}
		}

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			messages, themeDisplay);
	}

	@Override
	public int getGroupMessagesCount(long groupId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.filterCountByGroupId(groupId);
		}
		else {
			return mbMessagePersistence.filterCountByG_S(groupId, status);
		}
	}

	@Override
	public String getGroupMessagesRSS(
			long groupId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		List<MBMessage> messages = new ArrayList<MBMessage>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;
		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false);

		while ((messages.size() < max) && listNotExhausted) {
			List<MBMessage> messageList =
				mbMessageLocalService.getGroupMessages(
					groupId, status, lastIntervalStart, lastIntervalStart + max,
					comparator);

			lastIntervalStart += max;
			listNotExhausted = (messageList.size() == max);

			for (MBMessage message : messageList) {
				if (messages.size() >= max) {
					break;
				}

				if (MBMessagePermission.contains(
						getPermissionChecker(), message, ActionKeys.VIEW)) {

					messages.add(message);
				}
			}
		}

		if (!messages.isEmpty()) {
			MBMessage message = messages.get(messages.size() - 1);

			name = message.getSubject();
			description = message.getSubject();
		}

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			messages, themeDisplay);
	}

	@Override
	public String getGroupMessagesRSS(
			long groupId, long userId, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		List<MBMessage> messages = new ArrayList<MBMessage>();

		int lastIntervalStart = 0;
		boolean listNotExhausted = true;
		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false);

		while ((messages.size() < max) && listNotExhausted) {
			List<MBMessage> messageList =
				mbMessageLocalService.getGroupMessages(
					groupId, userId, status, lastIntervalStart,
					lastIntervalStart + max, comparator);

			lastIntervalStart += max;
			listNotExhausted = (messageList.size() == max);

			for (MBMessage message : messageList) {
				if (messages.size() >= max) {
					break;
				}

				if (MBMessagePermission.contains(
						getPermissionChecker(), message, ActionKeys.VIEW)) {

					messages.add(message);
				}
			}
		}

		if (!messages.isEmpty()) {
			MBMessage message = messages.get(messages.size() - 1);

			name = message.getSubject();
			description = message.getSubject();
		}

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			messages, themeDisplay);
	}

	@Override
	public MBMessage getMessage(long messageId) throws PortalException {
		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return mbMessageLocalService.getMessage(messageId);
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long messageId, int status, String threadView,
			boolean includePrevAndNext)
		throws PortalException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return mbMessageLocalService.getMessageDisplay(
			getGuestOrUserId(), messageId, status, threadView,
			includePrevAndNext);
	}

	@Override
	public int getThreadAnswersCount(
		long groupId, long categoryId, long threadId) {

		return mbMessagePersistence.filterCountByG_C_T_A(
			groupId, categoryId, threadId, true);
	}

	@Override
	public List<MBMessage> getThreadMessages(
		long groupId, long categoryId, long threadId, int status, int start,
		int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.filterFindByG_C_T(
				groupId, categoryId, threadId, start, end);
		}
		else {
			return mbMessagePersistence.filterFindByG_C_T_S(
				groupId, categoryId, threadId, status, start, end);
		}
	}

	@Override
	public int getThreadMessagesCount(
		long groupId, long categoryId, long threadId, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.filterCountByG_C_T(
				groupId, categoryId, threadId);
		}
		else {
			return mbMessagePersistence.filterCountByG_C_T_S(
				groupId, categoryId, threadId, status);
		}
	}

	@Override
	public String getThreadMessagesRSS(
			long threadId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		List<MBMessage> messages = new ArrayList<MBMessage>();

		MBThread thread = mbThreadLocalService.getThread(threadId);

		if (MBMessagePermission.contains(
				getPermissionChecker(), thread.getRootMessageId(),
				ActionKeys.VIEW)) {

			MessageCreateDateComparator comparator =
				new MessageCreateDateComparator(false);

			List<MBMessage> threadMessages =
				mbMessageLocalService.getThreadMessages(
					threadId, status, comparator);

			for (MBMessage message : threadMessages) {
				if (messages.size() >= max) {
					break;
				}

				if (MBMessagePermission.contains(
						getPermissionChecker(), message, ActionKeys.VIEW)) {

					messages.add(message);
				}
			}

			if (!messages.isEmpty()) {
				MBMessage message = messages.get(messages.size() - 1);

				name = message.getSubject();
				description = message.getSubject();
			}
		}

		return exportToRSS(
			name, description, type, version, displayStyle, feedURL, entryURL,
			messages, themeDisplay);
	}

	@Override
	public void restoreMessageAttachmentFromTrash(
			long messageId, String fileName)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		MBCategoryPermission.check(
			getPermissionChecker(), message.getGroupId(),
			message.getCategoryId(), ActionKeys.ADD_FILE);

		mbMessageLocalService.restoreMessageAttachmentFromTrash(
			getUserId(), messageId, fileName);
	}

	@Override
	public void subscribeMessage(long messageId) throws PortalException {
		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		mbMessageLocalService.subscribeMessage(getUserId(), messageId);
	}

	@Override
	public void unsubscribeMessage(long messageId) throws PortalException {
		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		mbMessageLocalService.unsubscribeMessage(getUserId(), messageId);
	}

	@Override
	public void updateAnswer(long messageId, boolean answer, boolean cascade)
		throws PortalException {

		mbMessageLocalService.updateAnswer(messageId, answer, cascade);
	}

	@Override
	public MBMessage updateDiscussionMessage(
			String className, long classPK, String permissionClassName,
			long permissionClassPK, long permissionOwnerId, long messageId,
			String subject, String body, ServiceContext serviceContext)
		throws PortalException {

		User user = getUser();

		MBDiscussionPermission.check(
			getPermissionChecker(), user.getCompanyId(),
			serviceContext.getScopeGroupId(), permissionClassName,
			permissionClassPK, messageId, permissionOwnerId,
			ActionKeys.UPDATE_DISCUSSION);

		return mbMessageLocalService.updateDiscussionMessage(
			getUserId(), messageId, className, classPK, subject, body,
			serviceContext);
	}

	@Override
	public MBMessage updateMessage(
			long messageId, String subject, String body,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			List<String> existingFiles, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		boolean preview = ParamUtil.getBoolean(serviceContext, "preview");

		if (preview &&
			MBMessagePermission.contains(
				getPermissionChecker(), message, ActionKeys.UPDATE)) {

			checkReplyToPermission(
				message.getGroupId(), message.getCategoryId(),
				message.getParentMessageId());
		}
		else {
			MBMessagePermission.check(
				getPermissionChecker(), messageId, ActionKeys.UPDATE);
		}

		if (lockLocalService.isLocked(
				MBThread.class.getName(), message.getThreadId())) {

			throw new LockedThreadException();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), message.getGroupId(),
				message.getCategoryId(), ActionKeys.ADD_FILE)) {

			inputStreamOVPs = Collections.emptyList();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), message.getGroupId(),
				message.getCategoryId(), ActionKeys.UPDATE_THREAD_PRIORITY)) {

			MBThread thread = mbThreadLocalService.getThread(
				message.getThreadId());

			priority = thread.getPriority();
		}

		return mbMessageLocalService.updateMessage(
			getGuestOrUserId(), messageId, subject, body, inputStreamOVPs,
			existingFiles, priority, allowPingbacks, serviceContext);
	}

	protected void checkReplyToPermission(
			long groupId, long categoryId, long parentMessageId)
		throws PortalException {

		if (parentMessageId > 0) {
			if (MBCategoryPermission.contains(
					getPermissionChecker(), groupId, categoryId,
					ActionKeys.ADD_MESSAGE)) {

				return;
			}

			if (!MBCategoryPermission.contains(
					getPermissionChecker(), groupId, categoryId,
					ActionKeys.REPLY_TO_MESSAGE)) {

				throw new PrincipalException();
			}
		}
		else {
			MBCategoryPermission.check(
				getPermissionChecker(), groupId, categoryId,
				ActionKeys.ADD_MESSAGE);
		}
	}

	protected String exportToRSS(
		String name, String description, String type, double version,
		String displayStyle, String feedURL, String entryURL,
		List<MBMessage> messages, ThemeDisplay themeDisplay) {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(description);

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(syndEntries);

		for (MBMessage message : messages) {
			SyndEntry syndEntry = new SyndEntryImpl();

			if (!message.isAnonymous()) {
				String author = PortalUtil.getUserName(message);

				syndEntry.setAuthor(author);
			}

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			String value = null;

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT)) {
				value = StringUtil.shorten(
					HtmlUtil.extractText(message.getBody()),
					PropsValues.MESSAGE_BOARDS_RSS_ABSTRACT_LENGTH,
					StringPool.BLANK);
			}
			else if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else if (message.isFormatBBCode()) {
				value = BBCodeTranslatorUtil.getHTML(message.getBody());

				value = MBUtil.replaceMessageBodyPaths(themeDisplay, value);
			}
			else {
				value = message.getBody();
			}

			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			syndEntry.setLink(
				entryURL + "&messageId=" + message.getMessageId());
			syndEntry.setPublishedDate(message.getCreateDate());
			syndEntry.setTitle(message.getSubject());
			syndEntry.setUpdatedDate(message.getModifiedDate());
			syndEntry.setUri(syndEntry.getLink());

			syndEntries.add(syndEntry);
		}

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));

		List<SyndLink> syndLinks = new ArrayList<SyndLink>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = new SyndLinkImpl();

		syndLinks.add(selfSyndLink);

		selfSyndLink.setHref(feedURL);
		selfSyndLink.setRel("self");

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(name);
		syndFeed.setUri(feedURL);

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
	}

}