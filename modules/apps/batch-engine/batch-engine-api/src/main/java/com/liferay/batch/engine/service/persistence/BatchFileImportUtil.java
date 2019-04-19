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

import com.liferay.batch.engine.model.BatchFileImport;
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
 * The persistence utility for the batch file import service. This utility wraps <code>com.liferay.batch.engine.service.persistence.impl.BatchFileImportPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @see BatchFileImportPersistence
 * @generated
 */
@ProviderType
public class BatchFileImportUtil {

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
	public static void clearCache(BatchFileImport batchFileImport) {
		getPersistence().clearCache(batchFileImport);
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
	public static Map<Serializable, BatchFileImport> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchFileImport> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchFileImport> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchFileImport> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchFileImport update(BatchFileImport batchFileImport) {
		return getPersistence().update(batchFileImport);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchFileImport update(
		BatchFileImport batchFileImport, ServiceContext serviceContext) {

		return getPersistence().update(batchFileImport, serviceContext);
	}

	/**
	 * Returns all the batch file imports where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch file imports
	 */
	public static List<BatchFileImport> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the batch file imports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of matching batch file imports
	 */
	public static List<BatchFileImport> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch file imports
	 */
	public static List<BatchFileImport> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch file imports
	 */
	public static List<BatchFileImport> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public static BatchFileImport findByUuid_First(
			String uuid, OrderByComparator<BatchFileImport> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public static BatchFileImport fetchByUuid_First(
		String uuid, OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public static BatchFileImport findByUuid_Last(
			String uuid, OrderByComparator<BatchFileImport> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public static BatchFileImport fetchByUuid_Last(
		String uuid, OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the batch file imports before and after the current batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param batchFileImportId the primary key of the current batch file import
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	public static BatchFileImport[] findByUuid_PrevAndNext(
			long batchFileImportId, String uuid,
			OrderByComparator<BatchFileImport> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByUuid_PrevAndNext(
			batchFileImportId, uuid, orderByComparator);
	}

	/**
	 * Removes all the batch file imports where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of batch file imports where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch file imports
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch file imports
	 */
	public static List<BatchFileImport> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of matching batch file imports
	 */
	public static List<BatchFileImport> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch file imports
	 */
	public static List<BatchFileImport> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch file imports
	 */
	public static List<BatchFileImport> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public static BatchFileImport findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchFileImport> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public static BatchFileImport fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public static BatchFileImport findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BatchFileImport> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public static BatchFileImport fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the batch file imports before and after the current batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchFileImportId the primary key of the current batch file import
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	public static BatchFileImport[] findByUuid_C_PrevAndNext(
			long batchFileImportId, String uuid, long companyId,
			OrderByComparator<BatchFileImport> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByUuid_C_PrevAndNext(
			batchFileImportId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the batch file imports where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch file imports
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the batch file import where batchJobExecutionId = &#63; or throws a <code>NoSuchFileImportException</code> if it could not be found.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public static BatchFileImport findByBatchJobExecutionId(
			long batchJobExecutionId)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByBatchJobExecutionId(batchJobExecutionId);
	}

	/**
	 * Returns the batch file import where batchJobExecutionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public static BatchFileImport fetchByBatchJobExecutionId(
		long batchJobExecutionId) {

		return getPersistence().fetchByBatchJobExecutionId(batchJobExecutionId);
	}

	/**
	 * Returns the batch file import where batchJobExecutionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public static BatchFileImport fetchByBatchJobExecutionId(
		long batchJobExecutionId, boolean retrieveFromCache) {

		return getPersistence().fetchByBatchJobExecutionId(
			batchJobExecutionId, retrieveFromCache);
	}

	/**
	 * Removes the batch file import where batchJobExecutionId = &#63; from the database.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the batch file import that was removed
	 */
	public static BatchFileImport removeByBatchJobExecutionId(
			long batchJobExecutionId)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().removeByBatchJobExecutionId(
			batchJobExecutionId);
	}

	/**
	 * Returns the number of batch file imports where batchJobExecutionId = &#63;.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the number of matching batch file imports
	 */
	public static int countByBatchJobExecutionId(long batchJobExecutionId) {
		return getPersistence().countByBatchJobExecutionId(batchJobExecutionId);
	}

	/**
	 * Returns all the batch file imports where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching batch file imports
	 */
	public static List<BatchFileImport> findByStatus(String status) {
		return getPersistence().findByStatus(status);
	}

	/**
	 * Returns a range of all the batch file imports where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of matching batch file imports
	 */
	public static List<BatchFileImport> findByStatus(
		String status, int start, int end) {

		return getPersistence().findByStatus(status, start, end);
	}

	/**
	 * Returns an ordered range of all the batch file imports where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch file imports
	 */
	public static List<BatchFileImport> findByStatus(
		String status, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().findByStatus(
			status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch file imports where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch file imports
	 */
	public static List<BatchFileImport> findByStatus(
		String status, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByStatus(
			status, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public static BatchFileImport findByStatus_First(
			String status, OrderByComparator<BatchFileImport> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByStatus_First(status, orderByComparator);
	}

	/**
	 * Returns the first batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public static BatchFileImport fetchByStatus_First(
		String status, OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().fetchByStatus_First(status, orderByComparator);
	}

	/**
	 * Returns the last batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	public static BatchFileImport findByStatus_Last(
			String status, OrderByComparator<BatchFileImport> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByStatus_Last(status, orderByComparator);
	}

	/**
	 * Returns the last batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	public static BatchFileImport fetchByStatus_Last(
		String status, OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().fetchByStatus_Last(status, orderByComparator);
	}

	/**
	 * Returns the batch file imports before and after the current batch file import in the ordered set where status = &#63;.
	 *
	 * @param batchFileImportId the primary key of the current batch file import
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	public static BatchFileImport[] findByStatus_PrevAndNext(
			long batchFileImportId, String status,
			OrderByComparator<BatchFileImport> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByStatus_PrevAndNext(
			batchFileImportId, status, orderByComparator);
	}

	/**
	 * Removes all the batch file imports where status = &#63; from the database.
	 *
	 * @param status the status
	 */
	public static void removeByStatus(String status) {
		getPersistence().removeByStatus(status);
	}

	/**
	 * Returns the number of batch file imports where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching batch file imports
	 */
	public static int countByStatus(String status) {
		return getPersistence().countByStatus(status);
	}

	/**
	 * Caches the batch file import in the entity cache if it is enabled.
	 *
	 * @param batchFileImport the batch file import
	 */
	public static void cacheResult(BatchFileImport batchFileImport) {
		getPersistence().cacheResult(batchFileImport);
	}

	/**
	 * Caches the batch file imports in the entity cache if it is enabled.
	 *
	 * @param batchFileImports the batch file imports
	 */
	public static void cacheResult(List<BatchFileImport> batchFileImports) {
		getPersistence().cacheResult(batchFileImports);
	}

	/**
	 * Creates a new batch file import with the primary key. Does not add the batch file import to the database.
	 *
	 * @param batchFileImportId the primary key for the new batch file import
	 * @return the new batch file import
	 */
	public static BatchFileImport create(long batchFileImportId) {
		return getPersistence().create(batchFileImportId);
	}

	/**
	 * Removes the batch file import with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import that was removed
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	public static BatchFileImport remove(long batchFileImportId)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().remove(batchFileImportId);
	}

	public static BatchFileImport updateImpl(BatchFileImport batchFileImport) {
		return getPersistence().updateImpl(batchFileImport);
	}

	/**
	 * Returns the batch file import with the primary key or throws a <code>NoSuchFileImportException</code> if it could not be found.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	public static BatchFileImport findByPrimaryKey(long batchFileImportId)
		throws com.liferay.batch.engine.exception.NoSuchFileImportException {

		return getPersistence().findByPrimaryKey(batchFileImportId);
	}

	/**
	 * Returns the batch file import with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import, or <code>null</code> if a batch file import with the primary key could not be found
	 */
	public static BatchFileImport fetchByPrimaryKey(long batchFileImportId) {
		return getPersistence().fetchByPrimaryKey(batchFileImportId);
	}

	/**
	 * Returns all the batch file imports.
	 *
	 * @return the batch file imports
	 */
	public static List<BatchFileImport> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the batch file imports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of batch file imports
	 */
	public static List<BatchFileImport> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the batch file imports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch file imports
	 */
	public static List<BatchFileImport> findAll(
		int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch file imports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of batch file imports
	 */
	public static List<BatchFileImport> findAll(
		int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the batch file imports from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch file imports.
	 *
	 * @return the number of batch file imports
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchFileImportPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchFileImportPersistence, BatchFileImportPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchFileImportPersistence.class);

		ServiceTracker<BatchFileImportPersistence, BatchFileImportPersistence>
			serviceTracker =
				new ServiceTracker
					<BatchFileImportPersistence, BatchFileImportPersistence>(
						bundle.getBundleContext(),
						BatchFileImportPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}