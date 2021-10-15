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

package com.liferay.batch.planner.service.persistence;

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the batch planner log service. This utility wraps <code>com.liferay.batch.planner.service.persistence.impl.BatchPlannerLogPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerLogPersistence
 * @generated
 */
public class BatchPlannerLogUtil {

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
	public static void clearCache(BatchPlannerLog batchPlannerLog) {
		getPersistence().clearCache(batchPlannerLog);
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
	public static Map<Serializable, BatchPlannerLog> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchPlannerLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchPlannerLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchPlannerLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchPlannerLog update(BatchPlannerLog batchPlannerLog) {
		return getPersistence().update(batchPlannerLog);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchPlannerLog update(
		BatchPlannerLog batchPlannerLog, ServiceContext serviceContext) {

		return getPersistence().update(batchPlannerLog, serviceContext);
	}

	/**
	 * Returns all the batch planner logs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching batch planner logs
	 */
	public static List<BatchPlannerLog> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the batch planner logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @return the range of matching batch planner logs
	 */
	public static List<BatchPlannerLog> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner logs
	 */
	public static List<BatchPlannerLog> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner logs
	 */
	public static List<BatchPlannerLog> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch planner log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog findByCompanyId_First(
			long companyId,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first batch planner log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog fetchByCompanyId_First(
		long companyId, OrderByComparator<BatchPlannerLog> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last batch planner log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog findByCompanyId_Last(
			long companyId,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last batch planner log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog fetchByCompanyId_Last(
		long companyId, OrderByComparator<BatchPlannerLog> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the batch planner logs before and after the current batch planner log in the ordered set where companyId = &#63;.
	 *
	 * @param batchPlannerLogId the primary key of the current batch planner log
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner log
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	public static BatchPlannerLog[] findByCompanyId_PrevAndNext(
			long batchPlannerLogId, long companyId,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().findByCompanyId_PrevAndNext(
			batchPlannerLogId, companyId, orderByComparator);
	}

	/**
	 * Removes all the batch planner logs where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of batch planner logs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching batch planner logs
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog findByBatchPlannerPlanId(
			long batchPlannerPlanId)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().findByBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog fetchByBatchPlannerPlanId(
		long batchPlannerPlanId) {

		return getPersistence().fetchByBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog fetchByBatchPlannerPlanId(
		long batchPlannerPlanId, boolean useFinderCache) {

		return getPersistence().fetchByBatchPlannerPlanId(
			batchPlannerPlanId, useFinderCache);
	}

	/**
	 * Removes the batch planner log where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the batch planner log that was removed
	 */
	public static BatchPlannerLog removeByBatchPlannerPlanId(
			long batchPlannerPlanId)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().removeByBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the number of matching batch planner logs
	 */
	public static int countByBatchPlannerPlanId(long batchPlannerPlanId) {
		return getPersistence().countByBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineExportTaskERC = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineExportTaskERC the batch engine export task erc
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog findByBPPI_BEETERC(
			long batchPlannerPlanId, String batchEngineExportTaskERC)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().findByBPPI_BEETERC(
			batchPlannerPlanId, batchEngineExportTaskERC);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineExportTaskERC = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineExportTaskERC the batch engine export task erc
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog fetchByBPPI_BEETERC(
		long batchPlannerPlanId, String batchEngineExportTaskERC) {

		return getPersistence().fetchByBPPI_BEETERC(
			batchPlannerPlanId, batchEngineExportTaskERC);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineExportTaskERC = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineExportTaskERC the batch engine export task erc
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog fetchByBPPI_BEETERC(
		long batchPlannerPlanId, String batchEngineExportTaskERC,
		boolean useFinderCache) {

		return getPersistence().fetchByBPPI_BEETERC(
			batchPlannerPlanId, batchEngineExportTaskERC, useFinderCache);
	}

	/**
	 * Removes the batch planner log where batchPlannerPlanId = &#63; and batchEngineExportTaskERC = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineExportTaskERC the batch engine export task erc
	 * @return the batch planner log that was removed
	 */
	public static BatchPlannerLog removeByBPPI_BEETERC(
			long batchPlannerPlanId, String batchEngineExportTaskERC)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().removeByBPPI_BEETERC(
			batchPlannerPlanId, batchEngineExportTaskERC);
	}

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63; and batchEngineExportTaskERC = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineExportTaskERC the batch engine export task erc
	 * @return the number of matching batch planner logs
	 */
	public static int countByBPPI_BEETERC(
		long batchPlannerPlanId, String batchEngineExportTaskERC) {

		return getPersistence().countByBPPI_BEETERC(
			batchPlannerPlanId, batchEngineExportTaskERC);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineImportTaskERC = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineImportTaskERC the batch engine import task erc
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog findByBPPI_BEITERC(
			long batchPlannerPlanId, String batchEngineImportTaskERC)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().findByBPPI_BEITERC(
			batchPlannerPlanId, batchEngineImportTaskERC);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineImportTaskERC = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineImportTaskERC the batch engine import task erc
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog fetchByBPPI_BEITERC(
		long batchPlannerPlanId, String batchEngineImportTaskERC) {

		return getPersistence().fetchByBPPI_BEITERC(
			batchPlannerPlanId, batchEngineImportTaskERC);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineImportTaskERC = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineImportTaskERC the batch engine import task erc
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog fetchByBPPI_BEITERC(
		long batchPlannerPlanId, String batchEngineImportTaskERC,
		boolean useFinderCache) {

		return getPersistence().fetchByBPPI_BEITERC(
			batchPlannerPlanId, batchEngineImportTaskERC, useFinderCache);
	}

	/**
	 * Removes the batch planner log where batchPlannerPlanId = &#63; and batchEngineImportTaskERC = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineImportTaskERC the batch engine import task erc
	 * @return the batch planner log that was removed
	 */
	public static BatchPlannerLog removeByBPPI_BEITERC(
			long batchPlannerPlanId, String batchEngineImportTaskERC)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().removeByBPPI_BEITERC(
			batchPlannerPlanId, batchEngineImportTaskERC);
	}

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63; and batchEngineImportTaskERC = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineImportTaskERC the batch engine import task erc
	 * @return the number of matching batch planner logs
	 */
	public static int countByBPPI_BEITERC(
		long batchPlannerPlanId, String batchEngineImportTaskERC) {

		return getPersistence().countByBPPI_BEITERC(
			batchPlannerPlanId, batchEngineImportTaskERC);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and dispatchTriggerERC = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchTriggerERC the dispatch trigger erc
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog findByBPPI_DTERC(
			long batchPlannerPlanId, String dispatchTriggerERC)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().findByBPPI_DTERC(
			batchPlannerPlanId, dispatchTriggerERC);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and dispatchTriggerERC = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchTriggerERC the dispatch trigger erc
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog fetchByBPPI_DTERC(
		long batchPlannerPlanId, String dispatchTriggerERC) {

		return getPersistence().fetchByBPPI_DTERC(
			batchPlannerPlanId, dispatchTriggerERC);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and dispatchTriggerERC = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchTriggerERC the dispatch trigger erc
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	public static BatchPlannerLog fetchByBPPI_DTERC(
		long batchPlannerPlanId, String dispatchTriggerERC,
		boolean useFinderCache) {

		return getPersistence().fetchByBPPI_DTERC(
			batchPlannerPlanId, dispatchTriggerERC, useFinderCache);
	}

	/**
	 * Removes the batch planner log where batchPlannerPlanId = &#63; and dispatchTriggerERC = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchTriggerERC the dispatch trigger erc
	 * @return the batch planner log that was removed
	 */
	public static BatchPlannerLog removeByBPPI_DTERC(
			long batchPlannerPlanId, String dispatchTriggerERC)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().removeByBPPI_DTERC(
			batchPlannerPlanId, dispatchTriggerERC);
	}

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63; and dispatchTriggerERC = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchTriggerERC the dispatch trigger erc
	 * @return the number of matching batch planner logs
	 */
	public static int countByBPPI_DTERC(
		long batchPlannerPlanId, String dispatchTriggerERC) {

		return getPersistence().countByBPPI_DTERC(
			batchPlannerPlanId, dispatchTriggerERC);
	}

	/**
	 * Caches the batch planner log in the entity cache if it is enabled.
	 *
	 * @param batchPlannerLog the batch planner log
	 */
	public static void cacheResult(BatchPlannerLog batchPlannerLog) {
		getPersistence().cacheResult(batchPlannerLog);
	}

	/**
	 * Caches the batch planner logs in the entity cache if it is enabled.
	 *
	 * @param batchPlannerLogs the batch planner logs
	 */
	public static void cacheResult(List<BatchPlannerLog> batchPlannerLogs) {
		getPersistence().cacheResult(batchPlannerLogs);
	}

	/**
	 * Creates a new batch planner log with the primary key. Does not add the batch planner log to the database.
	 *
	 * @param batchPlannerLogId the primary key for the new batch planner log
	 * @return the new batch planner log
	 */
	public static BatchPlannerLog create(long batchPlannerLogId) {
		return getPersistence().create(batchPlannerLogId);
	}

	/**
	 * Removes the batch planner log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log that was removed
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	public static BatchPlannerLog remove(long batchPlannerLogId)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().remove(batchPlannerLogId);
	}

	public static BatchPlannerLog updateImpl(BatchPlannerLog batchPlannerLog) {
		return getPersistence().updateImpl(batchPlannerLog);
	}

	/**
	 * Returns the batch planner log with the primary key or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	public static BatchPlannerLog findByPrimaryKey(long batchPlannerLogId)
		throws com.liferay.batch.planner.exception.NoSuchLogException {

		return getPersistence().findByPrimaryKey(batchPlannerLogId);
	}

	/**
	 * Returns the batch planner log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log, or <code>null</code> if a batch planner log with the primary key could not be found
	 */
	public static BatchPlannerLog fetchByPrimaryKey(long batchPlannerLogId) {
		return getPersistence().fetchByPrimaryKey(batchPlannerLogId);
	}

	/**
	 * Returns all the batch planner logs.
	 *
	 * @return the batch planner logs
	 */
	public static List<BatchPlannerLog> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the batch planner logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @return the range of batch planner logs
	 */
	public static List<BatchPlannerLog> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch planner logs
	 */
	public static List<BatchPlannerLog> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch planner logs
	 */
	public static List<BatchPlannerLog> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the batch planner logs from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch planner logs.
	 *
	 * @return the number of batch planner logs
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchPlannerLogPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchPlannerLogPersistence, BatchPlannerLogPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchPlannerLogPersistence.class);

		ServiceTracker<BatchPlannerLogPersistence, BatchPlannerLogPersistence>
			serviceTracker =
				new ServiceTracker
					<BatchPlannerLogPersistence, BatchPlannerLogPersistence>(
						bundle.getBundleContext(),
						BatchPlannerLogPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}