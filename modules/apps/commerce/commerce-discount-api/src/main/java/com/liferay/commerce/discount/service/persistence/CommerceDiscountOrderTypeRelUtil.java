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

import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
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
 * The persistence utility for the commerce discount order type rel service. This utility wraps <code>com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountOrderTypeRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountOrderTypeRelPersistence
 * @generated
 */
public class CommerceDiscountOrderTypeRelUtil {

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
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		getPersistence().clearCache(commerceDiscountOrderTypeRel);
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
	public static Map<Serializable, CommerceDiscountOrderTypeRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceDiscountOrderTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceDiscountOrderTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceDiscountOrderTypeRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceDiscountOrderTypeRel update(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		return getPersistence().update(commerceDiscountOrderTypeRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceDiscountOrderTypeRel update(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceDiscountOrderTypeRel, serviceContext);
	}

	/**
	 * Returns all the commerce discount order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce discount order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel findByUuid_First(
			String uuid,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public static CommerceDiscountOrderTypeRel[] findByUuid_PrevAndNext(
			long commerceDiscountOrderTypeRelId, String uuid,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceDiscountOrderTypeRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce discount order type rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce discount order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce discount order type rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public static CommerceDiscountOrderTypeRel[] findByUuid_C_PrevAndNext(
			long commerceDiscountOrderTypeRelId, String uuid, long companyId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceDiscountOrderTypeRelId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce discount order type rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce discount order type rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByCommerceDiscountId(
		long commerceDiscountId) {

		return getPersistence().findByCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Returns a range of all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end) {

		return getPersistence().findByCommerceDiscountId(
			commerceDiscountId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel findByCommerceDiscountId_First(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByCommerceDiscountId_First(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByCommerceDiscountId_First(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel findByCommerceDiscountId_Last(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByCommerceDiscountId_Last(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByCommerceDiscountId_Last(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public static CommerceDiscountOrderTypeRel[]
			findByCommerceDiscountId_PrevAndNext(
				long commerceDiscountOrderTypeRelId, long commerceDiscountId,
				OrderByComparator<CommerceDiscountOrderTypeRel>
					orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByCommerceDiscountId_PrevAndNext(
			commerceDiscountOrderTypeRelId, commerceDiscountId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce discount order type rels where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	public static void removeByCommerceDiscountId(long commerceDiscountId) {
		getPersistence().removeByCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Returns the number of commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount order type rels
	 */
	public static int countByCommerceDiscountId(long commerceDiscountId) {
		return getPersistence().countByCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Returns all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId) {

		return getPersistence().findByCommerceOrderTypeId(commerceOrderTypeId);
	}

	/**
	 * Returns a range of all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end) {

		return getPersistence().findByCommerceOrderTypeId(
			commerceOrderTypeId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().findByCommerceOrderTypeId(
			commerceOrderTypeId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceOrderTypeId(
			commerceOrderTypeId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel findByCommerceOrderTypeId_First(
			long commerceOrderTypeId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByCommerceOrderTypeId_First(
			commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByCommerceOrderTypeId_First(
		long commerceOrderTypeId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByCommerceOrderTypeId_First(
			commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel findByCommerceOrderTypeId_Last(
			long commerceOrderTypeId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByCommerceOrderTypeId_Last(
			commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByCommerceOrderTypeId_Last(
		long commerceOrderTypeId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().fetchByCommerceOrderTypeId_Last(
			commerceOrderTypeId, orderByComparator);
	}

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public static CommerceDiscountOrderTypeRel[]
			findByCommerceOrderTypeId_PrevAndNext(
				long commerceDiscountOrderTypeRelId, long commerceOrderTypeId,
				OrderByComparator<CommerceDiscountOrderTypeRel>
					orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByCommerceOrderTypeId_PrevAndNext(
			commerceDiscountOrderTypeRelId, commerceOrderTypeId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce discount order type rels where commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 */
	public static void removeByCommerceOrderTypeId(long commerceOrderTypeId) {
		getPersistence().removeByCommerceOrderTypeId(commerceOrderTypeId);
	}

	/**
	 * Returns the number of commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce discount order type rels
	 */
	public static int countByCommerceOrderTypeId(long commerceOrderTypeId) {
		return getPersistence().countByCommerceOrderTypeId(commerceOrderTypeId);
	}

	/**
	 * Returns the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; or throws a <code>NoSuchDiscountOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel findByCDI_COTI(
			long commerceDiscountId, long commerceOrderTypeId)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByCDI_COTI(
			commerceDiscountId, commerceOrderTypeId);
	}

	/**
	 * Returns the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByCDI_COTI(
		long commerceDiscountId, long commerceOrderTypeId) {

		return getPersistence().fetchByCDI_COTI(
			commerceDiscountId, commerceOrderTypeId);
	}

	/**
	 * Returns the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByCDI_COTI(
		long commerceDiscountId, long commerceOrderTypeId,
		boolean useFinderCache) {

		return getPersistence().fetchByCDI_COTI(
			commerceDiscountId, commerceOrderTypeId, useFinderCache);
	}

	/**
	 * Removes the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the commerce discount order type rel that was removed
	 */
	public static CommerceDiscountOrderTypeRel removeByCDI_COTI(
			long commerceDiscountId, long commerceOrderTypeId)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().removeByCDI_COTI(
			commerceDiscountId, commerceOrderTypeId);
	}

