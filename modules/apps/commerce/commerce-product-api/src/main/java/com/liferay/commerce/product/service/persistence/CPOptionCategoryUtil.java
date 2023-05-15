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

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the cp option category service. This utility wraps <code>com.liferay.commerce.product.service.persistence.impl.CPOptionCategoryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPOptionCategoryPersistence
 * @generated
 */
public class CPOptionCategoryUtil {

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
	public static void clearCache(CPOptionCategory cpOptionCategory) {
		getPersistence().clearCache(cpOptionCategory);
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
	public static Map<Serializable, CPOptionCategory> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPOptionCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPOptionCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPOptionCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPOptionCategory update(CPOptionCategory cpOptionCategory) {
		return getPersistence().update(cpOptionCategory);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPOptionCategory update(
		CPOptionCategory cpOptionCategory, ServiceContext serviceContext) {

		return getPersistence().update(cpOptionCategory, serviceContext);
	}

	/**
	 * Returns all the cp option categories where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp option categories
	 */
	public static List<CPOptionCategory> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the cp option categories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @return the range of matching cp option categories
	 */
	public static List<CPOptionCategory> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cp option categories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option categories
	 */
	public static List<CPOptionCategory> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp option categories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp option categories
	 */
	public static List<CPOptionCategory> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp option category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option category
	 * @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	 */
	public static CPOptionCategory findByUuid_First(
			String uuid, OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first cp option category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	 */
	public static CPOptionCategory fetchByUuid_First(
		String uuid, OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp option category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option category
	 * @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	 */
	public static CPOptionCategory findByUuid_Last(
			String uuid, OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last cp option category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	 */
	public static CPOptionCategory fetchByUuid_Last(
		String uuid, OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the cp option categories before and after the current cp option category in the ordered set where uuid = &#63;.
	 *
	 * @param CPOptionCategoryId the primary key of the current cp option category
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option category
	 * @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	 */
	public static CPOptionCategory[] findByUuid_PrevAndNext(
			long CPOptionCategoryId, String uuid,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByUuid_PrevAndNext(
			CPOptionCategoryId, uuid, orderByComparator);
	}

	/**
	 * Returns all the cp option categories that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp option categories that the user has permission to view
	 */
	public static List<CPOptionCategory> filterFindByUuid(String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	 * Returns a range of all the cp option categories that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @return the range of matching cp option categories that the user has permission to view
	 */
	public static List<CPOptionCategory> filterFindByUuid(
		String uuid, int start, int end) {

		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the cp option categories that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option categories that the user has permission to view
	 */
	public static List<CPOptionCategory> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().filterFindByUuid(
			uuid, start, end, orderByComparator);
	}

	/**
	 * Returns the cp option categories before and after the current cp option category in the ordered set of cp option categories that the user has permission to view where uuid = &#63;.
	 *
	 * @param CPOptionCategoryId the primary key of the current cp option category
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option category
	 * @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	 */
	public static CPOptionCategory[] filterFindByUuid_PrevAndNext(
			long CPOptionCategoryId, String uuid,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().filterFindByUuid_PrevAndNext(
			CPOptionCategoryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the cp option categories where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of cp option categories where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp option categories
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the number of cp option categories that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp option categories that the user has permission to view
	 */
	public static int filterCountByUuid(String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	 * Returns all the cp option categories where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp option categories
	 */
	public static List<CPOptionCategory> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the cp option categories where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @return the range of matching cp option categories
	 */
	public static List<CPOptionCategory> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp option categories where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option categories
	 */
	public static List<CPOptionCategory> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp option categories where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp option categories
	 */
	public static List<CPOptionCategory> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option category
	 * @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	 */
	public static CPOptionCategory findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	 */
	public static CPOptionCategory fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option category
	 * @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	 */
	public static CPOptionCategory findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	 */
	public static CPOptionCategory fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the cp option categories before and after the current cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPOptionCategoryId the primary key of the current cp option category
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option category
	 * @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	 */
	public static CPOptionCategory[] findByUuid_C_PrevAndNext(
			long CPOptionCategoryId, String uuid, long companyId,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			CPOptionCategoryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Returns all the cp option categories that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp option categories that the user has permission to view
	 */
	public static List<CPOptionCategory> filterFindByUuid_C(
		String uuid, long companyId) {

		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the cp option categories that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @return the range of matching cp option categories that the user has permission to view
	 */
	public static List<CPOptionCategory> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp option categories that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option categories that the user has permission to view
	 */
	public static List<CPOptionCategory> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().filterFindByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the cp option categories before and after the current cp option category in the ordered set of cp option categories that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPOptionCategoryId the primary key of the current cp option category
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option category
	 * @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	 */
	public static CPOptionCategory[] filterFindByUuid_C_PrevAndNext(
			long CPOptionCategoryId, String uuid, long companyId,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().filterFindByUuid_C_PrevAndNext(
			CPOptionCategoryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp option categories where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of cp option categories where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp option categories
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of cp option categories that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp option categories that the user has permission to view
	 */
	public static int filterCountByUuid_C(String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the cp option categories where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp option categories
	 */
	public static List<CPOptionCategory> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the cp option categories where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @return the range of matching cp option categories
	 */
	public static List<CPOptionCategory> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp option categories where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option categories
	 */
	public static List<CPOptionCategory> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp option categories where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp option categories
	 */
	public static List<CPOptionCategory> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp option category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option category
	 * @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	 */
	public static CPOptionCategory findByCompanyId_First(
			long companyId,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first cp option category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	 */
	public static CPOptionCategory fetchByCompanyId_First(
		long companyId, OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last cp option category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option category
	 * @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	 */
	public static CPOptionCategory findByCompanyId_Last(
			long companyId,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last cp option category in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	 */
	public static CPOptionCategory fetchByCompanyId_Last(
		long companyId, OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the cp option categories before and after the current cp option category in the ordered set where companyId = &#63;.
	 *
	 * @param CPOptionCategoryId the primary key of the current cp option category
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option category
	 * @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	 */
	public static CPOptionCategory[] findByCompanyId_PrevAndNext(
			long CPOptionCategoryId, long companyId,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			CPOptionCategoryId, companyId, orderByComparator);
	}

	/**
	 * Returns all the cp option categories that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp option categories that the user has permission to view
	 */
	public static List<CPOptionCategory> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the cp option categories that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @return the range of matching cp option categories that the user has permission to view
	 */
	public static List<CPOptionCategory> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp option categories that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option categories that the user has permission to view
	 */
	public static List<CPOptionCategory> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the cp option categories before and after the current cp option category in the ordered set of cp option categories that the user has permission to view where companyId = &#63;.
	 *
	 * @param CPOptionCategoryId the primary key of the current cp option category
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option category
	 * @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	 */
	public static CPOptionCategory[] filterFindByCompanyId_PrevAndNext(
			long CPOptionCategoryId, long companyId,
			OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			CPOptionCategoryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the cp option categories where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of cp option categories where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp option categories
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of cp option categories that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp option categories that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns the cp option category where companyId = &#63; and key = &#63; or throws a <code>NoSuchCPOptionCategoryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching cp option category
	 * @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	 */
	public static CPOptionCategory findByC_K(long companyId, String key)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByC_K(companyId, key);
	}

	/**
	 * Returns the cp option category where companyId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	 */
	public static CPOptionCategory fetchByC_K(long companyId, String key) {
		return getPersistence().fetchByC_K(companyId, key);
	}

	/**
	 * Returns the cp option category where companyId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	 */
	public static CPOptionCategory fetchByC_K(
		long companyId, String key, boolean useFinderCache) {

		return getPersistence().fetchByC_K(companyId, key, useFinderCache);
	}

	/**
	 * Removes the cp option category where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the cp option category that was removed
	 */
	public static CPOptionCategory removeByC_K(long companyId, String key)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().removeByC_K(companyId, key);
	}

	/**
	 * Returns the number of cp option categories where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching cp option categories
	 */
	public static int countByC_K(long companyId, String key) {
		return getPersistence().countByC_K(companyId, key);
	}

	/**
	 * Caches the cp option category in the entity cache if it is enabled.
	 *
	 * @param cpOptionCategory the cp option category
	 */
	public static void cacheResult(CPOptionCategory cpOptionCategory) {
		getPersistence().cacheResult(cpOptionCategory);
	}

	/**
	 * Caches the cp option categories in the entity cache if it is enabled.
	 *
	 * @param cpOptionCategories the cp option categories
	 */
	public static void cacheResult(List<CPOptionCategory> cpOptionCategories) {
		getPersistence().cacheResult(cpOptionCategories);
	}

	/**
	 * Creates a new cp option category with the primary key. Does not add the cp option category to the database.
	 *
	 * @param CPOptionCategoryId the primary key for the new cp option category
	 * @return the new cp option category
	 */
	public static CPOptionCategory create(long CPOptionCategoryId) {
		return getPersistence().create(CPOptionCategoryId);
	}

	/**
	 * Removes the cp option category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPOptionCategoryId the primary key of the cp option category
	 * @return the cp option category that was removed
	 * @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	 */
	public static CPOptionCategory remove(long CPOptionCategoryId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().remove(CPOptionCategoryId);
	}

	public static CPOptionCategory updateImpl(
		CPOptionCategory cpOptionCategory) {

		return getPersistence().updateImpl(cpOptionCategory);
	}

	/**
	 * Returns the cp option category with the primary key or throws a <code>NoSuchCPOptionCategoryException</code> if it could not be found.
	 *
	 * @param CPOptionCategoryId the primary key of the cp option category
	 * @return the cp option category
	 * @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	 */
	public static CPOptionCategory findByPrimaryKey(long CPOptionCategoryId)
		throws com.liferay.commerce.product.exception.
			NoSuchCPOptionCategoryException {

		return getPersistence().findByPrimaryKey(CPOptionCategoryId);
	}

	/**
	 * Returns the cp option category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPOptionCategoryId the primary key of the cp option category
	 * @return the cp option category, or <code>null</code> if a cp option category with the primary key could not be found
	 */
	public static CPOptionCategory fetchByPrimaryKey(long CPOptionCategoryId) {
		return getPersistence().fetchByPrimaryKey(CPOptionCategoryId);
	}

	/**
	 * Returns all the cp option categories.
	 *
	 * @return the cp option categories
	 */
	public static List<CPOptionCategory> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp option categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @return the range of cp option categories
	 */
	public static List<CPOptionCategory> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp option categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp option categories
	 */
	public static List<CPOptionCategory> findAll(
		int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp option categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionCategoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp option categories
	 * @param end the upper bound of the range of cp option categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp option categories
	 */
	public static List<CPOptionCategory> findAll(
		int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp option categories from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp option categories.
	 *
	 * @return the number of cp option categories
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPOptionCategoryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CPOptionCategoryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CPOptionCategoryPersistence _persistence;

}