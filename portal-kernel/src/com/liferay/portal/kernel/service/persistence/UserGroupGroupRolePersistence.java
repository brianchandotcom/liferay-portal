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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchUserGroupGroupRoleException;
import com.liferay.portal.kernel.model.UserGroupGroupRole;

import java.util.Set;

/**
 * The persistence interface for the user group group role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRoleUtil
 * @generated
 */
@ProviderType
public interface UserGroupGroupRolePersistence extends BasePersistence<UserGroupGroupRole> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserGroupGroupRoleUtil} to access the user group group role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the user group group roles where userGroupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @return the matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByUserGroupId(
		long userGroupId);

	/**
	* Returns a range of all the user group group roles where userGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userGroupId the user group ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @return the range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end);

	/**
	* Returns an ordered range of all the user group group roles where userGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userGroupId the user group ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns an ordered range of all the user group group roles where userGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userGroupId the user group ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user group group role in the ordered set where userGroupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group group role
	* @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	*/
	public UserGroupGroupRole findByUserGroupId_First(long userGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the first user group group role in the ordered set where userGroupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	*/
	public UserGroupGroupRole fetchByUserGroupId_First(long userGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns the last user group group role in the ordered set where userGroupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group group role
	* @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	*/
	public UserGroupGroupRole findByUserGroupId_Last(long userGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the last user group group role in the ordered set where userGroupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	*/
	public UserGroupGroupRole fetchByUserGroupId_Last(long userGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns the user group group roles before and after the current user group group role in the ordered set where userGroupId = &#63;.
	*
	* @param userGroupGroupRolePK the primary key of the current user group group role
	* @param userGroupId the user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group group role
	* @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	*/
	public UserGroupGroupRole[] findByUserGroupId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long userGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Removes all the user group group roles where userGroupId = &#63; from the database.
	*
	* @param userGroupId the user group ID
	*/
	public void removeByUserGroupId(long userGroupId);

	/**
	* Returns the number of user group group roles where userGroupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @return the number of matching user group group roles
	*/
	public int countByUserGroupId(long userGroupId);

	/**
	* Returns all the user group group roles where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByGroupId(long groupId);

	/**
	* Returns a range of all the user group group roles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @return the range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the user group group roles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns an ordered range of all the user group group roles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user group group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group group role
	* @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	*/
	public UserGroupGroupRole findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the first user group group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	*/
	public UserGroupGroupRole fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns the last user group group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group group role
	* @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	*/
	public UserGroupGroupRole findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the last user group group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	*/
	public UserGroupGroupRole fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns the user group group roles before and after the current user group group role in the ordered set where groupId = &#63;.
	*
	* @param userGroupGroupRolePK the primary key of the current user group group role
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group group role
	* @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	*/
	public UserGroupGroupRole[] findByGroupId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Removes all the user group group roles where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of user group group roles where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching user group group roles
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the user group group roles where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByRoleId(long roleId);

	/**
	* Returns a range of all the user group group roles where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @return the range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByRoleId(long roleId,
		int start, int end);

	/**
	* Returns an ordered range of all the user group group roles where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByRoleId(long roleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns an ordered range of all the user group group roles where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByRoleId(long roleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user group group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group group role
	* @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	*/
	public UserGroupGroupRole findByRoleId_First(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the first user group group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	*/
	public UserGroupGroupRole fetchByRoleId_First(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns the last user group group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group group role
	* @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	*/
	public UserGroupGroupRole findByRoleId_Last(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the last user group group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	*/
	public UserGroupGroupRole fetchByRoleId_Last(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns the user group group roles before and after the current user group group role in the ordered set where roleId = &#63;.
	*
	* @param userGroupGroupRolePK the primary key of the current user group group role
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group group role
	* @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	*/
	public UserGroupGroupRole[] findByRoleId_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Removes all the user group group roles where roleId = &#63; from the database.
	*
	* @param roleId the role ID
	*/
	public void removeByRoleId(long roleId);

	/**
	* Returns the number of user group group roles where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the number of matching user group group roles
	*/
	public int countByRoleId(long roleId);

	/**
	* Returns all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @param groupId the group ID
	* @return the matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByU_G(long userGroupId,
		long groupId);

	/**
	* Returns a range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userGroupId the user group ID
	* @param groupId the group ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @return the range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByU_G(long userGroupId,
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userGroupId the user group ID
	* @param groupId the group ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByU_G(long userGroupId,
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns an ordered range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userGroupId the user group ID
	* @param groupId the group ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByU_G(long userGroupId,
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group group role
	* @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	*/
	public UserGroupGroupRole findByU_G_First(long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the first user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	*/
	public UserGroupGroupRole fetchByU_G_First(long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns the last user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group group role
	* @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	*/
	public UserGroupGroupRole findByU_G_Last(long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the last user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	*/
	public UserGroupGroupRole fetchByU_G_Last(long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns the user group group roles before and after the current user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	*
	* @param userGroupGroupRolePK the primary key of the current user group group role
	* @param userGroupId the user group ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group group role
	* @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	*/
	public UserGroupGroupRole[] findByU_G_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long userGroupId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Removes all the user group group roles where userGroupId = &#63; and groupId = &#63; from the database.
	*
	* @param userGroupId the user group ID
	* @param groupId the group ID
	*/
	public void removeByU_G(long userGroupId, long groupId);

	/**
	* Returns the number of user group group roles where userGroupId = &#63; and groupId = &#63;.
	*
	* @param userGroupId the user group ID
	* @param groupId the group ID
	* @return the number of matching user group group roles
	*/
	public int countByU_G(long userGroupId, long groupId);

	/**
	* Returns all the user group group roles where groupId = &#63; and roleId = &#63;.
	*
	* @param groupId the group ID
	* @param roleId the role ID
	* @return the matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByG_R(long groupId,
		long roleId);

	/**
	* Returns a range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param roleId the role ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @return the range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByG_R(long groupId,
		long roleId, int start, int end);

	/**
	* Returns an ordered range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param roleId the role ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByG_R(long groupId,
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns an ordered range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param roleId the role ID
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findByG_R(long groupId,
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	*
	* @param groupId the group ID
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group group role
	* @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	*/
	public UserGroupGroupRole findByG_R_First(long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the first user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	*
	* @param groupId the group ID
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group group role, or <code>null</code> if a matching user group group role could not be found
	*/
	public UserGroupGroupRole fetchByG_R_First(long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns the last user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	*
	* @param groupId the group ID
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group group role
	* @throws NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	*/
	public UserGroupGroupRole findByG_R_Last(long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the last user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	*
	* @param groupId the group ID
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group group role, or <code>null</code> if a matching user group group role could not be found
	*/
	public UserGroupGroupRole fetchByG_R_Last(long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns the user group group roles before and after the current user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	*
	* @param userGroupGroupRolePK the primary key of the current user group group role
	* @param groupId the group ID
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group group role
	* @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	*/
	public UserGroupGroupRole[] findByG_R_PrevAndNext(
		UserGroupGroupRolePK userGroupGroupRolePK, long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Removes all the user group group roles where groupId = &#63; and roleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param roleId the role ID
	*/
	public void removeByG_R(long groupId, long roleId);

	/**
	* Returns the number of user group group roles where groupId = &#63; and roleId = &#63;.
	*
	* @param groupId the group ID
	* @param roleId the role ID
	* @return the number of matching user group group roles
	*/
	public int countByG_R(long groupId, long roleId);

	/**
	* Caches the user group group role in the entity cache if it is enabled.
	*
	* @param userGroupGroupRole the user group group role
	*/
	public void cacheResult(UserGroupGroupRole userGroupGroupRole);

	/**
	* Caches the user group group roles in the entity cache if it is enabled.
	*
	* @param userGroupGroupRoles the user group group roles
	*/
	public void cacheResult(
		java.util.List<UserGroupGroupRole> userGroupGroupRoles);

	/**
	* Creates a new user group group role with the primary key. Does not add the user group group role to the database.
	*
	* @param userGroupGroupRolePK the primary key for the new user group group role
	* @return the new user group group role
	*/
	public UserGroupGroupRole create(UserGroupGroupRolePK userGroupGroupRolePK);

	/**
	* Removes the user group group role with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupGroupRolePK the primary key of the user group group role
	* @return the user group group role that was removed
	* @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	*/
	public UserGroupGroupRole remove(UserGroupGroupRolePK userGroupGroupRolePK)
		throws NoSuchUserGroupGroupRoleException;

	public UserGroupGroupRole updateImpl(UserGroupGroupRole userGroupGroupRole);

	/**
	* Returns the user group group role with the primary key or throws a <code>NoSuchUserGroupGroupRoleException</code> if it could not be found.
	*
	* @param userGroupGroupRolePK the primary key of the user group group role
	* @return the user group group role
	* @throws NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	*/
	public UserGroupGroupRole findByPrimaryKey(
		UserGroupGroupRolePK userGroupGroupRolePK)
		throws NoSuchUserGroupGroupRoleException;

	/**
	* Returns the user group group role with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userGroupGroupRolePK the primary key of the user group group role
	* @return the user group group role, or <code>null</code> if a user group group role with the primary key could not be found
	*/
	public UserGroupGroupRole fetchByPrimaryKey(
		UserGroupGroupRolePK userGroupGroupRolePK);

	/**
	* Returns all the user group group roles.
	*
	* @return the user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findAll();

	/**
	* Returns a range of all the user group group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @return the range of user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findAll(int start, int end);

	/**
	* Returns an ordered range of all the user group group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator);

	/**
	* Returns an ordered range of all the user group group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserGroupGroupRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user group group roles
	* @param end the upper bound of the range of user group group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of user group group roles
	*/
	public java.util.List<UserGroupGroupRole> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserGroupGroupRole> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the user group group roles from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of user group group roles.
	*
	* @return the number of user group group roles
	*/
	public int countAll();

	public Set<String> getCompoundPKColumnNames();
}