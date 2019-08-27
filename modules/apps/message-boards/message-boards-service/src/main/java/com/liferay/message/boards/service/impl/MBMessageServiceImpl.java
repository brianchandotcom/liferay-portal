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

package com.liferay.message.boards.service.impl;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.constants.MBThreadConstants;
import com.liferay.message.boards.exception.LockedThreadException;
import com.liferay.message.boards.internal.util.MBUtil;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBMessageDisplay;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.service.base.MBMessageServiceBaseImpl;
import com.liferay.message.boards.service.permission.MBDiscussionPermission;
import com.liferay.message.boards.util.comparator.MessageCreateDateComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.rss.export.RSSExporter;
import com.liferay.rss.model.SyndContent;
import com.liferay.rss.model.SyndEntry;
import com.liferay.rss.model.SyndFeed;
import com.liferay.rss.model.SyndLink;
import com.liferay.rss.model.SyndModelFactory;
import com.liferay.rss.util.RSSUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 * @author Shuyang Zhou
 */
@Component(
	property = {
		"json.web.service.context.name=mb",
		"json.web.service.context.path=MBMessage"
	},
	service = AopService.class
)
public class MBMessageServiceImpl extends MBMessageServiceBaseImpl {

	@Override
	public MBMessage addDiscussionMessage(
			long groupId, String className, long classPK, long threadId,
			long parentMessageId, String subject, String body,
			ServiceContext serviceContext)
		throws PortalException {

		User user = getGuestOrUser();

		MBDiscussionPermission.check(
			getPermissionChecker(), user.getCompanyId(),
			serviceContext.getScopeGroupId(), className, classPK,
			ActionKeys.ADD_DISCUSSION);

		return mbMessageLocalService.addDiscussionMessage(
			user.getUserId(), null, groupId, className, classPK, threadId,
			parentMessageId, subject, body, serviceContext);
	}

	@Override
	public MBMessage addMessage(
			long groupId, long categoryId, String subject, String body,
			String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(), groupId,
			categoryId, ActionKeys.ADD_MESSAGE);

		if (!ModelResourcePermissionHelper.contains(
				_categoryModelResourcePermission, getPermissionChecker(),
				groupId, categoryId, ActionKeys.ADD_FILE)) {

			inputStreamOVPs = Collections.emptyList();
		}

