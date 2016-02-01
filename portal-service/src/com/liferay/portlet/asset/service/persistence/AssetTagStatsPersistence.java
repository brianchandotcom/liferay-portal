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

import com.liferay.portlet.asset.exception.NoSuchTagStatsException;
import com.liferay.portlet.asset.model.AssetTagStats;

/**
 * The persistence interface for the asset tag stats service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetTagStatsPersistenceImpl
 * @see AssetTagStatsUtil
 * @generated
 */
@ProviderType
public interface AssetTagStatsPersistence extends BasePersistence<AssetTagStats> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetTagStatsUtil} to access the asset tag stats persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset tag statses where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @return the matching asset tag statses
	*/
	public java.util.List<AssetTagStats> findByTagId(long tagId);

	/**
	* Returns a range of all the asset tag statses where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tagId the tag ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @return the range of matching asset tag statses
	*/
	public java.util.List<AssetTagStats> findByTagId(long tagId, int start,
		int end);

	/**
	* Returns an ordered range of all the asset tag statses where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tagId the tag ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tag statses
	*/
	public java.util.List<AssetTagStats> findByTagId(long tagId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator);

	/**
	* Returns an ordered range of all the asset tag statses where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tagId the tag ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset tag statses
	*/
	public java.util.List<AssetTagStats> findByTagId(long tagId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset tag stats in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag stats
	* @throws NoSuchTagStatsException if a matching asset tag stats could not be found
	*/
	public AssetTagStats findByTagId_First(long tagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator)
		throws NoSuchTagStatsException;

	/**
	* Returns the first asset tag stats in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public AssetTagStats fetchByTagId_First(long tagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator);

	/**
	* Returns the last asset tag stats in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag stats
	* @throws NoSuchTagStatsException if a matching asset tag stats could not be found
	*/
	public AssetTagStats findByTagId_Last(long tagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator)
		throws NoSuchTagStatsException;

	/**
	* Returns the last asset tag stats in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public AssetTagStats fetchByTagId_Last(long tagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator);

	/**
	* Returns the asset tag statses before and after the current asset tag stats in the ordered set where tagId = &#63;.
	*
	* @param tagStatsId the primary key of the current asset tag stats
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag stats
	* @throws NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	*/
	public AssetTagStats[] findByTagId_PrevAndNext(long tagStatsId, long tagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator)
		throws NoSuchTagStatsException;

	/**
	* Removes all the asset tag statses where tagId = &#63; from the database.
	*
	* @param tagId the tag ID
	*/
	public void removeByTagId(long tagId);

	/**
	* Returns the number of asset tag statses where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @return the number of matching asset tag statses
	*/
	public int countByTagId(long tagId);

	/**
	* Returns all the asset tag statses where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching asset tag statses
	*/
	public java.util.List<AssetTagStats> findByClassNameId(long classNameId);

	/**
	* Returns a range of all the asset tag statses where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @return the range of matching asset tag statses
	*/
	public java.util.List<AssetTagStats> findByClassNameId(long classNameId,
		int start, int end);

	/**
	* Returns an ordered range of all the asset tag statses where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tag statses
	*/
	public java.util.List<AssetTagStats> findByClassNameId(long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator);

	/**
	* Returns an ordered range of all the asset tag statses where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset tag statses
	*/
	public java.util.List<AssetTagStats> findByClassNameId(long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset tag stats in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag stats
	* @throws NoSuchTagStatsException if a matching asset tag stats could not be found
	*/
	public AssetTagStats findByClassNameId_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator)
		throws NoSuchTagStatsException;

	/**
	* Returns the first asset tag stats in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public AssetTagStats fetchByClassNameId_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator);

	/**
	* Returns the last asset tag stats in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag stats
	* @throws NoSuchTagStatsException if a matching asset tag stats could not be found
	*/
	public AssetTagStats findByClassNameId_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator)
		throws NoSuchTagStatsException;

	/**
	* Returns the last asset tag stats in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public AssetTagStats fetchByClassNameId_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator);

	/**
	* Returns the asset tag statses before and after the current asset tag stats in the ordered set where classNameId = &#63;.
	*
	* @param tagStatsId the primary key of the current asset tag stats
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag stats
	* @throws NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	*/
	public AssetTagStats[] findByClassNameId_PrevAndNext(long tagStatsId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator)
		throws NoSuchTagStatsException;

	/**
	* Removes all the asset tag statses where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public void removeByClassNameId(long classNameId);

	/**
	* Returns the number of asset tag statses where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching asset tag statses
	*/
	public int countByClassNameId(long classNameId);

	/**
	* Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or throws a {@link NoSuchTagStatsException} if it could not be found.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the matching asset tag stats
	* @throws NoSuchTagStatsException if a matching asset tag stats could not be found
	*/
	public AssetTagStats findByT_C(long tagId, long classNameId)
		throws NoSuchTagStatsException;

	/**
	* Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public AssetTagStats fetchByT_C(long tagId, long classNameId);

	/**
	* Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public AssetTagStats fetchByT_C(long tagId, long classNameId,
		boolean retrieveFromCache);

	/**
	* Removes the asset tag stats where tagId = &#63; and classNameId = &#63; from the database.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the asset tag stats that was removed
	*/
	public AssetTagStats removeByT_C(long tagId, long classNameId)
		throws NoSuchTagStatsException;

	/**
	* Returns the number of asset tag statses where tagId = &#63; and classNameId = &#63;.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the number of matching asset tag statses
	*/
	public int countByT_C(long tagId, long classNameId);

	/**
	* Caches the asset tag stats in the entity cache if it is enabled.
	*
	* @param assetTagStats the asset tag stats
	*/
	public void cacheResult(AssetTagStats assetTagStats);

	/**
	* Caches the asset tag statses in the entity cache if it is enabled.
	*
	* @param assetTagStatses the asset tag statses
	*/
	public void cacheResult(java.util.List<AssetTagStats> assetTagStatses);

	/**
	* Creates a new asset tag stats with the primary key. Does not add the asset tag stats to the database.
	*
	* @param tagStatsId the primary key for the new asset tag stats
	* @return the new asset tag stats
	*/
	public AssetTagStats create(long tagStatsId);

	/**
	* Removes the asset tag stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats that was removed
	* @throws NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	*/
	public AssetTagStats remove(long tagStatsId) throws NoSuchTagStatsException;

	public AssetTagStats updateImpl(AssetTagStats assetTagStats);

	/**
	* Returns the asset tag stats with the primary key or throws a {@link NoSuchTagStatsException} if it could not be found.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats
	* @throws NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	*/
	public AssetTagStats findByPrimaryKey(long tagStatsId)
		throws NoSuchTagStatsException;

	/**
	* Returns the asset tag stats with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats, or <code>null</code> if a asset tag stats with the primary key could not be found
	*/
	public AssetTagStats fetchByPrimaryKey(long tagStatsId);

	@Override
	public java.util.Map<java.io.Serializable, AssetTagStats> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset tag statses.
	*
	* @return the asset tag statses
	*/
	public java.util.List<AssetTagStats> findAll();

	/**
	* Returns a range of all the asset tag statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @return the range of asset tag statses
	*/
	public java.util.List<AssetTagStats> findAll(int start, int end);

	/**
	* Returns an ordered range of all the asset tag statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tag statses
	*/
	public java.util.List<AssetTagStats> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator);

	/**
	* Returns an ordered range of all the asset tag statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset tag statses
	*/
	public java.util.List<AssetTagStats> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagStats> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset tag statses from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset tag statses.
	*
	* @return the number of asset tag statses
	*/
	public int countAll();
}