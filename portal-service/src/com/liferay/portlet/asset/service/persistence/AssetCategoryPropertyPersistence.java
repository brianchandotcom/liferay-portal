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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.asset.exception.NoSuchCategoryPropertyException;
import com.liferay.portlet.asset.model.AssetCategoryProperty;

/**
 * The persistence interface for the asset category property service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetCategoryPropertyPersistenceImpl
 * @see AssetCategoryPropertyUtil
 * @generated
 */
@ProviderType
public interface AssetCategoryPropertyPersistence extends BasePersistence<AssetCategoryProperty> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetCategoryPropertyUtil} to access the asset category property persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset category properties where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching asset category properties
	*/
	public java.util.List<AssetCategoryProperty> findByCompanyId(long companyId);

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
	public java.util.List<AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator);

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
	public java.util.List<AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public AssetCategoryProperty findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException;

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public AssetCategoryProperty fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator);

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public AssetCategoryProperty findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException;

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public AssetCategoryProperty fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator);

	/**
	* Returns the asset category properties before and after the current asset category property in the ordered set where companyId = &#63;.
	*
	* @param categoryPropertyId the primary key of the current asset category property
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category property
	* @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	*/
	public AssetCategoryProperty[] findByCompanyId_PrevAndNext(
		long categoryPropertyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException;

	/**
	* Removes all the asset category properties where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of asset category properties where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching asset category properties
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the asset category properties where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @return the matching asset category properties
	*/
	public java.util.List<AssetCategoryProperty> findByCategoryId(
		long categoryId);

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
	public java.util.List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end);

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
	public java.util.List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator);

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
	public java.util.List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public AssetCategoryProperty findByCategoryId_First(long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException;

	/**
	* Returns the first asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public AssetCategoryProperty fetchByCategoryId_First(long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator);

	/**
	* Returns the last asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public AssetCategoryProperty findByCategoryId_Last(long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException;

	/**
	* Returns the last asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public AssetCategoryProperty fetchByCategoryId_Last(long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator);

	/**
	* Returns the asset category properties before and after the current asset category property in the ordered set where categoryId = &#63;.
	*
	* @param categoryPropertyId the primary key of the current asset category property
	* @param categoryId the category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category property
	* @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	*/
	public AssetCategoryProperty[] findByCategoryId_PrevAndNext(
		long categoryPropertyId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException;

	/**
	* Removes all the asset category properties where categoryId = &#63; from the database.
	*
	* @param categoryId the category ID
	*/
	public void removeByCategoryId(long categoryId);

	/**
	* Returns the number of asset category properties where categoryId = &#63;.
	*
	* @param categoryId the category ID
	* @return the number of matching asset category properties
	*/
	public int countByCategoryId(long categoryId);

	/**
	* Returns all the asset category properties where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @return the matching asset category properties
	*/
	public java.util.List<AssetCategoryProperty> findByC_K(long companyId,
		java.lang.String key);

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
	public java.util.List<AssetCategoryProperty> findByC_K(long companyId,
		java.lang.String key, int start, int end);

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
	public java.util.List<AssetCategoryProperty> findByC_K(long companyId,
		java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator);

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
	public java.util.List<AssetCategoryProperty> findByC_K(long companyId,
		java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public AssetCategoryProperty findByC_K_First(long companyId,
		java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException;

	/**
	* Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public AssetCategoryProperty fetchByC_K_First(long companyId,
		java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator);

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public AssetCategoryProperty findByC_K_Last(long companyId,
		java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException;

	/**
	* Returns the last asset category property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public AssetCategoryProperty fetchByC_K_Last(long companyId,
		java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator);

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
	public AssetCategoryProperty[] findByC_K_PrevAndNext(
		long categoryPropertyId, long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException;

	/**
	* Removes all the asset category properties where companyId = &#63; and key = &#63; from the database.
	*
	* @param companyId the company ID
	* @param key the key
	*/
	public void removeByC_K(long companyId, java.lang.String key);

	/**
	* Returns the number of asset category properties where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @return the number of matching asset category properties
	*/
	public int countByC_K(long companyId, java.lang.String key);

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or throws a {@link NoSuchCategoryPropertyException} if it could not be found.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the matching asset category property
	* @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	*/
	public AssetCategoryProperty findByCA_K(long categoryId,
		java.lang.String key) throws NoSuchCategoryPropertyException;

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public AssetCategoryProperty fetchByCA_K(long categoryId,
		java.lang.String key);

	/**
	* Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param categoryId the category ID
	* @param key the key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	*/
	public AssetCategoryProperty fetchByCA_K(long categoryId,
		java.lang.String key, boolean retrieveFromCache);

	/**
	* Removes the asset category property where categoryId = &#63; and key = &#63; from the database.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the asset category property that was removed
	*/
	public AssetCategoryProperty removeByCA_K(long categoryId,
		java.lang.String key) throws NoSuchCategoryPropertyException;

	/**
	* Returns the number of asset category properties where categoryId = &#63; and key = &#63;.
	*
	* @param categoryId the category ID
	* @param key the key
	* @return the number of matching asset category properties
	*/
	public int countByCA_K(long categoryId, java.lang.String key);

	/**
	* Caches the asset category property in the entity cache if it is enabled.
	*
	* @param assetCategoryProperty the asset category property
	*/
	public void cacheResult(AssetCategoryProperty assetCategoryProperty);

	/**
	* Caches the asset category properties in the entity cache if it is enabled.
	*
	* @param assetCategoryProperties the asset category properties
	*/
	public void cacheResult(
		java.util.List<AssetCategoryProperty> assetCategoryProperties);

	/**
	* Creates a new asset category property with the primary key. Does not add the asset category property to the database.
	*
	* @param categoryPropertyId the primary key for the new asset category property
	* @return the new asset category property
	*/
	public AssetCategoryProperty create(long categoryPropertyId);

	/**
	* Removes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property that was removed
	* @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	*/
	public AssetCategoryProperty remove(long categoryPropertyId)
		throws NoSuchCategoryPropertyException;

	public AssetCategoryProperty updateImpl(
		AssetCategoryProperty assetCategoryProperty);

	/**
	* Returns the asset category property with the primary key or throws a {@link NoSuchCategoryPropertyException} if it could not be found.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property
	* @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	*/
	public AssetCategoryProperty findByPrimaryKey(long categoryPropertyId)
		throws NoSuchCategoryPropertyException;

	/**
	* Returns the asset category property with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property, or <code>null</code> if a asset category property with the primary key could not be found
	*/
	public AssetCategoryProperty fetchByPrimaryKey(long categoryPropertyId);

	@Override
	public java.util.Map<java.io.Serializable, AssetCategoryProperty> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset category properties.
	*
	* @return the asset category properties
	*/
	public java.util.List<AssetCategoryProperty> findAll();

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
	public java.util.List<AssetCategoryProperty> findAll(int start, int end);

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
	public java.util.List<AssetCategoryProperty> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator);

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
	public java.util.List<AssetCategoryProperty> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset category properties from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset category properties.
	*
	* @return the number of asset category properties
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}