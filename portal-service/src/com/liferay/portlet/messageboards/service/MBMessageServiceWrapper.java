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

package com.liferay.portlet.messageboards.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBMessageService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageService
 * @generated
 */
@ProviderType
public class MBMessageServiceWrapper implements MBMessageService,
	ServiceWrapper<MBMessageService> {
	public MBMessageServiceWrapper(MBMessageService mbMessageService) {
		_mbMessageService = mbMessageService;
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long groupId, java.lang.String className, long classPK, long threadId,
		long parentMessageId, java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.addDiscussionMessage(groupId, className,
			classPK, threadId, parentMessageId, subject, body, serviceContext);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long categoryId, java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.addMessage(categoryId, subject, body,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body, java.lang.String format,
		java.lang.String fileName, java.io.File file, boolean anonymous,
		double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			java.io.FileNotFoundException {
		return _mbMessageService.addMessage(groupId, categoryId, subject, body,
			format, fileName, file, anonymous, priority, allowPingbacks,
			serviceContext);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body, java.lang.String format,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.addMessage(groupId, categoryId, subject, body,
			format, inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addMessage(long, String,
	String, String, List, boolean, double, boolean,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.lang.String format,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.addMessage(groupId, categoryId, threadId,
			parentMessageId, subject, body, format, inputStreamOVPs, anonymous,
			priority, allowPingbacks, serviceContext);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long parentMessageId, java.lang.String subject, java.lang.String body,
		java.lang.String format,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.addMessage(parentMessageId, subject, body,
			format, inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);
	}

	@Override
	public void addMessageAttachment(long messageId, java.lang.String fileName,
		java.io.File file, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.addMessageAttachment(messageId, fileName, file,
			mimeType);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#deleteDiscussionMessage(long)}
	*/
	@Deprecated
	@Override
	public void deleteDiscussionMessage(long groupId,
		java.lang.String className, long classPK,
		java.lang.String permissionClassName, long permissionClassPK,
		long permissionOwnerId, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.deleteDiscussionMessage(groupId, className, classPK,
			permissionClassName, permissionClassPK, permissionOwnerId, messageId);
	}

	@Override
	public void deleteDiscussionMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.deleteDiscussionMessage(messageId);
	}

	@Override
	public void deleteMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.deleteMessage(messageId);
	}

	@Override
	public void deleteMessageAttachment(long messageId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.deleteMessageAttachment(messageId, fileName);
	}

	@Override
	public void deleteMessageAttachments(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.deleteMessageAttachments(messageId);
	}

	@Override
	public void emptyMessageAttachments(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.emptyMessageAttachments(messageId);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.getCategoryMessages(groupId, categoryId,
			status, start, end);
	}

	@Override
	public int getCategoryMessagesCount(long groupId, long categoryId,
		int status) {
		return _mbMessageService.getCategoryMessagesCount(groupId, categoryId,
			status);
	}

	@Override
	public java.lang.String getCategoryMessagesRSS(long groupId,
		long categoryId, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.getCategoryMessagesRSS(groupId, categoryId,
			status, max, type, version, displayStyle, feedURL, entryURL,
			themeDisplay);
	}

	@Override
	public java.lang.String getCompanyMessagesRSS(long companyId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.getCompanyMessagesRSS(companyId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	@Override
	public int getGroupMessagesCount(long groupId, int status) {
		return _mbMessageService.getGroupMessagesCount(groupId, status);
	}

	@Override
	public java.lang.String getGroupMessagesRSS(long groupId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.getGroupMessagesRSS(groupId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	@Override
	public java.lang.String getGroupMessagesRSS(long groupId, long userId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.getGroupMessagesRSS(groupId, userId, status,
			max, type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.getMessage(messageId);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long messageId, int status, java.lang.String threadView,
		boolean includePrevAndNext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.getMessageDisplay(messageId, status,
			threadView, includePrevAndNext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _mbMessageService.getOSGiServiceIdentifier();
	}

	@Override
	public int getThreadAnswersCount(long groupId, long categoryId,
		long threadId) {
		return _mbMessageService.getThreadAnswersCount(groupId, categoryId,
			threadId);
	}

	@Override
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long groupId, long categoryId, long threadId, int status, int start,
		int end) {
		return _mbMessageService.getThreadMessages(groupId, categoryId,
			threadId, status, start, end);
	}

	@Override
	public int getThreadMessagesCount(long groupId, long categoryId,
		long threadId, int status) {
		return _mbMessageService.getThreadMessagesCount(groupId, categoryId,
			threadId, status);
	}

	@Override
	public java.lang.String getThreadMessagesRSS(long threadId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.getThreadMessagesRSS(threadId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	@Override
	public void restoreMessageAttachmentFromTrash(long messageId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.restoreMessageAttachmentFromTrash(messageId, fileName);
	}

	@Override
	public void subscribeMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.subscribeMessage(messageId);
	}

	@Override
	public void unsubscribeMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.unsubscribeMessage(messageId);
	}

	@Override
	public void updateAnswer(long messageId, boolean answer, boolean cascade)
		throws com.liferay.portal.kernel.exception.PortalException {
		_mbMessageService.updateAnswer(messageId, answer, cascade);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String className, long classPK, long messageId,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.updateDiscussionMessage(className, classPK,
			messageId, subject, body, serviceContext);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		java.util.List<java.lang.String> existingFiles, double priority,
		boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessageService.updateMessage(messageId, subject, body,
			inputStreamOVPs, existingFiles, priority, allowPingbacks,
			serviceContext);
	}

	@Override
	public MBMessageService getWrappedService() {
		return _mbMessageService;
	}

	@Override
	public void setWrappedService(MBMessageService mbMessageService) {
		_mbMessageService = mbMessageService;
	}

	private MBMessageService _mbMessageService;
}