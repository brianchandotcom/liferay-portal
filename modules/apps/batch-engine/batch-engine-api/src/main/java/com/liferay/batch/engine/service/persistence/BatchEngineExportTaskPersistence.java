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

package com.liferay.batch.engine.service.persistence;

import com.liferay.batch.engine.exception.NoSuchExportTaskException;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch engine export task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineExportTaskUtil
 * @generated
 */
@ProviderType
public interface BatchEngineExportTaskPersistence
	extends BasePersistence<BatchEngineExportTask> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchEngineExportTaskUtil} to access the batch engine export task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch engine export tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByUuid(String uuid);

	/**
	 * Returns a range of all the batch engine export tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @return the range of matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the batch engine export tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch engine export tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch engine export task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException;

	/**
	 * Returns the first batch engine export task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator);

	/**
	 * Returns the last batch engine export task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException;

	/**
	 * Returns the last batch engine export task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator);

	/**
	 * Returns the batch engine export tasks before and after the current batch engine export task in the ordered set where uuid = &#63;.
	 *
	 * @param batchEngineExportTaskId the primary key of the current batch engine export task
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine export task
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	public BatchEngineExportTask[] findByUuid_PrevAndNext(
			long batchEngineExportTaskId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException;

	/**
	 * Removes all the batch engine export tasks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of batch engine export tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch engine export tasks
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the batch engine export tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the batch engine export tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @return the range of matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the batch engine export tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch engine export tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch engine export task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException;

	/**
	 * Returns the first batch engine export task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator);

	/**
	 * Returns the last batch engine export task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException;

	/**
	 * Returns the last batch engine export task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator);

	/**
	 * Returns the batch engine export tasks before and after the current batch engine export task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchEngineExportTaskId the primary key of the current batch engine export task
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine export task
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	public BatchEngineExportTask[] findByUuid_C_PrevAndNext(
			long batchEngineExportTaskId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException;

	/**
	 * Removes all the batch engine export tasks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of batch engine export tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch engine export tasks
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the batch engine export tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByExecuteStatus(
		String executeStatus);

	/**
	 * Returns a range of all the batch engine export tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @return the range of matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByExecuteStatus(
		String executeStatus, int start, int end);

	/**
	 * Returns an ordered range of all the batch engine export tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch engine export tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch engine export task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask findByExecuteStatus_First(
			String executeStatus,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException;

	/**
	 * Returns the first batch engine export task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask fetchByExecuteStatus_First(
		String executeStatus,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator);

	/**
	 * Returns the last batch engine export task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask findByExecuteStatus_Last(
			String executeStatus,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException;

	/**
	 * Returns the last batch engine export task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	public BatchEngineExportTask fetchByExecuteStatus_Last(
		String executeStatus,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator);

	/**
	 * Returns the batch engine export tasks before and after the current batch engine export task in the ordered set where executeStatus = &#63;.
	 *
	 * @param batchEngineExportTaskId the primary key of the current batch engine export task
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine export task
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	public BatchEngineExportTask[] findByExecuteStatus_PrevAndNext(
			long batchEngineExportTaskId, String executeStatus,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException;

	/**
	 * Removes all the batch engine export tasks where executeStatus = &#63; from the database.
	 *
	 * @param executeStatus the execute status
	 */
	public void removeByExecuteStatus(String executeStatus);

	/**
	 * Returns the number of batch engine export tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the number of matching batch engine export tasks
	 */
	public int countByExecuteStatus(String executeStatus);

	/**
	 * Caches the batch engine export task in the entity cache if it is enabled.
	 *
	 * @param batchEngineExportTask the batch engine export task
	 */
	public void cacheResult(BatchEngineExportTask batchEngineExportTask);

	/**
	 * Caches the batch engine export tasks in the entity cache if it is enabled.
	 *
	 * @param batchEngineExportTasks the batch engine export tasks
	 */
	public void cacheResult(
		java.util.List<BatchEngineExportTask> batchEngineExportTasks);

	/**
	 * Creates a new batch engine export task with the primary key. Does not add the batch engine export task to the database.
	 *
	 * @param batchEngineExportTaskId the primary key for the new batch engine export task
	 * @return the new batch engine export task
	 */
	public BatchEngineExportTask create(long batchEngineExportTaskId);

	/**
	 * Removes the batch engine export task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineExportTaskId the primary key of the batch engine export task
	 * @return the batch engine export task that was removed
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	public BatchEngineExportTask remove(long batchEngineExportTaskId)
		throws NoSuchExportTaskException;

	public BatchEngineExportTask updateImpl(
		BatchEngineExportTask batchEngineExportTask);

	/**
	 * Returns the batch engine export task with the primary key or throws a <code>NoSuchExportTaskException</code> if it could not be found.
	 *
	 * @param batchEngineExportTaskId the primary key of the batch engine export task
	 * @return the batch engine export task
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	public BatchEngineExportTask findByPrimaryKey(long batchEngineExportTaskId)
		throws NoSuchExportTaskException;

	/**
	 * Returns the batch engine export task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineExportTaskId the primary key of the batch engine export task
	 * @return the batch engine export task, or <code>null</code> if a batch engine export task with the primary key could not be found
	 */
	public BatchEngineExportTask fetchByPrimaryKey(
		long batchEngineExportTaskId);

	/**
	 * Returns all the batch engine export tasks.
	 *
	 * @return the batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findAll();

	/**
	 * Returns a range of all the batch engine export tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @return the range of batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the batch engine export tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch engine export tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine export tasks
	 */
	public java.util.List<BatchEngineExportTask> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineExportTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the batch engine export tasks from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch engine export tasks.
	 *
	 * @return the number of batch engine export tasks
	 */
	public int countAll();

}