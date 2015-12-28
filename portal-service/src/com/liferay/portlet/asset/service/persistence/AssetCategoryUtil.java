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

package com.liferay.portlet.asset.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.asset.model.AssetCategory;

import java.util.List;

/**
 * The persistence utility for the asset category service. This utility wraps {@link com.liferay.portlet.asset.service.persistence.impl.AssetCategoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPersistence
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetCategoryPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetCategoryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(AssetCategory assetCategory) {
		getPersistence().clearCache(assetCategory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetCategory update(AssetCategory assetCategory) {
		return getPersistence().update(assetCategory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetCategory update(AssetCategory assetCategory,
		ServiceContext serviceContext) {
		return getPersistence().update(assetCategory, serviceContext);
	}

	/**
	* Returns all the asset categories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the asset categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByUuid_First(java.lang.String uuid,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByUuid_Last(java.lang.String uuid,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where uuid = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByUuid_PrevAndNext(long categoryId,
		java.lang.String uuid,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(categoryId, uuid, orderByComparator);
	}

	/**
	* Removes all the asset categories where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of asset categories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching asset categories
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the asset category where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCategoryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the asset category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the asset category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the asset category where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the asset category that was removed
	*/
	public static AssetCategory removeByUUID_G(java.lang.String uuid,
		long groupId) throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of asset categories where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching asset categories
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the asset categories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the asset categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByUuid_C_PrevAndNext(long categoryId,
		java.lang.String uuid, long companyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(categoryId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the asset categories where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of asset categories where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching asset categories
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the asset categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByGroupId_First(long groupId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByGroupId_First(long groupId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByGroupId_Last(long groupId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByGroupId_Last(long groupId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(categoryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the asset categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset categories that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] filterFindByGroupId_PrevAndNext(
		long categoryId, long groupId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(categoryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the asset categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of asset categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset categories
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of asset categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset categories that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the asset categories where parentCategoryId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByParentCategoryId(
		long parentCategoryId) {
		return getPersistence().findByParentCategoryId(parentCategoryId);
	}

	/**
	* Returns a range of all the asset categories where parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end) {
		return getPersistence()
				   .findByParentCategoryId(parentCategoryId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByParentCategoryId(parentCategoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByParentCategoryId(parentCategoryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where parentCategoryId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByParentCategoryId_First(
		long parentCategoryId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_First(parentCategoryId,
			orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where parentCategoryId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByParentCategoryId_First(
		long parentCategoryId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByParentCategoryId_First(parentCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where parentCategoryId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByParentCategoryId_Last(
		long parentCategoryId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_Last(parentCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where parentCategoryId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByParentCategoryId_Last(
		long parentCategoryId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByParentCategoryId_Last(parentCategoryId,
			orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByParentCategoryId_PrevAndNext(
		long categoryId, long parentCategoryId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_PrevAndNext(categoryId,
			parentCategoryId, orderByComparator);
	}

	/**
	* Removes all the asset categories where parentCategoryId = &#63; from the database.
	*
	* @param parentCategoryId the parent category ID
	*/
	public static void removeByParentCategoryId(long parentCategoryId) {
		getPersistence().removeByParentCategoryId(parentCategoryId);
	}

	/**
	* Returns the number of asset categories where parentCategoryId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @return the number of matching asset categories
	*/
	public static int countByParentCategoryId(long parentCategoryId) {
		return getPersistence().countByParentCategoryId(parentCategoryId);
	}

	/**
	* Returns all the asset categories where vocabularyId = &#63;.
	*
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByVocabularyId(long vocabularyId) {
		return getPersistence().findByVocabularyId(vocabularyId);
	}

	/**
	* Returns a range of all the asset categories where vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByVocabularyId(long vocabularyId,
		int start, int end) {
		return getPersistence().findByVocabularyId(vocabularyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByVocabularyId(long vocabularyId,
		int start, int end, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByVocabularyId(vocabularyId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByVocabularyId(long vocabularyId,
		int start, int end, OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByVocabularyId(vocabularyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where vocabularyId = &#63;.
	*
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByVocabularyId_First(long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByVocabularyId_First(vocabularyId, orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where vocabularyId = &#63;.
	*
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByVocabularyId_First(long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByVocabularyId_First(vocabularyId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where vocabularyId = &#63;.
	*
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByVocabularyId_Last(long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByVocabularyId_Last(vocabularyId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where vocabularyId = &#63;.
	*
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByVocabularyId_Last(long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByVocabularyId_Last(vocabularyId, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByVocabularyId_PrevAndNext(
		long categoryId, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByVocabularyId_PrevAndNext(categoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Removes all the asset categories where vocabularyId = &#63; from the database.
	*
	* @param vocabularyId the vocabulary ID
	*/
	public static void removeByVocabularyId(long vocabularyId) {
		getPersistence().removeByVocabularyId(vocabularyId);
	}

	/**
	* Returns the number of asset categories where vocabularyId = &#63;.
	*
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories
	*/
	public static int countByVocabularyId(long vocabularyId) {
		return getPersistence().countByVocabularyId(vocabularyId);
	}

	/**
	* Returns all the asset categories where groupId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByG_V(long groupId, long vocabularyId) {
		return getPersistence().findByG_V(groupId, vocabularyId);
	}

	/**
	* Returns a range of all the asset categories where groupId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByG_V(long groupId,
		long vocabularyId, int start, int end) {
		return getPersistence().findByG_V(groupId, vocabularyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_V(long groupId,
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByG_V(groupId, vocabularyId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_V(long groupId,
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_V(groupId, vocabularyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByG_V_First(long groupId,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_V_First(groupId, vocabularyId, orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByG_V_First(long groupId,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByG_V_First(groupId, vocabularyId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByG_V_Last(long groupId, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_V_Last(groupId, vocabularyId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByG_V_Last(long groupId,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByG_V_Last(groupId, vocabularyId, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByG_V_PrevAndNext(long categoryId,
		long groupId, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_V_PrevAndNext(categoryId, groupId, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_V(long groupId,
		long vocabularyId) {
		return getPersistence().filterFindByG_V(groupId, vocabularyId);
	}

	/**
	* Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_V(long groupId,
		long vocabularyId, int start, int end) {
		return getPersistence()
				   .filterFindByG_V(groupId, vocabularyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_V(long groupId,
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .filterFindByG_V(groupId, vocabularyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] filterFindByG_V_PrevAndNext(long categoryId,
		long groupId, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByG_V_PrevAndNext(categoryId, groupId,
			vocabularyId, orderByComparator);
	}

	/**
	* Returns all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = any &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyIds the vocabulary IDs
	* @return the matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_V(long groupId,
		long[] vocabularyIds) {
		return getPersistence().filterFindByG_V(groupId, vocabularyIds);
	}

	/**
	* Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyIds the vocabulary IDs
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_V(long groupId,
		long[] vocabularyIds, int start, int end) {
		return getPersistence()
				   .filterFindByG_V(groupId, vocabularyIds, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyIds the vocabulary IDs
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_V(long groupId,
		long[] vocabularyIds, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .filterFindByG_V(groupId, vocabularyIds, start, end,
			orderByComparator);
	}

	/**
	* Returns all the asset categories where groupId = &#63; and vocabularyId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyIds the vocabulary IDs
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByG_V(long groupId,
		long[] vocabularyIds) {
		return getPersistence().findByG_V(groupId, vocabularyIds);
	}

	/**
	* Returns a range of all the asset categories where groupId = &#63; and vocabularyId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyIds the vocabulary IDs
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByG_V(long groupId,
		long[] vocabularyIds, int start, int end) {
		return getPersistence().findByG_V(groupId, vocabularyIds, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and vocabularyId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyIds the vocabulary IDs
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_V(long groupId,
		long[] vocabularyIds, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByG_V(groupId, vocabularyIds, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and vocabularyId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_V(long groupId,
		long[] vocabularyIds, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_V(groupId, vocabularyIds, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset categories where groupId = &#63; and vocabularyId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	*/
	public static void removeByG_V(long groupId, long vocabularyId) {
		getPersistence().removeByG_V(groupId, vocabularyId);
	}

	/**
	* Returns the number of asset categories where groupId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories
	*/
	public static int countByG_V(long groupId, long vocabularyId) {
		return getPersistence().countByG_V(groupId, vocabularyId);
	}

	/**
	* Returns the number of asset categories where groupId = &#63; and vocabularyId = any &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyIds the vocabulary IDs
	* @return the number of matching asset categories
	*/
	public static int countByG_V(long groupId, long[] vocabularyIds) {
		return getPersistence().countByG_V(groupId, vocabularyIds);
	}

	/**
	* Returns the number of asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories that the user has permission to view
	*/
	public static int filterCountByG_V(long groupId, long vocabularyId) {
		return getPersistence().filterCountByG_V(groupId, vocabularyId);
	}

	/**
	* Returns the number of asset categories that the user has permission to view where groupId = &#63; and vocabularyId = any &#63;.
	*
	* @param groupId the group ID
	* @param vocabularyIds the vocabulary IDs
	* @return the number of matching asset categories that the user has permission to view
	*/
	public static int filterCountByG_V(long groupId, long[] vocabularyIds) {
		return getPersistence().filterCountByG_V(groupId, vocabularyIds);
	}

	/**
	* Returns all the asset categories where parentCategoryId = &#63; and name = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByP_N(long parentCategoryId,
		java.lang.String name) {
		return getPersistence().findByP_N(parentCategoryId, name);
	}

	/**
	* Returns a range of all the asset categories where parentCategoryId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByP_N(long parentCategoryId,
		java.lang.String name, int start, int end) {
		return getPersistence().findByP_N(parentCategoryId, name, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where parentCategoryId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByP_N(long parentCategoryId,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByP_N(parentCategoryId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where parentCategoryId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByP_N(long parentCategoryId,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByP_N(parentCategoryId, name, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByP_N_First(long parentCategoryId,
		java.lang.String name,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_N_First(parentCategoryId, name, orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByP_N_First(long parentCategoryId,
		java.lang.String name,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByP_N_First(parentCategoryId, name, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByP_N_Last(long parentCategoryId,
		java.lang.String name,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_N_Last(parentCategoryId, name, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByP_N_Last(long parentCategoryId,
		java.lang.String name,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByP_N_Last(parentCategoryId, name, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByP_N_PrevAndNext(long categoryId,
		long parentCategoryId, java.lang.String name,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_N_PrevAndNext(categoryId, parentCategoryId, name,
			orderByComparator);
	}

	/**
	* Removes all the asset categories where parentCategoryId = &#63; and name = &#63; from the database.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	*/
	public static void removeByP_N(long parentCategoryId, java.lang.String name) {
		getPersistence().removeByP_N(parentCategoryId, name);
	}

	/**
	* Returns the number of asset categories where parentCategoryId = &#63; and name = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @return the number of matching asset categories
	*/
	public static int countByP_N(long parentCategoryId, java.lang.String name) {
		return getPersistence().countByP_N(parentCategoryId, name);
	}

	/**
	* Returns all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId) {
		return getPersistence().findByP_V(parentCategoryId, vocabularyId);
	}

	/**
	* Returns a range of all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId, int start, int end) {
		return getPersistence()
				   .findByP_V(parentCategoryId, vocabularyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByP_V(parentCategoryId, vocabularyId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByP_V(parentCategoryId, vocabularyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByP_V_First(long parentCategoryId,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_First(parentCategoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByP_V_First(long parentCategoryId,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByP_V_First(parentCategoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByP_V_Last(long parentCategoryId,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_Last(parentCategoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByP_V_Last(long parentCategoryId,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByP_V_Last(parentCategoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByP_V_PrevAndNext(long categoryId,
		long parentCategoryId, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_PrevAndNext(categoryId, parentCategoryId,
			vocabularyId, orderByComparator);
	}

	/**
	* Removes all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63; from the database.
	*
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	*/
	public static void removeByP_V(long parentCategoryId, long vocabularyId) {
		getPersistence().removeByP_V(parentCategoryId, vocabularyId);
	}

	/**
	* Returns the number of asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories
	*/
	public static int countByP_V(long parentCategoryId, long vocabularyId) {
		return getPersistence().countByP_V(parentCategoryId, vocabularyId);
	}

	/**
	* Returns all the asset categories where name = &#63; and vocabularyId = &#63;.
	*
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByN_V(java.lang.String name,
		long vocabularyId) {
		return getPersistence().findByN_V(name, vocabularyId);
	}

	/**
	* Returns a range of all the asset categories where name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByN_V(java.lang.String name,
		long vocabularyId, int start, int end) {
		return getPersistence().findByN_V(name, vocabularyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByN_V(java.lang.String name,
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByN_V(name, vocabularyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByN_V(java.lang.String name,
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByN_V(name, vocabularyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	*
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByN_V_First(java.lang.String name,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByN_V_First(name, vocabularyId, orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	*
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByN_V_First(java.lang.String name,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByN_V_First(name, vocabularyId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	*
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByN_V_Last(java.lang.String name,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByN_V_Last(name, vocabularyId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	*
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByN_V_Last(java.lang.String name,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByN_V_Last(name, vocabularyId, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByN_V_PrevAndNext(long categoryId,
		java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByN_V_PrevAndNext(categoryId, name, vocabularyId,
			orderByComparator);
	}

	/**
	* Removes all the asset categories where name = &#63; and vocabularyId = &#63; from the database.
	*
	* @param name the name
	* @param vocabularyId the vocabulary ID
	*/
	public static void removeByN_V(java.lang.String name, long vocabularyId) {
		getPersistence().removeByN_V(name, vocabularyId);
	}

	/**
	* Returns the number of asset categories where name = &#63; and vocabularyId = &#63;.
	*
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories
	*/
	public static int countByN_V(java.lang.String name, long vocabularyId) {
		return getPersistence().countByN_V(name, vocabularyId);
	}

	/**
	* Returns all the asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByG_P_V(long groupId,
		long parentCategoryId, long vocabularyId) {
		return getPersistence()
				   .findByG_P_V(groupId, parentCategoryId, vocabularyId);
	}

	/**
	* Returns a range of all the asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByG_P_V(long groupId,
		long parentCategoryId, long vocabularyId, int start, int end) {
		return getPersistence()
				   .findByG_P_V(groupId, parentCategoryId, vocabularyId, start,
			end);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_P_V(long groupId,
		long parentCategoryId, long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByG_P_V(groupId, parentCategoryId, vocabularyId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_P_V(long groupId,
		long parentCategoryId, long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_V(groupId, parentCategoryId, vocabularyId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByG_P_V_First(long groupId,
		long parentCategoryId, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_V_First(groupId, parentCategoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByG_P_V_First(long groupId,
		long parentCategoryId, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_V_First(groupId, parentCategoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByG_P_V_Last(long groupId,
		long parentCategoryId, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_V_Last(groupId, parentCategoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByG_P_V_Last(long groupId,
		long parentCategoryId, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_V_Last(groupId, parentCategoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByG_P_V_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_V_PrevAndNext(categoryId, groupId,
			parentCategoryId, vocabularyId, orderByComparator);
	}

	/**
	* Returns all the asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_P_V(long groupId,
		long parentCategoryId, long vocabularyId) {
		return getPersistence()
				   .filterFindByG_P_V(groupId, parentCategoryId, vocabularyId);
	}

	/**
	* Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_P_V(long groupId,
		long parentCategoryId, long vocabularyId, int start, int end) {
		return getPersistence()
				   .filterFindByG_P_V(groupId, parentCategoryId, vocabularyId,
			start, end);
	}

	/**
	* Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_P_V(long groupId,
		long parentCategoryId, long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .filterFindByG_P_V(groupId, parentCategoryId, vocabularyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] filterFindByG_P_V_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByG_P_V_PrevAndNext(categoryId, groupId,
			parentCategoryId, vocabularyId, orderByComparator);
	}

	/**
	* Removes all the asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	*/
	public static void removeByG_P_V(long groupId, long parentCategoryId,
		long vocabularyId) {
		getPersistence().removeByG_P_V(groupId, parentCategoryId, vocabularyId);
	}

	/**
	* Returns the number of asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories
	*/
	public static int countByG_P_V(long groupId, long parentCategoryId,
		long vocabularyId) {
		return getPersistence()
				   .countByG_P_V(groupId, parentCategoryId, vocabularyId);
	}

	/**
	* Returns the number of asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories that the user has permission to view
	*/
	public static int filterCountByG_P_V(long groupId, long parentCategoryId,
		long vocabularyId) {
		return getPersistence()
				   .filterCountByG_P_V(groupId, parentCategoryId, vocabularyId);
	}

	/**
	* Returns all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByG_LikeN_V(long groupId,
		java.lang.String name, long vocabularyId) {
		return getPersistence().findByG_LikeN_V(groupId, name, vocabularyId);
	}

	/**
	* Returns a range of all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByG_LikeN_V(long groupId,
		java.lang.String name, long vocabularyId, int start, int end) {
		return getPersistence()
				   .findByG_LikeN_V(groupId, name, vocabularyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_LikeN_V(long groupId,
		java.lang.String name, long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByG_LikeN_V(groupId, name, vocabularyId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_LikeN_V(long groupId,
		java.lang.String name, long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LikeN_V(groupId, name, vocabularyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByG_LikeN_V_First(long groupId,
		java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_LikeN_V_First(groupId, name, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByG_LikeN_V_First(long groupId,
		java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeN_V_First(groupId, name, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByG_LikeN_V_Last(long groupId,
		java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_LikeN_V_Last(groupId, name, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByG_LikeN_V_Last(long groupId,
		java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeN_V_Last(groupId, name, vocabularyId,
			orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByG_LikeN_V_PrevAndNext(long categoryId,
		long groupId, java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_LikeN_V_PrevAndNext(categoryId, groupId, name,
			vocabularyId, orderByComparator);
	}

	/**
	* Returns all the asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_LikeN_V(long groupId,
		java.lang.String name, long vocabularyId) {
		return getPersistence()
				   .filterFindByG_LikeN_V(groupId, name, vocabularyId);
	}

	/**
	* Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_LikeN_V(long groupId,
		java.lang.String name, long vocabularyId, int start, int end) {
		return getPersistence()
				   .filterFindByG_LikeN_V(groupId, name, vocabularyId, start,
			end);
	}

	/**
	* Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_LikeN_V(long groupId,
		java.lang.String name, long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LikeN_V(groupId, name, vocabularyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] filterFindByG_LikeN_V_PrevAndNext(
		long categoryId, long groupId, java.lang.String name,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByG_LikeN_V_PrevAndNext(categoryId, groupId,
			name, vocabularyId, orderByComparator);
	}

	/**
	* Returns all the asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyIds the vocabulary IDs
	* @return the matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_LikeN_V(long groupId,
		java.lang.String name, long[] vocabularyIds) {
		return getPersistence()
				   .filterFindByG_LikeN_V(groupId, name, vocabularyIds);
	}

	/**
	* Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyIds the vocabulary IDs
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_LikeN_V(long groupId,
		java.lang.String name, long[] vocabularyIds, int start, int end) {
		return getPersistence()
				   .filterFindByG_LikeN_V(groupId, name, vocabularyIds, start,
			end);
	}

	/**
	* Returns an ordered range of all the asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyIds the vocabulary IDs
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_LikeN_V(long groupId,
		java.lang.String name, long[] vocabularyIds, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LikeN_V(groupId, name, vocabularyIds, start,
			end, orderByComparator);
	}

	/**
	* Returns all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyIds the vocabulary IDs
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByG_LikeN_V(long groupId,
		java.lang.String name, long[] vocabularyIds) {
		return getPersistence().findByG_LikeN_V(groupId, name, vocabularyIds);
	}

	/**
	* Returns a range of all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyIds the vocabulary IDs
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByG_LikeN_V(long groupId,
		java.lang.String name, long[] vocabularyIds, int start, int end) {
		return getPersistence()
				   .findByG_LikeN_V(groupId, name, vocabularyIds, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyIds the vocabulary IDs
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_LikeN_V(long groupId,
		java.lang.String name, long[] vocabularyIds, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByG_LikeN_V(groupId, name, vocabularyIds, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_LikeN_V(long groupId,
		java.lang.String name, long[] vocabularyIds, int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LikeN_V(groupId, name, vocabularyIds, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	*/
	public static void removeByG_LikeN_V(long groupId, java.lang.String name,
		long vocabularyId) {
		getPersistence().removeByG_LikeN_V(groupId, name, vocabularyId);
	}

	/**
	* Returns the number of asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories
	*/
	public static int countByG_LikeN_V(long groupId, java.lang.String name,
		long vocabularyId) {
		return getPersistence().countByG_LikeN_V(groupId, name, vocabularyId);
	}

	/**
	* Returns the number of asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyIds the vocabulary IDs
	* @return the number of matching asset categories
	*/
	public static int countByG_LikeN_V(long groupId, java.lang.String name,
		long[] vocabularyIds) {
		return getPersistence().countByG_LikeN_V(groupId, name, vocabularyIds);
	}

	/**
	* Returns the number of asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories that the user has permission to view
	*/
	public static int filterCountByG_LikeN_V(long groupId,
		java.lang.String name, long vocabularyId) {
		return getPersistence()
				   .filterCountByG_LikeN_V(groupId, name, vocabularyId);
	}

	/**
	* Returns the number of asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param vocabularyIds the vocabulary IDs
	* @return the number of matching asset categories that the user has permission to view
	*/
	public static int filterCountByG_LikeN_V(long groupId,
		java.lang.String name, long[] vocabularyIds) {
		return getPersistence()
				   .filterCountByG_LikeN_V(groupId, name, vocabularyIds);
	}

	/**
	* Returns the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or throws a {@link NoSuchCategoryException} if it could not be found.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByP_N_V(long parentCategoryId,
		java.lang.String name, long vocabularyId)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByP_N_V(parentCategoryId, name, vocabularyId);
	}

	/**
	* Returns the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByP_N_V(long parentCategoryId,
		java.lang.String name, long vocabularyId) {
		return getPersistence()
				   .fetchByP_N_V(parentCategoryId, name, vocabularyId);
	}

	/**
	* Returns the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByP_N_V(long parentCategoryId,
		java.lang.String name, long vocabularyId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByP_N_V(parentCategoryId, name, vocabularyId,
			retrieveFromCache);
	}

	/**
	* Removes the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; from the database.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the asset category that was removed
	*/
	public static AssetCategory removeByP_N_V(long parentCategoryId,
		java.lang.String name, long vocabularyId)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .removeByP_N_V(parentCategoryId, name, vocabularyId);
	}

	/**
	* Returns the number of asset categories where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories
	*/
	public static int countByP_N_V(long parentCategoryId,
		java.lang.String name, long vocabularyId) {
		return getPersistence()
				   .countByP_N_V(parentCategoryId, name, vocabularyId);
	}

	/**
	* Returns all the asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories
	*/
	public static List<AssetCategory> findByG_P_N_V(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId) {
		return getPersistence()
				   .findByG_P_N_V(groupId, parentCategoryId, name, vocabularyId);
	}

	/**
	* Returns a range of all the asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories
	*/
	public static List<AssetCategory> findByG_P_N_V(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId,
		int start, int end) {
		return getPersistence()
				   .findByG_P_N_V(groupId, parentCategoryId, name,
			vocabularyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_P_N_V(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId,
		int start, int end, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .findByG_P_N_V(groupId, parentCategoryId, name,
			vocabularyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset categories
	*/
	public static List<AssetCategory> findByG_P_N_V(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId,
		int start, int end, OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_N_V(groupId, parentCategoryId, name,
			vocabularyId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByG_P_N_V_First(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_N_V_First(groupId, parentCategoryId, name,
			vocabularyId, orderByComparator);
	}

	/**
	* Returns the first asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByG_P_N_V_First(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_N_V_First(groupId, parentCategoryId, name,
			vocabularyId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws NoSuchCategoryException if a matching asset category could not be found
	*/
	public static AssetCategory findByG_P_N_V_Last(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_N_V_Last(groupId, parentCategoryId, name,
			vocabularyId, orderByComparator);
	}

	/**
	* Returns the last asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	*/
	public static AssetCategory fetchByG_P_N_V_Last(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_N_V_Last(groupId, parentCategoryId, name,
			vocabularyId, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] findByG_P_N_V_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId, java.lang.String name,
		long vocabularyId, OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_N_V_PrevAndNext(categoryId, groupId,
			parentCategoryId, name, vocabularyId, orderByComparator);
	}

	/**
	* Returns all the asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_P_N_V(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId) {
		return getPersistence()
				   .filterFindByG_P_N_V(groupId, parentCategoryId, name,
			vocabularyId);
	}

	/**
	* Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_P_N_V(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId,
		int start, int end) {
		return getPersistence()
				   .filterFindByG_P_N_V(groupId, parentCategoryId, name,
			vocabularyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories that the user has permission to view
	*/
	public static List<AssetCategory> filterFindByG_P_N_V(long groupId,
		long parentCategoryId, java.lang.String name, long vocabularyId,
		int start, int end, OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence()
				   .filterFindByG_P_N_V(groupId, parentCategoryId, name,
			vocabularyId, start, end, orderByComparator);
	}

	/**
	* Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory[] filterFindByG_P_N_V_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		java.lang.String name, long vocabularyId,
		OrderByComparator<AssetCategory> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByG_P_N_V_PrevAndNext(categoryId, groupId,
			parentCategoryId, name, vocabularyId, orderByComparator);
	}

	/**
	* Removes all the asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	*/
	public static void removeByG_P_N_V(long groupId, long parentCategoryId,
		java.lang.String name, long vocabularyId) {
		getPersistence()
			.removeByG_P_N_V(groupId, parentCategoryId, name, vocabularyId);
	}

	/**
	* Returns the number of asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories
	*/
	public static int countByG_P_N_V(long groupId, long parentCategoryId,
		java.lang.String name, long vocabularyId) {
		return getPersistence()
				   .countByG_P_N_V(groupId, parentCategoryId, name, vocabularyId);
	}

	/**
	* Returns the number of asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param name the name
	* @param vocabularyId the vocabulary ID
	* @return the number of matching asset categories that the user has permission to view
	*/
	public static int filterCountByG_P_N_V(long groupId, long parentCategoryId,
		java.lang.String name, long vocabularyId) {
		return getPersistence()
				   .filterCountByG_P_N_V(groupId, parentCategoryId, name,
			vocabularyId);
	}

	/**
	* Caches the asset category in the entity cache if it is enabled.
	*
	* @param assetCategory the asset category
	*/
	public static void cacheResult(AssetCategory assetCategory) {
		getPersistence().cacheResult(assetCategory);
	}

	/**
	* Caches the asset categories in the entity cache if it is enabled.
	*
	* @param assetCategories the asset categories
	*/
	public static void cacheResult(List<AssetCategory> assetCategories) {
		getPersistence().cacheResult(assetCategories);
	}

	/**
	* Creates a new asset category with the primary key. Does not add the asset category to the database.
	*
	* @param categoryId the primary key for the new asset category
	* @return the new asset category
	*/
	public static AssetCategory create(long categoryId) {
		return getPersistence().create(categoryId);
	}

	/**
	* Removes the asset category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category that was removed
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory remove(long categoryId)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().remove(categoryId);
	}

	public static AssetCategory updateImpl(AssetCategory assetCategory) {
		return getPersistence().updateImpl(assetCategory);
	}

	/**
	* Returns the asset category with the primary key or throws a {@link NoSuchCategoryException} if it could not be found.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category
	* @throws NoSuchCategoryException if a asset category with the primary key could not be found
	*/
	public static AssetCategory findByPrimaryKey(long categoryId)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	/**
	* Returns the asset category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryId the primary key of the asset category
	* @return the asset category, or <code>null</code> if a asset category with the primary key could not be found
	*/
	public static AssetCategory fetchByPrimaryKey(long categoryId) {
		return getPersistence().fetchByPrimaryKey(categoryId);
	}

	public static java.util.Map<java.io.Serializable, AssetCategory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset categories.
	*
	* @return the asset categories
	*/
	public static List<AssetCategory> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of asset categories
	*/
	public static List<AssetCategory> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset categories
	*/
	public static List<AssetCategory> findAll(int start, int end,
		OrderByComparator<AssetCategory> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset categories
	*/
	public static List<AssetCategory> findAll(int start, int end,
		OrderByComparator<AssetCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset categories from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset categories.
	*
	* @return the number of asset categories
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of asset entries associated with the asset category.
	*
	* @param pk the primary key of the asset category
	* @return long[] of the primaryKeys of asset entries associated with the asset category
	*/
	public static long[] getAssetEntryPrimaryKeys(long pk) {
		return getPersistence().getAssetEntryPrimaryKeys(pk);
	}

	/**
	* Returns all the asset entries associated with the asset category.
	*
	* @param pk the primary key of the asset category
	* @return the asset entries associated with the asset category
	*/
	public static List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) {
		return getPersistence().getAssetEntries(pk);
	}

	/**
	* Returns a range of all the asset entries associated with the asset category.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset category
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @return the range of asset entries associated with the asset category
	*/
	public static List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end) {
		return getPersistence().getAssetEntries(pk, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries associated with the asset category.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset category
	* @param start the lower bound of the range of asset categories
	* @param end the upper bound of the range of asset categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entries associated with the asset category
	*/
	public static List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.asset.model.AssetEntry> orderByComparator) {
		return getPersistence()
				   .getAssetEntries(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of asset entries associated with the asset category.
	*
	* @param pk the primary key of the asset category
	* @return the number of asset entries associated with the asset category
	*/
	public static int getAssetEntriesSize(long pk) {
		return getPersistence().getAssetEntriesSize(pk);
	}

	/**
	* Returns <code>true</code> if the asset entry is associated with the asset category.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPK the primary key of the asset entry
	* @return <code>true</code> if the asset entry is associated with the asset category; <code>false</code> otherwise
	*/
	public static boolean containsAssetEntry(long pk, long assetEntryPK) {
		return getPersistence().containsAssetEntry(pk, assetEntryPK);
	}

	/**
	* Returns <code>true</code> if the asset category has any asset entries associated with it.
	*
	* @param pk the primary key of the asset category to check for associations with asset entries
	* @return <code>true</code> if the asset category has any asset entries associated with it; <code>false</code> otherwise
	*/
	public static boolean containsAssetEntries(long pk) {
		return getPersistence().containsAssetEntries(pk);
	}

	/**
	* Adds an association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPK the primary key of the asset entry
	*/
	public static void addAssetEntry(long pk, long assetEntryPK) {
		getPersistence().addAssetEntry(pk, assetEntryPK);
	}

	/**
	* Adds an association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntry the asset entry
	*/
	public static void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		getPersistence().addAssetEntry(pk, assetEntry);
	}

	/**
	* Adds an association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPKs the primary keys of the asset entries
	*/
	public static void addAssetEntries(long pk, long[] assetEntryPKs) {
		getPersistence().addAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Adds an association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntries the asset entries
	*/
	public static void addAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries) {
		getPersistence().addAssetEntries(pk, assetEntries);
	}

	/**
	* Clears all associations between the asset category and its asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category to clear the associated asset entries from
	*/
	public static void clearAssetEntries(long pk) {
		getPersistence().clearAssetEntries(pk);
	}

	/**
	* Removes the association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPK the primary key of the asset entry
	*/
	public static void removeAssetEntry(long pk, long assetEntryPK) {
		getPersistence().removeAssetEntry(pk, assetEntryPK);
	}

	/**
	* Removes the association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntry the asset entry
	*/
	public static void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		getPersistence().removeAssetEntry(pk, assetEntry);
	}

	/**
	* Removes the association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPKs the primary keys of the asset entries
	*/
	public static void removeAssetEntries(long pk, long[] assetEntryPKs) {
		getPersistence().removeAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Removes the association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntries the asset entries
	*/
	public static void removeAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries) {
		getPersistence().removeAssetEntries(pk, assetEntries);
	}

	/**
	* Sets the asset entries associated with the asset category, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPKs the primary keys of the asset entries to be associated with the asset category
	*/
	public static void setAssetEntries(long pk, long[] assetEntryPKs) {
		getPersistence().setAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Sets the asset entries associated with the asset category, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntries the asset entries to be associated with the asset category
	*/
	public static void setAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries) {
		getPersistence().setAssetEntries(pk, assetEntries);
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static long countAncestors(AssetCategory assetCategory) {
		return getPersistence().countAncestors(assetCategory);
	}

	public static long countDescendants(AssetCategory assetCategory) {
		return getPersistence().countDescendants(assetCategory);
	}

	public static List<AssetCategory> getAncestors(AssetCategory assetCategory) {
		return getPersistence().getAncestors(assetCategory);
	}

	public static List<AssetCategory> getDescendants(
		AssetCategory assetCategory) {
		return getPersistence().getDescendants(assetCategory);
	}

	/**
	* Rebuilds the asset categories tree for the scope using the modified pre-order tree traversal algorithm.
	*
	* <p>
	* Only call this method if the tree has become stale through operations other than normal CRUD. Under normal circumstances the tree is automatically rebuilt whenver necessary.
	* </p>
	*
	* @param groupId the ID of the scope
	* @param force whether to force the rebuild even if the tree is not stale
	*/
	public static void rebuildTree(long groupId, boolean force) {
		getPersistence().rebuildTree(groupId, force);
	}

	public static void setRebuildTreeEnabled(boolean rebuildTreeEnabled) {
		getPersistence().setRebuildTreeEnabled(rebuildTreeEnabled);
	}

	public static AssetCategoryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetCategoryPersistence)PortalBeanLocatorUtil.locate(AssetCategoryPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetCategoryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static AssetCategoryPersistence _persistence;
}