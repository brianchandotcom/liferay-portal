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

package com.liferay.message.boards.internal.service;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageDisplay;
import com.liferay.message.boards.kernel.service.MBMessageService;
import com.liferay.message.boards.kernel.service.MBMessageServiceWrapper;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularMBMessageServiceWrapper extends MBMessageServiceWrapper {

	public ModularMBMessageServiceWrapper() {
		super(null);
	}

	public ModularMBMessageServiceWrapper(MBMessageService mbMessageService) {
		super(mbMessageService);
	}

	@Override
	public MBMessage addDiscussionMessage(
			long groupId, String className, long classPK, long threadId,
			long parentMessageId, String subject, String body,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageService.addDiscussionMessage(
				groupId, className, classPK, threadId, parentMessageId, subject,
				body, serviceContext));
	}

	@Override
	public MBMessage addMessage(
			long groupId, long categoryId, String subject, String body,
			String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageService.addMessage(
				groupId, categoryId, subject, body, format, inputStreamOVPs,
				anonymous, priority, allowPingbacks, serviceContext));
	}

	@Override
	public MBMessage addMessage(
			long groupId, long categoryId, String subject, String body,
			String format, String fileName, File file, boolean anonymous,
			double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws FileNotFoundException, PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageService.addMessage(
				groupId, categoryId, subject, body, format, fileName, file,
				anonymous, priority, allowPingbacks, serviceContext));
	}

	@Override
	public MBMessage addMessage(
			long categoryId, String subject, String body,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageService.addMessage(
				categoryId, subject, body, serviceContext));
	}

	@Override
	public MBMessage addMessage(
			long parentMessageId, String subject, String body, String format,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageService.addMessage(
				parentMessageId, subject, body, format, inputStreamOVPs,
				anonymous, priority, allowPingbacks, serviceContext));
	}

	@Override
	public void addMessageAttachment(
			long messageId, String fileName, File file, String mimeType)
		throws PortalException {

		_mbMessageService.addMessageAttachment(
			messageId, fileName, file, mimeType);
	}

	@Override
	public void deleteDiscussionMessage(long messageId) throws PortalException {
		_mbMessageService.deleteDiscussionMessage(messageId);
	}

	@Override
	public void deleteDiscussionMessage(
			long groupId, String className, long classPK,
			String permissionClassName, long permissionClassPK,
			long permissionOwnerId, long messageId)
		throws PortalException {

		super.deleteDiscussionMessage(
			groupId, className, classPK, permissionClassName, permissionClassPK,
			permissionOwnerId, messageId);
	}

	@Override
	public void deleteMessage(long messageId) throws PortalException {
		_mbMessageService.deleteMessage(messageId);
	}

	@Override
	public void deleteMessageAttachment(long messageId, String fileName)
		throws PortalException {

		_mbMessageService.deleteMessageAttachment(messageId, fileName);
	}

	@Override
	public void deleteMessageAttachments(long messageId)
		throws PortalException {

		_mbMessageService.deleteMessageAttachments(messageId);
	}

	@Override
	public void emptyMessageAttachments(long messageId) throws PortalException {
		_mbMessageService.emptyMessageAttachments(messageId);
	}

	@Override
	public List<MBMessage> getCategoryMessages(
			long groupId, long categoryId, int status, int start, int end)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageService.getCategoryMessages(
				groupId, categoryId, status, start, end));
	}

	@Override
	public int getCategoryMessagesCount(
		long groupId, long categoryId, int status) {

		return _mbMessageService.getCategoryMessagesCount(
			groupId, categoryId, status);
	}

	@Override
	public String getCategoryMessagesRSS(
			long groupId, long categoryId, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		return _mbMessageService.getCategoryMessagesRSS(
			groupId, categoryId, status, max, type, version, displayStyle,
			feedURL, entryURL, themeDisplay);
	}

	@Override
	public String getCompanyMessagesRSS(
			long companyId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException {

		return _mbMessageService.getCompanyMessagesRSS(
			companyId, status, max, type, version, displayStyle, feedURL,
			entryURL, themeDisplay);
	}

	@Override
	public int getGroupMessagesCount(long groupId, int status) {
		return _mbMessageService.getGroupMessagesCount(groupId, status);
	}

	@Override
	public String getGroupMessagesRSS(
			long groupId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException {

		return _mbMessageService.getGroupMessagesRSS(
			groupId, status, max, type, version, displayStyle, feedURL,
			entryURL, themeDisplay);
	}

	@Override
	public String getGroupMessagesRSS(
			long groupId, long userId, int status, int max, String type,
			double version, String displayStyle, String feedURL,
			String entryURL, ThemeDisplay themeDisplay)
		throws PortalException {

		return _mbMessageService.getGroupMessagesRSS(
			groupId, userId, status, max, type, version, displayStyle, feedURL,
			entryURL, themeDisplay);
	}

	@Override
	public MBMessage getMessage(long messageId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBMessage.class, _mbMessageService.getMessage(messageId));
	}

	@Override
	public MBMessageDisplay getMessageDisplay(long messageId, int status)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessageDisplay.class,
			_mbMessageService.getMessageDisplay(messageId, status));
	}

	@Override
	public MBMessageDisplay getMessageDisplay(
			long messageId, int status, String threadView,
			boolean includePrevAndNext)
		throws PortalException {

		return super.getMessageDisplay(
			messageId, status, threadView, includePrevAndNext);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbMessageService.getOSGiServiceIdentifier();
	}

	@Override
	public int getThreadAnswersCount(
		long groupId, long categoryId, long threadId) {

		return _mbMessageService.getThreadAnswersCount(
			groupId, categoryId, threadId);
	}

	@Override
	public List<MBMessage> getThreadMessages(
		long groupId, long categoryId, long threadId, int status, int start,
		int end) {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageService.getThreadMessages(
				groupId, categoryId, threadId, status, start, end));
	}

	@Override
	public int getThreadMessagesCount(
		long groupId, long categoryId, long threadId, int status) {

		return _mbMessageService.getThreadMessagesCount(
			groupId, categoryId, threadId, status);
	}

	@Override
	public String getThreadMessagesRSS(
			long threadId, int status, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			ThemeDisplay themeDisplay)
		throws PortalException {

		return _mbMessageService.getThreadMessagesRSS(
			threadId, status, max, type, version, displayStyle, feedURL,
			entryURL, themeDisplay);
	}

	@Override
	public MBMessageService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public void restoreMessageAttachmentFromTrash(
			long messageId, String fileName)
		throws PortalException {

		_mbMessageService.restoreMessageAttachmentFromTrash(
			messageId, fileName);
	}

	@Override
	public void setWrappedService(MBMessageService mbMessageService) {
		super.setWrappedService(mbMessageService);
	}

	@Override
	public void subscribeMessage(long messageId) throws PortalException {
		_mbMessageService.subscribeMessage(messageId);
	}

	@Override
	public void unsubscribeMessage(long messageId) throws PortalException {
		_mbMessageService.unsubscribeMessage(messageId);
	}

	@Override
	public void updateAnswer(long messageId, boolean answer, boolean cascade)
		throws PortalException {

		_mbMessageService.updateAnswer(messageId, answer, cascade);
	}

	@Override
	public MBMessage updateDiscussionMessage(
			String className, long classPK, long messageId, String subject,
			String body, ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageService.updateDiscussionMessage(
				className, classPK, messageId, subject, body, serviceContext));
	}

	@Override
	public MBMessage updateMessage(
			long messageId, String subject, String body,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			List<String> existingFiles, double priority, boolean allowPingbacks,
			ServiceContext serviceContext)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBMessage.class,
			_mbMessageService.updateMessage(
				messageId, subject, body, inputStreamOVPs, existingFiles,
				priority, allowPingbacks, serviceContext));
	}

	@Reference
	private com.liferay.message.boards.service.MBMessageService
		_mbMessageService;

}