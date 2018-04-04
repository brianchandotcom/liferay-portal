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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPOptionCategory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp option category service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPOptionCategoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPOptionCategoryPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPOptionCategoryPersistenceImpl
 * @generated
 */
@ProviderType
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
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
	public static CPOptionCategory update(CPOptionCategory cpOptionCategory,
		ServiceContext serviceContext) {
		return getPersistence().update(cpOptionCategory, serviceContext);
	}

	/**
	* Returns all the cp option categories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp option categories
	*/
	public static List<CPOptionCategory> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp option categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp option categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp option category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public static CPOptionCategory findByUuid_First(java.lang.String uuid,
		OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp option category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CPOptionCategory> orderByComparator) {
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
	public static CPOptionCategory findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp option category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPOptionCategory> orderByComparator) {
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
		long CPOptionCategoryId, java.lang.String uuid,
		OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPOptionCategoryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp option categories where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp option categories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp option categories
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp option category where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPOptionCategoryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public static CPOptionCategory findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp option category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp option category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp option category where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp option category that was removed
	*/
	public static CPOptionCategory removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp option categories where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp option categories
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp option categories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp option categories
	*/
	public static List<CPOptionCategory> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp option categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp option categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
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
	public static CPOptionCategory findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
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
	public static CPOptionCategory findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp option category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
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
		long CPOptionCategoryId, java.lang.String uuid, long companyId,
		OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPOptionCategoryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp option categories where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp option categories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp option categories
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp option categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp option categories
	*/
	public static List<CPOptionCategory> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp option categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp option categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPOptionCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp option category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public static CPOptionCategory findByGroupId_First(long groupId,
		OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first cp option category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByGroupId_First(long groupId,
		OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last cp option category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public static CPOptionCategory findByGroupId_Last(long groupId,
		OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last cp option category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByGroupId_Last(long groupId,
		OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the cp option categories before and after the current cp option category in the ordered set where groupId = &#63;.
	*
	* @param CPOptionCategoryId the primary key of the current cp option category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option category
	* @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	*/
	public static CPOptionCategory[] findByGroupId_PrevAndNext(
		long CPOptionCategoryId, long groupId,
		OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(CPOptionCategoryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the cp option categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching cp option categories that the user has permission to view
	*/
	public static List<CPOptionCategory> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the cp option categories that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of matching cp option categories that the user has permission to view
	*/
	public static List<CPOptionCategory> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the cp option categories that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option categories that the user has permission to view
	*/
	public static List<CPOptionCategory> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the cp option categories before and after the current cp option category in the ordered set of cp option categories that the user has permission to view where groupId = &#63;.
	*
	* @param CPOptionCategoryId the primary key of the current cp option category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp option category
	* @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	*/
	public static CPOptionCategory[] filterFindByGroupId_PrevAndNext(
		long CPOptionCategoryId, long groupId,
		OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(CPOptionCategoryId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the cp option categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp option categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp option categories
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of cp option categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp option categories that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @return the range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp option categories where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option categories where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp option categories
	*/
	public static List<CPOptionCategory> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp option category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public static CPOptionCategory findByCompanyId_First(long companyId,
		OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first cp option category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByCompanyId_First(long companyId,
		OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last cp option category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public static CPOptionCategory findByCompanyId_Last(long companyId,
		OrderByComparator<CPOptionCategory> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last cp option category in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByCompanyId_Last(long companyId,
		OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
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
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(CPOptionCategoryId, companyId,
			orderByComparator);
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
	* Returns the cp option category where groupId = &#63; and key = &#63; or throws a {@link NoSuchCPOptionCategoryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp option category
	* @throws NoSuchCPOptionCategoryException if a matching cp option category could not be found
	*/
	public static CPOptionCategory findByG_K(long groupId, java.lang.String key)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence().findByG_K(groupId, key);
	}

	/**
	* Returns the cp option category where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByG_K(long groupId, java.lang.String key) {
		return getPersistence().fetchByG_K(groupId, key);
	}

	/**
	* Returns the cp option category where groupId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp option category, or <code>null</code> if a matching cp option category could not be found
	*/
	public static CPOptionCategory fetchByG_K(long groupId,
		java.lang.String key, boolean retrieveFromCache) {
		return getPersistence().fetchByG_K(groupId, key, retrieveFromCache);
	}

	/**
	* Removes the cp option category where groupId = &#63; and key = &#63; from the database.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the cp option category that was removed
	*/
	public static CPOptionCategory removeByG_K(long groupId,
		java.lang.String key)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence().removeByG_K(groupId, key);
	}

	/**
	* Returns the number of cp option categories where groupId = &#63; and key = &#63;.
	*
	* @param groupId the group ID
	* @param key the key
	* @return the number of matching cp option categories
	*/
	public static int countByG_K(long groupId, java.lang.String key) {
		return getPersistence().countByG_K(groupId, key);
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
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
		return getPersistence().remove(CPOptionCategoryId);
	}

	public static CPOptionCategory updateImpl(CPOptionCategory cpOptionCategory) {
		return getPersistence().updateImpl(cpOptionCategory);
	}

	/**
	* Returns the cp option category with the primary key or throws a {@link NoSuchCPOptionCategoryException} if it could not be found.
	*
	* @param CPOptionCategoryId the primary key of the cp option category
	* @return the cp option category
	* @throws NoSuchCPOptionCategoryException if a cp option category with the primary key could not be found
	*/
	public static CPOptionCategory findByPrimaryKey(long CPOptionCategoryId)
		throws com.liferay.commerce.product.exception.NoSuchCPOptionCategoryException {
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

	public static java.util.Map<java.io.Serializable, CPOptionCategory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp option categories
	*/
	public static List<CPOptionCategory> findAll(int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp option categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option categories
	* @param end the upper bound of the range of cp option categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp option categories
	*/
	public static List<CPOptionCategory> findAll(int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
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

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPOptionCategoryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPOptionCategoryPersistence, CPOptionCategoryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPOptionCategoryPersistence.class);

		ServiceTracker<CPOptionCategoryPersistence, CPOptionCategoryPersistence> serviceTracker =
			new ServiceTracker<CPOptionCategoryPersistence, CPOptionCategoryPersistence>(bundle.getBundleContext(),
				CPOptionCategoryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}