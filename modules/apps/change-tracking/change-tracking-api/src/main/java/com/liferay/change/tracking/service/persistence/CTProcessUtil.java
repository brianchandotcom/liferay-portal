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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.model.CTProcess;
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
 * The persistence utility for the ct process service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTProcessPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTProcessPersistence
 * @generated
 */
public class CTProcessUtil {

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
	public static void clearCache(CTProcess ctProcess) {
		getPersistence().clearCache(ctProcess);
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
	public static Map<Serializable, CTProcess> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTProcess> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTProcess> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTProcess> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTProcess> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTProcess update(CTProcess ctProcess) {
		return getPersistence().update(ctProcess);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTProcess update(
		CTProcess ctProcess, ServiceContext serviceContext) {

		return getPersistence().update(ctProcess, serviceContext);
	}

	/**
	 * Returns all the ct processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct processes
	 */
	public static List<CTProcess> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the ct processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of matching ct processes
	 */
	public static List<CTProcess> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct processes
	 */
	public static List<CTProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTProcess> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct processes
	 */
	public static List<CTProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTProcess> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	public static CTProcess findByCompanyId_First(
			long companyId, OrderByComparator<CTProcess> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchProcessException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	public static CTProcess fetchByCompanyId_First(
		long companyId, OrderByComparator<CTProcess> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	public static CTProcess findByCompanyId_Last(
			long companyId, OrderByComparator<CTProcess> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchProcessException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	public static CTProcess fetchByCompanyId_Last(
		long companyId, OrderByComparator<CTProcess> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the ct processes before and after the current ct process in the ordered set where companyId = &#63;.
	 *
	 * @param ctProcessId the primary key of the current ct process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct process
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	public static CTProcess[] findByCompanyId_PrevAndNext(
			long ctProcessId, long companyId,
			OrderByComparator<CTProcess> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchProcessException {

		return getPersistence().findByCompanyId_PrevAndNext(
			ctProcessId, companyId, orderByComparator);
	}

	/**
	 * Removes all the ct processes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of ct processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct processes
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the ct processes where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct processes
	 */
	public static List<CTProcess> findByCtCollectionId(long ctCollectionId) {
		return getPersistence().findByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns a range of all the ct processes where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of matching ct processes
	 */
	public static List<CTProcess> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct processes where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct processes
	 */
	public static List<CTProcess> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTProcess> orderByComparator) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct processes where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct processes
	 */
	public static List<CTProcess> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTProcess> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	public static CTProcess findByCtCollectionId_First(
			long ctCollectionId, OrderByComparator<CTProcess> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchProcessException {

		return getPersistence().findByCtCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	public static CTProcess fetchByCtCollectionId_First(
		long ctCollectionId, OrderByComparator<CTProcess> orderByComparator) {

		return getPersistence().fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	public static CTProcess findByCtCollectionId_Last(
			long ctCollectionId, OrderByComparator<CTProcess> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchProcessException {

		return getPersistence().findByCtCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	public static CTProcess fetchByCtCollectionId_Last(
		long ctCollectionId, OrderByComparator<CTProcess> orderByComparator) {

		return getPersistence().fetchByCtCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the ct processes before and after the current ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctProcessId the primary key of the current ct process
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct process
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	public static CTProcess[] findByCtCollectionId_PrevAndNext(
			long ctProcessId, long ctCollectionId,
			OrderByComparator<CTProcess> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchProcessException {

		return getPersistence().findByCtCollectionId_PrevAndNext(
			ctProcessId, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the ct processes where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCtCollectionId(long ctCollectionId) {
		getPersistence().removeByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of ct processes where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct processes
	 */
	public static int countByCtCollectionId(long ctCollectionId) {
		return getPersistence().countByCtCollectionId(ctCollectionId);
	}

	/**
	 * Caches the ct process in the entity cache if it is enabled.
	 *
	 * @param ctProcess the ct process
	 */
	public static void cacheResult(CTProcess ctProcess) {
		getPersistence().cacheResult(ctProcess);
	}

	/**
	 * Caches the ct processes in the entity cache if it is enabled.
	 *
	 * @param ctProcesses the ct processes
	 */
	public static void cacheResult(List<CTProcess> ctProcesses) {
		getPersistence().cacheResult(ctProcesses);
	}

	/**
	 * Creates a new ct process with the primary key. Does not add the ct process to the database.
	 *
	 * @param ctProcessId the primary key for the new ct process
	 * @return the new ct process
	 */
	public static CTProcess create(long ctProcessId) {
		return getPersistence().create(ctProcessId);
	}

	/**
	 * Removes the ct process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process that was removed
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	public static CTProcess remove(long ctProcessId)
		throws com.liferay.change.tracking.exception.NoSuchProcessException {

		return getPersistence().remove(ctProcessId);
	}

	public static CTProcess updateImpl(CTProcess ctProcess) {
		return getPersistence().updateImpl(ctProcess);
	}

	/**
	 * Returns the ct process with the primary key or throws a <code>NoSuchProcessException</code> if it could not be found.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	public static CTProcess findByPrimaryKey(long ctProcessId)
		throws com.liferay.change.tracking.exception.NoSuchProcessException {

		return getPersistence().findByPrimaryKey(ctProcessId);
	}

	/**
	 * Returns the ct process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process, or <code>null</code> if a ct process with the primary key could not be found
	 */
	public static CTProcess fetchByPrimaryKey(long ctProcessId) {
		return getPersistence().fetchByPrimaryKey(ctProcessId);
	}

	/**
	 * Returns all the ct processes.
	 *
	 * @return the ct processes
	 */
	public static List<CTProcess> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of ct processes
	 */
	public static List<CTProcess> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct processes
	 */
	public static List<CTProcess> findAll(
		int start, int end, OrderByComparator<CTProcess> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct processes
	 */
	public static List<CTProcess> findAll(
		int start, int end, OrderByComparator<CTProcess> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct processes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct processes.
	 *
	 * @return the number of ct processes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTProcessPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTProcessPersistence, CTProcessPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTProcessPersistence.class);

		ServiceTracker<CTProcessPersistence, CTProcessPersistence>
			serviceTracker =
				new ServiceTracker<CTProcessPersistence, CTProcessPersistence>(
					bundle.getBundleContext(), CTProcessPersistence.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}