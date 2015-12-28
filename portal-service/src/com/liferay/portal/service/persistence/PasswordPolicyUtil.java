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
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the password policy service. This utility wraps {@link com.liferay.portal.service.persistence.impl.PasswordPolicyPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyPersistence
 * @see com.liferay.portal.service.persistence.impl.PasswordPolicyPersistenceImpl
 * @generated
 */
@ProviderType
public class PasswordPolicyUtil {
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
	public static void clearCache(PasswordPolicy passwordPolicy) {
		getPersistence().clearCache(passwordPolicy);
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
	public static List<PasswordPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PasswordPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PasswordPolicy> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static PasswordPolicy update(PasswordPolicy passwordPolicy) {
		return getPersistence().update(passwordPolicy);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static PasswordPolicy update(PasswordPolicy passwordPolicy,
		ServiceContext serviceContext) {
		return getPersistence().update(passwordPolicy, serviceContext);
	}

	/**
	* Returns all the password policies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching password policies
	*/
	public static List<PasswordPolicy> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the password policies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies
	*/
	public static List<PasswordPolicy> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the password policies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies
	*/
	public static List<PasswordPolicy> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the password policies where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching password policies
	*/
	public static List<PasswordPolicy> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<PasswordPolicy> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first password policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy
	* @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public static PasswordPolicy findByUuid_First(java.lang.String uuid,
		OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first password policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public static PasswordPolicy fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last password policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy
	* @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public static PasswordPolicy findByUuid_Last(java.lang.String uuid,
		OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last password policy in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public static PasswordPolicy fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the password policies before and after the current password policy in the ordered set where uuid = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public static PasswordPolicy[] findByUuid_PrevAndNext(
		long passwordPolicyId, java.lang.String uuid,
		OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence()
				   .findByUuid_PrevAndNext(passwordPolicyId, uuid,
			orderByComparator);
	}

	/**
	* Returns all the password policies that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching password policies that the user has permission to view
	*/
	public static List<PasswordPolicy> filterFindByUuid(java.lang.String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	* Returns a range of all the password policies that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies that the user has permission to view
	*/
	public static List<PasswordPolicy> filterFindByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the password policies that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies that the user has permission to view
	*/
	public static List<PasswordPolicy> filterFindByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence()
				   .filterFindByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where uuid = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public static PasswordPolicy[] filterFindByUuid_PrevAndNext(
		long passwordPolicyId, java.lang.String uuid,
		OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence()
				   .filterFindByUuid_PrevAndNext(passwordPolicyId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the password policies where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of password policies where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching password policies
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of password policies that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching password policies that the user has permission to view
	*/
	public static int filterCountByUuid(java.lang.String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	* Returns all the password policies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching password policies
	*/
	public static List<PasswordPolicy> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the password policies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies
	*/
	public static List<PasswordPolicy> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the password policies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies
	*/
	public static List<PasswordPolicy> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the password policies where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching password policies
	*/
	public static List<PasswordPolicy> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<PasswordPolicy> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy
	* @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public static PasswordPolicy findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public static PasswordPolicy fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy
	* @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public static PasswordPolicy findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public static PasswordPolicy fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the password policies before and after the current password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public static PasswordPolicy[] findByUuid_C_PrevAndNext(
		long passwordPolicyId, java.lang.String uuid, long companyId,
		OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(passwordPolicyId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Returns all the password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching password policies that the user has permission to view
	*/
	public static List<PasswordPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies that the user has permission to view
	*/
	public static List<PasswordPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the password policies that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies that the user has permission to view
	*/
	public static List<PasswordPolicy> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence()
				   .filterFindByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public static PasswordPolicy[] filterFindByUuid_C_PrevAndNext(
		long passwordPolicyId, java.lang.String uuid, long companyId,
		OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence()
				   .filterFindByUuid_C_PrevAndNext(passwordPolicyId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the password policies where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of password policies where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching password policies
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching password policies that the user has permission to view
	*/
	public static int filterCountByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the password policies where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching password policies
	*/
	public static List<PasswordPolicy> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the password policies where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies
	*/
	public static List<PasswordPolicy> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the password policies where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies
	*/
	public static List<PasswordPolicy> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the password policies where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching password policies
	*/
	public static List<PasswordPolicy> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<PasswordPolicy> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first password policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy
	* @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public static PasswordPolicy findByCompanyId_First(long companyId,
		OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first password policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public static PasswordPolicy fetchByCompanyId_First(long companyId,
		OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last password policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy
	* @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public static PasswordPolicy findByCompanyId_Last(long companyId,
		OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last password policy in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public static PasswordPolicy fetchByCompanyId_Last(long companyId,
		OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the password policies before and after the current password policy in the ordered set where companyId = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public static PasswordPolicy[] findByCompanyId_PrevAndNext(
		long passwordPolicyId, long companyId,
		OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(passwordPolicyId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the password policies that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching password policies that the user has permission to view
	*/
	public static List<PasswordPolicy> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	* Returns a range of all the password policies that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of matching password policies that the user has permission to view
	*/
	public static List<PasswordPolicy> filterFindByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the password policies that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching password policies that the user has permission to view
	*/
	public static List<PasswordPolicy> filterFindByCompanyId(long companyId,
		int start, int end, OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence()
				   .filterFindByCompanyId(companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where companyId = &#63;.
	*
	* @param passwordPolicyId the primary key of the current password policy
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next password policy
	* @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public static PasswordPolicy[] filterFindByCompanyId_PrevAndNext(
		long passwordPolicyId, long companyId,
		OrderByComparator<PasswordPolicy> orderByComparator)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence()
				   .filterFindByCompanyId_PrevAndNext(passwordPolicyId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the password policies where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of password policies where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching password policies
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of password policies that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching password policies that the user has permission to view
	*/
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	* Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or throws a {@link NoSuchPasswordPolicyException} if it could not be found.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the matching password policy
	* @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public static PasswordPolicy findByC_DP(long companyId,
		boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().findByC_DP(companyId, defaultPolicy);
	}

	/**
	* Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public static PasswordPolicy fetchByC_DP(long companyId,
		boolean defaultPolicy) {
		return getPersistence().fetchByC_DP(companyId, defaultPolicy);
	}

	/**
	* Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public static PasswordPolicy fetchByC_DP(long companyId,
		boolean defaultPolicy, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_DP(companyId, defaultPolicy, retrieveFromCache);
	}

	/**
	* Removes the password policy where companyId = &#63; and defaultPolicy = &#63; from the database.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the password policy that was removed
	*/
	public static PasswordPolicy removeByC_DP(long companyId,
		boolean defaultPolicy)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().removeByC_DP(companyId, defaultPolicy);
	}

	/**
	* Returns the number of password policies where companyId = &#63; and defaultPolicy = &#63;.
	*
	* @param companyId the company ID
	* @param defaultPolicy the default policy
	* @return the number of matching password policies
	*/
	public static int countByC_DP(long companyId, boolean defaultPolicy) {
		return getPersistence().countByC_DP(companyId, defaultPolicy);
	}

	/**
	* Returns the password policy where companyId = &#63; and name = &#63; or throws a {@link NoSuchPasswordPolicyException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching password policy
	* @throws NoSuchPasswordPolicyException if a matching password policy could not be found
	*/
	public static PasswordPolicy findByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().findByC_N(companyId, name);
	}

	/**
	* Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public static PasswordPolicy fetchByC_N(long companyId,
		java.lang.String name) {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	* Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	public static PasswordPolicy fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	/**
	* Removes the password policy where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the password policy that was removed
	*/
	public static PasswordPolicy removeByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	* Returns the number of password policies where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching password policies
	*/
	public static int countByC_N(long companyId, java.lang.String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	* Caches the password policy in the entity cache if it is enabled.
	*
	* @param passwordPolicy the password policy
	*/
	public static void cacheResult(PasswordPolicy passwordPolicy) {
		getPersistence().cacheResult(passwordPolicy);
	}

	/**
	* Caches the password policies in the entity cache if it is enabled.
	*
	* @param passwordPolicies the password policies
	*/
	public static void cacheResult(List<PasswordPolicy> passwordPolicies) {
		getPersistence().cacheResult(passwordPolicies);
	}

	/**
	* Creates a new password policy with the primary key. Does not add the password policy to the database.
	*
	* @param passwordPolicyId the primary key for the new password policy
	* @return the new password policy
	*/
	public static PasswordPolicy create(long passwordPolicyId) {
		return getPersistence().create(passwordPolicyId);
	}

	/**
	* Removes the password policy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy that was removed
	* @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public static PasswordPolicy remove(long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().remove(passwordPolicyId);
	}

	public static PasswordPolicy updateImpl(PasswordPolicy passwordPolicy) {
		return getPersistence().updateImpl(passwordPolicy);
	}

	/**
	* Returns the password policy with the primary key or throws a {@link NoSuchPasswordPolicyException} if it could not be found.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy
	* @throws NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	*/
	public static PasswordPolicy findByPrimaryKey(long passwordPolicyId)
		throws com.liferay.portal.NoSuchPasswordPolicyException {
		return getPersistence().findByPrimaryKey(passwordPolicyId);
	}

	/**
	* Returns the password policy with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy, or <code>null</code> if a password policy with the primary key could not be found
	*/
	public static PasswordPolicy fetchByPrimaryKey(long passwordPolicyId) {
		return getPersistence().fetchByPrimaryKey(passwordPolicyId);
	}

	public static java.util.Map<java.io.Serializable, PasswordPolicy> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the password policies.
	*
	* @return the password policies
	*/
	public static List<PasswordPolicy> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the password policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of password policies
	*/
	public static List<PasswordPolicy> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the password policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of password policies
	*/
	public static List<PasswordPolicy> findAll(int start, int end,
		OrderByComparator<PasswordPolicy> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the password policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of password policies
	*/
	public static List<PasswordPolicy> findAll(int start, int end,
		OrderByComparator<PasswordPolicy> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the password policies from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of password policies.
	*
	* @return the number of password policies
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static PasswordPolicyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PasswordPolicyPersistence)PortalBeanLocatorUtil.locate(PasswordPolicyPersistence.class.getName());

			ReferenceRegistry.registerReference(PasswordPolicyUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static PasswordPolicyPersistence _persistence;
}