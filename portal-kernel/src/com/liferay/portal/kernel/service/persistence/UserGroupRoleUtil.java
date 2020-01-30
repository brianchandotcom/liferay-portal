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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the user group role service. This utility wraps <code>com.liferay.portal.service.persistence.impl.UserGroupRolePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupRolePersistence
 * @generated
 */
public class UserGroupRoleUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(UserGroupRole userGroupRole) {
		getPersistence().clearCache(userGroupRole);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, UserGroupRole> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<UserGroupRole> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UserGroupRole> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UserGroupRole> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static UserGroupRole update(UserGroupRole userGroupRole) {
		return getPersistence().update(userGroupRole);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static UserGroupRole update(
		UserGroupRole userGroupRole, ServiceContext serviceContext) {

		return getPersistence().update(userGroupRole, serviceContext);
	}

	/**
	 * Returns all the user group roles where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user group roles
	 */
	public static List<UserGroupRole> findByUserId(long userId) {
		return getPersistence().findByUserId(userId);
	}

	/**
	 * Returns a range of all the user group roles where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of matching user group roles
	 */
	public static List<UserGroupRole> findByUserId(
		long userId, int start, int end) {

		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	 * Returns an ordered range of all the user group roles where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group roles
	 */
	public static List<UserGroupRole> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the user group roles where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group roles
	 */
	public static List<UserGroupRole> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUserId(
			userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first user group role in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByUserId_First(
			long userId, OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the first user group role in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByUserId_First(
		long userId, OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	 * Returns the last user group role in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByUserId_Last(
			long userId, OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the last user group role in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByUserId_Last(
		long userId, OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	 * Returns the user group roles before and after the current user group role in the ordered set where userId = &#63;.
	 *
	 * @param userGroupRoleId the primary key of the current user group role
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	public static UserGroupRole[] findByUserId_PrevAndNext(
			long userGroupRoleId, long userId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByUserId_PrevAndNext(
			userGroupRoleId, userId, orderByComparator);
	}

	/**
	 * Removes all the user group roles where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public static void removeByUserId(long userId) {
		getPersistence().removeByUserId(userId);
	}

	/**
	 * Returns the number of user group roles where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user group roles
	 */
	public static int countByUserId(long userId) {
		return getPersistence().countByUserId(userId);
	}

	/**
	 * Returns all the user group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching user group roles
	 */
	public static List<UserGroupRole> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the user group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of matching user group roles
	 */
	public static List<UserGroupRole> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the user group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group roles
	 */
	public static List<UserGroupRole> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the user group roles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group roles
	 */
	public static List<UserGroupRole> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first user group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByGroupId_First(
			long groupId, OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first user group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByGroupId_First(
		long groupId, OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last user group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByGroupId_Last(
			long groupId, OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last user group role in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByGroupId_Last(
		long groupId, OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the user group roles before and after the current user group role in the ordered set where groupId = &#63;.
	 *
	 * @param userGroupRoleId the primary key of the current user group role
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	public static UserGroupRole[] findByGroupId_PrevAndNext(
			long userGroupRoleId, long groupId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByGroupId_PrevAndNext(
			userGroupRoleId, groupId, orderByComparator);
	}

	/**
	 * Removes all the user group roles where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of user group roles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching user group roles
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the user group roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the matching user group roles
	 */
	public static List<UserGroupRole> findByRoleId(long roleId) {
		return getPersistence().findByRoleId(roleId);
	}

	/**
	 * Returns a range of all the user group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of matching user group roles
	 */
	public static List<UserGroupRole> findByRoleId(
		long roleId, int start, int end) {

		return getPersistence().findByRoleId(roleId, start, end);
	}

	/**
	 * Returns an ordered range of all the user group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group roles
	 */
	public static List<UserGroupRole> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().findByRoleId(
			roleId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the user group roles where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group roles
	 */
	public static List<UserGroupRole> findByRoleId(
		long roleId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByRoleId(
			roleId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first user group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByRoleId_First(
			long roleId, OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByRoleId_First(roleId, orderByComparator);
	}

	/**
	 * Returns the first user group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByRoleId_First(
		long roleId, OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().fetchByRoleId_First(roleId, orderByComparator);
	}

	/**
	 * Returns the last user group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByRoleId_Last(
			long roleId, OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByRoleId_Last(roleId, orderByComparator);
	}

	/**
	 * Returns the last user group role in the ordered set where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByRoleId_Last(
		long roleId, OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().fetchByRoleId_Last(roleId, orderByComparator);
	}

	/**
	 * Returns the user group roles before and after the current user group role in the ordered set where roleId = &#63;.
	 *
	 * @param userGroupRoleId the primary key of the current user group role
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	public static UserGroupRole[] findByRoleId_PrevAndNext(
			long userGroupRoleId, long roleId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByRoleId_PrevAndNext(
			userGroupRoleId, roleId, orderByComparator);
	}

	/**
	 * Removes all the user group roles where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID
	 */
	public static void removeByRoleId(long roleId) {
		getPersistence().removeByRoleId(roleId);
	}

	/**
	 * Returns the number of user group roles where roleId = &#63;.
	 *
	 * @param roleId the role ID
	 * @return the number of matching user group roles
	 */
	public static int countByRoleId(long roleId) {
		return getPersistence().countByRoleId(roleId);
	}

	/**
	 * Returns all the user group roles where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the matching user group roles
	 */
	public static List<UserGroupRole> findByU_G(long userId, long groupId) {
		return getPersistence().findByU_G(userId, groupId);
	}

	/**
	 * Returns a range of all the user group roles where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of matching user group roles
	 */
	public static List<UserGroupRole> findByU_G(
		long userId, long groupId, int start, int end) {

		return getPersistence().findByU_G(userId, groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the user group roles where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group roles
	 */
	public static List<UserGroupRole> findByU_G(
		long userId, long groupId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().findByU_G(
			userId, groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the user group roles where userId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group roles
	 */
	public static List<UserGroupRole> findByU_G(
		long userId, long groupId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByU_G(
			userId, groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first user group role in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByU_G_First(
			long userId, long groupId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByU_G_First(
			userId, groupId, orderByComparator);
	}

	/**
	 * Returns the first user group role in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByU_G_First(
		long userId, long groupId,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().fetchByU_G_First(
			userId, groupId, orderByComparator);
	}

	/**
	 * Returns the last user group role in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByU_G_Last(
			long userId, long groupId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByU_G_Last(
			userId, groupId, orderByComparator);
	}

	/**
	 * Returns the last user group role in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByU_G_Last(
		long userId, long groupId,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().fetchByU_G_Last(
			userId, groupId, orderByComparator);
	}

	/**
	 * Returns the user group roles before and after the current user group role in the ordered set where userId = &#63; and groupId = &#63;.
	 *
	 * @param userGroupRoleId the primary key of the current user group role
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	public static UserGroupRole[] findByU_G_PrevAndNext(
			long userGroupRoleId, long userId, long groupId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByU_G_PrevAndNext(
			userGroupRoleId, userId, groupId, orderByComparator);
	}

	/**
	 * Removes all the user group roles where userId = &#63; and groupId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 */
	public static void removeByU_G(long userId, long groupId) {
		getPersistence().removeByU_G(userId, groupId);
	}

	/**
	 * Returns the number of user group roles where userId = &#63; and groupId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @return the number of matching user group roles
	 */
	public static int countByU_G(long userId, long groupId) {
		return getPersistence().countByU_G(userId, groupId);
	}

	/**
	 * Returns all the user group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group roles
	 */
	public static List<UserGroupRole> findByG_R(long groupId, long roleId) {
		return getPersistence().findByG_R(groupId, roleId);
	}

	/**
	 * Returns a range of all the user group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of matching user group roles
	 */
	public static List<UserGroupRole> findByG_R(
		long groupId, long roleId, int start, int end) {

		return getPersistence().findByG_R(groupId, roleId, start, end);
	}

	/**
	 * Returns an ordered range of all the user group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user group roles
	 */
	public static List<UserGroupRole> findByG_R(
		long groupId, long roleId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().findByG_R(
			groupId, roleId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the user group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user group roles
	 */
	public static List<UserGroupRole> findByG_R(
		long groupId, long roleId, int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_R(
			groupId, roleId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first user group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByG_R_First(
			long groupId, long roleId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByG_R_First(
			groupId, roleId, orderByComparator);
	}

	/**
	 * Returns the first user group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByG_R_First(
		long groupId, long roleId,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().fetchByG_R_First(
			groupId, roleId, orderByComparator);
	}

	/**
	 * Returns the last user group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByG_R_Last(
			long groupId, long roleId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByG_R_Last(
			groupId, roleId, orderByComparator);
	}

	/**
	 * Returns the last user group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByG_R_Last(
		long groupId, long roleId,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().fetchByG_R_Last(
			groupId, roleId, orderByComparator);
	}

	/**
	 * Returns the user group roles before and after the current user group role in the ordered set where groupId = &#63; and roleId = &#63;.
	 *
	 * @param userGroupRoleId the primary key of the current user group role
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	public static UserGroupRole[] findByG_R_PrevAndNext(
			long userGroupRoleId, long groupId, long roleId,
			OrderByComparator<UserGroupRole> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByG_R_PrevAndNext(
			userGroupRoleId, groupId, roleId, orderByComparator);
	}

	/**
	 * Removes all the user group roles where groupId = &#63; and roleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 */
	public static void removeByG_R(long groupId, long roleId) {
		getPersistence().removeByG_R(groupId, roleId);
	}

	/**
	 * Returns the number of user group roles where groupId = &#63; and roleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the number of matching user group roles
	 */
	public static int countByG_R(long groupId, long roleId) {
		return getPersistence().countByG_R(groupId, roleId);
	}

	/**
	 * Returns the user group role where userId = &#63; and groupId = &#63; and roleId = &#63; or throws a <code>NoSuchUserGroupRoleException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group role
	 * @throws NoSuchUserGroupRoleException if a matching user group role could not be found
	 */
	public static UserGroupRole findByU_G_R(
			long userId, long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByU_G_R(userId, groupId, roleId);
	}

	/**
	 * Returns the user group role where userId = &#63; and groupId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByU_G_R(
		long userId, long groupId, long roleId) {

		return getPersistence().fetchByU_G_R(userId, groupId, roleId);
	}

	/**
	 * Returns the user group role where userId = &#63; and groupId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user group role, or <code>null</code> if a matching user group role could not be found
	 */
	public static UserGroupRole fetchByU_G_R(
		long userId, long groupId, long roleId, boolean useFinderCache) {

		return getPersistence().fetchByU_G_R(
			userId, groupId, roleId, useFinderCache);
	}

	/**
	 * Removes the user group role where userId = &#63; and groupId = &#63; and roleId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the user group role that was removed
	 */
	public static UserGroupRole removeByU_G_R(
			long userId, long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().removeByU_G_R(userId, groupId, roleId);
	}

	/**
	 * Returns the number of user group roles where userId = &#63; and groupId = &#63; and roleId = &#63;.
	 *
	 * @param userId the user ID
	 * @param groupId the group ID
	 * @param roleId the role ID
	 * @return the number of matching user group roles
	 */
	public static int countByU_G_R(long userId, long groupId, long roleId) {
		return getPersistence().countByU_G_R(userId, groupId, roleId);
	}

	/**
	 * Caches the user group role in the entity cache if it is enabled.
	 *
	 * @param userGroupRole the user group role
	 */
	public static void cacheResult(UserGroupRole userGroupRole) {
		getPersistence().cacheResult(userGroupRole);
	}

	/**
	 * Caches the user group roles in the entity cache if it is enabled.
	 *
	 * @param userGroupRoles the user group roles
	 */
	public static void cacheResult(List<UserGroupRole> userGroupRoles) {
		getPersistence().cacheResult(userGroupRoles);
	}

	/**
	 * Creates a new user group role with the primary key. Does not add the user group role to the database.
	 *
	 * @param userGroupRoleId the primary key for the new user group role
	 * @return the new user group role
	 */
	public static UserGroupRole create(long userGroupRoleId) {
		return getPersistence().create(userGroupRoleId);
	}

	/**
	 * Removes the user group role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userGroupRoleId the primary key of the user group role
	 * @return the user group role that was removed
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	public static UserGroupRole remove(long userGroupRoleId)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().remove(userGroupRoleId);
	}

	public static UserGroupRole updateImpl(UserGroupRole userGroupRole) {
		return getPersistence().updateImpl(userGroupRole);
	}

	/**
	 * Returns the user group role with the primary key or throws a <code>NoSuchUserGroupRoleException</code> if it could not be found.
	 *
	 * @param userGroupRoleId the primary key of the user group role
	 * @return the user group role
	 * @throws NoSuchUserGroupRoleException if a user group role with the primary key could not be found
	 */
	public static UserGroupRole findByPrimaryKey(long userGroupRoleId)
		throws com.liferay.portal.kernel.exception.
			NoSuchUserGroupRoleException {

		return getPersistence().findByPrimaryKey(userGroupRoleId);
	}

	/**
	 * Returns the user group role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userGroupRoleId the primary key of the user group role
	 * @return the user group role, or <code>null</code> if a user group role with the primary key could not be found
	 */
	public static UserGroupRole fetchByPrimaryKey(long userGroupRoleId) {
		return getPersistence().fetchByPrimaryKey(userGroupRoleId);
	}

	/**
	 * Returns all the user group roles.
	 *
	 * @return the user group roles
	 */
	public static List<UserGroupRole> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the user group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @return the range of user group roles
	 */
	public static List<UserGroupRole> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the user group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user group roles
	 */
	public static List<UserGroupRole> findAll(
		int start, int end,
		OrderByComparator<UserGroupRole> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the user group roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserGroupRoleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user group roles
	 * @param end the upper bound of the range of user group roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user group roles
	 */
	public static List<UserGroupRole> findAll(
		int start, int end, OrderByComparator<UserGroupRole> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the user group roles from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of user group roles.
	 *
	 * @return the number of user group roles
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static UserGroupRolePersistence getPersistence() {
		if (_persistence == null) {
			_persistence =
				(UserGroupRolePersistence)PortalBeanLocatorUtil.locate(
					UserGroupRolePersistence.class.getName());
		}

		return _persistence;
	}

	private static UserGroupRolePersistence _persistence;

}