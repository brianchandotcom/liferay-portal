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

import com.liferay.portlet.social.model.SocialActivityAchievement;

/**
 * The persistence interface for the social activity achievement service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityAchievementPersistenceImpl
 * @see SocialActivityAchievementUtil
 * @generated
 */
public interface SocialActivityAchievementPersistence extends BasePersistence<SocialActivityAchievement> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityAchievementUtil} to access the social activity achievement persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the social activity achievement in the entity cache if it is enabled.
	*
	* @param socialActivityAchievement the social activity achievement
	*/
	public void cacheResult(
		com.liferay.portlet.social.model.SocialActivityAchievement socialActivityAchievement);

	/**
	* Caches the social activity achievements in the entity cache if it is enabled.
	*
	* @param socialActivityAchievements the social activity achievements
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> socialActivityAchievements);

	/**
	* Creates a new social activity achievement with the primary key. Does not add the social activity achievement to the database.
	*
	* @param activityAchievementId the primary key for the new social activity achievement
	* @return the new social activity achievement
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement create(
		long activityAchievementId);

	/**
	* Removes the social activity achievement with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityAchievementId the primary key of the social activity achievement
	* @return the social activity achievement that was removed
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement remove(
		long activityAchievementId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	public com.liferay.portlet.social.model.SocialActivityAchievement updateImpl(
		com.liferay.portlet.social.model.SocialActivityAchievement socialActivityAchievement,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity achievement with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivityAchievementException} if it could not be found.
	*
	* @param activityAchievementId the primary key of the social activity achievement
	* @return the social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement findByPrimaryKey(
		long activityAchievementId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the social activity achievement with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityAchievementId the primary key of the social activity achievement
	* @return the social activity achievement, or <code>null</code> if a social activity achievement with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement fetchByPrimaryKey(
		long activityAchievementId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity achievements where groupId = &#63; and firstUnlock = &#63;.
	*
	* @param groupId the group ID
	* @param firstUnlock the first unlock
	* @return the matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_F(
		long groupId, boolean firstUnlock)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity achievements where groupId = &#63; and firstUnlock = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param firstUnlock the first unlock
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_F(
		long groupId, boolean firstUnlock, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and firstUnlock = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param firstUnlock the first unlock
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_F(
		long groupId, boolean firstUnlock, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and firstUnlock = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param firstUnlock the first unlock
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement findByG_F_First(
		long groupId, boolean firstUnlock,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and firstUnlock = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param firstUnlock the first unlock
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement findByG_F_Last(
		long groupId, boolean firstUnlock,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the social activity achievements before and after the current social activity achievement in the ordered set where groupId = &#63; and firstUnlock = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityAchievementId the primary key of the current social activity achievement
	* @param groupId the group ID
	* @param firstUnlock the first unlock
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement[] findByG_F_PrevAndNext(
		long activityAchievementId, long groupId, boolean firstUnlock,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns all the social activity achievements where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity achievements where groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_N(
		long groupId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_N(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement findByG_N_First(
		long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement findByG_N_Last(
		long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the social activity achievements before and after the current social activity achievement in the ordered set where groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityAchievementId the primary key of the current social activity achievement
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement[] findByG_N_PrevAndNext(
		long activityAchievementId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns all the social activity achievements where groupId = &#63; and unlockedBy = &#63;.
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @return the matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_UB(
		long groupId, long unlockedBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity achievements where groupId = &#63; and unlockedBy = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_UB(
		long groupId, long unlockedBy, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and unlockedBy = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_UB(
		long groupId, long unlockedBy, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and unlockedBy = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement findByG_UB_First(
		long groupId, long unlockedBy,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and unlockedBy = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement findByG_UB_Last(
		long groupId, long unlockedBy,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the social activity achievements before and after the current social activity achievement in the ordered set where groupId = &#63; and unlockedBy = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityAchievementId the primary key of the current social activity achievement
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement[] findByG_UB_PrevAndNext(
		long activityAchievementId, long groupId, long unlockedBy,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the social activity achievement where groupId = &#63; and name = &#63; and unlockedBy = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityAchievementException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @param unlockedBy the unlocked by
	* @return the matching social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement findByG_N_UB(
		long groupId, java.lang.String name, long unlockedBy)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the social activity achievement where groupId = &#63; and name = &#63; and unlockedBy = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param unlockedBy the unlocked by
	* @return the matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement fetchByG_N_UB(
		long groupId, java.lang.String name, long unlockedBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity achievement where groupId = &#63; and name = &#63; and unlockedBy = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param unlockedBy the unlocked by
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement fetchByG_N_UB(
		long groupId, java.lang.String name, long unlockedBy,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activity achievements where groupId = &#63; and unlockedBy = &#63; and firstUnlock = &#63;.
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param firstUnlock the first unlock
	* @return the matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_UB_F(
		long groupId, long unlockedBy, boolean firstUnlock)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity achievements where groupId = &#63; and unlockedBy = &#63; and firstUnlock = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param firstUnlock the first unlock
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_UB_F(
		long groupId, long unlockedBy, boolean firstUnlock, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and unlockedBy = &#63; and firstUnlock = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param firstUnlock the first unlock
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findByG_UB_F(
		long groupId, long unlockedBy, boolean firstUnlock, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and unlockedBy = &#63; and firstUnlock = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param firstUnlock the first unlock
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement findByG_UB_F_First(
		long groupId, long unlockedBy, boolean firstUnlock,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and unlockedBy = &#63; and firstUnlock = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param firstUnlock the first unlock
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a matching social activity achievement could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement findByG_UB_F_Last(
		long groupId, long unlockedBy, boolean firstUnlock,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns the social activity achievements before and after the current social activity achievement in the ordered set where groupId = &#63; and unlockedBy = &#63; and firstUnlock = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityAchievementId the primary key of the current social activity achievement
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param firstUnlock the first unlock
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity achievement
	* @throws com.liferay.portlet.social.NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityAchievement[] findByG_UB_F_PrevAndNext(
		long activityAchievementId, long groupId, long unlockedBy,
		boolean firstUnlock,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Returns all the social activity achievements.
	*
	* @return the social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activity achievements.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activity achievements.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activity achievements where groupId = &#63; and firstUnlock = &#63; from the database.
	*
	* @param groupId the group ID
	* @param firstUnlock the first unlock
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_F(long groupId, boolean firstUnlock)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activity achievements where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activity achievements where groupId = &#63; and unlockedBy = &#63; from the database.
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_UB(long groupId, long unlockedBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the social activity achievement where groupId = &#63; and name = &#63; and unlockedBy = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @param unlockedBy the unlocked by
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_N_UB(long groupId, java.lang.String name,
		long unlockedBy)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityAchievementException;

	/**
	* Removes all the social activity achievements where groupId = &#63; and unlockedBy = &#63; and firstUnlock = &#63; from the database.
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param firstUnlock the first unlock
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_UB_F(long groupId, long unlockedBy,
		boolean firstUnlock)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activity achievements from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity achievements where groupId = &#63; and firstUnlock = &#63;.
	*
	* @param groupId the group ID
	* @param firstUnlock the first unlock
	* @return the number of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_F(long groupId, boolean firstUnlock)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity achievements where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity achievements where groupId = &#63; and unlockedBy = &#63;.
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @return the number of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_UB(long groupId, long unlockedBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity achievements where groupId = &#63; and name = &#63; and unlockedBy = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param unlockedBy the unlocked by
	* @return the number of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_N_UB(long groupId, java.lang.String name,
		long unlockedBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity achievements where groupId = &#63; and unlockedBy = &#63; and firstUnlock = &#63;.
	*
	* @param groupId the group ID
	* @param unlockedBy the unlocked by
	* @param firstUnlock the first unlock
	* @return the number of matching social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_UB_F(long groupId, long unlockedBy, boolean firstUnlock)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activity achievements.
	*
	* @return the number of social activity achievements
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public SocialActivityAchievement remove(
		SocialActivityAchievement socialActivityAchievement)
		throws SystemException;
}