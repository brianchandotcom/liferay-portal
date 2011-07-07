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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
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
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ResourceBlockPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ResourceBlockPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ResourceBlockPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ResourceBlockPermission remove(
		ResourceBlockPermission resourceBlockPermission)
		throws SystemException {
		return getPersistence().remove(resourceBlockPermission);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ResourceBlockPermission update(
		ResourceBlockPermission resourceBlockPermission, boolean merge)
		throws SystemException {
		return getPersistence().update(resourceBlockPermission, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static ResourceBlockPermission update(
		ResourceBlockPermission resourceBlockPermission, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(resourceBlockPermission, merge, serviceContext);
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
	* @param resourceBlockPermissionPK the primary key for the new resource block permission
	* @return the new resource block permission
	*/
	public static com.liferay.portal.model.ResourceBlockPermission create(
		com.liferay.portal.service.persistence.ResourceBlockPermissionPK resourceBlockPermissionPK) {
		return getPersistence().create(resourceBlockPermissionPK);
	}

	/**
	* Removes the resource block permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockPermissionPK the primary key of the resource block permission
	* @return the resource block permission that was removed
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission remove(
		com.liferay.portal.service.persistence.ResourceBlockPermissionPK resourceBlockPermissionPK)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(resourceBlockPermissionPK);
	}

	public static com.liferay.portal.model.ResourceBlockPermission updateImpl(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(resourceBlockPermission, merge);
	}

	/**
	* Returns the resource block permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourceBlockPermissionException} if it could not be found.
	*
	* @param resourceBlockPermissionPK the primary key of the resource block permission
	* @return the resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission findByPrimaryKey(
		com.liferay.portal.service.persistence.ResourceBlockPermissionPK resourceBlockPermissionPK)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(resourceBlockPermissionPK);
	}

	/**
	* Returns the resource block permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceBlockPermissionPK the primary key of the resource block permission
	* @return the resource block permission, or <code>null</code> if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission fetchByPrimaryKey(
		com.liferay.portal.service.persistence.ResourceBlockPermissionPK resourceBlockPermissionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(resourceBlockPermissionPK);
	}

	/**
	* Returns all the resource block permissions where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByResourceBlockId(resourceBlockId);
	}

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
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceBlockId(resourceBlockId, start, end);
	}

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
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByResourceBlockId(
		long resourceBlockId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceBlockId(resourceBlockId, start, end,
			orderByComparator);
	}

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
	public static com.liferay.portal.model.ResourceBlockPermission findByResourceBlockId_First(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceBlockId_First(resourceBlockId,
			orderByComparator);
	}

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
	public static com.liferay.portal.model.ResourceBlockPermission findByResourceBlockId_Last(
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceBlockId_Last(resourceBlockId,
			orderByComparator);
	}

	/**
	* Returns the resource block permissions before and after the current resource block permission in the ordered set where resourceBlockId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockPermissionPK the primary key of the current resource block permission
	* @param resourceBlockId the resource block ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission[] findByResourceBlockId_PrevAndNext(
		com.liferay.portal.service.persistence.ResourceBlockPermissionPK resourceBlockPermissionPK,
		long resourceBlockId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceBlockId_PrevAndNext(resourceBlockPermissionPK,
			resourceBlockId, orderByComparator);
	}

	/**
	* Returns all the resource block permissions where roleId = &#63; and actionIds = &#63;.
	*
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @return the matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByR_A(
		long roleId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_A(roleId, actionIds);
	}

	/**
	* Returns a range of all the resource block permissions where roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @return the range of matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByR_A(
		long roleId, long actionIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_A(roleId, actionIds, start, end);
	}

	/**
	* Returns an ordered range of all the resource block permissions where roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param start the lower bound of the range of resource block permissions
	* @param end the upper bound of the range of resource block permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findByR_A(
		long roleId, long actionIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_A(roleId, actionIds, start, end, orderByComparator);
	}

	/**
	* Returns the first resource block permission in the ordered set where roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission findByR_A_First(
		long roleId, long actionIds,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_A_First(roleId, actionIds, orderByComparator);
	}

	/**
	* Returns the last resource block permission in the ordered set where roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a matching resource block permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission findByR_A_Last(
		long roleId, long actionIds,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_A_Last(roleId, actionIds, orderByComparator);
	}

	/**
	* Returns the resource block permissions before and after the current resource block permission in the ordered set where roleId = &#63; and actionIds = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockPermissionPK the primary key of the current resource block permission
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block permission
	* @throws com.liferay.portal.NoSuchResourceBlockPermissionException if a resource block permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockPermission[] findByR_A_PrevAndNext(
		com.liferay.portal.service.persistence.ResourceBlockPermissionPK resourceBlockPermissionPK,
		long roleId, long actionIds,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_A_PrevAndNext(resourceBlockPermissionPK, roleId,
			actionIds, orderByComparator);
	}

	/**
	* Returns all the resource block permissions.
	*
	* @return the resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.portal.model.ResourceBlockPermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the resource block permissions where resourceBlockId = &#63; from the database.
	*
	* @param resourceBlockId the resource block ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByResourceBlockId(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByResourceBlockId(resourceBlockId);
	}

	/**
	* Removes all the resource block permissions where roleId = &#63; and actionIds = &#63; from the database.
	*
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByR_A(long roleId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByR_A(roleId, actionIds);
	}

	/**
	* Removes all the resource block permissions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of resource block permissions where resourceBlockId = &#63;.
	*
	* @param resourceBlockId the resource block ID
	* @return the number of matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByResourceBlockId(long resourceBlockId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the number of resource block permissions where roleId = &#63; and actionIds = &#63;.
	*
	* @param roleId the role ID
	* @param actionIds the action IDs
	* @return the number of matching resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByR_A(long roleId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_A(roleId, actionIds);
	}

	/**
	* Returns the number of resource block permissions.
	*
	* @return the number of resource block permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
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

	public void setPersistence(ResourceBlockPermissionPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(ResourceBlockPermissionUtil.class,
			"_persistence");
	}

	private static ResourceBlockPermissionPersistence _persistence;
}