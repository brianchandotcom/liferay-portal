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

package com.liferay.dispatch.service.persistence;

import com.liferay.dispatch.model.DispatchLog;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the dispatch log service. This utility wraps <code>com.liferay.dispatch.service.persistence.impl.DispatchLogPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matija Petanjek
 * @see DispatchLogPersistence
 * @generated
 */
public class DispatchLogUtil {

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
	public static void clearCache(DispatchLog dispatchLog) {
		getPersistence().clearCache(dispatchLog);
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
	public static Map<Serializable, DispatchLog> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DispatchLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DispatchLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DispatchLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DispatchLog update(DispatchLog dispatchLog) {
		return getPersistence().update(dispatchLog);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DispatchLog update(
		DispatchLog dispatchLog, ServiceContext serviceContext) {

		return getPersistence().update(dispatchLog, serviceContext);
	}

	/**
	 * Returns all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @return the matching dispatch logs
	 */
	public static List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId) {

		return getPersistence().findByDispatchTriggerId(dispatchTriggerId);
	}

	/**
	 * Returns a range of all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @return the range of matching dispatch logs
	 */
	public static List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId, int start, int end) {

		return getPersistence().findByDispatchTriggerId(
			dispatchTriggerId, start, end);
	}

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch logs
	 */
	public static List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator) {

		return getPersistence().findByDispatchTriggerId(
			dispatchTriggerId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch logs
	 */
	public static List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByDispatchTriggerId(
			dispatchTriggerId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	public static DispatchLog findByDispatchTriggerId_First(
			long dispatchTriggerId,
			OrderByComparator<DispatchLog> orderByComparator)
		throws com.liferay.dispatch.exception.NoSuchLogException {

		return getPersistence().findByDispatchTriggerId_First(
			dispatchTriggerId, orderByComparator);
	}

	/**
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	public static DispatchLog fetchByDispatchTriggerId_First(
		long dispatchTriggerId,
		OrderByComparator<DispatchLog> orderByComparator) {

		return getPersistence().fetchByDispatchTriggerId_First(
			dispatchTriggerId, orderByComparator);
	}

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	public static DispatchLog findByDispatchTriggerId_Last(
			long dispatchTriggerId,
			OrderByComparator<DispatchLog> orderByComparator)
		throws com.liferay.dispatch.exception.NoSuchLogException {

		return getPersistence().findByDispatchTriggerId_Last(
			dispatchTriggerId, orderByComparator);
	}

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	public static DispatchLog fetchByDispatchTriggerId_Last(
		long dispatchTriggerId,
		OrderByComparator<DispatchLog> orderByComparator) {

		return getPersistence().fetchByDispatchTriggerId_Last(
			dispatchTriggerId, orderByComparator);
	}

	/**
	 * Returns the dispatch logs before and after the current dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchLogId the primary key of the current dispatch log
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dispatch log
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	public static DispatchLog[] findByDispatchTriggerId_PrevAndNext(
			long dispatchLogId, long dispatchTriggerId,
			OrderByComparator<DispatchLog> orderByComparator)
		throws com.liferay.dispatch.exception.NoSuchLogException {

		return getPersistence().findByDispatchTriggerId_PrevAndNext(
			dispatchLogId, dispatchTriggerId, orderByComparator);
	}

	/**
	 * Removes all the dispatch logs where dispatchTriggerId = &#63; from the database.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 */
	public static void removeByDispatchTriggerId(long dispatchTriggerId) {
		getPersistence().removeByDispatchTriggerId(dispatchTriggerId);
	}

	/**
	 * Returns the number of dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @return the number of matching dispatch logs
	 */
	public static int countByDispatchTriggerId(long dispatchTriggerId) {
		return getPersistence().countByDispatchTriggerId(dispatchTriggerId);
	}

	/**
	 * Returns all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @return the matching dispatch logs
	 */
	public static List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status) {

		return getPersistence().findByDTI_S(dispatchTriggerId, status);
	}

	/**
	 * Returns a range of all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @return the range of matching dispatch logs
	 */
	public static List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status, int start, int end) {

		return getPersistence().findByDTI_S(
			dispatchTriggerId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch logs
	 */
	public static List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator) {

		return getPersistence().findByDTI_S(
			dispatchTriggerId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch logs
	 */
	public static List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByDTI_S(
			dispatchTriggerId, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	public static DispatchLog findByDTI_S_First(
			long dispatchTriggerId, int status,
			OrderByComparator<DispatchLog> orderByComparator)
		throws com.liferay.dispatch.exception.NoSuchLogException {

		return getPersistence().findByDTI_S_First(
			dispatchTriggerId, status, orderByComparator);
	}

	/**
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	public static DispatchLog fetchByDTI_S_First(
		long dispatchTriggerId, int status,
		OrderByComparator<DispatchLog> orderByComparator) {

		return getPersistence().fetchByDTI_S_First(
			dispatchTriggerId, status, orderByComparator);
	}

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	public static DispatchLog findByDTI_S_Last(
			long dispatchTriggerId, int status,
			OrderByComparator<DispatchLog> orderByComparator)
		throws com.liferay.dispatch.exception.NoSuchLogException {

		return getPersistence().findByDTI_S_Last(
			dispatchTriggerId, status, orderByComparator);
	}

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	public static DispatchLog fetchByDTI_S_Last(
		long dispatchTriggerId, int status,
		OrderByComparator<DispatchLog> orderByComparator) {

		return getPersistence().fetchByDTI_S_Last(
			dispatchTriggerId, status, orderByComparator);
	}

	/**
	 * Returns the dispatch logs before and after the current dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchLogId the primary key of the current dispatch log
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dispatch log
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	public static DispatchLog[] findByDTI_S_PrevAndNext(
			long dispatchLogId, long dispatchTriggerId, int status,
			OrderByComparator<DispatchLog> orderByComparator)
		throws com.liferay.dispatch.exception.NoSuchLogException {

		return getPersistence().findByDTI_S_PrevAndNext(
			dispatchLogId, dispatchTriggerId, status, orderByComparator);
	}

	/**
	 * Removes all the dispatch logs where dispatchTriggerId = &#63; and status = &#63; from the database.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 */
	public static void removeByDTI_S(long dispatchTriggerId, int status) {
		getPersistence().removeByDTI_S(dispatchTriggerId, status);
	}

	/**
	 * Returns the number of dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @return the number of matching dispatch logs
	 */
	public static int countByDTI_S(long dispatchTriggerId, int status) {
		return getPersistence().countByDTI_S(dispatchTriggerId, status);
	}

	/**
	 * Caches the dispatch log in the entity cache if it is enabled.
	 *
	 * @param dispatchLog the dispatch log
	 */
	public static void cacheResult(DispatchLog dispatchLog) {
		getPersistence().cacheResult(dispatchLog);
	}

	/**
	 * Caches the dispatch logs in the entity cache if it is enabled.
	 *
	 * @param dispatchLogs the dispatch logs
	 */
	public static void cacheResult(List<DispatchLog> dispatchLogs) {
		getPersistence().cacheResult(dispatchLogs);
	}

	/**
	 * Creates a new dispatch log with the primary key. Does not add the dispatch log to the database.
	 *
	 * @param dispatchLogId the primary key for the new dispatch log
	 * @return the new dispatch log
	 */
	public static DispatchLog create(long dispatchLogId) {
		return getPersistence().create(dispatchLogId);
	}

	/**
	 * Removes the dispatch log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log that was removed
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	public static DispatchLog remove(long dispatchLogId)
		throws com.liferay.dispatch.exception.NoSuchLogException {

		return getPersistence().remove(dispatchLogId);
	}

	public static DispatchLog updateImpl(DispatchLog dispatchLog) {
		return getPersistence().updateImpl(dispatchLog);
	}

	/**
	 * Returns the dispatch log with the primary key or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	public static DispatchLog findByPrimaryKey(long dispatchLogId)
		throws com.liferay.dispatch.exception.NoSuchLogException {

		return getPersistence().findByPrimaryKey(dispatchLogId);
	}

	/**
	 * Returns the dispatch log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log, or <code>null</code> if a dispatch log with the primary key could not be found
	 */
	public static DispatchLog fetchByPrimaryKey(long dispatchLogId) {
		return getPersistence().fetchByPrimaryKey(dispatchLogId);
	}

	/**
	 * Returns all the dispatch logs.
	 *
	 * @return the dispatch logs
	 */
	public static List<DispatchLog> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the dispatch logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @return the range of dispatch logs
	 */
	public static List<DispatchLog> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the dispatch logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dispatch logs
	 */
	public static List<DispatchLog> findAll(
		int start, int end, OrderByComparator<DispatchLog> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dispatch logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dispatch logs
	 */
	public static List<DispatchLog> findAll(
		int start, int end, OrderByComparator<DispatchLog> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the dispatch logs from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of dispatch logs.
	 *
	 * @return the number of dispatch logs
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DispatchLogPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(DispatchLogPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile DispatchLogPersistence _persistence;

}