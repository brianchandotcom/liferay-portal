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

package com.liferay.portlet.backgroundtask.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.backgroundtask.model.BackgroundTask;

import java.util.List;

/**
 * The persistence utility for the background task service. This utility wraps {@link BackgroundTaskPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTaskPersistence
 * @see BackgroundTaskPersistenceImpl
 * @generated
 */
public class BackgroundTaskUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(BackgroundTask backgroundTask) {
		getPersistence().clearCache(backgroundTask);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BackgroundTask> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BackgroundTask> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BackgroundTask> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static BackgroundTask update(BackgroundTask backgroundTask)
		throws SystemException {
		return getPersistence().update(backgroundTask);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static BackgroundTask update(BackgroundTask backgroundTask,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(backgroundTask, serviceContext);
	}

	/**
	* Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @return the matching background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.backgroundtask.model.BackgroundTask> findByG_T(
		long groupId, java.lang.String taskExecutorClassName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_T(groupId, taskExecutorClassName);
	}

	/**
	* Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.backgroundtask.model.BackgroundTask> findByG_T(
		long groupId, java.lang.String taskExecutorClassName, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_T(groupId, taskExecutorClassName, start, end);
	}

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.backgroundtask.model.BackgroundTask> findByG_T(
		long groupId, java.lang.String taskExecutorClassName, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_T(groupId, taskExecutorClassName, start, end,
			orderByComparator);
	}

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException if a matching background task could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask findByG_T_First(
		long groupId, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException {
		return getPersistence()
				   .findByG_T_First(groupId, taskExecutorClassName,
			orderByComparator);
	}

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask fetchByG_T_First(
		long groupId, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_T_First(groupId, taskExecutorClassName,
			orderByComparator);
	}

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException if a matching background task could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask findByG_T_Last(
		long groupId, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException {
		return getPersistence()
				   .findByG_T_Last(groupId, taskExecutorClassName,
			orderByComparator);
	}

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask fetchByG_T_Last(
		long groupId, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_T_Last(groupId, taskExecutorClassName,
			orderByComparator);
	}

	/**
	* Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask[] findByG_T_PrevAndNext(
		long backgroundTaskId, long groupId,
		java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException {
		return getPersistence()
				   .findByG_T_PrevAndNext(backgroundTaskId, groupId,
			taskExecutorClassName, orderByComparator);
	}

	/**
	* Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; from the database.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_T(long groupId,
		java.lang.String taskExecutorClassName)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_T(groupId, taskExecutorClassName);
	}

	/**
	* Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @return the number of matching background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_T(long groupId,
		java.lang.String taskExecutorClassName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_T(groupId, taskExecutorClassName);
	}

	/**
	* Returns all the background tasks where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param taskExecutorClassName the task executor class name
	* @return the matching background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.backgroundtask.model.BackgroundTask> findByG_S_T(
		long groupId, int status, java.lang.String taskExecutorClassName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_S_T(groupId, status, taskExecutorClassName);
	}

	/**
	* Returns a range of all the background tasks where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param taskExecutorClassName the task executor class name
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.backgroundtask.model.BackgroundTask> findByG_S_T(
		long groupId, int status, java.lang.String taskExecutorClassName,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_S_T(groupId, status, taskExecutorClassName, start,
			end);
	}

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param taskExecutorClassName the task executor class name
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.backgroundtask.model.BackgroundTask> findByG_S_T(
		long groupId, int status, java.lang.String taskExecutorClassName,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_S_T(groupId, status, taskExecutorClassName, start,
			end, orderByComparator);
	}

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException if a matching background task could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask findByG_S_T_First(
		long groupId, int status, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException {
		return getPersistence()
				   .findByG_S_T_First(groupId, status, taskExecutorClassName,
			orderByComparator);
	}

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask fetchByG_S_T_First(
		long groupId, int status, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_S_T_First(groupId, status, taskExecutorClassName,
			orderByComparator);
	}

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException if a matching background task could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask findByG_S_T_Last(
		long groupId, int status, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException {
		return getPersistence()
				   .findByG_S_T_Last(groupId, status, taskExecutorClassName,
			orderByComparator);
	}

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask fetchByG_S_T_Last(
		long groupId, int status, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_S_T_Last(groupId, status, taskExecutorClassName,
			orderByComparator);
	}

	/**
	* Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param groupId the group ID
	* @param status the status
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask[] findByG_S_T_PrevAndNext(
		long backgroundTaskId, long groupId, int status,
		java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException {
		return getPersistence()
				   .findByG_S_T_PrevAndNext(backgroundTaskId, groupId, status,
			taskExecutorClassName, orderByComparator);
	}

	/**
	* Removes all the background tasks where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	* @param taskExecutorClassName the task executor class name
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_S_T(long groupId, int status,
		java.lang.String taskExecutorClassName)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_S_T(groupId, status, taskExecutorClassName);
	}

	/**
	* Returns the number of background tasks where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param taskExecutorClassName the task executor class name
	* @return the number of matching background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_S_T(long groupId, int status,
		java.lang.String taskExecutorClassName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_S_T(groupId, status, taskExecutorClassName);
	}

	/**
	* Caches the background task in the entity cache if it is enabled.
	*
	* @param backgroundTask the background task
	*/
	public static void cacheResult(
		com.liferay.portlet.backgroundtask.model.BackgroundTask backgroundTask) {
		getPersistence().cacheResult(backgroundTask);
	}

	/**
	* Caches the background tasks in the entity cache if it is enabled.
	*
	* @param backgroundTasks the background tasks
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.backgroundtask.model.BackgroundTask> backgroundTasks) {
		getPersistence().cacheResult(backgroundTasks);
	}

	/**
	* Creates a new background task with the primary key. Does not add the background task to the database.
	*
	* @param backgroundTaskId the primary key for the new background task
	* @return the new background task
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask create(
		long backgroundTaskId) {
		return getPersistence().create(backgroundTaskId);
	}

	/**
	* Removes the background task with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param backgroundTaskId the primary key of the background task
	* @return the background task that was removed
	* @throws com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask remove(
		long backgroundTaskId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException {
		return getPersistence().remove(backgroundTaskId);
	}

	public static com.liferay.portlet.backgroundtask.model.BackgroundTask updateImpl(
		com.liferay.portlet.backgroundtask.model.BackgroundTask backgroundTask)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(backgroundTask);
	}

	/**
	* Returns the background task with the primary key or throws a {@link com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException} if it could not be found.
	*
	* @param backgroundTaskId the primary key of the background task
	* @return the background task
	* @throws com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask findByPrimaryKey(
		long backgroundTaskId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.backgroundtask.NoSuchBackgroundTaskException {
		return getPersistence().findByPrimaryKey(backgroundTaskId);
	}

	/**
	* Returns the background task with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param backgroundTaskId the primary key of the background task
	* @return the background task, or <code>null</code> if a background task with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BackgroundTask fetchByPrimaryKey(
		long backgroundTaskId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(backgroundTaskId);
	}

	/**
	* Returns all the background tasks.
	*
	* @return the background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.backgroundtask.model.BackgroundTask> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the background tasks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.backgroundtask.model.BackgroundTask> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the background tasks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.backgroundtask.model.BackgroundTask> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the background tasks from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of background tasks.
	*
	* @return the number of background tasks
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static BackgroundTaskPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (BackgroundTaskPersistence)PortalBeanLocatorUtil.locate(BackgroundTaskPersistence.class.getName());

			ReferenceRegistry.registerReference(BackgroundTaskUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(BackgroundTaskPersistence persistence) {
	}

	private static BackgroundTaskPersistence _persistence;
}