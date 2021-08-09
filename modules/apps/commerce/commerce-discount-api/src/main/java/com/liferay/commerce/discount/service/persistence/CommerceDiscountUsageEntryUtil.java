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

package com.liferay.commerce.discount.service.persistence;

import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the commerce discount usage entry service. This utility wraps <code>com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountUsageEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntryPersistence
 * @generated
 */
public class CommerceDiscountUsageEntryUtil {

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
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		getPersistence().clearCache(commerceDiscountUsageEntry);
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
	public static Map<Serializable, CommerceDiscountUsageEntry>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceDiscountUsageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceDiscountUsageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceDiscountUsageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceDiscountUsageEntry update(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		return getPersistence().update(commerceDiscountUsageEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceDiscountUsageEntry update(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceDiscountUsageEntry, serviceContext);
	}

	/**
	 * Returns all the commerce discount usage entries where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCommerceDiscountId(
		long commerceDiscountId) {

		return getPersistence().findByCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Returns a range of all the commerce discount usage entries where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end) {

		return getPersistence().findByCommerceDiscountId(
			commerceDiscountId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry findByCommerceDiscountId_First(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCommerceDiscountId_First(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().fetchByCommerceDiscountId_First(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry findByCommerceDiscountId_Last(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCommerceDiscountId_Last(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().fetchByCommerceDiscountId_Last(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	public static CommerceDiscountUsageEntry[]
			findByCommerceDiscountId_PrevAndNext(
				long commerceDiscountUsageEntryId, long commerceDiscountId,
				OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCommerceDiscountId_PrevAndNext(
			commerceDiscountUsageEntryId, commerceDiscountId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce discount usage entries where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	public static void removeByCommerceDiscountId(long commerceDiscountId) {
		getPersistence().removeByCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Returns the number of commerce discount usage entries where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount usage entries
	 */
	public static int countByCommerceDiscountId(long commerceDiscountId) {
		return getPersistence().countByCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Returns all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCAI_CDI(
		long commerceAccountId, long commerceDiscountId) {

		return getPersistence().findByCAI_CDI(
			commerceAccountId, commerceDiscountId);
	}

	/**
	 * Returns a range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCAI_CDI(
		long commerceAccountId, long commerceDiscountId, int start, int end) {

		return getPersistence().findByCAI_CDI(
			commerceAccountId, commerceDiscountId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCAI_CDI(
		long commerceAccountId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().findByCAI_CDI(
			commerceAccountId, commerceDiscountId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCAI_CDI(
		long commerceAccountId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCAI_CDI(
			commerceAccountId, commerceDiscountId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry findByCAI_CDI_First(
			long commerceAccountId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCAI_CDI_First(
			commerceAccountId, commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry fetchByCAI_CDI_First(
		long commerceAccountId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().fetchByCAI_CDI_First(
			commerceAccountId, commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry findByCAI_CDI_Last(
			long commerceAccountId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCAI_CDI_Last(
			commerceAccountId, commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry fetchByCAI_CDI_Last(
		long commerceAccountId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().fetchByCAI_CDI_Last(
			commerceAccountId, commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	public static CommerceDiscountUsageEntry[] findByCAI_CDI_PrevAndNext(
			long commerceDiscountUsageEntryId, long commerceAccountId,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCAI_CDI_PrevAndNext(
			commerceDiscountUsageEntryId, commerceAccountId, commerceDiscountId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 */
	public static void removeByCAI_CDI(
		long commerceAccountId, long commerceDiscountId) {

		getPersistence().removeByCAI_CDI(commerceAccountId, commerceDiscountId);
	}

	/**
	 * Returns the number of commerce discount usage entries where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount usage entries
	 */
	public static int countByCAI_CDI(
		long commerceAccountId, long commerceDiscountId) {

		return getPersistence().countByCAI_CDI(
			commerceAccountId, commerceDiscountId);
	}

	/**
	 * Returns all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCOI_CDI(
		long commerceOrderId, long commerceDiscountId) {

		return getPersistence().findByCOI_CDI(
			commerceOrderId, commerceDiscountId);
	}

	/**
	 * Returns a range of all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCOI_CDI(
		long commerceOrderId, long commerceDiscountId, int start, int end) {

		return getPersistence().findByCOI_CDI(
			commerceOrderId, commerceDiscountId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCOI_CDI(
		long commerceOrderId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().findByCOI_CDI(
			commerceOrderId, commerceDiscountId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCOI_CDI(
		long commerceOrderId, long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCOI_CDI(
			commerceOrderId, commerceDiscountId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry findByCOI_CDI_First(
			long commerceOrderId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCOI_CDI_First(
			commerceOrderId, commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry fetchByCOI_CDI_First(
		long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().fetchByCOI_CDI_First(
			commerceOrderId, commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry findByCOI_CDI_Last(
			long commerceOrderId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCOI_CDI_Last(
			commerceOrderId, commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry fetchByCOI_CDI_Last(
		long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().fetchByCOI_CDI_Last(
			commerceOrderId, commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	public static CommerceDiscountUsageEntry[] findByCOI_CDI_PrevAndNext(
			long commerceDiscountUsageEntryId, long commerceOrderId,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCOI_CDI_PrevAndNext(
			commerceDiscountUsageEntryId, commerceOrderId, commerceDiscountId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 */
	public static void removeByCOI_CDI(
		long commerceOrderId, long commerceDiscountId) {

		getPersistence().removeByCOI_CDI(commerceOrderId, commerceDiscountId);
	}

	/**
	 * Returns the number of commerce discount usage entries where commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount usage entries
	 */
	public static int countByCOI_CDI(
		long commerceOrderId, long commerceDiscountId) {

		return getPersistence().countByCOI_CDI(
			commerceOrderId, commerceDiscountId);
	}

	/**
	 * Returns all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		return getPersistence().findByCAI_COI_CDI(
			commerceAccountId, commerceOrderId, commerceDiscountId);
	}

	/**
	 * Returns a range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		int start, int end) {

		return getPersistence().findByCAI_COI_CDI(
			commerceAccountId, commerceOrderId, commerceDiscountId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().findByCAI_COI_CDI(
			commerceAccountId, commerceOrderId, commerceDiscountId, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCAI_COI_CDI(
			commerceAccountId, commerceOrderId, commerceDiscountId, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry findByCAI_COI_CDI_First(
			long commerceAccountId, long commerceOrderId,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCAI_COI_CDI_First(
			commerceAccountId, commerceOrderId, commerceDiscountId,
			orderByComparator);
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry fetchByCAI_COI_CDI_First(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().fetchByCAI_COI_CDI_First(
			commerceAccountId, commerceOrderId, commerceDiscountId,
			orderByComparator);
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry findByCAI_COI_CDI_Last(
			long commerceAccountId, long commerceOrderId,
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCAI_COI_CDI_Last(
			commerceAccountId, commerceOrderId, commerceDiscountId,
			orderByComparator);
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	public static CommerceDiscountUsageEntry fetchByCAI_COI_CDI_Last(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().fetchByCAI_COI_CDI_Last(
			commerceAccountId, commerceOrderId, commerceDiscountId,
			orderByComparator);
	}

	/**
	 * Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	public static CommerceDiscountUsageEntry[] findByCAI_COI_CDI_PrevAndNext(
			long commerceDiscountUsageEntryId, long commerceAccountId,
			long commerceOrderId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByCAI_COI_CDI_PrevAndNext(
			commerceDiscountUsageEntryId, commerceAccountId, commerceOrderId,
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Removes all the commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 */
	public static void removeByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		getPersistence().removeByCAI_COI_CDI(
			commerceAccountId, commerceOrderId, commerceDiscountId);
	}

	/**
	 * Returns the number of commerce discount usage entries where commerceAccountId = &#63; and commerceOrderId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceOrderId the commerce order ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount usage entries
	 */
	public static int countByCAI_COI_CDI(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		return getPersistence().countByCAI_COI_CDI(
			commerceAccountId, commerceOrderId, commerceDiscountId);
	}

	/**
	 * Caches the commerce discount usage entry in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUsageEntry the commerce discount usage entry
	 */
	public static void cacheResult(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		getPersistence().cacheResult(commerceDiscountUsageEntry);
	}

	/**
	 * Caches the commerce discount usage entries in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUsageEntries the commerce discount usage entries
	 */
	public static void cacheResult(
		List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries) {

		getPersistence().cacheResult(commerceDiscountUsageEntries);
	}

	/**
	 * Creates a new commerce discount usage entry with the primary key. Does not add the commerce discount usage entry to the database.
	 *
	 * @param commerceDiscountUsageEntryId the primary key for the new commerce discount usage entry
	 * @return the new commerce discount usage entry
	 */
	public static CommerceDiscountUsageEntry create(
		long commerceDiscountUsageEntryId) {

		return getPersistence().create(commerceDiscountUsageEntryId);
	}

	/**
	 * Removes the commerce discount usage entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry that was removed
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	public static CommerceDiscountUsageEntry remove(
			long commerceDiscountUsageEntryId)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().remove(commerceDiscountUsageEntryId);
	}

	public static CommerceDiscountUsageEntry updateImpl(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {

		return getPersistence().updateImpl(commerceDiscountUsageEntry);
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or throws a <code>NoSuchDiscountUsageEntryException</code> if it could not be found.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	public static CommerceDiscountUsageEntry findByPrimaryKey(
			long commerceDiscountUsageEntryId)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountUsageEntryException {

		return getPersistence().findByPrimaryKey(commerceDiscountUsageEntryId);
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry, or <code>null</code> if a commerce discount usage entry with the primary key could not be found
	 */
	public static CommerceDiscountUsageEntry fetchByPrimaryKey(
		long commerceDiscountUsageEntryId) {

		return getPersistence().fetchByPrimaryKey(commerceDiscountUsageEntryId);
	}

	/**
	 * Returns all the commerce discount usage entries.
	 *
	 * @return the commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountUsageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount usage entries
	 */
	public static List<CommerceDiscountUsageEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce discount usage entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce discount usage entries.
	 *
	 * @return the number of commerce discount usage entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceDiscountUsageEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceDiscountUsageEntryPersistence,
		 CommerceDiscountUsageEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceDiscountUsageEntryPersistence.class);

		ServiceTracker
			<CommerceDiscountUsageEntryPersistence,
			 CommerceDiscountUsageEntryPersistence> serviceTracker =
				new ServiceTracker
					<CommerceDiscountUsageEntryPersistence,
					 CommerceDiscountUsageEntryPersistence>(
						 bundle.getBundleContext(),
						 CommerceDiscountUsageEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}