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

package com.liferay.portlet.blogs.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.blogs.exception.NoSuchStatsUserException;
import com.liferay.portlet.blogs.model.BlogsStatsUser;

import java.util.Date;

/**
 * The persistence interface for the blogs stats user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.blogs.service.persistence.impl.BlogsStatsUserPersistenceImpl
 * @see BlogsStatsUserUtil
 * @generated
 */
@ProviderType
public interface BlogsStatsUserPersistence extends BasePersistence<BlogsStatsUser> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BlogsStatsUserUtil} to access the blogs stats user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the blogs stats users where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByGroupId(long groupId);

	/**
	* Returns a range of all the blogs stats users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @return the range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the blogs stats users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns an ordered range of all the blogs stats users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs stats user in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Returns the first blogs stats user in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns the last blogs stats user in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Returns the last blogs stats user in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns the blogs stats users before and after the current blogs stats user in the ordered set where groupId = &#63;.
	*
	* @param statsUserId the primary key of the current blogs stats user
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs stats user
	* @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	*/
	public BlogsStatsUser[] findByGroupId_PrevAndNext(long statsUserId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Removes all the blogs stats users where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of blogs stats users where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching blogs stats users
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the blogs stats users where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByUserId(long userId);

	/**
	* Returns a range of all the blogs stats users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @return the range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByUserId(long userId, int start,
		int end);

	/**
	* Returns an ordered range of all the blogs stats users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns an ordered range of all the blogs stats users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs stats user in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Returns the first blogs stats user in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns the last blogs stats user in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Returns the last blogs stats user in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns the blogs stats users before and after the current blogs stats user in the ordered set where userId = &#63;.
	*
	* @param statsUserId the primary key of the current blogs stats user
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs stats user
	* @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	*/
	public BlogsStatsUser[] findByUserId_PrevAndNext(long statsUserId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Removes all the blogs stats users where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of blogs stats users where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching blogs stats users
	*/
	public int countByUserId(long userId);

	/**
	* Returns the blogs stats user where groupId = &#63; and userId = &#63; or throws a {@link NoSuchStatsUserException} if it could not be found.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException;

	/**
	* Returns the blogs stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByG_U(long groupId, long userId);

	/**
	* Returns the blogs stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache);

	/**
	* Removes the blogs stats user where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the blogs stats user that was removed
	*/
	public BlogsStatsUser removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException;

	/**
	* Returns the number of blogs stats users where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching blogs stats users
	*/
	public int countByG_U(long groupId, long userId);

	/**
	* Returns all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	*
	* @param groupId the group ID
	* @param entryCount the entry count
	* @return the matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByG_NotE(long groupId,
		int entryCount);

	/**
	* Returns a range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param entryCount the entry count
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @return the range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByG_NotE(long groupId,
		int entryCount, int start, int end);

	/**
	* Returns an ordered range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param entryCount the entry count
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByG_NotE(long groupId,
		int entryCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns an ordered range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param entryCount the entry count
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByG_NotE(long groupId,
		int entryCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	*
	* @param groupId the group ID
	* @param entryCount the entry count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByG_NotE_First(long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Returns the first blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	*
	* @param groupId the group ID
	* @param entryCount the entry count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByG_NotE_First(long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns the last blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	*
	* @param groupId the group ID
	* @param entryCount the entry count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByG_NotE_Last(long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Returns the last blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	*
	* @param groupId the group ID
	* @param entryCount the entry count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByG_NotE_Last(long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns the blogs stats users before and after the current blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	*
	* @param statsUserId the primary key of the current blogs stats user
	* @param groupId the group ID
	* @param entryCount the entry count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs stats user
	* @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	*/
	public BlogsStatsUser[] findByG_NotE_PrevAndNext(long statsUserId,
		long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Removes all the blogs stats users where groupId = &#63; and entryCount &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param entryCount the entry count
	*/
	public void removeByG_NotE(long groupId, int entryCount);

	/**
	* Returns the number of blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	*
	* @param groupId the group ID
	* @param entryCount the entry count
	* @return the number of matching blogs stats users
	*/
	public int countByG_NotE(long groupId, int entryCount);

	/**
	* Returns all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	*
	* @param companyId the company ID
	* @param entryCount the entry count
	* @return the matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByC_NotE(long companyId,
		int entryCount);

	/**
	* Returns a range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param entryCount the entry count
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @return the range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByC_NotE(long companyId,
		int entryCount, int start, int end);

	/**
	* Returns an ordered range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param entryCount the entry count
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByC_NotE(long companyId,
		int entryCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns an ordered range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param entryCount the entry count
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByC_NotE(long companyId,
		int entryCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	*
	* @param companyId the company ID
	* @param entryCount the entry count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByC_NotE_First(long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Returns the first blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	*
	* @param companyId the company ID
	* @param entryCount the entry count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByC_NotE_First(long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns the last blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	*
	* @param companyId the company ID
	* @param entryCount the entry count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByC_NotE_Last(long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Returns the last blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	*
	* @param companyId the company ID
	* @param entryCount the entry count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByC_NotE_Last(long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns the blogs stats users before and after the current blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	*
	* @param statsUserId the primary key of the current blogs stats user
	* @param companyId the company ID
	* @param entryCount the entry count
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs stats user
	* @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	*/
	public BlogsStatsUser[] findByC_NotE_PrevAndNext(long statsUserId,
		long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Removes all the blogs stats users where companyId = &#63; and entryCount &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param entryCount the entry count
	*/
	public void removeByC_NotE(long companyId, int entryCount);

	/**
	* Returns the number of blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	*
	* @param companyId the company ID
	* @param entryCount the entry count
	* @return the number of matching blogs stats users
	*/
	public int countByC_NotE(long companyId, int entryCount);

	/**
	* Returns all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	*
	* @param userId the user ID
	* @param lastPostDate the last post date
	* @return the matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByU_L(long userId,
		Date lastPostDate);

	/**
	* Returns a range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param lastPostDate the last post date
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @return the range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByU_L(long userId,
		Date lastPostDate, int start, int end);

	/**
	* Returns an ordered range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param lastPostDate the last post date
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByU_L(long userId,
		Date lastPostDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns an ordered range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param lastPostDate the last post date
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findByU_L(long userId,
		Date lastPostDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	*
	* @param userId the user ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByU_L_First(long userId, Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Returns the first blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	*
	* @param userId the user ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByU_L_First(long userId, Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns the last blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	*
	* @param userId the user ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs stats user
	* @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser findByU_L_Last(long userId, Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Returns the last blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	*
	* @param userId the user ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	*/
	public BlogsStatsUser fetchByU_L_Last(long userId, Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns the blogs stats users before and after the current blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	*
	* @param statsUserId the primary key of the current blogs stats user
	* @param userId the user ID
	* @param lastPostDate the last post date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next blogs stats user
	* @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	*/
	public BlogsStatsUser[] findByU_L_PrevAndNext(long statsUserId,
		long userId, Date lastPostDate,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException;

	/**
	* Removes all the blogs stats users where userId = &#63; and lastPostDate = &#63; from the database.
	*
	* @param userId the user ID
	* @param lastPostDate the last post date
	*/
	public void removeByU_L(long userId, Date lastPostDate);

	/**
	* Returns the number of blogs stats users where userId = &#63; and lastPostDate = &#63;.
	*
	* @param userId the user ID
	* @param lastPostDate the last post date
	* @return the number of matching blogs stats users
	*/
	public int countByU_L(long userId, Date lastPostDate);

	/**
	* Caches the blogs stats user in the entity cache if it is enabled.
	*
	* @param blogsStatsUser the blogs stats user
	*/
	public void cacheResult(BlogsStatsUser blogsStatsUser);

	/**
	* Caches the blogs stats users in the entity cache if it is enabled.
	*
	* @param blogsStatsUsers the blogs stats users
	*/
	public void cacheResult(java.util.List<BlogsStatsUser> blogsStatsUsers);

	/**
	* Creates a new blogs stats user with the primary key. Does not add the blogs stats user to the database.
	*
	* @param statsUserId the primary key for the new blogs stats user
	* @return the new blogs stats user
	*/
	public BlogsStatsUser create(long statsUserId);

	/**
	* Removes the blogs stats user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsUserId the primary key of the blogs stats user
	* @return the blogs stats user that was removed
	* @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	*/
	public BlogsStatsUser remove(long statsUserId)
		throws NoSuchStatsUserException;

	public BlogsStatsUser updateImpl(BlogsStatsUser blogsStatsUser);

	/**
	* Returns the blogs stats user with the primary key or throws a {@link NoSuchStatsUserException} if it could not be found.
	*
	* @param statsUserId the primary key of the blogs stats user
	* @return the blogs stats user
	* @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	*/
	public BlogsStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException;

	/**
	* Returns the blogs stats user with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param statsUserId the primary key of the blogs stats user
	* @return the blogs stats user, or <code>null</code> if a blogs stats user with the primary key could not be found
	*/
	public BlogsStatsUser fetchByPrimaryKey(long statsUserId);

	@Override
	public java.util.Map<java.io.Serializable, BlogsStatsUser> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the blogs stats users.
	*
	* @return the blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findAll();

	/**
	* Returns a range of all the blogs stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @return the range of blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findAll(int start, int end);

	/**
	* Returns an ordered range of all the blogs stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator);

	/**
	* Returns an ordered range of all the blogs stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of blogs stats users
	* @param end the upper bound of the range of blogs stats users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of blogs stats users
	*/
	public java.util.List<BlogsStatsUser> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the blogs stats users from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of blogs stats users.
	*
	* @return the number of blogs stats users
	*/
	public int countAll();
}