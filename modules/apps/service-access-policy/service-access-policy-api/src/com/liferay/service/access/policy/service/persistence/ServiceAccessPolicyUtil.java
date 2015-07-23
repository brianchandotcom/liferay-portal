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

package com.liferay.service.access.policy.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.service.access.policy.model.ServiceAccessPolicy;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the service access policy service. This utility wraps {@link com.liferay.service.access.policy.service.persistence.impl.ServiceAccessPolicyPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceAccessPolicyPersistence
 * @see com.liferay.service.access.policy.service.persistence.impl.ServiceAccessPolicyPersistenceImpl
 * @generated
 */
@ProviderType
public class ServiceAccessPolicyUtil {
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
	public static void clearCache(ServiceAccessPolicy serviceAccessPolicy) {
		getPersistence().clearCache(serviceAccessPolicy);
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
	public static List<ServiceAccessPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ServiceAccessPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ServiceAccessPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ServiceAccessPolicy update(
		ServiceAccessPolicy serviceAccessPolicy) {
		return getPersistence().update(serviceAccessPolicy);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ServiceAccessPolicy update(
		ServiceAccessPolicy serviceAccessPolicy, ServiceContext serviceContext) {
		return getPersistence().update(serviceAccessPolicy, serviceContext);
	}

	/**
	* Returns all the service access policies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the service access policies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @return the range of matching service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the service access policies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first service access policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first service access policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last service access policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last service access policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the service access policies before and after the current service access policy in the ordered set where uuid = &#63;.
	*
	* @param serviceAccessPolicyId the primary key of the current service access policy
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy[] findByUuid_PrevAndNext(
		long serviceAccessPolicyId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence()
				   .findByUuid_PrevAndNext(serviceAccessPolicyId, uuid,
			orderByComparator);
	}

	/**
	* Returns all the service access policies that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching service access policies that the user has permission to view
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid(
		java.lang.String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	* Returns a range of all the service access policies that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @return the range of matching service access policies that the user has permission to view
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the service access policies that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service access policies that the user has permission to view
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence()
				   .filterFindByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the service access policies before and after the current service access policy in the ordered set of service access policies that the user has permission to view where uuid = &#63;.
	*
	* @param serviceAccessPolicyId the primary key of the current service access policy
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy[] filterFindByUuid_PrevAndNext(
		long serviceAccessPolicyId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence()
				   .filterFindByUuid_PrevAndNext(serviceAccessPolicyId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the service access policies where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of service access policies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching service access policies
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of service access policies that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching service access policies that the user has permission to view
	*/
	public static int filterCountByUuid(java.lang.String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	* Returns all the service access policies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the service access policies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @return the range of matching service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the service access policies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the service access policies before and after the current service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param serviceAccessPolicyId the primary key of the current service access policy
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy[] findByUuid_C_PrevAndNext(
		long serviceAccessPolicyId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(serviceAccessPolicyId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Returns all the service access policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching service access policies that the user has permission to view
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the service access policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @return the range of matching service access policies that the user has permission to view
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the service access policies that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service access policies that the user has permission to view
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence()
				   .filterFindByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the service access policies before and after the current service access policy in the ordered set of service access policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param serviceAccessPolicyId the primary key of the current service access policy
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy[] filterFindByUuid_C_PrevAndNext(
		long serviceAccessPolicyId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence()
				   .filterFindByUuid_C_PrevAndNext(serviceAccessPolicyId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the service access policies where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of service access policies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching service access policies
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of service access policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching service access policies that the user has permission to view
	*/
	public static int filterCountByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the service access policies where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the service access policies where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @return the range of matching service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the service access policies where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first service access policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first service access policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last service access policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last service access policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the service access policies before and after the current service access policy in the ordered set where companyId = &#63;.
	*
	* @param serviceAccessPolicyId the primary key of the current service access policy
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy[] findByCompanyId_PrevAndNext(
		long serviceAccessPolicyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(serviceAccessPolicyId,
			companyId, orderByComparator);
	}

	/**
	* Returns all the service access policies that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching service access policies that the user has permission to view
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByCompanyId(
		long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	* Returns a range of all the service access policies that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @return the range of matching service access policies that the user has permission to view
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the service access policies that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service access policies that the user has permission to view
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence()
				   .filterFindByCompanyId(companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the service access policies before and after the current service access policy in the ordered set of service access policies that the user has permission to view where companyId = &#63;.
	*
	* @param serviceAccessPolicyId the primary key of the current service access policy
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy[] filterFindByCompanyId_PrevAndNext(
		long serviceAccessPolicyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence()
				   .filterFindByCompanyId_PrevAndNext(serviceAccessPolicyId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the service access policies where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of service access policies where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching service access policies
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of service access policies that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching service access policies that the user has permission to view
	*/
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	* Returns the service access policy where companyId = &#63; and name = &#63; or throws a {@link com.liferay.service.access.policy.NoSuchServiceAccessPolicyException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy findByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence().findByC_N(companyId, name);
	}

	/**
	* Returns the service access policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByC_N(
		long companyId, java.lang.String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	* Returns the service access policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByC_N(
		long companyId, java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	/**
	* Removes the service access policy where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the service access policy that was removed
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy removeByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	* Returns the number of service access policies where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching service access policies
	*/
	public static int countByC_N(long companyId, java.lang.String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	* Caches the service access policy in the entity cache if it is enabled.
	*
	* @param serviceAccessPolicy the service access policy
	*/
	public static void cacheResult(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy) {
		getPersistence().cacheResult(serviceAccessPolicy);
	}

	/**
	* Caches the service access policies in the entity cache if it is enabled.
	*
	* @param serviceAccessPolicies the service access policies
	*/
	public static void cacheResult(
		java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> serviceAccessPolicies) {
		getPersistence().cacheResult(serviceAccessPolicies);
	}

	/**
	* Creates a new service access policy with the primary key. Does not add the service access policy to the database.
	*
	* @param serviceAccessPolicyId the primary key for the new service access policy
	* @return the new service access policy
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy create(
		long serviceAccessPolicyId) {
		return getPersistence().create(serviceAccessPolicyId);
	}

	/**
	* Removes the service access policy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceAccessPolicyId the primary key of the service access policy
	* @return the service access policy that was removed
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy remove(
		long serviceAccessPolicyId)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence().remove(serviceAccessPolicyId);
	}

	public static com.liferay.service.access.policy.model.ServiceAccessPolicy updateImpl(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy) {
		return getPersistence().updateImpl(serviceAccessPolicy);
	}

	/**
	* Returns the service access policy with the primary key or throws a {@link com.liferay.service.access.policy.NoSuchServiceAccessPolicyException} if it could not be found.
	*
	* @param serviceAccessPolicyId the primary key of the service access policy
	* @return the service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy findByPrimaryKey(
		long serviceAccessPolicyId)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException {
		return getPersistence().findByPrimaryKey(serviceAccessPolicyId);
	}

	/**
	* Returns the service access policy with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param serviceAccessPolicyId the primary key of the service access policy
	* @return the service access policy, or <code>null</code> if a service access policy with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByPrimaryKey(
		long serviceAccessPolicyId) {
		return getPersistence().fetchByPrimaryKey(serviceAccessPolicyId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.service.access.policy.model.ServiceAccessPolicy> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the service access policies.
	*
	* @return the service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the service access policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @return the range of service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the service access policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service access policies
	* @param end the upper bound of the range of service access policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of service access policies
	*/
	public static java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the service access policies from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of service access policies.
	*
	* @return the number of service access policies
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ServiceAccessPolicyPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(ServiceAccessPolicyPersistence persistence) {
	}

	private static ServiceTracker<ServiceAccessPolicyPersistence, ServiceAccessPolicyPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ServiceAccessPolicyUtil.class);

		_serviceTracker = new ServiceTracker<ServiceAccessPolicyPersistence, ServiceAccessPolicyPersistence>(bundle.getBundleContext(),
				ServiceAccessPolicyPersistence.class, null);

		_serviceTracker.open();
	}
}