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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBThreadLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThreadLocalService
 * @generated
 */
public class MBThreadLocalServiceWrapper implements MBThreadLocalService,
	ServiceWrapper<MBThreadLocalService> {
	public MBThreadLocalServiceWrapper(
		MBThreadLocalService mbThreadLocalService) {
		_mbThreadLocalService = mbThreadLocalService;
	}

	/**
	* Adds the message boards thread to the database. Also notifies the appropriate model listeners.
	*
	* @param mbThread the message boards thread
	* @return the message boards thread that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread addMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.addMBThread(mbThread);
	}

	/**
	* Creates a new message boards thread with the primary key. Does not add the message boards thread to the database.
	*
	* @param threadId the primary key for the new message boards thread
	* @return the new message boards thread
	*/
	public com.liferay.portlet.messageboards.model.MBThread createMBThread(
		long threadId) {
		return _mbThreadLocalService.createMBThread(threadId);
	}

	/**
	* Deletes the message boards thread with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param threadId the primary key of the message boards thread
	* @return the message boards thread that was removed
	* @throws PortalException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread deleteMBThread(
		long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.deleteMBThread(threadId);
	}

	/**
	* Deletes the message boards thread from the database. Also notifies the appropriate model listeners.
	*
	* @param mbThread the message boards thread
	* @return the message boards thread that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread deleteMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.deleteMBThread(mbThread);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mbThreadLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.dynamicQuery(dynamicQuery);
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.dynamicQuery(dynamicQuery, start, end);
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
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.messageboards.model.MBThread fetchMBThread(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.fetchMBThread(threadId);
	}

	/**
	* Returns the message boards thread with the primary key.
	*
	* @param threadId the primary key of the message boards thread
	* @return the message boards thread
	* @throws PortalException if a message boards thread with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread getMBThread(
		long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getMBThread(threadId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the message boards thread matching the UUID and group.
	*
	* @param uuid the message boards thread's UUID
	* @param groupId the primary key of the group
	* @return the matching message boards thread
	* @throws PortalException if a matching message boards thread could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread getMBThreadByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getMBThreadByUuidAndGroupId(uuid, groupId);
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
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getMBThreads(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getMBThreads(start, end);
	}

	/**
	* Returns the number of message boards threads.
	*
	* @return the number of message boards threads
	* @throws SystemException if a system exception occurred
	*/
	public int getMBThreadsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getMBThreadsCount();
	}

	/**
	* Updates the message boards thread in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mbThread the message boards thread
	* @return the message boards thread that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBThread updateMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.updateMBThread(mbThread);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _mbThreadLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_mbThreadLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.messageboards.model.MBThread addThread(
		long categoryId,
		com.liferay.portlet.messageboards.model.MBMessage message,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.addThread(categoryId, message,
			serviceContext);
	}

	public void deleteThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.deleteThread(threadId);
	}

	public void deleteThread(
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.deleteThread(thread);
	}

	public void deleteThreads(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.deleteThreads(groupId, categoryId);
	}

	public void deleteThreads(long groupId, long categoryId,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.deleteThreads(groupId, categoryId,
			includeTrashedEntries);
	}

	public com.liferay.portlet.messageboards.model.MBThread fetchThread(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.fetchThread(threadId);
	}

	public int getCategoryThreadsCount(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getCategoryThreadsCount(groupId,
			categoryId, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long,
	QueryDefinition)}
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, status, start, end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, boolean subscribed,
		boolean includeAnonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, userId,
			subscribed, includeAnonymous, queryDefinition);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, boolean subscribed,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, userId,
			subscribed, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long, long,
	boolean, boolean, QueryDefinition)}
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, userId, status,
			subscribed, includeAnonymous, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long, long,
	boolean, QueryDefinition)}
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, boolean subscribed, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, userId, status,
			subscribed, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreads(long, long,
	QueryDefinition)}
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, userId, status,
			start, end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, userId,
			queryDefinition);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	QueryDefinition)}
	*/
	public int getGroupThreadsCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, status);
	}

	public int getGroupThreadsCount(long groupId, long userId,
		boolean subscribed, boolean includeAnonymous,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, userId,
			subscribed, includeAnonymous, queryDefinition);
	}

	public int getGroupThreadsCount(long groupId, long userId,
		boolean subscribed,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, userId,
			subscribed, queryDefinition);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	long, QueryDefinition)}
	*/
	public int getGroupThreadsCount(long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, userId,
			status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	long, boolean, QueryDefinition)}
	*/
	public int getGroupThreadsCount(long groupId, long userId, int status,
		boolean subscribed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, userId,
			status, subscribed);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupThreadsCount(long,
	long, boolean, boolean, QueryDefinition)}
	*/
	public int getGroupThreadsCount(long groupId, long userId, int status,
		boolean subscribed, boolean includeAnonymous)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, userId,
			status, subscribed, includeAnonymous);
	}

	public int getGroupThreadsCount(long groupId, long userId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, userId,
			queryDefinition);
	}

	public int getGroupThreadsCount(long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition queryDefinition)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId,
			queryDefinition);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getNoAssetThreads()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getNoAssetThreads();
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getPriorityThreads(
		long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getPriorityThreads(categoryId, priority);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getPriorityThreads(
		long categoryId, double priority, boolean inherit)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getPriorityThreads(categoryId, priority,
			inherit);
	}

	public com.liferay.portlet.messageboards.model.MBThread getThread(
		long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getThread(threadId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getThreads(groupId, categoryId, status,
			start, end);
	}

	public int getThreadsCount(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getThreadsCount(groupId, categoryId, status);
	}

	public boolean hasAnswerMessage(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.hasAnswerMessage(threadId);
	}

	public com.liferay.portlet.messageboards.model.MBThread incrementViewCounter(
		long threadId, int increment)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.incrementViewCounter(threadId, increment);
	}

	public com.liferay.portlet.messageboards.model.MBThread moveThread(
		long groupId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.moveThread(groupId, categoryId, threadId);
	}

	public com.liferay.portlet.messageboards.model.MBThread moveThreadFromTrash(
		long userId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.moveThreadFromTrash(userId, categoryId,
			threadId);
	}

	public void moveThreadsToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.moveThreadsToTrash(groupId, userId);
	}

	public com.liferay.portlet.messageboards.model.MBThread moveThreadToTrash(
		long userId, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.moveThreadToTrash(userId, entryId);
	}

	public com.liferay.portlet.messageboards.model.MBThread moveThreadToTrash(
		long userId, com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.moveThreadToTrash(userId, thread);
	}

	public void restoreThreadFromTrash(long userId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.restoreThreadFromTrash(userId, threadId);
	}

	public com.liferay.portlet.messageboards.model.MBThread splitThread(
		long messageId, java.lang.String subject,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.splitThread(messageId, subject,
			serviceContext);
	}

	public void updateQuestion(long threadId, boolean question)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.updateQuestion(threadId, question);
	}

	public com.liferay.portlet.messageboards.model.MBThread updateStatus(
		long userId, long threadId, int status, int categoryStatus)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.updateStatus(userId, threadId, status,
			categoryStatus);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #incrementViewCounter(long,
	int)}
	*/
	public com.liferay.portlet.messageboards.model.MBThread updateThread(
		long threadId, int viewCount)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.updateThread(threadId, viewCount);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public MBThreadLocalService getWrappedMBThreadLocalService() {
		return _mbThreadLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedMBThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {
		_mbThreadLocalService = mbThreadLocalService;
	}

	public MBThreadLocalService getWrappedService() {
		return _mbThreadLocalService;
	}

	public void setWrappedService(MBThreadLocalService mbThreadLocalService) {
		_mbThreadLocalService = mbThreadLocalService;
	}

	private MBThreadLocalService _mbThreadLocalService;
}