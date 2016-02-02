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

import com.liferay.portlet.social.exception.NoSuchActivityLimitException;
import com.liferay.portlet.social.model.SocialActivityLimit;

/**
 * The persistence interface for the social activity limit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.persistence.impl.SocialActivityLimitPersistenceImpl
 * @see SocialActivityLimitUtil
 * @generated
 */
@ProviderType
public interface SocialActivityLimitPersistence extends BasePersistence<SocialActivityLimit> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityLimitUtil} to access the social activity limit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the social activity limits where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByGroupId(long groupId);

	/**
	* Returns a range of all the social activity limits where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @return the range of matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the social activity limits where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator);

	/**
	* Returns an ordered range of all the social activity limits where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity limit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public SocialActivityLimit findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException;

	/**
	* Returns the first social activity limit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public SocialActivityLimit fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator);

	/**
	* Returns the last social activity limit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public SocialActivityLimit findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException;

	/**
	* Returns the last social activity limit in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public SocialActivityLimit fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator);

	/**
	* Returns the social activity limits before and after the current social activity limit in the ordered set where groupId = &#63;.
	*
	* @param activityLimitId the primary key of the current social activity limit
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity limit
	* @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	*/
	public SocialActivityLimit[] findByGroupId_PrevAndNext(
		long activityLimitId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException;

	/**
	* Removes all the social activity limits where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of social activity limits where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activity limits
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the social activity limits where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByUserId(long userId);

	/**
	* Returns a range of all the social activity limits where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @return the range of matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByUserId(long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the social activity limits where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator);

	/**
	* Returns an ordered range of all the social activity limits where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity limit in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public SocialActivityLimit findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException;

	/**
	* Returns the first social activity limit in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public SocialActivityLimit fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator);

	/**
	* Returns the last social activity limit in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public SocialActivityLimit findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException;

	/**
	* Returns the last social activity limit in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public SocialActivityLimit fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator);

	/**
	* Returns the social activity limits before and after the current social activity limit in the ordered set where userId = &#63;.
	*
	* @param activityLimitId the primary key of the current social activity limit
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity limit
	* @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	*/
	public SocialActivityLimit[] findByUserId_PrevAndNext(
		long activityLimitId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException;

	/**
	* Removes all the social activity limits where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of social activity limits where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching social activity limits
	*/
	public int countByUserId(long userId);

	/**
	* Returns all the social activity limits where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByC_C(long classNameId,
		long classPK);

	/**
	* Returns a range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @return the range of matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator);

	/**
	* Returns an ordered range of all the social activity limits where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity limits
	*/
	public java.util.List<SocialActivityLimit> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public SocialActivityLimit findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException;

	/**
	* Returns the first social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public SocialActivityLimit fetchByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator);

	/**
	* Returns the last social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public SocialActivityLimit findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException;

	/**
	* Returns the last social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public SocialActivityLimit fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator);

	/**
	* Returns the social activity limits before and after the current social activity limit in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param activityLimitId the primary key of the current social activity limit
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity limit
	* @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	*/
	public SocialActivityLimit[] findByC_C_PrevAndNext(long activityLimitId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator)
		throws NoSuchActivityLimitException;

	/**
	* Removes all the social activity limits where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of social activity limits where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activity limits
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or throws a {@link NoSuchActivityLimitException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param activityType the activity type
	* @param activityCounterName the activity counter name
	* @return the matching social activity limit
	* @throws NoSuchActivityLimitException if a matching social activity limit could not be found
	*/
	public SocialActivityLimit findByG_U_C_C_A_A(long groupId, long userId,
		long classNameId, long classPK, int activityType,
		java.lang.String activityCounterName)
		throws NoSuchActivityLimitException;

	/**
	* Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param activityType the activity type
	* @param activityCounterName the activity counter name
	* @return the matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public SocialActivityLimit fetchByG_U_C_C_A_A(long groupId, long userId,
		long classNameId, long classPK, int activityType,
		java.lang.String activityCounterName);

	/**
	* Returns the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param activityType the activity type
	* @param activityCounterName the activity counter name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity limit, or <code>null</code> if a matching social activity limit could not be found
	*/
	public SocialActivityLimit fetchByG_U_C_C_A_A(long groupId, long userId,
		long classNameId, long classPK, int activityType,
		java.lang.String activityCounterName, boolean retrieveFromCache);

	/**
	* Removes the social activity limit where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param activityType the activity type
	* @param activityCounterName the activity counter name
	* @return the social activity limit that was removed
	*/
	public SocialActivityLimit removeByG_U_C_C_A_A(long groupId, long userId,
		long classNameId, long classPK, int activityType,
		java.lang.String activityCounterName)
		throws NoSuchActivityLimitException;

	/**
	* Returns the number of social activity limits where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and activityType = &#63; and activityCounterName = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param activityType the activity type
	* @param activityCounterName the activity counter name
	* @return the number of matching social activity limits
	*/
	public int countByG_U_C_C_A_A(long groupId, long userId, long classNameId,
		long classPK, int activityType, java.lang.String activityCounterName);

	/**
	* Caches the social activity limit in the entity cache if it is enabled.
	*
	* @param socialActivityLimit the social activity limit
	*/
	public void cacheResult(SocialActivityLimit socialActivityLimit);

	/**
	* Caches the social activity limits in the entity cache if it is enabled.
	*
	* @param socialActivityLimits the social activity limits
	*/
	public void cacheResult(
		java.util.List<SocialActivityLimit> socialActivityLimits);

	/**
	* Creates a new social activity limit with the primary key. Does not add the social activity limit to the database.
	*
	* @param activityLimitId the primary key for the new social activity limit
	* @return the new social activity limit
	*/
	public SocialActivityLimit create(long activityLimitId);

	/**
	* Removes the social activity limit with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityLimitId the primary key of the social activity limit
	* @return the social activity limit that was removed
	* @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	*/
	public SocialActivityLimit remove(long activityLimitId)
		throws NoSuchActivityLimitException;

	public SocialActivityLimit updateImpl(
		SocialActivityLimit socialActivityLimit);

	/**
	* Returns the social activity limit with the primary key or throws a {@link NoSuchActivityLimitException} if it could not be found.
	*
	* @param activityLimitId the primary key of the social activity limit
	* @return the social activity limit
	* @throws NoSuchActivityLimitException if a social activity limit with the primary key could not be found
	*/
	public SocialActivityLimit findByPrimaryKey(long activityLimitId)
		throws NoSuchActivityLimitException;

	/**
	* Returns the social activity limit with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityLimitId the primary key of the social activity limit
	* @return the social activity limit, or <code>null</code> if a social activity limit with the primary key could not be found
	*/
	public SocialActivityLimit fetchByPrimaryKey(long activityLimitId);

	@Override
	public java.util.Map<java.io.Serializable, SocialActivityLimit> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the social activity limits.
	*
	* @return the social activity limits
	*/
	public java.util.List<SocialActivityLimit> findAll();

	/**
	* Returns a range of all the social activity limits.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @return the range of social activity limits
	*/
	public java.util.List<SocialActivityLimit> findAll(int start, int end);

	/**
	* Returns an ordered range of all the social activity limits.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity limits
	*/
	public java.util.List<SocialActivityLimit> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator);

	/**
	* Returns an ordered range of all the social activity limits.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityLimitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity limits
	* @param end the upper bound of the range of social activity limits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social activity limits
	*/
	public java.util.List<SocialActivityLimit> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityLimit> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the social activity limits from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of social activity limits.
	*
	* @return the number of social activity limits
	*/
	public int countAll();
}