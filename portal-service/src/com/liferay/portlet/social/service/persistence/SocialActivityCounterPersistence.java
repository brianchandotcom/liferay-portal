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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.model.SocialActivityCounter;

/**
 * The persistence interface for the social activity counter service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityCounterPersistenceImpl
 * @see SocialActivityCounterUtil
 * @generated
 */
public interface SocialActivityCounterPersistence extends BasePersistence<SocialActivityCounter> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityCounterUtil} to access the social activity counter persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the social activity counter in the entity cache if it is enabled.
	*
	* @param socialActivityCounter the social activity counter
	*/
	public void cacheResult(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter);

	/**
	* Caches the social activity counters in the entity cache if it is enabled.
	*
	* @param socialActivityCounters the social activity counters
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> socialActivityCounters);

	/**
	* Creates a new social activity counter with the primary key. Does not add the social activity counter to the database.
	*
	* @param activityCounterId the primary key for the new social activity counter
	* @return the new social activity counter
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter create(
		long activityCounterId);

	/**
	* Removes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter that was removed
	* @throws com.liferay.portlet.social.NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter remove(
		long activityCounterId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	public com.liferay.portlet.social.model.SocialActivityCounter updateImpl(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity counter with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivityCounterException} if it could not be found.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter
	* @throws com.liferay.portlet.social.NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter findByPrimaryKey(
		long activityCounterId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Returns the social activity counter with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter, or <code>null</code> if a social activity counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter fetchByPrimaryKey(
		long activityCounterId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of matching social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter
	* @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter
	* @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Returns the social activity counters before and after the current social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityCounterId the primary key of the current social activity counter
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity counter
	* @throws com.liferay.portlet.social.NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter[] findByC_C_PrevAndNext(
		long activityCounterId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Returns all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @return the matching social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findByG_C_C_O(
		long groupId, long classNameId, long classPK, int ownerType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of matching social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findByG_C_C_O(
		long groupId, long classNameId, long classPK, int ownerType, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findByG_C_C_O(
		long groupId, long classNameId, long classPK, int ownerType, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter
	* @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter findByG_C_C_O_First(
		long groupId, long classNameId, long classPK, int ownerType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter
	* @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter findByG_C_C_O_Last(
		long groupId, long classNameId, long classPK, int ownerType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Returns the social activity counters before and after the current social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityCounterId the primary key of the current social activity counter
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity counter
	* @throws com.liferay.portlet.social.NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter[] findByG_C_C_O_PrevAndNext(
		long activityCounterId, long groupId, long classNameId, long classPK,
		int ownerType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityCounterException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the matching social activity counter
	* @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter findByG_C_C_N_O_S(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int startPeriod)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter fetchByG_C_C_N_O_S(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int startPeriod)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter fetchByG_C_C_N_O_S(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int startPeriod, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityCounterException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the matching social activity counter
	* @throws com.liferay.portlet.social.NoSuchActivityCounterException if a matching social activity counter could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter findByG_C_C_N_O_E(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter fetchByG_C_C_N_O_E(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter fetchByG_C_C_N_O_E(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int endPeriod, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity counters.
	*
	* @return the social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activity counters where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C_C_O(long groupId, long classNameId, long classPK,
		int ownerType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the social activity counter that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter removeByG_C_C_N_O_S(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int startPeriod)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the social activity counter that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter removeByG_C_C_N_O_E(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityCounterException;

	/**
	* Removes all the social activity counters from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @return the number of matching social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_C_O(long groupId, long classNameId, long classPK,
		int ownerType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the number of matching social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_C_N_O_S(long groupId, long classNameId, long classPK,
		java.lang.String name, int ownerType, int startPeriod)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the number of matching social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_C_N_O_E(long groupId, long classNameId, long classPK,
		java.lang.String name, int ownerType, int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity counters.
	*
	* @return the number of social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}