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
import com.liferay.segments.model.SegmentsEntryRel;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the segments entry rel service. This utility wraps <code>com.liferay.segments.service.persistence.impl.SegmentsEntryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRelPersistence
 * @generated
 */
public class SegmentsEntryRelUtil {

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
	public static void clearCache(SegmentsEntryRel segmentsEntryRel) {
		getPersistence().clearCache(segmentsEntryRel);
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
	public static Map<Serializable, SegmentsEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SegmentsEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SegmentsEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SegmentsEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SegmentsEntryRel update(SegmentsEntryRel segmentsEntryRel) {
		return getPersistence().update(segmentsEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SegmentsEntryRel update(
		SegmentsEntryRel segmentsEntryRel, ServiceContext serviceContext) {

		return getPersistence().update(segmentsEntryRel, serviceContext);
	}

	/**
	 * Returns all the segments entry rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId) {

		return getPersistence().findBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns a range of all the segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @return the range of matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where segmentsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findBySegmentsEntryId(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel findBySegmentsEntryId_First(
			long segmentsEntryId,
			OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the first segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel fetchBySegmentsEntryId_First(
		long segmentsEntryId,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchBySegmentsEntryId_First(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the last segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel findBySegmentsEntryId_Last(
			long segmentsEntryId,
			OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the last segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel fetchBySegmentsEntryId_Last(
		long segmentsEntryId,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchBySegmentsEntryId_Last(
			segmentsEntryId, orderByComparator);
	}

	/**
	 * Returns the segments entry rels before and after the current segments entry rel in the ordered set where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryRelId the primary key of the current segments entry rel
	 * @param segmentsEntryId the segments entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry rel
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	public static SegmentsEntryRel[] findBySegmentsEntryId_PrevAndNext(
			long segmentsEntryRelId, long segmentsEntryId,
			OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findBySegmentsEntryId_PrevAndNext(
			segmentsEntryRelId, segmentsEntryId, orderByComparator);
	}

	/**
	 * Removes all the segments entry rels where segmentsEntryId = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 */
	public static void removeBySegmentsEntryId(long segmentsEntryId) {
		getPersistence().removeBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns the number of segments entry rels where segmentsEntryId = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @return the number of matching segments entry rels
	 */
	public static int countBySegmentsEntryId(long segmentsEntryId) {
		return getPersistence().countBySegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Returns all the segments entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findByCN_CPK(
		long classNameId, long classPK) {

		return getPersistence().findByCN_CPK(classNameId, classPK);
	}

	/**
	 * Returns a range of all the segments entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @return the range of matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByCN_CPK(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().findByCN_CPK(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCN_CPK(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first segments entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel findByCN_CPK_First(
			long classNameId, long classPK,
			OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findByCN_CPK_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first segments entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel fetchByCN_CPK_First(
		long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchByCN_CPK_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last segments entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel findByCN_CPK_Last(
			long classNameId, long classPK,
			OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findByCN_CPK_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last segments entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel fetchByCN_CPK_Last(
		long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchByCN_CPK_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the segments entry rels before and after the current segments entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsEntryRelId the primary key of the current segments entry rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry rel
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	public static SegmentsEntryRel[] findByCN_CPK_PrevAndNext(
			long segmentsEntryRelId, long classNameId, long classPK,
			OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findByCN_CPK_PrevAndNext(
			segmentsEntryRelId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the segments entry rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByCN_CPK(long classNameId, long classPK) {
		getPersistence().removeByCN_CPK(classNameId, classPK);
	}

	/**
	 * Returns the number of segments entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments entry rels
	 */
	public static int countByCN_CPK(long classNameId, long classPK) {
		return getPersistence().countByCN_CPK(classNameId, classPK);
	}

	/**
	 * Returns all the segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findByG_CN_CPK(
		long groupId, long classNameId, long classPK) {

		return getPersistence().findByG_CN_CPK(groupId, classNameId, classPK);
	}

	/**
	 * Returns a range of all the segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @return the range of matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findByG_CN_CPK(
		long groupId, long classNameId, long classPK, int start, int end) {

		return getPersistence().findByG_CN_CPK(
			groupId, classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findByG_CN_CPK(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().findByG_CN_CPK(
			groupId, classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments entry rels
	 */
	public static List<SegmentsEntryRel> findByG_CN_CPK(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_CN_CPK(
			groupId, classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first segments entry rel in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel findByG_CN_CPK_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findByG_CN_CPK_First(
			groupId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first segments entry rel in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel fetchByG_CN_CPK_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchByG_CN_CPK_First(
			groupId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last segments entry rel in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel findByG_CN_CPK_Last(
			long groupId, long classNameId, long classPK,
			OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findByG_CN_CPK_Last(
			groupId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last segments entry rel in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel fetchByG_CN_CPK_Last(
		long groupId, long classNameId, long classPK,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().fetchByG_CN_CPK_Last(
			groupId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the segments entry rels before and after the current segments entry rel in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsEntryRelId the primary key of the current segments entry rel
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments entry rel
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	public static SegmentsEntryRel[] findByG_CN_CPK_PrevAndNext(
			long segmentsEntryRelId, long groupId, long classNameId,
			long classPK, OrderByComparator<SegmentsEntryRel> orderByComparator)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findByG_CN_CPK_PrevAndNext(
			segmentsEntryRelId, groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	 * Removes all the segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByG_CN_CPK(
		long groupId, long classNameId, long classPK) {

		getPersistence().removeByG_CN_CPK(groupId, classNameId, classPK);
	}

	/**
	 * Returns the number of segments entry rels where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments entry rels
	 */
	public static int countByG_CN_CPK(
		long groupId, long classNameId, long classPK) {

		return getPersistence().countByG_CN_CPK(groupId, classNameId, classPK);
	}

	/**
	 * Returns the segments entry rel where segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchEntryRelException</code> if it could not be found.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments entry rel
	 * @throws NoSuchEntryRelException if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel findByS_CN_CPK(
			long segmentsEntryId, long classNameId, long classPK)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findByS_CN_CPK(
			segmentsEntryId, classNameId, classPK);
	}

	/**
	 * Returns the segments entry rel where segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel fetchByS_CN_CPK(
		long segmentsEntryId, long classNameId, long classPK) {

		return getPersistence().fetchByS_CN_CPK(
			segmentsEntryId, classNameId, classPK);
	}

	/**
	 * Returns the segments entry rel where segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments entry rel, or <code>null</code> if a matching segments entry rel could not be found
	 */
	public static SegmentsEntryRel fetchByS_CN_CPK(
		long segmentsEntryId, long classNameId, long classPK,
		boolean useFinderCache) {

		return getPersistence().fetchByS_CN_CPK(
			segmentsEntryId, classNameId, classPK, useFinderCache);
	}

	/**
	 * Removes the segments entry rel where segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the segments entry rel that was removed
	 */
	public static SegmentsEntryRel removeByS_CN_CPK(
			long segmentsEntryId, long classNameId, long classPK)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().removeByS_CN_CPK(
			segmentsEntryId, classNameId, classPK);
	}

	/**
	 * Returns the number of segments entry rels where segmentsEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsEntryId the segments entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments entry rels
	 */
	public static int countByS_CN_CPK(
		long segmentsEntryId, long classNameId, long classPK) {

		return getPersistence().countByS_CN_CPK(
			segmentsEntryId, classNameId, classPK);
	}

	/**
	 * Caches the segments entry rel in the entity cache if it is enabled.
	 *
	 * @param segmentsEntryRel the segments entry rel
	 */
	public static void cacheResult(SegmentsEntryRel segmentsEntryRel) {
		getPersistence().cacheResult(segmentsEntryRel);
	}

	/**
	 * Caches the segments entry rels in the entity cache if it is enabled.
	 *
	 * @param segmentsEntryRels the segments entry rels
	 */
	public static void cacheResult(List<SegmentsEntryRel> segmentsEntryRels) {
		getPersistence().cacheResult(segmentsEntryRels);
	}

	/**
	 * Creates a new segments entry rel with the primary key. Does not add the segments entry rel to the database.
	 *
	 * @param segmentsEntryRelId the primary key for the new segments entry rel
	 * @return the new segments entry rel
	 */
	public static SegmentsEntryRel create(long segmentsEntryRelId) {
		return getPersistence().create(segmentsEntryRelId);
	}

	/**
	 * Removes the segments entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRelId the primary key of the segments entry rel
	 * @return the segments entry rel that was removed
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	public static SegmentsEntryRel remove(long segmentsEntryRelId)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().remove(segmentsEntryRelId);
	}

	public static SegmentsEntryRel updateImpl(
		SegmentsEntryRel segmentsEntryRel) {

		return getPersistence().updateImpl(segmentsEntryRel);
	}

	/**
	 * Returns the segments entry rel with the primary key or throws a <code>NoSuchEntryRelException</code> if it could not be found.
	 *
	 * @param segmentsEntryRelId the primary key of the segments entry rel
	 * @return the segments entry rel
	 * @throws NoSuchEntryRelException if a segments entry rel with the primary key could not be found
	 */
	public static SegmentsEntryRel findByPrimaryKey(long segmentsEntryRelId)
		throws com.liferay.segments.exception.NoSuchEntryRelException {

		return getPersistence().findByPrimaryKey(segmentsEntryRelId);
	}

	/**
	 * Returns the segments entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsEntryRelId the primary key of the segments entry rel
	 * @return the segments entry rel, or <code>null</code> if a segments entry rel with the primary key could not be found
	 */
	public static SegmentsEntryRel fetchByPrimaryKey(long segmentsEntryRelId) {
		return getPersistence().fetchByPrimaryKey(segmentsEntryRelId);
	}

	/**
	 * Returns all the segments entry rels.
	 *
	 * @return the segments entry rels
	 */
	public static List<SegmentsEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @return the range of segments entry rels
	 */
	public static List<SegmentsEntryRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments entry rels
	 */
	public static List<SegmentsEntryRel> findAll(
		int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of segments entry rels
	 */
	public static List<SegmentsEntryRel> findAll(
		int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the segments entry rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of segments entry rels.
	 *
	 * @return the number of segments entry rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SegmentsEntryRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SegmentsEntryRelPersistence, SegmentsEntryRelPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SegmentsEntryRelPersistence.class);

		ServiceTracker<SegmentsEntryRelPersistence, SegmentsEntryRelPersistence>
			serviceTracker =
				new ServiceTracker
					<SegmentsEntryRelPersistence, SegmentsEntryRelPersistence>(
						bundle.getBundleContext(),
						SegmentsEntryRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}