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

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBMessageDisplay;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.List;

/**
 * Provides the remote service interface for MBMessage. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageServiceUtil
 * @see com.liferay.message.boards.service.base.MBMessageServiceBaseImpl
 * @see com.liferay.message.boards.service.impl.MBMessageServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=mb", "json.web.service.context.path=MBMessage"}, service = MBMessageService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MBMessageService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBMessageServiceUtil} to access the message-boards message remote service. Add custom service methods to {@link com.liferay.message.boards.service.impl.MBMessageServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public MBMessage addDiscussionMessage(long groupId,
		java.lang.String className, long classPK, long threadId,
		long parentMessageId, java.lang.String subject, java.lang.String body,
		ServiceContext serviceContext) throws PortalException;

	public MBMessage addMessage(long groupId, long categoryId,
		java.lang.String subject, java.lang.String body,
		java.lang.String format,
		List<ObjectValuePair<java.lang.String, InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		ServiceContext serviceContext) throws PortalException;

	public MBMessage addMessage(long groupId, long categoryId,
		java.lang.String subject, java.lang.String body,
		java.lang.String format, java.lang.String fileName, File file,
		boolean anonymous, double priority, boolean allowPingbacks,
		ServiceContext serviceContext)
		throws FileNotFoundException, PortalException;

	public MBMessage addMessage(long categoryId, java.lang.String subject,
		java.lang.String body, ServiceContext serviceContext)
		throws PortalException;

	public MBMessage addMessage(long parentMessageId, java.lang.String subject,
		java.lang.String body, java.lang.String format,
		List<ObjectValuePair<java.lang.String, InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		ServiceContext serviceContext) throws PortalException;

	public void addMessageAttachment(long messageId, java.lang.String fileName,
		File file, java.lang.String mimeType) throws PortalException;

	public FileEntry addTempAttachment(long groupId, long categoryId,
		java.lang.String folderName, java.lang.String fileName,
		InputStream inputStream, java.lang.String mimeType)
		throws PortalException;

	public void deleteDiscussionMessage(long messageId)
		throws PortalException;

	public void deleteMessage(long messageId) throws PortalException;

	public void deleteMessageAttachment(long messageId,
		java.lang.String fileName) throws PortalException;

	public void deleteMessageAttachments(long messageId)
		throws PortalException;

	public void deleteTempAttachment(long groupId, long categoryId,
		java.lang.String folderName, java.lang.String fileName)
		throws PortalException;

	public void emptyMessageAttachments(long messageId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getCategoryMessages(long groupId, long categoryId,
		int status, int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoryMessagesCount(long groupId, long categoryId,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getCategoryMessagesRSS(long groupId,
		long categoryId, int status, int max, java.lang.String type,
		double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		ThemeDisplay themeDisplay) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getCompanyMessagesRSS(long companyId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, ThemeDisplay themeDisplay)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupMessagesCount(long groupId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getGroupMessagesRSS(long groupId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, ThemeDisplay themeDisplay)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getGroupMessagesRSS(long groupId, long userId,
		int status, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, ThemeDisplay themeDisplay)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessage getMessage(long messageId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessageDisplay getMessageDisplay(long messageId, int status)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTempAttachmentNames(long groupId,
		java.lang.String folderName) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getThreadAnswersCount(long groupId, long categoryId,
		long threadId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getThreadMessages(long groupId, long categoryId,
		long threadId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getThreadMessagesCount(long groupId, long categoryId,
		long threadId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getThreadMessagesRSS(long threadId, int status,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, ThemeDisplay themeDisplay)
		throws PortalException;

	public void moveMessageAttachmentToTrash(long messageId,
		java.lang.String fileName) throws PortalException;

	public void restoreMessageAttachmentFromTrash(long messageId,
		java.lang.String fileName) throws PortalException;

	public void subscribeMessage(long messageId) throws PortalException;

	public void unsubscribeMessage(long messageId) throws PortalException;

	public void updateAnswer(long messageId, boolean answer, boolean cascade)
		throws PortalException;

	public MBMessage updateDiscussionMessage(java.lang.String className,
		long classPK, long messageId, java.lang.String subject,
		java.lang.String body, ServiceContext serviceContext)
		throws PortalException;

	public MBMessage updateMessage(long messageId, java.lang.String subject,
		java.lang.String body,
		List<ObjectValuePair<java.lang.String, InputStream>> inputStreamOVPs,
		List<java.lang.String> existingFiles, double priority,
		boolean allowPingbacks, ServiceContext serviceContext)
		throws PortalException;
}