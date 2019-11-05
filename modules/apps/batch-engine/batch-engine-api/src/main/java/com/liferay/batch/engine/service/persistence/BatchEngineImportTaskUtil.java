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

import com.liferay.batch.engine.model.BatchEngineImportTask;
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
 * The persistence utility for the batch engine import task service. This utility wraps <code>com.liferay.batch.engine.service.persistence.impl.BatchEngineImportTaskPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskPersistence
 * @generated
 */
public class BatchEngineImportTaskUtil {

	/**
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
	public static void clearCache(BatchEngineImportTask batchEngineImportTask) {
		getPersistence().clearCache(batchEngineImportTask);
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
	public static Map<Serializable, BatchEngineImportTask> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchEngineImportTask> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchEngineImportTask> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchEngineImportTask> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchEngineImportTask update(
		BatchEngineImportTask batchEngineImportTask) {

		return getPersistence().update(batchEngineImportTask);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchEngineImportTask update(
		BatchEngineImportTask batchEngineImportTask,
		ServiceContext serviceContext) {

		return getPersistence().update(batchEngineImportTask, serviceContext);
	}

	/**
	 * Returns all the batch engine import tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the batch engine import tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch engine import task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask findByUuid_First(
			String uuid,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first batch engine import task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask fetchByUuid_First(
		String uuid,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last batch engine import task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask findByUuid_Last(
			String uuid,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last batch engine import task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask fetchByUuid_Last(
		String uuid,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the batch engine import tasks before and after the current batch engine import task in the ordered set where uuid = &#63;.
	 *
	 * @param batchEngineImportTaskId the primary key of the current batch engine import task
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine import task
	 * @throws NoSuchImportTaskException if a batch engine import task with the primary key could not be found
	 */
	public static BatchEngineImportTask[] findByUuid_PrevAndNext(
			long batchEngineImportTaskId, String uuid,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().findByUuid_PrevAndNext(
			batchEngineImportTaskId, uuid, orderByComparator);
	}

