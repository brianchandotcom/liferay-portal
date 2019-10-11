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

package com.liferay.mail.reader.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MessageLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MessageLocalService
 * @generated
 */
public class MessageLocalServiceWrapper
	implements MessageLocalService, ServiceWrapper<MessageLocalService> {

	public MessageLocalServiceWrapper(MessageLocalService messageLocalService) {
		_messageLocalService = messageLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MessageLocalServiceUtil} to access the message local service. Add custom service methods to <code>com.liferay.mail.reader.service.impl.MessageLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.mail.reader.model.Message addMessage(
			long userId, long folderId, String sender, String to, String cc,
			String bcc, java.util.Date sentDate, String subject, String body,
			String flags, long remoteMessageId, String contentType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.addMessage(
			userId, folderId, sender, to, cc, bcc, sentDate, subject, body,
			flags, remoteMessageId, contentType);
	}

	/**
	 * Adds the message to the database. Also notifies the appropriate model listeners.
	 *
	 * @param message the message
	 * @return the message that was added
	 */
	@Override
	public com.liferay.mail.reader.model.Message addMessage(
		com.liferay.mail.reader.model.Message message) {

		return _messageLocalService.addMessage(message);
	}

	/**
	 * Creates a new message with the primary key. Does not add the message to the database.
	 *
	 * @param messageId the primary key for the new message
	 * @return the new message
	 */
	@Override
	public com.liferay.mail.reader.model.Message createMessage(long messageId) {
		return _messageLocalService.createMessage(messageId);
	}

	/**
	 * Deletes the message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param messageId the primary key of the message
	 * @return the message that was removed
	 * @throws PortalException if a message with the primary key could not be found
	 */
	@Override
	public com.liferay.mail.reader.model.Message deleteMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.deleteMessage(messageId);
	}

	/**
	 * Deletes the message from the database. Also notifies the appropriate model listeners.
	 *
	 * @param message the message
	 * @return the message that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.mail.reader.model.Message deleteMessage(
			com.liferay.mail.reader.model.Message message)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.deleteMessage(message);
	}

	@Override
	public void deleteMessages(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_messageLocalService.deleteMessages(folderId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _messageLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _messageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.mail.reader.model.impl.MessageModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _messageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.mail.reader.model.impl.MessageModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _messageLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _messageLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _messageLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.mail.reader.model.Message fetchMessage(long messageId) {
		return _messageLocalService.fetchMessage(messageId);
	}

	@Override
	public int getAccountUnreadMessagesCount(long accountId) {
		return _messageLocalService.getAccountUnreadMessagesCount(accountId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _messageLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.mail.reader.model.Message>
		getCompanyMessages(long companyId, int start, int end) {

		return _messageLocalService.getCompanyMessages(companyId, start, end);
	}

	@Override
	public int getCompanyMessagesCount(long companyId) {
		return _messageLocalService.getCompanyMessagesCount(companyId);
	}

	@Override
	public java.util.List<com.liferay.mail.reader.model.Message>
		getFolderMessages(long folderId) {

		return _messageLocalService.getFolderMessages(folderId);
	}

	@Override
	public int getFolderMessagesCount(long folderId) {
		return _messageLocalService.getFolderMessagesCount(folderId);
	}

	@Override
	public int getFolderUnreadMessagesCount(long folderId) {
		return _messageLocalService.getFolderUnreadMessagesCount(folderId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _messageLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the message with the primary key.
	 *
	 * @param messageId the primary key of the message
	 * @return the message
	 * @throws PortalException if a message with the primary key could not be found
	 */
	@Override
	public com.liferay.mail.reader.model.Message getMessage(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.getMessage(messageId);
	}

	@Override
	public com.liferay.mail.reader.model.Message getMessage(
			long folderId, long remoteMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.getMessage(folderId, remoteMessageId);
	}

	/**
	 * Returns a range of all the messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.mail.reader.model.impl.MessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of messages
	 * @param end the upper bound of the range of messages (not inclusive)
	 * @return the range of messages
	 */
	@Override
	public java.util.List<com.liferay.mail.reader.model.Message> getMessages(
		int start, int end) {

		return _messageLocalService.getMessages(start, end);
	}

	/**
	 * Returns the number of messages.
	 *
	 * @return the number of messages
	 */
	@Override
	public int getMessagesCount() {
		return _messageLocalService.getMessagesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _messageLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.mail.reader.model.Message getRemoteMessage(
			long folderId, boolean oldest)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.getRemoteMessage(folderId, oldest);
	}

	@Override
	public int populateMessages(
		java.util.List<com.liferay.mail.reader.model.Message> messages,
		long folderId, String keywords, int pageNumber, int messagesPerPage,
		String orderByField, String orderByType) {

		return _messageLocalService.populateMessages(
			messages, folderId, keywords, pageNumber, messagesPerPage,
			orderByField, orderByType);
	}

	@Override
	public com.liferay.mail.reader.model.Message updateContent(
			long messageId, String body, String flags)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.updateContent(messageId, body, flags);
	}

	@Override
	public com.liferay.mail.reader.model.Message updateFlag(
			long messageId, int flag, boolean value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.updateFlag(messageId, flag, value);
	}

	@Override
	public com.liferay.mail.reader.model.Message updateMessage(
			long messageId, long folderId, String sender, String to, String cc,
			String bcc, java.util.Date sentDate, String subject, String body,
			String flags, long remoteMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _messageLocalService.updateMessage(
			messageId, folderId, sender, to, cc, bcc, sentDate, subject, body,
			flags, remoteMessageId);
	}

	/**
	 * Updates the message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param message the message
	 * @return the message that was updated
	 */
	@Override
	public com.liferay.mail.reader.model.Message updateMessage(
		com.liferay.mail.reader.model.Message message) {

		return _messageLocalService.updateMessage(message);
	}

	@Override
	public MessageLocalService getWrappedService() {
		return _messageLocalService;
	}

	@Override
	public void setWrappedService(MessageLocalService messageLocalService) {
		_messageLocalService = messageLocalService;
	}

	private MessageLocalService _messageLocalService;

}