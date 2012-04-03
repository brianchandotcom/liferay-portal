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

import com.liferay.portlet.asset.model.AssetEntry;

/**
 * The persistence interface for the asset entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryPersistenceImpl
 * @see AssetEntryUtil
 * @generated
 */
public interface AssetEntryPersistence extends BasePersistence<AssetEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryUtil} to access the asset entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the asset entry in the entity cache if it is enabled.
	*
	* @param assetEntry the asset entry
	*/
	public void cacheResult(
		com.liferay.portlet.asset.model.AssetEntry assetEntry);

	/**
	* Caches the asset entries in the entity cache if it is enabled.
	*
	* @param assetEntries the asset entries
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries);

	/**
	* Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	*
	* @param entryId the primary key for the new asset entry
	* @return the new asset entry
	*/
	public com.liferay.portlet.asset.model.AssetEntry create(long entryId);

	/**
	* Removes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry that was removed
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry remove(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry updateImpl(
		com.liferay.portlet.asset.model.AssetEntry assetEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the asset entry with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the asset entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry, or <code>null</code> if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the asset entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the last asset entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current asset entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns all the asset entries where visible = &#63;.
	*
	* @param visible the visible
	* @return the matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByVisible(
		boolean visible)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset entries where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param visible the visible
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByVisible(
		boolean visible, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset entries where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param visible the visible
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByVisible(
		boolean visible, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset entry in the ordered set where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByVisible_First(
		boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the last asset entry in the ordered set where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByVisible_Last(
		boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current asset entry
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry[] findByVisible_PrevAndNext(
		long entryId, boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns all the asset entries where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @return the matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByPublishDate(
		java.util.Date publishDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset entries where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param publishDate the publish date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByPublishDate(
		java.util.Date publishDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset entries where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param publishDate the publish date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByPublishDate(
		java.util.Date publishDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset entry in the ordered set where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByPublishDate_First(
		java.util.Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the last asset entry in the ordered set where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByPublishDate_Last(
		java.util.Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current asset entry
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry[] findByPublishDate_PrevAndNext(
		long entryId, java.util.Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns all the asset entries where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @return the matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByExpirationDate(
		java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset entries where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByExpirationDate(
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset entries where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByExpirationDate(
		java.util.Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset entry in the ordered set where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByExpirationDate_First(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the last asset entry in the ordered set where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByExpirationDate_Last(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current asset entry
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry[] findByExpirationDate_PrevAndNext(
		long entryId, java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByG_CU(long groupId,
		java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry fetchByG_CU(
		long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry fetchByG_CU(
		long groupId, java.lang.String classUuid, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the asset entries.
	*
	* @return the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset entries where visible = &#63; from the database.
	*
	* @param visible the visible
	* @throws SystemException if a system exception occurred
	*/
	public void removeByVisible(boolean visible)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset entries where publishDate = &#63; from the database.
	*
	* @param publishDate the publish date
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPublishDate(java.util.Date publishDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset entries where expirationDate = &#63; from the database.
	*
	* @param expirationDate the expiration date
	* @throws SystemException if a system exception occurred
	*/
	public void removeByExpirationDate(java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the asset entry where groupId = &#63; and classUuid = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the asset entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry removeByG_CU(
		long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Removes the asset entry where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the asset entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry removeByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	/**
	* Removes all the asset entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset entries where visible = &#63;.
	*
	* @param visible the visible
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByVisible(boolean visible)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset entries where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByPublishDate(java.util.Date publishDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset entries where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByExpirationDate(java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset entries where groupId = &#63; and classUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_CU(long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset entries.
	*
	* @return the number of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset categories associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset categories associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the number of asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public int getAssetCategoriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the asset category is associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @return <code>true</code> if the asset category is associated with the asset entry; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the asset entry has any asset categories associated with it.
	*
	* @param pk the primary key of the asset entry to check for associations with asset categories
	* @return <code>true</code> if the asset entry has any asset categories associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsAssetCategories(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategory the asset category
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the asset entry and its asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to clear the associated asset categories from
	* @throws SystemException if a system exception occurred
	*/
	public void clearAssetCategories(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategory the asset category
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void setAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void setAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset tags associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset tags associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the number of asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public int getAssetTagsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the asset tag is associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @return <code>true</code> if the asset tag is associated with the asset entry; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the asset entry has any asset tags associated with it.
	*
	* @param pk the primary key of the asset entry to check for associations with asset tags
	* @return <code>true</code> if the asset entry has any asset tags associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsAssetTags(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTag the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public void addAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the asset entry and its asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to clear the associated asset tags from
	* @throws SystemException if a system exception occurred
	*/
	public void clearAssetTags(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTag the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public void removeAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void setAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void setAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException;
}