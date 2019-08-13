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
import com.liferay.batch.engine.model.BatchTask;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchTaskUtil
 * @generated
 */
@ProviderType
public interface BatchTaskPersistence extends BasePersistence<BatchTask> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchTaskUtil} to access the batch task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch tasks
	 */
	public java.util.List<BatchTask> findByUuid(String uuid);

	/**
	 * Returns a range of all the batch tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch tasks
	 * @param end the upper bound of the range of batch tasks (not inclusive)
	 * @return the range of matching batch tasks
	 */
	public java.util.List<BatchTask> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the batch tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch tasks
	 * @param end the upper bound of the range of batch tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch tasks
	 */
	public java.util.List<BatchTask> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch tasks
	 * @param end the upper bound of the range of batch tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch tasks
	 */
	public java.util.List<BatchTask> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch task
	 * @throws NoSuchTaskException if a matching batch task could not be found
	 */
	public BatchTask findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Returns the first batch task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch task, or <code>null</code> if a matching batch task could not be found
	 */
	public BatchTask fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
			orderByComparator);

	/**
	 * Returns the last batch task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch task
	 * @throws NoSuchTaskException if a matching batch task could not be found
	 */
	public BatchTask findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Returns the last batch task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch task, or <code>null</code> if a matching batch task could not be found
	 */
	public BatchTask fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
			orderByComparator);

	/**
	 * Returns the batch tasks before and after the current batch task in the ordered set where uuid = &#63;.
	 *
	 * @param batchTaskId the primary key of the current batch task
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch task
	 * @throws NoSuchTaskException if a batch task with the primary key could not be found
	 */
	public BatchTask[] findByUuid_PrevAndNext(
			long batchTaskId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Removes all the batch tasks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of batch tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch tasks
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the batch tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch tasks
	 */
	public java.util.List<BatchTask> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the batch tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch tasks
	 * @param end the upper bound of the range of batch tasks (not inclusive)
	 * @return the range of matching batch tasks
	 */
	public java.util.List<BatchTask> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the batch tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch tasks
	 * @param end the upper bound of the range of batch tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch tasks
	 */
	public java.util.List<BatchTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch tasks
	 * @param end the upper bound of the range of batch tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch tasks
	 */
	public java.util.List<BatchTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch task
	 * @throws NoSuchTaskException if a matching batch task could not be found
	 */
	public BatchTask findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Returns the first batch task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch task, or <code>null</code> if a matching batch task could not be found
	 */
	public BatchTask fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
			orderByComparator);

	/**
	 * Returns the last batch task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch task
	 * @throws NoSuchTaskException if a matching batch task could not be found
	 */
	public BatchTask findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Returns the last batch task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch task, or <code>null</code> if a matching batch task could not be found
	 */
	public BatchTask fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
			orderByComparator);

	/**
	 * Returns the batch tasks before and after the current batch task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchTaskId the primary key of the current batch task
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch task
	 * @throws NoSuchTaskException if a batch task with the primary key could not be found
	 */
	public BatchTask[] findByUuid_C_PrevAndNext(
			long batchTaskId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
				orderByComparator)
		throws NoSuchTaskException;

	/**
	 * Removes all the batch tasks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of batch tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch tasks
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Caches the batch task in the entity cache if it is enabled.
	 *
	 * @param batchTask the batch task
	 */
	public void cacheResult(BatchTask batchTask);

	/**
	 * Caches the batch tasks in the entity cache if it is enabled.
	 *
	 * @param batchTasks the batch tasks
	 */
	public void cacheResult(java.util.List<BatchTask> batchTasks);

	/**
	 * Creates a new batch task with the primary key. Does not add the batch task to the database.
	 *
	 * @param batchTaskId the primary key for the new batch task
	 * @return the new batch task
	 */
	public BatchTask create(long batchTaskId);

	/**
	 * Removes the batch task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchTaskId the primary key of the batch task
	 * @return the batch task that was removed
	 * @throws NoSuchTaskException if a batch task with the primary key could not be found
	 */
	public BatchTask remove(long batchTaskId) throws NoSuchTaskException;

	public BatchTask updateImpl(BatchTask batchTask);

	/**
	 * Returns the batch task with the primary key or throws a <code>NoSuchTaskException</code> if it could not be found.
	 *
	 * @param batchTaskId the primary key of the batch task
	 * @return the batch task
	 * @throws NoSuchTaskException if a batch task with the primary key could not be found
	 */
	public BatchTask findByPrimaryKey(long batchTaskId)
		throws NoSuchTaskException;

	/**
	 * Returns the batch task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchTaskId the primary key of the batch task
	 * @return the batch task, or <code>null</code> if a batch task with the primary key could not be found
	 */
	public BatchTask fetchByPrimaryKey(long batchTaskId);

	/**
	 * Returns all the batch tasks.
	 *
	 * @return the batch tasks
	 */
	public java.util.List<BatchTask> findAll();

	/**
	 * Returns a range of all the batch tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch tasks
	 * @param end the upper bound of the range of batch tasks (not inclusive)
	 * @return the range of batch tasks
	 */
	public java.util.List<BatchTask> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the batch tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch tasks
	 * @param end the upper bound of the range of batch tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch tasks
	 */
	public java.util.List<BatchTask> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchTaskModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch tasks
	 * @param end the upper bound of the range of batch tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch tasks
	 */
	public java.util.List<BatchTask> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchTask>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the batch tasks from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch tasks.
	 *
	 * @return the number of batch tasks
	 */
	public int countAll();

}