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

import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
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
 * The persistence utility for the commerce discount account rel service. This utility wraps <code>com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountAccountRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountAccountRelPersistence
 * @generated
 */
public class CommerceDiscountAccountRelUtil {

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
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		getPersistence().clearCache(commerceDiscountAccountRel);
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
	public static Map<Serializable, CommerceDiscountAccountRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceDiscountAccountRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceDiscountAccountRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceDiscountAccountRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceDiscountAccountRel update(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		return getPersistence().update(commerceDiscountAccountRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceDiscountAccountRel update(
		CommerceDiscountAccountRel commerceDiscountAccountRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceDiscountAccountRel, serviceContext);
	}

	/**
	 * Returns all the commerce discount account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce discount account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel findByUuid_First(
			String uuid,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public static CommerceDiscountAccountRel[] findByUuid_PrevAndNext(
			long commerceDiscountAccountRelId, String uuid,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceDiscountAccountRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce discount account rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce discount account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce discount account rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public static CommerceDiscountAccountRel[] findByUuid_C_PrevAndNext(
			long commerceDiscountAccountRelId, String uuid, long companyId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceDiscountAccountRelId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce discount account rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce discount account rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId) {

		return getPersistence().findByCommerceAccountId(commerceAccountId);
	}

	/**
	 * Returns a range of all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end) {

		return getPersistence().findByCommerceAccountId(
			commerceAccountId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().findByCommerceAccountId(
			commerceAccountId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceAccountId(
			commerceAccountId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel findByCommerceAccountId_First(
			long commerceAccountId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByCommerceAccountId_First(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel fetchByCommerceAccountId_First(
		long commerceAccountId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().fetchByCommerceAccountId_First(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel findByCommerceAccountId_Last(
			long commerceAccountId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByCommerceAccountId_Last(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel fetchByCommerceAccountId_Last(
		long commerceAccountId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().fetchByCommerceAccountId_Last(
			commerceAccountId, orderByComparator);
	}

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public static CommerceDiscountAccountRel[]
			findByCommerceAccountId_PrevAndNext(
				long commerceDiscountAccountRelId, long commerceAccountId,
				OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByCommerceAccountId_PrevAndNext(
			commerceDiscountAccountRelId, commerceAccountId, orderByComparator);
	}

	/**
	 * Removes all the commerce discount account rels where commerceAccountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 */
	public static void removeByCommerceAccountId(long commerceAccountId) {
		getPersistence().removeByCommerceAccountId(commerceAccountId);
	}

	/**
	 * Returns the number of commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the number of matching commerce discount account rels
	 */
	public static int countByCommerceAccountId(long commerceAccountId) {
		return getPersistence().countByCommerceAccountId(commerceAccountId);
	}

	/**
	 * Returns all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId) {

		return getPersistence().findByCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Returns a range of all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end) {

		return getPersistence().findByCommerceDiscountId(
			commerceDiscountId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel findByCommerceDiscountId_First(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByCommerceDiscountId_First(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().fetchByCommerceDiscountId_First(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel findByCommerceDiscountId_Last(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByCommerceDiscountId_Last(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().fetchByCommerceDiscountId_Last(
			commerceDiscountId, orderByComparator);
	}

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public static CommerceDiscountAccountRel[]
			findByCommerceDiscountId_PrevAndNext(
				long commerceDiscountAccountRelId, long commerceDiscountId,
				OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByCommerceDiscountId_PrevAndNext(
			commerceDiscountAccountRelId, commerceDiscountId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce discount account rels where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	public static void removeByCommerceDiscountId(long commerceDiscountId) {
		getPersistence().removeByCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Returns the number of commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount account rels
	 */
	public static int countByCommerceDiscountId(long commerceDiscountId) {
		return getPersistence().countByCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Returns the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; or throws a <code>NoSuchDiscountAccountRelException</code> if it could not be found.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel findByCAI_CDI(
			long commerceAccountId, long commerceDiscountId)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByCAI_CDI(
			commerceAccountId, commerceDiscountId);
	}

	/**
	 * Returns the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel fetchByCAI_CDI(
		long commerceAccountId, long commerceDiscountId) {

		return getPersistence().fetchByCAI_CDI(
			commerceAccountId, commerceDiscountId);
	}

	/**
	 * Returns the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public static CommerceDiscountAccountRel fetchByCAI_CDI(
		long commerceAccountId, long commerceDiscountId,
		boolean useFinderCache) {

		return getPersistence().fetchByCAI_CDI(
			commerceAccountId, commerceDiscountId, useFinderCache);
	}

	/**
	 * Removes the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the commerce discount account rel that was removed
	 */
	public static CommerceDiscountAccountRel removeByCAI_CDI(
			long commerceAccountId, long commerceDiscountId)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().removeByCAI_CDI(
			commerceAccountId, commerceDiscountId);
	}

