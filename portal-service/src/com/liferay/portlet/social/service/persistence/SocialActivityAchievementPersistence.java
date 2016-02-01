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

package com.liferay.portlet.social.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.exception.NoSuchActivityAchievementException;
import com.liferay.portlet.social.model.SocialActivityAchievement;

/**
 * The persistence interface for the social activity achievement service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.persistence.impl.SocialActivityAchievementPersistenceImpl
 * @see SocialActivityAchievementUtil
 * @generated
 */
@ProviderType
public interface SocialActivityAchievementPersistence extends BasePersistence<SocialActivityAchievement> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityAchievementUtil} to access the social activity achievement persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the social activity achievements where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByGroupId(long groupId);

	/**
	* Returns a range of all the social activity achievements where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns the social activity achievements before and after the current social activity achievement in the ordered set where groupId = &#63;.
	*
	* @param activityAchievementId the primary key of the current social activity achievement
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity achievement
	* @throws NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	*/
	public SocialActivityAchievement[] findByGroupId_PrevAndNext(
		long activityAchievementId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Removes all the social activity achievements where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of social activity achievements where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activity achievements
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the social activity achievements where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_U(long groupId,
		long userId);

	/**
	* Returns a range of all the social activity achievements where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_U(long groupId,
		long userId, int start, int end);

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_U(long groupId,
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_U(long groupId,
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByG_U_First(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByG_U_First(long groupId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByG_U_Last(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByG_U_Last(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns the social activity achievements before and after the current social activity achievement in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param activityAchievementId the primary key of the current social activity achievement
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity achievement
	* @throws NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	*/
	public SocialActivityAchievement[] findByG_U_PrevAndNext(
		long activityAchievementId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Removes all the social activity achievements where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	*/
	public void removeByG_U(long groupId, long userId);

	/**
	* Returns the number of social activity achievements where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching social activity achievements
	*/
	public int countByG_U(long groupId, long userId);

	/**
	* Returns all the social activity achievements where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_N(long groupId,
		java.lang.String name);

	/**
	* Returns a range of all the social activity achievements where groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_N(long groupId,
		java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_N(long groupId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_N(long groupId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByG_N_First(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByG_N_First(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByG_N_Last(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByG_N_Last(long groupId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns the social activity achievements before and after the current social activity achievement in the ordered set where groupId = &#63; and name = &#63;.
	*
	* @param activityAchievementId the primary key of the current social activity achievement
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity achievement
	* @throws NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	*/
	public SocialActivityAchievement[] findByG_N_PrevAndNext(
		long activityAchievementId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Removes all the social activity achievements where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public void removeByG_N(long groupId, java.lang.String name);

	/**
	* Returns the number of social activity achievements where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching social activity achievements
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Returns all the social activity achievements where groupId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param firstInGroup the first in group
	* @return the matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_F(long groupId,
		boolean firstInGroup);

	/**
	* Returns a range of all the social activity achievements where groupId = &#63; and firstInGroup = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param firstInGroup the first in group
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_F(long groupId,
		boolean firstInGroup, int start, int end);

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and firstInGroup = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param firstInGroup the first in group
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_F(long groupId,
		boolean firstInGroup, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and firstInGroup = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param firstInGroup the first in group
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_F(long groupId,
		boolean firstInGroup, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param firstInGroup the first in group
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByG_F_First(long groupId,
		boolean firstInGroup,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param firstInGroup the first in group
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByG_F_First(long groupId,
		boolean firstInGroup,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param firstInGroup the first in group
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByG_F_Last(long groupId,
		boolean firstInGroup,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param firstInGroup the first in group
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByG_F_Last(long groupId,
		boolean firstInGroup,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns the social activity achievements before and after the current social activity achievement in the ordered set where groupId = &#63; and firstInGroup = &#63;.
	*
	* @param activityAchievementId the primary key of the current social activity achievement
	* @param groupId the group ID
	* @param firstInGroup the first in group
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity achievement
	* @throws NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	*/
	public SocialActivityAchievement[] findByG_F_PrevAndNext(
		long activityAchievementId, long groupId, boolean firstInGroup,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Removes all the social activity achievements where groupId = &#63; and firstInGroup = &#63; from the database.
	*
	* @param groupId the group ID
	* @param firstInGroup the first in group
	*/
	public void removeByG_F(long groupId, boolean firstInGroup);

	/**
	* Returns the number of social activity achievements where groupId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param firstInGroup the first in group
	* @return the number of matching social activity achievements
	*/
	public int countByG_F(long groupId, boolean firstInGroup);

	/**
	* Returns the social activity achievement where groupId = &#63; and userId = &#63; and name = &#63; or throws a {@link NoSuchActivityAchievementException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @return the matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByG_U_N(long groupId, long userId,
		java.lang.String name) throws NoSuchActivityAchievementException;

	/**
	* Returns the social activity achievement where groupId = &#63; and userId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @return the matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByG_U_N(long groupId, long userId,
		java.lang.String name);

	/**
	* Returns the social activity achievement where groupId = &#63; and userId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByG_U_N(long groupId, long userId,
		java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the social activity achievement where groupId = &#63; and userId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @return the social activity achievement that was removed
	*/
	public SocialActivityAchievement removeByG_U_N(long groupId, long userId,
		java.lang.String name) throws NoSuchActivityAchievementException;

	/**
	* Returns the number of social activity achievements where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @return the number of matching social activity achievements
	*/
	public int countByG_U_N(long groupId, long userId, java.lang.String name);

	/**
	* Returns all the social activity achievements where groupId = &#63; and userId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	* @return the matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_U_F(long groupId,
		long userId, boolean firstInGroup);

	/**
	* Returns a range of all the social activity achievements where groupId = &#63; and userId = &#63; and firstInGroup = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_U_F(long groupId,
		long userId, boolean firstInGroup, int start, int end);

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and userId = &#63; and firstInGroup = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_U_F(long groupId,
		long userId, boolean firstInGroup, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns an ordered range of all the social activity achievements where groupId = &#63; and userId = &#63; and firstInGroup = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findByG_U_F(long groupId,
		long userId, boolean firstInGroup, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and userId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByG_U_F_First(long groupId,
		long userId, boolean firstInGroup,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Returns the first social activity achievement in the ordered set where groupId = &#63; and userId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByG_U_F_First(long groupId,
		long userId, boolean firstInGroup,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and userId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement
	* @throws NoSuchActivityAchievementException if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement findByG_U_F_Last(long groupId,
		long userId, boolean firstInGroup,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Returns the last social activity achievement in the ordered set where groupId = &#63; and userId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity achievement, or <code>null</code> if a matching social activity achievement could not be found
	*/
	public SocialActivityAchievement fetchByG_U_F_Last(long groupId,
		long userId, boolean firstInGroup,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns the social activity achievements before and after the current social activity achievement in the ordered set where groupId = &#63; and userId = &#63; and firstInGroup = &#63;.
	*
	* @param activityAchievementId the primary key of the current social activity achievement
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity achievement
	* @throws NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	*/
	public SocialActivityAchievement[] findByG_U_F_PrevAndNext(
		long activityAchievementId, long groupId, long userId,
		boolean firstInGroup,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator)
		throws NoSuchActivityAchievementException;

	/**
	* Removes all the social activity achievements where groupId = &#63; and userId = &#63; and firstInGroup = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	*/
	public void removeByG_U_F(long groupId, long userId, boolean firstInGroup);

	/**
	* Returns the number of social activity achievements where groupId = &#63; and userId = &#63; and firstInGroup = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param firstInGroup the first in group
	* @return the number of matching social activity achievements
	*/
	public int countByG_U_F(long groupId, long userId, boolean firstInGroup);

	/**
	* Caches the social activity achievement in the entity cache if it is enabled.
	*
	* @param socialActivityAchievement the social activity achievement
	*/
	public void cacheResult(SocialActivityAchievement socialActivityAchievement);

	/**
	* Caches the social activity achievements in the entity cache if it is enabled.
	*
	* @param socialActivityAchievements the social activity achievements
	*/
	public void cacheResult(
		java.util.List<SocialActivityAchievement> socialActivityAchievements);

	/**
	* Creates a new social activity achievement with the primary key. Does not add the social activity achievement to the database.
	*
	* @param activityAchievementId the primary key for the new social activity achievement
	* @return the new social activity achievement
	*/
	public SocialActivityAchievement create(long activityAchievementId);

	/**
	* Removes the social activity achievement with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityAchievementId the primary key of the social activity achievement
	* @return the social activity achievement that was removed
	* @throws NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	*/
	public SocialActivityAchievement remove(long activityAchievementId)
		throws NoSuchActivityAchievementException;

	public SocialActivityAchievement updateImpl(
		SocialActivityAchievement socialActivityAchievement);

	/**
	* Returns the social activity achievement with the primary key or throws a {@link NoSuchActivityAchievementException} if it could not be found.
	*
	* @param activityAchievementId the primary key of the social activity achievement
	* @return the social activity achievement
	* @throws NoSuchActivityAchievementException if a social activity achievement with the primary key could not be found
	*/
	public SocialActivityAchievement findByPrimaryKey(
		long activityAchievementId) throws NoSuchActivityAchievementException;

	/**
	* Returns the social activity achievement with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityAchievementId the primary key of the social activity achievement
	* @return the social activity achievement, or <code>null</code> if a social activity achievement with the primary key could not be found
	*/
	public SocialActivityAchievement fetchByPrimaryKey(
		long activityAchievementId);

	@Override
	public java.util.Map<java.io.Serializable, SocialActivityAchievement> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the social activity achievements.
	*
	* @return the social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findAll();

	/**
	* Returns a range of all the social activity achievements.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findAll(int start, int end);

	/**
	* Returns an ordered range of all the social activity achievements.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator);

	/**
	* Returns an ordered range of all the social activity achievements.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social activity achievements
	*/
	public java.util.List<SocialActivityAchievement> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityAchievement> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the social activity achievements from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of social activity achievements.
	*
	* @return the number of social activity achievements
	*/
	public int countAll();
}