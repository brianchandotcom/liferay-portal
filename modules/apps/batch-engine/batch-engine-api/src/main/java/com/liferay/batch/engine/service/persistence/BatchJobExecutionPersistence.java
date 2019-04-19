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

import com.liferay.batch.engine.exception.NoSuchJobExecutionException;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch job execution service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @see BatchJobExecutionUtil
 * @generated
 */
@ProviderType
public interface BatchJobExecutionPersistence
	extends BasePersistence<BatchJobExecution> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchJobExecutionUtil} to access the batch job execution persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch job executions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findByUuid(String uuid);

	/**
	 * Returns a range of all the batch job executions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the batch job executions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch job executions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public BatchJobExecution findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public BatchJobExecution fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public BatchJobExecution findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public BatchJobExecution fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public BatchJobExecution[] findByUuid_PrevAndNext(
			long batchJobExecutionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Removes all the batch job executions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of batch job executions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch job executions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public BatchJobExecution findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public BatchJobExecution fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public BatchJobExecution findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public BatchJobExecution fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public BatchJobExecution[] findByUuid_C_PrevAndNext(
			long batchJobExecutionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Removes all the batch job executions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch job executions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the batch job executions where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @return the matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId);

	/**
	 * Returns a range of all the batch job executions where batchJobInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId, int start, int end);

	/**
	 * Returns an ordered range of all the batch job executions where batchJobInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch job executions where batchJobInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public BatchJobExecution findBybatchJobInstanceId_First(
			long batchJobInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Returns the first batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public BatchJobExecution fetchBybatchJobInstanceId_First(
		long batchJobInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns the last batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public BatchJobExecution findBybatchJobInstanceId_Last(
			long batchJobInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Returns the last batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public BatchJobExecution fetchBybatchJobInstanceId_Last(
		long batchJobInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public BatchJobExecution[] findBybatchJobInstanceId_PrevAndNext(
			long batchJobExecutionId, long batchJobInstanceId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Removes all the batch job executions where batchJobInstanceId = &#63; from the database.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 */
	public void removeBybatchJobInstanceId(long batchJobInstanceId);

	/**
	 * Returns the number of batch job executions where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @return the number of matching batch job executions
	 */
	public int countBybatchJobInstanceId(long batchJobInstanceId);

	/**
	 * Returns all the batch job executions where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findBystatus(String status);

	/**
	 * Returns a range of all the batch job executions where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findBystatus(
		String status, int start, int end);

	/**
	 * Returns an ordered range of all the batch job executions where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findBystatus(
		String status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch job executions where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job executions
	 */
	public java.util.List<BatchJobExecution> findBystatus(
		String status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public BatchJobExecution findBystatus_First(
			String status,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Returns the first batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public BatchJobExecution fetchBystatus_First(
		String status,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns the last batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public BatchJobExecution findBystatus_Last(
			String status,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Returns the last batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public BatchJobExecution fetchBystatus_Last(
		String status,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where status = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public BatchJobExecution[] findBystatus_PrevAndNext(
			long batchJobExecutionId, String status,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
				orderByComparator)
		throws NoSuchJobExecutionException;

	/**
	 * Removes all the batch job executions where status = &#63; from the database.
	 *
	 * @param status the status
	 */
	public void removeBystatus(String status);

	/**
	 * Returns the number of batch job executions where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching batch job executions
	 */
	public int countBystatus(String status);

	/**
	 * Caches the batch job execution in the entity cache if it is enabled.
	 *
	 * @param batchJobExecution the batch job execution
	 */
	public void cacheResult(BatchJobExecution batchJobExecution);

	/**
	 * Caches the batch job executions in the entity cache if it is enabled.
	 *
	 * @param batchJobExecutions the batch job executions
	 */
	public void cacheResult(
		java.util.List<BatchJobExecution> batchJobExecutions);

	/**
	 * Creates a new batch job execution with the primary key. Does not add the batch job execution to the database.
	 *
	 * @param batchJobExecutionId the primary key for the new batch job execution
	 * @return the new batch job execution
	 */
	public BatchJobExecution create(long batchJobExecutionId);

	/**
	 * Removes the batch job execution with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution that was removed
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public BatchJobExecution remove(long batchJobExecutionId)
		throws NoSuchJobExecutionException;

	public BatchJobExecution updateImpl(BatchJobExecution batchJobExecution);

	/**
	 * Returns the batch job execution with the primary key or throws a <code>NoSuchJobExecutionException</code> if it could not be found.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public BatchJobExecution findByPrimaryKey(long batchJobExecutionId)
		throws NoSuchJobExecutionException;

	/**
	 * Returns the batch job execution with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution, or <code>null</code> if a batch job execution with the primary key could not be found
	 */
	public BatchJobExecution fetchByPrimaryKey(long batchJobExecutionId);

	/**
	 * Returns all the batch job executions.
	 *
	 * @return the batch job executions
	 */
	public java.util.List<BatchJobExecution> findAll();

	/**
	 * Returns a range of all the batch job executions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of batch job executions
	 */
	public java.util.List<BatchJobExecution> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the batch job executions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch job executions
	 */
	public java.util.List<BatchJobExecution> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch job executions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of batch job executions
	 */
	public java.util.List<BatchJobExecution> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobExecution>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the batch job executions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch job executions.
	 *
	 * @return the number of batch job executions
	 */
	public int countAll();

}