	/**
	 * Returns the number of commerce discount account rels where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount account rels
	 */
	public static int countByCAI_CDI(
		long commerceAccountId, long commerceDiscountId) {

		return getPersistence().countByCAI_CDI(
			commerceAccountId, commerceDiscountId);
	}

	/**
	 * Caches the commerce discount account rel in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountAccountRel the commerce discount account rel
	 */
	public static void cacheResult(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		getPersistence().cacheResult(commerceDiscountAccountRel);
	}

	/**
	 * Caches the commerce discount account rels in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountAccountRels the commerce discount account rels
	 */
	public static void cacheResult(
		List<CommerceDiscountAccountRel> commerceDiscountAccountRels) {

		getPersistence().cacheResult(commerceDiscountAccountRels);
	}

	/**
	 * Creates a new commerce discount account rel with the primary key. Does not add the commerce discount account rel to the database.
	 *
	 * @param commerceDiscountAccountRelId the primary key for the new commerce discount account rel
	 * @return the new commerce discount account rel
	 */
	public static CommerceDiscountAccountRel create(
		long commerceDiscountAccountRelId) {

		return getPersistence().create(commerceDiscountAccountRelId);
	}

	/**
	 * Removes the commerce discount account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel that was removed
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public static CommerceDiscountAccountRel remove(
			long commerceDiscountAccountRelId)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().remove(commerceDiscountAccountRelId);
	}

	public static CommerceDiscountAccountRel updateImpl(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		return getPersistence().updateImpl(commerceDiscountAccountRel);
	}

	/**
	 * Returns the commerce discount account rel with the primary key or throws a <code>NoSuchDiscountAccountRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public static CommerceDiscountAccountRel findByPrimaryKey(
			long commerceDiscountAccountRelId)
		throws com.liferay.commerce.discount.exception.
			NoSuchDiscountAccountRelException {

		return getPersistence().findByPrimaryKey(commerceDiscountAccountRelId);
	}

	/**
	 * Returns the commerce discount account rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel, or <code>null</code> if a commerce discount account rel with the primary key could not be found
	 */
	public static CommerceDiscountAccountRel fetchByPrimaryKey(
		long commerceDiscountAccountRelId) {

		return getPersistence().fetchByPrimaryKey(commerceDiscountAccountRelId);
	}

	/**
	 * Returns all the commerce discount account rels.
	 *
	 * @return the commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce discount account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount account rels
	 */
	public static List<CommerceDiscountAccountRel> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce discount account rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce discount account rels.
	 *
	 * @return the number of commerce discount account rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceDiscountAccountRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceDiscountAccountRelPersistence,
		 CommerceDiscountAccountRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceDiscountAccountRelPersistence.class);

		ServiceTracker
			<CommerceDiscountAccountRelPersistence,
			 CommerceDiscountAccountRelPersistence> serviceTracker =
				new ServiceTracker
					<CommerceDiscountAccountRelPersistence,
					 CommerceDiscountAccountRelPersistence>(
						 bundle.getBundleContext(),
						 CommerceDiscountAccountRelPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}