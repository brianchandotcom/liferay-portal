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

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
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
 * The persistence utility for the ddm form instance version service. This utility wraps <code>com.liferay.dynamic.data.mapping.service.persistence.impl.DDMFormInstanceVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceVersionPersistence
 * @generated
 */
public class DDMFormInstanceVersionUtil {

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
	public static void clearCache(
		DDMFormInstanceVersion ddmFormInstanceVersion) {

		getPersistence().clearCache(ddmFormInstanceVersion);
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
	public static Map<Serializable, DDMFormInstanceVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMFormInstanceVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMFormInstanceVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMFormInstanceVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDMFormInstanceVersion update(
		DDMFormInstanceVersion ddmFormInstanceVersion) {

		return getPersistence().update(ddmFormInstanceVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDMFormInstanceVersion update(
		DDMFormInstanceVersion ddmFormInstanceVersion,
		ServiceContext serviceContext) {

		return getPersistence().update(ddmFormInstanceVersion, serviceContext);
	}

	/**
	 * Returns all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId) {

		return getPersistence().findByFormInstanceId(formInstanceId);
	}

	/**
	 * Returns a range of all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @return the range of matching ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end) {

		return getPersistence().findByFormInstanceId(
			formInstanceId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return getPersistence().findByFormInstanceId(
			formInstanceId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByFormInstanceId(
			formInstanceId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion findByFormInstanceId_First(
			long formInstanceId,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceVersionException {

		return getPersistence().findByFormInstanceId_First(
			formInstanceId, orderByComparator);
	}

	/**
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion fetchByFormInstanceId_First(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return getPersistence().fetchByFormInstanceId_First(
			formInstanceId, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion findByFormInstanceId_Last(
			long formInstanceId,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceVersionException {

		return getPersistence().findByFormInstanceId_Last(
			formInstanceId, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion fetchByFormInstanceId_Last(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return getPersistence().fetchByFormInstanceId_Last(
			formInstanceId, orderByComparator);
	}

	/**
	 * Returns the ddm form instance versions before and after the current ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceVersionId the primary key of the current ddm form instance version
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	public static DDMFormInstanceVersion[] findByFormInstanceId_PrevAndNext(
			long formInstanceVersionId, long formInstanceId,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceVersionException {

		return getPersistence().findByFormInstanceId_PrevAndNext(
			formInstanceVersionId, formInstanceId, orderByComparator);
	}

	/**
	 * Removes all the ddm form instance versions where formInstanceId = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 */
	public static void removeByFormInstanceId(long formInstanceId) {
		getPersistence().removeByFormInstanceId(formInstanceId);
	}

	/**
	 * Returns the number of ddm form instance versions where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the number of matching ddm form instance versions
	 */
	public static int countByFormInstanceId(long formInstanceId) {
		return getPersistence().countByFormInstanceId(formInstanceId);
	}

	/**
	 * Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or throws a <code>NoSuchFormInstanceVersionException</code> if it could not be found.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion findByF_V(
			long formInstanceId, String version)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceVersionException {

		return getPersistence().findByF_V(formInstanceId, version);
	}

	/**
	 * Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion fetchByF_V(
		long formInstanceId, String version) {

		return getPersistence().fetchByF_V(formInstanceId, version);
	}

	/**
	 * Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion fetchByF_V(
		long formInstanceId, String version, boolean useFinderCache) {

		return getPersistence().fetchByF_V(
			formInstanceId, version, useFinderCache);
	}

	/**
	 * Removes the ddm form instance version where formInstanceId = &#63; and version = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the ddm form instance version that was removed
	 */
	public static DDMFormInstanceVersion removeByF_V(
			long formInstanceId, String version)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceVersionException {

		return getPersistence().removeByF_V(formInstanceId, version);
	}

	/**
	 * Returns the number of ddm form instance versions where formInstanceId = &#63; and version = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the number of matching ddm form instance versions
	 */
	public static int countByF_V(long formInstanceId, String version) {
		return getPersistence().countByF_V(formInstanceId, version);
	}

	/**
	 * Returns all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @return the matching ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status) {

		return getPersistence().findByF_S(formInstanceId, status);
	}

	/**
	 * Returns a range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @return the range of matching ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status, int start, int end) {

		return getPersistence().findByF_S(formInstanceId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return getPersistence().findByF_S(
			formInstanceId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findByF_S(
		long formInstanceId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByF_S(
			formInstanceId, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion findByF_S_First(
			long formInstanceId, int status,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceVersionException {

		return getPersistence().findByF_S_First(
			formInstanceId, status, orderByComparator);
	}

	/**
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion fetchByF_S_First(
		long formInstanceId, int status,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return getPersistence().fetchByF_S_First(
			formInstanceId, status, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion findByF_S_Last(
			long formInstanceId, int status,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceVersionException {

		return getPersistence().findByF_S_Last(
			formInstanceId, status, orderByComparator);
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	public static DDMFormInstanceVersion fetchByF_S_Last(
		long formInstanceId, int status,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return getPersistence().fetchByF_S_Last(
			formInstanceId, status, orderByComparator);
	}

	/**
	 * Returns the ddm form instance versions before and after the current ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceVersionId the primary key of the current ddm form instance version
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	public static DDMFormInstanceVersion[] findByF_S_PrevAndNext(
			long formInstanceVersionId, long formInstanceId, int status,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceVersionException {

		return getPersistence().findByF_S_PrevAndNext(
			formInstanceVersionId, formInstanceId, status, orderByComparator);
	}

	/**
	 * Removes all the ddm form instance versions where formInstanceId = &#63; and status = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 */
	public static void removeByF_S(long formInstanceId, int status) {
		getPersistence().removeByF_S(formInstanceId, status);
	}

	/**
	 * Returns the number of ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @return the number of matching ddm form instance versions
	 */
	public static int countByF_S(long formInstanceId, int status) {
		return getPersistence().countByF_S(formInstanceId, status);
	}

	/**
	 * Caches the ddm form instance version in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceVersion the ddm form instance version
	 */
	public static void cacheResult(
		DDMFormInstanceVersion ddmFormInstanceVersion) {

		getPersistence().cacheResult(ddmFormInstanceVersion);
	}

	/**
	 * Caches the ddm form instance versions in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceVersions the ddm form instance versions
	 */
	public static void cacheResult(
		List<DDMFormInstanceVersion> ddmFormInstanceVersions) {

		getPersistence().cacheResult(ddmFormInstanceVersions);
	}

	/**
	 * Creates a new ddm form instance version with the primary key. Does not add the ddm form instance version to the database.
	 *
	 * @param formInstanceVersionId the primary key for the new ddm form instance version
	 * @return the new ddm form instance version
	 */
	public static DDMFormInstanceVersion create(long formInstanceVersionId) {
		return getPersistence().create(formInstanceVersionId);
	}

	/**
	 * Removes the ddm form instance version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version that was removed
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	public static DDMFormInstanceVersion remove(long formInstanceVersionId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceVersionException {

		return getPersistence().remove(formInstanceVersionId);
	}

	public static DDMFormInstanceVersion updateImpl(
		DDMFormInstanceVersion ddmFormInstanceVersion) {

		return getPersistence().updateImpl(ddmFormInstanceVersion);
	}

	/**
	 * Returns the ddm form instance version with the primary key or throws a <code>NoSuchFormInstanceVersionException</code> if it could not be found.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	public static DDMFormInstanceVersion findByPrimaryKey(
			long formInstanceVersionId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchFormInstanceVersionException {

		return getPersistence().findByPrimaryKey(formInstanceVersionId);
	}

	/**
	 * Returns the ddm form instance version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version, or <code>null</code> if a ddm form instance version with the primary key could not be found
	 */
	public static DDMFormInstanceVersion fetchByPrimaryKey(
		long formInstanceVersionId) {

		return getPersistence().fetchByPrimaryKey(formInstanceVersionId);
	}

	/**
	 * Returns all the ddm form instance versions.
	 *
	 * @return the ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddm form instance versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @return the range of ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findAll(
		int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm form instance versions
	 */
	public static List<DDMFormInstanceVersion> findAll(
		int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm form instance versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddm form instance versions.
	 *
	 * @return the number of ddm form instance versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMFormInstanceVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMFormInstanceVersionPersistence, DDMFormInstanceVersionPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMFormInstanceVersionPersistence.class);

		ServiceTracker
			<DDMFormInstanceVersionPersistence,
			 DDMFormInstanceVersionPersistence> serviceTracker =
				new ServiceTracker
					<DDMFormInstanceVersionPersistence,
					 DDMFormInstanceVersionPersistence>(
						 bundle.getBundleContext(),
						 DDMFormInstanceVersionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}