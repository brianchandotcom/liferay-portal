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

import com.liferay.portal.exception.NoSuchResourceBlockPermissionException;
import com.liferay.portal.model.ResourceBlockPermission;

/**
 * The persistence interface for the resource block permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.ResourceBlockPermissionPersistenceImpl
 * @see ResourceBlockPermissionUtil
 * @generated
 */
@ProviderType
public interface ResourceBlockPermissionPersistence extends BasePersistence<ResourceBlockPermission> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ResourceBlockPermissionUtil} to access the resource block permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the resource block permissions where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the matching resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId);

	/**
	* Returns a range of all the resource block permissions where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of matching resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end);

	/**
	* Returns an ordered range of all the resource block permissions where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource block permissions where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block permission
	* @throws NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission findByResourceBlockId_First(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException;

	/**
	* Returns the first resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission fetchByResourceBlockId_First(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator);

	/**
	* Returns the last resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block permission
	* @throws NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission findByResourceBlockId_Last(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException;

	/**
	* Returns the last resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission fetchByResourceBlockId_Last(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator);

	/**
	* Returns the resource block permissions before and after the current resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockPermissionId the primary key of the current resource block permission
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block permission
	* @throws NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	*/
	public ResourceBlockPermission[] findByResourceBlockId_PrevAndNext(
		long resourceBlockPermissionId, long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException;

	/**
	* Removes all the resource block permissions where resourceBlockId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	*/
	public void removeByResourceBlockId(long resourceBlockId);

	/**
	* Returns the number of resource block permissions where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the number of matching resource block permissions
	*/
	public int countByResourceBlockId(long resourceBlockId);

	/**
	* Returns all the resource block permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the matching resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findByRoleId(long roleId);

	/**
	* Returns a range of all the resource block permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of matching resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findByRoleId(long roleId,
		int start, int end);

	/**
	* Returns an ordered range of all the resource block permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findByRoleId(long roleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource block permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findByRoleId(long roleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource block permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block permission
	* @throws NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission findByRoleId_First(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException;

	/**
	* Returns the first resource block permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission fetchByRoleId_First(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator);

	/**
	* Returns the last resource block permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block permission
	* @throws NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission findByRoleId_Last(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException;

	/**
	* Returns the last resource block permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission fetchByRoleId_Last(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator);

	/**
	* Returns the resource block permissions before and after the current resource block permission in the ordered set where roleId = &#63;.
	*
	* @param resourceBlockPermissionId the primary key of the current resource block permission
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block permission
	* @throws NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	*/
	public ResourceBlockPermission[] findByRoleId_PrevAndNext(
		long resourceBlockPermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator)
		throws NoSuchResourceBlockPermissionException;

	/**
	* Removes all the resource block permissions where roleId = &#63; from the database.
	*
	* @param roleId the role ID
	*/
	public void removeByRoleId(long roleId);

	/**
	* Returns the number of resource block permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the number of matching resource block permissions
	*/
	public int countByRoleId(long roleId);

	/**
	* Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or throws a {@link NoSuchResourceBlockPermissionException} if it could not be found.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the matching resource block permission
	* @throws NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission findByR_R(long resourceBlockId, long roleId)
		throws NoSuchResourceBlockPermissionException;

	/**
	* Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission fetchByR_R(long resourceBlockId, long roleId);

	/**
	* Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public ResourceBlockPermission fetchByR_R(long resourceBlockId,
		long roleId, boolean retrieveFromCache);

	/**
	* Removes the resource block permission where resourceBlockId = &#63; and roleId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the resource block permission that was removed
	*/
	public ResourceBlockPermission removeByR_R(long resourceBlockId, long roleId)
		throws NoSuchResourceBlockPermissionException;

	/**
	* Returns the number of resource block permissions where resourceBlockId = &#63; and roleId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the number of matching resource block permissions
	*/
	public int countByR_R(long resourceBlockId, long roleId);

	/**
	* Caches the resource block permission in the entity cache if it is enabled.
	*
	* @param resourceBlockPermission the resource block permission
	*/
	public void cacheResult(ResourceBlockPermission resourceBlockPermission);

	/**
	* Caches the resource block permissions in the entity cache if it is enabled.
	*
	* @param resourceBlockPermissions the resource block permissions
	*/
	public void cacheResult(
		java.util.List<ResourceBlockPermission> resourceBlockPermissions);

	/**
	* Creates a new resource block permission with the primary key. Does not add the resource block permission to the database.
	*
	* @param resourceBlockPermissionId the primary key for the new resource block permission
	* @return the new resource block permission
	*/
	public ResourceBlockPermission create(long resourceBlockPermissionId);

	/**
	* Removes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission that was removed
	* @throws NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	*/
	public ResourceBlockPermission remove(long resourceBlockPermissionId)
		throws NoSuchResourceBlockPermissionException;

	public ResourceBlockPermission updateImpl(
		ResourceBlockPermission resourceBlockPermission);

	/**
	* Returns the resource block permission with the primary key or throws a {@link NoSuchResourceBlockPermissionException} if it could not be found.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission
	* @throws NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	*/
	public ResourceBlockPermission findByPrimaryKey(
		long resourceBlockPermissionId)
		throws NoSuchResourceBlockPermissionException;

	/**
	* Returns the resource block permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission, or <code>null</code> if a resource block permission with the primary key could not be found
	*/
	public ResourceBlockPermission fetchByPrimaryKey(
		long resourceBlockPermissionId);

	@Override
	public java.util.Map<java.io.Serializable, ResourceBlockPermission> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the resource block permissions.
	*
	* @return the resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findAll();

	/**
	* Returns a range of all the resource block permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findAll(int start, int end);

	/**
	* Returns an ordered range of all the resource block permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource block permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of resource block permissions
	*/
	public java.util.List<ResourceBlockPermission> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourceBlockPermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the resource block permissions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of resource block permissions.
	*
	* @return the number of resource block permissions
	*/
	public int countAll();
}