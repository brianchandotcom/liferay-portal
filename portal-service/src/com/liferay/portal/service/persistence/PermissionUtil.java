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
import com.liferay.portal.model.Permission;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the permission service. This utility wraps {@link PermissionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPersistence
 * @see PermissionPersistenceImpl
 * @generated
 */
public class PermissionUtil {
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
	public static void clearCache(Permission permission) {
		getPersistence().clearCache(permission);
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
	public static List<Permission> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Permission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Permission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Permission remove(Permission permission)
		throws SystemException {
		return getPersistence().remove(permission);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Permission update(Permission permission, boolean merge)
		throws SystemException {
		return getPersistence().update(permission, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Permission update(Permission permission, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(permission, merge, serviceContext);
	}

	/**
	* Caches the permission in the entity cache if it is enabled.
	*
	* @param permission the permission
	*/
	public static void cacheResult(
		com.liferay.portal.model.Permission permission) {
		getPersistence().cacheResult(permission);
	}

	/**
	* Caches the permissions in the entity cache if it is enabled.
	*
	* @param permissions the permissions
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Permission> permissions) {
		getPersistence().cacheResult(permissions);
	}

	/**
	* Creates a new permission with the primary key. Does not add the permission to the database.
	*
	* @param permissionId the primary key for the new permission
	* @return the new permission
	*/
	public static com.liferay.portal.model.Permission create(long permissionId) {
		return getPersistence().create(permissionId);
	}

	/**
	* Removes the permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param permissionId the primary key of the permission
	* @return the permission that was removed
	* @throws com.liferay.portal.NoSuchPermissionException if a permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission remove(long permissionId)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(permissionId);
	}

	public static com.liferay.portal.model.Permission updateImpl(
		com.liferay.portal.model.Permission permission, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(permission, merge);
	}

	/**
	* Returns the permission with the primary key or throws a {@link com.liferay.portal.NoSuchPermissionException} if it could not be found.
	*
	* @param permissionId the primary key of the permission
	* @return the permission
	* @throws com.liferay.portal.NoSuchPermissionException if a permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission findByPrimaryKey(
		long permissionId)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(permissionId);
	}

	/**
	* Returns the permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param permissionId the primary key of the permission
	* @return the permission, or <code>null</code> if a permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission fetchByPrimaryKey(
		long permissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(permissionId);
	}

	/**
	* Returns all the permissions where resourceId = &#63;.
	*
	* @param resourceId the resource ID
	* @return the matching permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Permission> findByResourceId(
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByResourceId(resourceId);
	}

	/**
	* Returns a range of all the permissions where resourceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceId the resource ID
	* @param start the lower bound of the range of permissions
	* @param end the upper bound of the range of permissions (not inclusive)
	* @return the range of matching permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Permission> findByResourceId(
		long resourceId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByResourceId(resourceId, start, end);
	}

	/**
	* Returns an ordered range of all the permissions where resourceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceId the resource ID
	* @param start the lower bound of the range of permissions
	* @param end the upper bound of the range of permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Permission> findByResourceId(
		long resourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceId(resourceId, start, end, orderByComparator);
	}

	/**
	* Returns the first permission in the ordered set where resourceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceId the resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching permission
	* @throws com.liferay.portal.NoSuchPermissionException if a matching permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission findByResourceId_First(
		long resourceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceId_First(resourceId, orderByComparator);
	}

	/**
	* Returns the last permission in the ordered set where resourceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceId the resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching permission
	* @throws com.liferay.portal.NoSuchPermissionException if a matching permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission findByResourceId_Last(
		long resourceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceId_Last(resourceId, orderByComparator);
	}

	/**
	* Returns the permissions before and after the current permission in the ordered set where resourceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param permissionId the primary key of the current permission
	* @param resourceId the resource ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next permission
	* @throws com.liferay.portal.NoSuchPermissionException if a permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission[] findByResourceId_PrevAndNext(
		long permissionId, long resourceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByResourceId_PrevAndNext(permissionId, resourceId,
			orderByComparator);
	}

	/**
	* Returns the permission where actionId = &#63; and resourceId = &#63; or throws a {@link com.liferay.portal.NoSuchPermissionException} if it could not be found.
	*
	* @param actionId the action ID
	* @param resourceId the resource ID
	* @return the matching permission
	* @throws com.liferay.portal.NoSuchPermissionException if a matching permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission findByA_R(
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByA_R(actionId, resourceId);
	}

	/**
	* Returns the permission where actionId = &#63; and resourceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param actionId the action ID
	* @param resourceId the resource ID
	* @return the matching permission, or <code>null</code> if a matching permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission fetchByA_R(
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByA_R(actionId, resourceId);
	}

	/**
	* Returns the permission where actionId = &#63; and resourceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param actionId the action ID
	* @param resourceId the resource ID
	* @return the matching permission, or <code>null</code> if a matching permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission fetchByA_R(
		java.lang.String actionId, long resourceId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByA_R(actionId, resourceId, retrieveFromCache);
	}

	/**
	* Returns all the permissions.
	*
	* @return the permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Permission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of permissions
	* @param end the upper bound of the range of permissions (not inclusive)
	* @return the range of permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Permission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of permissions
	* @param end the upper bound of the range of permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Permission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the permissions where resourceId = &#63; from the database.
	*
	* @param resourceId the resource ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByResourceId(long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByResourceId(resourceId);
	}

	/**
	* Removes the permission where actionId = &#63; and resourceId = &#63; from the database.
	*
	* @param actionId the action ID
	* @param resourceId the resource ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByA_R(java.lang.String actionId, long resourceId)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByA_R(actionId, resourceId);
	}

	/**
	* Removes all the permissions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of permissions where resourceId = &#63;.
	*
	* @param resourceId the resource ID
	* @return the number of matching permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByResourceId(long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByResourceId(resourceId);
	}

	/**
	* Returns the number of permissions where actionId = &#63; and resourceId = &#63;.
	*
	* @param actionId the action ID
	* @param resourceId the resource ID
	* @return the number of matching permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByA_R(java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByA_R(actionId, resourceId);
	}

	/**
	* Returns the number of permissions.
	*
	* @return the number of permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	/**
	* Returns all the groups associated with the permission.
	*
	* @param pk the primary key of the permission
	* @return the groups associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk);
	}

	/**
	* Returns a range of all the groups associated with the permission.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the permission
	* @param start the lower bound of the range of permissions
	* @param end the upper bound of the range of permissions (not inclusive)
	* @return the range of groups associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk, start, end);
	}

	/**
	* Returns an ordered range of all the groups associated with the permission.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the permission
	* @param start the lower bound of the range of permissions
	* @param end the upper bound of the range of permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of groups associated with the permission.
	*
	* @param pk the primary key of the permission
	* @return the number of groups associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static int getGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroupsSize(pk);
	}

	/**
	* Returns <code>true</code> if the group is associated with the permission.
	*
	* @param pk the primary key of the permission
	* @param groupPK the primary key of the group
	* @return <code>true</code> if the group is associated with the permission; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsGroup(pk, groupPK);
	}

	/**
	* Returns <code>true</code> if the permission has any groups associated with it.
	*
	* @param pk the primary key of the permission to check for associations with groups
	* @return <code>true</code> if the permission has any groups associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsGroups(pk);
	}

	/**
	* Adds an association between the permission and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param groupPK the primary key of the group
	* @throws SystemException if a system exception occurred
	*/
	public static void addGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroup(pk, groupPK);
	}

	/**
	* Adds an association between the permission and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param group the group
	* @throws SystemException if a system exception occurred
	*/
	public static void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroup(pk, group);
	}

	/**
	* Adds an association between the permission and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param groupPKs the primary keys of the groups
	* @throws SystemException if a system exception occurred
	*/
	public static void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroups(pk, groupPKs);
	}

	/**
	* Adds an association between the permission and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param groups the groups
	* @throws SystemException if a system exception occurred
	*/
	public static void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroups(pk, groups);
	}

	/**
	* Clears all associations between the permission and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission to clear the associated groups from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearGroups(pk);
	}

	/**
	* Removes the association between the permission and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param groupPK the primary key of the group
	* @throws SystemException if a system exception occurred
	*/
	public static void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroup(pk, groupPK);
	}

	/**
	* Removes the association between the permission and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param group the group
	* @throws SystemException if a system exception occurred
	*/
	public static void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroup(pk, group);
	}

	/**
	* Removes the association between the permission and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param groupPKs the primary keys of the groups
	* @throws SystemException if a system exception occurred
	*/
	public static void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroups(pk, groupPKs);
	}

	/**
	* Removes the association between the permission and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param groups the groups
	* @throws SystemException if a system exception occurred
	*/
	public static void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroups(pk, groups);
	}

	/**
	* Sets the groups associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param groupPKs the primary keys of the groups to be associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setGroups(pk, groupPKs);
	}

	/**
	* Sets the groups associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param groups the groups to be associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setGroups(pk, groups);
	}

	/**
	* Returns all the roles associated with the permission.
	*
	* @param pk the primary key of the permission
	* @return the roles associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getRoles(pk);
	}

	/**
	* Returns a range of all the roles associated with the permission.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the permission
	* @param start the lower bound of the range of permissions
	* @param end the upper bound of the range of permissions (not inclusive)
	* @return the range of roles associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getRoles(pk, start, end);
	}

	/**
	* Returns an ordered range of all the roles associated with the permission.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the permission
	* @param start the lower bound of the range of permissions
	* @param end the upper bound of the range of permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of roles associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getRoles(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of roles associated with the permission.
	*
	* @param pk the primary key of the permission
	* @return the number of roles associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static int getRolesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getRolesSize(pk);
	}

	/**
	* Returns <code>true</code> if the role is associated with the permission.
	*
	* @param pk the primary key of the permission
	* @param rolePK the primary key of the role
	* @return <code>true</code> if the role is associated with the permission; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsRole(pk, rolePK);
	}

	/**
	* Returns <code>true</code> if the permission has any roles associated with it.
	*
	* @param pk the primary key of the permission to check for associations with roles
	* @return <code>true</code> if the permission has any roles associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsRoles(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsRoles(pk);
	}

	/**
	* Adds an association between the permission and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param rolePK the primary key of the role
	* @throws SystemException if a system exception occurred
	*/
	public static void addRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addRole(pk, rolePK);
	}

	/**
	* Adds an association between the permission and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param role the role
	* @throws SystemException if a system exception occurred
	*/
	public static void addRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addRole(pk, role);
	}

	/**
	* Adds an association between the permission and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param rolePKs the primary keys of the roles
	* @throws SystemException if a system exception occurred
	*/
	public static void addRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addRoles(pk, rolePKs);
	}

	/**
	* Adds an association between the permission and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param roles the roles
	* @throws SystemException if a system exception occurred
	*/
	public static void addRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addRoles(pk, roles);
	}

	/**
	* Clears all associations between the permission and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission to clear the associated roles from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearRoles(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearRoles(pk);
	}

	/**
	* Removes the association between the permission and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param rolePK the primary key of the role
	* @throws SystemException if a system exception occurred
	*/
	public static void removeRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeRole(pk, rolePK);
	}

	/**
	* Removes the association between the permission and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param role the role
	* @throws SystemException if a system exception occurred
	*/
	public static void removeRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeRole(pk, role);
	}

	/**
	* Removes the association between the permission and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param rolePKs the primary keys of the roles
	* @throws SystemException if a system exception occurred
	*/
	public static void removeRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeRoles(pk, rolePKs);
	}

	/**
	* Removes the association between the permission and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param roles the roles
	* @throws SystemException if a system exception occurred
	*/
	public static void removeRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeRoles(pk, roles);
	}

	/**
	* Sets the roles associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param rolePKs the primary keys of the roles to be associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static void setRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setRoles(pk, rolePKs);
	}

	/**
	* Sets the roles associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param roles the roles to be associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static void setRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setRoles(pk, roles);
	}

	/**
	* Returns all the users associated with the permission.
	*
	* @param pk the primary key of the permission
	* @return the users associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk);
	}

	/**
	* Returns a range of all the users associated with the permission.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the permission
	* @param start the lower bound of the range of permissions
	* @param end the upper bound of the range of permissions (not inclusive)
	* @return the range of users associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk, start, end);
	}

	/**
	* Returns an ordered range of all the users associated with the permission.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the permission
	* @param start the lower bound of the range of permissions
	* @param end the upper bound of the range of permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of users associated with the permission.
	*
	* @param pk the primary key of the permission
	* @return the number of users associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static int getUsersSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	/**
	* Returns <code>true</code> if the user is associated with the permission.
	*
	* @param pk the primary key of the permission
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the permission; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsUser(pk, userPK);
	}

	/**
	* Returns <code>true</code> if the permission has any users associated with it.
	*
	* @param pk the primary key of the permission to check for associations with users
	* @return <code>true</code> if the permission has any users associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsUsers(pk);
	}

	/**
	* Adds an association between the permission and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param userPK the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public static void addUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUser(pk, userPK);
	}

	/**
	* Adds an association between the permission and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param user the user
	* @throws SystemException if a system exception occurred
	*/
	public static void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUser(pk, user);
	}

	/**
	* Adds an association between the permission and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param userPKs the primary keys of the users
	* @throws SystemException if a system exception occurred
	*/
	public static void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUsers(pk, userPKs);
	}

	/**
	* Adds an association between the permission and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param users the users
	* @throws SystemException if a system exception occurred
	*/
	public static void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUsers(pk, users);
	}

	/**
	* Clears all associations between the permission and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission to clear the associated users from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearUsers(pk);
	}

	/**
	* Removes the association between the permission and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param userPK the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public static void removeUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUser(pk, userPK);
	}

	/**
	* Removes the association between the permission and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param user the user
	* @throws SystemException if a system exception occurred
	*/
	public static void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUser(pk, user);
	}

	/**
	* Removes the association between the permission and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param userPKs the primary keys of the users
	* @throws SystemException if a system exception occurred
	*/
	public static void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUsers(pk, userPKs);
	}

	/**
	* Removes the association between the permission and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param users the users
	* @throws SystemException if a system exception occurred
	*/
	public static void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUsers(pk, users);
	}

	/**
	* Sets the users associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param userPKs the primary keys of the users to be associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setUsers(pk, userPKs);
	}

	/**
	* Sets the users associated with the permission, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the permission
	* @param users the users to be associated with the permission
	* @throws SystemException if a system exception occurred
	*/
	public static void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static PermissionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PermissionPersistence)PortalBeanLocatorUtil.locate(PermissionPersistence.class.getName());

			ReferenceRegistry.registerReference(PermissionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(PermissionPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(PermissionUtil.class, "_persistence");
	}

	private static PermissionPersistence _persistence;
}