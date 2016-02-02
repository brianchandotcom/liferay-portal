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

import com.liferay.portlet.social.exception.NoSuchActivityException;
import com.liferay.portlet.social.model.SocialActivity;

/**
 * The persistence interface for the social activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.persistence.impl.SocialActivityPersistenceImpl
 * @see SocialActivityUtil
 * @generated
 */
@ProviderType
public interface SocialActivityPersistence extends BasePersistence<SocialActivity> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityUtil} to access the social activity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the social activities where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activities
	*/
	public java.util.List<SocialActivity> findByGroupId(long groupId);

	/**
	* Returns a range of all the social activities where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public java.util.List<SocialActivity> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the social activities where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the first social activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the last social activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the social activities before and after the current social activity in the ordered set where groupId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity[] findByGroupId_PrevAndNext(long activityId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Removes all the social activities where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of social activities where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activities
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the social activities where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching social activities
	*/
	public java.util.List<SocialActivity> findByCompanyId(long companyId);

	/**
	* Returns a range of all the social activities where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public java.util.List<SocialActivity> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the social activities where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the first social activity in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the last social activity in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the social activities before and after the current social activity in the ordered set where companyId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity[] findByCompanyId_PrevAndNext(long activityId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Removes all the social activities where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of social activities where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching social activities
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the social activities where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching social activities
	*/
	public java.util.List<SocialActivity> findByUserId(long userId);

	/**
	* Returns a range of all the social activities where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public java.util.List<SocialActivity> findByUserId(long userId, int start,
		int end);

	/**
	* Returns an ordered range of all the social activities where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the first social activity in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the last social activity in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the social activities before and after the current social activity in the ordered set where userId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity[] findByUserId_PrevAndNext(long activityId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Removes all the social activities where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of social activities where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching social activities
	*/
	public int countByUserId(long userId);

	/**
	* Returns all the social activities where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @return the matching social activities
	*/
	public java.util.List<SocialActivity> findByActivitySetId(
		long activitySetId);

	/**
	* Returns a range of all the social activities where activitySetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param activitySetId the activity set ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public java.util.List<SocialActivity> findByActivitySetId(
		long activitySetId, int start, int end);

	/**
	* Returns an ordered range of all the social activities where activitySetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param activitySetId the activity set ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByActivitySetId(
		long activitySetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities where activitySetId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param activitySetId the activity set ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByActivitySetId(
		long activitySetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity in the ordered set where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByActivitySetId_First(long activitySetId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the first social activity in the ordered set where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByActivitySetId_First(long activitySetId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the last social activity in the ordered set where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByActivitySetId_Last(long activitySetId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByActivitySetId_Last(long activitySetId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the social activities before and after the current social activity in the ordered set where activitySetId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param activitySetId the activity set ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity[] findByActivitySetId_PrevAndNext(long activityId,
		long activitySetId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Removes all the social activities where activitySetId = &#63; from the database.
	*
	* @param activitySetId the activity set ID
	*/
	public void removeByActivitySetId(long activitySetId);

	/**
	* Returns the number of social activities where activitySetId = &#63;.
	*
	* @param activitySetId the activity set ID
	* @return the number of matching social activities
	*/
	public int countByActivitySetId(long activitySetId);

	/**
	* Returns the social activity where mirrorActivityId = &#63; or throws a {@link NoSuchActivityException} if it could not be found.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByMirrorActivityId(long mirrorActivityId)
		throws NoSuchActivityException;

	/**
	* Returns the social activity where mirrorActivityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByMirrorActivityId(long mirrorActivityId);

	/**
	* Returns the social activity where mirrorActivityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByMirrorActivityId(long mirrorActivityId,
		boolean retrieveFromCache);

	/**
	* Removes the social activity where mirrorActivityId = &#63; from the database.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the social activity that was removed
	*/
	public SocialActivity removeByMirrorActivityId(long mirrorActivityId)
		throws NoSuchActivityException;

	/**
	* Returns the number of social activities where mirrorActivityId = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @return the number of matching social activities
	*/
	public int countByMirrorActivityId(long mirrorActivityId);

	/**
	* Returns all the social activities where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching social activities
	*/
	public java.util.List<SocialActivity> findByClassNameId(long classNameId);

	/**
	* Returns a range of all the social activities where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public java.util.List<SocialActivity> findByClassNameId(long classNameId,
		int start, int end);

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByClassNameId(long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByClassNameId(long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByClassNameId_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByClassNameId_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByClassNameId_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByClassNameId_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the social activities before and after the current social activity in the ordered set where classNameId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity[] findByClassNameId_PrevAndNext(long activityId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Removes all the social activities where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public void removeByClassNameId(long classNameId);

	/**
	* Returns the number of social activities where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching social activities
	*/
	public int countByClassNameId(long classNameId);

	/**
	* Returns all the social activities where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @return the matching social activities
	*/
	public java.util.List<SocialActivity> findByReceiverUserId(
		long receiverUserId);

	/**
	* Returns a range of all the social activities where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public java.util.List<SocialActivity> findByReceiverUserId(
		long receiverUserId, int start, int end);

	/**
	* Returns an ordered range of all the social activities where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByReceiverUserId(
		long receiverUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities where receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param receiverUserId the receiver user ID
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByReceiverUserId(
		long receiverUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByReceiverUserId_First(long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the first social activity in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByReceiverUserId_First(long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the last social activity in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByReceiverUserId_Last(long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByReceiverUserId_Last(long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the social activities before and after the current social activity in the ordered set where receiverUserId = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity[] findByReceiverUserId_PrevAndNext(long activityId,
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Removes all the social activities where receiverUserId = &#63; from the database.
	*
	* @param receiverUserId the receiver user ID
	*/
	public void removeByReceiverUserId(long receiverUserId);

	/**
	* Returns the number of social activities where receiverUserId = &#63;.
	*
	* @param receiverUserId the receiver user ID
	* @return the number of matching social activities
	*/
	public int countByReceiverUserId(long receiverUserId);

	/**
	* Returns all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activities
	*/
	public java.util.List<SocialActivity> findByC_C(long classNameId,
		long classPK);

	/**
	* Returns a range of all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public java.util.List<SocialActivity> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the social activities before and after the current social activity in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity[] findByC_C_PrevAndNext(long activityId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Removes all the social activities where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of social activities where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activities
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activities
	*/
	public java.util.List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK);

	/**
	* Returns a range of all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public java.util.List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByM_C_C_First(long mirrorActivityId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the first social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByM_C_C_First(long mirrorActivityId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the last social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByM_C_C_Last(long mirrorActivityId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByM_C_C_Last(long mirrorActivityId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the social activities before and after the current social activity in the ordered set where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity[] findByM_C_C_PrevAndNext(long activityId,
		long mirrorActivityId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Removes all the social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByM_C_C(long mirrorActivityId, long classNameId,
		long classPK);

	/**
	* Returns the number of social activities where mirrorActivityId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param mirrorActivityId the mirror activity ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activities
	*/
	public int countByM_C_C(long mirrorActivityId, long classNameId,
		long classPK);

	/**
	* Returns all the social activities where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching social activities
	*/
	public java.util.List<SocialActivity> findByC_C_T(long classNameId,
		long classPK, int type);

	/**
	* Returns a range of all the social activities where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of matching social activities
	*/
	public java.util.List<SocialActivity> findByC_C_T(long classNameId,
		long classPK, int type, int start, int end);

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByC_C_T(long classNameId,
		long classPK, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByC_C_T(long classNameId,
		long classPK, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByC_C_T_First(long classNameId, long classPK,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the first social activity in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByC_C_T_First(long classNameId, long classPK,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByC_C_T_Last(long classNameId, long classPK,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByC_C_T_Last(long classNameId, long classPK,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the social activities before and after the current social activity in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param activityId the primary key of the current social activity
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity[] findByC_C_T_PrevAndNext(long activityId,
		long classNameId, long classPK, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Removes all the social activities where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	*/
	public void removeByC_C_T(long classNameId, long classPK, int type);

	/**
	* Returns the number of social activities where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching social activities
	*/
	public int countByC_C_T(long classNameId, long classPK, int type);

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
	*/
	public java.util.List<SocialActivity> findByG_U_C_C_T_R(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId);

	/**
	* Returns a range of all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<SocialActivity> findByG_U_C_C_T_R(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, int start, int end);

	/**
	* Returns an ordered range of all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<SocialActivity> findByG_U_C_C_T_R(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activities
	*/
	public java.util.List<SocialActivity> findByG_U_C_C_T_R(long groupId,
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByG_U_C_C_T_R_First(long groupId, long userId,
		long classNameId, long classPK, int type, long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the first social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByG_U_C_C_T_R_First(long groupId, long userId,
		long classNameId, long classPK, int type, long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the last social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByG_U_C_C_T_R_Last(long groupId, long userId,
		long classNameId, long classPK, int type, long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Returns the last social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByG_U_C_C_T_R_Last(long groupId, long userId,
		long classNameId, long classPK, int type, long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns the social activities before and after the current social activity in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63;.
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
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity[] findByG_U_C_C_T_R_PrevAndNext(long activityId,
		long groupId, long userId, long classNameId, long classPK, int type,
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator)
		throws NoSuchActivityException;

	/**
	* Removes all the social activities where groupId = &#63; and userId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	*/
	public void removeByG_U_C_C_T_R(long groupId, long userId,
		long classNameId, long classPK, int type, long receiverUserId);

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
	*/
	public int countByG_U_C_C_T_R(long groupId, long userId, long classNameId,
		long classPK, int type, long receiverUserId);

	/**
	* Returns the social activity where groupId = &#63; and userId = &#63; and createDate = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and receiverUserId = &#63; or throws a {@link NoSuchActivityException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param createDate the create date
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param receiverUserId the receiver user ID
	* @return the matching social activity
	* @throws NoSuchActivityException if a matching social activity could not be found
	*/
	public SocialActivity findByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId) throws NoSuchActivityException;

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
	*/
	public SocialActivity fetchByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId);

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity, or <code>null</code> if a matching social activity could not be found
	*/
	public SocialActivity fetchByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId, boolean retrieveFromCache);

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
	*/
	public SocialActivity removeByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId) throws NoSuchActivityException;

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
	*/
	public int countByG_U_CD_C_C_T_R(long groupId, long userId,
		long createDate, long classNameId, long classPK, int type,
		long receiverUserId);

	/**
	* Caches the social activity in the entity cache if it is enabled.
	*
	* @param socialActivity the social activity
	*/
	public void cacheResult(SocialActivity socialActivity);

	/**
	* Caches the social activities in the entity cache if it is enabled.
	*
	* @param socialActivities the social activities
	*/
	public void cacheResult(java.util.List<SocialActivity> socialActivities);

	/**
	* Creates a new social activity with the primary key. Does not add the social activity to the database.
	*
	* @param activityId the primary key for the new social activity
	* @return the new social activity
	*/
	public SocialActivity create(long activityId);

	/**
	* Removes the social activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity that was removed
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity remove(long activityId)
		throws NoSuchActivityException;

	public SocialActivity updateImpl(SocialActivity socialActivity);

	/**
	* Returns the social activity with the primary key or throws a {@link NoSuchActivityException} if it could not be found.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity
	* @throws NoSuchActivityException if a social activity with the primary key could not be found
	*/
	public SocialActivity findByPrimaryKey(long activityId)
		throws NoSuchActivityException;

	/**
	* Returns the social activity with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity, or <code>null</code> if a social activity with the primary key could not be found
	*/
	public SocialActivity fetchByPrimaryKey(long activityId);

	@Override
	public java.util.Map<java.io.Serializable, SocialActivity> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the social activities.
	*
	* @return the social activities
	*/
	public java.util.List<SocialActivity> findAll();

	/**
	* Returns a range of all the social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of social activities
	*/
	public java.util.List<SocialActivity> findAll(int start, int end);

	/**
	* Returns an ordered range of all the social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activities
	*/
	public java.util.List<SocialActivity> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator);

	/**
	* Returns an ordered range of all the social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social activities
	*/
	public java.util.List<SocialActivity> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivity> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the social activities from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of social activities.
	*
	* @return the number of social activities
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}