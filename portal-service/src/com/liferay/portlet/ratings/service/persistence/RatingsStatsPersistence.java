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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.ratings.model.RatingsStats;

/**
 * The persistence interface for the ratings stats service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RatingsStatsPersistenceImpl
 * @see RatingsStatsUtil
 * @generated
 */
public interface RatingsStatsPersistence extends BasePersistence<RatingsStats> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RatingsStatsUtil} to access the ratings stats persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the ratings stats in the entity cache if it is enabled.
	*
	* @param ratingsStats the ratings stats
	*/
	public void cacheResult(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats);

	/**
	* Caches the ratings statses in the entity cache if it is enabled.
	*
	* @param ratingsStatses the ratings statses
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.ratings.model.RatingsStats> ratingsStatses);

	/**
	* Creates a new ratings stats with the primary key. Does not add the ratings stats to the database.
	*
	* @param statsId the primary key for the new ratings stats
	* @return the new ratings stats
	*/
	public com.liferay.portlet.ratings.model.RatingsStats create(long statsId);

	/**
	* Removes the ratings stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsId the primary key of the ratings stats
	* @return the ratings stats that was removed
	* @throws com.liferay.portlet.ratings.NoSuchStatsException if a ratings stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats remove(long statsId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchStatsException;

	public com.liferay.portlet.ratings.model.RatingsStats updateImpl(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the ratings stats with the primary key or throws a {@link com.liferay.portlet.ratings.NoSuchStatsException} if it could not be found.
	*
	* @param statsId the primary key of the ratings stats
	* @return the ratings stats
	* @throws com.liferay.portlet.ratings.NoSuchStatsException if a ratings stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats findByPrimaryKey(
		long statsId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchStatsException;

	/**
	* Returns the ratings stats with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param statsId the primary key of the ratings stats
	* @return the ratings stats, or <code>null</code> if a ratings stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats fetchByPrimaryKey(
		long statsId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the ratings stats where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.ratings.NoSuchStatsException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings stats
	* @throws com.liferay.portlet.ratings.NoSuchStatsException if a matching ratings stats could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchStatsException;

	/**
	* Returns the ratings stats where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching ratings stats, or <code>null</code> if a matching ratings stats could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the ratings stats where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching ratings stats, or <code>null</code> if a matching ratings stats could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the ratings statses.
	*
	* @return the ratings statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsStats> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the ratings statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of ratings statses
	* @param end the upper bound of the range of ratings statses (not inclusive)
	* @return the range of ratings statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsStats> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the ratings statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of ratings statses
	* @param end the upper bound of the range of ratings statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ratings statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsStats> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the ratings stats where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the ratings stats that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats removeByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchStatsException;

	/**
	* Removes all the ratings statses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of ratings statses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching ratings statses
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of ratings statses.
	*
	* @return the number of ratings statses
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}