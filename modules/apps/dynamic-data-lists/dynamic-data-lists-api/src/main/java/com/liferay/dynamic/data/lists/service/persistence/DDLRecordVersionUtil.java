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

package com.liferay.dynamic.data.lists.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.model.DDLRecordVersion;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the d d l record version service. This utility wraps {@link com.liferay.dynamic.data.lists.service.persistence.impl.DDLRecordVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionPersistence
 * @see com.liferay.dynamic.data.lists.service.persistence.impl.DDLRecordVersionPersistenceImpl
 * @generated
 */
@ProviderType
public class DDLRecordVersionUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(DDLRecordVersion ddlRecordVersion) {
		getPersistence().clearCache(ddlRecordVersion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDLRecordVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDLRecordVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDLRecordVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDLRecordVersion update(DDLRecordVersion ddlRecordVersion) {
		return getPersistence().update(ddlRecordVersion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDLRecordVersion update(DDLRecordVersion ddlRecordVersion,
		ServiceContext serviceContext) {
		return getPersistence().update(ddlRecordVersion, serviceContext);
	}

	/**
	* Returns all the d d l record versions where recordId = &#63;.
	*
	* @param recordId the record ID
	* @return the matching d d l record versions
	*/
	public static List<DDLRecordVersion> findByRecordId(long recordId) {
		return getPersistence().findByRecordId(recordId);
	}

	/**
	* Returns a range of all the d d l record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of matching d d l record versions
	*/
	public static List<DDLRecordVersion> findByRecordId(long recordId,
		int start, int end) {
		return getPersistence().findByRecordId(recordId, start, end);
	}

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record versions
	*/
	public static List<DDLRecordVersion> findByRecordId(long recordId,
		int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return getPersistence()
				   .findByRecordId(recordId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l record versions
	*/
	public static List<DDLRecordVersion> findByRecordId(long recordId,
		int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByRecordId(recordId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordVersionException if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion findByRecordId_First(long recordId,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException {
		return getPersistence().findByRecordId_First(recordId, orderByComparator);
	}

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion fetchByRecordId_First(long recordId,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return getPersistence()
				   .fetchByRecordId_First(recordId, orderByComparator);
	}

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordVersionException if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion findByRecordId_Last(long recordId,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException {
		return getPersistence().findByRecordId_Last(recordId, orderByComparator);
	}

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion fetchByRecordId_Last(long recordId,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return getPersistence().fetchByRecordId_Last(recordId, orderByComparator);
	}

	/**
	* Returns the d d l record versions before and after the current d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordVersionId the primary key of the current d d l record version
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record version
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	*/
	public static DDLRecordVersion[] findByRecordId_PrevAndNext(
		long recordVersionId, long recordId,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException {
		return getPersistence()
				   .findByRecordId_PrevAndNext(recordVersionId, recordId,
			orderByComparator);
	}

	/**
	* Removes all the d d l record versions where recordId = &#63; from the database.
	*
	* @param recordId the record ID
	*/
	public static void removeByRecordId(long recordId) {
		getPersistence().removeByRecordId(recordId);
	}

	/**
	* Returns the number of d d l record versions where recordId = &#63;.
	*
	* @param recordId the record ID
	* @return the number of matching d d l record versions
	*/
	public static int countByRecordId(long recordId) {
		return getPersistence().countByRecordId(recordId);
	}

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or throws a {@link com.liferay.dynamic.data.lists.NoSuchRecordVersionException} if it could not be found.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the matching d d l record version
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordVersionException if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion findByR_V(long recordId,
		java.lang.String version)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException {
		return getPersistence().findByR_V(recordId, version);
	}

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion fetchByR_V(long recordId,
		java.lang.String version) {
		return getPersistence().fetchByR_V(recordId, version);
	}

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param recordId the record ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion fetchByR_V(long recordId,
		java.lang.String version, boolean retrieveFromCache) {
		return getPersistence().fetchByR_V(recordId, version, retrieveFromCache);
	}

	/**
	* Removes the d d l record version where recordId = &#63; and version = &#63; from the database.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the d d l record version that was removed
	*/
	public static DDLRecordVersion removeByR_V(long recordId,
		java.lang.String version)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException {
		return getPersistence().removeByR_V(recordId, version);
	}

	/**
	* Returns the number of d d l record versions where recordId = &#63; and version = &#63;.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the number of matching d d l record versions
	*/
	public static int countByR_V(long recordId, java.lang.String version) {
		return getPersistence().countByR_V(recordId, version);
	}

	/**
	* Returns all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @return the matching d d l record versions
	*/
	public static List<DDLRecordVersion> findByR_S(long recordId, int status) {
		return getPersistence().findByR_S(recordId, status);
	}

	/**
	* Returns a range of all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of matching d d l record versions
	*/
	public static List<DDLRecordVersion> findByR_S(long recordId, int status,
		int start, int end) {
		return getPersistence().findByR_S(recordId, status, start, end);
	}

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record versions
	*/
	public static List<DDLRecordVersion> findByR_S(long recordId, int status,
		int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return getPersistence()
				   .findByR_S(recordId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d l record versions
	*/
	public static List<DDLRecordVersion> findByR_S(long recordId, int status,
		int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByR_S(recordId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordVersionException if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion findByR_S_First(long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException {
		return getPersistence()
				   .findByR_S_First(recordId, status, orderByComparator);
	}

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion fetchByR_S_First(long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return getPersistence()
				   .fetchByR_S_First(recordId, status, orderByComparator);
	}

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordVersionException if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion findByR_S_Last(long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException {
		return getPersistence()
				   .findByR_S_Last(recordId, status, orderByComparator);
	}

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	*/
	public static DDLRecordVersion fetchByR_S_Last(long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return getPersistence()
				   .fetchByR_S_Last(recordId, status, orderByComparator);
	}

	/**
	* Returns the d d l record versions before and after the current d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordVersionId the primary key of the current d d l record version
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record version
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	*/
	public static DDLRecordVersion[] findByR_S_PrevAndNext(
		long recordVersionId, long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException {
		return getPersistence()
				   .findByR_S_PrevAndNext(recordVersionId, recordId, status,
			orderByComparator);
	}

	/**
	* Removes all the d d l record versions where recordId = &#63; and status = &#63; from the database.
	*
	* @param recordId the record ID
	* @param status the status
	*/
	public static void removeByR_S(long recordId, int status) {
		getPersistence().removeByR_S(recordId, status);
	}

	/**
	* Returns the number of d d l record versions where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @return the number of matching d d l record versions
	*/
	public static int countByR_S(long recordId, int status) {
		return getPersistence().countByR_S(recordId, status);
	}

	/**
	* Caches the d d l record version in the entity cache if it is enabled.
	*
	* @param ddlRecordVersion the d d l record version
	*/
	public static void cacheResult(DDLRecordVersion ddlRecordVersion) {
		getPersistence().cacheResult(ddlRecordVersion);
	}

	/**
	* Caches the d d l record versions in the entity cache if it is enabled.
	*
	* @param ddlRecordVersions the d d l record versions
	*/
	public static void cacheResult(List<DDLRecordVersion> ddlRecordVersions) {
		getPersistence().cacheResult(ddlRecordVersions);
	}

	/**
	* Creates a new d d l record version with the primary key. Does not add the d d l record version to the database.
	*
	* @param recordVersionId the primary key for the new d d l record version
	* @return the new d d l record version
	*/
	public static DDLRecordVersion create(long recordVersionId) {
		return getPersistence().create(recordVersionId);
	}

	/**
	* Removes the d d l record version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version that was removed
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	*/
	public static DDLRecordVersion remove(long recordVersionId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException {
		return getPersistence().remove(recordVersionId);
	}

	public static DDLRecordVersion updateImpl(DDLRecordVersion ddlRecordVersion) {
		return getPersistence().updateImpl(ddlRecordVersion);
	}

	/**
	* Returns the d d l record version with the primary key or throws a {@link com.liferay.dynamic.data.lists.NoSuchRecordVersionException} if it could not be found.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version
	* @throws com.liferay.dynamic.data.lists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	*/
	public static DDLRecordVersion findByPrimaryKey(long recordVersionId)
		throws com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException {
		return getPersistence().findByPrimaryKey(recordVersionId);
	}

	/**
	* Returns the d d l record version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version, or <code>null</code> if a d d l record version with the primary key could not be found
	*/
	public static DDLRecordVersion fetchByPrimaryKey(long recordVersionId) {
		return getPersistence().fetchByPrimaryKey(recordVersionId);
	}

	public static java.util.Map<java.io.Serializable, DDLRecordVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the d d l record versions.
	*
	* @return the d d l record versions
	*/
	public static List<DDLRecordVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of d d l record versions
	*/
	public static List<DDLRecordVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d l record versions
	*/
	public static List<DDLRecordVersion> findAll(int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d l record versions
	*/
	public static List<DDLRecordVersion> findAll(int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d d l record versions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d l record versions.
	*
	* @return the number of d d l record versions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDLRecordVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(DDLRecordVersionPersistence persistence) {
	}

	private static ServiceTracker<DDLRecordVersionPersistence, DDLRecordVersionPersistence> _serviceTracker =
		ServiceTrackerFactory.open(DDLRecordVersionPersistence.class);
}