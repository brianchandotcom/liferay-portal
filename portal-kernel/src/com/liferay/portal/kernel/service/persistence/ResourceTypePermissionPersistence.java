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

import com.liferay.portal.kernel.exception.NoSuchResourceTypePermissionException;
import com.liferay.portal.kernel.model.ResourceTypePermission;

/**
 * The persistence interface for the resource type permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermissionUtil
 * @deprecated As of Judson (7.1.x), with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public interface ResourceTypePermissionPersistence extends BasePersistence<ResourceTypePermission> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ResourceTypePermissionUtil} to access the resource type permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the resource type permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the matching resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findByRoleId(long roleId);

	/**
	* Returns a range of all the resource type permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceTypePermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @return the range of matching resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findByRoleId(long roleId,
		int start, int end);

	/**
	* Returns an ordered range of all the resource type permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceTypePermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findByRoleId(long roleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource type permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceTypePermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findByRoleId(long roleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource type permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource type permission
	* @throws NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	*/
	public ResourceTypePermission findByRoleId_First(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException;

	/**
	* Returns the first resource type permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public ResourceTypePermission fetchByRoleId_First(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator);

	/**
	* Returns the last resource type permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource type permission
	* @throws NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	*/
	public ResourceTypePermission findByRoleId_Last(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException;

	/**
	* Returns the last resource type permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public ResourceTypePermission fetchByRoleId_Last(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator);

	/**
	* Returns the resource type permissions before and after the current resource type permission in the ordered set where roleId = &#63;.
	*
	* @param resourceTypePermissionId the primary key of the current resource type permission
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource type permission
	* @throws NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	*/
	public ResourceTypePermission[] findByRoleId_PrevAndNext(
		long resourceTypePermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException;

	/**
	* Removes all the resource type permissions where roleId = &#63; from the database.
	*
	* @param roleId the role ID
	*/
	public void removeByRoleId(long roleId);

	/**
	* Returns the number of resource type permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the number of matching resource type permissions
	*/
	public int countByRoleId(long roleId);

	/**
	* Returns all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @return the matching resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId);

	/**
	* Returns a range of all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceTypePermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @return the range of matching resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId, int start, int end);

	/**
	* Returns an ordered range of all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceTypePermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceTypePermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findByC_N_R(long companyId,
		String name, long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource type permission
	* @throws NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	*/
	public ResourceTypePermission findByC_N_R_First(long companyId,
		String name, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException;

	/**
	* Returns the first resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public ResourceTypePermission fetchByC_N_R_First(long companyId,
		String name, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator);

	/**
	* Returns the last resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource type permission
	* @throws NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	*/
	public ResourceTypePermission findByC_N_R_Last(long companyId, String name,
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException;

	/**
	* Returns the last resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public ResourceTypePermission fetchByC_N_R_Last(long companyId,
		String name, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator);

	/**
	* Returns the resource type permissions before and after the current resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param resourceTypePermissionId the primary key of the current resource type permission
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource type permission
	* @throws NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	*/
	public ResourceTypePermission[] findByC_N_R_PrevAndNext(
		long resourceTypePermissionId, long companyId, String name,
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator)
		throws NoSuchResourceTypePermissionException;

	/**
	* Removes all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	*/
	public void removeByC_N_R(long companyId, String name, long roleId);

	/**
	* Returns the number of resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @return the number of matching resource type permissions
	*/
	public int countByC_N_R(long companyId, String name, long roleId);

	/**
	* Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or throws a <code>NoSuchResourceTypePermissionException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param roleId the role ID
	* @return the matching resource type permission
	* @throws NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	*/
	public ResourceTypePermission findByC_G_N_R(long companyId, long groupId,
		String name, long roleId) throws NoSuchResourceTypePermissionException;

	/**
	* Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param roleId the role ID
	* @return the matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public ResourceTypePermission fetchByC_G_N_R(long companyId, long groupId,
		String name, long roleId);

	/**
	* Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param roleId the role ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public ResourceTypePermission fetchByC_G_N_R(long companyId, long groupId,
		String name, long roleId, boolean retrieveFromCache);

	/**
	* Removes the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param roleId the role ID
	* @return the resource type permission that was removed
	*/
	public ResourceTypePermission removeByC_G_N_R(long companyId, long groupId,
		String name, long roleId) throws NoSuchResourceTypePermissionException;

	/**
	* Returns the number of resource type permissions where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param roleId the role ID
	* @return the number of matching resource type permissions
	*/
	public int countByC_G_N_R(long companyId, long groupId, String name,
		long roleId);

	/**
	* Caches the resource type permission in the entity cache if it is enabled.
	*
	* @param resourceTypePermission the resource type permission
	*/
	public void cacheResult(ResourceTypePermission resourceTypePermission);

	/**
	* Caches the resource type permissions in the entity cache if it is enabled.
	*
	* @param resourceTypePermissions the resource type permissions
	*/
	public void cacheResult(
		java.util.List<ResourceTypePermission> resourceTypePermissions);

	/**
	* Creates a new resource type permission with the primary key. Does not add the resource type permission to the database.
	*
	* @param resourceTypePermissionId the primary key for the new resource type permission
	* @return the new resource type permission
	*/
	public ResourceTypePermission create(long resourceTypePermissionId);

	/**
	* Removes the resource type permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission that was removed
	* @throws NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	*/
	public ResourceTypePermission remove(long resourceTypePermissionId)
		throws NoSuchResourceTypePermissionException;

	public ResourceTypePermission updateImpl(
		ResourceTypePermission resourceTypePermission);

	/**
	* Returns the resource type permission with the primary key or throws a <code>NoSuchResourceTypePermissionException</code> if it could not be found.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission
	* @throws NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	*/
	public ResourceTypePermission findByPrimaryKey(
		long resourceTypePermissionId)
		throws NoSuchResourceTypePermissionException;

	/**
	* Returns the resource type permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission, or <code>null</code> if a resource type permission with the primary key could not be found
	*/
	public ResourceTypePermission fetchByPrimaryKey(
		long resourceTypePermissionId);

	/**
	* Returns all the resource type permissions.
	*
	* @return the resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findAll();

	/**
	* Returns a range of all the resource type permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceTypePermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @return the range of resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findAll(int start, int end);

	/**
	* Returns an ordered range of all the resource type permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceTypePermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource type permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ResourceTypePermissionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of resource type permissions
	*/
	public java.util.List<ResourceTypePermission> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceTypePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the resource type permissions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of resource type permissions.
	*
	* @return the number of resource type permissions
	*/
	public int countAll();
}