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

import com.liferay.batch.engine.model.BatchJobInstance;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the batch job instance service. This utility wraps <code>com.liferay.batch.engine.service.persistence.impl.BatchJobInstancePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @see BatchJobInstancePersistence
 * @generated
 */
@ProviderType
public class BatchJobInstanceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(BatchJobInstance batchJobInstance) {
		getPersistence().clearCache(batchJobInstance);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, BatchJobInstance> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchJobInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchJobInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchJobInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchJobInstance update(BatchJobInstance batchJobInstance) {
		return getPersistence().update(batchJobInstance);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchJobInstance update(
		BatchJobInstance batchJobInstance, ServiceContext serviceContext) {

		return getPersistence().update(batchJobInstance, serviceContext);
	}

	/**
	 * Returns all the batch job instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch job instances
	 */
	public static List<BatchJobInstance> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<BatchJobInstance> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<BatchJobInstance> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<BatchJobInstance> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	public static BatchJobInstance findByUuid_First(
			String uuid, OrderByComparator<BatchJobInstance> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobInstanceException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public static BatchJobInstance fetchByUuid_First(
		String uuid, OrderByComparator<BatchJobInstance> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	public static BatchJobInstance findByUuid_Last(
			String uuid, OrderByComparator<BatchJobInstance> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobInstanceException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public static BatchJobInstance fetchByUuid_Last(
		String uuid, OrderByComparator<BatchJobInstance> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the batch job instances before and after the current batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param batchJobInstanceId the primary key of the current batch job instance
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job instance
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	public static BatchJobInstance[] findByUuid_PrevAndNext(
			long batchJobInstanceId, String uuid,
			OrderByComparator<BatchJobInstance> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobInstanceException {

		return getPersistence().findByUuid_PrevAndNext(
			batchJobInstanceId, uuid, orderByComparator);
	}

	/**
	 * Removes all the batch job instances where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of batch job instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch job instances
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch job instances
	 */
	public static List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	public static BatchJobInstance findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchJobInstance> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobInstanceException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public static BatchJobInstance fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	public static BatchJobInstance findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BatchJobInstance> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobInstanceException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public static BatchJobInstance fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

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
	public static BatchJobInstance[] findByUuid_C_PrevAndNext(
			long batchJobInstanceId, String uuid, long companyId,
			OrderByComparator<BatchJobInstance> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobInstanceException {

		return getPersistence().findByUuid_C_PrevAndNext(
			batchJobInstanceId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the batch job instances where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch job instances
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the batch job instance where jobName = &#63; and jobKey = &#63; or throws a <code>NoSuchJobInstanceException</code> if it could not be found.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	public static BatchJobInstance findByJN_JK(String jobName, String jobKey)
		throws com.liferay.batch.engine.exception.NoSuchJobInstanceException {

		return getPersistence().findByJN_JK(jobName, jobKey);
	}

	/**
	 * Returns the batch job instance where jobName = &#63; and jobKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public static BatchJobInstance fetchByJN_JK(String jobName, String jobKey) {
		return getPersistence().fetchByJN_JK(jobName, jobKey);
	}

	/**
	 * Returns the batch job instance where jobName = &#63; and jobKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	public static BatchJobInstance fetchByJN_JK(
		String jobName, String jobKey, boolean retrieveFromCache) {

		return getPersistence().fetchByJN_JK(
			jobName, jobKey, retrieveFromCache);
	}

	/**
	 * Removes the batch job instance where jobName = &#63; and jobKey = &#63; from the database.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the batch job instance that was removed
	 */
	public static BatchJobInstance removeByJN_JK(String jobName, String jobKey)
		throws com.liferay.batch.engine.exception.NoSuchJobInstanceException {

		return getPersistence().removeByJN_JK(jobName, jobKey);
	}

	/**
	 * Returns the number of batch job instances where jobName = &#63; and jobKey = &#63;.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the number of matching batch job instances
	 */
	public static int countByJN_JK(String jobName, String jobKey) {
		return getPersistence().countByJN_JK(jobName, jobKey);
	}

	/**
	 * Caches the batch job instance in the entity cache if it is enabled.
	 *
	 * @param batchJobInstance the batch job instance
	 */
	public static void cacheResult(BatchJobInstance batchJobInstance) {
		getPersistence().cacheResult(batchJobInstance);
	}

	/**
	 * Caches the batch job instances in the entity cache if it is enabled.
	 *
	 * @param batchJobInstances the batch job instances
	 */
	public static void cacheResult(List<BatchJobInstance> batchJobInstances) {
		getPersistence().cacheResult(batchJobInstances);
	}

	/**
	 * Creates a new batch job instance with the primary key. Does not add the batch job instance to the database.
	 *
	 * @param batchJobInstanceId the primary key for the new batch job instance
	 * @return the new batch job instance
	 */
	public static BatchJobInstance create(long batchJobInstanceId) {
		return getPersistence().create(batchJobInstanceId);
	}

	/**
	 * Removes the batch job instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance that was removed
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	public static BatchJobInstance remove(long batchJobInstanceId)
		throws com.liferay.batch.engine.exception.NoSuchJobInstanceException {

		return getPersistence().remove(batchJobInstanceId);
	}

	public static BatchJobInstance updateImpl(
		BatchJobInstance batchJobInstance) {

		return getPersistence().updateImpl(batchJobInstance);
	}

	/**
	 * Returns the batch job instance with the primary key or throws a <code>NoSuchJobInstanceException</code> if it could not be found.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	public static BatchJobInstance findByPrimaryKey(long batchJobInstanceId)
		throws com.liferay.batch.engine.exception.NoSuchJobInstanceException {

		return getPersistence().findByPrimaryKey(batchJobInstanceId);
	}

	/**
	 * Returns the batch job instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance, or <code>null</code> if a batch job instance with the primary key could not be found
	 */
	public static BatchJobInstance fetchByPrimaryKey(long batchJobInstanceId) {
		return getPersistence().fetchByPrimaryKey(batchJobInstanceId);
	}

	/**
	 * Returns all the batch job instances.
	 *
	 * @return the batch job instances
	 */
	public static List<BatchJobInstance> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<BatchJobInstance> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<BatchJobInstance> findAll(
		int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<BatchJobInstance> findAll(
		int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the batch job instances from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch job instances.
	 *
	 * @return the number of batch job instances
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchJobInstancePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchJobInstancePersistence, BatchJobInstancePersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchJobInstancePersistence.class);

		ServiceTracker<BatchJobInstancePersistence, BatchJobInstancePersistence>
			serviceTracker =
				new ServiceTracker
					<BatchJobInstancePersistence, BatchJobInstancePersistence>(
						bundle.getBundleContext(),
						BatchJobInstancePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}