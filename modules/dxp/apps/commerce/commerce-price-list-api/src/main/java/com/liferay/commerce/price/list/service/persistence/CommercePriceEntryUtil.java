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

package com.liferay.commerce.price.list.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.model.CommercePriceEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce price entry service. This utility wraps {@link com.liferay.commerce.price.list.service.persistence.impl.CommercePriceEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryPersistence
 * @see com.liferay.commerce.price.list.service.persistence.impl.CommercePriceEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CommercePriceEntryUtil {
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
	public static void clearCache(CommercePriceEntry commercePriceEntry) {
		getPersistence().clearCache(commercePriceEntry);
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
	public static List<CommercePriceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePriceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePriceEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePriceEntry update(
		CommercePriceEntry commercePriceEntry) {
		return getPersistence().update(commercePriceEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePriceEntry update(
		CommercePriceEntry commercePriceEntry, ServiceContext serviceContext) {
		return getPersistence().update(commercePriceEntry, serviceContext);
	}

	/**
	* Returns all the commerce price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the commerce price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByUuid_First(java.lang.String uuid,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first commerce price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where uuid = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public static CommercePriceEntry[] findByUuid_PrevAndNext(
		long commercePriceEntryId, java.lang.String uuid,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(commercePriceEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the commerce price entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of commerce price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce price entries
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the commerce price entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the commerce price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the commerce price entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce price entry that was removed
	*/
	public static CommercePriceEntry removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of commerce price entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce price entries
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the commerce price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public static CommercePriceEntry[] findByUuid_C_PrevAndNext(
		long commercePriceEntryId, java.lang.String uuid, long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(commercePriceEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce price entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of commerce price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce price entries
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the commerce price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByGroupId_First(long groupId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByGroupId_Last(long groupId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where groupId = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public static CommercePriceEntry[] findByGroupId_PrevAndNext(
		long commercePriceEntryId, long groupId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commercePriceEntryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the commerce price entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce price entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the commerce price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the commerce price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByCompanyId_First(long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first commerce price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByCompanyId_Last(long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where companyId = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public static CommercePriceEntry[] findByCompanyId_PrevAndNext(
		long commercePriceEntryId, long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(commercePriceEntryId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the commerce price entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of commerce price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce price entries
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the commerce price entries where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCPInstanceId(long CPInstanceId) {
		return getPersistence().findByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns a range of all the commerce price entries where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCPInstanceId(
		long CPInstanceId, int start, int end) {
		return getPersistence().findByCPInstanceId(CPInstanceId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price entries where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .findByCPInstanceId(CPInstanceId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price entries where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPInstanceId(CPInstanceId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price entry in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByCPInstanceId_First(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the first commerce price entry in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCPInstanceId_First(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByCPInstanceId_Last(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCPInstanceId_Last(CPInstanceId, orderByComparator);
	}

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where CPInstanceId = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public static CommercePriceEntry[] findByCPInstanceId_PrevAndNext(
		long commercePriceEntryId, long CPInstanceId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByCPInstanceId_PrevAndNext(commercePriceEntryId,
			CPInstanceId, orderByComparator);
	}

	/**
	* Removes all the commerce price entries where CPInstanceId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	*/
	public static void removeByCPInstanceId(long CPInstanceId) {
		getPersistence().removeByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns the number of commerce price entries where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce price entries
	*/
	public static int countByCPInstanceId(long CPInstanceId) {
		return getPersistence().countByCPInstanceId(CPInstanceId);
	}

	/**
	* Returns all the commerce price entries where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId) {
		return getPersistence().findByCommercePriceListId(commercePriceListId);
	}

	/**
	* Returns a range of all the commerce price entries where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {
		return getPersistence()
				   .findByCommercePriceListId(commercePriceListId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce price entries where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .findByCommercePriceListId(commercePriceListId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price entries where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public static List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommercePriceListId(commercePriceListId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce price entry in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByCommercePriceListId_First(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the first commerce price entry in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceListId_First(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByCommercePriceListId_Last(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the last commerce price entry in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCommercePriceListId_Last(commercePriceListId,
			orderByComparator);
	}

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public static CommercePriceEntry[] findByCommercePriceListId_PrevAndNext(
		long commercePriceEntryId, long commercePriceListId,
		OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence()
				   .findByCommercePriceListId_PrevAndNext(commercePriceEntryId,
			commercePriceListId, orderByComparator);
	}

	/**
	* Removes all the commerce price entries where commercePriceListId = &#63; from the database.
	*
	* @param commercePriceListId the commerce price list ID
	*/
	public static void removeByCommercePriceListId(long commercePriceListId) {
		getPersistence().removeByCommercePriceListId(commercePriceListId);
	}

	/**
	* Returns the number of commerce price entries where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the number of matching commerce price entries
	*/
	public static int countByCommercePriceListId(long commercePriceListId) {
		return getPersistence().countByCommercePriceListId(commercePriceListId);
	}

	/**
	* Returns the commerce price entry where CPInstanceId = &#63; and commercePriceListId = &#63; or throws a {@link NoSuchPriceEntryException} if it could not be found.
	*
	* @param CPInstanceId the cp instance ID
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry findByC_C(long CPInstanceId,
		long commercePriceListId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence().findByC_C(CPInstanceId, commercePriceListId);
	}

	/**
	* Returns the commerce price entry where CPInstanceId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPInstanceId the cp instance ID
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByC_C(long CPInstanceId,
		long commercePriceListId) {
		return getPersistence().fetchByC_C(CPInstanceId, commercePriceListId);
	}

	/**
	* Returns the commerce price entry where CPInstanceId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPInstanceId the cp instance ID
	* @param commercePriceListId the commerce price list ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public static CommercePriceEntry fetchByC_C(long CPInstanceId,
		long commercePriceListId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(CPInstanceId, commercePriceListId,
			retrieveFromCache);
	}

	/**
	* Removes the commerce price entry where CPInstanceId = &#63; and commercePriceListId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	* @param commercePriceListId the commerce price list ID
	* @return the commerce price entry that was removed
	*/
	public static CommercePriceEntry removeByC_C(long CPInstanceId,
		long commercePriceListId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence().removeByC_C(CPInstanceId, commercePriceListId);
	}

	/**
	* Returns the number of commerce price entries where CPInstanceId = &#63; and commercePriceListId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param commercePriceListId the commerce price list ID
	* @return the number of matching commerce price entries
	*/
	public static int countByC_C(long CPInstanceId, long commercePriceListId) {
		return getPersistence().countByC_C(CPInstanceId, commercePriceListId);
	}

	/**
	* Caches the commerce price entry in the entity cache if it is enabled.
	*
	* @param commercePriceEntry the commerce price entry
	*/
	public static void cacheResult(CommercePriceEntry commercePriceEntry) {
		getPersistence().cacheResult(commercePriceEntry);
	}

	/**
	* Caches the commerce price entries in the entity cache if it is enabled.
	*
	* @param commercePriceEntries the commerce price entries
	*/
	public static void cacheResult(
		List<CommercePriceEntry> commercePriceEntries) {
		getPersistence().cacheResult(commercePriceEntries);
	}

	/**
	* Creates a new commerce price entry with the primary key. Does not add the commerce price entry to the database.
	*
	* @param commercePriceEntryId the primary key for the new commerce price entry
	* @return the new commerce price entry
	*/
	public static CommercePriceEntry create(long commercePriceEntryId) {
		return getPersistence().create(commercePriceEntryId);
	}

	/**
	* Removes the commerce price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceEntryId the primary key of the commerce price entry
	* @return the commerce price entry that was removed
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public static CommercePriceEntry remove(long commercePriceEntryId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence().remove(commercePriceEntryId);
	}

	public static CommercePriceEntry updateImpl(
		CommercePriceEntry commercePriceEntry) {
		return getPersistence().updateImpl(commercePriceEntry);
	}

	/**
	* Returns the commerce price entry with the primary key or throws a {@link NoSuchPriceEntryException} if it could not be found.
	*
	* @param commercePriceEntryId the primary key of the commerce price entry
	* @return the commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public static CommercePriceEntry findByPrimaryKey(long commercePriceEntryId)
		throws com.liferay.commerce.price.list.exception.NoSuchPriceEntryException {
		return getPersistence().findByPrimaryKey(commercePriceEntryId);
	}

	/**
	* Returns the commerce price entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePriceEntryId the primary key of the commerce price entry
	* @return the commerce price entry, or <code>null</code> if a commerce price entry with the primary key could not be found
	*/
	public static CommercePriceEntry fetchByPrimaryKey(
		long commercePriceEntryId) {
		return getPersistence().fetchByPrimaryKey(commercePriceEntryId);
	}

	public static java.util.Map<java.io.Serializable, CommercePriceEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce price entries.
	*
	* @return the commerce price entries
	*/
	public static List<CommercePriceEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of commerce price entries
	*/
	public static List<CommercePriceEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce price entries
	*/
	public static List<CommercePriceEntry> findAll(int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce price entries
	*/
	public static List<CommercePriceEntry> findAll(int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce price entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce price entries.
	*
	* @return the number of commerce price entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommercePriceEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceEntryPersistence, CommercePriceEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommercePriceEntryPersistence.class);

		ServiceTracker<CommercePriceEntryPersistence, CommercePriceEntryPersistence> serviceTracker =
			new ServiceTracker<CommercePriceEntryPersistence, CommercePriceEntryPersistence>(bundle.getBundleContext(),
				CommercePriceEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}