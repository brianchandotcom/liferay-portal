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

import com.liferay.batch.engine.exception.NoSuchTaskException;
import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch engine task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineTaskUtil
 * @generated
 */
@ProviderType
public interface BatchEngineTaskPersistence
	extends BasePersistence<BatchEngineTask> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchEngineTaskUtil} to access the batch engine task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch engine tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByUuid(String uuid);

	/**
	 * Returns a range of all the batch engine tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @return the range of matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the batch engine tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch engine tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public BatchEngineTask findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Returns the first batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public BatchEngineTask fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator);

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public BatchEngineTask findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public BatchEngineTask fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator);

	/**
	 * Returns the batch engine tasks before and after the current batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param batchEngineTaskId the primary key of the current batch engine task
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	public BatchEngineTask[] findByUuid_PrevAndNext(
			long batchEngineTaskId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Removes all the batch engine tasks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of batch engine tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch engine tasks
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @return the range of matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public BatchEngineTask findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Returns the first batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public BatchEngineTask fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator);

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public BatchEngineTask findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public BatchEngineTask fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator);

	/**
	 * Returns the batch engine tasks before and after the current batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchEngineTaskId the primary key of the current batch engine task
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	public BatchEngineTask[] findByUuid_C_PrevAndNext(
			long batchEngineTaskId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Removes all the batch engine tasks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch engine tasks
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the batch engine tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByExecuteStatus(
		String executeStatus);

	/**
	 * Returns a range of all the batch engine tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @return the range of matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByExecuteStatus(
		String executeStatus, int start, int end);

	/**
	 * Returns an ordered range of all the batch engine tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch engine tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public BatchEngineTask findByExecuteStatus_First(
			String executeStatus,
			com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Returns the first batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public BatchEngineTask fetchByExecuteStatus_First(
		String executeStatus,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator);

	/**
	 * Returns the last batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public BatchEngineTask findByExecuteStatus_Last(
			String executeStatus,
			com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Returns the last batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public BatchEngineTask fetchByExecuteStatus_Last(
		String executeStatus,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator);

	/**
	 * Returns the batch engine tasks before and after the current batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param batchEngineTaskId the primary key of the current batch engine task
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	public BatchEngineTask[] findByExecuteStatus_PrevAndNext(
			long batchEngineTaskId, String executeStatus,
			com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Removes all the batch engine tasks where executeStatus = &#63; from the database.
	 *
	 * @param executeStatus the execute status
	 */
	public void removeByExecuteStatus(String executeStatus);

	/**
	 * Returns the number of batch engine tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the number of matching batch engine tasks
	 */
	public int countByExecuteStatus(String executeStatus);

	/**
	 * Caches the batch engine task in the entity cache if it is enabled.
	 *
	 * @param batchEngineTask the batch engine task
	 */
	public void cacheResult(BatchEngineTask batchEngineTask);

	/**
	 * Caches the batch engine tasks in the entity cache if it is enabled.
	 *
	 * @param batchEngineTasks the batch engine tasks
	 */
	public void cacheResult(java.util.List<BatchEngineTask> batchEngineTasks);

	/**
	 * Creates a new batch engine task with the primary key. Does not add the batch engine task to the database.
	 *
	 * @param batchEngineTaskId the primary key for the new batch engine task
	 * @return the new batch engine task
	 */
	public BatchEngineTask create(long batchEngineTaskId);

	/**
	 * Removes the batch engine task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task that was removed
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	public BatchEngineTask remove(long batchEngineTaskId)
		throws NoSuchTaskException;

	public BatchEngineTask updateImpl(BatchEngineTask batchEngineTask);

	/**
	 * Returns the batch engine task with the primary key or throws a <code>NoSuchTaskException</code> if it could not be found.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	public BatchEngineTask findByPrimaryKey(long batchEngineTaskId)
		throws NoSuchTaskException;

	/**
	 * Returns the batch engine task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task, or <code>null</code> if a batch engine task with the primary key could not be found
	 */
	public BatchEngineTask fetchByPrimaryKey(long batchEngineTaskId);

	/**
	 * Returns all the batch engine tasks.
	 *
	 * @return the batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findAll();

	/**
	 * Returns a range of all the batch engine tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @return the range of batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the batch engine tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch engine tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine tasks
	 */
	public java.util.List<BatchEngineTask> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchEngineTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the batch engine tasks from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch engine tasks.
	 *
	 * @return the number of batch engine tasks
	 */
	public int countAll();

}