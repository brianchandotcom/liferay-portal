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

package com.liferay.dynamic.data.mapping.service.persistence;

import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
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
 * The persistence utility for the ddm structure version service. This utility wraps <code>com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStructureVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersionPersistence
 * @generated
 */
public class DDMStructureVersionUtil {

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
	public static void clearCache(DDMStructureVersion ddmStructureVersion) {
		getPersistence().clearCache(ddmStructureVersion);
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
	public static Map<Serializable, DDMStructureVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMStructureVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStructureVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStructureVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDMStructureVersion update(
		DDMStructureVersion ddmStructureVersion) {

		return getPersistence().update(ddmStructureVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDMStructureVersion update(
		DDMStructureVersion ddmStructureVersion,
		ServiceContext serviceContext) {

		return getPersistence().update(ddmStructureVersion, serviceContext);
	}

	/**
	 * Returns all the ddm structure versions where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching ddm structure versions
	 */
	public static List<DDMStructureVersion> findByStructureId(
		long structureId) {

		return getPersistence().findByStructureId(structureId);
	}

	/**
	 * Returns a range of all the ddm structure versions where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureVersionModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @return the range of matching ddm structure versions
	 */
	public static List<DDMStructureVersion> findByStructureId(
		long structureId, int start, int end) {

		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure versions where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureVersionModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure versions
	 */
	public static List<DDMStructureVersion> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return getPersistence().findByStructureId(
			structureId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure versions where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureVersionModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure versions
	 */
	public static List<DDMStructureVersion> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByStructureId(
			structureId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm structure version in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure version
	 * @throws NoSuchStructureVersionException if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion findByStructureId_First(
			long structureId,
			OrderByComparator<DDMStructureVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureVersionException {

		return getPersistence().findByStructureId_First(
			structureId, orderByComparator);
	}

	/**
	 * Returns the first ddm structure version in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure version, or <code>null</code> if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion fetchByStructureId_First(
		long structureId,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return getPersistence().fetchByStructureId_First(
			structureId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure version in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure version
	 * @throws NoSuchStructureVersionException if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion findByStructureId_Last(
			long structureId,
			OrderByComparator<DDMStructureVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureVersionException {

		return getPersistence().findByStructureId_Last(
			structureId, orderByComparator);
	}

	/**
	 * Returns the last ddm structure version in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure version, or <code>null</code> if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion fetchByStructureId_Last(
		long structureId,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return getPersistence().fetchByStructureId_Last(
			structureId, orderByComparator);
	}

	/**
	 * Returns the ddm structure versions before and after the current ddm structure version in the ordered set where structureId = &#63;.
	 *
	 * @param structureVersionId the primary key of the current ddm structure version
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure version
	 * @throws NoSuchStructureVersionException if a ddm structure version with the primary key could not be found
	 */
	public static DDMStructureVersion[] findByStructureId_PrevAndNext(
			long structureVersionId, long structureId,
			OrderByComparator<DDMStructureVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureVersionException {

		return getPersistence().findByStructureId_PrevAndNext(
			structureVersionId, structureId, orderByComparator);
	}

	/**
	 * Removes all the ddm structure versions where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	public static void removeByStructureId(long structureId) {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	 * Returns the number of ddm structure versions where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching ddm structure versions
	 */
	public static int countByStructureId(long structureId) {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	 * Returns the ddm structure version where structureId = &#63; and version = &#63; or throws a <code>NoSuchStructureVersionException</code> if it could not be found.
	 *
	 * @param structureId the structure ID
	 * @param version the version
	 * @return the matching ddm structure version
	 * @throws NoSuchStructureVersionException if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion findByS_V(
			long structureId, String version)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureVersionException {

		return getPersistence().findByS_V(structureId, version);
	}

	/**
	 * Returns the ddm structure version where structureId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param structureId the structure ID
	 * @param version the version
	 * @return the matching ddm structure version, or <code>null</code> if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion fetchByS_V(
		long structureId, String version) {

		return getPersistence().fetchByS_V(structureId, version);
	}

	/**
	 * Returns the ddm structure version where structureId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param structureId the structure ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure version, or <code>null</code> if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion fetchByS_V(
		long structureId, String version, boolean useFinderCache) {

		return getPersistence().fetchByS_V(
			structureId, version, useFinderCache);
	}

	/**
	 * Removes the ddm structure version where structureId = &#63; and version = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 * @param version the version
	 * @return the ddm structure version that was removed
	 */
	public static DDMStructureVersion removeByS_V(
			long structureId, String version)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureVersionException {

		return getPersistence().removeByS_V(structureId, version);
	}

	/**
	 * Returns the number of ddm structure versions where structureId = &#63; and version = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param version the version
	 * @return the number of matching ddm structure versions
	 */
	public static int countByS_V(long structureId, String version) {
		return getPersistence().countByS_V(structureId, version);
	}

	/**
	 * Returns all the ddm structure versions where structureId = &#63; and status = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param status the status
	 * @return the matching ddm structure versions
	 */
	public static List<DDMStructureVersion> findByS_S(
		long structureId, int status) {

		return getPersistence().findByS_S(structureId, status);
	}

	/**
	 * Returns a range of all the ddm structure versions where structureId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureVersionModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @return the range of matching ddm structure versions
	 */
	public static List<DDMStructureVersion> findByS_S(
		long structureId, int status, int start, int end) {

		return getPersistence().findByS_S(structureId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure versions where structureId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureVersionModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure versions
	 */
	public static List<DDMStructureVersion> findByS_S(
		long structureId, int status, int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return getPersistence().findByS_S(
			structureId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure versions where structureId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureVersionModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure versions
	 */
	public static List<DDMStructureVersion> findByS_S(
		long structureId, int status, int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByS_S(
			structureId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm structure version in the ordered set where structureId = &#63; and status = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure version
	 * @throws NoSuchStructureVersionException if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion findByS_S_First(
			long structureId, int status,
			OrderByComparator<DDMStructureVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureVersionException {

		return getPersistence().findByS_S_First(
			structureId, status, orderByComparator);
	}

	/**
	 * Returns the first ddm structure version in the ordered set where structureId = &#63; and status = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure version, or <code>null</code> if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion fetchByS_S_First(
		long structureId, int status,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return getPersistence().fetchByS_S_First(
			structureId, status, orderByComparator);
	}

	/**
	 * Returns the last ddm structure version in the ordered set where structureId = &#63; and status = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure version
	 * @throws NoSuchStructureVersionException if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion findByS_S_Last(
			long structureId, int status,
			OrderByComparator<DDMStructureVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureVersionException {

		return getPersistence().findByS_S_Last(
			structureId, status, orderByComparator);
	}

	/**
	 * Returns the last ddm structure version in the ordered set where structureId = &#63; and status = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure version, or <code>null</code> if a matching ddm structure version could not be found
	 */
	public static DDMStructureVersion fetchByS_S_Last(
		long structureId, int status,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return getPersistence().fetchByS_S_Last(
			structureId, status, orderByComparator);
	}

	/**
	 * Returns the ddm structure versions before and after the current ddm structure version in the ordered set where structureId = &#63; and status = &#63;.
	 *
	 * @param structureVersionId the primary key of the current ddm structure version
	 * @param structureId the structure ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure version
	 * @throws NoSuchStructureVersionException if a ddm structure version with the primary key could not be found
	 */
	public static DDMStructureVersion[] findByS_S_PrevAndNext(
			long structureVersionId, long structureId, int status,
			OrderByComparator<DDMStructureVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureVersionException {

		return getPersistence().findByS_S_PrevAndNext(
			structureVersionId, structureId, status, orderByComparator);
	}

	/**
	 * Removes all the ddm structure versions where structureId = &#63; and status = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 * @param status the status
	 */
	public static void removeByS_S(long structureId, int status) {
		getPersistence().removeByS_S(structureId, status);
	}

	/**
	 * Returns the number of ddm structure versions where structureId = &#63; and status = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param status the status
	 * @return the number of matching ddm structure versions
	 */
	public static int countByS_S(long structureId, int status) {
		return getPersistence().countByS_S(structureId, status);
	}

	/**
	 * Caches the ddm structure version in the entity cache if it is enabled.
	 *
	 * @param ddmStructureVersion the ddm structure version
	 */
	public static void cacheResult(DDMStructureVersion ddmStructureVersion) {
		getPersistence().cacheResult(ddmStructureVersion);
	}

	/**
	 * Caches the ddm structure versions in the entity cache if it is enabled.
	 *
	 * @param ddmStructureVersions the ddm structure versions
	 */
	public static void cacheResult(
		List<DDMStructureVersion> ddmStructureVersions) {

		getPersistence().cacheResult(ddmStructureVersions);
	}

	/**
	 * Creates a new ddm structure version with the primary key. Does not add the ddm structure version to the database.
	 *
	 * @param structureVersionId the primary key for the new ddm structure version
	 * @return the new ddm structure version
	 */
	public static DDMStructureVersion create(long structureVersionId) {
		return getPersistence().create(structureVersionId);
	}

	/**
	 * Removes the ddm structure version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureVersionId the primary key of the ddm structure version
	 * @return the ddm structure version that was removed
	 * @throws NoSuchStructureVersionException if a ddm structure version with the primary key could not be found
	 */
	public static DDMStructureVersion remove(long structureVersionId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureVersionException {

		return getPersistence().remove(structureVersionId);
	}

	public static DDMStructureVersion updateImpl(
		DDMStructureVersion ddmStructureVersion) {

		return getPersistence().updateImpl(ddmStructureVersion);
	}

	/**
	 * Returns the ddm structure version with the primary key or throws a <code>NoSuchStructureVersionException</code> if it could not be found.
	 *
	 * @param structureVersionId the primary key of the ddm structure version
	 * @return the ddm structure version
	 * @throws NoSuchStructureVersionException if a ddm structure version with the primary key could not be found
	 */
	public static DDMStructureVersion findByPrimaryKey(long structureVersionId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStructureVersionException {

		return getPersistence().findByPrimaryKey(structureVersionId);
	}

	/**
	 * Returns the ddm structure version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureVersionId the primary key of the ddm structure version
	 * @return the ddm structure version, or <code>null</code> if a ddm structure version with the primary key could not be found
	 */
	public static DDMStructureVersion fetchByPrimaryKey(
		long structureVersionId) {

		return getPersistence().fetchByPrimaryKey(structureVersionId);
	}

	/**
	 * Returns all the ddm structure versions.
	 *
	 * @return the ddm structure versions
	 */
	public static List<DDMStructureVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddm structure versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @return the range of ddm structure versions
	 */
	public static List<DDMStructureVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddm structure versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm structure versions
	 */
	public static List<DDMStructureVersion> findAll(
		int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm structure versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure versions
	 * @param end the upper bound of the range of ddm structure versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm structure versions
	 */
	public static List<DDMStructureVersion> findAll(
		int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm structure versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddm structure versions.
	 *
	 * @return the number of ddm structure versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMStructureVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMStructureVersionPersistence, DDMStructureVersionPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMStructureVersionPersistence.class);

		ServiceTracker
			<DDMStructureVersionPersistence, DDMStructureVersionPersistence>
				serviceTracker =
					new ServiceTracker
						<DDMStructureVersionPersistence,
						 DDMStructureVersionPersistence>(
							 bundle.getBundleContext(),
							 DDMStructureVersionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}