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

import com.liferay.commerce.model.CommerceTierPriceEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce tier price entry service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceTierPriceEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntryPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceTierPriceEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceTierPriceEntryUtil {
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
	public static void clearCache(CommerceTierPriceEntry commerceTierPriceEntry) {
		getPersistence().clearCache(commerceTierPriceEntry);
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
	public static List<CommerceTierPriceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceTierPriceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceTierPriceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceTierPriceEntry update(
		CommerceTierPriceEntry commerceTierPriceEntry) {
		return getPersistence().update(commerceTierPriceEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceTierPriceEntry update(
		CommerceTierPriceEntry commerceTierPriceEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceTierPriceEntry, serviceContext);
	}

	/**
	* Returns all the commerce tier price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce tier price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where uuid = &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public static CommerceTierPriceEntry[] findByUuid_PrevAndNext(
		long commerceTierPriceEntryId, java.lang.String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commerceTierPriceEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce tier price entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce tier price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce tier price entries
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce tier price entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchTierPriceEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce tier price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce tier price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce tier price entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce tier price entry that was removed
	*/
	public static CommerceTierPriceEntry removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce tier price entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce tier price entries
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public static CommerceTierPriceEntry[] findByUuid_C_PrevAndNext(
		long commerceTierPriceEntryId, java.lang.String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commerceTierPriceEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce tier price entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce tier price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce tier price entries
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce tier price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce tier price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByGroupId_First(long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByGroupId_Last(long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where groupId = &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public static CommerceTierPriceEntry[] findByGroupId_PrevAndNext(
		long commerceTierPriceEntryId, long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceTierPriceEntryId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce tier price entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce tier price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce tier price entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce tier price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce tier price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByCompanyId_First(long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where companyId = &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public static CommerceTierPriceEntry[] findByCompanyId_PrevAndNext(
		long commerceTierPriceEntryId, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(commerceTierPriceEntryId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce tier price entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce tier price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce tier price entries
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the commerce tier price entries where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @return the matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId) {
		return getPersistence().findByCommercePriceEntryId(commercePriceEntryId);
	}

	/**
	* Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end) {
		return getPersistence()
				   .findByCommercePriceEntryId(commercePriceEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .findByCommercePriceEntryId(commercePriceEntryId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommercePriceEntryId(commercePriceEntryId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByCommercePriceEntryId_First(
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByCommercePriceEntryId_First(commercePriceEntryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByCommercePriceEntryId_First(
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceEntryId_First(commercePriceEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByCommercePriceEntryId_Last(commercePriceEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceEntryId_Last(commercePriceEntryId,
			orderByComparator);
	}

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public static CommerceTierPriceEntry[] findByCommercePriceEntryId_PrevAndNext(
		long commerceTierPriceEntryId, long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByCommercePriceEntryId_PrevAndNext(commerceTierPriceEntryId,
			commercePriceEntryId, orderByComparator);
	}

	/**
	* Removes all the commerce tier price entries where commercePriceEntryId = &#63; from the database.
	*
	* @param commercePriceEntryId the commerce price entry ID
	*/
	public static void removeByCommercePriceEntryId(long commercePriceEntryId) {
		getPersistence().removeByCommercePriceEntryId(commercePriceEntryId);
	}

	/**
	* Returns the number of commerce tier price entries where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @return the number of matching commerce tier price entries
	*/
	public static int countByCommercePriceEntryId(long commercePriceEntryId) {
		return getPersistence().countByCommercePriceEntryId(commercePriceEntryId);
	}

	/**
	* Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or throws a {@link NoSuchTierPriceEntryException} if it could not be found.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByC_M(long commercePriceEntryId,
		int minQuantity)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence().findByC_M(commercePriceEntryId, minQuantity);
	}

	/**
	* Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByC_M(long commercePriceEntryId,
		int minQuantity) {
		return getPersistence().fetchByC_M(commercePriceEntryId, minQuantity);
	}

	/**
	* Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByC_M(long commercePriceEntryId,
		int minQuantity, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_M(commercePriceEntryId, minQuantity,
			retrieveFromCache);
	}

	/**
	* Removes the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; from the database.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the commerce tier price entry that was removed
	*/
	public static CommerceTierPriceEntry removeByC_M(
		long commercePriceEntryId, int minQuantity)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence().removeByC_M(commercePriceEntryId, minQuantity);
	}

	/**
	* Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the number of matching commerce tier price entries
	*/
	public static int countByC_M(long commercePriceEntryId, int minQuantity) {
		return getPersistence().countByC_M(commercePriceEntryId, minQuantity);
	}

	/**
	* Returns all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByC_M2(
		long commercePriceEntryId, int minQuantity) {
		return getPersistence().findByC_M2(commercePriceEntryId, minQuantity);
	}

	/**
	* Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByC_M2(
		long commercePriceEntryId, int minQuantity, int start, int end) {
		return getPersistence()
				   .findByC_M2(commercePriceEntryId, minQuantity, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByC_M2(
		long commercePriceEntryId, int minQuantity, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .findByC_M2(commercePriceEntryId, minQuantity, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findByC_M2(
		long commercePriceEntryId, int minQuantity, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_M2(commercePriceEntryId, minQuantity, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByC_M2_First(
		long commercePriceEntryId, int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByC_M2_First(commercePriceEntryId, minQuantity,
			orderByComparator);
	}

	/**
	* Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByC_M2_First(
		long commercePriceEntryId, int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_M2_First(commercePriceEntryId, minQuantity,
			orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry findByC_M2_Last(
		long commercePriceEntryId, int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByC_M2_Last(commercePriceEntryId, minQuantity,
			orderByComparator);
	}

	/**
	* Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public static CommerceTierPriceEntry fetchByC_M2_Last(
		long commercePriceEntryId, int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_M2_Last(commercePriceEntryId, minQuantity,
			orderByComparator);
	}

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public static CommerceTierPriceEntry[] findByC_M2_PrevAndNext(
		long commerceTierPriceEntryId, long commercePriceEntryId,
		int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence()
				   .findByC_M2_PrevAndNext(commerceTierPriceEntryId,
			commercePriceEntryId, minQuantity, orderByComparator);
	}

	/**
	* Removes all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; from the database.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	*/
	public static void removeByC_M2(long commercePriceEntryId, int minQuantity) {
		getPersistence().removeByC_M2(commercePriceEntryId, minQuantity);
	}

	/**
	* Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the number of matching commerce tier price entries
	*/
	public static int countByC_M2(long commercePriceEntryId, int minQuantity) {
		return getPersistence().countByC_M2(commercePriceEntryId, minQuantity);
	}

	/**
	* Caches the commerce tier price entry in the entity cache if it is enabled.
	*
	* @param commerceTierPriceEntry the commerce tier price entry
	*/
	public static void cacheResult(
		CommerceTierPriceEntry commerceTierPriceEntry) {
		getPersistence().cacheResult(commerceTierPriceEntry);
	}

	/**
	* Caches the commerce tier price entries in the entity cache if it is enabled.
	*
	* @param commerceTierPriceEntries the commerce tier price entries
	*/
	public static void cacheResult(
		List<CommerceTierPriceEntry> commerceTierPriceEntries) {
		getPersistence().cacheResult(commerceTierPriceEntries);
	}

	/**
	* Creates a new commerce tier price entry with the primary key. Does not add the commerce tier price entry to the database.
	*
	* @param commerceTierPriceEntryId the primary key for the new commerce tier price entry
	* @return the new commerce tier price entry
	*/
	public static CommerceTierPriceEntry create(long commerceTierPriceEntryId) {
		return getPersistence().create(commerceTierPriceEntryId);
	}

	/**
	* Removes the commerce tier price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	* @return the commerce tier price entry that was removed
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public static CommerceTierPriceEntry remove(long commerceTierPriceEntryId)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence().remove(commerceTierPriceEntryId);
	}

	public static CommerceTierPriceEntry updateImpl(
		CommerceTierPriceEntry commerceTierPriceEntry) {
		return getPersistence().updateImpl(commerceTierPriceEntry);
	}

	/**
	* Returns the commerce tier price entry with the primary key or throws a {@link NoSuchTierPriceEntryException} if it could not be found.
	*
	* @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	* @return the commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public static CommerceTierPriceEntry findByPrimaryKey(
		long commerceTierPriceEntryId)
		throws com.liferay.commerce.exception.NoSuchTierPriceEntryException {
		return getPersistence().findByPrimaryKey(commerceTierPriceEntryId);
	}

	/**
	* Returns the commerce tier price entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	* @return the commerce tier price entry, or <code>null</code> if a commerce tier price entry with the primary key could not be found
	*/
	public static CommerceTierPriceEntry fetchByPrimaryKey(
		long commerceTierPriceEntryId) {
		return getPersistence().fetchByPrimaryKey(commerceTierPriceEntryId);
	}

	public static java.util.Map<java.io.Serializable, CommerceTierPriceEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce tier price entries.
	*
	* @return the commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce tier price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findAll(int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tier price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce tier price entries
	*/
	public static List<CommerceTierPriceEntry> findAll(int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce tier price entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce tier price entries.
	*
	* @return the number of commerce tier price entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceTierPriceEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTierPriceEntryPersistence, CommerceTierPriceEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTierPriceEntryPersistence.class);

		ServiceTracker<CommerceTierPriceEntryPersistence, CommerceTierPriceEntryPersistence> serviceTracker =
			new ServiceTracker<CommerceTierPriceEntryPersistence, CommerceTierPriceEntryPersistence>(bundle.getBundleContext(),
				CommerceTierPriceEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}