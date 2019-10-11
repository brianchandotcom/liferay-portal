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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for MBMessage. This utility wraps
 * <code>com.liferay.message.boards.service.impl.MBMessageLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageLocalService
 * @generated
 */
public class MBMessageLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.message.boards.service.impl.MBMessageLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBMessageLocalServiceUtil} to access the message-boards message local service. Add custom service methods to <code>com.liferay.message.boards.service.impl.MBMessageLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.message.boards.model.MBMessage
			addDiscussionMessage(
				long userId, String userName, long groupId, String className,
				long classPK, int workflowAction)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDiscussionMessage(
			userId, userName, groupId, className, classPK, workflowAction);
	}

	public static com.liferay.message.boards.model.MBMessage
			addDiscussionMessage(
				long userId, String userName, long groupId, String className,
				long classPK, long threadId, long parentMessageId,
				String subject, String body,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDiscussionMessage(
			userId, userName, groupId, className, classPK, threadId,
			parentMessageId, subject, body, serviceContext);
	}

	/**
	 * Adds the message-boards message to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbMessage the message-boards message
	 * @return the message-boards message that was added
	 */
	public static com.liferay.message.boards.model.MBMessage addMBMessage(
		com.liferay.message.boards.model.MBMessage mbMessage) {

		return getService().addMBMessage(mbMessage);
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			long threadId, long parentMessageId, String subject, String body,
			String format,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair
					<String, java.io.InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addMessage(
			userId, userName, groupId, categoryId, threadId, parentMessageId,
			subject, body, format, inputStreamOVPs, anonymous, priority,
			allowPingbacks, serviceContext);
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addMessage(
			userId, userName, groupId, categoryId, subject, body,
			serviceContext);
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, String format,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair
					<String, java.io.InputStream>> inputStreamOVPs,
			boolean anonymous, double priority, boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addMessage(
			userId, userName, groupId, categoryId, subject, body, format,
			inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);
	}

	public static com.liferay.message.boards.model.MBMessage addMessage(
			long userId, String userName, long groupId, long categoryId,
			String subject, String body, String format, String fileName,
			java.io.File file, boolean anonymous, double priority,
			boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			   java.io.FileNotFoundException {

		return getService().addMessage(
			userId, userName, groupId, categoryId, subject, body, format,
			fileName, file, anonymous, priority, allowPingbacks,
			serviceContext);
	}

	public static void addMessageAttachment(
			long userId, long messageId, String fileName, java.io.File file,
			String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addMessageAttachment(
			userId, messageId, fileName, file, mimeType);
	}

	public static void addMessageResources(
			long messageId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addMessageResources(
			messageId, addGroupPermissions, addGuestPermissions);
	}

	public static void addMessageResources(
			long messageId,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addMessageResources(messageId, modelPermissions);
	}

	public static void addMessageResources(
			com.liferay.message.boards.model.MBMessage message,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addMessageResources(
			message, addGroupPermissions, addGuestPermissions);
	}

	public static void addMessageResources(
			com.liferay.message.boards.model.MBMessage message,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addMessageResources(message, modelPermissions);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addTempAttachment(
				long groupId, long userId, String folderName, String fileName,
				java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTempAttachment(
			groupId, userId, folderName, fileName, inputStream, mimeType);
	}

	/**
	 * Creates a new message-boards message with the primary key. Does not add the message-boards message to the database.
	 *
	 * @param messageId the primary key for the new message-boards message
	 * @return the new message-boards message
	 */
	public static com.liferay.message.boards.model.MBMessage createMBMessage(
		long messageId) {

		return getService().createMBMessage(messageId);
	}

	public static com.liferay.message.boards.model.MBMessage
			deleteDiscussionMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDiscussionMessage(messageId);
	}

	public static void deleteDiscussionMessages(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteDiscussionMessages(className, classPK);
	}

	/**
	 * Deletes the message-boards message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message that was removed
	 * @throws PortalException if a message-boards message with the primary key could not be found
	 */
	public static com.liferay.message.boards.model.MBMessage deleteMBMessage(
			long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteMBMessage(messageId);
	}

	/**
	 * Deletes the message-boards message from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbMessage the message-boards message
	 * @return the message-boards message that was removed
	 */
	public static com.liferay.message.boards.model.MBMessage deleteMBMessage(
		com.liferay.message.boards.model.MBMessage mbMessage) {

		return getService().deleteMBMessage(mbMessage);
	}

	public static com.liferay.message.boards.model.MBMessage deleteMessage(
			long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteMessage(messageId);
	}

	public static com.liferay.message.boards.model.MBMessage deleteMessage(
			com.liferay.message.boards.model.MBMessage message)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteMessage(message);
	}

	public static void deleteMessageAttachment(long messageId, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteMessageAttachment(messageId, fileName);
	}

	public static void deleteMessageAttachments(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteMessageAttachments(messageId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteTempAttachment(
			long groupId, long userId, String folderName, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteTempAttachment(
			groupId, userId, folderName, fileName);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static void emptyMessageAttachments(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().emptyMessageAttachments(messageId);
	}

	public static com.liferay.message.boards.model.MBMessage
			fetchFileEntryMessage(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchFileEntryMessage(fileEntryId);
	}

	public static com.liferay.message.boards.model.MBMessage fetchFirstMessage(
			long threadId, long parentMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchFirstMessage(threadId, parentMessageId);
	}

	public static com.liferay.message.boards.model.MBMessage fetchMBMessage(
		long messageId) {

		return getService().fetchMBMessage(messageId);
	}

	/**
	 * Returns the message-boards message matching the UUID and group.
	 *
	 * @param uuid the message-boards message's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	public static com.liferay.message.boards.model.MBMessage
		fetchMBMessageByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchMBMessageByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getCategoryMessages(
			long groupId, long categoryId, int status, int start, int end) {

		return getService().getCategoryMessages(
			groupId, categoryId, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getCategoryMessages(
			long groupId, long categoryId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBMessage> obc) {

		return getService().getCategoryMessages(
			groupId, categoryId, status, start, end, obc);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getCategoryMessages(long groupId, long categoryId, long threadId) {

		return getService().getCategoryMessages(groupId, categoryId, threadId);
	}

	public static int getCategoryMessagesCount(
		long groupId, long categoryId, int status) {

		return getService().getCategoryMessagesCount(
			groupId, categoryId, status);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getChildMessages(long parentMessageId, int status) {

		return getService().getChildMessages(parentMessageId, status);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getChildMessages(long parentMessageId, int status, int start, int end) {

		return getService().getChildMessages(
			parentMessageId, status, start, end);
	}

	public static int getChildMessagesCount(long parentMessageId, int status) {
		return getService().getChildMessagesCount(parentMessageId, status);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getCompanyMessages(long companyId, int status, int start, int end) {

		return getService().getCompanyMessages(companyId, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getCompanyMessages(
			long companyId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBMessage> obc) {

		return getService().getCompanyMessages(
			companyId, status, start, end, obc);
	}

	public static int getCompanyMessagesCount(long companyId, int status) {
		return getService().getCompanyMessagesCount(companyId, status);
	}

	public static com.liferay.message.boards.model.MBMessageDisplay
			getDiscussionMessageDisplay(
				long userId, long groupId, String className, long classPK,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDiscussionMessageDisplay(
			userId, groupId, className, classPK, status);
	}

	public static com.liferay.message.boards.model.MBMessageDisplay
			getDiscussionMessageDisplay(
				long userId, long groupId, String className, long classPK,
				int status,
				java.util.Comparator<com.liferay.message.boards.model.MBMessage>
					comparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDiscussionMessageDisplay(
			userId, groupId, className, classPK, status, comparator);
	}

	public static int getDiscussionMessagesCount(
		long classNameId, long classPK, int status) {

		return getService().getDiscussionMessagesCount(
			classNameId, classPK, status);
	}

	public static int getDiscussionMessagesCount(
		String className, long classPK, int status) {

		return getService().getDiscussionMessagesCount(
			className, classPK, status);
	}

	public static java.util.List<com.liferay.message.boards.model.MBDiscussion>
		getDiscussions(String className) {

		return getService().getDiscussions(className);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.message.boards.model.MBMessage
			getFileEntryMessage(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFileEntryMessage(fileEntryId);
	}

	public static com.liferay.message.boards.model.MBMessage getFirstMessage(
			long threadId, long parentMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFirstMessage(threadId, parentMessageId);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getGroupMessages(long groupId, int status, int start, int end) {

		return getService().getGroupMessages(groupId, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getGroupMessages(
			long groupId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBMessage> obc) {

		return getService().getGroupMessages(groupId, status, start, end, obc);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getGroupMessages(
			long groupId, long userId, int status, int start, int end) {

		return getService().getGroupMessages(
			groupId, userId, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getGroupMessages(
			long groupId, long userId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBMessage> obc) {

		return getService().getGroupMessages(
			groupId, userId, status, start, end, obc);
	}

	public static int getGroupMessagesCount(long groupId, int status) {
		return getService().getGroupMessagesCount(groupId, status);
	}

	public static int getGroupMessagesCount(
		long groupId, long userId, int status) {

		return getService().getGroupMessagesCount(groupId, userId, status);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static com.liferay.message.boards.model.MBMessage
			getLastThreadMessage(long threadId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLastThreadMessage(threadId, status);
	}

	/**
	 * Returns the message-boards message with the primary key.
	 *
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message
	 * @throws PortalException if a message-boards message with the primary key could not be found
	 */
	public static com.liferay.message.boards.model.MBMessage getMBMessage(
			long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMBMessage(messageId);
	}

	/**
	 * Returns the message-boards message matching the UUID and group.
	 *
	 * @param uuid the message-boards message's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message-boards message
	 * @throws PortalException if a matching message-boards message could not be found
	 */
	public static com.liferay.message.boards.model.MBMessage
			getMBMessageByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMBMessageByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the message-boards messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of message-boards messages
	 */
	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getMBMessages(int start, int end) {

		return getService().getMBMessages(start, end);
	}

	/**
	 * Returns all the message-boards messages matching the UUID and company.
	 *
	 * @param uuid the UUID of the message-boards messages
	 * @param companyId the primary key of the company
	 * @return the matching message-boards messages, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getMBMessagesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getMBMessagesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of message-boards messages matching the UUID and company.
	 *
	 * @param uuid the UUID of the message-boards messages
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching message-boards messages, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getMBMessagesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBMessage>
					orderByComparator) {

		return getService().getMBMessagesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of message-boards messages.
	 *
	 * @return the number of message-boards messages
	 */
	public static int getMBMessagesCount() {
		return getService().getMBMessagesCount();
	}

	public static com.liferay.message.boards.model.MBMessage getMessage(
			long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMessage(messageId);
	}

	public static com.liferay.message.boards.model.MBMessageDisplay
			getMessageDisplay(long userId, long messageId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMessageDisplay(userId, messageId, status);
	}

	public static com.liferay.message.boards.model.MBMessageDisplay
			getMessageDisplay(
				long userId, com.liferay.message.boards.model.MBMessage message,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMessageDisplay(userId, message, status);
	}

	public static com.liferay.message.boards.model.MBMessageDisplay
			getMessageDisplay(
				long userId, com.liferay.message.boards.model.MBMessage message,
				int status,
				java.util.Comparator<com.liferay.message.boards.model.MBMessage>
					comparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMessageDisplay(
			userId, message, status, comparator);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getMessages(String className, long classPK, int status) {

		return getService().getMessages(className, classPK, status);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getNoAssetMessages() {

		return getService().getNoAssetMessages();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static int getPositionInThread(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPositionInThread(messageId);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
			getRootDiscussionMessages(
				String className, long classPK, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRootDiscussionMessages(
			className, classPK, status);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
			getRootDiscussionMessages(
				String className, long classPK, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRootDiscussionMessages(
			className, classPK, status, start, end);
	}

	public static int getRootDiscussionMessagesCount(
		String className, long classPK, int status) {

		return getService().getRootDiscussionMessagesCount(
			className, classPK, status);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #getRootDiscussionMessages(String, long, int)}
	 */
	@Deprecated
	public static java.util.List<com.liferay.message.boards.model.MBMessage>
			getRootMessages(String className, long classPK, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRootMessages(className, classPK, status);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #getRootDiscussionMessages(String, long, int, int, int)}
	 */
	@Deprecated
	public static java.util.List<com.liferay.message.boards.model.MBMessage>
			getRootMessages(
				String className, long classPK, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRootMessages(
			className, classPK, status, start, end);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #getRootDiscussionMessagesCount(String, long, int)}
	 */
	@Deprecated
	public static int getRootMessagesCount(
		String className, long classPK, int status) {

		return getService().getRootMessagesCount(className, classPK, status);
	}

	public static String[] getTempAttachmentNames(
			long groupId, long userId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTempAttachmentNames(groupId, userId, folderName);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getThreadMessages(long threadId, int status) {

		return getService().getThreadMessages(threadId, status);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getThreadMessages(
			long threadId, int status,
			java.util.Comparator<com.liferay.message.boards.model.MBMessage>
				comparator) {

		return getService().getThreadMessages(threadId, status, comparator);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getThreadMessages(long threadId, int status, int start, int end) {

		return getService().getThreadMessages(threadId, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getThreadMessages(long threadId, long parentMessageId) {

		return getService().getThreadMessages(threadId, parentMessageId);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getThreadMessages(
			long userId, long threadId, int status, int start, int end,
			java.util.Comparator<com.liferay.message.boards.model.MBMessage>
				comparator) {

		return getService().getThreadMessages(
			userId, threadId, status, start, end, comparator);
	}

	public static int getThreadMessagesCount(long threadId, boolean answer) {
		return getService().getThreadMessagesCount(threadId, answer);
	}

	public static int getThreadMessagesCount(long threadId, int status) {
		return getService().getThreadMessagesCount(threadId, status);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getThreadRepliesMessages(
			long threadId, int status, int start, int end) {

		return getService().getThreadRepliesMessages(
			threadId, status, start, end);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getUserDiscussionMessages(
			long userId, long classNameId, long classPK, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBMessage> obc) {

		return getService().getUserDiscussionMessages(
			userId, classNameId, classPK, status, start, end, obc);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getUserDiscussionMessages(
			long userId, long[] classNameIds, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBMessage> obc) {

		return getService().getUserDiscussionMessages(
			userId, classNameIds, status, start, end, obc);
	}

	public static java.util.List<com.liferay.message.boards.model.MBMessage>
		getUserDiscussionMessages(
			long userId, String className, long classPK, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBMessage> obc) {

		return getService().getUserDiscussionMessages(
			userId, className, classPK, status, start, end, obc);
	}

	public static int getUserDiscussionMessagesCount(
		long userId, long classNameId, long classPK, int status) {

		return getService().getUserDiscussionMessagesCount(
			userId, classNameId, classPK, status);
	}

	public static int getUserDiscussionMessagesCount(
		long userId, long[] classNameIds, int status) {

		return getService().getUserDiscussionMessagesCount(
			userId, classNameIds, status);
	}

	public static int getUserDiscussionMessagesCount(
		long userId, String className, long classPK, int status) {

		return getService().getUserDiscussionMessagesCount(
			userId, className, classPK, status);
	}

	public static long moveMessageAttachmentToTrash(
			long userId, long messageId, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().moveMessageAttachmentToTrash(
			userId, messageId, fileName);
	}

	public static void restoreMessageAttachmentFromTrash(
			long userId, long messageId, String deletedFileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().restoreMessageAttachmentFromTrash(
			userId, messageId, deletedFileName);
	}

	public static void subscribeMessage(long userId, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().subscribeMessage(userId, messageId);
	}

	public static void unsubscribeMessage(long userId, long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unsubscribeMessage(userId, messageId);
	}

	public static void updateAnswer(
			long messageId, boolean answer, boolean cascade)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateAnswer(messageId, answer, cascade);
	}

	public static void updateAnswer(
			com.liferay.message.boards.model.MBMessage message, boolean answer,
			boolean cascade)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateAnswer(message, answer, cascade);
	}

	public static void updateAsset(
			long userId, com.liferay.message.boards.model.MBMessage message,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateAsset(
			userId, message, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	public static com.liferay.message.boards.model.MBMessage
			updateDiscussionMessage(
				long userId, long messageId, String className, long classPK,
				String subject, String body,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDiscussionMessage(
			userId, messageId, className, classPK, subject, body,
			serviceContext);
	}

	/**
	 * Updates the message-boards message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mbMessage the message-boards message
	 * @return the message-boards message that was updated
	 */
	public static com.liferay.message.boards.model.MBMessage updateMBMessage(
		com.liferay.message.boards.model.MBMessage mbMessage) {

		return getService().updateMBMessage(mbMessage);
	}

	public static com.liferay.message.boards.model.MBMessage updateMessage(
			long userId, long messageId, String body,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateMessage(
			userId, messageId, body, serviceContext);
	}

	public static com.liferay.message.boards.model.MBMessage updateMessage(
			long userId, long messageId, String subject, String body,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair
					<String, java.io.InputStream>> inputStreamOVPs,
			double priority, boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateMessage(
			userId, messageId, subject, body, inputStreamOVPs, priority,
			allowPingbacks, serviceContext);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #updateMessage(long,
	 long, String, String, List, double, boolean, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.message.boards.model.MBMessage updateMessage(
			long userId, long messageId, String subject, String body,
			java.util.List
				<com.liferay.portal.kernel.util.ObjectValuePair
					<String, java.io.InputStream>> inputStreamOVPs,
			java.util.List<String> existingFiles, double priority,
			boolean allowPingbacks,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateMessage(
			userId, messageId, subject, body, inputStreamOVPs, existingFiles,
			priority, allowPingbacks, serviceContext);
	}

	public static com.liferay.message.boards.model.MBMessage updateStatus(
			long userId, long messageId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, messageId, status, serviceContext, workflowContext);
	}

	public static void updateUserName(long userId, String userName) {
		getService().updateUserName(userId, userName);
	}

	public static MBMessageLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MBMessageLocalService, MBMessageLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MBMessageLocalService.class);

		ServiceTracker<MBMessageLocalService, MBMessageLocalService>
			serviceTracker =
				new ServiceTracker
					<MBMessageLocalService, MBMessageLocalService>(
						bundle.getBundleContext(), MBMessageLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}