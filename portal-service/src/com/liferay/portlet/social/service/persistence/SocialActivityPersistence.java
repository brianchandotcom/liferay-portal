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

import com.liferay.portlet.social.model.SocialActivity;

/**
 * The persistence interface for the social activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityPersistenceImpl
 * @see SocialActivityUtil
 * @generated
 */
public interface SocialActivityPersistence extends BasePersistence<SocialActivity> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityUtil} to access the social activity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the social activity in the entity cache if it is enabled.
	*
	* @param socialActivity the social activity
	*/
	public void cacheResult(
		com.liferay.portlet.social.model.SocialActivity socialActivity);

	/**
	* Caches the social activities in the entity cache if it is enabled.
	*
	* @param socialActivities the social activities
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialActivity> socialActivities);

	/**
	* Creates a new social activity with the primary key. Does not add the social activity to the database.
	*
	* @param activityId the primary key for the new social activity
	* @return the new social activity
	*/
	public com.liferay.portlet.social.model.SocialActivity create(
		long activityId);

	/**
	* Removes the social activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity that was removed
	* @throws com.liferay.portlet.social.NoSuchActivityException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity remove(
		long activityId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	public com.liferay.portlet.social.model.SocialActivity updateImpl(
		com.liferay.portlet.social.model.SocialActivity socialActivity,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivityException} if it could not be found.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByPrimaryKey(
		long activityId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activity with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity, or <code>null</code> if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity fetchByPrimaryKey(
		long activityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activities where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activities where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activities where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activities before and after the current social activity in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityId the primary key of the current social activity
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity[] findByGroupId_PrevAndNext(
		long activityId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns all the social activities where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activities where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activities where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activities before and after the current social activity in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityId the primary key of the current social activity
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity[] findByCompanyId_PrevAndNext(
		long activityId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns all the social activities where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activities where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activities where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activities before and after the current social activity in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityId the primary key of the current social activity
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity[] findByUserId_PrevAndNext(
		long activityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activity where mirrorActivityId = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityException} if it could not be found.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByMirrorActivityId(
		long mirrorActivityId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activity where mirrorActivityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity fetchByMirrorActivityId(
		long mirrorActivityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity where mirrorActivityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity fetchByMirrorActivityId(
		long mirrorActivityId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activities where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activities where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activities before and after the current social activity in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityId the primary key of the current social activity
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity[] findByClassNameId_PrevAndNext(
		long activityId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns all the social activities where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @return the matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByReceiverUserId(
		long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activities where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByReceiverUserId(
		long receiverUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activities where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByReceiverUserId(
		long receiverUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity in the ordered set where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByReceiverUserId_First(
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByReceiverUserId_Last(
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activities before and after the current social activity in the ordered set where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityId the primary key of the current social activity
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity[] findByReceiverUserId_PrevAndNext(
		long activityId, long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activities before and after the current social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityId the primary key of the current social activity
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity[] findByC_C_PrevAndNext(
		long activityId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByM_C_C(
		long mirrorActivityId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByM_C_C(
		long mirrorActivityId, long classNameId, long classPK, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByM_C_C(
		long mirrorActivityId, long classNameId, long classPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByM_C_C_First(
		long mirrorActivityId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByM_C_C_Last(
		long mirrorActivityId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activities before and after the current social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityId the primary key of the current social activity
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity[] findByM_C_C_PrevAndNext(
		long activityId, long mirrorActivityId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByG_U_C_C_T_R(
		long groupId, long userId, long classNameId, long classPK, int type,
		long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByG_U_C_C_T_R(
		long groupId, long userId, long classNameId, long classPK, int type,
		long receiverUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findByG_U_C_C_T_R(
		long groupId, long userId, long classNameId, long classPK, int type,
		long receiverUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByG_U_C_C_T_R_First(
		long groupId, long userId, long classNameId, long classPK, int type,
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByG_U_C_C_T_R_Last(
		long groupId, long userId, long classNameId, long classPK, int type,
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activities before and after the current social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param activityId the primary key of the current social activity
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity[] findByG_U_C_C_T_R_PrevAndNext(
		long activityId, long groupId, long userId, long classNameId,
		long classPK, int type, long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activity where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or throws a {@link com.liferay.portlet.social.NoSuchActivityException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the matching social activity
	* @throws com.liferay.portlet.social.NoSuchActivityException if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity findByG_U_CD_C_C_T_R(
		long groupId, long userId, long createDate, long classNameId,
		long classPK, int type, long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Returns the social activity where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity fetchByG_U_CD_C_C_T_R(
		long groupId, long userId, long createDate, long classNameId,
		long classPK, int type, long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social activity where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity fetchByG_U_CD_C_C_T_R(
		long groupId, long userId, long createDate, long classNameId,
		long classPK, int type, long receiverUserId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social activities.
	*
	* @return the social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activities where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activities where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activities where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the social activity where mirrorActivityId = &#63; from the database.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the social activity that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity removeByMirrorActivityId(
		long mirrorActivityId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Removes all the social activities where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activities where receiverUserId = &#63; from the database.
	*
	* @param receiverUserId the receiver user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByReceiverUserId(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activities where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public void removeByM_C_C(long mirrorActivityId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_U_C_C_T_R(long groupId, long userId,
		long classNameId, long classPK, int type, long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the social activity where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the social activity that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity removeByG_U_CD_C_C_T_R(
		long groupId, long userId, long createDate, long classNameId,
		long classPK, int type, long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchActivityException;

	/**
	* Removes all the social activities from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities where mirrorActivityId = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the number of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByMirrorActivityId(long mirrorActivityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @return the number of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByReceiverUserId(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByM_C_C(long mirrorActivityId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the number of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_U_C_C_T_R(long groupId, long userId, long classNameId,
		long classPK, int type, long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the number of matching social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social activities.
	*
	* @return the number of social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}