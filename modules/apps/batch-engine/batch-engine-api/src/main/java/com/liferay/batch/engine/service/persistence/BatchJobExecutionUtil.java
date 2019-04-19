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

import com.liferay.batch.engine.model.BatchJobExecution;
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
 * The persistence utility for the batch job execution service. This utility wraps <code>com.liferay.batch.engine.service.persistence.impl.BatchJobExecutionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @see BatchJobExecutionPersistence
 * @generated
 */
@ProviderType
public class BatchJobExecutionUtil {

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
	public static void clearCache(BatchJobExecution batchJobExecution) {
		getPersistence().clearCache(batchJobExecution);
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
	public static Map<Serializable, BatchJobExecution> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchJobExecution> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchJobExecution> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchJobExecution> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchJobExecution update(
		BatchJobExecution batchJobExecution) {

		return getPersistence().update(batchJobExecution);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchJobExecution update(
		BatchJobExecution batchJobExecution, ServiceContext serviceContext) {

		return getPersistence().update(batchJobExecution, serviceContext);
	}

	/**
	 * Returns all the batch job executions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch job executions
	 */
	public static List<BatchJobExecution> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<BatchJobExecution> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<BatchJobExecution> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<BatchJobExecution> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public static BatchJobExecution findByUuid_First(
			String uuid, OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public static BatchJobExecution fetchByUuid_First(
		String uuid, OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public static BatchJobExecution findByUuid_Last(
			String uuid, OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public static BatchJobExecution fetchByUuid_Last(
		String uuid, OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public static BatchJobExecution[] findByUuid_PrevAndNext(
			long batchJobExecutionId, String uuid,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findByUuid_PrevAndNext(
			batchJobExecutionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the batch job executions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of batch job executions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch job executions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch job executions
	 */
	public static List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public static BatchJobExecution findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public static BatchJobExecution fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public static BatchJobExecution findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public static BatchJobExecution fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

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
	public static BatchJobExecution[] findByUuid_C_PrevAndNext(
			long batchJobExecutionId, String uuid, long companyId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			batchJobExecutionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the batch job executions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch job executions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the batch job executions where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @return the matching batch job executions
	 */
	public static List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId) {

		return getPersistence().findBybatchJobInstanceId(batchJobInstanceId);
	}

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
	public static List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId, int start, int end) {

		return getPersistence().findBybatchJobInstanceId(
			batchJobInstanceId, start, end);
	}

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
	public static List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().findBybatchJobInstanceId(
			batchJobInstanceId, start, end, orderByComparator);
	}

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
	public static List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findBybatchJobInstanceId(
			batchJobInstanceId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public static BatchJobExecution findBybatchJobInstanceId_First(
			long batchJobInstanceId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findBybatchJobInstanceId_First(
			batchJobInstanceId, orderByComparator);
	}

	/**
	 * Returns the first batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public static BatchJobExecution fetchBybatchJobInstanceId_First(
		long batchJobInstanceId,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().fetchBybatchJobInstanceId_First(
			batchJobInstanceId, orderByComparator);
	}

	/**
	 * Returns the last batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public static BatchJobExecution findBybatchJobInstanceId_Last(
			long batchJobInstanceId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findBybatchJobInstanceId_Last(
			batchJobInstanceId, orderByComparator);
	}

	/**
	 * Returns the last batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public static BatchJobExecution fetchBybatchJobInstanceId_Last(
		long batchJobInstanceId,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().fetchBybatchJobInstanceId_Last(
			batchJobInstanceId, orderByComparator);
	}

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public static BatchJobExecution[] findBybatchJobInstanceId_PrevAndNext(
			long batchJobExecutionId, long batchJobInstanceId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findBybatchJobInstanceId_PrevAndNext(
			batchJobExecutionId, batchJobInstanceId, orderByComparator);
	}

	/**
	 * Removes all the batch job executions where batchJobInstanceId = &#63; from the database.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 */
	public static void removeBybatchJobInstanceId(long batchJobInstanceId) {
		getPersistence().removeBybatchJobInstanceId(batchJobInstanceId);
	}

	/**
	 * Returns the number of batch job executions where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @return the number of matching batch job executions
	 */
	public static int countBybatchJobInstanceId(long batchJobInstanceId) {
		return getPersistence().countBybatchJobInstanceId(batchJobInstanceId);
	}

	/**
	 * Returns all the batch job executions where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching batch job executions
	 */
	public static List<BatchJobExecution> findBystatus(String status) {
		return getPersistence().findBystatus(status);
	}

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
	public static List<BatchJobExecution> findBystatus(
		String status, int start, int end) {

		return getPersistence().findBystatus(status, start, end);
	}

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
	public static List<BatchJobExecution> findBystatus(
		String status, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().findBystatus(
			status, start, end, orderByComparator);
	}

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
	public static List<BatchJobExecution> findBystatus(
		String status, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findBystatus(
			status, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public static BatchJobExecution findBystatus_First(
			String status,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findBystatus_First(status, orderByComparator);
	}

	/**
	 * Returns the first batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public static BatchJobExecution fetchBystatus_First(
		String status, OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().fetchBystatus_First(status, orderByComparator);
	}

	/**
	 * Returns the last batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	public static BatchJobExecution findBystatus_Last(
			String status,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findBystatus_Last(status, orderByComparator);
	}

	/**
	 * Returns the last batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	public static BatchJobExecution fetchBystatus_Last(
		String status, OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().fetchBystatus_Last(status, orderByComparator);
	}

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where status = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public static BatchJobExecution[] findBystatus_PrevAndNext(
			long batchJobExecutionId, String status,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findBystatus_PrevAndNext(
			batchJobExecutionId, status, orderByComparator);
	}

	/**
	 * Removes all the batch job executions where status = &#63; from the database.
	 *
	 * @param status the status
	 */
	public static void removeBystatus(String status) {
		getPersistence().removeBystatus(status);
	}

	/**
	 * Returns the number of batch job executions where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching batch job executions
	 */
	public static int countBystatus(String status) {
		return getPersistence().countBystatus(status);
	}

	/**
	 * Caches the batch job execution in the entity cache if it is enabled.
	 *
	 * @param batchJobExecution the batch job execution
	 */
	public static void cacheResult(BatchJobExecution batchJobExecution) {
		getPersistence().cacheResult(batchJobExecution);
	}

	/**
	 * Caches the batch job executions in the entity cache if it is enabled.
	 *
	 * @param batchJobExecutions the batch job executions
	 */
	public static void cacheResult(List<BatchJobExecution> batchJobExecutions) {
		getPersistence().cacheResult(batchJobExecutions);
	}

	/**
	 * Creates a new batch job execution with the primary key. Does not add the batch job execution to the database.
	 *
	 * @param batchJobExecutionId the primary key for the new batch job execution
	 * @return the new batch job execution
	 */
	public static BatchJobExecution create(long batchJobExecutionId) {
		return getPersistence().create(batchJobExecutionId);
	}

	/**
	 * Removes the batch job execution with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution that was removed
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public static BatchJobExecution remove(long batchJobExecutionId)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().remove(batchJobExecutionId);
	}

	public static BatchJobExecution updateImpl(
		BatchJobExecution batchJobExecution) {

		return getPersistence().updateImpl(batchJobExecution);
	}

	/**
	 * Returns the batch job execution with the primary key or throws a <code>NoSuchJobExecutionException</code> if it could not be found.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	public static BatchJobExecution findByPrimaryKey(long batchJobExecutionId)
		throws com.liferay.batch.engine.exception.NoSuchJobExecutionException {

		return getPersistence().findByPrimaryKey(batchJobExecutionId);
	}

	/**
	 * Returns the batch job execution with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution, or <code>null</code> if a batch job execution with the primary key could not be found
	 */
	public static BatchJobExecution fetchByPrimaryKey(
		long batchJobExecutionId) {

		return getPersistence().fetchByPrimaryKey(batchJobExecutionId);
	}

	/**
	 * Returns all the batch job executions.
	 *
	 * @return the batch job executions
	 */
	public static List<BatchJobExecution> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<BatchJobExecution> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<BatchJobExecution> findAll(
		int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<BatchJobExecution> findAll(
		int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the batch job executions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch job executions.
	 *
	 * @return the number of batch job executions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchJobExecutionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchJobExecutionPersistence, BatchJobExecutionPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchJobExecutionPersistence.class);

		ServiceTracker
			<BatchJobExecutionPersistence, BatchJobExecutionPersistence>
				serviceTracker =
					new ServiceTracker
						<BatchJobExecutionPersistence,
						 BatchJobExecutionPersistence>(
							 bundle.getBundleContext(),
							 BatchJobExecutionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}