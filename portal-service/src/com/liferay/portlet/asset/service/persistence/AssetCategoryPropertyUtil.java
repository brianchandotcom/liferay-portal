/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.asset.model.AssetCategoryProperty;

import java.util.List;

/**
 * The persistence utility for the asset category property service. This utility wraps {@link AssetCategoryPropertyPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPropertyPersistence
 * @see AssetCategoryPropertyPersistenceImpl
 * @generated
 */
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
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetCategoryProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetCategoryProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetCategoryProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetCategoryProperty update(
		AssetCategoryProperty assetCategoryProperty) throws SystemException {
		return getPersistence().update(assetCategoryProperty);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetCategoryProperty update(
		AssetCategoryProperty assetCategoryProperty,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(assetCategoryProperty, serviceContext);
	}

	/**
	* Returns all the asset category properties where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the asset category properties where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset category properties where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty[] findByCompanyId_PrevAndNext(
		long categoryPropertyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(categoryPropertyId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the asset category properties where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of asset category properties where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the asset category properties where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @return the matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCategoryId(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCategoryId(categoryId);
	}

	/**
	* Returns a range of all the asset category properties where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCategoryId(categoryId, start, end);
	}

	/**
	* Returns an ordered range of all the asset category properties where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param categoryId the category ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCategoryId(categoryId, start, end, orderByComparator);
	}

	/**
	* Returns the first asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByCategoryId_First(
		long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCategoryId_First(categoryId, orderByComparator);
	}

	/**
	* Returns the first asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByCategoryId_First(
		long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCategoryId_First(categoryId, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByCategoryId_Last(
		long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCategoryId_Last(categoryId, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByCategoryId_Last(
		long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty[] findByCategoryId_PrevAndNext(
		long categoryPropertyId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByCategoryId_PrevAndNext(categoryPropertyId,
			categoryId, orderByComparator);
	}

	/**
	* Removes all the asset category properties where categoryId = &#63; from the database.
	*
	* @param categoryId the category ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCategoryId(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCategoryId(categoryId);
	}

	/**
	* Returns the number of asset category properties where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @return the number of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCategoryId(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCategoryId(categoryId);
	}

	/**
	* Returns all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @return the matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByC_K(
		long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_K(companyId, key);
	}

	/**
	* Returns a range of all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param key the key
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_K(companyId, key, start, end);
	}

	/**
	* Returns an ordered range of all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param key the key
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_K(companyId, key, start, end, orderByComparator);
	}

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByC_K_First(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByC_K_First(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByC_K_Last(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByC_K_Last(companyId, key, orderByComparator);
	}

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByC_K_Last(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty[] findByC_K_PrevAndNext(
		long categoryPropertyId, long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence()
				   .findByC_K_PrevAndNext(categoryPropertyId, companyId, key,
			orderByComparator);
	}

	/**
	* Removes all the asset category properties where companyId = &#63; and key = &#63; from the database.
	*
	* @param companyId the company ID
	* @param key the key
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_K(companyId, key);
	}

	/**
	* Returns the number of asset category properties where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @return the number of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_K(companyId, key);
	}

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchCategoryPropertyException} if it could not be found.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByCA_K(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByCA_K(categoryId, key);
	}

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByCA_K(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCA_K(categoryId, key);
	}

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param categoryId the category ID
	* @param key the key
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByCA_K(
		long categoryId, java.lang.String key, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCA_K(categoryId, key, retrieveFromCache);
	}

	/**
	* Removes the asset category property where categoryId = &#63; and key = &#63; from the database.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the asset category property that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty removeByCA_K(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().removeByCA_K(categoryId, key);
	}

	/**
	* Returns the number of asset category properties where categoryId = &#63; and key = &#63;.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the number of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCA_K(long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCA_K(categoryId, key);
	}

	/**
	* Caches the asset category property in the entity cache if it is enabled.
	*
	* @param assetCategoryProperty the asset category property
	*/
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty) {
		getPersistence().cacheResult(assetCategoryProperty);
	}

	/**
	* Caches the asset category properties in the entity cache if it is enabled.
	*
	* @param assetCategoryProperties the asset category properties
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> assetCategoryProperties) {
		getPersistence().cacheResult(assetCategoryProperties);
	}

	/**
	* Creates a new asset category property with the primary key. Does not add the asset category property to the database.
	*
	* @param categoryPropertyId the primary key for the new asset category property
	* @return the new asset category property
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty create(
		long categoryPropertyId) {
		return getPersistence().create(categoryPropertyId);
	}

	/**
	* Removes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property that was removed
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty remove(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().remove(categoryPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateImpl(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetCategoryProperty);
	}

	/**
	* Returns the asset category property with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchCategoryPropertyException} if it could not be found.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty findByPrimaryKey(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException {
		return getPersistence().findByPrimaryKey(categoryPropertyId);
	}

	/**
	* Returns the asset category property with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property, or <code>null</code> if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchByPrimaryKey(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(categoryPropertyId);
	}

	/**
	* Returns all the asset category properties.
	*
	* @return the asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset category properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset category properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the asset category properties from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset category properties.
	*
	* @return the number of asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AssetCategoryPropertyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetCategoryPropertyPersistence)PortalBeanLocatorUtil.locate(AssetCategoryPropertyPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetCategoryPropertyUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(AssetCategoryPropertyPersistence persistence) {
	}

	private static AssetCategoryPropertyPersistence _persistence;
}