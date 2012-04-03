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

import com.liferay.portlet.asset.model.AssetLink;

/**
 * The persistence interface for the asset link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetLinkPersistenceImpl
 * @see AssetLinkUtil
 * @generated
 */
public interface AssetLinkPersistence extends BasePersistence<AssetLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetLinkUtil} to access the asset link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the asset link in the entity cache if it is enabled.
	*
	* @param assetLink the asset link
	*/
	public void cacheResult(com.liferay.portlet.asset.model.AssetLink assetLink);

	/**
	* Caches the asset links in the entity cache if it is enabled.
	*
	* @param assetLinks the asset links
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetLink> assetLinks);

	/**
	* Creates a new asset link with the primary key. Does not add the asset link to the database.
	*
	* @param linkId the primary key for the new asset link
	* @return the new asset link
	*/
	public com.liferay.portlet.asset.model.AssetLink create(long linkId);

	/**
	* Removes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param linkId the primary key of the asset link
	* @return the asset link that was removed
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink remove(long linkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	public com.liferay.portlet.asset.model.AssetLink updateImpl(
		com.liferay.portlet.asset.model.AssetLink assetLink, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the asset link with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchLinkException} if it could not be found.
	*
	* @param linkId the primary key of the asset link
	* @return the asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByPrimaryKey(
		long linkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the asset link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param linkId the primary key of the asset link
	* @return the asset link, or <code>null</code> if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink fetchByPrimaryKey(
		long linkId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the asset links where entryId1 = &#63;.
	*
	* @param entryId1 the entry id1
	* @return the matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset links where entryId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset links where entryId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset link in the ordered set where entryId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE1_First(
		long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the last asset link in the ordered set where entryId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE1_Last(
		long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param linkId the primary key of the current asset link
	* @param entryId1 the entry id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink[] findByE1_PrevAndNext(
		long linkId, long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns all the asset links where entryId2 = &#63;.
	*
	* @param entryId2 the entry id2
	* @return the matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset links where entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset links where entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset link in the ordered set where entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE2_First(
		long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the last asset link in the ordered set where entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE2_Last(
		long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the asset links before and after the current asset link in the ordered set where entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param linkId the primary key of the current asset link
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink[] findByE2_PrevAndNext(
		long linkId, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @return the matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE_E_First(
		long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the last asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE_E_Last(
		long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param linkId the primary key of the current asset link
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink[] findByE_E_PrevAndNext(
		long linkId, long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns all the asset links where entryId1 = &#63; and type = &#63;.
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @return the matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset links where entryId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset links where entryId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE1_T_First(
		long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the last asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE1_T_Last(
		long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param linkId the primary key of the current asset link
	* @param entryId1 the entry id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink[] findByE1_T_PrevAndNext(
		long linkId, long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns all the asset links where entryId2 = &#63; and type = &#63;.
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @return the matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset links where entryId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset links where entryId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE2_T_First(
		long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the last asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE2_T_Last(
		long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the asset links before and after the current asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param linkId the primary key of the current asset link
	* @param entryId2 the entry id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink[] findByE2_T_PrevAndNext(
		long linkId, long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchLinkException} if it could not be found.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param type the type
	* @return the matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink findByE_E_T(
		long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param type the type
	* @return the matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink fetchByE_E_T(
		long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param type the type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink fetchByE_E_T(
		long entryId1, long entryId2, int type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the asset links.
	*
	* @return the asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset links where entryId1 = &#63; from the database.
	*
	* @param entryId1 the entry id1
	* @throws SystemException if a system exception occurred
	*/
	public void removeByE1(long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset links where entryId2 = &#63; from the database.
	*
	* @param entryId2 the entry id2
	* @throws SystemException if a system exception occurred
	*/
	public void removeByE2(long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset links where entryId1 = &#63; and entryId2 = &#63; from the database.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @throws SystemException if a system exception occurred
	*/
	public void removeByE_E(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset links where entryId1 = &#63; and type = &#63; from the database.
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByE1_T(long entryId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset links where entryId2 = &#63; and type = &#63; from the database.
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByE2_T(long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; from the database.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param type the type
	* @return the asset link that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink removeByE_E_T(
		long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException;

	/**
	* Removes all the asset links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset links where entryId1 = &#63;.
	*
	* @param entryId1 the entry id1
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public int countByE1(long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset links where entryId2 = &#63;.
	*
	* @param entryId2 the entry id2
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public int countByE2(long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset links where entryId1 = &#63; and entryId2 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public int countByE_E(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset links where entryId1 = &#63; and type = &#63;.
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public int countByE1_T(long entryId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset links where entryId2 = &#63; and type = &#63;.
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public int countByE2_T(long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset links where entryId1 = &#63; and entryId2 = &#63; and type = &#63;.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param type the type
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public int countByE_E_T(long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset links.
	*
	* @return the number of asset links
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}