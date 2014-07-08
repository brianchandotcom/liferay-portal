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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.OrgGroupRole;

/**
 * The persistence interface for the org group role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrgGroupRolePersistenceImpl
 * @see OrgGroupRoleUtil
 * @generated
 */
@ProviderType
public interface OrgGroupRolePersistence extends BasePersistence<OrgGroupRole> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OrgGroupRoleUtil} to access the org group role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the org group roles where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching org group roles
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the org group roles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @return the range of matching org group roles
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the org group roles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching org group roles
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first org group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org group role
	* @throws com.liferay.portal.NoSuchOrgGroupRoleException if a matching org group role could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException;

	/**
	* Returns the first org group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org group role, or <code>null</code> if a matching org group role could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last org group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org group role
	* @throws com.liferay.portal.NoSuchOrgGroupRoleException if a matching org group role could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException;

	/**
	* Returns the last org group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org group role, or <code>null</code> if a matching org group role could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the org group roles before and after the current org group role in the ordered set where groupId = &#63;.
	*
	* @param orgGroupRolePK the primary key of the current org group role
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next org group role
	* @throws com.liferay.portal.NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole[] findByGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException;

	/**
	* Removes all the org group roles where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of org group roles where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching org group roles
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the org group roles where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the matching org group roles
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByRoleId(
		long roleId);

	/**
	* Returns a range of all the org group roles where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @return the range of matching org group roles
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByRoleId(
		long roleId, int start, int end);

	/**
	* Returns an ordered range of all the org group roles where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching org group roles
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first org group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org group role
	* @throws com.liferay.portal.NoSuchOrgGroupRoleException if a matching org group role could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole findByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException;

	/**
	* Returns the first org group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org group role, or <code>null</code> if a matching org group role could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole fetchByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last org group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org group role
	* @throws com.liferay.portal.NoSuchOrgGroupRoleException if a matching org group role could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole findByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException;

	/**
	* Returns the last org group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org group role, or <code>null</code> if a matching org group role could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole fetchByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the org group roles before and after the current org group role in the ordered set where roleId = &#63;.
	*
	* @param orgGroupRolePK the primary key of the current org group role
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next org group role
	* @throws com.liferay.portal.NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole[] findByRoleId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK,
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException;

	/**
	* Removes all the org group roles where roleId = &#63; from the database.
	*
	* @param roleId the role ID
	*/
	public void removeByRoleId(long roleId);

	/**
	* Returns the number of org group roles where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the number of matching org group roles
	*/
	public int countByRoleId(long roleId);

	/**
	* Caches the org group role in the entity cache if it is enabled.
	*
	* @param orgGroupRole the org group role
	*/
	public void cacheResult(com.liferay.portal.model.OrgGroupRole orgGroupRole);

	/**
	* Caches the org group roles in the entity cache if it is enabled.
	*
	* @param orgGroupRoles the org group roles
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.OrgGroupRole> orgGroupRoles);

	/**
	* Creates a new org group role with the primary key. Does not add the org group role to the database.
	*
	* @param orgGroupRolePK the primary key for the new org group role
	* @return the new org group role
	*/
	public com.liferay.portal.model.OrgGroupRole create(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK);

	/**
	* Removes the org group role with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orgGroupRolePK the primary key of the org group role
	* @return the org group role that was removed
	* @throws com.liferay.portal.NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole remove(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK)
		throws com.liferay.portal.NoSuchOrgGroupRoleException;

	public com.liferay.portal.model.OrgGroupRole updateImpl(
		com.liferay.portal.model.OrgGroupRole orgGroupRole);

	/**
	* Returns the org group role with the primary key or throws a {@link com.liferay.portal.NoSuchOrgGroupRoleException} if it could not be found.
	*
	* @param orgGroupRolePK the primary key of the org group role
	* @return the org group role
	* @throws com.liferay.portal.NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole findByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK)
		throws com.liferay.portal.NoSuchOrgGroupRoleException;

	/**
	* Returns the org group role with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param orgGroupRolePK the primary key of the org group role
	* @return the org group role, or <code>null</code> if a org group role with the primary key could not be found
	*/
	public com.liferay.portal.model.OrgGroupRole fetchByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.OrgGroupRole> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the org group roles.
	*
	* @return the org group roles
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupRole> findAll();

	/**
	* Returns a range of all the org group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @return the range of org group roles
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupRole> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the org group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of org group roles
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupRole> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the org group roles from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of org group roles.
	*
	* @return the number of org group roles
	*/
	public int countAll();
}