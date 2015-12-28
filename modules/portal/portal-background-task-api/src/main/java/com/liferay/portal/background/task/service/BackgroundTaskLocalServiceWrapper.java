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

package com.liferay.portal.background.task.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BackgroundTaskLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTaskLocalService
 * @generated
 */
@ProviderType
public class BackgroundTaskLocalServiceWrapper
	implements BackgroundTaskLocalService,
		ServiceWrapper<BackgroundTaskLocalService> {
	public BackgroundTaskLocalServiceWrapper(
		BackgroundTaskLocalService backgroundTaskLocalService) {
		_backgroundTaskLocalService = backgroundTaskLocalService;
	}

	/**
	* Adds the background task to the database. Also notifies the appropriate model listeners.
	*
	* @param backgroundTask the background task
	* @return the background task that was added
	*/
	@Override
	public com.liferay.portal.background.task.model.BackgroundTask addBackgroundTask(
		com.liferay.portal.background.task.model.BackgroundTask backgroundTask) {
		return _backgroundTaskLocalService.addBackgroundTask(backgroundTask);
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask addBackgroundTask(
		long userId, long groupId, java.lang.String name,
		java.lang.String[] servletContextNames,
		java.lang.Class<?> taskExecutorClass,
		java.util.Map<java.lang.String, java.io.Serializable> taskContextMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTaskLocalService.addBackgroundTask(userId, groupId,
			name, servletContextNames, taskExecutorClass, taskContextMap,
			serviceContext);
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask addBackgroundTask(
		long userId, long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName,
		java.util.Map<java.lang.String, java.io.Serializable> taskContextMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTaskLocalService.addBackgroundTask(userId, groupId,
			name, taskExecutorClassName, taskContextMap, serviceContext);
	}

	@Override
	public void addBackgroundTaskAttachment(long userId, long backgroundTaskId,
		java.lang.String fileName, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_backgroundTaskLocalService.addBackgroundTaskAttachment(userId,
			backgroundTaskId, fileName, file);
	}

	@Override
	public void addBackgroundTaskAttachment(long userId, long backgroundTaskId,
		java.lang.String fileName, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		_backgroundTaskLocalService.addBackgroundTaskAttachment(userId,
			backgroundTaskId, fileName, inputStream);
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask amendBackgroundTask(
		long backgroundTaskId,
		java.util.Map<java.lang.String, java.io.Serializable> taskContextMap,
		int status, com.liferay.portal.service.ServiceContext serviceContext) {
		return _backgroundTaskLocalService.amendBackgroundTask(backgroundTaskId,
			taskContextMap, status, serviceContext);
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask amendBackgroundTask(
		long backgroundTaskId,
		java.util.Map<java.lang.String, java.io.Serializable> taskContextMap,
		int status, java.lang.String statusMessage,
		com.liferay.portal.service.ServiceContext serviceContext) {
		return _backgroundTaskLocalService.amendBackgroundTask(backgroundTaskId,
			taskContextMap, status, statusMessage, serviceContext);
	}

	@Override
	public void cleanUpBackgroundTask(long backgroundTaskId, int status) {
		_backgroundTaskLocalService.cleanUpBackgroundTask(backgroundTaskId,
			status);
	}

	@Override
	public void cleanUpBackgroundTasks() {
		_backgroundTaskLocalService.cleanUpBackgroundTasks();
	}

	/**
	* Creates a new background task with the primary key. Does not add the background task to the database.
	*
	* @param backgroundTaskId the primary key for the new background task
	* @return the new background task
	*/
	@Override
	public com.liferay.portal.background.task.model.BackgroundTask createBackgroundTask(
		long backgroundTaskId) {
		return _backgroundTaskLocalService.createBackgroundTask(backgroundTaskId);
	}

	/**
	* Deletes the background task from the database. Also notifies the appropriate model listeners.
	*
	* @param backgroundTask the background task
	* @return the background task that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.background.task.model.BackgroundTask deleteBackgroundTask(
		com.liferay.portal.background.task.model.BackgroundTask backgroundTask)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTaskLocalService.deleteBackgroundTask(backgroundTask);
	}

	/**
	* Deletes the background task with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param backgroundTaskId the primary key of the background task
	* @return the background task that was removed
	* @throws PortalException if a background task with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.background.task.model.BackgroundTask deleteBackgroundTask(
		long backgroundTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTaskLocalService.deleteBackgroundTask(backgroundTaskId);
	}

	@Override
	public void deleteCompanyBackgroundTasks(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_backgroundTaskLocalService.deleteCompanyBackgroundTasks(companyId);
	}

	@Override
	public void deleteGroupBackgroundTasks(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_backgroundTaskLocalService.deleteGroupBackgroundTasks(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTaskLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _backgroundTaskLocalService.dynamicQuery();
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
		return _backgroundTaskLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.background.task.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _backgroundTaskLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.background.task.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _backgroundTaskLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _backgroundTaskLocalService.dynamicQueryCount(dynamicQuery);
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
		return _backgroundTaskLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask fetchBackgroundTask(
		long backgroundTaskId) {
		return _backgroundTaskLocalService.fetchBackgroundTask(backgroundTaskId);
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask fetchFirstBackgroundTask(
		long groupId, java.lang.String taskExecutorClassName,
		boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return _backgroundTaskLocalService.fetchFirstBackgroundTask(groupId,
			taskExecutorClassName, completed, orderByComparator);
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask fetchFirstBackgroundTask(
		java.lang.String taskExecutorClassName, int status) {
		return _backgroundTaskLocalService.fetchFirstBackgroundTask(taskExecutorClassName,
			status);
	}

	@Override
	public com.liferay.portal.background.task.model.BackgroundTask fetchFirstBackgroundTask(
		java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return _backgroundTaskLocalService.fetchFirstBackgroundTask(taskExecutorClassName,
			status, orderByComparator);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _backgroundTaskLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the background task with the primary key.
	*
	* @param backgroundTaskId the primary key of the background task
	* @return the background task
	* @throws PortalException if a background task with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.background.task.model.BackgroundTask getBackgroundTask(
		long backgroundTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTaskLocalService.getBackgroundTask(backgroundTaskId);
	}

	@Override
	public java.lang.String getBackgroundTaskStatusJSON(long backgroundTaskId) {
		return _backgroundTaskLocalService.getBackgroundTaskStatusJSON(backgroundTaskId);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return _backgroundTaskLocalService.getBackgroundTasks(groupId, name,
			taskExecutorClassName, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, int status) {
		return _backgroundTaskLocalService.getBackgroundTasks(groupId, status);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String taskExecutorClassName) {
		return _backgroundTaskLocalService.getBackgroundTasks(groupId,
			taskExecutorClassName);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String taskExecutorClassName, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return _backgroundTaskLocalService.getBackgroundTasks(groupId,
			taskExecutorClassName, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String taskExecutorClassName, int status) {
		return _backgroundTaskLocalService.getBackgroundTasks(groupId,
			taskExecutorClassName, status);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String[] taskExecutorClassNames) {
		return _backgroundTaskLocalService.getBackgroundTasks(groupId,
			taskExecutorClassNames);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String[] taskExecutorClassNames, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return _backgroundTaskLocalService.getBackgroundTasks(groupId,
			taskExecutorClassNames, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String[] taskExecutorClassNames, int status) {
		return _backgroundTaskLocalService.getBackgroundTasks(groupId,
			taskExecutorClassNames, status);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long[] groupIds, java.lang.String name,
		java.lang.String taskExecutorClassName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return _backgroundTaskLocalService.getBackgroundTasks(groupIds, name,
			taskExecutorClassName, start, end, orderByComparator);
	}

	/**
	* Returns a range of all the background tasks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.background.task.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of background tasks
	*/
	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		int start, int end) {
		return _backgroundTaskLocalService.getBackgroundTasks(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		java.lang.String taskExecutorClassName, int status) {
		return _backgroundTaskLocalService.getBackgroundTasks(taskExecutorClassName,
			status);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		java.lang.String taskExecutorClassName, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return _backgroundTaskLocalService.getBackgroundTasks(taskExecutorClassName,
			status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		java.lang.String[] taskExecutorClassNames, int status) {
		return _backgroundTaskLocalService.getBackgroundTasks(taskExecutorClassNames,
			status);
	}

	@Override
	public java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		java.lang.String[] taskExecutorClassNames, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return _backgroundTaskLocalService.getBackgroundTasks(taskExecutorClassNames,
			status, start, end, orderByComparator);
	}

	/**
	* Returns the number of background tasks.
	*
	* @return the number of background tasks
	*/
	@Override
	public int getBackgroundTasksCount() {
		return _backgroundTaskLocalService.getBackgroundTasksCount();
	}

	@Override
	public int getBackgroundTasksCount(long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName) {
		return _backgroundTaskLocalService.getBackgroundTasksCount(groupId,
			name, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed) {
		return _backgroundTaskLocalService.getBackgroundTasksCount(groupId,
			name, taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(long groupId,
		java.lang.String taskExecutorClassName) {
		return _backgroundTaskLocalService.getBackgroundTasksCount(groupId,
			taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(long groupId,
		java.lang.String taskExecutorClassName, boolean completed) {
		return _backgroundTaskLocalService.getBackgroundTasksCount(groupId,
			taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(long groupId,
		java.lang.String[] taskExecutorClassNames) {
		return _backgroundTaskLocalService.getBackgroundTasksCount(groupId,
			taskExecutorClassNames);
	}

	@Override
	public int getBackgroundTasksCount(long groupId,
		java.lang.String[] taskExecutorClassNames, boolean completed) {
		return _backgroundTaskLocalService.getBackgroundTasksCount(groupId,
			taskExecutorClassNames, completed);
	}

	@Override
	public int getBackgroundTasksCount(long[] groupIds, java.lang.String name,
		java.lang.String taskExecutorClassName) {
		return _backgroundTaskLocalService.getBackgroundTasksCount(groupIds,
			name, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(long[] groupIds, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed) {
		return _backgroundTaskLocalService.getBackgroundTasksCount(groupIds,
			name, taskExecutorClassName, completed);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _backgroundTaskLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _backgroundTaskLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _backgroundTaskLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void resumeBackgroundTask(long backgroundTaskId) {
		_backgroundTaskLocalService.resumeBackgroundTask(backgroundTaskId);
	}

	@Override
	public void triggerBackgroundTask(long backgroundTaskId) {
		_backgroundTaskLocalService.triggerBackgroundTask(backgroundTaskId);
	}

	/**
	* Updates the background task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param backgroundTask the background task
	* @return the background task that was updated
	*/
	@Override
	public com.liferay.portal.background.task.model.BackgroundTask updateBackgroundTask(
		com.liferay.portal.background.task.model.BackgroundTask backgroundTask) {
		return _backgroundTaskLocalService.updateBackgroundTask(backgroundTask);
	}

	@Override
	public BackgroundTaskLocalService getWrappedService() {
		return _backgroundTaskLocalService;
	}

	@Override
	public void setWrappedService(
		BackgroundTaskLocalService backgroundTaskLocalService) {
		_backgroundTaskLocalService = backgroundTaskLocalService;
	}

	private BackgroundTaskLocalService _backgroundTaskLocalService;
}