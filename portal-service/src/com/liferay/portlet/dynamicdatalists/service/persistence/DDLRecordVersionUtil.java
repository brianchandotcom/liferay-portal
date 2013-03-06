/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;

import java.util.List;

/**
 * The persistence utility for the d d l record version service. This utility wraps {@link DDLRecordVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionPersistence
 * @see DDLRecordVersionPersistenceImpl
 * @generated
 */
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
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDLRecordVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDLRecordVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDLRecordVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDLRecordVersion update(DDLRecordVersion ddlRecordVersion)
		throws SystemException {
		return getPersistence().update(ddlRecordVersion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDLRecordVersion update(DDLRecordVersion ddlRecordVersion,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(ddlRecordVersion, serviceContext);
	}

	/**
	* Returns all the d d l record versions where recordId = &#63;.
	*
	* @param recordId the record ID
	* @return the matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByRecordId(
		long recordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRecordId(recordId);
	}

	/**
	* Returns a range of all the d d l record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByRecordId(
		long recordId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRecordId(recordId, start, end);
	}

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByRecordId(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRecordId(recordId, start, end, orderByComparator);
	}

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByRecordId_First(
		long recordId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException {
		return getPersistence().findByRecordId_First(recordId, orderByComparator);
	}

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchByRecordId_First(
		long recordId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByRecordId_First(recordId, orderByComparator);
	}

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByRecordId_Last(
		long recordId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException {
		return getPersistence().findByRecordId_Last(recordId, orderByComparator);
	}

	/**
	* Returns the last d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchByRecordId_Last(
		long recordId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByRecordId_Last(recordId, orderByComparator);
	}

	/**
	* Returns the d d l record versions before and after the current d d l record version in the ordered set where recordId = &#63;.
	*
	* @param recordVersionId the primary key of the current d d l record version
	* @param recordId the record ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion[] findByRecordId_PrevAndNext(
		long recordVersionId, long recordId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException {
		return getPersistence()
				   .findByRecordId_PrevAndNext(recordVersionId, recordId,
			orderByComparator);
	}

	/**
	* Removes all the d d l record versions where recordId = &#63; from the database.
	*
	* @param recordId the record ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByRecordId(long recordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRecordId(recordId);
	}

	/**
	* Returns the number of d d l record versions where recordId = &#63;.
	*
	* @param recordId the record ID
	* @return the number of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRecordId(long recordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRecordId(recordId);
	}

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException} if it could not be found.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the matching d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByR_V(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException {
		return getPersistence().findByR_V(recordId, version);
	}

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchByR_V(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByR_V(recordId, version);
	}

	/**
	* Returns the d d l record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param recordId the record ID
	* @param version the version
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d l record version, or <code>null</code> if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchByR_V(
		long recordId, java.lang.String version, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByR_V(recordId, version, retrieveFromCache);
	}

	/**
	* Removes the d d l record version where recordId = &#63; and version = &#63; from the database.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the d d l record version that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion removeByR_V(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException {
		return getPersistence().removeByR_V(recordId, version);
	}

	/**
	* Returns the number of d d l record versions where recordId = &#63; and version = &#63;.
	*
	* @param recordId the record ID
	* @param version the version
	* @return the number of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByR_V(long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_V(recordId, version);
	}

	/**
	* Returns all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @return the matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByR_S(
		long recordId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_S(recordId, status);
	}

	/**
	* Returns a range of all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByR_S(
		long recordId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_S(recordId, status, start, end);
	}

	/**
	* Returns an ordered range of all the d d l record versions where recordId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param recordId the record ID
	* @param status the status
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findByR_S(
		long recordId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_S(recordId, status, start, end, orderByComparator);
	}

	/**
	* Returns the first d d l record version in the ordered set where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByR_S_First(
		long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchByR_S_First(
		long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a matching d d l record version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByR_S_Last(
		long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchByR_S_Last(
		long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion[] findByR_S_PrevAndNext(
		long recordVersionId, long recordId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException {
		return getPersistence()
				   .findByR_S_PrevAndNext(recordVersionId, recordId, status,
			orderByComparator);
	}

	/**
	* Removes all the d d l record versions where recordId = &#63; and status = &#63; from the database.
	*
	* @param recordId the record ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByR_S(long recordId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByR_S(recordId, status);
	}

	/**
	* Returns the number of d d l record versions where recordId = &#63; and status = &#63;.
	*
	* @param recordId the record ID
	* @param status the status
	* @return the number of matching d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByR_S(long recordId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_S(recordId, status);
	}

	/**
	* Caches the d d l record version in the entity cache if it is enabled.
	*
	* @param ddlRecordVersion the d d l record version
	*/
	public static void cacheResult(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion ddlRecordVersion) {
		getPersistence().cacheResult(ddlRecordVersion);
	}

	/**
	* Caches the d d l record versions in the entity cache if it is enabled.
	*
	* @param ddlRecordVersions the d d l record versions
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> ddlRecordVersions) {
		getPersistence().cacheResult(ddlRecordVersions);
	}

	/**
	* Creates a new d d l record version with the primary key. Does not add the d d l record version to the database.
	*
	* @param recordVersionId the primary key for the new d d l record version
	* @return the new d d l record version
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion create(
		long recordVersionId) {
		return getPersistence().create(recordVersionId);
	}

	/**
	* Removes the d d l record version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version that was removed
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion remove(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException {
		return getPersistence().remove(recordVersionId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion updateImpl(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion ddlRecordVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(ddlRecordVersion);
	}

	/**
	* Returns the d d l record version with the primary key or throws a {@link com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException} if it could not be found.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version
	* @throws com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException if a d d l record version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion findByPrimaryKey(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException {
		return getPersistence().findByPrimaryKey(recordVersionId);
	}

	/**
	* Returns the d d l record version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param recordVersionId the primary key of the d d l record version
	* @return the d d l record version, or <code>null</code> if a d d l record version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion fetchByPrimaryKey(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(recordVersionId);
	}

	/**
	* Returns all the d d l record versions.
	*
	* @return the d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @return the range of d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d l record versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record versions
	* @param end the upper bound of the range of d d l record versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d d l record versions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d l record versions.
	*
	* @return the number of d d l record versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DDLRecordVersionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DDLRecordVersionPersistence)PortalBeanLocatorUtil.locate(DDLRecordVersionPersistence.class.getName());

			ReferenceRegistry.registerReference(DDLRecordVersionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(DDLRecordVersionPersistence persistence) {
	}

	private static DDLRecordVersionPersistence _persistence;
}