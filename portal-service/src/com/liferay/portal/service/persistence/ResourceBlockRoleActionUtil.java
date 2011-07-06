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
import com.liferay.portal.model.ResourceBlockRoleAction;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the resource block role action service. This utility wraps {@link ResourceBlockRoleActionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockRoleActionPersistence
 * @see ResourceBlockRoleActionPersistenceImpl
 * @generated
 */
public class ResourceBlockRoleActionUtil {
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
		ResourceBlockRoleAction resourceBlockRoleAction) {
		getPersistence().clearCache(resourceBlockRoleAction);
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
	public static List<ResourceBlockRoleAction> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ResourceBlockRoleAction> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ResourceBlockRoleAction> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ResourceBlockRoleAction remove(
		ResourceBlockRoleAction resourceBlockRoleAction)
		throws SystemException {
		return getPersistence().remove(resourceBlockRoleAction);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ResourceBlockRoleAction update(
		ResourceBlockRoleAction resourceBlockRoleAction, boolean merge)
		throws SystemException {
		return getPersistence().update(resourceBlockRoleAction, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static ResourceBlockRoleAction update(
		ResourceBlockRoleAction resourceBlockRoleAction, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(resourceBlockRoleAction, merge, serviceContext);
	}

	/**
	* Caches the resource block role action in the entity cache if it is enabled.
	*
	* @param resourceBlockRoleAction the resource block role action
	*/
	public static void cacheResult(
		com.liferay.portal.model.ResourceBlockRoleAction resourceBlockRoleAction) {
		getPersistence().cacheResult(resourceBlockRoleAction);
	}

	/**
	* Caches the resource block role actions in the entity cache if it is enabled.
	*
	* @param resourceBlockRoleActions the resource block role actions
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceBlockRoleAction> resourceBlockRoleActions) {
		getPersistence().cacheResult(resourceBlockRoleActions);
	}

	/**
	* Creates a new resource block role action with the primary key. Does not add the resource block role action to the database.
	*
	* @param resourceBlockRoleActionPK the primary key for the new resource block role action
	* @return the new resource block role action
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction create(
		ResourceBlockRoleActionPK resourceBlockRoleActionPK) {
		return getPersistence().create(resourceBlockRoleActionPK);
	}

	/**
	* Removes the resource block role action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourceBlockRoleActionPK the primary key of the resource block role action
	* @return the resource block role action that was removed
	* @throws com.liferay.portal.NoSuchResourceBlockRoleActionException if a resource block role action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction remove(
		ResourceBlockRoleActionPK resourceBlockRoleActionPK)
		throws com.liferay.portal.NoSuchResourceBlockRoleActionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(resourceBlockRoleActionPK);
	}

	public static com.liferay.portal.model.ResourceBlockRoleAction updateImpl(
		com.liferay.portal.model.ResourceBlockRoleAction resourceBlockRoleAction,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(resourceBlockRoleAction, merge);
	}

	/**
	* Returns the resource block role action with the primary key or throws a {@link com.liferay.portal.NoSuchResourceBlockRoleActionException} if it could not be found.
	*
	* @param resourceBlockRoleActionPK the primary key of the resource block role action
	* @return the resource block role action
	* @throws com.liferay.portal.NoSuchResourceBlockRoleActionException if a resource block role action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction findByPrimaryKey(
		ResourceBlockRoleActionPK resourceBlockRoleActionPK)
		throws com.liferay.portal.NoSuchResourceBlockRoleActionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(resourceBlockRoleActionPK);
	}

	/**
	* Returns the resource block role action with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourceBlockRoleActionPK the primary key of the resource block role action
	* @return the resource block role action, or <code>null</code> if a resource block role action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction fetchByPrimaryKey(
		ResourceBlockRoleActionPK resourceBlockRoleActionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(resourceBlockRoleActionPK);
	}

	/**
	* Returns all the resource block role actions where roleId = &#63; and actionId = &#63;.
	*
	* @param roleId the role ID
	* @param actionId the action ID
	* @return the matching resource block role actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockRoleAction> findByR_A(
		long roleId, long actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_A(roleId, actionId);
	}

	/**
	* Returns a range of all the resource block role actions where roleId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param actionId the action ID
	* @param start the lower bound of the range of resource block role actions
	* @param end the upper bound of the range of resource block role actions (not inclusive)
	* @return the range of matching resource block role actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockRoleAction> findByR_A(
		long roleId, long actionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_A(roleId, actionId, start, end);
	}

	/**
	* Returns an ordered range of all the resource block role actions where roleId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param actionId the action ID
	* @param start the lower bound of the range of resource block role actions
	* @param end the upper bound of the range of resource block role actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource block role actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockRoleAction> findByR_A(
		long roleId, long actionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_A(roleId, actionId, start, end, orderByComparator);
	}

	/**
	* Returns the first resource block role action in the ordered set where roleId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param actionId the action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource block role action
	* @throws com.liferay.portal.NoSuchResourceBlockRoleActionException if a matching resource block role action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction findByR_A_First(
		long roleId, long actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockRoleActionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_A_First(roleId, actionId, orderByComparator);
	}

	/**
	* Returns the last resource block role action in the ordered set where roleId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID
	* @param actionId the action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource block role action
	* @throws com.liferay.portal.NoSuchResourceBlockRoleActionException if a matching resource block role action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction findByR_A_Last(
		long roleId, long actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockRoleActionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_A_Last(roleId, actionId, orderByComparator);
	}

	/**
	* Returns the resource block role actions before and after the current resource block role action in the ordered set where roleId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourceBlockRoleActionPK the primary key of the current resource block role action
	* @param roleId the role ID
	* @param actionId the action ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource block role action
	* @throws com.liferay.portal.NoSuchResourceBlockRoleActionException if a resource block role action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourceBlockRoleAction[] findByR_A_PrevAndNext(
		ResourceBlockRoleActionPK resourceBlockRoleActionPK, long roleId,
		long actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceBlockRoleActionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_A_PrevAndNext(resourceBlockRoleActionPK, roleId,
			actionId, orderByComparator);
	}

	/**
	* Returns all the resource block role actions.
	*
	* @return the resource block role actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockRoleAction> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the resource block role actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource block role actions
	* @param end the upper bound of the range of resource block role actions (not inclusive)
	* @return the range of resource block role actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockRoleAction> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the resource block role actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource block role actions
	* @param end the upper bound of the range of resource block role actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource block role actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourceBlockRoleAction> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the resource block role actions where roleId = &#63; and actionId = &#63; from the database.
	*
	* @param roleId the role ID
	* @param actionId the action ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByR_A(long roleId, long actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByR_A(roleId, actionId);
	}

	/**
	* Removes all the resource block role actions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of resource block role actions where roleId = &#63; and actionId = &#63;.
	*
	* @param roleId the role ID
	* @param actionId the action ID
	* @return the number of matching resource block role actions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByR_A(long roleId, long actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_A(roleId, actionId);
	}

	/**
	* Returns the number of resource block role actions.
	*
	* @return the number of resource block role actions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ResourceBlockRoleActionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ResourceBlockRoleActionPersistence)PortalBeanLocatorUtil.locate(ResourceBlockRoleActionPersistence.class.getName());

			ReferenceRegistry.registerReference(ResourceBlockRoleActionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(ResourceBlockRoleActionPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(ResourceBlockRoleActionUtil.class,
			"_persistence");
	}

	private static ResourceBlockRoleActionPersistence _persistence;
}