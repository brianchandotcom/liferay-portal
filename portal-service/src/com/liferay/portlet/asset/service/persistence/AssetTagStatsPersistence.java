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

import com.liferay.portlet.asset.model.AssetTagStats;

/**
 * The persistence interface for the asset tag stats service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagStatsPersistenceImpl
 * @see AssetTagStatsUtil
 * @generated
 */
public interface AssetTagStatsPersistence extends BasePersistence<AssetTagStats> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetTagStatsUtil} to access the asset tag stats persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the asset tag stats in the entity cache if it is enabled.
	*
	* @param assetTagStats the asset tag stats
	*/
	public void cacheResult(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats);

	/**
	* Caches the asset tag statses in the entity cache if it is enabled.
	*
	* @param assetTagStatses the asset tag statses
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTagStats> assetTagStatses);

	/**
	* Creates a new asset tag stats with the primary key. Does not add the asset tag stats to the database.
	*
	* @param tagStatsId the primary key for the new asset tag stats
	* @return the new asset tag stats
	*/
	public com.liferay.portlet.asset.model.AssetTagStats create(long tagStatsId);

	/**
	* Removes the asset tag stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats that was removed
	* @throws com.liferay.portlet.asset.NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats remove(long tagStatsId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public com.liferay.portlet.asset.model.AssetTagStats updateImpl(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the asset tag stats with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchTagStatsException} if it could not be found.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats
	* @throws com.liferay.portlet.asset.NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats findByPrimaryKey(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	/**
	* Returns the asset tag stats with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats, or <code>null</code> if a asset tag stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats fetchByPrimaryKey(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the asset tag statses where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @return the matching asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset tag statses where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tagId the tag ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @return the range of matching asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset tag statses where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tagId the tag ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset tag stats in the ordered set where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag stats
	* @throws com.liferay.portlet.asset.NoSuchTagStatsException if a matching asset tag stats could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats findByTagId_First(
		long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	/**
	* Returns the last asset tag stats in the ordered set where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag stats
	* @throws com.liferay.portlet.asset.NoSuchTagStatsException if a matching asset tag stats could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats findByTagId_Last(
		long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	/**
	* Returns the asset tag statses before and after the current asset tag stats in the ordered set where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tagStatsId the primary key of the current asset tag stats
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag stats
	* @throws com.liferay.portlet.asset.NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats[] findByTagId_PrevAndNext(
		long tagStatsId, long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	/**
	* Returns all the asset tag statses where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset tag statses where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @return the range of matching asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset tag statses where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first asset tag stats in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag stats
	* @throws com.liferay.portlet.asset.NoSuchTagStatsException if a matching asset tag stats could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats findByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	/**
	* Returns the last asset tag stats in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag stats
	* @throws com.liferay.portlet.asset.NoSuchTagStatsException if a matching asset tag stats could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats findByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	/**
	* Returns the asset tag statses before and after the current asset tag stats in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tagStatsId the primary key of the current asset tag stats
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag stats
	* @throws com.liferay.portlet.asset.NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats[] findByClassNameId_PrevAndNext(
		long tagStatsId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	/**
	* Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchTagStatsException} if it could not be found.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the matching asset tag stats
	* @throws com.liferay.portlet.asset.NoSuchTagStatsException if a matching asset tag stats could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats findByT_C(long tagId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	/**
	* Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats fetchByT_C(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats fetchByT_C(
		long tagId, long classNameId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the asset tag statses.
	*
	* @return the asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the asset tag statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @return the range of asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the asset tag statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset tag statses where tagId = &#63; from the database.
	*
	* @param tagId the tag ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the asset tag statses where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the asset tag stats where tagId = &#63; and classNameId = &#63; from the database.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the asset tag stats that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagStats removeByT_C(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	/**
	* Removes all the asset tag statses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset tag statses where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @return the number of matching asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public int countByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset tag statses where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset tag statses where tagId = &#63; and classNameId = &#63;.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the number of matching asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_C(long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of asset tag statses.
	*
	* @return the number of asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}