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

package com.liferay.scheduler.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.scheduler.model.SchedulerProcess;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the scheduler process service. This utility wraps <code>com.liferay.scheduler.service.persistence.impl.SchedulerProcessPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see SchedulerProcessPersistence
 * @generated
 */
public class SchedulerProcessUtil {

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
	public static void clearCache(SchedulerProcess schedulerProcess) {
		getPersistence().clearCache(schedulerProcess);
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
	public static Map<Serializable, SchedulerProcess> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SchedulerProcess> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SchedulerProcess> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SchedulerProcess> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SchedulerProcess update(SchedulerProcess schedulerProcess) {
		return getPersistence().update(schedulerProcess);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SchedulerProcess update(
		SchedulerProcess schedulerProcess, ServiceContext serviceContext) {

		return getPersistence().update(schedulerProcess, serviceContext);
	}

	/**
	 * Returns all the scheduler processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching scheduler processes
	 */
	public static List<SchedulerProcess> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the scheduler processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @return the range of matching scheduler processes
	 */
	public static List<SchedulerProcess> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching scheduler processes
	 */
	public static List<SchedulerProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching scheduler processes
	 */
	public static List<SchedulerProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	public static SchedulerProcess findByCompanyId_First(
			long companyId,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws com.liferay.scheduler.exception.NoSuchProcessException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public static SchedulerProcess fetchByCompanyId_First(
		long companyId, OrderByComparator<SchedulerProcess> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	public static SchedulerProcess findByCompanyId_Last(
			long companyId,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws com.liferay.scheduler.exception.NoSuchProcessException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public static SchedulerProcess fetchByCompanyId_Last(
		long companyId, OrderByComparator<SchedulerProcess> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the scheduler processes before and after the current scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param schedulerProcessId the primary key of the current scheduler process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next scheduler process
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	public static SchedulerProcess[] findByCompanyId_PrevAndNext(
			long schedulerProcessId, long companyId,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws com.liferay.scheduler.exception.NoSuchProcessException {

		return getPersistence().findByCompanyId_PrevAndNext(
			schedulerProcessId, companyId, orderByComparator);
	}

	/**
	 * Removes all the scheduler processes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of scheduler processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching scheduler processes
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the scheduler process where companyId = &#63; and name = &#63; or throws a <code>NoSuchProcessException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	public static SchedulerProcess findByC_N(long companyId, String name)
		throws com.liferay.scheduler.exception.NoSuchProcessException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the scheduler process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public static SchedulerProcess fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the scheduler process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public static SchedulerProcess fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the scheduler process where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the scheduler process that was removed
	 */
	public static SchedulerProcess removeByC_N(long companyId, String name)
		throws com.liferay.scheduler.exception.NoSuchProcessException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of scheduler processes where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching scheduler processes
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Returns all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching scheduler processes
	 */
	public static List<SchedulerProcess> findByC_T(
		long companyId, String type) {

		return getPersistence().findByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @return the range of matching scheduler processes
	 */
	public static List<SchedulerProcess> findByC_T(
		long companyId, String type, int start, int end) {

		return getPersistence().findByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching scheduler processes
	 */
	public static List<SchedulerProcess> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching scheduler processes
	 */
	public static List<SchedulerProcess> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	public static SchedulerProcess findByC_T_First(
			long companyId, String type,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws com.liferay.scheduler.exception.NoSuchProcessException {

		return getPersistence().findByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the first scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public static SchedulerProcess fetchByC_T_First(
		long companyId, String type,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		return getPersistence().fetchByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	public static SchedulerProcess findByC_T_Last(
			long companyId, String type,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws com.liferay.scheduler.exception.NoSuchProcessException {

		return getPersistence().findByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	public static SchedulerProcess fetchByC_T_Last(
		long companyId, String type,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		return getPersistence().fetchByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the scheduler processes before and after the current scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param schedulerProcessId the primary key of the current scheduler process
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next scheduler process
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	public static SchedulerProcess[] findByC_T_PrevAndNext(
			long schedulerProcessId, long companyId, String type,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws com.liferay.scheduler.exception.NoSuchProcessException {

		return getPersistence().findByC_T_PrevAndNext(
			schedulerProcessId, companyId, type, orderByComparator);
	}

	/**
	 * Removes all the scheduler processes where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public static void removeByC_T(long companyId, String type) {
		getPersistence().removeByC_T(companyId, type);
	}

	/**
	 * Returns the number of scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching scheduler processes
	 */
	public static int countByC_T(long companyId, String type) {
		return getPersistence().countByC_T(companyId, type);
	}

	/**
	 * Caches the scheduler process in the entity cache if it is enabled.
	 *
	 * @param schedulerProcess the scheduler process
	 */
	public static void cacheResult(SchedulerProcess schedulerProcess) {
		getPersistence().cacheResult(schedulerProcess);
	}

	/**
	 * Caches the scheduler processes in the entity cache if it is enabled.
	 *
	 * @param schedulerProcesses the scheduler processes
	 */
	public static void cacheResult(List<SchedulerProcess> schedulerProcesses) {
		getPersistence().cacheResult(schedulerProcesses);
	}

	/**
	 * Creates a new scheduler process with the primary key. Does not add the scheduler process to the database.
	 *
	 * @param schedulerProcessId the primary key for the new scheduler process
	 * @return the new scheduler process
	 */
	public static SchedulerProcess create(long schedulerProcessId) {
		return getPersistence().create(schedulerProcessId);
	}

	/**
	 * Removes the scheduler process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process that was removed
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	public static SchedulerProcess remove(long schedulerProcessId)
		throws com.liferay.scheduler.exception.NoSuchProcessException {

		return getPersistence().remove(schedulerProcessId);
	}

	public static SchedulerProcess updateImpl(
		SchedulerProcess schedulerProcess) {

		return getPersistence().updateImpl(schedulerProcess);
	}

	/**
	 * Returns the scheduler process with the primary key or throws a <code>NoSuchProcessException</code> if it could not be found.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	public static SchedulerProcess findByPrimaryKey(long schedulerProcessId)
		throws com.liferay.scheduler.exception.NoSuchProcessException {

		return getPersistence().findByPrimaryKey(schedulerProcessId);
	}

	/**
	 * Returns the scheduler process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process, or <code>null</code> if a scheduler process with the primary key could not be found
	 */
	public static SchedulerProcess fetchByPrimaryKey(long schedulerProcessId) {
		return getPersistence().fetchByPrimaryKey(schedulerProcessId);
	}

	/**
	 * Returns all the scheduler processes.
	 *
	 * @return the scheduler processes
	 */
	public static List<SchedulerProcess> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the scheduler processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @return the range of scheduler processes
	 */
	public static List<SchedulerProcess> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the scheduler processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of scheduler processes
	 */
	public static List<SchedulerProcess> findAll(
		int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the scheduler processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of scheduler processes
	 */
	public static List<SchedulerProcess> findAll(
		int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the scheduler processes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of scheduler processes.
	 *
	 * @return the number of scheduler processes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SchedulerProcessPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SchedulerProcessPersistence, SchedulerProcessPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SchedulerProcessPersistence.class);

		ServiceTracker<SchedulerProcessPersistence, SchedulerProcessPersistence>
			serviceTracker =
				new ServiceTracker
					<SchedulerProcessPersistence, SchedulerProcessPersistence>(
						bundle.getBundleContext(),
						SchedulerProcessPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}