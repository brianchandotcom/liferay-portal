/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourceTypePermission;

/**
 * The persistence interface for the resource type permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermissionPersistenceImpl
 * @see ResourceTypePermissionUtil
 * @generated
 */
public interface ResourceTypePermissionPersistence extends BasePersistence<ResourceTypePermission> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ResourceTypePermissionUtil} to access the resource type permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the resource type permission in the entity cache if it is enabled.
	*
	* @param resourceTypePermission the resource type permission
	*/
	public void cacheResult(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission);

	/**
	* Caches the resource type permissions in the entity cache if it is enabled.
	*
	* @param resourceTypePermissions the resource type permissions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceTypePermission> resourceTypePermissions);

	/**
	* Creates a new resource type permission with the primary key. Does not add the resource type permission to the database.
	*
	* @param resourceTypePermissionId the primary key for the new resource type permission
	* @return the new resource type permission
	*/
	public com.liferay.portal.model.ResourceTypePermission create(
		long resourceTypePermissionId);

	/**
	* Removes the resource type permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission that was removed
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceTypePermission remove(
		long resourceTypePermissionId)
		throws com.liferay.portal.NoSuchResourceTypePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourceTypePermission updateImpl(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource type permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourceTypePermissionException} if it could not be found.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceTypePermission findByPrimaryKey(
		long resourceTypePermissionId)
		throws com.liferay.portal.NoSuchResourceTypePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource type permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission, or <code>null</code> if a resource type permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceTypePermission fetchByPrimaryKey(
		long resourceTypePermissionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource type permissions where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @return the matching resource type permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceTypePermission> findByC_G(
		long companyId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource type permissions where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @return the range of matching resource type permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceTypePermission> findByC_G(
		long companyId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource type permissions where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource type permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceTypePermission> findByC_G(
		long companyId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource type permission in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceTypePermission findByC_G_First(
		long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceTypePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource type permission in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceTypePermission findByC_G_Last(
		long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceTypePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource type permissions before and after the current resource type permission in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceTypePermissionId the primary key of the current resource type permission
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourceTypePermission[] findByC_G_PrevAndNext(
		long resourceTypePermissionId, long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceTypePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource type permissions.
	*
	* @return the resource type permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceTypePermission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource type permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @return the range of resource type permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceTypePermission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource type permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource type permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourceTypePermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource type permissions where companyId = &#63; and groupId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_G(long companyId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource type permissions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource type permissions where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @return the number of matching resource type permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_G(long companyId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource type permissions.
	*
	* @return the number of resource type permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public ResourceTypePermission remove(
		ResourceTypePermission resourceTypePermission)
		throws SystemException;
}