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
import com.liferay.segments.model.SegmentsEntryRole;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the segments entry role service. This utility wraps <code>com.liferay.segments.service.persistence.impl.SegmentsEntryRolePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRolePersistence
 * @generated
 */
public class SegmentsEntryRoleUtil {

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
	public static void clearCache(SegmentsEntryRole segmentsEntryRole) {
		getPersistence().clearCache(segmentsEntryRole);
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
	public static Map<Serializable, SegmentsEntryRole> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SegmentsEntryRole> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SegmentsEntryRole> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SegmentsEntryRole> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SegmentsEntryRole update(
		SegmentsEntryRole segmentsEntryRole) {

		return getPersistence().update(segmentsEntryRole);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SegmentsEntryRole update(
		SegmentsEntryRole segmentsEntryRole, ServiceContext serviceContext) {

		return getPersistence().update(segmentsEntryRole, serviceContext);
	}

	/**
	 * Returns all the segments entry roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching segments entry roles
	 */
	public static List<SegmentsEntryRole> findByRoleId(long roleId) {
		return getPersistence().findByRoleId(roleId);
	}

	/**
	 * Returns a range of all the segments entry roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @return the range of matching segments entry roles
	 */
	public static List<SegmentsEntryRole> findByRoleId(
		long roleId, int start, int end) {

		return getPersistence().findByRoleId(roleId, start, end);
	}

	/**
	 * Returns an ordered range of all the segments entry roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry roles
	 */
	public static List<SegmentsEntryRole> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return getPersistence().findByRoleId(
			roleId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the segments entry roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entry roles
	 */
	public static List<SegmentsEntryRole> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByRoleId(
			roleId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole findByRoleId_First(
			long roleId, OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRoleException {

		return getPersistence().findByRoleId_First(roleId, orderByComparator);
	}

	/**
	 * Returns the first segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole fetchByRoleId_First(
		long roleId, OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return getPersistence().fetchByRoleId_First(roleId, orderByComparator);
	}

	/**
	 * Returns the last segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole findByRoleId_Last(
			long roleId, OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRoleException {

		return getPersistence().findByRoleId_Last(roleId, orderByComparator);
	}

	/**
	 * Returns the last segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole fetchByRoleId_Last(
		long roleId, OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return getPersistence().fetchByRoleId_Last(roleId, orderByComparator);
	}

	/**
	 * Returns the segments entry roles before and after the current segments entry role in the ordered set where roleId = &#63;.
	 *
	 * @param segmentsEntryRoleId the primary key of the current segments entry role
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry role
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	public static SegmentsEntryRole[] findByRoleId_PrevAndNext(
			long segmentsEntryRoleId, long roleId,
			OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRoleException {

		return getPersistence().findByRoleId_PrevAndNext(
			segmentsEntryRoleId, roleId, orderByComparator);
	}

	/**
	 * Removes all the segments entry roles where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 */
	public static void removeByRoleId(long roleId) {
		getPersistence().removeByRoleId(roleId);
	}

	/**
	 * Returns the number of segments entry roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching segments entry roles
	 */
	public static int countByRoleId(long roleId) {
		return getPersistence().countByRoleId(roleId);
	}

	/**
	 * Returns all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments entry roles
	 */
	public static List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId) {

		return getPersistence().findBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns a range of all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @return the range of matching segments entry roles
	 */
	public static List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry roles
	 */
	public static List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the segments entry roles where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entry roles
	 */
	public static List<SegmentsEntryRole> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole findBySegmentsEntryId_First(
			long segmentsEntryId,
			OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRoleException {

		return getPersistence().findBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the first segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return getPersistence().fetchBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the last segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole findBySegmentsEntryId_Last(
			long segmentsEntryId,
			OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRoleException {

		return getPersistence().findBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the last segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return getPersistence().fetchBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the segments entry roles before and after the current segments entry role in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryRoleId the primary key of the current segments entry role
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry role
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	public static SegmentsEntryRole[] findBySegmentsEntryId_PrevAndNext(
			long segmentsEntryRoleId, long segmentsEntryId,
			OrderByComparator<SegmentsEntryRole> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRoleException {

		return getPersistence().findBySegmentsEntryId_PrevAndNext(
			segmentsEntryRoleId, segmentsEntryId, orderByComparator);
	}

	/**
	 * Removes all the segments entry roles where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	public static void removeBySegmentsEntryId(long segmentsEntryId) {
		getPersistence().removeBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns the number of segments entry roles where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching segments entry roles
	 */
	public static int countBySegmentsEntryId(long segmentsEntryId) {
		return getPersistence().countBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns the segments entry role where roleId = &#63; and segmentsEntryId = &#63; or throws a <code>NoSuchEntryRoleException</code> if it could not be found.
	 *
	 * @param roleId the role ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments entry role
	 * @throws NoSuchEntryRoleException if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole findByR_S(long roleId, long segmentsEntryId)
		throws com.liferay.segments.exception.NoSuchEntryRoleException {

		return getPersistence().findByR_S(roleId, segmentsEntryId);
	}

	/**
	 * Returns the segments entry role where roleId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param roleId the role ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole fetchByR_S(
		long roleId, long segmentsEntryId) {

		return getPersistence().fetchByR_S(roleId, segmentsEntryId);
	}

	/**
	 * Returns the segments entry role where roleId = &#63; and segmentsEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param roleId the role ID
	 * @param segmentsEntryId the segments entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments entry role, or <code>null</code> if a matching segments entry role could not be found
	 */
	public static SegmentsEntryRole fetchByR_S(
		long roleId, long segmentsEntryId, boolean useFinderCache) {

		return getPersistence().fetchByR_S(
			roleId, segmentsEntryId, useFinderCache);
	}

	/**
	 * Removes the segments entry role where roleId = &#63; and segmentsEntryId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the segments entry role that was removed
	 */
	public static SegmentsEntryRole removeByR_S(
			long roleId, long segmentsEntryId)
		throws com.liferay.segments.exception.NoSuchEntryRoleException {

		return getPersistence().removeByR_S(roleId, segmentsEntryId);
	}

	/**
	 * Returns the number of segments entry roles where roleId = &#63; and segmentsEntryId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching segments entry roles
	 */
	public static int countByR_S(long roleId, long segmentsEntryId) {
		return getPersistence().countByR_S(roleId, segmentsEntryId);
	}

	/**
	 * Caches the segments entry role in the entity cache if it is enabled.
	 *
	 * @param segmentsEntryRole the segments entry role
	 */
	public static void cacheResult(SegmentsEntryRole segmentsEntryRole) {
		getPersistence().cacheResult(segmentsEntryRole);
	}

	/**
	 * Caches the segments entry roles in the entity cache if it is enabled.
	 *
	 * @param segmentsEntryRoles the segments entry roles
	 */
	public static void cacheResult(List<SegmentsEntryRole> segmentsEntryRoles) {
		getPersistence().cacheResult(segmentsEntryRoles);
	}

	/**
	 * Creates a new segments entry role with the primary key. Does not add the segments entry role to the database.
	 *
	 * @param segmentsEntryRoleId the primary key for the new segments entry role
	 * @return the new segments entry role
	 */
	public static SegmentsEntryRole create(long segmentsEntryRoleId) {
		return getPersistence().create(segmentsEntryRoleId);
	}

	/**
	 * Removes the segments entry role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRoleId the primary key of the segments entry role
	 * @return the segments entry role that was removed
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	public static SegmentsEntryRole remove(long segmentsEntryRoleId)
		throws com.liferay.segments.exception.NoSuchEntryRoleException {

		return getPersistence().remove(segmentsEntryRoleId);
	}

	public static SegmentsEntryRole updateImpl(
		SegmentsEntryRole segmentsEntryRole) {

		return getPersistence().updateImpl(segmentsEntryRole);
	}

	/**
	 * Returns the segments entry role with the primary key or throws a <code>NoSuchEntryRoleException</code> if it could not be found.
	 *
	 * @param segmentsEntryRoleId the primary key of the segments entry role
	 * @return the segments entry role
	 * @throws NoSuchEntryRoleException if a segments entry role with the primary key could not be found
	 */
	public static SegmentsEntryRole findByPrimaryKey(long segmentsEntryRoleId)
		throws com.liferay.segments.exception.NoSuchEntryRoleException {

		return getPersistence().findByPrimaryKey(segmentsEntryRoleId);
	}

	/**
	 * Returns the segments entry role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsEntryRoleId the primary key of the segments entry role
	 * @return the segments entry role, or <code>null</code> if a segments entry role with the primary key could not be found
	 */
	public static SegmentsEntryRole fetchByPrimaryKey(
		long segmentsEntryRoleId) {

		return getPersistence().fetchByPrimaryKey(segmentsEntryRoleId);
	}

	/**
	 * Returns all the segments entry roles.
	 *
	 * @return the segments entry roles
	 */
	public static List<SegmentsEntryRole> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the segments entry roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @return the range of segments entry roles
	 */
	public static List<SegmentsEntryRole> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the segments entry roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments entry roles
	 */
	public static List<SegmentsEntryRole> findAll(
		int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the segments entry roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry roles
	 * @param end the upper bound of the range of segments entry roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of segments entry roles
	 */
	public static List<SegmentsEntryRole> findAll(
		int start, int end,
		OrderByComparator<SegmentsEntryRole> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the segments entry roles from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of segments entry roles.
	 *
	 * @return the number of segments entry roles
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SegmentsEntryRolePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SegmentsEntryRolePersistence, SegmentsEntryRolePersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SegmentsEntryRolePersistence.class);

		ServiceTracker
			<SegmentsEntryRolePersistence, SegmentsEntryRolePersistence>
				serviceTracker =
					new ServiceTracker
						<SegmentsEntryRolePersistence,
						 SegmentsEntryRolePersistence>(
							 bundle.getBundleContext(),
							 SegmentsEntryRolePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}