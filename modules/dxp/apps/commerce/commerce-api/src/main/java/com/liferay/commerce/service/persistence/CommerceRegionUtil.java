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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceRegion;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce region service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceRegionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceRegionPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceRegionUtil {
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
	public static void clearCache(CommerceRegion commerceRegion) {
		getPersistence().clearCache(commerceRegion);
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
	public static List<CommerceRegion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceRegion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceRegion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceRegion update(CommerceRegion commerceRegion) {
		return getPersistence().update(commerceRegion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceRegion update(CommerceRegion commerceRegion,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceRegion, serviceContext);
	}

	/**
	* Returns all the commerce regions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce regions
	*/
	public static List<CommerceRegion> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce regions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @return the range of matching commerce regions
	*/
	public static List<CommerceRegion> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce regions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce regions
	*/
	public static List<CommerceRegion> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce regions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce regions
	*/
	public static List<CommerceRegion> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce region in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce region in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce region in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce region in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce regions before and after the current commerce region in the ordered set where uuid = &#63;.
	*
	* @param commerceRegionId the primary key of the current commerce region
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce region
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion[] findByUuid_PrevAndNext(
		long commerceRegionId, java.lang.String uuid,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceRegionId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce regions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce regions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce regions
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce region where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchRegionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce region where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce region where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce region where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce region that was removed
	*/
	public static CommerceRegion removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce regions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce regions
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce regions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce regions
	*/
	public static List<CommerceRegion> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce regions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @return the range of matching commerce regions
	*/
	public static List<CommerceRegion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce regions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce regions
	*/
	public static List<CommerceRegion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce regions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce regions
	*/
	public static List<CommerceRegion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce region in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce region in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce region in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce region in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce regions before and after the current commerce region in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceRegionId the primary key of the current commerce region
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce region
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion[] findByUuid_C_PrevAndNext(
		long commerceRegionId, java.lang.String uuid, long companyId,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceRegionId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the commerce regions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce regions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce regions
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce regions where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce regions
	*/
	public static List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId) {
		return getPersistence().findByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns a range of all the commerce regions where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @return the range of matching commerce regions
	*/
	public static List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce regions where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce regions
	*/
	public static List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce regions where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce regions
	*/
	public static List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence()
				   .findByCommerceCountryId_First(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCountryId_First(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence()
				   .findByCommerceCountryId_Last(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceCountryId_Last(commerceCountryId,
			orderByComparator);
	}

	/**
	* Returns the commerce regions before and after the current commerce region in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceRegionId the primary key of the current commerce region
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce region
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion[] findByCommerceCountryId_PrevAndNext(
		long commerceRegionId, long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence()
				   .findByCommerceCountryId_PrevAndNext(commerceRegionId,
			commerceCountryId, orderByComparator);
	}

	/**
	* Removes all the commerce regions where commerceCountryId = &#63; from the database.
	*
	* @param commerceCountryId the commerce country ID
	*/
	public static void removeByCommerceCountryId(long commerceCountryId) {
		getPersistence().removeByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns the number of commerce regions where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce regions
	*/
	public static int countByCommerceCountryId(long commerceCountryId) {
		return getPersistence().countByCommerceCountryId(commerceCountryId);
	}

	/**
	* Returns all the commerce regions where commerceCountryId = &#63; and active = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param active the active
	* @return the matching commerce regions
	*/
	public static List<CommerceRegion> findByC_A(long commerceCountryId,
		boolean active) {
		return getPersistence().findByC_A(commerceCountryId, active);
	}

	/**
	* Returns a range of all the commerce regions where commerceCountryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param active the active
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @return the range of matching commerce regions
	*/
	public static List<CommerceRegion> findByC_A(long commerceCountryId,
		boolean active, int start, int end) {
		return getPersistence().findByC_A(commerceCountryId, active, start, end);
	}

	/**
	* Returns an ordered range of all the commerce regions where commerceCountryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param active the active
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce regions
	*/
	public static List<CommerceRegion> findByC_A(long commerceCountryId,
		boolean active, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .findByC_A(commerceCountryId, active, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce regions where commerceCountryId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param active the active
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce regions
	*/
	public static List<CommerceRegion> findByC_A(long commerceCountryId,
		boolean active, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_A(commerceCountryId, active, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce region in the ordered set where commerceCountryId = &#63; and active = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByC_A_First(long commerceCountryId,
		boolean active, OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence()
				   .findByC_A_First(commerceCountryId, active, orderByComparator);
	}

	/**
	* Returns the first commerce region in the ordered set where commerceCountryId = &#63; and active = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByC_A_First(long commerceCountryId,
		boolean active, OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .fetchByC_A_First(commerceCountryId, active,
			orderByComparator);
	}

	/**
	* Returns the last commerce region in the ordered set where commerceCountryId = &#63; and active = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region
	* @throws NoSuchRegionException if a matching commerce region could not be found
	*/
	public static CommerceRegion findByC_A_Last(long commerceCountryId,
		boolean active, OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence()
				   .findByC_A_Last(commerceCountryId, active, orderByComparator);
	}

	/**
	* Returns the last commerce region in the ordered set where commerceCountryId = &#63; and active = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static CommerceRegion fetchByC_A_Last(long commerceCountryId,
		boolean active, OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence()
				   .fetchByC_A_Last(commerceCountryId, active, orderByComparator);
	}

	/**
	* Returns the commerce regions before and after the current commerce region in the ordered set where commerceCountryId = &#63; and active = &#63;.
	*
	* @param commerceRegionId the primary key of the current commerce region
	* @param commerceCountryId the commerce country ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce region
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion[] findByC_A_PrevAndNext(
		long commerceRegionId, long commerceCountryId, boolean active,
		OrderByComparator<CommerceRegion> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence()
				   .findByC_A_PrevAndNext(commerceRegionId, commerceCountryId,
			active, orderByComparator);
	}

	/**
	* Removes all the commerce regions where commerceCountryId = &#63; and active = &#63; from the database.
	*
	* @param commerceCountryId the commerce country ID
	* @param active the active
	*/
	public static void removeByC_A(long commerceCountryId, boolean active) {
		getPersistence().removeByC_A(commerceCountryId, active);
	}

	/**
	* Returns the number of commerce regions where commerceCountryId = &#63; and active = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param active the active
	* @return the number of matching commerce regions
	*/
	public static int countByC_A(long commerceCountryId, boolean active) {
		return getPersistence().countByC_A(commerceCountryId, active);
	}

	/**
	* Caches the commerce region in the entity cache if it is enabled.
	*
	* @param commerceRegion the commerce region
	*/
	public static void cacheResult(CommerceRegion commerceRegion) {
		getPersistence().cacheResult(commerceRegion);
	}

	/**
	* Caches the commerce regions in the entity cache if it is enabled.
	*
	* @param commerceRegions the commerce regions
	*/
	public static void cacheResult(List<CommerceRegion> commerceRegions) {
		getPersistence().cacheResult(commerceRegions);
	}

	/**
	* Creates a new commerce region with the primary key. Does not add the commerce region to the database.
	*
	* @param commerceRegionId the primary key for the new commerce region
	* @return the new commerce region
	*/
	public static CommerceRegion create(long commerceRegionId) {
		return getPersistence().create(commerceRegionId);
	}

	/**
	* Removes the commerce region with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region that was removed
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion remove(long commerceRegionId)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence().remove(commerceRegionId);
	}

	public static CommerceRegion updateImpl(CommerceRegion commerceRegion) {
		return getPersistence().updateImpl(commerceRegion);
	}

	/**
	* Returns the commerce region with the primary key or throws a {@link NoSuchRegionException} if it could not be found.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region
	* @throws NoSuchRegionException if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion findByPrimaryKey(long commerceRegionId)
		throws com.liferay.commerce.exception.NoSuchRegionException {
		return getPersistence().findByPrimaryKey(commerceRegionId);
	}

	/**
	* Returns the commerce region with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region, or <code>null</code> if a commerce region with the primary key could not be found
	*/
	public static CommerceRegion fetchByPrimaryKey(long commerceRegionId) {
		return getPersistence().fetchByPrimaryKey(commerceRegionId);
	}

	public static java.util.Map<java.io.Serializable, CommerceRegion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce regions.
	*
	* @return the commerce regions
	*/
	public static List<CommerceRegion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @return the range of commerce regions
	*/
	public static List<CommerceRegion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce regions
	*/
	public static List<CommerceRegion> findAll(int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce regions
	*/
	public static List<CommerceRegion> findAll(int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce regions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce regions.
	*
	* @return the number of commerce regions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceRegionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceRegionPersistence, CommerceRegionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceRegionPersistence.class);

		ServiceTracker<CommerceRegionPersistence, CommerceRegionPersistence> serviceTracker =
			new ServiceTracker<CommerceRegionPersistence, CommerceRegionPersistence>(bundle.getBundleContext(),
				CommerceRegionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}