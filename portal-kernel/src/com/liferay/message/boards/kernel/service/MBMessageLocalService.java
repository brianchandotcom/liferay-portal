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

package com.liferay.message.boards.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageDisplay;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Provides the local service interface for MBMessage. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageLocalServiceUtil
 * @see com.liferay.portlet.messageboards.service.base.MBMessageLocalServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBMessageLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MBMessageLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBMessageLocalServiceUtil} to access the message-boards message local service. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBMessageLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public MBMessage addDiscussionMessage(long userId,
		java.lang.String userName, long groupId, java.lang.String className,
		long classPK, int workflowAction) throws PortalException;

	public MBMessage addDiscussionMessage(long userId,
		java.lang.String userName, long groupId, java.lang.String className,
		long classPK, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Adds the message-boards message to the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessage the message-boards message
	* @return the message-boards message that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public MBMessage addMBMessage(MBMessage mbMessage);

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addMessage(long, String,
	long, long, String, String, ServiceContext)}
	*/
	@java.lang.Deprecated
	public MBMessage addMessage(long userId, java.lang.String userName,
		long categoryId, java.lang.String subject, java.lang.String body,
		ServiceContext serviceContext) throws PortalException;

	public MBMessage addMessage(long userId, java.lang.String userName,
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body, ServiceContext serviceContext)
		throws PortalException;

	public MBMessage addMessage(long userId, java.lang.String userName,
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body, java.lang.String format,
		java.lang.String fileName, File file, boolean anonymous,
		double priority, boolean allowPingbacks, ServiceContext serviceContext)
		throws PortalException, FileNotFoundException;

	public MBMessage addMessage(long userId, java.lang.String userName,
		long groupId, long categoryId, java.lang.String subject,
		java.lang.String body, java.lang.String format,
		List<ObjectValuePair<java.lang.String, InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		ServiceContext serviceContext) throws PortalException;

	public MBMessage addMessage(long userId, java.lang.String userName,
		long groupId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.lang.String format,
		List<ObjectValuePair<java.lang.String, InputStream>> inputStreamOVPs,
		boolean anonymous, double priority, boolean allowPingbacks,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new message-boards message with the primary key. Does not add the message-boards message to the database.
	*
	* @param messageId the primary key for the new message-boards message
	* @return the new message-boards message
	*/
	public MBMessage createMBMessage(long messageId);

	@Indexable(type = IndexableType.DELETE)
	public MBMessage deleteDiscussionMessage(long messageId)
		throws PortalException;

	/**
	* Deletes the message-boards message from the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessage the message-boards message
	* @return the message-boards message that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public MBMessage deleteMBMessage(MBMessage mbMessage);

	/**
	* Deletes the message-boards message with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param messageId the primary key of the message-boards message
	* @return the message-boards message that was removed
	* @throws PortalException if a message-boards message with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public MBMessage deleteMBMessage(long messageId) throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public MBMessage deleteMessage(MBMessage message) throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	public MBMessage deleteMessage(long messageId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessage fetchMBMessage(long messageId);

	/**
	* Returns the message-boards message matching the UUID and group.
	*
	* @param uuid the message-boards message's UUID
	* @param groupId the primary key of the group
	* @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessage fetchMBMessageByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	/**
	* Returns the message-boards message with the primary key.
	*
	* @param messageId the primary key of the message-boards message
	* @return the message-boards message
	* @throws PortalException if a message-boards message with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessage getMBMessage(long messageId) throws PortalException;

	/**
	* Returns the message-boards message matching the UUID and group.
	*
	* @param uuid the message-boards message's UUID
	* @param groupId the primary key of the group
	* @return the matching message-boards message
	* @throws PortalException if a matching message-boards message could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessage getMBMessageByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessage getMessage(long messageId) throws PortalException;

	public MBMessage updateDiscussionMessage(long userId, long messageId,
		java.lang.String className, long classPK, java.lang.String subject,
		java.lang.String body, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the message-boards message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbMessage the message-boards message
	* @return the message-boards message that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public MBMessage updateMBMessage(MBMessage mbMessage);

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated
	public MBMessage updateMessage(long messageId, java.lang.String body)
		throws PortalException;

	public MBMessage updateMessage(long userId, long messageId,
		java.lang.String body, ServiceContext serviceContext)
		throws PortalException;

	public MBMessage updateMessage(long userId, long messageId,
		java.lang.String subject, java.lang.String body,
		List<ObjectValuePair<java.lang.String, InputStream>> inputStreamOVPs,
		List<java.lang.String> existingFiles, double priority,
		boolean allowPingbacks, ServiceContext serviceContext)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, long,
	int, ServiceContext, Map)}
	*/
	@java.lang.Deprecated
	public MBMessage updateStatus(long userId, long messageId, int status,
		ServiceContext serviceContext) throws PortalException;

	public MBMessage updateStatus(long userId, long messageId, int status,
		ServiceContext serviceContext,
		Map<java.lang.String, Serializable> workflowContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessageDisplay getDiscussionMessageDisplay(long userId,
		long groupId, java.lang.String className, long classPK, int status)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#getDiscussionMessageDisplay(long, long, String, long, int)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessageDisplay getDiscussionMessageDisplay(long userId,
		long groupId, java.lang.String className, long classPK, int status,
		java.lang.String threadView) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessageDisplay getDiscussionMessageDisplay(long userId,
		long groupId, java.lang.String className, long classPK, int status,
		Comparator<MBMessage> comparator) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessageDisplay getMessageDisplay(long userId, MBMessage message,
		int status) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getMessageDisplay(long,
	MBMessage, int)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessageDisplay getMessageDisplay(long userId, MBMessage message,
		int status, java.lang.String threadView, boolean includePrevAndNext)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getMessageDisplay(long,
	MBMessage, int, Comparator)} (
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessageDisplay getMessageDisplay(long userId, MBMessage message,
		int status, java.lang.String threadView, boolean includePrevAndNext,
		Comparator<MBMessage> comparator) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessageDisplay getMessageDisplay(long userId, MBMessage message,
		int status, Comparator<MBMessage> comparator) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessageDisplay getMessageDisplay(long userId, long messageId,
		int status) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getMessageDisplay(long,
	long, int)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MBMessageDisplay getMessageDisplay(long userId, long messageId,
		int status, java.lang.String threadView, boolean includePrevAndNext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoryMessagesCount(long groupId, long categoryId,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyMessagesCount(long companyId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDiscussionMessagesCount(java.lang.String className,
		long classPK, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDiscussionMessagesCount(long classNameId, long classPK,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupMessagesCount(long groupId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupMessagesCount(long groupId, long userId, int status);

	/**
	* Returns the number of message-boards messages.
	*
	* @return the number of message-boards messages
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMBMessagesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPositionInThread(long messageId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getThreadMessagesCount(long threadId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserDiscussionMessagesCount(long userId,
		java.lang.String className, long classPK, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserDiscussionMessagesCount(long userId, long classNameId,
		long classPK, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserDiscussionMessagesCount(long userId, long[] classNameIds,
		int status);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getCategoryMessages(long groupId, long categoryId,
		int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getCategoryMessages(long groupId, long categoryId,
		int status, int start, int end, OrderByComparator<MBMessage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getCompanyMessages(long companyId, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getCompanyMessages(long companyId, int status,
		int start, int end, OrderByComparator<MBMessage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBDiscussion> getDiscussions(java.lang.String className);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getGroupMessages(long groupId, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getGroupMessages(long groupId, int status,
		int start, int end, OrderByComparator<MBMessage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getGroupMessages(long groupId, long userId,
		int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getGroupMessages(long groupId, long userId,
		int status, int start, int end, OrderByComparator<MBMessage> obc);

	/**
	* Returns a range of all the message-boards messages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message-boards messages
	* @param end the upper bound of the range of message-boards messages (not inclusive)
	* @return the range of message-boards messages
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getMBMessages(int start, int end);

	/**
	* Returns all the message-boards messages matching the UUID and company.
	*
	* @param uuid the UUID of the message-boards messages
	* @param companyId the primary key of the company
	* @return the matching message-boards messages, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getMBMessagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getMBMessagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getMessages(java.lang.String className,
		long classPK, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getNoAssetMessages();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getThreadMessages(long threadId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getThreadMessages(long threadId, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getThreadMessages(long threadId, int status,
		Comparator<MBMessage> comparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getThreadMessages(long userId, long threadId,
		int status, int start, int end, Comparator<MBMessage> comparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getThreadRepliesMessages(long threadId, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getUserDiscussionMessages(long userId,
		java.lang.String className, long classPK, int status, int start,
		int end, OrderByComparator<MBMessage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getUserDiscussionMessages(long userId,
		long classNameId, long classPK, int status, int start, int end,
		OrderByComparator<MBMessage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MBMessage> getUserDiscussionMessages(long userId,
		long[] classNameIds, int status, int start, int end,
		OrderByComparator<MBMessage> obc);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public long moveMessageAttachmentToTrash(long userId, long messageId,
		java.lang.String fileName) throws PortalException;

	public void addMessageAttachment(long userId, long messageId,
		java.lang.String fileName, File file, java.lang.String mimeType)
		throws PortalException;

	public void addMessageResources(MBMessage message,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addMessageResources(MBMessage message,
		ModelPermissions modelPermissions) throws PortalException;

	public void addMessageResources(long messageId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addMessageResources(long messageId,
		ModelPermissions modelPermissions) throws PortalException;

	public void deleteDiscussionMessages(java.lang.String className,
		long classPK) throws PortalException;

	public void deleteMessageAttachment(long messageId,
		java.lang.String fileName) throws PortalException;

	public void deleteMessageAttachments(long messageId)
		throws PortalException;

	public void emptyMessageAttachments(long messageId)
		throws PortalException;

	public void restoreMessageAttachmentFromTrash(long userId, long messageId,
		java.lang.String deletedFileName) throws PortalException;

	public void subscribeMessage(long userId, long messageId)
		throws PortalException;

	public void unsubscribeMessage(long userId, long messageId)
		throws PortalException;

	public void updateAnswer(MBMessage message, boolean answer, boolean cascade)
		throws PortalException;

	public void updateAnswer(long messageId, boolean answer, boolean cascade)
		throws PortalException;

	public void updateAsset(long userId, MBMessage message,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds) throws PortalException;

	public void updateUserName(long userId, java.lang.String userName);
}