	/**
	 * Removes all the batch engine import tasks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of batch engine import tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch engine import tasks
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the batch engine import tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the batch engine import tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch engine import task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first batch engine import task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last batch engine import task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last batch engine import task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the batch engine import tasks before and after the current batch engine import task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchEngineImportTaskId the primary key of the current batch engine import task
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine import task
	 * @throws NoSuchImportTaskException if a batch engine import task with the primary key could not be found
	 */
	public static BatchEngineImportTask[] findByUuid_C_PrevAndNext(
			long batchEngineImportTaskId, String uuid, long companyId,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().findByUuid_C_PrevAndNext(
			batchEngineImportTaskId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the batch engine import tasks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of batch engine import tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch engine import tasks
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the batch engine import tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByExecuteStatus(
		String executeStatus) {

		return getPersistence().findByExecuteStatus(executeStatus);
	}

	/**
	 * Returns a range of all the batch engine import tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByExecuteStatus(
		String executeStatus, int start, int end) {

		return getPersistence().findByExecuteStatus(executeStatus, start, end);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().findByExecuteStatus(
			executeStatus, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByExecuteStatus(
			executeStatus, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch engine import task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask findByExecuteStatus_First(
			String executeStatus,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().findByExecuteStatus_First(
			executeStatus, orderByComparator);
	}

	/**
	 * Returns the first batch engine import task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask fetchByExecuteStatus_First(
		String executeStatus,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().fetchByExecuteStatus_First(
			executeStatus, orderByComparator);
	}

	/**
	 * Returns the last batch engine import task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task
	 * @throws NoSuchImportTaskException if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask findByExecuteStatus_Last(
			String executeStatus,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().findByExecuteStatus_Last(
			executeStatus, orderByComparator);
	}

	/**
	 * Returns the last batch engine import task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task, or <code>null</code> if a matching batch engine import task could not be found
	 */
	public static BatchEngineImportTask fetchByExecuteStatus_Last(
		String executeStatus,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().fetchByExecuteStatus_Last(
			executeStatus, orderByComparator);
	}

	/**
	 * Returns the batch engine import tasks before and after the current batch engine import task in the ordered set where executeStatus = &#63;.
	 *
	 * @param batchEngineImportTaskId the primary key of the current batch engine import task
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine import task
	 * @throws NoSuchImportTaskException if a batch engine import task with the primary key could not be found
	 */
	public static BatchEngineImportTask[] findByExecuteStatus_PrevAndNext(
			long batchEngineImportTaskId, String executeStatus,
			OrderByComparator<BatchEngineImportTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().findByExecuteStatus_PrevAndNext(
			batchEngineImportTaskId, executeStatus, orderByComparator);
	}

	/**
	 * Removes all the batch engine import tasks where executeStatus = &#63; from the database.
	 *
	 * @param executeStatus the execute status
	 */
	public static void removeByExecuteStatus(String executeStatus) {
		getPersistence().removeByExecuteStatus(executeStatus);
	}

	/**
	 * Returns the number of batch engine import tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the number of matching batch engine import tasks
	 */
	public static int countByExecuteStatus(String executeStatus) {
		return getPersistence().countByExecuteStatus(executeStatus);
	}

	/**
	 * Caches the batch engine import task in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportTask the batch engine import task
	 */
	public static void cacheResult(
		BatchEngineImportTask batchEngineImportTask) {

		getPersistence().cacheResult(batchEngineImportTask);
	}

	/**
	 * Caches the batch engine import tasks in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportTasks the batch engine import tasks
	 */
	public static void cacheResult(
		List<BatchEngineImportTask> batchEngineImportTasks) {

		getPersistence().cacheResult(batchEngineImportTasks);
	}

	/**
	 * Creates a new batch engine import task with the primary key. Does not add the batch engine import task to the database.
	 *
	 * @param batchEngineImportTaskId the primary key for the new batch engine import task
	 * @return the new batch engine import task
	 */
	public static BatchEngineImportTask create(long batchEngineImportTaskId) {
		return getPersistence().create(batchEngineImportTaskId);
	}

	/**
	 * Removes the batch engine import task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTaskId the primary key of the batch engine import task
	 * @return the batch engine import task that was removed
	 * @throws NoSuchImportTaskException if a batch engine import task with the primary key could not be found
	 */
	public static BatchEngineImportTask remove(long batchEngineImportTaskId)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().remove(batchEngineImportTaskId);
	}

	public static BatchEngineImportTask updateImpl(
		BatchEngineImportTask batchEngineImportTask) {

		return getPersistence().updateImpl(batchEngineImportTask);
	}

	/**
	 * Returns the batch engine import task with the primary key or throws a <code>NoSuchImportTaskException</code> if it could not be found.
	 *
	 * @param batchEngineImportTaskId the primary key of the batch engine import task
	 * @return the batch engine import task
	 * @throws NoSuchImportTaskException if a batch engine import task with the primary key could not be found
	 */
	public static BatchEngineImportTask findByPrimaryKey(
			long batchEngineImportTaskId)
		throws com.liferay.batch.engine.exception.NoSuchImportTaskException {

		return getPersistence().findByPrimaryKey(batchEngineImportTaskId);
	}

	/**
	 * Returns the batch engine import task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineImportTaskId the primary key of the batch engine import task
	 * @return the batch engine import task, or <code>null</code> if a batch engine import task with the primary key could not be found
	 */
	public static BatchEngineImportTask fetchByPrimaryKey(
		long batchEngineImportTaskId) {

		return getPersistence().fetchByPrimaryKey(batchEngineImportTaskId);
	}

	/**
	 * Returns all the batch engine import tasks.
	 *
	 * @return the batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the batch engine import tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @return the range of batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch engine import tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import tasks
	 * @param end the upper bound of the range of batch engine import tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine import tasks
	 */
	public static List<BatchEngineImportTask> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the batch engine import tasks from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch engine import tasks.
	 *
	 * @return the number of batch engine import tasks
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchEngineImportTaskPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchEngineImportTaskPersistence, BatchEngineImportTaskPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchEngineImportTaskPersistence.class);

		ServiceTracker
			<BatchEngineImportTaskPersistence, BatchEngineImportTaskPersistence>
				serviceTracker =
					new ServiceTracker
						<BatchEngineImportTaskPersistence,
						 BatchEngineImportTaskPersistence>(
							 bundle.getBundleContext(),
							 BatchEngineImportTaskPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}