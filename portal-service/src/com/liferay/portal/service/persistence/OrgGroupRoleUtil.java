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
import com.liferay.portal.model.OrgGroupRole;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the org group role service. This utility wraps {@link com.liferay.portal.service.persistence.impl.OrgGroupRolePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrgGroupRolePersistence
 * @see com.liferay.portal.service.persistence.impl.OrgGroupRolePersistenceImpl
 * @generated
 */
@ProviderType
public class OrgGroupRoleUtil {
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
	public static void clearCache(OrgGroupRole orgGroupRole) {
		getPersistence().clearCache(orgGroupRole);
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
	public static List<OrgGroupRole> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<OrgGroupRole> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<OrgGroupRole> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<OrgGroupRole> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static OrgGroupRole update(OrgGroupRole orgGroupRole) {
		return getPersistence().update(orgGroupRole);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static OrgGroupRole update(OrgGroupRole orgGroupRole,
		ServiceContext serviceContext) {
		return getPersistence().update(orgGroupRole, serviceContext);
	}

	/**
	* Returns all the org group roles where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching org group roles
	*/
	public static List<OrgGroupRole> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the org group roles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @return the range of matching org group roles
	*/
	public static List<OrgGroupRole> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the org group roles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching org group roles
	*/
	public static List<OrgGroupRole> findByGroupId(long groupId, int start,
		int end, OrderByComparator<OrgGroupRole> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the org group roles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching org group roles
	*/
	public static List<OrgGroupRole> findByGroupId(long groupId, int start,
		int end, OrderByComparator<OrgGroupRole> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first org group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org group role
	* @throws NoSuchOrgGroupRoleException if a matching org group role could not be found
	*/
	public static OrgGroupRole findByGroupId_First(long groupId,
		OrderByComparator<OrgGroupRole> orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first org group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org group role, or <code>null</code> if a matching org group role could not be found
	*/
	public static OrgGroupRole fetchByGroupId_First(long groupId,
		OrderByComparator<OrgGroupRole> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last org group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org group role
	* @throws NoSuchOrgGroupRoleException if a matching org group role could not be found
	*/
	public static OrgGroupRole findByGroupId_Last(long groupId,
		OrderByComparator<OrgGroupRole> orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last org group role in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org group role, or <code>null</code> if a matching org group role could not be found
	*/
	public static OrgGroupRole fetchByGroupId_Last(long groupId,
		OrderByComparator<OrgGroupRole> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the org group roles before and after the current org group role in the ordered set where groupId = &#63;.
	*
	* @param orgGroupRolePK the primary key of the current org group role
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next org group role
	* @throws NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	*/
	public static OrgGroupRole[] findByGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK,
		long groupId, OrderByComparator<OrgGroupRole> orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(orgGroupRolePK, groupId,
			orderByComparator);
	}

	/**
	* Removes all the org group roles where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of org group roles where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching org group roles
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the org group roles where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the matching org group roles
	*/
	public static List<OrgGroupRole> findByRoleId(long roleId) {
		return getPersistence().findByRoleId(roleId);
	}

	/**
	* Returns a range of all the org group roles where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @return the range of matching org group roles
	*/
	public static List<OrgGroupRole> findByRoleId(long roleId, int start,
		int end) {
		return getPersistence().findByRoleId(roleId, start, end);
	}

	/**
	* Returns an ordered range of all the org group roles where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching org group roles
	*/
	public static List<OrgGroupRole> findByRoleId(long roleId, int start,
		int end, OrderByComparator<OrgGroupRole> orderByComparator) {
		return getPersistence()
				   .findByRoleId(roleId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the org group roles where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param roleId the role ID
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching org group roles
	*/
	public static List<OrgGroupRole> findByRoleId(long roleId, int start,
		int end, OrderByComparator<OrgGroupRole> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByRoleId(roleId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first org group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org group role
	* @throws NoSuchOrgGroupRoleException if a matching org group role could not be found
	*/
	public static OrgGroupRole findByRoleId_First(long roleId,
		OrderByComparator<OrgGroupRole> orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByRoleId_First(roleId, orderByComparator);
	}

	/**
	* Returns the first org group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching org group role, or <code>null</code> if a matching org group role could not be found
	*/
	public static OrgGroupRole fetchByRoleId_First(long roleId,
		OrderByComparator<OrgGroupRole> orderByComparator) {
		return getPersistence().fetchByRoleId_First(roleId, orderByComparator);
	}

	/**
	* Returns the last org group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org group role
	* @throws NoSuchOrgGroupRoleException if a matching org group role could not be found
	*/
	public static OrgGroupRole findByRoleId_Last(long roleId,
		OrderByComparator<OrgGroupRole> orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByRoleId_Last(roleId, orderByComparator);
	}

	/**
	* Returns the last org group role in the ordered set where roleId = &#63;.
	*
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching org group role, or <code>null</code> if a matching org group role could not be found
	*/
	public static OrgGroupRole fetchByRoleId_Last(long roleId,
		OrderByComparator<OrgGroupRole> orderByComparator) {
		return getPersistence().fetchByRoleId_Last(roleId, orderByComparator);
	}

	/**
	* Returns the org group roles before and after the current org group role in the ordered set where roleId = &#63;.
	*
	* @param orgGroupRolePK the primary key of the current org group role
	* @param roleId the role ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next org group role
	* @throws NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	*/
	public static OrgGroupRole[] findByRoleId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK,
		long roleId, OrderByComparator<OrgGroupRole> orderByComparator)
		throws com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence()
				   .findByRoleId_PrevAndNext(orgGroupRolePK, roleId,
			orderByComparator);
	}

	/**
	* Removes all the org group roles where roleId = &#63; from the database.
	*
	* @param roleId the role ID
	*/
	public static void removeByRoleId(long roleId) {
		getPersistence().removeByRoleId(roleId);
	}

	/**
	* Returns the number of org group roles where roleId = &#63;.
	*
	* @param roleId the role ID
	* @return the number of matching org group roles
	*/
	public static int countByRoleId(long roleId) {
		return getPersistence().countByRoleId(roleId);
	}

	/**
	* Caches the org group role in the entity cache if it is enabled.
	*
	* @param orgGroupRole the org group role
	*/
	public static void cacheResult(OrgGroupRole orgGroupRole) {
		getPersistence().cacheResult(orgGroupRole);
	}

	/**
	* Caches the org group roles in the entity cache if it is enabled.
	*
	* @param orgGroupRoles the org group roles
	*/
	public static void cacheResult(List<OrgGroupRole> orgGroupRoles) {
		getPersistence().cacheResult(orgGroupRoles);
	}

	/**
	* Creates a new org group role with the primary key. Does not add the org group role to the database.
	*
	* @param orgGroupRolePK the primary key for the new org group role
	* @return the new org group role
	*/
	public static OrgGroupRole create(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK) {
		return getPersistence().create(orgGroupRolePK);
	}

	/**
	* Removes the org group role with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orgGroupRolePK the primary key of the org group role
	* @return the org group role that was removed
	* @throws NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	*/
	public static OrgGroupRole remove(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK)
		throws com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().remove(orgGroupRolePK);
	}

	public static OrgGroupRole updateImpl(OrgGroupRole orgGroupRole) {
		return getPersistence().updateImpl(orgGroupRole);
	}

	/**
	* Returns the org group role with the primary key or throws a {@link NoSuchOrgGroupRoleException} if it could not be found.
	*
	* @param orgGroupRolePK the primary key of the org group role
	* @return the org group role
	* @throws NoSuchOrgGroupRoleException if a org group role with the primary key could not be found
	*/
	public static OrgGroupRole findByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK)
		throws com.liferay.portal.NoSuchOrgGroupRoleException {
		return getPersistence().findByPrimaryKey(orgGroupRolePK);
	}

	/**
	* Returns the org group role with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param orgGroupRolePK the primary key of the org group role
	* @return the org group role, or <code>null</code> if a org group role with the primary key could not be found
	*/
	public static OrgGroupRole fetchByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK) {
		return getPersistence().fetchByPrimaryKey(orgGroupRolePK);
	}

	public static java.util.Map<java.io.Serializable, OrgGroupRole> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the org group roles.
	*
	* @return the org group roles
	*/
	public static List<OrgGroupRole> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the org group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @return the range of org group roles
	*/
	public static List<OrgGroupRole> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the org group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of org group roles
	*/
	public static List<OrgGroupRole> findAll(int start, int end,
		OrderByComparator<OrgGroupRole> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the org group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link OrgGroupRoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of org group roles
	* @param end the upper bound of the range of org group roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of org group roles
	*/
	public static List<OrgGroupRole> findAll(int start, int end,
		OrderByComparator<OrgGroupRole> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the org group roles from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of org group roles.
	*
	* @return the number of org group roles
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static OrgGroupRolePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (OrgGroupRolePersistence)PortalBeanLocatorUtil.locate(OrgGroupRolePersistence.class.getName());

			ReferenceRegistry.registerReference(OrgGroupRoleUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static OrgGroupRolePersistence _persistence;
}