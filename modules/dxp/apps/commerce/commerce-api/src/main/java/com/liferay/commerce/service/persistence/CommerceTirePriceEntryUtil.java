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

import com.liferay.commerce.model.CommerceTirePriceEntry;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce tire price entry service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceTirePriceEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTirePriceEntryPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceTirePriceEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceTirePriceEntryUtil {
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
	public static void clearCache(CommerceTirePriceEntry commerceTirePriceEntry) {
		getPersistence().clearCache(commerceTirePriceEntry);
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
	public static List<CommerceTirePriceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceTirePriceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceTirePriceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceTirePriceEntry update(
		CommerceTirePriceEntry commerceTirePriceEntry) {
		return getPersistence().update(commerceTirePriceEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceTirePriceEntry update(
		CommerceTirePriceEntry commerceTirePriceEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(commerceTirePriceEntry, serviceContext);
	}

	/**
	* Returns all the commerce tire price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce tire price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce tire price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce tire price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce tire price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce tire price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where uuid = &#63;.
	*
	* @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public static CommerceTirePriceEntry[] findByUuid_PrevAndNext(
		long CommerceTirePriceEntryId, java.lang.String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CommerceTirePriceEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce tire price entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce tire price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce tire price entries
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce tire price entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchTirePriceEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce tire price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce tire price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce tire price entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce tire price entry that was removed
	*/
	public static CommerceTirePriceEntry removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce tire price entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce tire price entries
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public static CommerceTirePriceEntry[] findByUuid_C_PrevAndNext(
		long CommerceTirePriceEntryId, java.lang.String uuid, long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CommerceTirePriceEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce tire price entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce tire price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce tire price entries
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce tire price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce tire price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce tire price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByGroupId_First(long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce tire price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce tire price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByGroupId_Last(long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce tire price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where groupId = &#63;.
	*
	* @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public static CommerceTirePriceEntry[] findByGroupId_PrevAndNext(
		long CommerceTirePriceEntryId, long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CommerceTirePriceEntryId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce tire price entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce tire price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce tire price entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce tire price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce tire price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce tire price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByCompanyId_First(long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce tire price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce tire price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce tire price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where companyId = &#63;.
	*
	* @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public static CommerceTirePriceEntry[] findByCompanyId_PrevAndNext(
		long CommerceTirePriceEntryId, long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(CommerceTirePriceEntryId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce tire price entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce tire price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce tire price entries
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the commerce tire price entries where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @return the matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId) {
		return getPersistence().findByCommercePriceEntryId(commercePriceEntryId);
	}

	/**
	* Returns a range of all the commerce tire price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end) {
		return getPersistence()
				   .findByCommercePriceEntryId(commercePriceEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .findByCommercePriceEntryId(commercePriceEntryId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommercePriceEntryId(commercePriceEntryId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByCommercePriceEntryId_First(
		long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByCommercePriceEntryId_First(commercePriceEntryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByCommercePriceEntryId_First(
		long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceEntryId_First(commercePriceEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry findByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByCommercePriceEntryId_Last(commercePriceEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	public static CommerceTirePriceEntry fetchByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceEntryId_Last(commercePriceEntryId,
			orderByComparator);
	}

	/**
	* Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public static CommerceTirePriceEntry[] findByCommercePriceEntryId_PrevAndNext(
		long CommerceTirePriceEntryId, long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence()
				   .findByCommercePriceEntryId_PrevAndNext(CommerceTirePriceEntryId,
			commercePriceEntryId, orderByComparator);
	}

	/**
	* Removes all the commerce tire price entries where commercePriceEntryId = &#63; from the database.
	*
	* @param commercePriceEntryId the commerce price entry ID
	*/
	public static void removeByCommercePriceEntryId(long commercePriceEntryId) {
		getPersistence().removeByCommercePriceEntryId(commercePriceEntryId);
	}

	/**
	* Returns the number of commerce tire price entries where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @return the number of matching commerce tire price entries
	*/
	public static int countByCommercePriceEntryId(long commercePriceEntryId) {
		return getPersistence().countByCommercePriceEntryId(commercePriceEntryId);
	}

	/**
	* Caches the commerce tire price entry in the entity cache if it is enabled.
	*
	* @param commerceTirePriceEntry the commerce tire price entry
	*/
	public static void cacheResult(
		CommerceTirePriceEntry commerceTirePriceEntry) {
		getPersistence().cacheResult(commerceTirePriceEntry);
	}

	/**
	* Caches the commerce tire price entries in the entity cache if it is enabled.
	*
	* @param commerceTirePriceEntries the commerce tire price entries
	*/
	public static void cacheResult(
		List<CommerceTirePriceEntry> commerceTirePriceEntries) {
		getPersistence().cacheResult(commerceTirePriceEntries);
	}

	/**
	* Creates a new commerce tire price entry with the primary key. Does not add the commerce tire price entry to the database.
	*
	* @param CommerceTirePriceEntryId the primary key for the new commerce tire price entry
	* @return the new commerce tire price entry
	*/
	public static CommerceTirePriceEntry create(long CommerceTirePriceEntryId) {
		return getPersistence().create(CommerceTirePriceEntryId);
	}

	/**
	* Removes the commerce tire price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	* @return the commerce tire price entry that was removed
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public static CommerceTirePriceEntry remove(long CommerceTirePriceEntryId)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence().remove(CommerceTirePriceEntryId);
	}

	public static CommerceTirePriceEntry updateImpl(
		CommerceTirePriceEntry commerceTirePriceEntry) {
		return getPersistence().updateImpl(commerceTirePriceEntry);
	}

	/**
	* Returns the commerce tire price entry with the primary key or throws a {@link NoSuchTirePriceEntryException} if it could not be found.
	*
	* @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	* @return the commerce tire price entry
	* @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	*/
	public static CommerceTirePriceEntry findByPrimaryKey(
		long CommerceTirePriceEntryId)
		throws com.liferay.commerce.exception.NoSuchTirePriceEntryException {
		return getPersistence().findByPrimaryKey(CommerceTirePriceEntryId);
	}

	/**
	* Returns the commerce tire price entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	* @return the commerce tire price entry, or <code>null</code> if a commerce tire price entry with the primary key could not be found
	*/
	public static CommerceTirePriceEntry fetchByPrimaryKey(
		long CommerceTirePriceEntryId) {
		return getPersistence().fetchByPrimaryKey(CommerceTirePriceEntryId);
	}

	public static java.util.Map<java.io.Serializable, CommerceTirePriceEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce tire price entries.
	*
	* @return the commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce tire price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findAll(int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce tire price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce tire price entries
	*/
	public static List<CommerceTirePriceEntry> findAll(int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce tire price entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce tire price entries.
	*
	* @return the number of commerce tire price entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceTirePriceEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTirePriceEntryPersistence, CommerceTirePriceEntryPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceTirePriceEntryPersistence.class);
}