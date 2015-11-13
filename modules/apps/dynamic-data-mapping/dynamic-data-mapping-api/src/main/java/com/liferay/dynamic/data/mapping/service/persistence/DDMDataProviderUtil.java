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

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMDataProvider;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the d d m data provider service. This utility wraps {@link com.liferay.dynamic.data.mapping.service.persistence.impl.DDMDataProviderPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMDataProviderPersistenceImpl
 * @generated
 */
@ProviderType
public class DDMDataProviderUtil {
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
	public static void clearCache(DDMDataProvider ddmDataProvider) {
		getPersistence().clearCache(ddmDataProvider);
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
	public static List<DDMDataProvider> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMDataProvider> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMDataProvider> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDMDataProvider update(DDMDataProvider ddmDataProvider) {
		return getPersistence().update(ddmDataProvider);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDMDataProvider update(DDMDataProvider ddmDataProvider,
		ServiceContext serviceContext) {
		return getPersistence().update(ddmDataProvider, serviceContext);
	}

	/**
	* Returns all the d d m data providers where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m data providers
	*/
	public static List<DDMDataProvider> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the d d m data providers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the d d m data providers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m data providers where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m data provider in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider findByUuid_First(java.lang.String uuid,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first d d m data provider in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last d d m data provider in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last d d m data provider in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the d d m data providers before and after the current d d m data provider in the ordered set where uuid = &#63;.
	*
	* @param dataProviderId the primary key of the current d d m data provider
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public static DDMDataProvider[] findByUuid_PrevAndNext(
		long dataProviderId, java.lang.String uuid,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence()
				   .findByUuid_PrevAndNext(dataProviderId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the d d m data providers where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of d d m data providers where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m data providers
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the d d m data provider where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchDataProviderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d m data provider where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d m data provider where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the d d m data provider where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m data provider that was removed
	*/
	public static DDMDataProvider removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of d d m data providers where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m data providers
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the d d m data providers where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m data providers
	*/
	public static List<DDMDataProvider> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the d d m data providers where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m data providers where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m data providers where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the d d m data providers before and after the current d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param dataProviderId the primary key of the current d d m data provider
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public static DDMDataProvider[] findByUuid_C_PrevAndNext(
		long dataProviderId, java.lang.String uuid, long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(dataProviderId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the d d m data providers where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of d d m data providers where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m data providers
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the d d m data providers where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m data providers
	*/
	public static List<DDMDataProvider> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the d d m data providers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m data providers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m data providers where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m data provider in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider findByGroupId_First(long groupId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first d d m data provider in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider fetchByGroupId_First(long groupId,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last d d m data provider in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider findByGroupId_Last(long groupId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last d d m data provider in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider fetchByGroupId_Last(long groupId,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the d d m data providers before and after the current d d m data provider in the ordered set where groupId = &#63;.
	*
	* @param dataProviderId the primary key of the current d d m data provider
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public static DDMDataProvider[] findByGroupId_PrevAndNext(
		long dataProviderId, long groupId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(dataProviderId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the d d m data providers where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching d d m data providers
	*/
	public static List<DDMDataProvider> findByGroupId(long[] groupIds) {
		return getPersistence().findByGroupId(groupIds);
	}

	/**
	* Returns a range of all the d d m data providers where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByGroupId(long[] groupIds,
		int start, int end) {
		return getPersistence().findByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the d d m data providers where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByGroupId(long[] groupIds,
		int start, int end, OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m data providers where groupId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByGroupId(long[] groupIds,
		int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Removes all the d d m data providers where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of d d m data providers where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m data providers
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of d d m data providers where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching d d m data providers
	*/
	public static int countByGroupId(long[] groupIds) {
		return getPersistence().countByGroupId(groupIds);
	}

	/**
	* Returns all the d d m data providers where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching d d m data providers
	*/
	public static List<DDMDataProvider> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the d d m data providers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m data providers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m data providers where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m data providers
	*/
	public static List<DDMDataProvider> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m data provider in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider findByCompanyId_First(long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first d d m data provider in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider fetchByCompanyId_First(long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last d d m data provider in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider findByCompanyId_Last(long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last d d m data provider in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static DDMDataProvider fetchByCompanyId_Last(long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the d d m data providers before and after the current d d m data provider in the ordered set where companyId = &#63;.
	*
	* @param dataProviderId the primary key of the current d d m data provider
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public static DDMDataProvider[] findByCompanyId_PrevAndNext(
		long dataProviderId, long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(dataProviderId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the d d m data providers where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of d d m data providers where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching d d m data providers
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Caches the d d m data provider in the entity cache if it is enabled.
	*
	* @param ddmDataProvider the d d m data provider
	*/
	public static void cacheResult(DDMDataProvider ddmDataProvider) {
		getPersistence().cacheResult(ddmDataProvider);
	}

	/**
	* Caches the d d m data providers in the entity cache if it is enabled.
	*
	* @param ddmDataProviders the d d m data providers
	*/
	public static void cacheResult(List<DDMDataProvider> ddmDataProviders) {
		getPersistence().cacheResult(ddmDataProviders);
	}

	/**
	* Creates a new d d m data provider with the primary key. Does not add the d d m data provider to the database.
	*
	* @param dataProviderId the primary key for the new d d m data provider
	* @return the new d d m data provider
	*/
	public static DDMDataProvider create(long dataProviderId) {
		return getPersistence().create(dataProviderId);
	}

	/**
	* Removes the d d m data provider with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider that was removed
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public static DDMDataProvider remove(long dataProviderId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence().remove(dataProviderId);
	}

	public static DDMDataProvider updateImpl(DDMDataProvider ddmDataProvider) {
		return getPersistence().updateImpl(ddmDataProvider);
	}

	/**
	* Returns the d d m data provider with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchDataProviderException} if it could not be found.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider
	* @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	*/
	public static DDMDataProvider findByPrimaryKey(long dataProviderId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException {
		return getPersistence().findByPrimaryKey(dataProviderId);
	}

	/**
	* Returns the d d m data provider with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider, or <code>null</code> if a d d m data provider with the primary key could not be found
	*/
	public static DDMDataProvider fetchByPrimaryKey(long dataProviderId) {
		return getPersistence().fetchByPrimaryKey(dataProviderId);
	}

	public static java.util.Map<java.io.Serializable, DDMDataProvider> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the d d m data providers.
	*
	* @return the d d m data providers
	*/
	public static List<DDMDataProvider> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d m data providers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of d d m data providers
	*/
	public static List<DDMDataProvider> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d m data providers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m data providers
	*/
	public static List<DDMDataProvider> findAll(int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m data providers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m data providers
	*/
	public static List<DDMDataProvider> findAll(int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d d m data providers from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d m data providers.
	*
	* @return the number of d d m data providers
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DDMDataProviderPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(DDMDataProviderPersistence persistence) {
	}

	private static ServiceTracker<DDMDataProviderPersistence, DDMDataProviderPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMDataProviderUtil.class);

		_serviceTracker = new ServiceTracker<DDMDataProviderPersistence, DDMDataProviderPersistence>(bundle.getBundleContext(),
				DDMDataProviderPersistence.class, null);

		_serviceTracker.open();
	}
}