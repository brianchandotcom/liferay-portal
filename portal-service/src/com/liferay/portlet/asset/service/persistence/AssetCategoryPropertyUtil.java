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

import com.liferay.portlet.asset.model.AssetCategoryProperty;

import java.util.List;

/**
 * The persistence utility for the asset category property service. This utility wraps {@link com.liferay.portlet.asset.service.persistence.impl.AssetCategoryPropertyPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPropertyPersistence
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetCategoryPropertyPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetCategoryPropertyUtil {
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
	public static void clearCache(AssetCategoryProperty assetCategoryProperty) {
		getPersistence().clearCache(assetCategoryProperty);
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
	public static List<AssetCategoryProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetCategoryProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetCategoryProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetCategoryProperty update(
		AssetCategoryProperty assetCategoryProperty) {
		return getPersistence().update(assetCategoryProperty);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetCategoryProperty update(
		AssetCategoryProperty assetCategoryProperty,
		ServiceContext serviceContext) {
		return getPersistence().update(assetCategoryProperty, serviceContext);
	}

	/**
	* Returns all the asset category properties where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the asset category properties where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset category properties where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset category properties where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty findByCompanyId_First(long companyId,
		OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty fetchByCompanyId_First(long companyId,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty findByCompanyId_Last(long companyId,
		OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty fetchByCompanyId_Last(long companyId,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the asset category properties before and after the current asset category property in the ordered set where companyId = &#63;.
	*
	* @param categoryPropertyId the primary key of the current asset category property
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category property
	* @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	*/
	public static AssetCategoryProperty[] findByCompanyId_PrevAndNext(
		long categoryPropertyId, long companyId,
		OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(categoryPropertyId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the asset category properties where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of asset category properties where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching asset category properties
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the asset category properties where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @return the matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByCategoryId(long categoryId) {
		return getPersistence().findByCategoryId(categoryId);
	}

	/**
	* Returns a range of all the asset category properties where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end) {
		return getPersistence().findByCategoryId(categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the asset category properties where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence()
				   .findByCategoryId(categoryId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset category properties where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCategoryId(categoryId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty findByCategoryId_First(
		long categoryId,
		OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCategoryId_First(categoryId, orderByComparator);
	}

	/**
	* Returns the first asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty fetchByCategoryId_First(
		long categoryId,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence()
				   .fetchByCategoryId_First(categoryId, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty findByCategoryId_Last(long categoryId,
		OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCategoryId_Last(categoryId, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty fetchByCategoryId_Last(
		long categoryId,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence()
				   .fetchByCategoryId_Last(categoryId, orderByComparator);
	}

	/**
	* Returns the asset category properties before and after the current asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryPropertyId the primary key of the current asset category property
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category property
	* @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	*/
	public static AssetCategoryProperty[] findByCategoryId_PrevAndNext(
		long categoryPropertyId, long categoryId,
		OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCategoryId_PrevAndNext(categoryPropertyId,
			categoryId, orderByComparator);
	}

	/**
	* Removes all the asset category properties where categoryId = &#63; from the database.
	*
	* @param categoryId the category ID
	*/
	public static void removeByCategoryId(long categoryId) {
		getPersistence().removeByCategoryId(categoryId);
	}

	/**
	* Returns the number of asset category properties where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @return the number of matching asset category properties
	*/
	public static int countByCategoryId(long categoryId) {
		return getPersistence().countByCategoryId(categoryId);
	}

	/**
	* Returns all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @return the matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByC_K(long companyId,
		java.lang.String key) {
		return getPersistence().findByC_K(companyId, key);
	}

	/**
	* Returns a range of all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param key the key
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByC_K(long companyId,
		java.lang.String key, int start, int end) {
		return getPersistence().findByC_K(companyId, key, start, end);
	}

	/**
	* Returns an ordered range of all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param key the key
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByC_K(long companyId,
		java.lang.String key, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence()
				   .findByC_K(companyId, key, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param key the key
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset category properties
	*/
	public static List<AssetCategoryProperty> findByC_K(long companyId,
		java.lang.String key, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_K(companyId, key, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty findByC_K_First(long companyId,
		java.lang.String key,
		OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByC_K_First(companyId, key, orderByComparator);
	}

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty fetchByC_K_First(long companyId,
		java.lang.String key,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence()
				   .fetchByC_K_First(companyId, key, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty findByC_K_Last(long companyId,
		java.lang.String key,
		OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByC_K_Last(companyId, key, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty fetchByC_K_Last(long companyId,
		java.lang.String key,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence()
				   .fetchByC_K_Last(companyId, key, orderByComparator);
	}

	/**
	* Returns the asset category properties before and after the current asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param categoryPropertyId the primary key of the current asset category property
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category property
	* @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	*/
	public static AssetCategoryProperty[] findByC_K_PrevAndNext(
		long categoryPropertyId, long companyId, java.lang.String key,
		OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByC_K_PrevAndNext(categoryPropertyId, companyId, key,
			orderByComparator);
	}

	/**
	* Removes all the asset category properties where companyId = &#63; and key = &#63; from the database.
	*
	* @param companyId the company ID
	* @param key the key
	*/
	public static void removeByC_K(long companyId, java.lang.String key) {
		getPersistence().removeByC_K(companyId, key);
	}

	/**
	* Returns the number of asset category properties where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @return the number of matching asset category properties
	*/
	public static int countByC_K(long companyId, java.lang.String key) {
		return getPersistence().countByC_K(companyId, key);
	}

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or throws a {@link NoSuchCategoryPropertyException} if it could not be found.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty findByCA_K(long categoryId,
		java.lang.String key)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByCA_K(categoryId, key);
	}

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty fetchByCA_K(long categoryId,
		java.lang.String key) {
		return getPersistence().fetchByCA_K(categoryId, key);
	}

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param categoryId the category ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public static AssetCategoryProperty fetchByCA_K(long categoryId,
		java.lang.String key, boolean retrieveFromCache) {
		return getPersistence().fetchByCA_K(categoryId, key, retrieveFromCache);
	}

	/**
	* Removes the asset category property where categoryId = &#63; and key = &#63; from the database.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the asset category property that was removed
	*/
	public static AssetCategoryProperty removeByCA_K(long categoryId,
		java.lang.String key)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().removeByCA_K(categoryId, key);
	}

	/**
	* Returns the number of asset category properties where categoryId = &#63; and key = &#63;.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the number of matching asset category properties
	*/
	public static int countByCA_K(long categoryId, java.lang.String key) {
		return getPersistence().countByCA_K(categoryId, key);
	}

	/**
	* Caches the asset category property in the entity cache if it is enabled.
	*
	* @param assetCategoryProperty the asset category property
	*/
	public static void cacheResult(AssetCategoryProperty assetCategoryProperty) {
		getPersistence().cacheResult(assetCategoryProperty);
	}

	/**
	* Caches the asset category properties in the entity cache if it is enabled.
	*
	* @param assetCategoryProperties the asset category properties
	*/
	public static void cacheResult(
		List<AssetCategoryProperty> assetCategoryProperties) {
		getPersistence().cacheResult(assetCategoryProperties);
	}

	/**
	* Creates a new asset category property with the primary key. Does not add the asset category property to the database.
	*
	* @param categoryPropertyId the primary key for the new asset category property
	* @return the new asset category property
	*/
	public static AssetCategoryProperty create(long categoryPropertyId) {
		return getPersistence().create(categoryPropertyId);
	}

	/**
	* Removes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property that was removed
	* @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	*/
	public static AssetCategoryProperty remove(long categoryPropertyId)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().remove(categoryPropertyId);
	}

	public static AssetCategoryProperty updateImpl(
		AssetCategoryProperty assetCategoryProperty) {
		return getPersistence().updateImpl(assetCategoryProperty);
	}

	/**
	* Returns the asset category property with the primary key or throws a {@link NoSuchCategoryPropertyException} if it could not be found.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property
	* @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	*/
	public static AssetCategoryProperty findByPrimaryKey(
		long categoryPropertyId)
		throws com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByPrimaryKey(categoryPropertyId);
	}

	/**
	* Returns the asset category property with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property, or <code>null</code> if a asset category property with the primary key could not be found
	*/
	public static AssetCategoryProperty fetchByPrimaryKey(
		long categoryPropertyId) {
		return getPersistence().fetchByPrimaryKey(categoryPropertyId);
	}

	public static java.util.Map<java.io.Serializable, AssetCategoryProperty> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset category properties.
	*
	* @return the asset category properties
	*/
	public static List<AssetCategoryProperty> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset category properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of asset category properties
	*/
	public static List<AssetCategoryProperty> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset category properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset category properties
	*/
	public static List<AssetCategoryProperty> findAll(int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset category properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset category properties
	*/
	public static List<AssetCategoryProperty> findAll(int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset category properties from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset category properties.
	*
	* @return the number of asset category properties
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static AssetCategoryPropertyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetCategoryPropertyPersistence)PortalBeanLocatorUtil.locate(AssetCategoryPropertyPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetCategoryPropertyUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static AssetCategoryPropertyPersistence _persistence;
}