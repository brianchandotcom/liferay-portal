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

import com.liferay.portal.model.OrgGroupPermission;

/**
 * The persistence interface for the org group permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrgGroupPermissionPersistenceImpl
 * @see OrgGroupPermissionUtil
 * @generated
 */
public interface OrgGroupPermissionPersistence extends BasePersistence<OrgGroupPermission> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OrgGroupPermissionUtil} to access the org group permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the org group permission in the entity cache if it is enabled.
	*
	* @param orgGroupPermission the org group permission
	*/
	public void cacheResult(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission);

	/**
	* Caches the org group permissions in the entity cache if it is enabled.
	*
	* @param orgGroupPermissions the org group permissions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.OrgGroupPermission> orgGroupPermissions);

	/**
	* Creates a new org group permission with the primary key. Does not add the org group permission to the database.
	*
	* @param orgGroupPermissionPK the primary key for the new org group permission
	* @return the new org group permission
	*/
	public com.liferay.portal.model.OrgGroupPermission create(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK);

	/**
	* Removes the org group permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orgGroupPermissionPK the primary key of the org group permission
	* @return the org group permission that was removed
	* @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a org group permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.OrgGroupPermission remove(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgGroupPermission updateImpl(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the org group permission with the primary key or throws a {@link com.liferay.portal.NoSuchOrgGroupPermissionException} if it could not be found.
	*
	* @param orgGroupPermissionPK the primary key of the org group permission
	* @return the org group permission
	* @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a org group permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.OrgGroupPermission findByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the org group permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param orgGroupPermissionPK the primary key of the org group permission
	* @return the org group permission, or <code>null</code> if a org group permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.OrgGroupPermission fetchByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the org group permissions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupPermission> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the org group permissions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of org group permissions
	* @param end the upper bound of the range of org group permissions (not inclusive)
	* @return the range of matching org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupPermission> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the org group permissions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of org group permissions
	* @param end the upper bound of the range of org group permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupPermission> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first org group permission in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org group permission
	* @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a matching org group permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.OrgGroupPermission findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last org group permission in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org group permission
	* @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a matching org group permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.OrgGroupPermission findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the org group permissions before and after the current org group permission in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param orgGroupPermissionPK the primary key of the current org group permission
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next org group permission
	* @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a org group permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.OrgGroupPermission[] findByGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the org group permissions where permissionId = &#63;.
	*
	* @param permissionId the permission ID
	* @return the matching org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupPermission> findByPermissionId(
		long permissionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the org group permissions where permissionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param permissionId the permission ID
	* @param start the lower bound of the range of org group permissions
	* @param end the upper bound of the range of org group permissions (not inclusive)
	* @return the range of matching org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupPermission> findByPermissionId(
		long permissionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the org group permissions where permissionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param permissionId the permission ID
	* @param start the lower bound of the range of org group permissions
	* @param end the upper bound of the range of org group permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupPermission> findByPermissionId(
		long permissionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first org group permission in the ordered set where permissionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param permissionId the permission ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org group permission
	* @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a matching org group permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.OrgGroupPermission findByPermissionId_First(
		long permissionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last org group permission in the ordered set where permissionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param permissionId the permission ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org group permission
	* @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a matching org group permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.OrgGroupPermission findByPermissionId_Last(
		long permissionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the org group permissions before and after the current org group permission in the ordered set where permissionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param orgGroupPermissionPK the primary key of the current org group permission
	* @param permissionId the permission ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next org group permission
	* @throws com.liferay.portal.NoSuchOrgGroupPermissionException if a org group permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.OrgGroupPermission[] findByPermissionId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupPermissionPK orgGroupPermissionPK,
		long permissionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupPermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the org group permissions.
	*
	* @return the org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupPermission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the org group permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of org group permissions
	* @param end the upper bound of the range of org group permissions (not inclusive)
	* @return the range of org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupPermission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the org group permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of org group permissions
	* @param end the upper bound of the range of org group permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.OrgGroupPermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the org group permissions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the org group permissions where permissionId = &#63; from the database.
	*
	* @param permissionId the permission ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPermissionId(long permissionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the org group permissions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of org group permissions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of org group permissions where permissionId = &#63;.
	*
	* @param permissionId the permission ID
	* @return the number of matching org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByPermissionId(long permissionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of org group permissions.
	*
	* @return the number of org group permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}