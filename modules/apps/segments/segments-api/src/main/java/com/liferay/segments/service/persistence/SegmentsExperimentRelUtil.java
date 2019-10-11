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

package com.liferay.segments.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsExperimentRel;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the segments experiment rel service. This utility wraps <code>com.liferay.segments.service.persistence.impl.SegmentsExperimentRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentRelPersistence
 * @generated
 */
public class SegmentsExperimentRelUtil {

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
	public static void clearCache(SegmentsExperimentRel segmentsExperimentRel) {
		getPersistence().clearCache(segmentsExperimentRel);
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
	public static Map<Serializable, SegmentsExperimentRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SegmentsExperimentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SegmentsExperimentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SegmentsExperimentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SegmentsExperimentRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SegmentsExperimentRel update(
		SegmentsExperimentRel segmentsExperimentRel) {

		return getPersistence().update(segmentsExperimentRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SegmentsExperimentRel update(
		SegmentsExperimentRel segmentsExperimentRel,
		ServiceContext serviceContext) {

		return getPersistence().update(segmentsExperimentRel, serviceContext);
	}

	/**
	 * Returns all the segments experiment rels where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @return the matching segments experiment rels
	 */
	public static List<SegmentsExperimentRel> findBySegmentsExperimentId(
		long segmentsExperimentId) {

		return getPersistence().findBySegmentsExperimentId(
			segmentsExperimentId);
	}

	/**
	 * Returns a range of all the segments experiment rels where segmentsExperimentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @return the range of matching segments experiment rels
	 */
	public static List<SegmentsExperimentRel> findBySegmentsExperimentId(
		long segmentsExperimentId, int start, int end) {

		return getPersistence().findBySegmentsExperimentId(
			segmentsExperimentId, start, end);
	}

	/**
	 * Returns an ordered range of all the segments experiment rels where segmentsExperimentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiment rels
	 */
	public static List<SegmentsExperimentRel> findBySegmentsExperimentId(
		long segmentsExperimentId, int start, int end,
		OrderByComparator<SegmentsExperimentRel> orderByComparator) {

		return getPersistence().findBySegmentsExperimentId(
			segmentsExperimentId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the segments experiment rels where segmentsExperimentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiment rels
	 */
	public static List<SegmentsExperimentRel> findBySegmentsExperimentId(
		long segmentsExperimentId, int start, int end,
		OrderByComparator<SegmentsExperimentRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBySegmentsExperimentId(
			segmentsExperimentId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first segments experiment rel in the ordered set where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment rel
	 * @throws NoSuchExperimentRelException if a matching segments experiment rel could not be found
	 */
	public static SegmentsExperimentRel findBySegmentsExperimentId_First(
			long segmentsExperimentId,
			OrderByComparator<SegmentsExperimentRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchExperimentRelException {

		return getPersistence().findBySegmentsExperimentId_First(
			segmentsExperimentId, orderByComparator);
	}

	/**
	 * Returns the first segments experiment rel in the ordered set where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment rel, or <code>null</code> if a matching segments experiment rel could not be found
	 */
	public static SegmentsExperimentRel fetchBySegmentsExperimentId_First(
		long segmentsExperimentId,
		OrderByComparator<SegmentsExperimentRel> orderByComparator) {

		return getPersistence().fetchBySegmentsExperimentId_First(
			segmentsExperimentId, orderByComparator);
	}

	/**
	 * Returns the last segments experiment rel in the ordered set where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment rel
	 * @throws NoSuchExperimentRelException if a matching segments experiment rel could not be found
	 */
	public static SegmentsExperimentRel findBySegmentsExperimentId_Last(
			long segmentsExperimentId,
			OrderByComparator<SegmentsExperimentRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchExperimentRelException {

		return getPersistence().findBySegmentsExperimentId_Last(
			segmentsExperimentId, orderByComparator);
	}

	/**
	 * Returns the last segments experiment rel in the ordered set where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment rel, or <code>null</code> if a matching segments experiment rel could not be found
	 */
	public static SegmentsExperimentRel fetchBySegmentsExperimentId_Last(
		long segmentsExperimentId,
		OrderByComparator<SegmentsExperimentRel> orderByComparator) {

		return getPersistence().fetchBySegmentsExperimentId_Last(
			segmentsExperimentId, orderByComparator);
	}

	/**
	 * Returns the segments experiment rels before and after the current segments experiment rel in the ordered set where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentRelId the primary key of the current segments experiment rel
	 * @param segmentsExperimentId the segments experiment ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment rel
	 * @throws NoSuchExperimentRelException if a segments experiment rel with the primary key could not be found
	 */
	public static SegmentsExperimentRel[]
			findBySegmentsExperimentId_PrevAndNext(
				long segmentsExperimentRelId, long segmentsExperimentId,
				OrderByComparator<SegmentsExperimentRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchExperimentRelException {

		return getPersistence().findBySegmentsExperimentId_PrevAndNext(
			segmentsExperimentRelId, segmentsExperimentId, orderByComparator);
	}

	/**
	 * Removes all the segments experiment rels where segmentsExperimentId = &#63; from the database.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 */
	public static void removeBySegmentsExperimentId(long segmentsExperimentId) {
		getPersistence().removeBySegmentsExperimentId(segmentsExperimentId);
	}

	/**
	 * Returns the number of segments experiment rels where segmentsExperimentId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @return the number of matching segments experiment rels
	 */
	public static int countBySegmentsExperimentId(long segmentsExperimentId) {
		return getPersistence().countBySegmentsExperimentId(
			segmentsExperimentId);
	}

	/**
	 * Returns the segments experiment rel where segmentsExperimentId = &#63; and segmentsExperienceId = &#63; or throws a <code>NoSuchExperimentRelException</code> if it could not be found.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching segments experiment rel
	 * @throws NoSuchExperimentRelException if a matching segments experiment rel could not be found
	 */
	public static SegmentsExperimentRel findByS_S(
			long segmentsExperimentId, long segmentsExperienceId)
		throws com.liferay.segments.exception.NoSuchExperimentRelException {

		return getPersistence().findByS_S(
			segmentsExperimentId, segmentsExperienceId);
	}

	/**
	 * Returns the segments experiment rel where segmentsExperimentId = &#63; and segmentsExperienceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching segments experiment rel, or <code>null</code> if a matching segments experiment rel could not be found
	 */
	public static SegmentsExperimentRel fetchByS_S(
		long segmentsExperimentId, long segmentsExperienceId) {

		return getPersistence().fetchByS_S(
			segmentsExperimentId, segmentsExperienceId);
	}

	/**
	 * Returns the segments experiment rel where segmentsExperimentId = &#63; and segmentsExperienceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments experiment rel, or <code>null</code> if a matching segments experiment rel could not be found
	 */
	public static SegmentsExperimentRel fetchByS_S(
		long segmentsExperimentId, long segmentsExperienceId,
		boolean useFinderCache) {

		return getPersistence().fetchByS_S(
			segmentsExperimentId, segmentsExperienceId, useFinderCache);
	}

	/**
	 * Removes the segments experiment rel where segmentsExperimentId = &#63; and segmentsExperienceId = &#63; from the database.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the segments experiment rel that was removed
	 */
	public static SegmentsExperimentRel removeByS_S(
			long segmentsExperimentId, long segmentsExperienceId)
		throws com.liferay.segments.exception.NoSuchExperimentRelException {

		return getPersistence().removeByS_S(
			segmentsExperimentId, segmentsExperienceId);
	}

	/**
	 * Returns the number of segments experiment rels where segmentsExperimentId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperimentId the segments experiment ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the number of matching segments experiment rels
	 */
	public static int countByS_S(
		long segmentsExperimentId, long segmentsExperienceId) {

		return getPersistence().countByS_S(
			segmentsExperimentId, segmentsExperienceId);
	}

	/**
	 * Caches the segments experiment rel in the entity cache if it is enabled.
	 *
	 * @param segmentsExperimentRel the segments experiment rel
	 */
	public static void cacheResult(
		SegmentsExperimentRel segmentsExperimentRel) {

		getPersistence().cacheResult(segmentsExperimentRel);
	}

	/**
	 * Caches the segments experiment rels in the entity cache if it is enabled.
	 *
	 * @param segmentsExperimentRels the segments experiment rels
	 */
	public static void cacheResult(
		List<SegmentsExperimentRel> segmentsExperimentRels) {

		getPersistence().cacheResult(segmentsExperimentRels);
	}

	/**
	 * Creates a new segments experiment rel with the primary key. Does not add the segments experiment rel to the database.
	 *
	 * @param segmentsExperimentRelId the primary key for the new segments experiment rel
	 * @return the new segments experiment rel
	 */
	public static SegmentsExperimentRel create(long segmentsExperimentRelId) {
		return getPersistence().create(segmentsExperimentRelId);
	}

	/**
	 * Removes the segments experiment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperimentRelId the primary key of the segments experiment rel
	 * @return the segments experiment rel that was removed
	 * @throws NoSuchExperimentRelException if a segments experiment rel with the primary key could not be found
	 */
	public static SegmentsExperimentRel remove(long segmentsExperimentRelId)
		throws com.liferay.segments.exception.NoSuchExperimentRelException {

		return getPersistence().remove(segmentsExperimentRelId);
	}

	public static SegmentsExperimentRel updateImpl(
		SegmentsExperimentRel segmentsExperimentRel) {

		return getPersistence().updateImpl(segmentsExperimentRel);
	}

	/**
	 * Returns the segments experiment rel with the primary key or throws a <code>NoSuchExperimentRelException</code> if it could not be found.
	 *
	 * @param segmentsExperimentRelId the primary key of the segments experiment rel
	 * @return the segments experiment rel
	 * @throws NoSuchExperimentRelException if a segments experiment rel with the primary key could not be found
	 */
	public static SegmentsExperimentRel findByPrimaryKey(
			long segmentsExperimentRelId)
		throws com.liferay.segments.exception.NoSuchExperimentRelException {

		return getPersistence().findByPrimaryKey(segmentsExperimentRelId);
	}

	/**
	 * Returns the segments experiment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsExperimentRelId the primary key of the segments experiment rel
	 * @return the segments experiment rel, or <code>null</code> if a segments experiment rel with the primary key could not be found
	 */
	public static SegmentsExperimentRel fetchByPrimaryKey(
		long segmentsExperimentRelId) {

		return getPersistence().fetchByPrimaryKey(segmentsExperimentRelId);
	}

	/**
	 * Returns all the segments experiment rels.
	 *
	 * @return the segments experiment rels
	 */
	public static List<SegmentsExperimentRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the segments experiment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @return the range of segments experiment rels
	 */
	public static List<SegmentsExperimentRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the segments experiment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments experiment rels
	 */
	public static List<SegmentsExperimentRel> findAll(
		int start, int end,
		OrderByComparator<SegmentsExperimentRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the segments experiment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of segments experiment rels
	 */
	public static List<SegmentsExperimentRel> findAll(
		int start, int end,
		OrderByComparator<SegmentsExperimentRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the segments experiment rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of segments experiment rels.
	 *
	 * @return the number of segments experiment rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SegmentsExperimentRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SegmentsExperimentRelPersistence, SegmentsExperimentRelPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SegmentsExperimentRelPersistence.class);

		ServiceTracker
			<SegmentsExperimentRelPersistence, SegmentsExperimentRelPersistence>
				serviceTracker =
					new ServiceTracker
						<SegmentsExperimentRelPersistence,
						 SegmentsExperimentRelPersistence>(
							 bundle.getBundleContext(),
							 SegmentsExperimentRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}