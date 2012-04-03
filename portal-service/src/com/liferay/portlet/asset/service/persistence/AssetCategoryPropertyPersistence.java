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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.asset.model.AssetCategoryProperty;

/**
 * The persistence interface for the asset category property service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPropertyPersistenceImpl
 * @see AssetCategoryPropertyUtil
 * @generated
 */
public interface AssetCategoryPropertyPersistence extends BasePersistence<AssetCategoryProperty> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetCategoryPropertyUtil} to access the asset category property persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the asset category property in the entity cache if it is enabled.
	*
	* @param assetCategoryProperty the asset category property
	*/
	public void cacheResult(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty);

	/**
	* Caches the asset category properties in the entity cache if it is enabled.
	*
	* @param assetCategoryProperties the asset category properties
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> assetCategoryProperties);

	/**
	* Creates a new asset category property with the primary key. Does not add the asset category property to the database.
	*
	* @param categoryPropertyId the primary key for the new asset category property
	* @return the new asset category property
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty create(
		long categoryPropertyId);

	/**
	* Removes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property that was removed
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty remove(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	public com.liferay.portlet.asset.model.AssetCategoryProperty updateImpl(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the asset category property with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchCategoryPropertyException} if it could not be found.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty findByPrimaryKey(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns the asset category property with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property, or <code>null</code> if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty fetchByPrimaryKey(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the asset category properties where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset category properties where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset category properties where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns the asset category properties before and after the current asset category property in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryPropertyId the primary key of the current asset category property
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty[] findByCompanyId_PrevAndNext(
		long categoryPropertyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns all the asset category properties where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @return the matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCategoryId(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset category properties where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the category ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset category properties where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the category ID
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset category property in the ordered set where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty findByCategoryId_First(
		long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns the last asset category property in the ordered set where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty findByCategoryId_Last(
		long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns the asset category properties before and after the current asset category property in the ordered set where categoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryPropertyId the primary key of the current asset category property
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty[] findByCategoryId_PrevAndNext(
		long categoryPropertyId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @return the matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByC_K(
		long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param key the key
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty findByC_K_First(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty findByC_K_Last(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns the asset category properties before and after the current asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryPropertyId the primary key of the current asset category property
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty[] findByC_K_PrevAndNext(
		long categoryPropertyId, long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchCategoryPropertyException} if it could not be found.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the matching asset category property
	* @throws com.liferay.portlet.asset.NoSuchCategoryPropertyException if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty findByCA_K(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty fetchByCA_K(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param categoryId the category ID
	* @param key the key
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty fetchByCA_K(
		long categoryId, java.lang.String key, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the asset category properties.
	*
	* @return the asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset category properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset category properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset category properties where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset category properties where categoryId = &#63; from the database.
	*
	* @param categoryId the category ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCategoryId(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset category properties where companyId = &#63; and key = &#63; from the database.
	*
	* @param companyId the company ID
	* @param key the key
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the asset category property where categoryId = &#63; and key = &#63; from the database.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the asset category property that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetCategoryProperty removeByCA_K(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryPropertyException;

	/**
	* Removes all the asset category properties from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset category properties where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset category properties where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @return the number of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public int countByCategoryId(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset category properties where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @return the number of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset category properties where categoryId = &#63; and key = &#63;.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the number of matching asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public int countByCA_K(long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset category properties.
	*
	* @return the number of asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}