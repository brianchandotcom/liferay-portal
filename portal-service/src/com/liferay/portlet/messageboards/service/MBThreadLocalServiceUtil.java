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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for MBThread. This utility wraps
 * {@link com.liferay.portlet.messageboards.service.impl.MBThreadLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadLocalService
 * @see com.liferay.portlet.messageboards.service.base.MBThreadLocalServiceBaseImpl
 * @see com.liferay.portlet.messageboards.service.impl.MBThreadLocalServiceImpl
 * @generated
 */
@ProviderType
public class MBThreadLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.messageboards.service.impl.MBThreadLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the message boards thread to the database. Also notifies the appropriate model listeners.
	*
	* @param mbThread the message boards thread
	* @return the message boards thread that was added
	*/
	public static com.liferay.portlet.messageboards.model.MBThread addMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread) {
		return getService().addMBThread(mbThread);
	}

	public static com.liferay.portlet.messageboards.model.MBThread addThread(
		long categoryId,
		com.liferay.portlet.messageboards.model.MBMessage message,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addThread(categoryId, message, serviceContext);
	}

	/**
	* Creates a new message boards thread with the primary key. Does not add the message boards thread to the database.
	*
	* @param threadId the primary key for the new message boards thread
	* @return the new message boards thread
	*/
	public static com.liferay.portlet.messageboards.model.MBThread createMBThread(
		long threadId) {
		return getService().createMBThread(threadId);
	}

	/**
	* Deletes the message boards thread from the database. Also notifies the appropriate model listeners.
	*
	* @param mbThread the message boards thread
	* @return the message boards thread that was removed
	*/
	public static com.liferay.portlet.messageboards.model.MBThread deleteMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread) {
		return getService().deleteMBThread(mbThread);
	}

	/**
	* Deletes the message boards thread with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param threadId the primary key of the message boards thread
	* @return the message boards thread that was removed
	* @throws PortalException if a message boards thread with the primary key could not be found
	*/
	public static com.liferay.portlet.messageboards.model.MBThread deleteMBThread(
		long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMBThread(threadId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteThread(
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteThread(thread);
	}

	public static void deleteThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteThread(threadId);
	}

	public static void deleteThreads(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteThreads(groupId, categoryId);
	}

	public static void deleteThreads(long groupId, long categoryId,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteThreads(groupId, categoryId, includeTrashedEntries);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.portlet.messageboards.model.MBThread fetchMBThread(
		long threadId) {
		return getService().fetchMBThread(threadId);
	}

	/**
	* Returns the message boards thread matching the UUID and group.
	*
	* @param uuid the message boards thread's UUID
	* @param groupId the primary key of the group
	* @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	*/
	public static com.liferay.portlet.messageboards.model.MBThread fetchMBThreadByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchMBThreadByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread fetchThread(
		long threadId) {
		return getService().fetchThread(threadId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static int getCategoryThreadsCount(long groupId, long categoryId,
		int status) {
		return getService().getCategoryThreadsCount(groupId, categoryId, status);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getService().getGroupThreads(groupId, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long,
	QueryDefinition)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, int status, int start, int end) {
		return getService().getGroupThreads(groupId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getService().getGroupThreads(groupId, userId, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long, long,
	QueryDefinition)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, int start, int end) {
		return getService().getGroupThreads(groupId, userId, status, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long, long,
	boolean, boolean, QueryDefinition)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous, int start, int end) {
		return getService()
				   .getGroupThreads(groupId, userId, status, subscribed,
			includeAnonymous, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long, long,
	boolean, QueryDefinition)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, boolean subscribed, int start,
		int end) {
		return getService()
				   .getGroupThreads(groupId, userId, status, subscribed, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, boolean subscribed,
		boolean includeAnonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getService()
				   .getGroupThreads(groupId, userId, subscribed,
			includeAnonymous, queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, boolean subscribed,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getService()
				   .getGroupThreads(groupId, userId, subscribed, queryDefinition);
	}

	public static int getGroupThreadsCount(long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getService().getGroupThreadsCount(groupId, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	QueryDefinition)}
	*/
	@Deprecated
	public static int getGroupThreadsCount(long groupId, int status) {
		return getService().getGroupThreadsCount(groupId, status);
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getService()
				   .getGroupThreadsCount(groupId, userId, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	long, QueryDefinition)}
	*/
	@Deprecated
	public static int getGroupThreadsCount(long groupId, long userId, int status) {
		return getService().getGroupThreadsCount(groupId, userId, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	long, boolean, QueryDefinition)}
	*/
	@Deprecated
	public static int getGroupThreadsCount(long groupId, long userId,
		int status, boolean subscribed) {
		return getService()
				   .getGroupThreadsCount(groupId, userId, status, subscribed);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	long, boolean, boolean, QueryDefinition)}
	*/
	@Deprecated
	public static int getGroupThreadsCount(long groupId, long userId,
		int status, boolean subscribed, boolean includeAnonymous) {
		return getService()
				   .getGroupThreadsCount(groupId, userId, status, subscribed,
			includeAnonymous);
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		boolean subscribed, boolean includeAnonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getService()
				   .getGroupThreadsCount(groupId, userId, subscribed,
			includeAnonymous, queryDefinition);
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		boolean subscribed,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.messageboards.model.MBThread> queryDefinition) {
		return getService()
				   .getGroupThreadsCount(groupId, userId, subscribed,
			queryDefinition);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the message boards thread with the primary key.
	*
	* @param threadId the primary key of the message boards thread
	* @return the message boards thread
	* @throws PortalException if a message boards thread with the primary key could not be found
	*/
	public static com.liferay.portlet.messageboards.model.MBThread getMBThread(
		long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMBThread(threadId);
	}

	/**
	* Returns the message boards thread matching the UUID and group.
	*
	* @param uuid the message boards thread's UUID
	* @param groupId the primary key of the group
	* @return the matching message boards thread
	* @throws PortalException if a matching message boards thread could not be found
	*/
	public static com.liferay.portlet.messageboards.model.MBThread getMBThreadByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMBThreadByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the message boards threads.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @return the range of message boards threads
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getMBThreads(
		int start, int end) {
		return getService().getMBThreads(start, end);
	}

	/**
	* Returns all the message boards threads matching the UUID and company.
	*
	* @param uuid the UUID of the message boards threads
	* @param companyId the primary key of the company
	* @return the matching message boards threads, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getMBThreadsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getMBThreadsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of message boards threads matching the UUID and company.
	*
	* @param uuid the UUID of the message boards threads
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of message boards threads
	* @param end the upper bound of the range of message boards threads (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching message boards threads, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getMBThreadsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.messageboards.model.MBThread> orderByComparator) {
		return getService()
				   .getMBThreadsByUuidAndCompanyId(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of message boards threads.
	*
	* @return the number of message boards threads
	*/
	public static int getMBThreadsCount() {
		return getService().getMBThreadsCount();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getNoAssetThreads() {
		return getService().getNoAssetThreads();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getPriorityThreads(
		long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPriorityThreads(categoryId, priority);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getPriorityThreads(
		long categoryId, double priority, boolean inherit)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPriorityThreads(categoryId, priority, inherit);
	}

	public static com.liferay.portlet.messageboards.model.MBThread getThread(
		long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getThread(threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end) {
		return getService().getThreads(groupId, categoryId, status, start, end);
	}

	public static int getThreadsCount(long groupId, long categoryId, int status) {
		return getService().getThreadsCount(groupId, categoryId, status);
	}

	public static boolean hasAnswerMessage(long threadId) {
		return getService().hasAnswerMessage(threadId);
	}

	public static void incrementViewCounter(long threadId, int increment)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().incrementViewCounter(threadId, increment);
	}

	public static void moveDependentsToTrash(long groupId, long threadId,
		long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().moveDependentsToTrash(groupId, threadId, trashEntryId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread moveThread(
		long groupId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveThread(groupId, categoryId, threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread moveThreadFromTrash(
		long userId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveThreadFromTrash(userId, categoryId, threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread moveThreadToTrash(
		long userId, com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveThreadToTrash(userId, thread);
	}

	public static com.liferay.portlet.messageboards.model.MBThread moveThreadToTrash(
		long userId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveThreadToTrash(userId, threadId);
	}

	public static void moveThreadsToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().moveThreadsToTrash(groupId, userId);
	}

	public static void restoreDependentsFromTrash(long groupId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreDependentsFromTrash(groupId, threadId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#restoreDependentsFromTrash(long, long)}
	*/
	@Deprecated
	public static void restoreDependentsFromTrash(long groupId, long threadId,
		long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreDependentsFromTrash(groupId, threadId, trashEntryId);
	}

	public static void restoreThreadFromTrash(long userId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreThreadFromTrash(userId, threadId);
	}

	public static com.liferay.portal.kernel.search.Hits search(long groupId,
		long userId, long creatorUserId, long startDate, long endDate,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(groupId, userId, creatorUserId, startDate, endDate,
			status, start, end);
	}

	public static com.liferay.portal.kernel.search.Hits search(long groupId,
		long userId, long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(groupId, userId, creatorUserId, status, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBThread splitThread(
		long userId, long messageId, java.lang.String subject,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .splitThread(userId, messageId, subject, serviceContext);
	}

	/**
	* Updates the message boards thread in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbThread the message boards thread
	* @return the message boards thread that was updated
	*/
	public static com.liferay.portlet.messageboards.model.MBThread updateMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread) {
		return getService().updateMBThread(mbThread);
	}

	public static com.liferay.portlet.messageboards.model.MBThread updateMessageCount(
		long threadId) {
		return getService().updateMessageCount(threadId);
	}

	public static void updateQuestion(long threadId, boolean question)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateQuestion(threadId, question);
	}

	public static com.liferay.portlet.messageboards.model.MBThread updateStatus(
		long userId, long threadId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateStatus(userId, threadId, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #incrementViewCounter(long,
	int)}
	*/
	@Deprecated
	public static com.liferay.portlet.messageboards.model.MBThread updateThread(
		long threadId, int viewCount)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateThread(threadId, viewCount);
	}

	public static MBThreadLocalService getService() {
		if (_service == null) {
			_service = (MBThreadLocalService)PortalBeanLocatorUtil.locate(MBThreadLocalService.class.getName());

			ReferenceRegistry.registerReference(MBThreadLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(MBThreadLocalService service) {
	}

	private static MBThreadLocalService _service;
}