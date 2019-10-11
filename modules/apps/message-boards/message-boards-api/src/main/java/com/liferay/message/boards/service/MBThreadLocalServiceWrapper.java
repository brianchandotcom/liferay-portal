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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBThreadLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadLocalService
 * @generated
 */
public class MBThreadLocalServiceWrapper
	implements MBThreadLocalService, ServiceWrapper<MBThreadLocalService> {

	public MBThreadLocalServiceWrapper(
		MBThreadLocalService mbThreadLocalService) {

		_mbThreadLocalService = mbThreadLocalService;
	}

	/**
	 * Adds the message boards thread to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbThread the message boards thread
	 * @return the message boards thread that was added
	 */
	@Override
	public com.liferay.message.boards.model.MBThread addMBThread(
		com.liferay.message.boards.model.MBThread mbThread) {

		return _mbThreadLocalService.addMBThread(mbThread);
	}

	@Override
	public com.liferay.message.boards.model.MBThread addThread(
			long categoryId, com.liferay.message.boards.model.MBMessage message,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.addThread(
			categoryId, message, serviceContext);
	}

	/**
	 * Creates a new message boards thread with the primary key. Does not add the message boards thread to the database.
	 *
	 * @param threadId the primary key for the new message boards thread
	 * @return the new message boards thread
	 */
	@Override
	public com.liferay.message.boards.model.MBThread createMBThread(
		long threadId) {

		return _mbThreadLocalService.createMBThread(threadId);
	}

	/**
	 * Deletes the message boards thread with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param threadId the primary key of the message boards thread
	 * @return the message boards thread that was removed
	 * @throws PortalException if a message boards thread with the primary key could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBThread deleteMBThread(
			long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.deleteMBThread(threadId);
	}

	/**
	 * Deletes the message boards thread from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbThread the message boards thread
	 * @return the message boards thread that was removed
	 */
	@Override
	public com.liferay.message.boards.model.MBThread deleteMBThread(
		com.liferay.message.boards.model.MBThread mbThread) {

		return _mbThreadLocalService.deleteMBThread(mbThread);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadLocalService.deleteThread(threadId);
	}

	@Override
	public void deleteThread(com.liferay.message.boards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadLocalService.deleteThread(thread);
	}

	@Override
	public void deleteThreads(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadLocalService.deleteThreads(groupId, categoryId);
	}

	@Override
	public void deleteThreads(
			long groupId, long categoryId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadLocalService.deleteThreads(
			groupId, categoryId, includeTrashedEntries);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mbThreadLocalService.dynamicQuery();
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

		return _mbThreadLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBThreadModelImpl</code>.
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

		return _mbThreadLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBThreadModelImpl</code>.
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

		return _mbThreadLocalService.dynamicQuery(
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

		return _mbThreadLocalService.dynamicQueryCount(dynamicQuery);
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

		return _mbThreadLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.message.boards.model.MBThread fetchMBThread(
		long threadId) {

		return _mbThreadLocalService.fetchMBThread(threadId);
	}

	/**
	 * Returns the message boards thread matching the UUID and group.
	 *
	 * @param uuid the message boards thread's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBThread
		fetchMBThreadByUuidAndGroupId(String uuid, long groupId) {

		return _mbThreadLocalService.fetchMBThreadByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.message.boards.model.MBThread fetchThread(
		long threadId) {

		return _mbThreadLocalService.fetchThread(threadId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _mbThreadLocalService.getActionableDynamicQuery();
	}

	@Override
	public int getCategoryThreadsCount(
		long groupId, long categoryId, int status) {

		return _mbThreadLocalService.getCategoryThreadsCount(
			groupId, categoryId, status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _mbThreadLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread>
		getGroupThreads(
			long groupId, long userId, boolean subscribed,
			boolean includeAnonymous,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreads(
			groupId, userId, subscribed, includeAnonymous, queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread>
		getGroupThreads(
			long groupId, long userId, boolean subscribed,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreads(
			groupId, userId, subscribed, queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread>
		getGroupThreads(
			long groupId, long userId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreads(
			groupId, userId, queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread>
		getGroupThreads(
			long groupId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreads(groupId, queryDefinition);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, boolean subscribed, boolean includeAnonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreadsCount(
			groupId, userId, subscribed, includeAnonymous, queryDefinition);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId, boolean subscribed,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreadsCount(
			groupId, userId, subscribed, queryDefinition);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreadsCount(
			groupId, userId, queryDefinition);
	}

	@Override
	public int getGroupThreadsCount(
		long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.message.boards.model.MBThread> queryDefinition) {

		return _mbThreadLocalService.getGroupThreadsCount(
			groupId, queryDefinition);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbThreadLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the message boards thread with the primary key.
	 *
	 * @param threadId the primary key of the message boards thread
	 * @return the message boards thread
	 * @throws PortalException if a message boards thread with the primary key could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBThread getMBThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.getMBThread(threadId);
	}

	/**
	 * Returns the message boards thread matching the UUID and group.
	 *
	 * @param uuid the message boards thread's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message boards thread
	 * @throws PortalException if a matching message boards thread could not be found
	 */
	@Override
	public com.liferay.message.boards.model.MBThread
			getMBThreadByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.getMBThreadByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the message boards threads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.message.boards.model.impl.MBThreadModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of message boards threads
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread>
		getMBThreads(int start, int end) {

		return _mbThreadLocalService.getMBThreads(start, end);
	}

	/**
	 * Returns all the message boards threads matching the UUID and company.
	 *
	 * @param uuid the UUID of the message boards threads
	 * @param companyId the primary key of the company
	 * @return the matching message boards threads, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread>
		getMBThreadsByUuidAndCompanyId(String uuid, long companyId) {

		return _mbThreadLocalService.getMBThreadsByUuidAndCompanyId(
			uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread>
		getMBThreadsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.message.boards.model.MBThread> orderByComparator) {

		return _mbThreadLocalService.getMBThreadsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of message boards threads.
	 *
	 * @return the number of message boards threads
	 */
	@Override
	public int getMBThreadsCount() {
		return _mbThreadLocalService.getMBThreadsCount();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread>
		getNoAssetThreads() {

		return _mbThreadLocalService.getNoAssetThreads();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mbThreadLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread>
			getPriorityThreads(long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.getPriorityThreads(categoryId, priority);
	}

	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread>
			getPriorityThreads(
				long categoryId, double priority, boolean inherit)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.getPriorityThreads(
			categoryId, priority, inherit);
	}

	@Override
	public com.liferay.message.boards.model.MBThread getThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.getThread(threadId);
	}

	@Override
	public java.util.List<com.liferay.message.boards.model.MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end) {

		return _mbThreadLocalService.getThreads(
			groupId, categoryId, status, start, end);
	}

	@Override
	public int getThreadsCount(long groupId, long categoryId, int status) {
		return _mbThreadLocalService.getThreadsCount(
			groupId, categoryId, status);
	}

	@Override
	public boolean hasAnswerMessage(long threadId) {
		return _mbThreadLocalService.hasAnswerMessage(threadId);
	}

	@Override
	public void incrementViewCounter(long threadId, int increment)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadLocalService.incrementViewCounter(threadId, increment);
	}

	@Override
	public void moveDependentsToTrash(
			long groupId, long threadId, long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadLocalService.moveDependentsToTrash(
			groupId, threadId, trashEntryId);
	}

	@Override
	public com.liferay.message.boards.model.MBThread moveThread(
			long groupId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.moveThread(groupId, categoryId, threadId);
	}

	@Override
	public com.liferay.message.boards.model.MBThread moveThreadFromTrash(
			long userId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.moveThreadFromTrash(
			userId, categoryId, threadId);
	}

	@Override
	public void moveThreadsToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadLocalService.moveThreadsToTrash(groupId, userId);
	}

	@Override
	public com.liferay.message.boards.model.MBThread moveThreadToTrash(
			long userId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.moveThreadToTrash(userId, threadId);
	}

	@Override
	public com.liferay.message.boards.model.MBThread moveThreadToTrash(
			long userId, com.liferay.message.boards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.moveThreadToTrash(userId, thread);
	}

	@Override
	public void restoreDependentsFromTrash(long groupId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadLocalService.restoreDependentsFromTrash(groupId, threadId);
	}

	@Override
	public void restoreThreadFromTrash(long userId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadLocalService.restoreThreadFromTrash(userId, threadId);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
			long groupId, long userId, long creatorUserId, int status,
			int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.search(
			groupId, userId, creatorUserId, status, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
			long groupId, long userId, long creatorUserId, long startDate,
			long endDate, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.search(
			groupId, userId, creatorUserId, startDate, endDate, status, start,
			end);
	}

	@Override
	public com.liferay.message.boards.model.MBThread splitThread(
			long userId, long messageId, String subject,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.splitThread(
			userId, messageId, subject, serviceContext);
	}

	/**
	 * Updates the message boards thread in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mbThread the message boards thread
	 * @return the message boards thread that was updated
	 */
	@Override
	public com.liferay.message.boards.model.MBThread updateMBThread(
		com.liferay.message.boards.model.MBThread mbThread) {

		return _mbThreadLocalService.updateMBThread(mbThread);
	}

	@Override
	public com.liferay.message.boards.model.MBThread updateMessageCount(
		long threadId) {

		return _mbThreadLocalService.updateMessageCount(threadId);
	}

	@Override
	public void updateQuestion(long threadId, boolean question)
		throws com.liferay.portal.kernel.exception.PortalException {

		_mbThreadLocalService.updateQuestion(threadId, question);
	}

	@Override
	public com.liferay.message.boards.model.MBThread updateStatus(
			long userId, long threadId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbThreadLocalService.updateStatus(userId, threadId, status);
	}

	@Override
	public MBThreadLocalService getWrappedService() {
		return _mbThreadLocalService;
	}

	@Override
	public void setWrappedService(MBThreadLocalService mbThreadLocalService) {
		_mbThreadLocalService = mbThreadLocalService;
	}

	private MBThreadLocalService _mbThreadLocalService;

}