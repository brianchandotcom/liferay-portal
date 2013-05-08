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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for MBMessage. This utility wraps
 * {@link com.liferay.portlet.messageboards.service.impl.MBMessageServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageService
 * @see com.liferay.portlet.messageboards.service.base.MBMessageServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBMessageServiceImpl
 * @generated
 */
public class MBMessageServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBMessageServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long groupId, java.lang.String className, long classPK,
		java.lang.String permissionClassName, long permissionClassPK,
		long permissionOwnerId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addDiscussionMessage(groupId, className, classPK,
			permissionClassName, permissionClassPK, permissionOwnerId,
			threadId, parentMessageId, subject, body, serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addMessage(long, String,
	String, String, java.util.List, boolean, double, boolean,
	com.liferay.portal.service.ServiceContext)}
	*/
	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.lang.String format,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addMessage(groupId, categoryId, threadId, parentMessageId,
			subject, body, format, inputStreamOVPs, anonymous, priority,
			allowPingbacks, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body, java.lang.String format,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addMessage(groupId, categoryId, subject, body, format,
			inputStreamOVPs, anonymous, priority, allowPingbacks, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long categoryId, java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addMessage(categoryId, subject, body, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long parentMessageId, java.lang.String subject, java.lang.String body,
		java.lang.String format,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addMessage(parentMessageId, subject, body, format,
			inputStreamOVPs, anonymous, priority, allowPingbacks, serviceContext);
	}

	public static void deleteDiscussionMessage(long groupId,
		java.lang.String className, long classPK,
		java.lang.String permissionClassName, long permissionClassPK,
		long permissionOwnerId, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteDiscussionMessage(groupId, className, classPK,
			permissionClassName, permissionClassPK, permissionOwnerId, messageId);
	}

	public static void deleteMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMessage(messageId);
	}

	public static void deleteMessageAttachments(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMessageAttachments(messageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCategoryMessages(groupId, categoryId, status, start, end);
	}

	public static int getCategoryMessagesCount(long groupId, long categoryId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryMessagesCount(groupId, categoryId, status);
	}

	public static java.lang.String getCategoryMessagesRSS(long groupId,
		long categoryId, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCategoryMessagesRSS(groupId, categoryId, status, max,
			type, version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static java.lang.String getCompanyMessagesRSS(long companyId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCompanyMessagesRSS(companyId, status, max, type,
			version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static int getGroupMessagesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupMessagesCount(groupId, status);
	}

	public static java.lang.String getGroupMessagesRSS(long groupId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupMessagesRSS(groupId, status, max, type, version,
			displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static java.lang.String getGroupMessagesRSS(long groupId,
		long userId, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupMessagesRSS(groupId, userId, status, max, type,
			version, displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMessage(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long messageId, int status, java.lang.String threadView,
		boolean includePrevAndNext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getMessageDisplay(messageId, status, threadView,
			includePrevAndNext);
	}

	public static int getThreadAnswersCount(long groupId, long categoryId,
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getThreadAnswersCount(groupId, categoryId, threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long groupId, long categoryId, long threadId, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getThreadMessages(groupId, categoryId, threadId, status,
			start, end);
	}

	public static int getThreadMessagesCount(long groupId, long categoryId,
		long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getThreadMessagesCount(groupId, categoryId, threadId, status);
	}

	public static java.lang.String getThreadMessagesRSS(long threadId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getThreadMessagesRSS(threadId, status, max, type, version,
			displayStyle, feedURL, entryURL, themeDisplay);
	}

	public static void restoreMessageAttachmentFromTrash(long messageId,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().restoreMessageAttachmentFromTrash(messageId, fileName);
	}

	public static void subscribeMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().subscribeMessage(messageId);
	}

	public static void unsubscribeMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsubscribeMessage(messageId);
	}

	public static void updateAnswer(long messageId, boolean answer,
		boolean cascade)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateAnswer(messageId, answer, cascade);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		java.lang.String className, long classPK,
		java.lang.String permissionClassName, long permissionClassPK,
		long permissionOwnerId, long messageId, java.lang.String subject,
		java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateDiscussionMessage(className, classPK,
			permissionClassName, permissionClassPK, permissionOwnerId,
			messageId, subject, body, serviceContext);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs,
		java.util.List<java.lang.String> existingFiles, double priority,
		boolean allowPingbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateMessage(messageId, subject, body, inputStreamOVPs,
			existingFiles, priority, allowPingbacks, serviceContext);
	}

	public static MBMessageService getService() {
		if (_service == null) {
			_service = (MBMessageService)PortalBeanLocatorUtil.locate(MBMessageService.class.getName());

			ReferenceRegistry.registerReference(MBMessageServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(MBMessageService service) {
	}

	private static MBMessageService _service;
}