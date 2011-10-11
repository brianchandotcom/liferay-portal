/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.model.SocialActivityStatsEntry;

/**
 * The persistence interface for the social activity stats entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityStatsEntryPersistenceImpl
 * @see SocialActivityStatsEntryUtil
 * @generated
 */
public interface SocialActivityStatsEntryPersistence extends BasePersistence<SocialActivityStatsEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityStatsEntryUtil} to access the social activity stats entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the social activity stats entry in the entity cache if it is enabled.
	*
	* @param socialActivityStatsEntry the social activity stats entry
	*/
	public void cacheResult(
		com.liferay.portlet.social.model.SocialActivityStatsEntry socialActivityStatsEntry);

	/**
	* Caches the social activity stats entries in the entity cache if it is enabled.
	*
	* @param socialActivityStatsEntries the social activity stats entries
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> socialActivityStatsEntries);

	/**
	* Creates a new social activity stats entry with the primary key. Does not add the social activity stats entry to the database.
	*
	* @param activityStatsEntryId the primary key for the new social activity stats entry
	* @return the new social activity stats entry
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry create(
		long activityStatsEntryId);

	/**
	* Removes the social activity stats entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityStatsEntryId the primary key of the social activity stats entry
	* @return the social activity stats entry that was removed
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry remove(
		long activityStatsEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	public com.liferay.portlet.social.model.SocialActivityStatsEntry updateImpl(
		com.liferay.portlet.social.model.SocialActivityStatsEntry socialActivityStatsEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity stats entry with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivityStatsEntryException} if it could not be found.
	*
	* @param activityStatsEntryId the primary key of the social activity stats entry
	* @return the social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry findByPrimaryKey(
		long activityStatsEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Returns the social activity stats entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityStatsEntryId the primary key of the social activity stats entry
	* @return the social activity stats entry, or <code>null</code> if a social activity stats entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry fetchByPrimaryKey(
		long activityStatsEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity stats entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByCN_CP(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity stats entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @return the range of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByCN_CP(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity stats entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByCN_CP(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity stats entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry findByCN_CP_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Returns the last social activity stats entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry findByCN_CP_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Returns the social activity stats entries before and after the current social activity stats entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityStatsEntryId the primary key of the current social activity stats entry
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry[] findByCN_CP_PrevAndNext(
		long activityStatsEntryId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Returns all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @return the matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByG_CN_CP_CT(
		long groupId, long classNameId, long classPK, int classType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @return the range of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByG_CN_CP_CT(
		long groupId, long classNameId, long classPK, int classType, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findByG_CN_CP_CT(
		long groupId, long classNameId, long classPK, int classType, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity stats entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry findByG_CN_CP_CT_First(
		long groupId, long classNameId, long classPK, int classType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Returns the last social activity stats entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry findByG_CN_CP_CT_Last(
		long groupId, long classNameId, long classPK, int classType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Returns the social activity stats entries before and after the current social activity stats entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityStatsEntryId the primary key of the current social activity stats entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a social activity stats entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry[] findByG_CN_CP_CT_PrevAndNext(
		long activityStatsEntryId, long groupId, long classNameId,
		long classPK, int classType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityStatsEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodEnd the stat period end
	* @return the matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry findByG_CN_CP_CT_SN_SPE(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodEnd the stat period end
	* @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPE(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodEnd the stat period end
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPE(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodEnd, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityStatsEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodStart the stat period start
	* @return the matching social activity stats entry
	* @throws com.liferay.portlet.social.NoSuchActivityStatsEntryException if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry findByG_CN_CP_CT_SN_SPS(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodStart)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodStart the stat period start
	* @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPS(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodStart)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodStart the stat period start
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social activity stats entry, or <code>null</code> if a matching social activity stats entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityStatsEntry fetchByG_CN_CP_CT_SN_SPS(
		long groupId, long classNameId, long classPK, int classType,
		java.lang.String statName, int statPeriodStart,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity stats entries.
	*
	* @return the social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity stats entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @return the range of social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity stats entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activity stats entries
	* @param end the upper bound of the range of social activity stats entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activity stats entries where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCN_CP(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_CN_CP_CT(long groupId, long classNameId,
		long classPK, int classType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodEnd the stat period end
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_CN_CP_CT_SN_SPE(long groupId, long classNameId,
		long classPK, int classType, java.lang.String statName,
		int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Removes the social activity stats entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodStart the stat period start
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_CN_CP_CT_SN_SPS(long groupId, long classNameId,
		long classPK, int classType, java.lang.String statName,
		int statPeriodStart)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityStatsEntryException;

	/**
	* Removes all the social activity stats entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity stats entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByCN_CP(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @return the number of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_CN_CP_CT(long groupId, long classNameId, long classPK,
		int classType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodEnd = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodEnd the stat period end
	* @return the number of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_CN_CP_CT_SN_SPE(long groupId, long classNameId,
		long classPK, int classType, java.lang.String statName,
		int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity stats entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and classType = &#63; and statName = &#63; and statPeriodStart = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param classType the class type
	* @param statName the stat name
	* @param statPeriodStart the stat period start
	* @return the number of matching social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_CN_CP_CT_SN_SPS(long groupId, long classNameId,
		long classPK, int classType, java.lang.String statName,
		int statPeriodStart)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity stats entries.
	*
	* @return the number of social activity stats entries
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public SocialActivityStatsEntry remove(
		SocialActivityStatsEntry socialActivityStatsEntry)
		throws SystemException;
}