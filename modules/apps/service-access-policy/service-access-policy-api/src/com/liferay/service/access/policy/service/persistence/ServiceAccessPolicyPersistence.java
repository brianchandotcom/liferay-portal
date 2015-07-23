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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.service.access.policy.model.ServiceAccessPolicy;

/**
 * The persistence interface for the service access policy service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.service.access.policy.service.persistence.impl.ServiceAccessPolicyPersistenceImpl
 * @see ServiceAccessPolicyUtil
 * @generated
 */
@ProviderType
public interface ServiceAccessPolicyPersistence extends BasePersistence<ServiceAccessPolicy> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ServiceAccessPolicyUtil} to access the service access policy persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the service access policies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching service access policies
	*/
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid(
		java.lang.String uuid);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid(
		java.lang.String uuid, int start, int end);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Returns the first service access policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns the first service access policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Returns the last service access policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns the last service access policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Returns the service access policies before and after the current service access policy in the ordered set where uuid = &#63;.
	*
	* @param serviceAccessPolicyId the primary key of the current service access policy
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy[] findByUuid_PrevAndNext(
		long serviceAccessPolicyId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns all the service access policies that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching service access policies that the user has permission to view
	*/
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid(
		java.lang.String uuid);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid(
		java.lang.String uuid, int start, int end);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Returns the service access policies before and after the current service access policy in the ordered set of service access policies that the user has permission to view where uuid = &#63;.
	*
	* @param serviceAccessPolicyId the primary key of the current service access policy
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy[] filterFindByUuid_PrevAndNext(
		long serviceAccessPolicyId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Removes all the service access policies where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of service access policies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching service access policies
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the number of service access policies that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching service access policies that the user has permission to view
	*/
	public int filterCountByUuid(java.lang.String uuid);

	/**
	* Returns all the service access policies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching service access policies
	*/
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid_C(
		java.lang.String uuid, long companyId);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Returns the first service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns the first service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Returns the last service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns the last service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

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
	public com.liferay.service.access.policy.model.ServiceAccessPolicy[] findByUuid_C_PrevAndNext(
		long serviceAccessPolicyId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns all the service access policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching service access policies that the user has permission to view
	*/
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

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
	public com.liferay.service.access.policy.model.ServiceAccessPolicy[] filterFindByUuid_C_PrevAndNext(
		long serviceAccessPolicyId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Removes all the service access policies where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of service access policies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching service access policies
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of service access policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching service access policies that the user has permission to view
	*/
	public int filterCountByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the service access policies where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching service access policies
	*/
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByCompanyId(
		long companyId);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Returns the first service access policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns the first service access policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Returns the last service access policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns the last service access policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Returns the service access policies before and after the current service access policy in the ordered set where companyId = &#63;.
	*
	* @param serviceAccessPolicyId the primary key of the current service access policy
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy[] findByCompanyId_PrevAndNext(
		long serviceAccessPolicyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns all the service access policies that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching service access policies that the user has permission to view
	*/
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByCompanyId(
		long companyId);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Returns the service access policies before and after the current service access policy in the ordered set of service access policies that the user has permission to view where companyId = &#63;.
	*
	* @param serviceAccessPolicyId the primary key of the current service access policy
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy[] filterFindByCompanyId_PrevAndNext(
		long serviceAccessPolicyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Removes all the service access policies where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of service access policies where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching service access policies
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the number of service access policies that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching service access policies that the user has permission to view
	*/
	public int filterCountByCompanyId(long companyId);

	/**
	* Returns the service access policy where companyId = &#63; and name = &#63; or throws a {@link com.liferay.service.access.policy.NoSuchServiceAccessPolicyException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy findByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns the service access policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByC_N(
		long companyId, java.lang.String name);

	/**
	* Returns the service access policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching service access policy, or <code>null</code> if a matching service access policy could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByC_N(
		long companyId, java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the service access policy where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the service access policy that was removed
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy removeByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns the number of service access policies where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching service access policies
	*/
	public int countByC_N(long companyId, java.lang.String name);

	/**
	* Caches the service access policy in the entity cache if it is enabled.
	*
	* @param serviceAccessPolicy the service access policy
	*/
	public void cacheResult(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy);

	/**
	* Caches the service access policies in the entity cache if it is enabled.
	*
	* @param serviceAccessPolicies the service access policies
	*/
	public void cacheResult(
		java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> serviceAccessPolicies);

	/**
	* Creates a new service access policy with the primary key. Does not add the service access policy to the database.
	*
	* @param serviceAccessPolicyId the primary key for the new service access policy
	* @return the new service access policy
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy create(
		long serviceAccessPolicyId);

	/**
	* Removes the service access policy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceAccessPolicyId the primary key of the service access policy
	* @return the service access policy that was removed
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy remove(
		long serviceAccessPolicyId)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	public com.liferay.service.access.policy.model.ServiceAccessPolicy updateImpl(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy);

	/**
	* Returns the service access policy with the primary key or throws a {@link com.liferay.service.access.policy.NoSuchServiceAccessPolicyException} if it could not be found.
	*
	* @param serviceAccessPolicyId the primary key of the service access policy
	* @return the service access policy
	* @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy findByPrimaryKey(
		long serviceAccessPolicyId)
		throws com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;

	/**
	* Returns the service access policy with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param serviceAccessPolicyId the primary key of the service access policy
	* @return the service access policy, or <code>null</code> if a service access policy with the primary key could not be found
	*/
	public com.liferay.service.access.policy.model.ServiceAccessPolicy fetchByPrimaryKey(
		long serviceAccessPolicyId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.service.access.policy.model.ServiceAccessPolicy> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the service access policies.
	*
	* @return the service access policies
	*/
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findAll();

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findAll(
		int start, int end);

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
	public java.util.List<com.liferay.service.access.policy.model.ServiceAccessPolicy> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.ServiceAccessPolicy> orderByComparator);

	/**
	* Removes all the service access policies from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of service access policies.
	*
	* @return the number of service access policies
	*/
	public int countAll();
}