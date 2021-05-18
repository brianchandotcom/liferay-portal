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

import com.liferay.mail.reader.model.Message;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for Message. This utility wraps
 * <code>com.liferay.mail.reader.service.impl.MessageLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MessageLocalService
 * @generated
 */
public class MessageLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.mail.reader.service.impl.MessageLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Message addMessage(
			long userId, long folderId, String sender, String to, String cc,
			String bcc, java.util.Date sentDate, String subject, String body,
			String flags, long remoteMessageId, String contentType)
		throws PortalException {

		return getService().addMessage(
			userId, folderId, sender, to, cc, bcc, sentDate, subject, body,
			flags, remoteMessageId, contentType);
	}

	/**
	 * Adds the message to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MessageLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param message the message
	 * @return the message that was added
	 */
	public static Message addMessage(Message message) {
		return getService().addMessage(message);
	}

	/**
	 * Creates a new message with the primary key. Does not add the message to the database.
	 *
	 * @param messageId the primary key for the new message
	 * @return the new message
	 */
	public static Message createMessage(long messageId) {
		return getService().createMessage(messageId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MessageLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param messageId the primary key of the message
	 * @return the message that was removed
	 * @throws PortalException if a message with the primary key could not be found
	 */
	public static Message deleteMessage(long messageId) throws PortalException {
		return getService().deleteMessage(messageId);
	}

	/**
	 * Deletes the message from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MessageLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param message the message
	 * @return the message that was removed
	 * @throws PortalException
	 */
	public static Message deleteMessage(Message message)
		throws PortalException {

		return getService().deleteMessage(message);
	}

	public static void deleteMessages(long folderId) throws PortalException {
		getService().deleteMessages(folderId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static Message fetchMessage(long messageId) {
		return getService().fetchMessage(messageId);
	}

	public static int getAccountUnreadMessagesCount(long accountId) {
		return getService().getAccountUnreadMessagesCount(accountId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<Message> getCompanyMessages(
		long companyId, int start, int end) {

		return getService().getCompanyMessages(companyId, start, end);
	}

	public static int getCompanyMessagesCount(long companyId) {
		return getService().getCompanyMessagesCount(companyId);
	}

	public static List<Message> getFolderMessages(long folderId) {
		return getService().getFolderMessages(folderId);
	}

	public static int getFolderMessagesCount(long folderId) {
		return getService().getFolderMessagesCount(folderId);
	}

	public static int getFolderUnreadMessagesCount(long folderId) {
		return getService().getFolderUnreadMessagesCount(folderId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the message with the primary key.
	 *
	 * @param messageId the primary key of the message
	 * @return the message
	 * @throws PortalException if a message with the primary key could not be found
	 */
	public static Message getMessage(long messageId) throws PortalException {
		return getService().getMessage(messageId);
	}

	public static Message getMessage(long folderId, long remoteMessageId)
		throws PortalException {

		return getService().getMessage(folderId, remoteMessageId);
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
	public static List<Message> getMessages(int start, int end) {
		return getService().getMessages(start, end);
	}

	/**
	 * Returns the number of messages.
	 *
	 * @return the number of messages
	 */
	public static int getMessagesCount() {
		return getService().getMessagesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static Message getRemoteMessage(long folderId, boolean oldest)
		throws PortalException {

		return getService().getRemoteMessage(folderId, oldest);
	}

	public static int populateMessages(
		List<Message> messages, long folderId, String keywords, int pageNumber,
		int messagesPerPage, String orderByField, String orderByType) {

		return getService().populateMessages(
			messages, folderId, keywords, pageNumber, messagesPerPage,
			orderByField, orderByType);
	}

	public static Message updateContent(
			long messageId, String body, String flags)
		throws PortalException {

		return getService().updateContent(messageId, body, flags);
	}

	public static Message updateFlag(long messageId, int flag, boolean value)
		throws PortalException {

		return getService().updateFlag(messageId, flag, value);
	}

	public static Message updateMessage(
			long messageId, long folderId, String sender, String to, String cc,
			String bcc, java.util.Date sentDate, String subject, String body,
			String flags, long remoteMessageId)
		throws PortalException {

		return getService().updateMessage(
			messageId, folderId, sender, to, cc, bcc, sentDate, subject, body,
			flags, remoteMessageId);
	}

	/**
	 * Updates the message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MessageLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param message the message
	 * @return the message that was updated
	 */
	public static Message updateMessage(Message message) {
		return getService().updateMessage(message);
	}

	public static MessageLocalService getService() {
		return _service;
	}

	private static volatile MessageLocalService _service;

}