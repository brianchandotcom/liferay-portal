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
import com.liferay.portal.model.ResourceBlockPermission;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the resource block permission service. This utility wraps {@link ResourceBlockPermissionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermissionPersistence
 * @see ResourceBlockPermissionPersistenceImpl
 * @generated
 */
@ProviderType
public class ResourceBlockPermissionUtil {
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
	public static void clearCache(
		ResourceBlockPermission resourceBlockPermission) {
		getPersistence().clearCache(resourceBlockPermission);
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
	public static List<ResourceBlockPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ResourceBlockPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ResourceBlockPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ResourceBlockPermission update(
		ResourceBlockPermission resourceBlockPermission) {
		return getPersistence().update(resourceBlockPermission);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ResourceBlockPermission update(
		ResourceBlockPermission resourceBlockPermission,
		ServiceContext serviceContext) {
		return getPersistence().update(resourceBlockPermission, serviceContext);
	}

	/**
	* Returns all the resource block permissions where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the matching resource block permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId) {
		return getPersistence().findByResourceBlockId(resourceBlockId);
	}

	/**
	* Returns a range of all the resource block permissions where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of matching resource block permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end) {
		return getPersistence()
				   .findByResourceBlockId(resourceBlockId, start, end);
	}

	/**
	* Returns an ordered range of all the resource block permissions where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param resourceBlockId the resource block ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource block permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByResourceBlockId(resourceBlockId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission findByResourceBlockId_First(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException {
		return getPersistence()
				   .findByResourceBlockId_First(resourceBlockId,
			orderByComparator);
	}

	/**
	* Returns the first resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission fetchByResourceBlockId_First(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByResourceBlockId_First(resourceBlockId,
			orderByComparator);
	}

	/**
	* Returns the last resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission findByResourceBlockId_Last(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException {
		return getPersistence()
				   .findByResourceBlockId_Last(resourceBlockId,
			orderByComparator);
	}

	/**
	* Returns the last resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission fetchByResourceBlockId_Last(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByResourceBlockId_Last(resourceBlockId,
			orderByComparator);
	}

	/**
	* Returns the resource block permissions before and after the current resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* @param resourceBlockPermissionId the primary key of the current resource block permission
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission[] findByResourceBlockId_PrevAndNext(
		long resourceBlockPermissionId, long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException {
		return getPersistence()
				   .findByResourceBlockId_PrevAndNext(resourceBlockPermissionId,
			resourceBlockId, orderByComparator);
	}

	/**
	* Removes all the resource block permissions where resourceBlockId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	*/
	public static void removeByResourceBlockId(long resourceBlockId) {
		getPersistence().removeByResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the number of resource block permissions where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the number of matching resource block permissions
	*/
	public static int countByResourceBlockId(long resourceBlockId) {
		return getPersistence().countByResourceBlockId(resourceBlockId);
	}

	/**
	* Returns all the resource block permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the matching resource block permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByRoleId(
		long roleId) {
		return getPersistence().findByRoleId(roleId);
	}

	/**
	* Returns a range of all the resource block permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of matching resource block permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByRoleId(
		long roleId, int start, int end) {
		return getPersistence().findByRoleId(roleId, start, end);
	}

	/**
	* Returns an ordered range of all the resource block permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource block permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByRoleId(roleId, start, end, orderByComparator);
	}

	/**
	* Returns the first resource block permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission findByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException {
		return getPersistence().findByRoleId_First(roleId, orderByComparator);
	}

	/**
	* Returns the first resource block permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission fetchByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByRoleId_First(roleId, orderByComparator);
	}

	/**
	* Returns the last resource block permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission findByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException {
		return getPersistence().findByRoleId_Last(roleId, orderByComparator);
	}

	/**
	* Returns the last resource block permission in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission fetchByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByRoleId_Last(roleId, orderByComparator);
	}

	/**
	* Returns the resource block permissions before and after the current resource block permission in the ordered set where roleId = &#63;.
	*
	* @param resourceBlockPermissionId the primary key of the current resource block permission
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission[] findByRoleId_PrevAndNext(
		long resourceBlockPermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException {
		return getPersistence()
				   .findByRoleId_PrevAndNext(resourceBlockPermissionId, roleId,
			orderByComparator);
	}

	/**
	* Removes all the resource block permissions where roleId = &#63; from the database.
	*
	* @param roleId the role ID
	*/
	public static void removeByRoleId(long roleId) {
		getPersistence().removeByRoleId(roleId);
	}

	/**
	* Returns the number of resource block permissions where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the number of matching resource block permissions
	*/
	public static int countByRoleId(long roleId) {
		return getPersistence().countByRoleId(roleId);
	}

	/**
	* Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or throws a {@link com.liferay.portal.NoSuchResourceBlockPermissionException} if it could not be found.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the matching resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission findByR_R(
		long resourceBlockId, long roleId)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException {
		return getPersistence().findByR_R(resourceBlockId, roleId);
	}

	/**
	* Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission fetchByR_R(
		long resourceBlockId, long roleId) {
		return getPersistence().fetchByR_R(resourceBlockId, roleId);
	}

	/**
	* Returns the resource block permission where resourceBlockId = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching resource block permission, or <code>null</code> if a matching resource block permission could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission fetchByR_R(
		long resourceBlockId, long roleId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByR_R(resourceBlockId, roleId, retrieveFromCache);
	}

	/**
	* Removes the resource block permission where resourceBlockId = &#63; and roleId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the resource block permission that was removed
	*/
	public static com.liferay.portal.model.ResourceBlockPermission removeByR_R(
		long resourceBlockId, long roleId)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException {
		return getPersistence().removeByR_R(resourceBlockId, roleId);
	}

	/**
	* Returns the number of resource block permissions where resourceBlockId = &#63; and roleId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @param roleId the role ID
	* @return the number of matching resource block permissions
	*/
	public static int countByR_R(long resourceBlockId, long roleId) {
		return getPersistence().countByR_R(resourceBlockId, roleId);
	}

	/**
	* Caches the resource block permission in the entity cache if it is enabled.
	*
	* @param resourceBlockPermission the resource block permission
	*/
	public static void cacheResult(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission) {
		getPersistence().cacheResult(resourceBlockPermission);
	}

	/**
	* Caches the resource block permissions in the entity cache if it is enabled.
	*
	* @param resourceBlockPermissions the resource block permissions
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceBlockPermission> resourceBlockPermissions) {
		getPersistence().cacheResult(resourceBlockPermissions);
	}

	/**
	* Creates a new resource block permission with the primary key. Does not add the resource block permission to the database.
	*
	* @param resourceBlockPermissionId the primary key for the new resource block permission
	* @return the new resource block permission
	*/
	public static com.liferay.portal.model.ResourceBlockPermission create(
		long resourceBlockPermissionId) {
		return getPersistence().create(resourceBlockPermissionId);
	}

	/**
	* Removes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission that was removed
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission remove(
		long resourceBlockPermissionId)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException {
		return getPersistence().remove(resourceBlockPermissionId);
	}

	public static com.liferay.portal.model.ResourceBlockPermission updateImpl(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission) {
		return getPersistence().updateImpl(resourceBlockPermission);
	}

	/**
	* Returns the resource block permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourceBlockPermissionException} if it could not be found.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission findByPrimaryKey(
		long resourceBlockPermissionId)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException {
		return getPersistence().findByPrimaryKey(resourceBlockPermissionId);
	}

	/**
	* Returns the resource block permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceBlockPermissionId the primary key of the resource block permission
	* @return the resource block permission, or <code>null</code> if a resource block permission with the primary key could not be found
	*/
	public static com.liferay.portal.model.ResourceBlockPermission fetchByPrimaryKey(
		long resourceBlockPermissionId) {
		return getPersistence().fetchByPrimaryKey(resourceBlockPermissionId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.ResourceBlockPermission> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the resource block permissions.
	*
	* @return the resource block permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the resource block permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of resource block permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the resource block permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ResourceBlockPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource block permissions
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the resource block permissions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of resource block permissions.
	*
	* @return the number of resource block permissions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ResourceBlockPermissionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ResourceBlockPermissionPersistence)PortalBeanLocatorUtil.locate(ResourceBlockPermissionPersistence.class.getName());

			ReferenceRegistry.registerReference(ResourceBlockPermissionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(ResourceBlockPermissionPersistence persistence) {
	}

	private static ResourceBlockPermissionPersistence _persistence;
}