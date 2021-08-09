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

import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the commerce tier price entry service. This utility wraps <code>com.liferay.commerce.price.list.service.persistence.impl.CommerceTierPriceEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntryPersistence
 * @generated
 */
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
	public static void clearCache(
		CommerceTierPriceEntry commerceTierPriceEntry) {

		getPersistence().clearCache(commerceTierPriceEntry);
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
	public static Map<Serializable, CommerceTierPriceEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
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

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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
	public static List<CommerceTierPriceEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce tier price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
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
			String uuid,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

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
		String uuid,
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
			String uuid,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

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
		String uuid,
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
			long commerceTierPriceEntryId, String uuid,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceTierPriceEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce tier price entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce tier price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce tier price entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
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
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
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
			String uuid, long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
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
		String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
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
			String uuid, long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
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
		String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
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
			long commerceTierPriceEntryId, String uuid, long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceTierPriceEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce tier price entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce tier price entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
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

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
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
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commerceTierPriceEntryId, companyId, orderByComparator);
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

		return getPersistence().findByCommercePriceEntryId(
			commercePriceEntryId);
	}

	/**
	 * Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end) {

		return getPersistence().findByCommercePriceEntryId(
			commercePriceEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
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

		return getPersistence().findByCommercePriceEntryId(
			commercePriceEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommercePriceEntryId(
			commercePriceEntryId, start, end, orderByComparator,
			useFinderCache);
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
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByCommercePriceEntryId_First(
			commercePriceEntryId, orderByComparator);
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

		return getPersistence().fetchByCommercePriceEntryId_First(
			commercePriceEntryId, orderByComparator);
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
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByCommercePriceEntryId_Last(
			commercePriceEntryId, orderByComparator);
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

		return getPersistence().fetchByCommercePriceEntryId_Last(
			commercePriceEntryId, orderByComparator);
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
	public static CommerceTierPriceEntry[]
			findByCommercePriceEntryId_PrevAndNext(
				long commerceTierPriceEntryId, long commercePriceEntryId,
				OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByCommercePriceEntryId_PrevAndNext(
			commerceTierPriceEntryId, commercePriceEntryId, orderByComparator);
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
		return getPersistence().countByCommercePriceEntryId(
			commercePriceEntryId);
	}

	/**
	 * Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or throws a <code>NoSuchTierPriceEntryException</code> if it could not be found.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry findByC_M(
			long commercePriceEntryId, int minQuantity)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByC_M(commercePriceEntryId, minQuantity);
	}

	/**
	 * Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByC_M(
		long commercePriceEntryId, int minQuantity) {

		return getPersistence().fetchByC_M(commercePriceEntryId, minQuantity);
	}

	/**
	 * Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByC_M(
		long commercePriceEntryId, int minQuantity, boolean useFinderCache) {

		return getPersistence().fetchByC_M(
			commercePriceEntryId, minQuantity, useFinderCache);
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
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

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
	public static List<CommerceTierPriceEntry> findByC_LteM(
		long commercePriceEntryId, int minQuantity) {

		return getPersistence().findByC_LteM(commercePriceEntryId, minQuantity);
	}

	/**
	 * Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByC_LteM(
		long commercePriceEntryId, int minQuantity, int start, int end) {

		return getPersistence().findByC_LteM(
			commercePriceEntryId, minQuantity, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByC_LteM(
		long commercePriceEntryId, int minQuantity, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().findByC_LteM(
			commercePriceEntryId, minQuantity, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByC_LteM(
		long commercePriceEntryId, int minQuantity, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_LteM(
			commercePriceEntryId, minQuantity, start, end, orderByComparator,
			useFinderCache);
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
	public static CommerceTierPriceEntry findByC_LteM_First(
			long commercePriceEntryId, int minQuantity,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByC_LteM_First(
			commercePriceEntryId, minQuantity, orderByComparator);
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByC_LteM_First(
		long commercePriceEntryId, int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByC_LteM_First(
			commercePriceEntryId, minQuantity, orderByComparator);
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
	public static CommerceTierPriceEntry findByC_LteM_Last(
			long commercePriceEntryId, int minQuantity,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByC_LteM_Last(
			commercePriceEntryId, minQuantity, orderByComparator);
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByC_LteM_Last(
		long commercePriceEntryId, int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByC_LteM_Last(
			commercePriceEntryId, minQuantity, orderByComparator);
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
	public static CommerceTierPriceEntry[] findByC_LteM_PrevAndNext(
			long commerceTierPriceEntryId, long commercePriceEntryId,
			int minQuantity,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByC_LteM_PrevAndNext(
			commerceTierPriceEntryId, commercePriceEntryId, minQuantity,
			orderByComparator);
	}

	/**
	 * Removes all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; from the database.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 */
	public static void removeByC_LteM(
		long commercePriceEntryId, int minQuantity) {

		getPersistence().removeByC_LteM(commercePriceEntryId, minQuantity);
	}

	/**
	 * Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the number of matching commerce tier price entries
	 */
	public static int countByC_LteM(
		long commercePriceEntryId, int minQuantity) {

		return getPersistence().countByC_LteM(
			commercePriceEntryId, minQuantity);
	}

	/**
	 * Returns all the commerce tier price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByLtD_S(
		Date displayDate, int status) {

		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	 * Returns a range of all the commerce tier price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	public static CommerceTierPriceEntry[] findByLtD_S_PrevAndNext(
			long commerceTierPriceEntryId, Date displayDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByLtD_S_PrevAndNext(
			commerceTierPriceEntryId, displayDate, status, orderByComparator);
	}

	/**
	 * Removes all the commerce tier price entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public static void removeByLtD_S(Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	 * Returns the number of commerce tier price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce tier price entries
	 */
	public static int countByLtD_S(Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	 * Returns all the commerce tier price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByLtE_S(
		Date expirationDate, int status) {

		return getPersistence().findByLtE_S(expirationDate, status);
	}

	/**
	 * Returns a range of all the commerce tier price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return getPersistence().findByLtE_S(expirationDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().findByLtE_S(
			expirationDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtE_S(
			expirationDate, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry findByLtE_S_First(
			Date expirationDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByLtE_S_First(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByLtE_S_First(
		Date expirationDate, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByLtE_S_First(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry findByLtE_S_Last(
			Date expirationDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByLtE_S_Last(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByLtE_S_Last(
		Date expirationDate, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByLtE_S_Last(
			expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	public static CommerceTierPriceEntry[] findByLtE_S_PrevAndNext(
			long commerceTierPriceEntryId, Date expirationDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByLtE_S_PrevAndNext(
			commerceTierPriceEntryId, expirationDate, status,
			orderByComparator);
	}

	/**
	 * Removes all the commerce tier price entries where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public static void removeByLtE_S(Date expirationDate, int status) {
		getPersistence().removeByLtE_S(expirationDate, status);
	}

	/**
	 * Returns the number of commerce tier price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce tier price entries
	 */
	public static int countByLtE_S(Date expirationDate, int status) {
		return getPersistence().countByLtE_S(expirationDate, status);
	}

	/**
	 * Returns all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @return the matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status) {

		return getPersistence().findByC_LteM_S(
			commercePriceEntryId, minQuantity, status);
	}

	/**
	 * Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status, int start,
		int end) {

		return getPersistence().findByC_LteM_S(
			commercePriceEntryId, minQuantity, status, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status, int start,
		int end, OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().findByC_LteM_S(
			commercePriceEntryId, minQuantity, status, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status, int start,
		int end, OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_LteM_S(
			commercePriceEntryId, minQuantity, status, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry findByC_LteM_S_First(
			long commercePriceEntryId, int minQuantity, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByC_LteM_S_First(
			commercePriceEntryId, minQuantity, status, orderByComparator);
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByC_LteM_S_First(
		long commercePriceEntryId, int minQuantity, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByC_LteM_S_First(
			commercePriceEntryId, minQuantity, status, orderByComparator);
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry findByC_LteM_S_Last(
			long commercePriceEntryId, int minQuantity, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByC_LteM_S_Last(
			commercePriceEntryId, minQuantity, status, orderByComparator);
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByC_LteM_S_Last(
		long commercePriceEntryId, int minQuantity, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().fetchByC_LteM_S_Last(
			commercePriceEntryId, minQuantity, status, orderByComparator);
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	public static CommerceTierPriceEntry[] findByC_LteM_S_PrevAndNext(
			long commerceTierPriceEntryId, long commercePriceEntryId,
			int minQuantity, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByC_LteM_S_PrevAndNext(
			commerceTierPriceEntryId, commercePriceEntryId, minQuantity, status,
			orderByComparator);
	}

	/**
	 * Removes all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63; from the database.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 */
	public static void removeByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status) {

		getPersistence().removeByC_LteM_S(
			commercePriceEntryId, minQuantity, status);
	}

	/**
	 * Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @return the number of matching commerce tier price entries
	 */
	public static int countByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status) {

		return getPersistence().countByC_LteM_S(
			commercePriceEntryId, minQuantity, status);
	}

	/**
	 * Returns the commerce tier price entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchTierPriceEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce tier price entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce tier price entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static CommerceTierPriceEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the commerce tier price entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce tier price entry that was removed
	 */
	public static CommerceTierPriceEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of commerce tier price entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce tier price entries
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
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
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

		return getPersistence().remove(commerceTierPriceEntryId);
	}

	public static CommerceTierPriceEntry updateImpl(
		CommerceTierPriceEntry commerceTierPriceEntry) {

		return getPersistence().updateImpl(commerceTierPriceEntry);
	}

	/**
	 * Returns the commerce tier price entry with the primary key or throws a <code>NoSuchTierPriceEntryException</code> if it could not be found.
	 *
	 * @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	 * @return the commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	public static CommerceTierPriceEntry findByPrimaryKey(
			long commerceTierPriceEntryId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchTierPriceEntryException {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce tier price entries
	 */
	public static List<CommerceTierPriceEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
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

	public static CommerceTierPriceEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceTierPriceEntryPersistence, CommerceTierPriceEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceTierPriceEntryPersistence.class);

		ServiceTracker
			<CommerceTierPriceEntryPersistence,
			 CommerceTierPriceEntryPersistence> serviceTracker =
				new ServiceTracker
					<CommerceTierPriceEntryPersistence,
					 CommerceTierPriceEntryPersistence>(
						 bundle.getBundleContext(),
						 CommerceTierPriceEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}