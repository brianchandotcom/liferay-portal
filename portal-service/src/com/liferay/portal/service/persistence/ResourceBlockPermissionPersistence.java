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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ResourceBlockPermission;

/**
 * The persistence interface for the resource block permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermissionPersistenceImpl
 * @see ResourceBlockPermissionUtil
 * @generated
 */
public interface ResourceBlockPermissionPersistence extends BasePersistence<ResourceBlockPermission> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ResourceBlockPermissionUtil} to access the resource block permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the resource block permission in the entity cache if it is enabled.
	*
	* @param resourceBlockPermission the resource block permission
	*/
	public void cacheResult(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission);

	/**
	* Caches the resource block permissions in the entity cache if it is enabled.
	*
	* @param resourceBlockPermissions the resource block permissions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceBlockPermission> resourceBlockPermissions);

	/**
	* Creates a new resource block permission with the primary key. Does not add the resource block permission to the database.
	*
	* @param resourceBlockPermissionId the primary key for the new resource block permission
	* @return the new resource block permission
	*/
	public com.liferay.portal.model.ResourceBlockPermission create(
		long resourceBlockPermissionId);

	/**
	* Removes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission that was removed
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlockPermission remove(
		long resourceBlockPermissionId)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceBlockPermission updateImpl(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourceBlockPermissionException} if it could not be found.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlockPermission findByPrimaryKey(
		long resourceBlockPermissionId)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission, or <code>null</code> if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlockPermission fetchByPrimaryKey(
		long resourceBlockPermissionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource block permissions where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource block permissions where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource block permissions where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlockPermission findByResourceBlockId_First(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlockPermission findByResourceBlockId_Last(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block permissions before and after the current resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockPermissionId the primary key of the current resource block permission
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlockPermission[] findByResourceBlockId_PrevAndNext(
		long resourceBlockPermissionId, long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or throws a {@link com.liferay.portal.NoSuchResourceBlockPermissionException} if it could not be found.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the matching resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlockPermission findByR_R(
		long resourceBlockId, long roleId)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlockPermission fetchByR_R(
		long resourceBlockId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlockPermission fetchByR_R(
		long resourceBlockId, long roleId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource block permissions.
	*
	* @return the resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlockPermission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource block permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlockPermission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource block permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceBlockPermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource block permissions where resourceBlockId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByResourceBlockId(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the resource block permission where resourceBlockId = &#63; and roleId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the resource block permission that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceBlockPermission removeByR_R(
		long resourceBlockId, long roleId)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource block permissions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource block permissions where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the number of matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByResourceBlockId(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource block permissions where resourceBlockId = &#63; and roleId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the number of matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByR_R(long resourceBlockId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource block permissions.
	*
	* @return the number of resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}