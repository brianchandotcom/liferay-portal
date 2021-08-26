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

import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
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
 * The persistence utility for the commerce price list account rel service. This utility wraps <code>com.liferay.commerce.price.list.service.persistence.impl.CommercePriceListAccountRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListAccountRelPersistence
 * @generated
 */
public class CommercePriceListAccountRelUtil {

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
		CommercePriceListAccountRel commercePriceListAccountRel) {

		getPersistence().clearCache(commercePriceListAccountRel);
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
	public static Map<Serializable, CommercePriceListAccountRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommercePriceListAccountRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePriceListAccountRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePriceListAccountRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePriceListAccountRel update(
		CommercePriceListAccountRel commercePriceListAccountRel) {

		return getPersistence().update(commercePriceListAccountRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePriceListAccountRel update(
		CommercePriceListAccountRel commercePriceListAccountRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commercePriceListAccountRel, serviceContext);
	}

	/**
	 * Returns all the commerce price list account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce price list account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce price list account rels before and after the current commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListAccountRelId the primary key of the current commerce price list account rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	public static CommercePriceListAccountRel[] findByUuid_PrevAndNext(
			long commercePriceListAccountRelId, String uuid,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByUuid_PrevAndNext(
			commercePriceListAccountRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce price list account rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce price list account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list account rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce price list account rels before and after the current commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListAccountRelId the primary key of the current commerce price list account rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	public static CommercePriceListAccountRel[] findByUuid_C_PrevAndNext(
			long commercePriceListAccountRelId, String uuid, long companyId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commercePriceListAccountRelId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce price list account rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list account rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByCommercePriceListId(
		long commercePriceListId) {

		return getPersistence().findByCommercePriceListId(commercePriceListId);
	}

	/**
	 * Returns a range of all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel findByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByCommercePriceListId_First(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the first commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().fetchByCommercePriceListId_First(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel findByCommercePriceListId_Last(
			long commercePriceListId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByCommercePriceListId_Last(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().fetchByCommercePriceListId_Last(
			commercePriceListId, orderByComparator);
	}

	/**
	 * Returns the commerce price list account rels before and after the current commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListAccountRelId the primary key of the current commerce price list account rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	public static CommercePriceListAccountRel[]
			findByCommercePriceListId_PrevAndNext(
				long commercePriceListAccountRelId, long commercePriceListId,
				OrderByComparator<CommercePriceListAccountRel>
					orderByComparator)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByCommercePriceListId_PrevAndNext(
			commercePriceListAccountRelId, commercePriceListId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce price list account rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	public static void removeByCommercePriceListId(long commercePriceListId) {
		getPersistence().removeByCommercePriceListId(commercePriceListId);
	}

	/**
	 * Returns the number of commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list account rels
	 */
	public static int countByCommercePriceListId(long commercePriceListId) {
		return getPersistence().countByCommercePriceListId(commercePriceListId);
	}

	/**
	 * Returns the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; or throws a <code>NoSuchPriceListAccountRelException</code> if it could not be found.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel findByCAI_CPI(
			long commerceAccountId, long commercePriceListId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByCAI_CPI(
			commerceAccountId, commercePriceListId);
	}

	/**
	 * Returns the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel fetchByCAI_CPI(
		long commerceAccountId, long commercePriceListId) {

		return getPersistence().fetchByCAI_CPI(
			commerceAccountId, commercePriceListId);
	}

	/**
	 * Returns the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public static CommercePriceListAccountRel fetchByCAI_CPI(
		long commerceAccountId, long commercePriceListId,
		boolean useFinderCache) {

		return getPersistence().fetchByCAI_CPI(
			commerceAccountId, commercePriceListId, useFinderCache);
	}

	/**
	 * Removes the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the commerce price list account rel that was removed
	 */
	public static CommercePriceListAccountRel removeByCAI_CPI(
			long commerceAccountId, long commercePriceListId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().removeByCAI_CPI(
			commerceAccountId, commercePriceListId);
	}

	/**
	 * Returns the number of commerce price list account rels where commerceAccountId = &#63; and commercePriceListId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list account rels
	 */
	public static int countByCAI_CPI(
		long commerceAccountId, long commercePriceListId) {

		return getPersistence().countByCAI_CPI(
			commerceAccountId, commercePriceListId);
	}

	/**
	 * Caches the commerce price list account rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListAccountRel the commerce price list account rel
	 */
	public static void cacheResult(
		CommercePriceListAccountRel commercePriceListAccountRel) {

		getPersistence().cacheResult(commercePriceListAccountRel);
	}

	/**
	 * Caches the commerce price list account rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListAccountRels the commerce price list account rels
	 */
	public static void cacheResult(
		List<CommercePriceListAccountRel> commercePriceListAccountRels) {

		getPersistence().cacheResult(commercePriceListAccountRels);
	}

	/**
	 * Creates a new commerce price list account rel with the primary key. Does not add the commerce price list account rel to the database.
	 *
	 * @param commercePriceListAccountRelId the primary key for the new commerce price list account rel
	 * @return the new commerce price list account rel
	 */
	public static CommercePriceListAccountRel create(
		long commercePriceListAccountRelId) {

		return getPersistence().create(commercePriceListAccountRelId);
	}

	/**
	 * Removes the commerce price list account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListAccountRelId the primary key of the commerce price list account rel
	 * @return the commerce price list account rel that was removed
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	public static CommercePriceListAccountRel remove(
			long commercePriceListAccountRelId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().remove(commercePriceListAccountRelId);
	}

	public static CommercePriceListAccountRel updateImpl(
		CommercePriceListAccountRel commercePriceListAccountRel) {

		return getPersistence().updateImpl(commercePriceListAccountRel);
	}

	/**
	 * Returns the commerce price list account rel with the primary key or throws a <code>NoSuchPriceListAccountRelException</code> if it could not be found.
	 *
	 * @param commercePriceListAccountRelId the primary key of the commerce price list account rel
	 * @return the commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	public static CommercePriceListAccountRel findByPrimaryKey(
			long commercePriceListAccountRelId)
		throws com.liferay.commerce.price.list.exception.
			NoSuchPriceListAccountRelException {

		return getPersistence().findByPrimaryKey(commercePriceListAccountRelId);
	}

	/**
	 * Returns the commerce price list account rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListAccountRelId the primary key of the commerce price list account rel
	 * @return the commerce price list account rel, or <code>null</code> if a commerce price list account rel with the primary key could not be found
	 */
	public static CommercePriceListAccountRel fetchByPrimaryKey(
		long commercePriceListAccountRelId) {

		return getPersistence().fetchByPrimaryKey(
			commercePriceListAccountRelId);
	}

	/**
	 * Returns all the commerce price list account rels.
	 *
	 * @return the commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce price list account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list account rels
	 */
	public static List<CommercePriceListAccountRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce price list account rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce price list account rels.
	 *
	 * @return the number of commerce price list account rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommercePriceListAccountRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceListAccountRelPersistence,
		 CommercePriceListAccountRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceListAccountRelPersistence.class);

		ServiceTracker
			<CommercePriceListAccountRelPersistence,
			 CommercePriceListAccountRelPersistence> serviceTracker =
				new ServiceTracker
					<CommercePriceListAccountRelPersistence,
					 CommercePriceListAccountRelPersistence>(
						 bundle.getBundleContext(),
						 CommercePriceListAccountRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}