		if (!ModelResourcePermissionHelper.contains(
				_categoryModelResourcePermission, getPermissionChecker(),
				groupId, categoryId, ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadConstants.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), null, groupId, categoryId, subject, body,
			format, inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);
	}

	@Override
	public MBMessage addMessage(
			long groupId, long categoryId, String subject, String body,
			String format, String fileName, File file, boolean anonymous,
			double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws FileNotFoundException, PortalException {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<>();

		InputStream inputStream = new FileInputStream(file);

		ObjectValuePair<String, InputStream> inputStreamOVP =
			new ObjectValuePair<>(fileName, inputStream);

		inputStreamOVPs.add(inputStreamOVP);

		return addMessage(
			groupId, categoryId, subject, body, format, inputStreamOVPs,
			anonymous, priority, allowPingbacks, serviceContext);
	}

	@Override
	public MBMessage addMessage(
			long categoryId, String subject, String body,
			ServiceContext serviceContext)
		throws PortalException {

		MBCategory category = _mbCategoryLocalService.getCategory(categoryId);

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

			_messageModelResourcePermission.check(
				getPermissionChecker(), parentMessageId, ActionKeys.UPDATE);
		}

		if (_lockManager.isLocked(
				MBThread.class.getName(), parentMessage.getThreadId())) {

			StringBundler sb = new StringBundler(4);

			sb.append("Thread is locked for class name ");
			sb.append(MBThread.class.getName());
			sb.append(" and class PK ");
			sb.append(parentMessage.getThreadId());

			throw new LockedThreadException(sb.toString());
		}

		if (!ModelResourcePermissionHelper.contains(
				_categoryModelResourcePermission, getPermissionChecker(),
				parentMessage.getGroupId(), parentMessage.getCategoryId(),
				ActionKeys.ADD_FILE)) {

			inputStreamOVPs = Collections.emptyList();
		}

		if (!ModelResourcePermissionHelper.contains(
				_categoryModelResourcePermission, getPermissionChecker(),
				parentMessage.getGroupId(), parentMessage.getCategoryId(),
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
	public void addMessageAttachment(
			long messageId, String fileName, File file, String mimeType)
		throws PortalException {

		MBMessage message = mbMessageLocalService.getMBMessage(messageId);

		if (_lockManager.isLocked(
				MBThread.class.getName(), message.getThreadId())) {

			StringBundler sb = new StringBundler(4);

			sb.append("Thread is locked for class name ");
			sb.append(MBThread.class.getName());
			sb.append(" and class PK ");
			sb.append(message.getThreadId());

			throw new LockedThreadException(sb.toString());
		}

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(),
			message.getGroupId(), message.getCategoryId(), ActionKeys.ADD_FILE);

		mbMessageLocalService.addMessageAttachment(
			getUserId(), messageId, fileName, file, mimeType);
	}

	@Override
	public FileEntry addTempAttachment(
			long groupId, long categoryId, String folderName, String fileName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(), groupId,
			categoryId, ActionKeys.ADD_FILE);

		return mbMessageLocalService.addTempAttachment(
			groupId, getUserId(), folderName, fileName, inputStream, mimeType);
	}

	@Override
	public void deleteDiscussionMessage(long messageId) throws PortalException {
		MBDiscussionPermission.check(
			getPermissionChecker(), messageId, ActionKeys.DELETE_DISCUSSION);

		mbMessageLocalService.deleteDiscussionMessage(messageId);
	}

	@Override
	public void deleteMessage(long messageId) throws PortalException {
		_messageModelResourcePermission.check(
			getPermissionChecker(), messageId, ActionKeys.DELETE);

		mbMessageLocalService.deleteMessage(messageId);
	}

	@Override
	public void deleteMessageAttachment(long messageId, String fileName)
		throws PortalException {

		_messageModelResourcePermission.check(
			getPermissionChecker(), messageId, ActionKeys.UPDATE);

		mbMessageLocalService.deleteMessageAttachment(messageId, fileName);
	}

	@Override
	public void deleteMessageAttachments(long messageId)
		throws PortalException {

		_messageModelResourcePermission.check(
			getPermissionChecker(), messageId, ActionKeys.DELETE);

		mbMessageLocalService.deleteMessageAttachments(messageId);
	}

	@Override
	public void deleteTempAttachment(
			long groupId, long categoryId, String folderName, String fileName)
		throws PortalException {

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(), groupId,
			categoryId, ActionKeys.ADD_FILE);

		mbMessageLocalService.deleteTempAttachment(
			groupId, getUserId(), folderName, fileName);
	}

	@Override
	public void emptyMessageAttachments(long messageId) throws PortalException {
		_messageModelResourcePermission.check(
			getPermissionChecker(), messageId, ActionKeys.DELETE);

		mbMessageLocalService.emptyMessageAttachments(messageId);
	}

	@Override
	public List<MBMessage> getCategoryMessages(
			long groupId, long categoryId, int status, int start, int end)
		throws PortalException {

		List<MBMessage> messages = new ArrayList<>();

		List<MBMessage> categoryMessages =
			mbMessageLocalService.getCategoryMessages(
				groupId, categoryId, status, start, end);

		for (MBMessage message : categoryMessages) {
			if (_messageModelResourcePermission.contains(
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

		MBCategory category = _mbCategoryLocalService.fetchMBCategory(
			categoryId);

		if (category == null) {
			Group group = groupLocalService.getGroup(categoryId);

			groupId = group.getGroupId();

			categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
			name = group.getDescriptiveName();
			description = group.getDescription(
				LocaleUtil.getMostRelevantLocale());
		}
		else {
			groupId = category.getGroupId();
			name = category.getName();
			description = category.getDescription();
		}

		List<MBMessage> messages = new ArrayList<>();

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
			listNotExhausted = messageList.size() == max;

			for (MBMessage message : messageList) {
				if (messages.size() >= max) {
					break;
				}

				if (_messageModelResourcePermission.contains(
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

		Company company = companyLocalService.getCompany(companyId);

		String name = company.getName();
		String description = company.getName();

		List<MBMessage> messages = new ArrayList<>();

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
			listNotExhausted = messageList.size() == max;

			for (MBMessage message : messageList) {
				if (messages.size() >= max) {
					break;
				}

				if (_messageModelResourcePermission.contains(
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

		return mbMessagePersistence.filterCountByG_S(groupId, status);
	}

	@Override
	public String getGroupMessagesRSS(
			long groupId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Group group = groupLocalService.getGroup(groupId);

		String name = group.getDescriptiveName();
		String description = group.getDescription(
			LocaleUtil.getMostRelevantLocale());

		List<MBMessage> messages = new ArrayList<>();

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
			listNotExhausted = messageList.size() == max;

			for (MBMessage message : messageList) {
				if (messages.size() >= max) {
					break;
				}

				if (_messageModelResourcePermission.contains(
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
	public String getGroupMessagesRSS(
			long groupId, long userId, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		Group group = groupLocalService.getGroup(groupId);

		String name = group.getDescriptiveName();
		String description = group.getDescription(
			LocaleUtil.getMostRelevantLocale());

		List<MBMessage> messages = new ArrayList<>();

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
			listNotExhausted = messageList.size() == max;

			for (MBMessage message : messageList) {
				if (messages.size() >= max) {
					break;
				}

				if (_messageModelResourcePermission.contains(
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
	public MBMessage getMessage(long messageId) throws PortalException {
		_messageModelResourcePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return mbMessageLocalService.getMessage(messageId);
	}

	@Override
	public MBMessageDisplay getMessageDisplay(long messageId, int status)
		throws PortalException {

		_messageModelResourcePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return mbMessageLocalService.getMessageDisplay(
			getGuestOrUserId(), messageId, status);
	}

	@Override
	public String[] getTempAttachmentNames(long groupId, String folderName)
		throws PortalException {

		return mbMessageLocalService.getTempAttachmentNames(
			groupId, getUserId(), folderName);
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

		return mbMessagePersistence.filterFindByG_C_T_S(
			groupId, categoryId, threadId, status, start, end);
	}

	@Override
	public int getThreadMessagesCount(
		long groupId, long categoryId, long threadId, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return mbMessagePersistence.filterCountByG_C_T(
				groupId, categoryId, threadId);
		}

		return mbMessagePersistence.filterCountByG_C_T_S(
			groupId, categoryId, threadId, status);
	}

	@Override
	public String getThreadMessagesRSS(
			long threadId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		List<MBMessage> messages = new ArrayList<>();

		MBThread thread = _mbThreadLocalService.getThread(threadId);

		if (_messageModelResourcePermission.contains(
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

				if (_messageModelResourcePermission.contains(
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
	public void moveMessageAttachmentToTrash(long messageId, String fileName)
		throws PortalException {

		_messageModelResourcePermission.check(
			getPermissionChecker(), messageId, ActionKeys.UPDATE);

		mbMessageLocalService.moveMessageAttachmentToTrash(
			getUserId(), messageId, fileName);
	}

	@Override
	public void restoreMessageAttachmentFromTrash(
			long messageId, String fileName)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		ModelResourcePermissionHelper.check(
			_categoryModelResourcePermission, getPermissionChecker(),
			message.getGroupId(), message.getCategoryId(), ActionKeys.ADD_FILE);

		mbMessageLocalService.restoreMessageAttachmentFromTrash(
			getUserId(), messageId, fileName);
	}

	@Override
	public void subscribeMessage(long messageId) throws PortalException {
		_messageModelResourcePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		mbMessageLocalService.subscribeMessage(getUserId(), messageId);
	}

	@Override
	public void unsubscribeMessage(long messageId) throws PortalException {
		_messageModelResourcePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		mbMessageLocalService.unsubscribeMessage(getUserId(), messageId);
	}

	@Override
	public void updateAnswer(long messageId, boolean answer, boolean cascade)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		_messageModelResourcePermission.check(
			getPermissionChecker(), message.getRootMessageId(),
			ActionKeys.UPDATE);

		mbMessageLocalService.updateAnswer(messageId, answer, cascade);
	}

	@Override
	public MBMessage updateDiscussionMessage(
			String className, long classPK, long messageId, String subject,
			String body, ServiceContext serviceContext)
		throws PortalException {

		MBDiscussionPermission.check(
			getPermissionChecker(), messageId, ActionKeys.UPDATE_DISCUSSION);

		return mbMessageLocalService.updateDiscussionMessage(
			getUserId(), messageId, className, classPK, subject, body,
			serviceContext);
	}

	@Override
	public MBMessage updateMessage(
			long messageId, String subject, String body,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		boolean preview = ParamUtil.getBoolean(serviceContext, "preview");

		if (preview &&
			_messageModelResourcePermission.contains(
				getPermissionChecker(), message, ActionKeys.UPDATE)) {

			checkReplyToPermission(
				message.getGroupId(), message.getCategoryId(),
				message.getParentMessageId());
		}
		else {
			_messageModelResourcePermission.check(
				getPermissionChecker(), messageId, ActionKeys.UPDATE);
		}

		if (_lockManager.isLocked(
				MBThread.class.getName(), message.getThreadId())) {

			StringBundler sb = new StringBundler(4);

			sb.append("Thread is locked for class name ");
			sb.append(MBThread.class.getName());
			sb.append(" and class PK ");
			sb.append(message.getThreadId());

			throw new LockedThreadException(sb.toString());
		}

		if (!ModelResourcePermissionHelper.contains(
				_categoryModelResourcePermission, getPermissionChecker(),
				message.getGroupId(), message.getCategoryId(),
				ActionKeys.ADD_FILE)) {

			inputStreamOVPs = Collections.emptyList();
		}

		if (!ModelResourcePermissionHelper.contains(
				_categoryModelResourcePermission, getPermissionChecker(),
				message.getGroupId(), message.getCategoryId(),
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			MBThread thread = _mbThreadLocalService.getThread(
				message.getThreadId());

			priority = thread.getPriority();
		}

		return mbMessageLocalService.updateMessage(
			getGuestOrUserId(), messageId, subject, body, inputStreamOVPs,
			priority, allowPingbacks, serviceContext);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #updateMessage(long,
	 *             String, String, List, double, boolean, ServiceContext)}
	 */
	@Deprecated
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
			_messageModelResourcePermission.contains(
				getPermissionChecker(), message, ActionKeys.UPDATE)) {

			checkReplyToPermission(
				message.getGroupId(), message.getCategoryId(),
				message.getParentMessageId());
		}
		else {
			_messageModelResourcePermission.check(
				getPermissionChecker(), messageId, ActionKeys.UPDATE);
		}

		if (_lockManager.isLocked(
				MBThread.class.getName(), message.getThreadId())) {

			StringBundler sb = new StringBundler(4);

			sb.append("Thread is locked for class name ");
			sb.append(MBThread.class.getName());
			sb.append(" and class PK ");
			sb.append(message.getThreadId());

			throw new LockedThreadException(sb.toString());
		}

		if (!ModelResourcePermissionHelper.contains(
				_categoryModelResourcePermission, getPermissionChecker(),
				message.getGroupId(), message.getCategoryId(),
				ActionKeys.ADD_FILE)) {

			inputStreamOVPs = Collections.emptyList();
		}

		if (!ModelResourcePermissionHelper.contains(
				_categoryModelResourcePermission, getPermissionChecker(),
				message.getGroupId(), message.getCategoryId(),
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			MBThread thread = _mbThreadLocalService.getThread(
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

		PermissionChecker permissionChecker = getPermissionChecker();

		if (parentMessageId > 0) {
			if (ModelResourcePermissionHelper.contains(
					_categoryModelResourcePermission, permissionChecker,
					groupId, categoryId, ActionKeys.ADD_MESSAGE)) {

				return;
			}

			if (!ModelResourcePermissionHelper.contains(
					_categoryModelResourcePermission, permissionChecker,
					groupId, categoryId, ActionKeys.REPLY_TO_MESSAGE)) {

				throw new PrincipalException.MustHavePermission(
					permissionChecker, MBCategory.class.getName(), categoryId,
					ActionKeys.REPLY_TO_MESSAGE);
			}
		}
		else {
			ModelResourcePermissionHelper.check(
				_categoryModelResourcePermission, permissionChecker, groupId,
				categoryId, ActionKeys.ADD_MESSAGE);
		}
	}

	protected String exportToRSS(
		String name, String description, String type, double version,
		String displayStyle, String feedURL, String entryURL,
		List<MBMessage> messages, ThemeDisplay themeDisplay) {

		SyndFeed syndFeed = _syndModelFactory.createSyndFeed();

		syndFeed.setDescription(description);

		List<SyndEntry> syndEntries = new ArrayList<>();

		syndFeed.setEntries(syndEntries);

		for (MBMessage message : messages) {
			SyndEntry syndEntry = _syndModelFactory.createSyndEntry();

			if (!message.isAnonymous()) {
				String author = _portal.getUserName(message);

				syndEntry.setAuthor(author);
			}

			SyndContent syndContent = _syndModelFactory.createSyndContent();

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

			String link = entryURL + "&messageId=" + message.getMessageId();

			syndEntry.setLink(link);

			syndEntry.setPublishedDate(message.getCreateDate());
			syndEntry.setTitle(message.getSubject());
			syndEntry.setUpdatedDate(message.getModifiedDate());
			syndEntry.setUri(link);

			syndEntries.add(syndEntry);
		}

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));

		List<SyndLink> syndLinks = new ArrayList<>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = _syndModelFactory.createSyndLink();

		syndLinks.add(selfSyndLink);

		selfSyndLink.setHref(feedURL);
		selfSyndLink.setRel("self");

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(name);
		syndFeed.setUri(feedURL);

		return _rssExporter.export(syndFeed);
	}

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBCategory)"
	)
	private ModelResourcePermission<MBCategory>
		_categoryModelResourcePermission;

	@Reference
	private LockManager _lockManager;

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBMessage)"
	)
	private ModelResourcePermission<MBMessage> _messageModelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference
	private RSSExporter _rssExporter;

	@Reference
	private SyndModelFactory _syndModelFactory;

}