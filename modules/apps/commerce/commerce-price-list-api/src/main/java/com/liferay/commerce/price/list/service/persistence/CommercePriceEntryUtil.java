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

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce price entry service. This utility wraps <code>com.liferay.commerce.price.list.service.persistence.impl.CommercePriceEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryPersistence
 * @generated
 */
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
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CommercePriceEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
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

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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
	public static List<CommercePriceEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByUuid_First(
		String uuid, OrderByComparator<CommercePriceEntry> orderByComparator) {

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
	public static CommercePriceEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByUuid_Last(
		String uuid, OrderByComparator<CommercePriceEntry> orderByComparator) {

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
			long commercePriceEntryId, String uuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			commercePriceEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce price entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
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
	public static CommercePriceEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
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
		String uuid, long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
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
	public static CommercePriceEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
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
			long commercePriceEntryId, String uuid, long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commercePriceEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce price entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
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
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commercePriceEntryId, companyId, orderByComparator);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
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

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, useFinderCache);
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
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByCommercePriceListId_First(
			commercePriceListId, orderByComparator);
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

		return getPersistence().fetchByCommercePriceListId_First(
			commercePriceListId, orderByComparator);
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
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByCommercePriceListId_Last(
			commercePriceListId, orderByComparator);
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

		return getPersistence().fetchByCommercePriceListId_Last(
			commercePriceListId, orderByComparator);
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
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByCommercePriceListId_PrevAndNext(
			commercePriceEntryId, commercePriceListId, orderByComparator);
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
	 * Returns all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid) {

		return getPersistence().findByCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Returns a range of all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end) {

		return getPersistence().findByCPInstanceUuid(
			CPInstanceUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().findByCPInstanceUuid(
			CPInstanceUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCPInstanceUuid(
			CPInstanceUuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByCPInstanceUuid_First(
			String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByCPInstanceUuid_First(
			CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByCPInstanceUuid_First(
		String CPInstanceUuid,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByCPInstanceUuid_First(
			CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByCPInstanceUuid_Last(
			String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByCPInstanceUuid_Last(
			CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByCPInstanceUuid_Last(
		String CPInstanceUuid,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByCPInstanceUuid_Last(
			CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public static CommercePriceEntry[] findByCPInstanceUuid_PrevAndNext(
			long commercePriceEntryId, String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByCPInstanceUuid_PrevAndNext(
			commercePriceEntryId, CPInstanceUuid, orderByComparator);
	}

	/**
	 * Removes all the commerce price entries where CPInstanceUuid = &#63; from the database.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 */
	public static void removeByCPInstanceUuid(String CPInstanceUuid) {
		getPersistence().removeByCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Returns the number of commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce price entries
	 */
	public static int countByCPInstanceUuid(String CPInstanceUuid) {
		return getPersistence().countByCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Returns all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid) {

		return getPersistence().findByC_C(commercePriceListId, CPInstanceUuid);
	}

	/**
	 * Returns a range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid, int start, int end) {

		return getPersistence().findByC_C(
			commercePriceListId, CPInstanceUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().findByC_C(
			commercePriceListId, CPInstanceUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			commercePriceListId, CPInstanceUuid, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByC_C_First(
			long commercePriceListId, String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByC_C_First(
			commercePriceListId, CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByC_C_First(
		long commercePriceListId, String CPInstanceUuid,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			commercePriceListId, CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByC_C_Last(
			long commercePriceListId, String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByC_C_Last(
			commercePriceListId, CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByC_C_Last(
		long commercePriceListId, String CPInstanceUuid,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			commercePriceListId, CPInstanceUuid, orderByComparator);
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public static CommercePriceEntry[] findByC_C_PrevAndNext(
			long commercePriceEntryId, long commercePriceListId,
			String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByC_C_PrevAndNext(
			commercePriceEntryId, commercePriceListId, CPInstanceUuid,
			orderByComparator);
	}

	/**
	 * Removes all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 */
	public static void removeByC_C(
		long commercePriceListId, String CPInstanceUuid) {

		getPersistence().removeByC_C(commercePriceListId, CPInstanceUuid);
	}

	/**
	 * Returns the number of commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce price entries
	 */
	public static int countByC_C(
		long commercePriceListId, String CPInstanceUuid) {

		return getPersistence().countByC_C(commercePriceListId, CPInstanceUuid);
	}

	/**
	 * Returns all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status) {

		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	 * Returns a range of all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public static CommercePriceEntry[] findByLtD_S_PrevAndNext(
			long commercePriceEntryId, Date displayDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByLtD_S_PrevAndNext(
			commercePriceEntryId, displayDate, status, orderByComparator);
	}

	/**
	 * Removes all the commerce price entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public static void removeByLtD_S(Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	 * Returns the number of commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce price entries
	 */
	public static int countByLtD_S(Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	 * Returns all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status) {

		return getPersistence().findByLtE_S(expirationDate, status);
	}

	/**
	 * Returns a range of all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return getPersistence().findByLtE_S(expirationDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().findByLtE_S(
			expirationDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtE_S(
			expirationDate, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByLtE_S_First(
			Date expirationDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByLtE_S_First(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByLtE_S_First(
		Date expirationDate, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByLtE_S_First(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByLtE_S_Last(
			Date expirationDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByLtE_S_Last(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByLtE_S_Last(
		Date expirationDate, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByLtE_S_Last(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public static CommercePriceEntry[] findByLtE_S_PrevAndNext(
			long commercePriceEntryId, Date expirationDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByLtE_S_PrevAndNext(
			commercePriceEntryId, expirationDate, status, orderByComparator);
	}

	/**
	 * Removes all the commerce price entries where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public static void removeByLtE_S(Date expirationDate, int status) {
		getPersistence().removeByLtE_S(expirationDate, status);
	}

	/**
	 * Returns the number of commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce price entries
	 */
	public static int countByLtE_S(Date expirationDate, int status) {
		return getPersistence().countByLtE_S(expirationDate, status);
	}

	/**
	 * Returns all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @return the matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status) {

		return getPersistence().findByC_C_S(
			commercePriceListId, CPInstanceUuid, status);
	}

	/**
	 * Returns a range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status, int start,
		int end) {

		return getPersistence().findByC_C_S(
			commercePriceListId, CPInstanceUuid, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status, int start,
		int end, OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().findByC_C_S(
			commercePriceListId, CPInstanceUuid, status, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public static List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status, int start,
		int end, OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_S(
			commercePriceListId, CPInstanceUuid, status, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByC_C_S_First(
			long commercePriceListId, String CPInstanceUuid, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByC_C_S_First(
			commercePriceListId, CPInstanceUuid, status, orderByComparator);
	}

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByC_C_S_First(
		long commercePriceListId, String CPInstanceUuid, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByC_C_S_First(
			commercePriceListId, CPInstanceUuid, status, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByC_C_S_Last(
			long commercePriceListId, String CPInstanceUuid, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByC_C_S_Last(
			commercePriceListId, CPInstanceUuid, status, orderByComparator);
	}

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByC_C_S_Last(
		long commercePriceListId, String CPInstanceUuid, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().fetchByC_C_S_Last(
			commercePriceListId, CPInstanceUuid, status, orderByComparator);
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public static CommercePriceEntry[] findByC_C_S_PrevAndNext(
			long commercePriceEntryId, long commercePriceListId,
			String CPInstanceUuid, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByC_C_S_PrevAndNext(
			commercePriceEntryId, commercePriceListId, CPInstanceUuid, status,
			orderByComparator);
	}

	/**
	 * Removes all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 */
	public static void removeByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status) {

		getPersistence().removeByC_C_S(
			commercePriceListId, CPInstanceUuid, status);
	}

	/**
	 * Returns the number of commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @return the number of matching commerce price entries
	 */
	public static int countByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status) {

		return getPersistence().countByC_C_S(
			commercePriceListId, CPInstanceUuid, status);
	}

	/**
	 * Returns the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchPriceEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public static CommercePriceEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce price entry that was removed
	 */
	public static CommercePriceEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of commerce price entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce price entries
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
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
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

		return getPersistence().remove(commercePriceEntryId);
	}

	public static CommercePriceEntry updateImpl(
		CommercePriceEntry commercePriceEntry) {

		return getPersistence().updateImpl(commercePriceEntry);
	}

	/**
	 * Returns the commerce price entry with the primary key or throws a <code>NoSuchPriceEntryException</code> if it could not be found.
	 *
	 * @param commercePriceEntryId the primary key of the commerce price entry
	 * @return the commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public static CommercePriceEntry findByPrimaryKey(long commercePriceEntryId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceEntryException {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price entries
	 */
	public static List<CommercePriceEntry> findAll(
		int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price entries
	 */
	public static List<CommercePriceEntry> findAll(
		int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
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

	public static CommercePriceEntryPersistence getPersistence() {
		return _persistence;
	}

	private static volatile CommercePriceEntryPersistence _persistence;

}