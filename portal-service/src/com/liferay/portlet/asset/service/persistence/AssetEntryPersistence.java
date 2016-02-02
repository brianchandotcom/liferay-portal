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

import com.liferay.portlet.asset.exception.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;

import java.util.Date;

/**
 * The persistence interface for the asset entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetEntryPersistenceImpl
 * @see AssetEntryUtil
 * @generated
 */
@ProviderType
public interface AssetEntryPersistence extends BasePersistence<AssetEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryUtil} to access the asset entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset entries
	*/
	public java.util.List<AssetEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the asset entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the asset entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first asset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the last asset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last asset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where groupId = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public AssetEntry[] findByGroupId_PrevAndNext(long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the asset entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of asset entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the asset entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching asset entries
	*/
	public java.util.List<AssetEntry> findByCompanyId(long companyId);

	/**
	* Returns a range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the last asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where companyId = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public AssetEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the asset entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of asset entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching asset entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the asset entries where visible = &#63;.
	*
	* @param visible the visible
	* @return the matching asset entries
	*/
	public java.util.List<AssetEntry> findByVisible(boolean visible);

	/**
	* Returns a range of all the asset entries where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param visible the visible
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByVisible(boolean visible, int start,
		int end);

	/**
	* Returns an ordered range of all the asset entries where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param visible the visible
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByVisible(boolean visible, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset entries where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param visible the visible
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByVisible(boolean visible, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByVisible_First(boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByVisible_First(boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the last asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByVisible_Last(boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByVisible_Last(boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where visible = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public AssetEntry[] findByVisible_PrevAndNext(long entryId,
		boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the asset entries where visible = &#63; from the database.
	*
	* @param visible the visible
	*/
	public void removeByVisible(boolean visible);

	/**
	* Returns the number of asset entries where visible = &#63;.
	*
	* @param visible the visible
	* @return the number of matching asset entries
	*/
	public int countByVisible(boolean visible);

	/**
	* Returns all the asset entries where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @return the matching asset entries
	*/
	public java.util.List<AssetEntry> findByPublishDate(Date publishDate);

	/**
	* Returns a range of all the asset entries where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param publishDate the publish date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByPublishDate(Date publishDate,
		int start, int end);

	/**
	* Returns an ordered range of all the asset entries where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param publishDate the publish date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByPublishDate(Date publishDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset entries where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param publishDate the publish date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByPublishDate(Date publishDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByPublishDate_First(Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByPublishDate_First(Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the last asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByPublishDate_Last(Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByPublishDate_Last(Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where publishDate = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public AssetEntry[] findByPublishDate_PrevAndNext(long entryId,
		Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the asset entries where publishDate = &#63; from the database.
	*
	* @param publishDate the publish date
	*/
	public void removeByPublishDate(Date publishDate);

	/**
	* Returns the number of asset entries where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @return the number of matching asset entries
	*/
	public int countByPublishDate(Date publishDate);

	/**
	* Returns all the asset entries where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @return the matching asset entries
	*/
	public java.util.List<AssetEntry> findByExpirationDate(Date expirationDate);

	/**
	* Returns a range of all the asset entries where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByExpirationDate(
		Date expirationDate, int start, int end);

	/**
	* Returns an ordered range of all the asset entries where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByExpirationDate(
		Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset entries where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByExpirationDate(
		Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByExpirationDate_First(Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByExpirationDate_First(Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the last asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByExpirationDate_Last(Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByExpirationDate_Last(Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public AssetEntry[] findByExpirationDate_PrevAndNext(long entryId,
		Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the asset entries where expirationDate = &#63; from the database.
	*
	* @param expirationDate the expiration date
	*/
	public void removeByExpirationDate(Date expirationDate);

	/**
	* Returns the number of asset entries where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @return the number of matching asset entries
	*/
	public int countByExpirationDate(Date expirationDate);

	/**
	* Returns all the asset entries where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @return the matching asset entries
	*/
	public java.util.List<AssetEntry> findByLayoutUuid(
		java.lang.String layoutUuid);

	/**
	* Returns a range of all the asset entries where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end);

	/**
	* Returns an ordered range of all the asset entries where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset entries where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public java.util.List<AssetEntry> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByLayoutUuid_First(java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByLayoutUuid_First(java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByLayoutUuid_Last(java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByLayoutUuid_Last(java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public AssetEntry[] findByLayoutUuid_PrevAndNext(long entryId,
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the asset entries where layoutUuid = &#63; from the database.
	*
	* @param layoutUuid the layout uuid
	*/
	public void removeByLayoutUuid(java.lang.String layoutUuid);

	/**
	* Returns the number of asset entries where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @return the number of matching asset entries
	*/
	public int countByLayoutUuid(java.lang.String layoutUuid);

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByG_CU(long groupId, java.lang.String classUuid)
		throws NoSuchEntryException;

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByG_CU(long groupId, java.lang.String classUuid);

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByG_CU(long groupId, java.lang.String classUuid,
		boolean retrieveFromCache);

	/**
	* Removes the asset entry where groupId = &#63; and classUuid = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the asset entry that was removed
	*/
	public AssetEntry removeByG_CU(long groupId, java.lang.String classUuid)
		throws NoSuchEntryException;

	/**
	* Returns the number of asset entries where groupId = &#63; and classUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the number of matching asset entries
	*/
	public int countByG_CU(long groupId, java.lang.String classUuid);

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public AssetEntry findByC_C(long classNameId, long classPK)
		throws NoSuchEntryException;

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByC_C(long classNameId, long classPK);

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public AssetEntry fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache);

	/**
	* Removes the asset entry where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the asset entry that was removed
	*/
	public AssetEntry removeByC_C(long classNameId, long classPK)
		throws NoSuchEntryException;

	/**
	* Returns the number of asset entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching asset entries
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Caches the asset entry in the entity cache if it is enabled.
	*
	* @param assetEntry the asset entry
	*/
	public void cacheResult(AssetEntry assetEntry);

	/**
	* Caches the asset entries in the entity cache if it is enabled.
	*
	* @param assetEntries the asset entries
	*/
	public void cacheResult(java.util.List<AssetEntry> assetEntries);

	/**
	* Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	*
	* @param entryId the primary key for the new asset entry
	* @return the new asset entry
	*/
	public AssetEntry create(long entryId);

	/**
	* Removes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry that was removed
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public AssetEntry remove(long entryId) throws NoSuchEntryException;

	public AssetEntry updateImpl(AssetEntry assetEntry);

	/**
	* Returns the asset entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public AssetEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException;

	/**
	* Returns the asset entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry, or <code>null</code> if a asset entry with the primary key could not be found
	*/
	public AssetEntry fetchByPrimaryKey(long entryId);

	@Override
	public java.util.Map<java.io.Serializable, AssetEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset entries.
	*
	* @return the asset entries
	*/
	public java.util.List<AssetEntry> findAll();

	/**
	* Returns a range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset entries
	*/
	public java.util.List<AssetEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entries
	*/
	public java.util.List<AssetEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator);

	/**
	* Returns an ordered range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset entries
	*/
	public java.util.List<AssetEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset entries.
	*
	* @return the number of asset entries
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return long[] of the primaryKeys of asset categories associated with the asset entry
	*/
	public long[] getAssetCategoryPrimaryKeys(long pk);

	/**
	* Returns all the asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the asset categories associated with the asset entry
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk);

	/**
	* Returns a range of all the asset categories associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset categories associated with the asset entry
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the asset categories associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset categories associated with the asset entry
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> orderByComparator);

	/**
	* Returns the number of asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the number of asset categories associated with the asset entry
	*/
	public int getAssetCategoriesSize(long pk);

	/**
	* Returns <code>true</code> if the asset category is associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @return <code>true</code> if the asset category is associated with the asset entry; <code>false</code> otherwise
	*/
	public boolean containsAssetCategory(long pk, long assetCategoryPK);

	/**
	* Returns <code>true</code> if the asset entry has any asset categories associated with it.
	*
	* @param pk the primary key of the asset entry to check for associations with asset categories
	* @return <code>true</code> if the asset entry has any asset categories associated with it; <code>false</code> otherwise
	*/
	public boolean containsAssetCategories(long pk);

	/**
	* Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	*/
	public void addAssetCategory(long pk, long assetCategoryPK);

	/**
	* Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategory the asset category
	*/
	public void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory);

	/**
	* Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories
	*/
	public void addAssetCategories(long pk, long[] assetCategoryPKs);

	/**
	* Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories
	*/
	public void addAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories);

	/**
	* Clears all associations between the asset entry and its asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to clear the associated asset categories from
	*/
	public void clearAssetCategories(long pk);

	/**
	* Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	*/
	public void removeAssetCategory(long pk, long assetCategoryPK);

	/**
	* Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategory the asset category
	*/
	public void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory);

	/**
	* Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories
	*/
	public void removeAssetCategories(long pk, long[] assetCategoryPKs);

	/**
	* Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories
	*/
	public void removeAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories);

	/**
	* Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories to be associated with the asset entry
	*/
	public void setAssetCategories(long pk, long[] assetCategoryPKs);

	/**
	* Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories to be associated with the asset entry
	*/
	public void setAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories);

	/**
	* Returns the primaryKeys of asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return long[] of the primaryKeys of asset tags associated with the asset entry
	*/
	public long[] getAssetTagPrimaryKeys(long pk);

	/**
	* Returns all the asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the asset tags associated with the asset entry
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk);

	/**
	* Returns a range of all the asset tags associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset tags associated with the asset entry
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the asset tags associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tags associated with the asset entry
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator);

	/**
	* Returns the number of asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the number of asset tags associated with the asset entry
	*/
	public int getAssetTagsSize(long pk);

	/**
	* Returns <code>true</code> if the asset tag is associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @return <code>true</code> if the asset tag is associated with the asset entry; <code>false</code> otherwise
	*/
	public boolean containsAssetTag(long pk, long assetTagPK);

	/**
	* Returns <code>true</code> if the asset entry has any asset tags associated with it.
	*
	* @param pk the primary key of the asset entry to check for associations with asset tags
	* @return <code>true</code> if the asset entry has any asset tags associated with it; <code>false</code> otherwise
	*/
	public boolean containsAssetTags(long pk);

	/**
	* Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	*/
	public void addAssetTag(long pk, long assetTagPK);

	/**
	* Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTag the asset tag
	*/
	public void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag);

	/**
	* Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags
	*/
	public void addAssetTags(long pk, long[] assetTagPKs);

	/**
	* Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags
	*/
	public void addAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags);

	/**
	* Clears all associations between the asset entry and its asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to clear the associated asset tags from
	*/
	public void clearAssetTags(long pk);

	/**
	* Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	*/
	public void removeAssetTag(long pk, long assetTagPK);

	/**
	* Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTag the asset tag
	*/
	public void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag);

	/**
	* Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags
	*/
	public void removeAssetTags(long pk, long[] assetTagPKs);

	/**
	* Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags
	*/
	public void removeAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags);

	/**
	* Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags to be associated with the asset entry
	*/
	public void setAssetTags(long pk, long[] assetTagPKs);

	/**
	* Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags to be associated with the asset entry
	*/
	public void setAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags);
}