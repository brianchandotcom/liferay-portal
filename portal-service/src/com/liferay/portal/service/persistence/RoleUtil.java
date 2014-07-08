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
import com.liferay.portal.model.Role;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the role service. This utility wraps {@link RolePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RolePersistence
 * @see RolePersistenceImpl
 * @generated
 */
@ProviderType
public class RoleUtil {
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
	public static void clearCache(Role role) {
		getPersistence().clearCache(role);
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
	public static List<Role> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Role> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Role> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Role update(Role role) {
		return getPersistence().update(role);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Role update(Role role, ServiceContext serviceContext) {
		return getPersistence().update(role, serviceContext);
	}

	/**
	* Returns all the roles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the roles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the roles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set where uuid = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] findByUuid_PrevAndNext(
		long roleId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByUuid_PrevAndNext(roleId, uuid, orderByComparator);
	}

	/**
	* Returns all the roles that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByUuid(
		java.lang.String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	* Returns a range of all the roles that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the roles that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .filterFindByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where uuid = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] filterFindByUuid_PrevAndNext(
		long roleId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .filterFindByUuid_PrevAndNext(roleId, uuid, orderByComparator);
	}

	/**
	* Removes all the roles where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of roles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching roles
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of roles that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching roles that the user has permission to view
	*/
	public static int filterCountByUuid(java.lang.String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	* Returns all the roles where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the roles where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the roles where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] findByUuid_C_PrevAndNext(
		long roleId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(roleId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Returns all the roles that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the roles that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the roles that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .filterFindByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] filterFindByUuid_C_PrevAndNext(
		long roleId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .filterFindByUuid_C_PrevAndNext(roleId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the roles where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of roles where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching roles
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of roles that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching roles that the user has permission to view
	*/
	public static int filterCountByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the roles where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the roles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the roles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set where companyId = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] findByCompanyId_PrevAndNext(
		long roleId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(roleId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the roles that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByCompanyId(
		long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	* Returns a range of all the roles that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the roles that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .filterFindByCompanyId(companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where companyId = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] filterFindByCompanyId_PrevAndNext(
		long roleId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .filterFindByCompanyId_PrevAndNext(roleId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the roles where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of roles where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching roles
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of roles that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching roles that the user has permission to view
	*/
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	* Returns all the roles where name = &#63;.
	*
	* @param name the name
	* @return the matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByName(
		java.lang.String name) {
		return getPersistence().findByName(name);
	}

	/**
	* Returns a range of all the roles where name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param name the name
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByName(
		java.lang.String name, int start, int end) {
		return getPersistence().findByName(name, start, end);
	}

	/**
	* Returns an ordered range of all the roles where name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param name the name
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByName(
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findByName(name, start, end, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where name = &#63;.
	*
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByName_First(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByName_First(name, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where name = &#63;.
	*
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByName_First(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByName_First(name, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where name = &#63;.
	*
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByName_Last(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByName_Last(name, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where name = &#63;.
	*
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByName_Last(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByName_Last(name, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set where name = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] findByName_PrevAndNext(
		long roleId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByName_PrevAndNext(roleId, name, orderByComparator);
	}

	/**
	* Returns all the roles that the user has permission to view where name = &#63;.
	*
	* @param name the name
	* @return the matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByName(
		java.lang.String name) {
		return getPersistence().filterFindByName(name);
	}

	/**
	* Returns a range of all the roles that the user has permission to view where name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param name the name
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByName(
		java.lang.String name, int start, int end) {
		return getPersistence().filterFindByName(name, start, end);
	}

	/**
	* Returns an ordered range of all the roles that the user has permissions to view where name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param name the name
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByName(
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .filterFindByName(name, start, end, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where name = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] filterFindByName_PrevAndNext(
		long roleId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .filterFindByName_PrevAndNext(roleId, name, orderByComparator);
	}

	/**
	* Removes all the roles where name = &#63; from the database.
	*
	* @param name the name
	*/
	public static void removeByName(java.lang.String name) {
		getPersistence().removeByName(name);
	}

	/**
	* Returns the number of roles where name = &#63;.
	*
	* @param name the name
	* @return the number of matching roles
	*/
	public static int countByName(java.lang.String name) {
		return getPersistence().countByName(name);
	}

	/**
	* Returns the number of roles that the user has permission to view where name = &#63;.
	*
	* @param name the name
	* @return the number of matching roles that the user has permission to view
	*/
	public static int filterCountByName(java.lang.String name) {
		return getPersistence().filterCountByName(name);
	}

	/**
	* Returns all the roles where type = &#63;.
	*
	* @param type the type
	* @return the matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByType(
		int type) {
		return getPersistence().findByType(type);
	}

	/**
	* Returns a range of all the roles where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByType(
		int type, int start, int end) {
		return getPersistence().findByType(type, start, end);
	}

	/**
	* Returns an ordered range of all the roles where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByType(
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findByType(type, start, end, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByType_First(int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByType_First(type, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByType_First(int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByType_First(type, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByType_Last(int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByType_Last(type, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByType_Last(int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByType_Last(type, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set where type = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] findByType_PrevAndNext(
		long roleId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByType_PrevAndNext(roleId, type, orderByComparator);
	}

	/**
	* Returns all the roles that the user has permission to view where type = &#63;.
	*
	* @param type the type
	* @return the matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByType(
		int type) {
		return getPersistence().filterFindByType(type);
	}

	/**
	* Returns a range of all the roles that the user has permission to view where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByType(
		int type, int start, int end) {
		return getPersistence().filterFindByType(type, start, end);
	}

	/**
	* Returns an ordered range of all the roles that the user has permissions to view where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByType(
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .filterFindByType(type, start, end, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where type = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] filterFindByType_PrevAndNext(
		long roleId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .filterFindByType_PrevAndNext(roleId, type, orderByComparator);
	}

	/**
	* Removes all the roles where type = &#63; from the database.
	*
	* @param type the type
	*/
	public static void removeByType(int type) {
		getPersistence().removeByType(type);
	}

	/**
	* Returns the number of roles where type = &#63;.
	*
	* @param type the type
	* @return the number of matching roles
	*/
	public static int countByType(int type) {
		return getPersistence().countByType(type);
	}

	/**
	* Returns the number of roles that the user has permission to view where type = &#63;.
	*
	* @param type the type
	* @return the number of matching roles that the user has permission to view
	*/
	public static int filterCountByType(int type) {
		return getPersistence().filterCountByType(type);
	}

	/**
	* Returns all the roles where subtype = &#63;.
	*
	* @param subtype the subtype
	* @return the matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findBySubtype(
		java.lang.String subtype) {
		return getPersistence().findBySubtype(subtype);
	}

	/**
	* Returns a range of all the roles where subtype = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param subtype the subtype
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findBySubtype(
		java.lang.String subtype, int start, int end) {
		return getPersistence().findBySubtype(subtype, start, end);
	}

	/**
	* Returns an ordered range of all the roles where subtype = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param subtype the subtype
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findBySubtype(
		java.lang.String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findBySubtype(subtype, start, end, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where subtype = &#63;.
	*
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findBySubtype_First(
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findBySubtype_First(subtype, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where subtype = &#63;.
	*
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchBySubtype_First(
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchBySubtype_First(subtype, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where subtype = &#63;.
	*
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findBySubtype_Last(
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findBySubtype_Last(subtype, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where subtype = &#63;.
	*
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchBySubtype_Last(
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchBySubtype_Last(subtype, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set where subtype = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] findBySubtype_PrevAndNext(
		long roleId, java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findBySubtype_PrevAndNext(roleId, subtype, orderByComparator);
	}

	/**
	* Returns all the roles that the user has permission to view where subtype = &#63;.
	*
	* @param subtype the subtype
	* @return the matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindBySubtype(
		java.lang.String subtype) {
		return getPersistence().filterFindBySubtype(subtype);
	}

	/**
	* Returns a range of all the roles that the user has permission to view where subtype = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param subtype the subtype
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindBySubtype(
		java.lang.String subtype, int start, int end) {
		return getPersistence().filterFindBySubtype(subtype, start, end);
	}

	/**
	* Returns an ordered range of all the roles that the user has permissions to view where subtype = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param subtype the subtype
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindBySubtype(
		java.lang.String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .filterFindBySubtype(subtype, start, end, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where subtype = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] filterFindBySubtype_PrevAndNext(
		long roleId, java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .filterFindBySubtype_PrevAndNext(roleId, subtype,
			orderByComparator);
	}

	/**
	* Removes all the roles where subtype = &#63; from the database.
	*
	* @param subtype the subtype
	*/
	public static void removeBySubtype(java.lang.String subtype) {
		getPersistence().removeBySubtype(subtype);
	}

	/**
	* Returns the number of roles where subtype = &#63;.
	*
	* @param subtype the subtype
	* @return the number of matching roles
	*/
	public static int countBySubtype(java.lang.String subtype) {
		return getPersistence().countBySubtype(subtype);
	}

	/**
	* Returns the number of roles that the user has permission to view where subtype = &#63;.
	*
	* @param subtype the subtype
	* @return the number of matching roles that the user has permission to view
	*/
	public static int filterCountBySubtype(java.lang.String subtype) {
		return getPersistence().filterCountBySubtype(subtype);
	}

	/**
	* Returns the role where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchRoleException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByC_N(long companyId,
		java.lang.String name) throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByC_N(companyId, name);
	}

	/**
	* Returns the role where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByC_N(long companyId,
		java.lang.String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	* Returns the role where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	/**
	* Removes the role where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the role that was removed
	*/
	public static com.liferay.portal.model.Role removeByC_N(long companyId,
		java.lang.String name) throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	* Returns the number of roles where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching roles
	*/
	public static int countByC_N(long companyId, java.lang.String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	* Returns all the roles where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @return the matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByC_T(
		long companyId, int type) {
		return getPersistence().findByC_T(companyId, type);
	}

	/**
	* Returns a range of all the roles where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByC_T(
		long companyId, int type, int start, int end) {
		return getPersistence().findByC_T(companyId, type, start, end);
	}

	/**
	* Returns an ordered range of all the roles where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByC_T(companyId, type, start, end, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByC_T_First(companyId, type, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByC_T_First(companyId, type, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByC_T_Last(long companyId,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByC_T_Last(companyId, type, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByC_T_Last(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByC_T_Last(companyId, type, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] findByC_T_PrevAndNext(
		long roleId, long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByC_T_PrevAndNext(roleId, companyId, type,
			orderByComparator);
	}

	/**
	* Returns all the roles that the user has permission to view where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @return the matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByC_T(
		long companyId, int type) {
		return getPersistence().filterFindByC_T(companyId, type);
	}

	/**
	* Returns a range of all the roles that the user has permission to view where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByC_T(
		long companyId, int type, int start, int end) {
		return getPersistence().filterFindByC_T(companyId, type, start, end);
	}

	/**
	* Returns an ordered range of all the roles that the user has permissions to view where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .filterFindByC_T(companyId, type, start, end,
			orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where companyId = &#63; and type = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] filterFindByC_T_PrevAndNext(
		long roleId, long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .filterFindByC_T_PrevAndNext(roleId, companyId, type,
			orderByComparator);
	}

	/**
	* Returns all the roles that the user has permission to view where companyId = &#63; and type = any &#63;.
	*
	* @param companyId the company ID
	* @param types the types
	* @return the matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByC_T(
		long companyId, int[] types) {
		return getPersistence().filterFindByC_T(companyId, types);
	}

	/**
	* Returns a range of all the roles that the user has permission to view where companyId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param types the types
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByC_T(
		long companyId, int[] types, int start, int end) {
		return getPersistence().filterFindByC_T(companyId, types, start, end);
	}

	/**
	* Returns an ordered range of all the roles that the user has permission to view where companyId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param types the types
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByC_T(
		long companyId, int[] types, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .filterFindByC_T(companyId, types, start, end,
			orderByComparator);
	}

	/**
	* Returns all the roles where companyId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param types the types
	* @return the matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByC_T(
		long companyId, int[] types) {
		return getPersistence().findByC_T(companyId, types);
	}

	/**
	* Returns a range of all the roles where companyId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param types the types
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByC_T(
		long companyId, int[] types, int start, int end) {
		return getPersistence().findByC_T(companyId, types, start, end);
	}

	/**
	* Returns an ordered range of all the roles where companyId = &#63; and type = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param types the types
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByC_T(
		long companyId, int[] types, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByC_T(companyId, types, start, end, orderByComparator);
	}

	/**
	* Removes all the roles where companyId = &#63; and type = &#63; from the database.
	*
	* @param companyId the company ID
	* @param type the type
	*/
	public static void removeByC_T(long companyId, int type) {
		getPersistence().removeByC_T(companyId, type);
	}

	/**
	* Returns the number of roles where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @return the number of matching roles
	*/
	public static int countByC_T(long companyId, int type) {
		return getPersistence().countByC_T(companyId, type);
	}

	/**
	* Returns the number of roles where companyId = &#63; and type = any &#63;.
	*
	* @param companyId the company ID
	* @param types the types
	* @return the number of matching roles
	*/
	public static int countByC_T(long companyId, int[] types) {
		return getPersistence().countByC_T(companyId, types);
	}

	/**
	* Returns the number of roles that the user has permission to view where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @return the number of matching roles that the user has permission to view
	*/
	public static int filterCountByC_T(long companyId, int type) {
		return getPersistence().filterCountByC_T(companyId, type);
	}

	/**
	* Returns the number of roles that the user has permission to view where companyId = &#63; and type = any &#63;.
	*
	* @param companyId the company ID
	* @param types the types
	* @return the number of matching roles that the user has permission to view
	*/
	public static int filterCountByC_T(long companyId, int[] types) {
		return getPersistence().filterCountByC_T(companyId, types);
	}

	/**
	* Returns all the roles where type = &#63; and subtype = &#63;.
	*
	* @param type the type
	* @param subtype the subtype
	* @return the matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByT_S(
		int type, java.lang.String subtype) {
		return getPersistence().findByT_S(type, subtype);
	}

	/**
	* Returns a range of all the roles where type = &#63; and subtype = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param subtype the subtype
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByT_S(
		int type, java.lang.String subtype, int start, int end) {
		return getPersistence().findByT_S(type, subtype, start, end);
	}

	/**
	* Returns an ordered range of all the roles where type = &#63; and subtype = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param subtype the subtype
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findByT_S(
		int type, java.lang.String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByT_S(type, subtype, start, end, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where type = &#63; and subtype = &#63;.
	*
	* @param type the type
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByT_S_First(int type,
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByT_S_First(type, subtype, orderByComparator);
	}

	/**
	* Returns the first role in the ordered set where type = &#63; and subtype = &#63;.
	*
	* @param type the type
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByT_S_First(int type,
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByT_S_First(type, subtype, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where type = &#63; and subtype = &#63;.
	*
	* @param type the type
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByT_S_Last(int type,
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByT_S_Last(type, subtype, orderByComparator);
	}

	/**
	* Returns the last role in the ordered set where type = &#63; and subtype = &#63;.
	*
	* @param type the type
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByT_S_Last(int type,
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().fetchByT_S_Last(type, subtype, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set where type = &#63; and subtype = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param type the type
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] findByT_S_PrevAndNext(
		long roleId, int type, java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .findByT_S_PrevAndNext(roleId, type, subtype,
			orderByComparator);
	}

	/**
	* Returns all the roles that the user has permission to view where type = &#63; and subtype = &#63;.
	*
	* @param type the type
	* @param subtype the subtype
	* @return the matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByT_S(
		int type, java.lang.String subtype) {
		return getPersistence().filterFindByT_S(type, subtype);
	}

	/**
	* Returns a range of all the roles that the user has permission to view where type = &#63; and subtype = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param subtype the subtype
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByT_S(
		int type, java.lang.String subtype, int start, int end) {
		return getPersistence().filterFindByT_S(type, subtype, start, end);
	}

	/**
	* Returns an ordered range of all the roles that the user has permissions to view where type = &#63; and subtype = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param subtype the subtype
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching roles that the user has permission to view
	*/
	public static java.util.List<com.liferay.portal.model.Role> filterFindByT_S(
		int type, java.lang.String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .filterFindByT_S(type, subtype, start, end, orderByComparator);
	}

	/**
	* Returns the roles before and after the current role in the ordered set of roles that the user has permission to view where type = &#63; and subtype = &#63;.
	*
	* @param roleId the primary key of the current role
	* @param type the type
	* @param subtype the subtype
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role[] filterFindByT_S_PrevAndNext(
		long roleId, int type, java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence()
				   .filterFindByT_S_PrevAndNext(roleId, type, subtype,
			orderByComparator);
	}

	/**
	* Removes all the roles where type = &#63; and subtype = &#63; from the database.
	*
	* @param type the type
	* @param subtype the subtype
	*/
	public static void removeByT_S(int type, java.lang.String subtype) {
		getPersistence().removeByT_S(type, subtype);
	}

	/**
	* Returns the number of roles where type = &#63; and subtype = &#63;.
	*
	* @param type the type
	* @param subtype the subtype
	* @return the number of matching roles
	*/
	public static int countByT_S(int type, java.lang.String subtype) {
		return getPersistence().countByT_S(type, subtype);
	}

	/**
	* Returns the number of roles that the user has permission to view where type = &#63; and subtype = &#63;.
	*
	* @param type the type
	* @param subtype the subtype
	* @return the number of matching roles that the user has permission to view
	*/
	public static int filterCountByT_S(int type, java.lang.String subtype) {
		return getPersistence().filterCountByT_S(type, subtype);
	}

	/**
	* Returns the role where companyId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchRoleException} if it could not be found.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching role
	* @throws com.liferay.portal.NoSuchRoleException if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role findByC_C_C(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Returns the role where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByC_C_C(long companyId,
		long classNameId, long classPK) {
		return getPersistence().fetchByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Returns the role where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching role, or <code>null</code> if a matching role could not be found
	*/
	public static com.liferay.portal.model.Role fetchByC_C_C(long companyId,
		long classNameId, long classPK, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C_C(companyId, classNameId, classPK,
			retrieveFromCache);
	}

	/**
	* Removes the role where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the role that was removed
	*/
	public static com.liferay.portal.model.Role removeByC_C_C(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().removeByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Returns the number of roles where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching roles
	*/
	public static int countByC_C_C(long companyId, long classNameId,
		long classPK) {
		return getPersistence().countByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Caches the role in the entity cache if it is enabled.
	*
	* @param role the role
	*/
	public static void cacheResult(com.liferay.portal.model.Role role) {
		getPersistence().cacheResult(role);
	}

	/**
	* Caches the roles in the entity cache if it is enabled.
	*
	* @param roles the roles
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Role> roles) {
		getPersistence().cacheResult(roles);
	}

	/**
	* Creates a new role with the primary key. Does not add the role to the database.
	*
	* @param roleId the primary key for the new role
	* @return the new role
	*/
	public static com.liferay.portal.model.Role create(long roleId) {
		return getPersistence().create(roleId);
	}

	/**
	* Removes the role with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param roleId the primary key of the role
	* @return the role that was removed
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role remove(long roleId)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().remove(roleId);
	}

	public static com.liferay.portal.model.Role updateImpl(
		com.liferay.portal.model.Role role) {
		return getPersistence().updateImpl(role);
	}

	/**
	* Returns the role with the primary key or throws a {@link com.liferay.portal.NoSuchRoleException} if it could not be found.
	*
	* @param roleId the primary key of the role
	* @return the role
	* @throws com.liferay.portal.NoSuchRoleException if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role findByPrimaryKey(long roleId)
		throws com.liferay.portal.NoSuchRoleException {
		return getPersistence().findByPrimaryKey(roleId);
	}

	/**
	* Returns the role with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param roleId the primary key of the role
	* @return the role, or <code>null</code> if a role with the primary key could not be found
	*/
	public static com.liferay.portal.model.Role fetchByPrimaryKey(long roleId) {
		return getPersistence().fetchByPrimaryKey(roleId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.Role> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the roles.
	*
	* @return the roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of roles
	*/
	public static java.util.List<com.liferay.portal.model.Role> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the roles from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of roles.
	*
	* @return the number of roles
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of groups associated with the role.
	*
	* @param pk the primary key of the role
	* @return long[] of the primaryKeys of groups associated with the role
	*/
	public static long[] getGroupPrimaryKeys(long pk) {
		return getPersistence().getGroupPrimaryKeys(pk);
	}

	/**
	* Returns all the groups associated with the role.
	*
	* @param pk the primary key of the role
	* @return the groups associated with the role
	*/
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk) {
		return getPersistence().getGroups(pk);
	}

	/**
	* Returns a range of all the groups associated with the role.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the role
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of groups associated with the role
	*/
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end) {
		return getPersistence().getGroups(pk, start, end);
	}

	/**
	* Returns an ordered range of all the groups associated with the role.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the role
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups associated with the role
	*/
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().getGroups(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of groups associated with the role.
	*
	* @param pk the primary key of the role
	* @return the number of groups associated with the role
	*/
	public static int getGroupsSize(long pk) {
		return getPersistence().getGroupsSize(pk);
	}

	/**
	* Returns <code>true</code> if the group is associated with the role.
	*
	* @param pk the primary key of the role
	* @param groupPK the primary key of the group
	* @return <code>true</code> if the group is associated with the role; <code>false</code> otherwise
	*/
	public static boolean containsGroup(long pk, long groupPK) {
		return getPersistence().containsGroup(pk, groupPK);
	}

	/**
	* Returns <code>true</code> if the role has any groups associated with it.
	*
	* @param pk the primary key of the role to check for associations with groups
	* @return <code>true</code> if the role has any groups associated with it; <code>false</code> otherwise
	*/
	public static boolean containsGroups(long pk) {
		return getPersistence().containsGroups(pk);
	}

	/**
	* Adds an association between the role and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param groupPK the primary key of the group
	*/
	public static void addGroup(long pk, long groupPK) {
		getPersistence().addGroup(pk, groupPK);
	}

	/**
	* Adds an association between the role and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param group the group
	*/
	public static void addGroup(long pk, com.liferay.portal.model.Group group) {
		getPersistence().addGroup(pk, group);
	}

	/**
	* Adds an association between the role and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param groupPKs the primary keys of the groups
	*/
	public static void addGroups(long pk, long[] groupPKs) {
		getPersistence().addGroups(pk, groupPKs);
	}

	/**
	* Adds an association between the role and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param groups the groups
	*/
	public static void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups) {
		getPersistence().addGroups(pk, groups);
	}

	/**
	* Clears all associations between the role and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role to clear the associated groups from
	*/
	public static void clearGroups(long pk) {
		getPersistence().clearGroups(pk);
	}

	/**
	* Removes the association between the role and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param groupPK the primary key of the group
	*/
	public static void removeGroup(long pk, long groupPK) {
		getPersistence().removeGroup(pk, groupPK);
	}

	/**
	* Removes the association between the role and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param group the group
	*/
	public static void removeGroup(long pk, com.liferay.portal.model.Group group) {
		getPersistence().removeGroup(pk, group);
	}

	/**
	* Removes the association between the role and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param groupPKs the primary keys of the groups
	*/
	public static void removeGroups(long pk, long[] groupPKs) {
		getPersistence().removeGroups(pk, groupPKs);
	}

	/**
	* Removes the association between the role and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param groups the groups
	*/
	public static void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups) {
		getPersistence().removeGroups(pk, groups);
	}

	/**
	* Sets the groups associated with the role, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param groupPKs the primary keys of the groups to be associated with the role
	*/
	public static void setGroups(long pk, long[] groupPKs) {
		getPersistence().setGroups(pk, groupPKs);
	}

	/**
	* Sets the groups associated with the role, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param groups the groups to be associated with the role
	*/
	public static void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups) {
		getPersistence().setGroups(pk, groups);
	}

	/**
	* Returns the primaryKeys of users associated with the role.
	*
	* @param pk the primary key of the role
	* @return long[] of the primaryKeys of users associated with the role
	*/
	public static long[] getUserPrimaryKeys(long pk) {
		return getPersistence().getUserPrimaryKeys(pk);
	}

	/**
	* Returns all the users associated with the role.
	*
	* @param pk the primary key of the role
	* @return the users associated with the role
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk) {
		return getPersistence().getUsers(pk);
	}

	/**
	* Returns a range of all the users associated with the role.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the role
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @return the range of users associated with the role
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end) {
		return getPersistence().getUsers(pk, start, end);
	}

	/**
	* Returns an ordered range of all the users associated with the role.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.RoleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the role
	* @param start the lower bound of the range of roles
	* @param end the upper bound of the range of roles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users associated with the role
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().getUsers(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of users associated with the role.
	*
	* @param pk the primary key of the role
	* @return the number of users associated with the role
	*/
	public static int getUsersSize(long pk) {
		return getPersistence().getUsersSize(pk);
	}

	/**
	* Returns <code>true</code> if the user is associated with the role.
	*
	* @param pk the primary key of the role
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the role; <code>false</code> otherwise
	*/
	public static boolean containsUser(long pk, long userPK) {
		return getPersistence().containsUser(pk, userPK);
	}

	/**
	* Returns <code>true</code> if the role has any users associated with it.
	*
	* @param pk the primary key of the role to check for associations with users
	* @return <code>true</code> if the role has any users associated with it; <code>false</code> otherwise
	*/
	public static boolean containsUsers(long pk) {
		return getPersistence().containsUsers(pk);
	}

	/**
	* Adds an association between the role and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param userPK the primary key of the user
	*/
	public static void addUser(long pk, long userPK) {
		getPersistence().addUser(pk, userPK);
	}

	/**
	* Adds an association between the role and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param user the user
	*/
	public static void addUser(long pk, com.liferay.portal.model.User user) {
		getPersistence().addUser(pk, user);
	}

	/**
	* Adds an association between the role and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param userPKs the primary keys of the users
	*/
	public static void addUsers(long pk, long[] userPKs) {
		getPersistence().addUsers(pk, userPKs);
	}

	/**
	* Adds an association between the role and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param users the users
	*/
	public static void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users) {
		getPersistence().addUsers(pk, users);
	}

	/**
	* Clears all associations between the role and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role to clear the associated users from
	*/
	public static void clearUsers(long pk) {
		getPersistence().clearUsers(pk);
	}

	/**
	* Removes the association between the role and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param userPK the primary key of the user
	*/
	public static void removeUser(long pk, long userPK) {
		getPersistence().removeUser(pk, userPK);
	}

	/**
	* Removes the association between the role and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param user the user
	*/
	public static void removeUser(long pk, com.liferay.portal.model.User user) {
		getPersistence().removeUser(pk, user);
	}

	/**
	* Removes the association between the role and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param userPKs the primary keys of the users
	*/
	public static void removeUsers(long pk, long[] userPKs) {
		getPersistence().removeUsers(pk, userPKs);
	}

	/**
	* Removes the association between the role and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param users the users
	*/
	public static void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users) {
		getPersistence().removeUsers(pk, users);
	}

	/**
	* Sets the users associated with the role, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param userPKs the primary keys of the users to be associated with the role
	*/
	public static void setUsers(long pk, long[] userPKs) {
		getPersistence().setUsers(pk, userPKs);
	}

	/**
	* Sets the users associated with the role, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the role
	* @param users the users to be associated with the role
	*/
	public static void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users) {
		getPersistence().setUsers(pk, users);
	}

	public static RolePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (RolePersistence)PortalBeanLocatorUtil.locate(RolePersistence.class.getName());

			ReferenceRegistry.registerReference(RoleUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(RolePersistence persistence) {
	}

	private static RolePersistence _persistence;
}