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

package com.liferay.meris.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.meris.model.MerisSegment;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the meris segment service. This utility wraps {@link com.liferay.meris.service.persistence.impl.MerisSegmentPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see MerisSegmentPersistence
 * @see com.liferay.meris.service.persistence.impl.MerisSegmentPersistenceImpl
 * @generated
 */
@ProviderType
public class MerisSegmentUtil {
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
	public static void clearCache(MerisSegment merisSegment) {
		getPersistence().clearCache(merisSegment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MerisSegment> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MerisSegment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MerisSegment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MerisSegment> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MerisSegment update(MerisSegment merisSegment) {
		return getPersistence().update(merisSegment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MerisSegment update(MerisSegment merisSegment,
		ServiceContext serviceContext) {
		return getPersistence().update(merisSegment, serviceContext);
	}

	/**
	* Returns all the meris segments where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching meris segments
	*/
	public static List<MerisSegment> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the meris segments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @return the range of matching meris segments
	*/
	public static List<MerisSegment> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the meris segments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching meris segments
	*/
	public static List<MerisSegment> findByGroupId(long groupId, int start,
		int end, OrderByComparator<MerisSegment> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the meris segments where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching meris segments
	*/
	public static List<MerisSegment> findByGroupId(long groupId, int start,
		int end, OrderByComparator<MerisSegment> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first meris segment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment
	* @throws NoSuchSegmentException if a matching meris segment could not be found
	*/
	public static MerisSegment findByGroupId_First(long groupId,
		OrderByComparator<MerisSegment> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first meris segment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public static MerisSegment fetchByGroupId_First(long groupId,
		OrderByComparator<MerisSegment> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last meris segment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment
	* @throws NoSuchSegmentException if a matching meris segment could not be found
	*/
	public static MerisSegment findByGroupId_Last(long groupId,
		OrderByComparator<MerisSegment> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last meris segment in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public static MerisSegment fetchByGroupId_Last(long groupId,
		OrderByComparator<MerisSegment> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the meris segments before and after the current meris segment in the ordered set where groupId = &#63;.
	*
	* @param merisSegmentId the primary key of the current meris segment
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next meris segment
	* @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	*/
	public static MerisSegment[] findByGroupId_PrevAndNext(
		long merisSegmentId, long groupId,
		OrderByComparator<MerisSegment> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(merisSegmentId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the meris segments where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of meris segments where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching meris segments
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the meris segment where groupId = &#63; and key = &#63; or throws a {@link NoSuchSegmentException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching meris segment
	* @throws NoSuchSegmentException if a matching meris segment could not be found
	*/
	public static MerisSegment findByG_K(long groupId, String key)
		throws com.liferay.meris.exception.NoSuchSegmentException {
		return getPersistence().findByG_K(groupId, key);
	}

	/**
	* Returns the meris segment where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public static MerisSegment fetchByG_K(long groupId, String key) {
		return getPersistence().fetchByG_K(groupId, key);
	}

	/**
	* Returns the meris segment where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public static MerisSegment fetchByG_K(long groupId, String key,
		boolean retrieveFromCache) {
		return getPersistence().fetchByG_K(groupId, key, retrieveFromCache);
	}

	/**
	* Removes the meris segment where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the meris segment that was removed
	*/
	public static MerisSegment removeByG_K(long groupId, String key)
		throws com.liferay.meris.exception.NoSuchSegmentException {
		return getPersistence().removeByG_K(groupId, key);
	}

	/**
	* Returns the number of meris segments where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching meris segments
	*/
	public static int countByG_K(long groupId, String key) {
		return getPersistence().countByG_K(groupId, key);
	}

	/**
	* Returns all the meris segments where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching meris segments
	*/
	public static List<MerisSegment> findByG_A(long groupId, boolean active) {
		return getPersistence().findByG_A(groupId, active);
	}

	/**
	* Returns a range of all the meris segments where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @return the range of matching meris segments
	*/
	public static List<MerisSegment> findByG_A(long groupId, boolean active,
		int start, int end) {
		return getPersistence().findByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the meris segments where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching meris segments
	*/
	public static List<MerisSegment> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator<MerisSegment> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the meris segments where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching meris segments
	*/
	public static List<MerisSegment> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator<MerisSegment> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first meris segment in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment
	* @throws NoSuchSegmentException if a matching meris segment could not be found
	*/
	public static MerisSegment findByG_A_First(long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentException {
		return getPersistence()
				   .findByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the first meris segment in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public static MerisSegment fetchByG_A_First(long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the last meris segment in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment
	* @throws NoSuchSegmentException if a matching meris segment could not be found
	*/
	public static MerisSegment findByG_A_Last(long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentException {
		return getPersistence()
				   .findByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the last meris segment in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment, or <code>null</code> if a matching meris segment could not be found
	*/
	public static MerisSegment fetchByG_A_Last(long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the meris segments before and after the current meris segment in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param merisSegmentId the primary key of the current meris segment
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next meris segment
	* @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	*/
	public static MerisSegment[] findByG_A_PrevAndNext(long merisSegmentId,
		long groupId, boolean active,
		OrderByComparator<MerisSegment> orderByComparator)
		throws com.liferay.meris.exception.NoSuchSegmentException {
		return getPersistence()
				   .findByG_A_PrevAndNext(merisSegmentId, groupId, active,
			orderByComparator);
	}

	/**
	* Removes all the meris segments where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public static void removeByG_A(long groupId, boolean active) {
		getPersistence().removeByG_A(groupId, active);
	}

	/**
	* Returns the number of meris segments where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching meris segments
	*/
	public static int countByG_A(long groupId, boolean active) {
		return getPersistence().countByG_A(groupId, active);
	}

	/**
	* Caches the meris segment in the entity cache if it is enabled.
	*
	* @param merisSegment the meris segment
	*/
	public static void cacheResult(MerisSegment merisSegment) {
		getPersistence().cacheResult(merisSegment);
	}

	/**
	* Caches the meris segments in the entity cache if it is enabled.
	*
	* @param merisSegments the meris segments
	*/
	public static void cacheResult(List<MerisSegment> merisSegments) {
		getPersistence().cacheResult(merisSegments);
	}

	/**
	* Creates a new meris segment with the primary key. Does not add the meris segment to the database.
	*
	* @param merisSegmentId the primary key for the new meris segment
	* @return the new meris segment
	*/
	public static MerisSegment create(long merisSegmentId) {
		return getPersistence().create(merisSegmentId);
	}

	/**
	* Removes the meris segment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param merisSegmentId the primary key of the meris segment
	* @return the meris segment that was removed
	* @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	*/
	public static MerisSegment remove(long merisSegmentId)
		throws com.liferay.meris.exception.NoSuchSegmentException {
		return getPersistence().remove(merisSegmentId);
	}

	public static MerisSegment updateImpl(MerisSegment merisSegment) {
		return getPersistence().updateImpl(merisSegment);
	}

	/**
	* Returns the meris segment with the primary key or throws a {@link NoSuchSegmentException} if it could not be found.
	*
	* @param merisSegmentId the primary key of the meris segment
	* @return the meris segment
	* @throws NoSuchSegmentException if a meris segment with the primary key could not be found
	*/
	public static MerisSegment findByPrimaryKey(long merisSegmentId)
		throws com.liferay.meris.exception.NoSuchSegmentException {
		return getPersistence().findByPrimaryKey(merisSegmentId);
	}

	/**
	* Returns the meris segment with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param merisSegmentId the primary key of the meris segment
	* @return the meris segment, or <code>null</code> if a meris segment with the primary key could not be found
	*/
	public static MerisSegment fetchByPrimaryKey(long merisSegmentId) {
		return getPersistence().fetchByPrimaryKey(merisSegmentId);
	}

	public static java.util.Map<java.io.Serializable, MerisSegment> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the meris segments.
	*
	* @return the meris segments
	*/
	public static List<MerisSegment> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the meris segments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @return the range of meris segments
	*/
	public static List<MerisSegment> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the meris segments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of meris segments
	*/
	public static List<MerisSegment> findAll(int start, int end,
		OrderByComparator<MerisSegment> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the meris segments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segments
	* @param end the upper bound of the range of meris segments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of meris segments
	*/
	public static List<MerisSegment> findAll(int start, int end,
		OrderByComparator<MerisSegment> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the meris segments from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of meris segments.
	*
	* @return the number of meris segments
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static MerisSegmentPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MerisSegmentPersistence, MerisSegmentPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MerisSegmentPersistence.class);

		ServiceTracker<MerisSegmentPersistence, MerisSegmentPersistence> serviceTracker =
			new ServiceTracker<MerisSegmentPersistence, MerisSegmentPersistence>(bundle.getBundleContext(),
				MerisSegmentPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}