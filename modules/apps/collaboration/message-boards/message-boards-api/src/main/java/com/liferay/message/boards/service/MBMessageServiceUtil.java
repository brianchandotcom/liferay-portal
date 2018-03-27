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

package com.liferay.message.boards.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for MBMessage. This utility wraps
 * {@link com.liferay.message.boards.service.impl.MBMessageServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageService
 * @see com.liferay.message.boards.service.base.MBMessageServiceBaseImpl
 * @see com.liferay.message.boards.service.impl.MBMessageServiceImpl
 * @generated
 */
@ProviderType
public class MBMessageServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.message.boards.service.impl.MBMessageServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.message.boards.model.MBMessage addDiscussionMessage(
		long groupId, java.lang.String className, long classPK, long threadId,
		long parentMessageId, java.lang.String subject, java.lang.String body,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addDiscussionMessage(groupId, className, classPK, threadId,
			parentMessageId, subject, body, serviceContext);
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body, java.lang.String format,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addMessage(groupId, categoryId, subject, body, format,
			inputStreamOVPs, anonymous, priority, allowPingbacks, serviceContext);
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body, java.lang.String format,
		java.lang.String fileName, java.io.File file, boolean anonymous,
		double priority, boolean allowPingbacks,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws java.io.FileNotFoundException,
			com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addMessage(groupId, categoryId, subject, body, format,
			fileName, file, anonymous, priority, allowPingbacks, serviceContext);
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
		long categoryId, java.lang.String subject, java.lang.String body,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addMessage(categoryId, subject, body, serviceContext);
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
		long parentMessageId, java.lang.String subject, java.lang.String body,
		java.lang.String format,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addMessage(parentMessageId, subject, body, format,
			inputStreamOVPs, anonymous, priority, allowPingbacks, serviceContext);
	}

	public static void addMessageAttachment(long messageId,
		java.lang.String fileName, java.io.File file, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addMessageAttachment(messageId, fileName, file, mimeType);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry addTempAttachment(
		long groupId, long categoryId, java.lang.String folderName,
		java.lang.String fileName, java.io.InputStream inputStream,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addTempAttachment(groupId, categoryId, folderName,
			fileName, inputStream, mimeType);
	}

	public static void deleteDiscussionMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteDiscussionMessage(messageId);
	}

	public static void deleteMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteMessage(messageId);
	}

	public static void deleteMessageAttachment(long messageId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteMessageAttachment(messageId, fileName);
	}

	public static void deleteMessageAttachments(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteMessageAttachments(messageId);
	}

	public static void deleteTempAttachment(long groupId, long categoryId,
		java.lang.String folderName, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteTempAttachment(groupId, categoryId, folderName, fileName);
	}

	public static void emptyMessageAttachments(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().emptyMessageAttachments(messageId);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCategoryMessages(groupId, categoryId, status, start, end);
	}

	public static int getCategoryMessagesCount(long groupId, long categoryId,
		int status) {
		return getService().getCategoryMessagesCount(groupId, categoryId, status);
	}

	public static java.lang.String getCategoryMessagesRSS(long groupId,
		long categoryId, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCategoryMessagesRSS(groupId, categoryId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static java.lang.String getCompanyMessagesRSS(long companyId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCompanyMessagesRSS(companyId, status, max, type,
			version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static int getGroupMessagesCount(long groupId, int status) {
		return getService().getGroupMessagesCount(groupId, status);
	}

	public static java.lang.String getGroupMessagesRSS(long groupId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupMessagesRSS(groupId, status, max, type, version,
			displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static java.lang.String getGroupMessagesRSS(long groupId,
		long userId, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupMessagesRSS(groupId, userId, status, max, type,
			version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static com.liferay.message.boards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMessage(messageId);
	}

	public static com.liferay.message.boards.model.MBMessageDisplay getMessageDisplay(
		long messageId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMessageDisplay(messageId, status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.lang.String[] getTempAttachmentNames(long groupId,
		java.lang.String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTempAttachmentNames(groupId, folderName);
	}

	public static int getThreadAnswersCount(long groupId, long categoryId,
		long threadId) {
		return getService().getThreadAnswersCount(groupId, categoryId, threadId);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage> getThreadMessages(
		long groupId, long categoryId, long threadId, int status, int start,
		int end) {
		return getService()
				   .getThreadMessages(groupId, categoryId, threadId, status,
			start, end);
	}

	public static int getThreadMessagesCount(long groupId, long categoryId,
		long threadId, int status) {
		return getService()
				   .getThreadMessagesCount(groupId, categoryId, threadId, status);
	}

	public static java.lang.String getThreadMessagesRSS(long threadId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getThreadMessagesRSS(threadId, status, max, type, version,
			displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static void moveMessageAttachmentToTrash(long messageId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().moveMessageAttachmentToTrash(messageId, fileName);
	}

	public static void restoreMessageAttachmentFromTrash(long messageId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreMessageAttachmentFromTrash(messageId, fileName);
	}

	public static void subscribeMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().subscribeMessage(messageId);
	}

	public static void unsubscribeMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsubscribeMessage(messageId);
	}

	public static void updateAnswer(long messageId, boolean answer,
		boolean cascade)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateAnswer(messageId, answer, cascade);
	}

	public static com.liferay.message.boards.model.MBMessage updateDiscussionMessage(
		java.lang.String className, long classPK, long messageId,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateDiscussionMessage(className, classPK, messageId,
			subject, body, serviceContext);
	}

	public static com.liferay.message.boards.model.MBMessage updateMessage(
		long messageId, java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		java.util.List<java.lang.String> existingFiles, double priority,
		boolean allowPingbacks,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateMessage(messageId, subject, body, inputStreamOVPs,
			existingFiles, priority, allowPingbacks, serviceContext);
	}

	public static MBMessageService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MBMessageService, MBMessageService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MBMessageService.class);

		ServiceTracker<MBMessageService, MBMessageService> serviceTracker = new ServiceTracker<MBMessageService, MBMessageService>(bundle.getBundleContext(),
				MBMessageService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}