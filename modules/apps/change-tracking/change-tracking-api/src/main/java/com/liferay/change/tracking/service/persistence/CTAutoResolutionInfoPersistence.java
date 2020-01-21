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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.exception.NoSuchAutoResolutionInfoException;
import com.liferay.change.tracking.model.CTAutoResolutionInfo;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct auto resolution info service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTAutoResolutionInfoUtil
 * @generated
 */
@ProviderType
public interface CTAutoResolutionInfoPersistence
	extends BasePersistence<CTAutoResolutionInfo> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTAutoResolutionInfoUtil} to access the ct auto resolution info persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct auto resolution infos
	 */
	public java.util.List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId);

	/**
	 * Returns a range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of matching ct auto resolution infos
	 */
	public java.util.List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct auto resolution infos
	 */
	public java.util.List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTAutoResolutionInfo>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct auto resolution infos
	 */
	public java.util.List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTAutoResolutionInfo>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	public CTAutoResolutionInfo findByCTCollectionId_First(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException;

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	public CTAutoResolutionInfo fetchByCTCollectionId_First(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTAutoResolutionInfo>
			orderByComparator);

	/**
	 * Returns the last ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	public CTAutoResolutionInfo findByCTCollectionId_Last(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException;

	/**
	 * Returns the last ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	public CTAutoResolutionInfo fetchByCTCollectionId_Last(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTAutoResolutionInfo>
			orderByComparator);

	/**
	 * Returns the ct auto resolution infos before and after the current ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the current ct auto resolution info
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	public CTAutoResolutionInfo[] findByCTCollectionId_PrevAndNext(
			long ctAutoResolutionInfoId, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException;

	/**
	 * Removes all the ct auto resolution infos where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByCTCollectionId(long ctCollectionId);

	/**
	 * Returns the number of ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct auto resolution infos
	 */
	public int countByCTCollectionId(long ctCollectionId);

	/**
	 * Caches the ct auto resolution info in the entity cache if it is enabled.
	 *
	 * @param ctAutoResolutionInfo the ct auto resolution info
	 */
	public void cacheResult(CTAutoResolutionInfo ctAutoResolutionInfo);

	/**
	 * Caches the ct auto resolution infos in the entity cache if it is enabled.
	 *
	 * @param ctAutoResolutionInfos the ct auto resolution infos
	 */
	public void cacheResult(
		java.util.List<CTAutoResolutionInfo> ctAutoResolutionInfos);

	/**
	 * Creates a new ct auto resolution info with the primary key. Does not add the ct auto resolution info to the database.
	 *
	 * @param ctAutoResolutionInfoId the primary key for the new ct auto resolution info
	 * @return the new ct auto resolution info
	 */
	public CTAutoResolutionInfo create(long ctAutoResolutionInfoId);

	/**
	 * Removes the ct auto resolution info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info that was removed
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	public CTAutoResolutionInfo remove(long ctAutoResolutionInfoId)
		throws NoSuchAutoResolutionInfoException;

	public CTAutoResolutionInfo updateImpl(
		CTAutoResolutionInfo ctAutoResolutionInfo);

	/**
	 * Returns the ct auto resolution info with the primary key or throws a <code>NoSuchAutoResolutionInfoException</code> if it could not be found.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	public CTAutoResolutionInfo findByPrimaryKey(long ctAutoResolutionInfoId)
		throws NoSuchAutoResolutionInfoException;

	/**
	 * Returns the ct auto resolution info with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info, or <code>null</code> if a ct auto resolution info with the primary key could not be found
	 */
	public CTAutoResolutionInfo fetchByPrimaryKey(long ctAutoResolutionInfoId);

	/**
	 * Returns all the ct auto resolution infos.
	 *
	 * @return the ct auto resolution infos
	 */
	public java.util.List<CTAutoResolutionInfo> findAll();

	/**
	 * Returns a range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of ct auto resolution infos
	 */
	public java.util.List<CTAutoResolutionInfo> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct auto resolution infos
	 */
	public java.util.List<CTAutoResolutionInfo> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTAutoResolutionInfo>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct auto resolution infos
	 */
	public java.util.List<CTAutoResolutionInfo> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTAutoResolutionInfo>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct auto resolution infos from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct auto resolution infos.
	 *
	 * @return the number of ct auto resolution infos
	 */
	public int countAll();

}