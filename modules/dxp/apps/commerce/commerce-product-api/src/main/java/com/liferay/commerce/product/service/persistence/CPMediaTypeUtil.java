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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPMediaType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp media type service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPMediaTypePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPMediaTypePersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPMediaTypePersistenceImpl
 * @generated
 */
@ProviderType
public class CPMediaTypeUtil {
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
	public static void clearCache(CPMediaType cpMediaType) {
		getPersistence().clearCache(cpMediaType);
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
	public static List<CPMediaType> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPMediaType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPMediaType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPMediaType> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPMediaType update(CPMediaType cpMediaType) {
		return getPersistence().update(cpMediaType);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPMediaType update(CPMediaType cpMediaType,
		ServiceContext serviceContext) {
		return getPersistence().update(cpMediaType, serviceContext);
	}

	/**
	* Returns all the cp media types where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp media types
	*/
	public static List<CPMediaType> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp media types where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @return the range of matching cp media types
	*/
	public static List<CPMediaType> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp media types where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp media types
	*/
	public static List<CPMediaType> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<CPMediaType> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp media types where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp media types
	*/
	public static List<CPMediaType> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<CPMediaType> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp media type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public static CPMediaType findByUuid_First(java.lang.String uuid,
		OrderByComparator<CPMediaType> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMediaTypeException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp media type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public static CPMediaType fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CPMediaType> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp media type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public static CPMediaType findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPMediaType> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMediaTypeException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp media type in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public static CPMediaType fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPMediaType> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp media types before and after the current cp media type in the ordered set where uuid = &#63;.
	*
	* @param CPMediaTypeId the primary key of the current cp media type
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp media type
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public static CPMediaType[] findByUuid_PrevAndNext(long CPMediaTypeId,
		java.lang.String uuid, OrderByComparator<CPMediaType> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMediaTypeException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPMediaTypeId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp media types where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp media types where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp media types
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp media type where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPMediaTypeException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public static CPMediaType findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPMediaTypeException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp media type where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public static CPMediaType fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp media type where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public static CPMediaType fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp media type where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp media type that was removed
	*/
	public static CPMediaType removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPMediaTypeException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp media types where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp media types
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp media types where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp media types
	*/
	public static List<CPMediaType> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp media types where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @return the range of matching cp media types
	*/
	public static List<CPMediaType> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp media types where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp media types
	*/
	public static List<CPMediaType> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPMediaType> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp media types where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp media types
	*/
	public static List<CPMediaType> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPMediaType> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp media type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public static CPMediaType findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPMediaType> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMediaTypeException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp media type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public static CPMediaType fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPMediaType> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp media type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type
	* @throws NoSuchCPMediaTypeException if a matching cp media type could not be found
	*/
	public static CPMediaType findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPMediaType> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMediaTypeException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp media type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	public static CPMediaType fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPMediaType> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp media types before and after the current cp media type in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPMediaTypeId the primary key of the current cp media type
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp media type
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public static CPMediaType[] findByUuid_C_PrevAndNext(long CPMediaTypeId,
		java.lang.String uuid, long companyId,
		OrderByComparator<CPMediaType> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPMediaTypeException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPMediaTypeId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the cp media types where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp media types where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp media types
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Caches the cp media type in the entity cache if it is enabled.
	*
	* @param cpMediaType the cp media type
	*/
	public static void cacheResult(CPMediaType cpMediaType) {
		getPersistence().cacheResult(cpMediaType);
	}

	/**
	* Caches the cp media types in the entity cache if it is enabled.
	*
	* @param cpMediaTypes the cp media types
	*/
	public static void cacheResult(List<CPMediaType> cpMediaTypes) {
		getPersistence().cacheResult(cpMediaTypes);
	}

	/**
	* Creates a new cp media type with the primary key. Does not add the cp media type to the database.
	*
	* @param CPMediaTypeId the primary key for the new cp media type
	* @return the new cp media type
	*/
	public static CPMediaType create(long CPMediaTypeId) {
		return getPersistence().create(CPMediaTypeId);
	}

	/**
	* Removes the cp media type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPMediaTypeId the primary key of the cp media type
	* @return the cp media type that was removed
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public static CPMediaType remove(long CPMediaTypeId)
		throws com.liferay.commerce.product.exception.NoSuchCPMediaTypeException {
		return getPersistence().remove(CPMediaTypeId);
	}

	public static CPMediaType updateImpl(CPMediaType cpMediaType) {
		return getPersistence().updateImpl(cpMediaType);
	}

	/**
	* Returns the cp media type with the primary key or throws a {@link NoSuchCPMediaTypeException} if it could not be found.
	*
	* @param CPMediaTypeId the primary key of the cp media type
	* @return the cp media type
	* @throws NoSuchCPMediaTypeException if a cp media type with the primary key could not be found
	*/
	public static CPMediaType findByPrimaryKey(long CPMediaTypeId)
		throws com.liferay.commerce.product.exception.NoSuchCPMediaTypeException {
		return getPersistence().findByPrimaryKey(CPMediaTypeId);
	}

	/**
	* Returns the cp media type with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPMediaTypeId the primary key of the cp media type
	* @return the cp media type, or <code>null</code> if a cp media type with the primary key could not be found
	*/
	public static CPMediaType fetchByPrimaryKey(long CPMediaTypeId) {
		return getPersistence().fetchByPrimaryKey(CPMediaTypeId);
	}

	public static java.util.Map<java.io.Serializable, CPMediaType> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp media types.
	*
	* @return the cp media types
	*/
	public static List<CPMediaType> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp media types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @return the range of cp media types
	*/
	public static List<CPMediaType> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp media types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp media types
	*/
	public static List<CPMediaType> findAll(int start, int end,
		OrderByComparator<CPMediaType> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp media types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp media types
	*/
	public static List<CPMediaType> findAll(int start, int end,
		OrderByComparator<CPMediaType> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp media types from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp media types.
	*
	* @return the number of cp media types
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPMediaTypePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPMediaTypePersistence, CPMediaTypePersistence> _serviceTracker =
		ServiceTrackerFactory.open(CPMediaTypePersistence.class);
}