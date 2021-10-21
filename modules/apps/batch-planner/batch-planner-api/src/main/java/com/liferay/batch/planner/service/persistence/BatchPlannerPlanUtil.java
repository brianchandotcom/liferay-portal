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

import com.liferay.batch.planner.model.BatchPlannerPlan;
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
 * The persistence utility for the batch planner plan service. This utility wraps <code>com.liferay.batch.planner.service.persistence.impl.BatchPlannerPlanPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerPlanPersistence
 * @generated
 */
public class BatchPlannerPlanUtil {

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
	public static void clearCache(BatchPlannerPlan batchPlannerPlan) {
		getPersistence().clearCache(batchPlannerPlan);
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
	public static Map<Serializable, BatchPlannerPlan> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchPlannerPlan> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchPlannerPlan> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchPlannerPlan> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchPlannerPlan update(BatchPlannerPlan batchPlannerPlan) {
		return getPersistence().update(batchPlannerPlan);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchPlannerPlan update(
		BatchPlannerPlan batchPlannerPlan, ServiceContext serviceContext) {

		return getPersistence().update(batchPlannerPlan, serviceContext);
	}

	/**
	 * Returns all the batch planner plans where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the batch planner plans where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByCompanyId_First(
			long companyId,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByCompanyId_First(
		long companyId, OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByCompanyId_Last(
			long companyId,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByCompanyId_Last(
		long companyId, OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set where companyId = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan[] findByCompanyId_PrevAndNext(
			long batchPlannerPlanId, long companyId,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByCompanyId_PrevAndNext(
			batchPlannerPlanId, companyId, orderByComparator);
	}

	/**
	 * Returns all the batch planner plans that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the batch planner plans that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set of batch planner plans that the user has permission to view where companyId = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan[] filterFindByCompanyId_PrevAndNext(
			long batchPlannerPlanId, long companyId,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			batchPlannerPlanId, companyId, orderByComparator);
	}

	/**
	 * Removes all the batch planner plans where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of batch planner plans where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching batch planner plans
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of batch planner plans that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching batch planner plans that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the batch planner plans where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_U(
		long companyId, long userId) {

		return getPersistence().findByC_U(companyId, userId);
	}

	/**
	 * Returns a range of all the batch planner plans where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_U(
		long companyId, long userId, int start, int end) {

		return getPersistence().findByC_U(companyId, userId, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().findByC_U(
			companyId, userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_U(
			companyId, userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByC_U_First(
			long companyId, long userId,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_U_First(
			companyId, userId, orderByComparator);
	}

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByC_U_First(
		long companyId, long userId,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().fetchByC_U_First(
			companyId, userId, orderByComparator);
	}

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByC_U_Last(
			long companyId, long userId,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_U_Last(
			companyId, userId, orderByComparator);
	}

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByC_U_Last(
		long companyId, long userId,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().fetchByC_U_Last(
			companyId, userId, orderByComparator);
	}

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan[] findByC_U_PrevAndNext(
			long batchPlannerPlanId, long companyId, long userId,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_U_PrevAndNext(
			batchPlannerPlanId, companyId, userId, orderByComparator);
	}

	/**
	 * Returns all the batch planner plans that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_U(
		long companyId, long userId) {

		return getPersistence().filterFindByC_U(companyId, userId);
	}

	/**
	 * Returns a range of all the batch planner plans that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_U(
		long companyId, long userId, int start, int end) {

		return getPersistence().filterFindByC_U(companyId, userId, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans that the user has permissions to view where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().filterFindByC_U(
			companyId, userId, start, end, orderByComparator);
	}

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set of batch planner plans that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan[] filterFindByC_U_PrevAndNext(
			long batchPlannerPlanId, long companyId, long userId,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().filterFindByC_U_PrevAndNext(
			batchPlannerPlanId, companyId, userId, orderByComparator);
	}

	/**
	 * Removes all the batch planner plans where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	public static void removeByC_U(long companyId, long userId) {
		getPersistence().removeByC_U(companyId, userId);
	}

	/**
	 * Returns the number of batch planner plans where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching batch planner plans
	 */
	public static int countByC_U(long companyId, long userId) {
		return getPersistence().countByC_U(companyId, userId);
	}

	/**
	 * Returns the number of batch planner plans that the user has permission to view where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching batch planner plans that the user has permission to view
	 */
	public static int filterCountByC_U(long companyId, long userId) {
		return getPersistence().filterCountByC_U(companyId, userId);
	}

	/**
	 * Returns all the batch planner plans where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @return the matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_E(
		long companyId, boolean export) {

		return getPersistence().findByC_E(companyId, export);
	}

	/**
	 * Returns a range of all the batch planner plans where companyId = &#63; and export = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_E(
		long companyId, boolean export, int start, int end) {

		return getPersistence().findByC_E(companyId, export, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and export = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_E(
		long companyId, boolean export, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().findByC_E(
			companyId, export, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and export = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_E(
		long companyId, boolean export, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_E(
			companyId, export, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByC_E_First(
			long companyId, boolean export,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_E_First(
			companyId, export, orderByComparator);
	}

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByC_E_First(
		long companyId, boolean export,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().fetchByC_E_First(
			companyId, export, orderByComparator);
	}

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByC_E_Last(
			long companyId, boolean export,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_E_Last(
			companyId, export, orderByComparator);
	}

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByC_E_Last(
		long companyId, boolean export,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().fetchByC_E_Last(
			companyId, export, orderByComparator);
	}

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set where companyId = &#63; and export = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan[] findByC_E_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean export,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_E_PrevAndNext(
			batchPlannerPlanId, companyId, export, orderByComparator);
	}

	/**
	 * Returns all the batch planner plans that the user has permission to view where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @return the matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_E(
		long companyId, boolean export) {

		return getPersistence().filterFindByC_E(companyId, export);
	}

	/**
	 * Returns a range of all the batch planner plans that the user has permission to view where companyId = &#63; and export = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_E(
		long companyId, boolean export, int start, int end) {

		return getPersistence().filterFindByC_E(companyId, export, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans that the user has permissions to view where companyId = &#63; and export = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_E(
		long companyId, boolean export, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().filterFindByC_E(
			companyId, export, start, end, orderByComparator);
	}

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set of batch planner plans that the user has permission to view where companyId = &#63; and export = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param export the export
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan[] filterFindByC_E_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean export,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().filterFindByC_E_PrevAndNext(
			batchPlannerPlanId, companyId, export, orderByComparator);
	}

	/**
	 * Removes all the batch planner plans where companyId = &#63; and export = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 */
	public static void removeByC_E(long companyId, boolean export) {
		getPersistence().removeByC_E(companyId, export);
	}

	/**
	 * Returns the number of batch planner plans where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @return the number of matching batch planner plans
	 */
	public static int countByC_E(long companyId, boolean export) {
		return getPersistence().countByC_E(companyId, export);
	}

	/**
	 * Returns the number of batch planner plans that the user has permission to view where companyId = &#63; and export = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @return the number of matching batch planner plans that the user has permission to view
	 */
	public static int filterCountByC_E(long companyId, boolean export) {
		return getPersistence().filterCountByC_E(companyId, export);
	}

	/**
	 * Returns the batch planner plan where companyId = &#63; and name = &#63; or throws a <code>NoSuchPlanException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByC_N(long companyId, String name)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns the batch planner plan where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	 * Returns the batch planner plan where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_N(companyId, name, useFinderCache);
	}

	/**
	 * Removes the batch planner plan where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the batch planner plan that was removed
	 */
	public static BatchPlannerPlan removeByC_N(long companyId, String name)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of batch planner plans where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching batch planner plans
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Returns all the batch planner plans where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @return the matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_T(
		long companyId, boolean template) {

		return getPersistence().findByC_T(companyId, template);
	}

	/**
	 * Returns a range of all the batch planner plans where companyId = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_T(
		long companyId, boolean template, int start, int end) {

		return getPersistence().findByC_T(companyId, template, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_T(
		long companyId, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().findByC_T(
			companyId, template, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_T(
		long companyId, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_T(
			companyId, template, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByC_T_First(
			long companyId, boolean template,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_T_First(
			companyId, template, orderByComparator);
	}

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByC_T_First(
		long companyId, boolean template,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().fetchByC_T_First(
			companyId, template, orderByComparator);
	}

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByC_T_Last(
			long companyId, boolean template,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_T_Last(
			companyId, template, orderByComparator);
	}

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByC_T_Last(
		long companyId, boolean template,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().fetchByC_T_Last(
			companyId, template, orderByComparator);
	}

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set where companyId = &#63; and template = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan[] findByC_T_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean template,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_T_PrevAndNext(
			batchPlannerPlanId, companyId, template, orderByComparator);
	}

	/**
	 * Returns all the batch planner plans that the user has permission to view where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @return the matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_T(
		long companyId, boolean template) {

		return getPersistence().filterFindByC_T(companyId, template);
	}

	/**
	 * Returns a range of all the batch planner plans that the user has permission to view where companyId = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_T(
		long companyId, boolean template, int start, int end) {

		return getPersistence().filterFindByC_T(
			companyId, template, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans that the user has permissions to view where companyId = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_T(
		long companyId, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().filterFindByC_T(
			companyId, template, start, end, orderByComparator);
	}

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set of batch planner plans that the user has permission to view where companyId = &#63; and template = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan[] filterFindByC_T_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean template,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().filterFindByC_T_PrevAndNext(
			batchPlannerPlanId, companyId, template, orderByComparator);
	}

	/**
	 * Removes all the batch planner plans where companyId = &#63; and template = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 */
	public static void removeByC_T(long companyId, boolean template) {
		getPersistence().removeByC_T(companyId, template);
	}

	/**
	 * Returns the number of batch planner plans where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @return the number of matching batch planner plans
	 */
	public static int countByC_T(long companyId, boolean template) {
		return getPersistence().countByC_T(companyId, template);
	}

	/**
	 * Returns the number of batch planner plans that the user has permission to view where companyId = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param template the template
	 * @return the number of matching batch planner plans that the user has permission to view
	 */
	public static int filterCountByC_T(long companyId, boolean template) {
		return getPersistence().filterCountByC_T(companyId, template);
	}

	/**
	 * Returns all the batch planner plans where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @return the matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_E_T(
		long companyId, boolean export, boolean template) {

		return getPersistence().findByC_E_T(companyId, export, template);
	}

	/**
	 * Returns a range of all the batch planner plans where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_E_T(
		long companyId, boolean export, boolean template, int start, int end) {

		return getPersistence().findByC_E_T(
			companyId, export, template, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_E_T(
		long companyId, boolean export, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().findByC_E_T(
			companyId, export, template, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner plans where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner plans
	 */
	public static List<BatchPlannerPlan> findByC_E_T(
		long companyId, boolean export, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_E_T(
			companyId, export, template, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByC_E_T_First(
			long companyId, boolean export, boolean template,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_E_T_First(
			companyId, export, template, orderByComparator);
	}

	/**
	 * Returns the first batch planner plan in the ordered set where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByC_E_T_First(
		long companyId, boolean export, boolean template,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().fetchByC_E_T_First(
			companyId, export, template, orderByComparator);
	}

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan
	 * @throws NoSuchPlanException if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan findByC_E_T_Last(
			long companyId, boolean export, boolean template,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_E_T_Last(
			companyId, export, template, orderByComparator);
	}

	/**
	 * Returns the last batch planner plan in the ordered set where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner plan, or <code>null</code> if a matching batch planner plan could not be found
	 */
	public static BatchPlannerPlan fetchByC_E_T_Last(
		long companyId, boolean export, boolean template,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().fetchByC_E_T_Last(
			companyId, export, template, orderByComparator);
	}

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan[] findByC_E_T_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean export,
			boolean template,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByC_E_T_PrevAndNext(
			batchPlannerPlanId, companyId, export, template, orderByComparator);
	}

	/**
	 * Returns all the batch planner plans that the user has permission to view where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @return the matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_E_T(
		long companyId, boolean export, boolean template) {

		return getPersistence().filterFindByC_E_T(companyId, export, template);
	}

	/**
	 * Returns a range of all the batch planner plans that the user has permission to view where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_E_T(
		long companyId, boolean export, boolean template, int start, int end) {

		return getPersistence().filterFindByC_E_T(
			companyId, export, template, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans that the user has permissions to view where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner plans that the user has permission to view
	 */
	public static List<BatchPlannerPlan> filterFindByC_E_T(
		long companyId, boolean export, boolean template, int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().filterFindByC_E_T(
			companyId, export, template, start, end, orderByComparator);
	}

	/**
	 * Returns the batch planner plans before and after the current batch planner plan in the ordered set of batch planner plans that the user has permission to view where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param batchPlannerPlanId the primary key of the current batch planner plan
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan[] filterFindByC_E_T_PrevAndNext(
			long batchPlannerPlanId, long companyId, boolean export,
			boolean template,
			OrderByComparator<BatchPlannerPlan> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().filterFindByC_E_T_PrevAndNext(
			batchPlannerPlanId, companyId, export, template, orderByComparator);
	}

	/**
	 * Removes all the batch planner plans where companyId = &#63; and export = &#63; and template = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 */
	public static void removeByC_E_T(
		long companyId, boolean export, boolean template) {

		getPersistence().removeByC_E_T(companyId, export, template);
	}

	/**
	 * Returns the number of batch planner plans where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @return the number of matching batch planner plans
	 */
	public static int countByC_E_T(
		long companyId, boolean export, boolean template) {

		return getPersistence().countByC_E_T(companyId, export, template);
	}

	/**
	 * Returns the number of batch planner plans that the user has permission to view where companyId = &#63; and export = &#63; and template = &#63;.
	 *
	 * @param companyId the company ID
	 * @param export the export
	 * @param template the template
	 * @return the number of matching batch planner plans that the user has permission to view
	 */
	public static int filterCountByC_E_T(
		long companyId, boolean export, boolean template) {

		return getPersistence().filterCountByC_E_T(companyId, export, template);
	}

	/**
	 * Caches the batch planner plan in the entity cache if it is enabled.
	 *
	 * @param batchPlannerPlan the batch planner plan
	 */
	public static void cacheResult(BatchPlannerPlan batchPlannerPlan) {
		getPersistence().cacheResult(batchPlannerPlan);
	}

	/**
	 * Caches the batch planner plans in the entity cache if it is enabled.
	 *
	 * @param batchPlannerPlans the batch planner plans
	 */
	public static void cacheResult(List<BatchPlannerPlan> batchPlannerPlans) {
		getPersistence().cacheResult(batchPlannerPlans);
	}

	/**
	 * Creates a new batch planner plan with the primary key. Does not add the batch planner plan to the database.
	 *
	 * @param batchPlannerPlanId the primary key for the new batch planner plan
	 * @return the new batch planner plan
	 */
	public static BatchPlannerPlan create(long batchPlannerPlanId) {
		return getPersistence().create(batchPlannerPlanId);
	}

	/**
	 * Removes the batch planner plan with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerPlanId the primary key of the batch planner plan
	 * @return the batch planner plan that was removed
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan remove(long batchPlannerPlanId)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().remove(batchPlannerPlanId);
	}

	public static BatchPlannerPlan updateImpl(
		BatchPlannerPlan batchPlannerPlan) {

		return getPersistence().updateImpl(batchPlannerPlan);
	}

	/**
	 * Returns the batch planner plan with the primary key or throws a <code>NoSuchPlanException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the primary key of the batch planner plan
	 * @return the batch planner plan
	 * @throws NoSuchPlanException if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan findByPrimaryKey(long batchPlannerPlanId)
		throws com.liferay.batch.planner.exception.NoSuchPlanException {

		return getPersistence().findByPrimaryKey(batchPlannerPlanId);
	}

	/**
	 * Returns the batch planner plan with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the primary key of the batch planner plan
	 * @return the batch planner plan, or <code>null</code> if a batch planner plan with the primary key could not be found
	 */
	public static BatchPlannerPlan fetchByPrimaryKey(long batchPlannerPlanId) {
		return getPersistence().fetchByPrimaryKey(batchPlannerPlanId);
	}

	/**
	 * Returns all the batch planner plans.
	 *
	 * @return the batch planner plans
	 */
	public static List<BatchPlannerPlan> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the batch planner plans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @return the range of batch planner plans
	 */
	public static List<BatchPlannerPlan> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner plans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch planner plans
	 */
	public static List<BatchPlannerPlan> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner plans.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPlanModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner plans
	 * @param end the upper bound of the range of batch planner plans (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch planner plans
	 */
	public static List<BatchPlannerPlan> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerPlan> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the batch planner plans from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch planner plans.
	 *
	 * @return the number of batch planner plans
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchPlannerPlanPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchPlannerPlanPersistence, BatchPlannerPlanPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchPlannerPlanPersistence.class);

		ServiceTracker<BatchPlannerPlanPersistence, BatchPlannerPlanPersistence>
			serviceTracker =
				new ServiceTracker
					<BatchPlannerPlanPersistence, BatchPlannerPlanPersistence>(
						bundle.getBundleContext(),
						BatchPlannerPlanPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}