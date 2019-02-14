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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the organization service. This utility wraps <code>com.liferay.portal.service.persistence.impl.OrganizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrganizationPersistence
 * @generated
 */
@ProviderType
public class OrganizationUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Organization organization) {
		getPersistence().clearCache(organization);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, Organization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Organization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Organization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Organization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Organization update(Organization organization) {
		return getPersistence().update(organization);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Organization update(Organization organization,
		ServiceContext serviceContext) {
		return getPersistence().update(organization, serviceContext);
	}

	/**
	* Returns all the organizations where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching organizations
	*/
	public static List<Organization> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the organizations where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	*/
	public static List<Organization> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the organizations where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByUuid(String uuid, int start,
		int end, OrderByComparator<Organization> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the organizations where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByUuid(String uuid, int start,
		int end, OrderByComparator<Organization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first organization in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByUuid_First(String uuid,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first organization in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByUuid_First(String uuid,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByUuid_Last(String uuid,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByUuid_Last(String uuid,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set where uuid = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] findByUuid_PrevAndNext(long organizationId,
		String uuid, OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByUuid_PrevAndNext(organizationId, uuid,
			orderByComparator);
	}

	/**
	* Returns all the organizations that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByUuid(String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	* Returns a range of all the organizations that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByUuid(String uuid, int start,
		int end) {
		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByUuid(String uuid, int start,
		int end, OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .filterFindByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where uuid = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] filterFindByUuid_PrevAndNext(
		long organizationId, String uuid,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .filterFindByUuid_PrevAndNext(organizationId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the organizations where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of organizations where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching organizations
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of organizations that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching organizations that the user has permission to view
	*/
	public static int filterCountByUuid(String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	* Returns all the organizations where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching organizations
	*/
	public static List<Organization> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the organizations where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	*/
	public static List<Organization> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the organizations where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the organizations where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<Organization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first organization in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first organization in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] findByUuid_C_PrevAndNext(long organizationId,
		String uuid, long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(organizationId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Returns all the organizations that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByUuid_C(String uuid,
		long companyId) {
		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the organizations that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .filterFindByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] filterFindByUuid_C_PrevAndNext(
		long organizationId, String uuid, long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .filterFindByUuid_C_PrevAndNext(organizationId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the organizations where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of organizations where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching organizations
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of organizations that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching organizations that the user has permission to view
	*/
	public static int filterCountByUuid_C(String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the organizations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching organizations
	*/
	public static List<Organization> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the organizations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	*/
	public static List<Organization> findByCompanyId(long companyId, int start,
		int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<Organization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByCompanyId_First(long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByCompanyId_First(long companyId,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByCompanyId_Last(long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByCompanyId_Last(long companyId,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set where companyId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] findByCompanyId_PrevAndNext(
		long organizationId, long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(organizationId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the organizations that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	* Returns a range of all the organizations that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByCompanyId(long companyId,
		int start, int end, OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .filterFindByCompanyId(companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] filterFindByCompanyId_PrevAndNext(
		long organizationId, long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .filterFindByCompanyId_PrevAndNext(organizationId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the organizations where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of organizations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching organizations
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of organizations that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching organizations that the user has permission to view
	*/
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	* Returns all the organizations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching organizations
	*/
	public static List<Organization> findByLocations(long companyId) {
		return getPersistence().findByLocations(companyId);
	}

	/**
	* Returns a range of all the organizations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	*/
	public static List<Organization> findByLocations(long companyId, int start,
		int end) {
		return getPersistence().findByLocations(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByLocations(long companyId, int start,
		int end, OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .findByLocations(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByLocations(long companyId, int start,
		int end, OrderByComparator<Organization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLocations(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByLocations_First(long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByLocations_First(companyId, orderByComparator);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByLocations_First(long companyId,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByLocations_First(companyId, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByLocations_Last(long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByLocations_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByLocations_Last(long companyId,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByLocations_Last(companyId, orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set where companyId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] findByLocations_PrevAndNext(
		long organizationId, long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByLocations_PrevAndNext(organizationId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the organizations that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByLocations(long companyId) {
		return getPersistence().filterFindByLocations(companyId);
	}

	/**
	* Returns a range of all the organizations that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByLocations(long companyId,
		int start, int end) {
		return getPersistence().filterFindByLocations(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByLocations(long companyId,
		int start, int end, OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .filterFindByLocations(companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] filterFindByLocations_PrevAndNext(
		long organizationId, long companyId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .filterFindByLocations_PrevAndNext(organizationId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the organizations where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByLocations(long companyId) {
		getPersistence().removeByLocations(companyId);
	}

	/**
	* Returns the number of organizations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching organizations
	*/
	public static int countByLocations(long companyId) {
		return getPersistence().countByLocations(companyId);
	}

	/**
	* Returns the number of organizations that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching organizations that the user has permission to view
	*/
	public static int filterCountByLocations(long companyId) {
		return getPersistence().filterCountByLocations(companyId);
	}

	/**
	* Returns all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the matching organizations
	*/
	public static List<Organization> findByC_P(long companyId,
		long parentOrganizationId) {
		return getPersistence().findByC_P(companyId, parentOrganizationId);
	}

	/**
	* Returns a range of all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	*/
	public static List<Organization> findByC_P(long companyId,
		long parentOrganizationId, int start, int end) {
		return getPersistence()
				   .findByC_P(companyId, parentOrganizationId, start, end);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByC_P(long companyId,
		long parentOrganizationId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .findByC_P(companyId, parentOrganizationId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByC_P(long companyId,
		long parentOrganizationId, int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_P(companyId, parentOrganizationId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByC_P_First(long companyId,
		long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_P_First(companyId, parentOrganizationId,
			orderByComparator);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_P_First(long companyId,
		long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_First(companyId, parentOrganizationId,
			orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByC_P_Last(long companyId,
		long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_P_Last(companyId, parentOrganizationId,
			orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_P_Last(long companyId,
		long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_Last(companyId, parentOrganizationId,
			orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] findByC_P_PrevAndNext(long organizationId,
		long companyId, long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_P_PrevAndNext(organizationId, companyId,
			parentOrganizationId, orderByComparator);
	}

	/**
	* Returns all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_P(long companyId,
		long parentOrganizationId) {
		return getPersistence().filterFindByC_P(companyId, parentOrganizationId);
	}

	/**
	* Returns a range of all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_P(long companyId,
		long parentOrganizationId, int start, int end) {
		return getPersistence()
				   .filterFindByC_P(companyId, parentOrganizationId, start, end);
	}

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_P(long companyId,
		long parentOrganizationId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .filterFindByC_P(companyId, parentOrganizationId, start,
			end, orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] filterFindByC_P_PrevAndNext(
		long organizationId, long companyId, long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .filterFindByC_P_PrevAndNext(organizationId, companyId,
			parentOrganizationId, orderByComparator);
	}

	/**
	* Removes all the organizations where companyId = &#63; and parentOrganizationId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	*/
	public static void removeByC_P(long companyId, long parentOrganizationId) {
		getPersistence().removeByC_P(companyId, parentOrganizationId);
	}

	/**
	* Returns the number of organizations where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the number of matching organizations
	*/
	public static int countByC_P(long companyId, long parentOrganizationId) {
		return getPersistence().countByC_P(companyId, parentOrganizationId);
	}

	/**
	* Returns the number of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the number of matching organizations that the user has permission to view
	*/
	public static int filterCountByC_P(long companyId, long parentOrganizationId) {
		return getPersistence().filterCountByC_P(companyId, parentOrganizationId);
	}

	/**
	* Returns all the organizations where companyId = &#63; and treePath LIKE &#63;.
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @return the matching organizations
	*/
	public static List<Organization> findByC_T(long companyId, String treePath) {
		return getPersistence().findByC_T(companyId, treePath);
	}

	/**
	* Returns a range of all the organizations where companyId = &#63; and treePath LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	*/
	public static List<Organization> findByC_T(long companyId, String treePath,
		int start, int end) {
		return getPersistence().findByC_T(companyId, treePath, start, end);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63; and treePath LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByC_T(long companyId, String treePath,
		int start, int end, OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .findByC_T(companyId, treePath, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63; and treePath LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByC_T(long companyId, String treePath,
		int start, int end, OrderByComparator<Organization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_T(companyId, treePath, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63; and treePath LIKE &#63;.
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByC_T_First(long companyId, String treePath,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_T_First(companyId, treePath, orderByComparator);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63; and treePath LIKE &#63;.
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_T_First(long companyId,
		String treePath, OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByC_T_First(companyId, treePath, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63; and treePath LIKE &#63;.
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByC_T_Last(long companyId, String treePath,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_T_Last(companyId, treePath, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63; and treePath LIKE &#63;.
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_T_Last(long companyId, String treePath,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByC_T_Last(companyId, treePath, orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set where companyId = &#63; and treePath LIKE &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param treePath the tree path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] findByC_T_PrevAndNext(long organizationId,
		long companyId, String treePath,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_T_PrevAndNext(organizationId, companyId, treePath,
			orderByComparator);
	}

	/**
	* Returns all the organizations that the user has permission to view where companyId = &#63; and treePath LIKE &#63;.
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @return the matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_T(long companyId,
		String treePath) {
		return getPersistence().filterFindByC_T(companyId, treePath);
	}

	/**
	* Returns a range of all the organizations that the user has permission to view where companyId = &#63; and treePath LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_T(long companyId,
		String treePath, int start, int end) {
		return getPersistence().filterFindByC_T(companyId, treePath, start, end);
	}

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63; and treePath LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_T(long companyId,
		String treePath, int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .filterFindByC_T(companyId, treePath, start, end,
			orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63; and treePath LIKE &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param treePath the tree path
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] filterFindByC_T_PrevAndNext(
		long organizationId, long companyId, String treePath,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .filterFindByC_T_PrevAndNext(organizationId, companyId,
			treePath, orderByComparator);
	}

	/**
	* Removes all the organizations where companyId = &#63; and treePath LIKE &#63; from the database.
	*
	* @param companyId the company ID
	* @param treePath the tree path
	*/
	public static void removeByC_T(long companyId, String treePath) {
		getPersistence().removeByC_T(companyId, treePath);
	}

	/**
	* Returns the number of organizations where companyId = &#63; and treePath LIKE &#63;.
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @return the number of matching organizations
	*/
	public static int countByC_T(long companyId, String treePath) {
		return getPersistence().countByC_T(companyId, treePath);
	}

	/**
	* Returns the number of organizations that the user has permission to view where companyId = &#63; and treePath LIKE &#63;.
	*
	* @param companyId the company ID
	* @param treePath the tree path
	* @return the number of matching organizations that the user has permission to view
	*/
	public static int filterCountByC_T(long companyId, String treePath) {
		return getPersistence().filterCountByC_T(companyId, treePath);
	}

	/**
	* Returns the organization where companyId = &#63; and name = &#63; or throws a <code>NoSuchOrganizationException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByC_N(long companyId, String name)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence().findByC_N(companyId, name);
	}

	/**
	* Returns the organization where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_N(long companyId, String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	* Returns the organization where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	/**
	* Removes the organization where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the organization that was removed
	*/
	public static Organization removeByC_N(long companyId, String name)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	* Returns the number of organizations where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching organizations
	*/
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	* Returns all the organizations where companyId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching organizations
	*/
	public static List<Organization> findByC_LikeN(long companyId, String name) {
		return getPersistence().findByC_LikeN(companyId, name);
	}

	/**
	* Returns a range of all the organizations where companyId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	*/
	public static List<Organization> findByC_LikeN(long companyId, String name,
		int start, int end) {
		return getPersistence().findByC_LikeN(companyId, name, start, end);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByC_LikeN(long companyId, String name,
		int start, int end, OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .findByC_LikeN(companyId, name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByC_LikeN(long companyId, String name,
		int start, int end, OrderByComparator<Organization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_LikeN(companyId, name, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByC_LikeN_First(long companyId, String name,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_LikeN_First(companyId, name, orderByComparator);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_LikeN_First(long companyId,
		String name, OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByC_LikeN_First(companyId, name, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByC_LikeN_Last(long companyId, String name,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_LikeN_Last(companyId, name, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_LikeN_Last(long companyId, String name,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByC_LikeN_Last(companyId, name, orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set where companyId = &#63; and name LIKE &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] findByC_LikeN_PrevAndNext(
		long organizationId, long companyId, String name,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_LikeN_PrevAndNext(organizationId, companyId, name,
			orderByComparator);
	}

	/**
	* Returns all the organizations that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_LikeN(long companyId,
		String name) {
		return getPersistence().filterFindByC_LikeN(companyId, name);
	}

	/**
	* Returns a range of all the organizations that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_LikeN(long companyId,
		String name, int start, int end) {
		return getPersistence().filterFindByC_LikeN(companyId, name, start, end);
	}

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_LikeN(long companyId,
		String name, int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .filterFindByC_LikeN(companyId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] filterFindByC_LikeN_PrevAndNext(
		long organizationId, long companyId, String name,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .filterFindByC_LikeN_PrevAndNext(organizationId, companyId,
			name, orderByComparator);
	}

	/**
	* Removes all the organizations where companyId = &#63; and name LIKE &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	*/
	public static void removeByC_LikeN(long companyId, String name) {
		getPersistence().removeByC_LikeN(companyId, name);
	}

	/**
	* Returns the number of organizations where companyId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching organizations
	*/
	public static int countByC_LikeN(long companyId, String name) {
		return getPersistence().countByC_LikeN(companyId, name);
	}

	/**
	* Returns the number of organizations that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching organizations that the user has permission to view
	*/
	public static int filterCountByC_LikeN(long companyId, String name) {
		return getPersistence().filterCountByC_LikeN(companyId, name);
	}

	/**
	* Returns all the organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the matching organizations
	*/
	public static List<Organization> findByO_C_P(long organizationId,
		long companyId, long parentOrganizationId) {
		return getPersistence()
				   .findByO_C_P(organizationId, companyId, parentOrganizationId);
	}

	/**
	* Returns a range of all the organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	*/
	public static List<Organization> findByO_C_P(long organizationId,
		long companyId, long parentOrganizationId, int start, int end) {
		return getPersistence()
				   .findByO_C_P(organizationId, companyId,
			parentOrganizationId, start, end);
	}

	/**
	* Returns an ordered range of all the organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByO_C_P(long organizationId,
		long companyId, long parentOrganizationId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .findByO_C_P(organizationId, companyId,
			parentOrganizationId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByO_C_P(long organizationId,
		long companyId, long parentOrganizationId, int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByO_C_P(organizationId, companyId,
			parentOrganizationId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first organization in the ordered set where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByO_C_P_First(long organizationId,
		long companyId, long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByO_C_P_First(organizationId, companyId,
			parentOrganizationId, orderByComparator);
	}

	/**
	* Returns the first organization in the ordered set where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByO_C_P_First(long organizationId,
		long companyId, long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByO_C_P_First(organizationId, companyId,
			parentOrganizationId, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByO_C_P_Last(long organizationId,
		long companyId, long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByO_C_P_Last(organizationId, companyId,
			parentOrganizationId, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByO_C_P_Last(long organizationId,
		long companyId, long parentOrganizationId,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByO_C_P_Last(organizationId, companyId,
			parentOrganizationId, orderByComparator);
	}

	/**
	* Returns all the organizations that the user has permission to view where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByO_C_P(long organizationId,
		long companyId, long parentOrganizationId) {
		return getPersistence()
				   .filterFindByO_C_P(organizationId, companyId,
			parentOrganizationId);
	}

	/**
	* Returns a range of all the organizations that the user has permission to view where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByO_C_P(long organizationId,
		long companyId, long parentOrganizationId, int start, int end) {
		return getPersistence()
				   .filterFindByO_C_P(organizationId, companyId,
			parentOrganizationId, start, end);
	}

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByO_C_P(long organizationId,
		long companyId, long parentOrganizationId, int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .filterFindByO_C_P(organizationId, companyId,
			parentOrganizationId, start, end, orderByComparator);
	}

	/**
	* Removes all the organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63; from the database.
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	*/
	public static void removeByO_C_P(long organizationId, long companyId,
		long parentOrganizationId) {
		getPersistence()
			.removeByO_C_P(organizationId, companyId, parentOrganizationId);
	}

	/**
	* Returns the number of organizations where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the number of matching organizations
	*/
	public static int countByO_C_P(long organizationId, long companyId,
		long parentOrganizationId) {
		return getPersistence()
				   .countByO_C_P(organizationId, companyId, parentOrganizationId);
	}

	/**
	* Returns the number of organizations that the user has permission to view where organizationId &gt; &#63; and companyId = &#63; and parentOrganizationId = &#63;.
	*
	* @param organizationId the organization ID
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @return the number of matching organizations that the user has permission to view
	*/
	public static int filterCountByO_C_P(long organizationId, long companyId,
		long parentOrganizationId) {
		return getPersistence()
				   .filterCountByO_C_P(organizationId, companyId,
			parentOrganizationId);
	}

	/**
	* Returns all the organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @return the matching organizations
	*/
	public static List<Organization> findByC_P_LikeN(long companyId,
		long parentOrganizationId, String name) {
		return getPersistence()
				   .findByC_P_LikeN(companyId, parentOrganizationId, name);
	}

	/**
	* Returns a range of all the organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations
	*/
	public static List<Organization> findByC_P_LikeN(long companyId,
		long parentOrganizationId, String name, int start, int end) {
		return getPersistence()
				   .findByC_P_LikeN(companyId, parentOrganizationId, name,
			start, end);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByC_P_LikeN(long companyId,
		long parentOrganizationId, String name, int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .findByC_P_LikeN(companyId, parentOrganizationId, name,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching organizations
	*/
	public static List<Organization> findByC_P_LikeN(long companyId,
		long parentOrganizationId, String name, int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_P_LikeN(companyId, parentOrganizationId, name,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByC_P_LikeN_First(long companyId,
		long parentOrganizationId, String name,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_P_LikeN_First(companyId, parentOrganizationId,
			name, orderByComparator);
	}

	/**
	* Returns the first organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_P_LikeN_First(long companyId,
		long parentOrganizationId, String name,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_LikeN_First(companyId, parentOrganizationId,
			name, orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByC_P_LikeN_Last(long companyId,
		long parentOrganizationId, String name,
		OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_P_LikeN_Last(companyId, parentOrganizationId, name,
			orderByComparator);
	}

	/**
	* Returns the last organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_P_LikeN_Last(long companyId,
		long parentOrganizationId, String name,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_LikeN_Last(companyId, parentOrganizationId,
			name, orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] findByC_P_LikeN_PrevAndNext(
		long organizationId, long companyId, long parentOrganizationId,
		String name, OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .findByC_P_LikeN_PrevAndNext(organizationId, companyId,
			parentOrganizationId, name, orderByComparator);
	}

	/**
	* Returns all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @return the matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_P_LikeN(long companyId,
		long parentOrganizationId, String name) {
		return getPersistence()
				   .filterFindByC_P_LikeN(companyId, parentOrganizationId, name);
	}

	/**
	* Returns a range of all the organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_P_LikeN(long companyId,
		long parentOrganizationId, String name, int start, int end) {
		return getPersistence()
				   .filterFindByC_P_LikeN(companyId, parentOrganizationId,
			name, start, end);
	}

	/**
	* Returns an ordered range of all the organizations that the user has permissions to view where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching organizations that the user has permission to view
	*/
	public static List<Organization> filterFindByC_P_LikeN(long companyId,
		long parentOrganizationId, String name, int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence()
				   .filterFindByC_P_LikeN(companyId, parentOrganizationId,
			name, start, end, orderByComparator);
	}

	/**
	* Returns the organizations before and after the current organization in the ordered set of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* @param organizationId the primary key of the current organization
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization[] filterFindByC_P_LikeN_PrevAndNext(
		long organizationId, long companyId, long parentOrganizationId,
		String name, OrderByComparator<Organization> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence()
				   .filterFindByC_P_LikeN_PrevAndNext(organizationId,
			companyId, parentOrganizationId, name, orderByComparator);
	}

	/**
	* Removes all the organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	*/
	public static void removeByC_P_LikeN(long companyId,
		long parentOrganizationId, String name) {
		getPersistence().removeByC_P_LikeN(companyId, parentOrganizationId, name);
	}

	/**
	* Returns the number of organizations where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @return the number of matching organizations
	*/
	public static int countByC_P_LikeN(long companyId,
		long parentOrganizationId, String name) {
		return getPersistence()
				   .countByC_P_LikeN(companyId, parentOrganizationId, name);
	}

	/**
	* Returns the number of organizations that the user has permission to view where companyId = &#63; and parentOrganizationId = &#63; and name LIKE &#63;.
	*
	* @param companyId the company ID
	* @param parentOrganizationId the parent organization ID
	* @param name the name
	* @return the number of matching organizations that the user has permission to view
	*/
	public static int filterCountByC_P_LikeN(long companyId,
		long parentOrganizationId, String name) {
		return getPersistence()
				   .filterCountByC_P_LikeN(companyId, parentOrganizationId, name);
	}

	/**
	* Returns the organization where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchOrganizationException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param externalReferenceCode the external reference code
	* @return the matching organization
	* @throws NoSuchOrganizationException if a matching organization could not be found
	*/
	public static Organization findByC_ERC(long companyId,
		String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	* Returns the organization where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param externalReferenceCode the external reference code
	* @return the matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_ERC(long companyId,
		String externalReferenceCode) {
		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	* Returns the organization where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param externalReferenceCode the external reference code
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static Organization fetchByC_ERC(long companyId,
		String externalReferenceCode, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_ERC(companyId, externalReferenceCode,
			retrieveFromCache);
	}

	/**
	* Removes the organization where companyId = &#63; and externalReferenceCode = &#63; from the database.
	*
	* @param companyId the company ID
	* @param externalReferenceCode the external reference code
	* @return the organization that was removed
	*/
	public static Organization removeByC_ERC(long companyId,
		String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	* Returns the number of organizations where companyId = &#63; and externalReferenceCode = &#63;.
	*
	* @param companyId the company ID
	* @param externalReferenceCode the external reference code
	* @return the number of matching organizations
	*/
	public static int countByC_ERC(long companyId, String externalReferenceCode) {
		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	* Caches the organization in the entity cache if it is enabled.
	*
	* @param organization the organization
	*/
	public static void cacheResult(Organization organization) {
		getPersistence().cacheResult(organization);
	}

	/**
	* Caches the organizations in the entity cache if it is enabled.
	*
	* @param organizations the organizations
	*/
	public static void cacheResult(List<Organization> organizations) {
		getPersistence().cacheResult(organizations);
	}

	/**
	* Creates a new organization with the primary key. Does not add the organization to the database.
	*
	* @param organizationId the primary key for the new organization
	* @return the new organization
	*/
	public static Organization create(long organizationId) {
		return getPersistence().create(organizationId);
	}

	/**
	* Removes the organization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param organizationId the primary key of the organization
	* @return the organization that was removed
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization remove(long organizationId)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence().remove(organizationId);
	}

	public static Organization updateImpl(Organization organization) {
		return getPersistence().updateImpl(organization);
	}

	/**
	* Returns the organization with the primary key or throws a <code>NoSuchOrganizationException</code> if it could not be found.
	*
	* @param organizationId the primary key of the organization
	* @return the organization
	* @throws NoSuchOrganizationException if a organization with the primary key could not be found
	*/
	public static Organization findByPrimaryKey(long organizationId)
		throws com.liferay.portal.kernel.exception.NoSuchOrganizationException {
		return getPersistence().findByPrimaryKey(organizationId);
	}

	/**
	* Returns the organization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param organizationId the primary key of the organization
	* @return the organization, or <code>null</code> if a organization with the primary key could not be found
	*/
	public static Organization fetchByPrimaryKey(long organizationId) {
		return getPersistence().fetchByPrimaryKey(organizationId);
	}

	/**
	* Returns all the organizations.
	*
	* @return the organizations
	*/
	public static List<Organization> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the organizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of organizations
	*/
	public static List<Organization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the organizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of organizations
	*/
	public static List<Organization> findAll(int start, int end,
		OrderByComparator<Organization> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the organizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of organizations
	*/
	public static List<Organization> findAll(int start, int end,
		OrderByComparator<Organization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the organizations from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of organizations.
	*
	* @return the number of organizations
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of groups associated with the organization.
	*
	* @param pk the primary key of the organization
	* @return long[] of the primaryKeys of groups associated with the organization
	*/
	public static long[] getGroupPrimaryKeys(long pk) {
		return getPersistence().getGroupPrimaryKeys(pk);
	}

	/**
	* Returns all the groups associated with the organization.
	*
	* @param pk the primary key of the organization
	* @return the groups associated with the organization
	*/
	public static List<com.liferay.portal.kernel.model.Group> getGroups(long pk) {
		return getPersistence().getGroups(pk);
	}

	/**
	* Returns a range of all the groups associated with the organization.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the organization
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of groups associated with the organization
	*/
	public static List<com.liferay.portal.kernel.model.Group> getGroups(
		long pk, int start, int end) {
		return getPersistence().getGroups(pk, start, end);
	}

	/**
	* Returns an ordered range of all the groups associated with the organization.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the organization
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups associated with the organization
	*/
	public static List<com.liferay.portal.kernel.model.Group> getGroups(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Group> orderByComparator) {
		return getPersistence().getGroups(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of groups associated with the organization.
	*
	* @param pk the primary key of the organization
	* @return the number of groups associated with the organization
	*/
	public static int getGroupsSize(long pk) {
		return getPersistence().getGroupsSize(pk);
	}

	/**
	* Returns <code>true</code> if the group is associated with the organization.
	*
	* @param pk the primary key of the organization
	* @param groupPK the primary key of the group
	* @return <code>true</code> if the group is associated with the organization; <code>false</code> otherwise
	*/
	public static boolean containsGroup(long pk, long groupPK) {
		return getPersistence().containsGroup(pk, groupPK);
	}

	/**
	* Returns <code>true</code> if the organization has any groups associated with it.
	*
	* @param pk the primary key of the organization to check for associations with groups
	* @return <code>true</code> if the organization has any groups associated with it; <code>false</code> otherwise
	*/
	public static boolean containsGroups(long pk) {
		return getPersistence().containsGroups(pk);
	}

	/**
	* Adds an association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groupPK the primary key of the group
	*/
	public static void addGroup(long pk, long groupPK) {
		getPersistence().addGroup(pk, groupPK);
	}

	/**
	* Adds an association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param group the group
	*/
	public static void addGroup(long pk,
		com.liferay.portal.kernel.model.Group group) {
		getPersistence().addGroup(pk, group);
	}

	/**
	* Adds an association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groupPKs the primary keys of the groups
	*/
	public static void addGroups(long pk, long[] groupPKs) {
		getPersistence().addGroups(pk, groupPKs);
	}

	/**
	* Adds an association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groups the groups
	*/
	public static void addGroups(long pk,
		List<com.liferay.portal.kernel.model.Group> groups) {
		getPersistence().addGroups(pk, groups);
	}

	/**
	* Clears all associations between the organization and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization to clear the associated groups from
	*/
	public static void clearGroups(long pk) {
		getPersistence().clearGroups(pk);
	}

	/**
	* Removes the association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groupPK the primary key of the group
	*/
	public static void removeGroup(long pk, long groupPK) {
		getPersistence().removeGroup(pk, groupPK);
	}

	/**
	* Removes the association between the organization and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param group the group
	*/
	public static void removeGroup(long pk,
		com.liferay.portal.kernel.model.Group group) {
		getPersistence().removeGroup(pk, group);
	}

	/**
	* Removes the association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groupPKs the primary keys of the groups
	*/
	public static void removeGroups(long pk, long[] groupPKs) {
		getPersistence().removeGroups(pk, groupPKs);
	}

	/**
	* Removes the association between the organization and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groups the groups
	*/
	public static void removeGroups(long pk,
		List<com.liferay.portal.kernel.model.Group> groups) {
		getPersistence().removeGroups(pk, groups);
	}

	/**
	* Sets the groups associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groupPKs the primary keys of the groups to be associated with the organization
	*/
	public static void setGroups(long pk, long[] groupPKs) {
		getPersistence().setGroups(pk, groupPKs);
	}

	/**
	* Sets the groups associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param groups the groups to be associated with the organization
	*/
	public static void setGroups(long pk,
		List<com.liferay.portal.kernel.model.Group> groups) {
		getPersistence().setGroups(pk, groups);
	}

	/**
	* Returns the primaryKeys of users associated with the organization.
	*
	* @param pk the primary key of the organization
	* @return long[] of the primaryKeys of users associated with the organization
	*/
	public static long[] getUserPrimaryKeys(long pk) {
		return getPersistence().getUserPrimaryKeys(pk);
	}

	/**
	* Returns all the users associated with the organization.
	*
	* @param pk the primary key of the organization
	* @return the users associated with the organization
	*/
	public static List<com.liferay.portal.kernel.model.User> getUsers(long pk) {
		return getPersistence().getUsers(pk);
	}

	/**
	* Returns a range of all the users associated with the organization.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the organization
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of users associated with the organization
	*/
	public static List<com.liferay.portal.kernel.model.User> getUsers(long pk,
		int start, int end) {
		return getPersistence().getUsers(pk, start, end);
	}

	/**
	* Returns an ordered range of all the users associated with the organization.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>OrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the organization
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users associated with the organization
	*/
	public static List<com.liferay.portal.kernel.model.User> getUsers(long pk,
		int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.User> orderByComparator) {
		return getPersistence().getUsers(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of users associated with the organization.
	*
	* @param pk the primary key of the organization
	* @return the number of users associated with the organization
	*/
	public static int getUsersSize(long pk) {
		return getPersistence().getUsersSize(pk);
	}

	/**
	* Returns <code>true</code> if the user is associated with the organization.
	*
	* @param pk the primary key of the organization
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the organization; <code>false</code> otherwise
	*/
	public static boolean containsUser(long pk, long userPK) {
		return getPersistence().containsUser(pk, userPK);
	}

	/**
	* Returns <code>true</code> if the organization has any users associated with it.
	*
	* @param pk the primary key of the organization to check for associations with users
	* @return <code>true</code> if the organization has any users associated with it; <code>false</code> otherwise
	*/
	public static boolean containsUsers(long pk) {
		return getPersistence().containsUsers(pk);
	}

	/**
	* Adds an association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param userPK the primary key of the user
	*/
	public static void addUser(long pk, long userPK) {
		getPersistence().addUser(pk, userPK);
	}

	/**
	* Adds an association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param user the user
	*/
	public static void addUser(long pk,
		com.liferay.portal.kernel.model.User user) {
		getPersistence().addUser(pk, user);
	}

	/**
	* Adds an association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param userPKs the primary keys of the users
	*/
	public static void addUsers(long pk, long[] userPKs) {
		getPersistence().addUsers(pk, userPKs);
	}

	/**
	* Adds an association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param users the users
	*/
	public static void addUsers(long pk,
		List<com.liferay.portal.kernel.model.User> users) {
		getPersistence().addUsers(pk, users);
	}

	/**
	* Clears all associations between the organization and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization to clear the associated users from
	*/
	public static void clearUsers(long pk) {
		getPersistence().clearUsers(pk);
	}

	/**
	* Removes the association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param userPK the primary key of the user
	*/
	public static void removeUser(long pk, long userPK) {
		getPersistence().removeUser(pk, userPK);
	}

	/**
	* Removes the association between the organization and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param user the user
	*/
	public static void removeUser(long pk,
		com.liferay.portal.kernel.model.User user) {
		getPersistence().removeUser(pk, user);
	}

	/**
	* Removes the association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param userPKs the primary keys of the users
	*/
	public static void removeUsers(long pk, long[] userPKs) {
		getPersistence().removeUsers(pk, userPKs);
	}

	/**
	* Removes the association between the organization and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param users the users
	*/
	public static void removeUsers(long pk,
		List<com.liferay.portal.kernel.model.User> users) {
		getPersistence().removeUsers(pk, users);
	}

	/**
	* Sets the users associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param userPKs the primary keys of the users to be associated with the organization
	*/
	public static void setUsers(long pk, long[] userPKs) {
		getPersistence().setUsers(pk, userPKs);
	}

	/**
	* Sets the users associated with the organization, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the organization
	* @param users the users to be associated with the organization
	*/
	public static void setUsers(long pk,
		List<com.liferay.portal.kernel.model.User> users) {
		getPersistence().setUsers(pk, users);
	}

	public static OrganizationPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (OrganizationPersistence)PortalBeanLocatorUtil.locate(OrganizationPersistence.class.getName());

			ReferenceRegistry.registerReference(OrganizationUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static OrganizationPersistence _persistence;
}