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

import com.liferay.portal.exception.NoSuchResourcePermissionException;
import com.liferay.portal.model.ResourcePermission;

/**
 * The persistence interface for the resource permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.ResourcePermissionPersistenceImpl
 * @see ResourcePermissionUtil
 * @generated
 */
@ProviderType
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
	*/
	public java.util.List<ResourcePermission> findByScope(int scope);

	/**
	* Returns a range of all the resource permissions where scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByScope(int scope, int start,
		int end);

	/**
	* Returns an ordered range of all the resource permissions where scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByScope(int scope, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource permissions where scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByScope(int scope, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource permission in the ordered set where scope = &#63;.
	*
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByScope_First(int scope,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the first resource permission in the ordered set where scope = &#63;.
	*
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByScope_First(int scope,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the last resource permission in the ordered set where scope = &#63;.
	*
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByScope_Last(int scope,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the last resource permission in the ordered set where scope = &#63;.
	*
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByScope_Last(int scope,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where scope = &#63;.
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	*/
	public ResourcePermission[] findByScope_PrevAndNext(
		long resourcePermissionId, int scope,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns all the resource permissions where scope = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scopes the scopes
	* @return the matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByScope(int[] scopes);

	/**
	* Returns a range of all the resource permissions where scope = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scopes the scopes
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByScope(int[] scopes,
		int start, int end);

	/**
	* Returns an ordered range of all the resource permissions where scope = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scopes the scopes
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByScope(int[] scopes,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource permissions where scope = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByScope(int[] scopes,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the resource permissions where scope = &#63; from the database.
	*
	* @param scope the scope
	*/
	public void removeByScope(int scope);

	/**
	* Returns the number of resource permissions where scope = &#63;.
	*
	* @param scope the scope
	* @return the number of matching resource permissions
	*/
	public int countByScope(int scope);

	/**
	* Returns the number of resource permissions where scope = any &#63;.
	*
	* @param scopes the scopes
	* @return the number of matching resource permissions
	*/
	public int countByScope(int[] scopes);

	/**
	* Returns all the resource permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByRoleId(long roleId);

	/**
	* Returns a range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByRoleId(long roleId,
		int start, int end);

	/**
	* Returns an ordered range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByRoleId(long roleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByRoleId(long roleId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByRoleId_First(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the first resource permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByRoleId_First(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the last resource permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByRoleId_Last(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the last resource permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByRoleId_Last(long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where roleId = &#63;.
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	*/
	public ResourcePermission[] findByRoleId_PrevAndNext(
		long resourcePermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Removes all the resource permissions where roleId = &#63; from the database.
	*
	* @param roleId the role ID
	*/
	public void removeByRoleId(long roleId);

	/**
	* Returns the number of resource permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the number of matching resource permissions
	*/
	public int countByRoleId(long roleId);

	/**
	* Returns all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @return the matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_LikeP(long companyId,
		java.lang.String primKey);

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_LikeP(long companyId,
		java.lang.String primKey, int start, int end);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_LikeP(long companyId,
		java.lang.String primKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_LikeP(long companyId,
		java.lang.String primKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByC_LikeP_First(long companyId,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByC_LikeP_First(long companyId,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByC_LikeP_Last(long companyId,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByC_LikeP_Last(long companyId,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	*/
	public ResourcePermission[] findByC_LikeP_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Removes all the resource permissions where companyId = &#63; and primKey LIKE &#63; from the database.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	*/
	public void removeByC_LikeP(long companyId, java.lang.String primKey);

	/**
	* Returns the number of resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @return the number of matching resource permissions
	*/
	public int countByC_LikeP(long companyId, java.lang.String primKey);

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @return the matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S(long companyId,
		java.lang.String name, int scope);

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S(long companyId,
		java.lang.String name, int scope, int start, int end);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S(long companyId,
		java.lang.String name, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S(long companyId,
		java.lang.String name, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByC_N_S_First(long companyId,
		java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByC_N_S_First(long companyId,
		java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByC_N_S_Last(long companyId,
		java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByC_N_S_Last(long companyId,
		java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	*/
	public ResourcePermission[] findByC_N_S_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	*/
	public void removeByC_N_S(long companyId, java.lang.String name, int scope);

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @return the number of matching resource permissions
	*/
	public int countByC_N_S(long companyId, java.lang.String name, int scope);

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @return the matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P(long companyId,
		java.lang.String name, int scope, java.lang.String primKey);

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P(long companyId,
		java.lang.String name, int scope, java.lang.String primKey, int start,
		int end);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P(long companyId,
		java.lang.String name, int scope, java.lang.String primKey, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P(long companyId,
		java.lang.String name, int scope, java.lang.String primKey, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByC_N_S_P_First(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByC_N_S_P_First(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByC_N_S_P_Last(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByC_N_S_P_Last(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

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
	* @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	*/
	public ResourcePermission[] findByC_N_S_P_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	*/
	public void removeByC_N_S_P(long companyId, java.lang.String name,
		int scope, java.lang.String primKey);

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @return the number of matching resource permissions
	*/
	public int countByC_N_S_P(long companyId, java.lang.String name, int scope,
		java.lang.String primKey);

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleIds the role IDs
	* @return the matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long[] roleIds);

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long[] roleIds, int start, int end);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long[] roleIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long[] roleIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or throws a {@link NoSuchResourcePermissionException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @return the matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByC_N_S_P_R(long companyId,
		java.lang.String name, int scope, java.lang.String primKey, long roleId)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByC_N_S_P_R(long companyId,
		java.lang.String name, int scope, java.lang.String primKey, long roleId);

	/**
	* Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByC_N_S_P_R(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, boolean retrieveFromCache);

	/**
	* Removes the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @return the resource permission that was removed
	*/
	public ResourcePermission removeByC_N_S_P_R(long companyId,
		java.lang.String name, int scope, java.lang.String primKey, long roleId)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @return the number of matching resource permissions
	*/
	public int countByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId);

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleIds the role IDs
	* @return the number of matching resource permissions
	*/
	public int countByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long[] roleIds);

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @return the matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, java.lang.String name, int scope, long primKeyId,
		long roleId, boolean viewActionId);

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, java.lang.String name, int scope, long primKeyId,
		long roleId, boolean viewActionId, int start, int end);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, java.lang.String name, int scope, long primKeyId,
		long roleId, boolean viewActionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, java.lang.String name, int scope, long primKeyId,
		long roleId, boolean viewActionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByC_N_S_P_R_V_First(long companyId,
		java.lang.String name, int scope, long primKeyId, long roleId,
		boolean viewActionId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByC_N_S_P_R_V_First(long companyId,
		java.lang.String name, int scope, long primKeyId, long roleId,
		boolean viewActionId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws NoSuchResourcePermissionException if a matching resource permission could not be found
	*/
	public ResourcePermission findByC_N_S_P_R_V_Last(long companyId,
		java.lang.String name, int scope, long primKeyId, long roleId,
		boolean viewActionId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission, or <code>null</code> if a matching resource permission could not be found
	*/
	public ResourcePermission fetchByC_N_S_P_R_V_Last(long companyId,
		java.lang.String name, int scope, long primKeyId, long roleId,
		boolean viewActionId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	*/
	public ResourcePermission[] findByC_N_S_P_R_V_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope, long primKeyId, long roleId, boolean viewActionId,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator)
		throws NoSuchResourcePermissionException;

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = any &#63; and viewActionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleIds the role IDs
	* @param viewActionId the view action ID
	* @return the matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, java.lang.String name, int scope, long primKeyId,
		long[] roleIds, boolean viewActionId);

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = any &#63; and viewActionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleIds the role IDs
	* @param viewActionId the view action ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, java.lang.String name, int scope, long primKeyId,
		long[] roleIds, boolean viewActionId, int start, int end);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = any &#63; and viewActionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleIds the role IDs
	* @param viewActionId the view action ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, java.lang.String name, int scope, long primKeyId,
		long[] roleIds, boolean viewActionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching resource permissions
	*/
	public java.util.List<ResourcePermission> findByC_N_S_P_R_V(
		long companyId, java.lang.String name, int scope, long primKeyId,
		long[] roleIds, boolean viewActionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	*/
	public void removeByC_N_S_P_R_V(long companyId, java.lang.String name,
		int scope, long primKeyId, long roleId, boolean viewActionId);

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = &#63; and viewActionId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleId the role ID
	* @param viewActionId the view action ID
	* @return the number of matching resource permissions
	*/
	public int countByC_N_S_P_R_V(long companyId, java.lang.String name,
		int scope, long primKeyId, long roleId, boolean viewActionId);

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKeyId = &#63; and roleId = any &#63; and viewActionId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKeyId the prim key ID
	* @param roleIds the role IDs
	* @param viewActionId the view action ID
	* @return the number of matching resource permissions
	*/
	public int countByC_N_S_P_R_V(long companyId, java.lang.String name,
		int scope, long primKeyId, long[] roleIds, boolean viewActionId);

	/**
	* Caches the resource permission in the entity cache if it is enabled.
	*
	* @param resourcePermission the resource permission
	*/
	public void cacheResult(ResourcePermission resourcePermission);

	/**
	* Caches the resource permissions in the entity cache if it is enabled.
	*
	* @param resourcePermissions the resource permissions
	*/
	public void cacheResult(
		java.util.List<ResourcePermission> resourcePermissions);

	/**
	* Creates a new resource permission with the primary key. Does not add the resource permission to the database.
	*
	* @param resourcePermissionId the primary key for the new resource permission
	* @return the new resource permission
	*/
	public ResourcePermission create(long resourcePermissionId);

	/**
	* Removes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission that was removed
	* @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	*/
	public ResourcePermission remove(long resourcePermissionId)
		throws NoSuchResourcePermissionException;

	public ResourcePermission updateImpl(ResourcePermission resourcePermission);

	/**
	* Returns the resource permission with the primary key or throws a {@link NoSuchResourcePermissionException} if it could not be found.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission
	* @throws NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	*/
	public ResourcePermission findByPrimaryKey(long resourcePermissionId)
		throws NoSuchResourcePermissionException;

	/**
	* Returns the resource permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission, or <code>null</code> if a resource permission with the primary key could not be found
	*/
	public ResourcePermission fetchByPrimaryKey(long resourcePermissionId);

	@Override
	public java.util.Map<java.io.Serializable, ResourcePermission> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the resource permissions.
	*
	* @return the resource permissions
	*/
	public java.util.List<ResourcePermission> findAll();

	/**
	* Returns a range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of resource permissions
	*/
	public java.util.List<ResourcePermission> findAll(int start, int end);

	/**
	* Returns an ordered range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource permissions
	*/
	public java.util.List<ResourcePermission> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator);

	/**
	* Returns an ordered range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ResourcePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of resource permissions
	*/
	public java.util.List<ResourcePermission> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ResourcePermission> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the resource permissions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of resource permissions.
	*
	* @return the number of resource permissions
	*/
	public int countAll();
}