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

package com.liferay.portlet.ratings.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.ratings.exception.NoSuchStatsException;
import com.liferay.portlet.ratings.model.RatingsStats;

/**
 * The persistence interface for the ratings stats service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.ratings.service.persistence.impl.RatingsStatsPersistenceImpl
 * @see RatingsStatsUtil
 * @generated
 */
@ProviderType
public interface RatingsStatsPersistence extends BasePersistence<RatingsStats> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RatingsStatsUtil} to access the ratings stats persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the ratings stats where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchStatsException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings stats
	* @throws NoSuchStatsException if a matching ratings stats could not be found
	*/
	public RatingsStats findByC_C(long classNameId, long classPK)
		throws NoSuchStatsException;

	/**
	* Returns the ratings stats where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings stats, or <code>null</code> if a matching ratings stats could not be found
	*/
	public RatingsStats fetchByC_C(long classNameId, long classPK);

	/**
	* Returns the ratings stats where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching ratings stats, or <code>null</code> if a matching ratings stats could not be found
	*/
	public RatingsStats fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache);

	/**
	* Removes the ratings stats where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the ratings stats that was removed
	*/
	public RatingsStats removeByC_C(long classNameId, long classPK)
		throws NoSuchStatsException;

	/**
	* Returns the number of ratings statses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching ratings statses
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Caches the ratings stats in the entity cache if it is enabled.
	*
	* @param ratingsStats the ratings stats
	*/
	public void cacheResult(RatingsStats ratingsStats);

	/**
	* Caches the ratings statses in the entity cache if it is enabled.
	*
	* @param ratingsStatses the ratings statses
	*/
	public void cacheResult(java.util.List<RatingsStats> ratingsStatses);

	/**
	* Creates a new ratings stats with the primary key. Does not add the ratings stats to the database.
	*
	* @param statsId the primary key for the new ratings stats
	* @return the new ratings stats
	*/
	public RatingsStats create(long statsId);

	/**
	* Removes the ratings stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsId the primary key of the ratings stats
	* @return the ratings stats that was removed
	* @throws NoSuchStatsException if a ratings stats with the primary key could not be found
	*/
	public RatingsStats remove(long statsId) throws NoSuchStatsException;

	public RatingsStats updateImpl(RatingsStats ratingsStats);

	/**
	* Returns the ratings stats with the primary key or throws a {@link NoSuchStatsException} if it could not be found.
	*
	* @param statsId the primary key of the ratings stats
	* @return the ratings stats
	* @throws NoSuchStatsException if a ratings stats with the primary key could not be found
	*/
	public RatingsStats findByPrimaryKey(long statsId)
		throws NoSuchStatsException;

	/**
	* Returns the ratings stats with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param statsId the primary key of the ratings stats
	* @return the ratings stats, or <code>null</code> if a ratings stats with the primary key could not be found
	*/
	public RatingsStats fetchByPrimaryKey(long statsId);

	@Override
	public java.util.Map<java.io.Serializable, RatingsStats> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the ratings statses.
	*
	* @return the ratings statses
	*/
	public java.util.List<RatingsStats> findAll();

	/**
	* Returns a range of all the ratings statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ratings statses
	* @param end the upper bound of the range of ratings statses (not inclusive)
	* @return the range of ratings statses
	*/
	public java.util.List<RatingsStats> findAll(int start, int end);

	/**
	* Returns an ordered range of all the ratings statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ratings statses
	* @param end the upper bound of the range of ratings statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ratings statses
	*/
	public java.util.List<RatingsStats> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsStats> orderByComparator);

	/**
	* Returns an ordered range of all the ratings statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RatingsStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ratings statses
	* @param end the upper bound of the range of ratings statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of ratings statses
	*/
	public java.util.List<RatingsStats> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RatingsStats> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the ratings statses from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of ratings statses.
	*
	* @return the number of ratings statses
	*/
	public int countAll();
}