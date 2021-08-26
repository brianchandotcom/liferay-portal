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

import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
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
 * The persistence utility for the commerce price list order type rel service. This utility wraps <code>com.liferay.commerce.price.list.service.persistence.impl.CommercePriceListOrderTypeRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListOrderTypeRelPersistence
 * @generated
 */
public class CommercePriceListOrderTypeRelUtil {

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
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel) {

		getPersistence().clearCache(commercePriceListOrderTypeRel);
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
	public static Map<Serializable, CommercePriceListOrderTypeRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommercePriceListOrderTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePriceListOrderTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePriceListOrderTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePriceListOrderTypeRel update(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel) {

		return getPersistence().update(commercePriceListOrderTypeRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePriceListOrderTypeRel update(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commercePriceListOrderTypeRel, serviceContext);
	}

	/**
	 * Returns all the commerce price list order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce price list order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce price list order type rels before and after the current commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the current commerce price list order type rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	public static CommercePriceListOrderTypeRel[] findByUuid_PrevAndNext(
			long commercePriceListOrderTypeRelId, String uuid,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByUuid_PrevAndNext(
			commercePriceListOrderTypeRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce price list order type rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce price list order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list order type rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce price list order type rels before and after the current commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the current commerce price list order type rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	public static CommercePriceListOrderTypeRel[] findByUuid_C_PrevAndNext(
			long commercePriceListOrderTypeRelId, String uuid, long companyId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commercePriceListOrderTypeRelId, uuid, companyId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce price list order type rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list order type rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByCommercePriceListId(
		long commercePriceListId) {

		return getPersistence().findByCommercePriceListId(commercePriceListId);
	}

	/**
	 * Returns a range of all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel findByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByCommercePriceListId_First(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel
		fetchByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceListOrderTypeRel>
				orderByComparator) {

		return getPersistence().fetchByCommercePriceListId_First(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel findByCommercePriceListId_Last(
			long commercePriceListId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByCommercePriceListId_Last(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByCommercePriceListId_Last(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the commerce price list order type rels before and after the current commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the current commerce price list order type rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	public static CommercePriceListOrderTypeRel[]
			findByCommercePriceListId_PrevAndNext(
				long commercePriceListOrderTypeRelId, long commercePriceListId,
				OrderByComparator<CommercePriceListOrderTypeRel>
					orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByCommercePriceListId_PrevAndNext(
			commercePriceListOrderTypeRelId, commercePriceListId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce price list order type rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	public static void removeByCommercePriceListId(long commercePriceListId) {
		getPersistence().removeByCommercePriceListId(commercePriceListId);
	}

	/**
	 * Returns the number of commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list order type rels
	 */
	public static int countByCommercePriceListId(long commercePriceListId) {
		return getPersistence().countByCommercePriceListId(commercePriceListId);
	}

	/**
	 * Returns the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; or throws a <code>NoSuchPriceListOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel findByCPI_COTI(
			long commercePriceListId, long commerceOrderTypeId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByCPI_COTI(
			commercePriceListId, commerceOrderTypeId);
	}

	/**
	 * Returns the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel fetchByCPI_COTI(
		long commercePriceListId, long commerceOrderTypeId) {

		return getPersistence().fetchByCPI_COTI(
			commercePriceListId, commerceOrderTypeId);
	}

	/**
	 * Returns the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public static CommercePriceListOrderTypeRel fetchByCPI_COTI(
		long commercePriceListId, long commerceOrderTypeId,
		boolean useFinderCache) {

		return getPersistence().fetchByCPI_COTI(
			commercePriceListId, commerceOrderTypeId, useFinderCache);
	}

	/**
	 * Removes the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the commerce price list order type rel that was removed
	 */
	public static CommercePriceListOrderTypeRel removeByCPI_COTI(
			long commercePriceListId, long commerceOrderTypeId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().removeByCPI_COTI(
			commercePriceListId, commerceOrderTypeId);
	}

	/**
	 * Returns the number of commerce price list order type rels where commercePriceListId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce price list order type rels
	 */
	public static int countByCPI_COTI(
		long commercePriceListId, long commerceOrderTypeId) {

		return getPersistence().countByCPI_COTI(
			commercePriceListId, commerceOrderTypeId);
	}

	/**
	 * Caches the commerce price list order type rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListOrderTypeRel the commerce price list order type rel
	 */
	public static void cacheResult(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel) {

		getPersistence().cacheResult(commercePriceListOrderTypeRel);
	}

	/**
	 * Caches the commerce price list order type rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListOrderTypeRels the commerce price list order type rels
	 */
	public static void cacheResult(
		List<CommercePriceListOrderTypeRel> commercePriceListOrderTypeRels) {

		getPersistence().cacheResult(commercePriceListOrderTypeRels);
	}

	/**
	 * Creates a new commerce price list order type rel with the primary key. Does not add the commerce price list order type rel to the database.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key for the new commerce price list order type rel
	 * @return the new commerce price list order type rel
	 */
	public static CommercePriceListOrderTypeRel create(
		long commercePriceListOrderTypeRelId) {

		return getPersistence().create(commercePriceListOrderTypeRelId);
	}

	/**
	 * Removes the commerce price list order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel that was removed
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	public static CommercePriceListOrderTypeRel remove(
			long commercePriceListOrderTypeRelId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().remove(commercePriceListOrderTypeRelId);
	}

	public static CommercePriceListOrderTypeRel updateImpl(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel) {

		return getPersistence().updateImpl(commercePriceListOrderTypeRel);
	}

	/**
	 * Returns the commerce price list order type rel with the primary key or throws a <code>NoSuchPriceListOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	public static CommercePriceListOrderTypeRel findByPrimaryKey(
			long commercePriceListOrderTypeRelId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListOrderTypeRelException {

		return getPersistence().findByPrimaryKey(
			commercePriceListOrderTypeRelId);
	}

	/**
	 * Returns the commerce price list order type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel, or <code>null</code> if a commerce price list order type rel with the primary key could not be found
	 */
	public static CommercePriceListOrderTypeRel fetchByPrimaryKey(
		long commercePriceListOrderTypeRelId) {

		return getPersistence().fetchByPrimaryKey(
			commercePriceListOrderTypeRelId);
	}

	/**
	 * Returns all the commerce price list order type rels.
	 *
	 * @return the commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce price list order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list order type rels
	 */
	public static List<CommercePriceListOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce price list order type rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce price list order type rels.
	 *
	 * @return the number of commerce price list order type rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommercePriceListOrderTypeRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceListOrderTypeRelPersistence,
		 CommercePriceListOrderTypeRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceListOrderTypeRelPersistence.class);

		ServiceTracker
			<CommercePriceListOrderTypeRelPersistence,
			 CommercePriceListOrderTypeRelPersistence> serviceTracker =
				new ServiceTracker
					<CommercePriceListOrderTypeRelPersistence,
					 CommercePriceListOrderTypeRelPersistence>(
						 bundle.getBundleContext(),
						 CommercePriceListOrderTypeRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}