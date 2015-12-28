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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for BackgroundTask. This utility wraps
 * {@link com.liferay.portal.background.task.service.impl.BackgroundTaskLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTaskLocalService
 * @see com.liferay.portal.background.task.service.base.BackgroundTaskLocalServiceBaseImpl
 * @see com.liferay.portal.background.task.service.impl.BackgroundTaskLocalServiceImpl
 * @generated
 */
@ProviderType
public class BackgroundTaskLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.background.task.service.impl.BackgroundTaskLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the background task to the database. Also notifies the appropriate model listeners.
	*
	* @param backgroundTask the background task
	* @return the background task that was added
	*/
	public static com.liferay.portal.background.task.model.BackgroundTask addBackgroundTask(
		com.liferay.portal.background.task.model.BackgroundTask backgroundTask) {
		return getService().addBackgroundTask(backgroundTask);
	}

	public static com.liferay.portal.background.task.model.BackgroundTask addBackgroundTask(
		long userId, long groupId, java.lang.String name,
		java.lang.String[] servletContextNames,
		java.lang.Class<?> taskExecutorClass,
		java.util.Map<java.lang.String, java.io.Serializable> taskContextMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addBackgroundTask(userId, groupId, name,
			servletContextNames, taskExecutorClass, taskContextMap,
			serviceContext);
	}

	public static com.liferay.portal.background.task.model.BackgroundTask addBackgroundTask(
		long userId, long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName,
		java.util.Map<java.lang.String, java.io.Serializable> taskContextMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addBackgroundTask(userId, groupId, name,
			taskExecutorClassName, taskContextMap, serviceContext);
	}

	public static void addBackgroundTaskAttachment(long userId,
		long backgroundTaskId, java.lang.String fileName, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addBackgroundTaskAttachment(userId, backgroundTaskId, fileName,
			file);
	}

	public static void addBackgroundTaskAttachment(long userId,
		long backgroundTaskId, java.lang.String fileName,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addBackgroundTaskAttachment(userId, backgroundTaskId, fileName,
			inputStream);
	}

	public static com.liferay.portal.background.task.model.BackgroundTask amendBackgroundTask(
		long backgroundTaskId,
		java.util.Map<java.lang.String, java.io.Serializable> taskContextMap,
		int status, com.liferay.portal.service.ServiceContext serviceContext) {
		return getService()
				   .amendBackgroundTask(backgroundTaskId, taskContextMap,
			status, serviceContext);
	}

	public static com.liferay.portal.background.task.model.BackgroundTask amendBackgroundTask(
		long backgroundTaskId,
		java.util.Map<java.lang.String, java.io.Serializable> taskContextMap,
		int status, java.lang.String statusMessage,
		com.liferay.portal.service.ServiceContext serviceContext) {
		return getService()
				   .amendBackgroundTask(backgroundTaskId, taskContextMap,
			status, statusMessage, serviceContext);
	}

	public static void cleanUpBackgroundTask(long backgroundTaskId, int status) {
		getService().cleanUpBackgroundTask(backgroundTaskId, status);
	}

	public static void cleanUpBackgroundTasks() {
		getService().cleanUpBackgroundTasks();
	}

	/**
	* Creates a new background task with the primary key. Does not add the background task to the database.
	*
	* @param backgroundTaskId the primary key for the new background task
	* @return the new background task
	*/
	public static com.liferay.portal.background.task.model.BackgroundTask createBackgroundTask(
		long backgroundTaskId) {
		return getService().createBackgroundTask(backgroundTaskId);
	}

	/**
	* Deletes the background task from the database. Also notifies the appropriate model listeners.
	*
	* @param backgroundTask the background task
	* @return the background task that was removed
	* @throws PortalException
	*/
	public static com.liferay.portal.background.task.model.BackgroundTask deleteBackgroundTask(
		com.liferay.portal.background.task.model.BackgroundTask backgroundTask)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteBackgroundTask(backgroundTask);
	}

	/**
	* Deletes the background task with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param backgroundTaskId the primary key of the background task
	* @return the background task that was removed
	* @throws PortalException if a background task with the primary key could not be found
	*/
	public static com.liferay.portal.background.task.model.BackgroundTask deleteBackgroundTask(
		long backgroundTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteBackgroundTask(backgroundTaskId);
	}

	public static void deleteCompanyBackgroundTasks(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCompanyBackgroundTasks(companyId);
	}

	public static void deleteGroupBackgroundTasks(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteGroupBackgroundTasks(groupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.background.task.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.background.task.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.background.task.model.BackgroundTask fetchBackgroundTask(
		long backgroundTaskId) {
		return getService().fetchBackgroundTask(backgroundTaskId);
	}

	public static com.liferay.portal.background.task.model.BackgroundTask fetchFirstBackgroundTask(
		long groupId, java.lang.String taskExecutorClassName,
		boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return getService()
				   .fetchFirstBackgroundTask(groupId, taskExecutorClassName,
			completed, orderByComparator);
	}

	public static com.liferay.portal.background.task.model.BackgroundTask fetchFirstBackgroundTask(
		java.lang.String taskExecutorClassName, int status) {
		return getService()
				   .fetchFirstBackgroundTask(taskExecutorClassName, status);
	}

	public static com.liferay.portal.background.task.model.BackgroundTask fetchFirstBackgroundTask(
		java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return getService()
				   .fetchFirstBackgroundTask(taskExecutorClassName, status,
			orderByComparator);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the background task with the primary key.
	*
	* @param backgroundTaskId the primary key of the background task
	* @return the background task
	* @throws PortalException if a background task with the primary key could not be found
	*/
	public static com.liferay.portal.background.task.model.BackgroundTask getBackgroundTask(
		long backgroundTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getBackgroundTask(backgroundTaskId);
	}

	public static java.lang.String getBackgroundTaskStatusJSON(
		long backgroundTaskId) {
		return getService().getBackgroundTaskStatusJSON(backgroundTaskId);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return getService()
				   .getBackgroundTasks(groupId, name, taskExecutorClassName,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, int status) {
		return getService().getBackgroundTasks(groupId, status);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String taskExecutorClassName) {
		return getService().getBackgroundTasks(groupId, taskExecutorClassName);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String taskExecutorClassName, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return getService()
				   .getBackgroundTasks(groupId, taskExecutorClassName, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String taskExecutorClassName, int status) {
		return getService()
				   .getBackgroundTasks(groupId, taskExecutorClassName, status);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String[] taskExecutorClassNames) {
		return getService().getBackgroundTasks(groupId, taskExecutorClassNames);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String[] taskExecutorClassNames, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return getService()
				   .getBackgroundTasks(groupId, taskExecutorClassNames, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long groupId, java.lang.String[] taskExecutorClassNames, int status) {
		return getService()
				   .getBackgroundTasks(groupId, taskExecutorClassNames, status);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		long[] groupIds, java.lang.String name,
		java.lang.String taskExecutorClassName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return getService()
				   .getBackgroundTasks(groupIds, name, taskExecutorClassName,
			start, end, orderByComparator);
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
	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		int start, int end) {
		return getService().getBackgroundTasks(start, end);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		java.lang.String taskExecutorClassName, int status) {
		return getService().getBackgroundTasks(taskExecutorClassName, status);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		java.lang.String taskExecutorClassName, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return getService()
				   .getBackgroundTasks(taskExecutorClassName, status, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		java.lang.String[] taskExecutorClassNames, int status) {
		return getService().getBackgroundTasks(taskExecutorClassNames, status);
	}

	public static java.util.List<com.liferay.portal.background.task.model.BackgroundTask> getBackgroundTasks(
		java.lang.String[] taskExecutorClassNames, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.background.task.model.BackgroundTask> orderByComparator) {
		return getService()
				   .getBackgroundTasks(taskExecutorClassNames, status, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of background tasks.
	*
	* @return the number of background tasks
	*/
	public static int getBackgroundTasksCount() {
		return getService().getBackgroundTasksCount();
	}

	public static int getBackgroundTasksCount(long groupId,
		java.lang.String name, java.lang.String taskExecutorClassName) {
		return getService()
				   .getBackgroundTasksCount(groupId, name, taskExecutorClassName);
	}

	public static int getBackgroundTasksCount(long groupId,
		java.lang.String name, java.lang.String taskExecutorClassName,
		boolean completed) {
		return getService()
				   .getBackgroundTasksCount(groupId, name,
			taskExecutorClassName, completed);
	}

	public static int getBackgroundTasksCount(long groupId,
		java.lang.String taskExecutorClassName) {
		return getService()
				   .getBackgroundTasksCount(groupId, taskExecutorClassName);
	}

	public static int getBackgroundTasksCount(long groupId,
		java.lang.String taskExecutorClassName, boolean completed) {
		return getService()
				   .getBackgroundTasksCount(groupId, taskExecutorClassName,
			completed);
	}

	public static int getBackgroundTasksCount(long groupId,
		java.lang.String[] taskExecutorClassNames) {
		return getService()
				   .getBackgroundTasksCount(groupId, taskExecutorClassNames);
	}

	public static int getBackgroundTasksCount(long groupId,
		java.lang.String[] taskExecutorClassNames, boolean completed) {
		return getService()
				   .getBackgroundTasksCount(groupId, taskExecutorClassNames,
			completed);
	}

	public static int getBackgroundTasksCount(long[] groupIds,
		java.lang.String name, java.lang.String taskExecutorClassName) {
		return getService()
				   .getBackgroundTasksCount(groupIds, name,
			taskExecutorClassName);
	}

	public static int getBackgroundTasksCount(long[] groupIds,
		java.lang.String name, java.lang.String taskExecutorClassName,
		boolean completed) {
		return getService()
				   .getBackgroundTasksCount(groupIds, name,
			taskExecutorClassName, completed);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
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

	public static void resumeBackgroundTask(long backgroundTaskId) {
		getService().resumeBackgroundTask(backgroundTaskId);
	}

	public static void triggerBackgroundTask(long backgroundTaskId) {
		getService().triggerBackgroundTask(backgroundTaskId);
	}

	/**
	* Updates the background task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param backgroundTask the background task
	* @return the background task that was updated
	*/
	public static com.liferay.portal.background.task.model.BackgroundTask updateBackgroundTask(
		com.liferay.portal.background.task.model.BackgroundTask backgroundTask) {
		return getService().updateBackgroundTask(backgroundTask);
	}

	public static BackgroundTaskLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<BackgroundTaskLocalService, BackgroundTaskLocalService> _serviceTracker =
		ServiceTrackerFactory.open(BackgroundTaskLocalService.class);
}