	/**
	 * Returns the number of commerce discount order type rels where commerceDiscountId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce discount order type rels
	 */
	public static int countByCDI_COTI(
		long commerceDiscountId, long commerceOrderTypeId) {

		return getPersistence().countByCDI_COTI(
			commerceDiscountId, commerceOrderTypeId);
	}

	/**
	 * Caches the commerce discount order type rel in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountOrderTypeRel the commerce discount order type rel
	 */
	public static void cacheResult(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		getPersistence().cacheResult(commerceDiscountOrderTypeRel);
	}

	/**
	 * Caches the commerce discount order type rels in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountOrderTypeRels the commerce discount order type rels
	 */
	public static void cacheResult(
		List<CommerceDiscountOrderTypeRel> commerceDiscountOrderTypeRels) {

		getPersistence().cacheResult(commerceDiscountOrderTypeRels);
	}

	/**
	 * Creates a new commerce discount order type rel with the primary key. Does not add the commerce discount order type rel to the database.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key for the new commerce discount order type rel
	 * @return the new commerce discount order type rel
	 */
	public static CommerceDiscountOrderTypeRel create(
		long commerceDiscountOrderTypeRelId) {

		return getPersistence().create(commerceDiscountOrderTypeRelId);
	}

	/**
	 * Removes the commerce discount order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel that was removed
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public static CommerceDiscountOrderTypeRel remove(
			long commerceDiscountOrderTypeRelId)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().remove(commerceDiscountOrderTypeRelId);
	}

	public static CommerceDiscountOrderTypeRel updateImpl(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		return getPersistence().updateImpl(commerceDiscountOrderTypeRel);
	}

	/**
	 * Returns the commerce discount order type rel with the primary key or throws a <code>NoSuchDiscountOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public static CommerceDiscountOrderTypeRel findByPrimaryKey(
			long commerceDiscountOrderTypeRelId)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountOrderTypeRelException {

		return getPersistence().findByPrimaryKey(
			commerceDiscountOrderTypeRelId);
	}

	/**
	 * Returns the commerce discount order type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel, or <code>null</code> if a commerce discount order type rel with the primary key could not be found
	 */
	public static CommerceDiscountOrderTypeRel fetchByPrimaryKey(
		long commerceDiscountOrderTypeRelId) {

		return getPersistence().fetchByPrimaryKey(
			commerceDiscountOrderTypeRelId);
	}

	/**
	 * Returns all the commerce discount order type rels.
	 *
	 * @return the commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce discount order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount order type rels
	 */
	public static List<CommerceDiscountOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce discount order type rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce discount order type rels.
	 *
	 * @return the number of commerce discount order type rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceDiscountOrderTypeRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceDiscountOrderTypeRelPersistence,
		 CommerceDiscountOrderTypeRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceDiscountOrderTypeRelPersistence.class);

		ServiceTracker
			<CommerceDiscountOrderTypeRelPersistence,
			 CommerceDiscountOrderTypeRelPersistence> serviceTracker =
				new ServiceTracker
					<CommerceDiscountOrderTypeRelPersistence,
					 CommerceDiscountOrderTypeRelPersistence>(
						 bundle.getBundleContext(),
						 CommerceDiscountOrderTypeRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}