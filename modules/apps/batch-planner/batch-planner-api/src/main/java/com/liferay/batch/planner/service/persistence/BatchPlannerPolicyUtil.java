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

import com.liferay.batch.planner.model.BatchPlannerPolicy;
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
 * The persistence utility for the batch planner policy service. This utility wraps <code>com.liferay.batch.planner.service.persistence.impl.BatchPlannerPolicyPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerPolicyPersistence
 * @generated
 */
public class BatchPlannerPolicyUtil {

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
	public static void clearCache(BatchPlannerPolicy batchPlannerPolicy) {
		getPersistence().clearCache(batchPlannerPolicy);
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
	public static Map<Serializable, BatchPlannerPolicy> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<BatchPlannerPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BatchPlannerPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BatchPlannerPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BatchPlannerPolicy> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BatchPlannerPolicy update(
		BatchPlannerPolicy batchPlannerPolicy) {

		return getPersistence().update(batchPlannerPolicy);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BatchPlannerPolicy update(
		BatchPlannerPolicy batchPlannerPolicy, ServiceContext serviceContext) {

		return getPersistence().update(batchPlannerPolicy, serviceContext);
	}

	/**
	 * Returns all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner policies
	 */
	public static List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId) {

		return getPersistence().findByBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Returns a range of all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @return the range of matching batch planner policies
	 */
	public static List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end) {

		return getPersistence().findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner policies
	 */
	public static List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerPolicy> orderByComparator) {

		return getPersistence().findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner policies
	 */
	public static List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerPolicy> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner policy
	 * @throws NoSuchPolicyException if a matching batch planner policy could not be found
	 */
	public static BatchPlannerPolicy findByBatchPlannerPlanId_First(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerPolicy> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPolicyException {

		return getPersistence().findByBatchPlannerPlanId_First(
			batchPlannerPlanId, orderByComparator);
	}

	/**
	 * Returns the first batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	public static BatchPlannerPolicy fetchByBatchPlannerPlanId_First(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerPolicy> orderByComparator) {

		return getPersistence().fetchByBatchPlannerPlanId_First(
			batchPlannerPlanId, orderByComparator);
	}

	/**
	 * Returns the last batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner policy
	 * @throws NoSuchPolicyException if a matching batch planner policy could not be found
	 */
	public static BatchPlannerPolicy findByBatchPlannerPlanId_Last(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerPolicy> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPolicyException {

		return getPersistence().findByBatchPlannerPlanId_Last(
			batchPlannerPlanId, orderByComparator);
	}

	/**
	 * Returns the last batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	public static BatchPlannerPolicy fetchByBatchPlannerPlanId_Last(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerPolicy> orderByComparator) {

		return getPersistence().fetchByBatchPlannerPlanId_Last(
			batchPlannerPlanId, orderByComparator);
	}

	/**
	 * Returns the batch planner policies before and after the current batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPolicyId the primary key of the current batch planner policy
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner policy
	 * @throws NoSuchPolicyException if a batch planner policy with the primary key could not be found
	 */
	public static BatchPlannerPolicy[] findByBatchPlannerPlanId_PrevAndNext(
			long batchPlannerPolicyId, long batchPlannerPlanId,
			OrderByComparator<BatchPlannerPolicy> orderByComparator)
		throws com.liferay.batch.planner.exception.NoSuchPolicyException {

		return getPersistence().findByBatchPlannerPlanId_PrevAndNext(
			batchPlannerPolicyId, batchPlannerPlanId, orderByComparator);
	}

	/**
	 * Removes all the batch planner policies where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 */
	public static void removeByBatchPlannerPlanId(long batchPlannerPlanId) {
		getPersistence().removeByBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Returns the number of batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the number of matching batch planner policies
	 */
	public static int countByBatchPlannerPlanId(long batchPlannerPlanId) {
		return getPersistence().countByBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Returns the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; or throws a <code>NoSuchPolicyException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the matching batch planner policy
	 * @throws NoSuchPolicyException if a matching batch planner policy could not be found
	 */
	public static BatchPlannerPolicy findByBPPI_N(
			long batchPlannerPlanId, String name)
		throws com.liferay.batch.planner.exception.NoSuchPolicyException {

		return getPersistence().findByBPPI_N(batchPlannerPlanId, name);
	}

	/**
	 * Returns the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	public static BatchPlannerPolicy fetchByBPPI_N(
		long batchPlannerPlanId, String name) {

		return getPersistence().fetchByBPPI_N(batchPlannerPlanId, name);
	}

	/**
	 * Returns the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	public static BatchPlannerPolicy fetchByBPPI_N(
		long batchPlannerPlanId, String name, boolean useFinderCache) {

		return getPersistence().fetchByBPPI_N(
			batchPlannerPlanId, name, useFinderCache);
	}

	/**
	 * Removes the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the batch planner policy that was removed
	 */
	public static BatchPlannerPolicy removeByBPPI_N(
			long batchPlannerPlanId, String name)
		throws com.liferay.batch.planner.exception.NoSuchPolicyException {

		return getPersistence().removeByBPPI_N(batchPlannerPlanId, name);
	}

	/**
	 * Returns the number of batch planner policies where batchPlannerPlanId = &#63; and name = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the number of matching batch planner policies
	 */
	public static int countByBPPI_N(long batchPlannerPlanId, String name) {
		return getPersistence().countByBPPI_N(batchPlannerPlanId, name);
	}

	/**
	 * Caches the batch planner policy in the entity cache if it is enabled.
	 *
	 * @param batchPlannerPolicy the batch planner policy
	 */
	public static void cacheResult(BatchPlannerPolicy batchPlannerPolicy) {
		getPersistence().cacheResult(batchPlannerPolicy);
	}

	/**
	 * Caches the batch planner policies in the entity cache if it is enabled.
	 *
	 * @param batchPlannerPolicies the batch planner policies
	 */
	public static void cacheResult(
		List<BatchPlannerPolicy> batchPlannerPolicies) {

		getPersistence().cacheResult(batchPlannerPolicies);
	}

	/**
	 * Creates a new batch planner policy with the primary key. Does not add the batch planner policy to the database.
	 *
	 * @param batchPlannerPolicyId the primary key for the new batch planner policy
	 * @return the new batch planner policy
	 */
	public static BatchPlannerPolicy create(long batchPlannerPolicyId) {
		return getPersistence().create(batchPlannerPolicyId);
	}

	/**
	 * Removes the batch planner policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy that was removed
	 * @throws NoSuchPolicyException if a batch planner policy with the primary key could not be found
	 */
	public static BatchPlannerPolicy remove(long batchPlannerPolicyId)
		throws com.liferay.batch.planner.exception.NoSuchPolicyException {

		return getPersistence().remove(batchPlannerPolicyId);
	}

	public static BatchPlannerPolicy updateImpl(
		BatchPlannerPolicy batchPlannerPolicy) {

		return getPersistence().updateImpl(batchPlannerPolicy);
	}

	/**
	 * Returns the batch planner policy with the primary key or throws a <code>NoSuchPolicyException</code> if it could not be found.
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy
	 * @throws NoSuchPolicyException if a batch planner policy with the primary key could not be found
	 */
	public static BatchPlannerPolicy findByPrimaryKey(long batchPlannerPolicyId)
		throws com.liferay.batch.planner.exception.NoSuchPolicyException {

		return getPersistence().findByPrimaryKey(batchPlannerPolicyId);
	}

	/**
	 * Returns the batch planner policy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy, or <code>null</code> if a batch planner policy with the primary key could not be found
	 */
	public static BatchPlannerPolicy fetchByPrimaryKey(
		long batchPlannerPolicyId) {

		return getPersistence().fetchByPrimaryKey(batchPlannerPolicyId);
	}

	/**
	 * Returns all the batch planner policies.
	 *
	 * @return the batch planner policies
	 */
	public static List<BatchPlannerPolicy> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the batch planner policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @return the range of batch planner policies
	 */
	public static List<BatchPlannerPolicy> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the batch planner policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch planner policies
	 */
	public static List<BatchPlannerPolicy> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerPolicy> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the batch planner policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch planner policies
	 */
	public static List<BatchPlannerPolicy> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerPolicy> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the batch planner policies from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of batch planner policies.
	 *
	 * @return the number of batch planner policies
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BatchPlannerPolicyPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<BatchPlannerPolicyPersistence, BatchPlannerPolicyPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchPlannerPolicyPersistence.class);

		ServiceTracker
			<BatchPlannerPolicyPersistence, BatchPlannerPolicyPersistence>
				serviceTracker =
					new ServiceTracker
						<BatchPlannerPolicyPersistence,
						 BatchPlannerPolicyPersistence>(
							 bundle.getBundleContext(),
							 BatchPlannerPolicyPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}