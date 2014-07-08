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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.BackgroundTask;

/**
 * The persistence interface for the background task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTaskPersistenceImpl
 * @see BackgroundTaskUtil
 * @generated
 */
@ProviderType
public interface BackgroundTaskPersistence extends BasePersistence<BackgroundTask> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BackgroundTaskUtil} to access the background task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the background tasks where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the background tasks where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first background task in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the first background task in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last background task in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the last background task in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the background tasks before and after the current background task in the ordered set where groupId = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask[] findByGroupId_PrevAndNext(
		long backgroundTaskId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Removes all the background tasks where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of background tasks where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching background tasks
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the background tasks where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the background tasks where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the background tasks where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first background task in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the first background task in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last background task in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the last background task in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the background tasks before and after the current background task in the ordered set where companyId = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask[] findByCompanyId_PrevAndNext(
		long backgroundTaskId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Removes all the background tasks where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of background tasks where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching background tasks
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the background tasks where status = &#63;.
	*
	* @param status the status
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByStatus(
		int status);

	/**
	* Returns a range of all the background tasks where status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByStatus(
		int status, int start, int end);

	/**
	* Returns an ordered range of all the background tasks where status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByStatus(
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first background task in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByStatus_First(
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the first background task in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByStatus_First(
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last background task in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByStatus_Last(
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the last background task in the ordered set where status = &#63;.
	*
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByStatus_Last(
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the background tasks before and after the current background task in the ordered set where status = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask[] findByStatus_PrevAndNext(
		long backgroundTaskId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Removes all the background tasks where status = &#63; from the database.
	*
	* @param status the status
	*/
	public void removeByStatus(int status);

	/**
	* Returns the number of background tasks where status = &#63;.
	*
	* @param status the status
	* @return the number of matching background tasks
	*/
	public int countByStatus(int status);

	/**
	* Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T(
		long groupId, java.lang.String taskExecutorClassName);

	/**
	* Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T(
		long groupId, java.lang.String taskExecutorClassName, int start, int end);

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T(
		long groupId, java.lang.String taskExecutorClassName, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_T_First(
		long groupId, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_T_First(
		long groupId, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_T_Last(
		long groupId, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_T_Last(
		long groupId, java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask[] findByG_T_PrevAndNext(
		long backgroundTaskId, long groupId,
		java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T(
		long groupId, java.lang.String[] taskExecutorClassNames);

	/**
	* Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T(
		long groupId, java.lang.String[] taskExecutorClassNames, int start,
		int end);

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T(
		long groupId, java.lang.String[] taskExecutorClassNames, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; from the database.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	*/
	public void removeByG_T(long groupId, java.lang.String taskExecutorClassName);

	/**
	* Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @return the number of matching background tasks
	*/
	public int countByG_T(long groupId, java.lang.String taskExecutorClassName);

	/**
	* Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = any &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @return the number of matching background tasks
	*/
	public int countByG_T(long groupId,
		java.lang.String[] taskExecutorClassNames);

	/**
	* Returns all the background tasks where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_S(
		long groupId, int status);

	/**
	* Returns a range of all the background tasks where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_S(
		long groupId, int status, int start, int end);

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask[] findByG_S_PrevAndNext(
		long backgroundTaskId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Removes all the background tasks where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_S(long groupId, int status);

	/**
	* Returns the number of background tasks where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching background tasks
	*/
	public int countByG_S(long groupId, int status);

	/**
	* Returns all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByT_S(
		java.lang.String taskExecutorClassName, int status);

	/**
	* Returns a range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByT_S(
		java.lang.String taskExecutorClassName, int status, int start, int end);

	/**
	* Returns an ordered range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByT_S(
		java.lang.String taskExecutorClassName, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByT_S_First(
		java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the first background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByT_S_First(
		java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByT_S_Last(
		java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the last background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByT_S_Last(
		java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the background tasks before and after the current background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask[] findByT_S_PrevAndNext(
		long backgroundTaskId, java.lang.String taskExecutorClassName,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns all the background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param taskExecutorClassNames the task executor class names
	* @param status the status
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByT_S(
		java.lang.String[] taskExecutorClassNames, int status);

	/**
	* Returns a range of all the background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param taskExecutorClassNames the task executor class names
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByT_S(
		java.lang.String[] taskExecutorClassNames, int status, int start,
		int end);

	/**
	* Returns an ordered range of all the background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param taskExecutorClassNames the task executor class names
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByT_S(
		java.lang.String[] taskExecutorClassNames, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the background tasks where taskExecutorClassName = &#63; and status = &#63; from the database.
	*
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	*/
	public void removeByT_S(java.lang.String taskExecutorClassName, int status);

	/**
	* Returns the number of background tasks where taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @return the number of matching background tasks
	*/
	public int countByT_S(java.lang.String taskExecutorClassName, int status);

	/**
	* Returns the number of background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	*
	* @param taskExecutorClassNames the task executor class names
	* @param status the status
	* @return the number of matching background tasks
	*/
	public int countByT_S(java.lang.String[] taskExecutorClassNames, int status);

	/**
	* Returns all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_N_T(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName);

	/**
	* Returns a range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_N_T(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, int start, int end);

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_N_T(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_N_T_First(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_N_T_First(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_N_T_Last(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_N_T_Last(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask[] findByG_N_T_PrevAndNext(
		long backgroundTaskId, long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Removes all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	*/
	public void removeByG_N_T(long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName);

	/**
	* Returns the number of background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @return the number of matching background tasks
	*/
	public int countByG_N_T(long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName);

	/**
	* Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_C(
		long groupId, java.lang.String taskExecutorClassName, boolean completed);

	/**
	* Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_C(
		long groupId, java.lang.String taskExecutorClassName,
		boolean completed, int start, int end);

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_C(
		long groupId, java.lang.String taskExecutorClassName,
		boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_T_C_First(
		long groupId, java.lang.String taskExecutorClassName,
		boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_T_C_First(
		long groupId, java.lang.String taskExecutorClassName,
		boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_T_C_Last(
		long groupId, java.lang.String taskExecutorClassName,
		boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_T_C_Last(
		long groupId, java.lang.String taskExecutorClassName,
		boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask[] findByG_T_C_PrevAndNext(
		long backgroundTaskId, long groupId,
		java.lang.String taskExecutorClassName, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @param completed the completed
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_C(
		long groupId, java.lang.String[] taskExecutorClassNames,
		boolean completed);

	/**
	* Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @param completed the completed
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_C(
		long groupId, java.lang.String[] taskExecutorClassNames,
		boolean completed, int start, int end);

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @param completed the completed
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_C(
		long groupId, java.lang.String[] taskExecutorClassNames,
		boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63; from the database.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	*/
	public void removeByG_T_C(long groupId,
		java.lang.String taskExecutorClassName, boolean completed);

	/**
	* Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @return the number of matching background tasks
	*/
	public int countByG_T_C(long groupId,
		java.lang.String taskExecutorClassName, boolean completed);

	/**
	* Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @param completed the completed
	* @return the number of matching background tasks
	*/
	public int countByG_T_C(long groupId,
		java.lang.String[] taskExecutorClassNames, boolean completed);

	/**
	* Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_S(
		long groupId, java.lang.String taskExecutorClassName, int status);

	/**
	* Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_S(
		long groupId, java.lang.String taskExecutorClassName, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_S(
		long groupId, java.lang.String taskExecutorClassName, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_T_S_First(
		long groupId, java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_T_S_First(
		long groupId, java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_T_S_Last(
		long groupId, java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_T_S_Last(
		long groupId, java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask[] findByG_T_S_PrevAndNext(
		long backgroundTaskId, long groupId,
		java.lang.String taskExecutorClassName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @param status the status
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_S(
		long groupId, java.lang.String[] taskExecutorClassNames, int status);

	/**
	* Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_S(
		long groupId, java.lang.String[] taskExecutorClassNames, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @param status the status
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_T_S(
		long groupId, java.lang.String[] taskExecutorClassNames, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	*/
	public void removeByG_T_S(long groupId,
		java.lang.String taskExecutorClassName, int status);

	/**
	* Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassName the task executor class name
	* @param status the status
	* @return the number of matching background tasks
	*/
	public int countByG_T_S(long groupId,
		java.lang.String taskExecutorClassName, int status);

	/**
	* Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param taskExecutorClassNames the task executor class names
	* @param status the status
	* @return the number of matching background tasks
	*/
	public int countByG_T_S(long groupId,
		java.lang.String[] taskExecutorClassNames, int status);

	/**
	* Returns all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @return the matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_N_T_C(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed);

	/**
	* Returns a range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_N_T_C(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed, int start,
		int end);

	/**
	* Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findByG_N_T_C(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_N_T_C_First(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_N_T_C_First(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByG_N_T_C_Last(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the last background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching background task, or <code>null</code> if a matching background task could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByG_N_T_C_Last(
		long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param backgroundTaskId the primary key of the current background task
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask[] findByG_N_T_C_PrevAndNext(
		long backgroundTaskId, long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Removes all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	*/
	public void removeByG_N_T_C(long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed);

	/**
	* Returns the number of background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param taskExecutorClassName the task executor class name
	* @param completed the completed
	* @return the number of matching background tasks
	*/
	public int countByG_N_T_C(long groupId, java.lang.String name,
		java.lang.String taskExecutorClassName, boolean completed);

	/**
	* Caches the background task in the entity cache if it is enabled.
	*
	* @param backgroundTask the background task
	*/
	public void cacheResult(
		com.liferay.portal.model.BackgroundTask backgroundTask);

	/**
	* Caches the background tasks in the entity cache if it is enabled.
	*
	* @param backgroundTasks the background tasks
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.BackgroundTask> backgroundTasks);

	/**
	* Creates a new background task with the primary key. Does not add the background task to the database.
	*
	* @param backgroundTaskId the primary key for the new background task
	* @return the new background task
	*/
	public com.liferay.portal.model.BackgroundTask create(long backgroundTaskId);

	/**
	* Removes the background task with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param backgroundTaskId the primary key of the background task
	* @return the background task that was removed
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask remove(long backgroundTaskId)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	public com.liferay.portal.model.BackgroundTask updateImpl(
		com.liferay.portal.model.BackgroundTask backgroundTask);

	/**
	* Returns the background task with the primary key or throws a {@link com.liferay.portal.NoSuchBackgroundTaskException} if it could not be found.
	*
	* @param backgroundTaskId the primary key of the background task
	* @return the background task
	* @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask findByPrimaryKey(
		long backgroundTaskId)
		throws com.liferay.portal.NoSuchBackgroundTaskException;

	/**
	* Returns the background task with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param backgroundTaskId the primary key of the background task
	* @return the background task, or <code>null</code> if a background task with the primary key could not be found
	*/
	public com.liferay.portal.model.BackgroundTask fetchByPrimaryKey(
		long backgroundTaskId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.BackgroundTask> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the background tasks.
	*
	* @return the background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findAll();

	/**
	* Returns a range of all the background tasks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @return the range of background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the background tasks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of background tasks
	* @param end the upper bound of the range of background tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of background tasks
	*/
	public java.util.List<com.liferay.portal.model.BackgroundTask> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the background tasks from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of background tasks.
	*
	* @return the number of background tasks
	*/
	public int countAll();
}