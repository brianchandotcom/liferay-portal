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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the resource type permission service. This utility wraps {@link ResourceTypePermissionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermissionPersistence
 * @see ResourceTypePermissionPersistenceImpl
 * @generated
 */
@ProviderType
public class ResourceTypePermissionUtil {
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
	public static void clearCache(ResourceTypePermission resourceTypePermission) {
		getPersistence().clearCache(resourceTypePermission);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ResourceTypePermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ResourceTypePermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ResourceTypePermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ResourceTypePermission update(
		ResourceTypePermission resourceTypePermission) {
		return getPersistence().update(resourceTypePermission);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ResourceTypePermission update(
		ResourceTypePermission resourceTypePermission,
		ServiceContext serviceContext) {
		return getPersistence().update(resourceTypePermission, serviceContext);
	}

	/**
	* Returns all the resource type permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the matching resource type permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> findByRoleId(
		long roleId) {
		return getPersistence().findByRoleId(roleId);
	}

	/**
	* Returns a range of all the resource type permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @return the range of matching resource type permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> findByRoleId(
		long roleId, int start, int end) {
		return getPersistence().findByRoleId(roleId, start, end);
	}

	/**
	* Returns an ordered range of all the resource type permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource type permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByRoleId(roleId, start, end, orderByComparator);
	}

	/**
	* Returns the first resource type permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission findByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceTypePermissionException {
		return getPersistence().findByRoleId_First(roleId, orderByComparator);
	}

	/**
	* Returns the first resource type permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission fetchByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByRoleId_First(roleId, orderByComparator);
	}

	/**
	* Returns the last resource type permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission findByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceTypePermissionException {
		return getPersistence().findByRoleId_Last(roleId, orderByComparator);
	}

	/**
	* Returns the last resource type permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission fetchByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByRoleId_Last(roleId, orderByComparator);
	}

	/**
	* Returns the resource type permissions before and after the current resource type permission in the ordered set where roleId = &#63;.
	*
	* @param resourceTypePermissionId the primary key of the current resource type permission
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission[] findByRoleId_PrevAndNext(
		long resourceTypePermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceTypePermissionException {
		return getPersistence()
				   .findByRoleId_PrevAndNext(resourceTypePermissionId, roleId,
			orderByComparator);
	}

	/**
	* Removes all the resource type permissions where roleId = &#63; from the database.
	*
	* @param roleId the role ID
	*/
	public static void removeByRoleId(long roleId) {
		getPersistence().removeByRoleId(roleId);
	}

	/**
	* Returns the number of resource type permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the number of matching resource type permissions
	*/
	public static int countByRoleId(long roleId) {
		return getPersistence().countByRoleId(roleId);
	}

	/**
	* Returns all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @return the matching resource type permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> findByC_N_R(
		long companyId, java.lang.String name, long roleId) {
		return getPersistence().findByC_N_R(companyId, name, roleId);
	}

	/**
	* Returns a range of all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @return the range of matching resource type permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> findByC_N_R(
		long companyId, java.lang.String name, long roleId, int start, int end) {
		return getPersistence().findByC_N_R(companyId, name, roleId, start, end);
	}

	/**
	* Returns an ordered range of all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> findByC_N_R(
		long companyId, java.lang.String name, long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByC_N_R(companyId, name, roleId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission findByC_N_R_First(
		long companyId, java.lang.String name, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceTypePermissionException {
		return getPersistence()
				   .findByC_N_R_First(companyId, name, roleId, orderByComparator);
	}

	/**
	* Returns the first resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission fetchByC_N_R_First(
		long companyId, java.lang.String name, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByC_N_R_First(companyId, name, roleId,
			orderByComparator);
	}

	/**
	* Returns the last resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission findByC_N_R_Last(
		long companyId, java.lang.String name, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceTypePermissionException {
		return getPersistence()
				   .findByC_N_R_Last(companyId, name, roleId, orderByComparator);
	}

	/**
	* Returns the last resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission fetchByC_N_R_Last(
		long companyId, java.lang.String name, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByC_N_R_Last(companyId, name, roleId, orderByComparator);
	}

	/**
	* Returns the resource type permissions before and after the current resource type permission in the ordered set where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param resourceTypePermissionId the primary key of the current resource type permission
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission[] findByC_N_R_PrevAndNext(
		long resourceTypePermissionId, long companyId, java.lang.String name,
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceTypePermissionException {
		return getPersistence()
				   .findByC_N_R_PrevAndNext(resourceTypePermissionId,
			companyId, name, roleId, orderByComparator);
	}

	/**
	* Removes all the resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	*/
	public static void removeByC_N_R(long companyId, java.lang.String name,
		long roleId) {
		getPersistence().removeByC_N_R(companyId, name, roleId);
	}

	/**
	* Returns the number of resource type permissions where companyId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param roleId the role ID
	* @return the number of matching resource type permissions
	*/
	public static int countByC_N_R(long companyId, java.lang.String name,
		long roleId) {
		return getPersistence().countByC_N_R(companyId, name, roleId);
	}

	/**
	* Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or throws a {@link com.liferay.portal.NoSuchResourceTypePermissionException} if it could not be found.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param roleId the role ID
	* @return the matching resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission findByC_G_N_R(
		long companyId, long groupId, java.lang.String name, long roleId)
		throws com.liferay.portal.NoSuchResourceTypePermissionException {
		return getPersistence().findByC_G_N_R(companyId, groupId, name, roleId);
	}

	/**
	* Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param roleId the role ID
	* @return the matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission fetchByC_G_N_R(
		long companyId, long groupId, java.lang.String name, long roleId) {
		return getPersistence().fetchByC_G_N_R(companyId, groupId, name, roleId);
	}

	/**
	* Returns the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param roleId the role ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching resource type permission, or <code>null</code> if a matching resource type permission could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission fetchByC_G_N_R(
		long companyId, long groupId, java.lang.String name, long roleId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_G_N_R(companyId, groupId, name, roleId,
			retrieveFromCache);
	}

	/**
	* Removes the resource type permission where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param roleId the role ID
	* @return the resource type permission that was removed
	*/
	public static com.liferay.portal.model.ResourceTypePermission removeByC_G_N_R(
		long companyId, long groupId, java.lang.String name, long roleId)
		throws com.liferay.portal.NoSuchResourceTypePermissionException {
		return getPersistence().removeByC_G_N_R(companyId, groupId, name, roleId);
	}

	/**
	* Returns the number of resource type permissions where companyId = &#63; and groupId = &#63; and name = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param name the name
	* @param roleId the role ID
	* @return the number of matching resource type permissions
	*/
	public static int countByC_G_N_R(long companyId, long groupId,
		java.lang.String name, long roleId) {
		return getPersistence().countByC_G_N_R(companyId, groupId, name, roleId);
	}

	/**
	* Caches the resource type permission in the entity cache if it is enabled.
	*
	* @param resourceTypePermission the resource type permission
	*/
	public static void cacheResult(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission) {
		getPersistence().cacheResult(resourceTypePermission);
	}

	/**
	* Caches the resource type permissions in the entity cache if it is enabled.
	*
	* @param resourceTypePermissions the resource type permissions
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceTypePermission> resourceTypePermissions) {
		getPersistence().cacheResult(resourceTypePermissions);
	}

	/**
	* Creates a new resource type permission with the primary key. Does not add the resource type permission to the database.
	*
	* @param resourceTypePermissionId the primary key for the new resource type permission
	* @return the new resource type permission
	*/
	public static com.liferay.portal.model.ResourceTypePermission create(
		long resourceTypePermissionId) {
		return getPersistence().create(resourceTypePermissionId);
	}

	/**
	* Removes the resource type permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission that was removed
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission remove(
		long resourceTypePermissionId)
		throws com.liferay.portal.NoSuchResourceTypePermissionException {
		return getPersistence().remove(resourceTypePermissionId);
	}

	public static com.liferay.portal.model.ResourceTypePermission updateImpl(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission) {
		return getPersistence().updateImpl(resourceTypePermission);
	}

	/**
	* Returns the resource type permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourceTypePermissionException} if it could not be found.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission
	* @throws com.liferay.portal.NoSuchResourceTypePermissionException if a resource type permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission findByPrimaryKey(
		long resourceTypePermissionId)
		throws com.liferay.portal.NoSuchResourceTypePermissionException {
		return getPersistence().findByPrimaryKey(resourceTypePermissionId);
	}

	/**
	* Returns the resource type permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceTypePermissionId the primary key of the resource type permission
	* @return the resource type permission, or <code>null</code> if a resource type permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceTypePermission fetchByPrimaryKey(
		long resourceTypePermissionId) {
		return getPersistence().fetchByPrimaryKey(resourceTypePermissionId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.ResourceTypePermission> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the resource type permissions.
	*
	* @return the resource type permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the resource type permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @return the range of resource type permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the resource type permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceTypePermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource type permissions
	* @param end the upper bound of the range of resource type permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource type permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceTypePermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the resource type permissions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of resource type permissions.
	*
	* @return the number of resource type permissions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ResourceTypePermissionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ResourceTypePermissionPersistence)PortalBeanLocatorUtil.locate(ResourceTypePermissionPersistence.class.getName());

			ReferenceRegistry.registerReference(ResourceTypePermissionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(ResourceTypePermissionPersistence persistence) {
	}

	private static ResourceTypePermissionPersistence _persistence;
}