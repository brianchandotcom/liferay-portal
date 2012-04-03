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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the resource permission service. This utility wraps {@link ResourcePermissionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionPersistence
 * @see ResourcePermissionPersistenceImpl
 * @generated
 */
public class ResourcePermissionUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(ResourcePermission resourcePermission) {
		getPersistence().clearCache(resourcePermission);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ResourcePermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ResourcePermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ResourcePermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ResourcePermission update(
		ResourcePermission resourcePermission, boolean merge)
		throws SystemException {
		return getPersistence().update(resourcePermission, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static ResourcePermission update(
		ResourcePermission resourcePermission, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(resourcePermission, merge, serviceContext);
	}

	/**
	* Caches the resource permission in the entity cache if it is enabled.
	*
	* @param resourcePermission the resource permission
	*/
	public static void cacheResult(
		com.liferay.portal.model.ResourcePermission resourcePermission) {
		getPersistence().cacheResult(resourcePermission);
	}

	/**
	* Caches the resource permissions in the entity cache if it is enabled.
	*
	* @param resourcePermissions the resource permissions
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ResourcePermission> resourcePermissions) {
		getPersistence().cacheResult(resourcePermissions);
	}

	/**
	* Creates a new resource permission with the primary key. Does not add the resource permission to the database.
	*
	* @param resourcePermissionId the primary key for the new resource permission
	* @return the new resource permission
	*/
	public static com.liferay.portal.model.ResourcePermission create(
		long resourcePermissionId) {
		return getPersistence().create(resourcePermissionId);
	}

	/**
	* Removes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission that was removed
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission remove(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(resourcePermissionId);
	}

	public static com.liferay.portal.model.ResourcePermission updateImpl(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(resourcePermission, merge);
	}

	/**
	* Returns the resource permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(resourcePermissionId);
	}

	/**
	* Returns the resource permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePermissionId the primary key of the resource permission
	* @return the resource permission, or <code>null</code> if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission fetchByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(resourcePermissionId);
	}

	/**
	* Returns all the resource permissions where scope = &#63;.
	*
	* @param scope the scope
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int scope) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByScope(scope);
	}

	/**
	* Returns a range of all the resource permissions where scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int scope, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByScope(scope, start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param scope the scope
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByScope(scope, start, end, orderByComparator);
	}

	/**
	* Returns the first resource permission in the ordered set where scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByScope_First(
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByScope_First(scope, orderByComparator);
	}

	/**
	* Returns the last resource permission in the ordered set where scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByScope_Last(
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByScope_Last(scope, orderByComparator);
	}

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission[] findByScope_PrevAndNext(
		long resourcePermissionId, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByScope_PrevAndNext(resourcePermissionId, scope,
			orderByComparator);
	}

	/**
	* Returns all the resource permissions where scope = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param scopes the scopes
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int[] scopes)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByScope(scopes);
	}

	/**
	* Returns a range of all the resource permissions where scope = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param scopes the scopes
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int[] scopes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByScope(scopes, start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where scope = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param scopes the scopes
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByScope(
		int[] scopes, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByScope(scopes, start, end, orderByComparator);
	}

	/**
	* Returns all the resource permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId(roleId);
	}

	/**
	* Returns a range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId(roleId, start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRoleId(roleId, start, end, orderByComparator);
	}

	/**
	* Returns the first resource permission in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId_First(roleId, orderByComparator);
	}

	/**
	* Returns the last resource permission in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId_Last(roleId, orderByComparator);
	}

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission[] findByRoleId_PrevAndNext(
		long resourcePermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRoleId_PrevAndNext(resourcePermissionId, roleId,
			orderByComparator);
	}

	/**
	* Returns all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_P(
		long companyId, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_P(companyId, primKey);
	}

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_P(
		long companyId, java.lang.String primKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_P(companyId, primKey, start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_P(
		long companyId, java.lang.String primKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P(companyId, primKey, start, end, orderByComparator);
	}

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_P_First(
		long companyId, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P_First(companyId, primKey, orderByComparator);
	}

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_P_Last(
		long companyId, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P_Last(companyId, primKey, orderByComparator);
	}

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and primKey LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param primKey the prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission[] findByC_P_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P_PrevAndNext(resourcePermissionId, companyId,
			primKey, orderByComparator);
	}

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_S(companyId, name, scope);
	}

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_S(companyId, name, scope, start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S(companyId, name, scope, start, end,
			orderByComparator);
	}

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_First(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_First(companyId, name, scope, orderByComparator);
	}

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_Last(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_Last(companyId, name, scope, orderByComparator);
	}

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
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
	public static com.liferay.portal.model.ResourcePermission[] findByC_N_S_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_PrevAndNext(resourcePermissionId, companyId,
			name, scope, orderByComparator);
	}

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
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_S_P(companyId, name, scope, primKey);
	}

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P(companyId, name, scope, primKey, start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P(companyId, name, scope, primKey, start, end,
			orderByComparator);
	}

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
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
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_First(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_First(companyId, name, scope, primKey,
			orderByComparator);
	}

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
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
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_Last(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_Last(companyId, name, scope, primKey,
			orderByComparator);
	}

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
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
	public static com.liferay.portal.model.ResourcePermission[] findByC_N_S_P_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_PrevAndNext(resourcePermissionId, companyId,
			name, scope, primKey, orderByComparator);
	}

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and primKey = &#63; and ownerId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param ownerId the owner ID
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_P_O(
		long companyId, java.lang.String name, java.lang.String primKey,
		long ownerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_P_O(companyId, name, primKey, ownerId);
	}

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and primKey = &#63; and ownerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param ownerId the owner ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_P_O(
		long companyId, java.lang.String name, java.lang.String primKey,
		long ownerId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_O(companyId, name, primKey, ownerId, start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and primKey = &#63; and ownerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param ownerId the owner ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_P_O(
		long companyId, java.lang.String name, java.lang.String primKey,
		long ownerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_O(companyId, name, primKey, ownerId, start,
			end, orderByComparator);
	}

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and primKey = &#63; and ownerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param ownerId the owner ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_P_O_First(
		long companyId, java.lang.String name, java.lang.String primKey,
		long ownerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_O_First(companyId, name, primKey, ownerId,
			orderByComparator);
	}

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and primKey = &#63; and ownerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param ownerId the owner ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_P_O_Last(
		long companyId, java.lang.String name, java.lang.String primKey,
		long ownerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_O_Last(companyId, name, primKey, ownerId,
			orderByComparator);
	}

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and primKey = &#63; and ownerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param ownerId the owner ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission[] findByC_N_P_O_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		java.lang.String primKey, long ownerId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_O_PrevAndNext(resourcePermissionId, companyId,
			name, primKey, ownerId, orderByComparator);
	}

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R(companyId, name, scope, primKey, roleId,
			start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R(companyId, name, scope, primKey, roleId,
			start, end, orderByComparator);
	}

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_R_First(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_First(companyId, name, scope, primKey,
			roleId, orderByComparator);
	}

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_R_Last(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_Last(companyId, name, scope, primKey,
			roleId, orderByComparator);
	}

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission[] findByC_N_S_P_R_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_PrevAndNext(resourcePermissionId,
			companyId, name, scope, primKey, roleId, orderByComparator);
	}

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R(companyId, name, scope, primKey, roleIds);
	}

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R(companyId, name, scope, primKey, roleIds,
			start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R(companyId, name, scope, primKey, roleIds,
			start, end, orderByComparator);
	}

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_P_R_A(
		long companyId, java.lang.String name, java.lang.String primKey,
		long roleId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_R_A(companyId, name, primKey, roleId, actionIds);
	}

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_P_R_A(
		long companyId, java.lang.String name, java.lang.String primKey,
		long roleId, long actionIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_R_A(companyId, name, primKey, roleId,
			actionIds, start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_P_R_A(
		long companyId, java.lang.String name, java.lang.String primKey,
		long roleId, long actionIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_R_A(companyId, name, primKey, roleId,
			actionIds, start, end, orderByComparator);
	}

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_P_R_A_First(
		long companyId, java.lang.String name, java.lang.String primKey,
		long roleId, long actionIds,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_R_A_First(companyId, name, primKey, roleId,
			actionIds, orderByComparator);
	}

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_P_R_A_Last(
		long companyId, java.lang.String name, java.lang.String primKey,
		long roleId, long actionIds,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_R_A_Last(companyId, name, primKey, roleId,
			actionIds, orderByComparator);
	}

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission[] findByC_N_P_R_A_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		java.lang.String primKey, long roleId, long actionIds,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_P_R_A_PrevAndNext(resourcePermissionId,
			companyId, name, primKey, roleId, actionIds, orderByComparator);
	}

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_A(companyId, name, scope, primKey, roleId,
			actionIds);
	}

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long actionIds, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_A(companyId, name, scope, primKey, roleId,
			actionIds, start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long actionIds, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_A(companyId, name, scope, primKey, roleId,
			actionIds, start, end, orderByComparator);
	}

	/**
	* Returns the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_R_A_First(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long actionIds,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_A_First(companyId, name, scope, primKey,
			roleId, actionIds, orderByComparator);
	}

	/**
	* Returns the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_R_A_Last(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long actionIds,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_A_Last(companyId, name, scope, primKey,
			roleId, actionIds, orderByComparator);
	}

	/**
	* Returns the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission[] findByC_N_S_P_R_A_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId, long actionIds,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_A_PrevAndNext(resourcePermissionId,
			companyId, name, scope, primKey, roleId, actionIds,
			orderByComparator);
	}

	/**
	* Returns all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleIds the role IDs
	* @param actionIds the action IDs
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_A(companyId, name, scope, primKey, roleIds,
			actionIds);
	}

	/**
	* Returns a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleIds the role IDs
	* @param actionIds the action IDs
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds, long actionIds, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_A(companyId, name, scope, primKey, roleIds,
			actionIds, start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleIds the role IDs
	* @param actionIds the action IDs
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P_R_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long[] roleIds, long actionIds, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_A(companyId, name, scope, primKey, roleIds,
			actionIds, start, end, orderByComparator);
	}

	/**
	* Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and ownerId = &#63; and actionIds = &#63; or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param ownerId the owner ID
	* @param actionIds the action IDs
	* @return the matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_R_O_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long ownerId, long actionIds)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_O_A(companyId, name, scope, primKey,
			roleId, ownerId, actionIds);
	}

	/**
	* Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and ownerId = &#63; and actionIds = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param ownerId the owner ID
	* @param actionIds the action IDs
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R_O_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long ownerId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_N_S_P_R_O_A(companyId, name, scope, primKey,
			roleId, ownerId, actionIds);
	}

	/**
	* Returns the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and ownerId = &#63; and actionIds = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param ownerId the owner ID
	* @param actionIds the action IDs
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R_O_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long ownerId, long actionIds,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_N_S_P_R_O_A(companyId, name, scope, primKey,
			roleId, ownerId, actionIds, retrieveFromCache);
	}

	/**
	* Returns all the resource permissions.
	*
	* @return the resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @return the range of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions
	* @param end the upper bound of the range of resource permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the resource permissions where scope = &#63; from the database.
	*
	* @param scope the scope
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByScope(int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByScope(scope);
	}

	/**
	* Removes all the resource permissions where roleId = &#63; from the database.
	*
	* @param roleId the role ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRoleId(roleId);
	}

	/**
	* Removes all the resource permissions where companyId = &#63; and primKey LIKE &#63; from the database.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_P(long companyId, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_P(companyId, primKey);
	}

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_N_S(long companyId, java.lang.String name,
		int scope) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N_S(companyId, name, scope);
	}

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_N_S_P(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N_S_P(companyId, name, scope, primKey);
	}

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and primKey = &#63; and ownerId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param ownerId the owner ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_N_P_O(long companyId, java.lang.String name,
		java.lang.String primKey, long ownerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N_P_O(companyId, name, primKey, ownerId);
	}

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_N_P_R_A(long companyId, java.lang.String name,
		java.lang.String primKey, long roleId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByC_N_P_R_A(companyId, name, primKey, roleId, actionIds);
	}

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_N_S_P_R_A(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByC_N_S_P_R_A(companyId, name, scope, primKey, roleId,
			actionIds);
	}

	/**
	* Removes the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and ownerId = &#63; and actionIds = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param ownerId the owner ID
	* @param actionIds the action IDs
	* @return the resource permission that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission removeByC_N_S_P_R_O_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long ownerId, long actionIds)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .removeByC_N_S_P_R_O_A(companyId, name, scope, primKey,
			roleId, ownerId, actionIds);
	}

	/**
	* Removes all the resource permissions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of resource permissions where scope = &#63;.
	*
	* @param scope the scope
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByScope(int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByScope(scope);
	}

	/**
	* Returns the number of resource permissions where scope = any &#63;.
	*
	* @param scopes the scopes
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByScope(int[] scopes)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByScope(scopes);
	}

	/**
	* Returns the number of resource permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRoleId(roleId);
	}

	/**
	* Returns the number of resource permissions where companyId = &#63; and primKey LIKE &#63;.
	*
	* @param companyId the company ID
	* @param primKey the prim key
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_P(long companyId, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_P(companyId, primKey);
	}

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N_S(long companyId, java.lang.String name,
		int scope) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N_S(companyId, name, scope);
	}

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
	public static int countByC_N_S_P(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N_S_P(companyId, name, scope, primKey);
	}

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and primKey = &#63; and ownerId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param ownerId the owner ID
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N_P_O(long companyId, java.lang.String name,
		java.lang.String primKey, long ownerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N_P_O(companyId, name, primKey, ownerId);
	}

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
	public static int countByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

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
	public static int countByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long[] roleIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_N_S_P_R(companyId, name, scope, primKey, roleIds);
	}

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N_P_R_A(long companyId, java.lang.String name,
		java.lang.String primKey, long roleId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_N_P_R_A(companyId, name, primKey, roleId, actionIds);
	}

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and actionIds = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N_S_P_R_A(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_N_S_P_R_A(companyId, name, scope, primKey, roleId,
			actionIds);
	}

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = any &#63; and actionIds = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleIds the role IDs
	* @param actionIds the action IDs
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N_S_P_R_A(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long[] roleIds, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_N_S_P_R_A(companyId, name, scope, primKey,
			roleIds, actionIds);
	}

	/**
	* Returns the number of resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and ownerId = &#63; and actionIds = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param scope the scope
	* @param primKey the prim key
	* @param roleId the role ID
	* @param ownerId the owner ID
	* @param actionIds the action IDs
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N_S_P_R_O_A(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, long ownerId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_N_S_P_R_O_A(companyId, name, scope, primKey,
			roleId, ownerId, actionIds);
	}

	/**
	* Returns the number of resource permissions.
	*
	* @return the number of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ResourcePermissionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ResourcePermissionPersistence)PortalBeanLocatorUtil.locate(ResourcePermissionPersistence.class.getName());

			ReferenceRegistry.registerReference(ResourcePermissionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(ResourcePermissionPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(ResourcePermissionUtil.class,
			"_persistence");
	}

	private static ResourcePermissionPersistence _persistence;
}