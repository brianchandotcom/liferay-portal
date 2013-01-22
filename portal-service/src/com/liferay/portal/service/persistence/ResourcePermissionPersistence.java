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

import com.liferay.portal.model.ResourcePermission;

/**
 * The persistence interface for the resource permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionPersistenceImpl
 * @see ResourcePermissionUtil
 * @generated
 */
public interface ResourcePermissionPersistence extends BasePersistence<ResourcePermission> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ResourcePermissionUtil} to access the resource permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the resource permissions where scope = &#63;.
	*
	* @param scope the scope
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int scope) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource permissions where scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int scope, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource permissions where scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource permission in the ordered set where scope = &#63;.
	*
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByScope_First(
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource permission in the ordered set where scope = &#63;.
	*
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByScope_First(
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource permission in the ordered set where scope = &#63;.
	*
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByScope_Last(
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource permission in the ordered set where scope = &#63;.
	*
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByScope_Last(
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where scope = &#63;.
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission[] findByScope_PrevAndNext(
		long resourcePermissionId, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource permissions where scope = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scopes the scopes
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int[] scopes)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource permissions where scope = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scopes the scopes
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int[] scopes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource permissions where scope = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scopes the scopes
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int[] scopes, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions where scope = &#63; from the database.
	*
	* @param scope the scope
	* @throws SystemException if a system exception occurred
	*/
	public void removeByScope(int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource permissions where scope = &#63;.
	*
	* @param scope the scope
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByScope(int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource permissions where scope = any &#63;.
	*
	* @param scopes the scopes
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByScope(int[] scopes)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where roleId = &#63;.
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission[] findByRoleId_PrevAndNext(
		long resourcePermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions where roleId = &#63; from the database.
	*
	* @param roleId the role ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_P(
		long companyId, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_P(
		long companyId, java.lang.String primKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_P(
		long companyId, java.lang.String primKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_P_First(
		long companyId, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByC_P_First(
		long companyId, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_P_Last(
		long companyId, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByC_P_Last(
		long companyId, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission[] findByC_P_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions where companyId = &#63; and primKey LIKE &#63; from the database.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_P(long companyId, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_P(long companyId, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_N_S_First(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByC_N_S_First(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_N_S_Last(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByC_N_S_Last(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission[] findByC_N_S_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_N_S(long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N_S(long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_N_S_P_First(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_First(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_N_S_P_Last(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_Last(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission[] findByC_N_S_P_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_N_S_P(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N_S_P(long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleIds the role IDs
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleIds the role IDs
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleIds the role IDs
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @return the matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @return the resource permission that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission removeByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleIds the role IDs
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long[] roleIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the resource permission in the entity cache if it is enabled.
	*
	* @param resourcePermission the resource permission
	*/
	public void cacheResult(
		com.liferay.portal.model.ResourcePermission resourcePermission);

	/**
	* Caches the resource permissions in the entity cache if it is enabled.
	*
	* @param resourcePermissions the resource permissions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.ResourcePermission> resourcePermissions);

	/**
	* Creates a new resource permission with the primary key. Does not add the resource permission to the database.
	*
	* @param resourcePermissionId the primary key for the new resource permission
	* @return the new resource permission
	*/
	public com.liferay.portal.model.ResourcePermission create(
		long resourcePermissionId);

	/**
	* Removes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission that was removed
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission remove(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourcePermission updateImpl(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission findByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the resource permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission, or <code>null</code> if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission fetchByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the resource permissions.
	*
	* @return the resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the resource permissions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of resource permissions.
	*
	* @return the number of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}