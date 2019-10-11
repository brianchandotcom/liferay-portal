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

import com.liferay.batch.engine.model.BatchEngineTask;
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
 * The persistence utility for the batch engine task service. This utility wraps <code>com.liferay.batch.engine.service.persistence.impl.BatchEngineTaskPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineTaskPersistence
 * @generated
 */
public class BatchEngineTaskUtil {

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
	public static void clearCache(BatchEngineTask batchEngineTask) {
		getPersistence().clearCache(batchEngineTask);
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
	public static Map<Serializable, BatchEngineTask> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchEngineTask> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchEngineTask> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchEngineTask> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchEngineTask update(BatchEngineTask batchEngineTask) {
		return getPersistence().update(batchEngineTask);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchEngineTask update(
		BatchEngineTask batchEngineTask, ServiceContext serviceContext) {

		return getPersistence().update(batchEngineTask, serviceContext);
	}

	/**
	 * Returns all the batch engine tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch engine tasks
	 */
	public static List<BatchEngineTask> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<BatchEngineTask> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<BatchEngineTask> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<BatchEngineTask> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public static BatchEngineTask findByUuid_First(
			String uuid, OrderByComparator<BatchEngineTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public static BatchEngineTask fetchByUuid_First(
		String uuid, OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public static BatchEngineTask findByUuid_Last(
			String uuid, OrderByComparator<BatchEngineTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public static BatchEngineTask fetchByUuid_Last(
		String uuid, OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the batch engine tasks before and after the current batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param batchEngineTaskId the primary key of the current batch engine task
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	public static BatchEngineTask[] findByUuid_PrevAndNext(
			long batchEngineTaskId, String uuid,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().findByUuid_PrevAndNext(
			batchEngineTaskId, uuid, orderByComparator);
	}

	/**
	 * Removes all the batch engine tasks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of batch engine tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch engine tasks
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch engine tasks
	 */
	public static List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public static BatchEngineTask findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public static BatchEngineTask fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public static BatchEngineTask findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public static BatchEngineTask fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

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
	public static BatchEngineTask[] findByUuid_C_PrevAndNext(
			long batchEngineTaskId, String uuid, long companyId,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().findByUuid_C_PrevAndNext(
			batchEngineTaskId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the batch engine tasks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch engine tasks
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the batch engine tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the matching batch engine tasks
	 */
	public static List<BatchEngineTask> findByExecuteStatus(
		String executeStatus) {

		return getPersistence().findByExecuteStatus(executeStatus);
	}

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
	public static List<BatchEngineTask> findByExecuteStatus(
		String executeStatus, int start, int end) {

		return getPersistence().findByExecuteStatus(executeStatus, start, end);
	}

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
	public static List<BatchEngineTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().findByExecuteStatus(
			executeStatus, start, end, orderByComparator);
	}

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
	public static List<BatchEngineTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByExecuteStatus(
			executeStatus, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public static BatchEngineTask findByExecuteStatus_First(
			String executeStatus,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().findByExecuteStatus_First(
			executeStatus, orderByComparator);
	}

	/**
	 * Returns the first batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public static BatchEngineTask fetchByExecuteStatus_First(
		String executeStatus,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().fetchByExecuteStatus_First(
			executeStatus, orderByComparator);
	}

	/**
	 * Returns the last batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	public static BatchEngineTask findByExecuteStatus_Last(
			String executeStatus,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().findByExecuteStatus_Last(
			executeStatus, orderByComparator);
	}

	/**
	 * Returns the last batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	public static BatchEngineTask fetchByExecuteStatus_Last(
		String executeStatus,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().fetchByExecuteStatus_Last(
			executeStatus, orderByComparator);
	}

	/**
	 * Returns the batch engine tasks before and after the current batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param batchEngineTaskId the primary key of the current batch engine task
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	public static BatchEngineTask[] findByExecuteStatus_PrevAndNext(
			long batchEngineTaskId, String executeStatus,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().findByExecuteStatus_PrevAndNext(
			batchEngineTaskId, executeStatus, orderByComparator);
	}

	/**
	 * Removes all the batch engine tasks where executeStatus = &#63; from the database.
	 *
	 * @param executeStatus the execute status
	 */
	public static void removeByExecuteStatus(String executeStatus) {
		getPersistence().removeByExecuteStatus(executeStatus);
	}

	/**
	 * Returns the number of batch engine tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the number of matching batch engine tasks
	 */
	public static int countByExecuteStatus(String executeStatus) {
		return getPersistence().countByExecuteStatus(executeStatus);
	}

	/**
	 * Caches the batch engine task in the entity cache if it is enabled.
	 *
	 * @param batchEngineTask the batch engine task
	 */
	public static void cacheResult(BatchEngineTask batchEngineTask) {
		getPersistence().cacheResult(batchEngineTask);
	}

	/**
	 * Caches the batch engine tasks in the entity cache if it is enabled.
	 *
	 * @param batchEngineTasks the batch engine tasks
	 */
	public static void cacheResult(List<BatchEngineTask> batchEngineTasks) {
		getPersistence().cacheResult(batchEngineTasks);
	}

	/**
	 * Creates a new batch engine task with the primary key. Does not add the batch engine task to the database.
	 *
	 * @param batchEngineTaskId the primary key for the new batch engine task
	 * @return the new batch engine task
	 */
	public static BatchEngineTask create(long batchEngineTaskId) {
		return getPersistence().create(batchEngineTaskId);
	}

	/**
	 * Removes the batch engine task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task that was removed
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	public static BatchEngineTask remove(long batchEngineTaskId)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().remove(batchEngineTaskId);
	}

	public static BatchEngineTask updateImpl(BatchEngineTask batchEngineTask) {
		return getPersistence().updateImpl(batchEngineTask);
	}

	/**
	 * Returns the batch engine task with the primary key or throws a <code>NoSuchTaskException</code> if it could not be found.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	public static BatchEngineTask findByPrimaryKey(long batchEngineTaskId)
		throws com.liferay.batch.engine.exception.NoSuchTaskException {

		return getPersistence().findByPrimaryKey(batchEngineTaskId);
	}

	/**
	 * Returns the batch engine task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task, or <code>null</code> if a batch engine task with the primary key could not be found
	 */
	public static BatchEngineTask fetchByPrimaryKey(long batchEngineTaskId) {
		return getPersistence().fetchByPrimaryKey(batchEngineTaskId);
	}

	/**
	 * Returns all the batch engine tasks.
	 *
	 * @return the batch engine tasks
	 */
	public static List<BatchEngineTask> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<BatchEngineTask> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<BatchEngineTask> findAll(
		int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<BatchEngineTask> findAll(
		int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the batch engine tasks from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch engine tasks.
	 *
	 * @return the number of batch engine tasks
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchEngineTaskPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchEngineTaskPersistence, BatchEngineTaskPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchEngineTaskPersistence.class);

		ServiceTracker<BatchEngineTaskPersistence, BatchEngineTaskPersistence>
			serviceTracker =
				new ServiceTracker
					<BatchEngineTaskPersistence, BatchEngineTaskPersistence>(
						bundle.getBundleContext(),
						BatchEngineTaskPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}