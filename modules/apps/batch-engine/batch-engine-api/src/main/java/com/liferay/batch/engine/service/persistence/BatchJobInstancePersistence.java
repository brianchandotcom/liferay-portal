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

import com.liferay.batch.engine.exception.NoSuchJobInstanceException;
import com.liferay.batch.engine.model.BatchJobInstance;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch job instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @see BatchJobInstanceUtil
 * @generated
 */
@ProviderType
public interface BatchJobInstancePersistence
	extends BasePersistence<BatchJobInstance> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchJobInstanceUtil} to access the batch job instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch job instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch job instances
	 */
	public java.util.List<BatchJobInstance> findByUuid(String uuid);

	/**
	 * Returns a range of all the batch job instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @return the range of matching batch job instances
	 */
	public java.util.List<BatchJobInstance> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the batch job instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job instances
	 */
	public java.util.List<BatchJobInstance> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch job instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job instances
	 */
	public java.util.List<BatchJobInstance> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	public BatchJobInstance findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
				orderByComparator)
		throws NoSuchJobInstanceException;

	/**
	 * Returns the first batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public BatchJobInstance fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
			orderByComparator);

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	public BatchJobInstance findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
				orderByComparator)
		throws NoSuchJobInstanceException;

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public BatchJobInstance fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
			orderByComparator);

	/**
	 * Returns the batch job instances before and after the current batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param batchJobInstanceId the primary key of the current batch job instance
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job instance
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	public BatchJobInstance[] findByUuid_PrevAndNext(
			long batchJobInstanceId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
				orderByComparator)
		throws NoSuchJobInstanceException;

	/**
	 * Removes all the batch job instances where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of batch job instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch job instances
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch job instances
	 */
	public java.util.List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @return the range of matching batch job instances
	 */
	public java.util.List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job instances
	 */
	public java.util.List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job instances
	 */
	public java.util.List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	public BatchJobInstance findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
				orderByComparator)
		throws NoSuchJobInstanceException;

	/**
	 * Returns the first batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public BatchJobInstance fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
			orderByComparator);

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	public BatchJobInstance findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
				orderByComparator)
		throws NoSuchJobInstanceException;

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public BatchJobInstance fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
			orderByComparator);

	/**
	 * Returns the batch job instances before and after the current batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchJobInstanceId the primary key of the current batch job instance
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job instance
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	public BatchJobInstance[] findByUuid_C_PrevAndNext(
			long batchJobInstanceId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
				orderByComparator)
		throws NoSuchJobInstanceException;

	/**
	 * Removes all the batch job instances where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch job instances
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the batch job instance where jobName = &#63; and jobKey = &#63; or throws a <code>NoSuchJobInstanceException</code> if it could not be found.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	public BatchJobInstance findByJN_JK(String jobName, String jobKey)
		throws NoSuchJobInstanceException;

	/**
	 * Returns the batch job instance where jobName = &#63; and jobKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public BatchJobInstance fetchByJN_JK(String jobName, String jobKey);

	/**
	 * Returns the batch job instance where jobName = &#63; and jobKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public BatchJobInstance fetchByJN_JK(
		String jobName, String jobKey, boolean retrieveFromCache);

	/**
	 * Removes the batch job instance where jobName = &#63; and jobKey = &#63; from the database.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the batch job instance that was removed
	 */
	public BatchJobInstance removeByJN_JK(String jobName, String jobKey)
		throws NoSuchJobInstanceException;

	/**
	 * Returns the number of batch job instances where jobName = &#63; and jobKey = &#63;.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the number of matching batch job instances
	 */
	public int countByJN_JK(String jobName, String jobKey);

	/**
	 * Caches the batch job instance in the entity cache if it is enabled.
	 *
	 * @param batchJobInstance the batch job instance
	 */
	public void cacheResult(BatchJobInstance batchJobInstance);

	/**
	 * Caches the batch job instances in the entity cache if it is enabled.
	 *
	 * @param batchJobInstances the batch job instances
	 */
	public void cacheResult(java.util.List<BatchJobInstance> batchJobInstances);

	/**
	 * Creates a new batch job instance with the primary key. Does not add the batch job instance to the database.
	 *
	 * @param batchJobInstanceId the primary key for the new batch job instance
	 * @return the new batch job instance
	 */
	public BatchJobInstance create(long batchJobInstanceId);

	/**
	 * Removes the batch job instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance that was removed
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	public BatchJobInstance remove(long batchJobInstanceId)
		throws NoSuchJobInstanceException;

	public BatchJobInstance updateImpl(BatchJobInstance batchJobInstance);

	/**
	 * Returns the batch job instance with the primary key or throws a <code>NoSuchJobInstanceException</code> if it could not be found.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	public BatchJobInstance findByPrimaryKey(long batchJobInstanceId)
		throws NoSuchJobInstanceException;

	/**
	 * Returns the batch job instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance, or <code>null</code> if a batch job instance with the primary key could not be found
	 */
	public BatchJobInstance fetchByPrimaryKey(long batchJobInstanceId);

	/**
	 * Returns all the batch job instances.
	 *
	 * @return the batch job instances
	 */
	public java.util.List<BatchJobInstance> findAll();

	/**
	 * Returns a range of all the batch job instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @return the range of batch job instances
	 */
	public java.util.List<BatchJobInstance> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the batch job instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch job instances
	 */
	public java.util.List<BatchJobInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the batch job instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of batch job instances
	 */
	public java.util.List<BatchJobInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BatchJobInstance>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the batch job instances from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch job instances.
	 *
	 * @return the number of batch job instances
	 */
	public int countAll();

}