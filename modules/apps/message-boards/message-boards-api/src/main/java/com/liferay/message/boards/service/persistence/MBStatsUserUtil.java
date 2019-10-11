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

package com.liferay.message.boards.service.persistence;

import com.liferay.message.boards.model.MBStatsUser;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the message boards stats user service. This utility wraps <code>com.liferay.message.boards.service.persistence.impl.MBStatsUserPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserPersistence
 * @generated
 */
public class MBStatsUserUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(MBStatsUser mbStatsUser) {
		getPersistence().clearCache(mbStatsUser);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, MBStatsUser> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MBStatsUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBStatsUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MBStatsUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MBStatsUser update(MBStatsUser mbStatsUser) {
		return getPersistence().update(mbStatsUser);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MBStatsUser update(
		MBStatsUser mbStatsUser, ServiceContext serviceContext) {

		return getPersistence().update(mbStatsUser, serviceContext);
	}

	/**
	 * Returns all the message boards stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards stats users
	 */
	public static List<MBStatsUser> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 */
	public static List<MBStatsUser> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 */
	public static List<MBStatsUser> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards stats users
	 */
	public static List<MBStatsUser> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public static MBStatsUser findByGroupId_First(
			long groupId, OrderByComparator<MBStatsUser> orderByComparator)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public static MBStatsUser fetchByGroupId_First(
		long groupId, OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public static MBStatsUser findByGroupId_Last(
			long groupId, OrderByComparator<MBStatsUser> orderByComparator)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public static MBStatsUser fetchByGroupId_Last(
		long groupId, OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	public static MBStatsUser[] findByGroupId_PrevAndNext(
			long statsUserId, long groupId,
			OrderByComparator<MBStatsUser> orderByComparator)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByGroupId_PrevAndNext(
			statsUserId, groupId, orderByComparator);
	}

	/**
	 * Removes all the message boards stats users where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of message boards stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards stats users
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the message boards stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching message boards stats users
	 */
	public static List<MBStatsUser> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 */
	public static List<MBStatsUser> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 */
	public static List<MBStatsUser> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards stats users
	 */
	public static List<MBStatsUser> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public static MBStatsUser findByUserId_First(
			long userId, OrderByComparator<MBStatsUser> orderByComparator)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public static MBStatsUser fetchByUserId_First(
		long userId, OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public static MBStatsUser findByUserId_Last(
			long userId, OrderByComparator<MBStatsUser> orderByComparator)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public static MBStatsUser fetchByUserId_Last(
		long userId, OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	public static MBStatsUser[] findByUserId_PrevAndNext(
			long statsUserId, long userId,
			OrderByComparator<MBStatsUser> orderByComparator)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByUserId_PrevAndNext(
			statsUserId, userId, orderByComparator);
	}

	/**
	 * Removes all the message boards stats users where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of message boards stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching message boards stats users
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or throws a <code>NoSuchStatsUserException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public static MBStatsUser findByG_U(long groupId, long userId)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public static MBStatsUser fetchByG_U(long groupId, long userId) {
		return getPersistence().fetchByG_U(groupId, userId);
	}

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public static MBStatsUser fetchByG_U(
		long groupId, long userId, boolean useFinderCache) {

		return getPersistence().fetchByG_U(groupId, userId, useFinderCache);
	}

	/**
	 * Removes the message boards stats user where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the message boards stats user that was removed
	 */
	public static MBStatsUser removeByG_U(long groupId, long userId)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().removeByG_U(groupId, userId);
	}

	/**
	 * Returns the number of message boards stats users where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching message boards stats users
	 */
	public static int countByG_U(long groupId, long userId) {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	 * Returns all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @return the matching message boards stats users
	 */
	public static List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount) {

		return getPersistence().findByG_NotU_NotM(
			groupId, userId, messageCount);
	}

	/**
	 * Returns a range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 */
	public static List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end) {

		return getPersistence().findByG_NotU_NotM(
			groupId, userId, messageCount, start, end);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 */
	public static List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().findByG_NotU_NotM(
			groupId, userId, messageCount, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards stats users
	 */
	public static List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_NotU_NotM(
			groupId, userId, messageCount, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public static MBStatsUser findByG_NotU_NotM_First(
			long groupId, long userId, int messageCount,
			OrderByComparator<MBStatsUser> orderByComparator)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByG_NotU_NotM_First(
			groupId, userId, messageCount, orderByComparator);
	}

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public static MBStatsUser fetchByG_NotU_NotM_First(
		long groupId, long userId, int messageCount,
		OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().fetchByG_NotU_NotM_First(
			groupId, userId, messageCount, orderByComparator);
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	public static MBStatsUser findByG_NotU_NotM_Last(
			long groupId, long userId, int messageCount,
			OrderByComparator<MBStatsUser> orderByComparator)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByG_NotU_NotM_Last(
			groupId, userId, messageCount, orderByComparator);
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	public static MBStatsUser fetchByG_NotU_NotM_Last(
		long groupId, long userId, int messageCount,
		OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().fetchByG_NotU_NotM_Last(
			groupId, userId, messageCount, orderByComparator);
	}

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	public static MBStatsUser[] findByG_NotU_NotM_PrevAndNext(
			long statsUserId, long groupId, long userId, int messageCount,
			OrderByComparator<MBStatsUser> orderByComparator)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByG_NotU_NotM_PrevAndNext(
			statsUserId, groupId, userId, messageCount, orderByComparator);
	}

	/**
	 * Removes all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 */
	public static void removeByG_NotU_NotM(
		long groupId, long userId, int messageCount) {

		getPersistence().removeByG_NotU_NotM(groupId, userId, messageCount);
	}

	/**
	 * Returns the number of message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @return the number of matching message boards stats users
	 */
	public static int countByG_NotU_NotM(
		long groupId, long userId, int messageCount) {

		return getPersistence().countByG_NotU_NotM(
			groupId, userId, messageCount);
	}

	/**
	 * Caches the message boards stats user in the entity cache if it is enabled.
	 *
	 * @param mbStatsUser the message boards stats user
	 */
	public static void cacheResult(MBStatsUser mbStatsUser) {
		getPersistence().cacheResult(mbStatsUser);
	}

	/**
	 * Caches the message boards stats users in the entity cache if it is enabled.
	 *
	 * @param mbStatsUsers the message boards stats users
	 */
	public static void cacheResult(List<MBStatsUser> mbStatsUsers) {
		getPersistence().cacheResult(mbStatsUsers);
	}

	/**
	 * Creates a new message boards stats user with the primary key. Does not add the message boards stats user to the database.
	 *
	 * @param statsUserId the primary key for the new message boards stats user
	 * @return the new message boards stats user
	 */
	public static MBStatsUser create(long statsUserId) {
		return getPersistence().create(statsUserId);
	}

	/**
	 * Removes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user that was removed
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	public static MBStatsUser remove(long statsUserId)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().remove(statsUserId);
	}

	public static MBStatsUser updateImpl(MBStatsUser mbStatsUser) {
		return getPersistence().updateImpl(mbStatsUser);
	}

	/**
	 * Returns the message boards stats user with the primary key or throws a <code>NoSuchStatsUserException</code> if it could not be found.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	public static MBStatsUser findByPrimaryKey(long statsUserId)
		throws com.liferay.message.boards.exception.NoSuchStatsUserException {

		return getPersistence().findByPrimaryKey(statsUserId);
	}

	/**
	 * Returns the message boards stats user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user, or <code>null</code> if a message boards stats user with the primary key could not be found
	 */
	public static MBStatsUser fetchByPrimaryKey(long statsUserId) {
		return getPersistence().fetchByPrimaryKey(statsUserId);
	}

	/**
	 * Returns all the message boards stats users.
	 *
	 * @return the message boards stats users
	 */
	public static List<MBStatsUser> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of message boards stats users
	 */
	public static List<MBStatsUser> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards stats users
	 */
	public static List<MBStatsUser> findAll(
		int start, int end, OrderByComparator<MBStatsUser> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message boards stats users
	 */
	public static List<MBStatsUser> findAll(
		int start, int end, OrderByComparator<MBStatsUser> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the message boards stats users from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of message boards stats users.
	 *
	 * @return the number of message boards stats users
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MBStatsUserPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<MBStatsUserPersistence, MBStatsUserPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MBStatsUserPersistence.class);

		ServiceTracker<MBStatsUserPersistence, MBStatsUserPersistence>
			serviceTracker =
				new ServiceTracker
					<MBStatsUserPersistence, MBStatsUserPersistence>(
						bundle.getBundleContext(), MBStatsUserPersistence.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}